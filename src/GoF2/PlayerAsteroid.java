package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class PlayerAsteroid extends KIPlayer {
   private static AEVector3D tempVector1_ = new AEVector3D();
   private static AEVector3D tempVector2_ = new AEVector3D();
   private int frametime;
   private int posX;
   private int posY;
   private int posZ;
   private boolean clampDisabled;
   private int baseScaleX;
   private int baseScaleY;
   private int baseScaleZ;
   private int passedTime;
   private static AEVector3D center_;
   private boolean rotationEnabled;
   private Matrix playerTransform_ = new Matrix();
   private int sizeCoef_;
   private int tier;
   private AbstractMesh explosionMesh;
   public boolean clampedByDistance;
   public int oreItemId;

   public PlayerAsteroid(int var1, AbstractMesh var2, int var3, boolean var4, int var5, int var6, int var7) {
      super(var1, -1, new Player(1500.0F, 30, 0, 0, 0), var2, var5, var6, var7);
      this.player.setKIPlayer(this);
      this.oreItemId = var3;
      this.posX = var5;
      this.posY = var6;
      this.posZ = var7;
      tempVector2_.set(var5, var6, var7);
      this.clampDisabled = var4;
      this.clampedByDistance = false;
      this.baseScaleX = 1024 + GlobalStatus.random.nextInt(2448);
      this.baseScaleY = 1024 + GlobalStatus.random.nextInt(2448);
      this.baseScaleZ = 1024 + GlobalStatus.random.nextInt(2448);
      this.sizeCoef_ = (int)((float)((this.baseScaleX + this.baseScaleY + this.baseScaleZ) / 3) / 3072.0F * 100.0F);
      this.player.setMaxHP(30 + (int)((float)this.sizeCoef_ / 100.0F * 100.0F));
      this.tier = AEMath.min(7, 2 + (int)((float)(this.sizeCoef_ + 15) / 100.0F * 5.0F));
      this.tempVector_.set(-4096 + GlobalStatus.random.nextInt(8192), -4096 + GlobalStatus.random.nextInt(8192), -4096 + GlobalStatus.random.nextInt(8192));
      this.tempVector_.normalize();
      this.mainMesh_.getToParentTransform().setOrientation(this.tempVector_);
      this.mainMesh_.moveTo(tempVector2_);
      this.mainMesh_.updateTransform(true);
      this.mainMesh_.setAnimationRangeInTime(var3 - 154, var3 - 154);
      this.mainMesh_.disableAnimation();
      this.rotationEnabled = true;
      this.isAsteroid = true;
      this.hasCargo = true;
      this.state = 0;
   }

   public final void sub_22() {
      super.OnRelease();
      this.explosionMesh = null;
   }

   public final int getMass_SizeCoef__() {
      return this.sizeCoef_;
   }

   public final int getQuality() {
      return this.tier;
   }

   public final int getQualityFrameIndex() {
      return 7 - this.tier;
   }

   public final void setAsteroidCenter(AEVector3D var1) {
      center_ = var1;
      if (this.clampDisabled) {
         this.scaleOnDistanceClamp_();
      }

   }

   public final void setRotationEnabled(boolean var1) {
      this.rotationEnabled = var1;
   }

   public final void update(long var1) {
      this.frametime = (int)var1;
      if (!this.player.isActive()) {
         this.clampedByDistance = false;
      } else {
         this.passedTime += this.frametime;
         int var6;
         int var7;
         if (this.player.getHitpoints() <= 0 && this.state == 0) {
            tempVector2_ = this.mainMesh_.getPosition(tempVector2_);
            if (this.explosionMesh == null) {
               this.explosionMesh = AEResourceManager.getGeometryResource(6782);
               this.explosionMesh.setRenderLayer(2);
               this.explosionMesh.setAnimationSpeed(100);
               this.player.setKIPlayer(this);
            }

            this.explosionMesh.setTransform(this.mainMesh_.getToParentTransform());
            this.explosionMesh.setAnimationMode((byte)1);
            this.player.transform = this.explosionMesh.getToParentTransform();
            tempVector2_.subtract(this.level.getPlayer().getPosition());
            var7 = AEMath.min(40000, tempVector2_.getLength());
            float var5 = 1.0F - (float)var7 / 40000.0F;
            var6 = GlobalStatus.soundManager.getMusicVolume();
            GlobalStatus.soundManager.playSfx(5, (int)((float)var6 * var5));
            this.explosion.start(this.mainMesh_.getPosX(), this.mainMesh_.getPosY(), this.mainMesh_.getPosZ());
            this.state = 3;
            this.level.asteroidDied();
            this.passedTime = 0;
            ++Status.asteroidsDestroyed;
            if (this.hasCargo && (this.tier == 7 && GlobalStatus.random.nextInt(100) < 40 || this.tier < 7 && GlobalStatus.random.nextInt(100) < 20) && this.oreItemId != 164) {
               this.cargo = new int[2];
               this.cargo[0] = this.oreItemId + (this.tier == 7 ? 11 : 0);
               this.cargo[1] = this.tier == 7 ? 1 : 1 + GlobalStatus.random.nextInt(3);
               this.createCrate(1);
            } else {
               this.hasCargo = false;
            }
         } else {
            if (this.state == 4) {
               this.setActive(false);
            } else if (this.state != 3 && this.rotationEnabled) {
               this.mainMesh_.roll((int)var1 >> 2);
            }

            float var3;
            if ((var3 = this.player.getBombForce()) > 0.0F) {
               this.tempVector_.set(this.player.getHitVector());
               this.tempVector_.scale((int)(1024.0F * var3 * (1.0F - (float)AEMath.min(60, this.sizeCoef_) / 100.0F)));
               this.mainMesh_.translate(this.tempVector_);
               this.posX += this.tempVector_.x;
               this.posY += this.tempVector_.y;
               this.posZ += this.tempVector_.z;
               this.mainMesh_.rotateEuler((int)var1 >> 1, (int)var1 >> 1, (int)var1 >> 1);
               if ((var3 *= 0.98F) < 0.05F) {
                  var3 = 0.0F;
               }

               this.player.setBombForce(var3);
            }

            this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
            this.position.set(this.posX, this.posY, this.posZ);
            this.position.subtract(this.tempVector_, tempVector1_);
            int var4 = tempVector1_.getLength();
            
            if (!this.clampDisabled) {
               if (var4 > 188000 ) {
                  tempVector1_.normalize();
                  tempVector1_.scale(188000);
                  tempVector1_.add(this.tempVector_);
                  this.mainMesh_.moveTo(tempVector1_);
                  float var2;
                  var4 = (int)((var2 = 188000.0F / (float)var4) * (float)this.baseScaleX);
                  var7 = (int)(var2 * (float)this.baseScaleY);
                  var6 = (int)(var2 * (float)this.baseScaleZ);
                  this.mainMesh_.setScale(var4, var7, var6);
                  this.clampedByDistance = true;
               } else {
                  this.mainMesh_.setScale(this.baseScaleX, this.baseScaleY, this.baseScaleZ);
                  this.mainMesh_.moveTo(this.posX, this.posY, this.posZ);
                  this.clampedByDistance = false;
               }
            } /*else if (var4 > 35000 && this.passedTime > 10000) {
               var6 = GameStatus.random.nextInt(20000) - 10000;
               var4 = GameStatus.random.nextInt(20000) - 10000;
               var7 = GameStatus.random.nextInt(2000) + 30000;
               this.passedTime = 0;
               var_1c5.set(var6, var4, var7);
               if (this.level.getPlayerEgo() != null) {
                  this.var_80b = this.level.getPlayerEgo().var_50e.sub_8a0();
               } else {
                  this.var_80b.identity();
               }

               this.positon = this.var_80b.sub_a19(var_1c5, this.positon);
               this.var_25d.sub_1f3(this.positon);
               this.var_312 = this.positon.x;
               this.var_356 = this.positon.y;
               this.var_3fe = this.positon.z;
               this.sub_34(true);
               this.sub_296();
            }*/
		
            if (this.explosionMesh != null) {
               this.explosionMesh.setTransform(this.mainMesh_.getToParentTransform());
            }

         }
      }
   }

   private void scaleOnDistanceClamp_() {
      this.tempVector_.set(this.posX, this.posY, this.posZ);
      this.position.set(center_);
      this.position.subtract(this.tempVector_, tempVector1_);
      float var1 = (float)AEMath.max(0, 100000 - tempVector1_.getLength()) / 100000.0F;
      int var2 = (int)((float)this.baseScaleX * var1);
      int var3 = (int)((float)this.baseScaleY * var1);
      int var4 = (int)((float)this.baseScaleZ * var1);
      if (var2 + var3 + var4 < 256) {
         this.sizeCoef_ = 0;
         this.tier = 0;
         this.setActive(false);
      } else {
         this.mainMesh_.setScale(var2, var3, var4);
         this.sizeCoef_ = (int)((float)((var2 + var3 + var4) / 3) / 3072.0F * 100.0F);
         this.tier = AEMath.min(7, 2 + (int)((float)(this.sizeCoef_ + 15) / 100.0F * 5.0F));
      }
   }

   public final void appendToRender() {
      if (this.tier > 0) {
         if (this.waste != null) {
            GlobalStatus.renderer.drawNodeInVF(this.waste);
         }

         if (this.state == 3) {
            GlobalStatus.renderer.drawNodeInVF(this.explosionMesh);
            if (this.passedTime > 3000) {
               this.state = 4;
               return;
            }
         } else if (this.state == 0) {
            GlobalStatus.renderer.drawNodeInVF(this.mainMesh_);
         }
      }

   }

   public final boolean outerCollide(AEVector3D var1) {
      return this.outerCollide(var1.x, var1.y, var1.z);
   }

   public final AEVector3D getProjectionVector_(AEVector3D var1) {
      (tempVector2_ = this.mainMesh_.getPosition(tempVector2_)).subtract(var1);
      var1.set(tempVector2_);
      var1.normalize();
      return var1;
   }

   public final boolean outerCollide(int var1, int var2, int var3) {
      return (float)(var1 - this.posX) < this.player.radius && (float)(var1 - this.posX) > -this.player.radius && (float)(var2 - this.posY) < this.player.radius && (float)(var2 - this.posY) > -this.player.radius && (float)(var3 - this.posZ) < this.player.radius && (float)(var3 - this.posZ) > -this.player.radius;
   }
}
