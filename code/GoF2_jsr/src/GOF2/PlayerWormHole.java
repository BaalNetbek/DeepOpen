package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.GlobalStatus;
import AE.AEGroup;
import AE.Math.AEVector3D;
import AE.Math.AEMath;
import AE.PaintCanvas.AEGraphics3D;

public final class PlayerWormHole extends PlayerStaticFar {
    private int lifeTime;
    private int scale;

    public PlayerWormHole(int id, AEGeometry mesh, final int x, final int y, final int z, final boolean active) {
        super(id, mesh, x, y, z);
        this.name = GlobalStatus.gameText.getText(269);
        setVisible(active);
        this.geometry = new AEGroup();
        if (active) {
            this.mainMesh_.setAnimationSpeed(30);
            this.mainMesh_.setRotation(-AEMath.Q_32nd, -AEMath.Q_32nd, 0);
            this.mainMesh_.setAnimationMode((byte)2);
        }

        this.geometry.uniqueAppend_(this.mainMesh_);
        this.mainMesh_.moveTo(0, 0, 0);
        this.geometry.moveTo(x, y, z);
        final char var7 = 40000;
        this.player.radius = var7;

        for(int i = 0; i < 10; ++i) {
            (mesh = AEResourceManager.getGeometryResource(6806)).setTransform(this.mainMesh_.getToParentTransform());
            mesh.roll((i + 1) * GlobalStatus.random.nextInt(400));
            mesh.setAnimationMode((byte)2);
            mesh.setAnimationSpeed(20 + GlobalStatus.random.nextInt(50));
            this.geometry.uniqueAppend_(mesh);
        }

        reset(false);
    }

    public final void reset(final boolean var1) {
        this.lifeTime = var1 ? 39000 : 0;
        this.scale = AEMath.Q_1;
    }

    public final boolean isShrinking() {
        return this.lifeTime > 40000;
    }

    public final void setPosition(final int var1, final int var2, final int var3) {
        this.posX = var1;
        this.posY = var2;
        this.posZ = var3;
        this.geometry.moveTo(var1, var2, var3);
    }

    public final void appendToRender() {
        if (this.visible) {
            GlobalStatus.renderer.drawNodeInVF(this.geometry);
        }

    }

    public final void update(final long var1) {
        if (this.visible) {
            this.lifeTime = (int)(this.lifeTime + var1);
            int var2;
            int var5;
            if (this.lifeTime < 0) {
                this.scale = AEMath.Q_1 - (int)(-this.lifeTime / 3000.0F * AEMath.TO_Q);
                if (this.lifeTime >= 0) {
                    this.scale = AEMath.Q_1;
                }
            } else if (this.lifeTime > 40000) {
                if ((var5 = Status.getCurrentCampaignMission()) == 40 || var5 == 42) {
                    this.lifeTime = 40000;
                }

                this.scale = AEMath.Q_1 - (int)((this.lifeTime - 40000) / 3000.0F * AEMath.TO_Q);
                if (this.lifeTime > 43000) {
                    if (!Status.inAlienOrbit() && !Status.getStation().isAttackedByAliens()) {
                        this.visible = false;
                        this.geometry.setDraw(false);
                    } else {
                        this.lifeTime = -3000;
                        var2 = (20000 + GlobalStatus.random.nextInt(40000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1);
                        int var3 = (20000 + GlobalStatus.random.nextInt(40000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1);
                        int var4 = (20000 + GlobalStatus.random.nextInt(40000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1);
                        if (var5 == 29 || var5 == 41) {
                            this.tempVector_ = this.level.getPlayer().getPosition();
                            var2 += this.tempVector_.x + var2 * 3;
                            var3 += this.tempVector_.y + var3 * 3;
                            var4 += this.tempVector_.z + var4 * 3;
                        }

                        setPosition(var2, var3, var4);
                        if (this.level.getPlayer().goingToWormhole()) {
                            this.level.getPlayer().getHud().hudEvent(6, this.level.getPlayer());
                            this.level.getPlayer().setAutoPilot((KIPlayer)null);
                        }
                    }
                }
            }

            this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
            this.position.set(this.posX, this.posY, this.posZ);
            this.position.subtract(this.tempVector_, virtDistToCam_);
            var5 = virtDistToCam_.getLength();
            if (var5 > AEGraphics3D.CLAMP_MID) {
                virtDistToCam_.normalize();
                virtDistToCam_.scale(AEGraphics3D.CLAMP_MID);
                virtDistToCam_.add(this.tempVector_);
                this.geometry.moveTo(virtDistToCam_);
                var2 = (int)((float)AEGraphics3D.CLAMP_MID / var5 * this.scale);
                this.geometry.setScale(var2, var2, var2);
            } else {
                this.geometry.setScale(this.scale, this.scale, this.scale);
                this.geometry.moveTo(this.posX, this.posY, this.posZ);
            }

            virtDistToCam_ = this.geometry.getPostition();
            this.tempVector_.subtract(virtDistToCam_);
            this.tempVector_.normalize();
            virtDistToCam_.set(0, AEMath.Q_1, 0);
            this.position = virtDistToCam_.crossProduct(this.tempVector_, this.position);
            this.position.normalize();
            (virtDistToCam_ = this.position.crossProduct(this.tempVector_, virtDistToCam_)).normalize();
            this.geometry.setTransform(this.position, virtDistToCam_, this.tempVector_);
        }

    }

    public final AEVector3D getPosition(final AEVector3D var1) {
        var1.x = this.posX;
        var1.y = this.posY;
        var1.z = this.posZ;
        return var1;
    }
}
