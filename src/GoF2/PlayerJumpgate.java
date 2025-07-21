package GoF2;

import AE.AbstractMesh;
import AE.BoundingSphere;
import AE.BoundingVolume;

public final class PlayerJumpgate extends PlayerStaticFar {
   private boolean animationInit;

   public PlayerJumpgate(int var1, AbstractMesh var2, int var3, int var4, int var5, boolean var6) {
      super(15, var2, var3, var4, var5);
      this.setVisible(var6);
      if (var6) {
         this.boundingBoxes = new BoundingVolume[1];
         this.boundingBoxes[0] = new BoundingSphere(var3, var4, var5, 0, 0, 0, 15000);
         this.mainMesh_.setAnimationSpeed(100);
         this.mainMesh_.setAnimationRangeInTime(1, 20);
         this.mainMesh_.setAnimationMode((byte)2);
      }

      var2.setRotation(-1024, 0, 0);
      this.animationInit = false;
   }

   public final void activate() {
      if (!this.animationInit) {
         this.mainMesh_.setAnimationSpeed(50);
         this.mainMesh_.setAnimationRangeInTime(21, 79);
         this.mainMesh_.setAnimationMode((byte)1);
         this.animationInit = true;
      }

   }

   public final void setPosition(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
      this.mainMesh_.moveTo(var1, var2, var3);
   }

   public final void update(long var1) {
      if (this.mainMesh_.getCurrentAnimFrame() == 79) {
         this.mainMesh_.setAnimationSpeed(100);
         this.mainMesh_.setAnimationRangeInTime(38, 60);
         this.mainMesh_.setAnimationMode((byte)2);
      }

      super.update(var1);
   }
}
