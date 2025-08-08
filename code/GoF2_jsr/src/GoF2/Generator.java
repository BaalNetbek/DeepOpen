package GoF2;

import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.ImageFactory;

public final class Generator {
	private final int[] GENERATOR_CAMPAIGN_STATIONS = {10, 22, 27, 29, 30, 48, 55, 56, 76, 79, 91, 98};
	private final int[] GENERATOR_TYPE_PROBS = {5, 20, 2, 5, 100};

	public static Ship[] getShipBuyList(final Station station) {
		if (station.getSystemIndex() == 15 && Status.getCurrentCampaignMission() < 16) {
			return null;
		}
		Ship[] listShips = null;
		final int race = Status.getSystem().getRace();
		final boolean sellSecretShip = station.getIndex() == 10 && Achievements.gotAllGoldMedals();
		final int listLen = race != Globals.VOSSK && !sellSecretShip ? GlobalStatus.random.nextInt(6) : 1;
		if (listLen == 0) {
			return null;
		} else {
			listShips = new Ship[listLen];

			for(int i = 0; i < listShips.length; ++i) {
				int shipId = 0;
				boolean noDublicates = false;

				while(true) {
					while(!noDublicates) {
						shipId = sellSecretShip ? 8 : Globals.getRandomEnemyFighter(race);
						noDublicates = true;

						for(int j = 0; j < listShips.length; ++j) {
							if (listShips[j] != null && listShips[j].getIndex() == shipId) {
								noDublicates = false;
								break;
							}
						}
					}

					listShips[i] = Globals.getShips()[shipId].cloneBase();
					listShips[i].setRace(Status.getSystem().getRace());
					listShips[i].adjustPrice();
					break;
				}
			}

			return listShips;
		}
	}

	public final int[] getLootList() {
		final Item[] items = Globals.getItems();
		int var2 = GlobalStatus.random.nextInt(3);
		if (var2 == 0) {
			return null;
		}
		final int[] var9 = new int[var2 << 1];
		final Item[] var3 = Globals.getItems();
		int var4 = 0;

		for(int i = 0; i < var9.length; i += 2) {
			boolean var6 = false;
			int var7 = 0;

			for(int j = 0; j < 100 && !var6; ++j) {
				var7 = GlobalStatus.random.nextInt(items.length);
				var4 = var3[var7].getType();
				if (var3[var7].getBluePrintComponentsIds() == null && GlobalStatus.random.nextInt(100) < this.GENERATOR_TYPE_PROBS[var4] && GlobalStatus.random.nextInt(100) < items[var7].getOccurance() && var3[var7].getSinglePrice() > 0 && var7 != 175 && var7 != 164 && (var4 == 4 || var3[var7].getTecLevel() <= 7)) {
					var6 = true;
				}
			}

			if (!var6) {
				var7 = 154 + GlobalStatus.random.nextInt(10);
				var4 = 4;
			}

			var9[i] = var7;
			if (var4 == 4) {
				var9[i + 1] = 1 + GlobalStatus.random.nextInt(9);
			} else {
				var9[i + 1] = 1;
			}
		}

		return var9;
	}

	private int generateStationIndex(final SolarSystem[] var1, final int var2) {
		int stationIdx = 0;
		boolean var4 = false;

		while(!var4) {
			if (GlobalStatus.random.nextInt(100) < 20) {
				stationIdx = var2;
			} else if (GlobalStatus.random.nextInt(100) < 40) {
				int[] var5 = var1[Status.getSystem().getIndex()].getStations();
				stationIdx = var5[GlobalStatus.random.nextInt(var5.length)];
			} else {
				stationIdx = GlobalStatus.random.nextInt(100);
			}

			if (Status.getSystem().getIndex() == 15) {
				stationIdx = Status.getSystem().getStations()[GlobalStatus.random.nextInt(Status.getSystem().getStations().length)];
			}

			var4 = true;

			for(int i = 0; i < this.GENERATOR_CAMPAIGN_STATIONS.length; ++i) {
				if (stationIdx == this.GENERATOR_CAMPAIGN_STATIONS[i]) {
					var4 = false;
					break;
				}
			}

			int var7 = 0;
			for(int i = 0; i < var1.length; ++i) {
				if (var1[i].stationIsInSystem(stationIdx)) {
					var7 = i;
					break;
				}
			}

			if (!Status.getSystemVisibilities()[var7]) {
				var4 = false;
			}

			if (var1[var7].getNeighbourSystems() == null && var7 != Status.getSystem().getIndex()) {
				var4 = false;
			}
		}

		return stationIdx;
	}

