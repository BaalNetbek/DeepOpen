package GOF2;

/**
 * #TODO uinling types
 * 
 * @author Fishlabs
 *
 */
public final class Objective {
	public static final int TYPE_0 = 0;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;
	public static final int TYPE_5 = 5;
	public static final int TYPE_6 = 6;
	public static final int TYPE_7 = 7;
	public static final int TYPE_8 = 8;
	public static final int TYPE_9 = 9;
	public static final int TYPE_10 = 10;
	public static final int TYPE_11 = 11;
	public static final int TYPE_12 = 12;
	public static final int TYPE_13 = 13;
	public static final int TYPE_14 = 14;
	public static final int TYPE_15 = 15;
	public static final int TYPE_16 = 16;
	public static final int TYPE_17 = 17;
	public static final int TYPE_18 = 18;
	public static final int TYPE_19 = 19;
	public static final int TYPE_20 = 20;
	public static final int TYPE_21 = 21;
	public static final int TYPE_22 = 22;
	public static final int TYPE_23 = 23;
	public static final int TYPE_24 = 24;
	public static final int TYPE_25 = 25;
	
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
	/** #TODO uninline */
	public final boolean achieved(final int time) {
		KIPlayer[] targets;
		int i = 0, dead = 0;
		switch(this.type) {
		case TYPE_0: //kill everyone
			if (this.level.getEnemiesLeft() == 0) {
				return true;
			}

			return false;
		case TYPE_1:
			if (this.level.getEnemies()[this.indexA_].isDead()) {
				return true;
			}

			return false;
		case TYPE_2:
			if (this.level.getPlayerRoute().getLastWaypoint().reached_) {
				return true;
			}

			return false;
		case TYPE_3:
			if (time > this.indexA_) {
				return true;
			}

			return false;
		case TYPE_4:
			if (this.level.getMessages()[this.indexA_].isOver()) {
				return true;
			}

			return false;
		case TYPE_5:
			if (this.level.getFriendsLeft() == 0) {
				return true;
			}

			return false;
		case TYPE_6:
			if (this.level.getEnemies()[this.indexA_].isDead()) {
				return true;
			}

			return false;
		case TYPE_7:
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
		case TYPE_8:
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
		case TYPE_9:
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
		case TYPE_10:
			targets = this.level.getAsteroids();
			for(i = 0; i < targets.length; ++i) {
				if (i >= this.indexA_) {
					return false;
				}
			}

			return false;
		case TYPE_11:
			return ((PlayerFighter)this.level.getEnemies()[this.indexA_]).lostMissionCrateToEgo();
		case TYPE_12:
			return ((PlayerFighter)this.level.getEnemies()[this.indexA_]).diedWithMissionCrate();
		case TYPE_13:
			return false;
		case TYPE_14:
			if (this.level.unknown7f9_() >= this.indexA_) {
				return true;
			}

			return false;
		case TYPE_15:
			return this.level.getEnemies()[this.indexA_].player.isActive();
		case TYPE_16:
			targets = this.level.getEnemies();
			for(i = 0; i < targets.length; ++i) {
				if (!((PlayerFighter)targets[i]).lostMissionCrateToEgo()) {
					return false;
				}
			}

			return true;
		case TYPE_17:
			targets = this.level.getEnemies();
			for(i = 0; i < targets.length; ++i) {
				if (((PlayerFighter)targets[i]).diedWithMissionCrate()) {
					return true;
				}
			}

			return false;
		case TYPE_18:
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
		case TYPE_19:
			return this.level.friendCargoWasStolen();
		case TYPE_20:
		case TYPE_21:
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
		case TYPE_22:
			if (this.level.getMessages()[this.level.getMessages().length - 1].isOver()) {
				return true;
			}

			return false;
		case TYPE_23:
			return this.level.getEnemies()[this.indexA_].stunned;
		case TYPE_24:
		default:
			return false;
		case TYPE_25:
			if (this.level.getEnemies()[this.indexA_].speed == 0) {
				return true;
			}
			return false;
		}
	}

	public final boolean isSurvivalObjective() {
		return this.type == TYPE_3;
	}
}
