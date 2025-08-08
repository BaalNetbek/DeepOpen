package GoF2;

import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;
import AE.Math.AEMath;

public final class Gun {
	private static final short[][][] MUZZLE_POSITIONS = {
	      {
	            {0, 0, 0}},
	      {
	            {270, 0, 100},
	            {-270, 0, 100}},
	      {
	            {320, 0, 100},
	            {0, 0, 100},
	            {-320, 0, 100}},
	      {
	            {370, 0, 100},
	            {170, 100, 150},
	            {-170, 100, 150},
	            {-370, 0, 100}},
	      {
	            {350, 100, 200},
	            {270, -100, 150},
	            {0, 0, 100},
	            {-270, -100, 150},
	            {-350, 100, 200}},
	      {
	            {500, 100, 150},
	            {500, -100, 150},
	            {200, 0, 100},
	            {-200, 0, 100},
	            {-500, -100, 150},
	            {-500, 100, 150}},
	      {
	            {500, -100, 150},
	            {400, 100, 150},
	            {200, -50, 100},
	            {0, 0, 150},
	            {-200, -50, 100},
	            {-400, 100, 150},
	            {-500, -100, 150}},
	      {
	            {550, -100, 100},
	            {550, 100, 100},
	            {300, 0, 100},
	            {100, 0, 150},
	            {-100, 0, 150},
	            {-300, 0, 100},
	            {-550, 100, 100},
	            {-550, -100, 100}},
	      {
	            {550, -100, 100},
	            {550, 100, 100},
	            {300, 0, 100},
	            {100, 100, 150},
	            {0, -100, 100},
	            {-100, 100, 150},
	            {-300, 0, 100},
	            {-550, 100, 100},
	            {-550, -100, 100}},
	      {
	            {600, -120, 100},
	            {600, 120, 100},
	            {400, 0, 100},
	            {200, 100, 150},
	            {200, -100, 100},
	            {-200, -100, 100},
	            {-200, 100, 150},
	            {-400, 0, 100},
	            {-600, 120, 100},
	            {-600, -120, 100}}
        	};
	private Level level;
	private Player[] targets;
	public AEVector3D[] projectilesPos;
	AEVector3D[] projectilesDir;
	private AEVector3D tempPos;
	private AEVector3D tempDir;
	private Player tempTarget;
	public int[] projectilesTimeLeft;
	private final int equipmentId;
	private final int damage;
	public int ammo;
	public float projectileSpeed;
	public int range;
	public AEVector3D muzzleOffset;
	public boolean fired;
	private AEVector3D tempVector;
	public int timeSinceLastShot;
	public int reloadTimeMilis;
	boolean inAir;
	private final boolean unused547_;
	/** Item index */
	public int index;
	public int subType;
	private int magnitude;
	private Sparks sparks;
	private final boolean unused5de_;
	private boolean friendGun;
	private int spread;

	public Gun(int equipmentIdx, final int dmg, final int maxInAir, int ammo, final int range, final int reloadT, final float velocity, final AEVector3D offset, final AEVector3D var9) {
		this.damage = dmg;
		this.projectileSpeed = velocity;
		this.equipmentId = equipmentIdx;
		this.muzzleOffset = offset;
		this.range = range;
		this.reloadTimeMilis = reloadT;
		this.timeSinceLastShot = 0;
		if (ammo < 0) {
			ammo = Integer.MAX_VALUE;
		}

		this.ammo = ammo;
		this.projectilesTimeLeft = new int[maxInAir];
		this.projectilesPos = new AEVector3D[maxInAir];
		this.projectilesDir = new AEVector3D[maxInAir];
		this.tempPos = new AEVector3D();
		this.tempDir = new AEVector3D();

		for(equipmentIdx = 0; equipmentIdx < maxInAir; ++equipmentIdx) {
			this.projectilesPos[equipmentIdx] = new AEVector3D(50000, 0, 0);
			this.projectilesDir[equipmentIdx] = new AEVector3D();
			this.projectilesTimeLeft[equipmentIdx] = -1;
		}

		this.unused547_ = true;
		this.fired = false;
		this.tempVector = new AEVector3D();
		this.inAir = false;
		this.sparks = null;
		this.targets = null;
		this.unused5de_ = false;
		this.friendGun = false;
		this.spread = 0;
	}

	public final void setSpread(final int var1) {
		this.spread = 1000;
	}

