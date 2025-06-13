package AE;

public final class BoundingAAB extends BoundingVolume {
   private int halfSizeX;
   private int halfSizeY;
   private int halfSizeZ;
   
   /**
    * 
    * @param var1 position x coordinate
    * @param var2 position y coordinate
    * @param var3 position z coordinate
    * @param var4 center offset x
    * @param var5 center offset y
    * @param var6 center offset z
    * @param var7 x dimension lenght
    * @param var8 y dimension lenght
    * @param var9 z dimension lenght
    */
   public BoundingAAB(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      super(var1, var2, var3, var4, var5, var6);
      this.halfSizeX = var7 >> 1;
      this.halfSizeY = var8 >> 1;
      this.halfSizeZ = var9 >> 1;
   }

   public final boolean outerCollide_(int var1, int var2, int var3) {
      return this.isPointInBounding(var1, var2, var3) ? super.outerCollide_(var1, var2, var3) : false;
   }

   public final boolean isPointInBounding(int var1, int var2, int var3) {
      return var1 > this.posX + this.offsetX - (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var1 < this.posX + this.offsetX + (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var2 > this.posY + this.offsetY - (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var2 < this.posY + this.offsetY + (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var3 > this.posZ + this.offsetZ - (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ) && var3 < this.posZ + this.offsetZ + (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ);
   }

   public final void setPosition(int var1, int var2, int var3) {
      super.setPosition(var1, var2, var3);
   }
}
