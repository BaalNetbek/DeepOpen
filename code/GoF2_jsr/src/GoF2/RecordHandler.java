package GoF2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import AE.GlobalStatus;

public final class RecordHandler {
    private RecordStore recordStore = null;
    private Mission mission;
    private Agent agent;

    public final GameRecord[] readAllPreviews() {
        final GameRecord[] var1 = new GameRecord[4];

        for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = readPreview(var2);
        }

        return var1;
    }

    public final void readOptions() {
        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_Options", false);
            this.recordStore.closeRecordStore();
        } catch (final RecordStoreException var5) {
        }

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_Options", false);
            final byte[] var1 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
            final ByteArrayInputStream var6 = new ByteArrayInputStream(var1);
            DataInputStream var2;
            GlobalStatus.musicOn = (var2 = new DataInputStream(var6)).readBoolean();
            GlobalStatus.vibrationOn = var2.readBoolean();
            GlobalStatus.invertedControlsOn = var2.readBoolean();
            GlobalStatus.musicVolume = var2.readInt();
            GlobalStatus.sfxVolume = var2.readInt();
            GlobalStatus.sfxOn = var2.readBoolean();
            var2.close();
            var6.close();
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final RecordStoreException var3) {
        } catch (final IOException var4) {
        }
    }

    public final void writeOptions() {
        try {
            RecordStore.deleteRecordStore("GOF2_Options");
        } catch (final RecordStoreException var6) {
        }

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_Options", true);
        } catch (final RecordStoreException var5) {
        }

        ByteArrayOutputStream var1 = null;
        DataOutputStream var2 = null;

        try {
            var1 = new ByteArrayOutputStream();
            (var2 = new DataOutputStream(var1)).writeBoolean(GlobalStatus.musicOn);
            var2.writeBoolean(GlobalStatus.vibrationOn);
            var2.writeBoolean(GlobalStatus.invertedControlsOn);
            var2.writeInt(GlobalStatus.musicVolume);
            var2.writeInt(GlobalStatus.sfxVolume);
            var2.writeBoolean(GlobalStatus.sfxOn);
            var2.close();
            var1.close();
            this.recordStore.addRecord(var1.toByteArray(), 0, var1.toByteArray().length);
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final IOException var3) {
        } catch (final RecordStoreException var4) {
            var4.printStackTrace();
        }
    }

    private GameRecord readRecordAsByteArray(final byte[] var1) {
        final ByteArrayInputStream var16 = new ByteArrayInputStream(var1);
        final DataInputStream var2 = new DataInputStream(var16);
        final GameRecord var3 = new GameRecord();

        try {
            int var4;
            for(var4 = 0; var4 < var3.visitedStations.length; ++var4) {
                var3.visitedStations[var4] = var2.readBoolean();
            }

            var3.credits = var2.readInt();
            var3.rating = var2.readInt();
            var3.playTime = var2.readLong();
            var3.kills = var2.readInt();
            var3.missionCount = var2.readInt();
            var3.level = var2.readInt();
            var3.lastWorth = var2.readInt();
            var3.goodsProduced = var2.readInt();
            var3.stationsVisited = var2.readInt();
            var3.currentCampaignMissionIndex = var2.readInt();
            var3.freelanceMission = readMission(var2);
            var3.storyMission = readMission(var2);
            var3.jumpGateUses = var2.readInt();
            var3.cargoSalvaged = var2.readInt();
            var3.unused__ = var2.readInt();
            var3.pirateKills = var2.readInt();
            var3.wormholeStation = var2.readInt();
            var3.wormholeSystem = var2.readInt();
            var3.lastVisitedNonVoidOrbit = var2.readInt();
            var3.wormHoleTick = var2.readInt();

            for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
                var3.minedOreTypes[var4] = var2.readBoolean();
            }

            for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
                var3.minedCoreTypes[var4] = var2.readBoolean();
            }

            var3.missionGoodsCarried = var2.readInt();
            var3.minedOre = var2.readInt();
            var3.minedCores = var2.readInt();
            var3.boughtBooze = var2.readInt();

            for(var4 = 0; var4 < Status.drinkTypesPossesed.length; ++var4) {
                var3.drinkTypesPossesed[var4] = var2.readBoolean();
            }

            var3.destroyedJunk = var2.readInt();

            for(var4 = 0; var4 < Status.systemsVisited.length; ++var4) {
                var3.systemsVisited[var4] = var2.readBoolean();
            }

            var3.passengersCarried = var2.readInt();
            var3.invisibleTime = var2.readLong();
            var3.bombsUsed = var2.readInt();
            var3.alienJunkSalvaged = var2.readInt();
            var3.barInteractions = var2.readInt();
            var3.commandedWingmen = var2.readInt();
            var2.readInt();
            var3.asteroidsDestroyed = var2.readInt();
            var3.maxFreeCargo = var2.readInt();
            var3.missionsRejected = var2.readInt();
            var3.askedToRepeate = var2.readInt();
            var3.acceptedNotAskingDifficulty = var2.readInt();
            var3.acceptedNotAskingLocation = var2.readInt();
            var4 = var2.readInt();
            var3.achievements = new int[var4];

            for(var4 = 0; var4 < var3.achievements.length; ++var4) {
                var3.achievements[var4] = var2.readInt();
            }

            var3.egoShip = Globals.getShips()[var2.readInt()].cloneBase();
            var3.egoShip.setRace(var2.readInt());
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
                        var5[var6] = Globals.getItems()[var7].getCopyInAmmount(var8, Globals.getItems()[var7].getMaxPrice());
                        var5[var6].setUnsaleable(var2.readBoolean());
                    }
                }

                var3.egoShip.replaceEquipment(var5);
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
                    final boolean var10 = var2.readBoolean();
                    var19[var8] = Globals.getItems()[var4].getCopyInAmmount(var17, Globals.getItems()[var4].getMaxPrice());
                    var19[var8].setPrice(var9);
                    var19[var8].setUnsaleable(var10);
                }

                var3.egoShip.refreshCargoSpaceUnsafe(var19);
            }

            final Station[] var21 = new Station[3];

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
                            final boolean var13 = var2.readBoolean();
                            var26[var7] = Globals.getItems()[var9].getCopyInAmmount(var11, Globals.getItems()[var9].getMaxPrice());
                            var26[var7].setPrice(var12);
                            var26[var7].setUnsaleable(var13);
                        }

                        var18.setShopItems(var26);
                    }

                    var7 = var2.readInt();
                    Ship[] var25 = null;
                    if (var7 > 0) {
                        var25 = new Ship[var7];

                        for(var11 = 0; var11 < var7; ++var11) {
                            var12 = var2.readInt();
                            var25[var11] = Globals.getShips()[var12].cloneBase();
                            var25[var11].setRace(var2.readInt());
                        }

                        var18.setShopShips(var25);
                    }

                    var11 = var2.readInt();
                    Agent[] var31 = null;
                    if (var11 > 0) {
                        var31 = new Agent[var11];

                        for(int var32 = 0; var32 < var11; ++var32) {
                            var31[var32] = readAgent(var2);
                        }

                        var18.setBarAgents(var31);
                    }
                }

                if (var4 == var21.length) {
                    var3.currentStation = var18;
                } else {
                    var21[var4] = var18;
                }
            }

            var3.lastVisitedStations = var21;
            final int[] var23 = Status.getStanding().getStanding();

            for(var17 = 0; var17 < var23.length; ++var17) {
                var23[var17] = var2.readInt();
            }

            var2.readInt();
            var2.readInt();
            var3.standing = new Standing();
            var3.standing.setStanding(var23);
            final BluePrint[] var20 = new BluePrint[Status.getBluePrints().length];

            for(var9 = 0; var9 < var20.length; ++var9) {
                var20[var9] = new BluePrint(Status.getBluePrints()[var9].getIndex());
                final BluePrint var27 = var20[var9];

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

            var3.blueprints = var20;
            var9 = var2.readInt();
            if (var9 <= 0) {
                var3.waitingGoods = null;
            } else {
                final ProducedGood[] var28 = new ProducedGood[var9];

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

                var3.waitingGoods = var28;
            }

            int var30;
            if ((var30 = var2.readInt()) <= 0) {
                var3.wingmenNames = null;
            } else {
                final String[] var29 = new String[var30];

                for(var9 = 0; var9 < var30; ++var9) {
                    var29[var9] = var2.readUTF();
                }

                var3.wingmenNames = var29;
                var3.wingmanRace = var2.readInt();
                var3.wingmenTimeRemaining = var2.readInt();
                var9 = var2.readInt();
                var3.wingmanFace = new byte[var9];

                for(var11 = 0; var11 < var3.wingmanFace.length; ++var11) {
                    var3.wingmanFace[var11] = var2.readByte();
                }
            }

            var3.passengerCount = var2.readInt();
            var3.visibleSystems = new boolean[Status.visibleSystems.length];

            for(var7 = 0; var7 < Status.visibleSystems.length; ++var7) {
                var3.visibleSystems[var7] = var2.readBoolean();
            }

            var3.highestItemPrices = new int[Status.highestItemPrices.length];

            for(var7 = 0; var7 < Status.highestItemPrices.length; ++var7) {
                var3.highestItemPrices[var7] = var2.readInt();
            }

            var3.lowestItemPrices = new int[Status.lowestItemPrices.length];

            for(var7 = 0; var7 < Status.lowestItemPrices.length; ++var7) {
                var3.lowestItemPrices[var7] = var2.readInt();
            }

            var3.highestItemPriceSystems = new byte[Status.highestItemPriceSystems.length];

            for(var7 = 0; var7 < Status.highestItemPriceSystems.length; ++var7) {
                var3.highestItemPriceSystems[var7] = var2.readByte();
            }

            var3.lowestItemPriceSystems = new byte[Status.lowestItemPriceSystems.length];

            for(var7 = 0; var7 < Status.lowestItemPriceSystems.length; ++var7) {
                var3.lowestItemPriceSystems[var7] = var2.readByte();
            }

            var3.specialAgents = new Agent[Status.getSpecialAgents().length];

            for(var7 = 0; var7 < var3.specialAgents.length; ++var7) {
                var3.specialAgents[var7] = readAgent(var2);
            }

            var3.shopHelpShown = var2.readBoolean();
            var3.shipHelpShown = var2.readBoolean();
            var3.actionsHelpShown = var2.readBoolean();
            var3.bluePrintsHelpShown = var2.readBoolean();
            var3.bluePrintInfoHelpShown = var2.readBoolean();
            var3.barHelpShown = var2.readBoolean();
            var3.galaxyMapHelpShown = var2.readBoolean();
            var3.systemMapHelpShown = var2.readBoolean();
            var3.unused12121_ = var2.readBoolean();
            var3.miningHelpShown = var2.readBoolean();
            var3.asteroidHelpShown = var2.readBoolean();
            var3.missionsHelpShown = var2.readBoolean();
            var3.cargoFullHelpShown = var2.readBoolean();
            var3.wingmenHelpShown = var2.readBoolean();
            var3.actionMenuHelpShown = var2.readBoolean();
            var3.boosterHelpShown = var2.readBoolean();
            var3.statusHelpShown = var2.readBoolean();
            var3.medalsHelpShown = var2.readBoolean();
            var3.fanWingmenNoticeShown = var2.readBoolean();
            var3.voidxNoticeShown = var2.readBoolean();
            var3.reputationHelpShown = var2.readBoolean();
            var3.buyingHelpShown = var2.readBoolean();
            var3.itemMountingHelpShown = var2.readBoolean();
            var3.itemMounting2HelpShown = var2.readBoolean();
            var3.interplanetHelpShown = var2.readBoolean();
            var3.loadingsCount = var2.readLong();
            var3.timeSpentLoading = var2.readLong();

            try {
                var3.jumpDriveHelpShown = var2.readBoolean();
                var3.cloakHelpShown = var2.readBoolean();
            } catch (final Exception var14) {
            }

            var2.close();
            var16.close();
        } catch (final Exception var15) {
            System.err.println("Error in readRecordFromByteArray!");
        }

        return var3;
    }

    public final GameRecord recordStoreRead(final int var1) {
        this.agent = null;
        this.mission = null;
        GameRecord var2 = null;

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, false);
            this.recordStore.closeRecordStore();
        } catch (final RecordStoreException var4) {
        }

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, false);
            final byte[] var5 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
            var2 = readRecordAsByteArray(var5);
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final Exception var3) {
            var2 = null;
        }

        return var2;
    }

    public final void saveGame(final int var1) {
        this.agent = null;
        this.mission = null;

        try {
            RecordStore.deleteRecordStore("GOF2_" + var1);
        } catch (final RecordStoreException var10) {
        }

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_" + var1, true);
        } catch (final RecordStoreException var9) {
        }

        try {
            final byte[] var2 = recordStoreToByteArray();
            this.recordStore.addRecord(var2, 0, var2.length);
            this.recordStore.closeRecordStore();
            this.recordStore = null;
            final int var12 = var1;
            final RecordHandler var11 = this;

            try {
                RecordStore.deleteRecordStore("GOF2_Preview_" + var12);
            } catch (final RecordStoreException var7) {
            }

            try {
                var11.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var12, true);
            } catch (final RecordStoreException var6) {
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
            } catch (final IOException var4) {
            } catch (final RecordStoreException var5) {
                return;
            }
        } catch (final Exception var8) {
        }

    }

    private byte[] recordStoreToByteArray() {
        ByteArrayOutputStream var1 = null;
        DataOutputStream var2 = null;

        try {
            var1 = new ByteArrayOutputStream();
            var2 = new DataOutputStream(var1);
            final boolean[] var3 = Galaxy.getVisitedStations();

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
            writeMission(var2, Status.getFreelanceMission());
            writeMission(var2, Status.getCampaignMission());
            var2.writeInt(Status.getJumpgateUsed());
            var2.writeInt(Status.getCargoSalvaged());
            var2.writeInt(Status.setUnusedVar());
            var2.writeInt(Status.getPirateKills());
            var2.writeInt(Status.wormholeStation);
            var2.writeInt(Status.wormholeSystem);
            var2.writeInt(Status.lastVisitedNonVoidOrbit);
            var2.writeInt(Status.wormHoleTick);

            for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
                var2.writeBoolean(Status.minedOreTypes[var4]);
            }

            for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
                var2.writeBoolean(Status.minedCoreTypes[var4]);
            }

            var2.writeInt(Status.missionGoodsCarried);
            var2.writeInt(Status.minedOre);
            var2.writeInt(Status.minedCores);
            var2.writeInt(Status.boughtBooze);

            for(var4 = 0; var4 < Status.drinkTypesPossesed.length; ++var4) {
                var2.writeBoolean(Status.drinkTypesPossesed[var4]);
            }

            var2.writeInt(Status.destroyedJunk);

            for(var4 = 0; var4 < Status.systemsVisited.length; ++var4) {
                var2.writeBoolean(Status.systemsVisited[var4]);
            }

            var2.writeInt(Status.passengersCarried);
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
            final int[] var15 = Achievements.getMedals();
            var2.writeInt(var15.length);

            int var9;
            for(var9 = 0; var9 < var15.length; ++var9) {
                var2.writeInt(var15[var9]);
            }

            var2.writeInt(Status.getShip().getIndex());
            var2.writeInt(Status.getShip().getRace());
            Item[] var10 = Status.getShip().getEquipment();
            if (var10 == null) {
                var2.writeInt(0);
            } else {
                var2.writeInt(var10.length);

                for(var4 = 0; var4 < var10.length; ++var4) {
                    if (var10[var4] != null) {
                        var2.writeInt(var10[var4].getIndex());
                        var2.writeInt(var10[var4].getAmount());
                        var2.writeBoolean(var10[var4].setUnsaleable());
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
                    var2.writeInt(var18[var9].getSinglePrice());
                    var2.writeBoolean(var18[var9].setUnsaleable());
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
                            var2.writeInt(var18[var6].getSinglePrice());
                            var2.writeBoolean(var18[var6].setUnsaleable());
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
                            final Agent var12 = var19[var7];
                            writeAgent(var2, var12);
                        }
                    }
                }
            }

            final int[] var11 = Status.getStanding().getStanding();

            for(int var13 = 0; var13 < var11.length; ++var13) {
                var2.writeInt(var11[var13]);
            }

            var2.writeInt(1);
            var2.writeInt(1);
            final BluePrint[] var16 = Status.getBluePrints();

            for(var6 = 0; var6 < var16.length; ++var6) {
                final BluePrint var20 = var16[var6];

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

                var2.writeInt(Status.wingmanRace);
                var2.writeInt(Status.wingmenTimeRemaining);
                var2.writeInt(Status.wingmanFace.length);

                for(var4 = 0; var4 < Status.wingmanFace.length; ++var4) {
                    var2.writeByte(Status.wingmanFace[var4]);
                }
            }

            var2.writeInt(Status.passengerCount);

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
                writeAgent(var2, var19[var7]);
            }

            var2.writeBoolean(GlobalStatus.shopHelpShown);
            var2.writeBoolean(GlobalStatus.shipHelpShown);
            var2.writeBoolean(GlobalStatus.actionsHelpShown);
            var2.writeBoolean(GlobalStatus.bluePrintsHelpShown);
            var2.writeBoolean(GlobalStatus.bluePrintInfoHelpShown);
            var2.writeBoolean(GlobalStatus.barHelpShown);
            var2.writeBoolean(GlobalStatus.galaxyMapHelpShown);
            var2.writeBoolean(GlobalStatus.systemMapHelpShown);
            var2.writeBoolean(GlobalStatus.somehelpShown_unused_);
            var2.writeBoolean(GlobalStatus.miningHelpShown);
            var2.writeBoolean(GlobalStatus.asteroidHelpShown);
            var2.writeBoolean(GlobalStatus.missionsHelpShown);
            var2.writeBoolean(GlobalStatus.cargoFullHelpShown);
            var2.writeBoolean(GlobalStatus.wingmenHelpShown);
            var2.writeBoolean(GlobalStatus.actionMenuHelpShown);
            var2.writeBoolean(GlobalStatus.boosterHelpShown);
            var2.writeBoolean(GlobalStatus.statusHelpShown);
            var2.writeBoolean(GlobalStatus.medalsHelpShown);
            var2.writeBoolean(GlobalStatus.fanWingmenNoticeShown);
            var2.writeBoolean(GlobalStatus.voidxNoticeShown);
            var2.writeBoolean(GlobalStatus.reputationHelpShown);
            var2.writeBoolean(GlobalStatus.buyingHelpShown);
            var2.writeBoolean(GlobalStatus.itemMountingHelpShown);
            var2.writeBoolean(GlobalStatus.itemMounting2HelpShown);
            var2.writeBoolean(GlobalStatus.interplanetHelpShown);
            var2.writeLong(Status.loadingsCount);
            var2.writeLong(Status.loadingTime);
            var2.writeBoolean(GlobalStatus.jumpDriveHelpShown);
            var2.writeBoolean(GlobalStatus.cloakHelpShown);
            var2.close();
            var1.close();
        } catch (final Exception var8) {
            var8.printStackTrace();
        }

        return var1.toByteArray();
    }

    private void writeAgent(final DataOutputStream var1, final Agent var2) throws IOException {
        var1.writeInt(var2.getCosts());
        var1.writeInt(var2.getSellSystemIndex());
        var1.writeInt(var2.getSellBlueprintIndex());
        var1.writeInt(var2.getEvent());
        var1.writeInt(var2.getMessageId());
        var1.writeInt(var2.getType());
        var1.writeInt(var2.getRace());
        var1.writeInt(var2.getSellItemIndex());
        var1.writeInt(var2.getSellItemPrice());
        var1.writeInt(var2.getSellItemQuantity());
        var1.writeInt(var2.getStationId());
        var1.writeInt(var2.getSystemId());
        var1.writeInt(var2.wingmanFriendsCount);
        var1.writeBoolean(var2.isMale());
        var1.writeBoolean(var2.getUnused0_());
        var1.writeBoolean(var2.isAccepted());
        var1.writeBoolean(var2.wasAskedForDifficulty);
        var1.writeBoolean(var2.wasAskedForLocation);
        if (var2.getFace() != null) {
            var1.writeInt(var2.getFace().length);

            for(int var3 = 0; var3 < var2.getFace().length; ++var3) {
                var1.writeByte(var2.getFace()[var3]);
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

        if (var2.wingman1Name == null) {
            var1.writeUTF("");
        } else {
            var1.writeUTF(var2.wingman1Name);
        }

        if (var2.wingman2Name == null) {
            var1.writeUTF("");
        } else {
            var1.writeUTF(var2.wingman2Name);
        }

        this.agent = var2;
        if (var2.getMission() != null && this.mission != var2.getMission()) {
            var1.writeInt(1);
            writeMission(var1, var2.getMission());
        } else {
            var1.writeInt(-1);
        }
    }

    private Agent readAgent(final DataInputStream var1) throws IOException {
        final int var2 = var1.readInt();
        final int var3 = var1.readInt();
        final int var4 = var1.readInt();
        final int var5 = var1.readInt();
        final int var6 = var1.readInt();
        final int var7 = var1.readInt();
        final int var8 = var1.readInt();
        final int var9 = var1.readInt();
        final int var10 = var1.readInt();
        final int var11 = var1.readInt();
        final int var12 = var1.readInt();
        final int var13 = var1.readInt();
        final int var14 = var1.readInt();
        final boolean var15 = var1.readBoolean();
        final boolean var16 = var1.readBoolean();
        final boolean var17 = var1.readBoolean();
        final boolean var18 = var1.readBoolean();
        final boolean var19 = var1.readBoolean();
        int var20 = var1.readInt();
        byte[] var21 = null;
        if (var20 > 0) {
            var21 = new byte[var20];

            for(var20 = 0; var20 < var21.length; ++var20) {
                var21[var20] = var1.readByte();
            }
        }

        final String var28 = var1.readUTF();
        final String var22 = var1.readUTF();
        final String var23 = var1.readUTF();
        final String var24 = var1.readUTF();
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
            var27 = readMission(var1);
        }

        Agent var29;
        (var29 = new Agent(var6, var22, var12, var13, var8, var15, var3, var4, var10)).setCosts(var2);
        var29.setEvent(var5);
        var29.setType(var7);
        var29.setSellItem(var9, var11, var10);
        var29.wingman1Name = var25;
        var29.wingman2Name = var26;
        var29.wingmanFriendsCount = var14;
        var29.setUnused0_(var16);
        var29.setAccepted(var17);
        var29.setImageParts(var21);
        var29.setMessage(var28);
        var29.setAgentsStationName(var23);
        var29.setAgentsSystemName(var24);
        var29.setMission(var27);
        var29.wasAskedForDifficulty = var18;
        var29.wasAskedForLocation = var19;
        return var29;
    }

    private void writeMission(final DataOutputStream var1, final Mission var2) throws IOException {
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
            var1.writeInt(var2.getBonus());
            var1.writeInt(var2.getReward());
            var1.writeInt(var2.getTargetStation());
            var1.writeInt(var2.getDifficulty());
            var1.writeInt(var2.getCommodityIndex());
            var1.writeInt(var2.getCommodityAmount_());
            var1.writeInt(var2.getStatusValue_());
            var1.writeBoolean(var2.isVisible());
            this.mission = var2;
            if (var2.getAgent() != null && this.agent != var2.getAgent()) {
                var1.writeInt(1);
                writeAgent(var1, var2.getAgent());
            } else {
                var1.writeInt(-1);
            }
        }
    }

    private Mission readMission(DataInputStream var1) throws IOException {
        int var2 = var1.readInt();
        if (var2 == -1) {
            return Mission.emptyMission_;
        }
        String var3;
        if ((var3 = var1.readUTF()).equals("")) {
            var3 = null;
        }

        String var4;
        if ((var4 = var1.readUTF()).equals("")) {
            var4 = null;
        }

        var1.readUTF().equals("");
        final boolean var5 = var1.readBoolean();
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
        final int var9 = var1.readInt();
        final int var10 = var1.readInt();
        final int var11 = var1.readInt();
        final int var12 = var1.readInt();
        final int var13 = var1.readInt();
        final int var14 = var1.readInt();
        final boolean var15 = var1.readBoolean();
        final int var16 = var1.readInt();
        Agent var17 = null;
        if (var16 > 0) {
            var17 = readAgent(var1);
        }

        var1 = null;
        Mission var18;
        if (var5) {
            var18 = new Mission(var2, var9, var10);
        } else {
            var18 = new Mission(var2, var3, var6, var8, var9, var10, var11);
        }

        var18.setBonus(var7);
        var18.setCommodity(var12, var13);
        var18.setTasksTreshold_(var14);
        var18.setVisible(var15);
        var18.setAgent(var17);
        var18.setTargetName(var4);
        return var18;
    }

    public final GameRecord readPreview(final int var1) {
        GameRecord var2 = null;

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var1, false);
            this.recordStore.closeRecordStore();
        } catch (final RecordStoreException var5) {
        }

        try {
            this.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var1, false);
            final byte[] var7 = this.recordStore.getRecord(this.recordStore.getNextRecordID() - 1);
            final ByteArrayInputStream var6 = new ByteArrayInputStream(var7);
            final DataInputStream var3 = new DataInputStream(var6);
            (var2 = new GameRecord()).playTime = var3.readLong();
            var2.credits = var3.readInt();
            var2.stationName = var3.readUTF();
            var3.readUTF();
            var2.currentCampaignMissionIndex = var3.readInt();
            var3.close();
            var6.close();
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final Exception var4) {
            var2 = null;
        }

        return var2;
    }
}
