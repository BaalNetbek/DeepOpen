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

			for(int i = 0; i < this.gun.projectilesPos.length; ++i) {
				if (this.gun.projectilesPos[i].x != 50000) {
					this.projectiles[i].moveTo(this.gun.projectilesPos[i]);
					temp.set(this.gun.projectilesDir[i]);
					temp.normalize();
					this.projectiles[i].getToParentTransform().setOrientation(temp);
					GlobalStatus.renderer.drawNodeInVF(this.projectiles[i]);
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
