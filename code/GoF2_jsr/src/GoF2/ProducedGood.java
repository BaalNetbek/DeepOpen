package GoF2;

public final class ProducedGood {
   public String stationName;
   public int stationId;
   public int producedQuantity;
   public int index;

   public ProducedGood(BluePrint var1) {
      this.index = var1.productId;
      this.stationName = var1.productionStationName;
      this.stationId = var1.productionStationId;
      this.producedQuantity = var1.producedTons;
   }

   public ProducedGood(int var1, String var2, int var3, int var4) {
      this.index = var1;
      this.stationName = var2;
      this.stationId = var3;
      this.producedQuantity = var4;
   }
}
