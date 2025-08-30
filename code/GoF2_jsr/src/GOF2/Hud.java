package GOF2;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import AE.AEFile;
import AE.GlobalStatus;
import AE.Time;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import GOF2.Main.GOF2Canvas;

public final class Hud {
	private final String[][] actionmenuLabels = {
			{"", "", "", ""},
			{"", "", "", ""},
			{"", "", "", ""},
			{"", "", "", ""},
			{"", "", "", ""}};
	private byte[][] actionmenuButtonsState;
	private static int lastWingmenAction;
	private static int drawSecondaryIcon = -1;
	private int screenMidX;
	private int hullAlertMSDx;
	private int hullAlertLSDx;
	private int hullAlertPercentX;
	private Image symbolShield;
	private Image symbolHull;
	private Image hit;
	private Sprite hudIcons;
	private Sprite quickmenuCrosshairAnim;
	private Sprite quickMenu;
	private Image hullBarEmpty;
	private Image hullBarFull;
	private Sprite panelUpperLeft;
	private Sprite panelLowerLeft;
	private Sprite hullAlarmNumbers;
	private Image hullAlarm;
	private Image hullAlarmShipIcon;
	public Image items;
	private Image factionLogo;
	private int hullDamageTick;
	private String tempLogMsg;
	private String logMsg = "";
	private boolean drawBoostIcon;
	private boolean hasShield;
	private boolean hasArmor;
	private boolean hasWeapon;
	private int lastHullDamage;
	public boolean drawUI;
	private boolean cargoFull;
	private boolean actionmenuOpen;
	private int actionSubMenuOpen;
	private int actionmenuSelectDir;
	private int hudIconsHeight;
	private boolean playerHit;
	private boolean actionmenuConverging;
	private boolean menuReady;
	private boolean settingSecondaryWeapon;
	private int menuItemSelectTick;
	private int menuItemsSpread;
	private int menuAnimStep;
	private Item[] secondaries;
	private Item[] cloakAndDrive;
	private ListItem[] logMessages;
	private int firstLogEntryLifeTime;
	private boolean displayLog_;
	private int queueScroll;
	private boolean jumpDriveSelected;

	public Hud() {
		init();
	}

