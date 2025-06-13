package GoF2;

import AE.GlobalStatus;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileRead {
   private static Class varClass;

   public final Station loadStation(int id) {
      return loadStationsBinary(new short[]{(short)id})[0];
   }

   private static Station[] loadStationsBinary(short[] ids) {
      Station[] stations = null;

      try {
         InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stations.bin");
         DataInputStream dis = new DataInputStream(var2);
         stations = new Station[ids.length];
         int loaded = 0;

         for(int i = 0; i < 100; ++i) {
            String name = dis.readUTF();
            int staionId = dis.readInt();
            int systemId = dis.readInt();
            int tecLevel = dis.readInt();
            int planetType = dis.readInt();

            for(int j = 0; j < ids.length; ++j) {
               if (ids[j] == i) {
                  stations[loaded] = new Station(name, staionId, systemId, tecLevel, planetType);
                  loaded++;
               }
            }
         }

         dis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return stations;
   }

   public static int[] loadShipParts(int shipId) {
      int[] parts = null;

      try {
         InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/shipparts_jsr.bin");
         DataInputStream dis = new DataInputStream(var2);

         for(int i = 0; i < 37; ++i) {
            if (dis.available() == 0) {
               return null;
            }

            byte id = dis.readByte();
            byte partsCount = dis.readByte();
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
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      return parts;
   }

   public static int[] loadStationParts(int id, int race) {
      int[] parts = null;
      id = race == 1 ? 100 : id;

      try {
         InputStream var8 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stationparts.bin");
         DataInputStream var9 = new DataInputStream(var8);

         for(int var3 = 0; var3 < 101; ++var3) {
            if (var9.available() == 0) {
               return null;
            }

            byte var4 = var9.readByte();
            short var5 = var9.readShort();
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
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      return parts;
   }

   public static Station[] loadStationsBinary(SolarSystem var0) {
      Station[] var1 = null;

      try {
         int[] var12 = var0.getStations();
         InputStream var2 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/stations.bin");
         DataInputStream var13 = new DataInputStream(var2);
         var1 = new Station[var12.length];
         int var8 = 0;

         for(int var9 = 0; var9 < 100; ++var9) {
            String var3 = var13.readUTF();
            int var4 = var13.readInt();
            int var5 = var13.readInt();
            int var6 = var13.readInt();
            int var7 = var13.readInt();

            for(int var10 = 0; var10 < var12.length; ++var10) {
               if (var12[var10] == var9) {
                  var1[var8++] = new Station(var3, var4, var5, var6, var7);
               }

               if (var8 == var12.length) {
                  return var1;
               }
            }
         }

         var13.close();
      } catch (IOException var11) {
         var11.printStackTrace();
      }

      return var1;
   }

   public static Agent[] loadAgents() {
      Agent[] var0 = null;

      try {
         InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/agents.bin");
         DataInputStream var14 = new DataInputStream(var1);
         var0 = new Agent[16];
         int var11 = 0;

         for(int var12 = 0; var12 < var0.length; ++var12) {
            String var2 = var14.readUTF();
            int var3 = var14.readInt();
            int var4 = var14.readInt();
            int var5 = var14.readInt();
            int var6 = var14.readInt();
            boolean var7 = var14.readInt() == 1;
            int var8 = var14.readInt();
            int var9 = var14.readInt();
            int var10 = var14.readInt();
            var0[var11++] = new Agent(var3, var2, var4, var5, var6, var7, var8, var9, var10);
            int var15;
            if ((var15 = var14.readInt()) > 0) {
               byte[] var16 = new byte[var15];

               for(var3 = 0; var3 < var16.length; ++var3) {
                  var16[var3] = var14.readByte();
               }

               var0[var11 - 1].setImageParts(var16);
            }
         }

         var14.close();
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      return var0;
   }

   public static SolarSystem[] loadSystemsBinary() {
      SolarSystem[] var0 = null;

      try {
         InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/systems.bin");
         DataInputStream var18 = new DataInputStream(var1);
         var0 = new SolarSystem[22];

         for(int var2 = 0; var2 < 22; ++var2) {
            String var3 = var18.readUTF();
            int var4 = var18.readInt();
            boolean var5 = var18.readInt() == 1;
            int var6 = var18.readInt();
            int var7 = var18.readInt();
            int var8 = var18.readInt();
            int var9 = var18.readInt();
            int var10 = var18.readInt();
            int var11 = var18.readInt();
            int[] var12 = new int[var18.readInt()];

            for(int var13 = 0; var13 < var12.length; ++var13) {
               var12[var13] = var18.readInt();
            }

            int[] var19 = new int[var18.readInt()];

            int var14;
            for(var14 = 0; var14 < var19.length; ++var14) {
               var19[var14] = var18.readInt();
            }

            var14 = var18.readInt();
            int[] var15 = null;
            if (var14 > 0) {
               var15 = new int[var14];

               for(var14 = 0; var14 < var15.length; ++var14) {
                  var15[var14] = var18.readInt();
               }
            }

            var14 = var18.readInt();
            int[] var16 = null;
            if (var14 > 0) {
               var16 = new int[var14];

               for(var14 = 0; var14 < var16.length; ++var14) {
                  var16[var14] = var18.readInt();
               }
            }

            var0[var2] = new SolarSystem(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var19, var15, var16);
         }

         var18.close();
      } catch (IOException var17) {
         var17.printStackTrace();
      }

      return var0;
   }

   public static Item[] loadItemsBinary() {
      Item[] var0 = null;

      try {
         InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/items.bin");
         DataInputStream var9 = new DataInputStream(var1);
         var0 = new Item[176];
         boolean var2 = false;
         var2 = false;
         var2 = false;
         int[] var3 = null;
         int[] var4 = null;
         int[] var5 = null;

         for(int var6 = 0; var6 < 176; ++var6) {
            int var7;
            int var10;
            if ((var10 = var9.readInt()) > 0) {
               var3 = new int[var10];

               for(var7 = 0; var7 < var10; ++var7) {
                  var3[var7] = var9.readInt();
               }
            }

            if ((var10 = var9.readInt()) > 0) {
               var4 = new int[var10];

               for(var7 = 0; var7 < var10; ++var7) {
                  var4[var7] = var9.readInt();
               }
            }

            if ((var10 = var9.readInt()) > 0) {
               var5 = new int[var10];

               for(var7 = 0; var7 < var10; ++var7) {
                  var5[var7] = var9.readInt();
               }
            }

            var0[var6] = new Item(var3, var4, var5);
            var3 = null;
            var4 = null;
            var5 = null;
         }

         var9.close();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      return var0;
   }

   public static Ship[] loadShipsBinary() {
      Ship[] var0 = null;

      try {
         InputStream var1 = (varClass == null ? (varClass = getClassForName("java.lang.Class")) : varClass).getResourceAsStream("/data/txt/ships.bin");
         DataInputStream var4 = new DataInputStream(var1);
         var0 = new Ship[37];

         for(int var2 = 0; var2 < 37; ++var2) {
            var0[var2] = new Ship(var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), var4.readInt(), (float)var4.readInt());
         }

         var4.close();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      return var0;
   }

   public static String[] loadNamesBinary(int race, boolean male, boolean firstName) {
      String[] names = null;
      String path = null;
      switch(race) {
      case 0:
         path = firstName ? (male ? "names_terran_0_m.bin" : "names_terran_0_w.bin") : "names_terran_1.bin";
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
         if (varClass == null) varClass = getClassForName("java.lang.Class");
         InputStream is = varClass.getResourceAsStream(path);
         DataInputStream dis = new DataInputStream(is);
         int namesCount = dis.readInt();
         names = new String[namesCount];

         for(int i = 0; i < namesCount; ++i) {
            names[i] = dis.readUTF();
         }

         dis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return names;
   }

   private static Class getClassForName(String classPath) {
      try {
         return Class.forName(classPath);
      } catch (ClassNotFoundException var1) {
         throw new NoClassDefFoundError(var1.getMessage());
      }
   }
}
