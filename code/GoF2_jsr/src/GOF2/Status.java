package GOF2;

import java.util.Vector;

import AE.GlobalStatus;
import AE.Math.AEMath;

/**
 * Contains and manages shared variables holding
 * current state, progress and objects in game.
 * Many of them are saved by RecordHandler.
 * 
 * @author Fishlabs 2009
 */
public final class Status {
    private static int credits;
    private static int rating;
    private static long playTime;
    private static int kills;
    private static int missionsCount;
    private static int level = 1;
    private static int lastXP;
    private static int stationsVisited;
    private static int bluePrintsProduced;
    private static int pirateKills;
    private static int jumpgatesUsed;
    private static int cargoSalvaged;
    private static int unused_;
    private static int currentCampaignMissionIndex;
    public static Standing standing;
    private static Ship playersShip;
    private static Mission mission;
    private static Mission[] currentMissions = new Mission[2];
    private static Station currentStation;
    private static Station[] lastVisitedStations = new Station[3];
    public static BluePrint[] blueprints;
    private static SolarSystem currentSolarSystem;
    private static String[] currSysPlanetNames;
    private static int[] currSysPlanetTextures;
    public static PendingProduct[] waitingGoods;
    public static Agent[] specialAgents;
    public static String[] wingmenNames;
    public static byte[] wingmanFace;
    public static int wingmanRace;
    public static int wingmenTimeRemaining;
    public static int passengerCount;
    public static boolean[] visibleSystems = new boolean[22];
    public static int[] lowestItemPrices;
    public static int[] highestItemPrices;
    public static byte[] lowestItemPriceSystems;
    public static byte[] highestItemPriceSystems;
    public static int shield;
    public static int additionalArmour;
    public static int baseArmour;
    public static long lastLoadingTime;
    public static Station voidStation = new Station();
    public static int wormholeSystem;
    public static int wormholeStation;
    public static int lastVisitedNonVoidOrbit;
    public static int wormHoleTick;
    public static long loadingTime;
    public static long loadingsCount;
    public static boolean[] minedOreTypes = new boolean[11];
    public static boolean[] minedCoreTypes = new boolean[11];
    public static int missionGoodsCarried;
    public static int minedOre;
    public static int minedCores;
    public static int boughtBooze;
    public static boolean[] drinkTypesPossesed = new boolean[22];
    public static int destroyedJunk;
    public static boolean[] systemsVisited = new boolean[22];
    public static int passengersCarried;
    public static long invisibleTime;
    public static int bombsUsed;
    public static int alienJunkSalvaged;
    public static int barInteractions;
    public static int commandedWingmen;
    public static int asteroidsDestroyed;
    public static int maxFreeCargo;
    public static int missionsRejected;
    public static int askedToRepeate;
    public static int acceptedNotAskingDifficulty;
    public static int acceptedNotAskingLocation;

    public static void addPendingProduct(final BluePrint bp) {
        int var1 = 0;
        if (waitingGoods == null) {
            waitingGoods = new PendingProduct[1];
        } else {
            for(int i = 0; i < waitingGoods.length; ++i) {
                if (waitingGoods[i] != null 
               		 && waitingGoods[i].index == bp.getIndex() 
               		 && waitingGoods[i].stationId == bp.productionStationId) {
                    waitingGoods[i].producedQuantity += bp.getTonsPerProduction2();
                    return;
                }
            }

            for(int i = 0; i < waitingGoods.length; ++i) {
                if (waitingGoods[i] == null) {
                    waitingGoods[i] = new PendingProduct(bp);
                    return;
                }
            }

            final PendingProduct[] var4 = new PendingProduct[waitingGoods.length + 1];

            for(int i = 0; i < waitingGoods.length; ++i) {
                var4[var1] = waitingGoods[i];
                var1++;
            }

            waitingGoods = null;
            waitingGoods = var4;
        }

        waitingGoods[var1] = new PendingProduct(bp);
    }

    public static PendingProduct[] getPendingProducts() {
        return waitingGoods;
    }

    public static boolean[] getSystemVisibilities() {
        return visibleSystems;
    }

    public static void setSystemVisibility(final int var0, final boolean var1) {
        visibleSystems[var0] = true;
    }

    public static Station[] getStationStack() {
        return lastVisitedStations;
    }

