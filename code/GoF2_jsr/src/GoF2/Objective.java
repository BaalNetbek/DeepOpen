package GoF2;

public final class Objective {
	private final int type;
	private final int indexA_;
	private int indexB_;
	private final Level level;

	public Objective(final int var1, final int var2, final Level var3) {
		this.type = var1;
		this.indexA_ = var2;
		this.level = var3;
	}

	public Objective(final int var1, final int var2, final int var3, final Level var4) {
		this(var1, var2, var4);
		this.indexB_ = var3;
	}

	public final boolean achieved(int var1) {
		int var2;
		int var3;
		KIPlayer[] var4;
		KIPlayer[] var5;
		KIPlayer[] var6;
		switch(this.type) {
		case 0:
			if (this.level.getEnemiesLeft() == 0) {
				return true;
			}

			return false;
		case 1:
			if (this.level.getEnemies()[this.indexA_].isDead()) {
				return true;
			}

			return false;
		case 2:
			if (this.level.getPlayerRoute().getLastWaypoint().reached_) {
				return true;
			}

			return false;
		case 3:
			if (var1 > this.indexA_) {
				return true;
			}

			return false;
		case 4:
			if (this.level.getMessages()[this.indexA_].isOver()) {
				return true;
			}

			return false;
		case 5:
			if (this.level.getFriendsLeft() == 0) {
				return true;
			}

			return false;
		case 6:
			if (this.level.getEnemies()[this.indexA_].isDead()) {
				return true;
			}

			return false;
		case 7:
			var6 = this.level.getEnemies();
			var1 = 0;

			for(var3 = 0; var3 < this.indexA_; ++var3) {
				if (var6[var3].isDead()) {
					++var1;
				}
			}

			if (var1 == this.indexA_) {
				return true;
			}

			return false;
		case 8:
			var5 = this.level.getAsteroids();
			var1 = 0;

			for(var2 = 0; var2 < var5.length; ++var2) {
				if (var5[var2].isDead()) {
					++var1;
				}
			}

			if (var1 > this.indexA_) {
				return true;
			}

			return false;
		case 9:
			var6 = this.level.getAsteroids();

			for(var1 = 0; var1 < var6.length; ++var1) {
				if (var1 >= this.indexA_) {
					if (0 >= this.indexA_) {
						return true;
					}

					return false;
				}
			}

			return false;
		case 10:
			var4 = this.level.getAsteroids();

			for(var2 = 0; var2 < var4.length; ++var2) {
				if (var2 >= this.indexA_) {
					return false;
				}
			}

			return false;
		case 11:
			return ((PlayerFighter)this.level.getEnemies()[this.indexA_]).lostMissionCrateToEgo();
		case 12:
			return ((PlayerFighter)this.level.getEnemies()[this.indexA_]).diedWithMissionCrate();
		case 13:
			return false;
		case 14:
			if (this.level.unknown7f9_() >= this.indexA_) {
				return true;
			}

			return false;
		case 15:
			return this.level.getEnemies()[this.indexA_].player.isActive();
		case 16:
			var6 = this.level.getEnemies();

			for(var1 = 0; var1 < var6.length; ++var1) {
				if (!((PlayerFighter)var6[var1]).lostMissionCrateToEgo()) {
					return false;
				}
			}

			return true;
		case 17:
			var4 = this.level.getEnemies();

			for(var2 = 0; var2 < var4.length; ++var2) {
				if (((PlayerFighter)var4[var2]).diedWithMissionCrate()) {
					return true;
				}
			}

			return false;
		case 18:
			var5 = this.level.getEnemies();
			var1 = 0;

			for(var2 = this.indexA_; var2 < this.indexB_; ++var2) {
				if (var5[var2].isDead()) {
					++var1;
				}
			}

			if (var1 == this.indexB_ - this.indexA_) {
				return true;
			}

			return false;
		case 19:
			return this.level.friendCargoWasStolen();
		case 20:
		case 21:
			var4 = this.level.getEnemies();
			var2 = 0;

			for(var3 = this.indexA_; var3 < this.indexB_; ++var3) {
				if (var4[var3].isDead() && var4[var3].race == 8) {
					++var2;
				}
			}

			if (this.type == 20) {
				if (var2 == this.indexB_ - this.indexA_ && this.level.egoScore > this.level.challengerScore) {
					return true;
				}
			} else {
				if (var2 == this.indexB_ - this.indexA_ && this.level.egoScore <= this.level.challengerScore) {
					return true;
				}
			}
			return false;
		case 22:
			if (this.level.getMessages()[this.level.getMessages().length - 1].isOver()) {
				return true;
			}

			return false;
		case 23:
			return this.level.getEnemies()[this.indexA_].stunned;
		case 24:
		default:
			return false;
		case 25:
			if (this.level.getEnemies()[this.indexA_].speed == 0) {
				return true;
			}
			return false;
		}
	}

	public final boolean isSurvivalObjective() {
		return this.type == 3;
	}
}
