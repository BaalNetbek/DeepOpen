package GOF2;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;

public final class Layout {
	public static int uiInnerOutlineColor = 0xFF255D8D;
	public static int uiOuterTopRightOutlineColor = 0xFF244D70;
	public static int uiOuterDownLeftOutlineInnerLabalBgColor = 0xFF0D2941;
	public static int uiInactiveInnerLabelColor = 0xFF1C1C1C;
	private static int footerHeight;
	private static Image skipArrow;
	private static Image lock;
	private static Image menuBackground;
	private static Image menuHeaderPattern;
	private static Image mainMenuCorener;
	private static Image menuPanelCorInactive;
	private static Image menuPanelCorActive;
	private static int[] backgroundDimmColors;
	private static long quickTickCounter;
	private static long slowTickCounter;
	private static int naviDelay;
	private static boolean leftButtonSelected;
	private static boolean tickHighlight;

	private Layout() {
	}

	public static void OnRelease() {
		backgroundDimmColors = null;
	}

	public static void OnInitialize() {
		footerHeight = 10;
		if (skipArrow == null) {
			skipArrow = AEFile.loadImage("/data/interface/skip.png", true);
			menuBackground = AEFile.loadImage("/data/interface/menu_background.png", true);
			menuHeaderPattern = AEFile.loadImage("/data/interface/menu_header_pattern.png", true);
			mainMenuCorener = AEFile.loadImage("/data/interface/menu_main_corner.png", true);
			menuPanelCorInactive = AEFile.loadImage("/data/interface/menu_panel_corner_inactive.png", true);
			menuPanelCorActive = AEFile.loadImage("/data/interface/menu_panel_corner_active.png", true);
		}

		if (backgroundDimmColors == null) {
			backgroundDimmColors = new int[1024];

			for(int i = 0; i < backgroundDimmColors.length; ++i) {
				backgroundDimmColors[i] = 0x88000000;
			}
		}

	}

	public static void addTicks(final int dt) {
		slowTickCounter += dt;
		quickTickCounter += dt;
	}

	public static boolean slowClockHigh_() {
		if (slowTickCounter <= 1000L) {
			return false;
		}
		if (slowTickCounter > 2000L) {
			slowTickCounter = 0L;
		}

		return true;
	}

	public static boolean quickClockHigh_() {
		if (quickTickCounter <= 300L) {
			return false;
		}
		if (quickTickCounter > 600L) {
			quickTickCounter = 0L;
		}

		return true;
	}

	public static void drawMask(final int var0, final int var1, final int var2, final int var3) {
		GlobalStatus.graphics.setClip(10, var1, var2, var3);
		fillClip();
		GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
	}

	public static void fillClip() {
		for(int i = 0; i < GlobalStatus.screenWidth / 32 + 1; ++i) {
			for(int j = 0; j < GlobalStatus.screenHeight / 32 + 1; ++j) {
				GlobalStatus.graphics.drawRGB(backgroundDimmColors, 0, 32, i << 5, j << 5, 32, 32, true);
			}
		}

	}

	public static void drawTextItem(final String var0, final int var1, final int var2, final int var3, final boolean var4) {
		drawTextItem(var0, var1, var2, var3, var4, false);
	}

	public static void drawTextItem(final String var0, final int var1, final int var2, final int var3, final boolean highlight, final boolean var5, final boolean drawImage) {
		Font.drawString(
		  var0,
		  var5 ? var1 + (var3 >> 1) : var1 + 12,
		  var2 + 1,
		  highlight ? 2 : 1,
		  var5 ? Font.TOP|Font.HCENTER : Font.TOP|Font.LEFT
		);
		if (drawImage) {
			if (lock == null) {
				lock = AEFile.loadImage("/data/interface/lock.png", true);
			}

			if (Status.getCurrentCampaignMission() < 13 && (var0.equals(GlobalStatus.gameText.getText(218))
             || var0.equals(GlobalStatus.gameText.getText(33))) 
             || Status.getCurrentCampaignMission() < 9 && var0.equals(GlobalStatus.gameText.getText(72)) 
             || Status.getCurrentCampaignMission() < 5 && var0.equals(GlobalStatus.gameText.getText(62))) {
				GlobalStatus.graphics.drawImage(lock, var1 + 3, var2 + 2, 0);
			}
		}

	}

	public static void drawTextItem(final String var0, final int var1, final int var2, final int var3, final boolean var4, final boolean var5) {
		drawTextItem(var0, var1, var2, var3, var4, var5, false);
	}

	public static void drawBG() {
		drawBGPattern(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight, menuBackground);
	}

	private static void drawBGPattern(final int var0, final int var1, final int var2, final int var3, final Image var4) {
		final int var5 = var4.getWidth();
		final int var6 = var4.getHeight();
		GlobalStatus.graphics.setClip(var0, var1, var2, var3);

		for(int i = 0; i < var2 / var5 + 1; ++i) {
			for(int j = 0; j < var3 / var6 + 1; ++j) {
				GlobalStatus.graphics.drawImage(var4, var0 + i * var5, var1 + j * var6, 20);
			}
		}

		GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
	}

	public static void drawWindowFrame(final String var0) {
		drawNonFullScreenWindow(var0, false);
	}

