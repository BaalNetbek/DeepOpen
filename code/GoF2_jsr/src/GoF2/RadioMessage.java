package GoF2;

public final class RadioMessage {
	private Radio radio;
	private final int textId;
	private final int imageId;
	private final int triggerCondition;
	private final int triggerType;
	private final int[] conditionsGroup;
	private boolean triggered;
	private boolean finished;
	private int lastWaypoint_;
	/**
	 *
	 * @param var1 text index
	 * @param var2 face index
	 * @param var3 trigger condition
	 * @param var4 trigger type
	 */
	public RadioMessage(final int var1, final int var2, final int var3, final int var4) {
		this.textId = var1;
		this.imageId = var2;
		this.triggerCondition = var3;
		this.triggerType = var4;
		this.conditionsGroup = new int[]{var4};
		this.triggered = false;
		this.finished = false;
	}

	public RadioMessage(final int var1, final int var2, final int var3, final int[] var4) {
		this.textId = 854;
		this.imageId = 0;
		this.triggerCondition = 9;
		this.triggerType = var4[0];
		this.conditionsGroup = var4;
		this.triggered = false;
	}

	public final int getTextID() {
		return this.textId;
	}

	public final int getImageID() {
		return this.imageId;
	}

	public final void setRadio(final Radio var1) {
		this.radio = var1;
	}

	public final boolean isTriggered() {
		return this.triggered;
	}

	public final boolean isOver() {
		return this.finished;
	}

	public final void finish() {
		this.finished = true;
	}

	public final boolean triggered(final long var1, final PlayerEgo playerEgo) {
		if (this.triggered) {
			return false;
		}
		boolean var4;
		var4 = false;
		Player[] var2;
		int var6;
		int var7;
		Player[] var8;
		int i;
		Player[] var10;
		switch(this.triggerCondition) {
		case 0:
			if (playerEgo.getRoute() != null) {
				var4 = playerEgo.getRoute().getCurrent() > this.lastWaypoint_ && this.lastWaypoint_ == this.triggerType;
				this.lastWaypoint_ = playerEgo.getRoute().getCurrent();
			}
			break;
		case 1:
			var10 = playerEgo.player.getEnemies();

			for (var6 = 0; var6 < this.conditionsGroup.length; var6++) {
				if (var10[this.conditionsGroup[var6]].isDead()) {
					var4 = true;
					break;
				}
			}
			break;
		case 2:
			var10 = playerEgo.player.getEnemies();

			for (var6 = 0; var6 < this.conditionsGroup.length; var6++) {
				if (var10[this.conditionsGroup[var6]].friend && var10[this.conditionsGroup[var6]].isDead()) {
					var4 = true;
					break;
				}
			}
			break;
		case 3:
			var4 = playerEgo.level.getEnemiesLeft() <= 0;
			break;
		case 4:
			var4 = playerEgo.level.getFriendsLeft() <= 0;
			break;
		case 5:
			var4 = var1 >= this.triggerType;
			break;
		case 6:
			var4 = this.radio.getMessageFromQueue(this.triggerType).triggered;
			break;
		case 7:
		case 13:
		default:
			break;
		case 8:
			var8 = playerEgo.player.getEnemies();

			for (var7 = 0; var7 < this.conditionsGroup.length; var7++) {
				if (!var8[var7].isAsteroid() && var8[this.conditionsGroup[var7]].isActive()) {
					var4 = true;
					break;
				}
			}
			break;
		case 9:
			var4 = true;
			var2 = playerEgo.player.getEnemies();
			i = 0;

			for (i = 0; i<this.conditionsGroup.length; i++) {


				if (!var2[this.conditionsGroup[i]].isDead()) {
					var4 = false;
					break;
				}
			}
			break;
		case 10:
			var2 = playerEgo.player.getEnemies();
			
			for (var6 = 0; var6 < this.conditionsGroup.length; var6++) {
				if (var2[this.conditionsGroup[var6]].friend && var2[this.conditionsGroup[var6]].isActive()) {
					var4 = true;
					break;
				}
			}
			break;
		case 11:
			var4 = ((Objective)null).achieved((int)var1);
			break;
		case 12:
			var8 = playerEgo.player.getEnemies();

			for (i = 0; i<this.conditionsGroup.length; i++) {
				if (var8[this.conditionsGroup[i]].getHitpoints() < var8[this.conditionsGroup[i]].getMaxHitpoints() / 2) {
					var4 = true;
					break;
				}
			}
			break;
		case 14:
			var4 = ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).lostMissionCrateToEgo();
			break;
		case 15:
			var4 = false;
			var2 = playerEgo.player.getEnemies();
			
			for (var6 = 0; var6 < var2.length; var6++) {
				if (var2[var6].isDead() && !var2[var6].isAsteroid()) {
					var4 = true;
					break;
				}

			}
			break;
		case 16:
			var8 = playerEgo.player.getEnemies();

			for(var7 = 0; var7 < var8.length; var7++) {
				if (!var8[var7].isAsteroid() && var8[var7].isActive() && !var8[var7].isAlwaysFriend()) {
					var4 = true;
					break;
				}
			}
			break;
		case 17:
			var2 = playerEgo.player.getEnemies();
			var4 = true;

			for (var6 = 0; var6 < var2.length; var6++) {
				if (var6 != this.triggerType && !var2[var6].isAsteroid() && !var2[var6].isDead()) {
					var4 = false;
				   break;
				}
			}
			break;
		case 18:
			var4 = ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).lostCargo() 
					|| ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).unk151_();
			break;
		case 19:
			var8 = playerEgo.player.getEnemies();
			i = 0;
			for (i = 0; i <this.conditionsGroup.length; i++) {
				if (var8[this.conditionsGroup[i]].getHitpoints() < (var8[this.conditionsGroup[i]].getMaxHitpoints() * 0.75)) {
					var4 = true;
					break;
				}
			}
			break;
		case 20:
			var4 = false;
			var6 = 0;
			var2 = playerEgo.player.getEnemies();
			
			for (i = 0; i<var2.length; i++) {
				if (var2[i].isDead()) {
					++var6;
				}

				if (var6 >= this.triggerType) {
					var4 = true;
					break;
				}
			}
			break;
		case 21:
			if (!playerEgo.player.getEnemies()[this.triggerType].getKIPlayer().stunned) {
				break;
			}

			var4 = true;
			break;
		case 22:
			var4 = playerEgo.level.capturedCargoCount >= this.triggerType;
			break;
		case 23:
			Radar var5;
			var4 = (var5 = playerEgo.radar).targetedStation != null && var5.targetedStation instanceof PlayerStation;
			break;
		case 24:
			var4 = !playerEgo.player.getEnemies()[this.triggerType].isActive() && !playerEgo.player.getEnemies()[this.triggerType].isDead();
			break;
		}


		this.triggered = var4;
		if (this.triggered) {
			this.radio.setCurrentMessage(this);
		}

		return var4;
	}
}
