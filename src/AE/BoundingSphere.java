package AE;

public final class BoundingSphere extends BoundingVolume {
   private int r;
   private int x;
   private int y;
   private int z;

   public BoundingSphere(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, 0, 0, 0);
      this.r = var7;
      this.setPosition(var1, var2, var3);
   }

   public final boolean outerCollide_(int var1, int var2, int var3) {
      return this.isPointInBounding(var1, var2, var3) ? true : super.outerCollide_(var1, var2, var3);
   }

   public final boolean isPointInBounding(int var1, int var2, int var3) {
      return var1 - this.x < this.r && var1 - this.x > -this.r && var2 - this.y < this.r && var2 - this.y > -this.r && var3 - this.z < this.r && var3 - this.z > -this.r;
   }

   public final void setPosition(int var1, int var2, int var3) {
      super.setPosition(var1, var2, var3);
      this.x = var1 + this.offsetX;
      this.y = var2 + this.offsetY;
      this.z = var3 + this.offsetZ;
   }
}