	public static void drawNonFullScreenWindow(final String var0, final boolean var1) {
		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(0, 0, GlobalStatus.screenWidth - 1, GlobalStatus.screenHeight - 1);
		GlobalStatus.graphics.drawLine(0, GlobalStatus.screenHeight - 2, GlobalStatus.screenWidth, GlobalStatus.screenHeight - 2);
		drawTitleBarWindow(var0, 1, 1, GlobalStatus.screenWidth - 3, GlobalStatus.screenHeight - 8 - footerHeight, false);
	}

	public static String formatCredits(final int var0) {
		final String var4 = String.valueOf(var0);
		int var1 = 0;
		String var2 = "";

		for(int i = var4.length() - 1; i >= 0; --i) {
			++var1;
			var2 = var4.charAt(i) + var2;
			if (var1 == 3 && i > 0) {
				var1 = 0;
				var2 = "." + var2;
			}
		}

		if (var2.length() > 2 && var2.charAt(1) == '.' && var2.charAt(0) == '-') {
			var2 = var2.substring(2);
			var2 = "-" + var2;
		}

		return var2 + "$";
	}

	public static void drawFilledTitleBarWindow(final String title, final int x, final int y, final int width, final int height) {
		drawTitleBarWindow(title, x, y, width, height, true);
	}

	public static void drawTitleBarWindow(final String title, final int x, final int y, final int width, final int height, final boolean filled) {
		if (filled) {
			drawBGPattern(x, y, width, height, menuBackground);
		}

		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
		GlobalStatus.graphics.setColor(uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(x + 2, y + 2, width - 4, height - 4);
		drawRect(x, y, width, height);
		if (title != null) {
			drawBGPattern(x + 2, y + 2, width - 3, 11, menuHeaderPattern);
			GlobalStatus.graphics.setColor(uiInnerOutlineColor);
			GlobalStatus.graphics.drawLine(x + 2, y + 14, x + width - 3, y + 14);
			GlobalStatus.graphics.setColor(0);
			GlobalStatus.graphics.drawLine(x + 1, y + 13, x + width - 2, y + 13);
			GlobalStatus.graphics.drawImage(mainMenuCorener, x, y, 20);
		}

		if (title != null && !title.equals("")) {
			Font.drawString(title, x + 6, y + 1, 0);
		}

	}

	public static void drawMenuPanelCorner(final int var0, final int var1, final boolean var2) {
		GlobalStatus.graphics.drawImage(var2 ? menuPanelCorActive : menuPanelCorInactive, var0, var1, 20);
	}

	public static void drawFooter(final String leftText, final String rightText) {
		drawFooter(leftText, rightText, false);
	}

	public static void selectNavigationButton(final boolean var0) {
		leftButtonSelected = var0;
		naviDelay = 5;
	}

	public static boolean navigationDelayPassed() {
		return naviDelay <= 0;
	}

	public static void navigationDelayDownTick() {
		--naviDelay;
	}

	public static void setTickHighlight(final boolean var0) {
		tickHighlight = var0;
	}

	public static void drawStringWidthLimited(String var0, final int var1, final int var2, final int var3, final int var4) {
		String var6 = Font.truncateStringLine(var0 = var0.trim(), var3 - 2, 0, true);
		Font.drawString(
				var6.equals(var0) ? var0 : var6 + "...",
				var1,
				var2,
				var4,
				Font.TOP | Font.LEFT
		);
	}

	public static void drawFooter1(final String var0, final String var1, final boolean var2) {
		drawFooter(var0, var1, false);
	}

	private static void drawRect(final int x, final int y, final int width, final int height) {
		GlobalStatus.graphics.setColor(uiOuterTopRightOutlineColor);
		GlobalStatus.graphics.drawLine(x, y, x + width, y);
		GlobalStatus.graphics.drawLine(x + width, y, x + width, y + height);
		GlobalStatus.graphics.setColor(uiOuterDownLeftOutlineInnerLabalBgColor);
		GlobalStatus.graphics.drawLine(x, y, x, y + height);
		GlobalStatus.graphics.drawLine(x, y + height, x + width, y + height);
	}

	public static void drawFooter(final String var0, final String var1, final boolean var2) {
		final boolean var3 = leftButtonSelected && naviDelay > 0 && !var0.equals("") || tickHighlight;
		final boolean var4 = !leftButtonSelected && naviDelay > 0 && !var1.equals("");
		drawBGPattern(3, GlobalStatus.screenHeight - 15, GlobalStatus.screenWidth - 5, 11, menuHeaderPattern);
		drawRect(1, GlobalStatus.screenHeight - 17, GlobalStatus.screenWidth - 3, 14);
		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(2, GlobalStatus.screenHeight - 16, GlobalStatus.screenWidth - 5, 12);
		Font.drawString(
		  var0,
		  10,
		  GlobalStatus.screenHeight - 4,
		  var3 ? 2 : 1,
		  Font.BOTTOM|Font.LEFT
		);
		Font.drawString(
				var1,
				GlobalStatus.screenWidth - 10,
				GlobalStatus.screenHeight - 4,
				var4 ? 2 : 1,
				Font.BOTTOM | Font.RIGHT
		);
		if (var2) {
			GlobalStatus.graphics.drawImage(
					skipArrow,
					GlobalStatus.screenWidth >> 1,
					GlobalStatus.screenHeight - 5,
					Graphics.BOTTOM | Graphics.HCENTER
			);
		}

	}
}
