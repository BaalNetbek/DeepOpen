package GOF2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import AE.GlobalStatus;
import AE.Math.AEMath;

public final class FileRead {

    public static final int RESOURCE_ID = 0;
    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_Z = 3;
    public static final int ROTATION_X = 4;
    public static final int ROTATION_Y = 5;
    public static final int ROTATION_Z = 6;
    public static final int SCALE_X = 7;
    public static final int SCALE_Y = 8;
    public static final int SCALE_Z = 9;
	private static Class varClass;

    public final Station loadStation(final int id) {
        return loadStationsBinary(new short[]{(short)id})[0];
    }

    private static Station[] loadStationsBinary(final short[] ids) {
        Station[] stations = null;

        try {
            final InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stations.bin");
            final DataInputStream dis = new DataInputStream(var2);
            stations = new Station[ids.length];
            int loaded = 0;

            for(int i = 0; i < Globals.STATIONS_COUNT; ++i) {
                final String name = dis.readUTF();
                final int staionId = dis.readInt();
                final int systemId = dis.readInt();
                final int tecLevel = dis.readInt();
                final int planetType = dis.readInt();

                for(int j = 0; j < ids.length; ++j) {
                    if (ids[j] == i) {
                        stations[loaded] = new Station(name, staionId, systemId, tecLevel, planetType);
                        loaded++;
                    }
                }
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return stations;
    }

    public static int[] loadShipParts(final int shipId) {
        int[] parts = null;

        try {
            final InputStream is = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/shipparts_jsr.bin");
            final DataInputStream dis = new DataInputStream(is);

            for(int i = 0; i < Globals.SHIPS_COUNT; ++i) {
                if (dis.available() == 0) {
                    return null;
                }

                final byte id = dis.readByte();
                final byte partsCount = dis.readByte();
                parts = new int[partsCount * 10];

                for(int j = 0; j < parts.length; j += 10) {
                    parts[j + RESOURCE_ID] = dis.readShort();
                    parts[j + POSITION_X] = dis.readInt();  
                    parts[j + POSITION_Y] = dis.readInt();  
                    parts[j + POSITION_Z] = dis.readInt();  
                    parts[j + ROTATION_X] = dis.readShort();
                    parts[j + ROTATION_Y] = dis.readShort();
                    parts[j + ROTATION_Z] = dis.readShort();
                    parts[j + SCALE_X] = dis.readShort();
                    parts[j + SCALE_Y] = dis.readShort();
                    parts[j + SCALE_Z] = dis.readShort();
                }

                if (id == shipId + 1) {
                    return parts;
                }
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return parts;
    }

    public static int[] loadStationParts(int id, final int race) {
        int[] parts = null;
        id = race == 1 ? Globals.VOSSK_STATION_ID : id;

        try {
            final InputStream is = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stationparts.bin");
            final DataInputStream dis = new DataInputStream(is);

            for(int i = 0; i < (Globals.STATIONS_COUNT + 1); ++i) {
                if (dis.available() == 0) {
                    return null;
                }

                final byte stationId = dis.readByte();
                final short hangarResId = dis.readShort();
                final int partsCount = dis.readByte() + 1;
				parts = new int[partsCount * 7];
                parts[RESOURCE_ID] = hangarResId;
                parts[POSITION_X] = 0;
                parts[POSITION_Y] = 0;
                parts[POSITION_Z] = 0;
                parts[ROTATION_X] = 0;
                parts[ROTATION_Y] = AEMath.Q_PI_HALF;
                parts[ROTATION_Z] = 0;

                for(int j = 7; j < parts.length; j += 7) {
                    parts[j + RESOURCE_ID] = dis.readShort();
                    parts[j + POSITION_X] = dis.readInt();
                    parts[j + POSITION_Y] = dis.readInt();
                    parts[j + POSITION_Z] = dis.readInt();
                    parts[j + ROTATION_X] = dis.readShort();
                    parts[j + ROTATION_Y] = dis.readShort();
                    parts[j + ROTATION_Z] = dis.readShort();
                }

                if (stationId == id + 1) {
                    return parts;
                }
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return parts;
    }

    public static Station[] loadStationsBinary(final SolarSystem system) {
        Station[] var1 = null;

        try {
            final int[] stations = system.getStations();
            final InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stations.bin");
            final DataInputStream dis = new DataInputStream(var2);
            var1 = new Station[stations.length];
            int var8 = 0;

            for(int i = 0; i < Globals.STATIONS_COUNT; ++i) {
                final String stationName = dis.readUTF();
                final int stationId = dis.readInt();
                final int systemId = dis.readInt();
                final int tecLevel = dis.readInt();
                final int planetTextureId = dis.readInt();

                for(int j = 0; j < stations.length; ++j) {
                    if (stations[j] == i) {
                        var1[var8] = new Station(stationName, stationId, systemId, tecLevel, planetTextureId);
                        var8++;
                    }

                    if (var8 == stations.length) {
                        return var1;
                    }
                }
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return var1;
    }

    public static Agent[] loadAgents() {
        Agent[] agents = null;

        try {
            final InputStream is = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/agents.bin");
            final DataInputStream dis = new DataInputStream(is);
            agents = new Agent[Globals.SPECIAL_AGENTS_COUNT];
            int k = 0;

            for(int i = 0; i < agents.length; ++i) {
                final String name = dis.readUTF();
                final int msg = dis.readInt();
                final int station = dis.readInt();
                final int system = dis.readInt();
                final int race = dis.readInt();
                final boolean male = dis.readInt() == 1;
                final int secretSys = dis.readInt();
                final int bluePrint = dis.readInt();
                final int sellPrice = dis.readInt();
                agents[k] = new Agent(msg, name, station, system, race, male, secretSys, bluePrint, sellPrice);
                k++;
                int facePartsLen = dis.readInt();
                if (facePartsLen > 0) {
                    final byte[] faceParts = new byte[facePartsLen];

                    for(int j = 0; j < faceParts.length; ++j) {
                        faceParts[j] = dis.readByte();
                    }

                    agents[k - 1].setImageParts(faceParts);
                }
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return agents;
    }

    public static SolarSystem[] loadSystemsBinary() {
        SolarSystem[] var0 = null;

        try {
            final InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/systems.bin");
            final DataInputStream dis = new DataInputStream(var1);
            var0 = new SolarSystem[Globals.SYSTEMS_COUNT];

            for(int id = 0; id < Globals.SYSTEMS_COUNT; ++id) {
                final String name = dis.readUTF();
                final int safety = dis.readInt();
                final boolean visibleByDefault = dis.readInt() == 1;
                final int race = dis.readInt();
                final int posX = dis.readInt();
                final int posY = dis.readInt();
                final int posZ = dis.readInt();
                final int jumpgateStationId = dis.readInt();
                final int starTextureId = dis.readInt();
                final int[] starRGB = new int[dis.readInt()];

                for(int i = 0; i < starRGB.length; ++i) {
                    starRGB[i] = dis.readInt();
                }

                final int[] stationIDs = new int[dis.readInt()];

                for(int i = 0; i < stationIDs.length; ++i) {
                    stationIDs[i] = dis.readInt();
                }

                final int neighbourSysCount = dis.readInt();
                int[] neighbourSystems = null;
                if (neighbourSysCount > 0) {
                    neighbourSystems = new int[neighbourSysCount];

                    for(int i = 0; i < neighbourSystems.length; ++i) {
                        neighbourSystems[i] = dis.readInt();
                    }
                }

                // Propably legacy from Deep or GOF
                final int unkCnt = dis.readInt();
                int[] unk = null;
                if (unkCnt > 0) {
                    unk = new int[unkCnt];

                    for(int i = 0; i < unk.length; ++i) {
                        unk[i] = dis.readInt();
                    }
                }

                var0[id] = new SolarSystem(id, name, safety, visibleByDefault, race, posX, posY, posZ, jumpgateStationId, starTextureId, starRGB, stationIDs, neighbourSystems, unk);
            }

            dis.close();
        } catch (final IOException var17) {
            var17.printStackTrace();
        }

        return var0;
    }

    public static Item[] loadItemsBinary() {
        Item[] items = null;

        try {
            final InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/items.bin");
            final DataInputStream dis = new DataInputStream(var1);
            items = new Item[Globals.ITEMS_COUNT];
            int[] bluePrintComponents = null;
            int[] componentAmounts = null;
            int[] atributes = null;

            for(int i = 0; i < Globals.ITEMS_COUNT; ++i) {
                int j;
                int len = dis.readInt();
                if (len > 0) {
                    bluePrintComponents = new int[len];

                    for(j = 0; j < len; ++j) {
                        bluePrintComponents[j] = dis.readInt();
                    }
                }

                if ((len = dis.readInt()) > 0) {
                    componentAmounts = new int[len];

                    for(j = 0; j < len; ++j) {
                        componentAmounts[j] = dis.readInt();
                    }
                }

                if ((len = dis.readInt()) > 0) {
                    atributes = new int[len];

                    for(j = 0; j < len; ++j) {
                        atributes[j] = dis.readInt();
                    }
                }

                items[i] = new Item(bluePrintComponents, componentAmounts, atributes);
                bluePrintComponents = null;
                componentAmounts = null;
                atributes = null;
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static Ship[] loadShipsBinary() {
        Ship[] var0 = null;

        try {
            final InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/ships.bin");
            final DataInputStream var4 = new DataInputStream(var1);
            var0 = new Ship[Globals.SHIPS_COUNT];

            for(int i = 0; i < Globals.SHIPS_COUNT; ++i) {
                var0[i] = new Ship(var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt());
            }

            var4.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return var0;
    }

    public static String[] loadNamesBinary(final int race, final boolean male, final boolean firstName) {
        String[] names = null;
        String path = null;
        switch(race) {
        case Globals.TERRAN:
            path = firstName ? male ? "names_terran_0_m.bin" : "names_terran_0_w.bin" : "names_terran_1.bin";
            break;
        case Globals.VOSSK:
            path = firstName ? "names_vossk_0.bin" : "names_vossk_1.bin";
            break;
        case Globals.NIVELIAN:
            path = firstName ? "names_nivelian_0.bin" : "names_nivelian_1.bin";
            break;
        case Globals.MIDORIAN:
            if (firstName) {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_0_m.bin" : "names_nivelian_0.bin";
            } else {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_1.bin" : "names_nivelian_1.bin";
            }
            break;
        case Globals.MULTIPOD:
            path = firstName ? "names_multipod_0.bin" : "names_multipod_1.bin";
            break;
        case Globals.CYBORG:
            if (!firstName) {
                return null;
            }

            path = "names_cyborg_0.bin";
            break;
        case Globals.BOBOLIAN:
            path = firstName ? "names_bobolan_0.bin" : "names_bobolan_1.bin";
            break;
        case Globals.GREY:
            if (!firstName) {
                return null;
            }

            path = "names_grey_0.bin";
            break;
        case Globals.PIRATE:
            if (firstName) {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_0_m.bin" : "names_nivelian_0.bin";
            } else {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_1.bin" : "names_nivelian_1.bin";
            }
            break;
        default:
            return null;
        }


        try {
            path = "/data/txt/" + path;
            if (varClass == null) {
                varClass = getClassForName("java.lang.Class");
            }
            final InputStream is = varClass.getResourceAsStream(path);
            final DataInputStream dis = new DataInputStream(is);
            final int namesCount = dis.readInt();
            names = new String[namesCount];

            for(int i = 0; i < namesCount; ++i) {
                names[i] = dis.readUTF();
            }

            dis.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return names;
    }

    private static Class getClassForName(final String classPath) {
        try {
            return Class.forName(classPath);
        } catch (final ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
}
