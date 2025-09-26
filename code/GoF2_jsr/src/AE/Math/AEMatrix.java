package AE.Math;

public final class AEMatrix {
    private short rotationOrder;
    private int scaleX;
    private int scaleY;
    private int scaleZ;
    private int rightX;
    private int rightY;
    private int rightZ;
    private int upX;
    private int upY;
    private int upZ;
    private int dirX;
    private int dirY;
    private int dirZ;
    private int positionX;
    private int positionY;
    private int positionZ;
    private int eulerX;
    private int eulerY;
    private int eulerZ;
    private boolean isDirty;
    private static AEVector3D temp = new AEVector3D();

    public AEMatrix() {
        identity();
        this.rotationOrder = 0;
    }

    public AEMatrix(final AEMatrix var1) {
        this.scaleX = var1.scaleX;
        this.scaleY = var1.scaleY;
        this.scaleZ = var1.scaleZ;
        this.rightX = var1.rightX;
        this.rightY = var1.rightY;
        this.rightZ = var1.rightZ;
        this.upX = var1.upX;
        this.upY = var1.upY;
        this.upZ = var1.upZ;
        this.dirX = var1.dirX;
        this.dirY = var1.dirY;
        this.dirZ = var1.dirZ;
        this.positionX = var1.positionX;
        this.positionY = var1.positionY;
        this.positionZ = var1.positionZ;
        this.eulerX = var1.eulerX;
        this.eulerY = var1.eulerY;
        this.eulerZ = var1.eulerZ;
        this.isDirty = var1.isDirty;
    }

    public final void identity() {
        this.scaleX = this.scaleY = this.scaleZ = AEMath.Q_1;
        this.rightX = AEMath.Q_1;
        this.upX = 0;
        this.dirX = 0;
        this.rightY = 0;
        this.upY = AEMath.Q_1;
        this.dirY = 0;
        this.rightZ = 0;
        this.upZ = 0;
        this.dirZ = AEMath.Q_1;
        this.positionX = this.positionY = this.positionZ = 0;
        this.eulerX = this.eulerY = this.eulerZ = 0;
        this.isDirty = false;
    }

    public final void set(final AEMatrix m) {
        this.scaleX = m.scaleX;
        this.scaleY = m.scaleY;
        this.scaleZ = m.scaleZ;
        this.rightX = m.rightX;
        this.rightY = m.rightY;
        this.rightZ = m.rightZ;
        this.upX = m.upX;
        this.upY = m.upY;
        this.upZ = m.upZ;
        this.dirX = m.dirX;
        this.dirY = m.dirY;
        this.dirZ = m.dirZ;
        this.positionX = m.positionX;
        this.positionY = m.positionY;
        this.positionZ = m.positionZ;
        this.eulerX = m.eulerX;
        this.eulerY = m.eulerY;
        this.eulerZ = m.eulerZ;
        this.isDirty = m.isDirty;
    }

    public final void multiply(final AEMatrix m) {
        final int orx = this.rightX;
        final int oux = this.upX;
        final int ory = this.rightY;
        final int ouy = this.upY;
        final int orz = this.rightZ;
        final int ouz = this.upZ;
        this.positionX += this.scaleX * ((this.rightX * m.positionX >> AEMath.Q) + (this.upX * m.positionY >> AEMath.Q) + (this.dirX * m.positionZ >> AEMath.Q)) >> AEMath.Q;
        this.positionY += this.scaleY * ((this.rightY * m.positionX >> AEMath.Q) + (this.upY * m.positionY >> AEMath.Q) + (this.dirY * m.positionZ >> AEMath.Q)) >> AEMath.Q;
        this.positionZ += this.scaleZ * ((this.rightZ * m.positionX >> AEMath.Q) + (this.upZ * m.positionY >> AEMath.Q) + (this.dirZ * m.positionZ >> AEMath.Q)) >> AEMath.Q;
        this.scaleX = this.scaleX * m.scaleX >> AEMath.Q;
        this.scaleY = this.scaleY * m.scaleY >> AEMath.Q;
        this.scaleZ = this.scaleZ * m.scaleZ >> AEMath.Q;
        this.rightX = (orx * m.rightX >> AEMath.Q) + (oux * m.rightY >> AEMath.Q) + (this.dirX * m.rightZ >> AEMath.Q);
        this.rightY = (ory * m.rightX >> AEMath.Q) + (ouy * m.rightY >> AEMath.Q) + (this.dirY * m.rightZ >> AEMath.Q);
        this.rightZ = (orz * m.rightX >> AEMath.Q) + (ouz * m.rightY >> AEMath.Q) + (this.dirZ * m.rightZ >> AEMath.Q);
        this.upX = (orx * m.upX >> AEMath.Q) + (oux * m.upY >> AEMath.Q) + (this.dirX * m.upZ >> AEMath.Q);
        this.upY = (ory * m.upX >> AEMath.Q) + (ouy * m.upY >> AEMath.Q) + (this.dirY * m.upZ >> AEMath.Q);
        this.upZ = (orz * m.upX >> AEMath.Q) + (ouz * m.upY >> AEMath.Q) + (this.dirZ * m.upZ >> AEMath.Q);
        this.dirX = (orx * m.dirX >> AEMath.Q) + (oux * m.dirY >> AEMath.Q) + (this.dirX * m.dirZ >> AEMath.Q);
        this.dirY = (ory * m.dirX >> AEMath.Q) + (ouy * m.dirY >> AEMath.Q) + (this.dirY * m.dirZ >> AEMath.Q);
        this.dirZ = (orz * m.dirX >> AEMath.Q) + (ouz * m.dirY >> AEMath.Q) + (this.dirZ * m.dirZ >> AEMath.Q);
        this.isDirty = true;
    }

