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
		this.fontSpacingY = Font.getFontSpacingY();
		this.selectedRow = 0;
		this.subMenu = 0;
		this.confirmPopup = new Popup(20, GlobalStatus.screenHeight / 2, GlobalStatus.screenWidth - 40);
		this.confirmPopupOpen = false;
		this.forcePauseMenu_ = false;
		this.credits = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, GlobalStatus.gameText.getText(23) + "\n\n" + GlobalStatus.gameText.getText(25));
		this.instructions = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, "");
		this.controls = new TextBox(posX / 2 + 8, this.optionsListPosY, windowWidth + posX - 16, GlobalStatus.screenHeight - headerHeight - 48, GlobalStatus.gameText.getText(22));
		this.hidden__ = new TextBox(posX + 8, this.optionsListPosY, windowWidth - 16, GlobalStatus.screenHeight - headerHeight - 48, "");
		final String[] var1 = new String[GameText.helpFull.length];

		for(int i = 0; i < var1.length; ++i) {
			var1[i] = GlobalStatus.gameText.getText(GameText.helpTitles[i]);
		}

		this.manualWindow = new ListWindow_(posX, headerHeight + 2, windowWidth, GlobalStatus.screenHeight - headerHeight - 16 - 10, (String[])null);
		this.manualWindow.setRowHeight(12);
		this.manualWindow.setEntries(0, var1);
		this.manualWindow.setSelectionHighlight(true);
		this.manualWindow.setBackGroundDraw(false);
		this.manualWindow.decFontIndex_();
		this.nameInput = new TextInput(10);
		this.musicLevel = GlobalStatus.musicVolume;
		this.soundLevel = GlobalStatus.sfxVolume;
		menuItemCounts[12] = 2;
	}

	private static String unescape(final String var0) {
		int var1 = 0;
		final int var2 = var0.length();
		final StringBuffer var3 = new StringBuffer();

		while(true) {
			while(var1 < var2) {
				char var4 = var0.charAt(var1);
				if (var4 == '\\' && var1 + 6 <= var2 && var0.charAt(var1 + 1) == 'u') {
					var4 = (char)Integer.parseInt(var0.substring(var1 + 2, var1 + 6), 16);
					var3.append(var4);
					var1 += 6;
				} else {
					var3.append(var4);
					++var1;
				}
			}

			return var3.toString();
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

	public final void scrollAndTick_(final int var1, final int var2) {
		this.frameTime = var2;
		Layout.addTicks(var2);
		if ((var1 & 64) != 0) {
			if (this.subMenu >= 15) {
				this.instructions.scrollUp(var2);
				return;
			}

			if (this.subMenu == 8) {
				this.credits.scrollUp(var2);
				return;
			}

			if (this.subMenu == 7) {
				this.controls.scrollUp(var2);
			}
		} else if ((var1 & 2) != 0) {
			if (this.subMenu >= 15) {
				this.instructions.scrollDown(var2);
				return;
			}

			if (this.subMenu == 8) {
				this.credits.scrollDown(var2);
				return;
			}

			if (this.subMenu == 7) {
				this.controls.scrollDown(var2);
			}
		}

	}

	public final boolean update() {
		RecordHandler var1;
		switch(this.subMenu) {
		case 0:
			if (this.confirmPopupOpen && this.selectedRow == 5) {
				if (this.confirmPopup.getChoice()) {
					GlobalStatus.applicationManager.Quit();
					return true;
				}

				this.confirmPopupOpen = false;
				return false;
			}

			switch(this.selectedRow) {
			case 0:
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
			case 1:
				this.subMenu = 1;
				if (this.saves == null) {
					var1 = new RecordHandler();
					this.saves = var1.readAllPreviews();
				}

				loadSavePreviews(this.saves);
				this.selectedRow = 0;
				return false;
			case 2:
				this.subMenu = 2;
				if (this.saves == null) {
					var1 = new RecordHandler();
					this.saves = var1.readAllPreviews();
				}

				loadSavePreviews(this.saves);
				this.selectedRow = 0;
				return false;
			case 3:
				this.subMenu = 3;
				this.selectedRow = 0;
				return false;
			case 4:
				this.subMenu = 4;
				this.selectedRow = 0;
				return false;
			case 5:
				if (!this.confirmPopupOpen) {
					this.confirmPopup.set(GlobalStatus.gameText.getText(31), true);
					this.confirmPopupOpen = true;
				}

				return false;
			case 6:
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
		case 1:
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
		case 2:
			if (this.confirmPopupOpen) {
				if (!this.confirmPopup.getChoice()) {
					this.confirmPopupOpen = false;
					return true;
				}

				GlobalStatus.loadingScreen.startLoading_(false);
				(var1 = new RecordHandler()).saveGame(this.selectedRow);
				this.saves[this.selectedRow] = var1.readPreview(this.selectedRow);
				loadSavePreviews(this.saves);
				GlobalStatus.loadingScreen.close();
				this.confirmPopup.set(GlobalStatus.gameText.getText(28), false);
			} else {
				if (this.selectedRow < this.saves.length && this.saves[this.selectedRow] == null) {
					GlobalStatus.loadingScreen.startLoading_(false);
					(var1 = new RecordHandler()).saveGame(this.selectedRow);
					this.saves = var1.readAllPreviews();
					GlobalStatus.loadingScreen.close();
					this.confirmPopup.set(GlobalStatus.gameText.getText(28), false);
				} else {
					this.confirmPopup.set(GlobalStatus.gameText.getText(27), true);
				}
				this.confirmPopupOpen = true;
			}
			break;
		case 3:
			optionsRight();
			break;
		case 4:
			switch(this.selectedRow) {
			case 0:
				this.subMenu = 6;
				this.selectedRow = 0;
				this.manualWindow.selectedEntry = 0;
				return false;
			case 1:
				this.subMenu = 7;
				return false;
			case 2:
				this.subMenu = 8;
				return false;
			default:
				return false;
			}
		case 5:
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
					this.subMenu = 3;
					this.selectedRow = 0;
					return false;
				case 1:
					this.subMenu = 4;
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
		case 6:
			this.instructions.zeroTopPadding();
			this.instructions.setText(GlobalStatus.gameText.getText(GameText.helpFull[this.selectedRow]));
			this.subMenu = 15 + this.selectedRow;
			System.out.println(this.selectedRow + "  " + this.subMenu);
		case 7:
		case 8:
		case 10:
		default:
			break;
		case 9:
			switch(this.selectedRow) {
			case 0:
				if (Status.getPlayingTime() > 0L) {
					if (!this.confirmPopupOpen) {
						this.confirmPopup.set(GlobalStatus.gameText.getText(30), true);
						this.confirmPopupOpen = true;
						return false;
					}

					if (this.confirmPopup.getChoice()) {
						this.subMenu = 11;
						this.selectedRow = 0;
					}

					this.confirmPopupOpen = false;
				} else {
					this.subMenu = 11;
					this.selectedRow = 0;
				}
				break;
			case 1:
				this.subMenu = 10;
				this.selectedRow = 0;
			}
		}

		return false;
	}

	private void loadGameRecord() {
		final RecordHandler var10000 = new RecordHandler();
		final int var2 = this.selectedRow;
		final RecordHandler var1 = var10000;
		new GameRecord();
		final GameRecord var4 = var1.recordStoreRead(var2);
		Status.startNewGame();
		Galaxy.setVisitedStations(var4.visitedStations);
		Status.setLastXP(var4.lastWorth);
		Status.setCredits(var4.credits);
		Status.setRating(var4.rating);
		Status.setPlayingTime(var4.playTime);
		Status.setKills(var4.kills);
		Status.setMissionCount(var4.missionCount);
		Status.setLevel(var4.level);
		Status.setLastXP(var4.lastWorth);
		Status.setGoodsProduced(var4.goodsProduced);
		Status.setPirateKills(var4.pirateKills);
		Status.setJumpgateUsed(var4.jumpGateUses);
		Status.setCargoSalvaged(var4.cargoSalvaged);
		Status.getUnusedVar(var4.unused__);
		Status.setStationsVisited(var4.stationsVisited);
		Status.wormholeStation = var4.wormholeStation;
		Status.wormholeSystem = var4.wormholeSystem;
		Status.lastVisitedNonVoidOrbit = var4.lastVisitedNonVoidOrbit;
		Status.wormHoleTick = var4.wormHoleTick;
		Status.setCurrentCampaignMission(var4.currentCampaignMissionIndex);
		Status.setFreelanceMission(var4.freelanceMission);
		Status.setCampaignMission(var4.storyMission);
		Status.setStationStack(var4.lastVisitedStations);
		Status.minedOreTypes = var4.minedOreTypes;
		Status.minedCoreTypes = var4.minedCoreTypes;
		Status.missionGoodsCarried = var4.missionGoodsCarried;
		Status.minedOre = var4.minedOre;
		Status.minedCores = var4.minedCores;
		Status.boughtBooze = var4.boughtBooze;
		Status.drinkTypesPossesed = var4.drinkTypesPossesed;
		Status.destroyedJunk = var4.destroyedJunk;
		Status.systemsVisited = var4.systemsVisited;
		Status.passengersCarried = var4.passengersCarried;
		Status.invisibleTime = var4.invisibleTime;
		Status.bombsUsed = var4.bombsUsed;
		Status.alienJunkSalvaged = var4.alienJunkSalvaged;
		Status.barInteractions = var4.barInteractions;
		Status.commandedWingmen = var4.commandedWingmen;
		Status.asteroidsDestroyed = var4.asteroidsDestroyed;
		Status.maxFreeCargo = var4.maxFreeCargo;
		Status.missionsRejected = var4.missionsRejected;
		Status.askedToRepeate = var4.askedToRepeate;
		Status.acceptedNotAskingDifficulty = var4.acceptedNotAskingDifficulty;
		Status.acceptedNotAskingLocation = var4.acceptedNotAskingLocation;
		Achievements.init();
		if (var4.achievements != null) {
			Achievements.setMedals(var4.achievements);
		}

		Status.setShip(var4.egoShip);
		Status.setStation(var4.currentStation);
		Status.setMission(Mission.emptyMission_);
		Status.standing = var4.standing;
		Status.blueprints = var4.blueprints;
		Status.waitingGoods = var4.waitingGoods;
		Status.specialAgents = var4.specialAgents;
		Status.wingmenNames = var4.wingmenNames;
		Status.wingmanRace = var4.wingmanRace;
		Status.wingmenTimeRemaining = var4.wingmenTimeRemaining;
		Status.wingmanFace = var4.wingmanFace;
		Status.passengerCount = var4.passengerCount;
		Status.visibleSystems = var4.visibleSystems;
		Status.lowestItemPrices = var4.lowestItemPrices;
		Status.highestItemPrices = var4.highestItemPrices;
		Status.lowestItemPriceSystems = var4.lowestItemPriceSystems;
		Status.highestItemPriceSystems = var4.highestItemPriceSystems;
		GlobalStatus.shopHelpShown = var4.shopHelpShown;
		GlobalStatus.shipHelpShown = var4.shipHelpShown;
		GlobalStatus.actionsHelpShown = var4.actionsHelpShown;
		GlobalStatus.bluePrintsHelpShown = var4.bluePrintsHelpShown;
		GlobalStatus.bluePrintInfoHelpShown = var4.bluePrintInfoHelpShown;
		GlobalStatus.barHelpShown = var4.barHelpShown;
		GlobalStatus.galaxyMapHelpShown = var4.galaxyMapHelpShown;
		GlobalStatus.systemMapHelpShown = var4.systemMapHelpShown;
		GlobalStatus.somehelpShown_unused_ = var4.unused12121_;
		GlobalStatus.miningHelpShown = var4.miningHelpShown;
		GlobalStatus.asteroidHelpShown = var4.asteroidHelpShown;
		GlobalStatus.missionsHelpShown = var4.missionsHelpShown;
		GlobalStatus.cargoFullHelpShown = var4.cargoFullHelpShown;
		GlobalStatus.wingmenHelpShown = var4.wingmenHelpShown;
		GlobalStatus.actionMenuHelpShown = var4.actionMenuHelpShown;
		GlobalStatus.boosterHelpShown = var4.boosterHelpShown;
		GlobalStatus.statusHelpShown = var4.statusHelpShown;
		GlobalStatus.medalsHelpShown = var4.medalsHelpShown;
		GlobalStatus.fanWingmenNoticeShown = var4.fanWingmenNoticeShown;
		GlobalStatus.voidxNoticeShown = var4.voidxNoticeShown;
		GlobalStatus.reputationHelpShown = var4.reputationHelpShown;
		GlobalStatus.buyingHelpShown = var4.buyingHelpShown;
		GlobalStatus.itemMountingHelpShown = var4.itemMountingHelpShown;
		GlobalStatus.itemMounting2HelpShown = var4.itemMounting2HelpShown;
		GlobalStatus.interplanetHelpShown = var4.interplanetHelpShown;
		GlobalStatus.jumpDriveHelpShown = var4.jumpDriveHelpShown;
		GlobalStatus.cloakHelpShown = var4.cloakHelpShown;
		Status.loadingsCount = var4.loadingsCount;
		Status.loadingTime = var4.timeSpentLoading;
		var4.visitedStations = null;
		((ModStation)GlobalStatus.scenes[1]).fromGameSave();
		GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[1]);
	}

	public final void scrollUp(final int var1) {
		if (!this.confirmPopupOpen) {
			if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 13 && this.subMenu != 14 && this.subMenu < 15) {
				if (this.selectedRow > 0) {
					--this.selectedRow;
				} else {
					this.selectedRow = menuItemCounts[this.subMenu] - 1;
				}

				if (this.selectedRow == 2 && this.subMenu == 0 && Status.getPlayingTime() == 0L) {
					--this.selectedRow;
				}

				if (this.subMenu == 6) {
					this.manualWindow.scrollUp();
				}
			} else if (this.subMenu == 13) {
				this.hidden__.scrollDown(var1);
			}

		}
	}

	public final void scrollDown(final int var1) {
		if (!this.confirmPopupOpen) {
			if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 13 && this.subMenu != 14 && this.subMenu < 15) {
				if (this.selectedRow < menuItemCounts[this.subMenu] - 1) {
					++this.selectedRow;
				} else {
					this.selectedRow = 0;
				}

				if (this.selectedRow == 2 && this.subMenu == 0 && Status.getPlayingTime() == 0L) {
					++this.selectedRow;
				}

				if (this.subMenu == 6) {
					this.manualWindow.scrollDown();
				}
			} else if (this.subMenu == 13) {
				this.hidden__.scrollUp(var1);
			}

		}
	}

	public final void optionsLeft() {
		if (this.confirmPopupOpen) {
			this.confirmPopup.left();
		} else if (this.subMenu == 3) {
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
		} else if (this.subMenu == 3) {
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
		if (this.subMenu != 0 && this.subMenu != 5) {
			if (this.subMenu == 3) {
				new RecordHandler().writeOptions();
			}

			if (this.subMenu == 7) {
				this.selectedRow = 1;
				this.subMenu = 4;
				return false;
			} else if (this.subMenu == 6) {
				this.selectedRow = 0;
				this.subMenu = 4;
				return false;
			} else if (this.subMenu >= 15) {
				this.subMenu = 6;
				return false;
			} else
				switch (this.subMenu) {
				case 10:
					this.selectedRow = 1;
					this.subMenu = 9;
					return false;
				case 8:
					this.selectedRow = 2;
					this.subMenu = 4;
					return false;
				case 12:
					this.subMenu = 11;
					return false;
				case 11:
					return false;
				default:
					if (this.subMenu > 0 || this.subMenu == 9) {
						if (this.subMenu == 9) {
							this.selectedRow = 0;
						} else {
							this.selectedRow = this.subMenu;
						}

						this.subMenu = 0;
						if (this.forcePauseMenu_) {
							resetPauseMenu();
						}
					}
					return false;
				}
		} else {
			return true;
		}
	}

	public final boolean update1_() {
		update();
		return false;
	}

	public final void reset_(final int var1) {
		this.selectedRow = var1;
		this.subMenu = 0;
	}

	public final void resetPauseMenu() {
		this.subMenu = 5;
		this.selectedRow = 0;
		this.confirmPopupOpen = false;
		this.forcePauseMenu_ = true;
	}

	public final void draw() {
		GlobalStatus.graphics.drawImage(this.gameLogo, GlobalStatus.screenWidth / 2, 10, 17);
		label197:
			switch(this.subMenu) {
			case 0:
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
			case 1:
			case 2:
				int var1 = 6 * this.fontSpacingY;
				if (this.subMenu == 2) {
					Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(2), GlobalStatus.screenWidth - this.recordWindowWidth >> 1, headerHeight, this.recordWindowWidth, var1);
				} else {
					Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(1), GlobalStatus.screenWidth - this.recordWindowWidth >> 1, headerHeight, this.recordWindowWidth, var1);
				}

				var1 = 0;

				while(true) {
					if (var1 >= this.saves.length) {
						break label197;
					}

					this.saveInfo = var1 + 1 + ".  ";
					if (this.saves[var1] == null) {
						this.saveInfo = this.saveInfo + GlobalStatus.gameText.getText(26);
					} else {
						this.saveInfo = this.saveInfo + Time.timeToHM(this.saves[var1].playTime);
						this.stationName = this.saves[var1].stationName;
						if (var1 == 3) {
							this.saveInfo = this.saveInfo + " AUTOSAVE";
						} else {
							this.saveInfo = this.saveInfo + " " + this.stationName;
						}
					}

					Layout.drawTextItem(this.saveInfo, GlobalStatus.screenWidth - this.recordWindowWidth >> 1, this.optionsListPosY + var1 * this.fontSpacingY, this.recordWindowWidth, this.selectedRow == var1);
					++var1;
				}
			case 3:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(3), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(6) + "  " + GlobalStatus.gameText.getText(GameText.soundLevels[this.musicLevel]), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(7) + "  " + GlobalStatus.gameText.getText(GameText.soundLevels[this.soundLevel]), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(11) + "  " + GlobalStatus.gameText.getText(GlobalStatus.vibrationOn ? 15 : 16), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				Layout.drawTextItem(GlobalStatus.gameText.getText(14) + "  " + GlobalStatus.gameText.getText(GlobalStatus.invertedControlsOn ? 15 : 16), posX, this.optionsListPosY + 3 * this.fontSpacingY, windowWidth, this.selectedRow == 3);
				break;
			case 4:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(4), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(19), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(20), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(21), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				break;
			case 5:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(17), posX, headerHeight, windowWidth, (menuItemCounts[this.subMenu] + 2) * this.fontSpacingY);
				Layout.drawTextItem(GlobalStatus.gameText.getText(3), posX, this.optionsListPosY, windowWidth, this.selectedRow == 0);
				Layout.drawTextItem(GlobalStatus.gameText.getText(4), posX, this.optionsListPosY + this.fontSpacingY, windowWidth, this.selectedRow == 1);
				Layout.drawTextItem(GlobalStatus.gameText.getText(5), posX, this.optionsListPosY + 2 * this.fontSpacingY, windowWidth, this.selectedRow == 2);
				break;
			case 6:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(19), posX, headerHeight, windowWidth, AEMath.min(this.manualWindow.getFirstListLen_() * Font.getFontSpacingY() + 30, GlobalStatus.screenHeight - headerHeight - 16 - 10));
				this.manualWindow.drawItems();
				this.manualWindow.drawScroll();
				break;
			case 7:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(20), posX / 2, headerHeight, windowWidth + posX, AEMath.min(GlobalStatus.screenHeight - headerHeight - 16 - 10, this.controls.getHeight_()));
				this.controls.draw();
				break;
			case 8:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(21), posX / 2, headerHeight, windowWidth + posX, GlobalStatus.screenHeight - headerHeight - 16 - 10);
				Font.drawString(GlobalStatus.gameVersion, (GlobalStatus.screenWidth >> 1) - (Font.getStringWidth(GlobalStatus.gameVersion) >> 1), 10 + this.gameLogo.getHeight() + 2, 1);
				this.credits.draw();
			case 9:
			case 10:
			case 12:
			case 13:
			case 14:
			default:
				break;
			case 11:
				Layout.drawTitleBarWindow(GlobalStatus.gameText.getText(34), posX, headerHeight, windowWidth, 4 * this.fontSpacingY, true);
				this.nameInput.update(this.frameTime);
				Font.drawString(this.nameInput.getText() + this.nameInput.getNextChar(), posX + 10, this.optionsListPosY + this.fontSpacingY, 0);
				if (Layout.quickClockHigh_()) {
					Font.drawString("_", posX + 10 + Font.getStringWidth(this.nameInput.getText()), this.optionsListPosY + 2 + this.fontSpacingY, 0);
				}
				break;
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
				Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(GameText.helpTitles[this.selectedRow]), posX / 2, headerHeight, windowWidth + posX, GlobalStatus.screenHeight - headerHeight - 16 - 10);
				this.instructions.draw();
			}

		if (this.confirmPopupOpen) {
			this.confirmPopup.draw();
			Layout.drawFooter1("", "", false);
		} else if (this.subMenu == 0) {
			Layout.drawFooter1(GlobalStatus.gameText.getText(253), Status.getPlayingTime() == 0L ? "" : GlobalStatus.gameText.getText(65), false);
		} else if (this.subMenu == 5) {
			Layout.drawFooter1(GlobalStatus.gameText.getText(253), GlobalStatus.gameText.getText(18), false);
		} else if (this.subMenu != 11) {
			if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 14 && this.subMenu != 13 && (this.subMenu < 15 || this.subMenu > 24)) {
				Layout.drawFooter1(GlobalStatus.gameText.getText(35), GlobalStatus.gameText.getText(65), false);
				return;
			}

			Layout.drawFooter1("", GlobalStatus.gameText.getText(65), false);
		}
	}

	public final boolean handleKeystate(final int var1) {
		if (this.subMenu == 11) {
			if (var1 == 8192 && this.nameInput.getText().length() == 0) {
				this.selectedRow = 0;
				this.subMenu = 9;
			}

			if (var1 != 0 && var1 != -1) {
				this.nameInput.handleKeystate(var1);
			}
		}

		return true;
	}
}
