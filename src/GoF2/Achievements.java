package GoF2;

public final class Achievements {
   public static final int[][] VALUES;
   private static int[] medals;
   private static int[] newMedals;
   private static int killStreak;
   private static int pirateKillStreak;
   private static int weaponsEquipped;
   private static boolean isArmed;
   private static int maxCredits;
   private static boolean ALL_MEDALS;
   private static boolean ALL_GOLD_MEDALS;
   private static int goldMedalsCount;
   private static int unlockedMedalsCount;

   public static void checkForNewMedal(PlayerEgo var0) {
      int var1 = 0;
      int var2 = 0;
      int var4;
      if (Status.getCurrentCampaignMission() > 7) {
         Item[] var3;
         if ((var3 = Status.getShip().getEquipment()) != null) {
            for(var4 = 0; var4 < var3.length; ++var4) {
               if (var3[var4] != null && var3[var4].getType() != 4) {
                  if (var3[var4].getType() == 0) {
                     ++weaponsEquipped;
                  } else if (var3[var4].getType() == 3) {
                     ++var1;
                     continue;
                  }

                  ++var2;
               }
            }
         }

         isArmed = var2 > 0 && var1 > 0;
      } else {
         isArmed = true;
      }

      boolean var7 = false;

      for(var2 = 0; var2 < VALUES.length; ++var2) {
         int var8 = 0;
         if (medals[var2] != 1) {
            for(var4 = 0; var4 < VALUES[var2].length && var8 <= 0; ++var4) {
               var7 = false;
               int var6;
               switch(var2) {
               case 1:
                  var7 = var0.getHullDamageRate() <= VALUES[var2][var4];
                  break;
               case 2:
               case 3:
               case 9:
               case 12:
                  boolean[] var5 = var2 == 2 ? Status.minedOreTypes : (var2 == 3 ? Status.minedCoreTypes : (var2 == 9 ? Status.drinkTypesPossesed : Status.systemsVisited));
                  var1 = 0;
                  var6 = 0;

                  for(; var6 < var5.length; ++var6) {
                     if (var5[var6]) {
                        ++var1;
                     }
                  }

                  var7 = var1 >= VALUES[var2][var4];
                  break;
               case 4:
                  var7 = Status.getKills() >= VALUES[var2][var4];
                  break;
               case 5:
                  var7 = Status.missionGoodsCarried > VALUES[var2][var4];
                  break;
               case 6:
                  var7 = Status.minedOre > VALUES[var2][var4];
                  break;
               case 7:
                  var7 = Status.minedCores > VALUES[var2][var4];
                  break;
               case 8:
                  var7 = Status.boughtBooze > VALUES[var2][var4];
                  break;
               case 10:
                  var7 = Status.destroyedJunk > VALUES[var2][var4];
                  break;
               case 11:
                  var7 = Status.getStationsVisited() >= VALUES[var2][var4];
                  break;
               case 13:
                  var1 = 0;

                  for(var6 = 0; var6 < Status.blueprints.length; ++var6) {
                     if (Status.blueprints[var6].unlocked) {
                        ++var1;
                     }
                  }

                  var7 = var1 >= VALUES[var2][var4];
                  break;
               case 14:
                  var1 = 0;

                  for(var6 = 0; var6 < Status.blueprints.length; ++var6) {
                     if (Status.blueprints[var6].timesProduced > 0) {
                        ++var1;
                     }
                  }

                  var7 = var1 >= VALUES[var2][var4];
                  break;
               case 15:
                  var7 = Status.getPlayingTime() > (long)(VALUES[var2][var4] * 3600000);
                  break;
               case 16:
                  var7 = Status.getMissionCount() > VALUES[var2][var4];
                  break;
               case 17:
                  var7 = Status.getJumpgateUsed() >= VALUES[var2][var4];
                  break;
               case 18:
                  var7 = Status.passengersCarried > VALUES[var2][var4];
                  break;
               case 19:
                  var7 = Status.invisibleTime / 60000L >= (long)VALUES[var2][var4];
                  break;
               case 20:
                  var7 = Status.bombsUsed > VALUES[var2][var4];
                  break;
               case 21:
                  var7 = Status.alienJunkSalvaged > VALUES[var2][var4];
                  break;
               case 22:
                  var7 = !isArmed;
                  break;
               case 23:
                  var7 = weaponsEquipped >= VALUES[var2][var4];
                  break;
               case 24:
                  var7 = Status.getCargoSalvaged() >= VALUES[var2][var4];
                  break;
               case 25:
                  var7 = maxCredits >= VALUES[var2][var4];
                  break;
               case 26:
                  var7 = Status.barInteractions > VALUES[var2][var4];
                  break;
               case 27:
                  var7 = Status.commandedWingmen > VALUES[var2][var4];
                  break;
               case 28:
                  var7 = Status.getStanding().atWar();
                  break;
               case 29:
                  var7 = Status.asteroidsDestroyed > VALUES[var2][var4];
                  break;
               case 30:
                  var7 = Status.gameWon();
                  break;
               case 31:
                  var7 = Status.maxFreeCargo > VALUES[var2][var4];
                  break;
               case 32:
                  var7 = Status.missionsRejected > VALUES[var2][var4];
                  break;
               case 33:
                  var7 = Status.askedToRepeate > VALUES[var2][var4];
                  break;
               case 34:
                  var7 = Status.acceptedNotAskingDifficulty > VALUES[var2][var4];
                  break;
               case 35:
                  var7 = Status.acceptedNotAskingLocation > VALUES[var2][var4];
                  break;
               case 36:
                  var7 = true;

                  for(var6 = 0; var6 < medals.length - 1; ++var6) {
                     if (medals[var6] == 0) {
                        var7 = false;
                        break;
                     }
                  }
               }

               if (var7) {
                  var8 = var4 + 1;
               }
            }
         }

         if (medals[var2] > var8 || medals[var2] == 0) {
            newMedals[var2] = var8;
         }
      }

   }