    public final AEMatrix multiplyTo(final AEMatrix m, final AEMatrix result) {
        result.positionX = this.positionX + (this.scaleX * ((this.rightX * m.positionX >> AEMath.Q) + (this.upX * m.positionY >> AEMath.Q) + (this.dirX * m.positionZ >> AEMath.Q)) >> AEMath.Q);
        result.positionY = this.positionY + (this.scaleY * ((this.rightY * m.positionX >> AEMath.Q) + (this.upY * m.positionY >> AEMath.Q) + (this.dirY * m.positionZ >> AEMath.Q)) >> AEMath.Q);
        result.positionZ = this.positionZ + (this.scaleZ * ((this.rightZ * m.positionX >> AEMath.Q) + (this.upZ * m.positionY >> AEMath.Q) + (this.dirZ * m.positionZ >> AEMath.Q)) >> AEMath.Q);
        result.scaleX = this.scaleX * m.scaleX >> AEMath.Q;
        result.scaleY = this.scaleY * m.scaleY >> AEMath.Q;
        result.scaleZ = this.scaleZ * m.scaleZ >> AEMath.Q;
        result.rightX = (this.rightX * m.rightX >> AEMath.Q) + (this.upX * m.rightY >> AEMath.Q) + (this.dirX * m.rightZ >> AEMath.Q);
        result.rightY = (this.rightY * m.rightX >> AEMath.Q) + (this.upY * m.rightY >> AEMath.Q) + (this.dirY * m.rightZ >> AEMath.Q);
        result.rightZ = (this.rightZ * m.rightX >> AEMath.Q) + (this.upZ * m.rightY >> AEMath.Q) + (this.dirZ * m.rightZ >> AEMath.Q);
        result.upX = (this.rightX * m.upX >> AEMath.Q) + (this.upX * m.upY >> AEMath.Q) + (this.dirX * m.upZ >> AEMath.Q);
        result.upY = (this.rightY * m.upX >> AEMath.Q) + (this.upY * m.upY >> AEMath.Q) + (this.dirY * m.upZ >> AEMath.Q);
        result.upZ = (this.rightZ * m.upX >> AEMath.Q) + (this.upZ * m.upY >> AEMath.Q) + (this.dirZ * m.upZ >> AEMath.Q);
        result.dirX = (this.rightX * m.dirX >> AEMath.Q) + (this.upX * m.dirY >> AEMath.Q) + (this.dirX * m.dirZ >> AEMath.Q);
        result.dirY = (this.rightY * m.dirX >> AEMath.Q) + (this.upY * m.dirY >> AEMath.Q) + (this.dirY * m.dirZ >> AEMath.Q);
        result.dirZ = (this.rightZ * m.dirX >> AEMath.Q) + (this.upZ * m.dirY >> AEMath.Q) + (this.dirZ * m.dirZ >> AEMath.Q);
        result.isDirty = true;
        return result;
    }

    public final void translate(final int x, final int y, final int z) {
        this.positionX += x;
        this.positionY += y;
        this.positionZ += z;
    }

    public final void translate(final AEVector3D vec) {
        this.positionX += vec.x;
        this.positionY += vec.y;
        this.positionZ += vec.z;
    }

    public final void translateTo(final int x, final int y, final int z) {
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
    }

    public final void translateTo(final AEVector3D vec) {
        this.positionX = vec.x;
        this.positionY = vec.y;
        this.positionZ = vec.z;
    }

    public final void translateForward(final int dist) {
        this.positionX += this.dirX * dist >> AEMath.Q;
        this.positionY += this.dirY * dist >> AEMath.Q;
        this.positionZ += this.dirZ * dist >> AEMath.Q;
    }
    /**
     * Position is passed both in argument and return value.
     * @param v vector that position is copied to
     * @return parameter v holding position
     */
    public final AEVector3D getPosition(final AEVector3D v) {
        v.x = this.positionX;
        v.y = this.positionY;
        v.z = this.positionZ;
        return v;
    }

    public final AEVector3D getPosition() {
        return new AEVector3D(this.positionX, this.positionY, this.positionZ);
    }

    public final int getPositionX() {
        return this.positionX;
    }

    public final int getPositionY() {
        return this.positionY;
    }

    public final int getPositionZ() {
        return this.positionZ;
    }

    public final int getDirectionX() {
        return this.dirX;
    }

    public final int getDirectionY() {
        return this.dirY;
    }

    public final int getDirectionZ() {
        return this.dirZ;
    }

    public final AEVector3D getDirection(final AEVector3D vec) {
        vec.x = this.dirX;
        vec.y = this.dirY;
        vec.z = this.dirZ;
        return vec;
    }

    public final AEVector3D getDirection() {
        return new AEVector3D(this.dirX, this.dirY, this.dirZ);
    }

    public final AEVector3D getUp(final AEVector3D vec) {
        vec.x = this.upX;
        vec.y = this.upY;
        vec.z = this.upZ;
        return vec;
    }

    public final AEVector3D getUp() {
        return new AEVector3D(this.upX, this.upY, this.upZ);
    }

    public final AEVector3D getRight(final AEVector3D var1) {
        var1.x = this.rightX;
        var1.y = this.rightY;
        var1.z = this.rightZ;
        return var1;
    }

    public final AEVector3D getRight() {
        return new AEVector3D(this.rightX, this.rightY, this.rightZ);
    }

    public final void setRotationOrder(final short var1) {
        if (this.rotationOrder != var1) {
            this.rotationOrder = var1;
            updateEulerAngles();
            this.isDirty = false;
        }
    }

