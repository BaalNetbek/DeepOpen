package GoF2;

public final class BluePrint {
	int productId;
	private final int tonsPerProduction;
	public int[] missingComponentsTons;
	public int investedCredits;
	public boolean unlocked;
	public int timesProduced;
	public int productionStationId;
	public String productionStationName;
	int producedTons;

	public BluePrint(final int var1) {
		this.productId = var1;
		this.tonsPerProduction = Globals.getItems()[var1].getType() == 1 ? 10 : 1;
		this.productionStationId = -1;
		final Item var2 = Globals.getItems()[var1];
		final int[] var3 = Globals.getItems()[var1].getBlueprintComponentsQuantities();
		if (var2.getBluePrintComponentsIds() != null) {
			this.missingComponentsTons = new int[var2.getBluePrintComponentsIds().length];

			for(int i = 0; i < this.missingComponentsTons.length; ++i) {
				this.missingComponentsTons[i] = var3[i];
			}
		}

		this.unlocked = false;
		this.timesProduced = 0;
		this.producedTons = this.tonsPerProduction;
	}

	public final void startProduction(final Item var1, final int var2, final int var3) {
		if (this.productionStationId < 0 && var3 >= 0) {
			this.productionStationId = var3;
			this.productionStationName = Status.getStation().getName();
		}

		var1.setBlueprintAmount(0);
		int[] var5 = getIngredientList();
		if (var5 != null) {
			for(int i = 0; i < var5.length; ++i) {
				if (var5[i] == var1.getIndex()) {
					this.missingComponentsTons[i] -= var2;
					this.investedCredits += var1.getSinglePrice() * var2;
					return;
				}
			}
		}

	}

	public final boolean isComplete() {
		for(int i = 0; i < this.missingComponentsTons.length; ++i) {
			if (this.missingComponentsTons[i] > 0) {
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

	public final int getTotalAmount(final int var1) {
		final int[] var2 = getIngredientList();
		final int[] var3 = getQuantityList();

		for(int i = 0; i < var3.length; ++i) {
			if (var2[i] == var1) {
				return var3[i];
			}
		}

		return 0;
	}

	public final int getCurrentAmount(final int var1) {
		return getTotalAmount(var1) - getRemainingAmount(var1);
	}

	public final int getRemainingAmount(final int var1) {
		final int[] var2 = getIngredientList();

		for(int i = 0; i < this.missingComponentsTons.length; ++i) {
			if (var2[i] == var1) {
				return this.missingComponentsTons[i];
			}
		}

		return 0;
	}

	public final float getCompletionRate() {
		float var1 = 0.0F;
		final int[] var2 = getQuantityList();

		for(int i = 0; i < var2.length; ++i) {
			var1 += (float)(var2[i] - this.missingComponentsTons[i]) / (float)var2[i] / this.missingComponentsTons.length;
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
		final int[] var1 = getQuantityList();

		for(int i = 0; i < this.missingComponentsTons.length; ++i) {
			this.missingComponentsTons[i] = var1[i];
		}

		this.investedCredits = 0;
		this.productionStationId = -1;
		this.producedTons = this.tonsPerProduction;
	}
}
