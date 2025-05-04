package GoF2;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import javax.microedition.lcdui.Image;

public final class Layout {
   public static int uiInnerOutlineColor = -14328435;
   public static int uiOuterTopRightOutlineColor = -14398096;
   public static int uiOuterDownLeftOutlineInnerLabalBgColor = -15914687;
   public static int uiInactiveInnerLabelColor = -14935012;
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
         String var0 = "/data/interface/skip.png";
         skipArrow = AEFile.loadImage("/data/interface/skip.png", true);
         var0 = "/data/interface/menu_background.png";
         menuBackground = AEFile.loadImage("/data/interface/menu_background.png", true);
         var0 = "/data/interface/menu_header_pattern.png";
         menuHeaderPattern = AEFile.loadImage("/data/interface/menu_header_pattern.png", true);
         var0 = "/data/interface/menu_main_corner.png";
         mainMenuCorener = AEFile.loadImage("/data/interface/menu_main_corner.png", true);
         var0 = "/data/interface/menu_panel_corner_inactive.png";
         menuPanelCorInactive = AEFile.loadImage("/data/interface/menu_panel_corner_inactive.png", true);
         var0 = "/data/interface/menu_panel_corner_active.png";
         menuPanelCorActive = AEFile.loadImage("/data/interface/menu_panel_corner_active.png", true);
      }

      if (backgroundDimmColors == null) {
         backgroundDimmColors = new int[1024];

         for(int var1 = 0; var1 < backgroundDimmColors.length; ++var1) {
            backgroundDimmColors[var1] = -2013265920;
         }
      }

   }

   public static void addTicks(int var0) {
      slowTickCounter += (long)var0;
      quickTickCounter += (long)var0;
   }

   public static boolean slowTickHigh_() {
      if (slowTickCounter > 1000L) {
         if (slowTickCounter > 2000L) {
            slowTickCounter = 0L;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean quickTickHigh_() {
      if (quickTickCounter > 300L) {
         if (quickTickCounter > 600L) {
            quickTickCounter = 0L;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void drawMask(int var0, int var1, int var2, int var3) {
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

   public static void drawTextItem(String var0, int var1, int var2, int var3, boolean var4) {
      drawTextItem(var0, var1, var2, var3, var4, false);
   }

   public static void drawTextItem(String var0, int var1, int var2, int var3, boolean var4, boolean var5, boolean var6) {
      Font.drawString(var0, var5 ? var1 + (var3 >> 1) : var1 + 12, var2 + 1, var4 ? 2 : 1, var5 ? 24 : 17);
      if (var6) {
         if (lock == null) {
            String var7 = "/data/interface/lock.png";
            lock = AEFile.loadImage("/data/interface/lock.png", true);
         }

         if (Status.getCurrentCampaignMission() < 13 && (var0.equals(GlobalStatus.gameText.getText(218)) || var0.equals(GlobalStatus.gameText.getText(33))) || Status.getCurrentCampaignMission() < 9 && var0.equals(GlobalStatus.gameText.getText(72)) || Status.getCurrentCampaignMission() < 5 && var0.equals(GlobalStatus.gameText.getText(62))) {
            GlobalStatus.graphics.drawImage(lock, var1 + 3, var2 + 2, 0);
         }
      }

   }

   public static void drawTextItem(String var0, int var1, int var2, int var3, boolean var4, boolean var5) {
      drawTextItem(var0, var1, var2, var3, var4, var5, false);
   }

   public static void drawBG() {
      drawBGPattern(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight, menuBackground);
   }

   private static void drawBGPattern(int var0, int var1, int var2, int var3, Image var4) {
      int var5 = var4.getWidth();
      int var6 = var4.getHeight();
      GlobalStatus.graphics.setClip(var0, var1, var2, var3);

      for(int var7 = 0; var7 < var2 / var5 + 1; ++var7) {
         for(int var8 = 0; var8 < var3 / var6 + 1; ++var8) {
            GlobalStatus.graphics.drawImage(var4, var0 + var7 * var5, var1 + var8 * var6, 20);
         }
      }

      GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
   }

   public static void drawWindowFrame(String var0) {
      drawNonFullScreenWindow(var0, false);
   }

   public static void drawNonFullScreenWindow(String var0, boolean var1) {
      GlobalStatus.graphics.setColor(0);
      GlobalStatus.graphics.drawRect(0, 0, GlobalStatus.screenWidth - 1, GlobalStatus.screenHeight - 1);
      GlobalStatus.graphics.drawLine(0, GlobalStatus.screenHeight - 2, GlobalStatus.screenWidth, GlobalStatus.screenHeight - 2);
      drawTitleBarWindow(var0, 1, 1, GlobalStatus.screenWidth - 3, GlobalStatus.screenHeight - 8 - footerHeight, false);
   }

   public static String formatCredits(int var0) {
      String var4 = String.valueOf(var0);
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

   public static void drawFilledTitleBarWindow(String var0, int var1, int var2, int var3, int var4) {
      drawTitleBarWindow(var0, var1, var2, var3, var4, true);
   }

   public static void drawTitleBarWindow(String var0, int var1, int var2, int var3, int var4, boolean var5) {
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

   public static void drawMenuPanelCorner(int var0, int var1, boolean var2) {
      GlobalStatus.graphics.drawImage(var2 ? menuPanelCorActive : menuPanelCorInactive, var0, var1, 20);
   }

   public static void drawFooter(String var0, String var1) {
      String var10000 = var0;
      boolean var2 = true;
      drawFooter(var10000, var1, false);
   }

   public static void selectNavigationButton(boolean var0) {
      leftButtonSelected = var0;
      naviDelay = 5;
   }

   public static boolean navigationDelayPassed() {
      return naviDelay <= 0;
   }

   public static void navigationDelayDownTick() {
      --naviDelay;
   }

   public static void setTickHighlight(boolean var0) {
      tickHighlight = var0;
   }

   public static void drawStringWidthLimited(String var0, int var1, int var2, int var3, int var4) {
      String var10000 = var0;
      boolean var5 = true;
      String var6;
      Font.drawString((var6 = Font.truncateStringLine(var0 = var10000.trim(), var3 - 2, 0, true)).equals(var0) ? var0 : var6 + "...", var1, var2, var4, 17);
   }

   public static void drawFooter1(String var0, String var1, boolean var2) {
      drawFooter(var0, var1, false);
   }

   private static void drawRect(int var0, int var1, int var2, int var3) {
      GlobalStatus.graphics.setColor(uiOuterTopRightOutlineColor);
      GlobalStatus.graphics.drawLine(var0, var1, var0 + var2, var1);
      GlobalStatus.graphics.drawLine(var0 + var2, var1, var0 + var2, var1 + var3);
      GlobalStatus.graphics.setColor(uiOuterDownLeftOutlineInnerLabalBgColor);
      GlobalStatus.graphics.drawLine(var0, var1, var0, var1 + var3);
      GlobalStatus.graphics.drawLine(var0, var1 + var3, var0 + var2, var1 + var3);
   }

   public static void drawFooter(String var0, String var1, boolean var2) {
      boolean var3 = leftButtonSelected && naviDelay > 0 && !var0.equals("") || tickHighlight;
      boolean var4 = !leftButtonSelected && naviDelay > 0 && !var1.equals("");
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
