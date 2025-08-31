package GOF2;

import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;

public final class Player {
	public Gun[][] guns;
	private Player[] coPlayers;
	float radius;
	private int empPoints;
	private int maxEmpPoints;
	private int hp;
	private int maxHp;
	private float shield;
	private int maxShield;
	private int armorPoints;
	private int maxArmorPoints;
	private int percentHP;
	private int percentShield;
	private int percentArmor;
	private int percentEMP;
	private int threeSecTick;
	private boolean active;
	private boolean vulnerable;
	public boolean enemy;
	public boolean friend;
	public boolean hidden;
	public AEMatrix transform;
	private final AEVector3D hitVector = new AEVector3D();
	private KIPlayer kiPlayer;
	private boolean playShootSound;
	public boolean killedByKI;
	private float reamingNukeStun;
	private float empForce;
	private int egoInflictDamage;
	private int dealtEmpDamage;
	private boolean permanentEnemy;
	/**
	 * #TODO verify
	 */
	private int empImpactForce; 
	private int elapsedEMPstun;
	private boolean stunnedEMP;
	private boolean tempEnemy_;
	private boolean permanentFriend;
	public boolean invincible_;
	public int posX;
	public int posY;
	public int posZ;

	/**
	 *
	 * @param var1 bounding sphere radius
	 * @param var2 hitpoints
	 * @param var3 primary weapons count
	 * @param var4 secondary weapons count
	 * @param var5 turrets count
	 */
	public Player(final float var1, final int var2, final int var3, final int var4, final int var5) {
		this.radius = var1;
		this.hp = var2;
		this.maxHp = var2;
		updateDamageRate();
		this.guns = new Gun[3][];
		if (var3 > 0) {
			this.guns[0] = new Gun[var3];
		}

		if (var4 > 0) {
			this.guns[1] = new Gun[var4];
		}

		if (var5 > 0) {
			this.guns[2] = new Gun[var5];
		}

		this.transform = new AEMatrix();
		this.active = true;
		this.vulnerable = true;
		this.kiPlayer = null;
		this.playShootSound = false;
		this.killedByKI = false;
		this.invincible_ = false;
	}

	public final boolean isAsteroid() {
		return this.kiPlayer != null ? this.kiPlayer.isAsteroid : false;
	}

	public final void setAlwaysEnemy(final boolean var1) {
		this.tempEnemy_ = var1;
		this.enemy = true;
		this.friend = false;
		this.permanentEnemy = true;
	}

	public final void setAlwaysFriend(final boolean var1) {
		this.permanentFriend = var1;
		this.enemy = false;
		this.friend = true;
		this.permanentEnemy = false;
	}

	public final boolean isAlwaysFriend() {
		return this.permanentFriend;
	}

	public final void setEmpData(final int var1, final int var2) {
		this.empPoints = var1;
		if (this.empPoints > this.maxEmpPoints) {
			this.maxEmpPoints = this.empPoints;
		}

		updateDamageRate();
		this.empImpactForce = var2;
	}

	public final void setPlayShootSound(final boolean var1) {
		this.playShootSound = true;
	}

	public final void setRadius(final int var1) {
		this.radius = var1;
	}

	public final void setKIPlayer(final KIPlayer var1) {
		this.kiPlayer = var1;
	}

	public final KIPlayer getKIPlayer() {
		return this.kiPlayer;
	}

	public final void reset() {
		this.hp = this.maxHp;
		this.shield = this.maxShield;
		updateDamageRate();
		this.active = true;
		this.vulnerable = true;
		this.transform.identity();
		this.killedByKI = false;
		this.permanentEnemy = false;
		this.egoInflictDamage = 0;
		this.dealtEmpDamage = 0;
		this.hidden = false;
	}

	public final void setEnemies(final Player[] var1) {
		this.coPlayers = var1;
		if (this.guns != null) {
			for(int i = 0; i < this.guns.length; ++i) {
				if (this.guns[i] != null) {
					for(int j = 0; j < this.guns[i].length; ++j) {
						if (this.guns[i][j] != null) {
							this.guns[i][j].setTargets(this.coPlayers);
						}
					}
				}
			}
		}

	}

	public final void addEnemies(final Player[] var1) {
		if (this.coPlayers == null) {
			setEnemies(var1);
		} else {
			final Player[] var2 = new Player[this.coPlayers.length + var1.length];

			int i;
			for(i = 0; i < this.coPlayers.length; ++i) {
				var2[i] = this.coPlayers[i];
			}

			for(i = 0; i < var1.length; ++i) {
				var2[this.coPlayers.length + i] = var1[i];
			}

			setEnemies(var2);
		}
	}

	public final Player[] getEnemies() {
		return this.coPlayers;
	}

	public final Player getEnemy(final int var1) {
		return this.coPlayers[var1];
	}

	public final void setHitPoints(final int var1) {
		this.hp = var1;
		if (this.hp > this.maxHp) {
			this.maxHp = this.hp;
		}

		updateDamageRate();
	}

