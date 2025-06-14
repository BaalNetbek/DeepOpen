package GoF2;
/**
 * Stores data of BluePrint product. C struct like.
 * @author fishlabs
 *
 */
public final class ProducedGood {
	public String stationName;
	public int stationId;
	public int producedQuantity;
	public int index;

	public ProducedGood(final BluePrint var1) {
		this.index = var1.productId;
		this.stationName = var1.productionStationName;
		this.stationId = var1.productionStationId;
		this.producedQuantity = var1.producedTons;
	}

	public ProducedGood(final int var1, final String var2, final int var3, final int var4) {
		this.index = var1;
		this.stationName = var2;
		this.stationId = var3;
		this.producedQuantity = var4;
	}
}
