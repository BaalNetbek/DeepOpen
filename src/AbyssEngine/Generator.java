package AbyssEngine;

public final class Generator {
   private final int[] storySignificantStations_ = new int[]{10, 22, 27, 29, 30, 48, 55, 56, 76, 79, 91, 98};
   private final int[] itemTypeOccurance = new int[]{5, 20, 2, 5, 100};

   public static Ship[] getShipBuyList(Station var0) {
      if (var0.getSystemIndex() == 15 && Status.getCurrentCampaignMission() < 16) {
         return null;
      } else {
         Ship[] var1 = null;
         int var2 = Status.getSystem().getRace();
         boolean var7 = var0.getId() == 10 && Medals.gotAllGoldMedals();
         int var10000 = var2 != 1 && !var7 ? GameStatus.random.nextInt(6) : 1;
         int var8 = var10000;
         if (var10000 == 0) {
            return null;
         } else {
            var1 = new Ship[var8];

            for(int var3 = 0; var3 < var1.length; ++var3) {
               int var4 = 0;
               boolean var5 = false;

               while(true) {
                  while(!var5) {
                     var4 = var7 ? 8 : IndexManager.pickShipId(var2);
                     var5 = true;

                     for(int var6 = 0; var6 < var1.length; ++var6) {
                        if (var1[var6] != null && var1[var6].getIndex() == var4) {
                           var5 = false;
                           break;
                        }
                     }
                  }

                  var1[var3] = IndexManager.getShips()[var4].cloneBase();
                  var1[var3].setRace(Status.getSystem().getRace());
                  var1[var3].adjustPrice();
                  break;
               }
            }

            return var1;
         }
      }
   }

   public final int[] getLootList() {
      Item[] var1 = IndexManager.getItems();
      int var2;
      if ((var2 = GameStatus.random.nextInt(3)) == 0) {
         return null;
      } else {
         int[] var9 = new int[var2 << 1];
         Item[] var3 = IndexManager.getItems();
         int var4 = 0;

         for(int var5 = 0; var5 < var9.length; var5 += 2) {
            boolean var6 = false;
            int var7 = 0;

            for(int var8 = 0; var8 < 100 && !var6; ++var8) {
               var7 = GameStatus.random.nextInt(var1.length);
               var4 = var3[var7].getType();
               if (var3[var7].getBluePrintComponentsIds() == null && GameStatus.random.nextInt(100) < this.itemTypeOccurance[var4] && GameStatus.random.nextInt(100) < var1[var7].getOccurance() && var3[var7].getPrice() > 0 && var7 != 175 && var7 != 164 && (var4 == 4 || var3[var7].getTecLevel() <= 7)) {
                  var6 = true;
               }
            }

            if (!var6) {
               var7 = 154 + GameStatus.random.nextInt(10);
               var4 = 4;
            }

            var9[var5] = var7;
            if (var4 == 4) {
               var9[var5 + 1] = 1 + GameStatus.random.nextInt(9);
            } else {
               var9[var5 + 1] = 1;
            }
         }

         return var9;
      }
   }