	public final void setShieldHP(final int var1) {
		this.shield = var1;
		if (this.shield > this.maxShield) {
			this.shield = this.maxShield;
		}

		updateDamageRate();
	}

	public final void setArmorHP(final int var1) {
		this.armorPoints = var1;
		if (this.armorPoints > this.maxArmorPoints) {
			this.armorPoints = this.maxArmorPoints;
		}

		updateDamageRate();
	}

	public final void setMaxHP(final int var1) {
		this.maxHp = var1;
		this.hp = var1;
		updateDamageRate();
	}

	public final void setMaxShieldHP(final int var1) {
		this.maxShield = var1;
		this.shield = var1;
		updateDamageRate();
	}

	public final void setMaxArmorHP(final int var1) {
		this.maxArmorPoints = var1;
		this.armorPoints = var1;
		updateDamageRate();
	}

	public final int getShieldHP() {
		return (int)this.shield;
	}

	public final int getCombinedHP() {
		return (int)this.shield + this.armorPoints + this.hp;
	}

	public final int getArmorHP() {
		return this.armorPoints;
	}

	public final int getMaxArmorHP() {
		return this.maxArmorPoints;
	}

	public final void regenerateShield(final float var1) {
		if (this.shield + var1 < this.maxShield) {
			this.shield += var1;
		} else {
			this.shield = this.maxShield;
		}

		updateDamageRate();
	}

	public final void regenerateArmor() {
		if (this.armorPoints + 1 < this.maxArmorPoints) {
			++this.armorPoints;
		} else {
			this.armorPoints = this.maxArmorPoints;
		}

		updateDamageRate();
	}

	public final void regenerateHull() {
		if (this.hp + 1 < this.maxHp) {
			++this.hp;
		} else {
			this.hp = this.maxHp;
		}

		updateDamageRate();
	}

	public final int getHitpoints() {
		return this.hp;
	}

	public final int getMaxHitpoints() {
		return this.maxHp;
	}

	private void updateDamageRate() {
		this.percentHP = (int)((float)this.hp / (float)this.maxHp * 100.0F);
		this.percentShield = (int)(this.shield / this.maxShield * 100.0F);
		this.percentArmor = (int)((float)this.armorPoints / (float)this.maxArmorPoints * 100.0F);
		this.percentEMP = (int)((float)this.empPoints / (float)this.maxEmpPoints * 100.0F);
	}

	public final int getDamageRate() {
		return this.percentHP;
	}

	public final int getEmpDamageRate() {
		return this.percentEMP;
	}

	public final int getShieldDamageRate() {
		return this.percentShield;
	}

	public final int getArmorDamageRate() {
		return this.percentArmor;
	}

	public final void setVulnerable_(final boolean var1) {
		this.vulnerable = var1;
	}

	public final void setHitVector_(final int var1, final int var2, final int var3) {
		this.hitVector.x = var1;
		this.hitVector.y = var2;
		this.hitVector.z = var3;
	}

	public final void setBombForce(final float var1) {
		this.reamingNukeStun = var1;
	}

	public final void setEmpForce_(final float var1) {
		this.empForce = var1;
	}

	public final float getBombForce() {
		return this.reamingNukeStun;
	}

	public final float setEmpForce_() {
		return this.empForce;
	}

	public final AEVector3D getHitVector() {
		return this.hitVector;
	}

	public final void damageEmp(final int var1, final boolean var2) {
		if (this.vulnerable && this.active && this.empPoints > 0 && this.hp > 0) {
			if (!var2 && this.kiPlayer != null && !this.tempEnemy_ && this.kiPlayer.race != Globals.VOID && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
				this.dealtEmpDamage += var1;
				if (this.dealtEmpDamage > this.maxEmpPoints / 3) {
					this.permanentEnemy = true;
					this.kiPlayer.level.friendTurnedEnemy(this.kiPlayer.race);
				}
			}

			this.empPoints -= var1;
			if (this.empPoints <= 0) {
				if (!var2 && this.kiPlayer != null) {
					if (!this.tempEnemy_ && this.kiPlayer.race != Globals.VOID && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
						this.kiPlayer.level.alarmAllFriends(this.kiPlayer.race, false);
					}

					if (!this.kiPlayer.isAsteroid && !this.kiPlayer.junk) {
						Status.getStanding().applyDisable(this.kiPlayer.race);
					}
				}

				final float var3 = this.empImpactForce;
				this.empForce = var3;
				this.stunnedEMP = true;
				this.empPoints = 0;
				this.elapsedEMPstun = 0;
			}

			updateDamageRate();
		}
	}

