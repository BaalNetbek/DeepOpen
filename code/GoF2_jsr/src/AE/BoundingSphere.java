package AE;

public final class BoundingSphere extends BoundingVolume {
	private final int r;
	private int x;
	private int y;
	private int z;

	public BoundingSphere(final int x, final int y, final int z, final int off_x, final int off_y, final int off_z, final int r) {
		super(x, y, z, off_x, off_y, off_z);
		this.r = r;
		setPosition(x, y, z);
	}

	public final boolean outerCollide_(final int x, final int y, final int z) {
		return isPointInBounding(x, y, z) ? true : super.outerCollide_(x, y, z);
	}

	public final boolean isPointInBounding(final int x, final int y, final int z) {
		return x - this.x < this.r && x - this.x > -this.r 
				&& y - this.y < this.r && y - this.y > -this.r
		      && z - this.z < this.r && z - this.z > -this.r;
	}

	public final void setPosition(final int x, final int y, final int z) {
		super.setPosition(x, y, z);
		this.x = x + this.offsetX;
		this.y = y + this.offsetY;
		this.z = z + this.offsetZ;
	}
}
