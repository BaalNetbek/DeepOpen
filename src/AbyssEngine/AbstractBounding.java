package AbyssEngine;

public abstract class AbstractBounding {
   private static AEVector3D var_147 = new AEVector3D();
   protected int posX;
   protected int posY;
   protected int posZ;
   protected int offsetX;
   protected int offsetY;
   protected int offsetZ;

   public AbstractBounding(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
      this.offsetX = var4;
      this.offsetY = var5;
      this.offsetZ = var6;
   }

   public final AEVector3D sub_3d(AEVector3D var1) {
      var_147.set(var1.x - this.posX, var1.y - this.posY, var1.z - this.posZ);
      var_147.normalize();
      return var_147;
   }

   public void sub_e2(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
   }

   public boolean sub_4f(int var1, int var2, int var3) {
      return false;
   }

   public boolean sub_a0(int var1, int var2, int var3) {
      return false;
   }
}