	public final void damageHP(int var1, final boolean var2) {
		if (this.vulnerable && this.active && this.hp > 0) {
			if (!var2 && this.kiPlayer != null && !this.tempEnemy_ && this.kiPlayer.race != Globals.VOID && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
				this.egoInflictDamage += var1;
				if (this.egoInflictDamage > this.maxHp / 3) {
					this.permanentEnemy = true;
					this.kiPlayer.level.friendTurnedEnemy(this.kiPlayer.race);
				}

				if (this.kiPlayer != null && this.egoInflictDamage > this.maxHp - this.maxHp / 3) {
					this.kiPlayer.level.alarmAllFriends(this.kiPlayer.race, true);
				}
			}

			var1 = (int)this.shield - var1;
			if (var1 < 0) {
				this.shield = 0.0F;
				if ((var1 = this.armorPoints - -var1) < 0) {
					this.armorPoints = 0;
					this.hp -= -var1;
				} else {
					this.armorPoints = var1;
				}
			} else {
				this.shield = var1;
			}

			if (this.hp <= 0) {
				this.hp = 0;
				if (var2) {
					this.killedByKI = true;
				} else if (this.kiPlayer != null && !this.kiPlayer.isAsteroid && !this.kiPlayer.junk) {
					Status.getStanding().applyKill(this.kiPlayer.race);
				}
			}

			updateDamageRate();
		}
	}

	public final boolean isAlwaysEnemy() {
		return this.permanentEnemy;
	}

	public final void setAlwaysEnemy() {
		this.permanentEnemy = true;
	}

	public final boolean hasGunOfType(final int var1) {
		if (var1 < 3 && var1 >= 0) {
			return this.guns[var1] != null;
		}
		return false;
	}

	public final AEVector3D getPosition(final AEVector3D var1) {
		return this.transform.getPosition(var1);
	}

	public final void setActive(final boolean var1) {
		this.active = var1;
	}

	public final boolean isActive() {
		return this.active;
	}

	public final boolean isDead() {
		return this.hp <= 0;
	}

	public final void removeAllGuns() {
		this.guns = null;
	}

	public final void resetGunDelay(int var1) {
		if (this.guns != null && 0 < this.guns.length && this.guns[0] != null) {
			for(var1 = 0; var1 < this.guns[0].length; ++var1) {
				this.guns[0][var1].timeSinceLastShot = 0;
			}
		}

	}

	public final void playShootSound__(final int gunType, final long dt, final boolean var4, final AEMatrix var5) {
		if (this.guns != null && gunType < this.guns.length && gunType >= 0 && this.guns[gunType] != null) {
			for(int i = 0; i < this.guns[gunType].length; ++i) {
			boolean shot = this.guns[gunType][i].shootAt(var5, dt, var4);
			// Player::playShootSound
			if (this.guns[gunType][i].timeSinceLastShot > this.guns[gunType][i].reloadTimeMilis && shot) {
					this.guns[gunType][i].timeSinceLastShot = 0;
					if (this.playShootSound) {
						switch(this.guns[gunType][i].subType) {
						case Item.ROCKET:
							GlobalStatus.soundManager.playSfx(8);
							break;
						case Item.TORPEDO:
							GlobalStatus.soundManager.playSfx(9);
							break;
						case Item.EMP_BOMB:
							GlobalStatus.soundManager.playSfx(10);
							break;
						case Item.NUKE:
							GlobalStatus.soundManager.playSfx(10);
						}
					}
				}
			}
		}

	}

	public final void shoot(final int gunType, final long dt, final boolean var4) {
		playShootSound__(gunType, dt, false, this.transform);
	}

	public final boolean shoot(final int gunType, int var22, final long dt, final boolean var5) {
		boolean var6 = true;
		if (this.guns != null && gunType < this.guns.length && gunType >= 0 && this.guns[gunType] != null) {
			for(int i = 0; i < this.guns[gunType].length; ++i) {
				Gun gun = this.guns[gunType][i];
				if ((gun.subType == Item.NUKE || gun.subType == Item.EMP_BOMB) && gun.projectilesTimeLeft[0] >= 0) {
					gun.ignite();
				} else if (gun.index == var22 && gun.timeSinceLastShot > gun.reloadTimeMilis) {
					if (gun.shootAt(this.transform, dt, var5)) {
						// Player::playShootSound
						if (this.playShootSound) {
							switch(this.guns[gunType][i].subType) {
							case Item.ROCKET:
								GlobalStatus.soundManager.playSfx(8);
								break;
							case Item.TORPEDO:
								GlobalStatus.soundManager.playSfx(9);
								break;
							case Item.EMP_BOMB:
								GlobalStatus.soundManager.playSfx(10);
								break;
							case Item.NUKE:
								GlobalStatus.soundManager.playSfx(10);
							}
						}

						gun.timeSinceLastShot = 0;
						break;
					}

					if (gun.ammo <= 0) {
						var6 = false;
					}
				}
			}
		}

		return var6;
	}

	public final void update(final long var1) {
		this.threeSecTick = (int)(this.threeSecTick + var1);
		if (this.threeSecTick > 3000) {
			this.threeSecTick = 0;
		}

		if (this.stunnedEMP) {
			this.elapsedEMPstun = (int)(this.elapsedEMPstun + var1);
			this.empPoints = (int)((float)this.elapsedEMPstun / (float)this.empImpactForce * this.maxEmpPoints);
			if (this.empPoints > this.maxEmpPoints) {
				this.empPoints = this.maxEmpPoints;
				this.stunnedEMP = false;
				this.elapsedEMPstun = 0;
			}

			updateDamageRate();
		}

	}
}
