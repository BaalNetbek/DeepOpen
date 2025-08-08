package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;

public final class PlayerAsteroid extends KIPlayer {
	private static AEVector3D tempVector1_ = new AEVector3D();
	private static AEVector3D tempVector2_ = new AEVector3D();
	private int frametime;
	private int posX;
	private int posY;
	private int posZ;
	private final boolean clampDisabled;
	private final int baseScaleX;
	private final int baseScaleY;
	private final int baseScaleZ;
	private int passedTime;
	private static AEVector3D center_;
	private boolean rotationEnabled;
	private AEMatrix playerTransform_ = new AEMatrix();
	private int sizeCoef_;
	private int tier;
	private AbstractMesh explosionMesh;
	public boolean clampedByDistance;
	public int oreItemId;

	public PlayerAsteroid(final int meshId, final AbstractMesh mesh, final int ore, final boolean var4, final int var5, final int var6, final int var7) {
		super(meshId, -1, new Player(1500.0F, 30, 0, 0, 0), mesh, var5, var6, var7);
		this.player.setKIPlayer(this);
		this.oreItemId = ore;
		this.posX = var5;
		this.posY = var6;
		this.posZ = var7;
		tempVector2_.set(var5, var6, var7);
		this.clampDisabled = var4;
		this.clampedByDistance = false;
		this.baseScaleX = AEMath.Q_QUARTER + GlobalStatus.random.nextInt(2448);
		this.baseScaleY = AEMath.Q_QUARTER + GlobalStatus.random.nextInt(2448);
		this.baseScaleZ = AEMath.Q_QUARTER + GlobalStatus.random.nextInt(2448);
		this.sizeCoef_ = (int)((this.baseScaleX + this.baseScaleY + this.baseScaleZ) / 3.0f / AEMath.Q_THREE_QUARTERS * 100.0F);
		this.player.setMaxHP(30 + (int)(this.sizeCoef_ / 100.0F * 100.0F));
		this.tier = AEMath.min(7, 2 + (int)((this.sizeCoef_ + 15) / 100.0F * 5.0F));
		this.tempVector_.set(-AEMath.Q_PI + GlobalStatus.random.nextInt(AEMath.Q_PI_2), -AEMath.Q_PI + GlobalStatus.random.nextInt(AEMath.Q_PI_2), -AEMath.Q_PI + GlobalStatus.random.nextInt(8192));
		this.tempVector_.normalize();
		this.mainMesh_.getToParentTransform().setOrientation(this.tempVector_);
		this.mainMesh_.moveTo(tempVector2_);
		this.mainMesh_.updateTransform(true);
		this.mainMesh_.setAnimationRangeInTime(ore - 154, ore - 154);
		this.mainMesh_.disableAnimation();
		this.rotationEnabled = true;
		this.isAsteroid = true;
		this.hasCargo = true;
		this.state = 0;
	}

	public final void OnRelease() {
		super.OnRelease();
		this.explosionMesh = null;
	}

	public final int getMass_SizeCoef__() {
		return this.sizeCoef_;
	}

	public final int getQuality() {
		return this.tier;
	}

	public final int getQualityFrameIndex() {
		return 7 - this.tier;
	}

	public final void setAsteroidCenter(final AEVector3D var1) {
		center_ = var1;
		if (this.clampDisabled) {
			scaleOnDistanceClamp_();
		}

	}

	public final void setRotationEnabled(final boolean var1) {
		this.rotationEnabled = var1;
	}

