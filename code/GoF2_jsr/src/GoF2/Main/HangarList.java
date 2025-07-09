package GoF2.Main;

import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import GoF2.BluePrint;
import GoF2.Globals;
import GoF2.Item;
import GoF2.Layout;
import GoF2.ListItem;
import GoF2.Popup;
import GoF2.ProducedGood;
import GoF2.Ship;
import GoF2.Status;

public class HangarList extends TabbedWindow_ {
	public Image itemTypes;
	public Image items;
	public Image ships;
	public Image shipsColor;
	protected String unused_2_;
	private Image raceLogo;
	private Image itemTypesSel;
	private boolean trading_;
	public boolean popupOpen;
	private final Popup popup;
	private int[] stationItemTypeCounts;
	private int shipLoad;
	private Item[] stationItems;
	private BluePrint bluePrint;
	private int mountingSlot;
	private int preMountingScrollPos;
	private boolean isIngredientShippingPopup;
	private boolean isShipBuyPopup;
	private int amountToPutInBluePrint;
	private int lastStationAmount;
	private int lastOnShipAmount;
	private int lastBluePrintAmount;
	private int credits;
	private int lastShipLoad;

	public HangarList(final String[] tabNames, final String title) {
		this(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight, tabNames, title);
	}

	private HangarList(final int posX, final int posY, final int width, final int height, final String[] tabNames, final String title) {
		super(posX, posY, width, height, tabNames, title);
		this.trading_ = false;
		this.popup = new Popup();
		if (this.raceLogo == null) {
			this.raceLogo = AEFile.loadImage("/data/interface/logo_" + Status.getSystem().getRace() + "_relief.png", true);
		}

	}

	public final void OnRelease() {
		this.items 			= null;
		this.itemTypes 	= null;
		this.ships 			= null;
		this.stationItems = null;
	}

