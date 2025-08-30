package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.AECamera;
import AE.GlobalStatus;
import AE.LookAtCamera;
import AE.TargetFollowCamera;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;

public final class LevelScript {
	private final Level level;
	private int step;
	private boolean cinematicBreak_;
	private boolean active;
	private boolean startSequenceIsOver;
	private final LookAtCamera lookAtCam;
	private int passedTime;
	private AEVector3D tempVec = new AEVector3D();
	private AEVector3D approachStationCamPos = new AEVector3D();
	private AEVector3D tempVec2 = new AEVector3D();
	public boolean unused_428;
	private final Hud hud;
	private final Radar radar;
	private AEGeometry probe;
	public int timeLimit;
	public long timePassed;

	public LevelScript(final TargetFollowCamera tFollowCam, final LookAtCamera lookAtCam, final Level level, final Hud hud, final Radar radar) {
		this.hud = hud;
		this.radar = radar;
		this.level = level;
		this.step = 0;
		this.cinematicBreak_ = true;
		this.active = true;
		this.startSequenceIsOver = false;
		this.lookAtCam = lookAtCam;
		this.unused_428 = false;
		this.timeLimit = level.getTimeLimit();
		this.timePassed = 0L;
		tFollowCam.setLookAtCam(false);
		lookAtCam.setLookAt(true);
		if (level.getPlayer() == null) {
			this.active = false;
		}

		if (Status.getCurrentCampaignMission() == 0) {
			hud.drawUI = false;
			radar.draw = false;
			level.getPlayer().setFreeze(true);
			level.getPlayer().setComputerControlled(true);
			GlobalStatus.renderer.getCamera().moveTo(0, 0, 0);
			GlobalStatus.renderer.getCamera().setRotation(0, 1800, 0);
			this.startSequenceIsOver = true;
			tFollowCam.setLookAtCam(false);
			lookAtCam.setLookAt(false);
			level.getPlayer().setPosition_(0, 0, 120000);
		} else if (Status.getCurrentCampaignMission() == 1) {
			hud.drawUI = false;
			radar.draw = false;
			level.getPlayer().shipGrandGroup_.setRotation(300, 300, 1000);
			level.getPlayer().setFreeze(true);
			level.getPlayer().setComputerControlled(true);
			level.getPlayer().setPosition_(0, 0, 0);
			level.getPlayer().setExhaustVisible(false);
			GlobalStatus.renderer.getCamera().moveTo(1500, 600, -3000);
			this.startSequenceIsOver = true;
			lookAtCam.setLookAt(true);
			lookAtCam.setTarget(level.getPlayer().shipGrandGroup_);
		} else {
			if (Level.initStreamOutPosition) {
				this.tempVec.x = 500 + GlobalStatus.random.nextInt(500);
				this.tempVec.y = 500 + GlobalStatus.random.nextInt(500);
				this.tempVec.z = 10000;
			} else {
				this.tempVec.x = 1000 + GlobalStatus.random.nextInt(2000);
				this.tempVec.y = 500 + GlobalStatus.random.nextInt(2000);
				this.tempVec.z = 9000;
			}

			if (GlobalStatus.random.nextInt(2) == 0) {
				this.tempVec.x = -this.tempVec.x;
			}

			if (GlobalStatus.random.nextInt(2) == 0) {
				this.tempVec.y = -this.tempVec.y;
			}

			AEMatrix var6 = new AEMatrix();
			var6.setEulerY(level.getPlayer().shipGrandGroup_.getEulerY());
			this.approachStationCamPos = var6.transformVectorNoScale(this.tempVec, this.approachStationCamPos);
			this.approachStationCamPos.add(level.getPlayer().shipGrandGroup_.getPosition(this.tempVec));
			GlobalStatus.renderer.getCamera().moveTo(this.approachStationCamPos);
			tFollowCam.setCameraPosition(this.approachStationCamPos);
			lookAtCam.setOrientationLock(level.getPlayer().shipGrandGroup_.getUp(), 2);
			if (Status.inAlienOrbit() || Level.comingFromAlienWorld) {
				if (Status.getCurrentCampaignMission() < 43) {
					this.tempVec2 = level.getPlayer().shipGrandGroup_.getDirection(this.tempVec2);
					this.tempVec2.scale(AEMath.Q_2);
					this.tempVec.subtract(this.tempVec2);
					((PlayerWormHole)level.getLandmarks()[3]).reset(true);
					level.getLandmarks()[3].setPosition(this.tempVec.x, this.tempVec.y, this.tempVec.z);
					level.getLandmarks()[3].setVisible(true);
				}

				Level.comingFromAlienWorld = false;
			}
		}

		level.getPlayer().setCollide(false);
	}
	/** #TODO uninline */
	public final boolean process(final int dt, final TargetFollowCamera followCamera) {
		final RadioMessage[] rMessages = this.level.getMessages();
		final PlayerEgo playerEgo = this.level.getPlayer();
		final AECamera camera = GlobalStatus.renderer.getCamera();
		final KIPlayer[] enemies = this.level.getEnemies();
		int i;
		if (this.active) {
			this.passedTime += dt;
			if (Status.getCurrentCampaignMission() != 0) {
				if (Status.getCurrentCampaignMission() == 1) {
					playerEgo.shipGrandGroup_.rotateEuler(0, dt >> 3, 0);
					if (enemies[0].getPosition(this.tempVec).z < -1800) {
						enemies[0].geometry.moveForward(dt >> 1);
					} else {
						((PlayerFighter)enemies[0]).state = 5;
						((PlayerFighter)enemies[0]).setExhaustVisible(false);
					}

					if (rMessages[2].isOver()) {
						GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[1]);
						return false;
					}
				}
			} else if (!rMessages[1].isTriggered()) {
				camera.rotateEuler(0, dt >> 4, 0);
				enemies[0].setToSleep();
				enemies[1].setToSleep();
			} else if (this.step == 0) {
				this.lookAtCam.setLookAt(true);
				playerEgo.setFreeze(false);
				playerEgo.shipGrandGroup_.setRotation(0, AEMath.Q_PI_HALF, 0);
				camera.moveTo(-1000, -500, 110000);
				this.step = 1;
			} else {
				if (this.step < 6) {
					for(i = 0; i < 2; ++i) {
						final int dy = AEMath.sin(this.passedTime) >> 10;
						enemies[i].geometry.translate(0, dy, 0);
					}

					enemies[0].setToSleep();
					enemies[1].setToSleep();
				}

				if (rMessages[2].isOver() && this.step == 1) {
					playerEgo.setFreeze(true);
					this.lookAtCam.setTarget(enemies[0].geometry);
					camera.moveTo(enemies[0].getPosition(this.tempVec));
					camera.translate(1000, 700, 1500);
					this.step = 2;
				}

				if (rMessages[3].isOver() && this.step == 2) {
					this.lookAtCam.setTarget(enemies[1].geometry);
					camera.moveTo(enemies[1].getPosition(this.tempVec));
					camera.translate(-2300, 300, 200);
					this.step = 3;
				}

				if (rMessages[5].isOver() && this.step == 3) {
					enemies[2].awake();
					this.lookAtCam.setTarget(enemies[2].geometry);
					camera.moveTo(enemies[2].getPosition(this.tempVec));
					camera.translate(1000, 200, 6000);
					this.step = 4;
				}

				if (rMessages[6].isOver() && this.step == 4) {
					this.lookAtCam.setTarget(enemies[1].geometry);
					camera.moveTo(enemies[1].getPosition(this.tempVec));
					camera.translate(-1300, 300, 1700);
					this.step = 5;
				}

				if (rMessages[7].isOver() && this.step == 5) {
					this.radar.draw = true;
					this.level.getPlayer().setCollide(true);
					playerEgo.setFreeze(false);
					this.lookAtCam.setTarget(playerEgo.shipGrandGroup_);
					this.lookAtCam.setLookAt(false);
					followCamera.setLookAtCam(true);
					this.step = 6;
					GlobalStatus.soundManager.playMusic(3);
				}

				if (rMessages[8].isOver() && this.step == 6) {
					enemies[0].awake();
					enemies[1].awake();
					enemies[2].awake();
					((PlayerFighter)enemies[0]).setExhaustVisible(true);
					((PlayerFighter)enemies[1]).setExhaustVisible(true);
					playerEgo.setComputerControlled(false);
					this.cinematicBreak_ = false;
					this.step = 7;
				}

				if (rMessages[10].isTriggered() && this.step == 7) {
					this.level.getPlayer().setCollide(false);
					this.radar.draw = false;
					playerEgo.setComputerControlled(true);
					playerEgo.player.removeAllGuns();
					followCamera.setLookAtCam(false);
					this.lookAtCam.setLookAt(true);
					this.lookAtCam.setTarget(playerEgo.shipGrandGroup_);
					playerEgo.shipGrandGroup_.setRotation(0, AEMath.Q_PI_HALF, 0);
					camera.moveTo(playerEgo.shipGrandGroup_.getPostition());
					camera.translate(1000, -200, -60000);
					this.step = 8;
					this.passedTime = 0;
				}

				if (rMessages[12].isOver() && this.step == 8) {
					playerEgo.setFreeze(true);
					this.step = 9;
					playerEgo.fakeExplode();
					this.level.flashScreen(Explosion.HUGE);
					playerEgo.setExhaustVisible(false);
				}

				if (this.step == 9) {
					playerEgo.shipGrandGroup_.rotateEuler(dt >> 1, dt >> 1, dt >> 1);
					if (rMessages[16].isOver()) {
						this.step = 10;
					}
				}

				if (this.step == 10) {
					playerEgo.setFreeze(false);
					camera.moveTo(playerEgo.shipGrandGroup_.getPostition());
					camera.setRotation(0, 0, 0);
					camera.translate(-1000, -700, -1500);
					if (rMessages[14].isOver()) {
						playerEgo.setExhaustVisible(true);
						this.step = 11;
						playerEgo.shipGrandGroup_.setRotation(0, 0, 0);
						camera.moveTo(playerEgo.shipGrandGroup_.getPostition());
						camera.translate(1000, 200, 15000);
					}
				}

				if (rMessages[21].isOver()) {
					Status.nextCampaignMission();
					GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[2]);
					Level.initStreamOutPosition = false;
					return false;
				}
			}

