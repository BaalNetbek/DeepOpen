package GoF2;

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

			for(int var1 = 0; var1 < backgroundDimmColors.length; ++var1) {
				backgroundDimmColors[var1] = -2013265920;
			}
		}

	}

	public static void addTicks(final int var0) {
		slowTickCounter += var0;
		quickTickCounter += var0;
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
		for(int var0 = 0; var0 < GlobalStatus.screenWidth / 32 + 1; ++var0) {
			for(int var1 = 0; var1 < GlobalStatus.screenHeight / 32 + 1; ++var1) {
				GlobalStatus.graphics.drawRGB(backgroundDimmColors, 0, 32, var0 << 5, var1 << 5, 32, 32, true);
			}
		}

	}

	public static void drawTextItem(final String var0, final int var1, final int var2, final int var3, final boolean var4) {
		drawTextItem(var0, var1, var2, var3, var4, false);
	}

	public static void drawTextItem(final String var0, final int var1, final int var2, final int var3, final boolean var4, final boolean var5, final boolean var6) {
		Font.drawString(var0, var5 ? var1 + (var3 >> 1) : var1 + 12, var2 + 1, var4 ? 2 : 1, var5 ? 24 : 17);
		if (var6) {
			if (lock == null) {
				lock = AEFile.loadImage("/data/interface/lock.png", true);
			}

			if (Status.getCurrentCampaignMission() < 13 && (var0.equals(GlobalStatus.gameText.getText(218)) || var0.equals(GlobalStatus.gameText.getText(33))) || Status.getCurrentCampaignMission() < 9 && var0.equals(GlobalStatus.gameText.getText(72)) || Status.getCurrentCampaignMission() < 5 && var0.equals(GlobalStatus.gameText.getText(62))) {
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

		for(int var7 = 0; var7 < var2 / var5 + 1; ++var7) {
			for(int var8 = 0; var8 < var3 / var6 + 1; ++var8) {
				GlobalStatus.graphics.drawImage(var4, var0 + var7 * var5, var1 + var8 * var6, 20);
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

		for(int var3 = var4.length() - 1; var3 >= 0; --var3) {
			++var1;
			var2 = var4.charAt(var3) + var2;
			if (var1 == 3 && var3 > 0) {
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

	public static void drawFilledTitleBarWindow(final String var0, final int var1, final int var2, final int var3, final int var4) {
		drawTitleBarWindow(var0, var1, var2, var3, var4, true);
	}

	public static void drawTitleBarWindow(final String var0, final int var1, final int var2, final int var3, final int var4, final boolean var5) {
		if (var5) {
			drawBGPattern(var1, var2, var3, var4, menuBackground);
		}

		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(var1 + 1, var2 + 1, var3 - 2, var4 - 2);
		GlobalStatus.graphics.setColor(uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(var1 + 2, var2 + 2, var3 - 4, var4 - 4);
		drawRect(var1, var2, var3, var4);
		if (var0 != null) {
			drawBGPattern(var1 + 2, var2 + 2, var3 - 3, 11, menuHeaderPattern);
			GlobalStatus.graphics.setColor(uiInnerOutlineColor);
			GlobalStatus.graphics.drawLine(var1 + 2, var2 + 14, var1 + var3 - 3, var2 + 14);
			GlobalStatus.graphics.setColor(0);
			GlobalStatus.graphics.drawLine(var1 + 1, var2 + 13, var1 + var3 - 2, var2 + 13);
			GlobalStatus.graphics.drawImage(mainMenuCorener, var1, var2, 20);
		}

		if (var0 != null && !var0.equals("")) {
			Font.drawString(var0, var1 + 6, var2 + 1, 0);
		}

	}

	public static void drawMenuPanelCorner(final int var0, final int var1, final boolean var2) {
		GlobalStatus.graphics.drawImage(var2 ? menuPanelCorActive : menuPanelCorInactive, var0, var1, 20);
	}

	public static void drawFooter(final String var0, final String var1) {
		final String var10000 = var0;
		drawFooter(var10000, var1, false);
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
		final String var10000 = var0;
		String var6;
		Font.drawString((var6 = Font.truncateStringLine(var0 = var10000.trim(), var3 - 2, 0, true)).equals(var0) ? var0 : var6 + "...", var1, var2, var4, 17);
	}

	public static void drawFooter1(final String var0, final String var1, final boolean var2) {
		drawFooter(var0, var1, false);
	}

	private static void drawRect(final int var0, final int var1, final int var2, final int var3) {
		GlobalStatus.graphics.setColor(uiOuterTopRightOutlineColor);
		GlobalStatus.graphics.drawLine(var0, var1, var0 + var2, var1);
		GlobalStatus.graphics.drawLine(var0 + var2, var1, var0 + var2, var1 + var3);
		GlobalStatus.graphics.setColor(uiOuterDownLeftOutlineInnerLabalBgColor);
		GlobalStatus.graphics.drawLine(var0, var1, var0, var1 + var3);
		GlobalStatus.graphics.drawLine(var0, var1 + var3, var0 + var2, var1 + var3);
	}

	public static void drawFooter(final String var0, final String var1, final boolean var2) {
		final boolean var3 = leftButtonSelected && naviDelay > 0 && !var0.equals("") || tickHighlight;
		final boolean var4 = !leftButtonSelected && naviDelay > 0 && !var1.equals("");
		drawBGPattern(3, GlobalStatus.screenHeight - 15, GlobalStatus.screenWidth - 5, 11, menuHeaderPattern);
		drawRect(1, GlobalStatus.screenHeight - 17, GlobalStatus.screenWidth - 3, 14);
		GlobalStatus.graphics.setColor(0);
		GlobalStatus.graphics.drawRect(2, GlobalStatus.screenHeight - 16, GlobalStatus.screenWidth - 5, 12);
		Font.drawString(var0, 10, GlobalStatus.screenHeight - 4, var3 ? 2 : 1, 33);
		Font.drawString(var1, GlobalStatus.screenWidth - 10, GlobalStatus.screenHeight - 4, var4 ? 2 : 1, 34);
		if (var2) {
			GlobalStatus.graphics.drawImage(skipArrow, GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight - 5, 33);
		}

	}
}
