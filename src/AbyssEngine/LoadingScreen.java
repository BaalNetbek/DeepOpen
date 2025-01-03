package AbyssEngine;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public final class LoadingScreen implements Runnable {
   private boolean stillLoading = false;
   private GameCanvas var_7c;
   private Thread var_d7;
   private static Image var_129;
   private String[] tipsRows;
   private String stateIndicator;
   private static String textOverStateIndicator = "";
   private long loadingEndTime;
   private long var_267;

   public LoadingScreen() {
      String var1 = "/data/interface/logo.png";
      var_129 = AEImage.loadImage("/data/interface/logo.png", true);
   }

   public static Image sub_4a() {
      return var_129;
   }

   public final void setGameCanvas(GameCanvas var1) {
      this.var_7c = var1;
   }

   public final void run() {
      while(this.stillLoading && !GameStatus.var_bd5) {
         Layout.screenFillMenuBackground();
         SymbolMapManager_.sub_102(this.stateIndicator, GameStatus.screenWidth / 2 - SymbolMapManager_.sub_25b(this.stateIndicator, 0) / 2, GameStatus.screenHeight / 2 - SymbolMapManager_.sub_2c2() / 2 - 10, 0);
         SymbolMapManager_.sub_102(textOverStateIndicator, GameStatus.screenWidth / 2 - SymbolMapManager_.sub_25b(this.stateIndicator, 0) / 2, GameStatus.screenHeight / 2 - (SymbolMapManager_.sub_2c2() << 1) - 10, 0);
         GameStatus.graphics.drawImage(var_129, GameStatus.screenWidth >> 1, 10, 17);
         if (GameStatus.var_bfb != null && GameStatus.var_bfb.sub_28() != null && GameStatus.var_bfb.sub_28() != GameStatus.scenes[3]) {
            this.sub_d5();
         }

         this.loadingEndTime = System.currentTimeMillis();
         Status.timeSpentLoading += this.loadingEndTime - this.var_267;
         this.var_267 = this.loadingEndTime;
         if (this.var_7c != null) {
            this.var_7c.flushGraphics();
         }

         try {
            Thread.sleep(10L);
         } catch (Exception var3) {
            this.stillLoading = false;
         }
      }

   }

   public final void sub_d5() {
      Layout.sub_136(10, GameStatus.screenHeight - 10 * this.tipsRows.length - 32, GameStatus.screenWidth - 20, 10 * this.tipsRows.length + 20);
      Layout.drawTitleBarWindow(GameStatus.langManager.getLangString(277), 10, GameStatus.screenHeight - 10 * this.tipsRows.length - 34, GameStatus.screenWidth - 20, 10 * this.tipsRows.length + 24, false);
      SymbolMapManager_.sub_185(this.tipsRows, 17, GameStatus.screenHeight - 10 * this.tipsRows.length - 18, 1);
   }

   public final void close() {
      this.stillLoading = false;
   }

   public final void startLoading_(boolean var1) {
      this.var_267 = System.currentTimeMillis();
      ++Status.loadingsCount;
      this.stateIndicator = GameStatus.langManager.getLangString(var1 ? 214 : 215);
      int var2 = GameStatus.random.nextInt(LangManager.tips.length);
      this.tipsRows = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(LangManager.tips[var2]), GameStatus.screenWidth - 34);
      this.stillLoading = true;
      this.var_d7 = new Thread(this);
      this.var_d7.start();
   }
}
