package GoF2;
/**
 * Stores data of BluePrint product.
 * @author fishlabs
 *
 */
public final class PendingProduct {
    public String stationName;
    public int stationId;
    public int producedQuantity;
    public int index;

    public PendingProduct(final BluePrint var1) {
        this.index = var1.productId;
        this.stationName = var1.productionStationName;
        this.stationId = var1.productionStationId;
        this.producedQuantity = var1.producedTons;
    }

    public PendingProduct(final int var1, final String var2, final int var3, final int var4) {
        this.index = var1;
        this.stationName = var2;
        this.stationId = var3;
        this.producedQuantity = var4;
    }
}
