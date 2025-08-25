package AE;

public final class BoundingAAB extends BoundingVolume {
	public static final int OFFSET_X = 0; 
	public static final int OFFSET_Y = 1;
	public static final int OFFSET_Z = 2;
	public static final int DIMENSION_X = 3; 
	public static final int DIMENSION_Y = 4; 
	public static final int DIMENSION_Z = 5; 
	
	private final int halfSizeX;
	private final int halfSizeY;
	private final int halfSizeZ;

	/**
	 *
	 * @param x position x coordinate
	 * @param y position y coordinate
	 * @param z position z coordinate
	 * @param off_x center offset x
	 * @param off_y center offset y
	 * @param off_z center offset z
	 * @param dim_x x dimension length
	 * @param dim_y y dimension length
	 * @param dim_z z dimension length
	 */
	public BoundingAAB(final int x, final int y, final int z, final int off_x, final int off_y, final int off_z, final int dim_x, final int dim_y, final int dim_z) {
		super(x, y, z, off_x, off_y, off_z);
		this.halfSizeX = dim_x >> 1;
		this.halfSizeY = dim_y >> 1;
		this.halfSizeZ = dim_z >> 1;
	}

	public final boolean outerCollide_(final int x, final int y, final int z) {
		return isPointInBounding(x, y, z) ? super.outerCollide_(x, y, z) : false;
	}

	public final boolean isPointInBounding(final int x, final int y, final int z) {
		return x > this.posX + this.offsetX - (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX)
		      && x < this.posX + this.offsetX + (this.halfSizeX < 0 ? -this.halfSizeX : this.halfSizeX)
		      && y > this.posY + this.offsetY - (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY)
		      && y < this.posY + this.offsetY + (this.halfSizeY < 0 ? -this.halfSizeY : this.halfSizeY)
		      && z > this.posZ + this.offsetZ - (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ)
		      && z < this.posZ + this.offsetZ + (this.halfSizeZ < 0 ? -this.halfSizeZ : this.halfSizeZ);
	}

	public final void setPosition(final int x, final int y, final int z) {
		super.setPosition(x, y, z);
	}
}
