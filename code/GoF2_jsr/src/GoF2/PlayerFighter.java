package GoF2;

import AE.AbstractMesh;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class PlayerFighter extends KIPlayer {
	private final int deathTransitionTime = 5000;
	private int sightRange = 50000;
	private final int soundRange = 500;
	private float defaultSpeed = 2.0F;
	private float defaultHandling = 2.0F;
	private int boostChance = 20;
	private AEVector3D pos = new AEVector3D();
	private final AEVector3D initPos = new AEVector3D();
	private final AEVector3D targetPos_ = new AEVector3D();
	private AEVector3D distanceToTarget = new AEVector3D();
	private static Matrix tempTransform = new Matrix();
	private int targedId_;
	private boolean canBoost;
	private int behavioutChangeTick;
	private int boostTick;
	private int boostTimeLimit;
	private int trailTick;
	private Player target__;
	private boolean folowingRoute;
	private long frameTime;
	private int hitpoints;
	private int damageAccumulator;
	private boolean damageEscape;
	private static Route defaultRoute_ = new Route(new int[] {
	      20000, 0, 20000,
	      20000, 0, -20000,
	      -20000, 0, -20000,
	      -20000, 0, 20000
	      });
	private static Route stationRouteAliens = new Route(new int[] {
	      40000, 0, 40000,
	      40000, 0, -40000,
	      -40000, 0, -40000,
	      -40000, 0, 40000
	      });
	private Route activeRoute;
	private int initialWaypoint;
	private int weaponActive;
	private Trail trail;
	private boolean strayFromTarget_;
	private float speed;
	private float handling;
	private BoundingVolume[] boundings;
	private boolean targetIsActive;
	private boolean collisionEnabled;
	private boolean unused821_;
	private int crateTime_;

	public PlayerFighter(final int var1, final int race, final Player var3, final AbstractMesh var4, final int var5, final int var6, final int var7) {
		super(var1, race, var3, (AbstractMesh)null, var5, var6, var7);
		this.race = race;
		this.initPos.set(var5, var6, var7);
		this.hasCargo = true;
		this.trail = new Trail(race != 9 && race != 8 ? 0 : 1);
		this.trail.setWidth(50);
		this.trail.reset(this.initPos);
		this.canBoost = true;
		this.speed = this.defaultSpeed;
		this.handling = this.defaultHandling;
		defaultRoute_.setLoop(true);
		stationRouteAliens.setLoop(true);
		this.activeRoute_ = race == 9 ? stationRouteAliens.clone() : defaultRoute_.clone();
		this.strayFromTarget_ = false;
		if (race == 9) {
			this.cargo = null;
		} else {
			final Generator var8 = new Generator();
			this.cargo = var8.getLootList();
		}

		this.sightRange = Status.inAlienOrbit() ? 100000 : 50000;
		this.collisionEnabled = true;
		this.hitpoints = this.player.getHitpoints();
		this.damageAccumulator = 0;
		this.damageEscape = false;
		this.wingmanCommand = 0;
		this.armed = true;
	}

	public final void OnRelease() {
		super.OnRelease();
		if (this.trail != null) {
			this.trail.OnRelease();
		}

		this.trail = null;
		this.target__ = null;
		this.boundings = null;
	}

	public final void setMissionCrate(final boolean var1) {
		this.carriesMissionCrate = var1;
		if (var1) {
			this.cargo = null;
			this.cargo = new int[2];
			this.cargo[0] = Status.getMission().getType() == 5 ? 116 : 117;
			this.cargo[1] = 1;
		}

	}

	public final boolean lostMissionCrateToEgo() {
		return this.lostMissionCrateToEgo;
	}

	public final boolean diedWithMissionCrate() {
		return this.diedWithMissionCrate;
	}

	public final boolean lostCargo() {
		return !this.hasCargo;
	}

	public final boolean unk151_() {
		return false;
	}

	public final void setPosition(final int var1, final int var2, final int var3) {
		this.targetX = var1;
		this.targetY = var2;
		this.targetZ = var3;
		this.geometry.moveTo(var1, var2, var3);
		this.initPos.set(var1, var2, var3);
		if (this.trail != null) {
			this.trail.reset(this.initPos);
		}

	}

	public final void setWingmanCommand(final int var1, final KIPlayer var2) {
		super.setWingmanCommand(var1, var2);
		switch(var1) {
		case 0:
			this.damageEscape = false;
			this.canBoost = false;
			break;
		case 1:
			this.weaponActive = this.weaponActive == 0 ? 1 : 0;
			break;
		case 2:
			this.damageEscape = false;
			this.canBoost = false;
			break;
		case 3:
		case 4:
			this.behavioutChangeTick = 5001;
			if (this.speed != 3.0F) {
				this.boostTick = 5001;
			}

			if (var1 == 3 && this.level.getPlayerRoute() != null) {
				this.activeRoute = this.level.getPlayerRoute().getExactClone();
				this.initialWaypoint = this.activeRoute.getCurrent();
			}
		}

		this.speed = this.defaultSpeed;
		this.handling = this.defaultHandling;
	}

	public final void setSpeed(final int var1) {
		this.canBoost = false;
		this.defaultSpeed = var1;
		this.speed = this.defaultSpeed;
	}

	public final void setRotate(final int var1) {
		this.canBoost = false;
		this.defaultHandling = 3;
		this.handling = this.defaultHandling;
	}

	public final void setBoostProb(final int var1) {
		this.boostChance = 0;
	}

	public final void removeTrail() {
		this.trail = null;
	}

	public final void setExhaustVisible(final boolean var1) {
		for(GraphNode var2 = this.geometry.getEndNode(); var2 != null; var2 = var2.getParent()) {
			if (var2.getID() == 13067 || var2.getID() == 13068 || var2.getID() == 13070 || var2.getID() == 13064 || var2.getID() == 13065 || var2.getID() == 13071) {
				var2.setDraw(var1);
			}
		}

	}

	public final void update(final long var1) {
		if (this.state == 4 && !this.hasCargo) {
			setActive(false);
		} else {
			this.boostTick = (int)(this.boostTick + var1);
			this.behavioutChangeTick = (int)(this.behavioutChangeTick + var1);
			if (this.race == 1) {
				this.race = 1;
			}

			this.frameTime = var1;
			this.pos = this.geometry.getPosition(this.pos);
			this.player.enemy = this.race != 8 && this.race != 9 ? isWingman() ? false : Status.getStanding().isEnemy(this.race) : true;
			this.player.friend = this.race != 8 && this.race != 9 ? isWingman() ? true : Status.getStanding().isFriend(this.race) : false;
			if (this.player.isAlwaysEnemy()) {
				this.player.enemy = true;
				this.player.friend = false;
			}

			if (this.player.isAlwaysFriend()) {
				this.player.friend = true;
				this.player.enemy = false;
			}

			if (!this.wingman && this.activeRoute_ == null) {
				this.activeRoute_ = defaultRoute_.clone();
			}

			this.trailTick = (int)(this.trailTick + var1);
			if (this.trailTick > 200) {
				if (this.trail != null) {
					this.trail.update(this.pos);
				}

				this.trailTick = 0;
			}

			this.player.transform = this.geometry.getToParentTransform();
			if (this.wingman && this.state != 4 && this.activeRoute_ != null) {
				this.tempVector_ = this.level.getPlayer().shipGrandGroup_.getPosition(this.tempVector_);
				if (this.wingmanCommand == 2) {
					AEVector3D var10000;
					switch(this.wingmanId) {
					case 0:
						this.position = this.level.getPlayer().shipGrandGroup_.getRightVector(this.position);
						var10000 = this.position;
						var10000.x >>= 1;
						var10000 = this.position;
						var10000.y >>= 1;
						var10000 = this.position;
						var10000.z >>= 1;
						this.tempVector_.subtract(this.position);
						this.tempVector_.add(this.level.getPlayer().shipGrandGroup_.getDirection(this.position));
						break;
					case 1:
						this.position = this.level.getPlayer().shipGrandGroup_.getRightVector(this.position);
						var10000 = this.position;
						var10000.x >>= 1;
						var10000 = this.position;
						var10000.y >>= 1;
						var10000 = this.position;
						var10000.z >>= 1;
						this.tempVector_.add(this.position);
						this.tempVector_.add(this.level.getPlayer().shipGrandGroup_.getDirection(this.position));
						break;
					case 2:
						this.position = this.level.getPlayer().shipGrandGroup_.getDirection(this.position);
						this.tempVector_.subtract(this.position);
					}
				}

				if (this.activeRoute_.length() > 1) {
					this.activeRoute_ = new Route(new int[]{this.tempVector_.x, this.tempVector_.y, this.tempVector_.z});
				} else {
					this.activeRoute_.setNewCoords(this.tempVector_);
				}

				this.activeRoute_.setLoop(true);
			}

			int var4;
			int var5;
			if (this.state != 4 && this.state != 3) {
				this.player.update(var1);
				Player[] var3;
				if ((var3 = this.player.getEnemies()) != null) {
					if (!this.targetIsActive) {
						this.targedId_ = -1;
					} else if (this.targedId_ >= 0 && !var3[this.targedId_].isActive()) {
						this.targetIsActive = false;
					}

					this.target__ = null;
					if (this.behavioutChangeTick > 5000) {
						PlayerFighter var12;
						boolean var10001;
						label655: {
							if (this.strayFromTarget_) {
								var12 = this;
							} else {
								var12 = this;
								if (GlobalStatus.random.nextInt(100) < 20) {
									var10001 = true;
									break label655;
								}
							}

							var10001 = false;
						}

						label650: {
							var12.strayFromTarget_ = var10001;
							this.behavioutChangeTick = 0;
							if (GlobalStatus.random.nextInt(100) < 30 && var3.length > 1) {
								this.targetIsActive = false;

								for(var4 = 0; var4 < 5 && !this.targetIsActive; ++var4) {
									this.targedId_ = GlobalStatus.random.nextInt(var3.length);
									if (var3[this.targedId_].isActive()) {
										this.tempVector_ = var3[this.targedId_].getPosition(this.tempVector_);
										if (this.race != 8 && this.player.isAlwaysEnemy() || this.pos.x - this.tempVector_.x < this.sightRange && this.pos.x - this.tempVector_.x > -this.sightRange && this.pos.y - this.tempVector_.y < this.sightRange && this.pos.y - this.tempVector_.y > -this.sightRange && this.pos.z - this.tempVector_.z < this.sightRange && this.pos.z - this.tempVector_.z > -this.sightRange) {
											this.targetIsActive = true;
											break;
										}
									}
								}

								if (this.targetIsActive) {
									break label650;
								}
							}

							this.targedId_ = 0;
						}

						if (this.wingman && this.wingmanCommand == 4 && this.wingmanTarget != null) {
							for(var4 = 0; var4 < var3.length; ++var4) {
								if (var3[var4].equals(this.wingmanTarget.player) && var3[var4].isActive() && !var3[var4].isAlwaysFriend()) {
									this.targedId_ = var4;
								}
							}
						}

						if (var3[this.targedId_].isActive() && !var3[this.targedId_].isDead()) {
							this.tempVector_ = var3[this.targedId_].getPosition(this.tempVector_);
							if (this.pos.x - this.tempVector_.x >= this.sightRange || this.pos.x - this.tempVector_.x <= -this.sightRange || this.pos.y - this.tempVector_.y >= this.sightRange || this.pos.y - this.tempVector_.y <= -this.sightRange || this.pos.z - this.tempVector_.z >= this.sightRange || this.pos.z - this.tempVector_.z <= -this.sightRange) {
								this.targedId_ = -1;
							}
						} else {
							this.targedId_ = -1;
						}
					} else if (!this.targetIsActive) {
						for(var4 = 0; var4 < var3.length; ++var4) {
							if (var3[var4] != null && var3[var4].isActive() && !var3[var4].isDead()) {
								this.tempVector_ = var3[var4].getPosition(this.tempVector_);
								if (this.race != 8 && this.player.isAlwaysEnemy() || this.pos.x - this.tempVector_.x < this.sightRange && this.pos.x - this.tempVector_.x > -this.sightRange && this.pos.y - this.tempVector_.y < this.sightRange && this.pos.y - this.tempVector_.y > -this.sightRange && this.pos.z - this.tempVector_.z < this.sightRange && this.pos.z - this.tempVector_.z > -this.sightRange) {
									this.targedId_ = var4;
									this.targetIsActive = true;
									break;
								}
							}
						}
					}

					if (!this.player.enemy && this.targedId_ == 0) {
						this.targedId_ = 1;
						this.targetIsActive = false;
					}

					if (!this.wingman && this.targedId_ > 0) {
						this.targedId_ = -1;

						for(var4 = 1; var4 < var3.length; ++var4) {
							if (var3[var4].isActive() && !var3[var4].isDead() && var3[var4].getKIPlayer() != null) {
								var5 = var3[var4].getKIPlayer().race;
								if (isWingman() ? var3[var4].enemy : (this.race != 8) == (var5 == 8) || this.race != 9 && var5 == 9 || this.race == 9 && var5 != 9 || this.race == 1 && var5 == 0 || this.race == 0 && var5 == 1 || this.race == 2 && var5 == 3 || this.race == 3 && var5 == 2) {
									this.targedId_ = var4;
									this.targetIsActive = true;
									break;
								}
							}
						}
					}

					if (this.wingman && !this.targetIsActive && this.wingmanCommand == 2) {
						this.targedId_ = -1;
					}

					this.folowingRoute = false;
					if (this.targedId_ == -1 && this.activeRoute_ != null) {
						if (this.activeRoute_.getDockingTarget_() != null && this.player.isActive()) {
							this.activeRoute_.update(this.player.transform.getPositionX(), this.player.transform.getPositionY(), this.player.transform.getPositionZ());
							if (this.activeRoute_.getDockingTarget_() != null) {
								this.targetPos_.x = this.activeRoute_.getDockingTarget_().x;
								this.targetPos_.y = this.activeRoute_.getDockingTarget_().y;
								this.targetPos_.z = this.activeRoute_.getDockingTarget_().z;
								this.folowingRoute = true;
							}
						} else {
							this.targedId_ = 0;
							this.target__ = this.player.getEnemy(this.targedId_);
							if (this.target__ != null) {
								this.tempVector_ = this.target__.getPosition(this.tempVector_);
								this.targetPos_.x = this.tempVector_.x;
								this.targetPos_.y = this.tempVector_.y;
								this.targetPos_.z = this.tempVector_.z;
							}
						}
					} else {
						if (this.targedId_ < 0) {
							this.targedId_ = 0;
						}

						this.target__ = this.player.getEnemy(this.targedId_);
						this.tempVector_ = this.target__.getPosition(this.tempVector_);
						this.targetPos_.x = this.tempVector_.x;
						this.targetPos_.y = this.tempVector_.y;
						this.targetPos_.z = this.tempVector_.z;
					}
				} else if (this.activeRoute_ != null) {
					this.activeRoute_.update(this.player.transform.getPositionX(), this.player.transform.getPositionY(), this.player.transform.getPositionZ());
					if (this.activeRoute_.getDockingTarget_() != null) {
						this.targetPos_.x = this.activeRoute_.getDockingTarget_().x;
						this.targetPos_.y = this.activeRoute_.getDockingTarget_().y;
						this.targetPos_.z = this.activeRoute_.getDockingTarget_().z;
						this.folowingRoute = true;
					}
				} else {
					this.state = 5;
				}

				if (this.jumper && this.folowingRoute) {
					this.jumpTick = (int)(this.jumpTick + var1);
					if (this.jumpTick >= 20000) {
						this.jumpTick = 0;
						this.state = 6;
						this.folowingRoute = false;
					}
				} else {
					this.jumpTick = 0;
				}

				if (this.wingman && this.wingmanCommand == 3 && this.activeRoute != null) {
					this.activeRoute.update(this.player.transform.getPositionX(), this.player.transform.getPositionY(), this.player.transform.getPositionZ());
					if (this.activeRoute.getCurrent() > this.initialWaypoint) {
						this.activeRoute = null;
						setWingmanCommand(0, (KIPlayer)null);
					} else if (this.activeRoute.getDockingTarget_() != null) {
						this.targetPos_.x = this.activeRoute.getDockingTarget_().x;
						this.targetPos_.y = this.activeRoute.getDockingTarget_().y;
						this.targetPos_.z = this.activeRoute.getDockingTarget_().z;
						this.folowingRoute = true;
					}
				}
			}

			this.distanceToTarget.x = this.targetPos_.x - this.pos.x;
			this.distanceToTarget.y = this.targetPos_.y - this.pos.y;
			this.distanceToTarget.z = this.targetPos_.z - this.pos.z;
			if (this.player.enemy && (this.state == 5 || this.wingman && this.wingmanCommand == 0)) {
				this.tempVector_ = this.level.getPlayer().player.getPosition(this.tempVector_);
				this.distanceToTarget.x = this.tempVector_.x - this.pos.x;
				this.distanceToTarget.y = this.tempVector_.y - this.pos.y;
				this.distanceToTarget.z = this.tempVector_.z - this.pos.z;
				if (this.distanceToTarget.x < 25000 && this.distanceToTarget.x > -25000 && this.distanceToTarget.y < 25000 && this.distanceToTarget.y > -25000 && this.distanceToTarget.z < 25000 && this.distanceToTarget.z > -25000) {
					this.state = 1;
					this.player.setActive(true);
					if (this.wingman && this.wingmanCommand == 0) {
						this.wingmanCommand = 2;
					}
				}
			}

			int var6;
			int var7;
			float var9;
			var7 = this.player.getHitpoints();
			if (var7 <= 0 && this.state != 3 && this.state != 4) {
				if (this.player.enemy) {
					if (this.race == 8) {
						if (!this.player.killedByKI) {
							Status.incPirateKills();
						}
					} else if (this.race == 9) {
						this.cargo = new int[2];
						this.cargo[0] = 131;
						this.cargo[1] = 1 + GlobalStatus.random.nextInt(3);
					}

					this.level.enemyDied(this.player.killedByKI);
				} else {
					this.level.friendDied();
					if (this.wingman) {
						Level.wingmanDied(this.wingmanId);
					}
				}

				this.state = 3;
				this.crateTime_ = 0;
				if (this.explosion != null) {
					this.explosion.start(this.pos.x, this.pos.y, this.pos.z);
				}

				var4 = AEMath.min(40000, this.distanceToTarget.getLength());
				var9 = 1.0F - var4 / 40000.0F;
				var6 = GlobalStatus.soundManager.getMusicVolume();
				GlobalStatus.soundManager.playSfx(1, (int)(var6 * var9));
				this.hasCargo = cargoAvailable_();
				if (this.hasCargo) {
					createCrate(this.race == 9 ? 3 : 0);
				}

				this.unused821_ = false;
			}

			switch(this.state) {
			case 0:
				this.state = 1;
				break;
			case 1:
				if (this.canBoost) {
					if (this.hitpoints > var7) {
						this.damageAccumulator += this.hitpoints - var7;
						this.hitpoints = var7;
						if ((float)this.damageAccumulator / (float)this.player.getMaxHitpoints() * 100.0F > 40.0F) {
							this.damageAccumulator = 0;
							this.boostTick = 10000;
							this.damageEscape = true;
						}
					}

					if (this.boostTick > 5000 && this.speed != 3.0F) {
						if (this.damageEscape || GlobalStatus.random.nextInt(100) < this.boostChance) {
							this.boostTimeLimit = GlobalStatus.random.nextInt(3000) + 5000;
						}

						this.boostTick = 0;
						this.speed = 3.0F;
						this.handling = 1.3F;
					}

					if (this.speed > this.defaultSpeed && this.boostTick > this.boostTimeLimit) {
						this.boostTick = 0;
						this.damageEscape = false;
						this.speed = this.defaultSpeed;
						this.handling = this.defaultHandling;
					}
				}

				if (this.target__ != null && !this.folowingRoute && this.distanceToTarget.x < 8000 && this.distanceToTarget.x > -8000 && this.distanceToTarget.y < 8000 && this.distanceToTarget.y > -8000 && this.distanceToTarget.z < 8000 && this.distanceToTarget.z > -8000) {
					this.distanceToTarget = this.geometry.getRightVector(this.distanceToTarget);
				}

				this.distanceToTarget.normalize();
				this.position.set(this.distanceToTarget);
				this.distanceToTarget = this.geometry.getInverseTransform(tempTransform).transformVectorNoScale2(this.position, this.distanceToTarget);
				this.distanceToTarget.y = -this.distanceToTarget.y;
				var9 = this.player.getBombForce();
				float var10 = this.player.setEmpForce_();
				if (this.targetIsActive && !this.folowingRoute && this.target__ != null) {
					if (!this.target__.hidden) {
						if (this.distanceToTarget.x < this.soundRange && this.distanceToTarget.x > -this.soundRange && this.distanceToTarget.y < this.soundRange && this.distanceToTarget.y > -this.soundRange && this.targetPos_.x - this.pos.x < 35000 && this.targetPos_.x - this.pos.x > -35000 && this.targetPos_.y - this.pos.y < 35000 && this.targetPos_.y - this.pos.y > -35000 && this.targetPos_.z - this.pos.z < 35000 && this.targetPos_.z - this.pos.z > -35000) {
							if (this.target__.isActive() && !this.target__.isDead() && var9 < 0.05F && var10 < 0.05F) {
								this.player.shoot_(this.weaponActive, var1, false);
							} else {
								this.targetIsActive = false;
							}
						}
					} else {
						this.targetIsActive = false;
					}
				}

				if (!this.strayFromTarget_) {
					this.tempVector_.set(this.geometry.getDirection(this.tempVector_));
					this.position.subtract(this.tempVector_);
					this.position.normalize();
					this.position.scale((int)(var1 * this.handling));
					this.distanceToTarget = this.tempVector_.add(this.position, this.distanceToTarget);
					this.distanceToTarget.normalize();
					this.geometry.getToParentTransform().setOrientation(this.distanceToTarget);
				}

				if (!this.wingman || this.wingmanCommand != 0) {
					if (var10 < 0.05F && var9 < 0.05F) {
						this.geometry.moveForward((int)(var1 * this.speed));
					} else {
						if (var9 > 0.0F) {
							this.tempVector_.set(this.player.getHitVector());
							this.tempVector_.scale((int)(512.0F * var9));
							this.geometry.translate(this.tempVector_);
							var7 = (int)var1;
							this.geometry.rotateEuler(var7, var7, var7);
							if ((var9 *= 0.98F) < 0.05F) {
								var9 = 0.0F;
							}

							this.player.setBombForce(var9);
						}

						if (var10 > 0.0F) {
							this.stunned = true;
							var7 = (int)var1 >> 1;
							this.geometry.rotateEuler(var7, var7, var7);
							if ((var10 -= var1) < 0.05F) {
								var10 = 0.0F;
								this.stunned = false;
							}

							this.player.setEmpForce_(var10);
						}
					}
				}
			case 2:
			default:
				break;
			case 3:
				this.collisionEnabled = false;
				var5 = (int)var1 >> 1;
				if (this.waste != null) {
					this.waste.rotateEuler(var5, var5, var5);
				}

				if (this.explosion != null) {
					if (this.explosion.isOver()) {
						this.state = 4;
						this.explosion.reset();
					} else {
						this.crateTime_ = (int)(this.crateTime_ + var1);
						if (this.crateTime_ > this.deathTransitionTime) {
							this.crateTime_ = 0;
							this.state = 4;
						}
					}
				} else {
					this.state = 4;
				}
				break;
			case 4:
				this.crateLifeTime = (int)(this.crateLifeTime + var1);
				if (this.hasCargo && this.player.isActive() && this.waste != null) {
					var5 = (int)var1 >> 1;
					this.waste.rotateEuler(var5, var5, var5);
					if (this.crateLifeTime > 45000 && this.explosion != null) {
						if (this.crateLifeTime < 90000) {
							this.explosion.start(this.pos.x, this.pos.y, this.pos.z);
							if (this.level.getPlayer().radar.tractorBeamTarget == this) {
								this.level.getPlayer().radar.tractorBeamTarget = null;
							}

							this.crateLifeTime = 90000;
						} else if (this.explosion.isOver()) {
							if (this.carriesMissionCrate && !this.lostMissionCrateToEgo) {
								this.diedWithMissionCrate = true;
							}

							this.waste = null;
							this.crateLifeTime = 0;
							setActive(false);
						}
					}
				} else {
					if (this.carriesMissionCrate && !this.lostMissionCrateToEgo) {
						this.diedWithMissionCrate = true;
					}

					if (this.crateLifeTime > 45000) {
						setActive(false);
					}
				}
				break;
			case 5:
				if (this.target__ != null && !this.target__.hidden && this.distanceToTarget.x < this.sightRange && this.distanceToTarget.x > -this.sightRange && this.distanceToTarget.y < this.sightRange && this.distanceToTarget.y > -this.sightRange && this.distanceToTarget.z < this.sightRange && this.distanceToTarget.z > -this.sightRange) {
					this.state = 1;
					this.player.setActive(true);
				}
				break;
			case 6:
				this.speed *= 1.1F;
				this.geometry.moveForward((int)(var1 * this.speed));
				if (this.speed > 100.0F) {
					setDead();
				}
			}

			KIPlayer[] var11;
			if (this.collisionEnabled && (var11 = this.level.getLandmarks()) != null) {
				for(var6 = 0; var6 < var11.length; ++var6) {
					AEVector3D var8;
					if (var11[var6] != null && var11[var6].outerCollide(this.geometry.getPosition(this.position)) && (var8 = var11[var6].getProjectionVector_(this.geometry.getPosition(this.position))) != null) {
						this.tempVector_.set(this.geometry.getDirection(this.tempVector_));
						var8.subtract(this.tempVector_);
						var8.scale((int)(var1 * 5.0F));
						this.pos = this.tempVector_.add(var8, this.pos);
						this.pos.normalize();
						this.geometry.getToParentTransform().setOrientation(this.pos);
						this.geometry.moveForward((int)(var1 * this.speed));
					}
				}
			}

		}
	}

	public final void appendToRender() {
		if (this.waste != null) {
			GlobalStatus.renderer.drawNodeInVF(this.waste);
		}

		if (this.player.isActive() || this.state == 5) {
			if (this.state != 4 && this.state != 3) {
				if (this.withinRenderDistance) {
					GlobalStatus.renderer.drawNodeInVF(this.geometry);
					if (this.trail != null) {
						this.trail.render();
					}
				} else {
					this.geometry.updateTransform(true);
				}
			} else if ((this.state == 3 || this.state == 4) && this.explosion != null && !this.explosion.isOver()) {
				this.explosion.update(this.frameTime);
			}

			if (this.state == 6) {
				GlobalStatus.renderer.drawNodeInVF(this.jumpMesh);
			}

		}
	}

	public final boolean outerCollide(final int var1, final int var2, final int var3) {
		return this.state != 4 && this.state != 3 ? false : false;
	}

	public final void revive() {
		Globals.buildShip(this.geometry, this.shipId_);
		final boolean var1 = this.player.isAlwaysEnemy();
		this.player.reset();
		if (var1) {
			this.player.setAlwaysEnemy();
		}

		this.state = 1;
		this.waste = null;
		this.targetIsActive = false;
		this.targedId_ = -1;
		this.activeRoute_.reset();
		this.hitpoints = this.player.getHitpoints();
		this.damageAccumulator = 0;
		this.damageEscape = false;
		this.crateLifeTime = 0;
		setActive(true);
		this.jumpTick = 0;
		this.speed = this.defaultSpeed;
		setExhaustVisible(true);
		if (this.race == 9) {
			this.cargo = null;
		} else {
			final Generator var2 = new Generator();
			this.cargo = var2.getLootList();
		}
	}
}
