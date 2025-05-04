package GoF2;

public final class SolarSystem {
   private String name;
   private int id;
   private int safety;
   private int race;
   private int posX;
   private int posY;
   private int posZ;
   private int jumpgateStationId;
   private int starTextureId;
   private int[] stationIDs;
   private int[] neighbourSystems;
   private boolean visibleByDeafult;
   public int starR;
   public int starG;
   public int starB;

   public SolarSystem(int var1, String var2, int var3, boolean var4, int var5, int var6, int var7, int var8, int var9, int var10, int[] var11, int[] var12, int[] var13, int[] var14) {
      this.id = var1;
      this.name = var2;
      this.safety = var3;
      this.visibleByDeafult = var4;
      this.race = var5;
      this.posX = var6;
      this.posY = var7;
      this.posZ = var8;
      this.jumpgateStationId = var9;
      this.starTextureId = var10;
      this.starR = var11[0];
      this.starG = var11[1];
      this.starB = var11[2];
      this.stationIDs = var12;
      this.neighbourSystems = var13;
   }

   public final int getId() {
      return this.id;
   }

   public final String getName() {
      return this.name;
   }

   public final int getSafety() {
      return this.safety;
   }

   public final int getRace() {
      return this.race;
   }

   public final int getPosX() {
      return this.posX;
   }

   public final int getPosY() {
      return this.posY;
   }

   public final int getPosZ() {
      return this.posZ;
   }

   public final int getStarTextureIndex() {
      return this.starTextureId;
   }

   public final int getJumpgateStationIndex() {
      return this.jumpgateStationId;
   }

   public final int getJumpGateEnumIndex() {
      return this.getStationEnumIndex(this.jumpgateStationId);
   }

   public final int getStationEnumIndex(int var1) {
      for(int var2 = 0; var2 < this.stationIDs.length; ++var2) {
         if (this.stationIDs[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public final boolean currentOrbitHasJumpgate() {
      return this.jumpgateStationId == Status.getStation().getId();
   }

   public final boolean stationIsInSystem(int var1) {
      for(int var2 = 0; var2 < this.stationIDs.length; ++var2) {
         if (this.stationIDs[var2] == var1) {
            return true;
         }
      }

      return false;
   }

   public final boolean isFullyDiscovered() {
      for(int var1 = 0; var1 < this.stationIDs.length; ++var1) {
         if (!Galaxy.getVisitedStations()[this.stationIDs[var1]]) {
            return false;
         }
      }

      return true;
   }

   public final int[] getStations() {
      return this.stationIDs;
   }

   public final int[] getNeighbourSystems() {
      return this.neighbourSystems;
   }

   public final boolean inJumpageRange(int var1) {
      if (var1 == this.id) {
         return true;
      } else {
         if (this.neighbourSystems != null) {
            for(int var2 = 0; var2 < this.neighbourSystems.length; ++var2) {
               if (this.neighbourSystems[var2] == var1) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public final boolean isVisibleByDeafult() {
      return this.visibleByDeafult;
   }
}
