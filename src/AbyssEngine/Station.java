package AbyssEngine;

public final class Station {
   private String name;
   private int id;
   private int systemId;
   private int planetTextureId;
   private int tecLevel;
   private Item[] hangarItems;
   private Ship[] hangarShips;
   private Agent[] barAgents;

   public Station(String var1, int var2, int var3, int var4, int var5) {
      this.name = var1;
      this.id = var2;
      this.systemId = var3;
      this.tecLevel = var4;
      this.planetTextureId = var5;
      this.hangarItems = null;
      this.hangarShips = null;
      this.barAgents = null;
   }

   public Station() {
      this("", -1, -1, 0, 0);
   }

   public final int getSystemIndex() {
      return this.systemId;
   }

   public final String getName() {
      return this.name;
   }

   public final int getId() {
      return this.id;
   }

   public final boolean sub_fc() {
      return this.id == Status.wormholeStation;
   }

   public final int getPlanetTextureId() {
      return this.planetTextureId;
   }

   public final boolean sub_175() {
      return Galaxy.getVisitedStations()[this.id];
   }

   public final void sub_1a2() {
      if (!this.sub_175()) {
         Status.incStationsVisited();
         Galaxy.visitStation(this.id);
      }

   }

   public final int getTecLevel() {
      return this.tecLevel;
   }

   public final Ship[] getShopShips() {
      return this.hangarShips;
   }

   public final Item[] getShopItems() {
      return this.hangarItems;
   }

   public final void setShopItems(Item[] var1) {
      this.hangarItems = var1;
   }

   public final void setShopShips(Ship[] var1) {
      this.hangarShips = var1;
   }

   public final void setBarAgents(Agent[] var1) {
      this.barAgents = var1;
   }

   public final void sub_334(Item var1) {
      if (this.hangarItems == null) {
         this.hangarItems = new Item[]{var1};
      } else {
         for(int var2 = 0; var2 < this.hangarItems.length; ++var2) {
            if (this.hangarItems[var2].equals(var1)) {
               this.hangarItems[var2].changeAmount(var1.getAmount());
               return;
            }
         }

         Item[] var3 = new Item[this.hangarItems.length + 1];
         System.arraycopy(this.hangarItems, 0, var3, 0, this.hangarItems.length);
         var3[var3.length - 1] = var1;
         this.hangarItems = var3;
      }
   }

   public final Agent[] getBarAgents() {
      return this.barAgents;
   }

   public final String toString() {
      return this.name;
   }

   public final boolean equals(Station var1) {
      return this.name.equals(var1.name);
   }
}
