package GOF2;
/**
 * Item manages information about equipable item or an commodity.
 * 
 * @author Fishlabs 2009
 */
public final class Item {
	
	// types
	public static final int PRIMARY = 0;
	public static final int SECONDARY = 1;
	public static final int TURRET = 2;
	public static final int EQUIPMENT = 3;
	public static final int COMMODITY = 4;
	// sorts (sub types)
	public static final int LASER = 0;
	public static final int BLASTER = 1;
	public static final int AUTOCANNON = 2;
	public static final int THERMO = 3;
	public static final int ROCKET = 4;
	public static final int TORPEDO = 5;
	public static final int EMP_BOMB = 6;
	public static final int NUKE = 7;
	public static final int TURRET_SORT = 8;
	public static final int SHIELD = 9;
	public static final int ARMOR = 10;
	public static final int EMP_PROTECTION = 11;
	public static final int COMPRESSION = 12;
	public static final int TRACTOR_BEAM = 13;
	public static final int BOOSTER = 14;
	public static final int REPAIR_BOT = 15;
	public static final int STEERING_NOZZLE = 16;
	public static final int SCANNER = 17;
	public static final int JUMP_DRIVE = 18;
	public static final int MINING_LASER = 19;
	public static final int CABIN = 20;
	public static final int CLOAK = 21;
	public static final int COMMODITY_SORT = 22;
	public static final int ORE = 23;
	public static final int ORE_CORE = 24;
	
	// Not defined attribute values
	public static final int NULL_ATTRIBUTE = -979797979;
	// attributes
	public static final int ID = 0;
	public static final int TYPE = 1;
	public static final int SUB_TYPE = 2;
	public static final int TEC_LEVEL = 3;
	public static final int LOW_PRICE_SYSTEM_ID = 4;
	public static final int HIGH_PRICE_SYSTEM_ID = 5;
	public static final int OCCURANCE = 6;			// %	
	public static final int MIN_PRICE = 7;
	public static final int MAX_PRICE = 8;
	public static final int DAMAGE = 9;
	public static final int EMP_DAMAGE = 10;
	public static final int RELOAD = 11;			// ms
	public static final int RANGE = 12;				// m
	public static final int VELOCITY = 13;  // 250 km/h
	public static final int EXPLOSION_RANGE = 14;
	public static final int TURRET_HANDLING = 15;
	public static final int SHIELD_VALUE = 16;
	public static final int SHIELD_REGEN_TIME = 17;
	public static final int ARMOR_VALUE = 18;
	public static final int EMP_ARMOR = 19;
	public static final int CARGO_EXPANSION = 20; 	// %
	public static final int TRACTOR_AUTOMATIC = 21;
	public static final int TRACTOR_SCAN_TIME = 22; // ms
	public static final int SPEED_BOOST = 23; 		// %
	public static final int BOOST_LOAD_TIME = 24;	// ms
	public static final int BOOST_LENGTH = 25;		// ms
	public static final int HANDLING_BOOST = 26;	// %
	public static final int SCAN_TIME = 27;			// ms
	public static final int SHOW_ASTEROID = 28;
	public static final int SHOW_CARGO = 29;
	public static final int MINING_CONTROL = 30;
	public static final int MINING_EFFICIENCY = 31;
	public static final int CABIN_SIZE = 32;
	public static final int CLOAK_DURATION = 33;
	public static final int CLOAK_RELOAD = 34;		// ms
	public static final int RACE = 35;
	public static final int GUARANTEED_TO_HAVE_SYSTEM = 36;
	
	// item indices
	public static final int IDX_PRIMARY_START = 0;
	public static final int IDX_LASER_START = 0;
	public static final int IDX_BLASTER_START = 12;
	public static final int IDX_AUTOC_START = 22;
	public static final int IDX_THERMO_START = 28; // ref