	public final void initShipTab(final Ship ship) {
		if (this.items == null) {
			try {
				this.items 			= AEFile.loadImage("/data/interface/items.png", 			true);
				this.itemTypes 	= AEFile.loadImage("/data/interface/item_types.png", 		true);
				this.itemTypesSel = AEFile.loadImage("/data/interface/item_types_sel.png", true);
				this.ships 			= AEFile.loadImage("/data/interface/ships.png", 			true);
				this.shipsColor 	= AEFile.loadImage("/data/interface/ships_color.png", 	true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		final ListItem[] listItems = new ListItem[ship.getEquipment().length + 2 + ship.getSlotTypes()];

		int listStep = 0;
		listItems[listStep++] = new ListItem(GlobalStatus.gameText.getText(77)); //Ship
		listItems[listStep++] = new ListItem(ship);
		for(int i = 0; i < 4; ++i) {
			if (ship.getSlots(i) > 0) {
				final String sectionName = GlobalStatus.gameText.getText(i == 0 ? 123 : //Primary weapons
																							i == 1 ? 124 : //Secondary weapons
																							i == 2 ? 125 : //Turrets
																								    	127); //Equipment
				listItems[listStep++] = new ListItem(sectionName, i);
				final Item[] var5 = ship.getEquipment(i);

				for(int var6 = 0; var6 < var5.length; listItems[listStep - 1].inTabIndex = var6++) {
					if (var5[var6] != null) {
						listItems[listStep++] = new ListItem(var5[var6]);
					} else {
						listItems[listStep++] = new ListItem(i);
					}
				}
			}
		}

		this.perTabEntries[0] = listItems;
		if (this.perTabEntries[0] == null) {
			this.listsLengths[0] = 0;
		} else if (this.perTabEntries[0].length == 0) {
			this.perTabEntries[0] = null;
			this.listsLengths[0] = 0;
		} else {
			this.listsLengths[0] = this.perTabEntries[0].length;
		}

		this.displayedEntriesCount = this.perTabEntries[0].length;
		this.listsLengths[0] = this.displayedEntriesCount;
		updateScroll();
	}

	public final void initBlueprintTab(final BluePrint[] bluePrints) {
		if (bluePrints != null) {
			int listLen = 1;

			for(int i = 0; i < bluePrints.length; ++i) {
				if (bluePrints[i].unlocked) {
					++listLen;
				}
			}

			int temp = listLen;
			boolean goodsReady = false;
			ProducedGood[] waitingGoods = Status.getWaitingGoods();
			if (waitingGoods != null) {
				for(int i = 0; i < waitingGoods.length; ++i) {
					if (waitingGoods[i] != null) {
						++listLen;
					}
				}

				if (listLen > temp) {
					++listLen;
					goodsReady = true;
				}
			}

			ListItem[] listItems = new ListItem[listLen];
			
			int listStep = 0;
			listItems[listStep++] = new ListItem(GlobalStatus.gameText.getText(131));

			for(int i = 0; i < bluePrints.length; ++i) {
				if (bluePrints[i].unlocked) {
					listItems[listStep++] = new ListItem(bluePrints[i]);
				}
			}

			if (goodsReady) {
				listItems[listStep++] = new ListItem(GlobalStatus.gameText.getText(132));
				
				for(int i = 0; i < waitingGoods.length; ++i) {
					if (waitingGoods[i] != null) {
						listItems[listStep++] = new ListItem(waitingGoods[i]);
					}
				}
			}

			super.setEntries(2, listItems);
			this.displayedEntriesCount = this.perTabEntries[2].length;
			this.listsLengths[2] = this.displayedEntriesCount;
			updateScroll();
		}
	}

	public final void initShopTab(final Item[] var1) {
		this.stationItems = var1;
		if (var1 != null) {
			this.stationItemTypeCounts = new int[5];

			for(int i = 0; i < var1.length; ++i) {
				this.stationItemTypeCounts[var1[i].getType()]++;
			}

			int var2 = 0;

			for(int i = 0; i < this.stationItemTypeCounts.length; ++i) {
				if (this.stationItemTypeCounts[i] > 0) {
					++var2;
				}
			}

			Ship[] var6 = Status.getStation().getShopShips();
			if (var6 != null) {
				var2 += 1 + var6.length;
			}

			final ListItem[] listItems = new ListItem[var1.length + var2];
			int listStep = 0;
			if (var6 != null) {
				listItems[listStep++] = new ListItem(GlobalStatus.gameText.getText(68), -1);

				for(int i = 0; i < var6.length; ++i) {
					listItems[listStep++] = new ListItem(var6[i]);
				}
			}

			for(int i = 0; i < 5; ++i) {
				if (this.stationItemTypeCounts[i] > 0) {
					final String text = GlobalStatus.gameText.getText(i == 0 ? 123 :
																						i == 1 ? 124 :
																						i == 2 ? 125 :
																						i == 3 ? 127 :
																									128);
					listItems[listStep++] = new ListItem(text, i);

					for(int j = 0; j < var1.length; ++j) {
						if (var1[j].getType() == i) {
							listItems[listStep++] = new ListItem(var1[j]);
						}
					}
				}
			}

			super.setEntries(1, listItems);
			this.shipLoad = Status.getShip().getCurrentLoad();
			this.displayedEntriesCount = this.perTabEntries[1].length;
			this.listsLengths[1] = this.displayedEntriesCount;
			updateScroll();
		}
	}

	private void initMountingWindow(final ListItem var1) {
		this.perTabEntries[3] = null;
		this.listsLengths[3] = 0;
		final boolean var2 = var1.isShip();
		int var3 = var1.medalTier;
		if (!var2 && !var1.isMedal__()) {
			var3 = var1.item.getType();
		}

		int var4 = 0;
		int var6;
		if (var2) {
			Ship[] var5 = Status.getStation().getShopShips();
			if (var5 != null) {
				var4 = var5.length;
			}
		} else {
			Item[] var10 = Status.getShip().getCargo();
			if (var10 != null) {
				for(var6 = 0; var6 < var10.length; ++var6) {
					if (var10[var6].getType() == var3) {
						++var4;
					}
				}
			}
		}

		final int var11 = !var2 && !var1.isMedal__() ? 2 : 0;
		var6 = 2 + (var4 == 0 ? 2 : 1) + (var11 == 0 ? 0 : 1);
		ListItem[] var12;
		(var12 = new ListItem[var4 + var6 + var11])[0] = new ListItem(GlobalStatus.gameText.getText(135));
		var12[1] = new ListItem(var1);
		var6 = 2;
		if (!var2 && !var1.isMedal__()) {
			var12[var6++] = new ListItem(GlobalStatus.gameText.getText(136));
			var12[var6++] = new ListItem(GlobalStatus.gameText.getText(137), true, 0);
			var12[var6++] = new ListItem(GlobalStatus.gameText.getText(138), true, 1);
		}

		var12[var6++] = new ListItem(var2 ? GlobalStatus.gameText.getText(140) :
													GlobalStatus.gameText.getText(139));
		if (var4 > 0) {
			int var9;
			if (var2) {
				final Ship[] var7 = Status.getStation().getShopShips();

				for(var9 = 0; var9 < var7.length; ++var9) {
					var12[var6++] = new ListItem(var7[var9]);
				}
			} else {
				final Item[] var8 = Status.getShip().getCargo();

				for(var9 = 0; var9 < var8.length; ++var9) {
					if (var8[var9].getType() == var3) {
						var12[var6++] = new ListItem(var8[var9]);
					}
				}
			}
		} else {
			var12[var6] = new ListItem(GlobalStatus.gameText.getText(141), false, -1);
		}

		super.setEntries(3, var12);
		this.displayedEntriesCount = this.perTabEntries[3].length;
		this.listsLengths[3] = this.displayedEntriesCount;
		updateScroll();
	}

	private void fillIngredientsList(final BluePrint var1) {
		this.bluePrint = var1;
		this.perTabEntries[4] = null;
		this.listsLengths[4] = 0;
		final Item[] var3 = Status.getShip().getCargo();
		final Item[] var4 = Globals.getItems();
		int[] var9;
		final ListItem[] var10 = new ListItem[(var9 = var1.getIngredientList()).length + 1];
		int var5 = 0;

		for(int var6 = 0; var6 < var9.length; ++var6) {
			boolean var7 = false;
			if (var3 != null) {
				for(int var8 = 0; var8 < var3.length; ++var8) {
					if (var3[var8].getIndex() == var9[var6]) {
						var10[var5] = new ListItem(var3[var8]);
						var5++;
						var3[var8].setBlueprintAmount(0);
						var7 = true;
						break;
					}
				}
			}

			if (!var7) {
				var10[var5] = new ListItem(var4[var9[var6]]);
				var5++;
				var4[var9[var6]].setBlueprintAmount(0);
			}
		}

		super.setEntries(4, var10);
		this.displayedEntriesCount = this.perTabEntries[4].length;
		this.listsLengths[4] = this.displayedEntriesCount;
		updateScroll();
	}

	public final boolean browsingInterrupted_() {
		return this.trading_ || this.popupOpen;
	}

	public final boolean isTrading() {
		return this.trading_;
	}

	public final void cancelTransaction(final boolean var1) {
		if (this.trading_) {
			Item var2;
			(var2 = ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).setStationAmount(this.lastStationAmount);
			var2.setAmount(this.lastOnShipAmount);
			var2.setBlueprintAmount(this.lastBluePrintAmount);
			Status.setCredits(this.credits);
			this.shipLoad = this.lastShipLoad;
		}

		this.lastStationAmount = 0;
		this.lastOnShipAmount = 0;
		this.lastBluePrintAmount = 0;
		this.trading_ = false;
	}

	public final void leftAction() {
		if (this.trading_ && !this.popupOpen) {
			if (this.selectedTab == 1) {
				int var1 = ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.transaction(false);
				if (var1 > 0) {
					--this.shipLoad;
				}

				Status.changeCredits(var1);
			} else if (this.selectedTab == 4) {
				int var2;
				Item var4;
				var2 = (var4 = ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).transactionBlueprint(true);
				if (var2 > 0) {
					--this.shipLoad;
				} else if (var2 != 0) {
					--this.amountToPutInBluePrint;
				}

				Item[] var5;
				if ((var5 = Status.getShip().getCargo()) != null) {
					for(int var3 = 0; var3 < var5.length; ++var3) {
						if (var5[var3].getIndex() == var4.getIndex()) {
							var5[var3].setAmount(var4.getAmount());
							var5[var3].setBlueprintAmount(var4.getBlueprintAmount());
						}
					}
				}
			}

			updateScroll();
		} else if (!this.popupOpen && this.selectedTab < 3) {
			super.leftAction();
		} else if (this.popupOpen) {
			this.popup.left();
		}
	}

	public final void rightAction() {
		if (this.trading_ && !this.popupOpen) {
			if (this.selectedTab == 1) {
				int var1 = ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.transaction(true);
				if (var1 < 0) {
					++this.shipLoad;
				}

				Status.changeCredits(var1);
			} else if (this.selectedTab == 4) {
				Item var4;
				if ((var4 = ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).getBlueprintAmount() < this.bluePrint.getRemainingAmount(var4.getIndex())) {
					int var2;
					if ((var2 = var4.transactionBlueprint(false)) < 0) {
						++this.shipLoad;
					} else if (var2 != 0) {
						++this.amountToPutInBluePrint;
					}
				}

				Item[] var5 = Status.getShip().getCargo();
				if (var5 != null) {
					for(int var3 = 0; var3 < var5.length; ++var3) {
						if (var5[var3].getIndex() == var4.getIndex()) {
							var5[var3].setAmount(var4.getAmount());
							var5[var3].setBlueprintAmount(var4.getBlueprintAmount());
						}
					}
				}
			}

			updateScroll();
		} else if (!this.popupOpen) {
			if (this.selectedTab < 2) {
				super.rightAction();
			}
		} else {
			this.popup.right();
		}
	}

	public final void skipSell() {
		if (this.trading_) {
			for(int var1 = 0; var1 < 10; ++var1) {
				leftAction();
			}
		}

	}

	public final void skipBuy() {
		if (this.trading_) {
			for(int var1 = 0; var1 < 10; ++var1) {
				rightAction();
			}
		}

	}

	public final void scrollUp() {
		if (!this.trading_ && !this.popupOpen) {
			boolean var1 = false;
			int var2 = this.selectedEntry;

			for(int var3 = this.selectedEntry; var3 > 0; --var3) {
				if (((ListItem)this.perTabEntries[this.selectedTab][var3 - 1]).isSelectable) {
					var1 = true;
					break;
				}

				--var2;
			}

			if (this.selectedEntry == 0 || this.selectedEntry == 1 && !((ListItem)this.perTabEntries[this.selectedTab][0]).isSelectable) {
				var2 = this.listsLengths[this.selectedTab] - 1;
				this.selectedEntry = this.listsLengths[this.selectedTab] - 1;
				this.scrollPos = this.listsLengths[this.selectedTab] - this.displayedEntriesCount - 1;
			}

			if (var1) {
				super.scrollUp();
			}

			if (var2 > 0 && !((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).isSelectable) {
				scrollUp();
			}

			updateScroll();
		}

	}

	public final void scrollDown() {
		if (!this.trading_ && !this.popupOpen) {
			boolean var1 = false;
			int var2 = this.selectedEntry;

			for(int var3 = this.selectedEntry; var3 < this.listsLengths[this.selectedTab] - 1; ++var3) {
				if (((ListItem)this.perTabEntries[this.selectedTab][var3 + 1]).isSelectable) {
					var1 = true;
					break;
				}

				++var2;
			}

			if (this.selectedEntry == this.listsLengths[this.selectedTab] - 1 || this.selectedEntry == this.listsLengths[this.selectedTab] - 3 && !((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry + 1]).isSelectable && !((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry + 2]).isSelectable) {
				var2 = 0;
				this.selectedEntry = 0;
				this.scrollPos = 0;
			}

			if (var1) {
				super.scrollDown();
			}

			if (var2 < this.listsLengths[this.selectedTab] - 1 && !((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).isSelectable) {
				scrollDown();
			}

			updateScroll();
		}

	}

	public final int selectItem() {
		ListItem var1;
		int var3;
		int var18;
		String var22;
		if (this.popupOpen) {
			this.popupOpen = false;
			if (this.selectedTab == 1 && !this.isShipBuyPopup) {
				return 0;
			}

			if (this.trading_) {
				this.trading_ = this.popup.getChoice();
			}

			if (!this.isIngredientShippingPopup) {
				if (!this.isShipBuyPopup) {
					return 0;
				}

				this.isShipBuyPopup = false;
				this.popupOpen = false;
				if (this.popup.getChoice()) {
					var1 = (ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry];
					Status.changeCredits(Status.getShip().getPrice() - var1.ship.getPrice());
					Ship var13;
					final Item[] var16 = (var13 = Status.getShip()).getEquipment();
					final Item[] var21 = var13.getCargo();
					Status.setShip(Globals.getShips()[var1.ship.getIndex()].cloneBase());
					Status.getShip().setRace(var1.ship.getRace());
					Status.getShip().adjustPrice();
					Status.getShip().setCargo(var21);
					if (var16 != null) {
						for(int var19 = 0; var19 < var16.length; ++var19) {
							if (var16[var19] != null) {
								Status.getShip().addCargo(var16[var19]);
							}
						}
					}

					final Ship[] var23 = Status.getStation().getShopShips();

					for(var18 = 0; var18 < var23.length; ++var18) {
						if (var23[var18].getIndex() == var1.ship.getIndex()) {
							var23[var18] = Globals.getShips()[var13.getIndex()].cloneBase();
							var23[var18].setRace(var13.getRace());
							break;
						}
					}

					initShipTab(Status.getShip());
					initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
					setCurrentTab(0);
					var22 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(143)), GlobalStatus.gameText.getText(532 + var1.ship.getIndex()), "#N");
					this.popup.setAsWarning(var22);
					this.popupOpen = true;
					((ModStation)GlobalStatus.scenes[1]).getCutScene().replacePlayerShip(Status.getShip().getIndex(), Status.getShip().getRace());
				}

				return 0;
			}

			this.isIngredientShippingPopup = false;
			if (this.popup.getChoice()) {
				if (!this.trading_) {
					Status.changeCredits(-((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.getBlueprintAmount() * 10);
					this.trading_ = true;
					final int var2 = this.amountToPutInBluePrint;

					for(var3 = 0; var3 < var2; ++var3) {
						leftAction();
					}

					this.trading_ = false;
					return 0;
				}
			} else {
				this.trading_ = true;
				cancelTransaction(false);
			}

			this.amountToPutInBluePrint = 0;
			this.trading_ = true;
		}

		if (this.perTabEntries[this.selectedTab] == null || this.perTabEntries[this.selectedTab][this.selectedEntry] == null) {
			return 0;
		}
		var1 = (ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry];
		if (this.selectedTab == 4 && this.trading_ && this.amountToPutInBluePrint > 0 && !this.popupOpen && !this.bluePrint.isStarted() && this.bluePrint.getProductionStationId() != Status.getStation().getId()) {
			final String var11 = Status.replaceTokens(Status.replaceTokens(new String(GlobalStatus.gameText.getText(142)), this.bluePrint.getProductionStationName(), "#S"), Layout.formatCredits(var1.item.getBlueprintAmount() * 10), "#C");
			this.popup.set(var11, true);
			this.popupOpen = true;
			this.isIngredientShippingPopup = true;
		} else {
			if (this.selectedTab == 0) {
				this.mountingSlot = this.selectedEntry;
				this.preMountingScrollPos = this.scrollPos;
				initMountingWindow(var1);
				setCurrentTab(3);
				if (!GlobalStatus.itemMounting2HelpShown && var1.isMedal__()) {
					this.popup.setAsWarning(GlobalStatus.gameText.getText(301));
					GlobalStatus.itemMounting2HelpShown = true;
					this.popupOpen = true;
				}
			} else {
				Item var5;
				if (this.selectedTab == 1) {
					if (var1.isShip()) {
						if (var1.ship.getPrice() > Status.getCredits() + Status.getShip().getPrice()) {
							this.popup.setAsWarning(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var1.getPrice() - Status.getCredits() - Status.getShip().getPrice()), "#C"));
							this.popupOpen = true;
							return 0;
						}

						if (Status.passengerCount > 0) {
							this.popup.setAsWarning(GlobalStatus.gameText.getText(161));
							this.popupOpen = true;
							return 0;
						}

						this.isShipBuyPopup = true;
						this.popup.set(GlobalStatus.gameText.getText(144), true);
						this.popupOpen = true;
						return 0;
					}

					if (var1.item.setUnsaleable()) {
						this.popup.setAsWarning(GlobalStatus.gameText.getText(160));
						this.popupOpen = true;
					} else if (var1.item.getAmount() == 0 && Status.getCredits() < var1.item.getSinglePrice()) {
						this.popup.setAsWarning(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var1.item.getSinglePrice() - Status.getCredits()), "#C"));
						this.popupOpen = true;
					} else {
						this.trading_ = !this.trading_;
						if (!this.trading_) {
							if (var1.isItem() && var1.item.getType() != 4 && !GlobalStatus.itemMountingHelpShown) {
								this.popup.setAsWarning(GlobalStatus.gameText.getText(303));
								GlobalStatus.itemMountingHelpShown = true;
								this.popupOpen = true;
							}

							if (var1.getIndex() >= 132 && var1.getIndex() < 154) {
								Status.drinkTypesPossesed[var1.getIndex() - 132] = true;
							}

							Item[] var9;
							if (var1.isItem() && var1.item.getType() == 1 && var1.item.getAmount() > 0 && (var9 = Status.getShip().getEquipment(1)) != null) {
								for(var3 = 0; var3 < var9.length; ++var3) {
									Item var20 = var9[var3];
									if (var20 != null && var20.getIndex() == var1.item.getIndex()) {
										var5 = var1.item.makeItem(var1.item.getAmount() + var20.getAmount());
										if (this.stationItems != null) {
											for(var18 = 0; var18 < this.stationItems.length; ++var18) {
												if (this.stationItems[var18].getIndex() == var5.getIndex()) {
													this.stationItems[var18].setAmount(0);
												}
											}
										}

										Status.getShip().setEquipment(var5, var3);
										Status.getShip().removeCargo(var5.getIndex(), var5.getAmount());
										initShipTab(Status.getShip());
										var22 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(87)), GlobalStatus.gameText.getText(569 + var5.getIndex()), "#N");
										this.popup.setAsWarning(var22);
										this.popupOpen = true;
										break;
									}
								}
							}

							Status.getShip().setCargo(Item.extractItems(this.stationItems, true));
							Status.getStation().setShopItems(Item.extractItems(this.stationItems, false));
							initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
							updateScroll();
						} else {
							if (!GlobalStatus.buyingHelpShown) {
								this.popup.setAsWarning(GlobalStatus.gameText.getText(302));
								GlobalStatus.buyingHelpShown = true;
								this.popupOpen = true;
							}

							this.lastStationAmount = var1.item.getStationAmount();
							this.lastOnShipAmount = var1.item.getAmount();
							this.credits = Status.getCredits();
							this.lastShipLoad = this.shipLoad;
						}
					}
				} else if (this.selectedTab == 2 && !var1.isPendingProduct()) {
					fillIngredientsList(var1.bluePrint);
					setCurrentTab(4);
				} else {
					String var4;
					boolean var10;
					if (this.selectedTab == 3) {
						if (this.selectedEntry > 2) {
							Item var7 = ((ListItem)this.perTabEntries[this.selectedTab][1]).item;
							if (var7 != null && var7.setUnsaleable()) {
								this.popup.setAsWarning(GlobalStatus.gameText.getText(160));
								this.popupOpen = true;
								return 0;
							}

							if (var7 != null && var7.getSort() == 20 && Status.passengerCount > 0) {
								this.popup.setAsWarning(GlobalStatus.gameText.getText(160));
								this.popupOpen = true;
								return 0;
							}

							if (var1.isShip()) {
								if (var1.ship.getPrice() > Status.getCredits() + Status.getShip().getPrice()) {
									this.popup.setAsWarning(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var1.getPrice() - Status.getCredits() - Status.getShip().getPrice()), "#C"));
									this.popupOpen = true;
									return 0;
								}

								if (Status.passengerCount > 0) {
									this.popup.setAsWarning(GlobalStatus.gameText.getText(161));
									this.popupOpen = true;
									return 0;
								}

								this.isShipBuyPopup = true;
								this.popup.set(GlobalStatus.gameText.getText(144), true);
								this.popupOpen = true;
								return 0;
							}

							if (var1.isNonFancyHeader()) { // HangarWindow:::demountItem() - gof2hd
								var10 = var7.getType() == 1;
								if (var1.isSellButton()) {
									var4 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(88)), GlobalStatus.gameText.getText(569 + var7.getIndex()), "#N");
									this.popup.setAsWarning(var4);
									var5 = var7.makeItem(var10 ? var7.getAmount() : 1);
									Status.getShip().addCargo(var5);
									Status.getShip().freeSlot(var7);
									initShipTab(Status.getShip());
									initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
									setCurrentTab(0);
								} else if (var1.isMoveToCargoButton()) {
									var4 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(86)), GlobalStatus.gameText.getText(569 + var7.getIndex()), "#N");
									this.popup.setAsWarning(var4);
									var5 = var7.makeItem(var10 ? var7.getAmount() : 1);
									Status.getStation().addItem(var5);
									Status.changeCredits(var7.getTotalPrice());
									Status.getShip().freeSlot(var7);
									initShipTab(Status.getShip());
									initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
									setCurrentTab(0);
								}