   private int pickNextDestination(SolarSystem[] var1, int var2) {
      int var3 = 0;
      boolean var4 = false;

      while(!var4) {
         if (GameStatus.random.nextInt(100) < 20) {
            var3 = var2;
         } else if (GameStatus.random.nextInt(100) < 40) {
            int[] var5;
            var3 = (var5 = var1[Status.getSystem().getId()].getStations())[GameStatus.random.nextInt(var5.length)];
         } else {
            var3 = GameStatus.random.nextInt(100);
         }

         if (Status.getSystem().getId() == 15) {
            var3 = Status.getSystem().getStations()[GameStatus.random.nextInt(Status.getSystem().getStations().length)];
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

   public final Agent[] createAgents(Station var1) {
      Agent[] var2 = Status.getSpecialAgents();
      int var3 = 0;
      boolean var4 = Status.getCurrentCampaignMission() > 16;

      int var5;
      for(var5 = 0; var5 < var2.length; ++var5) {
         if (var2[var5].getStationId() == var1.getId() && var4) {
            ++var3;
         }
      }

      Agent[] var11 = new Agent[AEMath.min(5, var3 + 3 + GameStatus.random.nextInt(2))];
      var5 = 0;

      int var6;
      for(var6 = 0; var6 < var2.length; ++var6) {
         if (var2[var6].getStationId() == var1.getId() && var4) {
            var11[var5++] = var2[var6];
         }
      }

      boolean var15 = false;

      int var17;
      label176:
      for(int var9 = var5; var9 < var11.length; ++var9) {
         boolean var14 = false;
         var5 = Status.getSystem().getRace();
         if (GameStatus.random.nextInt(100) < 20) {
            var5 = GameStatus.random.nextInt(8);
         }

         boolean var7 = false;
         int var8 = -1;

         while(true) {
            do {
               if (var7) {
                  if (GameStatus.random.nextInt(100) < 33 || (var8 == 5 || var8 == 6) && Status.getCurrentCampaignMission() < 16) {
                     var8 = 0;
                  }

                  var7 = var8 != 6 && var5 == 0 ? GameStatus.random.nextInt(100) < 60 : true;
                  Agent var12;
                  (var12 = new Agent(-1, IndexManager.sub_116(var5, var7), var1.getId(), Status.getSystem().getId(), var5, var7, -1, -1, -1)).sub_48a(var8);
                  var12.sub_460(Face_.getRandomFace(var7, var5));
                  if (var12.sub_1b1() == 6) {
                     String[] var18 = new String[var5 = GameStatus.random.nextInt(3)];

                     for(var8 = 0; var8 < var5; ++var8) {
                        var18[var8] = IndexManager.sub_116(var12.getRace(), true);
                     }

                     var12.var_70 = null;
                     var12.var_ad = null;
                     var12.var_5d5 = 0;
                     if (var18.length > 0 && var18[0] != null) {
                        var12.var_70 = var18[0];
                        ++var12.var_5d5;
                     }

                     if (var18.length > 1 && var18[1] != null) {
                        var12.var_ad = var18[1];
                        ++var12.var_5d5;
                     }

                     var12.sub_5c4((var5 + 1) * (700 + GameStatus.random.nextInt(1300)));
                  } else if (var12.sub_1b1() == 2) {
                     Item[] var16 = IndexManager.getItems();
                     var7 = false;

                     while(true) {
                        while((var17 = GameStatus.random.nextInt(var16.length)) == 175) {
                        }

                        if (var17 != 164 && var17 != 131 && var16[var17].getBluePrintComponentsIds() == null && var16[var17].getPrice() != 0) {
                           Item var19 = var16[var17];
                           var5 = 5 + GameStatus.random.nextInt(15);
                           if (var19.getType() == 3 || var19.getType() == 0 || var19.getType() == 2) {
                              var5 = 1;
                           }

                           var8 = var5 * (int)((float)(40 + GameStatus.random.nextInt(120)) / 100.0F * (float)IndexManager.getItems()[var17].getPrice());
                           var12.sub_82f(var17, var5, var8);
                           break;
                        }
                     }
                  }

                  var11[var9] = var12;
                  if (var11[var9].sub_1b1() == 6) {
                     if (var15) {
                        var11[var9].sub_48a(1);
                     }

                     var15 = true;
                  } else if (var11[var9].sub_1b1() == 0) {
                     var11[var9].sub_4e1(this.createFreelanceMission(var11[var9]));
                  }
                  continue label176;
               }

               var8 = GameStatus.random.nextInt(7);
            } while(var5 == 1 && var8 == 6);

            if (var8 != 4 && var8 != 3) {
               var7 = true;
            }
         }
      }

      Standing var10 = Status.getStanding();
      int[] var13 = new int[]{2, 3, 0, 1};

      for(var5 = 0; var5 < var13.length; ++var5) {
         var6 = var13[var5];
         if (var10.isEnemy(var6)) {
            for(var17 = 0; var17 < var11.length; ++var17) {
               if (var11[var17].sub_41f() && var11[var17].sub_1b1() != 7) {
                  var11[var17] = null;
                  var11[var17] = new Agent(-1, IndexManager.sub_116(var6, true), var1.getId(), Status.getSystem().getId(), var6, true, -1, -1, -1);
                  var11[var17].sub_48a(7);
                  var11[var17].sub_460(Face_.getRandomFace(true, var6));
                  break;
               }
            }
         }
      }

      return var11;
   }

   public final Mission createFreelanceMission(Agent var1) {
      new FileRead();
      SolarSystem[] var2 = FileRead.loadSystemsBinary();
      int var3 = this.pickNextDestination(var2, var1.getStationId());
      if (Status.getSystem().getId() == 15) {
         var3 = Status.getSystem().getStations()[0] + GameStatus.random.nextInt(4);
      }

      boolean var4 = false;
      int var5 = 0;

      while(!var4) {
         if ((var5 = GameStatus.random.nextInt(13)) != 8) {
            var4 = true;
         }
      }

      if (Status.getCurrentCampaignMission() < 16) {
         switch(GameStatus.random.nextInt(5)) {
         case 0:
            var5 = 11;
            break;
         case 1:
            var5 = 0;
            break;
         case 2:
            var5 = 7;
            break;
         case 3:
            var5 = 4;
            break;
         case 4:
            var5 = 12;
         }
      }

      if (var5 == 12) {
         var3 = var1.getStationId();
      }

      if (var1.sub_1b1() == 5) {
         var5 = 8;
         var3 = var1.getStationId();
      }

      if (var5 == 11 || var5 == 0) {
         while(var3 == Status.getStation().getId()) {
            var3 = this.pickNextDestination(var2, var1.getStationId());
         }
      }

      int var14 = var1.getRace();
      String var6 = var1.fullName;
      boolean var7 = false;
      int var8 = 0;
      int var9 = 0;
      int var15;
      if (Status.getCurrentCampaignMission() < 16) {
         var15 = 1 + GameStatus.random.nextInt(2);
      } else {
         var15 = 1 + GameStatus.random.nextInt(9);
      }

      if (var5 == 8) {
         Item[] var10 = IndexManager.getItems();
         boolean var11 = false;

         int var17;
         do {
            do {
               do {
                  do {
                     var17 = 97 + GameStatus.random.nextInt(var10.length - 97);
                  } while(var10[var17].getPrice() == 0);
               } while(var17 == 175);
            } while(var17 == 164);
         } while(var17 == 131);

         var9 = var17;
         var8 = 5 + GameStatus.random.nextInt(15);
         var15 = var10[var17].getTecLevel();
      } else if (var5 != 3 && var5 != 5) {
         if (var5 == 0) {
            var8 = 5 + (int)(95.0F * ((float)var15 / 10.0F));
            var9 = GameStatus.random.nextInt(7);
         } else if (var5 == 11) {
            var8 = 2 + (int)(18.0F * ((float)var15 / 10.0F));
         } else if (var5 == 2) {
            var8 = 2 + (int)((float)GameStatus.random.nextInt(4));
         }
      } else {
         var8 = 2 + (int)(8.0F * ((float)var15 / 10.0F));
         var9 = var5 == 3 ? 116 : 117;
      }

      var15 = AEMath.min(10, var15);
      float var16 = 1.0F + Galaxy.distance(var2[Status.getStation().getSystemIndex()], var2[Galaxy.getStation(var3).getSystemIndex()]) / 1200.0F;
      float var18 = (var18 = (float)(1500 + (int)((float)var15 / 10.0F * 5500.0F))) * var16 + (float)(Status.getLevel() * 200);
      if (var5 == 7) {
         var18 *= 0.7F;
      } else if (var5 == 9) {
         var18 *= 1.2F;
      } else if (var5 == 8) {
         var18 = var18 / 2.0F + (float)(var8 * IndexManager.getItems()[var9].getMaxPrice() * 3);
      } else if (var5 == 11) {
         var18 = (var18 *= 0.6F) + (float)var8 * (var18 / 5.0F);
      } else if (var5 == 5 || var5 == 3) {
         var18 *= 2.0F;
      }

      int var13 = (int)var18 % 50;
      var18 = (var18 + (float)var13) % 50.0F == 0.0F ? var18 + (float)var13 : var18 - (float)var13;
      Mission var12 = new Mission(var5, var6, var1.sub_471(), var14, (int)var18, var3, var15);
      var3 = (int)(var18 / 10.0F + (float)GameStatus.random.nextInt((int)var18 / 10));
      if (var5 == 8) {
         var3 = (int)((float)var3 * 0.5F);
      } else if (var5 == 6) {
         var12.setTargetName(IndexManager.sub_116(0, true));
      }

      var13 = var3 % 50;
      var3 = (var3 + var13) % 50 == 0 ? var3 + var13 : var3 - var13;
      var12.sub_54a(var3);
      var12.setCommodity(var9, var8);
      return var12;
   }
}
