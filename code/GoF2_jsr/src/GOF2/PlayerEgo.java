package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.AECamera;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.AEGroup;
import AE.LookAtCamera;
import AE.TargetFollowCamera;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.PaintCanvas.AEGraphics3D;
import AE.Math.AEMatrix;

/**
 * The player's ship. Represent the user
 * pilotable ship and it's parameters.
 * Not inheriting from KIPlayer.
 * 
 * @author Fishlabs 2009
 */
public final class PlayerEgo {
	private static final int CLOAK_READY = -1;
	
	private static int boostSpeed = 5;
	private static int boostDuration = 5000;
	private static int boostDelay = 20000;
	private static int[][] turretPartsPositions = {
	      {1, 0, 228, -72},
	      {7, 0, 215, 46},
	      {11, 0, 165, 147},
	      {17, 0, 224, 404},
	      {23, 0, 140, -21},
	      {24, 0, 195, 88},
	      {28, 0, 269, -69},
	      {33, 0, 255, -176},
	      {36, 0, 255, 1015}
	      };
	public Player player;
	public AEGroup shipGrandGroup_;
	private AEGroup shipGroup;
	private AEGeometry turretStand;
	private AEGeometry turretGun;
	private AEVector3D vecUp;
	private AEVector3D vecRight;
	private DummyClass_ unused_7a2;
	private Crosshair crosshair;
	private Explosion explosion;
	public Level level;
	private Route route;
	public int speed;
	private int relPitchSpeed;
	private int relYawSpeed;
	private int roll;
	private int currentSecondaryId = -1;
	private long shieldRegenTick;
	private long repairHPTick;
	private long repairArmorTick;
	private int totalHP;
	private boolean freeze;
	private long frameTime;
	private int boostTime;
	private boolean boostActive;
	private int shipYaw;
	private boolean collisionsOn;
	private boolean beingPushedAway;
	private final boolean boostingEnabled;
	private AEVector3D laggingPos;
	private final AEMatrix calcMatrix_ = new AEMatrix();
	private float handling;
	private final int addHandling;
	private boolean autopilotActive;
	private KIPlayer autopilotTarget;
	private boolean asteroidFieldTarget_;
	private AEVector3D autoPilotDir;
	private boolean hasTurret;
	private TargetFollowCamera followingCamera_;
	private AECamera camera;
	private AEGroup turretViewCamera__;
	private AEGroup turretGrandGroup_;
	private AEGroup turretGroup;
	private boolean turretMode = false;
	private float turretHalfLift_;
	private float turretLift_;
	private boolean cloakOn;
	private final Item cloak;
	private TractorBeam tractorBeam;
	private KIPlayer lockedAsteroid;
	private boolean lockedOnAsteroid;
	private int miningState;
	private AEVector3D minigUpVec;
	private boolean isMinig;
	private int astroidDockDistance;
	private int dockMiningPitch;
	private int cloakingTimeCoef;
	private int cloakingScaleX;
	private MiningGame miningGame;
	private boolean unused_1399;
	private boolean jumpGating;
	private boolean jumpingToNeighbourPlanet;
	private int disanceFromCamera_;
	private boolean autoDriven;
	private int turretRotationSpeed;
	private int cloakTime;
	private final int cloakDuration;
	private final int cloakLoadingTime;
	private float shieldRegenRate;
	private Hud hud;
	public Radar radar;
	public Radio radio;
	private boolean inWormhole;
	private int[][] enginesBoostScale_;
	private int turretShipId_;
	private boolean touchesStation;

	public PlayerEgo(final Player var1) {
		this.player = var1;
		this.player.setPlayShootSound(true);
		this.vecUp = new AEVector3D();
		this.vecRight = new AEVector3D();
		this.autoPilotDir = new AEVector3D();
		this.laggingPos = new AEVector3D();
		this.roll = 1;
		this.unused_7a2 = new DummyClass_();
		this.crosshair = new Crosshair();
		this.totalHP = this.player.getCombinedHP();
		this.speed = 2;
		this.boostActive = false;
		this.collisionsOn = true;
		this.autopilotActive = false;
		this.autopilotTarget = null;
		boostDelay = Status.getShip().getBoostDelay();
		boostSpeed = 2 + (int)(Status.getShip().getBoostPercent() / 100.0F * 2.0F);
		boostDuration = Status.getShip().getBoostDuration();
		this.boostingEnabled = boostDelay > 0;
		this.handling = Status.getShip().getHandling();
		this.addHandling = Status.getShip().getAddHandlingPercent();
		this.handling += this.addHandling / 100.0F;

		try {
			this.shieldRegenRate = Status.getShip().getShield() / (Status.getShip().getShieldRegenTime() / 100.0F);
		} catch (final Exception var2) {
			this.shieldRegenRate = 0.0F;
		}

		this.lockedOnAsteroid = false;
		this.isMinig = false;
		this.inWormhole = false;
		this.cloak = Status.getShip().getFirstEquipmentOfSort(Item.CLOAK);
		this.cloakTime = CLOAK_READY;
		this.cloakDuration = this.cloak == null ? 0 : this.cloak.getAttribute(Item.CLOAK_DURATION);
		this.cloakLoadingTime = this.cloak == null ? 0 : this.cloak.getAttribute(Item.CLOAK_RELOAD);
		this.cloakOn = false;
		this.autoDriven = false;
	}

