package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.ParticleSystemMesh;
import AE.Math.AEVector3D;

public final class Trail {
    private static int[] UV = {
            80, 255,
            95, 254,
            32, 240,
            63, 239
            };
    private int width = 60;
    private final int[] vertices;
    private final int[] tempVerts;
    private final int[] uvs;
    private AbstractMesh mesh;
    private final AEVector3D sourcePos = new AEVector3D();
    private boolean stop = false;

    public Trail(final int var1) {
        int var2 = var1 == 0 ? 16 : 13;
        this.mesh = AbstractMesh.newPlaneStrip(0, var2, (byte)2);
        this.mesh.setTexture(AEResourceManager.getTextureResource(1));
        this.mesh.setRenderLayer(2);
        this.tempVerts = new int[var2 * 12];
        this.vertices = new int[var2 * 12];
        final int var3 = var1 << 2;
        int var4 = UV[var3 + 1];
        int var5 = UV[var3 + 3];
        this.uvs = new int[var2 * 8];

        for(var2 = 0; var2 < this.uvs.length; var2 += 8) {
            this.uvs[var2] = UV[var3];
            this.uvs[var2 + 1] = var4;
            this.uvs[var2 + 2] = UV[var3 + 2];
            this.uvs[var2 + 3] = var4;
            this.uvs[var2 + 4] = UV[var3 + 2];
            this.uvs[var2 + 5] = var5;
            this.uvs[var2 + 6] = UV[var3];
            this.uvs[var2 + 7] = var5;
            if (var1 == 1) {
                --var4;
                --var5;
            } else {
                var4 -= 2;
                var5 -= 2;
            }
        }

    }

    public final void OnRelease() {
        this.mesh.OnRelease();
        this.mesh = null;
    }

    public final void setWidth(final int var1) {
        this.width = var1;
    }

    public final void update(final AEVector3D pos) {
        if (this.stop) {
            this.sourcePos.set(pos);
            this.stop = false;
        }

        this.tempVerts[0] = pos.x - this.width;
        this.tempVerts[1] = pos.y;
        this.tempVerts[2] = pos.z;
        this.tempVerts[3] = pos.x + this.width;
        this.tempVerts[4] = pos.y;
        this.tempVerts[5] = pos.z;
        this.tempVerts[6] = this.sourcePos.x + this.width;
        this.tempVerts[7] = this.sourcePos.y;
        this.tempVerts[8] = this.sourcePos.z;
        this.tempVerts[9] = this.sourcePos.x - this.width;
        this.tempVerts[10] = this.sourcePos.y;
        this.tempVerts[11] = this.sourcePos.z;

        for(int i = this.tempVerts.length - 1; i >= 23; i -= 12) {
            this.tempVerts[i] = this.tempVerts[i - 12];
            this.tempVerts[i - 1] = this.tempVerts[i - 13];
            this.tempVerts[i - 2] = this.tempVerts[i - 14];
            this.tempVerts[i - 3] = this.tempVerts[i - 15];
            this.tempVerts[i - 4] = this.tempVerts[i - 16];
            this.tempVerts[i - 5] = this.tempVerts[i - 17];
            this.tempVerts[i - 6] = this.tempVerts[i - 18];
            this.tempVerts[i - 7] = this.tempVerts[i - 19];
            this.tempVerts[i - 8] = this.tempVerts[i - 20];
            this.tempVerts[i - 9] = this.tempVerts[i - 21];
            this.tempVerts[i - 10] = this.tempVerts[i - 22];
            this.tempVerts[i - 11] = this.tempVerts[i - 23];
        }

        for(int i = 0; i < this.tempVerts.length; i += 3) {
            this.vertices[i] = this.tempVerts[i] - pos.x;
            this.vertices[i + 1] = this.tempVerts[i + 1] - pos.y;
            this.vertices[i + 2] = this.tempVerts[i + 2] - pos.z;
        }

        this.mesh.moveTo(pos.x, pos.y, pos.z);
        ((ParticleSystemMesh)this.mesh).setMeshData_(this.vertices, this.uvs);
        this.sourcePos.set(pos);
    }

    public final void reset(final AEVector3D var1) {
        for(int var2 = 0; var2 < this.tempVerts.length; var2 += 3) {
            this.tempVerts[var2] = var1.x;
            this.tempVerts[var2 + 1] = var1.y;
            this.tempVerts[var2 + 2] = var1.z;
        }

        this.sourcePos.set(var1);
        this.stop = true;
    }

    public final void render() {
        GlobalStatus.renderer.drawNodeInVF(this.mesh);
    }
}