   private static void countMedals() {
      unlockedMedalsCount = 0;
      goldMedalsCount = 0;

      for(int var0 = 0; var0 < medals.length; ++var0) {
         if (medals[var0] == 1) {
            ++unlockedMedalsCount;
            ++goldMedalsCount;
         } else if (medals[var0] != 0) {
            ++unlockedMedalsCount;
         }
      }

      ALL_MEDALS = unlockedMedalsCount == medals.length;
      ALL_GOLD_MEDALS = goldMedalsCount == medals.length;
   }

   public static boolean gotAllMedals() {
      return ALL_MEDALS;
   }

   public static boolean gotAllGoldMedals() {
      return ALL_GOLD_MEDALS;
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

   public static void updateMaxCredits(int var0) {
      if (var0 > maxCredits) {
         maxCredits = var0;
      }

   }

   public static void setMedals(int[] var0) {
      medals = var0;
   }

   public static void init() {
      for(int var0 = 0; var0 < medals.length; ++var0) {
         medals[var0] = 0;
      }

      medals[0] = 1;
      resetNewMedals();
      maxCredits = 0;
   }

   public static void applyNewMedals() {
      for(int var0 = 0; var0 < medals.length; ++var0) {
         if (newMedals[var0] > 0 && (newMedals[var0] < medals[var0] || medals[var0] == 0)) {
            medals[var0] = newMedals[var0];
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
      for(int var0 = 0; var0 < newMedals.length; ++var0) {
         newMedals[var0] = 0;
      }

      killStreak = 0;
      pirateKillStreak = 0;
      weaponsEquipped = 0;
      isArmed = false;
   }

   static {
      medals = new int[(VALUES = new int[][]{{0}, {5, 15, 30}, {11, 8, 5}, {11, 8, 5}, {250, 100, 50}, {200, 100, 25}, {1000, 500, 100}, {25, 10, 3}, {1000, 100, 25}, {22, 16, 5}, {150, 100, 30}, {100, 50, 25}, {22, 10, 5}, {13, 6, 3}, {13, 6, 3}, {20, 10, 5}, {50, 25, 5}, {100, 50, 10}, {50, 20, 5}, {5, 3, 2}, {50, 20, 5}, {25, 10, 5}, {0}, {4, 3, 2}, {500, 200, 50}, {1000000, 500000, 125000}, {100, 50, 20}, {20, 10, 3}, {1}, {500, 250, 50}, {0}, {500, 250, 100}, {50}, {5}, {10}, {12}, {0}}).length];
      newMedals = new int[VALUES.length];
   }
}