    public final void addEulerAngles(final int x, final int y, final int z) {
        if (this.isDirty) {
            updateEulerAngles();
            this.isDirty = false;
        }

        this.eulerX += x;
        this.eulerY += y;
        this.eulerZ += z;
        setRotation(this.eulerX, this.eulerY, this.eulerZ);
    }

    public final void rotateAroundDir(int var1) {
        int var2 = AEMath.sin(var1);
        var1 = AEMath.cos(var1);
        int var3;
        int var4 = ((var3 = AEMath.Q_1 - var1) * this.dirX >> AEMath.Q) * this.dirZ >> AEMath.Q;
        int var5 = (var3 * this.dirY >> AEMath.Q) * this.dirZ >> AEMath.Q;
        int var6 = (var3 * this.dirX >> AEMath.Q) * this.dirY >> AEMath.Q;
        int var7 = var2 * this.dirY >> AEMath.Q;
        final int var8 = var2 * this.dirX >> AEMath.Q;
        var2 = var2 * this.dirZ >> AEMath.Q;
        final int var9 = ((var3 * this.dirX >> AEMath.Q) * this.dirX >> AEMath.Q) + var1;
        final int var10 = var6 - var2;
        final int var11 = var4 + var7;
        var2 += var6;
        var6 = ((var3 * this.dirY >> AEMath.Q) * this.dirY >> AEMath.Q) + var1;
        final int var12 = var5 - var8;
        var4 -= var7;
        var5 += var8;
        var1 += (var3 * this.dirZ >> AEMath.Q) * this.dirZ >> AEMath.Q;
        var3 = this.rightX;
        var7 = this.rightY;
        this.rightX = (this.rightX * var9 >> AEMath.Q) + (this.rightY * var10 >> AEMath.Q) + (this.rightZ * var11 >> AEMath.Q);
        this.rightY = (var3 * var2 >> AEMath.Q) + (this.rightY * var6 >> AEMath.Q) + (this.rightZ * var12 >> AEMath.Q);
        this.rightZ = (var3 * var4 >> AEMath.Q) + (var7 * var5 >> AEMath.Q) + (this.rightZ * var1 >> AEMath.Q);
        this.upX = (this.dirY * this.rightZ >> AEMath.Q) - (this.dirZ * this.rightY >> AEMath.Q);
        this.upY = (this.dirZ * this.rightX >> AEMath.Q) - (this.dirX * this.rightZ >> AEMath.Q);
        this.upZ = (this.dirX * this.rightY >> AEMath.Q) - (this.dirY * this.rightX >> AEMath.Q);
        var1 = AEMath.invSqrt((this.upX * this.upX >> AEMath.Q) + (this.upY * this.upY >> AEMath.Q) + (this.upZ * this.upZ >> AEMath.Q));
        this.upX = this.upX * var1 >> AEMath.Q;
        this.upY = this.upY * var1 >> AEMath.Q;
        this.upZ = this.upZ * var1 >> AEMath.Q;
        this.rightX = (this.dirZ * this.upY >> AEMath.Q) - (this.dirY * this.upZ >> AEMath.Q);
        this.rightY = (this.dirX * this.upZ >> AEMath.Q) - (this.dirZ * this.upX >> AEMath.Q);
        this.rightZ = (this.dirY * this.upX >> AEMath.Q) - (this.dirX * this.upY >> AEMath.Q);
        this.isDirty = true;
    }

    public final void rotateAroundUp(int var1) {
        int var2 = AEMath.sin(var1);
        var1 = AEMath.cos(var1);
        int var3;
        int var4 = ((var3 = AEMath.Q_1 - var1) * this.upX >> AEMath.Q) * this.upZ >> AEMath.Q;
        int var5 = (var3 * this.upY >> AEMath.Q) * this.upZ >> AEMath.Q;
        int var6 = (var3 * this.upX >> AEMath.Q) * this.upY >> AEMath.Q;
        int var7 = var2 * this.upY >> AEMath.Q;
        final int var8 = var2 * this.upX >> AEMath.Q;
        var2 = var2 * this.upZ >> AEMath.Q;
        final int var9 = ((var3 * this.upX >> AEMath.Q) * this.upX >> AEMath.Q) + var1;
        final int var10 = var6 - var2;
        final int var11 = var4 + var7;
        var2 += var6;
        var6 = ((var3 * this.upY >> AEMath.Q) * this.upY >> AEMath.Q) + var1;
        final int var12 = var5 - var8;
        var4 -= var7;
        var5 += var8;
        var1 += (var3 * this.upZ >> AEMath.Q) * this.upZ >> AEMath.Q;
        var3 = this.dirX;
        var7 = this.dirY;
        this.dirX = (this.dirX * var9 >> AEMath.Q) + (this.dirY * var10 >> AEMath.Q) + (this.dirZ * var11 >> AEMath.Q);
        this.dirY = (var3 * var2 >> AEMath.Q) + (this.dirY * var6 >> AEMath.Q) + (this.dirZ * var12 >> AEMath.Q);
        this.dirZ = (var3 * var4 >> AEMath.Q) + (var7 * var5 >> AEMath.Q) + (this.dirZ * var1 >> AEMath.Q);
        this.rightX = (this.dirZ * this.upY >> AEMath.Q) - (this.dirY * this.upZ >> AEMath.Q);
        this.rightY = (this.dirX * this.upZ >> AEMath.Q) - (this.dirZ * this.upX >> AEMath.Q);
        this.rightZ = (this.dirY * this.upX >> AEMath.Q) - (this.dirX * this.upY >> AEMath.Q);
        var1 = AEMath.invSqrt((this.rightX * this.rightX >> AEMath.Q) + (this.rightY * this.rightY >> AEMath.Q) + (this.rightZ * this.rightZ >> AEMath.Q));
        this.rightX = this.rightX * var1 >> AEMath.Q;
        this.rightY = this.rightY * var1 >> AEMath.Q;
        this.rightZ = this.rightZ * var1 >> AEMath.Q;
        this.dirX = (this.upZ * this.rightY >> AEMath.Q) - (this.upY * this.rightZ >> AEMath.Q);
        this.dirY = (this.upX * this.rightZ >> AEMath.Q) - (this.upZ * this.rightX >> AEMath.Q);
        this.dirZ = (this.upY * this.rightX >> AEMath.Q) - (this.upX * this.rightY >> AEMath.Q);
        this.isDirty = true;
    }

