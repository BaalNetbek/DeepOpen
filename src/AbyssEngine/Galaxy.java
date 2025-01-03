package AbyssEngine;

public final class Galaxy {
   private static AEVector3D var_11 = new AEVector3D();
   private static AEVector3D var_141 = new AEVector3D();
   private static boolean[] visitedStations = new boolean[100];

   public static SolarSystem loadSystem_(int var0) {
      if (var0 < 0) {
         return null;
      } else {
         new FileRead();
         SolarSystem[] var1 = FileRead.loadSystemsBinary();
         if (Status.getPlayingTime() == 0L) {
            boolean[] var2 = Status.getVisibleSystems();

            for(int var3 = 0; var3 < var2.length; ++var3) {
               var2[var3] = var1[var3].isVisibleByDeafult();
            }
         }

         return var1[var0];
      }
   }

   public static Station getStation(int var0) {
      return var0 < 0 ? Status.voidStation : (new FileRead()).loadStation(var0);
   }

   public static void setVisitedStations(boolean[] var0) {
      visitedStations = var0;
   }

   public static boolean[] getVisitedStations() {
      return visitedStations;
   }

   public static void visitStation(int var0) {
      visitedStations[var0] = true;
   }

   public static int invDistancePercent(int var0, int var1, int var2, int var3) {
      return 100 - (AEMath.sqrt((long)((var2 - var0) * (var2 - var0) + (var3 - var1) * (var3 - var1) << 12)) >> 12);
   }

   public static int distancePercent(int var0, int var1, int var2, int var3) {
      return AEMath.sqrt((long)((var2 - var0) * (var2 - var0) + (var3 - var1) * (var3 - var1) << 12)) >> 12;
   }

   public static float distance(SolarSystem var0, SolarSystem var1) {
      if (var0.equals(var1)) {
         return 0.0F;
      } else {
         var_11.set(var0.getPosX(), var0.getPosY(), var0.getPosZ() / 10);
         var_141.set(var1.getPosX(), var1.getPosY(), var1.getPosZ() / 10);
         var_11.subtract(var_141);
         return (float)(AEMath.sqrt((long)(var_11.x * var_11.x + var_11.y * var_11.y + var_11.z * var_11.z << 12)) >> 12) * 18.85F;
      }
   }

   public static int[] sub_180(Station var0) {
      boolean var1 = Status.inAlienOrbit();
      SolarSystem[] var2 = null;
      Item[] var3;
      if (!var1) {
         new FileRead();
         var3 = null;
         var2 = FileRead.loadSystemsBinary();
      }

      var3 = IndexManager.getItems();
      int[] var4 = new int[11];
      int[] var5 = new int[11];
      int var6 = 0;

      for(int var7 = 154; var7 < 164; ++var7) {
         var5[var6] = var7;
         if (var1) {
            var4[var6] = 0;
         } else {
            var4[var6] = (byte)invDistancePercent(var2[var0.getSystemIndex()].getPosX(), var2[var0.getSystemIndex()].getPosY(), var2[var3[var7].getLowestPriceSystemId()].getPosX(), var2[var3[var7].getLowestPriceSystemId()].getPosY());
            if (var4[var6] < 50) {
               var4[var6] = 0;
            }

            ++var6;
         }
      }

      var5[10] = 164;
      var4[10] = var1 ? 100 : 0;

      int var8;
      int var9;
      boolean var12;
      do {
         var12 = true;

         for(var8 = 1; var8 < var4.length; ++var8) {
            if (var4[var8 - 1] < var4[var8]) {
               var9 = var4[var8 - 1];
               int var11 = var5[var8 - 1];
               var4[var8 - 1] = var4[var8];
               var5[var8 - 1] = var5[var8];
               var4[var8] = var9;
               var5[var8] = var11;
               var12 = false;
            }
         }
      } while(!var12);

      for(var8 = 0; var8 < var4.length; ++var8) {
         if (var4[var8] > 0) {
            var4[var8] = (byte)(var4[var8] - var8 * 2);
         }
      }

      int[] var10 = new int[var4.length << 1];

      for(var9 = 0; var9 < var10.length; var9 += 2) {
         var10[var9] = var5[var9 / 2];
         var10[var9 + 1] = var4[var9 / 2];
      }

      return var10;
   }
}