	public final void setShip(int var1, int var2) {
		this.shipGrandGroup_ = new AEGroup();
		this.shipGroup = Globals.getShipGroup(var1, var2);
		var1 = 0;

		for(GraphNode var4 = this.shipGroup.getEndNode(); var4 != null; var4 = var4.getParent()) {
			if (var4.getID() == 13067 ||
					var4.getID() == 13068 ||
					var4.getID() == 13070 ||
					var4.getID() == 13064 ||
					var4.getID() == 13065 ||
					var4.getID() == 13071) {
				++var1;
			}
		}

		if (var1 > 0) {
			this.enginesBoostScale_ = new int[var1][3];
		}

		var2 = 0;

		for(GraphNode var3 = this.shipGroup.getEndNode(); var3 != null; var3 = var3.getParent()) {
			if (var3.getID() == 13067 ||
					var3.getID() == 13068 ||
					var3.getID() == 13070 ||
					var3.getID() == 13064 ||
					var3.getID() == 13065 ||
					var3.getID() == 13071) {
				this.vecUp = var3.copyScaleTo(this.vecUp);
				this.enginesBoostScale_[var2][0] = this.vecUp.x;
				this.enginesBoostScale_[var2][1] = this.vecUp.y;
				this.enginesBoostScale_[var2][2] = this.vecUp.z;
				++var2;
			}
		}

		this.shipGrandGroup_.uniqueAppend_(this.shipGroup);
		if (Status.getShip().getFirstEquipmentOfSort(Item.TRACTOR_BEAM) != null) {
			this.tractorBeam = new TractorBeam(this.shipGrandGroup_, Status.getShip().getFirstEquipmentOfSort(Item.TRACTOR_BEAM).getIndex() - 68); // todo replace constant
		}

	}

	public final void setExhaustVisible(final boolean var1) {
		for(GraphNode var2 = this.shipGroup.getEndNode(); var2 != null; var2 = var2.getParent()) {
			if (var2.getID() == 13067 ||
					var2.getID() == 13068 ||
					var2.getID() == 13070 ||
					var2.getID() == 13064 ||
					var2.getID() == 13065 ||
					var2.getID() == 13071) {
				var2.setDraw(var1);
			}
		}

	}

	public final void resetLastHP() {
		this.totalHP = this.player.getCombinedHP();
	}

	public final boolean toggleCloaking_() {
		if (this.cloakOn) {
			this.player.hidden = false;
			this.cloakOn = false;
		} else if (this.cloakTime < 0) {
			this.player.hidden = true;
			this.cloakOn = true;
			this.cloakTime = 0;
		}

		return this.cloakOn;
	}

	public final boolean isCloakPresent() {
		return this.cloak != null;
	}

	public final boolean isLookingBack() {
		return this.turretMode;
	}

	public final void setCamControllers(final TargetFollowCamera var1, final LookAtCamera var2) {
		this.followingCamera_ = var1;
	}

	public final boolean setTurretMode(final boolean var1) {
		if (!this.hasTurret || isMining()) {
			return false;
		}
		this.turretMode = var1;
		if (var1) {
			if (this.camera == null) {
				this.camera = AECamera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 700, 100, AEGraphics3D.CAMERA_FAR);
				this.camera.translate(0, 500, -1400);
				this.camera.rotateEuler(0, AEMath.Q_PI_HALF, 0);
				this.turretViewCamera__ = new AEGroup();
				this.turretViewCamera__.uniqueAppend_(this.camera);
				this.turretViewCamera__.setRotationOrder((short)2);
				this.turretViewCamera__.translate(turretPartsPositions[this.turretShipId_][1], turretPartsPositions[this.turretShipId_][2], turretPartsPositions[this.turretShipId_][3]);
				this.turretViewCamera__.rotateEuler(0, AEMath.Q_PI_HALF, 0);
				this.turretGrandGroup_ = new AEGroup();
				this.turretGrandGroup_.uniqueAppend_(this.turretViewCamera__);
			}

			this.turretGrandGroup_.moveTo(this.laggingPos);
			this.turretGrandGroup_.setTransform(this.shipGrandGroup_.getToParentTransform());
			GlobalStatus.renderer.setActiveCamera(this.camera);
		} else {
			GlobalStatus.renderer.setActiveCamera((AECamera)this.followingCamera_.getCamera());
			LevelScript.resetCamera(this.followingCamera_, this.level);
		}

