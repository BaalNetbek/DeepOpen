package GoF2;

import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.ImageFactory;

public final class Generator {
	private final int[] storySignificantStations_ = {10, 22, 27, 29, 30, 48, 55, 56, 76, 79, 91, 98};
	private final int[] itemTypeOccurance = {5, 20, 2, 5, 100};

	public static Ship[] getShipBuyList(final Station var0) {
		if (var0.getSystemIndex() == 15 && Status.getCurrentCampaignMission() < 16) {
			return null;
		}
		Ship[] var1 = null;
		final int var2 = Status.getSystem().getRace();
		final boolean var7 = var0.getId() == 10 && Achievements.gotAllGoldMedals();
		final int var10000 = var2 != 1 && !var7 ? GlobalStatus.random.nextInt(6) : 1;
		final int var8 = var10000;
		if (var10000 == 0) {
			return null;
		} else {
			var1 = new Ship[var8];

			for(int var3 = 0; var3 < var1.length; ++var3) {
				int var4 = 0;
				boolean var5 = false;

				while(true) {
					while(!var5) {
						var4 = var7 ? 8 : Globals.getRandomEnemyFighter(var2);
						var5 = true;

						for(int var6 = 0; var6 < var1.length; ++var6) {
							if (var1[var6] != null && var1[var6].getIndex() == var4) {
								var5 = false;
								break;
							}
						}
					}

					var1[var3] = Globals.getShips()[var4].cloneBase();
					var1[var3].setRace(Status.getSystem().getRace());
					var1[var3].adjustPrice();
					break;
				}
			}

			return var1;
		}
	}

	public final int[] getLootList() {
		final Item[] var1 = Globals.getItems();
		int var2 = GlobalStatus.random.nextInt(3);
		if (var2 == 0) {
			return null;
		}
		final int[] var9 = new int[var2 << 1];
		final Item[] var3 = Globals.getItems();
		int var4 = 0;

		for(int var5 = 0; var5 < var9.length; var5 += 2) {
			boolean var6 = false;
			int var7 = 0;

			for(int var8 = 0; var8 < 100 && !var6; ++var8) {
				var7 = GlobalStatus.random.nextInt(var1.length);
				var4 = var3[var7].getType();
				if (var3[var7].getBluePrintComponentsIds() == null && GlobalStatus.random.nextInt(100) < this.itemTypeOccurance[var4] && GlobalStatus.random.nextInt(100) < var1[var7].getOccurance() && var3[var7].getSinglePrice() > 0 && var7 != 175 && var7 != 164 && (var4 == 4 || var3[var7].getTecLevel() <= 7)) {
					var6 = true;
				}
			}

			if (!var6) {
				var7 = 154 + GlobalStatus.random.nextInt(10);
				var4 = 4;
			}

			var9[var5] = var7;
			if (var4 == 4) {
				var9[var5 + 1] = 1 + GlobalStatus.random.nextInt(9);
			} else {
				var9[var5 + 1] = 1;
			}
		}

		return var9;
	}

	private int pickNextDestination(final SolarSystem[] var1, final int var2) {
		int var3 = 0;
		boolean var4 = false;

		while(!var4) {
			if (GlobalStatus.random.nextInt(100) < 20) {
				var3 = var2;
			} else if (GlobalStatus.random.nextInt(100) < 40) {
				int[] var5;
				var3 = (var5 = var1[Status.getSystem().getId()].getStations())[GlobalStatus.random.nextInt(var5.length)];
			} else {
				var3 = GlobalStatus.random.nextInt(100);
			}

			if (Status.getSystem().getId() == 15) {
				var3 = Status.getSystem().getStations()[GlobalStatus.random.nextInt(Status.getSystem().getStations().length)];
			}

			var4 = true;

			int var7;
			for(var7 = 0; var7 < this.storySignificantStations_.length; ++var7) {
				if (var3 == this.storySignificantStations_[var7]) {
					var4 = false;
					break;
				}
			}

			var7 = 0;

			for(int var6 = 0; var6 < var1.length; ++var6) {
				if (var1[var6].stationIsInSystem(var3)) {
					var7 = var6;
					break;
				}
			}

			if (!Status.getVisibleSystems()[var7]) {
				var4 = false;
			}

			if (var1[var7].getNeighbourSystems() == null && var7 != Status.getSystem().getId()) {
				var4 = false;
			}
		}

		return var3;
	}