	public static final int IDX_SECONDARY_START = 31;
	public static final int IDX_ROCKET_START = 31;
	public static final int IDX_MISSILE_START = 36;
	public static final int IDX_EMP_BOMB_START = 41;
	public static final int IDX_NUKE_START = 44;

	public static final int IDX_TURRET_START = 47;

	public static final int IDX_EQ_START = 50;
	public static final int IDX_SHIELD_START = 50;
	public static final int IDX_ARMOR_START = 55;
	public static final int IDX_EMP_SHIELD_START = 60;
	public static final int IDX_COMPR_START = 63;
	public static final int IDX_TRACTOR_START = 68;
	public static final int IDX_BOOSTER_START = 71;
	public static final int IDX_REPAIR_START = 75;
	public static final int IDX_STEER_START = 76;
	public static final int IDX_SCAN_START = 81;
	public static final int IDX_DRIVE_START = 85;
	public static final int IDX_DRILL_START = 86;
	public static final int IDX_CABIN_START = 91;
	public static final int IDX_CLOAK_START = 94;

	public static final int IDX_COMM_START = 97;
	public static final int IDX_ORE_START = 154;
	public static final int IDX_CORE_START = 165;
	
	
	private static final boolean[] installableMultipleTimes = {
	      true, true, true, true, true, true, true, true, false,
	      false, false, true, true, false, false, false, false,
	      false, false, false, true, false, true, true, true
	};
	private final int id;
	private final int type;
	private final int subType;
	private final int tecLevel;
	private final int lowestPriceSystemId;
	private final int highestPriceSystemId;
	private int price;
	private final int occurance;
	private final int minPrice;
	private final int maxPrice;
	private final int[] blueprintComponentsIds;
	private final int[] blueprintComponentsQuantities;
	private final int[] atributesIndexed;
	private int amount;
	private int stationAmount;
	private int blueprintAmount;
	private boolean unsaleable;

	public Item(final int[] var1, final int[] var2, final int[] var3) {
		this.blueprintComponentsIds = var1;
		this.blueprintComponentsQuantities = var2;
		this.atributesIndexed = var3;
		this.id = this.atributesIndexed[(ID<<1)+1];
		this.type = this.atributesIndexed[(TYPE<<1)+1];
		this.subType = this.atributesIndexed[(SUB_TYPE<<1)+1];
		this.tecLevel = this.atributesIndexed[(TEC_LEVEL<<1)+1];
		this.occurance = this.atributesIndexed[(OCCURANCE<<1)+1];
		this.minPrice = this.atributesIndexed[(MIN_PRICE<<1)+1];
		this.maxPrice = this.atributesIndexed[(MAX_PRICE<<1)+1];
		this.lowestPriceSystemId = this.atributesIndexed[(LOW_PRICE_SYSTEM_ID<<1)+1];
		this.highestPriceSystemId = this.atributesIndexed[(HIGH_PRICE_SYSTEM_ID<<1)+1];
		this.price = this.minPrice + (this.maxPrice - this.minPrice) / 2;
		final boolean var4 = false;
		this.unsaleable = var4;
	}

	public final boolean canBeInstalledMultipleTimes() {
		return installableMultipleTimes[this.subType];
	}

	public final int getIndex() {
		return this.id;
	}
	/** PRIMARY = 0; SECONDARY = 1; TURRET = 2; EQUIPMENT = 3;**/
	public final int getType() {
		return this.type;
	}

	public final int getTecLevel() {
		return this.tecLevel;
	}

	public final int getSort() {
		return this.subType;
	}

	public final int getSinglePrice() {
		return this.price;
	}

	public final int getTotalPrice() {
		return this.price * this.amount;
	}

	public final int getMaxPrice() {
		return this.maxPrice;
	}

	public final int getMinPrice() {
		return this.minPrice;
	}

	public final int getHighestPriceSystemId() {
		return this.highestPriceSystemId;
	}

	public final int getLowestPriceSystemId() {
		return this.lowestPriceSystemId;
	}