	public final Agent[] createAgents(final Station station) {
		final Agent[] specialAgents = Status.getSpecialAgents();
		int n = 0;
		final boolean b = Status.getCurrentCampaignMission() > 16;
		for (int i = 0; i < specialAgents.length; ++i) {
			if (specialAgents[i].getStationId() == station.getIndex() && b) {
				++n;
			}
		}
		final Agent[] agents = new Agent[AEMath.min(5, n + 3 + GlobalStatus.random.nextInt(2))];
		int n2 = 0;
		for (int i = 0; i < specialAgents.length; ++i) {
			if (specialAgents[i].getStationId() == station.getIndex() && b) {
				agents[n2] = specialAgents[i];
				n2++;
			}
		}
		boolean wingmanGenerated = false;
		for (int i = n2; i < agents.length; ++i) {
			int race = Status.getSystem().getRace();
			if (GlobalStatus.random.nextInt(100) < 20) {
				race = GlobalStatus.random.nextInt(8);
			}
			int l = 0;
			int agentType = -1;
			while (l == 0) {
				agentType = GlobalStatus.random.nextInt(7);
				if ((race != Globals.VOSSK || agentType != 6) && agentType != 4 && agentType != 3) {
					l = 1;
				}
			}
			if (GlobalStatus.random.nextInt(100) < 33 || (agentType == 5 || agentType == 6) && Status.getCurrentCampaignMission() < 16) {
				agentType = 0;
			}
			final boolean male = agentType == 6 || race != Globals.TERRAN || GlobalStatus.random.nextInt(100) < 60;
			final Agent agent = new Agent(-1, Globals.getRandomName(race, male), station.getIndex(), Status.getSystem().getIndex(), race, male, -1, -1, -1);
			agent.setOffer(agentType);
			agent.setImageParts(ImageFactory.createChar(male, race));
			if (agent.getType() == 6) {
				final int nextInt2;
				final String[] array3 = new String[nextInt2 = GlobalStatus.random.nextInt(3)];
				for (int j = 0; j < nextInt2; ++j) {
					array3[j] = Globals.getRandomName(agent.getRace(), true);
				}
				agent.wingman1Name = null;
				agent.wingman2Name = null;
				agent.wingmanFriendsCount = 0;
				if (array3.length > 0 && array3[0] != null) {
					agent.wingman1Name = array3[0];
					++agent.wingmanFriendsCount;
				}
				if (array3.length > 1 && array3[1] != null) {
					agent.wingman2Name = array3[1];
					++agent.wingmanFriendsCount;
				}
				agent.setCosts((nextInt2 + 1) * (700 + GlobalStatus.random.nextInt(1300)));
			}
			else if (agent.getType() == 2) {
				final Item[] items = Globals.getItems();
				int sellItem;
				do {
					sellItem = GlobalStatus.random.nextInt(items.length);
				}while (sellItem == 175 || // void items
						sellItem == 164 ||
						sellItem == 131 ||
						items[sellItem].getBluePrintComponentsIds() != null ||
						items[sellItem].getSinglePrice() == 0);

				final Item item = items[sellItem];
				int quantity = 5 + GlobalStatus.random.nextInt(15);
				if (item.getType() == Item.EQUIPMENT || item.getType() == Item.PRIMARY || item.getType() == Item.TURRET) {
					quantity = 1;
				}
				agent.setSellItemData(sellItem, quantity, quantity * (int)((40 + GlobalStatus.random.nextInt(120)) / 100.0f * Globals.getItems()[sellItem].getSinglePrice()));
			}
			agents[i] = agent;
			if (agents[i].getType() == 6) {
				if (wingmanGenerated) {
					agents[i].setOffer(1);
				}
				wingmanGenerated = true;
			}
			else if (agents[i].getType() == 0) {
				agents[i].setMission(createFreelanceMission(agents[i]));
			}
		}
		final Standing standing = Status.getStanding();
		final int[] warEnemies = { Globals.NIVELIAN, Globals.MIDORIAN, Globals.TERRAN, Globals.VOSSK };
		for (int i = 0; i < warEnemies.length; ++i) {
			final int race = warEnemies[i];
			if (standing.isEnemy(race)) {
				for (int j = 0; j < agents.length; ++j) {
					if (agents[j].isGenericAgent_() && agents[j].getType() != 7) {
						agents[j] = new Agent(-1, Globals.getRandomName(race, true), station.getIndex(), Status.getSystem().getIndex(), race, true, -1, -1, -1);
						agents[j].setOffer(7);
						agents[j].setImageParts(ImageFactory.createChar(true, race));
						break;
					}
				}
			}
		}
		return agents;
	}

