package AbyssEngine;

import java.util.Vector;

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
   private static int var_510;
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
   public static ProducedGood[] waitingGoods;
   public static Agent[] SpecialAgents;
   public static String[] wingmenNames;
   public static byte[] var_8f8wingmenVar2;
   public static int wingmenRace_;
   public static int wingmenTimeRemaining;
   public static int var_9a8;
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
   public static int var_cae;
   public static long timeSpentLoading;
   public static long loadingsCount;
   public static boolean[] minedOreTypes = new boolean[11];
   public static boolean[] minedCoreTypes = new boolean[11];
   public static int var_d88;
   public static int minedOre;
   public static int minedCores;
   public static int var_e59;
   public static boolean[] drinksVar1 = new boolean[22];
   public static int var_ea8;
   public static boolean[] drinksVar2 = new boolean[22];
   public static int var_f13;
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

   public static void sub_5c(BluePrint var0) {
      int var1 = 0;
      if (waitingGoods == null) {
         waitingGoods = new ProducedGood[1];
      } else {
         int var2;
         for(var2 = 0; var2 < waitingGoods.length; ++var2) {
            if (waitingGoods[var2] != null && waitingGoods[var2].index == var0.getIndex() && waitingGoods[var2].stationId == var0.productionStationId) {
               ProducedGood var10000 = waitingGoods[var2];
               var10000.producedQuantity += var0.getTonsPerProduction2();
               return;
            }
         }

         for(var2 = 0; var2 < waitingGoods.length; ++var2) {
            if (waitingGoods[var2] == null) {
               waitingGoods[var2] = new ProducedGood(var0);
               return;
            }
         }

         ProducedGood[] var4 = new ProducedGood[waitingGoods.length + 1];

         for(int var3 = 0; var3 < waitingGoods.length; ++var3) {
            var4[var1++] = waitingGoods[var3];
         }

         waitingGoods = null;
         waitingGoods = var4;
      }

      waitingGoods[var1] = new ProducedGood(var0);
   }

   public static ProducedGood[] getWaitingGoods() {
      return waitingGoods;
   }

   public static boolean[] getVisibleSystems() {
      return visibleSystems;
   }

   public static void sub_e9(int var0, boolean var1) {
      visibleSystems[var0] = true;
   }

   public static Station[] getLastVisitedStations() {
      return lastVisitedStations;
   }

   public static void setLastVisitedStations(Station[] var0) {
      lastVisitedStations = var0;
   }

   private static Station inLastVisitedStations(Station var0) {
      for(int var1 = 0; var1 < 3; ++var1) {
         if (lastVisitedStations[var1] != null && lastVisitedStations[var1].equals(var0)) {
            return lastVisitedStations[var1];
         }
      }

      return null;
   }

   public static void sub_1e5(Station var0) {
      if (var0 != voidStation) {
         Station var1;
         label240: {
            var1 = inLastVisitedStations(var0);
            Station var4;
            boolean var10000;
            if ((var4 = inLastVisitedStations(var0)) != null) {
               setCurrentStation_andInitSystem_(var4);
            } else {
               int var5;
               if (lastVisitedStations[0] != null) {
                  for(var5 = 2; var5 > 0; --var5) {
                     lastVisitedStations[var5] = lastVisitedStations[var5 - 1];
                  }

                  lastVisitedStations[0] = var0;
                  setCurrentStation_andInitSystem_(var0);
                  var10000 = true;
                  break label240;
               }

               for(var5 = 2; var5 >= 0; --var5) {
                  if (lastVisitedStations[var5] == null) {
                     lastVisitedStations[var5] = var0;
                     setCurrentStation_andInitSystem_(var0);
                     var10000 = true;
                     break label240;
                  }
               }
            }

            var10000 = false;
         }

         if (var1 == null) {
            Generator var2 = new Generator();
            Station var3 = var0;
            Item[] var10001;
            if (var0.getId() == 78 && currentCampaignMissionIndex < 7) {
               Item[] var19;
               (var19 = new Item[3])[0] = IndexManager.getItems()[0].getCopyInAmmount(1, 0);
               var19[1] = IndexManager.getItems()[22].getCopyInAmmount(1, 0);
               var19[2] = IndexManager.getItems()[55].getCopyInAmmount(1, 0);
               var10001 = var19;
            } else {
               Vector var18 = new Vector();
               Item[] var20 = IndexManager.getItems();
               new FileRead();
               SolarSystem[] var14 = FileRead.loadSystemsBinary();
               int var6;
               int var7 = (var6 = var0.getTecLevel()) < 4 ? 1 : var6 / 2;
               if (currentCampaignMissionIndex > 16 && currentCampaignMissionIndex < 25 && GameStatus.random.nextInt(100) < 40) {
                  Item var8 = var20[68].singleCopy();
                  var18.addElement(var8);
               }

               GameStatus.random.setSeed(System.currentTimeMillis());

               int var21;
               int var23;
               for(var21 = 0; var21 < var20.length; ++var21) {
                  Item var9 = var20[var21];
                  boolean var10 = false;
                  if (var3.getId() == var9.getAttribute(36)) {
                     var10 = true;
                  }

                  int var11 = var9.getTecLevel();
                  boolean var12 = var9.getIndex() >= 132 && var9.getIndex() < 154;
                  if (var10 || var9.getBluePrintComponentsIds() == null && var21 != 175 && var21 != 164 && var11 <= var3.getTecLevel() && var9.getOccurance() != 0 && var9.getPrice() != 0 && (var9.getAttribute(35) != 1 || var14[var3.getSystemIndex()].getRace() == 1) && (!var12 || var9.getIndex() == 132 + var3.getSystemIndex())) {
                     int var13 = var9.getOccurance();
                     if (var10 || (var12 || var11 <= var6 && var11 >= var7) && GameStatus.random.nextInt(100) < var13) {
                        var23 = var14[var9.getLowestPriceSystemId()].getPosX();
                        var11 = var14[var9.getLowestPriceSystemId()].getPosY();
                        var23 = Galaxy.invDistancePercent(var14[var3.getSystemIndex()].getPosX(), var14[var3.getSystemIndex()].getPosY(), var23, var11);
                        var11 = 5 + GameStatus.random.nextInt(15);
                        if (var9.getType() != 4 && var9.getType() != 1) {
                           var11 = AEMath.max(1, var11 / 5);
                        } else if (var9.getType() == 4 && var23 > 50) {
                           var11 *= AEMath.max(1, (int)((float)(var23 - 50) / 50.0F * 20.0F));
                        }

                        var18.addElement(var9.anyAmountCopies(var11));
                     }
                  }
               }

               Item[] var22 = new Item[var21 = var18.size()];

               for(var23 = 0; var23 < var21; ++var23) {
                  var22[var23] = (Item)var18.elementAt(var23);
               }

               var10001 = var22;
            }

            var0.setShopItems(var10001);
            var0.setShopShips(Generator.getShipBuyList(var0));
            var0.setBarAgents(var2.createAgents(var0));
         }

         lastLoadingTime = playTime;
      }

      mission = Mission.var_dc;

      for(int var15 = 0; var15 < currentMissions.length; ++var15) {
         if (currentMissions[var15] != null) {
            if (currentMissions[var15].isCampaignMission() && currentMissions[var15].getType() == 25 && var0.getId() == wormholeStation) {
               mission = currentMissions[var15];
               break;
            }

            if (currentMissions[var15].setCampaignMission() == var0.getId() && currentMissions[var15].getType() != 8 && currentMissions[var15].getType() != 19 && currentMissions[var15].getType() != 16 && currentMissions[var15].getType() != 14 && currentMissions[var15].getType() != 13) {
               if (currentMissions[var15].isCampaignMission() || currentMissions[var15].getType() != 11) {
                  mission = currentMissions[var15];
               }
               break;
            }
         }
      }

      if (!gameWon() && currentCampaignMissionIndex >= 32 && var0.getId() != currentMissions[0].setCampaignMission()) {
         if (++var_cae > 10) {
            var_cae = 0;
            boolean var16 = false;

            while(true) {
               do {
                  do {
                     do {
                        do {
                           if (var16) {
                              return;
                           }

                           wormholeSystem = GameStatus.random.nextInt(22);
                        } while(!visibleSystems[wormholeSystem]);
                     } while(wormholeSystem == 10);
                  } while(wormholeSystem == 15);
               } while(mission.isCampaignMission() && mission.setCampaignMission() == wormholeStation);

               var16 = true;
               SolarSystem var17;
               wormholeStation = (var17 = Galaxy.loadSystem_(wormholeSystem)).getStations()[GameStatus.random.nextInt(var17.getStations().length)];
            }
         }
      } else if (gameWon()) {
         wormholeStation = -10;
         wormholeSystem = -10;
      }

   }

   public static void sub_236(int var0) {
      var_9a8 = var0;
   }

   public static Mission getMission() {
      return mission;
   }

   public static void setMission(Mission var0) {
      mission = var0;
   }

   public static Mission getFreelanceMission() {
      return currentMissions[1];
   }

   public static void setFreelanceMission(Mission var0) {
      currentMissions[1] = var0;
   }

   public static Mission getCampaignMission() {
      return currentMissions[0];
   }

   public static void startStoryMission(Mission var0) {
      var0.setStartedStoryMission(true);
      currentMissions[0] = var0;
   }

   public static void incMissionCount() {
      ++missionsCount;
   }

   public static void getCurrentCampaignMissionIndex(int var0) {
      currentCampaignMissionIndex = var0;
   }

   public static int getCurrentCampaignMission() {
      return currentCampaignMissionIndex;
   }

   public static void nextCampaignMission() {
      boolean var0;
      int var1;
      Item[] var2;
      switch(++currentCampaignMissionIndex) {
      case 1:
         startStoryMission(new Mission(11, 0, 78));
         return;
      case 2:
         startStoryMission(new Mission(18, 0, 78));
         currentMissions[0].setTasksTreshold_(10);
         return;
      case 3:
         startStoryMission(new Mission(11, 0, 78));
         return;
      case 4:
         playersShip.refreshCargoSpace((Item[])null);
         startStoryMission(new Mission(18, 0, 78));
         currentMissions[0].setTasksTreshold_(25);
         return;
      case 5:
         startStoryMission(new Mission(11, 0, 78));
         return;
      case 6:
         playersShip.sub_79a();
         startStoryMission(new Mission(22, 0, 78));
         return;
      case 7:
         startStoryMission(new Mission(4, 0, 78));
         return;
      case 8:
         if ((var2 = playersShip.getEquipment()) != null) {
            for(var1 = 0; var1 < var2.length; ++var1) {
               if (var2[var1] != null) {
                  var2[var1].sub_986(false);
                  var2[var1].setPrice(IndexManager.getItems()[var1].getPrice());
               }
            }
         }

         Item[] var6;
         if ((var6 = playersShip.getCargo()) != null) {
            for(int var5 = 0; var5 < var6.length; ++var5) {
               if (var6[var5] != null) {
                  var6[var5].sub_986(false);
                  var6[var5].setPrice(IndexManager.getItems()[var5].getPrice());
               }
            }
         }

         startStoryMission(new Mission(11, 0, 78));
         return;
      case 9:
         startStoryMission(new Mission(11, 0, 78));
         return;
      case 10:
         Item var4 = playersShip.getEquipedOfSubType(19);
         playersShip.sub_908(var4);
         startStoryMission(new Mission(11, 0, 79));
         return;
      case 11:
         startStoryMission(new Mission(11, 0, 76));
         return;
      case 12:
         startStoryMission(new Mission(11, 0, 79));
         return;
      case 13:
         startStoryMission(new Mission(13, 0, 0));
         currentMissions[0].setTasksTreshold_(missionsCount + 2);
         currentMissions[0].sub_91(false);
         return;
      case 14:
         startStoryMission(new Mission(4, 0, 79));
         return;
      case 15:
         startStoryMission(new Mission(11, 0, 98));
         return;
      case 16:
         startStoryMission(new Mission(4, 0, 98));
         return;
      case 17:
         startStoryMission(new Mission(11, 0, 98));
         return;
      case 18:
         playersShip.setRace(0);
         startStoryMission(new Mission(20, 0, 56));
         return;
      case 19:
         startStoryMission(new Mission(20, 0, 55));
         return;
      case 20:
         startStoryMission(new Mission(23, 0, 55));
         currentMissions[0].setTasksTreshold_(6);
         return;
      case 21:
         startStoryMission(new Mission(4, 0, 55));
         return;
      case 22:
         startStoryMission(new Mission(11, 0, 55));
         return;
      case 23:
         var0 = true;
         byte var3 = 6;
         visibleSystems[var3] = true;
         startStoryMission(new Mission(11, 20000, 10));
         return;
      case 24:
         wormholeStation = 48;
         wormholeSystem = 9;
         startStoryMission(new Mission(4, 0, 48));
         return;
      case 25:
         if ((var2 = playersShip.getCargo()) != null) {
            for(var1 = 0; var1 < var2.length; ++var1) {
               if (var2[var1].getIndex() == 131) {
                  var2[var1].sub_986(true);
                  break;
               }
            }
         }

         startStoryMission(new Mission(20, 0, -1));
         return;
      case 26:
         wormholeStation = -1;
         wormholeSystem = -1;
         startStoryMission(new Mission(4, 0, 48));
         return;
      case 27:
         startStoryMission(new Mission(11, 0, 10));
         return;
      case 28:
         wormholeStation = 91;
         wormholeSystem = 18;
         startStoryMission(new Mission(4, 0, 91));
         return;
      case 29:
         startStoryMission(new Mission(4, 0, -1));
         return;
      case 30:
         startStoryMission(new Mission(20, 0, 91));
         return;
      case 31:
         startStoryMission(new Mission(11, 30000, 98));
         return;
      case 32:
         startStoryMission(new Mission(11, 0, 10));
         return;
      case 33:
         startStoryMission(new Mission(8, 0, 10));
         currentMissions[0].setCommodity(164, 50);
         return;
      case 34:
         playersShip.sub_7fc(164, 50);

         for(var1 = 0; var1 < blueprints.length; ++var1) {
            if (blueprints[var1].getIndex() == 85) {
               blueprints[var1].unlocked = true;
               blueprints[var1].startProduction(IndexManager.getItems()[164].singleCopy(), 50, -1);
               BluePrint var10000 = blueprints[var1];
               var0 = false;
               var10000.investedCredits = 0;
            }
         }

         startStoryMission(new Mission(11, 0, 30));
         return;
      case 35:
         startStoryMission(new Mission(11, 0, 29));
         return;
      case 36:
         startStoryMission(new Mission(12, 0, 27));
         return;
      case 37:
         startStoryMission(new Mission(24, 0, 27));
         currentMissions[0].sub_91(false);
         return;
      case 38:
         startStoryMission(new Mission(4, 0, 22));
         return;
      case 39:
         startStoryMission(new Mission(11, 0, 30));
         return;
      case 40:
         startStoryMission(new Mission(25, 0, -1));
         return;
      case 41:
         startStoryMission(new Mission(4, 0, -1));
         return;
      case 42:
         wormholeStation = -10;
         wormholeSystem = -10;
         startStoryMission(new Mission(24, 0, -1));
         return;
      case 43:
         startStoryMission(new Mission(11, 0, 98));
         return;
      case 44:
         startStoryMission(new Mission(11, 0, 98));
         return;
      case 45:
         wormholeStation = -10;
         wormholeSystem = -10;
         startStoryMission(Mission.var_dc);
         currentMissions[0].sub_91(false);
         changeCredits(40000);
      default:
      }
   }

   public static boolean gameWon() {
      return currentCampaignMissionIndex > 44;
   }

   public static boolean inEmptyOrbit() {
      return currentCampaignMissionIndex < 2 && currentStation.getId() == 78 || inAlienOrbit() && currentCampaignMissionIndex > 42;
   }

   public static boolean inAlienOrbit() {
      return currentStation.equals(voidStation);
   }

   public static Mission missionCompleted_(boolean var0, long var1) {
      label140:
      for(int var3 = 0; var3 < currentMissions.length; ++var3) {
         Mission var4;
         if ((var4 = currentMissions[var3]).hasWon()) {
            return null;
         }

         if (var4 != null) {
            Item[] var5;
            int var6;
            switch(var4.getType()) {
            case 0:
            case 11:
               if (var0 && currentStation.getId() == var4.setCampaignMission()) {
                  return var4;
               }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 17:
            default:
               break;
            case 8:
               if (var0 && currentStation.getId() == var4.setCampaignMission() && Item.sub_737(var4.getCommodityIndex(), var4.getCommodityAmount(), playersShip.getCargo())) {
                  return var4;
               }
               break;
            case 13:
               if (missionsCount >= var4.getStatusValue_()) {
                  var4.setWon(true);
                  return var4;
               }
               break;
            case 14:
               if (kills >= var4.getStatusValue_()) {
                  var4.setWon(true);
                  return var4;
               }
               break;
            case 15:
               var5 = playersShip.getEquipment();
               var6 = 0;

               while(true) {
                  if (var6 >= var5.length) {
                     continue label140;
                  }

                  if (var5[var6] != null && var5[var6].getIndex() == var4.getStatusValue_()) {
                     return var4;
                  }

                  ++var6;
               }
            case 16:
               if (stationsVisited >= var4.getStatusValue_()) {
                  var4.setWon(true);
                  return var4;
               }
               break;
            case 18:
               if (playersShip.getCurrentLoad() >= var4.getStatusValue_()) {
                  var4.setWon(true);
                  return var4;
               }
               break;
            case 19:
               if (bluePrintsProduced >= var4.getStatusValue_()) {
                  var4.setWon(true);
                  return var4;
               }
               break;
            case 20:
               if (!var0 && currentStation.getId() == var4.setCampaignMission() && var1 > 10000L) {
                  return var4;
               }
               break;
            case 21:
               var5 = playersShip.getEquipment();
               var6 = 0;

               while(true) {
                  if (var6 >= var5.length) {
                     continue label140;
                  }

                  if (var5[var6] != null && var5[var6].getType() == var4.getStatusValue_()) {
                     return var4;
                  }

                  ++var6;
               }
            case 22:
               var5 = playersShip.getEquipment();
               boolean var9 = false;
               boolean var7 = false;

               for(int var8 = 0; var8 < var5.length; ++var8) {
                  if (var5[var8] != null && var5[var8].getType() == 0) {
                     var9 = true;
                  } else if (var5[var8] != null && var5[var8].getSubType() == 10) {
                     var7 = true;
                  }
               }

               if (var9 && var7) {
                  return var4;
               }
               break;
            case 23:
               if (var0) {
                  var5 = playersShip.getEquipment();

                  for(var6 = 0; var6 < var5.length; ++var6) {
                     if (var5[var6] != null && var5[var6].getSubType() == var4.getStatusValue_()) {
                        return var4;
                     }
                  }
               }
               break;
            case 24:
               if (var0 || !var0 && currentStation.getId() != var4.setCampaignMission() && var1 > 10000L) {
                  return var4;
               }
            }
         }
      }

      return null;
   }

   public static void setJumpgateUsed(int var0) {
      jumpgatesUsed = var0;
   }

   public static void jumpgateUsed() {
      ++jumpgatesUsed;
   }

   public static int getJumpgateUsed() {
      return jumpgatesUsed;
   }

   public static void incCargoSalvaged(int var0) {
      cargoSalvaged += var0;
   }

   public static void setCargoSalvaged(int var0) {
      cargoSalvaged = var0;
   }

   public static int getCargoSalvaged() {
      return cargoSalvaged;
   }

   public static void sub_690(int var0) {
      var_510 = var0;
   }

   public static int sub_6ac() {
      return var_510;
   }

   public static void sub_70a(Mission var0) {
      for(int var1 = 0; var1 < currentMissions.length; ++var1) {
         if (currentMissions[var1] == var0) {
            currentMissions[var1] = Mission.var_dc;
         }
      }

      if (mission == var0) {
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

   public static void setShip(Ship var0) {
      playersShip = var0;
   }

   public static Station getStation() {
      return currentStation;
   }

   public static void setCurrentStation_andInitSystem_(Station var0) {
      currentStation = var0;
      if ((currentSolarSystem = Galaxy.loadSystem_(var0.getSystemIndex())) != null) {
         drinksVar2[currentStation.getSystemIndex()] = true;
         new FileRead();
         Station[] var3 = FileRead.loadStationsBinary(currentSolarSystem);
         currSysPlanetNames = null;
         currSysPlanetNames = new String[var3.length];
         currSysPlanetTextures = new int[var3.length];

         for(int var1 = 0; var1 < currentSolarSystem.getStations().length; ++var1) {
            for(int var2 = 0; var2 < var3.length; ++var2) {
               if (currentSolarSystem.getStations()[var1] == var3[var2].getId()) {
                  if (currentCampaignMissionIndex == 0) {
                     currSysPlanetNames[var1] = "";
                  } else {
                     currSysPlanetNames[var1] = var3[var2].getName();
                  }

                  currSysPlanetTextures[var1] = var3[var2].getPlanetTextureId();
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

   public static void setRating(int var0) {
      rating = var0;
   }

   public static void setPlayingTime(long var0) {
      playTime = var0;
   }

   public static void setKills(int var0) {
      kills = var0;
   }

   public static void setMissionCount(int var0) {
      missionsCount = var0;
   }

   public static void setLevel(int var0) {
      level = var0;
   }

   public static void setLastXP(int var0) {
      lastXP = var0;
   }

   public static void setGoodsProduced(int var0) {
      bluePrintsProduced = var0;
   }

   public static int getGoodsProduced() {
      return bluePrintsProduced;
   }

   public static void incGoodsProduced(int var0) {
      ++bluePrintsProduced;
   }

   public static void setStationsVisited(int var0) {
      stationsVisited = var0;
   }

   public static void changeCredits(int var0) {
      credits += var0;
   }

   public static void setCredits(int var0) {
      credits = var0;
   }

   public static void checkForLevelUp() {
      int var0 = kills + commandedWingmen / 2 + minedOre / 50 + minedCores + 2 * missionsCount;
      if ((float)lastXP * 1.3F < (float)var0) {
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
      Medals.incKills();
   }

   public static void sub_ca6(int var0) {
      pirateKills = var0;
   }

   public static int sub_cc1() {
      return pirateKills;
   }

   public static void incPirateKills() {
      ++pirateKills;
      Medals.incPirateKills();
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
      return SpecialAgents;
   }

   public static void incPlayingTime(long var0) {
      playTime += var0;
   }

   public static long getPlayingTime() {
      return playTime;
   }

   public static String[] getWingmenNames() {
      return wingmenNames;
   }

   public static void setWingmenNames(String[] var0) {
      wingmenNames = var0;
   }

   public static String replaceToken(String var0, String var1) {
      int var2;
      return (var2 = var0.indexOf(35)) >= 0 ? var0.substring(0, var2) + var1 + var0.substring(var2 + 1) : var0;
   }

   public static String replaceTokens(String var0, String var1, String var2) {
      int var3;
      return (var3 = var0.indexOf(var2)) >= 0 ? var0.substring(0, var3) + var1 + var0.substring(var3 + var2.length()) : var0;
   }

   public static void calcCargoPrices() {
      new FileRead();
      SolarSystem[] var0 = FileRead.loadSystemsBinary();

      for(int var1 = 0; var1 < 3; ++var1) {
         Item[] var2;
         if ((var2 = var1 == 0 ? playersShip.getCargo() : (var1 == 1 ? playersShip.getEquipment() : currentStation.getShopItems())) != null) {
            for(int var5 = 0; var5 < var2.length; ++var5) {
               Item var6;
               if ((var6 = var2[var5]) != null) {
                  int var4 = Galaxy.distancePercent(var0[var6.getLowestPriceSystemId()].getPosX(), var0[var6.getLowestPriceSystemId()].getPosY(), var0[var6.getHighestPriceSystemId()].getPosX(), var0[var6.getHighestPriceSystemId()].getPosY());
                  int var3 = Galaxy.distancePercent(var0[var2[var5].getLowestPriceSystemId()].getPosX(), var0[var2[var5].getLowestPriceSystemId()].getPosY(), var0[currentStation.getSystemIndex()].getPosX(), var0[currentStation.getSystemIndex()].getPosY());
                  var3 = var6.getMinPrice() + (int)(AEMath.min(1.0F, 100.0F / (float)var4 * (float)var3 / 100.0F) * (float)(var6.getMaxPrice() - var6.getMinPrice()));
                  if (var6.getPrice() > 0) {
                     GameStatus.random.setSeed((long)currentStation.getId());
                     var4 = AEMath.max(1, (int)((float)var3 * 0.05F));
                     var3 += -var4 + GameStatus.random.nextInt((var4 << 1) + 1);
                     var6.setPrice(var3);
                  }
               }
            }

            GameStatus.random.setSeed(System.currentTimeMillis());
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
      var_510 = 0;
      level = 1;
      lastXP = 15;
      stationsVisited = 0;
      playTime = 0L;
      var_8f8wingmenVar2 = null;
      wingmenNames = null;
      wingmenTimeRemaining = 0;

      int var0;
      for(var0 = 0; var0 < minedOreTypes.length; ++var0) {
         minedOreTypes[var0] = false;
      }

      for(var0 = 0; var0 < minedCoreTypes.length; ++var0) {
         minedCoreTypes[var0] = false;
      }

      var_d88 = 0;
      minedOre = 0;
      minedCores = 0;
      var_e59 = 0;

      for(var0 = 0; var0 < drinksVar1.length; ++var0) {
         drinksVar1[var0] = false;
      }

      var_ea8 = 0;

      for(var0 = 0; var0 < drinksVar2.length; ++var0) {
         drinksVar2[var0] = false;
      }

      var_f13 = 0;
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
      Medals.init();
      highestItemPrices = new int[176];
      lowestItemPrices = new int[176];
      highestItemPriceSystems = new byte[176];
      lowestItemPriceSystems = new byte[176];
      boolean[] var3 = Galaxy.getVisitedStations();

      int var1;
      for(var1 = 0; var1 < var3.length; ++var1) {
         var3[var1] = false;
      }

      Galaxy.setVisitedStations(var3);
      var1 = 0;
      Item[] var4 = IndexManager.getItems();

      int var2;
      for(var2 = 0; var2 < var4.length; ++var2) {
         if (var4[var2].getBluePrintComponentsIds() != null) {
            ++var1;
         }
      }

      if (var1 > 0) {
         blueprints = new BluePrint[var1];
         var2 = 0;

         for(var1 = 0; var1 < var4.length; ++var1) {
            if (var4[var1].getBluePrintComponentsIds() != null) {
               blueprints[var2++] = new BluePrint(var1);
            }
         }
      }

      waitingGoods = null;
      new FileRead();
      SpecialAgents = FileRead.loadAgents();
      standing = new Standing();
      wormholeStation = -1;
      wormholeSystem = -1;
      lastVisitedNonVoidOrbit = 0;
      GameStatus.resetHelp();
      Mission var6 = Mission.var_dc;
      currentMissions[1] = var6;
      boolean var8 = false;
      currentCampaignMissionIndex = 0;
      startStoryMission(new Mission(4, 0, 78));
      mission = currentMissions[0];
      (playersShip = IndexManager.getShips()[10].cloneBase()).setRace(0);
      playersShip.setSellingPrice();
      setCurrentStation_andInitSystem_(Galaxy.getStation(78));
      playersShip.refreshCargoSpace((Item[])null);
      Item var7 = IndexManager.getItems()[3].singleCopy();
      playersShip.setEquipment(var7, 0);
      Item var5 = IndexManager.getItems()[3].singleCopy();
      playersShip.setEquipment(var5, 1);
      Item var9 = IndexManager.getItems()[54].singleCopy();
      playersShip.setEquipment(var9, 0);
      var9 = IndexManager.getItems()[59].singleCopy();
      playersShip.setEquipment(var9, 0);
      var9 = IndexManager.getItems()[82].singleCopy();
      playersShip.setEquipment(var9, 0);
      baseArmour = playersShip.getBaseArmour();
      shield = playersShip.getShield();
      additionalArmour = playersShip.getAdditionalArmour();
   }
}
