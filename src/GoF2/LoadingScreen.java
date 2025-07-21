package GoF2;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;

public final class LoadingScreen implements Runnable {
   private boolean stillLoading = false;
   private GameCanvas canvas;
   private Thread loadingThread;
   private static Image gameLogo;
   private String[] tipsRows;
   private String stateIndicator;
   private static String textOverStateIndicator = "";
   private long endTime;
   private long startTime;

   public LoadingScreen() {
      String var1 = "/data/interface/logo.png";
      gameLogo = AEFile.loadImage("/data/interface/logo.png", true);
   }

   public static Image getGameLogo() {
      return gameLogo;
   }

   public final void setGameCanvas(GameCanvas var1) {
      this.canvas = var1;
   }

   public final void run() {
      while(this.stillLoading && !GlobalStatus.paused) {
         Layout.drawBG();
         Font.drawString(this.stateIndicator, GlobalStatus.screenWidth / 2 - Font.getTextWidth(this.stateIndicator, 0) / 2, GlobalStatus.screenHeight / 2 - Font.getFontSpacingY() / 2 - 10, 0);
         Font.drawString(textOverStateIndicator, GlobalStatus.screenWidth / 2 - Font.getTextWidth(this.stateIndicator, 0) / 2, GlobalStatus.screenHeight / 2 - (Font.getFontSpacingY() << 1) - 10, 0);
         GlobalStatus.graphics.drawImage(gameLogo, GlobalStatus.screenWidth >> 1, 10, 17);
         if (GlobalStatus.applicationManager != null && GlobalStatus.applicationManager.GetCurrentApplicationModule() != null && GlobalStatus.applicationManager.GetCurrentApplicationModule() != GlobalStatus.scenes[3]) {
            this.draw();
         }

         this.endTime = System.currentTimeMillis();
         Status.loadingTime += this.endTime - this.startTime;
         this.startTime = this.endTime;
         if (this.canvas != null) {
            this.canvas.flushGraphics();
         }

         try {
            Thread.sleep(10L);
         } catch (Exception var3) {
            this.stillLoading = false;
         }
      }

   }

   public final void draw() {
      Layout.drawMask(10, GlobalStatus.screenHeight - 10 * this.tipsRows.length - 32, GlobalStatus.screenWidth - 20, 10 * this.tipsRows.length + 20);
      this.drawTips();
   }
   public final void drawTips(){
       Layout.drawTitleBarWindow(GlobalStatus.gameText.getText(277), 10, GlobalStatus.screenHeight - 10 * this.tipsRows.length - 34, GlobalStatus.screenWidth - 20, 10 * this.tipsRows.length + 24, false);
       Font.drawLines(this.tipsRows, 17, GlobalStatus.screenHeight - 10 * this.tipsRows.length - 18, 1);
   }
   public final void close() {
      this.stillLoading = false;
   }

   public final void startLoading_(boolean var1) {
      this.startTime = System.currentTimeMillis();
      ++Status.loadingsCount;
      this.stateIndicator = GlobalStatus.gameText.getText(var1 ? 214 : 215);
      int var2 = GlobalStatus.random.nextInt(GameText.tips.length);
      this.tipsRows = Font.splitToLines(GlobalStatus.gameText.getText(GameText.tips[var2]), GlobalStatus.screenWidth - 34);
      this.stillLoading = true;
      this.loadingThread = new Thread(this);
      this.loadingThread.start();
   }
}