    public final void rotateAroundRight(int var1) {
        int var2 = AEMath.sin(var1);
        var1 = AEMath.cos(var1);
        int var3;
        int var4 = ((var3 = AEMath.Q_1 - var1) * this.rightX >> AEMath.Q) * this.rightZ >> AEMath.Q;
        int var5 = (var3 * this.rightY >> AEMath.Q) * this.rightZ >> AEMath.Q;
        int var6 = (var3 * this.rightX >> AEMath.Q) * this.rightY >> AEMath.Q;
        int var7 = var2 * this.rightY >> AEMath.Q;
        final int var8 = var2 * this.rightX >> AEMath.Q;
        var2 = var2 * this.rightZ >> AEMath.Q;
        final int var9 = ((var3 * this.rightX >> AEMath.Q) * this.rightX >> AEMath.Q) + var1;
        final int var10 = var6 - var2;
        final int var11 = var4 + var7;
        var2 += var6;
        var6 = ((var3 * this.rightY >> AEMath.Q) * this.rightY >> AEMath.Q) + var1;
        final int var12 = var5 - var8;
        var4 -= var7;
        var5 += var8;
        var1 += (var3 * this.rightZ >> AEMath.Q) * this.rightZ >> AEMath.Q;
        var3 = this.upX;
        var7 = this.upY;
        this.upX = (this.upX * var9 >> AEMath.Q) + (this.upY * var10 >> AEMath.Q) + (this.upZ * var11 >> AEMath.Q);
        this.upY = (var3 * var2 >> AEMath.Q) + (this.upY * var6 >> AEMath.Q) + (this.upZ * var12 >> AEMath.Q);
        this.upZ = (var3 * var4 >> AEMath.Q) + (var7 * var5 >> AEMath.Q) + (this.upZ * var1 >> AEMath.Q);
        this.dirX = (this.rightY * this.upZ >> AEMath.Q) - (this.rightZ * this.upY >> AEMath.Q);
        this.dirY = (this.rightZ * this.upX >> AEMath.Q) - (this.rightX * this.upZ >> AEMath.Q);
        this.dirZ = (this.rightX * this.upY >> AEMath.Q) - (this.rightY * this.upX >> AEMath.Q);
        var1 = AEMath.invSqrt((this.dirX * this.dirX >> AEMath.Q) + (this.dirY * this.dirY >> AEMath.Q) + (this.dirZ * this.dirZ >> AEMath.Q));
        this.dirX = this.dirX * var1 >> AEMath.Q;
        this.dirY = this.dirY * var1 >> AEMath.Q;
        this.dirZ = this.dirZ * var1 >> AEMath.Q;
        this.upX = (this.dirY * this.rightZ >> AEMath.Q) - (this.dirZ * this.rightY >> AEMath.Q);
        this.upY = (this.dirZ * this.rightX >> AEMath.Q) - (this.dirX * this.rightZ >> AEMath.Q);
        this.upZ = (this.dirX * this.rightY >> AEMath.Q) - (this.dirY * this.rightX >> AEMath.Q);
        this.isDirty = true;
    }

    public final void setEulerY(final int var1) {
        setRotation(this.eulerX, var1, this.eulerZ);
    }

