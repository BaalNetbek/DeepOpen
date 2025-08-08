package GoF2;

import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEMath;

public final class PlayerJunk extends KIPlayer {
    private long frameTime;

    public PlayerJunk(int meshId, final Player player, final AbstractMesh mesh, final int x, final int y, final int z) {
        super(9996, -1, player, mesh, x, y, z);
        this.player.transform = mesh.getToParentTransform();
        int scale = AEMath.Q_1 + GlobalStatus.random.nextInt(AEMath.Q_2);
        mesh.setScale(scale, scale, scale);
        this.junk = true;
    }

    public final void update(final long var1) {
        this.frameTime = var1;
        if (this.player.getHitpoints() <= 0 && this.state != 3 && this.state != 4) {
            this.level.junkDied();
            this.state = 3;
            if (GlobalStatus.random.nextInt(100) < 10) {
                this.hasCargo = true;
                this.cargo = new int[2];
                this.cargo[1] = 1 + GlobalStatus.random.nextInt(10);
                this.cargo[0] = 99;
                createCrate(2);
                this.hasCargo = true;
            } else {
                this.player.setActive(false);
                if (this.level.getPlayer().radar.tractorBeamTarget == this) {
                    this.level.getPlayer().radar.tractorBeamTarget = null;
                }
            }

            if (this.explosion != null) {
                this.position = this.mainMesh_.getLocalPos(this.position);
                this.explosion.start(this.position.x, this.position.y, this.position.z);
            }
        }

        switch(this.state) {
        case 3:
            if (this.explosion == null || this.explosion.isOver()) {
                this.state = 4;
            }
        default:
        }
    }

    public final void appendToRender() {
        if (this.waste != null) {
            GlobalStatus.renderer.drawNodeInVF(this.waste);
        }

        if (this.state != 4 && this.state != 3) {
            super.appendToRender();
        }

        if (this.state == 3 && this.explosion != null) {
            this.explosion.update(this.frameTime);
        }

    }

    public final boolean outerCollide(final int var1, final int var2, final int var3) {
        return false;
    }
}
