package GOF2;

import AE.AEGeometry;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.Math.AEMath;
import AE.PaintCanvas.AEGraphics3D;

public class PlayerStaticFar extends PlayerStatic {
    protected BoundingVolume[] boundingBoxes;

    public PlayerStaticFar(final int id, final AEGeometry mesh, final int x, final int y, final int z) {
        super(id, mesh, x, y, z);
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
        if (mesh != null) {
            this.player.radius = 7500f;
            this.mainMesh_.setRenderLayer(2);
        }
    }

    public void appendToRender() {
        super.appendToRender();
    }

    public void update(final long dt) {
        if (this.mainMesh_ != null) {
            this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
            this.position.set(this.posX, this.posY, this.posZ);
            this.position.subtract(this.tempVector_, virtDistToCam_);
            int var3 = virtDistToCam_.getLength();
            if (var3 > AEGraphics3D.CLAMP_MID) {
                virtDistToCam_.normalize();
                virtDistToCam_.scale(AEGraphics3D.CLAMP_MID);
                virtDistToCam_.add(this.tempVector_);
                this.mainMesh_.moveTo(virtDistToCam_);
                var3 = (int)((float)AEGraphics3D.CLAMP_MID / var3 * AEMath.TO_Q);
                this.mainMesh_.setScale(var3, var3, var3);
            } else {
                this.mainMesh_.setScale(AEMath.Q_1, AEMath.Q_1, AEMath.Q_1);
                this.mainMesh_.moveTo(this.posX, this.posY, this.posZ);
            }
        }
    }

    public boolean outerCollide(final int x, final int y, final int z) {
        return x - this.posX < this.player.radius &&
            x - this.posX > -this.player.radius && 
            y - this.posY <  this.player.radius && 
            y - this.posY > -this.player.radius &&
            z - this.posZ <  this.player.radius && 
            z - this.posZ > -this.player.radius;
    }

    public final AEVector3D getTargetPos_(final AEVector3D buffer) {
        buffer.x = this.targetX;
        buffer.y = this.targetY;
        buffer.z = this.targetZ;
        return buffer;
    }

    public boolean outerCollide(final AEVector3D point) {
        return this.outerCollide(point.x, point.y, point.z);
    }

    public AEVector3D getProjectionVector_(final AEVector3D var1) {
        return this.boundingBoxes != null ? this.boundingBoxes[0].getProjectionVector(var1) : null;
    }
}
