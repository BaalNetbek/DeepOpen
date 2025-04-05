package AbyssEngine;

import java.util.Random;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public final class GameStatus {
   public static boolean musicOn = true;
   public static boolean sfxOn = true;
   public static boolean vibrationOn = true;
   public static boolean invertedControlsOn = true;
   public static int musicVolume = 2;
   public static int sfxVolume = 2;
   public static int language = 6;
   public static boolean shopHelpShown;
   public static boolean var_264;
   public static boolean var_2b3;
   public static boolean var_2cc;
   public static boolean var_2e8;
   public static boolean barHelpShown;
   public static boolean galaxyMapHelpShown;
   public static boolean systemMapHelpShown;
   public static boolean var_40c;
   public static boolean miningHelpShown;
   public static boolean asteroidHelpShown;
   public static boolean missionsHelpShown;
   public static boolean cargoFullHelpShown;
   public static boolean wingmenHelpShown;
   public static boolean var_554;
   public static boolean boosterHelpShown;
   public static boolean statusHelpShown;
   public static boolean medalsHelpShown;
   public static boolean fanWingmenNoticeShown;
   public static boolean voidxNoticeShown;
   public static boolean reputationHelpShown;
   public static boolean buyingHelpShown;
   public static boolean itemMountingHelpShown;
   public static boolean itemMounting2HelpShown;
   public static boolean interplanetHelpShown;
   public static boolean jumpDriveHelpShown;
   public static boolean cloakHelpShown;
   public static Class_db renderer;
   public static AbstractGraphics3D graphics3D;
   public static Display display;
   public static LoadingScreen loadingScreen;
   public static int var_9c3 = -1;
   public static boolean var_9da = false;
   public static int var_9fc = -1;
   public static LangManager langManager;
   public static Graphics graphics;
   public static MIDlet midlet;
   public static int screenWidth;
   public static int screenHeight;
   public static SoundManager soundManager;
   public static Random random;
   public static String gameVersion = "";
   public static boolean var_bd5 = false;
   public static Class_3sceneHelper var_bfb;
   public static AbstractScene[] scenes;

   public static void vibrate(int var0) {
      if (vibrationOn) {
         display.vibrate(var0);
      }

   }

   public static void resetHelp() {
      shopHelpShown = false;
      var_264 = false;
      var_2b3 = false;
      var_2cc = false;
      var_2e8 = false;
      barHelpShown = false;
      galaxyMapHelpShown = false;
      systemMapHelpShown = false;
      var_40c = false;
      miningHelpShown = false;
      asteroidHelpShown = false;
      missionsHelpShown = false;
      cargoFullHelpShown = false;
      wingmenHelpShown = false;
      var_554 = false;
      boosterHelpShown = false;
      statusHelpShown = false;
      medalsHelpShown = false;
      fanWingmenNoticeShown = false;
      voidxNoticeShown = false;
      reputationHelpShown = false;
      buyingHelpShown = false;
      itemMountingHelpShown = false;
      itemMounting2HelpShown = false;
      interplanetHelpShown = false;
      jumpDriveHelpShown = false;
      cloakHelpShown = false;
   }

   public static void sub_d1() {
      try {
         Class.forName("com.nokia.mid.ui.DeviceControl");
      } catch (Throwable var0) {
      }
   }
}
