package GOF2.Main;

import javax.microedition.lcdui.Image;

import AE.GlobalStatus;
import AE.SoundManager;
import AE.TextInput;
import AE.Time;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import GOF2.Achievements;
import GOF2.Galaxy;
import GOF2.GameRecord;
import GOF2.GameText;
import GOF2.Layout;
import GOF2.LoadingScreen;
import GOF2.Mission;
import GOF2.Popup;
import GOF2.RecordHandler;
import GOF2.Status;
import GOF2.TextBox;

public final class OptionsWindow {
   
   public static final int MM_NEW_GAME = 0;
   public static final int MM_LOAD_GAME = 1;
   public static final int MM_SAVE_GAME = 2;
   public static final int MM_OPTIONS = 3;
   public static final int MM_HELP = 4;
   public static final int MM_EXIT = 5;
   public static final int MM_VENDOR_URL = 6;
   
   private static final int MAIN_MENU = 0;
   private static final int LOAD_GAME = 1;
   private static final int SAVE_GAME = 2;
   private static final int OPTIONS = 3;
   private static final int HELP = 4;
   private static final int PAUSE = 5;
   private static final int HELP_INSTRUCTIONS = 6;
   private static final int HELP_CONTROLS = 7;
   private static final int HELP_CREDITS = 8;
   private static final int UNK_SUBMENU_9 = 9;
   private static final int UNK_SUBMENU_10 = 10;
   private static final int NAME_INPUT = 11;
   private static final int UNK_SUBMENU_12 = 12;
   private static final int UNK_SUBMENU_13 = 13;
   private static final int UNK_SUBMENU_14 = 14;
   private static final int HELP_INSTR_FIRST = 15;
   private static final int HELP_INSTR_24 = 24;
   private static final int HELP_INSTR_LAST = 25;
   
