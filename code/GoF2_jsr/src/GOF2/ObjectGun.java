package GOF2;

import AE.AbstractGun;
import AE.AEGeometry;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.ITexture;
import AE.Math.AEVector3D;

public class ObjectGun extends AEGeometry implements AbstractGun {
	protected Gun gun;
	private AEGeometry[] projectiles;
	protected AEGeometry rocketMesh_;
	protected static AEVector3D temp;

	public ObjectGun(final Gun gun, final AEGeometry mesh) {
		this.gun = gun;
		temp = new AEVector3D();
		if (mesh == null) {
			this.projectiles = null;
		} else {
			this.projectiles = new AEGeometry[gun.projectilesPos.length];

			for(int i = 0; i < this.projectiles.length; ++i) {
				temp.set(mesh.getScale());
				this.projectiles[i] = (AEGeometry)mesh.clone();
				this.projectiles[i].setScale(temp.x, temp.y, temp.z);
				this.projectiles[i].setRenderLayer(2);
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

	public void update(final long dt) {
		this.gun.calcCharacterCollision(dt);
	}

	public final GraphNode clone() {
		return null;
	}

	public final void setTexture(final ITexture var1) {
	}
}
