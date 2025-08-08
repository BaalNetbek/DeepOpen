package GoF2;

import AE.AbstractMesh;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.Math.AEVector3D;
import AE.Math.AEMath;
import AE.PaintCanvas.AEGraphics3D;

/**  
 * Represents big ships
 * 
 * @author Fishlabs 2009
 */
public final class PlayerFixedObject extends KIPlayer {
    private BoundingVolume[] bounds;
    private long frametime;
    private boolean moving;
    private AEVector3D pos = new AEVector3D();
    private AEVector3D lasSetPos = new AEVector3D();
    private AEVector3D nearEnemyPos = new AEVector3D();
    private AEVector3D nearEnemyDistance = new AEVector3D();
    private final AEVector3D distToCamera = new AEVector3D();
    private Player nearEnemy;
    private int collidingBound;
    private boolean destroyed;
    private int unusedFloatingPartsLifeDonwCounter_;
    private AEVector3D[] postExposionPartPos;
    private AEVector3D[] postExplosionPartRot;
    private int posX;
    private int posY;
    private int posZ;

    public PlayerFixedObject(final int shipIdx, int race, final Player player, final AbstractMesh mesh, final int x, final int y, final int z) {
        super(shipIdx, race, player, mesh, x, y, z);
        this.pos.set(x, y, z);
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.moving = false;
        if (!Status.getMission().isCampaignMission() || Status.getCurrentCampaignMission() != 40 && Status.getCurrentCampaignMission() != 41) {
            final Generator g = new Generator();
            this.cargo = g.getLootList();
            if (this.cargo != null) {
                for(int i = 0; i < this.cargo.length; i += 2) {
                    if (shipIdx == 14) {
                        this.cargo[i + 1] *= 5 + GlobalStatus.random.nextInt(8);
                    } else {
                        this.cargo[i + 1] *= 1 + GlobalStatus.random.nextInt(4);
                    }
                }
            }
        } else {
            this.cargo = null;
        }

        this.player.invincible_ = true;
    }

    public final void OnRelease() {
        super.OnRelease();
        this.bounds = null;
        this.nearEnemy = null;
        this.pos = null;
        this.lasSetPos = null;
        this.nearEnemyPos = null;
        this.nearEnemyDistance = null;
    }

    public final void setMoving(final boolean var1) {
        this.moving = var1;
    }

    public final void setPosition(int var1, final int var2, final int var3) {
        this.posX = var1;
        this.posY = var2;
        this.posZ = var3;
        this.targetX = var1;
        this.targetY = var2;
        this.targetZ = var3;
        this.geometry.moveTo(var1, var2, var3);
        this.player.transform = this.geometry.getToParentTransform();
        this.pos = this.geometry.getLocalPos(this.pos);
        this.lasSetPos = this.geometry.getLocalPos(this.lasSetPos);
        if (this.bounds != null) {
            for(var1 = 0; var1 < this.bounds.length; ++var1) {
                this.bounds[var1].setPosition(this.player.transform.getPositionX(), this.player.transform.getPositionY(), this.player.transform.getPositionZ());
            }
        }

    }

    public final void setBounds(final BoundingVolume[] var1) {
        this.bounds = var1;
    }

    public final AEVector3D getPosition(final AEVector3D var1) {
        var1.set(this.posX, this.posY, this.posZ);
        return var1;
    }

    public final void moveForward(int var1) {
        this.posZ += var1;
        this.geometry.moveForward(var1);
        this.player.transform = this.geometry.getToParentTransform();
        this.pos = this.geometry.getLocalPos(this.pos);
        if (this.bounds != null) {
            for(var1 = 0; var1 < this.bounds.length; ++var1) {
                this.bounds[var1].setPosition(this.player.transform.getPositionX(), this.player.transform.getPositionY(), this.player.transform.getPositionZ());
            }
        }

    }

