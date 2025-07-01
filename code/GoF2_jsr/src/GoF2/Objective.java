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

	public final boolean achieved(final int time) {
		KIPlayer[] targets;
		int i = 0, dead = 0;
		switch(this.type) {
		case 0: //kill everyone
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
			if (time > this.indexA_) {
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
			targets = this.level.getEnemies();
			for(i = 0; i < this.indexA_; ++i) {
				if (targets[i].isDead()) {
					++dead;
				}
			}

			if (dead == this.indexA_) {
				return true;
			}

			return false;
		case 8:
			targets = this.level.getAsteroids();
			dead = 0;
			for(i = 0; i < targets.length; ++i) {
				if (targets[i].isDead()) {
					++dead;
				}
			}

			if (dead > this.indexA_) {
				return true;
			}

			return false;
		case 9:
			targets = this.level.getAsteroids();
			for(i = 0; i < targets.length; ++i) {
				if (i >= this.indexA_) {
					if (0 >= this.indexA_) {
						return true;
					}

					return false;
				}
			}

			return false;
		case 10:
			targets = this.level.getAsteroids();
			for(i = 0; i < targets.length; ++i) {
				if (i >= this.indexA_) {
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
			targets = this.level.getEnemies();
			for(i = 0; i < targets.length; ++i) {
				if (!((PlayerFighter)targets[i]).lostMissionCrateToEgo()) {
					return false;
				}
			}

			return true;
		case 17:
			targets = this.level.getEnemies();
			for(i = 0; i < targets.length; ++i) {
				if (((PlayerFighter)targets[i]).diedWithMissionCrate()) {
					return true;
				}
			}

			return false;
		case 18:
			targets = this.level.getEnemies();
			dead = 0;
			for(i = this.indexA_; i < this.indexB_; ++i) {
				if (targets[i].isDead()) {
					++dead;
				}
			}

			if (dead == this.indexB_ - this.indexA_) {
				return true;
			}

			return false;
		case 19:
			return this.level.friendCargoWasStolen();
		case 20:
		case 21:
			targets = this.level.getEnemies();
			//pirates
			dead = 0;
			for(i = this.indexA_; i < this.indexB_; ++i) {
				if (targets[i].isDead() && targets[i].race == 8) {
					++dead;
				}
			}

			if (this.type == 20) {
				if (dead == this.indexB_ - this.indexA_ && this.level.egoScore > this.level.challengerScore) {
					return true;
				}
			} else {
				if (dead == this.indexB_ - this.indexA_ && this.level.egoScore <= this.level.challengerScore) {
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
