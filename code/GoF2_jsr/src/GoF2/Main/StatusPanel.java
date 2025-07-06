package GoF2.Main;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import AE.AEFile;
import AE.GlobalStatus;
import AE.Time;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import GoF2.Achievements;
import GoF2.Globals;
import GoF2.Layout;
import GoF2.ListItem;
import GoF2.Popup;
import GoF2.Standing;
import GoF2.Status;

public final class StatusPanel {
	private boolean infoOpen;
	private Popup infoMedal;
	private StatusWindow statusWindows;
	private String[] statsLeftColumn;
	private String[] statsRightColumn;
	private Sprite standingBarOutter;
	private Sprite standingBarInner;
	private Sprite standingCursor;
	private Sprite standingLogos;
	private Image allMedalsAdornment;
	private Image allGoldAdornment;
	private final int posY;
	private final int scrollRows;
	private final int posX;
	private int width;
	private final int height;
	private int scrollThumbSize;
	private int scrollPos;
	private Image[] face;

	public StatusPanel() {
		this.statusWindows = new StatusWindow(new String[]{GlobalStatus.gameText.getText(64), GlobalStatus.gameText.getText(63)}, GlobalStatus.gameText.getText(64));
		this.infoOpen = false;
		this.infoMedal = new Popup(20, GlobalStatus.screenHeight / 3, GlobalStatus.screenWidth - 40);
		init();
		this.posY = 35;
		this.posX = 9;
		this.scrollPos = this.posY;
		this.scrollRows = GlobalStatus.screenHeight - this.posY - 16 - 5;
		this.width = GlobalStatus.screenWidth - 2 * this.posX;
		this.height = 66 + ImageFactory.faceHeight + 4 + 6 * (Font.getFontSpacingY() + 2) + this.statsLeftColumn.length * (Font.getFontSpacingY() + 2);
		this.scrollThumbSize = 0;
		if (this.height > this.scrollRows) {
			this.scrollThumbSize = (int)((float)this.scrollRows / (float)this.height * this.scrollRows);
			this.width -= 5;
		}

		Image var2 = AEFile.loadImage("/data/interface/logos_small.png", true);
		this.standingLogos = new Sprite(var2, var2.getHeight(), var2.getHeight());
		if (GlobalStatus.screenWidth < 240) {
			var2 = AEFile.loadImage("/data/interface/standing_small_0.png", true);
		} else {
			var2 = AEFile.loadImage("/data/interface/standing_0.png", true);
		}

		this.standingBarOutter = new Sprite(var2, var2.getWidth() / 3, var2.getHeight());
		this.standingBarOutter.defineReferencePixel(0, this.standingBarOutter.getHeight());
		if (GlobalStatus.screenWidth < 240) {
			var2 = AEFile.loadImage("/data/interface/standing_small_1.png", true);
		} else {
			var2 = AEFile.loadImage("/data/interface/standing_1.png", true);
		}

		this.standingBarInner = new Sprite(var2);
		this.standingBarInner.defineReferencePixel(0, this.standingBarInner.getHeight());
		if (GlobalStatus.screenWidth < 240) {
			var2 = AEFile.loadImage("/data/interface/standing_small_2.png", true);
		} else {
			var2 = AEFile.loadImage("/data/interface/standing_2.png", true);
		}

		this.standingCursor = new Sprite(var2);
		this.standingCursor.defineReferencePixel(this.standingCursor.getWidth() >> 1, 0);
	}

	public final void OnRelease() {
		if (this.statusWindows != null) {
			this.statusWindows.OnRelease();
		}

		this.statusWindows = null;
		this.infoMedal = null;
		this.statsLeftColumn = null;
		this.statsRightColumn = null;
		this.standingBarOutter = null;
		this.standingBarInner = null;
		this.standingCursor = null;
		this.standingLogos = null;
	}

