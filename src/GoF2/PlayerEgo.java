package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.Camera;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.Group;
import AE.LookAtCamera;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;
import AE.TargetFollowCamera;

public final class PlayerEgo {
   private static int boostSpeed = 5;
   private static int boostDuration = 5000;
   private static int boostDelay = 20000;
   private static int[][] turretPartsPositions = new int[][]{{1, 0, 228, -72}, {7, 0, 215, 46}, {11, 0, 165, 147}, {17, 0, 224, 404}, {23, 0, 140, -21}, {24, 0, 195, 88}, {28, 0, 269, -69}, {33, 0, 255, -176}, {36, 0, 255, 1015}};
   public Player player;
   public Group shipGrandGroup_;
   private Group shipGroup;
   private AbstractMesh turretStand;
   private AbstractMesh turretGun;
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
   private boolean boostingEnabled;
   private AEVector3D laggingPos;
   private Matrix calcMatrix_ = new Matrix();
   private float handling;
   private int addHandling;
   private boolean autopilotActive;
   private KIPlayer autopilotTarget;
   private boolean asteroidFieldTarget_;
   private AEVector3D autoPilotDir;
   private boolean hasTurret;
   private TargetFollowCamera followingCamera_;
   private Camera camera;
   private Group turretViewCamera__;
   private Group turretGrandGroup_;
   private Group turretGroup;
   private boolean turretMode = false;
   private float turretHalfLift_;
   private float turretLift_;
   private boolean cloakOn;
   private Item cloak;
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
   private int cloakDuration;
   private int cloakLoadingTime;
   private float shieldRegenRate;
   private Hud hud;
   public Radar radar;
   public Radio radio;
   private boolean inWormhole;
   private int[][] enginesBoostScale_;
   private int turretShipId_;
   private boolean touchesStation;

   public PlayerEgo(Player var1) {
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
      boostSpeed = 2 + (int)((float)Status.getShip().getBoostPercent() / 100.0F * 2.0F);
      boostDuration = Status.getShip().getBoostDuration();
      this.boostingEnabled = boostDelay > 0;
      this.handling = Status.getShip().getHandling();
      this.addHandling = Status.getShip().getAddHandlingPercent();
      this.handling += (float)this.addHandling / 100.0F;

      try {
         this.shieldRegenRate = (float)Status.getShip().getShield() / ((float)Status.getShip().getShieldRegenTime() / 100.0F);
      } catch (Exception var2) {
         this.shieldRegenRate = 0.0F;
      }

      this.lockedOnAsteroid = false;
      this.isMinig = false;
      this.inWormhole = false;
      this.cloak = Status.getShip().getFirstEquipmentOfSort(21);
      this.cloakTime = -1;
      this.cloakDuration = this.cloak == null ? 0 : this.cloak.getAttribute(33);
      this.cloakLoadingTime = this.cloak == null ? 0 : this.cloak.getAttribute(34);
      this.cloakOn = false;
      this.autoDriven = false;
   }

   public final void setShip(int var1, int var2) {
      this.shipGrandGroup_ = new Group();
      this.shipGroup = Globals.getShipGroup(var1, var2);
      var1 = 0;

      for(GraphNode var4 = this.shipGroup.getEndNode(); var4 != null; var4 = var4.getParent()) {
         if (var4.getID() == 13067 || var4.getID() == 13068 || var4.getID() == 13070 || var4.getID() == 13064 || var4.getID() == 13065 || var4.getID() == 13071) {
            ++var1;
         }
      }

      if (var1 > 0) {
         this.enginesBoostScale_ = new int[var1][3];
      }

      var2 = 0;

      for(GraphNode var3 = this.shipGroup.getEndNode(); var3 != null; var3 = var3.getParent()) {
         if (var3.getID() == 13067 || var3.getID() == 13068 || var3.getID() == 13070 || var3.getID() == 13064 || var3.getID() == 13065 || var3.getID() == 13071) {
            this.vecUp = var3.copyScaleTo(this.vecUp);
            this.enginesBoostScale_[var2][0] = this.vecUp.x;
            this.enginesBoostScale_[var2][1] = this.vecUp.y;
            this.enginesBoostScale_[var2][2] = this.vecUp.z;
            ++var2;
         }
      }

      this.shipGrandGroup_.uniqueAppend_(this.shipGroup);
      if (Status.getShip().getFirstEquipmentOfSort(13) != null) {
         this.tractorBeam = new TractorBeam(this.shipGrandGroup_, Status.getShip().getFirstEquipmentOfSort(13).getIndex() - 68);
      }

   }