   private static int posX;
	private static int headerHeight;
	private static int windowWidth;
	private static int[] menuItemCounts = {6, 4, 3, 4, 3, 3, 11, 0, 0, 2, 12, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private Image gameLogo;
	private final int fontSpacingY;
	private int saveGameItemHeight;
	private int selectedRow;
	private int subMenu;
	private String saveInfo;
	private Popup confirmPopup;
	private boolean confirmPopupOpen;
	private boolean forcePauseMenu_;
	private GameRecord[] saves;
	private String stationName;
	private final ListWindow_ manualWindow;
	private final TextBox credits;
	private final TextBox instructions;
	private final TextBox controls;
	private final TextBox hidden__;
	private final TextInput nameInput;
	private int frameTime;
	private int musicLevel;
	private int soundLevel;
	private int recordWindowWidth;
	private boolean webAccessAvailable = false;
	private String operatorURL = null;
	private String operatorsPrompt = null;
	private String operatorMenuEntry = null;
	private int wapMode = -1;
	private final boolean unused_b06 = false;
	private final Image unused_b34 = null;
	private final int optionsListPosY;

	public OptionsWindow() {
		initWapItem();
		if (this.webAccessAvailable) {
			menuItemCounts[0] = 7;
		}

		this.gameLogo = LoadingScreen.getGameLogo();
		headerHeight = this.gameLogo.getHeight() + 25;
		this.optionsListPosY = headerHeight + 16;
		windowWidth = 117;
		posX = GlobalStatus.screenWidth - windowWidth >> 1;
		this.fontSpacingY = Font.getSpacingY();
		this.selectedRow = 0;
		this.subMenu = MAIN_MENU;
		this.confirmPopup = new Popup(20, GlobalStatus.screenHeight / 2, GlobalStatus.screenWidth - 40);
		this.confirmPopupOpen = false;
		this.forcePauseMenu_ = false;
		this.credits = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, GlobalStatus.gameText.getText(23) + "\n\n" + GlobalStatus.gameText.getText(25));
		this.instructions = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, "");
		this.controls = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, GlobalStatus.gameText.getText(22));
		this.hidden__ = new TextBox(posX + 8, this.optionsListPosY, windowWidth - 16, GlobalStatus.screenHeight - headerHeight - 48, "");
		final String[] helpTitles = new String[GameText.helpFull.length];

		for(int i = 0; i < helpTitles.length; ++i) {
			helpTitles[i] = GlobalStatus.gameText.getText(GameText.helpTitles[i]);
		}

		this.manualWindow = new ListWindow_(posX, headerHeight + 2, windowWidth, GlobalStatus.screenHeight - headerHeight - 16 - 10, (String[])null);
		this.manualWindow.setRowHeight(12);
		this.manualWindow.setEntries(0, helpTitles);
		this.manualWindow.setSelectionHighlight(true);
		this.manualWindow.setBackGroundDraw(false);
		this.manualWindow.decFontIndex_();
		this.nameInput = new TextInput(10);
		this.musicLevel = GlobalStatus.musicVolume;
		this.soundLevel = GlobalStatus.sfxVolume;
		menuItemCounts[12] = 2;
	}

	private static String unescape(final String str) {
		int i = 0;
		final int len = str.length();
		final StringBuffer sbuf = new StringBuffer();

		while(true) {
			while(i < len) {
				char ch = str.charAt(i);
				// looking for unicodeescape character: \\uXXXX
				if (ch == '\\' && i + 6 <= len && str.charAt(i + 1) == 'u') {
					ch = (char)Integer.parseInt(str.substring(i + 2, i + 6), 16);
					sbuf.append(ch);
					i += 6;
				} else {
					sbuf.append(ch);
					++i;
				}
			}

			return sbuf.toString();
		}
	}

	private void initWapItem() {
		try {
			try {
				this.operatorURL = GlobalStatus.midlet.getAppProperty("Operator-URL-ru");
				this.operatorMenuEntry = GlobalStatus.midlet.getAppProperty("Operator-Menu-ru");
			} catch (final Exception var10) {
				this.webAccessAvailable = false;
				this.operatorURL = null;
				this.operatorsPrompt = null;
				this.operatorMenuEntry = null;

				try {
					this.operatorURL = GlobalStatus.midlet.getAppProperty("Operator-URL-gn");
					this.operatorMenuEntry = GlobalStatus.midlet.getAppProperty("Operator-Menu-gn");
				} catch (final Exception var9) {
					this.webAccessAvailable = false;
					return;
				}

				if (this.operatorURL == null || this.operatorMenuEntry == null) {
					this.webAccessAvailable = false;
					return;
				}
			}

			if (this.operatorURL == null || this.operatorMenuEntry == null) {
				try {
					this.operatorURL = GlobalStatus.midlet.getAppProperty("Operator-URL-gn");
					this.operatorMenuEntry = GlobalStatus.midlet.getAppProperty("Operator-Menu-gn");
				} catch (final Exception var8) {
					this.webAccessAvailable = false;
					return;
				}
			}

			try {
				this.operatorsPrompt = GlobalStatus.midlet.getAppProperty("Operator-Prompt-ru");
			} catch (final Exception var7) {
				this.operatorsPrompt = null;

				try {
					this.operatorsPrompt = GlobalStatus.midlet.getAppProperty("Operator-Prompt-gn");
				} catch (final Exception var6) {
				}
			}

			if (this.operatorsPrompt == null) {
				try {
					this.operatorsPrompt = GlobalStatus.midlet.getAppProperty("Operator-Prompt-gn");
				} catch (final Exception var5) {
				}
			}

			if (this.operatorsPrompt != null) {
				this.operatorsPrompt = unescape(this.operatorsPrompt);
			}

			if (this.operatorMenuEntry != null) {
				this.operatorMenuEntry = unescape(this.operatorMenuEntry);
			}

			System.out.println("wap_url: " + this.operatorURL);
			System.out.println("wap_menu: " + this.operatorMenuEntry);
			System.out.println("wap_prompt: " + this.operatorsPrompt);
			final String var1 = GlobalStatus.midlet.getAppProperty("Operator-Mode");
			if ((this.operatorURL == null) || (var1 == null)) {
				throw new Exception();
			}
			final char[] var2 = {'1', '2'};

			for(int i = 0; i < var2.length; ++i) {
				int var4 = var1.indexOf(String.valueOf(var2[i]));
				if (var4 >= 0) {
					this.wapMode = Integer.valueOf(String.valueOf(var1.charAt(var4))).intValue();
				}
			}

			System.out.println("wap_mode: " + this.wapMode);
			if (this.wapMode < 1 || this.wapMode > 2) {
				this.wapMode = 2;
			}

			this.webAccessAvailable = true;
		} catch (final Exception var11) {
			this.webAccessAvailable = false;
		}
	}

	private void loadSavePreviews(final GameRecord[] var1) {
		this.recordWindowWidth = 0;
		if (var1 != null) {
			for(int i = 0; i < var1.length; ++i) {
				this.saveInfo = i + 1 + ".  " + (var1[i] == null ? GlobalStatus.gameText.getText(26) : Time.timeToHM(var1[i].playTime) + (i == 3 ? " AUTOSAVE" : " " + var1[i].stationName));
				int var3 = Font.getTextWidth(this.saveInfo, 1);
				if (var3 > this.recordWindowWidth) {
					this.recordWindowWidth = var3;
				}
			}

			this.recordWindowWidth += 24;
		} else {
			this.recordWindowWidth = windowWidth + (windowWidth >> 1);
		}
	}

	public final void OnRelease() {
		this.saves = null;
		this.confirmPopup = null;
		this.gameLogo = null;
	}

	public final void scrollAndTick_(final int keyState, final int dt) {
		this.frameTime = dt;
		Layout.addTicks(dt);
		if ((keyState & GOF2Canvas.DOWN) != 0) {
			if (this.subMenu >= HELP_INSTR_FIRST) {
				this.instructions.scrollUp(dt);
				return;
			}

			if (this.subMenu == HELP_CREDITS) {
				this.credits.scrollUp(dt);
				return;
			}

			if (this.subMenu == HELP_CONTROLS) {
				this.controls.scrollUp(dt);
			}
		} else if ((keyState & GOF2Canvas.UP) != 0) {
			if (this.subMenu >= HELP_INSTR_FIRST) {
				this.instructions.scrollDown(dt);
				return;
			}

			if (this.subMenu == HELP_CREDITS) {
				this.credits.scrollDown(dt);
				return;
			}

			if (this.subMenu == HELP_CONTROLS) {
				this.controls.scrollDown(dt);
			}
		}

	}

	public final boolean update() {
		RecordHandler recHandler;
		switch(this.subMenu) {
		case MAIN_MENU:
			if (this.confirmPopupOpen && this.selectedRow == 5) {
				if (this.confirmPopup.getChoice()) {
					GlobalStatus.applicationManager.Quit();
					return true;
				}

				this.confirmPopupOpen = false;
				return false;
			}

			switch(this.selectedRow) {
			case MM_NEW_GAME:
				if (Status.getPlayingTime() <= 0L) {
					Status.startNewGame();
					GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[2]);
					SoundManager.stopMusic__();
					return true;
				}

				if (!this.confirmPopupOpen) {
					this.confirmPopup.set(GlobalStatus.gameText.getText(30), true);
					this.confirmPopupOpen = true;
					return false;
				}

				if (this.confirmPopup.getChoice()) {
					Status.startNewGame();
					GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[2]);
					SoundManager.stopMusic__();
					return true;
				}

				this.confirmPopupOpen = false;
				return false;
			case MM_LOAD_GAME:
				this.subMenu = LOAD_GAME;
				if (this.saves == null) {
					recHandler = new RecordHandler();
					this.saves = recHandler.readAllPreviews();
				}
				
				loadSavePreviews(this.saves);
				this.selectedRow = 0;
				return false;
			case MM_SAVE_GAME:
				this.subMenu = SAVE_GAME;
				if (this.saves == null) {
					recHandler = new RecordHandler();
					this.saves = recHandler.readAllPreviews();
				}

				loadSavePreviews(this.saves);
				this.selectedRow = 0;
				return false;
			case MM_OPTIONS:
				this.subMenu = OPTIONS;
				this.selectedRow = 0;
				return false;
			case MM_HELP:
				this.subMenu = HELP;
				this.selectedRow = 0;
				return false;
			case MM_EXIT:
				if (!this.confirmPopupOpen) {
					this.confirmPopup.set(GlobalStatus.gameText.getText(31), true);
					this.confirmPopupOpen = true;
				}

				return false;
			case MM_VENDOR_URL:
				if (this.webAccessAvailable) {
					switch(this.wapMode) {
					case 1:
						try {
							if (!this.confirmPopupOpen && this.operatorsPrompt != null) {
								this.confirmPopup.set(this.operatorsPrompt, true);
							} else if (this.operatorsPrompt == null || this.confirmPopup.getChoice()) {
								GlobalStatus.midlet.platformRequest(this.operatorURL);
								GlobalStatus.applicationManager.Quit();
								return true;
							}

							if (this.operatorsPrompt != null) {
								this.confirmPopupOpen = !this.confirmPopupOpen;
							}
						} catch (final Exception var3) {
						}

						return false;
					default:
						try {
							if (!this.confirmPopupOpen && this.operatorsPrompt != null) {
								this.confirmPopup.set(this.operatorsPrompt, true);
							} else if (this.operatorsPrompt == null || this.confirmPopup.getChoice()) {
								GlobalStatus.midlet.platformRequest(this.operatorURL);
							}

							if (this.operatorsPrompt != null) {
								this.confirmPopupOpen = !this.confirmPopupOpen;
							}
						} catch (final Exception var2) {
						}

						return false;
					}
				}

				return false;
			default:
				return false;
			}
		case LOAD_GAME:
			if (this.selectedRow == this.saves.length || this.saves[this.selectedRow] != null) {
				if (Status.getPlayingTime() <= 0L) {
					this.confirmPopupOpen = false;
					loadGameRecord();
					return true;
				}

				if (!this.confirmPopupOpen) {
					this.confirmPopup.set(GlobalStatus.gameText.getText(29), true);
					this.confirmPopupOpen = true;
				} else {
					if (this.confirmPopup.getChoice()) {
						loadGameRecord();
					}

					this.confirmPopupOpen = false;
				}
			}
			break;
		case SAVE_GAME:
			if (this.confirmPopupOpen) {
				if (!this.confirmPopup.getChoice()) {
					this.confirmPopupOpen = false;
					return true;
				}
				GlobalStatus.loadingScreen.startLoading_(false);
				recHandler = new RecordHandler();
				recHandler.saveGame(this.selectedRow);
				this.saves[this.selectedRow] = recHandler.readPreview(this.selectedRow);
				loadSavePreviews(this.saves);
				GlobalStatus.loadingScreen.close();
				this.confirmPopup.set(GlobalStatus.gameText.getText(28), false);
			} else {
				if (this.selectedRow < this.saves.length && this.saves[this.selectedRow] == null) {
					GlobalStatus.loadingScreen.startLoading_(false);
					(recHandler = new RecordHandler()).saveGame(this.selectedRow);
					this.saves = recHandler.readAllPreviews();
					GlobalStatus.loadingScreen.close();
					this.confirmPopup.set(GlobalStatus.gameText.getText(28), false);
				} else {
					this.confirmPopup.set(GlobalStatus.gameText.getText(27), true);
				}
				this.confirmPopupOpen = true;
			}
			break;
		case OPTIONS:
			optionsRight();
			break;
		case HELP:
			switch(this.selectedRow) {
			case 0:
				this.subMenu = HELP_INSTRUCTIONS;
				this.selectedRow = 0;
				this.manualWindow.selectedEntry = 0;
				return false;
			case 1:
				this.subMenu = HELP_CONTROLS;
				return false;
			case 2:
				this.subMenu = HELP_CREDITS;
				return false;
			default:
				return false;
			}
		case PAUSE:
			if (this.confirmPopupOpen) {
				if (this.confirmPopup.getChoice()) {
					GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[0]);
					GlobalStatus.soundManager.playMusic(0);
					return true;
				}

				this.confirmPopupOpen = false;
			} else {
				switch(this.selectedRow) {
				case 0:
					this.subMenu = OPTIONS;
					this.selectedRow = 0;
					return false;
				case 1:
					this.subMenu = HELP;
					this.selectedRow = 0;
					return false;
				case 2:
					if (!this.confirmPopupOpen) {
						this.confirmPopup.set(GlobalStatus.gameText.getText(31), true);
						this.confirmPopupOpen = true;
					}
				}
			}
			break;
		case HELP_INSTRUCTIONS:
			this.instructions.zeroTopPadding();
			this.instructions.setText(GlobalStatus.gameText.getText(GameText.helpFull[this.selectedRow]));
			this.subMenu = HELP_INSTR_FIRST + this.selectedRow;
			System.out.println(this.selectedRow + "  " + this.subMenu);
		case HELP_CONTROLS:
		case HELP_CREDITS:
		case UNK_SUBMENU_10:
		default:
			break;
		case UNK_SUBMENU_9:
			switch(this.selectedRow) {
			case 0:
				if (Status.getPlayingTime() > 0L) {
					if (!this.confirmPopupOpen) {
						this.confirmPopup.set(GlobalStatus.gameText.getText(30), true);
						this.confirmPopupOpen = true;
						return false;
					}

					if (this.confirmPopup.getChoice()) {
						this.subMenu = NAME_INPUT;
						this.selectedRow = 0;
					}

					this.confirmPopupOpen = false;
				} else {
					this.subMenu = NAME_INPUT;
					this.selectedRow = 0;
				}
				break;
			case 1:
				this.subMenu = UNK_SUBMENU_10;
				this.selectedRow = 0;
			}
		}

		return false;
	}

	private void loadGameRecord() {
		final RecordHandler recHandler = new RecordHandler();
		//new GameRecord();
		final GameRecord rec = recHandler.recordStoreRead(this.selectedRow);
		Status.startNewGame();
		Galaxy.setVisitedStations(rec.visitedStations);
		Status.setLastXP(rec.lastWorth);
		Status.setCredits(rec.credits);
		Status.setRating(rec.rating);
		Status.setPlayingTime(rec.playTime);
		Status.setKills(rec.kills);
		Status.setMissionCount(rec.missionCount);
		Status.setLevel(rec.level);
		Status.setLastXP(rec.lastWorth);
		Status.setGoodsProduced(rec.goodsProduced);
		Status.setPirateKills(rec.pirateKills);
		Status.setJumpgateUsed(rec.jumpGateUses);
		Status.setCargoSalvaged(rec.cargoSalvaged);
		Status.getUnusedVar(rec.unused__);
		Status.setStationsVisited(rec.stationsVisited);
		Status.wormholeStation = rec.wormholeStation;
		Status.wormholeSystem = rec.wormholeSystem;
		Status.lastVisitedNonVoidOrbit = rec.lastVisitedNonVoidOrbit;
		Status.wormHoleTick = rec.wormHoleTick;
		Status.setCurrentCampaignMission(rec.currentCampaignMissionIndex);
		Status.setFreelanceMission(rec.freelanceMission);
		Status.setCampaignMission(rec.storyMission);
		Status.setStationStack(rec.lastVisitedStations);
		Status.minedOreTypes = rec.minedOreTypes;
		Status.minedCoreTypes = rec.minedCoreTypes;
		Status.missionGoodsCarried = rec.missionGoodsCarried;
		Status.minedOre = rec.minedOre;
		Status.minedCores = rec.minedCores;
		Status.boughtBooze = rec.boughtBooze;
		Status.drinkTypesPossesed = rec.drinkTypesPossesed;
		Status.destroyedJunk = rec.destroyedJunk;
		Status.systemsVisited = rec.systemsVisited;
		Status.passengersCarried = rec.passengersCarried;
		Status.invisibleTime = rec.invisibleTime;
		Status.bombsUsed = rec.bombsUsed;
		Status.alienJunkSalvaged = rec.alienJunkSalvaged;
		Status.barInteractions = rec.barInteractions;
		Status.commandedWingmen = rec.commandedWingmen;
		Status.asteroidsDestroyed = rec.asteroidsDestroyed;
		Status.maxFreeCargo = rec.maxFreeCargo;
		Status.missionsRejected = rec.missionsRejected;
		Status.askedToRepeate = rec.askedToRepeate;
		Status.acceptedNotAskingDifficulty = rec.acceptedNotAskingDifficulty;
		Status.acceptedNotAskingLocation = rec.acceptedNotAskingLocation;
		Achievements.init();
		if (rec.achievements != null) {
			Achievements.setMedals(rec.achievements);
		}

		Status.setShip(rec.egoShip);
		Status.setStation(rec.currentStation);
		Status.setMission(Mission.emptyMission_);
		Status.standing = rec.standing;
		Status.blueprints = rec.blueprints;
		Status.waitingGoods = rec.waitingGoods;
		Status.specialAgents = rec.specialAgents;
		Status.wingmenNames = rec.wingmenNames;
		Status.wingmanRace = rec.wingmanRace;
		Status.wingmenTimeRemaining = rec.wingmenTimeRemaining;
		Status.wingmanFace = rec.wingmanFace;
		Status.passengerCount = rec.passengerCount;
		Status.visibleSystems = rec.visibleSystems;
		Status.lowestItemPrices = rec.lowestItemPrices;
		Status.highestItemPrices = rec.highestItemPrices;
		Status.lowestItemPriceSystems = rec.lowestItemPriceSystems;
		Status.highestItemPriceSystems = rec.highestItemPriceSystems;
		GlobalStatus.shopHelpShown = rec.shopHelpShown;
		GlobalStatus.shipHelpShown = rec.shipHelpShown;
		GlobalStatus.actionsHelpShown = rec.actionsHelpShown;
		GlobalStatus.bluePrintsHelpShown = rec.bluePrintsHelpShown;
		GlobalStatus.bluePrintInfoHelpShown = rec.bluePrintInfoHelpShown;
		GlobalStatus.barHelpShown = rec.barHelpShown;
		GlobalStatus.galaxyMapHelpShown = rec.galaxyMapHelpShown;
		GlobalStatus.systemMapHelpShown = rec.systemMapHelpShown;
		GlobalStatus.somehelpShown_unused_ = rec.unused12121_;
		GlobalStatus.miningHelpShown = rec.miningHelpShown;
		GlobalStatus.asteroidHelpShown = rec.asteroidHelpShown;
		GlobalStatus.missionsHelpShown = rec.missionsHelpShown;
		GlobalStatus.cargoFullHelpShown = rec.cargoFullHelpShown;
		GlobalStatus.wingmenHelpShown = rec.wingmenHelpShown;
		GlobalStatus.actionMenuHelpShown = rec.actionMenuHelpShown;
		GlobalStatus.boosterHelpShown = rec.boosterHelpShown;
		GlobalStatus.statusHelpShown = rec.statusHelpShown;
		GlobalStatus.medalsHelpShown = rec.medalsHelpShown;
		GlobalStatus.fanWingmenNoticeShown = rec.fanWingmenNoticeShown;
		GlobalStatus.voidxNoticeShown = rec.voidxNoticeShown;
		GlobalStatus.reputationHelpShown = rec.reputationHelpShown;
		GlobalStatus.buyingHelpShown = rec.buyingHelpShown;
		GlobalStatus.itemMountingHelpShown = rec.itemMountingHelpShown;
		GlobalStatus.itemMounting2HelpShown = rec.itemMounting2HelpShown;
		GlobalStatus.interplanetHelpShown = rec.interplanetHelpShown;
		GlobalStatus.jumpDriveHelpShown = rec.jumpDriveHelpShown;
		GlobalStatus.cloakHelpShown = rec.cloakHelpShown;
		Status.loadingsCount = rec.loadingsCount;
		Status.loadingTime = rec.timeSpentLoading;
		rec.visitedStations = null;
		((ModStation)GlobalStatus.scenes[1]).fromGameSave();
		GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[1]);
	}

	public final void scrollUp(final int var1) {
		if (!this.confirmPopupOpen) {
			if (this.subMenu != HELP_CONTROLS &&
  			  this.subMenu != HELP_CREDITS &&
  			  this.subMenu != UNK_SUBMENU_13 &&
  			  this.subMenu != UNK_SUBMENU_14 &&
  			  this.subMenu < HELP_INSTR_FIRST) {
				if (this.selectedRow > 0) {
					--this.selectedRow;
				} else {
					this.selectedRow = menuItemCounts[this.subMenu] - 1;
				}

				if (this.selectedRow == 2 && this.subMenu == MAIN_MENU && Status.getPlayingTime() == 0L) {
					--this.selectedRow;
				}

				if (this.subMenu == HELP_INSTRUCTIONS) {
					this.manualWindow.scrollUp();
				}
			} else if (this.subMenu == UNK_SUBMENU_13) {
				this.hidden__.scrollDown(var1);
			}

		}
	}

	public final void scrollDown(final int var1) {
		if (!this.confirmPopupOpen) {
			if (this.subMenu != HELP_CONTROLS 
			  && this.subMenu != HELP_CREDITS 
			  && this.subMenu != UNK_SUBMENU_13 
			  && this.subMenu != UNK_SUBMENU_14 
			  && this.subMenu < HELP_INSTR_FIRST) {
				if (this.selectedRow < menuItemCounts[this.subMenu] - 1) {
					++this.selectedRow;
				} else {
					this.selectedRow = 0;
				}

				if (this.selectedRow == 2 
				  && this.subMenu == MAIN_MENU 
				  && Status.getPlayingTime() == 0L) {
					++this.selectedRow;
				}

				if (this.subMenu == HELP_INSTRUCTIONS) {
					this.manualWindow.scrollDown();
				}
			} else if (this.subMenu == UNK_SUBMENU_13) {
				this.hidden__.scrollUp(var1);
			}

		}
	}

	public final void optionsLeft() {
		if (this.confirmPopupOpen) {
			this.confirmPopup.left();
		} else if (this.subMenu == OPTIONS) {
			switch(this.selectedRow) {
			case 0:
				--this.musicLevel;
				updateMusicLevel();
				return;
			case 1:
				--this.soundLevel;
				updateSoundLevel();
				return;
			case 2:
				optionsRight();
				return;
			case 3:
				optionsRight();
			}
		}
	}

	private void updateSoundLevel() {
		if (this.soundLevel < 0) {
			this.soundLevel = 3;
		}

		if (this.soundLevel > 3) {
			this.soundLevel = 0;
		}

		switch(this.soundLevel) {
		case 0:
			GlobalStatus.sfxOn = true;
			GlobalStatus.soundManager.setSfxVolume(60);
			break;
		case 1:
			GlobalStatus.sfxOn = true;
			GlobalStatus.soundManager.setSfxVolume(80);
			break;
		case 2:
			GlobalStatus.sfxOn = true;
			GlobalStatus.soundManager.setSfxVolume(100);
			break;
		case 3:
			GlobalStatus.sfxOn = false;
		}

		GlobalStatus.sfxVolume = this.soundLevel;
	}

	private void updateMusicLevel() {
		if (this.musicLevel < 0) {
			this.musicLevel = 3;
		}

		if (this.musicLevel > 3) {
			this.musicLevel = 0;
		}

		switch(this.musicLevel) {
		case 0:
			GlobalStatus.musicOn = true;
			GlobalStatus.soundManager.setMusicVolume(60);
			if (GlobalStatus.applicationManager.GetCurrentApplicationModule() != GlobalStatus.scenes[2] && !SoundManager.isMusicPlaying()) {
				GlobalStatus.soundManager.playMusic(0);
			}
			break;
		case 1:
			GlobalStatus.musicOn = true;
			GlobalStatus.soundManager.setMusicVolume(80);
			break;
		case 2:
			GlobalStatus.musicOn = true;
			GlobalStatus.soundManager.setMusicVolume(100);
			if (GlobalStatus.applicationManager.GetCurrentApplicationModule() != GlobalStatus.scenes[2] && !SoundManager.isMusicPlaying()) {
				GlobalStatus.soundManager.playMusic(0);
			}
			break;
		case 3:
			SoundManager.stopMusic__();
			GlobalStatus.musicOn = false;
		}

		GlobalStatus.musicVolume = this.musicLevel;
	}

	public final void optionsRight() {
		if (this.confirmPopupOpen) {
			this.confirmPopup.right();
		} else if (this.subMenu == OPTIONS) {
			switch(this.selectedRow) {
			case 0:
				++this.musicLevel;
				updateMusicLevel();
				return;
			case 1:
				++this.soundLevel;
				updateSoundLevel();
				return;
			case 2:
				GlobalStatus.vibrationOn = !GlobalStatus.vibrationOn;
				if (GlobalStatus.vibrationOn) {
					GlobalStatus.vibrate(150);
					return;
				}
				break;
			case 3:
				GlobalStatus.invertedControlsOn = !GlobalStatus.invertedControlsOn;
			}
		}
	}

	public final boolean goBack() {
		if (this.confirmPopupOpen) {
			return false;
		}
      if (this.subMenu >= HELP_INSTR_FIRST) {
         this.subMenu = HELP_INSTRUCTIONS;
         return false;
      } else
			switch (this.subMenu) {
    			case MAIN_MENU:
    			   return true;
    			case PAUSE:
    			   return true;
    			case OPTIONS:
    				new RecordHandler().writeOptions();
    	         this.selectedRow = MM_OPTIONS;
                 this.subMenu = MAIN_MENU;
                 if (this.forcePauseMenu_) {
                    resetPauseMenu();
                 }
    	         return false;
    			case HELP_CONTROLS:
    	         this.selectedRow = 1;
    	         this.subMenu = HELP;
    	         return false;
    			case HELP_INSTRUCTIONS:
    	         this.selectedRow = 0;
    	         this.subMenu = HELP;
    	         return false;
    			case UNK_SUBMENU_10:
    				this.selectedRow = 1;
    				this.subMenu = UNK_SUBMENU_9;
    				return false;
    			case HELP_CREDITS:
    				this.selectedRow = 2;
    				this.subMenu = HELP;
    				return false;
    			case UNK_SUBMENU_12:
    				this.subMenu = NAME_INPUT;
    				return false;
    			case NAME_INPUT:
    				return false;
    			default:
               // if (this.subMenu > MAIN_MENU || this.subMenu == UNK_SUBMENU_9) {
                   if (this.subMenu == UNK_SUBMENU_9) {
                      this.selectedRow = 0;
                   } else {
                      this.selectedRow = this.subMenu;
                   }
        
                   this.subMenu = MAIN_MENU;
                   if (this.forcePauseMenu_) {
                      resetPauseMenu();
                   }
               //}
               return false;
        }
	}

	public final boolean update1_() {
		update();
		return false;
	}

	public final void reset_(final int var1) {
		this.selectedRow = var1;
		this.subMenu = MAIN_MENU;
	}

	public final void resetPauseMenu() {
		this.subMenu = PAUSE;
		this.selectedRow = 0;
		this.confirmPopupOpen = false;
		this.forcePauseMenu_ = true;
	}

	public final void draw() {
		GlobalStatus.graphics.drawImage(this.gameLogo, GlobalStatus.screenWidth / 2, 10, 17);

		switch(this.subMenu) {
			case MAIN_MENU:
				this.saveGameItemHeight = Status.getPlayingTime() > 0L ? this.fontSpacingY : 0;
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(67), posX, headerHeight, windowWidth, ((Status.getPlayingTime() > 0L ? menuItemCounts[this.subMenu] : menuItemCounts[this.subMenu] - 1) + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(0), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(1), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				if (Status.getPlayingTime() > 0L) {
					Layout.drawTextItem(GlobalStatus.gameText.getText(2), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				}

				Layout.drawTextItem(GlobalStatus.gameText.getText(3), posX, this.optionsListPosY + 2 * this.fontSpacingY + this.saveGameItemHeight, windowWidth, this.selectedRow == 3);
				Layout.drawTextItem(GlobalStatus.gameText.getText(4), posX, this.optionsListPosY + 3 * this.fontSpacingY + this.saveGameItemHeight, windowWidth, this.selectedRow == 4);
				Layout.drawTextItem(GlobalStatus.gameText.getText(5), posX, this.optionsListPosY + 4 * this.fontSpacingY + this.saveGameItemHeight, windowWidth, this.selectedRow == 5);
				if (this.webAccessAvailable) {
					Layout.drawTextItem(this.operatorMenuEntry, posX, this.optionsListPosY + 5 * this.fontSpacingY + this.saveGameItemHeight, windowWidth, this.selectedRow == 6);
				}
				break;
			case LOAD_GAME:
			case SAVE_GAME:
				int barHeight = 6 * this.fontSpacingY;
				if (this.subMenu == SAVE_GAME) {
					Layout.drawFilledTitleBarWindow(
					  GlobalStatus.gameText.getText(2),
					  GlobalStatus.screenWidth - this.recordWindowWidth >> 1,
					  headerHeight,
					  this.recordWindowWidth,
					  barHeight
					);
				} else {
					Layout.drawFilledTitleBarWindow(
					  GlobalStatus.gameText.getText(1),
					  GlobalStatus.screenWidth - this.recordWindowWidth >> 1,
					  headerHeight,
					  this.recordWindowWidth,
					  barHeight
					);
				}

				for (int i = 0; i < this.saves.length; i++) {
					this.saveInfo = i + 1 + ".  ";
					if (this.saves[i] == null) {
						this.saveInfo = this.saveInfo + GlobalStatus.gameText.getText(26);
					} 
					else {
						this.saveInfo = this.saveInfo + Time.timeToHM(this.saves[i].playTime);
						this.stationName = this.saves[i].stationName;
						if (i == 3) {
							this.saveInfo = this.saveInfo + " AUTOSAVE";
						} else {
							this.saveInfo = this.saveInfo + " " + this.stationName;
						}
					}
					Layout.drawTextItem(this.saveInfo, GlobalStatus.screenWidth - this.recordWindowWidth >> 1, this.optionsListPosY + i * this.fontSpacingY, this.recordWindowWidth, this.selectedRow == i);
				}
				break;
			case OPTIONS:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(3), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(6) + "  " + GlobalStatus.gameText.getText(GameText.soundLevels[this.musicLevel]), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(7) + "  " + GlobalStatus.gameText.getText(GameText.soundLevels[this.soundLevel]), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(11) + "  " + GlobalStatus.gameText.getText(GlobalStatus.vibrationOn ? 15 : 16), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				Layout.drawTextItem(GlobalStatus.gameText.getText(14) + "  " + GlobalStatus.gameText.getText(GlobalStatus.invertedControlsOn ? 15 : 16), posX, this.optionsListPosY + 3 * this.fontSpacingY, windowWidth, this.selectedRow == 3);
				break;
			case HELP:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(4), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(19), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(20), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(21), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				break;
			case PAUSE:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(17), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(3), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(4), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(5), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				break;
			case HELP_INSTRUCTIONS:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(19), posX, headerHeight, windowWidth, AEMath.min(this.manualWindow.getFirstListLen_() * Font.getSpacingY() + 30, GlobalStatus.screenHeight - headerHeight - 16 - 10));
				this.manualWindow.drawItems();
				this.manualWindow.drawScroll();
				break;
			case HELP_CONTROLS:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(20), posX / 2, headerHeight, windowWidth + posX, AEMath.min(GlobalStatus.screenHeight - headerHeight - 16 - 10, this.controls.getHeight_()));
				this.controls.draw();
				break;
			case HELP_CREDITS:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(21), posX / 2, headerHeight, windowWidth + posX, GlobalStatus.screenHeight - headerHeight - 16 - 10);
				Font.drawString(GlobalStatus.gameVersion, (GlobalStatus.screenWidth >> 1) - (Font.getStringWidth(GlobalStatus.gameVersion) >> 1), 10 + this.gameLogo.getHeight() + 2, 1);
				this.credits.draw();
			case UNK_SUBMENU_9:
			case UNK_SUBMENU_10:
			case UNK_SUBMENU_12:
			case UNK_SUBMENU_13:
			case UNK_SUBMENU_14:
			default:
				break;
			case NAME_INPUT:
				Layout.drawTitleBarWindow(GlobalStatus.gameText.getText(34), posX, headerHeight, windowWidth, 4 * this.fontSpacingY, true);
				this.nameInput.update(this.frameTime);
				Font.drawString(this.nameInput.getText() + this.nameInput.getNextChar(), posX + 10, this.optionsListPosY + this.fontSpacingY, 0);
				if (Layout.quickClockHigh_()) {
					Font.drawString("_", posX + 10 + Font.getStringWidth(this.nameInput.getText()), this.optionsListPosY + 2 + this.fontSpacingY, 0);
				}
				break;
			case HELP_INSTR_FIRST:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case HELP_INSTR_LAST:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(GameText.helpTitles[this.selectedRow]), posX / 2, headerHeight, windowWidth + posX, GlobalStatus.screenHeight - headerHeight - 16 - 10);
				this.instructions.draw();
		}

		if (this.confirmPopupOpen) {
			this.confirmPopup.draw();
			Layout.drawFooter1("", "", false);
		} else if (this.subMenu == MAIN_MENU) {
			Layout.drawFooter1(GlobalStatus.gameText.getText(253), Status.getPlayingTime() == 0L ? "" : GlobalStatus.gameText.getText(65), false);
		} else if (this.subMenu == PAUSE) {
			Layout.drawFooter1(GlobalStatus.gameText.getText(253), GlobalStatus.gameText.getText(18), false);
		} else if (this.subMenu != NAME_INPUT) {
			if (this.subMenu != HELP_CONTROLS &&
			  this.subMenu != HELP_CREDITS &&
			  this.subMenu != UNK_SUBMENU_14 &&
			  this.subMenu != UNK_SUBMENU_13 &&
			  (this.subMenu < HELP_INSTR_FIRST || this.subMenu > HELP_INSTR_24)) { // BUG, shows OK in Help/Instructions/Reputation footer
				Layout.drawFooter1(GlobalStatus.gameText.getText(35), GlobalStatus.gameText.getText(65), false);
				return;
			}

			Layout.drawFooter1("", GlobalStatus.gameText.getText(65), false);
		}
	}

	public final boolean handleKeystate(final int keyState) {
		if (this.subMenu == NAME_INPUT) {
			if (keyState == GOF2Canvas.RSB && this.nameInput.getText().length() == 0) {
				this.selectedRow = 0;
				this.subMenu = UNK_SUBMENU_9;
			}

			if (keyState != 0 && keyState != -1) {
				this.nameInput.handleKeystate(keyState);
			}
		}

		return true;
	}
}
