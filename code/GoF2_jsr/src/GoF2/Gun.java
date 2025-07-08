package GoF2;

import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

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

	public final void setPosition(final int var1, final int var2) {
		final short[] var3 = MUZZLE_POSITIONS[var2 - 1][var1];
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

	public final boolean shoot(Matrix var1, final long var2, final boolean var4) {
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
				this.projectilesDir[i].scale((int)(this.projectileSpeed * var2) << 12);
				this.projectilesDir[i].x >>= 12;
				this.projectilesDir[i].y >>= 12;
				this.projectilesDir[i].z >>= 12;
				this.projectilesTimeLeft[i] = this.range;
				if (!this.friendGun && this.ammo > 0 && gunItem != null && gunItem.getType() == 1) {
					--this.ammo;
					gunItem.changeAmount(-1);
					if (this.ammo == 0) {
						Status.getShip().removeSimilarEquipment(gunItem);
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
			for(int var1 = 0; var1 < this.targets.length; ++var1) {
				this.tempTarget = this.targets[var1];
				if ((this.subType != Item.EMP_BOMB || !this.tempTarget.isAsteroid()) && this.tempTarget.isActive()) {
					for(int var2 = 0; var2 < this.projectilesPos.length; ++var2) {
						this.tempPos.set(this.projectilesPos[var2]);
						this.tempVector = this.tempTarget.transform.getPosition(this.tempVector);
						this.tempVector.subtract(this.tempPos);
						int var3 = this.tempVector.getLength();
						if (var3 < this.magnitude) {
							float var6;
							if ((var6 = (float)(this.magnitude - var3) / (float)this.magnitude) > 1.0F) {
								var6 = 1.0F;
							}

							final int var4 = Globals.getItems()[this.index].getAttribute(10);
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
							this.projectilesTimeLeft[var2] = -1;
							this.tempVector.set(this.projectilesDir[var2]);
							this.tempVector.normalize();
						}
					}
				}
			}
		}

		final boolean var5 = this.subType == Item.NUKE;
		this.level.flashScreen(var5 ? 3 : 4);
		GlobalStatus.soundManager.playSfx(var5 ? 11 : 12);
		if (this.sparks != null) {
			this.sparks.explode(this.projectilesPos[0]);
		}

	}

	public final void calcCharacterCollision(final long var1) {
		this.timeSinceLastShot = (int)(this.timeSinceLastShot + var1);
		if (this.sparks != null) {
			this.sparks.update(var1);
		}

		if (this.inAir) {
			if (this.targets != null) {
				boolean var7 = this.subType == Item.NUKE || this.subType == Item.EMP_BOMB;
				final boolean var8 = var7 || this.subType == Item.ROCKET || this.subType == Item.TORPEDO;

				label96:
					for(int var9 = 0; var9 < this.targets.length; ++var9) {
						this.tempTarget = this.targets[var9];
						if (this.tempTarget.isActive()) {
							for(int var10 = 0; var10 < this.projectilesPos.length; ++var10) {
								this.tempPos.set(this.projectilesPos[var10]);
								this.tempDir.set(this.projectilesDir[var10]);
								int var4;
								int var5;
								int var13;
								if (this.tempTarget.invincible_) {
									var4 = this.tempTarget.posX - this.tempPos.x + this.tempDir.x;
									var5 = this.tempTarget.posY - this.tempPos.y + this.tempDir.y;
									var13 = this.tempTarget.posZ - this.tempPos.z + this.tempDir.z;
								} else {
									var4 = this.tempTarget.transform.getPositionX() - this.tempPos.x + this.tempDir.x;
									var5 = this.tempTarget.transform.getPositionY() - this.tempPos.y + this.tempDir.y;
									var13 = this.tempTarget.transform.getPositionZ() - this.tempPos.z + this.tempDir.z;
								}

								final int var11 = (int)this.tempTarget.radius;
								if (var4 < var11 && var4 > -var11 && var5 < var11 && var5 > -var11 && var13 < var11 && var13 > -var11) {
									if (var8 && this.tempTarget.isAsteroid()) {
										this.tempTarget.damageHP(9999, false);
										break label96;
									}

									if (var7) {
										this.ignite();
										break label96;
									}

									var4 = Globals.getItems()[this.index].getAttribute(10);
									if (var4 != Item.NULL_ATTRIBUTE) {
										this.tempTarget.damageEmp(var4, this.friendGun);
									}

									this.tempTarget.damageHP(this.damage, this.friendGun);
									this.tempTarget.setHitVector_(-this.tempDir.x, -this.tempDir.y, -this.tempDir.z);
									this.projectilesTimeLeft[var10] = -1;
									this.tempVector.set(this.projectilesDir[var10]);
									this.tempVector.normalize();
									if (this.sparks != null) {
										this.sparks.explode(this.projectilesPos[var10]);
									}
								}
							}
						}
					}
			}

			for(int var12 = 0; var12 < this.projectilesPos.length; ++var12) {
				if (this.projectilesTimeLeft[var12] > 0) {
					final int[] var10000 = this.projectilesTimeLeft;
					var10000[var12] -= (int)var1;
					this.projectilesPos[var12].add(this.projectilesDir[var12]);
					if (this.projectilesTimeLeft[var12] <= 0 && (this.subType == Item.NUKE || this.subType == Item.EMP_BOMB)) {
						ignite();
					}
				} else {
					this.projectilesPos[var12].set(50000, 50000, 50000);
					this.projectilesDir[var12].set(0, 0, 0);
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
