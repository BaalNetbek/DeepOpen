package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Group;
import AE.Math.AEMath;
import AE.Math.AEVector3D;

public abstract class KIPlayer {
   protected short state = 0;
   protected int speed = 2;
   public Player player;
   public AbstractMesh mainMesh_;
   public Group geometry;
   protected int meshId;
   protected int shipId_;
   protected int race;
   protected Level level;
   protected Route activeRoute_;
   protected Explosion explosion;
   protected AEVector3D tempVector_ = new AEVector3D();
   protected AEVector3D position = new AEVector3D();
   protected int targetX;
   protected int targetY;
   protected int targetZ;
   protected boolean carriesMissionCrate;
   public boolean isAsteroid;
   public boolean junk;
   public boolean hasCargo;
   protected int[] cargo;
   public AbstractMesh waste;
   protected AbstractMesh jumpMesh;
   protected boolean diedWithMissionCrate;
   protected boolean lostMissionCrateToEgo;
   protected boolean unusedc3a_;
   protected int crateLifeTime;
   protected boolean wingman;
   protected int wingmanId;
   protected int wingmanCommand;
   protected KIPlayer wingmanTarget;
   protected boolean jumper;
   protected int jumpTick;
   public String name;
   public boolean stunned;
   public boolean armed;
   protected boolean visible;
   public boolean withinRenderDistance = true;

   public KIPlayer(int var1, int var2, Player var3, AbstractMesh var4, int var5, int var6, int var7) {
      this.race = var2;
      this.player = var3;
      this.mainMesh_ = var4;
      this.meshId = var1;
      if (this.mainMesh_ != null) {
         this.mainMesh_.moveTo(var5, var6, var7);
         this.mainMesh_.setRotation(0, 0, 0);
         this.player.transform = var4.getToParentTransform();
      }

      this.player.setKIPlayer(this);
      this.armed = false;
      this.targetX = var5;
      this.targetY = var6;
      this.targetZ = var7;
      this.isAsteroid = false;
      this.junk = false;
      this.hasCargo = false;
      this.carriesMissionCrate = false;
      this.diedWithMissionCrate = false;
      this.lostMissionCrateToEgo = false;
      this.unusedc3a_ = false;
      this.wingman = false;
      if (this.tempVector_ == null) {
         this.tempVector_ = new AEVector3D();
      }

      this.jumper = false;
      this.name = null;
      this.visible = true;
   }

   public final boolean isVisible() {
      return this.visible;
   }

   public final void setVisible(boolean var1) {
      this.visible = var1;
      if (this.geometry != null) {
         this.geometry.setDraw(var1);
      }

      if (this.mainMesh_ != null) {
         this.mainMesh_.setDraw(var1);
      }

   }

   public final void setGroup(Group var1, int var2) {
      this.shipId_ = var2;
      this.geometry = var1;
      this.geometry.moveTo(this.targetX, this.targetY, this.targetZ);
      this.geometry.setRotation(0, 0, 0);
      this.player.transform = var1.getToParentTransform();
   }

   public final void setJumper(boolean var1) {
      this.jumper = true;
   }

   public final boolean isJumper() {
      return this.jumper;
   }

   public void OnRelease() {
      this.player = null;
      if (this.mainMesh_ != null) {
         this.mainMesh_.OnRelease();
      }

      this.mainMesh_ = null;
      this.geometry = null;
      this.level = null;
      this.activeRoute_ = null;
      if (this.explosion != null) {
         this.explosion.OnRelease();
      }

      this.explosion = null;
      this.jumpMesh = null;
   }

   public void setActive(boolean var1) {
      this.player.setActive(var1);
   }

   public final void setInitActive(boolean var1) {
      this.setActive(false);
   }

   public final void captureCrate(Hud var1) {
      if (this.isDead() || this.isDying()) {
         this.hasCargo = false;
         this.setActive(false);
      }

      this.waste = null;
      Item var2 = null;

      for(int var3 = 0; var3 < this.cargo.length; var3 += 2) {
         if (this.cargo[var3 + 1] > 0) {
            int var4 = AEMath.max(1, GlobalStatus.random.nextInt(this.cargo[var3 + 1]));
            var2 = Globals.getItems()[this.cargo[var3]].makeItem(var4);
            int[] var10000 = this.cargo;
            var10000[var3 + 1] -= var4;
            break;
         }
      }

      if (var2 != null) {
         if (this.player.friend) {
            this.level.stealFriendCargo();
         }

         Status.getStanding().applyStealCargo(this.race);
         boolean var5 = this.carriesMissionCrate && (var2.getIndex() == 116 || var2.getIndex() == 117);
         if (Status.getShip().spaceAvailable(var2.getAmount())) {
            Status.incCargoSalvaged(var2.getAmount());
            if (var5) {
               var2.setUnsaleable(true);
            }

            Status.getShip().addCargo(var2);
            Level var6 = this.level;
            var6.capturedCargoCount += var2.getAmount();
            if (var5) {
               this.lostMissionCrateToEgo = true;
            } else if (this.race == 9) {
               Status.alienJunkSalvaged += var2.getAmount();
            } else if (var2.getIndex() >= 132 && var2.getIndex() < 154) {
               Status.drinkTypesPossesed[var2.getIndex() - 132] = true;
            }

            var1.catchCargo(var2.getIndex(), var2.getAmount(), false, var5, false, false);
            return;
         }

         if (var5) {
            this.diedWithMissionCrate = true;
         }

         var1.catchCargo(var2.getIndex(), var2.getAmount(), true, false, false, false);
      }

   }

