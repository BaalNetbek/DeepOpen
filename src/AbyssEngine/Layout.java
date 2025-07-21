package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class Layout {
   public static int uiInnerOutlineColor = -14328435;
   public static int uiOuterTopRightOutlineColor = -14398096;
   public static int uiOuterDownLeftOutlineInnerLabalBgColor = -15914687;
   public static int uiInactiveInnerLabelColor = -14935012;
   private static int var_298;
   private static Image skip;
   private static Image lock;
   private static Image menuBackground;
   private static Image menuHeaderPattern;
   private static Image mainMenuCorener;
   private static Image menuPanelCorInactive;
   private static Image menuPanelCorActive;
   private static int[] var_528;
   private static long var_575;
   private static long var_5b6;
   private static int var_612;
   private static boolean var_61d;
   private static boolean var_6d1;

   private Layout() {
   }

   public static void sub_3a() {
      var_528 = null;
   }

   public static void init() {
      var_298 = 10;
      if (skip == null) {
         String var0 = "/data/interface/skip.png";
         skip = AEImage.loadImage("/data/interface/skip.png", true);
         var0 = "/data/interface/menu_background.png";
         menuBackground = AEImage.loadImage("/data/interface/menu_background.png", true);
         var0 = "/data/interface/menu_header_pattern.png";
         menuHeaderPattern = AEImage.loadImage("/data/interface/menu_header_pattern.png", true);
         var0 = "/data/interface/menu_main_corner.png";
         mainMenuCorener = AEImage.loadImage("/data/interface/menu_main_corner.png", true);
         var0 = "/data/interface/menu_panel_corner_inactive.png";
         menuPanelCorInactive = AEImage.loadImage("/data/interface/menu_panel_corner_inactive.png", true);
         var0 = "/data/interface/menu_panel_corner_active.png";
         menuPanelCorActive = AEImage.loadImage("/data/interface/menu_panel_corner_active.png", true);
      }

      if (var_528 == null) {
         var_528 = new int[1024];

         for(int var1 = 0; var1 < var_528.length; ++var1) {
            var_528[var1] = 0x88000000;
         }
      }

   }

   public static void addTicks(int var0) {
      var_5b6 += (long)var0;
      var_575 += (long)var0;
   }

   public static boolean sub_108() {
      if (var_5b6 > 1000L) {
         if (var_5b6 > 2000L) {
            var_5b6 = 0L;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean sub_119() {
      if (var_575 > 300L) {
         if (var_575 > 600L) {
            var_575 = 0L;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void sub_136(int var0, int var1, int var2, int var3) {
      GameStatus.graphics.setClip(10, var1, var2, var3);
      sub_189();
      GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
   }

   public static void sub_189() {
      for(int var0 = 0; var0 < GameStatus.screenWidth / 32 + 1; ++var0) {
         for(int var1 = 0; var1 < GameStatus.screenHeight / 32 + 1; ++var1) {
            GameStatus.graphics.drawRGB(var_528, 0, 32, var0 << 5, var1 << 5, 32, 32, true);
         }
      }

   }

   public static void scale(String var0, int var1, int var2, int var3, boolean var4) {
      sub_241(var0, var1, var2, var3, var4, false);
   }

   public static void sub_1df(String var0, int var1, int var2, int var3, boolean var4, boolean var5, boolean var6) {
      SymbolMapManager_.sub_161(var0, var5 ? var1 + (var3 >> 1) : var1 + 12, var2 + 1, var4 ? 2 : 1, var5 ? 24 : 17);
      if (var6) {
         if (lock == null) {
            String var7 = "/data/interface/lock.png";
            lock = AEImage.loadImage("/data/interface/lock.png", true);
         }

         if (Status.getCurrentCampaignMission() < 13 && (var0.equals(GameStatus.langManager.getLangString(218)) || var0.equals(GameStatus.langManager.getLangString(33))) || Status.getCurrentCampaignMission() < 9 && var0.equals(GameStatus.langManager.getLangString(72)) || Status.getCurrentCampaignMission() < 5 && var0.equals(GameStatus.langManager.getLangString(62))) {
            GameStatus.graphics.drawImage(lock, var1 + 3, var2 + 2, 0);
         }
      }

   }

   public static void sub_241(String var0, int var1, int var2, int var3, boolean var4, boolean var5) {
      sub_1df(var0, var1, var2, var3, var4, var5, false);
   }

   public static void screenFillMenuBackground() {
      tileFillRect(0, 0, GameStatus.screenWidth, GameStatus.screenHeight, menuBackground);
   }

   private static void tileFillRect(int var0, int var1, int var2, int var3, Image var4) {
      int var5 = var4.getWidth();
      int var6 = var4.getHeight();
      GameStatus.graphics.setClip(var0, var1, var2, var3);

      for(int var7 = 0; var7 < var2 / var5 + 1; ++var7) {
         for(int var8 = 0; var8 < var3 / var6 + 1; ++var8) {
            GameStatus.graphics.drawImage(var4, var0 + var7 * var5, var1 + var8 * var6, 20);
         }
      }

      GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
   }

   public static void sub_2c1(String var0) {
      sub_2db(var0, false);
   }

   public static void sub_2db(String var0, boolean var1) {
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawRect(0, 0, GameStatus.screenWidth - 1, GameStatus.screenHeight - 1);
      GameStatus.graphics.drawLine(0, GameStatus.screenHeight - 2, GameStatus.screenWidth, GameStatus.screenHeight - 2);
      drawTitleBarWindow(var0, 1, 1, GameStatus.screenWidth - 3, GameStatus.screenHeight - 8 - var_298, false);
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
         tileFillRect(var1, var2, var3, var4, menuBackground);
      }

      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawRect(var1 + 1, var2 + 1, var3 - 2, var4 - 2);
      GameStatus.graphics.setColor(uiInnerOutlineColor);
      GameStatus.graphics.drawRect(var1 + 2, var2 + 2, var3 - 4, var4 - 4);
      drawRect(var1, var2, var3, var4);
      if (var0 != null) {
         tileFillRect(var1 + 2, var2 + 2, var3 - 3, 11, menuHeaderPattern);
         GameStatus.graphics.setColor(uiInnerOutlineColor);
         GameStatus.graphics.drawLine(var1 + 2, var2 + 14, var1 + var3 - 3, var2 + 14);
         GameStatus.graphics.setColor(0);
         GameStatus.graphics.drawLine(var1 + 1, var2 + 13, var1 + var3 - 2, var2 + 13);
         GameStatus.graphics.drawImage(mainMenuCorener, var1, var2, 20);
      }

      if (var0 != null && !var0.equals("")) {
         SymbolMapManager_.sub_102(var0, var1 + 6, var2 + 1, 0);
      }

   }

   public static void sub_39a(int var0, int var1, boolean var2) {
      GameStatus.graphics.drawImage(var2 ? menuPanelCorActive : menuPanelCorInactive, var0, var1, 20);
   }

   public static void sub_3c6(String var0, String var1) {
      String var10000 = var0;
      boolean var2 = true;
      sub_5d9(var10000, var1, false);
   }

   public static void sub_411(boolean var0) {
      var_61d = var0;
      var_612 = 5;
   }

   public static boolean sub_44a() {
      return var_612 <= 0;
   }

   public static void sub_490() {
      --var_612;
   }

   public static void sub_4cc(boolean var0) {
      var_6d1 = var0;
   }

   public static void sub_4e1(String var0, int var1, int var2, int var3, int var4) {
      String var10000 = var0;
      boolean var5 = true;
      String var6;
      SymbolMapManager_.sub_161((var6 = SymbolMapManager_.sub_3f2(var0 = var10000.trim(), var3 - 2, 0, true)).equals(var0) ? var0 : var6 + "...", var1, var2, var4, 17);
   }

   public static void sub_535(String var0, String var1, boolean var2) {
      sub_5d9(var0, var1, false);
   }

   private static void drawRect(int var0, int var1, int var2, int var3) {
      GameStatus.graphics.setColor(uiOuterTopRightOutlineColor);
      GameStatus.graphics.drawLine(var0, var1, var0 + var2, var1);
      GameStatus.graphics.drawLine(var0 + var2, var1, var0 + var2, var1 + var3);
      GameStatus.graphics.setColor(uiOuterDownLeftOutlineInnerLabalBgColor);
      GameStatus.graphics.drawLine(var0, var1, var0, var1 + var3);
      GameStatus.graphics.drawLine(var0, var1 + var3, var0 + var2, var1 + var3);
   }

   public static void sub_5d9(String var0, String var1, boolean var2) {
      boolean var3 = var_61d && var_612 > 0 && !var0.equals("") || var_6d1;
      boolean var4 = !var_61d && var_612 > 0 && !var1.equals("");
      tileFillRect(3, GameStatus.screenHeight - 15, GameStatus.screenWidth - 5, 11, menuHeaderPattern);
      drawRect(1, GameStatus.screenHeight - 17, GameStatus.screenWidth - 3, 14);
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawRect(2, GameStatus.screenHeight - 16, GameStatus.screenWidth - 5, 12);
      SymbolMapManager_.sub_161(var0, 10, GameStatus.screenHeight - 4, var3 ? 2 : 1, 33);
      SymbolMapManager_.sub_161(var1, GameStatus.screenWidth - 10, GameStatus.screenHeight - 4, var4 ? 2 : 1, 34);
      if (var2) {
         GameStatus.graphics.drawImage(skip, GameStatus.screenWidth >> 1, GameStatus.screenHeight - 5, 33);
      }

   }
}
