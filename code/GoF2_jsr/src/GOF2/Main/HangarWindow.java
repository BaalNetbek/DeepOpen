package GOF2.Main;

import AE.GlobalStatus;
import GOF2.Item;
import GOF2.Layout;
import GOF2.ListItem;
import GOF2.Popup;
import GOF2.Status;

public final class HangarWindow {
	private static int initTab = 1;
	private long timeOpen;
	private int frameTime;
	private boolean loaded;
	private HangarList list;
	private ListItem selectedItem;
	private ListItemWindow itemInfo;
	private Popup unused__3a1_;
	private Popup freeSlotInfoPopup;
	private boolean freeSlotInfoPopupOpen;
	private int tabsWidth_unused_;
	private int[] tabWidths_unused_;
	private int state__;
	private int speedSellTick;
	private int speedBuyTick;
	private int openedBluePrint;

	public final void OnRelease() {
		this.tabWidths_unused_ = null;
		if (this.list != null) {
			this.list.OnRelease();
		}

		this.list = null;
		this.selectedItem = null;
		this.itemInfo = null;
		this.unused__3a1_ = null;
		this.freeSlotInfoPopup = null;
		this.loaded = false;
		System.gc();
	}

	public final void initialize() {
		this.list = new HangarList(new String[]{GlobalStatus.gameText.getText(77), GlobalStatus.gameText.getText(79), GlobalStatus.gameText.getText(130), "", ""}, GlobalStatus.gameText.getText(62));
		this.list.initShipTab(Status.getShip());
		Status.calcCargoPrices();
		final Item[] var1 = Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems());
		this.list.initShopTab(var1);
		this.list.initBlueprintTab(Status.getBluePrints());
		if (var1 != null) {
			for(int i = 0; i < var1.length; ++i) {
				Item var3;
				final int var4 = (var3 = var1[i]).getSinglePrice();
				final int var5 = var3.getIndex();
				if (var4 > Status.highestItemPrices[var5] || Status.highestItemPrices[var5] == 0) {
					Status.highestItemPrices[var5] = var4;
					Status.highestItemPriceSystems[var5] = (byte)Status.getSystem().getIndex();
				}

				if (var4 < Status.lowestItemPrices[var5] || Status.lowestItemPrices[var5] == 0) {
					Status.lowestItemPrices[var5] = var4;
					Status.lowestItemPriceSystems[var5] = (byte)Status.getSystem().getIndex();
				}
			}
		}