    public final void update(final long var1) {
        this.frametime = var1;
        this.player.update(var1);
        this.player.enemy = this.race != 8 && this.race != 9 ? Status.getStanding().isEnemy(this.race) : true;
        this.player.friend = this.race != 8 && this.race != 9 ? Status.getStanding().isFriend(this.race) : false;
        if (this.player.isAlwaysEnemy()) {
            this.player.enemy = true;
            this.player.friend = false;
        }

        if (this.player.isAlwaysFriend()) {
            this.player.friend = true;
            this.player.enemy = false;
        }

        if (this.state != 6) {
            float var3 = this.player.getBombForce();
            float var4 = this.player.setEmpForce_();
            if (var3 > 0.0F) {
                if ((var3 *= 0.98F) < 0.05F) {
                    var3 = 0.0F;
                }

                this.player.setBombForce(var3);
            }

            if (var4 > 0.0F) {
                this.stunned = true;
                if ((var4 -= var1) < 0.05F) {
                    var4 = 0.0F;
                    this.stunned = false;
                }

                this.player.setEmpForce_(var4);
            }
        }

        if (!this.stunned && this.moving && this.state != 3 && this.state != 4) {
            moveForward((int)var1);
        }

        this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
        this.position.set(this.posX, this.posY, this.posZ);
        this.position.subtract(this.tempVector_, this.distToCamera);
        int var6;
        int var9;
        var6 = this.distToCamera.getLength();
        if (var6 > AEGraphics3D.CLAMP_MID) {
            this.geometry.setTransform(this.geometry.getToParentTransform());
            this.distToCamera.normalize();
            this.distToCamera.scale(AEGraphics3D.CLAMP_MID);
            this.distToCamera.add(this.tempVector_);
            this.geometry.moveTo(this.distToCamera);
            var9 = (int)((float)AEGraphics3D.CLAMP_MID / var6 * AEMath.TO_Q);
            this.geometry.setScale(var9, var9, var9);
        } else {
            this.geometry.setScale(AEMath.Q_1, AEMath.Q_1, AEMath.Q_1);
            this.geometry.moveTo(this.posX, this.posY, this.posZ);
        }

        GraphNode var8;
        if (this.player.getHitpoints() <= 0 && this.state != 3 && this.state != 4) {
            if (this.player.enemy) {
                this.level.enemyDied(this.player.killedByKI);
            } else {
                this.level.friendDied();
            }

            this.state = 3;
            if (this.explosion != null) {
                this.position = this.geometry.getLocalPos(this.position);
                this.explosion.start(this.position.x, this.position.y, this.position.z);
            }

            this.hasCargo = cargoAvailable_();
            if (this.hasCargo) {
                createCrate(0);
            }

            for(var8 = this.geometry.getEndNode(); var8 != null; var8 = var8.getParent()) {
                if (var8.getID() == 13067 || var8.getID() == 13068 || var8.getID() == 13070 || var8.getID() == 13064 || var8.getID() == 13065 || var8.getID() == 13071) {
                    var8.setDraw(false);
                }
            }

            GlobalStatus.soundManager.playSfx(1);
            this.unusedFloatingPartsLifeDonwCounter_ = 10000;
            var9 = 0;

            for(var8 = this.geometry.getEndNode(); var8 != null; var8 = var8.getParent()) {
                ++var9;
            }

            this.destroyed = var9 > 3;
            this.postExposionPartPos = new AEVector3D[var9];
            this.postExplosionPartRot = new AEVector3D[var9];
            var6 = 0;

            for(GraphNode var5 = this.geometry.getEndNode(); var5 != null; var5 = var5.getParent()) {
                this.tempVector_ = var5.getToParentTransform().getPosition(this.tempVector_);
                this.tempVector_.normalize();
                this.tempVector_.scale(100 + GlobalStatus.random.nextInt(100));
                this.postExposionPartPos[var6] = new AEVector3D(this.tempVector_);
                this.postExplosionPartRot[var6] = new AEVector3D(-10 + GlobalStatus.random.nextInt(20), -10 + GlobalStatus.random.nextInt(20), -10 + GlobalStatus.random.nextInt(20));
                ++var6;
            }
        }

        boolean var11 = false;
        int var10;
        switch(this.state) {
        case 3:
            var11 = true;
            if (this.explosion != null) {
                if (this.explosion.isOver()) {
                    this.state = 4;
                }
            } else {
                this.state = 4;
            }
            break;
        case 4:
            var11 = true;
            this.crateLifeTime = (int)(this.crateLifeTime + var1);
            if (this.hasCargo && this.player.isActive() && this.waste != null) {
                var6 = (int)var1 >> 1;
                this.waste.rotateEuler(var6, var6, var6);
                if (this.crateLifeTime > 45000 && this.explosion != null) {
                    if (this.crateLifeTime < 90000) {
                        this.explosion.start(this.pos.x, this.pos.y, this.pos.z);
                        if (this.level.getPlayer().radar.tractorBeamTarget == this) {
                            this.level.getPlayer().radar.tractorBeamTarget = null;
                        }

                        this.crateLifeTime = 90000;
                    } else if (this.explosion.isOver()) {
                        this.waste = null;
                        this.crateLifeTime = 0;
                        setActive(false);
                    }
                }
            } else if (this.crateLifeTime > 45000) {
                setActive(false);
            }
            break;
        case 5:
            Player[] var12;
            if (this.state != 4 && this.state != 3 && (var12 = this.player.getEnemies()) != null) {
                this.nearEnemy = null;

                for(var10 = 0; var10 < var12.length; ++var10) {
                    if (var12[var10].isActive()) {
                        this.tempVector_ = var12[var10].getPosition(this.tempVector_);
                        if (this.pos.x - this.tempVector_.x < 50000 && this.pos.x - this.tempVector_.x > -50000 && this.pos.y - this.tempVector_.y < 50000 && this.pos.y - this.tempVector_.y > -50000 && this.pos.z - this.tempVector_.z < 50000 && this.pos.z - this.tempVector_.z > -50000) {
                            this.nearEnemy = this.player.getEnemy(var10);
                            this.tempVector_ = this.nearEnemy.getPosition(this.tempVector_);
                            this.nearEnemyPos.x = this.tempVector_.x;
                            this.nearEnemyPos.y = this.tempVector_.y;
                            this.nearEnemyPos.z = this.tempVector_.z;
                            break;
                        }
                    }
                }
            }

            this.nearEnemyDistance.x = this.nearEnemyPos.x - this.pos.x;
            this.nearEnemyDistance.y = this.nearEnemyPos.y - this.pos.y;
            this.nearEnemyDistance.z = this.nearEnemyPos.z - this.pos.z;
            if (this.nearEnemyDistance.x < 50000 && this.nearEnemyDistance.x > -50000 && this.nearEnemyDistance.y < 50000 && this.nearEnemyDistance.y > -50000 && this.nearEnemyDistance.z < 50000 && this.nearEnemyDistance.z > -50000) {
                this.state = 1;
                this.player.setActive(true);
            }
        }

        if (var11) {
            var6 = (int)var1 >> 1;
            this.unusedFloatingPartsLifeDonwCounter_ -= var6;
            if (this.unusedFloatingPartsLifeDonwCounter_ < 0) {
                this.unusedFloatingPartsLifeDonwCounter_ = 0;
            }

            var10 = 1;

            for(var8 = this.geometry.getEndNode(); var8 != null; var8 = var8.getParent()) {
                this.postExposionPartPos[var10 - 1].scale(AEMath.Q_1 - (int)var1);
                var8.translate(this.postExposionPartPos[var10 - 1]);
                var8.rotateEuler(this.postExplosionPartRot[var10 - 1].x, this.postExplosionPartRot[var10 - 1].y, this.postExplosionPartRot[var10 - 1].z);
                ++var10;
            }
        }

        this.player.posX = this.posX;
        this.player.posY = this.posY;
        this.player.posZ = this.posZ;
    }