	public final Agent[] createAgents(final Station station) {
		final Agent[] specialAgents = Status.getSpecialAgents();
		int n = 0;
		final boolean b = Status.getCurrentCampaignMission() > 16;
		for (int i = 0; i < specialAgents.length; ++i) {
			if (specialAgents[i].getStationId() == station.getId() && b) {
				++n;
			}
		}
		final Agent[] agents = new Agent[AEMath.min(5, n + 3 + GlobalStatus.random.nextInt(2))];
		int n2 = 0;
		for (int i = 0; i < specialAgents.length; ++i) {
			if (specialAgents[i].getStationId() == station.getId() && b) {
				agents[n2] = specialAgents[i];
				n2++;
			}
		}
		boolean wingmanGenerated = false;
		for (int i = n2; i < agents.length; ++i) {
			int n5 = Status.getSystem().getRace();
			if (GlobalStatus.random.nextInt(100) < 20) {
				n5 = GlobalStatus.random.nextInt(8);
			}
			int l = 0;
			int nextInt = -1;
			while (l == 0) {
				nextInt = GlobalStatus.random.nextInt(7);
				if ((n5 != 1 || nextInt != 6) && nextInt != 4 && nextInt != 3) {
					l = 1;
				}
			}
			if (GlobalStatus.random.nextInt(100) < 33 || (nextInt == 5 || nextInt == 6) && Status.getCurrentCampaignMission() < 16) {
				nextInt = 0;
			}
			final boolean b2 = nextInt == 6 || n5 != 0 || GlobalStatus.random.nextInt(100) < 60;
			final Agent agent = new Agent(-1, Globals.getRandomName(n5, b2), station.getId(), Status.getSystem().getId(), n5, b2, -1, -1, -1);
			agent.setType(nextInt);
			agent.setImageParts(ImageFactory.createChar(b2, n5));
			if (agent.getType() == 6) {
				final int nextInt2;
				final String[] array3 = new String[nextInt2 = GlobalStatus.random.nextInt(3)];
				for (int n6 = 0; n6 < nextInt2; ++n6) {
					array3[n6] = Globals.getRandomName(agent.getRace(), true);
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
				if (item.getType() == 3 || item.getType() == 0 || item.getType() == 2) {
					quantity = 1;
				}
				agent.setSellItem(sellItem, quantity, quantity * (int)((40 + GlobalStatus.random.nextInt(120)) / 100.0f * Globals.getItems()[sellItem].getSinglePrice()));
			}
			agents[i] = agent;
			if (agents[i].getType() == 6) {
				if (wingmanGenerated) {
					agents[i].setType(1);
				}
				wingmanGenerated = true;
			}
			else if (agents[i].getType() == 0) {
				agents[i].setMission(createFreelanceMission(agents[i]));
			}
		}
		final Standing standing = Status.getStanding();
		final int[] array5 = { 2, 3, 0, 1 };
		for (int i = 0; i < array5.length; ++i) {
			final int n9 = array5[i];
			if (standing.isEnemy(n9)) {
				for (int n10 = 0; n10 < agents.length; ++n10) {
					if (agents[n10].isGenericAgent_() && agents[n10].getType() != 7) {
						agents[n10] = new Agent(-1, Globals.getRandomName(n9, true), station.getId(), Status.getSystem().getId(), n9, true, -1, -1, -1);
						agents[n10].setType(7);
						agents[n10].setImageParts(ImageFactory.createChar(true, n9));
						break;
					}
				}
			}
		}
		return agents;
	}

	public final Mission createFreelanceMission(final Agent agent) {
		new FileRead();
		final SolarSystem[] loadSystemsBinary = FileRead.loadSystemsBinary();
		int i = pickNextDestination(loadSystemsBinary, agent.getStationId());
		if (Status.getSystem().getId() == 15) {
			i = Status.getSystem().getStations()[0] + GlobalStatus.random.nextInt(4);
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
			i = agent.getStationId();
		}
		if (agent.getType() == 5) {
			missionType = 8;
			i = agent.getStationId();
		}
		if (missionType == 11 || missionType == 0) {
			while (i == Status.getStation().getId()) {
				i = pickNextDestination(loadSystemsBinary, agent.getStationId());
			}
		}
		final int race = agent.getRace();
		final String fullName = agent.fullName;
		int n = 0;
		int nextInt2 = 0;
		int tecLevel;
		if (Status.getCurrentCampaignMission() < 16) {
			tecLevel = 1 + GlobalStatus.random.nextInt(2);
		}
		else {
			tecLevel = 1 + GlobalStatus.random.nextInt(9);
		}
		switch (missionType) {
		case 8: {
			final Item[] items = Globals.getItems();
			int n2;
			do {
				n2 = 97 + GlobalStatus.random.nextInt(items.length - 97);
			} while (items[n2].getSinglePrice() == 0 || n2 == 175 || n2 == 164 || n2 == 131);
			nextInt2 = n2;
			n = 5 + GlobalStatus.random.nextInt(15);
			tecLevel = items[nextInt2].getTecLevel();
			break;
		}
		case 3:
		case 5:
			n = 2 + (int)(8.0f * (tecLevel / 10.0f));
			nextInt2 = missionType == 3 ? 116 : 117;
			break;
		case 0:
			n = 5 + (int)(95.0f * (tecLevel / 10.0f));
			nextInt2 = GlobalStatus.random.nextInt(7);
			break;
		case 11:
			n = 2 + (int)(18.0f * (tecLevel / 10.0f));
			break;
		case 2:
			n = 2 + (int)(float)GlobalStatus.random.nextInt(4);
			break;
		default:
			break;
		}
		final int min = AEMath.min(10, tecLevel);
		float n3 = (1500 + (int)(min / 10.0f * 5500.0f)) * (1.0f + Galaxy.distance(loadSystemsBinary[Status.getStation().getSystemIndex()], loadSystemsBinary[Galaxy.getStation(i).getSystemIndex()]) / 1200.0f) + Status.getLevel() * 200;
		if (missionType == 7) {
			n3 *= 0.7f;
		}
		else if (missionType == 9) {
			n3 *= 1.2f;
		}
		else if (missionType == 8) {
			n3 = n3 / 2.0f + n * Globals.getItems()[nextInt2].getMaxPrice() * 3;
		}
		else if (missionType == 11) {
			final float n4 = n3 * 0.6f;
			n3 = n4 + n * (n4 / 5.0f);
		}
		else if (missionType == 5 || missionType == 3) {
			n3 *= 2.0f;
		}
		final int n5 = (int)n3 % 50;
		final float n6 = (n3 + n5) % 50.0f == 0.0f ? n3 + n5 : n3 - n5;
		final Mission mission = new Mission(missionType, fullName, agent.getFace(), race, (int)n6, i, min);
		int n7 = (int)(n6 / 10.0f + GlobalStatus.random.nextInt((int)n6 / 10));
		if (missionType == 8) {
			n7 *= 0.5f;
		}
		else if (missionType == 6) {
			mission.setTargetName(Globals.getRandomName(0, true));
		}
		final int n8 = n7 % 50;
		mission.setBonus((n7 + n8) % 50 == 0 ? n7 + n8 : n7 - n8);
		mission.setCommodity(nextInt2, n);
		return mission;
	}
}