			if (this.level.getPlayer() != null && Status.getCurrentCampaignMission() > 1 && this.passedTime > 7000) {
				this.passedTime = 0;
				this.active = false;
				this.startSequenceIsOver = true;
				this.lookAtCam.setLookAt(false);
				followCamera.setPostition(GlobalStatus.renderer.getCamera().getPostition());
				followCamera.setLookAtCam(true);
				resetCamera(followCamera, this.level);
				this.cinematicBreak_ = false;
				this.level.getPlayer().setCollide(true);
				if (Level.programmedStation != null && !Level.programmedStation.equals(Status.getStation())) {
					this.hud.hudEvent(Hud.EVENT_AUTO_ON, this.level.getPlayer());
				}

				Level.initStreamOutPosition = false;
				if (!Level.driveJumping) {
					setAutoPilotToProgrammedStation();
				}
			}
		}

		if (Status.getCurrentCampaignMission() == 5 && this.step == 0) {
			this.tempVec = playerEgo.getPosition();
			enemies[0].setPosition(this.tempVec.x + 5000, this.tempVec.y, this.tempVec.z + 30000);
			enemies[0].geometry.rotateEuler(0, AEMath.Q_PI_HALF, 0);
			enemies[0].awake();
			this.step = 1;
		}

		if (Status.getMission().isCampaignMission() && !Status.getMission().isEmpty()) {
			switch(Status.getCurrentCampaignMission()) {
			case 14:
				if (rMessages[1].isOver() && this.step == 0) {
					this.hud.drawUI = false;
					this.radar.draw = false;
					this.level.flashScreen(4);
					playerEgo.setComputerControlled(true);
					playerEgo.speed = 0;
					playerEgo.player.removeAllGuns();
					followCamera.setLookAtCam(false);
					this.lookAtCam.setLookAt(true);
					this.lookAtCam.setTarget(playerEgo.shipGrandGroup_);
					camera.moveTo(playerEgo.getPosition());
					camera.translate(1000, 700, 1000);

					for(i = 0; i < this.level.getEnemies().length; ++i) {
						if (this.level.getEnemies()[i].race == 8) {
							this.level.getEnemies()[i].player.setHitPoints(0);
						}
					}

					this.step = 1;
				} else if (this.step == 1) {
					playerEgo.shipGrandGroup_.rotateEuler(0, dt >> 1, 0);
					camera.translate(0, 0, dt);
				}
				break;
			case 16:
			case 21:
				if (rMessages[0].isTriggered() && this.step == 0) {
					this.hud.drawUI = false;
					this.radar.draw = false;
					playerEgo.setComputerControlled(true);
					followCamera.setTarget(this.level.getEnemies()[0].geometry);
					this.tempVec.set(300, 400, 3500);
					followCamera.setCamOffset(this.tempVec);
					this.tempVec.set(0, 0, 0);
					followCamera.setTargetOffset(this.tempVec);
					this.step = 1;
				} else if (rMessages[Status.getCurrentCampaignMission() == 16 ? 1 : 0].isOver() && this.step == 1) {
					playerEgo.setComputerControlled(false);
					this.hud.drawUI = true;
					this.radar.draw = true;
					resetCamera(followCamera, this.level);
					this.step = 2;
				}
				break;
			case 24:
				if (rMessages[0].isTriggered() && this.step == 0) {
					this.hud.drawUI = false;
					this.radar.draw = false;
					this.lookAtCam.setLookAt(true);
					followCamera.setLookAtCam(false);
					playerEgo.player.setHitPoints(9999999);
					playerEgo.shipGrandGroup_.setRotation(0, 1024, 0);
					playerEgo.setComputerControlled(true);
					playerEgo.player.removeAllGuns();
					this.lookAtCam.setTarget(playerEgo.shipGrandGroup_);
					camera.moveTo(playerEgo.shipGrandGroup_.getPosition(this.tempVec));
					camera.translate(30500, 700, 1000);
					this.tempVec = playerEgo.shipGrandGroup_.getPosition(this.tempVec);
					this.tempVec2 = playerEgo.shipGrandGroup_.getDirection(this.tempVec2);
					this.tempVec2.scale(10 * AEMath.Q_1);
					this.tempVec.add(this.tempVec2);
					this.level.getLandmarks()[3].setPosition(this.tempVec.x, this.tempVec.y, this.tempVec.z);

					for(i = 0; i < this.level.getEnemies().length; ++i) {
						this.level.getEnemies()[i].player.setEnemies((Player[])null);
					}

					this.step = 1;
				} else if (rMessages[0].isOver() && this.step == 1) {
					((PlayerWormHole)this.level.getLandmarks()[3]).reset(false);
					this.level.getLandmarks()[3].setVisible(true);
					this.step = 2;
				}
				break;
			case 29:
				if (rMessages[0].isTriggered() && this.step == 0) {
					this.step = 1;
					playerEgo.player.setVulnerable_(false);
					this.hud.drawUI = false;
					this.radar.draw = false;
					this.lookAtCam.setLookAt(true);
					followCamera.setLookAtCam(false);
					playerEgo.setComputerControlled(true);
					this.lookAtCam.setTarget(playerEgo.shipGrandGroup_);
					camera.moveTo(playerEgo.shipGrandGroup_.getPosition(this.tempVec));
					this.tempVec = playerEgo.shipGrandGroup_.getDirection();
					this.tempVec.scale(AEMath.Q_4);
					//new AEVector3D();
					AEVector3D var9 = playerEgo.shipGrandGroup_.getRight();
					var9.scale(AEMath.Q_HALF);
					this.tempVec.add(var9);
					camera.translate(this.tempVec);
					this.probe = AEResourceManager.getGeometryResource(18);
					this.probe.setTransform(playerEgo.shipGrandGroup_.getToParentTransform());
					this.probe.setScale(AEMath.Q_THREE_SIXTEENTHS, AEMath.Q_THREE_SIXTEENTHS, AEMath.Q_THREE_SIXTEENTHS);
				} else if (rMessages[2].isOver() && this.step == 1) {
					this.step = 2;
					this.hud.drawUI = true;
					this.radar.draw = true;
					this.lookAtCam.setLookAt(false);
					followCamera.setLookAtCam(true);
					playerEgo.setComputerControlled(false);
					playerEgo.player.setVulnerable_(true);
					this.probe = null;
					this.timeLimit = 180000;
					this.timePassed = 0L;
					this.level.successObjective = new Objective(Objective.TYPE_3, this.timeLimit, this.level);
				}

				if (this.probe != null) {
					this.probe.moveForward(dt * 3);
				}
				break;
			case 40:
				if (this.step == 0) {
					enemies[0].race = 1;
					this.step = 1;
				} else if (this.step == 1 && enemies[0].getPosition(this.tempVec2).z >= ((PlayerWormHole)this.level.getLandmarks()[3]).getPosition(this.tempVec).z) {
					this.step = 2;
				} else if (this.step == 2) {
					((PlayerFixedObject)enemies[0]).moveForward(enemies[0].getPosition(this.tempVec2).z - 200000 - dt);
					if (enemies[0].getPosition(this.tempVec2).z > 500000) {
						enemies[0].setPosition(0, 0, -200000);
						enemies[0].setActive(false);
						this.step = 3;
					}
				}
				break;
			case 41:
				if (this.step == 0 && enemies[0].geometry.getPosZ() > -10000) {
					this.step = 1;
					enemies[0].setSpeed(0);
					enemies[0].player.setMaxHP(9999999);
					this.level.getLandmarks()[3].setPosition(5000, -40000, 10000);
				}
			/*
			case 42:
				Stop the freighter.
				Disable it's engine.
			 */
			}
			
		}

		return this.cinematicBreak_;
	}

	public final void renderProbe__() {
		if (this.probe != null) {
			GlobalStatus.renderer.drawNodeInVF(this.probe);
		}

	}

	public final void skipSequence() {
		if (Status.getCurrentCampaignMission() > 0) {
			this.passedTime = 7001;
		}

	}

	public final void startSequence() {
		this.startSequenceIsOver = false;
		this.passedTime = 0;
	}

	public final boolean startSequenceOver() {
		return this.startSequenceIsOver;
	}

	public final boolean isActive() {
		return this.active;
	}

	public static void resetCamera(final TargetFollowCamera followCamera, final Level level) {
		if (level.getPlayer() != null) {
			followCamera.setTarget(level.getPlayer().shipGrandGroup_);
			followCamera.setTargetOffset(new AEVector3D(0, 850, 0));
			followCamera.setCamOffset(new AEVector3D(0, 700, -2000));
			followCamera.followTargetPosition();
		}

	}

	public static void lookBehind(final TargetFollowCamera followCamera) {
		followCamera.setTargetOffset(new AEVector3D(0, 300, 0));
		followCamera.setCamOffset(new AEVector3D(0, 600, 3100));
	}

	public final void setAutoPilotToProgrammedStation() {
		if (Level.programmedStation != null) {
			if (Status.getStation().equals(Level.programmedStation)) {
				Level.programmedStation = null;
				return;
			}

			final SolarSystem currentSystem = Status.getSystem();
			final Station autoPilotStation = Level.programmedStation;
			if (currentSystem.stationIsInSystem(autoPilotStation.getIndex())) {
				this.level.getPlayer().setAutoPilot(this.level.getStarSystem().getPlanetTargets()[Status.getSystem().getStationEnumIndex(Level.programmedStation.getIndex())]);
				return;
			}

			if (Status.getSystem().currentOrbitHasJumpgate()) {
				this.level.getPlayer().setAutoPilot(this.level.getLandmarks()[1]);
				return;
			}

			int jumpGatePlanet = Status.getSystem().getJumpGateEnumIndex();
			if (jumpGatePlanet >= 0) {
				this.level.getPlayer().setAutoPilot(this.level.getStarSystem().getPlanetTargets()[jumpGatePlanet]);
			}
		}

	}
}