	public final void setFriendGun(final boolean var1) {
		this.friendGun = true;
	}

	public final void setMagnitude(final int var1) {
		this.magnitude = var1;
	}

	public final int getMagnitude() {
		return this.magnitude;
	}

	public final void setOffset(final int gunIdx, final int gunsEquippedCount) {
		final short[] var3 = MUZZLE_POSITIONS[gunsEquippedCount - 1][gunIdx];
		this.tempVector.set(this.muzzleOffset);
		this.muzzleOffset = new AEVector3D(var3[0] + this.tempVector.x, var3[1] + this.tempVector.y, var3[2] + this.tempVector.z);
	}

	public final void OnRelease() {
		this.level = null;
		this.targets = null;
		this.projectilesPos = null;
		this.projectilesDir = null;
		this.tempPos = null;
		this.tempDir = null;
		this.tempTarget = null;
		this.projectilesTimeLeft = null;
		this.muzzleOffset = null;
		this.sparks = null;
	}

	public final void setSparks(final Sparks var1) {
		this.sparks = var1;
	}

	public final void setLevel(final Level var1) {
		this.level = var1;
	}

	public final void setTargets(final Player[] var1) {
		this.targets = var1;
	}

	public final Player[] getTargets() {
		return this.targets;
	}

	public final boolean shoot(AEMatrix var1, final long var2, final boolean var4) {
		this.timeSinceLastShot = 0;
		this.fired = true;
		this.inAir = true;
		Item gunItem = Status.getShip().getEquipment()[this.equipmentId];
		if (!this.friendGun && ((gunItem == null) || (gunItem.getAmount() == 0))) {
			return false;
		}
		for(int i = 0; i < this.projectilesPos.length; ++i) {
			if (this.projectilesTimeLeft[i] <= 0) {
				this.projectilesPos[i].set(var1.getPosition(this.tempVector));
				if (this.muzzleOffset != null) {
					this.tempVector.set(this.muzzleOffset);
					this.tempPos = var1.transformVectorNoScale(this.tempVector, this.tempPos);
					this.projectilesPos[i].add(this.tempPos);
				}

				this.tempVector.set(var1.getDirection(this.tempVector));
				this.projectilesDir[i].set(this.tempVector);
				if (this.spread > 0) {
					this.projectilesDir[i].x += -(this.spread >> 1) + GlobalStatus.random.nextInt(this.spread);
					this.projectilesDir[i].y += -(this.spread >> 1) + GlobalStatus.random.nextInt(this.spread);
					this.projectilesDir[i].z += -(this.spread >> 1) + GlobalStatus.random.nextInt(this.spread);
				}

				this.projectilesDir[i].normalize();
				this.projectilesDir[i].scale((int)(this.projectileSpeed * var2) << AEMath.Q);
				this.projectilesDir[i].x >>= AEMath.Q;
				this.projectilesDir[i].y >>= AEMath.Q;
				this.projectilesDir[i].z >>= AEMath.Q;
				this.projectilesTimeLeft[i] = this.range;
				if (!this.friendGun && this.ammo > 0 && gunItem != null && gunItem.getType() == 1) {
					--this.ammo;
					gunItem.changeAmount(-1);
					if (this.ammo == 0) {
						Status.getShip().freeSlot(gunItem);
					}
				}

				return true;
			}
		}

		return false;
	}