	public final void init() {
		this.statsLeftColumn = new String[11];
		this.statsRightColumn = new String[11];
		this.statsLeftColumn[0] = GlobalStatus.gameText.getText(77);
		this.statsRightColumn[0] = GlobalStatus.gameText.getText(532 + Status.getShip().getIndex());
		this.statsLeftColumn[1] = GlobalStatus.gameText.getText(290);
		this.statsRightColumn[1] = "" + Status.getShip().getFirePower();
		this.statsLeftColumn[2] = GlobalStatus.gameText.getText(291);
		this.statsRightColumn[2] = "" + Status.getShip().getCombinedHP();
		this.statsLeftColumn[3] = GlobalStatus.gameText.getText(33);
		this.statsRightColumn[3] = "" + Status.getMissionCount();
		this.statsLeftColumn[4] = GlobalStatus.gameText.getText(71);
		this.statsRightColumn[4] = "" + Status.getKills();
		this.statsLeftColumn[5] = GlobalStatus.gameText.getText(282);
		this.statsRightColumn[5] = "" + Status.getCargoSalvaged();
		this.statsLeftColumn[6] = GlobalStatus.gameText.getText(280);
		this.statsRightColumn[6] = "" + Status.getStationsVisited();
		this.statsLeftColumn[7] = GlobalStatus.gameText.getText(287);
		this.statsRightColumn[7] = "" + Status.getJumpgateUsed();
		this.statsLeftColumn[8] = GlobalStatus.gameText.getText(281);
		this.statsRightColumn[8] = "" + Status.getGoodsProduced();
		this.statsLeftColumn[9] = GlobalStatus.gameText.getText(283);
		this.statsRightColumn[9] = "" + Status.minedOre;
		this.statsLeftColumn[10] = GlobalStatus.gameText.getText(284);
		this.statsRightColumn[10] = "" + Status.minedCores;
	}

	public final boolean handleKeyPress(int var1) {
		if ((var1 == 256 || var1 == 16384) && this.statusWindows.getCurrentTab() == 1 && !this.infoOpen) {
			final ListItem var3 = (ListItem)this.statusWindows.getSelectedItem();
			if (Achievements.getMedals()[var3.itemId] != 0) {
				String var2 = Status.replaceToken(GlobalStatus.gameText.getText(782 + var3.itemId), Achievements.VALUES[var3.itemId][var3.medalTier - 1] + "");
				if (var3.itemId == 2 && var3.medalTier == 2) {
					var2 = var2 + "\n\n" + GlobalStatus.gameText.getText(134);

					for(var1 = 0; var1 < Status.minedOreTypes.length; ++var1) {
						if (!Status.minedOreTypes[var1]) {
							var2 = var2 + "\n- " + GlobalStatus.gameText.getText(var1 + 723);
						}
					}
				} else if (var3.itemId == 3 && var3.medalTier == 2) {
					var2 = var2 + "\n\n" + GlobalStatus.gameText.getText(134);

					for(var1 = 0; var1 < Status.minedCoreTypes.length; ++var1) {
						if (!Status.minedCoreTypes[var1]) {
							var2 = var2 + "\n- " + GlobalStatus.gameText.getText(var1 + 734);
						}
					}
				} else if (var3.itemId == 9 && var3.medalTier == 2) {
					var2 = var2 + "\n\n" + GlobalStatus.gameText.getText(134);

					for(var1 = 0; var1 < Status.drinkTypesPossesed.length; ++var1) {
						if (!Status.drinkTypesPossesed[var1]) {
							var2 = var2 + "\n- " + GlobalStatus.gameText.getText(var1 + 701);
						}
					}
				} else if (var3.itemId == 13 && var3.medalTier == 2) {
					var2 = var2 + "\n\n" + GlobalStatus.gameText.getText(134);

					for(var1 = 0; var1 < Status.blueprints.length; ++var1) {
						if (!Status.blueprints[var1].unlocked) {
							var2 = var2 + "\n- " + GlobalStatus.gameText.getText(569 + Status.blueprints[var1].getIndex());
						}
					}
				} else if (var3.itemId == 14 && var3.medalTier == 2) {
					var2 = var2 + "\n\n" + GlobalStatus.gameText.getText(134);

					for(var1 = 0; var1 < Status.blueprints.length; ++var1) {
						if (Status.blueprints[var1].timesProduced == 0) {
							var2 = var2 + "\n- " + GlobalStatus.gameText.getText(569 + Status.blueprints[var1].getIndex());
						}
					}
				}

				this.infoMedal.set(var2, false);
				this.infoOpen = true;
			}

			return true;
		}
		if (!this.infoOpen) {
			if (var1 == 4) {
				this.statusWindows.leftAction();
			}

			if (var1 == 32) {
				this.statusWindows.rightAction();
			}

			if (this.statusWindows.selectedTab == 1) {
				if (var1 == 2) {
					this.statusWindows.scrollUp();
				}

				if (var1 == 64) {
					this.statusWindows.scrollDown();
				}
			}

			return var1 != 8192;
		} else {
			if ((this.statusWindows.selectedTab == 1 || this.statusWindows.selectedTab == 2) && var1 == 256) {
				this.infoOpen = false;
			}

			return true;
		}
	}

