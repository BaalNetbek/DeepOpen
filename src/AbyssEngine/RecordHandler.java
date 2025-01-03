package AbyssEngine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public final class RecordHandler {
   private RecordStore recordStore = null;
   private Mission mission;
   private Agent agent;

   public final GameRecord[] readAllPreviews() {
      GameRecord[] var1 = new GameRecord[4];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = this.readPreview(var2);
      }

      return var1;
   }

   public final void readOptions() {
      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_Options", false);
         this.recordStore.closeRecordStore();
      } catch (RecordStoreException var5) {
      }

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_Options", false);
         byte[] var1 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
         ByteArrayInputStream var6 = new ByteArrayInputStream(var1);
         DataInputStream var2;
         GameStatus.musicOn = (var2 = new DataInputStream(var6)).readBoolean();
         GameStatus.vibrationOn = var2.readBoolean();
         GameStatus.invertedControlsOn = var2.readBoolean();
         GameStatus.musicVolume = var2.readInt();
         GameStatus.sfxVolume = var2.readInt();
         GameStatus.sfxOn = var2.readBoolean();
         var2.close();
         var6.close();
         this.recordStore.closeRecordStore();
         this.recordStore = null;
      } catch (RecordStoreException var3) {
      } catch (IOException var4) {
      }
   }

   public final void writeOptions() {
      try {
         RecordStore.deleteRecordStore("GOF2_Options");
      } catch (RecordStoreException var6) {
      }

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_Options", true);
      } catch (RecordStoreException var5) {
      }

      ByteArrayOutputStream var1 = null;
      DataOutputStream var2 = null;

      try {
         var1 = new ByteArrayOutputStream();
         (var2 = new DataOutputStream(var1)).writeBoolean(GameStatus.musicOn);
         var2.writeBoolean(GameStatus.vibrationOn);
         var2.writeBoolean(GameStatus.invertedControlsOn);
         var2.writeInt(GameStatus.musicVolume);
         var2.writeInt(GameStatus.sfxVolume);
         var2.writeBoolean(GameStatus.sfxOn);
         var2.close();
         var1.close();
         this.recordStore.addRecord(var1.toByteArray(), 0, var1.toByteArray().length);
         this.recordStore.closeRecordStore();
         this.recordStore = null;
      } catch (IOException var3) {
      } catch (RecordStoreException var4) {
         var4.printStackTrace();
      }
   }

   private GameRecord readRecordFromByteArray(byte[] var1) {
      ByteArrayInputStream var16 = new ByteArrayInputStream(var1);
      DataInputStream var2 = new DataInputStream(var16);
      GameRecord var3 = new GameRecord();

      try {
         int var4;
         for(var4 = 0; var4 < var3.var_df.length; ++var4) {
            var3.var_df[var4] = var2.readBoolean();
         }

         var3.credits = var2.readInt();
         var3.rating = var2.readInt();
         var3.playTime = var2.readLong();
         var3.kills = var2.readInt();
         var3.missionCount = var2.readInt();
         var3.level = var2.readInt();
         var3.lastWorth = var2.readInt();
         var3.var_4b1 = var2.readInt();
         var3.stationsVisited = var2.readInt();
         var3.var_73b = var2.readInt();
         var3.var_9a3 = this.readMission(var2);
         var3.var_a05 = this.readMission(var2);
         var3.var_552 = var2.readInt();
         var3.var_5ee = var2.readInt();
         var3.var_663 = var2.readInt();
         var3.var_4d6 = var2.readInt();
         var3.var_81e = var2.readInt();
         var3.var_883 = var2.readInt();
         var3.var_8e6 = var2.readInt();
         var3.var_97f = var2.readInt();

         for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
            var3.var_a74[var4] = var2.readBoolean();
         }

         for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
            var3.var_ad5[var4] = var2.readBoolean();
         }

         var3.var_b28 = var2.readInt();
         var3.var_b4b = var2.readInt();
         var3.var_b83 = var2.readInt();
         var3.var_bce = var2.readInt();

         for(var4 = 0; var4 < Status.drinksVar1.length; ++var4) {
            var3.var_c0d[var4] = var2.readBoolean();
         }

         var3.var_c47 = var2.readInt();

         for(var4 = 0; var4 < Status.drinksVar2.length; ++var4) {
            var3.var_c81[var4] = var2.readBoolean();
         }

         var3.var_cbe = var2.readInt();
         var3.var_cfa = var2.readLong();
         var3.var_d55 = var2.readInt();
         var3.var_d83 = var2.readInt();
         var3.var_dcf = var2.readInt();
         var3.var_de1 = var2.readInt();
         var2.readInt();
         var3.var_e29 = var2.readInt();
         var3.var_e62 = var2.readInt();
         var3.var_e81 = var2.readInt();
         var3.var_e95 = var2.readInt();
         var3.var_ea3 = var2.readInt();
         var3.var_ede = var2.readInt();
         var4 = var2.readInt();
         var3.var_a68 = new int[var4];

         for(var4 = 0; var4 < var3.var_a68.length; ++var4) {
            var3.var_a68[var4] = var2.readInt();
         }

         var3.var_14f4 = IndexManager.getShips()[var2.readInt()].cloneBase();
         var3.var_14f4.setRace(var2.readInt());
         var4 = var2.readInt();
         Item[] var5 = null;
         int var6;
         int var7;
         int var8;
         if (var4 > 0) {
            var5 = new Item[var4];

            for(var6 = 0; var6 < var4; ++var6) {
               if ((var7 = var2.readInt()) == -1) {
                  var5[var6] = null;
               } else {
                  var8 = var2.readInt();
                  var5[var6] = IndexManager.getItems()[var7].getCopyInAmmount(var8, IndexManager.getItems()[var7].getMaxPrice());
                  var5[var6].sub_986(var2.readBoolean());
               }
            }

            var3.var_14f4.setEquipment(var5);
         }

         var6 = var2.readInt();
         Item[] var19 = null;
         int var9;
         int var17;
         if (var6 > 0) {
            var19 = new Item[var6];

            for(var8 = 0; var8 < var6; ++var8) {
               var4 = var2.readInt();
               var17 = var2.readInt();
               var9 = var2.readInt();
               boolean var10 = var2.readBoolean();
               var19[var8] = IndexManager.getItems()[var4].getCopyInAmmount(var17, IndexManager.getItems()[var4].getMaxPrice());
               var19[var8].setPrice(var9);
               var19[var8].sub_986(var10);
            }

            var3.var_14f4.refreshCargoSpaceUnsafe(var19);
         }

         Station[] var21 = new Station[3];

         int var11;
         int var12;
         for(var4 = 0; var4 < var21.length + 1; ++var4) {
            var5 = null;
            Station var18;
            short var22;
            if ((var22 = var2.readShort()) == -1) {
               var18 = null;
            } else {
               var18 = Galaxy.getStation(var22);
               var6 = var2.readInt();
               Item[] var26 = null;
               if (var6 > 0) {
                  var26 = new Item[var6];

                  for(var7 = 0; var7 < var6; ++var7) {
                     var9 = var2.readInt();
                     var11 = var2.readInt();
                     var12 = var2.readInt();
                     boolean var13 = var2.readBoolean();
                     var26[var7] = IndexManager.getItems()[var9].getCopyInAmmount(var11, IndexManager.getItems()[var9].getMaxPrice());
                     var26[var7].setPrice(var12);
                     var26[var7].sub_986(var13);
                  }

                  var18.setShopItems(var26);
               }

               var7 = var2.readInt();
               Ship[] var25 = null;
               if (var7 > 0) {
                  var25 = new Ship[var7];

                  for(var11 = 0; var11 < var7; ++var11) {
                     var12 = var2.readInt();
                     var25[var11] = IndexManager.getShips()[var12].cloneBase();
                     var25[var11].setRace(var2.readInt());
                  }

                  var18.setShopShips(var25);
               }

               var11 = var2.readInt();
               Agent[] var31 = null;
               if (var11 > 0) {
                  var31 = new Agent[var11];

                  for(int var32 = 0; var32 < var11; ++var32) {
                     var31[var32] = this.readAgent(var2);
                  }

                  var18.setBarAgents(var31);
               }
            }

            if (var4 == var21.length) {
               var3.var_1521 = var18;
            } else {
               var21[var4] = var18;
            }
         }

         var3.var_a51 = var21;
         int[] var23 = Status.getStanding().getStanding();

         for(var17 = 0; var17 < var23.length; ++var17) {
            var23[var17] = var2.readInt();
         }

         var2.readInt();
         var2.readInt();
         var3.var_1566 = new Standing();
         var3.var_1566.setStanding(var23);
         BluePrint[] var20 = new BluePrint[Status.getBluePrints().length];

         for(var9 = 0; var9 < var20.length; ++var9) {
            var20[var9] = new BluePrint(Status.getBluePrints()[var9].getIndex());
            BluePrint var27 = var20[var9];

            for(var7 = 0; var7 < var27.missingComponentsTons.length; ++var7) {
               var27.missingComponentsTons[var7] = var2.readInt();
            }

            var27.investedCredits = var2.readInt();
            var27.unlocked = var2.readBoolean();
            var27.timesProduced = var2.readInt();
            var27.productionStationId = var2.readInt();
            String var24;
            if ((var24 = var2.readUTF()).equals("")) {
               var24 = null;
            }

            var27.productionStationName = var24;
         }

         var3.var_157c = var20;
         if ((var9 = var2.readInt()) <= 0) {
            var3.var_15d9 = null;
         } else {
            ProducedGood[] var28 = new ProducedGood[var9];

            for(var7 = 0; var7 < var28.length; ++var7) {
               var9 = var2.readInt();
               var11 = var2.readInt();
               var12 = var2.readInt();
               String var33;
               if ((var33 = var2.readUTF()).equals("")) {
                  var33 = null;
               }

               var28[var7] = new ProducedGood(var9, var33, var12, var11);
            }

            var3.var_15d9 = var28;
         }

         int var30;
         if ((var30 = var2.readInt()) <= 0) {
            var3.var_1646 = null;
         } else {
            String[] var29 = new String[var30];

            for(var9 = 0; var9 < var30; ++var9) {
               var29[var9] = var2.readUTF();
            }

            var3.var_1646 = var29;
            var3.var_1685 = var2.readInt();
            var3.var_16d5 = var2.readInt();
            var9 = var2.readInt();
            var3.var_1721 = new byte[var9];

            for(var11 = 0; var11 < var3.var_1721.length; ++var11) {
               var3.var_1721[var11] = var2.readByte();
            }
         }

         var3.var_175b = var2.readInt();
         var3.var_17bb = new boolean[Status.visibleSystems.length];

         for(var7 = 0; var7 < Status.visibleSystems.length; ++var7) {
            var3.var_17bb[var7] = var2.readBoolean();
         }

         var3.var_1829 = new int[Status.highestItemPrices.length];

         for(var7 = 0; var7 < Status.highestItemPrices.length; ++var7) {
            var3.var_1829[var7] = var2.readInt();
         }

         var3.var_17e0 = new int[Status.lowestItemPrices.length];

         for(var7 = 0; var7 < Status.lowestItemPrices.length; ++var7) {
            var3.var_17e0[var7] = var2.readInt();
         }

         var3.var_187b = new byte[Status.highestItemPriceSystems.length];

         for(var7 = 0; var7 < Status.highestItemPriceSystems.length; ++var7) {
            var3.var_187b[var7] = var2.readByte();
         }

         var3.var_186b = new byte[Status.lowestItemPriceSystems.length];

         for(var7 = 0; var7 < Status.lowestItemPriceSystems.length; ++var7) {
            var3.var_186b[var7] = var2.readByte();
         }

         var3.var_162f = new Agent[Status.getSpecialAgents().length];

         for(var7 = 0; var7 < var3.var_162f.length; ++var7) {
            var3.var_162f[var7] = this.readAgent(var2);
         }

         var3.var_f30 = var2.readBoolean();
         var3.var_f50 = var2.readBoolean();
         var3.var_f5f = var2.readBoolean();
         var3.var_f6c = var2.readBoolean();
         var3.var_fc2 = var2.readBoolean();
         var3.var_100f = var2.readBoolean();
         var3.var_1038 = var2.readBoolean();
         var3.var_1047 = var2.readBoolean();
         var3.var_108f = var2.readBoolean();
         var3.var_10ed = var2.readBoolean();
         var3.var_119d = var2.readBoolean();
         var3.var_11c5 = var2.readBoolean();
         var3.var_11eb = var2.readBoolean();
         var3.var_121f = var2.readBoolean();
         var3.var_1273 = var2.readBoolean();
         var3.var_12c6 = var2.readBoolean();
         var3.var_12e2 = var2.readBoolean();
         var3.var_132f = var2.readBoolean();
         var3.var_1378 = var2.readBoolean();
         var3.var_1392 = var2.readBoolean();
         var3.var_13ae = var2.readBoolean();
         var3.var_13db = var2.readBoolean();
         var3.var_13f8 = var2.readBoolean();
         var3.var_1407 = var2.readBoolean();
         var3.var_1428 = var2.readBoolean();
         var3.var_148e = var2.readLong();
         var3.var_14c1 = var2.readLong();

         try {
            var3.var_1449 = var2.readBoolean();
            var3.var_1459 = var2.readBoolean();
         } catch (Exception var14) {
         }

         var2.close();
         var16.close();
      } catch (Exception var15) {
         System.err.println("Error in readRecordFromByteArray!");
      }

      return var3;
   }

   public final GameRecord sub_106(int var1) {
      this.agent = null;
      this.mission = null;
      GameRecord var2 = null;

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, false);
         this.recordStore.closeRecordStore();
      } catch (RecordStoreException var4) {
      }

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, false);
         byte[] var5 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
         var2 = this.readRecordFromByteArray(var5);
         this.recordStore.closeRecordStore();
         this.recordStore = null;
      } catch (Exception var3) {
         var2 = null;
      }

      return var2;
   }

   public final void sub_115(int var1) {
      this.agent = null;
      this.mission = null;

      try {
         RecordStore.deleteRecordStore("GOF2_" + var1);
      } catch (RecordStoreException var10) {
      }

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, true);
      } catch (RecordStoreException var9) {
      }

      try {
         byte[] var2 = this.sub_157();
         this.recordStore.addRecord(var2, 0, var2.length);
         this.recordStore.closeRecordStore();
         this.recordStore = null;
         int var12 = var1;
         RecordHandler var11 = this;

         try {
            RecordStore.deleteRecordStore("GOF2_Preview_" + var12);
         } catch (RecordStoreException var7) {
         }

         try {
            var11.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var12, true);
         } catch (RecordStoreException var6) {
         }

         ByteArrayOutputStream var3 = null;
         DataOutputStream var13 = null;

         try {
            var3 = new ByteArrayOutputStream();
            (var13 = new DataOutputStream(var3)).writeLong(Status.getPlayingTime());
            var13.writeInt(Status.getCredits());
            var13.writeUTF(Status.getStation().getName());
            var13.writeUTF(Status.getSystem().getName());
            var13.writeInt(Status.getCurrentCampaignMission());
            var13.close();
            var3.close();
            var11.recordStore.addRecord(var3.toByteArray(), 0, var3.toByteArray().length);
            var11.recordStore.closeRecordStore();
            var11.recordStore = null;
         } catch (IOException var4) {
         } catch (RecordStoreException var5) {
            return;
         }
      } catch (Exception var8) {
      }

   }

   private byte[] sub_157() {
      ByteArrayOutputStream var1 = null;
      DataOutputStream var2 = null;

      try {
         var1 = new ByteArrayOutputStream();
         var2 = new DataOutputStream(var1);
         boolean[] var3 = Galaxy.getVisitedStations();

         int var4;
         for(var4 = 0; var4 < var3.length; ++var4) {
            var2.writeBoolean(var3[var4]);
         }

         var2.writeInt(Status.getCredits());
         var2.writeInt(Status.getRating());
         var2.writeLong(Status.getPlayingTime());
         var2.writeInt(Status.getKills());
         var2.writeInt(Status.getMissionCount());
         var2.writeInt(Status.getLevel());
         var2.writeInt(Status.getLastXP());
         var2.writeInt(Status.getGoodsProduced());
         var2.writeInt(Status.getStationsVisited());
         var2.writeInt(Status.getCurrentCampaignMission());
         this.writeMission(var2, Status.getFreelanceMission());
         this.writeMission(var2, Status.getCampaignMission());
         var2.writeInt(Status.getJumpgateUsed());
         var2.writeInt(Status.getCargoSalvaged());
         var2.writeInt(Status.sub_6ac());
         var2.writeInt(Status.sub_cc1());
         var2.writeInt(Status.wormholeStation);
         var2.writeInt(Status.wormholeSystem);
         var2.writeInt(Status.lastVisitedNonVoidOrbit);
         var2.writeInt(Status.var_cae);

         for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
            var2.writeBoolean(Status.minedOreTypes[var4]);
         }

         for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
            var2.writeBoolean(Status.minedCoreTypes[var4]);
         }

         var2.writeInt(Status.var_d88);
         var2.writeInt(Status.minedOre);
         var2.writeInt(Status.minedCores);
         var2.writeInt(Status.var_e59);

         for(var4 = 0; var4 < Status.drinksVar1.length; ++var4) {
            var2.writeBoolean(Status.drinksVar1[var4]);
         }

         var2.writeInt(Status.var_ea8);

         for(var4 = 0; var4 < Status.drinksVar2.length; ++var4) {
            var2.writeBoolean(Status.drinksVar2[var4]);
         }

         var2.writeInt(Status.var_f13);
         var2.writeLong(Status.invisibleTime);
         var2.writeInt(Status.bombsUsed);
         var2.writeInt(Status.alienJunkSalvaged);
         var2.writeInt(Status.barInteractions);
         var2.writeInt(Status.commandedWingmen);
         var2.writeInt(1);
         var2.writeInt(Status.asteroidsDestroyed);
         var2.writeInt(Status.maxFreeCargo);
         var2.writeInt(Status.missionsRejected);
         var2.writeInt(Status.askedToRepeate);
         var2.writeInt(Status.acceptedNotAskingDifficulty);
         var2.writeInt(Status.acceptedNotAskingLocation);
         int[] var15 = Medals.getMedals();
         var2.writeInt(var15.length);

         int var9;
         for(var9 = 0; var9 < var15.length; ++var9) {
            var2.writeInt(var15[var9]);
         }

         var2.writeInt(Status.getShip().getIndex());
         var2.writeInt(Status.getShip().getRace());
         Item[] var10;
         if ((var10 = Status.getShip().getEquipment()) == null) {
            var2.writeInt(0);
         } else {
            var2.writeInt(var10.length);

            for(var4 = 0; var4 < var10.length; ++var4) {
               if (var10[var4] != null) {
                  var2.writeInt(var10[var4].getIndex());
                  var2.writeInt(var10[var4].getAmount());
                  var2.writeBoolean(var10[var4].sub_95c());
               } else {
                  var2.writeInt(-1);
               }
            }
         }

         Item[] var18;
         if ((var18 = Status.getShip().getCargo()) == null) {
            var2.writeInt(0);
         } else {
            var2.writeInt(var18.length);

            for(var9 = 0; var9 < var18.length; ++var9) {
               var2.writeInt(var18[var9].getIndex());
               var2.writeInt(var18[var9].getAmount());
               var2.writeInt(var18[var9].getPrice());
               var2.writeBoolean(var18[var9].sub_95c());
            }
         }

         int var6;
         int var7;
         Agent[] var19;
         for(var9 = 0; var9 < Status.getLastVisitedStations().length + 1; ++var9) {
            Station var5 = null;
            if (var9 == Status.getLastVisitedStations().length) {
               var5 = Status.getStation();
            } else {
               var5 = Status.getLastVisitedStations()[var9];
            }

            if (var5 == null) {
               var2.writeShort(-1);
            } else {
               var2.writeShort(var5.getId());
               if ((var18 = var5.getShopItems()) == null) {
                  var2.writeInt(0);
               } else {
                  var2.writeInt(var18.length);

                  for(var6 = 0; var6 < var18.length; ++var6) {
                     var2.writeInt(var18[var6].getIndex());
                     var2.writeInt(var18[var6].getAmount());
                     var2.writeInt(var18[var6].getPrice());
                     var2.writeBoolean(var18[var6].sub_95c());
                  }
               }

               Ship[] var14;
               if ((var14 = var5.getShopShips()) == null) {
                  var2.writeInt(0);
               } else {
                  var2.writeInt(var14.length);

                  for(var4 = 0; var4 < var14.length; ++var4) {
                     var2.writeInt(var14[var4].getIndex());
                     var2.writeInt(var14[var4].getRace());
                  }
               }

               if ((var19 = var5.getBarAgents()) == null) {
                  var2.writeInt(0);
               } else {
                  var2.writeInt(var19.length);

                  for(var7 = 0; var7 < var19.length; ++var7) {
                     Agent var12 = var19[var7];
                     this.writeAgent(var2, var12);
                  }
               }
            }
         }

         int[] var11 = Status.getStanding().getStanding();

         for(int var13 = 0; var13 < var11.length; ++var13) {
            var2.writeInt(var11[var13]);
         }

         var2.writeInt(1);
         var2.writeInt(1);
         BluePrint[] var16 = Status.getBluePrints();

         for(var6 = 0; var6 < var16.length; ++var6) {
            BluePrint var20 = var16[var6];

            for(var7 = 0; var7 < var20.missingComponentsTons.length; ++var7) {
               var2.writeInt(var20.missingComponentsTons[var7]);
            }

            var2.writeInt(var20.investedCredits);
            var2.writeBoolean(var20.unlocked);
            var2.writeInt(var20.timesProduced);
            var2.writeInt(var20.productionStationId);
            if (var20.productionStationName == null) {
               var2.writeUTF("");
            } else {
               var2.writeUTF(var20.productionStationName);
            }
         }

         ProducedGood[] var17;
         if ((var17 = Status.getWaitingGoods()) == null) {
            var2.writeInt(-1);
         } else {
            var4 = 0;

            for(var7 = 0; var7 < var17.length; ++var7) {
               if (var17[var7] != null) {
                  ++var4;
               }
            }

            if (var4 == 0) {
               var2.writeInt(-1);
            } else {
               var2.writeInt(var4);

               for(var7 = 0; var7 < var17.length; ++var7) {
                  if (var17[var7] != null) {
                     var2.writeInt(var17[var7].index);
                     var2.writeInt(var17[var7].producedQuantity);
                     var2.writeInt(var17[var7].stationId);
                     if (var17[var7].stationName == null) {
                        var2.writeUTF("");
                     } else {
                        var2.writeUTF(var17[var7].stationName);
                     }
                  }
               }
            }
         }

         if (Status.wingmenNames == null) {
            var2.writeInt(-1);
         } else {
            var2.writeInt(Status.wingmenNames.length);

            for(var4 = 0; var4 < Status.wingmenNames.length; ++var4) {
               var2.writeUTF(Status.wingmenNames[var4]);
            }

            var2.writeInt(Status.wingmenRace_);
            var2.writeInt(Status.wingmenTimeRemaining);
            var2.writeInt(Status.var_8f8wingmenVar2.length);

            for(var4 = 0; var4 < Status.var_8f8wingmenVar2.length; ++var4) {
               var2.writeByte(Status.var_8f8wingmenVar2[var4]);
            }
         }

         var2.writeInt(Status.var_9a8);

         for(var4 = 0; var4 < Status.visibleSystems.length; ++var4) {
            var2.writeBoolean(Status.visibleSystems[var4]);
         }

         for(var4 = 0; var4 < Status.highestItemPrices.length; ++var4) {
            var2.writeInt(Status.highestItemPrices[var4]);
         }

         for(var4 = 0; var4 < Status.lowestItemPrices.length; ++var4) {
            var2.writeInt(Status.lowestItemPrices[var4]);
         }

         for(var4 = 0; var4 < Status.highestItemPriceSystems.length; ++var4) {
            var2.writeByte(Status.highestItemPriceSystems[var4]);
         }

         for(var4 = 0; var4 < Status.lowestItemPriceSystems.length; ++var4) {
            var2.writeByte(Status.lowestItemPriceSystems[var4]);
         }

         var19 = Status.getSpecialAgents();

         for(var7 = 0; var7 < var19.length; ++var7) {
            this.writeAgent(var2, var19[var7]);
         }

         var2.writeBoolean(GameStatus.shopHelpShown);
         var2.writeBoolean(GameStatus.var_264);
         var2.writeBoolean(GameStatus.var_2b3);
         var2.writeBoolean(GameStatus.var_2cc);
         var2.writeBoolean(GameStatus.var_2e8);
         var2.writeBoolean(GameStatus.barHelpShown);
         var2.writeBoolean(GameStatus.galaxyMapHelpShown);
         var2.writeBoolean(GameStatus.systemMapHelpShown);
         var2.writeBoolean(GameStatus.var_40c);
         var2.writeBoolean(GameStatus.miningHelpShown);
         var2.writeBoolean(GameStatus.asteroidHelpShown);
         var2.writeBoolean(GameStatus.missionsHelpShown);
         var2.writeBoolean(GameStatus.cargoFullHelpShown);
         var2.writeBoolean(GameStatus.wingmenHelpShown);
         var2.writeBoolean(GameStatus.var_554);
         var2.writeBoolean(GameStatus.boosterHelpShown);
         var2.writeBoolean(GameStatus.statusHelpShown);
         var2.writeBoolean(GameStatus.medalsHelpShown);
         var2.writeBoolean(GameStatus.fanWingmenNoticeShown);
         var2.writeBoolean(GameStatus.voidxNoticeShown);
         var2.writeBoolean(GameStatus.reputationHelpShown);
         var2.writeBoolean(GameStatus.buyingHelpShown);
         var2.writeBoolean(GameStatus.itemMountingHelpShown);
         var2.writeBoolean(GameStatus.itemMounting2HelpShown);
         var2.writeBoolean(GameStatus.interplanetHelpShown);
         var2.writeLong(Status.loadingsCount);
         var2.writeLong(Status.timeSpentLoading);
         var2.writeBoolean(GameStatus.jumpDriveHelpShown);
         var2.writeBoolean(GameStatus.cloakHelpShown);
         var2.close();
         var1.close();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return var1.toByteArray();
   }

   private void writeAgent(DataOutputStream var1, Agent var2) throws IOException {
      var1.writeInt(var2.sub_5d0());
      var1.writeInt(var2.sub_569());
      var1.writeInt(var2.sub_582());
      var1.writeInt(var2.sub_35e());
      var1.writeInt(var2.sub_112());
      var1.writeInt(var2.sub_1b1());
      var1.writeInt(var2.getRace());
      var1.writeInt(var2.sub_773());
      var1.writeInt(var2.sub_7d5());
      var1.writeInt(var2.sub_7ae());
      var1.writeInt(var2.getStationId());
      var1.writeInt(var2.getSystemId());
      var1.writeInt(var2.var_5d5);
      var1.writeBoolean(var2.sub_302());
      var1.writeBoolean(var2.sub_8fc());
      var1.writeBoolean(var2.isAccepted());
      var1.writeBoolean(var2.var_99e);
      var1.writeBoolean(var2.var_9cd);
      if (var2.sub_471() != null) {
         var1.writeInt(var2.sub_471().length);

         for(int var3 = 0; var3 < var2.sub_471().length; ++var3) {
            var1.writeByte(var2.sub_471()[var3]);
         }
      } else {
         var1.writeInt(-1);
      }

      if (var2.getMessage() == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.getMessage());
      }

      if (var2.fullName == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.fullName);
      }

      if (var2.getStationName() == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.getStationName());
      }

      if (var2.systemName == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.systemName);
      }

      if (var2.var_70 == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.var_70);
      }

      if (var2.var_ad == null) {
         var1.writeUTF("");
      } else {
         var1.writeUTF(var2.var_ad);
      }

      this.agent = var2;
      if (var2.sub_4f9() != null && this.mission != var2.sub_4f9()) {
         var1.writeInt(1);
         this.writeMission(var1, var2.sub_4f9());
      } else {
         var1.writeInt(-1);
      }
   }

   private Agent readAgent(DataInputStream var1) throws IOException {
      int var2 = var1.readInt();
      int var3 = var1.readInt();
      int var4 = var1.readInt();
      int var5 = var1.readInt();
      int var6 = var1.readInt();
      int var7 = var1.readInt();
      int var8 = var1.readInt();
      int var9 = var1.readInt();
      int var10 = var1.readInt();
      int var11 = var1.readInt();
      int var12 = var1.readInt();
      int var13 = var1.readInt();
      int var14 = var1.readInt();
      boolean var15 = var1.readBoolean();
      boolean var16 = var1.readBoolean();
      boolean var17 = var1.readBoolean();
      boolean var18 = var1.readBoolean();
      boolean var19 = var1.readBoolean();
      int var20 = var1.readInt();
      byte[] var21 = null;
      if (var20 > 0) {
         var21 = new byte[var20];

         for(var20 = 0; var20 < var21.length; ++var20) {
            var21[var20] = var1.readByte();
         }
      }

      String var28 = var1.readUTF();
      String var22 = var1.readUTF();
      String var23 = var1.readUTF();
      String var24 = var1.readUTF();
      String var25 = var1.readUTF();
      String var26 = var1.readUTF();
      if (var25.equals("")) {
         var25 = null;
      }

      if (var26.equals("")) {
         var26 = null;
      }

      Mission var27 = null;
      if (var1.readInt() > 0) {
         var27 = this.readMission(var1);
      }

      Agent var29;
      (var29 = new Agent(var6, var22, var12, var13, var8, var15, var3, var4, var10)).sub_5c4(var2);
      var29.sub_3c2(var5);
      var29.sub_48a(var7);
      var29.sub_82f(var9, var11, var10);
      var29.var_70 = var25;
      var29.var_ad = var26;
      var29.var_5d5 = var14;
      var29.sub_938(var16);
      var29.setAccepted(var17);
      var29.sub_460(var21);
      var29.setMessage(var28);
      var29.setAgentsStationName(var23);
      var29.setAgentsSystemName(var24);
      var29.sub_4e1(var27);
      var29.var_99e = var18;
      var29.var_9cd = var19;
      return var29;
   }

   private void writeMission(DataOutputStream var1, Mission var2) throws IOException {
      var1.writeInt(var2.getType());
      if (!var2.isEmpty()) {
         if (var2.getClientName() == null) {
            var1.writeUTF("");
         } else {
            var1.writeUTF(var2.getClientName());
         }

         if (var2.getTargetName() == null) {
            var1.writeUTF("");
         } else {
            var1.writeUTF(var2.getTargetName());
         }

         if (var2.getTargetStationName() == null) {
            var1.writeUTF("");
         } else {
            var1.writeUTF(var2.getTargetStationName());
         }

         var1.writeBoolean(var2.isCampaignMission());
         if (var2.getClientImage() != null) {
            var1.writeInt(var2.getClientImage().length);

            for(int var3 = 0; var3 < var2.getClientImage().length; ++var3) {
               var1.writeByte(var2.getClientImage()[var3]);
            }
         } else {
            var1.writeInt(-1);
         }

         var1.writeInt(var2.getClientRace());
         var1.writeInt(var2.sub_50f());
         var1.writeInt(var2.getReward());
         var1.writeInt(var2.setCampaignMission());
         var1.writeInt(var2.getDifficulty());
         var1.writeInt(var2.getCommodityIndex());
         var1.writeInt(var2.getCommodityAmount());
         var1.writeInt(var2.getStatusValue_());
         var1.writeBoolean(var2.sub_12c());
         this.mission = var2;
         if (var2.getAgent() != null && this.agent != var2.getAgent()) {
            var1.writeInt(1);
            this.writeAgent(var1, var2.getAgent());
         } else {
            var1.writeInt(-1);
         }
      }
   }

   private Mission readMission(DataInputStream var1) throws IOException {
      int var2;
      if ((var2 = var1.readInt()) == -1) {
         return Mission.var_dc;
      } else {
         String var3;
         if ((var3 = var1.readUTF()).equals("")) {
            var3 = null;
         }

         String var4;
         if ((var4 = var1.readUTF()).equals("")) {
            var4 = null;
         }

         var1.readUTF().equals("");
         boolean var5 = var1.readBoolean();
         byte[] var6 = null;
         int var7;
         int var8;
         if ((var7 = var1.readInt()) > 0) {
            var6 = new byte[var7];

            for(var8 = 0; var8 < var7; ++var8) {
               var6[var8] = var1.readByte();
            }
         }

         var8 = var1.readInt();
         var7 = var1.readInt();
         int var9 = var1.readInt();
         int var10 = var1.readInt();
         int var11 = var1.readInt();
         int var12 = var1.readInt();
         int var13 = var1.readInt();
         int var14 = var1.readInt();
         boolean var15 = var1.readBoolean();
         int var16 = var1.readInt();
         Agent var17 = null;
         if (var16 > 0) {
            var17 = this.readAgent(var1);
         }

         var1 = null;
         Mission var18;
         if (var5) {
            var18 = new Mission(var2, var9, var10);
         } else {
            var18 = new Mission(var2, var3, var6, var8, var9, var10, var11);
         }

         var18.sub_54a(var7);
         var18.setCommodity(var12, var13);
         var18.setTasksTreshold_(var14);
         var18.sub_91(var15);
         var18.setAgent(var17);
         var18.setTargetName(var4);
         return var18;
      }
   }

   public final GameRecord readPreview(int var1) {
      GameRecord var2 = null;

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var1, false);
         this.recordStore.closeRecordStore();
      } catch (RecordStoreException var5) {
      }

      try {
         this.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var1, false);
         byte[] var7 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
         ByteArrayInputStream var6 = new ByteArrayInputStream(var7);
         DataInputStream var3 = new DataInputStream(var6);
         (var2 = new GameRecord()).playTime = var3.readLong();
         var2.credits = var3.readInt();
         var2.stationName = var3.readUTF();
         var3.readUTF();
         var2.var_73b = var3.readInt();
         var3.close();
         var6.close();
         this.recordStore.closeRecordStore();
         this.recordStore = null;
      } catch (Exception var4) {
         var2 = null;
      }

      return var2;
   }
}