   public final boolean isWingman() {
      return this.wingman;
   }

   public final void setWingman(boolean var1, int var2) {
      this.wingman = true;
      this.wingmanId = var2;
      this.wingmanCommand = 2;
   }

   public void setWingmanCommand(int var1, KIPlayer var2) {
      this.wingmanCommand = var1;
      this.wingmanTarget = var2;
   }

   public void setSpeed(int var1) {
      this.speed = var1;
   }

   public final void setLevel(Level var1) {
      this.level = var1;
   }

   public final void setExplosion(Explosion var1) {
      this.explosion = var1;
   }

   public final void setRoute(Route var1) {
      this.activeRoute_ = var1;
   }

   public final Route getRoute() {
      return this.activeRoute_;
   }

   public final void addGun(Gun var1, int var2) {
      Player var10000 = this.player;
      int var3 = var2;
      Gun var5 = var1;
      Player var4 = var10000;
      if (var10000.guns != null && var3 <= 3 && var3 >= 0) {
         var4.guns[var3] = new Gun[1];
         var4.guns[var3][0] = var5;
      }

   }

   public final int getMeshId() {
      return this.meshId;
   }

   public AEVector3D getPosition(AEVector3D var1) {
      if (this.mainMesh_ != null) {
         return this.mainMesh_.getLocalPos(var1);
      } else {
         return this.geometry != null ? this.geometry.getLocalPos(var1) : null;
      }
   }

   public final boolean isDead() {
      return this.state == 4;
   }

   public final boolean isDying() {
      return this.state == 3;
   }

   public final void setDead() {
      this.state = 4;
      this.setActive(false);
   }

   public final void setToSleep() {
      this.state = 5;
      this.player.setActive(false);
   }

   public final void awake() {
      this.state = 1;
      this.player.setActive(true);
   }

   public final void setJumpMesh(AbstractMesh var1) {
      this.jumpMesh = var1;
   }

   public final void createCrate(int var1) {
      if (var1 == 0) {
         this.waste = AEResourceManager.getGeometryResource(17);
         this.waste.setScale(16384, 16384, 16384);
      } else if (var1 == 2) {
         this.waste = AEResourceManager.getGeometryResource(9996);
         this.waste.setScale(2048, 2048, 2048);
      } else if (var1 == 3) {
         this.waste = AEResourceManager.getGeometryResource(6767);
         this.waste.setScale(2048, 2048, 2048);
      } else {
         this.waste = AEResourceManager.getGeometryResource(6769);
         this.waste.setScale(512, 512, 512);
      }

      this.waste.setRenderLayer(2);
      this.waste.moveTo(this.mainMesh_ != null ? this.mainMesh_.getPostition() : this.geometry.getPostition());
      this.player.transform = this.waste.getToParentTransform();
      this.player.setKIPlayer(this);
   }

   public final boolean cargoAvailable_() {
      boolean var1 = false;
      if (this.cargo != null) {
         for(int var2 = 0; var2 < this.cargo.length; var2 += 2) {
            if (this.cargo[var2 + 1] > 0) {
               var1 = true;
               break;
            }
         }
      }

      return var1;
   }

   public abstract void update(long var1);

   public abstract boolean outerCollide(int var1, int var2, int var3);

   public boolean outerCollide(AEVector3D var1) {
      return false;
   }

   public AEVector3D getProjectionVector_(AEVector3D var1) {
      return null;
   }

   public void revive() {
   }

   public void setPosition(int var1, int var2, int var3) {
   }

   public void appendToRender() {
      if (this.mainMesh_ != null) {
         GlobalStatus.renderer.drawNodeInVF(this.mainMesh_);
      } else {
         GlobalStatus.renderer.drawNodeInVF(this.geometry);
      }
   }
}