    public static void setStationStack(final Station[] var0) {
        lastVisitedStations = var0;
    }

    private static Station isOnStack(final Station var0) {
        for(int i = 0; i < 3; ++i) {
            if (lastVisitedStations[i] != null && lastVisitedStations[i].equals(var0)) {
                return lastVisitedStations[i];
            }
        }

        return null;
    }
    /** More like departOrbit and init next Station **/
    public static void departStation(final Station destination) {
        if (destination != voidStation) {
      	  	// addStationToStack
            final Station alreadyStacked = isOnStack(destination);
	         if (alreadyStacked != null) {
	             setStation(alreadyStacked);
	         } 
	         else {                 
	             if (lastVisitedStations[0] != null) {
	                  for(int i = 2; i > 0; --i) {
	                      lastVisitedStations[i] = lastVisitedStations[i - 1];
	                  }
	                  lastVisitedStations[0] = destination;
	                  setStation(destination);
	             } 
	             else {
	                 for(int i = 2; i >= 0; --i) {
	                     if (lastVisitedStations[i] == null) {
	                         lastVisitedStations[i] = destination;
	                         setStation(destination);
	                         break;
	                     }
	                 }
	             }
	         }


            if (alreadyStacked == null) {
                final Generator gen = new Generator();
                Item[] shopItems;
                if (destination.getIndex() == 78 && currentCampaignMissionIndex < 7) {
                    Item[] gunantsScrap = new Item[3];
                    gunantsScrap[0] = Globals.getItems()[0].getCopyInAmmount(1, 0);
                    gunantsScrap[1] = Globals.getItems()[22].getCopyInAmmount(1, 0);
                    gunantsScrap[2] = Globals.getItems()[55].getCopyInAmmount(1, 0);
                    shopItems = gunantsScrap;
                } else {
                    final Vector shopItemsVec = new Vector();
                    final Item[] items = Globals.getItems();
                    //new FileRead();
                    final SolarSystem[] var14 = FileRead.loadSystemsBinary();
                    int stationLvl = destination.getTecLevel();
                    final int halfStationLvl = stationLvl < 4 ? 1 : stationLvl / 2;
                    if (currentCampaignMissionIndex > 16 
                  		  && currentCampaignMissionIndex < 25 
                  		  && GlobalStatus.random.nextInt(100) < 40) {
                        shopItemsVec.addElement(items[68].makeItem());
                    }

                    GlobalStatus.random.setSeed(System.currentTimeMillis());

                    for(int i = 0; i < items.length; ++i) {
                        final Item item = items[i];
                        boolean guaranteed = false;
                        if (destination.getIndex() == item.getAttribute(Item.GUARANTEED_TO_HAVE_SYSTEM)) {
                            guaranteed = true;
                        }

                        int itemLvl = item.getTecLevel();
                        final boolean booze = item.getIndex() >= 132 && item.getIndex() < 154;
								if (guaranteed || item.getBluePrintComponentsIds() == null
										&& i != 175 && i != 164 // void crystals
								      && item.getTecLevel() <= destination.getTecLevel()
								      && item.getOccurance() != 0
								      && item.getSinglePrice() != 0
								      && (item.getAttribute(Item.RACE) != Globals.VOSSK || var14[destination.getSystemIndex()].getRace() == Globals.VOSSK)
								      && (!booze || item.getIndex() == 132 + destination.getSystemIndex())) {
								    int lowToHere, ammount;
									 if (guaranteed || (booze || itemLvl <= stationLvl && itemLvl >= halfStationLvl)
											 && GlobalStatus.random.nextInt(100) < item.getOccurance()) {
                                lowToHere = Galaxy.invDistancePercent(
                              		  var14[destination.getSystemIndex()].getPosX(),
                              		  var14[destination.getSystemIndex()].getPosY(),
                              		  var14[item.getLowestPriceSystemId()].getPosX(),
                              		  var14[item.getLowestPriceSystemId()].getPosY());
                                ammount = 5 + GlobalStatus.random.nextInt(15);
                                if (item.getType() != Item.COMMODITY && item.getType() != Item.SECONDARY) {
                                    ammount = AEMath.max(1, ammount / 5);
                                } else if (item.getType() == 4 && lowToHere > 50) {
                                    ammount *= AEMath.max(1, (int)((lowToHere - 50) / 50.0F * 20.0F));
                                }

                                shopItemsVec.addElement(item.makeItem(ammount));
                            }
                        }
                    }

                    final Item[] newShopItems = new Item[shopItemsVec.size()];
                    for(int i = 0; i < shopItemsVec.size(); ++i) {
                        newShopItems[i] = (Item)shopItemsVec.elementAt(i);
                    }

                    shopItems = newShopItems;
                }

                destination.setShopItems(shopItems);
                destination.setShopShips(Generator.getShipBuyList(destination));
                destination.setBarAgents(gen.createAgents(destination));
            }

            lastLoadingTime = playTime;
        }

        mission = Mission.emptyMission_;

        for(int i = 0; i < currentMissions.length; ++i) {
            if (currentMissions[i] != null) {
                if (currentMissions[i].isCampaignMission() && currentMissions[i].getType() == Mission.TYPE_25 && destination.getIndex() == wormholeStation) {
                    mission = currentMissions[i];
                    break;
                }

						if (currentMissions[i].getTargetStation() == destination.getIndex()
							&& currentMissions[i].getType() != 8
					      && currentMissions[i].getType() != 19
					      && currentMissions[i].getType() != 16
					      && currentMissions[i].getType() != 14
					      && currentMissions[i].getType() != 13) {
							if (currentMissions[i].isCampaignMission() || currentMissions[i].getType() != 11) {
								mission = currentMissions[i];
							}
							break;
						}
            }
        }

        if (!gameWon() && currentCampaignMissionIndex >= 32 && destination.getIndex() != currentMissions[0].getTargetStation()) {
           if (++wormHoleTick > 10) {
              wormHoleTick = 0;
              boolean var19 = false;

              while (!var19) {
                 wormholeSystem = GlobalStatus.random.nextInt(22);
                 if (visibleSystems[wormholeSystem]
                    && wormholeSystem != 10
                    && wormholeSystem != 15
                    && (!mission.isCampaignMission() || mission.getTargetStation() != wormholeStation)) {
                    var19 = true;
                    SolarSystem var20 = Galaxy.loadSystem_(wormholeSystem);
                    wormholeStation = var20.getStations()[GlobalStatus.random.nextInt(var20.getStations().length)];
                 }
              }

              return;
           }
        } else if (gameWon()) {
           wormholeStation = -10;
           wormholeSystem = -10;
        }

    }

