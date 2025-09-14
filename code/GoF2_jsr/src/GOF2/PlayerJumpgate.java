package GOF2;

import AE.AEGeometry;
import AE.BoundingSphere;
import AE.BoundingVolume;
import AE.Math.AEMath;

public final class PlayerJumpgate extends PlayerStaticFar {
    private boolean animationInit;

    public PlayerJumpgate(final int id, final AEGeometry mesh, final int x, final int y, final int z, final boolean visible) {
        super(id, mesh, x, y, z);
        setVisible(visible);
        if (visible) {
            this.boundingBoxes = new BoundingVolume[1];
            this.boundingBoxes[0] = new BoundingSphere(x, y, z, 0, 0, 0, 15000);
            this.mainMesh_.setAnimationSpeed(100);
            this.mainMesh_.setAnimationRangeInTime(1, 20);
            this.mainMesh_.setAnimationMode((byte)2);
        }

        mesh.setRotation(-AEMath.Q_PI_QUARTER, 0, 0);
        this.animationInit = false;
    }

    public final void activate() {
        if (!this.animationInit) {
            this.mainMesh_.setAnimationSpeed(50);
            this.mainMesh_.setAnimationRangeInTime(21, 79);
            this.mainMesh_.setAnimationMode((byte)1);
            this.animationInit = true;
        }

    }

    public final void setPosition(final int var1, final int var2, final int var3) {
        this.posX = var1;
        this.posY = var2;
        this.posZ = var3;
        this.mainMesh_.moveTo(var1, var2, var3);
    }

    public final void update(final long var1) {
        if (this.mainMesh_.getCurrentAnimFrame() == 79) {
            this.mainMesh_.setAnimationSpeed(100);
            this.mainMesh_.setAnimationRangeInTime(38, 60);
            this.mainMesh_.setAnimationMode((byte)2);
        }

        super.update(var1);
    }
}