	public final void setPrice(final int var1) {
		this.price = var1;
	}

	public final void setAmount(final int var1) {
		this.amount = var1;
	}

	public final int getOccurance() {
		return this.occurance;
	}

	public final int getAmount() {
		return this.amount;
	}

	public final void changeAmount(final int var1) {
		this.amount += var1;
	}

	public final void setStationAmount(final int var1) {
		this.stationAmount = var1;
	}

	public final int getStationAmount() {
		return this.stationAmount;
	}

	private void changeStationAmount(final int var1) {
		this.stationAmount += var1;
	}

	public final void setBlueprintAmount(final int var1) {
		this.blueprintAmount = var1;
	}

	public final int getBlueprintAmount() {
		return this.blueprintAmount;
	}

	private void incBlueprintAmount(final int var1) {
		this.blueprintAmount += var1;
	}

	public final int[] getBluePrintComponentsIds() {
		return this.blueprintComponentsIds;
	}

	public final int[] getBlueprintComponentsQuantities() {
		return this.blueprintComponentsQuantities;
	}

	public final int getAttribute(final int var1) {
		for(int i = 0; i < this.atributesIndexed.length; i += 2) {
			if (this.atributesIndexed[i] == var1) {
				return this.atributesIndexed[i + 1];
			}
		}

		return NULL_ATTRIBUTE;
	}

	public final int transaction(final boolean var1) {
		int var3;
		if (var1 && this.stationAmount > 0 && Status.getCredits() >= this.price) {
			changeStationAmount(-1);
			changeAmount(1);
			var3 = -this.price;
		} else {
			if (var1 || this.amount <= 0) {
				return 0;
			}

			changeAmount(-1);
			changeStationAmount(1);
			var3 = this.price;
		}

		return var3;
	}

	public final int transactionBlueprint(final boolean var1) {
		if (var1 && this.blueprintAmount > 0) {
			incBlueprintAmount(-1);
			changeAmount(1);
			return -this.price;
		}
		if (!var1 && this.amount > 0) {
			changeAmount(-1);
			incBlueprintAmount(1);
			return this.price;
		} else {
			return 0;
		}
	}

	public static boolean isInList(final int var0, final int var1, final Item[] var2) {
		if (var2 == null) {
			return false;
		}
		for(int i = 0; i < var2.length; ++i) {
			if (var2[i].id == var0 && var2[i].amount >= var1) {
				return true;
			}
		}

		return false;
	}

	public static Item[] extractItems(final Item[] var0, final boolean var1) {
		if (var0 == null) {
			return null;
		}
		final Item[] var2 = new Item[var0.length];
		final Item[] var3 = new Item[var0.length];
		System.arraycopy(var0, 0, var3, 0, var0.length);
		int var6 = 0;

		for(int i = 0; i < var3.length; ++i) {
			if (var1 && var3[i].amount > 0) {
				var2[var6] = var3[i].makeItem(var3[i].amount);
				++var6;
			} else if (!var1 && var3[i].stationAmount > 0) {
				var2[var6] = var3[i].makeItem(var3[i].stationAmount);
				++var6;
			}
		}

		final Item[] var7 = new Item[var6];
		System.arraycopy(var2, 0, var7, 0, var6);
		if (var7.length == 0) {
			return null;
		} else {
			return var7;
		}
	}