    public static void setPassengers(final int var0) {
        passengerCount = var0;
    }

    public static Mission getMission() {
        return mission;
    }

    public static void setMission(final Mission var0) {
        mission = var0;
    }

    public static Mission getFreelanceMission() {
        return currentMissions[1];
    }

    public static void setFreelanceMission(final Mission var0) {
        currentMissions[1] = var0;
    }

    public static Mission getCampaignMission() {
        return currentMissions[0];
    }

    public static void setCampaignMission(final Mission mission) {
        mission.setCampaignMission(true);
        currentMissions[0] = mission;
    }

    public static void incMissionCount() {
        ++missionsCount;
    }

    public static void setCurrentCampaignMission(final int var0) {
        currentCampaignMissionIndex = var0;
    }

    public static int getCurrentCampaignMission() {
        return currentCampaignMissionIndex;
    }

    public static void nextCampaignMission() {
        Item[] eq, cargo;
        switch(++currentCampaignMissionIndex) {
        case Mission.STORY_1:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 78));
            return;
        case Mission.STORY_2:
            setCampaignMission(new Mission(Mission.TYPE_18, 0, 78));
            currentMissions[0].setStatusValue(10);
            return;
        case Mission.STORY_3:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 78));
            return;
        case Mission.STORY_4:
            playersShip.setCargo((Item[])null);
            setCampaignMission(new Mission(Mission.TYPE_18, 0, 78));
            currentMissions[0].setStatusValue(25);
            return;
        case Mission.STORY_5:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 78));
            return;
        case Mission.STORY_6:
            playersShip.removeAllCargo();
            setCampaignMission(new Mission(Mission.TYPE_22, 0, 78));
            return;
        case Mission.STORY_7:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 78));
            return;
        case Mission.STORY_8:
            eq = playersShip.getEquipment();
            if (eq != null) {
                for(int i = 0; i < eq.length; ++i) {
                    if (eq[i] != null) {
                        eq[i].setUnsaleable(false);
                        eq[i].setPrice(Globals.getItems()[i].getSinglePrice());
                    }
                }
            }

            cargo = playersShip.getCargo();
            if (cargo != null) {
                for(int i = 0; i < cargo.length; ++i) {
                    if (cargo[i] != null) {
                        cargo[i].setUnsaleable(false);
                        cargo[i].setPrice(Globals.getItems()[i].getSinglePrice());
                    }
                }
            }

            setCampaignMission(new Mission(Mission.TYPE_11, 0, 78));
            return;
        case Mission.STORY_9:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 78));
            return;
        case Mission.STORY_10:
            final Item var4 = playersShip.getFirstEquipmentOfSort(Item.MINING_LASER);
            playersShip.removeEquipment(var4);
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 79));
            return;
        case Mission.STORY_11:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 76));
            return;
        case Mission.STORY_12:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 79));
            return;
        case Mission.STORY_13:
            setCampaignMission(new Mission(Mission.TYPE_13, 0, 0));
            currentMissions[0].setStatusValue(missionsCount + 2);
            currentMissions[0].setVisible(false);
            return;
        case Mission.STORY_14:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 79));
            return;
        case Mission.STORY_15:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 98));
            return;
        case Mission.STORY_16:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 98));
            return;
        case Mission.STORY_17:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 98));
            return;
        case Mission.STORY_18:
            playersShip.setRace(0);
            setCampaignMission(new Mission(Mission.TYPE_20, 0, 56));
            return;
        case Mission.STORY_19:
            setCampaignMission(new Mission(Mission.TYPE_20, 0, 55));
            return;
        case Mission.STORY_20:
            setCampaignMission(new Mission(Mission.TYPE_23, 0, 55));
            currentMissions[0].setStatusValue(6);
            return;
        case Mission.STORY_21:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 55));
            return;
        case Mission.STORY_22:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 55));
            return;
        case Mission.STORY_23:
            final byte var3 = 6;
            visibleSystems[var3] = true;
            setCampaignMission(new Mission(Mission.TYPE_11, 20000, 10));
            return;
        case Mission.STORY_24:
            wormholeStation = 48;
            wormholeSystem = 9;
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 48));
            return;
        case Mission.STORY_25:
      	  	cargo = playersShip.getCargo();
            if ((cargo = playersShip.getCargo()) != null) {
                for(int i = 0; i < cargo.length; ++i) {
                    if (cargo[i].getIndex() == 131) {
                  	   cargo[i].setUnsaleable(true);
                        break;
                    }
                }
            }

            setCampaignMission(new Mission(Mission.TYPE_20, 0, -1));
            return;
        case Mission.STORY_26:
            wormholeStation = -1;
            wormholeSystem = -1;
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 48));
            return;
        case Mission.STORY_27:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 10));
            return;
        case Mission.STORY_28:
            wormholeStation = 91;
            wormholeSystem = 18;
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 91));
            return;
        case Mission.STORY_29:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, -1));
            return;
        case Mission.STORY_30:
            setCampaignMission(new Mission(Mission.TYPE_20, 0, 91));
            return;
        case Mission.STORY_31:
            setCampaignMission(new Mission(Mission.TYPE_11, 30000, 98));
            return;
        case Mission.STORY_32:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 10));
            return;
        case Mission.STORY_33:
            setCampaignMission(new Mission(Mission.TYPE_8, 0, 10));
            currentMissions[0].setCommodity(164, 50);
            return;
        case Mission.STORY_34:
            playersShip.removeCargo(164, 50);

            for(int i = 0; i < blueprints.length; ++i) {
                if (blueprints[i].getIndex() == 85) {
                    blueprints[i].unlocked = true;
                    blueprints[i].startProduction(Globals.getItems()[164].makeItem(), 50, -1);
                    blueprints[i].investedCredits = 0;
                }
            }

            setCampaignMission(new Mission(Mission.TYPE_11, 0, 30));
            return;
        case Mission.STORY_35:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 29));
            return;
        case Mission.STORY_36:
            setCampaignMission(new Mission(Mission.TYPE_12, 0, 27));
            return;
        case Mission.STORY_37:
            setCampaignMission(new Mission(Mission.TYPE_24, 0, 27));
            currentMissions[0].setVisible(false);
            return;
        case Mission.STORY_38:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, 22));
            return;
        case Mission.STORY_39:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 30));
            return;
        case Mission.STORY_40:
            setCampaignMission(new Mission(Mission.TYPE_25, 0, -1));
            return;
        case Mission.STORY_41:
            setCampaignMission(new Mission(Mission.TYPE_4, 0, -1));
            return;
        case Mission.STORY_42:
            wormholeStation = -10;
            wormholeSystem = -10;
            setCampaignMission(new Mission(Mission.TYPE_24, 0, -1));
            return;
        case Mission.STORY_43:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 98));
            return;
        case Mission.STORY_44:
            setCampaignMission(new Mission(Mission.TYPE_11, 0, 98));
            return;
        case Mission.STORY_45:
            wormholeStation = -10;
            wormholeSystem = -10;
            setCampaignMission(Mission.emptyMission_);
            currentMissions[0].setVisible(false);
            changeCredits(40000);
        default:
        }
    }

    public static boolean gameWon() {
        return currentCampaignMissionIndex > 44;
    }

    public static boolean inEmptyOrbit() {
        return currentCampaignMissionIndex < 2 && currentStation.getIndex() == 78 || inAlienOrbit() && currentCampaignMissionIndex > 42;
    }

    public static boolean inAlienOrbit() {
        return currentStation.equals(voidStation);
    }

    public static Mission missionCompleted_(final boolean var0, final long var1) {
       // label140:
            for(int i = 0; i < currentMissions.length; ++i) {
                Mission mission = currentMissions[i];
                if (mission.hasWon()) {
                    return null;
                }

                if (mission != null) {
                    Item[] eq;

                    switch(mission.getType()) {
                    case Mission.STORY_0:
                    case Mission.STORY_11:
                        if (var0 && currentStation.getIndex() == mission.getTargetStation()) {
                            return mission;
                        }
                    case Mission.STORY_1:
                    case Mission.STORY_2:
                    case Mission.STORY_3:
                    case Mission.STORY_4:
                    case Mission.STORY_5:
                    case Mission.STORY_6:
                    case Mission.STORY_7:
                    case Mission.STORY_9:
                    case Mission.STORY_10:
                    case Mission.STORY_12:
                    case Mission.STORY_17:
                    default:
                        break;
                    case Mission.STORY_8:
                        if (var0 && currentStation.getIndex() == mission.getTargetStation() && Item.isInList(mission.getCommodityIndex(), mission.getCommodityAmount_(), playersShip.getCargo())) {
                            return mission;
                        }
                        break;
                    case Mission.STORY_13:
                        if (missionsCount >= mission.getStatusValue_()) {
                            mission.setWon(true);
                            return mission;
                        }
                        break;
                    case Mission.STORY_14:
                        if (kills >= mission.getStatusValue_()) {
                            mission.setWon(true);
                            return mission;
                        }
                        break;
                    case Mission.STORY_15:
                        eq = playersShip.getEquipment();
                        //j = 0;
                        for (int j = 0; j < eq.length; j++) {
                        	if (eq[j] != null && eq[j].getIndex() == mission.getStatusValue_()) {
                              return mission;
                          }
                        }
                        break;
//                        while(true) {
//                            if (var6 >= var5.length) {
//                                continue label140;
//                            }
//
//                            if (var5[var6] != null && var5[var6].getIndex() == var4.getStatusValue_()) {
//                                return var4;
//                            }
//
//                            ++var6;
//                        }
                    case Mission.STORY_16:
                        if (stationsVisited >= mission.getStatusValue_()) {
                            mission.setWon(true);
                            return mission;
                        }
                        break;
                    case Mission.STORY_18:
                        if (playersShip.getCurrentLoad() >= mission.getStatusValue_()) {
                            mission.setWon(true);
                            return mission;
                        }
                        break;
                    case Mission.STORY_19:
                        if (bluePrintsProduced >= mission.getStatusValue_()) {
                            mission.setWon(true);
                            return mission;
                        }
                        break;
                    case Mission.STORY_20:
                        if (!var0 && currentStation.getIndex() == mission.getTargetStation() && var1 > 10000L) {
                            return mission;
                        }
                        break;
                    case Mission.STORY_21:
                        eq = playersShip.getEquipment();
                        //j = 0;
                        
                        for (int j = 0; j < eq.length; j++) {
	                     	if (eq[j] != null && eq[j].getType() == mission.getStatusValue_()) {
	                           return mission;
	                       }
                        }
                        break;
//                        while(true) {
//                            if (var6 >= var5.length) {
//                                continue label140;
//                            }
//
//                            if (var5[var6] != null && var5[var6].getType() == var4.getStatusValue_()) {
//                                return var4;
//                            }
//
//                            ++var6;
//                        }
                    case Mission.STORY_22:
                        eq = playersShip.getEquipment();
                        boolean var9 = false;
                        boolean var7 = false;

                        for(int j = 0; j < eq.length; ++j) {
                            if (eq[j] != null && eq[j].getType() == 0) {
                                var9 = true;
                            } else if (eq[j] != null && eq[j].getSort() == 10) {
                                var7 = true;
                            }
                        }

                        if (var9 && var7) {
                            return mission;
                        }
                        break;
                    case Mission.STORY_23:
                        if (var0) {
                            eq = playersShip.getEquipment();

                            for(int j = 0; j < eq.length; ++j) {
                                if (eq[j] != null && eq[j].getSort() == mission.getStatusValue_()) {
                                    return mission;
                                }
                            }
                        }
                        break;
                    case Mission.STORY_24:
                        if (var0 || !var0 && currentStation.getIndex() != mission.getTargetStation() && var1 > 10000L) {
                            return mission;
                        }
                    }
                }
            }

        return null;
    }

    public static void setJumpgateUsed(final int n) {
        jumpgatesUsed = n;
    }

    public static void jumpgateUsed() {
        ++jumpgatesUsed;
    }

    public static int getJumpgateUsed() {
        return jumpgatesUsed;
    }

    public static void incCargoSalvaged(final int n) {
        cargoSalvaged += n;
    }

    public static void setCargoSalvaged(final int n) {
        cargoSalvaged = n;
    }

    public static int getCargoSalvaged() {
        return cargoSalvaged;
    }

    public static void getUnusedVar(final int var0) {
        unused_ = var0;
    }

    public static int setUnusedVar() {
        return unused_;
    }

    public static void removeMission_(final Mission mis) {
        for(int i = 0; i < currentMissions.length; ++i) {
            if (currentMissions[i] == mis) {
                currentMissions[i] = Mission.emptyMission_;
            }
        }

        if (mission == mis) {
            mission = null;
        }

    }

    public static void incStationsVisited() {
        ++stationsVisited;
    }

    public static int getStationsVisited() {
        return stationsVisited;
    }

    public static Ship getShip() {
        return playersShip;
    }

    public static void setShip(final Ship ship) {
        playersShip = ship;
    }

    public static Station getStation() {
        return currentStation;
    }
    /** Sets current station and inits SolarSystem. **/
    public static void setStation(final Station currentOrbit) {
        currentStation = currentOrbit;
        currentSolarSystem = Galaxy.loadSystem_(currentOrbit.getSystemIndex());
        if (currentSolarSystem != null) {
            systemsVisited[currentStation.getSystemIndex()] = true;
            //new FileRead();
            final Station[] localStations = FileRead.loadStationsBinary(currentSolarSystem);
            currSysPlanetNames = null;
            currSysPlanetNames = new String[localStations.length];
            currSysPlanetTextures = new int[localStations.length];

            for(int i = 0; i < currentSolarSystem.getStations().length; ++i) {
                for(int j = 0; j < localStations.length; ++j) {
                    if (currentSolarSystem.getStations()[i] == localStations[j].getIndex()) {
                        if (currentCampaignMissionIndex == 0) {
                            currSysPlanetNames[i] = "";
                        } else {
                            currSysPlanetNames[i] = localStations[j].getName();
                        }

                        currSysPlanetTextures[i] = localStations[j].getPlanetTextureId();
                        break;
                    }
                }
            }

        }
    }

    public static String[] getPlanetNames() {
        return currSysPlanetNames;
    }

    public static int[] getPlanetTextures() {
        return currSysPlanetTextures;
    }

    public static SolarSystem getSystem() {
        return currentSolarSystem;
    }

    public static int getCredits() {
        return credits;
    }

    public static void setRating(final int var0) {
        rating = var0;
    }

    public static void setPlayingTime(final long var0) {
        playTime = var0;
    }

    public static void setKills(final int var0) {
        kills = var0;
    }

    public static void setMissionCount(final int var0) {
        missionsCount = var0;
    }

    public static void setLevel(final int var0) {
        level = var0;
    }

    public static void setLastXP(final int var0) {
        lastXP = var0;
    }

    public static void setGoodsProduced(final int var0) {
        bluePrintsProduced = var0;
    }

    public static int getGoodsProduced() {
        return bluePrintsProduced;
    }

    public static void incGoodsProduced(final int var0) {
        ++bluePrintsProduced;
    }

    public static void setStationsVisited(final int var0) {
        stationsVisited = var0;
    }

    public static void changeCredits(final int var0) {
        credits += var0;
    }

    public static void setCredits(final int var0) {
        credits = var0;
    }

    public static void checkForLevelUp() {
        final int var0 = kills + commandedWingmen / 2 + minedOre / 50 + minedCores + 2 * missionsCount;
        if (lastXP * 1.3F < var0) {
            lastXP = var0;
            ++level;
        }

    }

    public static int getRating() {
        return rating;
    }

    public static int getLastXP() {
        return lastXP;
    }

    public static int getKills() {
        return kills;
    }

    public static void incKills() {
        ++kills;
        Achievements.incKills();
    }

    public static void setPirateKills(final int var0) {
        pirateKills = var0;
    }

    public static int getPirateKills() {
        return pirateKills;
    }

    public static void incPirateKills() {
        ++pirateKills;
        Achievements.incPirateKills();
    }

    public static int getMissionCount() {
        return missionsCount;
    }

    public static int getLevel() {
        return level;
    }

    public static Standing getStanding() {
        return standing;
    }

    public static BluePrint[] getBluePrints() {
        return blueprints;
    }

    public static Agent[] getSpecialAgents() {
        return specialAgents;
    }

    public static void incPlayingTime(final long var0) {
        playTime += var0;
    }

    public static long getPlayingTime() {
        return playTime;
    }

    public static String[] getWingmenNames() {
        return wingmenNames;
    }

    public static void setWingmenNames(final String[] var0) {
        wingmenNames = var0;
    }

    /** Relpaces token '#' in template with value */
    public static String replaceToken(final String template, final String value) {
        int var2 = template.indexOf('#');
        return var2 >= 0 ? template.substring(0, var2) + value + template.substring(var2 + 1) : template;
    }
    /** Replaces tokens (String) in template with value */
    public static String replaceTokens(final String template, final String value, final String tokens) {
        int var3 = template.indexOf(tokens);
        return var3 >= 0 ? template.substring(0, var3) + value + template.substring(var3 + tokens.length()) : template;
    }

    public static void calcCargoPrices() {
        //new FileRead();
        final SolarSystem[] systems = FileRead.loadSystemsBinary();

        for(int i = 0; i < 3; ++i) {
            Item[] itemSet = i == 0 ? playersShip.getCargo() : i == 1 ? playersShip.getEquipment() : currentStation.getShopItems();
            if (itemSet != null) {
                for(int j = 0; j < itemSet.length; ++j) {
                    Item jtem = itemSet[j];
                    if (jtem != null) {
                        int lowToHigh = Galaxy.distancePercent(systems[jtem.getLowestPriceSystemId()].getPosX(), systems[jtem.getLowestPriceSystemId()].getPosY(), systems[jtem.getHighestPriceSystemId()].getPosX(), systems[jtem.getHighestPriceSystemId()].getPosY());
                        int lowToHere = Galaxy.distancePercent(systems[jtem.getLowestPriceSystemId()].getPosX(), systems[jtem.getLowestPriceSystemId()].getPosY(), systems[currentStation.getSystemIndex()].getPosX(), systems[currentStation.getSystemIndex()].getPosY());
                        lowToHere = jtem.getMinPrice() + (int)(AEMath.min(1.0F, 100.0F / lowToHigh * lowToHere / 100.0F) * (jtem.getMaxPrice() - jtem.getMinPrice()));
                        if (jtem.getSinglePrice() > 0) {
                            GlobalStatus.random.setSeed(currentStation.getIndex());
                            lowToHigh = AEMath.max(1, (int)(lowToHere * 0.05F));
                            lowToHere += -lowToHigh + GlobalStatus.random.nextInt((lowToHigh << 1) + 1);
                            jtem.setPrice(lowToHere);
                        }
                    }
                }

                GlobalStatus.random.setSeed(System.currentTimeMillis());
            }
        }

    }

    public static void startNewGame() {
        credits = 0;
        rating = 0;
        kills = 0;
        missionsCount = 0;
        bluePrintsProduced = 0;
        pirateKills = 0;
        jumpgatesUsed = 0;
        cargoSalvaged = 0;
        unused_ = 0;
        level = 1;
        lastXP = 15;
        stationsVisited = 0;
        playTime = 0L;
        wingmanFace = null;
        wingmenNames = null;
        wingmenTimeRemaining = 0;
        for(int i = 0; i < minedOreTypes.length; ++i) {
            minedOreTypes[i] = false;
        }
        for(int i = 0; i < minedCoreTypes.length; ++i) {
            minedCoreTypes[i] = false;
        }
        missionGoodsCarried = 0;
        minedOre = 0;
        minedCores = 0;
        boughtBooze = 0;
        for(int i = 0; i < drinkTypesPossesed.length; ++i) {
            drinkTypesPossesed[i] = false;
        }
        destroyedJunk = 0;
        for(int i = 0; i < systemsVisited.length; ++i) {
            systemsVisited[i] = false;
        }
        passengersCarried = 0;
        invisibleTime = 0L;
        bombsUsed = 0;
        alienJunkSalvaged = 0;
        barInteractions = 0;
        commandedWingmen = 0;
        asteroidsDestroyed = 0;
        maxFreeCargo = 0;
        missionsRejected = 0;
        askedToRepeate = 0;
        acceptedNotAskingDifficulty = 0;
        acceptedNotAskingLocation = 0;
        Achievements.init();
        highestItemPrices = new int[176];
        lowestItemPrices = new int[176];
        highestItemPriceSystems = new byte[176];
        lowestItemPriceSystems = new byte[176];
        final boolean[] visitedStations = Galaxy.getVisitedStations();
        for(int i = 0; i < visitedStations.length; ++i) {
            visitedStations[i] = false;
        }

        Galaxy.setVisitedStations(visitedStations);
        
        final Item[] items = Globals.getItems();
        int bluePrintsCnt = 0;
        for(int i = 0; i < items.length; ++i) {
            if (items[i].getBluePrintComponentsIds() != null) {
                ++bluePrintsCnt;
            }
        }

        if (bluePrintsCnt > 0) {
            blueprints = new BluePrint[bluePrintsCnt];
            int j = 0;
            for(int i = 0; i < items.length; ++i) {
                if (items[i].getBluePrintComponentsIds() != null) {
                    blueprints[j] = new BluePrint(i);
                    j++;
                }
            }
        }

        waitingGoods = null;
        //new FileRead();
        specialAgents = FileRead.loadAgents();
        standing = new Standing();
        wormholeStation = -1;
        wormholeSystem = -1;
        lastVisitedNonVoidOrbit = 0;
        GlobalStatus.resetHints();
        currentMissions[1] = Mission.emptyMission_;
        currentCampaignMissionIndex = 0;
        setCampaignMission(new Mission(Mission.TYPE_4, 0, 78));
        mission = currentMissions[0];
        playersShip = Globals.getShips()[10].cloneBase();
        playersShip.setRace(0);
        playersShip.setSellingPrice();
        setStation(Galaxy.getStation(78));
        playersShip.setCargo((Item[])null);
        playersShip.setEquipment(Globals.getItems()[3].makeItem(), 0);
        playersShip.setEquipment(Globals.getItems()[3].makeItem(), 1);
        playersShip.setEquipment(Globals.getItems()[54].makeItem(), 0);
        playersShip.setEquipment(Globals.getItems()[59].makeItem(), 0);
        playersShip.setEquipment(Globals.getItems()[82].makeItem(), 0);
        baseArmour = playersShip.getBaseArmour();
        shield = playersShip.getShield();
        additionalArmour = playersShip.getAdditionalArmour();
    }
}