    public final void setRotation(int x, int y, int z) {
        this.eulerX = x;
        this.eulerY = y;
        this.eulerZ = z;
        this.isDirty = false;
        final int sx = AEMath.sin(x);
        final int sy = AEMath.sin(y);
        final int sz = AEMath.sin(z);
        final int cx = AEMath.cos(x);
        final int cy = AEMath.cos(y);
        final int cz = AEMath.cos(z);
        final int var7;
        final int var8;
        final int var9;
        switch(this.rotationOrder) {
        case 0: // Tait–Bryan XYZ
            var7 = cx * cz >> AEMath.Q;
            var8 = cz * sx >> AEMath.Q;
            var9 = sy * sz >> AEMath.Q;
            this.rightX = cy * cz >> AEMath.Q;
            this.upX = -(cy * sz >> AEMath.Q);
            this.dirX = sy;
            this.rightY = (var8 * sy >> AEMath.Q) + (cx * sz >> AEMath.Q);
            this.upY = var7 - (var9 * sx >> AEMath.Q);
            this.dirY = -(cy * sx >> AEMath.Q);
            this.rightZ = -(var7 * sy >> AEMath.Q) + (sx * sz >> AEMath.Q);
            this.upZ = var8 + (var9 * cx >> AEMath.Q);
            this.dirZ = cx * cy >> AEMath.Q;
            return;
        case 1: // Tait–Bryan XZY
            var7 = cy * sz >> AEMath.Q;
            var8 = sy * sz >> AEMath.Q;
            this.rightX = cy * cz >> AEMath.Q;
            this.upX = -sz;
            this.dirX = cz * sy >> AEMath.Q;
            this.rightY = (sx * sy >> AEMath.Q) + (var7 * cx >> AEMath.Q);
            this.upY = cx * cz >> AEMath.Q;
            this.dirY = -(cy * sx >> AEMath.Q) + (var8 * cx >> AEMath.Q);
            this.rightZ = -(cx * sy >> AEMath.Q) + (var7 * sx >> AEMath.Q);
            this.upZ = cz * sx >> AEMath.Q;
            this.dirZ = (cx * cy >> AEMath.Q) + (var8 * sx >> AEMath.Q);
            return;
        case 2: // Tait–Bryan YXZ
            var7 = cy * cz >> AEMath.Q;
            var8 = sx * sz >> AEMath.Q;
            var9 = sy * cz >> AEMath.Q;
            this.rightX = var7 + (var8 * sy >> AEMath.Q);
            this.upX = (var9 * sx >> AEMath.Q) - (cy * sz >> AEMath.Q);
            this.dirX = cx * sy >> AEMath.Q;
            this.rightY = cx * sz >> AEMath.Q;
            this.upY = cx * cz >> AEMath.Q;
            this.dirY = -sx;
            this.rightZ = -var9 + (var8 * cy >> AEMath.Q);
            this.upZ = (var7 * sx >> AEMath.Q) + (sy * sz >> AEMath.Q);
            this.dirZ = cx * cy >> AEMath.Q;
            return;
        case 3:
            var7 = sx * sy >> AEMath.Q;
            var8 = cx * cy >> AEMath.Q;
            var9 = cy * sx >> AEMath.Q;
            this.rightX = cy * cz >> AEMath.Q;
            this.upX = var7 - (var8 * sz >> AEMath.Q);
            this.dirX = (cx * sy >> AEMath.Q) + (var9 * sz >> AEMath.Q);
            this.rightY = sz;
            this.upY = cx * cz >> AEMath.Q;
            this.dirY = -(cz * sx >> AEMath.Q);
            this.rightZ = -(cz * sy >> AEMath.Q);
            this.upZ = var9 + ((cx * sy >> AEMath.Q) * sz >> AEMath.Q);
            this.dirZ = var8 - (var7 * sz >> AEMath.Q);
            return;
        case 4:
            var7 = sx * sy >> AEMath.Q;
            var8 = cy * sx >> AEMath.Q;
            this.rightX = (cy * cz >> AEMath.Q) - (var7 * sz >> AEMath.Q);
            this.upX = -(cx * sz >> AEMath.Q);
            this.dirX = (cz * sy >> AEMath.Q) + (var8 * sz >> AEMath.Q);
            this.rightY = (var7 * cz >> AEMath.Q) + (cy * sz >> AEMath.Q);
            this.upY = cx * cz >> AEMath.Q;
            this.dirY = -(var8 * cz >> AEMath.Q) + (sy * sz >> AEMath.Q);
            this.rightZ = -(cx * sy >> AEMath.Q);
            this.upZ = sx;
            this.dirZ = cx * cy >> AEMath.Q;
            return;
        case 5:
            var7 = sy * sz >> AEMath.Q;
            var9 = cz * sy >> AEMath.Q;
            this.rightX = cy * cz >> AEMath.Q;
            this.upX = (var9 * sx >> AEMath.Q) - (cx * sz >> AEMath.Q);
            this.dirX = (var9 * cx >> AEMath.Q) + (sx * sz >> AEMath.Q);
            this.rightY = cy * sz >> AEMath.Q;
            this.upY = (cx * cz >> AEMath.Q) + (var7 * sx >> AEMath.Q);
            this.dirY = -(cz * sx >> AEMath.Q) + (var7 * cx >> AEMath.Q);
            this.rightZ = -sy;
            this.upZ = cy * sx >> AEMath.Q;
            this.dirZ = cx * cy >> AEMath.Q;
        default:
        }
    }

    public final int getEulerX() {
        if (this.isDirty) {
            updateEulerAngles();
            this.isDirty = false;
        }

        return this.eulerX;
    }

    public final int getEulerY() {
        if (this.isDirty) {
            updateEulerAngles();
            this.isDirty = false;
        }

        return this.eulerY;
    }

    public final int getEulerZ() {
        if (this.isDirty) {
            updateEulerAngles();
            this.isDirty = false;
        }

        return this.eulerZ;
    }