	public static Item[] combineItems(Item[] var0, final Item[] var1) {
		int var2 = var0 == null ? 0 : var0.length;
		int var3 = var1 == null ? 0 : var1.length;
		final Item[] var4 = new Item[var2 + var3];
		if (var2 > 0 && var3 == 0) {
			for(var3 = 0; var3 < var0.length; ++var3) {
				var4[var3] = var0[var3].makeItem(var0[var3].amount);
			}

			return var4;
		}
		Item var10000;
		if (var3 > 0 && var2 == 0) {
			for(var3 = 0; var3 < var1.length; ++var3) {
				var4[var3] = var1[var3].makeItem(0);
				var10000 = var4[var3];
				var2 = var1[var3].amount;
				var10000.stationAmount = var2;
			}

			return var4;
		} else if (var3 == 0 && var2 == 0) {
			return null;
		} else {
			for(var3 = 0; var3 < var0.length; ++var3) {
				var4[var3] = var0[var3].makeItem(var0[var3].amount);
			}

			var3 = var2;

			for(int i = 0; i < var1.length; ++i) {
				for(var2 = 0; var2 < var4.length; ++var2) {
					if (var4[var2] == null) {
						var4[var2] = var1[i].makeItem(0);
						var10000 = var4[var2];
						var2 = var1[i].amount;
						var10000.stationAmount = var2;
						++var3;
						break;
					}

					if (var1[i].equals(var4[var2])) {
						var4[var2] = var1[i].makeItem(var4[var2].amount);
						var10000 = var4[var2];
						var2 = var1[i].amount;
						var10000.stationAmount = var2;
						break;
					}
				}
			}

			var0 = new Item[var3];
			System.arraycopy(var4, 0, var0, 0, var3);

			boolean var8;
			do {
				var8 = true;

				for(int i = 1; i < var0.length; ++i) {
					if (var0[i - 1].id > var0[i].id) {
						final Item var9 = var0[i - 1];
						var0[i - 1] = var0[i];
						var0[i] = var9;
						var8 = false;
					}
				}
			} while(!var8);

			return var0;
		}
	}

	public static Item[] mixItems(final Item[] var0, final Item[] var1) {
		if (var0 == null) {
			return var1;
		}
		if (var1 == null) {
			return var0;
		} else {
			final Item[] var2 = new Item[var1.length];

			for(int i = 0; i < var1.length; ++i) {
				var2[i] = var1[i];
			}

			Item[] var7 = null;
			int var6 = var2.length;

			int var4;
			int var5;
			for(var4 = 0; var4 < var0.length; ++var4) {
				for(var5 = 0; var5 < var2.length; ++var5) {
					if (var2[var5] != null && var0[var4].id == var2[var5].id) {
						var0[var4].changeAmount(var2[var5].amount);
						--var6;
						var2[var5] = null;
					}
				}
			}

			if (var6 <= 0) {
				return var0;
			} else {
				var7 = new Item[var6];
				var4 = 0;

				for(var5 = 0; var5 < var2.length; ++var5) {
					if (var2[var5] != null) {
						var7[var4] = var2[var5];
						var4++;
					}
				}

				final Item[] var8 = new Item[var0.length + var7.length];

				for(var6 = 0; var6 < var0.length; ++var6) {
					var8[var6] = var0[var6];
				}

				for(var6 = 0; var6 < var7.length; ++var6) {
					var8[var6 + var0.length] = var7[var6];
				}

				return var8;
			}
		}
	}

	public final boolean isWeapon() {
		return this.type == 0 || this.type == 1 || this.type == 2;
	}

	public final Item makeItem(final int amount) {
		return getCopyInAmmount(amount, this.price);
	}

	public final Item getCopyInAmmount(final int amount, final int price) {
		Item copy = new Item(this.blueprintComponentsIds, this.blueprintComponentsQuantities, this.atributesIndexed);
		copy.price = this.price;
		copy.unsaleable = this.unsaleable;
		copy.price = price;
		copy.amount = amount;
		return copy;
	}

	public final Item makeItem() {
		return getCopyInAmmount(1, this.price);
	}

	public final boolean equals(final Item var1) {
		if (var1 == null) {
			return false;
		}
		return this.id == var1.id;
	}

	public final String toString() {
		return Globals.getItemName(this.id);
	}

	public final boolean setUnsaleable() {
		return this.unsaleable;
	}

	public final void setUnsaleable(final boolean var1) {
		this.unsaleable = var1;
	}
}
