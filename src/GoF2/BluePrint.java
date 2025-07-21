package GoF2;

public final class BluePrint {
   int productId;
   private int tonsPerProduction;
   public int[] missingComponentsTons;
   public int investedCredits;
   public boolean unlocked;
   public int timesProduced;
   public int productionStationId;
   public String productionStationName;
   int producedTons;

   public BluePrint(int var1) {
      this.productId = var1;
      this.tonsPerProduction = Globals.getItems()[var1].getType() == 1 ? 10 : 1;
      this.productionStationId = -1;
      Item var2 = Globals.getItems()[var1];
      int[] var3 = Globals.getItems()[var1].getBlueprintComponentsQuantities();
      if (var2.getBluePrintComponentsIds() != null) {
         this.missingComponentsTons = new int[var2.getBluePrintComponentsIds().length];

         for(int var4 = 0; var4 < this.missingComponentsTons.length; ++var4) {
            this.missingComponentsTons[var4] = var3[var4];
         }
      }

      this.unlocked = false;
      this.timesProduced = 0;
      this.producedTons = this.tonsPerProduction;
   }

   public final void startProduction(Item var1, int var2, int var3) {
      if (this.productionStationId < 0 && var3 >= 0) {
         this.productionStationId = var3;
         this.productionStationName = Status.getStation().getName();
      }

      var1.setBlueprintAmount(0);
      int[] var5;
      if ((var5 = this.getIngredientList()) != null) {
         for(int var4 = 0; var4 < var5.length; ++var4) {
            if (var5[var4] == var1.getIndex()) {
               int[] var10000 = this.missingComponentsTons;
               var10000[var4] -= var2;
               this.investedCredits += var1.getSinglePrice() * var2;
               return;
            }
         }
      }

   }

   public final boolean isComplete() {
      for(int var1 = 0; var1 < this.missingComponentsTons.length; ++var1) {
         if (this.missingComponentsTons[var1] > 0) {
            return false;
         }
      }

      return true;
   }

   public final int getIndex() {
      return this.productId;
   }

   public final int[] getIngredientList() {
      return Globals.getItems()[this.productId].getBluePrintComponentsIds();
   }

   private int[] getQuantityList() {
      return Globals.getItems()[this.productId].getBlueprintComponentsQuantities();
   }

   public final int getTotalAmount(int var1) {
      int[] var2 = this.getIngredientList();
      int[] var3 = this.getQuantityList();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var2[var4] == var1) {
            return var3[var4];
         }
      }

      return 0;
   }

   public final int getCurrentAmount(int var1) {
      return this.getTotalAmount(var1) - this.getRemainingAmount(var1);
   }

   public final int getRemainingAmount(int var1) {
      int[] var2 = this.getIngredientList();

      for(int var3 = 0; var3 < this.missingComponentsTons.length; ++var3) {
         if (var2[var3] == var1) {
            return this.missingComponentsTons[var3];
         }
      }

      return 0;
   }

   public final float getCompletionRate() {
      float var1 = 0.0F;
      int[] var2 = this.getQuantityList();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var1 += (float)(var2[var3] - this.missingComponentsTons[var3]) / (float)var2[var3] / (float)this.missingComponentsTons.length;
      }

      return var1;
   }

   public final int getProductionStationId() {
      return this.productionStationId;
   }

   public final String getProductionStationName() {
      return this.productionStationName;
   }

   public final int getTonsPerProduction() {
      return this.tonsPerProduction;
   }

   public final int getTonsPerProduction2() {
      return this.producedTons;
   }

   public final void unlock() {
      this.unlocked = true;
   }

   public final boolean isStarted() {
      return this.investedCredits == 0;
   }

   public final void finishProduction() {
      ++this.timesProduced;
      Status.incGoodsProduced(1);
      int[] var1 = this.getQuantityList();

      for(int var2 = 0; var2 < this.missingComponentsTons.length; ++var2) {
         this.missingComponentsTons[var2] = var1[var2];
      }

      this.investedCredits = 0;
      this.productionStationId = -1;
      this.producedTons = this.tonsPerProduction;
   }
}