		Status.getShip().adjustPrice();
		this.itemInfo = new ListItemWindow();
		this.unused__3a1_ = new Popup(20, GlobalStatus.screenHeight / 3, GlobalStatus.screenWidth - 40);
		this.state__ = 0;
		this.freeSlotInfoPopup = new Popup(10, GlobalStatus.screenHeight / 3, GlobalStatus.screenWidth - 20);
		this.tabsWidth_unused_ = GlobalStatus.screenWidth - 10;
		this.tabWidths_unused_ = new int[3];
		this.tabWidths_unused_[0] = this.tabsWidth_unused_ / 3 - 2;
		this.tabWidths_unused_[1] = this.tabWidths_unused_[0];
		this.tabWidths_unused_[2] = this.tabsWidth_unused_ - this.tabWidths_unused_[0] - this.tabWidths_unused_[1] - 4;
		this.list.setCurrentTab(initTab);
		System.gc();
		this.loaded = true;
	}

	public final int getCurrentTab() {
		return this.list.getCurrentTab();
	}

	public final boolean inRoot() {
		return this.state__ == 0 && !this.list.browsingInterrupted_();
	}

	public final ListItem getSelectedItem() {
		return this.selectedItem;
	}

	public final boolean handleKeyPressed(int var1) {
		this.selectedItem = (ListItem)this.list.getSelectedItem();
		if (var1 == 256) {
			if (this.freeSlotInfoPopupOpen && this.timeOpen > 200L) {
				this.freeSlotInfoPopupOpen = false;
				return true;
			}

			if (this.state__ == 4) {
				this.state__ = 0;
			} else if (this.state__ == 0) {
				if (this.list.getCurrentTab() == 2) {
					this.openedBluePrint = this.list.getSelectedEntry();
				}

				this.list.selectItem();
			} else if (this.state__ != 1) {
				if (this.state__ == 2) {
					this.state__ = 0;
				} else if (this.state__ == 3) {
					this.state__ = 0;
					this.list.initShipTab(Status.getShip());
				}
			}
		}

		if (this.freeSlotInfoPopupOpen) {
			return true;
		}
		if (var1 == 2 && this.state__ == 0) {
			this.list.scrollUp();
		}

		if (var1 == 64 && this.state__ == 0) {
			this.list.scrollDown();
		}

		if (var1 == 4 && (this.state__ == 4 || this.state__ == 0)) {
			this.list.leftAction();
		}

		if (var1 == 32 && (this.state__ == 4 || this.state__ == 0)) {
			this.list.rightAction();
		}

		if (var1 == 8192) {
			if (this.state__ == 4) {
				initTab = this.list.getCurrentTab();
				return false;
			}

			if (this.state__ == 0 && !this.list.popupOpen) {
				if (this.list.isTrading()) {
					this.list.cancelTransaction(false);
					return true;
				}

				if (this.list.getCurrentTab() == 3) {
					this.list.setCurrentTab(0);
					return true;
				}

				if (this.list.getCurrentTab() != 4) {
					if (this.list.browsingInterrupted_()) {
						return true;
					}

					this.itemInfo.updateCamera_(false);
					return false;
				}

				if (!this.list.browsingInterrupted_()) {
					this.list.setCurrentTab(2);

					for(var1 = 0; var1 < this.openedBluePrint - 1; ++var1) {
						this.list.scrollDown();
					}

					this.list.draw();
				}

				return true;
			}

			if (this.state__ == 1) {
				this.state__ = 0;
			} else if (this.state__ == 5) {
				this.state__ = 4;
			}
		}

		if (var1 == 16384) {
			if (this.state__ == 4) {
				this.state__ = 5;
			} else if (this.state__ == 0 && this.selectedItem != null) {
				if (this.selectedItem.isItem() || this.selectedItem.isShip() || this.selectedItem.isBluePrint() || this.selectedItem.isPendingProduct()) {
					this.itemInfo.setup((ListItem)this.list.getSelectedItem(), this.list.items, this.list.itemTypes, this.list.ships, this.list.shipsColor, true);
					this.state__ = 1;
				}

				if (this.selectedItem != null) {
					if (this.selectedItem.isMedal__()) {
						final String var2 = GlobalStatus.gameText.getText(82);
						this.freeSlotInfoPopup.set(var2, false);
						this.freeSlotInfoPopupOpen = true;
					} else {
						this.itemInfo.updateCamera_(this.selectedItem.isShip());
					}
				}
			}
		}

		return true;
	}

	public final boolean draw(final int var1, final int var2) {
		if (!this.loaded) {
			return true;
		}
		this.frameTime = var2;
		this.timeOpen += var2;
		if (this.state__ == 1 && this.itemInfo.shows3DShip()) {
			this.itemInfo.updateRotation(var1, var2);
		}

		if (this.state__ == 0) {
			this.list.draw();
			this.list.drawFrame();
		}

		if (this.state__ == 1) {
			if (this.itemInfo.shows3DShip()) {
				this.itemInfo.render();
			}

			this.itemInfo.draw();
		} else if (this.state__ == 2) {
			this.list.draw();
			this.list.drawFrame();
			this.unused__3a1_.draw();
		}

		label154: {
			String var10000;
			String var10001;
			if (!this.freeSlotInfoPopupOpen && this.state__ != 2) {
				label155: {
					if (this.state__ == 1 && this.list.getCurrentTab() == 0 && !this.selectedItem.isShip()) {
						var10000 = "";
					} else {
						if (this.state__ == 0 && (this.list.getSelectedItem() == null || !((ListItem)this.list.getSelectedItem()).isSelectable)) {
							Layout.drawFooter("", this.state__ == 0 && this.list.isTrading() && !this.list.popupOpen ? GlobalStatus.gameText.getText(246) : GlobalStatus.gameText.getText(65));
							break label154;
						}

						var10000 = this.state__ == 1 ? "" : this.list.getCurrentTab() == 3 && !((ListItem)this.list.getSelectedItem()).isItem() && !((ListItem)this.list.getSelectedItem()).isShip() ? "" : GlobalStatus.gameText.getText(212);
						if (this.state__ == 0 && this.list.isTrading() && !this.list.popupOpen) {
							var10001 = GlobalStatus.gameText.getText(246);
							break label155;
						}
					}

					var10001 = GlobalStatus.gameText.getText(65);
				}
			} else {
				var10000 = "";
				var10001 = "";
			}

			Layout.drawFooter(var10000, var10001);
		}

		if (this.freeSlotInfoPopupOpen) {
			this.freeSlotInfoPopup.draw();
		}

		this.selectedItem = (ListItem)this.list.getSelectedItem();
		if (this.freeSlotInfoPopupOpen) {
		} else {
			if (this.state__ == 0 && this.list.browsingInterrupted_()) {
				if ((var1 & 4) == 0) {
					this.speedSellTick = 0;
				} else {
					this.speedSellTick += var2;
				}

				if ((var1 & 32) == 0) {
					this.speedBuyTick = 0;
				} else {
					this.speedBuyTick += var2;
				}

				if (this.speedSellTick > 500) {
					this.list.skipSell();
					this.speedSellTick = 0;
				}

				if (this.speedBuyTick > 500) {
					this.list.skipBuy();
					this.speedBuyTick = 0;
				}
			}

			if ((var1 & 2) != 0 && this.state__ == 1) {
				this.itemInfo.scrollDown(var2);
			}

			if ((var1 & 64) != 0 && this.state__ == 1) {
				this.itemInfo.scrollUp(var2);
			}
		}
		return true;
	}
}
