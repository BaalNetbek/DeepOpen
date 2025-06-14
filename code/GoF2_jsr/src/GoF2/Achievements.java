package GoF2;

/**
 * Manages achievements (medals).
 * Stored in GameRecord.
 * 
 * @author fishlabs
 *
 */
public final class Achievements {
	public static final int[][] medalTresholds;
	private static int[] medals;
	private static int[] newMedals;
	/**
	 * unused (legacy from Deep)
	 */
	private static int killStreak; 
	/**
	 * unused (legacy from Deep)
	 */
	private static int pirateKillStreak;
	private static int weaponsEquipped;
	private static boolean isArmed;
	private static int maxCredits;
	private static boolean hasAll;
	private static boolean hasAllGold;
	private static int goldMedalsCount;
	private static int unlockedMedalsCount;

	public static void checkForNewMedal(final PlayerEgo ego) {
		int equipmentSloted = 0;
		int totalEquipped = 0;
		if (Status.getCurrentCampaignMission() > 7) {
			Item[] equipped = Status.getShip().getEquipment();
			if (equipped != null) {
				for(int i = 0; i < equipped.length; ++i) {
					if (equipped[i] != null && equipped[i].getType() != 4) {
						if (equipped[i].getType() == 0) {
							++weaponsEquipped;
						} else if (equipped[i].getType() == 3) {
							++equipmentSloted;
							continue;
						}
						
						++totalEquipped;
					}
				}
			}

			isArmed = totalEquipped > 0 && equipmentSloted > 0;
		} else {
			isArmed = true;
		}

		boolean qualifiable = false;

		for(int i = 0; i < medalTresholds.length; ++i) {
			int reachedTier = 0;
			if (medals[i] != 1) {
				for(int j = 0; j < medalTresholds[i].length && reachedTier <= 0; ++j) {
					qualifiable = false;
					switch(i) {
					case 1:
						qualifiable = ego.getHullDamageRate() <= medalTresholds[i][j];
						break;
					case 2:
					case 3:
					case 9:
					case 12:
						final boolean[] var5 =
								  i == 2 ? Status.minedOreTypes
								: i == 3 ? Status.minedCoreTypes
								: i == 9 ? Status.drinkTypesPossesed 
								: 		   Status.systemsVisited;
						
						equipmentSloted = 0;
						for(int k = 0; k < var5.length; ++k) {
							if (var5[k]) {
								++equipmentSloted;
							}
						}

						qualifiable = equipmentSloted >= medalTresholds[i][j];
						break;
					case 4:
						qualifiable = Status.getKills() >= medalTresholds[i][j];
						break;
					case 5:
						qualifiable = Status.missionGoodsCarried > medalTresholds[i][j];
						break;
					case 6:
						qualifiable = Status.minedOre > medalTresholds[i][j];
						break;
					case 7:
						qualifiable = Status.minedCores > medalTresholds[i][j];
						break;
					case 8:
						qualifiable = Status.boughtBooze > medalTresholds[i][j];
						break;
					case 10:
						qualifiable = Status.destroyedJunk > medalTresholds[i][j];
						break;
					case 11:
						qualifiable = Status.getStationsVisited() >= medalTresholds[i][j];
						break;
					case 13:
						equipmentSloted = 0;

						for(int k = 0; k < Status.blueprints.length; ++k) {
							if (Status.blueprints[k].unlocked) {
								++equipmentSloted;
							}
						}

						qualifiable = equipmentSloted >= medalTresholds[i][j];
						break;
					case 14:
						equipmentSloted = 0;

						for(int k = 0; k < Status.blueprints.length; ++k) {
							if (Status.blueprints[k].timesProduced > 0) {
								++equipmentSloted;
							}
						}

						qualifiable = equipmentSloted >= medalTresholds[i][j];
						break;
					case 15:
						qualifiable = Status.getPlayingTime() > medalTresholds[i][j] * 3600000;
						break;
					case 16:
						qualifiable = Status.getMissionCount() > medalTresholds[i][j];
						break;
					case 17:
						qualifiable = Status.getJumpgateUsed() >= medalTresholds[i][j];
						break;
					case 18:
						qualifiable = Status.passengersCarried > medalTresholds[i][j];
						break;
					case 19:
						qualifiable = Status.invisibleTime / 60000L >= medalTresholds[i][j];
						break;
					case 20:
						qualifiable = Status.bombsUsed > medalTresholds[i][j];
						break;
					case 21:
						qualifiable = Status.alienJunkSalvaged > medalTresholds[i][j];
						break;
					case 22:
						qualifiable = !isArmed;
						break;
					case 23:
						qualifiable = weaponsEquipped >= medalTresholds[i][j];
						break;
					case 24:
						qualifiable = Status.getCargoSalvaged() >= medalTresholds[i][j];
						break;
					case 25:
						qualifiable = maxCredits >= medalTresholds[i][j];
						break;
					case 26:
						qualifiable = Status.barInteractions > medalTresholds[i][j];
						break;
					case 27:
						qualifiable = Status.commandedWingmen > medalTresholds[i][j];
						break;
					case 28:
						qualifiable = Status.getStanding().atWar();
						break;
					case 29:
						qualifiable = Status.asteroidsDestroyed > medalTresholds[i][j];
						break;
					case 30:
						qualifiable = Status.gameWon();
						break;
					case 31:
						qualifiable = Status.maxFreeCargo > medalTresholds[i][j];
						break;
					case 32:
						qualifiable = Status.missionsRejected > medalTresholds[i][j];
						break;
					case 33:
						qualifiable = Status.askedToRepeate > medalTresholds[i][j];
						break;
					case 34:
						qualifiable = Status.acceptedNotAskingDifficulty > medalTresholds[i][j];
						break;
					case 35:
						qualifiable = Status.acceptedNotAskingLocation > medalTresholds[i][j];
						break;
					case 36:
						qualifiable = true;

						for(int k = 0; k < medals.length - 1; ++k) {
							if (medals[k] == 0) {
								qualifiable = false;
								break;
							}
						}
					}

					if (qualifiable) {
						reachedTier = j + 1;
					}
				}
			}

			if (medals[i] > reachedTier || medals[i] == 0) {
				newMedals[i] = reachedTier;
			}
		}

	}

