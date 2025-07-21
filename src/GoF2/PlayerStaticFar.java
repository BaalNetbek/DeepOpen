package GoF2;

import AE.AbstractMesh;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEVector3D;

public class PlayerStaticFar extends PlayerStatic {
   protected BoundingVolume[] boundingBoxes;

   public PlayerStaticFar(int var1, AbstractMesh var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
      this.targetX = var3;
      this.targetY = var4;
      this.targetZ = var5;
      if (var2 != null) {
         short var6 = 7500;
         this.player.radius = (float)var6;
         this.mainMesh_.setRenderLayer(2);
      }
   }

   public void appendToRender() {
      super.appendToRender();
   }

   public void update(long var1) {
      if (this.mainMesh_ != null) {
         this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
         this.position.set(this.posX, this.posY, this.posZ);
         this.position.subtract(this.tempVector_, virtDistToCam_);
         int var3;
         if ((var3 = virtDistToCam_.getLength()) > 188000) {
            virtDistToCam_.normalize();
            virtDistToCam_.scale(188000);
            virtDistToCam_.add(this.tempVector_);
            this.mainMesh_.moveTo(virtDistToCam_);
            var3 = (int)(188000.0F / (float)var3 * 4096.0F);
            this.mainMesh_.setScale(var3, var3, var3);
         } else {
            this.mainMesh_.setScale(4096, 4096, 4096);
            this.mainMesh_.moveTo(this.posX, this.posY, this.posZ);
         }
      }
   }

   public boolean outerCollide(int var1, int var2, int var3) {
      return (float)(var1 - this.posX) < this.player.radius && (float)(var1 - this.posX) > -this.player.radius && (float)(var2 - this.posY) < this.player.radius && (float)(var2 - this.posY) > -this.player.radius && (float)(var3 - this.posZ) < this.player.radius && (float)(var3 - this.posZ) > -this.player.radius;
   }

   public final AEVector3D getTargetPos_(AEVector3D var1) {
      var1.x = this.targetX;
      var1.y = this.targetY;
      var1.z = this.targetZ;
      return var1;
   }

   public boolean outerCollide(AEVector3D var1) {
      return this.outerCollide(var1.x, var1.y, var1.z);
   }

   public AEVector3D getProjectionVector_(AEVector3D var1) {
      return this.boundingBoxes != null ? this.boundingBoxes[0].getProjectionVector(var1) : null;
   }
}