   public final void setExhaustVisible(boolean var1) {
      for(GraphNode var2 = this.shipGroup.getEndNode(); var2 != null; var2 = var2.getParent()) {
         if (var2.getID() == 13067 || var2.getID() == 13068 || var2.getID() == 13070 || var2.getID() == 13064 || var2.getID() == 13065 || var2.getID() == 13071) {
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

   public final void setCamControllers(TargetFollowCamera var1, LookAtCamera var2) {
      this.followingCamera_ = var1;
   }

   public final boolean setTurretMode(boolean var1) {
      if (this.hasTurret && !this.isMining()) {
         this.turretMode = var1;
         if (var1) {
            if (this.camera == null) {
               this.camera = Camera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 700, 300, 200000);
               this.camera.translate(0, 500, -1400);
               this.camera.rotateEuler(0, 2048, 0);
               this.turretViewCamera__ = new Group();
               this.turretViewCamera__.uniqueAppend_(this.camera);
               this.turretViewCamera__.setRotationOrder((short)2);
               this.turretViewCamera__.translate(turretPartsPositions[this.turretShipId_][1], turretPartsPositions[this.turretShipId_][2], turretPartsPositions[this.turretShipId_][3]);
               this.turretViewCamera__.rotateEuler(0, 2048, 0);
               this.turretGrandGroup_ = new Group();
               this.turretGrandGroup_.uniqueAppend_(this.turretViewCamera__);
            }

            this.turretGrandGroup_.moveTo(this.laggingPos);
            this.turretGrandGroup_.setTransform(this.shipGrandGroup_.getToParentTransform());
            GlobalStatus.renderer.setActiveCamera(this.camera);
         } else {
            GlobalStatus.renderer.setActiveCamera((Camera)this.followingCamera_.getCamera());
            LevelScript.resetCamera(this.followingCamera_, this.level);
         }

         return true;
      } else {
         return false;
      }
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
      float var1;
      if ((var1 = (float)this.boostTime / (float)(boostDuration / 6)) < 1.0F) {
         return var1;
      } else {
         return var1 > 5.0F ? 6.0F - var1 : 1.0F;
      }
   }

   public final float getBoostRate() {
      float var1;
      return (var1 = (float)this.boostTime / (float)boostDelay) > 0.0F ? 1.0F : var1 + 1.0F;
   }

   public final float getCloakRate() {
      float var1;
      return (var1 = this.cloakOn ? (float)this.cloakTime / (float)this.cloakDuration : (float)this.cloakTime / (float)this.cloakLoadingTime) > 0.0F ? var1 : 1.0F;
   }

   public final boolean isCloakReady() {
      return !this.cloakOn && this.cloakTime == -1;
   }

   public final boolean isCloaked() {
      return this.cloakOn;
   }

   public final void setCollide(boolean var1) {
      this.collisionsOn = var1;
   }

   public final void setLevel(Level var1) {
      this.level = var1;
   }

   public final void setRoute(Route var1) {
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

   public final void shoot(int var1, int var2) {
      if (this.turretMode) {
         this.calcMatrix_.set(this.turretGun.getLocalTransform());
         this.player.playShootSound__(2, (long)var1, false, this.calcMatrix_);
      } else {
         if (var2 == 1) {
            if (!this.player.shoot(var2, this.currentSecondaryId, (long)var1, false)) {
               this.currentSecondaryId = -1;
               return;
            }
         } else {
            this.player.shoot_(var2, (long)var1, false);
         }

      }
   }

   public final void setGuns(Gun[] var1, int var2) {
      Player var6;
      if ((var6 = this.player).guns != null && var2 <= 3 && var2 >= 0) {
         var6.guns[var2] = new Gun[var1.length];
         var6.guns[var2] = var1;
      }

      if (!this.hasTurret) {
         this.hasTurret = this.player.hasGunOfType(2);
         if (this.hasTurret) {
            Item[] var8 = Status.getShip().getEquipment(2);
            short var9 = -1;
            short var4 = -1;
            byte var5 = -1;
            this.turretRotationSpeed = var8[0].getAttribute(15);
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
            this.turretGroup = new Group();

            for(var2 = 0; var2 < turretPartsPositions.length; ++var2) {
               if (turretPartsPositions[var2][0] == Status.getShip().getIndex()) {
                  this.turretStand.moveTo(turretPartsPositions[var2][1], turretPartsPositions[var2][2], turretPartsPositions[var2][3]);
                  this.turretGun.moveTo(turretPartsPositions[var2][1], turretPartsPositions[var2][2], turretPartsPositions[var2][3]);
                  this.turretShipId_ = var2;
                  break;
               }
            }

            this.turretGun.translate(0, var5, 0);
            this.turretStand.rotateEuler(0, 2048, 0);
            this.turretGun.rotateEuler(0, 2048, 0);
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

   public final void setCurrentSecondaryWeaponIndex(int var1) {
      this.currentSecondaryId = var1;
   }

   public final AEVector3D getPosition() {
      return this.shipGrandGroup_.getLocalPos(this.vecUp);
   }

   public final void setActive(boolean var1) {
      this.player.setActive(false);
   }

   public final void setComputerControlled(boolean var1) {
      this.autoDriven = var1;
   }

   public final void setFreeze(boolean var1) {
      this.freeze = var1;
   }

   public final void setAutoPilot(KIPlayer var1) {
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
      return this.autopilotActive && !this.goingToStream() && !this.goingToStation() && !this.asteroidFieldTarget_;
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
      } else {
         return false;
      }
   }

   public final boolean gointToAsteroidField() {
      return this.asteroidFieldTarget_;
   }

   public final void setPosition_(int var1, int var2, int var3) {
      this.shipGrandGroup_.moveTo(var1, var2, var3);
      this.player.transform.translateTo(var1, var2, var3);
      this.vecUp.set(var1, var2, var3);
   }

   public final void setPosition_(AEVector3D var1) {
      this.shipGrandGroup_.moveTo(var1);
      this.player.transform.translateTo(var1);
   }

   public final boolean isInWormhole() {
      return this.inWormhole;
   }

   public final void update(int var1, Radar var2, Hud var3, Radio var4, int var5) {
      if (this.hud == null) {
         this.hud = var3;
         this.radar = var2;
         this.radio = var4;
      }

      if (!this.freeze) {
         this.laggingPos = this.shipGrandGroup_.getLocalPos(this.laggingPos);
         this.frameTime = (long)var1;
         if (this.boostTime < 0 && this.boostTime + var1 * 3 > 0) {
            var3.hudEvent(4, this);
            this.boostTime = 0;
         }

         this.boostTime += var1;
         int var9;
         if (this.boostActive) {
            var9 = 0;
            int var6 = (int)(this.getBoostPercentage() * 2048.0F);

            GraphNode var7;
            int[] var8;
            for(var7 = this.shipGroup.getEndNode(); var7 != null; var7 = var7.getParent()) {
               if (var7.getID() == 13067 || var7.getID() == 13068 || var7.getID() == 13070 || var7.getID() == 13064 || var7.getID() == 13065 || var7.getID() == 13071) {
                  var8 = this.enginesBoostScale_[var9++];
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
                  if (var7.getID() == 13067 || var7.getID() == 13068 || var7.getID() == 13070 || var7.getID() == 13064 || var7.getID() == 13065 || var7.getID() == 13071) {
                     var8 = this.enginesBoostScale_[var9++];
                     var7.setScale(var8[0], var8[1], var8[2]);
                  }
               }
            }
         }

         if (this.cloakOn) {
            if (this.cloakingScaleX >= 0) {
               this.cloakingTimeCoef = (int)((long)this.cloakingTimeCoef + (this.frameTime << 3));
               this.cloakingScaleX = (int)(-4.8828125E-4F * (float)((this.cloakingTimeCoef - 4096) * (this.cloakingTimeCoef - 4096)) + 8192.0F + 4096.0F);
               this.shipGrandGroup_.setScale(this.cloakingScaleX, 4096, 4096);
            } else {
               this.shipGrandGroup_.setScale(4096, 4096, 4096);
            }

            this.cloakTime = (int)((long)this.cloakTime + this.frameTime);
            Status.invisibleTime += this.frameTime;
            if (this.cloakTime > this.cloakDuration) {
               this.cloakTime = 0;
               this.cloakOn = false;
               this.player.hidden = false;
            }
         } else {
            if (this.cloakingTimeCoef > 0) {
               this.cloakingTimeCoef = (int)((long)this.cloakingTimeCoef - (this.frameTime << 3));
               this.cloakingScaleX = (int)(-4.8828125E-4F * (float)((this.cloakingTimeCoef - 4096) * (this.cloakingTimeCoef - 4096)) + 8192.0F + 4096.0F);
               this.shipGrandGroup_.setScale(this.cloakingScaleX, 4096, 4096);
            } else {
               this.cloakingTimeCoef = 0;
               this.shipGrandGroup_.setScale(4096, 4096, 4096);
            }

            if (this.cloakTime >= 0) {
               this.cloakTime = (int)((long)this.cloakTime + this.frameTime);
               if (this.cloakTime > this.cloakLoadingTime) {
                  this.cloakTime = -1;
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
               this.disanceFromCamera_ = (int)((long)this.disanceFromCamera_ + this.frameTime);
            }

            this.shipGrandGroup_.moveForward(var1 * this.speed);
         }

         if (this.collisionsOn) {
            this.touchesStation = false;
            this.calcCollision(this.level.getLandmarks());
            this.calcCollision(this.level.getEnemies());
            this.calcCollision(this.level.getAsteroids());
         }

         if (this.lockedOnAsteroid) {
            this.beingPushedAway = true;
            if (this.lockedAsteroid.isDead()) {
               this.dockToAsteroid((KIPlayer)null, this.followingCamera_, var2);
               return;
            }

            this.approachAsteroid(var3, var5);
         }

         if (this.autopilotActive && this.autopilotTarget != null && !this.beingPushedAway) {
            if (this.asteroidFieldTarget_ && ((Waypoint)this.autopilotTarget).activeRoute_ != null) {
               this.autopilotTarget = ((Waypoint)this.autopilotTarget).activeRoute_.getDockingTarget_();
            }

            if (this.autopilotTarget != null && !this.jumpGating) {
               this.vecRight = this.autopilotTarget.getPosition(this.vecRight);
               this.moveTowards_(this.vecRight);
            } else {
               this.setAutoPilot((KIPlayer)null);
            }
         } else if (!this.beingPushedAway) {
            if (this.turretMode) {
               ((Camera)this.followingCamera_.getCamera()).updateTransform(true);
               this.followingCamera_.update();
               this.turretGrandGroup_.moveTo(this.laggingPos);
               this.vecUp = this.shipGrandGroup_.getUp(this.vecUp);
               this.vecUp.scale(512);
               this.turretGrandGroup_.translate(this.vecUp);
               this.relPitchSpeed = this.relYawSpeed = 0;
               this.alignToHorizon((int)this.frameTime);
            } else {
               this.shipGrandGroup_.pitch((int)(this.handling * (float)(this.relPitchSpeed * var1 / 3)));
               this.shipYaw = (int)(this.handling * (float)(this.relYawSpeed * var1 / 3));
               this.shipGrandGroup_.yaw(this.shipYaw);
               if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
                  this.alignToHorizon((int)this.frameTime);
               }
            }

            this.shipGrandGroup_.moveForward(var1 * this.speed);
         }

         if (this.lockedOnAsteroid || !this.beingPushedAway && !this.turretMode) {
            this.shipGroup.setRotation(0, 0, -this.roll);
            if (this.roll > 0 && this.relYawSpeed == 0) {
               this.roll -= var1 / 2;
            } else if (this.roll < -var1 / 2 && this.relYawSpeed == 0) {
               this.roll += var1 / 2;
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
            this.shieldRegenTick += (long)var1;
            if (this.shieldRegenTick > 100L) {
               this.shieldRegenTick = 0L;
               this.player.regenerateShield(this.shieldRegenRate);
            }
         }

         if (Status.getShip().hasRepairBot()) {
            this.repairHPTick += (long)var1;
            this.repairArmorTick += (long)var1;
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

         this.player.update((long)var1);
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

         if (this.isDead()) {
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

   private void calcCollision(KIPlayer[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            KIPlayer var3;
            if ((var3 = var1[var2]) != null && var3.outerCollide(this.shipGrandGroup_.getPosition(this.vecRight))) {
               int var8;
               if (var3.getMeshId() == 6805 && var3.isVisible()) {
                  if (!((PlayerWormHole)var3).isShrinking() && !this.isMining()) {
                     this.vecRight = var3.getPosition(this.vecRight);
                     this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.vecUp));
                     int var9 = this.vecRight.getLength();
                     var8 = '鱀' - var9;
                     int var6;
                     int var7 = (var6 = AEMath.max(1, (int)((float)(this.frameTime << 1) * ((float)var8 / 40000.0F)))) << 1;
                     this.shipGrandGroup_.translate(-var6 + GlobalStatus.random.nextInt(var7), -var6 + GlobalStatus.random.nextInt(var7), -var6 + GlobalStatus.random.nextInt(var7));
                     var8 >>= 8;
                     this.vecRight.normalize();
                     this.vecUp.set(this.vecRight);
                     this.vecUp.scale(var8);
                     this.shipGrandGroup_.translate(this.vecUp);
                     if (var9 < 4096) {
                        this.inWormhole = true;
                     }
                  }
               } else {
                  AEVector3D var4;
                  if (var3.isAsteroid) {
                     if (!var3.isDying() && !var3.isDead() && (!this.lockedOnAsteroid || var3 != this.lockedAsteroid) && (var4 = var3.getProjectionVector_(this.shipGrandGroup_.getPosition(this.vecRight))) != null) {
                        var3.player.setHitVector_(var4.x, var4.y, var4.z);
                        var3.player.setBombForce(this.boostActive ? 0.7F : 0.4F);
                        short var5 = 9999;
                        var3.player.damageHP(var5, false);
                        if (((PlayerAsteroid)var3).getMass_SizeCoef__() > 30) {
                           byte var10 = 40;
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
                     this.alignToHorizon((int)this.frameTime);
                     this.beingPushedAway = true;
                     if (var2 == 0) {
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
         this.roll += (int)(this.handling * (float)var1);
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
         this.roll -= (int)(this.handling * (float)var1);
      }

      this.relYawSpeed = -1;
   }

   public final void turretPitchDown(int var1) {
      if (this.turretMode) {
         if (this.turretLift_ < 0.0F) {
            this.turretLift_ += (float)var1;
            this.turretGun.rotateEuler(var1, 0, 0);
         }

         if (this.turretHalfLift_ < 0.0F) {
            float var2 = (float)var1 / 2.0F;
            this.turretHalfLift_ += var2;
            this.turretViewCamera__.rotateEuler((int)var2, 0, 0);
         }
      }

      this.relPitchSpeed = 1;
   }

   public final void turretPitchUp(int var1) {
      if (this.turretMode) {
         if (this.turretLift_ > -500.0F) {
            this.turretLift_ -= (float)var1;
            this.turretGun.rotateEuler(-var1, 0, 0);
         }

         if (this.turretHalfLift_ > -250.0F) {
            float var2 = (float)var1 / 2.0F;
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
      int var2 = this.calcMatrix_.getUp().dotPrecise(this.vecRight);
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

   public final void setExplosion(Explosion var1) {
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

   public final void stopPlanetDock_(TargetFollowCamera var1) {
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

   public final void dockToStream(TargetFollowCamera var1, boolean var2) {
      this.vecUp = ((PlayerStaticFar)this.level.getLandmarks()[1]).getTargetPos_(this.vecUp);
      if (var2) {
         this.jumpGating = true;
         this.unused_1399 = false;
      } else {
         this.speed = 2;
         this.vecRight = this.shipGrandGroup_.getDirection(this.vecRight);
         this.vecRight.scale(8192);
         this.vecUp.add(this.vecRight);
         this.setPosition_(this.vecUp);
         this.beingPushedAway = false;
         this.freeze = false;
         var1.setLookAtCam(true);
         this.unused_1399 = false;
         this.jumpGating = false;
         this.setAutoPilot((KIPlayer)null);
      }
   }

   public final boolean isLockedOnAsteroid_() {
      return this.lockedOnAsteroid;
   }

   private boolean approachAsteroid(Hud var1, int var2) {
      if (this.lockedAsteroid == null) {
         return false;
      } else {
         int var3;
         switch(this.miningState) {
         case 0:
            if (this.turretMode) {
               this.setTurretMode(false);
            }

            this.vecRight.set(this.lockedAsteroid.mainMesh_.getPosition(this.vecRight));
            this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.laggingPos));
            if ((var3 = this.vecRight.getLength()) >= this.astroidDockDistance) {
               this.moveTowards_(this.lockedAsteroid.mainMesh_.getPosition(this.vecRight));
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
               if (AEMath.abs(this.vecUp.getLength()) > 1500) {
                  if (this.dockMiningPitch > -1024) {
                     var3 = -((int)this.frameTime) >> 1;
                     this.dockMiningPitch += var3;
                     this.shipGrandGroup_.pitch(var3);
                  } else {
                     this.isMinig = true;
                  }
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
               this.setExhaustVisible(false);
               GlobalStatus.soundManager.playSfx(6);
            } else if (!this.miningGame.update((int)this.frameTime, var2)) {
               if (!this.miningGame.gameLost() && this.miningGame.getMiningProgressRounded() > 0) {
                  this.endMining();
               } else if (this.miningGame.gameLost()) {
                  this.endMining();
                  var1.hudEvent(8, this);
               }

               return true;
            }
         }

         return false;
      }
   }

   public final void endMining() {
      int var1;
      int var2 = (var1 = ((PlayerAsteroid)this.lockedAsteroid).oreItemId) - 154 + 165;
      int var3;
      int var4 = AEMath.min(var3 = Status.getShip().getFreeCargo(), this.miningGame.getMiningProgressRounded());
      if (var3 > 0) {
         Item var5 = null;
         boolean var6 = var3 >= 1;
         if (this.miningGame.gotCore() && var6) {
            var5 = Globals.getItems()[var2].makeItem(1);
            Status.getShip().addCargo(var5);
            this.hud.catchCargo(var2, 1, false, false, false, false);
            ++Status.minedCores;
            Status.minedCoreTypes[var2 - 165] = true;
            if ((var3 = Status.getShip().getFreeCargo()) < var4) {
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

      this.setExhaustVisible(true);
      this.lockedAsteroid.cargo = null;
      this.lockedAsteroid.hasCargo = false;
      this.lockedAsteroid.player.setHitPoints(-1);
      --Status.asteroidsDestroyed;
      this.miningGame = null;
      this.dockToAsteroid((KIPlayer)null, this.followingCamera_, this.radar);
   }

   private void moveTowards_(AEVector3D var1) {
      this.vecRight.set(var1);
      this.vecRight.subtract(this.shipGrandGroup_.getPosition(this.laggingPos));
      this.vecRight.normalize();
      this.vecUp.set(this.shipGrandGroup_.getDirection(this.vecUp));
      this.vecUp.normalize();
      this.vecRight.subtract(this.vecUp);
      this.vecRight.scale((int)((float)this.frameTime * 4.0F));
      this.autoPilotDir = this.vecUp.add(this.vecRight, this.autoPilotDir);
      this.autoPilotDir.normalize();
      this.shipGrandGroup_.getToParentTransform().setOrientation(this.autoPilotDir);
      if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
         this.alignToHorizon((int)this.frameTime);
      }

      this.shipGrandGroup_.moveForward((int)this.frameTime * this.speed);
   }

   public final void dockToAsteroid(KIPlayer var1, TargetFollowCamera var2, Radar var3) {
      if (this.lockedOnAsteroid) {
         ((PlayerAsteroid)this.lockedAsteroid).setRotationEnabled(true);
         this.lockedOnAsteroid = false;
         this.beingPushedAway = false;
         this.lockedAsteroid = null;
         this.minigUpVec = null;
         var2.setLookAtCam(true);
         GlobalStatus.renderer.setActiveCamera((Camera)var2.getCamera());
         LevelScript.resetCamera(var2, this.level);
         this.player.resetGunDelay(0);
         this.miningGame = null;
         var3.unlockAsteroid();
      } else if (var1 != null) {
         this.dockMiningPitch = 0;
         this.followingCamera_ = var2;
         this.lockedOnAsteroid = true;
         this.lockedAsteroid = var1;
         this.vecUp = var1.mainMesh_.copyScaleTo(this.vecUp);
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

   public final void render(boolean var1) {
      if (this.isDead()) {
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

   public final void draw(boolean var1, Camera var2) {
      if (this.miningGame != null) {
         this.miningGame.render2D();
      } else {
         if (!this.autoDriven && !this.isDead() && !this.freeze && var1 && !this.lockedOnAsteroid && !this.jumpingToNeighbourPlanet && !this.jumpGating && !this.autopilotActive) {
            this.crosshair.draw();
         }

      }
   }

   public final void hit() {
      this.followingCamera_.hit();
   }

   public final Hud getHud() {
      return this.hud;
   }
}