	private void drawSubHeader(final int var1, final String var2) {
		final int var3 = this.width;
		GlobalStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
		GlobalStatus.graphics.fillRect(this.posX, var1, var3, 17);
		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(this.posX + 1, var1 + 1, var3 - 3, 16);
		GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(this.posX, var1, var3 - 1, 18);
		Layout.drawMenuPanelCorner(this.posX, var1, false);
		Font.drawString(var2, this.posX + 6, var1 + 4, 0);
	}

	public final void draw(final int var1, int var2) {
		Layout.drawBG();
		this.statusWindows.draw();
		if (this.statusWindows.selectedTab == 0) {
			if (this.scrollThumbSize > 0) {
				if ((var1 & 2) != 0) {
					this.scrollPos += var2 / 8;
					if (this.scrollPos > this.posY) {
						this.scrollPos = this.posY;
					}
				}

				if ((var1 & 64) != 0) {
					this.scrollPos -= var2 / 8;
					if (this.scrollPos + this.height < this.posY + this.scrollRows) {
						this.scrollPos = this.posY + this.scrollRows - this.height;
					}
				}
			}

			var2 = Font.getFontSpacingY();
			GlobalStatus.graphics.setColor(0);
			GlobalStatus.graphics.setClip(0, this.posY, GlobalStatus.screenWidth, this.scrollRows);
			int var3 = this.scrollPos;
			drawSubHeader(var3, GlobalStatus.gameText.getText(819));
			var3 += 22;
			if (this.face == null) {
				this.face = ImageFactory.faceFromByteArray(Globals.CHAR_KEITH);
			}

			ImageFactory.drawChar(this.face, this.posX, var3, 20);
			if (Achievements.gotAllGoldMedals()) {
				if (this.allGoldAdornment == null) {
					this.allGoldAdornment = AEFile.loadImage("/data/interface/faces/Brille_golden.png", true);
				}

				GlobalStatus.graphics.drawImage(this.allGoldAdornment, this.posX + 1, var3 + 1, 20);
			} else if (Achievements.gotAllMedals()) {
				if (this.allMedalsAdornment == null) {
					this.allMedalsAdornment = AEFile.loadImage("/data/interface/faces/Brille_schwarz.png", true);
				}

				GlobalStatus.graphics.drawImage(this.allMedalsAdornment, this.posX + 1, var3 + 1, 20);
			}

			Font.drawString(GlobalStatus.gameText.getText(80), this.posX + ImageFactory.faceWidth + 4, var3, 1, 17);
			Font.drawString(Layout.formatCredits(Status.getCredits()), this.posX + this.width, var3, 1, 18);
			var3 += var2 + 2;
			Font.drawString(GlobalStatus.gameText.getText(158), this.posX + ImageFactory.faceWidth + 4, var3, 1, 17);
			Font.drawString(Status.getLevel() + "", this.posX + this.width, var3, 1, 18);
			var3 += var2 + 2;
			Font.drawString(GlobalStatus.gameText.getText(70), this.posX + ImageFactory.faceWidth + 4, var3, 1, 17);
			Font.drawString(Time.timeToHMS(Status.getPlayingTime()), this.posX + this.width, var3, 1, 18);
			var3 = this.scrollPos + 18 + 4 + ImageFactory.faceHeight + 4;
			drawSubHeader(var3, GlobalStatus.gameText.getText(298));
			var3 += 26;
			final Standing var8 = Status.getStanding();

			int var5;
			for(var5 = 0; var5 < 2; ++var5) {
				int var6 = this.posX + 6;
				this.standingLogos.setFrame(var5 == 0 ? 0 : 2);
				this.standingLogos.setPosition(var6, var3);
				this.standingLogos.paint(GlobalStatus.graphics);
				var6 += this.standingLogos.getHeight() + 4;
				Font.drawString(GlobalStatus.gameText.getText(var5 == 0 ? 229 : 231), var6, var3 + Font.getFontSpacingY() + this.standingCursor.getHeight(), 1);
				this.standingBarOutter.setTransform(0);
				this.standingBarOutter.setFrame(var8.isEnemy(var5 == 0 ? 0 : 2) ? 0 : var8.isFriend(var5 == 0 ? 0 : 2) ? 1 : 2);
				this.standingBarOutter.setRefPixelPosition(var6, var3 + this.standingBarOutter.getHeight());
				this.standingBarOutter.paint(GlobalStatus.graphics);
				var6 += this.standingBarOutter.getWidth() + 1;
				this.standingBarInner.setTransform(0);
				this.standingBarInner.setRefPixelPosition(var6, var3 + this.standingBarOutter.getHeight());
				this.standingBarInner.paint(GlobalStatus.graphics);
				var6 = this.posX + this.width - 2;
				this.standingLogos.setFrame(var5 == 0 ? 1 : 3);
				this.standingLogos.setPosition(var6 - this.standingLogos.getHeight(), var3);
				this.standingLogos.paint(GlobalStatus.graphics);
				var6 -= 4 + this.standingLogos.getHeight();
				Font.drawString(GlobalStatus.gameText.getText(var5 == 0 ? 230 : 232), var6, var3 + Font.getFontSpacingY() + this.standingCursor.getHeight(), 1, 18);
				this.standingBarOutter.setTransform(2);
				this.standingBarOutter.setFrame(var8.isEnemy(var5 == 0 ? 1 : 3) ? 0 : var8.isFriend(var5 == 0 ? 1 : 3) ? 1 : 2);
				this.standingBarOutter.setRefPixelPosition(var6, var3 + this.standingBarOutter.getHeight());
				this.standingBarOutter.paint(GlobalStatus.graphics);
				var6 -= this.standingBarOutter.getWidth() + 1;
				this.standingBarInner.setTransform(2);
				this.standingBarInner.setRefPixelPosition(var6, var3 + this.standingBarOutter.getHeight());
				this.standingBarInner.paint(GlobalStatus.graphics);
				var6 = (GlobalStatus.screenWidth >> 1) - (int)(var8.getStanding(var5 == 0 ? 0 : 1) / 100.0F * (this.standingBarOutter.getWidth() + 1 + this.standingBarInner.getWidth()));
				this.standingCursor.setRefPixelPosition(var6, var3 + this.standingBarOutter.getHeight());
				this.standingCursor.paint(GlobalStatus.graphics);
				var3 += 3 * Font.getFontSpacingY() + 4;
			}

			this.drawSubHeader(var3, GlobalStatus.gameText.getText(299));
			var3 += 22;

			for(var5 = 0; var5 < this.statsLeftColumn.length; ++var5) {
				Font.drawString(this.statsLeftColumn[var5], this.posX + 2, var3, 1, 17);
				Font.drawString(this.statsRightColumn[var5], this.posX + this.width, var3, 0, 18);
				var3 += var2 + 2;
			}

			GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
			var2 = this.width;
			var3 = this.height;
			final int var9 = this.scrollPos;
			var5 = this.scrollThumbSize;
			if (var5 > 0) {
				var3 = (int)((float)(this.posY - var9) / (float)(var3 - this.scrollRows) * (this.scrollRows - var5));
				GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
				GlobalStatus.graphics.drawLine(this.posX + var2 + 3,
														this.posY,
														this.posX + var2 + 3,
												      this.posY + this.scrollRows - 1);
				GlobalStatus.graphics.setColor(0xFFFF7700);
				GlobalStatus.graphics.fillRect(this.posX + var2 + 2,
														this.posY + var3,
														3,
														var5);
				GlobalStatus.graphics.setColor(0xFFB65500);
				GlobalStatus.graphics.drawLine(this.posX + var2 + 2,
														this.posY + 1 + var3,
														this.posX + var2 + 2,
												      this.posY + var5 - 2 + var3);
				GlobalStatus.graphics.drawLine(this.posX + var2 + 2,
														this.posY + var5 - 1 + var3,
														this.posX + var2 + 3,
												      this.posY + var5 - 1 + var3);
				GlobalStatus.graphics.setColor(0xFFFFD300);
				GlobalStatus.graphics.drawLine(this.posX + var2 + 3,
														this.posY + 1 + var3,
														this.posX + var2 + 3,
												      this.posY + var5 - 2 + var3);
				GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
			}
		}

		if (this.statusWindows.getCurrentTab() == 1) {
			Layout.drawFooter(((ListItem)this.statusWindows.getSelectedItem()).medalTier > 0 ? GlobalStatus.gameText.getText(212) : "", GlobalStatus.gameText.getText(65));
		} else {
			Layout.drawFooter("", GlobalStatus.gameText.getText(65));
		}

		if (this.infoOpen) {
			this.infoMedal.draw();
		}

	}
}