    private void updateEulerAngles() {
        switch(this.rotationOrder) {
        case 0:
            this.eulerY = AEMath.invSin(this.dirX);
            if (this.eulerY < AEMath.Q_HALF) {
                if (this.eulerY > -AEMath.Q_HALF) {
                    this.eulerX = AEMath.atan2(-this.dirY, this.dirZ);
                    this.eulerZ = AEMath.atan2(-this.upX, this.rightX);
                    return;
                }

                this.eulerX = -AEMath.atan2(this.rightY, this.upY);
                this.eulerZ = 0;
                return;
            }

            this.eulerX = AEMath.atan2(this.rightY, this.upY);
            this.eulerZ = 0;
            return;
        case 1:
            this.eulerZ = AEMath.invSin(-this.upX);
            if (this.eulerZ < AEMath.Q_HALF) {
                if (this.eulerZ > -AEMath.Q_HALF) {
                    this.eulerX = AEMath.atan2(this.upZ, this.upY);
                    this.eulerY = AEMath.atan2(this.dirX, this.rightX);
                    return;
                }

                this.eulerX = -AEMath.atan2(-this.rightZ, this.dirZ);
                this.eulerY = 0;
                return;
            }

            this.eulerX = AEMath.atan2(-this.rightZ, this.dirZ);
            this.eulerY = 0;
            return;
        case 2:
            this.eulerX = AEMath.invSin(-this.dirY);
            if (this.eulerX < AEMath.Q_HALF) {
                if (this.eulerX > -AEMath.Q_HALF) {
                    this.eulerY = AEMath.atan2(this.dirX, this.dirZ);
                    this.eulerZ = AEMath.atan2(this.rightY, this.upY);
                    return;
                }

                this.eulerY = -AEMath.atan2(-this.upX, this.rightX);
                this.eulerZ = 0;
                return;
            }

            this.eulerY = AEMath.atan2(-this.upX, this.rightX);
            this.eulerZ = 0;
            return;
        case 3:
            this.eulerZ = AEMath.invSin(this.rightY);
            if (this.eulerZ < AEMath.Q_HALF) {
                if (this.eulerZ > -AEMath.Q_HALF) {
                    this.eulerY = AEMath.atan2(-this.rightZ, this.rightX);
                    this.eulerX = AEMath.atan2(-this.dirY, this.upY);
                    return;
                }

                this.eulerY = -AEMath.atan2(this.upZ, this.dirZ);
                this.eulerX = 0;
                return;
            }

            this.eulerY = AEMath.atan2(this.upZ, this.dirZ);
            this.eulerX = 0;
            return;
        case 4:
            this.eulerX = AEMath.invSin(this.upZ);
            if (this.eulerX < AEMath.Q_HALF) {
                if (this.eulerX > -AEMath.Q_HALF) {
                    this.eulerZ = AEMath.atan2(-this.upX, this.upY);
                    this.eulerY = AEMath.atan2(-this.rightZ, this.dirZ);
                    return;
                }

                this.eulerZ = -AEMath.atan2(this.dirX, this.rightX);
                this.eulerY = 0;
                return;
            }

            this.eulerZ = AEMath.atan2(this.dirX, this.rightX);
            this.eulerY = 0;
            return;
        case 5:
            this.eulerY = AEMath.invSin(-this.rightZ);
            if (this.eulerY < AEMath.Q_HALF) {
                if (this.eulerY > -AEMath.Q_HALF) {
                    this.eulerZ = AEMath.atan2(this.rightY, this.rightX);
                    this.eulerX = AEMath.atan2(this.upZ, this.dirZ);
                    return;
                }

                this.eulerZ = -AEMath.atan2(-this.upX, this.dirX);
                this.eulerX = 0;
                return;
            }
            this.eulerZ = AEMath.atan2(-this.upX, this.dirX);
            this.eulerX = 0;
        default:
        }
    }

    public final void setScale(final int var1, final int var2, final int var3) {
        this.scaleX = var1;
        this.scaleY = var2;
        this.scaleZ = var3;
    }

    public final AEVector3D copyScaleTo(final AEVector3D var1) {
        var1.x = this.scaleX;
        var1.y = this.scaleY;
        var1.z = this.scaleZ;
        return var1;
    }

    public final AEVector3D getScale() {
        return copyScaleTo(new AEVector3D());
    }