	private static void countMedals() {
		unlockedMedalsCount = 0;
		goldMedalsCount = 0;

		for(int i = 0; i < medals.length; ++i) {
			if (medals[i] == 1) {
				++unlockedMedalsCount;
				++goldMedalsCount;
			} else if (medals[i] != 0) {
				++unlockedMedalsCount;
			}
		}

		hasAll = unlockedMedalsCount == medals.length;
		hasAllGold = goldMedalsCount == medals.length;
	}

	public static boolean gotAllMedals() {
		return hasAll;
	}

	public static boolean gotAllGoldMedals() {
		return hasAllGold;
	}

	public static int[] getMedals() {
		return medals;
	}

	public static int[] getNewMedals() {
		return newMedals;
	}

	public static void incKills() {
		++killStreak;
	}

	public static void incPirateKills() {
		++pirateKillStreak;
	}

	public static void updateMaxCredits(final int currentCredits) {
		if (currentCredits > maxCredits) {
			maxCredits = currentCredits;
		}

	}

	public static void setMedals(final int[] var0) {
		medals = var0;
	}

	public static void init() {
		for(int i = 0; i < medals.length; ++i) {
			medals[i] = 0;
		}

		medals[0] = 1;
		resetNewMedals();
		maxCredits = 0;
	}

	public static void applyNewMedals() {
		for(int i = 0; i < medals.length; ++i) {
			if (newMedals[i] > 0 && (newMedals[i] < medals[i] || medals[i] == 0)) {
				medals[i] = newMedals[i];
			}
		}

		countMedals();
		if (unlockedMedalsCount == medals.length - 1) {
			newMedals[medals.length - 1] = 1;
			medals[medals.length - 1] = 1;
			countMedals();
		}

	}

	public static void resetNewMedals() {
		for(int i = 0; i < newMedals.length; ++i) {
			newMedals[i] = 0;
		}

		killStreak = 0;
		pirateKillStreak = 0;
		weaponsEquipped = 0;
		isArmed = false;
	}

	static {
		medalTresholds = new int[][] { 
			{ 0 }, { 5, 15, 30 },
			{ 11, 8, 5 },
			{ 11, 8, 5 },
			{ 250, 100, 50 },
			{ 200, 100, 25 },
			{ 1000, 500, 100 },
			{ 25, 10, 3 },
			{ 1000, 100, 25 },
			{ 22, 16, 5 },
			{ 150, 100, 30 },
			{ 100, 50, 25 },
			{ 22, 10, 5 },
			{ 13, 6, 3 },
			{ 13, 6, 3 },
			{ 20, 10, 5 },
			{ 50, 25, 5 },
			{ 100, 50, 10 },
			{ 50, 20, 5 },
			{ 5, 3, 2 },
			{ 50, 20, 5 },
			{ 25, 10, 5 },
			{ 0 },
			{ 4, 3, 2 },
			{ 500, 200, 50 },
			{ 1000000, 500000, 125000 },
			{ 100, 50, 20 },
			{ 20, 10, 3 },
			{ 1 },
			{ 500, 250, 50 },
			{ 0 },
			{ 500, 250, 100 },
			{ 50 },
			{ 5 },
			{ 10 },
			{ 12 },
			{ 0 } };
		medals = new int[medalTresholds.length];
		newMedals = new int[medalTresholds.length];
	}
}
