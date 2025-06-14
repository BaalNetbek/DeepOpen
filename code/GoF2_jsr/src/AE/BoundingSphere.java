package AE;

public final class BoundingSphere extends BoundingVolume {
	private final int r;
	private int x;
	private int y;
	private int z;

	public BoundingSphere(final int var1, final int var2, final int var3, final int var4, final int var5, final int var6, final int var7) {
		super(var1, var2, var3, 0, 0, 0);
		this.r = var7;
		setPosition(var1, var2, var3);
	}

	public final boolean outerCollide_(final int var1, final int var2, final int var3) {
		return isPointInBounding(var1, var2, var3) ? true : super.outerCollide_(var1, var2, var3);
	}

	public final boolean isPointInBounding(final int var1, final int var2, final int var3) {
		return var1 - this.x < this.r && var1 - this.x > -this.r && var2 - this.y < this.r && var2 - this.y > -this.r && var3 - this.z < this.r && var3 - this.z > -this.r;
	}

	public final void setPosition(final int var1, final int var2, final int var3) {
		super.setPosition(var1, var2, var3);
		this.x = var1 + this.offsetX;
		this.y = var2 + this.offsetY;
		this.z = var3 + this.offsetZ;
	}
}