								this.popupOpen = true;
							} else {
								final ListItem var12 = (ListItem)this.perTabEntries[this.selectedTab][1];
								if (Status.getShip().getFirstEquipmentOfSort(var1.item.getSort()) != null && !var1.item.canBeInstalledMultipleTimes() && (!var12.isItem() || var12.item.getSort() != var1.item.getSort())) {
									var4 = new String(GlobalStatus.gameText.getText(164));
									this.popup.setAsWarning(var4);
									this.popupOpen = true;
									return 0;
								}

								Item var6;
								boolean var14;
								String var15;
								if (var12.isMedal__()) {
									var14 = var1.item.getType() == 1;
									var15 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(87)), GlobalStatus.gameText.getText(569 + var1.item.getIndex()), "#N");
									this.popup.setAsWarning(var15);
									var6 = var1.item.makeItem(var14 ? var1.item.getAmount() : 1);
									Status.getShip().setEquipment(var6, var12.inTabIndex);
									Status.getShip().removeCargo(var6.getIndex(), var14 ? var6.getAmount() : 1);
									initShipTab(Status.getShip());
									initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
									setCurrentTab(0);
								} else {
									var14 = var12.item.getType() == 1;
									var15 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(87)), GlobalStatus.gameText.getText(569 + var1.item.getIndex()), "#N");
									this.popup.setAsWarning(var15);
									var6 = var12.item.makeItem(var14 ? var12.item.getAmount() : 1);
									Status.getShip().addCargo(var6);
									Status.getShip().freeSlot(var12.item, var12.inTabIndex);
									Status.getShip().setEquipment(var1.item.makeItem(var14 ? var1.item.getAmount() : 1), var12.inTabIndex);
									Status.getShip().removeCargo(var1.item.getIndex(), var14 ? var1.item.getAmount() : 1);
									initShipTab(Status.getShip());
									initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
									setCurrentTab(0);
								}
							}

							this.selectedEntry = this.mountingSlot;
							this.scrollPos = this.preMountingScrollPos;
						}
					} else if (this.selectedTab == 4) {
						if (this.trading_) {
							boolean var8 = false;
							var10 = false;
							this.bluePrint.startProduction(var1.item, var1.item.getBlueprintAmount(), Status.getStation().getId());
							if (this.bluePrint.isComplete()) {
								if (this.bluePrint.getProductionStationId() != Status.getStation().getId()) {
									var4 = Status.replaceTokens(Status.replaceTokens(new String(GlobalStatus.gameText.getText(89)), GlobalStatus.gameText.getText(569 + this.bluePrint.getIndex()), "#N"), this.bluePrint.getProductionStationName(), "#S");
									this.popup.setAsWarning(var4);
									Status.appendProduced(this.bluePrint);
									setCurrentTab(2);
								} else {
									var4 = Status.replaceTokens(new String(GlobalStatus.gameText.getText(90)), GlobalStatus.gameText.getText(569 + this.bluePrint.getIndex()), "#N");
									this.popup.setAsWarning(var4);
									var5 = Globals.getItems()[this.bluePrint.getIndex()].makeItem(this.bluePrint.getTonsPerProduction2());
									Status.getShip().addCargo(var5);
									setCurrentTab(1);
								}

								var10 = true;
								this.bluePrint.finishProduction();
								var8 = true;
							}

							this.trading_ = false;
							Status.getShip().setCargo(Item.extractItems(Status.getShip().getCargo(), true));
							initShopTab(Item.combineItems(Status.getShip().getCargo(), Status.getStation().getShopItems()));
							initBlueprintTab(Status.getBluePrints());
							updateScroll();
							if (var8) {
								for(int var17 = 0; var17 < this.perTabEntries[this.selectedTab].length; ++var17) {
									scrollDown();
									if (!((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).isBluePrint() && ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).getIndex() == this.bluePrint.getIndex()) {
										break;
									}
								}
							}

							if (var10) {
								this.popupOpen = true;
							}

							return 0;
						}

						this.amountToPutInBluePrint = 0;
						if (this.bluePrint.isStarted() && ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.getAmount() > 0) {
							this.popup.set(GlobalStatus.gameText.getText(91), true);
							this.popupOpen = true;
						}

						this.lastBluePrintAmount = var1.item.getBlueprintAmount();
						this.lastOnShipAmount = var1.item.getAmount();
						this.credits = Status.getCredits();
						this.lastShipLoad = this.shipLoad;
						this.trading_ = !this.trading_;
					}
				}
			}
		}
		return 0;
	}

	public final void updateScroll() {
		if (this.perTabEntries.length >= 3) {
			if (this.selectedTab == 1) {
				this.itemListPosY = this.posY + ITEM_HEIGHT + this.tabHeight + this.rowHeight + 2;
			} else {
				this.itemListPosY = this.posY + ITEM_HEIGHT + this.tabHeight;
			}
		}

		super.updateScroll();
		if (this.listsLengths[this.selectedTab] > 0 && this.selectedEntry == 0 && !(this.listsLengths[this.selectedTab] == 0 ? false : ((ListItem)this.perTabEntries[this.selectedTab][this.selectedEntry]).isSelectable)) {
			++this.selectedEntry;
			super.updateScroll();
		}

	}

	public void draw() {
		drawBG();
		Layout.drawFilledTitleBarWindow(this.titleBarText, this.posX, this.posY, this.width, this.height);
		GlobalStatus.graphics.drawImage(this.raceLogo, GlobalStatus.screenWidth - 20, GlobalStatus.screenHeight - 30 - 16, 40);
		if (this.selectedTab > 2) {
			this.tabHeight = 1;
		} else {
			this.tabHeight = Font.getFontSpacingY() + 4;
		}

		drawTabs();
		drawItems();
		drawScroll();
		if (this.popupOpen) {
			this.popup.draw();
		}

	}

	public void drawFrame() {
		GlobalStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
		GlobalStatus.graphics.fillRect(this.posX + 2, this.posY + this.height - ITEM_HEIGHT, this.width - 4, (ITEM_HEIGHT - 2));
		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - (ITEM_HEIGHT + 1), this.width - 3, this.posY + this.height - (ITEM_HEIGHT + 1));
		GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 1, this.width - 3, this.posY + this.height - 1);
		GlobalStatus.graphics.drawRect(this.posX + 3, this.posY + this.height - (ITEM_HEIGHT - 1), this.width - 6, 10);
		GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(this.posX + 2, this.posY + this.height - ITEM_HEIGHT, this.width - 4, (ITEM_HEIGHT - 2));
		boolean var1 = this.shipLoad > Status.getShip().getCargoPlus();
		if (var1 && Layout.quickTickHigh_() || !var1) {
			Font.drawString(this.shipLoad + "/" + Status.getShip().getCargoPlus() + "t", this.innerLeftMargin, this.posY + this.height - ITEM_HEIGHT, var1 ? 2 : 1, 17);
		}

		Font.drawString(Layout.formatCredits(Status.getCredits()), this.posX + this.width - 3, this.posY + this.height - 13, 1, 18);
	}

	protected final void drawTabs() {
		if (this.perTabEntries.length < 3) {
			super.drawTabs();
		} else if (this.showTabs) {
			if (this.selectedTab > 2) {
				return;
			}

			GlobalStatus.graphics.setColor(0);
			GlobalStatus.graphics.drawLine(this.posX + this.width / 3,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width / 3,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			
			GlobalStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 1,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width - this.width / 3 - 1,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
			GlobalStatus.graphics.drawLine(this.posX + this.width / 3 - 1,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width / 3 - 1,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			
			GlobalStatus.graphics.drawLine(this.posX + this.width / 3 + 1,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width / 3 + 1,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			
			GlobalStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 2,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width - this.width / 3 - 2,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			
			GlobalStatus.graphics.drawLine(this.posX + this.width - this.width / 3,
														this.posY + ITEM_HEIGHT,
														this.posX + this.width - this.width / 3,
														this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			switch(this.selectedTab) {
			case 0:
				GlobalStatus.graphics.drawLine(this.posX + this.width / 3 - 1,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
															this.posX + this.width - 3,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
				break;
			case 1:
				GlobalStatus.graphics.drawLine(this.posX + 3,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
															this.posX + this.width / 3 + 1,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
				GlobalStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 2,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
															this.posX + this.width - 3,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
				break;
			case 2:
				GlobalStatus.graphics.drawLine(this.posX + 3,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
															this.posX + this.width - this.width / 3,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			}

			if (this.selectedTab != 0) {
				GlobalStatus.graphics.setColor(0);
				GlobalStatus.graphics.fillRect(this.posX + 3,
															this.posY + ITEM_HEIGHT + 1,
															this.width / 3 - 4,
															this.posY + ITEM_HEIGHT - 2);
				GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
				GlobalStatus.graphics.fillRect(this.posX + 4,
															this.posY + ITEM_HEIGHT + 2,
															this.width / 3 - 6,
															this.posY + ITEM_HEIGHT - 3);
			}

			if (this.selectedTab != 1) {
				GlobalStatus.graphics.setColor(0);
				GlobalStatus.graphics.fillRect(this.posX + this.width / 3 + 2,
															this.posY + ITEM_HEIGHT + 1,
															this.width / 3 - 2,
															this.posY + ITEM_HEIGHT - 2);
				GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
				GlobalStatus.graphics.fillRect(this.posX + this.width / 3 + 3,
															this.posY + ITEM_HEIGHT + 2,
															this.width / 3 - 4,
															this.posY + ITEM_HEIGHT - 3);
			}

			if (this.selectedTab != 2) {
				GlobalStatus.graphics.setColor(0);
				GlobalStatus.graphics.fillRect(this.posX + this.width - this.width / 3 + 1,
															this.posY + ITEM_HEIGHT + 1,
															this.width / 3 - 3,
															this.posY + ITEM_HEIGHT - 2);
				GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
				GlobalStatus.graphics.fillRect(this.posX + this.width - this.width / 3 + 2,
															this.posY + ITEM_HEIGHT + 2,
															this.width / 3 - 5,
															this.posY + ITEM_HEIGHT - 3);
			}

			Layout.drawMenuPanelCorner(this.posX + 2,
												this.posY + ITEM_HEIGHT,
												this.selectedTab == 0);
			Layout.drawMenuPanelCorner(this.posX + this.width / 3 + 1,
												this.posY + ITEM_HEIGHT,
												this.selectedTab == 1);
			Layout.drawMenuPanelCorner(this.posX + this.width - this.width / 3,
												this.posY + ITEM_HEIGHT,
												this.selectedTab == 2);
			Font.drawString(this.tabNames[0],
									this.posX + this.width / 6,
									this.posY + ITEM_HEIGHT + 1,
									this.selectedTab == 0 ? 2 : 1,
									24);
			Font.drawString(this.tabNames[1],
									this.posX + this.width / 2,
									this.posY + ITEM_HEIGHT + 1,
									this.selectedTab == 1 ? 2 : 1,
									24);
			Font.drawString(this.tabNames[2],
									this.posX + this.width - this.width / 6,
									this.posY + ITEM_HEIGHT + 1,
									this.selectedTab == 2 ? 2 : 1, 24);
		}
	}

	public final void drawItems() {
		if (this.perTabEntries[this.selectedTab] != null) {
			if (this.perTabEntries.length < 3 || this.selectedTab != 1 && this.selectedTab != 4) {
				this.itemListPosY = this.posY + ITEM_HEIGHT + this.tabHeight;
			} else {
				final String var1 = GlobalStatus.gameText.getText(this.selectedTab == 1 ? 40 : 78);
				final String var2 = this.selectedTab == 1 ? GlobalStatus.gameText.getText(78) : GlobalStatus.gameText.getText(129);
				this.itemListPosY = this.posY + ITEM_HEIGHT + this.tabHeight + this.rowHeight - 1;
				GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
				GlobalStatus.graphics.drawLine(this.posX + 3, this.itemListPosY - 2, this.posX + this.width - 3, this.itemListPosY - 2);
				Font.drawString(var1, this.innerLeftMargin + 3, this.itemListPosY - this.rowHeight + 3, 0);
				Font.drawString(var2, this.posX + this.width - 4 - 2, this.itemListPosY - this.rowHeight + 3, 0, 18);
			}

			int var4 = -1;
			if (this.scrollPos == -1) {
				this.scrollPos = 0;
			}

			for(int var5 = this.scrollPos; var5 < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][var5] != null && var5 < this.scrollPos + this.displayedEntriesCount + 1; ++var5) {
				if (((ListItem)this.perTabEntries[this.selectedTab][var5]).isHeader()) {
					if (this.selectedEntry > var5) {
						var4 = var5;
					}

					GlobalStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
					GlobalStatus.graphics.fillRect(this.posX + 7, this.itemListPosY + (var5 - this.scrollPos) * this.rowHeight + 3, this.width - 7 - this.listRightPadding - 7, this.rowHeight - 4);
					GlobalStatus.graphics.setColor(0);
					GlobalStatus.graphics.drawRect(this.posX + 7, this.itemListPosY + (var5 - this.scrollPos) * this.rowHeight + 3, this.width - 7 - this.listRightPadding - 7, this.rowHeight - 4);
					GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
					GlobalStatus.graphics.drawRect(this.posX + 6, this.itemListPosY + (var5 - this.scrollPos) * this.rowHeight + 2, this.width - 7 - this.listRightPadding - 5, this.rowHeight - 2);
					Layout.drawMenuPanelCorner(this.posX + 6, this.itemListPosY + (var5 - this.scrollPos) * this.rowHeight + 2, false);
				}

				drawItem(this.perTabEntries[this.selectedTab][var5], var5);
			}

			if (var4 >= 0) {
				Layout.drawMenuPanelCorner(this.posX + 6, this.itemListPosY + (var4 - this.scrollPos) * this.rowHeight + 2, true);
			}

		}
	}

	protected void drawItem(final Object var1, final int var2) {
		final ListItem var9 = (ListItem)var1;
		String var3;
		String var10000;
		int var10001;
		int var10002;
		int var10003;
		if (this.selectedTab != 0 && this.selectedTab != 3) {
			if (this.selectedTab != 1 && this.selectedTab != 4) {
				if (this.selectedTab != 2) {
					return;
				}

				if (var9.isHeader()) {
					var10000 = var9.label;
					var10001 = this.innerLeftMargin + 3;
					var10002 = this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 5;
					var10003 = 0;
				} else {
					if (!var9.isBluePrint() && !var9.isPendingProduct()) {
						return;
					}

					ImageFactory.drawItem(var9.getIndex(), Globals.getItems()[var9.getIndex()].getType(), this.items, this.itemTypes, this.innerLeftMargin + 1, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					if (var2 == this.selectedEntry) {
						GlobalStatus.graphics.drawImage(this.itemTypesSel, this.innerLeftMargin + 1, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					}

					if (var9.isBluePrint()) {
						boolean var10 = false;
						final Item[] var11 = Status.getShip().getCargo();
						final int[] var5 = var9.bluePrint.getIngredientList();
						final int[] var6 = var9.bluePrint.missingComponentsTons;
						if (var11 != null) {
							for(int i = 0; i < var5.length && !var10; ++i) {
								if (var6[i] > 0) {
									for(int var8 = 0; var8 < var11.length; ++var8) {
										if (var11[var8].getIndex() == var5[i]) {
											var10 = true;
											break;
										}
									}
								}
							}
						}

						Layout.drawStringWidthLimited((var9.bluePrint.getTonsPerProduction2() > 1 ? var9.bluePrint.getTonsPerProduction() + "x " : "") + Globals.getItemName(var9.getIndex()), this.innerLeftMargin + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - ImageFactory.itemFrameWidth - 20 - 9 - this.listRightPadding + 2, this.selectedEntry == var2 ? 2 : 0);
						if (!var10 || var10 && Layout.quickTickHigh_()) {
							Font.drawString((int)(var9.bluePrint.getCompletionRate() * 100.0F) + "%", this.posX + this.width - 6 - this.listRightPadding, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
						}

						if (!var9.bluePrint.isStarted()) {
							Font.drawString(GlobalStatus.gameText.getText(133) + " " + var9.bluePrint.getProductionStationName(), this.innerLeftMargin + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 9, this.selectedEntry == var2 ? 2 : 1);
						}

						return;
					}

					Font.drawString((var9.producedGood.producedQuantity > 1 ? var9.producedGood.producedQuantity + "x " : "") + Globals.getItemName(var9.getIndex()), this.innerLeftMargin + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
					var10000 = GlobalStatus.gameText.getText(133) + " " + var9.producedGood.stationName;
					var10001 = this.innerLeftMargin + ImageFactory.itemFrameWidth + 6;
					var10002 = this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 9;
					var10003 = this.selectedEntry == var2 ? 2 : 1;
				}
			} else {
				if (!var9.isHeader()) {
					if (var9.isNonFancyHeader()) {
						Font.drawString(var9.label, this.innerLeftMargin + 3, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 5, this.selectedEntry == var2 ? 2 : 1);
						return;
					}

					if (var9.isShip()) {
						ImageFactory.drawShip(var9.ship.getIndex(), var9.ship.getRace(), this.ships, this.shipsColor, this.innerLeftMargin + 3 + 20, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
						Font.drawString(GlobalStatus.gameText.getText(532 + var9.ship.getIndex()), this.innerLeftMargin + 20 + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
						Font.drawString(Layout.formatCredits(var9.ship.getPrice()) + " (" + Layout.formatCredits(var9.ship.getPrice() - Status.getShip().getPrice()) + ")", this.innerLeftMargin + 20 + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
						return;
					}

					var3 = this.selectedTab == 1 ? var9.item.getStationAmount() + "" : var9.item.getAmount() + "";
					final String var4 = this.selectedTab == 1 ? var9.item.getAmount() + "" : this.bluePrint.getCurrentAmount(var9.item.getIndex()) + var9.item.getBlueprintAmount() + "/" + this.bluePrint.getTotalAmount(var9.item.getIndex());
					ImageFactory.drawItem(var9.item.getIndex(), var9.item.getType(), this.items, this.itemTypes, this.innerLeftMargin + 3 + 20, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					if (var2 == this.selectedEntry) {
						GlobalStatus.graphics.drawImage(this.itemTypesSel, this.innerLeftMargin + 3 + 20, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					}

					Font.drawString(var3, this.innerLeftMargin + 20, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
					Layout.drawStringWidthLimited(Globals.getItemName(var9.getIndex()), this.innerLeftMargin + 20 + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - ImageFactory.itemFrameWidth - 40 - 9 - this.listRightPadding + 2 - (this.selectedTab == 4 ? 10 : 0), this.selectedEntry == var2 ? 2 : 0);
					Font.drawString(Layout.formatCredits(var9.item.getSinglePrice()), this.innerLeftMargin + 20 + ImageFactory.itemFrameWidth + 6, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
					Font.drawString(var4, this.posX + this.width - 6 - this.listRightPadding, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
					if (this.trading_ && !this.popupOpen && var2 == this.selectedEntry && Layout.quickTickHigh_()) {
						Font.drawString("<", this.innerLeftMargin + 20, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, 2, 18);
						Font.drawString(">", this.posX + this.width - 6 - this.listRightPadding, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, 2, 18);
					}

					return;
				}

				var10000 = var9.label + (var9.showCountItemType >= 0 ? " (" + this.stationItemTypeCounts[var9.showCountItemType] + ")" : "");
				var10001 = this.innerLeftMargin + 3;
				var10002 = this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 5;
				var10003 = 0;
			}
		} else {
			if (var9.isNonFancyHeader()) {
				Font.drawString(var9.label, this.innerLeftMargin + 3, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 5, this.selectedEntry == var2 ? 2 : 1);
				return;
			}

			if (!var9.isHeader()) {
				if (var9.isShip()) {
					ImageFactory.drawShip(var9.ship.getIndex(), var9.ship.getRace(), this.ships, this.shipsColor, this.innerLeftMargin + 1, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					Font.drawString(GlobalStatus.gameText.getText(532 + var9.ship.getIndex()), this.innerLeftMargin + ImageFactory.itemFrameWidth + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
					Font.drawString(Layout.formatCredits(var9.ship.getPrice()), this.innerLeftMargin + ImageFactory.itemFrameWidth + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
					return;
				}

				if (var9.isItem()) {
					ImageFactory.drawItem(var9.item.getIndex(), var9.item.getType(), this.items, this.itemTypes, this.innerLeftMargin + 1, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					if (var2 == this.selectedEntry) {
						GlobalStatus.graphics.drawImage(this.itemTypesSel, this.innerLeftMargin + 1, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + getRowHeight() / 2 + 1, 6);
					}

					var3 = Globals.getItemName(var9.item.getIndex());
					if (var9.item.getType() == 1) {
						var3 = var3 + " (" + var9.item.getAmount() + ")";
					}

					Layout.drawStringWidthLimited(var3, this.innerLeftMargin + ImageFactory.itemFrameWidth + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - ImageFactory.itemFrameWidth - 9 - this.listRightPadding + 2, this.selectedEntry == var2 ? 2 : 0);
					Font.drawString(Layout.formatCredits(var9.item.getSinglePrice()), this.innerLeftMargin + ImageFactory.itemFrameWidth + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
					return;
				}

				if (var9.isMedal__()) {
					Font.drawString(GlobalStatus.gameText.getText(69), this.innerLeftMargin + ImageFactory.itemFrameWidth + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 3, this.selectedEntry == var2 ? 2 : 1);
				}

				return;
			}

			var10000 = var9.label + (var9.showCountItemType >= 0 ? " (" + Status.getShip().getUsedSlots(var9.showCountItemType) + "/" + Status.getShip().getSlots(var9.showCountItemType) + ")" : "");
			var10001 = this.innerLeftMargin + 5;
			var10002 = this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 5;
			var10003 = 0;
		}

		Font.drawString(var10000, var10001, var10002, var10003);
	}

	public final int getRowHeight() {
		return 20;
	}

	public final int getTabHeight() {
		return Font.getFontSpacingY() + 4;
	}
}
