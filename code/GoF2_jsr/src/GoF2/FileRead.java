package GoF2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import AE.GlobalStatus;

public final class FileRead {
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

            for(int i = 0; i < 100; ++i) {
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
            final InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/shipparts_jsr.bin");
            final DataInputStream dis = new DataInputStream(var2);

            for(int i = 0; i < 37; ++i) {
                if (dis.available() == 0) {
                    return null;
                }

                final byte id = dis.readByte();
                final byte partsCount = dis.readByte();
                parts = new int[partsCount * 10];

                for(int j = 0; j < parts.length; j += 10) {
                    parts[j] = dis.readShort();    // part id
                    parts[j + 1] = dis.readInt();  // pos x
                    parts[j + 2] = dis.readInt();  // pos y
                    parts[j + 3] = dis.readInt();  // pos z
                    parts[j + 4] = dis.readShort();// rot x
                    parts[j + 5] = dis.readShort();// rot y
                    parts[j + 6] = dis.readShort();// rot z
                    parts[j + 7] = dis.readShort();// scale x
                    parts[j + 8] = dis.readShort();// scale y
                    parts[j + 9] = dis.readShort();// scale z
                }

                if (id == shipId + 1) {
                    return parts;
                }
            }

            dis.close();
        } catch (final IOException var6) {
            var6.printStackTrace();
        }

        return parts;
    }

    public static int[] loadStationParts(int id, final int race) {
        int[] parts = null;
        id = race == 1 ? 100 : id;

        try {
            final InputStream var8 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stationparts.bin");
            final DataInputStream var9 = new DataInputStream(var8);

            for(int var3 = 0; var3 < 101; ++var3) {
                if (var9.available() == 0) {
                    return null;
                }

                final byte var4 = var9.readByte();
                final short var5 = var9.readShort();
                parts = new int[(var9.readByte() + 1) * 7];
                parts[0] = var5;
                parts[1] = 0;
                parts[2] = 0;
                parts[3] = 0;
                parts[4] = 0;
                parts[5] = 2048;
                parts[6] = 0;

                for(int var10 = 7; var10 < parts.length; var10 += 7) {
                    parts[var10] = var9.readShort();
                    parts[var10 + 1] = var9.readInt();
                    parts[var10 + 2] = var9.readInt();
                    parts[var10 + 3] = var9.readInt();
                    parts[var10 + 4] = var9.readShort();
                    parts[var10 + 5] = var9.readShort();
                    parts[var10 + 6] = var9.readShort();
                }

                if (var4 == id + 1) {
                    return parts;
                }
            }

            var9.close();
        } catch (final IOException var7) {
            var7.printStackTrace();
        }

        return parts;
    }

    public static Station[] loadStationsBinary(final SolarSystem var0) {
        Station[] var1 = null;

        try {
            final int[] var12 = var0.getStations();
            final InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stations.bin");
            final DataInputStream var13 = new DataInputStream(var2);
            var1 = new Station[var12.length];
            int var8 = 0;

            for(int var9 = 0; var9 < 100; ++var9) {
                final String var3 = var13.readUTF();
                final int var4 = var13.readInt();
                final int var5 = var13.readInt();
                final int var6 = var13.readInt();
                final int var7 = var13.readInt();

                for(int var10 = 0; var10 < var12.length; ++var10) {
                    if (var12[var10] == var9) {
                        var1[var8] = new Station(var3, var4, var5, var6, var7);
                        var8++;
                    }

                    if (var8 == var12.length) {
                        return var1;
                    }
                }
            }

            var13.close();
        } catch (final IOException var11) {
            var11.printStackTrace();
        }

        return var1;
    }

    public static Agent[] loadAgents() {
        Agent[] var0 = null;

        try {
            final InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/agents.bin");
            final DataInputStream var14 = new DataInputStream(var1);
            var0 = new Agent[16];
            int var11 = 0;

            for(int var12 = 0; var12 < var0.length; ++var12) {
                final String var2 = var14.readUTF();
                int var3 = var14.readInt();
                final int var4 = var14.readInt();
                final int var5 = var14.readInt();
                final int var6 = var14.readInt();
                final boolean var7 = var14.readInt() == 1;
                final int var8 = var14.readInt();
                final int var9 = var14.readInt();
                final int var10 = var14.readInt();
                var0[var11] = new Agent(var3, var2, var4, var5, var6, var7, var8, var9, var10);
                var11++;
                int var15 = var14.readInt();
                if (var15 > 0) {
                    final byte[] var16 = new byte[var15];

                    for(var3 = 0; var3 < var16.length; ++var3) {
                        var16[var3] = var14.readByte();
                    }

                    var0[var11 - 1].setImageParts(var16);
                }
            }

            var14.close();
        } catch (final IOException var13) {
            var13.printStackTrace();
        }

        return var0;
    }

    public static SolarSystem[] loadSystemsBinary() {
        SolarSystem[] var0 = null;

        try {
            final InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/systems.bin");
            final DataInputStream dis = new DataInputStream(var1);
            var0 = new SolarSystem[22];

            for(int id = 0; id < 22; ++id) {
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
            items = new Item[176];
            int[] bluePrintComponents = null;
            int[] componentAmounts = null;
            int[] atributes = null;

            for(int i = 0; i < 176; ++i) {
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
            var0 = new Ship[37];

            for(int var2 = 0; var2 < 37; ++var2) {
                var0[var2] = new Ship(var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt());
            }

            var4.close();
        } catch (final IOException var3) {
            var3.printStackTrace();
        }

        return var0;
    }

    public static String[] loadNamesBinary(final int race, final boolean male, final boolean firstName) {
        String[] names = null;
        String path = null;
        switch(race) {
        case 0:
            path = firstName ? male ? "names_terran_0_m.bin" : "names_terran_0_w.bin" : "names_terran_1.bin";
            break;
        case 1:
            path = firstName ? "names_vossk_0.bin" : "names_vossk_1.bin";
            break;
        case 2:
            path = firstName ? "names_nivelian_0.bin" : "names_nivelian_1.bin";
            break;
        case 3:
            if (firstName) {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_0_m.bin" : "names_nivelian_0.bin";
            } else {
                path = GlobalStatus.random.nextInt(2) == 0 ? "names_terran_1.bin" : "names_nivelian_1.bin";
            }
            break;
        case 4:
            path = firstName ? "names_multipod_0.bin" : "names_multipod_1.bin";
            break;
        case 5:
            if (!firstName) {
                return null;
            }

            path = "names_cyborg_0.bin";
            break;
        case 6:
            path = firstName ? "names_bobolan_0.bin" : "names_bobolan_1.bin";
            break;
        case 7:
            if (!firstName) {
                return null;
            }

            path = "names_grey_0.bin";
            break;
        case 8:
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
        } catch (final ClassNotFoundException var1) {
            throw new NoClassDefFoundError(var1.getMessage());
        }
    }
}
