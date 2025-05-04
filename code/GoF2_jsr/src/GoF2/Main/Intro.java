package GoF2.Main;

import AE.AEFile;
import AE.GlobalStatus;
import AE.IApplicationModule;
import AE.PaintCanvas.Font;
import GoF2.Layout;
import GoF2.LoadingScreen;
import javax.microedition.lcdui.Image;

public final class Intro extends IApplicationModule {
   private Image logoFL;
   private Image logoAbyss;
   private boolean loaded;
   private long timeResourceLoaded;
   private int introState;
   private int soundSwitchCurrent;

   public final void OnInitialize() {
      try {
         String var1 = "/data/interface/fishlabs.png";
         this.logoFL = AEFile.loadImage("/data/interface/fishlabs.png", true);
         var1 = "/data/interface/abyss.png";
         this.logoAbyss = AEFile.loadImage("/data/interface/abyss.png", true);
         var1 = "/data/interface/ocean.png";
         AEFile.loadImage("/data/interface/ocean.png", true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.timeResourceLoaded = System.currentTimeMillis();
      this.introState = 0;
      this.loaded = true;
      this.soundSwitchCurrent = 0;
      System.gc();
   }

   public final void OnRelease() {
      this.logoFL = null;
      this.logoAbyss = null;
      this.loaded = false;
   }

   public final boolean isLoaded() {
      return this.loaded;
   }

   public final void handleKeystate(int var1) {
      if (this.introState == 0) {
         if (var1 == 2 && this.soundSwitchCurrent == 1) {
            --this.soundSwitchCurrent;
         }

         if (var1 == 64 && this.soundSwitchCurrent == 0) {
            ++this.soundSwitchCurrent;
         }

         if (var1 == 256 || var1 == 16384) {
            GlobalStatus.musicOn = this.soundSwitchCurrent == 0;
            GlobalStatus.sfxOn = this.soundSwitchCurrent == 0;
            if (this.soundSwitchCurrent == 1) {
               GlobalStatus.musicVolume = 3;
               GlobalStatus.sfxVolume = 3;
            } else {
               GlobalStatus.musicVolume = 1;
               GlobalStatus.sfxVolume = 1;
            }

            GlobalStatus.soundManager.setMusicVolume(100);
            GlobalStatus.soundManager.setSfxVolume(100);
            GlobalStatus.soundManager.playMusic(0);
            this.timeResourceLoaded = System.currentTimeMillis();
            this.introState = 1;
         }
      }

   }

   public final void renderScene(int var1) {
      Layout.drawBG();
      if (this.introState == 0) {
         GlobalStatus.graphics.drawImage(LoadingScreen.getGameLogo(), GlobalStatus.screenWidth >> 1, 10, 17);
         Layout.drawTextItem(GlobalStatus.gameText.getText(6) + " " + GlobalStatus.gameText.getText(15), 0, GlobalStatus.screenHeight >> 1, GlobalStatus.screenWidth, this.soundSwitchCurrent == 0);
         Layout.drawTextItem(GlobalStatus.gameText.getText(6) + " " + GlobalStatus.gameText.getText(16), 0, (GlobalStatus.screenHeight >> 1) + Font.getFontSpacingY(), GlobalStatus.screenWidth, this.soundSwitchCurrent == 1);
         Layout.drawFooter1(GlobalStatus.gameText.getText(253), "", false);
      } else {
         if (this.introState == 1) {
            GlobalStatus.graphics.drawImage(this.logoFL, GlobalStatus.screenWidth / 2, GlobalStatus.screenHeight / 2, 3);
            if (System.currentTimeMillis() - this.timeResourceLoaded > 3000L) {
               ++this.introState;
               return;
            }
         } else if (this.introState == 2) {
            GlobalStatus.graphics.drawImage(this.logoAbyss, GlobalStatus.screenWidth / 2, GlobalStatus.screenHeight / 2, 3);
            if (System.currentTimeMillis() - this.timeResourceLoaded > 6000L) {
               ++this.introState;
               return;
            }
         } else {
            GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[0]);
         }

      }
   }
}
