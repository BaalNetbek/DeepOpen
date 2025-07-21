package GoF2;

import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEVector3D;

public class PlayerStatic extends KIPlayer {
   protected static AEVector3D virtDistToCam_ = new AEVector3D();
   public int posX;
   public int posY;
   public int posZ;

   public PlayerStatic(int var1, AbstractMesh var2, int var3, int var4, int var5) {
      super(var1, -1, new Player(2000.0F, 0, 0, 0, 0), var2, var3, var4, var5);
      this.posX = var3;
      this.posY = var4;
      this.posZ = var5;
   }

   public void update(long var1) {
   }

   public AEVector3D getPosition(AEVector3D var1) {
      if (this.mainMesh_ == null) {
         var1.set(this.posX, this.posY, this.posZ);
      } else {
         var1.set(this.mainMesh_.getLocalPos(this.tempVector_));
      }

      return var1;
   }

   public void appendToRender() {
      GlobalStatus.renderer.drawNodeInVF(this.mainMesh_);
   }

   public boolean outerCollide(int var1, int var2, int var3) {
      return false;
   }
}
