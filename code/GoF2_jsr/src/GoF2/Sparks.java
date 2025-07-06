package GoF2;

import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.ITexture;
import AE.Math.AEVector3D;

public final class Sparks extends AbstractMesh {
    private final Impact impact;
    private boolean inactive;
    private final boolean unused4a_;

    private Sparks(final ITexture texture, final int u1, final int v1, final int u2, final int v2, final int particleCount, final int baseParticleTime, final int particleTimeRange, final int var9, final byte blending) {
        this.impact = new Impact(particleCount, texture, 256, u1, v1, u2, v2, var9, baseParticleTime, particleTimeRange, (byte)2);
        this.impact.mesh.setRenderLayer(2);
        this.inactive = true;
        this.unused4a_ = particleCount > 1;
    }

    public Sparks(final ITexture texture, final int u1, final int v1, final int u2, final int v2, final int particleCount, final int baseParticleTime, final int particleTimeRange, final int var9) {
      //this(texture, 33,225, 63,255,            10, 					700, 					 100,  500, (byte)2);    
        this(texture, u1, v1, u2, v2, particleCount, baseParticleTime, particleTimeRange, var9, (byte)2);
    }

    public final void moveTo(final int x, final int y, final int z) {
        this.impact.mesh.moveTo(x, y, z);
    }

    public final void explode(final AEVector3D pos) {
        this.impact.mesh.moveTo(pos.x, pos.y, pos.z);
        this.impact.explode();
        this.inactive = false;
    }

    public final void update(final long frameTime) {
        if (!this.inactive) {
            this.impact.update((int)frameTime);
        }
    }

    public final void render() {
        if (!this.inactive) {
            GlobalStatus.renderer.drawNodeInVF(this.impact.mesh);
        }
    }
    /**
     * Can't clone Sparks.
     */
    public final GraphNode clone() {
        return null;
    }
    /**
     * Can't set texture for Sparks.
     */
    public final void setTexture(final ITexture texture) {
    }

    public final void OnRelease() {
        if (this.impact.mesh != null) {
            this.impact.mesh.OnRelease();
        }
        this.impact.mesh = null;
    }
}
