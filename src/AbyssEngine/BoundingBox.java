package AbyssEngine;

public final class BoundingBox extends AbstractBounding {
   private int halfSizeX;
   private int halfSizeY;
   private int halfSizeZ;

   public BoundingBox(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      super(var1, var2, var3, var4, var5, var6);
      this.halfSizeX = var7 >> 1;
      this.halfSizeY = var8 >> 1;
      this.halfSizeZ = var9 >> 1;
   }

   public final boolean sub_4f(int var1, int var2, int var3) {
      return this.sub_a0(var1, var2, var3) ? super.sub_4f(var1, var2, var3) : false;
   }

   public final boolean sub_a0(int var1, int var2, int var3) {
      return var1 > this.posX + this.offsetX - (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var1 < this.posX + this.offsetX + (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var2 > this.posY + this.offsetY - (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var2 < this.posY + this.offsetY + (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var3 > this.posZ + this.offsetZ - (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ) && var3 < this.posZ + this.offsetZ + (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ);
   }

   public final void sub_e2(int var1, int var2, int var3) {
      super.sub_e2(var1, var2, var3);
   }
}
