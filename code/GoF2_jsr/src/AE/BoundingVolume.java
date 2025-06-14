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

	public BoundingVolume(final int var1, final int var2, final int var3, final int var4, final int var5, final int var6) {
		this.posX = var1;
		this.posY = var2;
		this.posZ = var3;
		this.offsetX = var4;
		this.offsetY = var5;
		this.offsetZ = var6;
	}

	public final AEVector3D getProjectionVector(final AEVector3D var1) {
		temp.set(var1.x - this.posX, var1.y - this.posY, var1.z - this.posZ);
		temp.normalize();
		return temp;
	}

	public void setPosition(final int var1, final int var2, final int var3) {
		this.posX = var1;
		this.posY = var2;
		this.posZ = var3;
	}

	public boolean outerCollide_(final int var1, final int var2, final int var3) {
		return false;
	}

	public boolean isPointInBounding(final int var1, final int var2, final int var3) {
		return false;
	}
}