	private void init() {
		try {
			this.logMessages = new ListItem[20];
			this.items = AEFile.loadImage("/data/interface/items.png", true);
			this.hullAlarmNumbers = new Sprite(AEFile.loadImage("/data/interface/hud_hull_alarm_numbers.png", true), 13, 12);
			this.hullAlarm = AEFile.loadImage("/data/interface/hud_hull_alarm.png", true);
			this.symbolShield = AEFile.loadImage("/data/interface/hud_symbol_shield_png24.png", true);
			this.symbolHull = AEFile.loadImage("/data/interface/hud_symbol_hull_png24.png", true);
			this.hullBarEmpty = AEFile.loadImage("/data/interface/hud_hull_bar_empty_png24.png", true);
			this.hullBarFull = AEFile.loadImage("/data/interface/hud_hull_bar_full_png24.png", true);
			//AEFile.loadImage("/data/interface/hud_hull_bar_hit_png24.png", true); //unused
			Image var7 = AEFile.loadImage("/data/interface/hud_panel_upper_left_png24.png", true);
			this.panelUpperLeft = new Sprite(var7);
			var7 = AEFile.loadImage("/data/interface/hud_panel_lower_left_png24.png", true);
			this.panelLowerLeft = new Sprite(var7);
			var7 = AEFile.loadImage("/data/interface/hud_icons.png", true);
			this.hudIconsHeight = var7.getHeight();
			this.hudIcons = new Sprite(var7, this.hudIconsHeight, this.hudIconsHeight);
			var7 = AEFile.loadImage("/data/interface/quickmenu_crosshair_anim.png", true);
			this.quickmenuCrosshairAnim = new Sprite(var7, var7.getHeight(), var7.getHeight());
			this.quickmenuCrosshairAnim.defineReferencePixel(this.quickmenuCrosshairAnim.getWidth() >> 1, this.quickmenuCrosshairAnim.getHeight() >> 1);
			var7 = AEFile.loadImage("/data/interface/quickmenu.png", true);
			this.quickMenu = new Sprite(var7, var7.getHeight(), var7.getHeight());
			this.quickMenu.defineReferencePixel(this.quickMenu.getWidth() >> 1, this.quickMenu.getHeight() >> 1);
			this.hit = AEFile.loadImage("/data/interface/hud_hit_png24.png", true);
			this.hullAlertMSDx = GlobalStatus.screenWidth / 2 - 21;
			this.hullAlertLSDx = this.hullAlertMSDx + 14;
			this.hullAlertPercentX = this.hullAlertLSDx + 14;
			this.screenMidX = GlobalStatus.screenWidth >> 1;
			this.drawBoostIcon = Status.getShip().getBoostDelay() > 0;
			this.hasShield = Status.getShip().getShield() > 0;
			this.hasArmor = Status.getShip().getAdditionalArmour() > 0;
			this.hasWeapon = Status.getShip().getFirePower() > 0;
			this.actionmenuButtonsState = new byte[5][4];
			final Item[] secondaries = Status.getShip().getEquipment(Item.SECONDARY);
			boolean noSecondaries = true;
			if (secondaries != null) {
				for(int i = 0; i < secondaries.length; ++i) {
					if (secondaries[i] != null) {
						noSecondaries = false;
						break;
					}
				}
			}

			if (!noSecondaries) {
				this.actionmenuButtonsState[0][0] = 1;
				initSecondariesSubMenu();
				this.actionmenuLabels[0][0] = GlobalStatus.gameText.getText(124);
			} else {
				this.actionmenuLabels[0][0] = "";
				this.actionmenuButtonsState[0][0] = 0;
			}

			final Item cloak = Status.getShip().getFirstEquipmentOfSort(Item.CLOAK);
			final Item drive = Status.getShip().getFirstEquipmentOfSort(Item.JUMP_DRIVE);
			if (cloak == null && drive == null) {
				this.cloakAndDrive = null;
			} else {
				this.cloakAndDrive = new Item[cloak != null && drive != null ? 2 : 1];
			}

			int var5 = 0;
			if (cloak != null) {
				this.actionmenuButtonsState[0][1] = 1;
				this.actionmenuLabels[0][1] = GlobalStatus.gameText.getText(569 + cloak.getIndex());
				++var5;
				this.cloakAndDrive[0] = cloak;
			} else {
				this.actionmenuButtonsState[0][1] = 0;
				this.actionmenuLabels[0][1] = "";
			}

			if (drive != null) {
				this.actionmenuButtonsState[0][3] = 1;
				this.actionmenuLabels[0][3] = GlobalStatus.gameText.getText(569 + drive.getIndex());
				this.cloakAndDrive[var5] = drive;
			} else {
				this.actionmenuButtonsState[0][3] = 0;
				this.actionmenuLabels[0][3] = "";
			}

			if (Status.getWingmenNames() != null) {
				this.actionmenuButtonsState[0][2] = 1;
				this.actionmenuButtonsState[3][0] = 1;
				this.actionmenuButtonsState[3][1] = 1;
				this.actionmenuButtonsState[3][2] = 1;
				this.actionmenuButtonsState[3][3] = 1;
				this.actionmenuLabels[0][2] = GlobalStatus.gameText.getText(146);
				this.actionmenuLabels[3][0] = GlobalStatus.gameText.getText(147);
				this.actionmenuLabels[3][1] = GlobalStatus.gameText.getText(148);
				this.actionmenuLabels[3][2] = GlobalStatus.gameText.getText(149);
				this.actionmenuLabels[3][3] = GlobalStatus.gameText.getText(151);
			} else {
				this.actionmenuLabels[0][2] = "";
			}

			this.logMsg = "";
			this.cargoFull = false;
			this.actionmenuOpen = false;
			this.drawUI = true;
			this.jumpDriveSelected = false;
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void initSecondariesSubMenu() {
		this.secondaries = Status.getShip().getEquipment(Item.SECONDARY);
		final Item[] sds = this.secondaries;

		for(int i = 0; i < this.actionmenuLabels[1].length; ++i) {
			if (i < sds.length && sds[i] != null && sds[i].getAmount() > 0) {
				this.actionmenuButtonsState[1][i] = 1;
				this.actionmenuLabels[1][i] = sds[i].toString() + "(" + sds[i].getAmount() + ")";
			} else {
				if (i < sds.length && sds[i] == null) {
					this.actionmenuButtonsState[1][i] = 0;
				}

				this.actionmenuLabels[1][i] = "";
			}
		}

	}

	public final void OnRelease() {
		this.symbolHull = null;
		this.symbolShield = null;
	}

	private void drawEventQueue() {
		final int var1 = 25 - this.queueScroll;
		final int var2 = ImageFactory.itemHeight - 2;
		if (this.queueScroll > 0 && this.queueScroll < var2) {
			++this.queueScroll;
		}

		GlobalStatus.graphics.setClip(0, var2 + 25, GlobalStatus.screenWidth, var2 * 2);

		for(int i = 0; i < 4; ++i) {
			ListItem var4 = this.logMessages[i];
			if (var4 != null) {
				boolean var5;
				if (var5 = var4.items != null) {
					ImageFactory.drawItemFrameless(
					  var4.itemId,
					  var4.items,
					  (GlobalStatus.screenWidth >> 1) + ImageFactory.faceWidth / 2 - (Font.getTextWidth(var4.label, 0) >> 1) - 5,
					  var1 + i * var2 - 2,
					  Graphics.TOP | Graphics.RIGHT);
				}

				Font.drawString(
				  var4.label,
				  (GlobalStatus.screenWidth >> 1) + (var5 ? ImageFactory.faceWidth / 2 - 5 : 0),
				  var1 + i * var2, i != 1 && (i != 2 || this.queueScroll != var2) ? 1 : var4.showCountItemType == 1 ? 2 : 0,
				  Font.TOP|Font.HCENTER
				);
			}
		}

		GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
	}

	private void appendHudEvent(final ListItem var1) {
		for(int i = 1; i < this.logMessages.length; ++i) {
			if (this.logMessages[i] == null) {
				this.logMessages[i] = var1;
				this.displayLog_ = true;
				return;
			}
		}

	}

	private void updateQueue(int var1) {
		this.firstLogEntryLifeTime += var1;
		if (this.firstLogEntryLifeTime <= 4000) {
			if (this.queueScroll == 0 && this.firstLogEntryLifeTime > 2000) {
				this.queueScroll = 1;
			}

		} else {
			this.firstLogEntryLifeTime = 0;

			for(var1 = 1; var1 < this.logMessages.length; ++var1) {
				this.logMessages[var1 - 1] = this.logMessages[var1];
			}

			if (this.logMessages[1] == null) {
				this.displayLog_ = false;
			}

			this.queueScroll = 0;
		}
	}
	/** #TODO uninline */
	public final void hudEvent(final int event, final PlayerEgo ego) {
		switch(event) {
		case 1: //Auto fire on
			if (!this.hasWeapon) {
				return;
			}

			this.tempLogMsg = GlobalStatus.gameText.getText(13) + " " + GlobalStatus.gameText.getText(15);
			break;
		case 2: //Auto fire off
			if (!this.hasWeapon) {
				return;
			}

			this.tempLogMsg = GlobalStatus.gameText.getText(13) + " " + GlobalStatus.gameText.getText(16);
			break;
		case 3: //Boost
			if (!this.drawBoostIcon || !ego.readyToBoost()) {
				return;
			}

			this.tempLogMsg = GlobalStatus.gameText.getText(154);
			break;
		case 4: //Booster ready
			if (!this.drawBoostIcon) {
				return;
			}

			this.tempLogMsg = GlobalStatus.gameText.getText(155);
			break;
		case 5: //Autopilot on
			this.tempLogMsg = GlobalStatus.gameText.getText(292) + " " + GlobalStatus.gameText.getText(15);
			break;
		case 6: //Autopilot off
			this.tempLogMsg = GlobalStatus.gameText.getText(292) + " " + GlobalStatus.gameText.getText(16);
			break;
		case 7: //Autopilot is activated!
			this.tempLogMsg = GlobalStatus.gameText.getText(276);
			break;
		case 8: //Mining failed
			this.tempLogMsg = GlobalStatus.gameText.getText(263);
			break;
		case 9: //No tractor beam installed
			this.tempLogMsg = GlobalStatus.gameText.getText(264);
			break;
		case 10: //Target: [name] Station
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + Status.getStation().getName() + " " + GlobalStatus.gameText.getText(40);
			break;
		case 11: //Target: Asteroid
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + GlobalStatus.gameText.getText(274);
			break;
		case 12: //Target: Jump gate
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + GlobalStatus.gameText.getText(271);
			break;
		case 13: //Target: Waypoint
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + GlobalStatus.gameText.getText(272);
			break;
		case 14: //Target: Asteroid field
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + GlobalStatus.gameText.getText(273);
			break;
		case 15: //Target: Wormhole (unused)
			this.tempLogMsg = GlobalStatus.gameText.getText(270) + ": " + GlobalStatus.gameText.getText(269);
			break;
		case 16: //Fire at will
			this.tempLogMsg = GlobalStatus.gameText.getText(147);
			break;
		case 17: //Attack my target
			this.tempLogMsg = GlobalStatus.gameText.getText(148);
			break;
		case 18: //Secure next wapoint
			this.tempLogMsg = GlobalStatus.gameText.getText(149);
			break;
		case 19: //unknown?, unused
			this.tempLogMsg = this.actionmenuLabels[4][3];
			break;
		case 20: //No drill installed.
			this.tempLogMsg = GlobalStatus.gameText.getText(265);
			break;
		case 21: //Not possible while on a mission.
			this.tempLogMsg = GlobalStatus.gameText.getText(254);
			break;
		case 22: //Nothing to salvage.
			this.tempLogMsg = GlobalStatus.gameText.getText(266);
			break;
		case 23: //Waypoint reached
			this.tempLogMsg = GlobalStatus.gameText.getText(267);
			break;
		case 24: //Last waypoint reached
			this.tempLogMsg = GlobalStatus.gameText.getText(268);
		}

		if (!sameHudEventAsBeforeAggregate(this.tempLogMsg)) {
			appendHudEvent(new ListItem(this.tempLogMsg));
			Font.getTextWidth(this.tempLogMsg, 0);
		}
	}

	private boolean sameHudEventAsBeforeAggregate(final String eventText) {
		for(int i = this.logMessages.length - 1; i > 0; --i) {
			if (this.logMessages[i] != null && this.logMessages[i].label.equals(eventText)) {
				return true;
			}
		}

		return false;
	}

	public final void playerHit() {
		this.playerHit = true;
	}

	public final void catchCargo(final int itemId, final int ammount, final boolean fullCargo, final boolean missionItem, final boolean uncountable, final boolean var6) {
		this.cargoFull = fullCargo;
		ListItem var7;
		if (missionItem) {
			this.logMsg = GlobalStatus.gameText.getText(261);
			this.logMsg = Status.replaceTokens(this.logMsg, GlobalStatus.gameText.getText(569 + (Status.getMission().getType() == 3 ? 116 : 117)), "#N");
			this.logMsg = Status.replaceTokens(this.logMsg, "1", "#Q");
			var7 = new ListItem(this.logMsg);
			var7.items = this.items;
			var7.itemId = itemId;
		} else {
			if (fullCargo) {
				this.logMsg = GlobalStatus.gameText.getText(159);
				appendHudEvent(new ListItem(this.logMsg, 1));
				return;
			}

			if (ammount <= 0) {
				return;
			}

			if (uncountable) {
				this.logMsg = ammount + "t " + GlobalStatus.gameText.getText(itemId + 569);
			} else if (var6) {
				this.logMsg = GlobalStatus.gameText.getText(itemId + 569);
			} else {
				this.logMsg = ammount + "x " + GlobalStatus.gameText.getText(itemId + 569);
			}
			var7 = new ListItem(this.logMsg);
			var7.items = this.items;
			var7.itemId = itemId;
			if (var6) {
				var7.isSelectable = true;
			}
		}

		appendHudEvent(var7);
	}

	public final boolean cargoFull() {
		return this.cargoFull;
	}

	public final void draw(final long var1, final long var3, final PlayerEgo var5, final boolean var6) {
		if (this.drawUI) {
			if (this.playerHit) {
				this.playerHit = false;
				GlobalStatus.graphics.drawImage(this.hit, GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight, 33);
			}

			this.panelUpperLeft.setTransform(0);
			this.panelUpperLeft.setPosition(0, 0);
			this.panelUpperLeft.paint(GlobalStatus.graphics);
			this.panelUpperLeft.setTransform(2);
			this.panelUpperLeft.setPosition(GlobalStatus.screenWidth - this.panelUpperLeft.getWidth(), 0);
			this.panelUpperLeft.paint(GlobalStatus.graphics);
			this.panelLowerLeft.setTransform(0);
			this.panelLowerLeft.setPosition(0, GlobalStatus.screenHeight - this.panelLowerLeft.getHeight());
			this.panelLowerLeft.paint(GlobalStatus.graphics);
			this.panelLowerLeft.setTransform(2);
			this.panelLowerLeft.setPosition(GlobalStatus.screenWidth - this.panelLowerLeft.getWidth(), GlobalStatus.screenHeight - this.panelLowerLeft.getHeight());
			this.panelLowerLeft.paint(GlobalStatus.graphics);
			int var7 = this.hullBarFull.getWidth();
			int var12;
			if (this.hasArmor) {
				GlobalStatus.graphics.drawImage(this.symbolHull, 4, GlobalStatus.screenHeight - this.panelLowerLeft.getHeight() - 3, Graphics.BOTTOM|Graphics.LEFT);
				GlobalStatus.graphics.drawImage(this.hullBarEmpty, this.panelLowerLeft.getWidth(), GlobalStatus.screenHeight - 13, Graphics.BOTTOM|Graphics.RIGHT);
				var12 = (int)(var5.player.getArmorDamageRate() / 100.0F * var7);
				GlobalStatus.graphics.drawRegion(this.hullBarFull, var7 - var12, 0, var12, this.hullBarFull.getHeight(), 0, this.panelLowerLeft.getWidth(), GlobalStatus.screenHeight - 13, Graphics.BOTTOM|Graphics.RIGHT);
			}

			if (this.hasShield) {
				GlobalStatus.graphics.drawImage(this.symbolShield, GlobalStatus.screenWidth - 4, GlobalStatus.screenHeight - this.panelLowerLeft.getHeight() - 3, Graphics.BOTTOM|Graphics.RIGHT);
				GlobalStatus.graphics.drawRegion(this.hullBarEmpty, 0, 0, var7, this.hullBarFull.getHeight(), 2, GlobalStatus.screenWidth - this.panelLowerLeft.getWidth(), GlobalStatus.screenHeight - 13, Graphics.BOTTOM|Graphics.LEFT);
				var12 = (int)(var5.player.getShieldDamageRate() / 100.0F * var7);
				GlobalStatus.graphics.drawRegion(this.hullBarFull, var7 - var12, 0, var12, this.hullBarFull.getHeight(), 2, GlobalStatus.screenWidth - this.panelLowerLeft.getWidth(), GlobalStatus.screenHeight - 13, Graphics.BOTTOM|Graphics.LEFT);
			}

			if (!this.settingSecondaryWeapon && drawSecondaryIcon > 0 && (var7 = var5.getCurrentSecondaryWeaponIndex()) >= 0 && this.secondaries[drawSecondaryIcon - 1] != null) {
				ImageFactory.drawItemFrameless(var7, this.items, 0, GlobalStatus.screenHeight - Font.getSpacingY(), Graphics.BOTTOM | Graphics.LEFT);
				Font.drawString(
						"x" + this.secondaries[drawSecondaryIcon - 1].getAmount(),
						4,
						GlobalStatus.screenHeight - 2,
						1,
						Font.BOTTOM | Font.LEFT
				);
			}

			var7 = this.hudIcons.getHeight();
			if (this.drawBoostIcon) {
				if (var5.readyToBoost()) {
					this.hudIcons.setFrame(4);
				} else {
					this.hudIcons.setFrame(5);
				}

				this.hudIcons.setPosition(2, 2);
				this.hudIcons.paint(GlobalStatus.graphics);
				GlobalStatus.graphics.setClip(2, var7 + 2 - (int)(var5.getBoostRate() * var7), var7, var7);
				this.hudIcons.setFrame(4);
				this.hudIcons.setPosition(2, 2);
				this.hudIcons.paint(GlobalStatus.graphics);
				GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
			}

			if (var5.isAutoPilot()) {
				this.hudIcons.setFrame(1);
			} else {
				this.hudIcons.setFrame(2);
			}

			this.hudIcons.setPosition(4 + this.hudIconsHeight, 2);
			if (this.logMessages[1] == null || !this.logMessages[1].label.equals(GlobalStatus.gameText.getText(276)) || Layout.quickClockHigh_()) {
				this.hudIcons.paint(GlobalStatus.graphics);
			}

			if (this.hasWeapon) {
				if (var6) {
					this.hudIcons.setFrame(14);
				} else {
					this.hudIcons.setFrame(15);
				}

				this.hudIcons.setPosition(2, 4 + this.hudIconsHeight);
				this.hudIcons.paint(GlobalStatus.graphics);
			}

			if (var5.isCloakPresent()) {
				this.hudIcons.setFrame(12);
				this.hudIcons.setPosition(GlobalStatus.screenWidth - 2 - this.hudIconsHeight, 2);
				this.hudIcons.paint(GlobalStatus.graphics);
				if (var5.isCloaked()) {
					GlobalStatus.graphics.setClip(GlobalStatus.screenWidth - 2 - this.hudIconsHeight, var7 + 2 - (int)((1.0F - var5.getCloakRate()) * var7), var7, var7);
				} else {
					GlobalStatus.graphics.setClip(GlobalStatus.screenWidth - 2 - this.hudIconsHeight, var7 + 2 - (int)(var5.getCloakRate() * var7), var7, var7);
				}

				this.hudIcons.setFrame(11);
				this.hudIcons.setPosition(GlobalStatus.screenWidth - 2 - this.hudIconsHeight, 2);
				this.hudIcons.paint(GlobalStatus.graphics);
				GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
			}

			this.hudIcons.setFrame(17);
			this.hudIcons.setPosition(GlobalStatus.screenWidth - 4 - 2 * this.hudIconsHeight, 2);
			this.hudIcons.paint(GlobalStatus.graphics);
			if (Status.getShip().getFreeCargo() > 0) {
				this.hudIcons.setFrame(7);
			} else {
				this.hudIcons.setFrame(9);
			}

			this.hudIcons.setPosition(GlobalStatus.screenWidth - 2 - this.hudIconsHeight, 4 + this.hudIconsHeight);
			this.hudIcons.paint(GlobalStatus.graphics);
			int var10;
			if (this.settingSecondaryWeapon) {
				var10 = this.actionmenuSelectDir;
				this.menuAnimStep = (int)(this.menuAnimStep - var1 / 10L);
				boolean var11 = false;
				if (this.menuAnimStep <= 0) {
					this.menuAnimStep = 0;
					var11 = true;
				}

				var12 = (GlobalStatus.screenWidth >> 1) + (var10 == 2 ? this.menuAnimStep : var10 == 4 ? -this.menuAnimStep : 0);
				var10 = (GlobalStatus.screenHeight >> 1) + (var10 == 1 ? -this.menuAnimStep : var10 == 3 ? this.menuAnimStep : 0);
				this.quickMenu.setRefPixelPosition(var12, var10);
				this.quickMenu.setFrame(2);
				if (!var11 || var11 && Layout.quickClockHigh_()) {
					this.quickMenu.paint(GlobalStatus.graphics);
					if (this.actionSubMenuOpen == 1) {
						var7 = var5.getCurrentSecondaryWeaponIndex();
						if (var7 >= 0) {
							ImageFactory.drawItemFrameless(var7, this.items, var12, var10, 3);
						}
					} else if (this.actionSubMenuOpen == 3) {
						this.quickMenu.setRefPixelPosition(var12, var10);
						this.quickMenu.setFrame(6 + this.actionmenuSelectDir);
						this.quickMenu.paint(GlobalStatus.graphics);
					}
				}

				this.menuItemSelectTick = (int)(this.menuItemSelectTick + var1);
				if (this.menuItemSelectTick > 1500) {
					this.settingSecondaryWeapon = false;
				}
			}

			if (var3 > 0L) {
				Font.drawString(Time.timeToHMS(var3), GlobalStatus.screenWidth >> 1, 2, var3 < 10000L ? 2 : 1, Font.TOP|Font.HCENTER);
			} else if (this.logMessages[1] != null && this.logMessages[1].items != null && !this.logMessages[1].isSelectable) {
				Font.drawString(Status.getShip().getCurrentLoad() + " / " + Status.getShip().getCargoPlus() + "t", GlobalStatus.screenWidth >> 1, 2, 1, Font.TOP|Font.HCENTER);
			} else if (var5.isMining()) {
				var10 = Status.getShip().getCurrentLoad() + var5.getMiningProgress();
				Font.drawString(var10 + " / " + Status.getShip().getCargoPlus() + "t", GlobalStatus.screenWidth >> 1, 2, var10 > Status.getShip().getCargoPlus() ? 2 : 1, Font.TOP|Font.HCENTER);
			} else if (Status.getMission().getType() == 12) {
				Font.drawString(var5.level.egoScore + " : " + var5.level.challengerScore, GlobalStatus.screenWidth >> 1, 2, 1, Font.TOP|Font.HCENTER);
			}

			if (this.displayLog_) {
				updateQueue((int)var1);
				drawEventQueue();
			}

			var10 = var5.getHullDamageRate();
			if (this.lastHullDamage > var10) {
				this.hullDamageTick = 0;
			}

			if (var10 < 100 && this.hullDamageTick < 3000) {
				if (Layout.quickClockHigh_()) {
					this.hullDamageTick = (int)(this.hullDamageTick + var1);
					GlobalStatus.graphics.drawImage(this.hullAlarm, this.screenMidX, 41, 17);
					this.hullAlarmNumbers.setFrame(var10 / 10);
					this.hullAlarmNumbers.setPosition(this.hullAlertMSDx, 55);
					this.hullAlarmNumbers.paint(GlobalStatus.graphics);
					this.hullAlarmNumbers.setFrame(var10 % 10);
					this.hullAlarmNumbers.setPosition(this.hullAlertLSDx, 55);
					this.hullAlarmNumbers.paint(GlobalStatus.graphics);
					this.hullAlarmNumbers.setFrame(10);
					this.hullAlarmNumbers.setPosition(this.hullAlertPercentX, 55);
					this.hullAlarmNumbers.paint(GlobalStatus.graphics);
				}
			} else if (var10 < 100) {
				if (this.hullAlarmShipIcon == null) {
					this.hullAlarmShipIcon = AEFile.loadImage("/data/interface/hud_hull_alarm_shipicon.png", true);
				}

				GlobalStatus.graphics.drawImage(this.hullAlarmShipIcon, (GlobalStatus.screenWidth >> 1) - 4, GlobalStatus.screenHeight - this.panelLowerLeft.getHeight() + 15, 40);
				Font.drawString(var10 + "%", GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight - this.panelLowerLeft.getHeight() + 15, 0, Font.BOTTOM|Font.LEFT);
			}

			this.lastHullDamage = var10;
		}
	}

	public final void drawBigDigits(final int var1, final int var2, final int var3, final boolean var4) {
		if (var1 > 9) {
			this.hullAlarmNumbers.setFrame(var1 / 10);
			this.hullAlarmNumbers.setPosition(var2, var3);
			this.hullAlarmNumbers.paint(GlobalStatus.graphics);
		}

		this.hullAlarmNumbers.setFrame(var1 % 10);
		this.hullAlarmNumbers.setPosition(var2 + 14, var3);
		this.hullAlarmNumbers.paint(GlobalStatus.graphics);
	}

	public final void drawOrbitInformation() {
		if (!Status.inAlienOrbit()) {
			if (this.factionLogo == null) {
				this.factionLogo = AEFile.loadImage("/data/interface/logo_" + Status.getSystem().getRace() + ".png", true);
			}

			final int var1 = this.factionLogo.getWidth() + 6;
			GlobalStatus.graphics.drawImage(this.factionLogo, 3, 3, 20);
			Font.drawString(Status.getStation().getName(), var1, 3, 0);
			Font.drawString(Status.getSystem().getName() + " " + GlobalStatus.gameText.getText(41), var1, 3 + Font.getSpacingY(), 1);
			Font.drawString(GlobalStatus.gameText.getText(220) + ": " + GlobalStatus.gameText.getText(225 + Status.getSystem().getSafety()), var1, 3 + 2 * Font.getSpacingY(), 1);
		}

	}

	public final void drawMenu_(final int var1) {
		final int var2 = GlobalStatus.screenHeight >> 1;
		final int var3 = GlobalStatus.screenWidth >> 1;
		if (this.actionmenuConverging) {
			if (this.menuItemSelectTick > 25) {
				this.menuItemSelectTick -= 25;
				this.quickmenuCrosshairAnim.nextFrame();
			}

			this.menuItemSelectTick += var1;
			this.quickmenuCrosshairAnim.setRefPixelPosition(var3, var2);
			this.quickmenuCrosshairAnim.paint(GlobalStatus.graphics);
		} else if (this.menuReady) {
			int var4 = (this.quickmenuCrosshairAnim.getHeight() >> 1) + 5 + (this.quickMenu.getHeight() >> 1);
			if (this.actionSubMenuOpen == 0) {
				this.quickmenuCrosshairAnim.setRefPixelPosition(var3, var2);
				this.quickmenuCrosshairAnim.paint(GlobalStatus.graphics);
				var4 = (this.quickmenuCrosshairAnim.getHeight() >> 1) + 5 + (this.quickMenu.getHeight() >> 1);
			} else {
				if (this.menuAnimStep > 0) {
					this.menuAnimStep -= var1 / 3;
					if (this.menuAnimStep < 0) {
						this.menuAnimStep = 0;
					}
				}

				this.quickmenuCrosshairAnim.setRefPixelPosition(var3, var2);
				this.quickmenuCrosshairAnim.paint(GlobalStatus.graphics);
			}

			if (this.menuItemsSpread > var4) {
				this.menuItemsSpread -= var1;
				if (this.menuItemsSpread < var4) {
					this.menuItemsSpread = var4;
				}
			}

			Item[] var7 = null;
			if (this.actionSubMenuOpen == 1) {
				var7 = Status.getShip().getEquipment(Item.SECONDARY);
			} else {
				var7 = this.cloakAndDrive;
			}

			for(var4 = 0; var4 < 4; ++var4) {
				this.quickMenu.setFrame(this.actionmenuButtonsState[this.actionSubMenuOpen][var4]);
				if (var4 == this.actionmenuSelectDir - 1) {
					this.quickMenu.setFrame(2);
				}

				final int var5 = var3 + (var4 == 1 ? this.menuItemsSpread : var4 == 3 ? -this.menuItemsSpread : 0);
				final int var6 = var2 + (var4 == 0 ? -this.menuItemsSpread : var4 == 2 ? this.menuItemsSpread : 0);
				this.quickMenu.setRefPixelPosition(var5, var6);
				this.quickMenu.paint(GlobalStatus.graphics);
				switch (this.actionSubMenuOpen) {
				case 0:
					if (this.actionmenuButtonsState[this.actionSubMenuOpen][var4] > 0) {
						this.quickMenu.setFrame(var4 + 3);
						this.quickMenu.paint(GlobalStatus.graphics);
					}
					break;
				case 1:
					if (var4 < var7.length && var7[var4] != null && var7[var4].getAmount() > 0) {
						ImageFactory.drawItemFrameless(var7[var4].getIndex(), this.items, var5, var6, Graphics.HCENTER | Graphics.VCENTER);
						Font.drawString(
						  "x" + var7[var4].getAmount(),
						  var5,
						  var6 + (ImageFactory.itemHeight >> 1),
						  1,
						  Font.TOP|Font.HCENTER
						);
					}
					break;
				case 3:
					this.quickMenu.setFrame(var4 + 7);
					this.quickMenu.paint(GlobalStatus.graphics);
					break;
				default:
					break;
				}
			}

			if (this.actionmenuSelectDir > 0) {
				Font.drawString(
						this.actionmenuLabels[this.actionSubMenuOpen][this.actionmenuSelectDir - 1],
						var3,
						10,
						0,
						Font.TOP|Font.HCENTER
				);
			}

			if (Status.wingmenTimeRemaining > 0) {
				Font.drawString(
						GlobalStatus.gameText.getText(152) + ": " + Time.timeToHMS(Status.wingmenTimeRemaining),
						var3,
						GlobalStatus.screenHeight - 30,
						0,
						Font.TOP|Font.HCENTER
				);
			}
		}
	}

	public final boolean handleActionMenuKeypress(int keyPressed, final Level level, final Radar var3) {
		if (keyPressed == GOF2Canvas.RSB) {
			if (this.actionmenuOpen && this.menuReady) {
				this.actionmenuOpen = false;
				this.actionmenuConverging = false;
				return this.actionmenuOpen;
			}

			if (!this.actionmenuConverging) {
				this.settingSecondaryWeapon = false;
				this.menuItemSelectTick = 0;
				this.actionmenuSelectDir = 0;
				this.menuItemsSpread = AEMath.max(GlobalStatus.screenWidth, GlobalStatus.screenHeight) + this.quickMenu.getHeight();
				this.quickmenuCrosshairAnim.setFrame(0);
				this.menuReady = false;
				this.actionmenuConverging = true;
				this.menuItemSelectTick = 0;
				this.actionmenuOpen = true;
				GlobalStatus.soundManager.playSfx(4);
				return this.actionmenuOpen;
			}
		}

		if (this.actionmenuConverging && this.quickmenuCrosshairAnim.getFrame() >= this.quickmenuCrosshairAnim.getRawFrameCount() - 1) {
			this.actionmenuConverging = false;
			this.menuReady = true;
			this.actionSubMenuOpen = 0;
			return this.actionmenuOpen;
		}
		final int var4 = this.actionSubMenuOpen;
		if (this.actionmenuOpen && this.menuReady) {
			int var5 = this.actionmenuSelectDir;
			if (this.actionmenuSelectDir > 0 && keyPressed == GOF2Canvas.KEY_5) {
				keyPressed = this.actionmenuSelectDir == 1 ? GOF2Canvas.UP:
              				this.actionmenuSelectDir == 2 ? GOF2Canvas.RIGHT:
              				this.actionmenuSelectDir == 3 ? GOF2Canvas.DOWN:
              				                                GOF2Canvas.LEFT;
			}

			if (this.actionmenuSelectDir == 0 ||
			  this.actionmenuSelectDir == 1 && keyPressed != GOF2Canvas.UP ||
			  this.actionmenuSelectDir == 2 && keyPressed != GOF2Canvas.RIGHT ||
			  this.actionmenuSelectDir == 3 && keyPressed != GOF2Canvas.DOWN ||
			  this.actionmenuSelectDir == 4 && keyPressed != GOF2Canvas.LEFT) {
    		  switch(keyPressed) {
    				case GOF2Canvas.UP:
  				      this.actionmenuSelectDir = 1;
    					GlobalStatus.soundManager.playSfx(4);
    					break;
    				case GOF2Canvas.LEFT:
    					this.actionmenuSelectDir = 4;
    					GlobalStatus.soundManager.playSfx(4);
    					break;
    				case GOF2Canvas.RIGHT:
    					this.actionmenuSelectDir = 2;
    					GlobalStatus.soundManager.playSfx(4);
    					break;
    				case GOF2Canvas.DOWN:
    					this.actionmenuSelectDir = 3;
    					GlobalStatus.soundManager.playSfx(4);
    					break;
    				case GOF2Canvas.KEY_5:
    					if (this.actionmenuSelectDir == 0) {
    						handleActionMenuKeypress(GOF2Canvas.RSB, level, var3);
    					}
    				}
    
    				if (this.actionmenuSelectDir == 0 
    						&& this.actionmenuSelectDir == 3
    						&& !level.getPlayer().isCloaked()
    				      && !level.getPlayer().isCloakReady()
    				      || this.actionmenuSelectDir > 0
    				            && this.actionmenuLabels[this.actionSubMenuOpen][this.actionmenuSelectDir - 1].equals("")) {
    					this.actionmenuSelectDir = var5;
    				}
    
    				return this.actionmenuOpen;
			}

			switch(this.actionSubMenuOpen) {
			case 1:
				if (keyPressed != GOF2Canvas.UP 
  				&& keyPressed != GOF2Canvas.RIGHT 
  				&& keyPressed != GOF2Canvas.DOWN 
  				&& keyPressed != GOF2Canvas.LEFT
  				&& keyPressed != GOF2Canvas.KEY_5) {
					this.actionmenuOpen = true;
					break;
				}
				var5 = keyPressed == GOF2Canvas.UP  ? 0 :
				       keyPressed == GOF2Canvas.RIGHT ? 1 :
				       keyPressed == GOF2Canvas.DOWN  ? 2 :
				       keyPressed == GOF2Canvas.LEFT ? 3 :
				       this.actionmenuSelectDir;
				
				if (!this.actionmenuLabels[1][var5].equals("")) {
					keyPressed = Status.getShip().getEquipment(Item.SECONDARY)[var5].getIndex();
					level.getPlayer().setCurrentSecondaryWeaponIndex(Status.getShip().getEquipment(1)[var5].getIndex());
					GlobalStatus.displayedSecondary = keyPressed;
					drawSecondaryIcon = this.actionmenuSelectDir;
				}

				this.actionmenuOpen = false;
				break;
			case 3:
				KIPlayer[] enemies = level.getEnemies();
				if (enemies == null) {
					break;
				}

				lastWingmenAction = keyPressed == GOF2Canvas.UP ? 2 :
                               keyPressed == GOF2Canvas.RIGHT ? 4 :
                               keyPressed == GOF2Canvas.DOWN ? 3 :
                               keyPressed == GOF2Canvas.LEFT ? 1 :
                                                 0;
				hudEvent(
				  lastWingmenAction == 2 ? 16 :
				  lastWingmenAction == 4 ? 17 :
				  lastWingmenAction == 1 ? 19 :
				                           18,
				  level.getPlayer()
				);
				this.actionmenuLabels[3][3] = 
				  this.actionmenuLabels[3][3].equals(GlobalStatus.gameText.getText(151)) ?
				    GlobalStatus.gameText.getText(150):
				    GlobalStatus.gameText.getText(151);

				for(int i = 0; i < enemies.length; ++i) {
					if (enemies[i].isWingman() && !enemies[i].isDead()) {
						if (lastWingmenAction == 0) {
							this.actionmenuOpen = true;
						} else {
							enemies[i].setWingmanCommand(lastWingmenAction, lastWingmenAction == 4 ? var3.getLockedEnemy() : null);
						}
					}
				}

				this.actionmenuOpen = false;
				break;
			default:
				switch(keyPressed) {
				case GOF2Canvas.UP:
					if (!this.actionmenuLabels[0][0].equals("")) {
						initSecondariesSubMenu();
						this.actionSubMenuOpen = 1;
						this.actionmenuOpen = true;
						GlobalStatus.soundManager.playSfx(4);
					}
					break;
				case GOF2Canvas.RIGHT:
					if (!this.actionmenuLabels[0][1].equals("")) {
						level.getPlayer().toggleCloaking_();
						GlobalStatus.soundManager.playSfx(4);
					}

					this.actionmenuOpen = false;
					break;
				case GOF2Canvas.DOWN:
					if (!this.actionmenuLabels[0][2].equals("")) {
						this.actionSubMenuOpen = 3;
						this.actionmenuOpen = true;
						GlobalStatus.soundManager.playSfx(4);
					}
					break;
            case GOF2Canvas.LEFT:
               if (!Status.getMission().isEmpty() && Status.getMission().getType() != 11 && Status.getMission().getType() != 0 && Status.getMission().getType() != 23) {
                  hudEvent(21, level.getPlayer());
                  GlobalStatus.soundManager.playSfx(4);
               } else if (!this.actionmenuLabels[0][3].equals("")) {
                  this.jumpDriveSelected = true;
                  GlobalStatus.soundManager.playSfx(4);
               }
  
               this.actionmenuOpen = false;
               break;
				case GOF2Canvas.KEY_5:
					this.actionmenuOpen = false;
				}
			}

			if (!this.actionmenuOpen) {
				this.settingSecondaryWeapon = this.actionSubMenuOpen != 0 || this.actionmenuSelectDir != 2 && this.actionmenuSelectDir != 4;
				this.menuAnimStep = (this.quickmenuCrosshairAnim.getHeight() >> 1) + 5 + (this.quickMenu.getHeight() >> 1);
				this.menuItemSelectTick = 0;
			}
		}

		if (this.actionSubMenuOpen != var4) {
			this.actionmenuSelectDir = 0;
			this.menuItemsSpread = AEMath.max(GlobalStatus.screenWidth, GlobalStatus.screenHeight) + this.quickMenu.getHeight();
			this.menuAnimStep = (this.quickmenuCrosshairAnim.getHeight() >> 1) + 5 + (this.quickMenu.getHeight() >> 1);
		}

		return this.actionmenuOpen;
	}

	public final boolean getJumpDriveSelected() {
		return this.jumpDriveSelected;
	}

	public final void setJumpDriveSelected(final boolean var1) {
		this.jumpDriveSelected = false;
	}
}
