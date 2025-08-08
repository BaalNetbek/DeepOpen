package AE;

import AE.Math.AEVector3D;

public abstract class BoundingVolume {
	private static AEVector3D temp = new AEVector3D();
	protected int posX;
	protected int posY;
	protected int posZ;
	protected int offsetX;
	protected int offsetY;
	protected int offsetZ;

	public BoundingVolume(final int x, final int y, final int z, final int off_x, final int off_y, final int off_z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.offsetX = off_x;
		this.offsetY = off_y;
		this.offsetZ = off_z;
	}

	public final AEVector3D getProjectionVector(final AEVector3D vec) {
		temp.set(vec.x - this.posX, vec.y - this.posY, vec.z - this.posZ);
		temp.normalize();
		return temp;
	}

	public void setPosition(final int x, final int y, final int z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}

	public boolean outerCollide_(final int x, final int y, final int z) {
		return false;
	}

	public boolean isPointInBounding(final int x, final int y, final int z) {
		return false;
	}
}
