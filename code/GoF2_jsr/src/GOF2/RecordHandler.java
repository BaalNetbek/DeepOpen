package GOF2;

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

        for(int i = 0; i < var1.length; ++i) {
            var1[i] = readPreview(i);
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
            final ByteArrayInputStream bais = new ByteArrayInputStream(var1);
            DataInputStream dis = new DataInputStream(bais);
            GlobalStatus.musicOn = dis.readBoolean();
            GlobalStatus.vibrationOn = dis.readBoolean();
            GlobalStatus.invertedControlsOn = dis.readBoolean();
            GlobalStatus.musicVolume = dis.readInt();
            GlobalStatus.sfxVolume = dis.readInt();
            GlobalStatus.sfxOn = dis.readBoolean();
            dis.close();
            bais.close();
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final RecordStoreException e) {
        } catch (final IOException e) {
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

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeBoolean(GlobalStatus.musicOn);
            dos.writeBoolean(GlobalStatus.vibrationOn);
            dos.writeBoolean(GlobalStatus.invertedControlsOn);
            dos.writeInt(GlobalStatus.musicVolume);
            dos.writeInt(GlobalStatus.sfxVolume);
            dos.writeBoolean(GlobalStatus.sfxOn);
            dos.close();
            baos.close();
            this.recordStore.addRecord(baos.toByteArray(), 0, baos.toByteArray().length);
            this.recordStore.closeRecordStore();
            this.recordStore = null;
        } catch (final IOException e) {
        } catch (final RecordStoreException e) {
            e.printStackTrace();
        }
    }

    private GameRecord readRecordFromByteArray(final byte[] bytes) {
        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        final DataInputStream dis = new DataInputStream(bais);
        final GameRecord record = new GameRecord();

        try {
            int var4;
            for(var4 = 0; var4 < record.visitedStations.length; ++var4) {
                record.visitedStations[var4] = dis.readBoolean();
            }

            record.credits = dis.readInt();
            record.rating = dis.readInt();
            record.playTime = dis.readLong();
            record.kills = dis.readInt();
            record.missionCount = dis.readInt();
            record.level = dis.readInt();
            record.lastWorth = dis.readInt();
            record.goodsProduced = dis.readInt();
            record.stationsVisited = dis.readInt();
            record.currentCampaignMissionIndex = dis.readInt();
            record.freelanceMission = readMission(dis);
            record.storyMission = readMission(dis);
            record.jumpGateUses = dis.readInt();
            record.cargoSalvaged = dis.readInt();
            record.unused__ = dis.readInt();
            record.pirateKills = dis.readInt();
            record.wormholeStation = dis.readInt();
            record.wormholeSystem = dis.readInt();
            record.lastVisitedNonVoidOrbit = dis.readInt();
            record.wormHoleTick = dis.readInt();

            for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
                record.minedOreTypes[var4] = dis.readBoolean();
            }

            for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
                record.minedCoreTypes[var4] = dis.readBoolean();
            }

            record.missionGoodsCarried = dis.readInt();
            record.minedOre = dis.readInt();
            record.minedCores = dis.readInt();
            record.boughtBooze = dis.readInt();

            for(var4 = 0; var4 < Status.drinkTypesPossesed.length; ++var4) {
                record.drinkTypesPossesed[var4] = dis.readBoolean();
            }

            record.destroyedJunk = dis.readInt();

            for(var4 = 0; var4 < Status.systemsVisited.length; ++var4) {
                record.systemsVisited[var4] = dis.readBoolean();
            }

            record.passengersCarried = dis.readInt();
            record.invisibleTime = dis.readLong();
            record.bombsUsed = dis.readInt();
            record.alienJunkSalvaged = dis.readInt();
            record.barInteractions = dis.readInt();
            record.commandedWingmen = dis.readInt();
            dis.readInt();
            record.asteroidsDestroyed = dis.readInt();
            record.maxFreeCargo = dis.readInt();
            record.missionsRejected = dis.readInt();
            record.askedToRepeate = dis.readInt();
            record.acceptedNotAskingDifficulty = dis.readInt();
            record.acceptedNotAskingLocation = dis.readInt();
            var4 = dis.readInt();
            record.achievements = new int[var4];

            for(var4 = 0; var4 < record.achievements.length; ++var4) {
                record.achievements[var4] = dis.readInt();
            }

            record.egoShip = Globals.getShips()[dis.readInt()].cloneBase();
            record.egoShip.setRace(dis.readInt());
            var4 = dis.readInt();
            Item[] var5 = null;
            int var6;
            int var7;
            int var8;
            if (var4 > 0) {
                var5 = new Item[var4];

                for(var6 = 0; var6 < var4; ++var6) {
                    if ((var7 = dis.readInt()) == -1) {
                        var5[var6] = null;
                    } else {
                        var8 = dis.readInt();
                        var5[var6] = Globals.getItems()[var7].getCopyInAmmount(var8, Globals.getItems()[var7].getMaxPrice());
                        var5[var6].setUnsaleable(dis.readBoolean());
                    }
                }

                record.egoShip.replaceEquipment(var5);
            }

            var6 = dis.readInt();
            Item[] var19 = null;
            int var9;
            int var17;
            if (var6 > 0) {
                var19 = new Item[var6];

                for(var8 = 0; var8 < var6; ++var8) {
                    var4 = dis.readInt();
                    var17 = dis.readInt();
                    var9 = dis.readInt();
                    final boolean var10 = dis.readBoolean();
                    var19[var8] = Globals.getItems()[var4].getCopyInAmmount(var17, Globals.getItems()[var4].getMaxPrice());
                    var19[var8].setPrice(var9);
                    var19[var8].setUnsaleable(var10);
                }

                record.egoShip.refreshCargoSpaceUnsafe(var19);
            }

            final Station[] var21 = new Station[3];

            int var11;
            int var12;
            for(var4 = 0; var4 < var21.length + 1; ++var4) {
                var5 = null;
                Station var18;
                short var22;
                if ((var22 = dis.readShort()) == -1) {
                    var18 = null;
                } else {
                    var18 = Galaxy.getStation(var22);
                    var6 = dis.readInt();
                    Item[] var26 = null;
                    if (var6 > 0) {
                        var26 = new Item[var6];

                        for(var7 = 0; var7 < var6; ++var7) {
                            var9 = dis.readInt();
                            var11 = dis.readInt();
                            var12 = dis.readInt();
                            final boolean var13 = dis.readBoolean();
                            var26[var7] = Globals.getItems()[var9].getCopyInAmmount(var11, Globals.getItems()[var9].getMaxPrice());
                            var26[var7].setPrice(var12);
                            var26[var7].setUnsaleable(var13);
                        }

                        var18.setShopItems(var26);
                    }

                    var7 = dis.readInt();
                    Ship[] var25 = null;
                    if (var7 > 0) {
                        var25 = new Ship[var7];

                        for(var11 = 0; var11 < var7; ++var11) {
                            var12 = dis.readInt();
                            var25[var11] = Globals.getShips()[var12].cloneBase();
                            var25[var11].setRace(dis.readInt());
                        }

                        var18.setShopShips(var25);
                    }

                    var11 = dis.readInt();
                    Agent[] var31 = null;
                    if (var11 > 0) {
                        var31 = new Agent[var11];

                        for(int i = 0; i < var11; ++i) {
                            var31[i] = readAgent(dis);
                        }

                        var18.setBarAgents(var31);
                    }
                }

                if (var4 == var21.length) {
                    record.currentStation = var18;
                } else {
                    var21[var4] = var18;
                }
            }

            record.lastVisitedStations = var21;
            final int[] var23 = Status.getStanding().getStanding();

            for(var17 = 0; var17 < var23.length; ++var17) {
                var23[var17] = dis.readInt();
            }

            dis.readInt();
            dis.readInt();
            record.standing = new Standing();
            record.standing.setStanding(var23);
            final BluePrint[] var20 = new BluePrint[Status.getBluePrints().length];

            for(var9 = 0; var9 < var20.length; ++var9) {
                var20[var9] = new BluePrint(Status.getBluePrints()[var9].getIndex());
                final BluePrint var27 = var20[var9];

                for(var7 = 0; var7 < var27.missingComponentsTons.length; ++var7) {
                    var27.missingComponentsTons[var7] = dis.readInt();
                }

                var27.investedCredits = dis.readInt();
                var27.unlocked = dis.readBoolean();
                var27.timesProduced = dis.readInt();
                var27.productionStationId = dis.readInt();
                String var24;
                if ((var24 = dis.readUTF()).equals("")) {
                    var24 = null;
                }

                var27.productionStationName = var24;
            }

            record.blueprints = var20;
            var9 = dis.readInt();
            if (var9 <= 0) {
                record.waitingGoods = null;
            } else {
                final PendingProduct[] var28 = new PendingProduct[var9];

                for(var7 = 0; var7 < var28.length; ++var7) {
                    var9 = dis.readInt();
                    var11 = dis.readInt();
                    var12 = dis.readInt();
                    String var33;
                    if ((var33 = dis.readUTF()).equals("")) {
                        var33 = null;
                    }

                    var28[var7] = new PendingProduct(var9, var33, var12, var11);
                }

                record.waitingGoods = var28;
            }

            int var30;
            if ((var30 = dis.readInt()) <= 0) {
                record.wingmenNames = null;
            } else {
                final String[] var29 = new String[var30];

                for(var9 = 0; var9 < var30; ++var9) {
                    var29[var9] = dis.readUTF();
                }

                record.wingmenNames = var29;
                record.wingmanRace = dis.readInt();
                record.wingmenTimeRemaining = dis.readInt();
                var9 = dis.readInt();
                record.wingmanFace = new byte[var9];

                for(var11 = 0; var11 < record.wingmanFace.length; ++var11) {
                    record.wingmanFace[var11] = dis.readByte();
                }
            }

            record.passengerCount = dis.readInt();
            record.visibleSystems = new boolean[Status.visibleSystems.length];

            for(var7 = 0; var7 < Status.visibleSystems.length; ++var7) {
                record.visibleSystems[var7] = dis.readBoolean();
            }

            record.highestItemPrices = new int[Status.highestItemPrices.length];

            for(var7 = 0; var7 < Status.highestItemPrices.length; ++var7) {
                record.highestItemPrices[var7] = dis.readInt();
            }

            record.lowestItemPrices = new int[Status.lowestItemPrices.length];

            for(var7 = 0; var7 < Status.lowestItemPrices.length; ++var7) {
                record.lowestItemPrices[var7] = dis.readInt();
            }

            record.highestItemPriceSystems = new byte[Status.highestItemPriceSystems.length];

            for(var7 = 0; var7 < Status.highestItemPriceSystems.length; ++var7) {
                record.highestItemPriceSystems[var7] = dis.readByte();
            }

            record.lowestItemPriceSystems = new byte[Status.lowestItemPriceSystems.length];

            for(var7 = 0; var7 < Status.lowestItemPriceSystems.length; ++var7) {
                record.lowestItemPriceSystems[var7] = dis.readByte();
            }

            record.specialAgents = new Agent[Status.getSpecialAgents().length];

            for(var7 = 0; var7 < record.specialAgents.length; ++var7) {
                record.specialAgents[var7] = readAgent(dis);
            }

            record.shopHelpShown = dis.readBoolean();
            record.shipHelpShown = dis.readBoolean();
            record.actionsHelpShown = dis.readBoolean();
            record.bluePrintsHelpShown = dis.readBoolean();
            record.bluePrintInfoHelpShown = dis.readBoolean();
            record.barHelpShown = dis.readBoolean();
            record.galaxyMapHelpShown = dis.readBoolean();
            record.systemMapHelpShown = dis.readBoolean();
            record.unused12121_ = dis.readBoolean();
            record.miningHelpShown = dis.readBoolean();
            record.asteroidHelpShown = dis.readBoolean();
            record.missionsHelpShown = dis.readBoolean();
            record.cargoFullHelpShown = dis.readBoolean();
            record.wingmenHelpShown = dis.readBoolean();
            record.actionMenuHelpShown = dis.readBoolean();
            record.boosterHelpShown = dis.readBoolean();
            record.statusHelpShown = dis.readBoolean();
            record.medalsHelpShown = dis.readBoolean();
            record.fanWingmenNoticeShown = dis.readBoolean();
            record.voidxNoticeShown = dis.readBoolean();
            record.reputationHelpShown = dis.readBoolean();
            record.buyingHelpShown = dis.readBoolean();
            record.itemMountingHelpShown = dis.readBoolean();
            record.itemMounting2HelpShown = dis.readBoolean();
            record.interplanetHelpShown = dis.readBoolean();
            record.loadingsCount = dis.readLong();
            record.timeSpentLoading = dis.readLong();

            try {
                record.jumpDriveHelpShown = dis.readBoolean();
                record.cloakHelpShown = dis.readBoolean();
            } catch (final Exception var14) {
            }

            dis.close();
            bais.close();
        } catch (final Exception var15) {
            System.err.println("Error in readRecordFromByteArray!");
        }

        return record;
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
            var2 = readRecordFromByteArray(var5);
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
            try {
                RecordStore.deleteRecordStore("GOF2_Preview_" + var12);
            } catch (final RecordStoreException var7) {
            }

            try {
                this.recordStore = RecordStore.openRecordStore("GOF2_Preview_" + var12, true);
            } catch (final RecordStoreException var6) {
            }

            ByteArrayOutputStream var3 = null;
            DataOutputStream var13 = null;

            try {
                var3 = new ByteArrayOutputStream();
                var13 = new DataOutputStream(var3);
                var13.writeLong(Status.getPlayingTime());
                var13.writeInt(Status.getCredits());
                var13.writeUTF(Status.getStation().getName());
                var13.writeUTF(Status.getSystem().getName());
                var13.writeInt(Status.getCurrentCampaignMission());
                var13.close();
                var3.close();
                this.recordStore.addRecord(var3.toByteArray(), 0, var3.toByteArray().length);
                this.recordStore.closeRecordStore();
                this.recordStore = null;
            } catch (final IOException var4) {
            } catch (final RecordStoreException var5) {
                return;
            }
        } catch (final Exception var8) {
        }

    }

    private byte[] recordStoreToByteArray() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            final boolean[] var3 = Galaxy.getVisitedStations();

            int var4;
            for(var4 = 0; var4 < var3.length; ++var4) {
                dos.writeBoolean(var3[var4]);
            }

            dos.writeInt(Status.getCredits());
            dos.writeInt(Status.getRating());
            dos.writeLong(Status.getPlayingTime());
            dos.writeInt(Status.getKills());
            dos.writeInt(Status.getMissionCount());
            dos.writeInt(Status.getLevel());
            dos.writeInt(Status.getLastXP());
            dos.writeInt(Status.getGoodsProduced());
            dos.writeInt(Status.getStationsVisited());
            dos.writeInt(Status.getCurrentCampaignMission());
            writeMission(dos, Status.getFreelanceMission());
            writeMission(dos, Status.getCampaignMission());
            dos.writeInt(Status.getJumpgateUsed());
            dos.writeInt(Status.getCargoSalvaged());
            dos.writeInt(Status.setUnusedVar());
            dos.writeInt(Status.getPirateKills());
            dos.writeInt(Status.wormholeStation);
            dos.writeInt(Status.wormholeSystem);
            dos.writeInt(Status.lastVisitedNonVoidOrbit);
            dos.writeInt(Status.wormHoleTick);

            for(var4 = 0; var4 < Status.minedOreTypes.length; ++var4) {
                dos.writeBoolean(Status.minedOreTypes[var4]);
            }

            for(var4 = 0; var4 < Status.minedCoreTypes.length; ++var4) {
                dos.writeBoolean(Status.minedCoreTypes[var4]);
            }

            dos.writeInt(Status.missionGoodsCarried);
            dos.writeInt(Status.minedOre);
            dos.writeInt(Status.minedCores);
            dos.writeInt(Status.boughtBooze);

            for(var4 = 0; var4 < Status.drinkTypesPossesed.length; ++var4) {
                dos.writeBoolean(Status.drinkTypesPossesed[var4]);
            }

            dos.writeInt(Status.destroyedJunk);

            for(var4 = 0; var4 < Status.systemsVisited.length; ++var4) {
                dos.writeBoolean(Status.systemsVisited[var4]);
            }

            dos.writeInt(Status.passengersCarried);
            dos.writeLong(Status.invisibleTime);
            dos.writeInt(Status.bombsUsed);
            dos.writeInt(Status.alienJunkSalvaged);
            dos.writeInt(Status.barInteractions);
            dos.writeInt(Status.commandedWingmen);
            dos.writeInt(1);
            dos.writeInt(Status.asteroidsDestroyed);
            dos.writeInt(Status.maxFreeCargo);
            dos.writeInt(Status.missionsRejected);
            dos.writeInt(Status.askedToRepeate);
            dos.writeInt(Status.acceptedNotAskingDifficulty);
            dos.writeInt(Status.acceptedNotAskingLocation);
            final int[] medals = Achievements.getMedals();
            dos.writeInt(medals.length);

            int var9;
            for(var9 = 0; var9 < medals.length; ++var9) {
                dos.writeInt(medals[var9]);
            }

            dos.writeInt(Status.getShip().getIndex());
            dos.writeInt(Status.getShip().getRace());
            Item[] var10 = Status.getShip().getEquipment();
            if (var10 == null) {
                dos.writeInt(0);
            } else {
                dos.writeInt(var10.length);

                for(var4 = 0; var4 < var10.length; ++var4) {
                    if (var10[var4] != null) {
                        dos.writeInt(var10[var4].getIndex());
                        dos.writeInt(var10[var4].getAmount());
                        dos.writeBoolean(var10[var4].setUnsaleable());
                    } else {
                        dos.writeInt(-1);
                    }
                }
            }

            Item[] var18;
            if ((var18 = Status.getShip().getCargo()) == null) {
                dos.writeInt(0);
            } else {
                dos.writeInt(var18.length);

                for(var9 = 0; var9 < var18.length; ++var9) {
                    dos.writeInt(var18[var9].getIndex());
                    dos.writeInt(var18[var9].getAmount());
                    dos.writeInt(var18[var9].getSinglePrice());
                    dos.writeBoolean(var18[var9].setUnsaleable());
                }
            }

            int var6;
            int var7;
            Agent[] var19;
            for(var9 = 0; var9 < Status.getStationStack().length + 1; ++var9) {
                Station var5 = null;
                if (var9 == Status.getStationStack().length) {
                    var5 = Status.getStation();
                } else {
                    var5 = Status.getStationStack()[var9];
                }

                if (var5 == null) {
                    dos.writeShort(-1);
                } else {
                    dos.writeShort(var5.getIndex());
                    if ((var18 = var5.getShopItems()) == null) {
                        dos.writeInt(0);
                    } else {
                        dos.writeInt(var18.length);

                        for(var6 = 0; var6 < var18.length; ++var6) {
                            dos.writeInt(var18[var6].getIndex());
                            dos.writeInt(var18[var6].getAmount());
                            dos.writeInt(var18[var6].getSinglePrice());
                            dos.writeBoolean(var18[var6].setUnsaleable());
                        }
                    }

                    Ship[] var14;
                    if ((var14 = var5.getShopShips()) == null) {
                        dos.writeInt(0);
                    } else {
                        dos.writeInt(var14.length);

                        for(var4 = 0; var4 < var14.length; ++var4) {
                            dos.writeInt(var14[var4].getIndex());
                            dos.writeInt(var14[var4].getRace());
                        }
                    }

                    if ((var19 = var5.getBarAgents()) == null) {
                        dos.writeInt(0);
                    } else {
                        dos.writeInt(var19.length);

                        for(var7 = 0; var7 < var19.length; ++var7) {
                            final Agent var12 = var19[var7];
                            writeAgent(dos, var12);
                        }
                    }
                }
            }

            final int[] var11 = Status.getStanding().getStanding();

            for(int i = 0; i < var11.length; ++i) {
                dos.writeInt(var11[i]);
            }

            dos.writeInt(1);
            dos.writeInt(1);
            final BluePrint[] var16 = Status.getBluePrints();

            for(var6 = 0; var6 < var16.length; ++var6) {
                final BluePrint var20 = var16[var6];

                for(var7 = 0; var7 < var20.missingComponentsTons.length; ++var7) {
                    dos.writeInt(var20.missingComponentsTons[var7]);
                }

                dos.writeInt(var20.investedCredits);
                dos.writeBoolean(var20.unlocked);
                dos.writeInt(var20.timesProduced);
                dos.writeInt(var20.productionStationId);
                if (var20.productionStationName == null) {
                    dos.writeUTF("");
                } else {
                    dos.writeUTF(var20.productionStationName);
                }
            }

            PendingProduct[] var17;
            if ((var17 = Status.getPendingProducts()) == null) {
                dos.writeInt(-1);
            } else {
                var4 = 0;

                for(var7 = 0; var7 < var17.length; ++var7) {
                    if (var17[var7] != null) {
                        ++var4;
                    }
                }

                if (var4 == 0) {
                    dos.writeInt(-1);
                } else {
                    dos.writeInt(var4);

                    for(var7 = 0; var7 < var17.length; ++var7) {
                        if (var17[var7] != null) {
                            dos.writeInt(var17[var7].index);
                            dos.writeInt(var17[var7].producedQuantity);
                            dos.writeInt(var17[var7].stationId);
                            if (var17[var7].stationName == null) {
                                dos.writeUTF("");
                            } else {
                                dos.writeUTF(var17[var7].stationName);
                            }
                        }
                    }
                }
            }

            if (Status.wingmenNames == null) {
                dos.writeInt(-1);
            } else {
                dos.writeInt(Status.wingmenNames.length);

                for(var4 = 0; var4 < Status.wingmenNames.length; ++var4) {
                    dos.writeUTF(Status.wingmenNames[var4]);
                }

                dos.writeInt(Status.wingmanRace);
                dos.writeInt(Status.wingmenTimeRemaining);
                dos.writeInt(Status.wingmanFace.length);

                for(var4 = 0; var4 < Status.wingmanFace.length; ++var4) {
                    dos.writeByte(Status.wingmanFace[var4]);
                }
            }

            dos.writeInt(Status.passengerCount);

            for(var4 = 0; var4 < Status.visibleSystems.length; ++var4) {
                dos.writeBoolean(Status.visibleSystems[var4]);
            }

            for(var4 = 0; var4 < Status.highestItemPrices.length; ++var4) {
                dos.writeInt(Status.highestItemPrices[var4]);
            }

            for(var4 = 0; var4 < Status.lowestItemPrices.length; ++var4) {
                dos.writeInt(Status.lowestItemPrices[var4]);
            }

            for(var4 = 0; var4 < Status.highestItemPriceSystems.length; ++var4) {
                dos.writeByte(Status.highestItemPriceSystems[var4]);
            }

            for(var4 = 0; var4 < Status.lowestItemPriceSystems.length; ++var4) {
                dos.writeByte(Status.lowestItemPriceSystems[var4]);
            }

            var19 = Status.getSpecialAgents();

            for(var7 = 0; var7 < var19.length; ++var7) {
                writeAgent(dos, var19[var7]);
            }

            dos.writeBoolean(GlobalStatus.shopHelpShown);
            dos.writeBoolean(GlobalStatus.shipHelpShown);
            dos.writeBoolean(GlobalStatus.actionsHelpShown);
            dos.writeBoolean(GlobalStatus.bluePrintsHelpShown);
            dos.writeBoolean(GlobalStatus.bluePrintInfoHelpShown);
            dos.writeBoolean(GlobalStatus.barHelpShown);
            dos.writeBoolean(GlobalStatus.galaxyMapHelpShown);
            dos.writeBoolean(GlobalStatus.systemMapHelpShown);
            dos.writeBoolean(GlobalStatus.somehelpShown_unused_);
            dos.writeBoolean(GlobalStatus.miningHelpShown);
            dos.writeBoolean(GlobalStatus.asteroidHelpShown);
            dos.writeBoolean(GlobalStatus.missionsHelpShown);
            dos.writeBoolean(GlobalStatus.cargoFullHelpShown);
            dos.writeBoolean(GlobalStatus.wingmenHelpShown);
            dos.writeBoolean(GlobalStatus.actionMenuHelpShown);
            dos.writeBoolean(GlobalStatus.boosterHelpShown);
            dos.writeBoolean(GlobalStatus.statusHelpShown);
            dos.writeBoolean(GlobalStatus.medalsHelpShown);
            dos.writeBoolean(GlobalStatus.fanWingmenNoticeShown);
            dos.writeBoolean(GlobalStatus.voidxNoticeShown);
            dos.writeBoolean(GlobalStatus.reputationHelpShown);
            dos.writeBoolean(GlobalStatus.buyingHelpShown);
            dos.writeBoolean(GlobalStatus.itemMountingHelpShown);
            dos.writeBoolean(GlobalStatus.itemMounting2HelpShown);
            dos.writeBoolean(GlobalStatus.interplanetHelpShown);
            dos.writeLong(Status.loadingsCount);
            dos.writeLong(Status.loadingTime);
            dos.writeBoolean(GlobalStatus.jumpDriveHelpShown);
            dos.writeBoolean(GlobalStatus.cloakHelpShown);
            dos.close();
            baos.close();
        } catch (final Exception var8) {
            var8.printStackTrace();
        }

        return baos.toByteArray();
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
        var1.writeBoolean(var2.hasReward());
        var1.writeBoolean(var2.hasAcceptedOffer());
        var1.writeBoolean(var2.wasAskedForDifficulty);
        var1.writeBoolean(var2.wasAskedForLocation);
        if (var2.getImageParts() != null) {
            var1.writeInt(var2.getImageParts().length);

            for(int i = 0; i < var2.getImageParts().length; ++i) {
                var1.writeByte(var2.getImageParts()[i]);
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

        Mission mission = null;
        if (var1.readInt() > 0) {
            mission = readMission(var1);
        }

        Agent agent = new Agent(var6, var22, var12, var13, var8, var15, var3, var4, var10);
        agent.setCosts(var2);
        agent.setEvent(var5);
        agent.setOffer(var7);
        agent.setSellItemData(var9, var11, var10);
        agent.wingman1Name = var25;
        agent.wingman2Name = var26;
        agent.wingmanFriendsCount = var14;
        agent.giveRewardAtNextChat(var16);
        agent.setOfferAccepted(var17);
        agent.setImageParts(var21);
        agent.setMessage(var28);
        agent.setStationName(var23);
        agent.setSystemName(var24);
        agent.setMission(mission);
        agent.wasAskedForDifficulty = var18;
        agent.wasAskedForLocation = var19;
        return agent;
    }

    private void writeMission(final DataOutputStream dos, final Mission mission) throws IOException {
        dos.writeInt(mission.getType());
        if (!mission.isEmpty()) {
            if (mission.getClientName() == null) {
                dos.writeUTF("");
            } else {
                dos.writeUTF(mission.getClientName());
            }

            if (mission.getTargetName() == null) {
                dos.writeUTF("");
            } else {
                dos.writeUTF(mission.getTargetName());
            }

            if (mission.getTargetStationName() == null) {
                dos.writeUTF("");
            } else {
                dos.writeUTF(mission.getTargetStationName());
            }

            dos.writeBoolean(mission.isCampaignMission());
            if (mission.getClientImage() != null) {
                dos.writeInt(mission.getClientImage().length);

                for(int i = 0; i < mission.getClientImage().length; ++i) {
                    dos.writeByte(mission.getClientImage()[i]);
                }
            } else {
                dos.writeInt(-1);
            }

            dos.writeInt(mission.getClientRace());
            dos.writeInt(mission.getBonus());
            dos.writeInt(mission.getReward());
            dos.writeInt(mission.getTargetStation());
            dos.writeInt(mission.getDifficulty());
            dos.writeInt(mission.getCommodityIndex());
            dos.writeInt(mission.getCommodityAmount_());
            dos.writeInt(mission.getStatusValue_());
            dos.writeBoolean(mission.isVisible());
            this.mission = mission;
            if (mission.getAgent() != null && this.agent != mission.getAgent()) {
                dos.writeInt(1);
                writeAgent(dos, mission.getAgent());
            } else {
                dos.writeInt(-1);
            }
        }
    }

    private Mission readMission(DataInputStream dis) throws IOException {
        int var2 = dis.readInt();
        if (var2 == -1) {
            return Mission.emptyMission_;
        }
        String var3;
        if ((var3 = dis.readUTF()).equals("")) {
            var3 = null;
        }

        String var4;
        if ((var4 = dis.readUTF()).equals("")) {
            var4 = null;
        }

        dis.readUTF().equals("");
        final boolean var5 = dis.readBoolean();
        byte[] var6 = null;
        int var7;
        int var8;
        if ((var7 = dis.readInt()) > 0) {
            var6 = new byte[var7];

            for(var8 = 0; var8 < var7; ++var8) {
                var6[var8] = dis.readByte();
            }
        }

        var8 = dis.readInt();
        var7 = dis.readInt();
        final int var9 = dis.readInt();
        final int var10 = dis.readInt();
        final int var11 = dis.readInt();
        final int var12 = dis.readInt();
        final int var13 = dis.readInt();
        final int var14 = dis.readInt();
        final boolean var15 = dis.readBoolean();
        final int var16 = dis.readInt();
        Agent var17 = null;
        if (var16 > 0) {
            var17 = readAgent(dis);
        }

        dis = null;
        Mission var18;
        if (var5) {
            var18 = new Mission(var2, var9, var10);
        } else {
            var18 = new Mission(var2, var3, var6, var8, var9, var10, var11);
        }

        var18.setBonus(var7);
        var18.setCommodity(var12, var13);
        var18.setStatusValue(var14);
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
