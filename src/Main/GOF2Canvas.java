package Main;

import AbyssEngine.AEGraphics3D;
import AbyssEngine.AEResourceManager;
import AbyssEngine.AbstractScene;
import AbyssEngine.Class_3sceneHelper;
import AbyssEngine.Class_db;
import AbyssEngine.GameStatus;
import AbyssEngine.IndexManager;
import AbyssEngine.LangManager;
import AbyssEngine.Layout;
import AbyssEngine.LoadingScreen;
import AbyssEngine.RecordHandler;
import AbyssEngine.SoundManager;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import java.util.Random;
import javax.microedition.lcdui.game.GameCanvas;

public final class GOF2Canvas extends GameCanvas {
   private GOF2MIDlet midlet;
   private int keyStates;
   private int keysState;
   private int lastKeyStates;
   private int lastFrameSignleKeyRelease;
   private int lastFrameSignleKeyPress;
   private int var_2bf;
   private Class_3sceneHelper var_2f1 = null;
   private LoadingScreen loadingScreen;
   private boolean var_355;
   private boolean resized_;
   private boolean initialized;
   private long var_4ba;
   private long var_4e5;

   GOF2Canvas(GOF2MIDlet var1) {
      super(false);
      GameStatus.sub_d1();
      this.resized_ = true;
      this.initialized = false;
      this.midlet = var1;
      this.var_355 = false;
      this.var_4ba = 0L;
      this.var_4e5 = 0L;
      GameStatus.display = var1.getDisplay();
      GameStatus.random = new Random();

      try {
         GameStatus.gameVersion = "v " + var1.getAppProperty("MIDlet-Version");
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   public final void sub_8b() {
      if (this.var_2f1.sub_28() == GameStatus.scenes[2]) {
         ((MGame)((MGame)GameStatus.scenes[2])).sub_1da();
      }

      GameStatus.soundManager.sub_260();
      GameStatus.var_bd5 = true;
   }

   protected final void hideNotify() {
      this.midlet.pause();
   }

   final synchronized void sub_ca() {
      if (this.resized_) {
         this.resized_ = false;
         GameStatus.screenWidth = this.getWidth();
         GameStatus.screenHeight = this.getHeight();
         this.setFullScreenMode(true);
         this.flushGraphics();
         GameStatus.screenWidth = this.getWidth();
         GameStatus.screenHeight = this.getHeight();
         this.setFullScreenMode(true);
      } else if (!this.initialized) {
         this.initialized = true;
         GameStatus.graphics = this.getGraphics();
         GameStatus.screenWidth = this.getWidth();
         GameStatus.screenHeight = this.getHeight();
         (GameStatus.langManager = new LangManager()).setup(GameStatus.language);
         GameStatus.screenWidth = this.getWidth();
         GameStatus.screenHeight = this.getHeight();
         this.setFullScreenMode(true);
         SymbolMapManager_.sub_77(GameStatus.graphics);
         SymbolMapManager_.addCharMap("/data/interface/font_w.png", 0, 15, 16);
         SymbolMapManager_.setPrimarySymMapSpacing(0);
         SymbolMapManager_.setPrimarySymMapHeightMaxRange(11);
         SymbolMapManager_.setSymMapSetOffsetY(-2, 0);
         SymbolMapManager_.addCharMap("/data/interface/font_g.png", 1, 15, 16);
         SymbolMapManager_.setSymMapSpacing(0, 1);
         SymbolMapManager_.setSymHeightMaxRange(11, 1);
         SymbolMapManager_.setSymMapSetOffsetY(-2, 1);
         SymbolMapManager_.addCharMap("/data/interface/font_r.png", 2, 15, 16);
         SymbolMapManager_.setSymMapSpacing(0, 2);
         SymbolMapManager_.setSymHeightMaxRange(11, 2);
         SymbolMapManager_.setSymMapSetOffsetY(-2, 2);
         SymbolMapManager_.addCharMap("/data/interface/font_void.png", 2, 15, 16);
         SymbolMapManager_.setSymMapSpacing(0, 3);
         SymbolMapManager_.setSymHeightMaxRange(11, 3);
         SymbolMapManager_.setSymMapSetOffsetY(-2, 3);
         Layout.init();
         this.loadingScreen = new LoadingScreen();
         this.loadingScreen.setGameCanvas(this);
         this.loadingScreen.startLoading_(true);
         GameStatus.loadingScreen = this.loadingScreen;
         GameStatus.resetHelp();
         (new RecordHandler()).readOptions();
         GameStatus.musicOn = false;
         GameStatus.graphics3D = new AEGraphics3D();
         (GameStatus.renderer = new Class_db(GameStatus.graphics3D)).sub_120();
         GameStatus.renderer.sub_120();
         GameStatus.renderer.sub_120();
         GameStatus.soundManager = new SoundManager();
         IndexManager.buildResourceList();
         IndexManager.sub_27();
         Status.setCredits(0);
         this.var_2f1 = new Class_3sceneHelper(this.midlet, this.loadingScreen);
         GameStatus.var_bfb = this.var_2f1;
         (GameStatus.scenes = new AbstractScene[4])[3] = new Intro();
         GameStatus.scenes[0] = new Class_f7scene();
         GameStatus.scenes[1] = new InsideStation();
         GameStatus.scenes[2] = new MGame();
         Status.startNewGame();
         this.var_2f1.setScene(GameStatus.scenes[3]);
      } else {
         Layout.sub_490();
         this.var_2f1.renderScene_andLoad_(this.keyStates);
         if (this.lastFrameSignleKeyPress == 16384) {
            Layout.sub_411(true);
            this.var_2bf = this.lastFrameSignleKeyPress;
         } else if (this.lastFrameSignleKeyPress == 8192) {
            Layout.sub_411(false);
            this.var_2bf = this.lastFrameSignleKeyPress;
         }

         if (Layout.sub_44a()) {
            this.var_2f1.handleKeystate(this.var_2bf);
            this.var_2bf = -1;
         }

         if (this.lastFrameSignleKeyPress != -1 && this.var_2bf == -1) {
            this.var_2f1.handleKeystate(this.lastFrameSignleKeyPress);
         }

         this.lastFrameSignleKeyPress = -1;
         this.lastFrameSignleKeyRelease = -1;
         this.flushGraphics();
      }
   }

   final void sub_10d() {
      this.var_2f1.sub_28().freeResources();
      IndexManager.freeResources();
      Layout.sub_3a();
      SymbolMapManager_.sub_58();
      GameStatus.soundManager.stopMusic();
      AEResourceManager.destroy();
      this.loadingScreen.close();
      this.loadingScreen = null;
   }

   public final void sub_125() {
      Layout.screenFillMenuBackground();
      SymbolMapManager_.sub_22a(SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(81), GameStatus.screenWidth - 20), GameStatus.screenWidth >> 1, GameStatus.screenHeight >> 1, 1, 24);
      if (this.var_4e5 == 0L) {
         this.var_4e5 = System.currentTimeMillis();
      }

      long var1 = System.currentTimeMillis();
      this.var_4ba += var1 - this.var_4e5;
      this.var_4e5 = var1;
      if (this.lastFrameSignleKeyPress == 256) {
         if (Status.getPlayingTime() > this.var_4ba) {
            Status.incPlayingTime(-this.var_4ba);
         }

         this.var_4ba = 0L;
         this.var_4e5 = 0L;
         this.midlet.resume();
         this.lastFrameSignleKeyPress = 0;
         this.lastFrameSignleKeyRelease = 0;
         this.keysState = 0;
         this.lastKeyStates = 0;
         this.keyStates = 0;
      }

      this.flushGraphics();
   }

   final synchronized void synchronizeKeyState() {
      this.keyStates = this.keysState;
   }

   public final void keyPressed(int var1) {
      this.lastKeyStates = this.keysState;
      if (var1 == -7) {
         this.keysState |= 8192;
      }

      if (var1 == -6) {
         this.keysState |= 16384;
      }

      if (var1 == 49 || var1 == 101 || var1 == 114) {
         this.keysState |= 32768;
      }

      if (var1 == 51 || var1 == 105 || var1 == 117) {
         this.keysState |= 65536;
      }

      if (var1 == 48 || var1 == 32) {
         this.keysState |= 131072;
      }

      if (var1 == -11) {
         this.keysState |= 262144;
      }

      if (var1 == -8) {
         this.keysState |= 524288;
      }

      if (var1 == -1 || var1 == 50 || var1 == 121 || var1 == 116) {
         this.keysState |= 2;
      }

      if (var1 == -2 || var1 == 56 || var1 == 98 || var1 == 118) {
         this.keysState |= 64;
      }

      if (var1 == -3 || var1 == 52 || var1 == 100 || var1 == 102) {
         this.keysState |= 4;
      }

      if (var1 == -4 || var1 == 54 || var1 == 106 || var1 == 107) {
         this.keysState |= 32;
      }

      if (var1 == -5 || var1 == 53 || var1 == 103 || var1 == 104) {
         this.keysState |= 256;
      }

      if (var1 == 55 || var1 == 99 || var1 == 120) {
         this.keysState |= 512;
      }

      if (var1 == 57 || var1 == 109 || var1 == 110) {
         this.keysState |= 1024;
      }

      if (var1 == 42) {
         this.keysState |= 2048;
      }

      if (var1 == 35) {
         this.keysState |= 4096;
      }

      this.lastFrameSignleKeyPress = this.keysState & ~this.lastKeyStates;
   }

   public final void keyRepeated(int var1) {
      this.keyPressed(var1);
   }

   public final void keyReleased(int var1) {
      this.lastKeyStates = this.keysState;
      if (var1 == -7) {
         this.keysState &= -8193;
      }

      if (var1 == -6) {
         this.keysState &= -16385;
      }

      if (var1 == 49 || var1 == 101 || var1 == 114) {
         this.keysState &= -32769;
      }

      if (var1 == 51 || var1 == 105 || var1 == 117) {
         this.keysState &= -65537;
      }

      if (var1 == 48 || var1 == 32) {
         this.keysState &= -131073;
      }

      if (var1 == -11) {
         this.keysState &= -262145;
      }

      if (var1 == -8) {
         this.keysState &= -524289;
      }

      if (var1 == -1 || var1 == 50 || var1 == 121 || var1 == 116) {
         this.keysState &= -3;
      }

      if (var1 == -2 || var1 == 56 || var1 == 98 || var1 == 118) {
         this.keysState &= -65;
      }

      if (var1 == -3 || var1 == 52 || var1 == 100 || var1 == 102) {
         this.keysState &= -5;
      }

      if (var1 == -4 || var1 == 54 || var1 == 106 || var1 == 107) {
         this.keysState &= -33;
      }

      if (var1 == -5 || var1 == 53 || var1 == 103 || var1 == 104) {
         this.keysState &= -257;
      }

      if (var1 == 55 || var1 == 99 || var1 == 120) {
         this.keysState &= -513;
      }

      if (var1 == 57 || var1 == 109 || var1 == 110) {
         this.keysState &= -1025;
      }

      if (var1 == 42) {
         this.keysState &= -2049;
      }

      if (var1 == 35) {
         this.keysState &= -4097;
      }

      this.lastFrameSignleKeyRelease = ~this.keysState & this.lastKeyStates;
   }
}
