package Main;

import AbyssEngine.GameStatus;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class GOF2MIDlet extends MIDlet implements Runnable {
   public int state;
   public static final int RUNNING = 1;
   public static final int RESUME = 2;
   public static final int PAUSE = 3;
   public static final int PRE_PAUSE = 4;
   private GOF2Canvas canvas;
   private Display display;
   private boolean var_619 = false;
   private boolean var_626 = false;
   private Thread thread;

   public GOF2MIDlet() {
      try {
         this.display = Display.getDisplay(this);
         GameStatus.display = this.display;
         GameStatus.midlet = this;
         this.canvas = new GOF2Canvas(this);
         this.thread = new Thread(this);
         this.state = 1;
      } catch (Exception var1) {
      }
   }

   public Display getDisplay() {
      return this.display;
   }

   public void startApp() {
      try {
         this.display.setCurrent(this.canvas);
         this.canvas.setFullScreenMode(true);
         if (!this.var_619) {
            this.thread.start();
            this.var_619 = true;
         }

      } catch (Exception var1) {
      }
   }

   public void pauseApp() {
      this.pause();
   }

   public void resume() {
      this.state = 2;
   }

   public void pause() {
      this.state = 4;
   }

   public void destroyApp(boolean var1) {
      try {
         this.var_626 = true;
         this.canvas.sub_10d();
         this.notifyDestroyed();
      } catch (Exception var2) {
      }
   }

   public void run() {
      try {
         while(true) {
            switch(this.state) {
            case 1:
               if (GameStatus.var_9da && !this.var_626 && GameStatus.var_9fc != -1) {
                  GameStatus.soundManager.playMusic(GameStatus.var_9fc);
               }

               this.canvas.sub_ca();
               this.canvas.synchronizeKeyState();
               break;
            case 2:
               if (GameStatus.soundManager != null && GameStatus.musicOn) {
                  GameStatus.soundManager.sub_287();
               }

               GameStatus.var_bd5 = false;
               this.state = 1;
               break;
            case 3:
               this.canvas.sub_125();
               this.canvas.synchronizeKeyState();
               break;
            case 4:
               this.canvas.sub_8b();
               this.state = 3;
            }

            try {
               Thread.sleep(2L);
            } catch (InterruptedException var2) {
            }
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public void quit() {
      this.destroyApp(false);
   }
}