    public final void appendToRender() {
        if (this.waste != null) {
            GlobalStatus.renderer.drawNodeInVF(this.waste);
        }

        if (this.state == 3 || this.state == 4) {
            if (this.explosion != null && !this.explosion.isOver()) {
                this.explosion.update(this.frametime);
            }

            if (this.destroyed) {
                GlobalStatus.renderer.drawNodeInVF(this.geometry);
                return;
            }
        }

        if (this.player.isActive()) {
            GlobalStatus.renderer.drawNodeInVF(this.geometry);
        }
    }

    public final boolean outerCollide(final int var1, final int var2, final int var3) {
        if ((this.state == 4) || (this.state == 4)) {
            return false;
        }
        for(int i = 0; i < this.bounds.length; ++i) {
            if (this.bounds[i].outerCollide_(var1, var2, var3)) {
                return true;
            }
        }

        return false;
    }

    public final AEVector3D getProjectionVector_(final AEVector3D var1) {
        return this.bounds != null ? this.bounds[this.collidingBound].getProjectionVector(var1) : null;
    }

    public final boolean outerCollide(final AEVector3D var1) {
        final int var4 = var1.z;
        final int var3 = var1.y;
        final int var2 = var1.x;
        if (this.state != 4) {
            for(int i = 0; i < this.bounds.length; ++i) {
                if (this.bounds[i].isPointInBounding(var2, var3, var4)) {
                    this.collidingBound = i;
                    return true;
                }
            }
        }

        return false;
    }
}