	public final void update(final long var1) {
		this.frametime = (int)var1;
		if (!this.player.isActive()) {
			this.clampedByDistance = false;
		} else {
			this.passedTime += this.frametime;
			int var6;
			int var7;
			if (this.player.getHitpoints() <= 0 && this.state == 0) {
				tempVector2_ = this.mainMesh_.getPosition(tempVector2_);
				if (this.explosionMesh == null) {
					this.explosionMesh = AEResourceManager.getGeometryResource(6782);
					this.explosionMesh.setRenderLayer(2);
					this.explosionMesh.setAnimationSpeed(100);
					this.player.setKIPlayer(this);
				}

				this.explosionMesh.setTransform(this.mainMesh_.getToParentTransform());
				this.explosionMesh.setAnimationMode((byte)1);
				this.player.transform = this.explosionMesh.getToParentTransform();
				tempVector2_.subtract(this.level.getPlayer().getPosition());
				var7 = AEMath.min(40000, tempVector2_.getLength());
				final float var5 = 1.0F - var7 / 40000.0F;
				var6 = GlobalStatus.soundManager.getMusicVolume();
				GlobalStatus.soundManager.playSfx(5, (int)(var6 * var5));
				this.explosion.start(this.mainMesh_.getPosX(), this.mainMesh_.getPosY(), this.mainMesh_.getPosZ());
				this.state = 3;
				this.level.asteroidDied();
				this.passedTime = 0;
				++Status.asteroidsDestroyed;
				if (this.hasCargo && (this.tier == 7 && GlobalStatus.random.nextInt(100) < 40 || this.tier < 7 && GlobalStatus.random.nextInt(100) < 20) && this.oreItemId != 164) {
					this.cargo = new int[2];
					this.cargo[0] = this.oreItemId + (this.tier == 7 ? 11 : 0);
					this.cargo[1] = this.tier == 7 ? 1 : 1 + GlobalStatus.random.nextInt(3);
					createCrate(1);
				} else {
					this.hasCargo = false;
				}
			} else {
				if (this.state == 4) {
					setActive(false);
				} else if (this.state != 3 && this.rotationEnabled) {
					this.mainMesh_.roll((int)var1 >> 2);
				}

				float var3 = this.player.getBombForce();
				if (var3 > 0.0F) {
					this.tempVector_.set(this.player.getHitVector());
					this.tempVector_.scale((int)(AEMath.Q_QUARTER * var3 * (1.0F - AEMath.min(60, this.sizeCoef_) / 100.0F)));
					this.mainMesh_.translate(this.tempVector_);
					this.posX += this.tempVector_.x;
					this.posY += this.tempVector_.y;
					this.posZ += this.tempVector_.z;
					this.mainMesh_.rotateEuler((int)var1 >> 1, (int)var1 >> 1, (int)var1 >> 1);
					if ((var3 *= 0.98F) < 0.05F) {
						var3 = 0.0F;
					}

					this.player.setBombForce(var3);
				}

				this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
				this.position.set(this.posX, this.posY, this.posZ);
				this.position.subtract(this.tempVector_, tempVector1_);
				int var4 = tempVector1_.getLength();
				if (!this.clampDisabled) {
					if (var4 > 30000) {
						tempVector1_.normalize();
						tempVector1_.scale(30000);
						tempVector1_.add(this.tempVector_);
						this.mainMesh_.moveTo(tempVector1_);
						float var2;
						var4 = (int)((var2 = 30000.0F / var4) * this.baseScaleX);
						var7 = (int)(var2 * this.baseScaleY);
						var6 = (int)(var2 * this.baseScaleZ);
						this.mainMesh_.setScale(var4, var7, var6);
						this.clampedByDistance = true;
					} else {
						this.mainMesh_.setScale(this.baseScaleX, this.baseScaleY, this.baseScaleZ);
						this.mainMesh_.moveTo(this.posX, this.posY, this.posZ);
						this.clampedByDistance = false;
					}
				} else if (var4 > 35000 && this.passedTime > 10000) {
					var6 = GlobalStatus.random.nextInt(20000) - 10000;
					var4 = GlobalStatus.random.nextInt(20000) - 10000;
					var7 = GlobalStatus.random.nextInt(2000) + 30000;
					this.passedTime = 0;
					tempVector1_.set(var6, var4, var7);
					if (this.level.getPlayer() != null) {
						this.playerTransform_ = this.level.getPlayer().shipGrandGroup_.getLocalTransform();
					} else {
						this.playerTransform_.identity();
					}

					this.position = this.playerTransform_.transformVector2(tempVector1_, this.position);
					this.mainMesh_.moveTo(this.position);
					this.posX = this.position.x;
					this.posY = this.position.y;
					this.posZ = this.position.z;
					setActive(true);
					scaleOnDistanceClamp_();
				}

				if (this.explosionMesh != null) {
					this.explosionMesh.setTransform(this.mainMesh_.getToParentTransform());
				}

			}
		}
	}

	private void scaleOnDistanceClamp_() {
		this.tempVector_.set(this.posX, this.posY, this.posZ);
		this.position.set(center_);
		this.position.subtract(this.tempVector_, tempVector1_);
		final float var1 = AEMath.max(0, 100000 - tempVector1_.getLength()) / 100000.0F;
		final int var2 = (int)(this.baseScaleX * var1);
		final int var3 = (int)(this.baseScaleY * var1);
		final int var4 = (int)(this.baseScaleZ * var1);
		if (var2 + var3 + var4 < AEMath.Q_SIXTEENTH) {
			this.sizeCoef_ = 0;
			this.tier = 0;
			setActive(false);
		} else {
			this.mainMesh_.setScale(var2, var3, var4);
			this.sizeCoef_ = (int)((var2 + var3 + var4) / 3 / (float)AEMath.Q_THREE_QUARTERS * 100.0F);
			this.tier = AEMath.min(7, 2 + (int)((this.sizeCoef_ + 15) / 100.0F * 5.0F));
		}
	}

	public final void appendToRender() {
		if (this.tier > 0) {
			if (this.waste != null) {
				GlobalStatus.renderer.drawNodeInVF(this.waste);
			}

			if (this.state == 3) {
				GlobalStatus.renderer.drawNodeInVF(this.explosionMesh);
				if (this.passedTime > 3000) {
					this.state = 4;
				}
			} else if (this.state == 0) {
				GlobalStatus.renderer.drawNodeInVF(this.mainMesh_);
			}
		}

	}

	public final boolean outerCollide(final AEVector3D var1) {
		return this.outerCollide(var1.x, var1.y, var1.z);
	}

	public final AEVector3D getProjectionVector_(final AEVector3D var1) {
		(tempVector2_ = this.mainMesh_.getPosition(tempVector2_)).subtract(var1);
		var1.set(tempVector2_);
		var1.normalize();
		return var1;
	}

	public final boolean outerCollide(final int var1, final int var2, final int var3) {
		return var1 - this.posX < this.player.radius && var1 - this.posX > -this.player.radius && var2 - this.posY < this.player.radius && var2 - this.posY > -this.player.radius && var3 - this.posZ < this.player.radius && var3 - this.posZ > -this.player.radius;
	}
}
