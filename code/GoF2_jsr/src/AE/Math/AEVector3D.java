package AE.Math;

public final class AEVector3D {
	public int x;
	public int y;
	public int z;

	public AEVector3D(final int var1, final int var2, final int var3) {
		this.x = var1;
		this.y = var2;
		this.z = var3;
	}

	public AEVector3D() {
		this.x = this.y = this.z = 0;
	}

	public AEVector3D(final AEVector3D var1) {
		this.x = var1.x;
		this.y = var1.y;
		this.z = var1.z;
	}

	public final void set(final AEVector3D var1) {
		this.x = var1.x;
		this.y = var1.y;
		this.z = var1.z;
	}

	public final void set(final int var1, final int var2, final int var3) {
		this.x = var1;
		this.y = var2;
		this.z = var3;
	}

	public final int dot(final AEVector3D var1) {
		return (this.x * var1.x >> 12) + (this.y * var1.y >> 12) + (this.z * var1.z >> 12);
	}

	public final int dotPrecise(final AEVector3D var1) {
		return (int)(((long)this.x * (long)var1.x >> 12) + ((long)this.y * (long)var1.y >> 12) + ((long)this.z * (long)var1.z >> 12));
	}

	public final AEVector3D crossProduct(final AEVector3D var1, final AEVector3D var2) {
		var2.x = (this.y * var1.z >> 12) - (this.z * var1.y >> 12);
		var2.y = (this.z * var1.x >> 12) - (this.x * var1.z >> 12);
		var2.z = (this.x * var1.y >> 12) - (this.y * var1.x >> 12);
		return var2;
	}

	public final AEVector3D add(final AEVector3D var1, final AEVector3D var2) {
		var2.x = this.x + var1.x;
		var2.y = this.y + var1.y;
		var2.z = this.z + var1.z;
		return var2;
	}

	public final void add(final AEVector3D var1) {
		this.x += var1.x;
		this.y += var1.y;
		this.z += var1.z;
	}

	public final AEVector3D subtract(final AEVector3D var1, final AEVector3D var2) {
		var2.x = this.x - var1.x;
		var2.y = this.y - var1.y;
		var2.z = this.z - var1.z;
		return var2;
	}

	public final void subtract(final AEVector3D var1) {
		this.x -= var1.x;
		this.y -= var1.y;
		this.z -= var1.z;
	}

	public final void scale(final int var1) {
		this.x = (int)((long)var1 * (long)this.x >> 12);
		this.y = (int)((long)var1 * (long)this.y >> 12);
		this.z = (int)((long)var1 * (long)this.z >> 12);
	}

	public final void normalize() {
		final int var3 = AEMath.invSqrt((int)(((long)this.x * (long)this.x >> 12) + ((long)this.y * (long)this.y >> 12) + ((long)this.z * (long)this.z >> 12)));
		this.x = this.x * var3 >> 12;
		this.y = this.y * var3 >> 12;
		this.z = this.z * var3 >> 12;
	}

	public final int getLength() {
		return AEMath.sqrt(((long)this.x * (long)this.x >> 12) + ((long)this.y * (long)this.y >> 12) + ((long)this.z * (long)this.z >> 12));
	}

	public final String toString() {
		return "AEVector3D | " + this.x + ",\t" + this.y + ",\t" + this.z;
	}
}
