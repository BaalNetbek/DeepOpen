package AE;

public final class BoundingAAB extends BoundingVolume {
	private final int halfSizeX;
	private final int halfSizeY;
	private final int halfSizeZ;

	/**
	 *
	 * @param var1 position x coordinate
	 * @param var2 position y coordinate
	 * @param var3 position z coordinate
	 * @param var4 center offset x
	 * @param var5 center offset y
	 * @param var6 center offset z
	 * @param var7 x dimension length
	 * @param var8 y dimension length
	 * @param var9 z dimension length
	 */
	public BoundingAAB(final int var1, final int var2, final int var3, final int var4, final int var5, final int var6, final int var7, final int var8, final int var9) {
		super(var1, var2, var3, var4, var5, var6);
		this.halfSizeX = var7 >> 1;
		this.halfSizeY = var8 >> 1;
		this.halfSizeZ = var9 >> 1;
	}

	public final boolean outerCollide_(final int var1, final int var2, final int var3) {
		return isPointInBounding(var1, var2, var3) ? super.outerCollide_(var1, var2, var3) : false;
	}

	public final boolean isPointInBounding(final int var1, final int var2, final int var3) {
		return var1 > this.posX + this.offsetX - (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var1 < this.posX + this.offsetX + (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX) && var2 > this.posY + this.offsetY - (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var2 < this.posY + this.offsetY + (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY) && var3 > this.posZ + this.offsetZ - (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ) && var3 < this.posZ + this.offsetZ + (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ);
	}

	public final void setPosition(final int var1, final int var2, final int var3) {
		super.setPosition(var1, var2, var3);
	}
}
