package GoF2;

import AE.AbstractGun;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.ITexture;
import AE.Math.AEVector3D;

public class ObjectGun extends AbstractMesh implements AbstractGun {
	protected Gun gun;
	private AbstractMesh[] projectiles;
	protected AbstractMesh rocketMesh_;
	protected static AEVector3D temp;

	public ObjectGun(final Gun var1, final AbstractMesh var2) {
		this.gun = var1;
		temp = new AEVector3D();
		if (var2 == null) {
			this.projectiles = null;
		} else {
			this.projectiles = new AbstractMesh[var1.projectilesPos.length];

			for(int var3 = 0; var3 < this.projectiles.length; ++var3) {
				temp.set(var2.getScale());
				this.projectiles[var3] = (AbstractMesh)var2.clone();
				this.projectiles[var3].setScale(temp.x, temp.y, temp.z);
				this.projectiles[var3].setRenderLayer(2);
			}

		}
	}

	public void OnRelease() {
		this.gun.OnRelease();
		this.gun = null;
	}

	public void render() {
		this.gun.renderSparks();
		if (this.gun.inAir && this.projectiles != null) {
			int var1 = 0;

			for(int var2 = 0; var2 < this.gun.projectilesPos.length; ++var2) {
				if (this.gun.projectilesPos[var2].x != 50000) {
					this.projectiles[var2].moveTo(this.gun.projectilesPos[var2]);
					temp.set(this.gun.projectilesDir[var2]);
					temp.normalize();
					this.projectiles[var2].getToParentTransform().setOrientation(temp);
					GlobalStatus.renderer.drawNodeInVF(this.projectiles[var2]);
				} else {
					++var1;
				}
			}

			if (var1 >= this.gun.projectilesTimeLeft.length) {
				this.gun.inAir = false;
			}
		}

	}

	public void update(final long var1) {
		this.gun.calcCharacterCollision(var1);
	}

	public final GraphNode clone() {
		return null;
	}

	public final void setTexture(final ITexture var1) {
	}
}
