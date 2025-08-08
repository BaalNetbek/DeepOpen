package GOF2;

import javax.microedition.lcdui.Image;
/**
 * 
 * @author Fishlabs 2009
 */
public final class ListItem {
	private Agent unused_owner__;
	public BluePrint bluePrint;
	public Ship ship;
	public Item item;
	private Mission unused;
	public boolean isSelectable;
	public String label;
	private String unused_description__;
	public int medalTier = -1;
	public int itemId = 0;
	public int showCountItemType = -1;
	public Image items;
	private boolean nonFancyLabel;
	public int inTabIndex;
	public PendingProduct producedGood;

	public ListItem(final ListItem var1) {
		this.unused_owner__ = var1.unused_owner__;
		this.bluePrint = var1.bluePrint;
		this.ship = var1.ship;
		this.item = var1.item;
		this.unused = var1.unused;
		this.isSelectable = var1.isSelectable;
		this.label = var1.label;
		this.unused_description__ = var1.unused_description__;
		this.medalTier = var1.medalTier;
		this.itemId = var1.itemId;
		this.showCountItemType = var1.showCountItemType;
		this.items = var1.items;
		this.nonFancyLabel = var1.nonFancyLabel;
		this.inTabIndex = var1.inTabIndex;
		this.producedGood = var1.producedGood;
	}

	public ListItem(final BluePrint var1) {
		this.bluePrint = var1;
		this.isSelectable = true;
	}

	public ListItem(final Ship var1) {
		this.ship = var1;
		this.isSelectable = true;
	}

	public ListItem(final Item var1) {
		this.item = var1;
		this.isSelectable = true;
	}

	public ListItem(final PendingProduct var1) {
		this.producedGood = var1;
		this.isSelectable = true;
	}

	public ListItem(final String var1, final boolean var2, final int var3) {
		this.label = var1;
		this.nonFancyLabel = true;
		this.showCountItemType = var3;
		this.isSelectable = var2;
	}

	public ListItem(final String var1) {
		this.label = var1;
		this.isSelectable = false;
	}

	public ListItem(final String var1, final int var2) {
		this.label = var1;
		this.showCountItemType = var2;
		this.isSelectable = false;
	}

	public ListItem(final int var1) {
		this.medalTier = var1;
		this.isSelectable = true;
	}

	public ListItem(final int var1, final int var2) {
		this.itemId = var1;
		this.medalTier = var2;
		this.isSelectable = true;
	}

	public final boolean isShip() {
		return this.ship != null;
	}

	public final boolean isItem() {
		return this.item != null;
	}

	private boolean hasItem() {
		return this.item != null;
	}

	public final boolean isHeader() {
		return !this.isSelectable && this.label != null && !this.nonFancyLabel;
	}

	public final boolean isMedal__() {
		return this.medalTier >= 0;
	}

	public final boolean isNonFancyHeader() {
		return this.nonFancyLabel;
	}

	public final boolean isMoveToCargoButton() {
		return this.nonFancyLabel && this.showCountItemType == 0;
	}

	public final boolean isSellButton() {
		return this.nonFancyLabel && this.showCountItemType == 1;
	}

	public final boolean isBluePrint() {
		return this.bluePrint != null;
	}

	public final boolean isPendingProduct() {
		return this.producedGood != null;
	}

	public final int getPrice() {
		if (isShip()) {
			return this.ship.getPrice();
		}
		if (isItem()) {
			return this.item.getSinglePrice();
		} else {
			return hasItem() ? this.item.getSinglePrice() : 0;
		}
	}

	public final int getIndex() {
		if (isShip()) {
			return this.ship.getIndex();
		}
		if (isItem() || hasItem()) {
			return this.item.getIndex();
		} else if (isBluePrint()) {
			return this.bluePrint.productId;
		} else {
			return isPendingProduct() ? this.producedGood.index : 999999;
		}
	}
}
