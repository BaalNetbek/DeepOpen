package AE;

import AE.PaintCanvas.IGraphics3D;
import GoF2.ApplicationManager;
import GoF2.GameText;
import GoF2.LoadingScreen;
import java.util.Random;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public final class GlobalStatus {
   public static boolean musicOn = true;
   public static boolean sfxOn = true;
   public static boolean vibrationOn = true;
   public static boolean invertedControlsOn = true;
   public static int musicVolume = 2;
   public static int sfxVolume = 2;
   public static int language = 6;
   public static boolean shopHelpShown;
   public static boolean shipHelpShown;
   public static boolean actionsHelpShown;
   public static boolean bluePrintsHelpShown;
   public static boolean bluePrintInfoHelpShown;
   public static boolean barHelpShown;
   public static boolean galaxyMapHelpShown;
   public static boolean systemMapHelpShown;
   public static boolean somehelpShown_unused_;
   public static boolean miningHelpShown;
   public static boolean asteroidHelpShown;
   public static boolean missionsHelpShown;
   public static boolean cargoFullHelpShown;
   public static boolean wingmenHelpShown;
   public static boolean actionMenuHelpShown;
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
   public static Renderer renderer;
   public static IGraphics3D graphics3D;
   public static Display display;
   public static LoadingScreen loadingScreen;
   public static int displayedSecondary = -1;
   public static boolean soundDeviceUnavailable = false;
   public static int currentMusic = -1;
   public static GameText gameText;
   public static Graphics graphics;
   public static MIDlet midlet;
   public static int screenWidth;
   public static int screenHeight;
   public static SoundManager soundManager;
   public static Random random;
   public static String gameVersion = "";
   public static boolean paused = false;
   public static ApplicationManager applicationManager;
   public static IApplicationModule[] scenes;

   public static void vibrate(int var0) {
      if (vibrationOn) {
         display.vibrate(var0);
      }

   }

   public static void resetHints() {
      shopHelpShown = false;
      shipHelpShown = false;
      actionsHelpShown = false;
      bluePrintsHelpShown = false;
      bluePrintInfoHelpShown = false;
      barHelpShown = false;
      galaxyMapHelpShown = false;
      systemMapHelpShown = false;
      somehelpShown_unused_ = false;
      miningHelpShown = false;
      asteroidHelpShown = false;
      missionsHelpShown = false;
      cargoFullHelpShown = false;
      wingmenHelpShown = false;
      actionMenuHelpShown = false;
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

   public static void checkDeviceControlSupport() {
      try {
         Class.forName("com.nokia.mid.ui.DeviceControl");
      } catch (Throwable var0) {
      }
   }
}
