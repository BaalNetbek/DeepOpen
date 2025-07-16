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

    public final void update(final long dt) {
        this.gun.calcCharacterCollision(dt);
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
                Player[] targets = this.gun.getTargets();
                if (targets != null) {
                    label74: {
                        if (this.radar == null) {
                            int var4 = -1;
                            int var5 = Integer.MAX_VALUE;

                            for(int i = 0; i < targets.length; ++i) {
                                if (targets[i].isActive() && !targets[i].isDead() && !targets[i].isAsteroid()) {
                                    postion = this.gun.projectilesPos[0];
                                    tempPos = targets[i].getPosition(tempPos);
                                    distX = postion.x - tempPos.x;
                                    distY = postion.y - tempPos.y;
                                    distZ = postion.z - tempPos.z;
                                    int var7;
                                    if (distX < 15000 && distX > -15000 && distY < 15000 && distY > -15000 && distZ < 15000 && distZ > -15000 && (var7 = distX * distX + distY * distY + distZ * distZ) < var5) {
                                        var4 = i;
                                        var5 = var7;
                                    }
                                }
                            }

                            if (var4 < 0) {
                                break label74;
                            }

                            tempPos = targets[var4].getPosition(tempPos);
                        } else if (this.radar.getLockedEnemy() != null) {
                            tempPos = this.radar.getLockedEnemy().getPosition(tempPos);
                        }

                        temp.x = tempPos.x - this.gun.projectilesPos[0].x;
                        temp.y = tempPos.y - this.gun.projectilesPos[0].y;
                        temp.z = tempPos.z - this.gun.projectilesPos[0].z;
                        direction.set(this.gun.projectilesDir[0]);
                        temp.subtract(direction);
                        temp.scale((int)dt);
                        this.gun.projectilesDir[0] = direction.add(temp, this.gun.projectilesDir[0]);
                        this.gun.projectilesDir[0].normalize();
                        this.gun.projectilesDir[0].scale((int)(this.gun.projectileSpeed * (int)dt) << 12);
                        this.gun.projectilesDir[0].x >>= 12;
                        this.gun.projectilesDir[0].y >>= 12;
                        this.gun.projectilesDir[0].z >>= 12;
                    }
                }
            }

            tempPos.set(this.gun.projectilesPos[0]);
            this.trail.update(tempPos);
            if (tempPos.z == 50000) { // >= better or not? it has to do sth with last lines of Gun.calcCharacterCollision()
                this.gun.inAir = false;
            }
        }

    }
}
