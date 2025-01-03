package Main;

import AbyssEngine.AEImage;
import AbyssEngine.AbstractScene;
import AbyssEngine.GameStatus;
import AbyssEngine.Layout;
import AbyssEngine.LoadingScreen;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;

public final class Intro extends AbstractScene {
   private Image logoFL;
   private Image logoAbyss;
   private boolean loaded;
   private long timeResourceLoaded;
   private int introState;
   private int soundSwitchCurrent;

   public final void loadResources() {
      try {
         String var1 = "/data/interface/fishlabs.png";
         this.logoFL = AEImage.loadImage("/data/interface/fishlabs.png", true);
         var1 = "/data/interface/abyss.png";
         this.logoAbyss = AEImage.loadImage("/data/interface/abyss.png", true);
         var1 = "/data/interface/ocean.png";
         AEImage.loadImage("/data/interface/ocean.png", true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.timeResourceLoaded = System.currentTimeMillis();
      this.introState = 0;
      this.loaded = true;
      this.soundSwitchCurrent = 0;
      System.gc();
   }

   public final void freeResources() {
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
            GameStatus.musicOn = this.soundSwitchCurrent == 0;
            GameStatus.sfxOn = this.soundSwitchCurrent == 0;
            if (this.soundSwitchCurrent == 1) {
               GameStatus.musicVolume = 3;
               GameStatus.sfxVolume = 3;
            } else {
               GameStatus.musicVolume = 1;
               GameStatus.sfxVolume = 1;
            }

            GameStatus.soundManager.setMusicVolume(100);
            GameStatus.soundManager.setSfxVolume(100);
            GameStatus.soundManager.playMusic(0);
            this.timeResourceLoaded = System.currentTimeMillis();
            this.introState = 1;
         }
      }

   }

   public final void renderScene(int var1) {
      Layout.screenFillMenuBackground();
      if (this.introState == 0) {
         GameStatus.graphics.drawImage(LoadingScreen.sub_4a(), GameStatus.screenWidth >> 1, 10, 17);
         Layout.scale(GameStatus.langManager.getLangString(6) + " " + GameStatus.langManager.getLangString(15), 0, GameStatus.screenHeight >> 1, GameStatus.screenWidth, this.soundSwitchCurrent == 0);
         Layout.scale(GameStatus.langManager.getLangString(6) + " " + GameStatus.langManager.getLangString(16), 0, (GameStatus.screenHeight >> 1) + SymbolMapManager_.sub_2c2(), GameStatus.screenWidth, this.soundSwitchCurrent == 1);
         Layout.sub_535(GameStatus.langManager.getLangString(253), "", false);
      } else {
         if (this.introState == 1) {
            GameStatus.graphics.drawImage(this.logoFL, GameStatus.screenWidth / 2, GameStatus.screenHeight / 2, 3);
            if (System.currentTimeMillis() - this.timeResourceLoaded > 3000L) {
               ++this.introState;
               return;
            }
         } else if (this.introState == 2) {
            GameStatus.graphics.drawImage(this.logoAbyss, GameStatus.screenWidth / 2, GameStatus.screenHeight / 2, 3);
            if (System.currentTimeMillis() - this.timeResourceLoaded > 6000L) {
               ++this.introState;
               return;
            }
         } else {
            GameStatus.var_bfb.setScene(GameStatus.scenes[0]);
         }

      }
   }
}