		return true;
	}

	public final void boost() {
		if (!this.boostActive && this.boostingEnabled && this.boostTime >= 0) {
			GlobalStatus.soundManager.playSfx(0);
			this.boostTime = 0;
			this.speed = boostSpeed;
			this.boostActive = true;
		}

	}

	public final void silentBoost_() {
		this.boostTime = 0;
		this.boostActive = true;
		this.speed = 8;
		boostDuration = 10000;
		boostDelay = 0;
	}

	public final int getMiningProgress() {
		return this.miningGame != null ? this.miningGame.getMiningProgressRounded() : 0;
	}

	public final boolean boosting() {
		return this.boostActive;
	}

	public final boolean readyToBoost() {
		return this.boostTime >= 0;
	}

	public final float getBoostPercentage() { 
		float var1 = (float)this.boostTime / (float)(boostDuration / 6);
		if (var1 < 1.0F) {
			return var1;
		}
		return var1 > 5.0F ? 6.0F - var1 : 1.0F;
	}

	public final float getBoostRate() {
		float var1 = (float)this.boostTime / (float)boostDelay;
		return (var1) > 0.0F ? 1.0F : var1 + 1.0F;
	}

	public final float getCloakRate() {
		final float var1;
		if (this.cloakOn) {
			var1 = (float)this.cloakTime / (float)this.cloakDuration;
		} else
			var1 = (float)this.cloakTime / (float)this.cloakLoadingTime;
		//float var1 = this.cloakOn ? (float)this.cloakTime / (float)this.cloakDuration : (float)this.cloakTime / (float)this.cloakLoadingTime;
		return var1 > 0.0F ? var1 : 1.0F;
	}

	public final boolean isCloakReady() {
		return !this.cloakOn && this.cloakTime == CLOAK_READY;
	}

	public final boolean isCloaked() {
		return this.cloakOn;
	}

	public final void setCollide(final boolean var1) {
		this.collisionsOn = var1;
	}

	public final void setLevel(final Level var1) {
		this.level = var1;
	}

	public final void setRoute(final Route var1) {
		this.route = var1;
	}

	public final Route getRoute() {
		return this.route;
	}

	public final void removeRoute() {
		this.route = null;
	}

	private boolean isDead() {
		return this.player.getHitpoints() <= 0;
	}

	public final void shoot(final int dt, final int var2) {
		if (this.turretMode) {
			this.calcMatrix_.set(this.turretGun.getLocalTransform());
			this.player.playShootSound__(2, dt, false, this.calcMatrix_);
		} else if (var2 == 1) {
			if (!this.player.shoot(var2, this.currentSecondaryId, dt, false)) {
				this.currentSecondaryId = -1;
			}
		} else {
			this.player.shoot_(var2, dt, false);
		}
	}

	public final void setGuns(final Gun[] var1, int i) {
		Player var6;
		if ((var6 = this.player).guns != null && i <= 3 && i >= 0) {
			var6.guns[i] = new Gun[var1.length];
			var6.guns[i] = var1;
		}

		if (!this.hasTurret) {
			this.hasTurret = this.player.hasGunOfType(2);
			if (this.hasTurret) {
				final Item[] var8 = Status.getShip().getEquipment(2);
				short var9 = -1;
				short var4 = -1;
				byte var5 = -1;
				this.turretRotationSpeed = var8[0].getAttribute(Item.TURRET_HANDLING);
				switch(var8[0].getIndex()) {
				case 47:
					var9 = 6770;
					var4 = 6771;
					var5 = 91;
					break;
				case 48:
					var9 = 6772;
					var4 = 6773;
					var5 = 75;
					break;
				case 49:
					var9 = 6774;
					var4 = 6775;
					var5 = 121;
				}

				this.turretStand = AEResourceManager.getGeometryResource(var9);
				this.turretGun = AEResourceManager.getGeometryResource(var4);
				this.turretStand.setRenderLayer(2);
				this.turretGun.setRenderLayer(2);
				this.turretGun.setRotationOrder((short)2);
				this.turretGroup = new AEGroup();

				for(i = 0; i < turretPartsPositions.length; ++i) {
					if (turretPartsPositions[i][0] == Status.getShip().getIndex()) {
						this.turretStand.moveTo(turretPartsPositions[i][1], turretPartsPositions[i][2], turretPartsPositions[i][3]);
						this.turretGun.moveTo(turretPartsPositions[i][1], turretPartsPositions[i][2], turretPartsPositions[i][3]);
						this.turretShipId_ = i;
						break;
					}
				}

				this.turretGun.translate(0, var5, 0);
				this.turretStand.rotateEuler(0, AEMath.Q_PI_HALF, 0);
				this.turretGun.rotateEuler(0, AEMath.Q_PI_HALF, 0);
				this.turretGroup.uniqueAppend_(this.turretStand);
				this.turretGroup.uniqueAppend_(this.turretGun);
			}
		}

	}

	public final int getHitpoints() {
		return this.player.getHitpoints();
	}

	public final int getHullDamageRate() {
		return this.player.getDamageRate();
	}

	public final int getCurrentSecondaryWeaponIndex() {
		return this.currentSecondaryId;
	}

	public final void setCurrentSecondaryWeaponIndex(final int var1) {
		this.currentSecondaryId = var1;
	}

	public final AEVector3D getPosition() {
		return this.shipGrandGroup_.getLocalPos(this.vecUp);
	}

	public final void setActive(final boolean var1) {
		this.player.setActive(false);
	}

	public final void setComputerControlled(final boolean var1) {
		this.autoDriven = var1;
	}

	public final void setFreeze(final boolean var1) {
		this.freeze = var1;
	}

	public final void setAutoPilot(final KIPlayer var1) {
		this.autopilotTarget = var1;
		this.asteroidFieldTarget_ = false;
		this.autopilotActive = this.autopilotTarget != null;
		if (this.autopilotTarget != null && this.autopilotTarget instanceof Waypoint) {
			this.asteroidFieldTarget_ = true;
		}

	}

	public final KIPlayer getAutoPilotTarget() {
		return this.autopilotTarget;
	}

	public final boolean goingToPlanet() {
		return this.autopilotActive && !goingToStream() && !goingToStation() && !this.asteroidFieldTarget_;
	}

	public final boolean isAutoPilot() {
		return this.autopilotActive;
	}

	public final boolean goingToWormhole() {
		return this.autopilotTarget == this.level.getLandmarks()[3];
	}

	public final boolean goingToStream() {
		return this.autopilotTarget == this.level.getLandmarks()[1];
	}

	public final boolean goingToStation() {
		if (this.level.getLandmarks()[0] != null) {
			return this.autopilotTarget == this.level.getLandmarks()[0];
		}
		return false;
	}

	public final boolean gointToAsteroidField() {
		return this.asteroidFieldTarget_;
	}

	public final void setPosition_(final int var1, final int var2, final int var3) {
		this.shipGrandGroup_.moveTo(var1, var2, var3);
		this.player.transform.translateTo(var1, var2, var3);
		this.vecUp.set(var1, var2, var3);
	}

	public final void setPosition_(final AEVector3D var1) {
		this.shipGrandGroup_.moveTo(var1);
		this.player.transform.translateTo(var1);
	}

	public final boolean isInWormhole() {
		return this.inWormhole;
	}

	public final void update(final int dt, final Radar var2, final Hud var3, final Radio var4, final int var5) {
		if (this.hud == null) {
			this.hud = var3;
			this.radar = var2;
			this.radio = var4;
		}

		if (!this.freeze) {
			this.laggingPos = this.shipGrandGroup_.getLocalPos(this.laggingPos);
			this.frameTime = dt;
			if (this.boostTime < 0 && this.boostTime + dt * 3 > 0) {
				var3.hudEvent(4, this);
				this.boostTime = 0;
			}

			this.boostTime += dt;
			int var9;
			if (this.boostActive) {
				var9 = 0;
				final int var6 = (int)(getBoostPercentage() * AEMath.Q_HALF);

				GraphNode var7;
				int[] var8;
				for(var7 = this.shipGroup.getEndNode(); var7 != null; var7 = var7.getParent()) {
					if (var7.getID() == 13067
				      || var7.getID() == 13068 
				      || var7.getID() == 13070 
				      || var7.getID() == 13064 
				      || var7.getID() == 13065
				      || var7.getID() == 13071) {
						var8 = this.enginesBoostScale_[var9];
						var9++;
						var7.setScale(var8[0] + var6, var8[1] + var6, var8[2] + var6 * 2);
					}
				}

				if (this.boostTime > boostDuration) {
					var9 = 0;
					this.speed = 2;
					this.boostActive = false;
					this.boostTime = -boostDelay;
					if (!this.boostingEnabled) {
						boostDuration = 0;
						boostSpeed = 2;
						boostDelay = 0;
					}

					for(var7 = this.shipGroup.getEndNode(); var7 != null; var7 = var7.getParent()) {
						if (var7.getID() == 13067
					      || var7.getID() == 13068 
					      || var7.getID() == 13070 
					      || var7.getID() == 13064 
					      || var7.getID() == 13065
					      || var7.getID() == 13071) {
							var8 = this.enginesBoostScale_[var9];
							var9++;
							var7.setScale(var8[0], var8[1], var8[2]);
						}
					}
				}
			}

			if (this.cloakOn) {
				if (this.cloakingScaleX >= 0) {
					this.cloakingTimeCoef = (int)(this.cloakingTimeCoef + (this.frameTime << 3));
					this.cloakingScaleX = (int)(-2.0F * AEMath.TO_FLOAT * ((this.cloakingTimeCoef - AEMath.Q_1) * (this.cloakingTimeCoef - AEMath.Q_1)) + AEMath.Q_2 + AEMath.Q_1);
					this.shipGrandGroup_.setScale(this.cloakingScaleX, AEMath.Q_1, AEMath.Q_1);
				} else {
					this.shipGrandGroup_.setScale(AEMath.Q_1, AEMath.Q_1, AEMath.Q_1);
				}

				this.cloakTime = (int)(this.cloakTime + this.frameTime);
				Status.invisibleTime += this.frameTime;
				if (this.cloakTime > this.cloakDuration) {
					this.cloakTime = 0;
					this.cloakOn = false;
					this.player.hidden = false;
				}
			} else {
				if (this.cloakingTimeCoef > 0) {
					this.cloakingTimeCoef = (int)(this.cloakingTimeCoef - (this.frameTime << 3));
					this.cloakingScaleX = (int)(-2.0F * AEMath.TO_FLOAT * ((this.cloakingTimeCoef - AEMath.Q_1) * (this.cloakingTimeCoef - AEMath.Q_1)) + AEMath.Q_2 + AEMath.Q_1);
					this.shipGrandGroup_.setScale(this.cloakingScaleX, AEMath.Q_1, AEMath.Q_1);
				} else {
					this.cloakingTimeCoef = 0;
					this.shipGrandGroup_.setScale(AEMath.Q_1, AEMath.Q_1, AEMath.Q_1);
				}

				if (this.cloakTime >= 0) {
					this.cloakTime = (int)(this.cloakTime + this.frameTime);
					if (this.cloakTime > this.cloakLoadingTime) {
						this.cloakTime = CLOAK_READY;
					}
				}
			}

			if (this.tractorBeam != null) {
				this.tractorBeam.update(this.frameTime, var2, this.level, var3);
			}

			this.followingCamera_.tickUpdate((int)this.frameTime);
			this.beingPushedAway = false;
			if (this.autoDriven || this.jumpingToNeighbourPlanet || this.jumpGating) {
				this.beingPushedAway = true;
				if (this.jumpingToNeighbourPlanet) {
					this.disanceFromCamera_ = (int)(this.disanceFromCamera_ + this.frameTime);
				}

				this.shipGrandGroup_.moveForward(dt * this.speed);
			}

			if (this.collisionsOn) {
				this.touchesStation = false;
				calcCollision(this.level.getLandmarks());
				calcCollision(this.level.getEnemies());
				calcCollision(this.level.getAsteroids());
			}

			if (this.lockedOnAsteroid) {
				this.beingPushedAway = true;
				if (this.lockedAsteroid.isDead()) {
					dockToAsteroid((KIPlayer)null, this.followingCamera_, var2);
					return;
				}

				approachAsteroid(var3, var5);
			}

			if (this.autopilotActive && this.autopilotTarget != null && !this.beingPushedAway) {
				if (this.asteroidFieldTarget_ && ((Waypoint)this.autopilotTarget).activeRoute_ != null) {
					this.autopilotTarget = ((Waypoint)this.autopilotTarget).activeRoute_.getDockingTarget_();
				}

				if (this.autopilotTarget != null && !this.jumpGating) {
					this.vecRight = this.autopilotTarget.getPosition(this.vecRight);
					moveTowards_(this.vecRight);
				} else {
					setAutoPilot((KIPlayer)null);
				}
			} else if (!this.beingPushedAway) {
				if (this.turretMode) {
					((AECamera)this.followingCamera_.getCamera()).updateTransform(true);
					this.followingCamera_.update();
					this.turretGrandGroup_.moveTo(this.laggingPos);
					this.vecUp = this.shipGrandGroup_.getUp(this.vecUp);
					this.vecUp.scale(AEMath.Q_EIGHTH);
					this.turretGrandGroup_.translate(this.vecUp);
					this.relPitchSpeed = this.relYawSpeed = 0;
					alignToHorizon((int)this.frameTime);
				} else {
					this.shipGrandGroup_.pitch((int)(this.handling * (this.relPitchSpeed * dt / 3)));
					this.shipYaw = (int)(this.handling * (this.relYawSpeed * dt / 3));
					this.shipGrandGroup_.yaw(this.shipYaw);
					if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
						alignToHorizon((int)this.frameTime);
					}
				}

				this.shipGrandGroup_.moveForward(dt * this.speed);
			}

			if (this.lockedOnAsteroid || !this.beingPushedAway && !this.turretMode) {
				this.shipGroup.setRotation(0, 0, -this.roll);
				if (this.roll > 0 && this.relYawSpeed == 0) {
					this.roll -= dt / 2;
				} else if (this.roll < -dt / 2 && this.relYawSpeed == 0) {
					this.roll += dt / 2;
				}

				this.relPitchSpeed = this.relYawSpeed = 0;
			}

			if (this.hasTurret) {
				this.turretGroup.setTransform(this.shipGrandGroup_.getToParentTransform());
				this.turretGroup.roll(-this.roll);
				if (this.turretMode) {
					this.turretGrandGroup_.setTransform(this.shipGrandGroup_.getToParentTransform());
					this.turretGrandGroup_.roll(-this.roll);
					this.turretGrandGroup_.updateTransform(true);
				}
			}

			if (Status.getShip().getShieldRegenTime() > 0) {
				this.shieldRegenTick += dt;
				if (this.shieldRegenTick > 100L) {
					this.shieldRegenTick = 0L;
					this.player.regenerateShield(this.shieldRegenRate);
				}
			}

			if (Status.getShip().hasRepairBot()) {
				this.repairHPTick += dt;
				this.repairArmorTick += dt;
				if (this.repairHPTick > 600L) {
					this.repairHPTick = 0L;
					if (this.player.getHitpoints() < this.player.getMaxHitpoints()) {
						this.player.regenerateHull();
					}
				}

				if (this.repairArmorTick > 1000L) {
					this.repairArmorTick = 0L;
					if (this.player.getArmorHP() < this.player.getMaxArmorHP()) {
						this.player.regenerateArmor();
					}
				}
			}

			this.player.update(dt);
			if (this.turretMode) {
				this.crosshair.update(this.turretGun.getLocalTransform(), GlobalStatus.renderer.getCamera());
			} else {
				this.crosshair.update(this.shipGrandGroup_.getToParentTransform(), GlobalStatus.renderer.getCamera());
			}

			this.player.transform = this.shipGroup.getLocalTransform();
			if (this.route != null) {
				var9 = this.route.getCurrent();
				this.route.update(this.shipGrandGroup_.getPosX(), this.shipGrandGroup_.getPosY(), this.shipGrandGroup_.getPosZ());
				if (this.route.getCurrent() != var9) {
					if (!this.route.isLooped() && this.route.getCurrent() == 0) {
						var3.hudEvent(24, this);
					} else {
						var3.hudEvent(23, this);
					}
				}
			}

			if (this.totalHP > this.player.getCombinedHP()) {
				GlobalStatus.vibrate(110);
				var3.playerHit();
				this.totalHP = this.player.getCombinedHP();
			}

			if (this.laggingPos.getLength() > 500000) {
				this.shipGrandGroup_.moveTo(20000, this.laggingPos.y, 20000);
			}

			if (isDead()) {
				if (this.player.isActive()) {
					this.explosion.start(this.shipGrandGroup_.getPosX(), this.shipGrandGroup_.getPosY(), this.shipGrandGroup_.getPosZ());
				}

				this.player.setActive(false);
			}

		}
	}

	public final boolean collidesWithStation() {
		return this.touchesStation;
	}

	public final boolean isMining() {
		return this.miningGame != null;
	}

	private void calcCollision(final KIPlayer[] var1) {
		if (var1 != null) {
			for(int i = 0; i < var1.length; ++i) {
				KIPlayer var3 = var1[i];
				if (var3 != null && var3.outerCollide(this.shipGrandGroup_.getPosition(this.vecRight))) {
					int var8;
					if (var3.getMeshId() == 6805 && var3.isVisible()) {
						if (!((PlayerWormHole)var3).isShrinking() && !isMining()) {
							this.vecRight = var3.getPosition(this.vecRight);
							this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.vecUp));
							final int var9 = this.vecRight.getLength();
							var8 = 40000 - var9;
							int var6;
							final int var7 = (var6 = AEMath.max(1, (int)((this.frameTime << 1) * (var8 / 40000.0F)))) << 1;
							this.shipGrandGroup_.translate(-var6 + GlobalStatus.random.nextInt(var7), -var6 + GlobalStatus.random.nextInt(var7), -var6 + GlobalStatus.random.nextInt(var7));
							var8 >>= 8;
							this.vecRight.normalize();
							this.vecUp.set(this.vecRight);
							this.vecUp.scale(var8);
							this.shipGrandGroup_.translate(this.vecUp);
							if (var9 < AEMath.Q_1) {
								this.inWormhole = true;
							}
						}
					} else {
						AEVector3D var4;
						if (var3.isAsteroid) {
							if (!var3.isDying() && !var3.isDead() && (!this.lockedOnAsteroid || var3 != this.lockedAsteroid) && (var4 = var3.getProjectionVector_(this.shipGrandGroup_.getPosition(this.vecRight))) != null) {
								var3.player.setHitVector_(var4.x, var4.y, var4.z);
								var3.player.setBombForce(this.boostActive ? 0.7F : 0.4F);
								final short var5 = 9999;
								var3.player.damageHP(var5, false);
								if (((PlayerAsteroid)var3).getMass_SizeCoef__() > 30) {
									final byte var10 = 40;
									this.player.damageHP(var10, false);
									this.followingCamera_.hit();
								}
							}
						} else if ((var4 = var3.getProjectionVector_(this.shipGrandGroup_.getPosition(this.vecRight))) != null) {
							this.vecUp.set(this.shipGrandGroup_.getDirection(this.vecUp));
							var4.subtract(this.vecUp);
							var8 = var4.getLength();
							var4.scale((int)((float)(this.frameTime >> 2) * (float)(var8 >> 7)));
							this.vecRight = this.vecUp.add(var4, this.vecRight);
							this.vecRight.normalize();
							this.shipGrandGroup_.getToParentTransform().setOrientation(this.vecRight);
							this.shipGrandGroup_.moveForward((int)this.frameTime * this.speed);
							alignToHorizon((int)this.frameTime);
							this.beingPushedAway = true;
							if (i == 0) {
								this.touchesStation = true;
								return;
							}
							break;
						}
					}
				}
			}
		}

	}

	public final void turretRotateLeft(int var1) {
		if (this.turretMode) {
			var1 /= 200 / this.turretRotationSpeed;
			this.turretViewCamera__.rotateEuler(0, var1, 0);
			this.turretStand.rotateEuler(0, var1, 0);
			this.turretGun.rotateEuler(0, var1, 0);
		} else if (!this.lockedOnAsteroid && this.roll < 384) {
			this.roll += (int)(this.handling * var1);
		}

		this.relYawSpeed = 1;
	}

	public final void turretRotateRight(int var1) {
		if (this.turretMode) {
			var1 /= 200 / this.turretRotationSpeed;
			this.turretViewCamera__.rotateEuler(0, -var1, 0);
			this.turretStand.rotateEuler(0, -var1, 0);
			this.turretGun.rotateEuler(0, -var1, 0);
		} else if (!this.lockedOnAsteroid && this.roll > -384) {
			this.roll -= (int)(this.handling * var1);
		}

		this.relYawSpeed = -1;
	}

	public final void turretPitchDown(final int var1) {
		if (this.turretMode) {
			if (this.turretLift_ < 0.0F) {
				this.turretLift_ += var1;
				this.turretGun.rotateEuler(var1, 0, 0);
			}

			if (this.turretHalfLift_ < 0.0F) {
				final float var2 = var1 / 2.0F;
				this.turretHalfLift_ += var2;
				this.turretViewCamera__.rotateEuler((int)var2, 0, 0);
			}
		}

		this.relPitchSpeed = 1;
	}

	public final void turretPitchUp(final int var1) {
		if (this.turretMode) {
			if (this.turretLift_ > -500.0F) {
				this.turretLift_ -= var1;
				this.turretGun.rotateEuler(-var1, 0, 0);
			}

			if (this.turretHalfLift_ > -250.0F) {
				final float var2 = var1 / 2.0F;
				this.turretHalfLift_ -= var2;
				this.turretViewCamera__.rotateEuler(-((int)var2), 0, 0);
			}
		}

		this.relPitchSpeed = -1;
	}

	private void alignToHorizon(int var1) {
		this.calcMatrix_.identity();
		var1 = AEMath.min(var1, 60);
		this.vecUp = this.shipGrandGroup_.getUp(this.vecUp);
		this.vecRight = this.shipGrandGroup_.getRightVector(this.vecRight);
		this.calcMatrix_.identity();
		final int var2 = this.calcMatrix_.getUp().dotPrecise(this.vecRight);
		if (this.calcMatrix_.getUp().dotPrecise(this.vecUp) < 0) {
			if (var2 <= 0) {
				this.shipGrandGroup_.roll(var1 >> 1);
				return;
			}
		} else {
			if ((var2 <= 128 || var2 >= 3968) && (var2 >= -128 || var2 <= -3968)) {
				return;
			}

			if (var2 < 0) {
				this.shipGrandGroup_.roll(var1 >> 1);
				return;
			}
		}

		this.shipGrandGroup_.roll(-var1 >> 1);
	}

	public final void fakeExplode() {
		this.explosion.start(this.shipGrandGroup_.getPosX(), this.shipGrandGroup_.getPosY(), this.shipGrandGroup_.getPosZ());
	}

	public final void setExplosion(final Explosion var1) {
		this.explosion = var1;
	}

	public final void resetGunDelay() {
		this.player.resetGunDelay(0);
	}

	public final boolean isDockingToAsteroid() {
		return this.lockedOnAsteroid && this.miningState != 1;
	}

	public final boolean isDockingToStream_() {
		return false;
	}

	public final boolean isDockedToStream() {
		return this.jumpGating;
	}

	public final void stopPlanetDock_(final TargetFollowCamera var1) {
		var1.setLookAtCam(false);
		this.jumpingToNeighbourPlanet = true;
		this.speed = 8;
		this.disanceFromCamera_ = 0;
	}

	public final boolean isDockingToPlanet() {
		return this.jumpingToNeighbourPlanet;
	}

	public final boolean isDockedToPlanet() {
		return this.disanceFromCamera_ > 3000;
	}

	public final void dockToStream(final TargetFollowCamera var1, final boolean var2) {
		this.vecUp = ((PlayerStaticFar)this.level.getLandmarks()[1]).getTargetPos_(this.vecUp);
		if (var2) {
			this.jumpGating = true;
			this.unused_1399 = false;
		} else {
			this.speed = 2;
			this.vecRight = this.shipGrandGroup_.getDirection(this.vecRight);
			this.vecRight.scale(AEMath.Q_2);
			this.vecUp.add(this.vecRight);
			this.setPosition_(this.vecUp);
			this.beingPushedAway = false;
			this.freeze = false;
			var1.setLookAtCam(true);
			this.unused_1399 = false;
			this.jumpGating = false;
			setAutoPilot((KIPlayer)null);
		}
	}

	public final boolean isLockedOnAsteroid_() {
		return this.lockedOnAsteroid;
	}

	private boolean approachAsteroid(final Hud var1, final int var2) {
		if (this.lockedAsteroid == null) {
			return false;
		}
		int var3;
		switch(this.miningState) {
		case 0:
			if (this.turretMode) {
				setTurretMode(false);
			}

			this.vecRight.set(this.lockedAsteroid.mainMesh_.getPosition(this.vecRight));
			this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.laggingPos));
			var3 = this.vecRight.getLength();
			if (var3 >= this.astroidDockDistance) {
				moveTowards_(this.lockedAsteroid.mainMesh_.getPosition(this.vecRight));
			} else if (this.isMinig) {
				if (this.lockedAsteroid instanceof PlayerAsteroid) {
					((PlayerAsteroid)this.lockedAsteroid).setRotationEnabled(false);
				}

				this.miningState = 1;
			}

			if (var3 < this.astroidDockDistance + 2000) {
				if (this.minigUpVec == null) {
					this.boostTime = boostDuration - boostDuration / 6;
					this.followingCamera_.setLookAtCam(false);
					this.minigUpVec = new AEVector3D(this.shipGrandGroup_.getUp(this.vecUp));
					this.minigUpVec.normalize();
				}

				this.vecUp.set(this.shipGrandGroup_.getDirection(this.vecUp));
				this.vecUp.normalize();
				this.vecUp.subtract(this.minigUpVec);
				if ((AEMath.abs(this.vecUp.getLength()) > 1500) && (this.dockMiningPitch > -1024)) {
					var3 = -((int)this.frameTime) >> 1;
					this.dockMiningPitch += var3;
					this.shipGrandGroup_.pitch(var3);
				} else {
					this.isMinig = true;
				}
			}
			break;
		case 1:
			if (this.dockMiningPitch > -1024) {
				var3 = -((int)this.frameTime) >> 1;
				this.dockMiningPitch += var3;
				this.shipGrandGroup_.pitch(var3);
			} else if (this.miningGame == null) {
				this.miningGame = new MiningGame(((PlayerAsteroid)this.lockedAsteroid).getQuality(), ((PlayerAsteroid)this.lockedAsteroid).oreItemId, var1);
				setExhaustVisible(false);
				GlobalStatus.soundManager.playSfx(6);
			} else if (!this.miningGame.update((int)this.frameTime, var2)) {
				if (!this.miningGame.gameLost() && this.miningGame.getMiningProgressRounded() > 0) {
					endMining();
				} else if (this.miningGame.gameLost()) {
					endMining();
					var1.hudEvent(8, this);
				}

				return true;
			}
		}

		return false;
	}

	public final void endMining() {
		int var1;
		final int var2 = (var1 = ((PlayerAsteroid)this.lockedAsteroid).oreItemId) - 154 + 165;
		int var3;
		int var4 = AEMath.min(var3 = Status.getShip().getFreeCargo(), this.miningGame.getMiningProgressRounded());
		if (var3 > 0) {
			Item var5 = null;
			final boolean var6 = var3 >= 1;
			if (this.miningGame.gotCore() && var6) {
				var5 = Globals.getItems()[var2].makeItem(1);
				Status.getShip().addCargo(var5);
				this.hud.catchCargo(var2, 1, false, false, false, false);
				++Status.minedCores;
				Status.minedCoreTypes[var2 - 165] = true;
				var3 = Status.getShip().getFreeCargo();
				if (var3 < var4) {
					var4 = var3;
				}
			}

			if (var4 > 0) {
				var5 = Globals.getItems()[var1].makeItem(var4);
				Status.getShip().addCargo(var5);
				this.hud.catchCargo(var1, var4, false, false, true, false);
				Status.minedOre += var4;
				Status.minedOreTypes[var1 - 154] = true;
			}

			if (Status.getShip().getFreeCargo() <= 0) {
				this.hud.catchCargo(var1, var4, true, false, true, false);
			}
		} else {
			this.hud.catchCargo(var1, 0, true, false, true, false);
		}

		setExhaustVisible(true);
		this.lockedAsteroid.cargo = null;
		this.lockedAsteroid.hasCargo = false;
		this.lockedAsteroid.player.setHitPoints(-1);
		--Status.asteroidsDestroyed;
		this.miningGame = null;
		dockToAsteroid((KIPlayer)null, this.followingCamera_, this.radar);
	}

	private void moveTowards_(final AEVector3D var1) {
		this.vecRight.set(var1);
		this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.laggingPos));
		this.vecRight.normalize();
		this.vecUp.set(this.shipGrandGroup_.getDirection(this.vecUp));
		this.vecUp.normalize();
		this.vecRight.subtract(this.vecUp);
		this.vecRight.scale((int)(this.frameTime * 4.0F));
		this.autoPilotDir = this.vecUp.add(this.vecRight, this.autoPilotDir);
		this.autoPilotDir.normalize();
		this.shipGrandGroup_.getToParentTransform().setOrientation(this.autoPilotDir);
		if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
			alignToHorizon((int)this.frameTime);
		}

		this.shipGrandGroup_.moveForward((int)this.frameTime * this.speed);
	}

	public final void dockToAsteroid(final KIPlayer asteroid, final TargetFollowCamera tfCam, final Radar radar) {
		if (this.lockedOnAsteroid) {
			((PlayerAsteroid)this.lockedAsteroid).setRotationEnabled(true);
			this.lockedOnAsteroid = false;
			this.beingPushedAway = false;
			this.lockedAsteroid = null;
			this.minigUpVec = null;
			tfCam.setLookAtCam(true);
			GlobalStatus.renderer.setActiveCamera((AECamera)tfCam.getCamera());
			LevelScript.resetCamera(tfCam, this.level);
			this.player.resetGunDelay(0);
			this.miningGame = null;
			radar.unlockAsteroid();
		} else if (asteroid != null) {
			this.dockMiningPitch = 0;
			this.followingCamera_ = tfCam;
			this.lockedOnAsteroid = true;
			this.lockedAsteroid = asteroid;
			this.vecUp = asteroid.mainMesh_.copyScaleTo(this.vecUp);
			this.astroidDockDistance = (this.vecUp.x + this.vecUp.y + this.vecUp.z) / 2;
			this.beingPushedAway = true;
			this.miningState = 0;
		}
	}

	public final void OnRelease() {
		this.level = null;
		if (this.explosion != null) {
			this.explosion.OnRelease();
		}

		this.explosion = null;
		if (this.crosshair != null) {
			this.crosshair.OnRelease();
		}

		this.crosshair = null;
		this.unused_7a2 = null;
		if (this.turretStand != null) {
			this.turretStand.OnRelease();
		}

		this.turretStand = null;
		if (this.turretGun != null) {
			this.turretGun.OnRelease();
		}

		this.turretGun = null;
		System.gc();
	}

	public final void render(final boolean var1) {
		if (isDead()) {
			this.explosion.update(this.frameTime);
		} else {
			if (this.explosion.canExplode()) {
				this.explosion.update(this.frameTime);
			}

			if (this.cloakingScaleX >= 0) {
				GlobalStatus.renderer.drawNodeInVF(this.shipGrandGroup_);
				if (this.hasTurret) {
					GlobalStatus.renderer.drawNodeInVF(this.turretGroup);
				}
			} else {
				this.shipGrandGroup_.updateTransform(true);
				if (this.hasTurret) {
					this.turretGroup.updateTransform(true);
				}
			}

			if (this.tractorBeam != null) {
				this.tractorBeam.render();
			}

		}
	}

	public final void draw(final boolean drawXhair, final AECamera var2) {
		if (this.miningGame != null) {
			this.miningGame.render2D();
		} else if (
				!this.autoDriven 
				&& !isDead() 
				&& !this.freeze 
				&& drawXhair 
				&& !this.lockedOnAsteroid
				&& !this.jumpingToNeighbourPlanet 
				&& !this.jumpGating 
				&& !this.autopilotActive) {
			this.crosshair.draw();
		}
	}
	/**
	 * Shakes the camera
	 */
	public final void hit() {
		this.followingCamera_.hit();
	}

	public final Hud getHud() {
		return this.hud;
	}
}