    public final AEMatrix getInverse(final AEMatrix var1) {
        var1.scaleX = (AEMath.Q_1 << AEMath.Q) / this.scaleX;
        var1.scaleY = (AEMath.Q_1 << AEMath.Q) / this.scaleY;
        var1.scaleZ = (AEMath.Q_1 << AEMath.Q) / this.scaleZ;
        var1.rightX = this.rightX;
        var1.upX = this.rightY;
        var1.dirX = this.rightZ;
        var1.rightY = this.upX;
        var1.upY = this.upY;
        var1.dirY = this.upZ;
        var1.rightZ = this.dirX;
        var1.upZ = this.dirY;
        var1.dirZ = this.dirZ;
        var1.positionX = var1.scaleX * (-(var1.rightX * this.positionX >> AEMath.Q) - (var1.upX * this.positionY >> AEMath.Q) - (var1.dirX * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        var1.positionY = var1.scaleY * (-(var1.rightY * this.positionX >> AEMath.Q) - (var1.upY * this.positionY >> AEMath.Q) - (var1.dirY * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        var1.positionZ = var1.scaleZ * (-(var1.rightZ * this.positionX >> AEMath.Q) - (var1.upZ * this.positionY >> AEMath.Q) - (var1.dirZ * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        var1.isDirty = true;
        return var1;
    }

    public final void setRows(final AEVector3D var1, final AEVector3D var2, final AEVector3D var3) {
        this.rightX = var1.x;
        this.upX = var2.x;
        this.dirX = var3.x;
        this.rightY = var1.y;
        this.upY = var2.y;
        this.dirY = var3.y;
        this.rightZ = var1.z;
        this.upZ = var2.z;
        this.dirZ = var3.z;
        this.isDirty = true;
    }

    public final void toFloatArray(final float[] var1) {
        var1[0] = this.scaleX * this.rightX * AEMath.TO_FLOAT_SQR;
        var1[1] = this.scaleY * this.upX * AEMath.TO_FLOAT_SQR;
        var1[2] = this.scaleZ * this.dirX * AEMath.TO_FLOAT_SQR;
        var1[3] = this.positionX;
        var1[4] = this.scaleX * this.rightY * AEMath.TO_FLOAT_SQR;
        var1[5] = this.scaleY * this.upY * AEMath.TO_FLOAT_SQR;
        var1[6] = this.scaleZ * this.dirY * AEMath.TO_FLOAT_SQR;
        var1[7] = this.positionY;
        var1[8] = this.scaleX * this.rightZ * AEMath.TO_FLOAT_SQR;
        var1[9] = this.scaleY * this.upZ * AEMath.TO_FLOAT_SQR;
        var1[10] = this.scaleZ * this.dirZ * AEMath.TO_FLOAT_SQR;
        var1[11] = this.positionZ;
        var1[12] = 0.0F;
        var1[13] = 0.0F;
        var1[14] = 0.0F;
        var1[15] = 1.0F;
    }

    public final String toString() {
        String var1 = "";
        var1 = var1 + "|\t" + (this.scaleX * this.rightX >> AEMath.Q) + ",\t" + (this.scaleY * this.upX >> AEMath.Q) + ",\t" + (this.scaleZ * this.dirX >> AEMath.Q) + ",\t" + this.positionX + "\t|\n";
        var1 = var1 + "|\t" + (this.scaleX * this.rightY >> AEMath.Q) + ",\t" + (this.scaleY * this.upY >> AEMath.Q) + ",\t" + (this.scaleZ * this.dirY >> AEMath.Q) + ",\t" + this.positionY + "\t|\n";
        return var1 + "|\t" + (this.scaleX * this.rightZ >> AEMath.Q) + ",\t" + (this.scaleY * this.upZ >> AEMath.Q) + ",\t" + (this.scaleZ * this.dirZ >> AEMath.Q) + ",\t" + this.positionZ + "\t|\n";
    }

    public final AEVector3D transformVector(final AEVector3D var1) {
        final int ol_x = var1.x;
        final int ol_y = var1.y;
        final int ol_z = var1.z;
        var1.x = ((this.scaleX * this.rightX >> AEMath.Q) * ol_x >> AEMath.Q) + ((this.scaleY * this.upX >> AEMath.Q) * ol_y >> AEMath.Q) + ((this.scaleZ * this.dirX >> AEMath.Q) * ol_z >> AEMath.Q) + this.positionX;
        var1.y = ((this.scaleX * this.rightY >> AEMath.Q) * ol_x >> AEMath.Q) + ((this.scaleY * this.upY >> AEMath.Q) * ol_y >> AEMath.Q) + ((this.scaleZ * this.dirY >> AEMath.Q) * ol_z >> AEMath.Q) + this.positionY;
        var1.z = ((this.scaleX * this.rightZ >> AEMath.Q) * ol_x >> AEMath.Q) + ((this.scaleY * this.upZ >> AEMath.Q) * ol_y >> AEMath.Q) + ((this.scaleZ * this.dirZ >> AEMath.Q) * ol_z >> AEMath.Q) + this.positionZ;
        return var1;
    }

    public final AEVector3D transformVector(final AEVector3D pre, final AEVector3D post) {
        post.x = ((this.scaleX * this.rightX >> AEMath.Q) * pre.x >> AEMath.Q) + ((this.scaleY * this.upX >> AEMath.Q) * pre.y >> AEMath.Q) + ((this.scaleZ * this.dirX >> AEMath.Q) * pre.z >> AEMath.Q) + this.positionX;
        post.y = ((this.scaleX * this.rightY >> AEMath.Q) * pre.x >> AEMath.Q) + ((this.scaleY * this.upY >> AEMath.Q) * pre.y >> AEMath.Q) + ((this.scaleZ * this.dirY >> AEMath.Q) * pre.z >> AEMath.Q) + this.positionY;
        post.z = ((this.scaleX * this.rightZ >> AEMath.Q) * pre.x >> AEMath.Q) + ((this.scaleY * this.upZ >> AEMath.Q) * pre.y >> AEMath.Q) + ((this.scaleZ * this.dirZ >> AEMath.Q) * pre.z >> AEMath.Q) + this.positionZ;
        return post;
    }
    /** higher precision for big values than Matrix.transformVector() */
    public final AEVector3D transformVector2(final AEVector3D var1, final AEVector3D var2) {
        final long var3 = ((long)(this.scaleX * this.rightX >> AEMath.Q) * (long)var1.x >> AEMath.Q) + ((long)(this.scaleY * this.upX >> AEMath.Q) * (long)var1.y >> AEMath.Q) + ((long)(this.scaleZ * this.dirX >> AEMath.Q) * (long)var1.z >> AEMath.Q) + this.positionX;
        final long var5 = ((long)(this.scaleX * this.rightY >> AEMath.Q) * (long)var1.x >> AEMath.Q) + ((long)(this.scaleY * this.upY >> AEMath.Q) * (long)var1.y >> AEMath.Q) + ((long)(this.scaleZ * this.dirY >> AEMath.Q) * (long)var1.z >> AEMath.Q) + this.positionY;
        final long var7 = ((long)(this.scaleX * this.rightZ >> AEMath.Q) * (long)var1.x >> AEMath.Q) + ((long)(this.scaleY * this.upZ >> AEMath.Q) * (long)var1.y >> AEMath.Q) + ((long)(this.scaleZ * this.dirZ >> AEMath.Q) * (long)var1.z >> AEMath.Q) + this.positionZ;
        var2.x = (int)var3;
        var2.y = (int)var5;
        var2.z = (int)var7;
        return var2;
    }
    /** transforms only directions */
    public final AEVector3D transformVectorNoScale(final AEVector3D var1, final AEVector3D var2) {
        var2.x = (this.rightX * var1.x >> AEMath.Q) + (this.upX * var1.y >> AEMath.Q) + (this.dirX * var1.z >> AEMath.Q);
        var2.y = (this.rightY * var1.x >> AEMath.Q) + (this.upY * var1.y >> AEMath.Q) + (this.dirY * var1.z >> AEMath.Q);
        var2.z = (this.rightZ * var1.x >> AEMath.Q) + (this.upZ * var1.y >> AEMath.Q) + (this.dirZ * var1.z >> AEMath.Q);
        return var2;
    }
    /** transforms only directions for big values */
    public final AEVector3D transformVectorNoScale2(final AEVector3D var1, final AEVector3D var2) {
        final long var3 = ((long)this.rightX * (long)var1.x >> AEMath.Q) + ((long)this.upX * (long)var1.y >> AEMath.Q) + ((long)this.dirX * (long)var1.z >> AEMath.Q);
        final long var5 = ((long)this.rightY * (long)var1.x >> AEMath.Q) + ((long)this.upY * (long)var1.y >> AEMath.Q) + ((long)this.dirY * (long)var1.z >> AEMath.Q);
        final long var7 = ((long)this.rightZ * (long)var1.x >> AEMath.Q) + ((long)this.upZ * (long)var1.y >> AEMath.Q) + ((long)this.dirZ * (long)var1.z >> AEMath.Q);
        var2.x = (int)var3;
        var2.y = (int)var5;
        var2.z = (int)var7;
        return var2;
    }

    public final AEVector3D[] transformVectorsNoScale(final AEVector3D[] var1, final AEVector3D[] var2) {
        for(int i = var2.length - 1; i >= 0; --i) {
            var2[i].x = (this.rightX * var1[i].x >> AEMath.Q) + (this.upX * var1[i].y >> AEMath.Q) + (this.dirX * var1[i].z >> AEMath.Q);
            var2[i].y = (this.rightY * var1[i].x >> AEMath.Q) + (this.upY * var1[i].y >> AEMath.Q) + (this.dirY * var1[i].z >> AEMath.Q);
            var2[i].z = (this.rightZ * var1[i].x >> AEMath.Q) + (this.upZ * var1[i].y >> AEMath.Q) + (this.dirZ * var1[i].z >> AEMath.Q);
        }

        return var2;
    }

    public final AEVector3D inverseTransformVector(final AEVector3D var1) {
        final int var2 = (AEMath.Q_1 << AEMath.Q) / this.scaleX;
        final int var3 = (AEMath.Q_1 << AEMath.Q) / this.scaleY;
        final int var4 = (AEMath.Q_1 << AEMath.Q) / this.scaleZ;
        final int var5 = var2 * (-(this.rightX * this.positionX >> AEMath.Q) - (this.rightY * this.positionY >> AEMath.Q) - (this.rightZ * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        final int var6 = var3 * (-(this.upX * this.positionX >> AEMath.Q) - (this.upY * this.positionY >> AEMath.Q) - (this.upZ * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        final int var7 = var4 * (-(this.dirX * this.positionX >> AEMath.Q) - (this.dirY * this.positionY >> AEMath.Q) - (this.dirZ * this.positionZ >> AEMath.Q)) >> AEMath.Q;
        final int var8 = var1.x;
        final int var9 = var1.y;
        final int var10 = var1.z;
        var1.x = ((var2 * this.rightX >> AEMath.Q) * var8 >> AEMath.Q) + ((var3 * this.rightY >> AEMath.Q) * var9 >> AEMath.Q) + ((var4 * this.rightZ >> AEMath.Q) * var10 >> AEMath.Q) + var5;
        var1.y = ((var2 * this.upX >> AEMath.Q) * var8 >> AEMath.Q) + ((var3 * this.upY >> AEMath.Q) * var9 >> AEMath.Q) + ((var4 * this.upZ >> AEMath.Q) * var10 >> AEMath.Q) + var6;
        var1.z = ((var2 * this.dirX >> AEMath.Q) * var8 >> AEMath.Q) + ((var3 * this.dirY >> AEMath.Q) * var9 >> AEMath.Q) + ((var4 * this.dirZ >> AEMath.Q) * var10 >> AEMath.Q) + var7;
        return var1;
    }

    public final AEVector3D transformVectorNoScale(final AEVector3D var1) {
        final int var2 = var1.x;
        final int var3 = var1.y;
        final int var4 = var1.z;
        var1.x = (this.rightX * var2 >> AEMath.Q) + (this.rightY * var3 >> AEMath.Q) + (this.rightZ * var4 >> AEMath.Q);
        var1.y = (this.upX * var2 >> AEMath.Q) + (this.upY * var3 >> AEMath.Q) + (this.upZ * var4 >> AEMath.Q);
        var1.z = (this.dirX * var2 >> AEMath.Q) + (this.dirY * var3 >> AEMath.Q) + (this.dirZ * var4 >> AEMath.Q);
        return var1;
    }

    public final void setOrientation(final AEVector3D var1) {
        temp.set(var1);
        temp.normalize();
        this.dirX = temp.x;
        this.dirY = temp.y;
        this.dirZ = temp.z;
        this.upX = 0;
        this.upY = AEMath.Q_1;
        this.upZ = 0;
        this.rightX = (this.upY * this.dirZ >> AEMath.Q) - (this.upZ * this.dirY >> AEMath.Q);
        this.rightY = (this.upZ * this.dirX >> AEMath.Q) - (this.upX * this.dirZ >> AEMath.Q);
        this.rightZ = (this.upX * this.dirY >> AEMath.Q) - (this.upY * this.dirX >> AEMath.Q);
        temp.x = this.rightX;
        temp.y = this.rightY;
        temp.z = this.rightZ;
        temp.normalize();
        this.rightX = temp.x;
        this.rightY = temp.y;
        this.rightZ = temp.z;
        this.upX = (this.dirY * this.rightZ >> AEMath.Q) - (this.dirZ * this.rightY >> AEMath.Q);
        this.upY = (this.dirZ * this.rightX >> AEMath.Q) - (this.dirX * this.rightZ >> AEMath.Q);
        this.upZ = (this.dirX * this.rightY >> AEMath.Q) - (this.dirY * this.rightX >> AEMath.Q);
        temp.x = this.upX;
        temp.y = this.upY;
        temp.z = this.upZ;
        temp.normalize();
        this.upX = temp.x;
        this.upY = temp.y;
        this.upZ = temp.z;
    }
}