	public final void ignite() {
		if (this.subType == Item.NUKE) {
			++Status.bombsUsed;
		}

		if (this.targets != null) {
			for(int i = 0; i < this.targets.length; ++i) {
				this.tempTarget = this.targets[i];
				if ((this.subType != Item.EMP_BOMB || !this.tempTarget.isAsteroid()) && this.tempTarget.isActive()) {
					for(int j = 0; j < this.projectilesPos.length; ++j) {
						this.tempPos.set(this.projectilesPos[j]);
						this.tempVector = this.tempTarget.transform.getPosition(this.tempVector);
						this.tempVector.subtract(this.tempPos);
						int var3 = this.tempVector.getLength();
						if (var3 < this.magnitude) {
							float var6;
							if ((var6 = (float)(this.magnitude - var3) / (float)this.magnitude) > 1.0F) {
								var6 = 1.0F;
							}

							final int var4 = Globals.getItems()[this.index].getAttribute(Item.EMP_DAMAGE);
							if (this.subType == Item.NUKE) {
								if (var4 != Item.NULL_ATTRIBUTE) {
									this.tempTarget.setEmpForce_(var4 * var6);
								}

								this.tempTarget.setBombForce(var6);
								if (this.tempTarget.isAsteroid()) {
									var6 *= 0.6F;
								}

								this.tempTarget.damageHP((int)(this.damage * var6), this.friendGun);
							} else {
								this.tempTarget.damageEmp((int)(var4 * var6), this.friendGun);
							}

							this.tempVector.normalize();
							this.tempTarget.setHitVector_(this.tempVector.x, this.tempVector.y, this.tempVector.z);
							this.projectilesTimeLeft[j] = -1;
							this.tempVector.set(this.projectilesDir[j]);
							this.tempVector.normalize();
						}
					}
				}
			}
		}

		final boolean isNuke = this.subType == Item.NUKE;
		this.level.flashScreen(isNuke ? Explosion.HUGE : Explosion.EMP);
		GlobalStatus.soundManager.playSfx(isNuke ? 11 : 12);
		if (this.sparks != null) {
			this.sparks.explode(this.projectilesPos[0]);
		}

	}

	public final void calcCharacterCollision(final long dt) {
		this.timeSinceLastShot = (int)(this.timeSinceLastShot + dt);
		if (this.sparks != null) {
			this.sparks.update(dt);
		}

		if (this.inAir) {
			if (this.targets != null) {
				final boolean isBomb = this.subType == Item.NUKE || this.subType == Item.EMP_BOMB;
				final boolean isRocket = isBomb || this.subType == Item.ROCKET || this.subType == Item.TORPEDO;

				label96:
					for(int i = 0; i < this.targets.length; ++i) {
						this.tempTarget = this.targets[i];
						if (this.tempTarget.isActive()) {
							for(int j = 0; j < this.projectilesPos.length; ++j) {
								this.tempPos.set(this.projectilesPos[j]);
								this.tempDir.set(this.projectilesDir[j]);
								int x, y, z;
								if (this.tempTarget.invincible_) {
									x = this.tempTarget.posX - this.tempPos.x + this.tempDir.x;
									y = this.tempTarget.posY - this.tempPos.y + this.tempDir.y;
									z = this.tempTarget.posZ - this.tempPos.z + this.tempDir.z;
								} else {
									x = this.tempTarget.transform.getPositionX() - this.tempPos.x + this.tempDir.x;
									y = this.tempTarget.transform.getPositionY() - this.tempPos.y + this.tempDir.y;
									z = this.tempTarget.transform.getPositionZ() - this.tempPos.z + this.tempDir.z;
								}

								final int r = (int)this.tempTarget.radius;
								if (x < r && x > -r && y < r && y > -r && z < r && z > -r) {
									if (isRocket && this.tempTarget.isAsteroid()) {
										this.tempTarget.damageHP(9999, false);
										break label96;
									}

									if (isBomb) {
										this.ignite();
										break label96;
									}

									x = Globals.getItems()[this.index].getAttribute(Item.EMP_DAMAGE);
									if (x != Item.NULL_ATTRIBUTE) {
										this.tempTarget.damageEmp(x, this.friendGun);
									}

									this.tempTarget.damageHP(this.damage, this.friendGun);
									this.tempTarget.setHitVector_(-this.tempDir.x, -this.tempDir.y, -this.tempDir.z);
									this.projectilesTimeLeft[j] = -1;
									this.tempVector.set(this.projectilesDir[j]);
									this.tempVector.normalize();
									if (this.sparks != null) {
										this.sparks.explode(this.projectilesPos[j]);
									}
								}
							}
						}
					}
			}

			for(int i = 0; i < this.projectilesPos.length; ++i) {
				if (this.projectilesTimeLeft[i] > 0) {
					this.projectilesTimeLeft[i] -= dt;
					this.projectilesPos[i].add(this.projectilesDir[i]);
					if (this.projectilesTimeLeft[i] <= 0 && (this.subType == Item.NUKE || this.subType == Item.EMP_BOMB)) {
						this.ignite();
					}
				} else {
					this.projectilesPos[i].set(50000, 50000, 50000);
					this.projectilesDir[i].set(0, 0, 0);
				}
			}
		}

	}

	public final void renderSparks() {
		if (this.sparks != null) {
			this.sparks.render();
		}

	}
}
