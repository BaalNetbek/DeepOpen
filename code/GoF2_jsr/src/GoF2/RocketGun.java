package GoF2;

import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEVector3D;

public final class RocketGun extends ObjectGun {
    private Trail trail = new Trail(1);
    private static AEVector3D tempPos;
    private static AEVector3D postion;
    private static AEVector3D direction = new AEVector3D();
    private static int distX;
    private static int distY;
    private static int distZ;
    private final boolean guided;
    private Radar radar;

    public RocketGun(final Gun var1, final AbstractMesh var2, final boolean var3) {
        super(var1, (AbstractMesh)null);
        this.trail.setWidth(100);
        this.rocketMesh_ = var2;
        this.guided = var3;
        tempPos = new AEVector3D();
    }

    public final void OnRelease() {
        super.OnRelease();
        if (this.trail != null) {
            this.trail.OnRelease();
        }

        this.trail = null;
    }

    public final void render() {
        this.gun.renderSparks();
        if (this.gun.inAir) {
            this.trail.render();
            this.rocketMesh_.updateTransform(true);
            GlobalStatus.renderer.drawNodeInVF(this.rocketMesh_);
        }

    }

    public final void renderRocket_() {
        if (this.gun.inAir) {
            GlobalStatus.renderer.getCamera().getScreenPosition(this.rocketMesh_.getLocalPos(tempPos));
        }

    }

    public final void setRadar(final Radar var1) {
        this.radar = var1;
    }

    public final void update(final long var1) {
        this.gun.calcCharacterCollision(var1);
        tempPos.set(this.gun.projectilesPos[0]);
        if (this.gun.fired) {
            this.trail.reset(tempPos);
            this.gun.fired = false;
        }

        this.rocketMesh_.moveTo(tempPos);
        temp.set(this.gun.projectilesDir[0]);
        temp.normalize();
        this.rocketMesh_.getToParentTransform().setOrientation(temp);
        if (this.gun.inAir) {
            if (this.guided && this.gun.projectilesTimeLeft[0] < this.gun.range - 1500) {
                final int var2 = (int)var1;
                final RocketGun var8 = this;
                Player[] var3 = this.gun.getTargets();
                if (var3 != null) {
                    label74: {
                        if (this.radar == null) {
                            int var4 = -1;
                            int var5 = Integer.MAX_VALUE;

                            for(int var6 = 0; var6 < var3.length; ++var6) {
                                if (var3[var6].isActive() && !var3[var6].isDead() && !var3[var6].isAsteroid()) {
                                    postion = var8.gun.projectilesPos[0];
                                    tempPos = var3[var6].getPosition(tempPos);
                                    distX = postion.x - tempPos.x;
                                    distY = postion.y - tempPos.y;
                                    distZ = postion.z - tempPos.z;
                                    int var7;
                                    if (distX < 15000 && distX > -15000 && distY < 15000 && distY > -15000 && distZ < 15000 && distZ > -15000 && (var7 = distX * distX + distY * distY + distZ * distZ) < var5) {
                                        var4 = var6;
                                        var5 = var7;
                                    }
                                }
                            }

                            if (var4 < 0) {
                                break label74;
                            }

                            tempPos = var3[var4].getPosition(tempPos);
                        } else if (this.radar.getLockedEnemy() != null) {
                            tempPos = this.radar.getLockedEnemy().getPosition(tempPos);
                        }

                        temp.x = tempPos.x - var8.gun.projectilesPos[0].x;
                        temp.y = tempPos.y - var8.gun.projectilesPos[0].y;
                        temp.z = tempPos.z - var8.gun.projectilesPos[0].z;
                        direction.set(var8.gun.projectilesDir[0]);
                        temp.subtract(direction);
                        temp.scale(var2);
                        var8.gun.projectilesDir[0] = direction.add(temp, var8.gun.projectilesDir[0]);
                        var8.gun.projectilesDir[0].normalize();
                        var8.gun.projectilesDir[0].scale((int)(var8.gun.projectileSpeed * var2) << 12);
                        AEVector3D var10000 = var8.gun.projectilesDir[0];
                        var10000.x >>= 12;
                        var10000 = var8.gun.projectilesDir[0];
                        var10000.y >>= 12;
                        var10000 = var8.gun.projectilesDir[0];
                        var10000.z >>= 12;
                    }
                }
            }

            tempPos.set(this.gun.projectilesPos[0]);
            this.trail.update(tempPos);
            if (tempPos.z == 50000) {
                this.gun.inAir = false;
            }
        }

    }
}