	public final Mission createFreelanceMission(final Agent agent) {
		final SolarSystem[] loadSystemsBinary = FileRead.loadSystemsBinary();
		int targetStation = generateStationIndex(loadSystemsBinary, agent.getStationId());
		if (Status.getSystem().getIndex() == 15) {
			targetStation = Status.getSystem().getStations()[0] + GlobalStatus.random.nextInt(4);
		}
		int j = 0;
		int missionType = 0;
		while (j == 0) {
			missionType = GlobalStatus.random.nextInt(13);
			if (missionType != 8) {
				j = 1;
			}
		}
		if (Status.getCurrentCampaignMission() < 16) {
			switch (GlobalStatus.random.nextInt(5)) {
			case 0: {
				missionType = 11;
				break;
			}
			case 1: {
				missionType = 0;
				break;
			}
			case 2: {
				missionType = 7;
				break;
			}
			case 3: {
				missionType = 4;
				break;
			}
			case 4: {
				missionType = 12;
				break;
			}
			}
		}
		if (missionType == 12) {
			targetStation = agent.getStationId();
		}
		if (agent.getType() == 5) {
			missionType = 8;
			targetStation = agent.getStationId();
		}
		if (missionType == 11 || missionType == 0) {
			while (targetStation == Status.getStation().getIndex()) {
				targetStation = generateStationIndex(loadSystemsBinary, agent.getStationId());
			}
		}
		final int race = agent.getRace();
		final String fullName = agent.fullName;
		int commodityAmount = 0;
		int commodity = 0;
		int tecLevel;
		if (Status.getCurrentCampaignMission() < 16) {
			tecLevel = 1 + GlobalStatus.random.nextInt(2);
		}
		else {
			tecLevel = 1 + GlobalStatus.random.nextInt(9);
		}
		switch (missionType) {
		case 8: { //Purchase
			final Item[] items = Globals.getItems();
			int n2;
			do {
				n2 = 97 + GlobalStatus.random.nextInt(items.length - 97);
			} while (items[n2].getSinglePrice() == 0 || n2 == 175 || n2 == 164 || n2 == 131);
			commodity = n2;
			commodityAmount = 5 + GlobalStatus.random.nextInt(15);
			tecLevel = items[commodity].getTecLevel();
			break;
		}
		case 3: // Recovery
		case 5: //	Salvage
			commodityAmount = 2 + (int)(8.0f * (tecLevel / 10.0f));
			commodity = missionType == 3 ? 116 : 117;
			break;
		case 0: //Courier
			commodityAmount = 5 + (int)(95.0f * (tecLevel / 10.0f));
			commodity = GlobalStatus.random.nextInt(7);
			break;
		case 11: //Passenger/Reach destination
			commodityAmount = 2 + (int)(18.0f * (tecLevel / 10.0f));
			break;
		case 2: //Protection
			commodityAmount = 2 + (int)(float)GlobalStatus.random.nextInt(4);
			break;
		default:
			break;
		}
		final int min = AEMath.min(10, tecLevel);
		float reward = (1500 + (int)(min / 10.0f * 5500.0f))
				* (Galaxy.distance(
						loadSystemsBinary[Status.getStation().getSystemIndex()], 
						loadSystemsBinary[Galaxy.getStation(targetStation).getSystemIndex()]
						) / 1200.0f + 1.0f)
				+ Status.getLevel() * 200;
		if (missionType == 7) {
			reward *= 0.7f;
		}
		else if (missionType == 9) {
			reward *= 1.2f;
		}
		else if (missionType == 8) {
			reward = reward / 2.0f + commodityAmount * Globals.getItems()[commodity].getMaxPrice() * 3;
		}
		else if (missionType == 11) {
			final float n4 = reward * 0.6f;
			reward = n4 + commodityAmount * (n4 / 5.0f);
		}
		else if (missionType == 5 || missionType == 3) {
			reward *= 2.0f;
		}
		int rest = (int)reward % 50;
		reward = (reward + rest) % 50.0f == 0.0f ? reward + rest : reward - rest;
		final Mission mission = new Mission(missionType, fullName, agent.getImageParts(), race, (int)reward, targetStation, min);
		int bonus = (int)(reward / 10.0f + GlobalStatus.random.nextInt((int)reward / 10));
		if (missionType == 8) {
			bonus *= 0.5f;
		}
		else if (missionType == 6) {
			mission.setTargetName(Globals.getRandomName(0, true));
		}
		rest = bonus % 50;
		mission.setBonus((bonus + rest) % 50 == 0 ? bonus + rest : bonus - rest);
		mission.setCommodity(commodity, commodityAmount);
		return mission;
	}
}
