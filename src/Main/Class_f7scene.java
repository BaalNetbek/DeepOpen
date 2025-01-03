package Main;

import AbyssEngine.AbstractScene;
import AbyssEngine.FileRead;
import AbyssEngine.GameStatus;
import AbyssEngine.Status;

public final class Class_f7scene extends AbstractScene {
   private long var_71;
   private long var_b8;
   private long var_ec;
   private long var_11f;
   private boolean var_160;
   private Class_26fscene var_1b3;
   private OptionsWindow var_1e7;
   private boolean var_230 = true;
   private static int var_258 = 0;

   public final void freeResources() {
      if (this.var_1b3 != null) {
         this.var_1b3.freeResources();
      }

      this.var_1b3 = null;
      this.var_160 = false;
      System.gc();
   }

   public final void loadResources() {
      if (this.var_1b3 == null) {
         var_258 = GameStatus.random.nextInt(100);
         Status.setCurrentStation_andInitSystem_((new FileRead()).loadStation(var_258));
         this.var_1b3 = new Class_26fscene(2);
      }

      if (!this.var_1b3.isLoaded()) {
         this.var_1b3.loadResources();
      } else {
         Status.setPlayingTime(0L);
         this.var_1e7 = new OptionsWindow();
         this.var_1e7.sub_33e(0);
         System.gc();
         this.var_71 = System.currentTimeMillis();
         this.var_b8 = System.currentTimeMillis();
         this.var_160 = true;
         this.var_230 = true;
      }
   }

   public final boolean isLoaded() {
      return this.var_160;
   }

   public final void renderScene(int var1) {
      if (this.var_160) {
         this.var_71 = System.currentTimeMillis();
         this.var_ec = this.var_71 - this.var_b8;
         this.var_b8 = this.var_71;
         this.var_11f += this.var_ec;
         this.var_1b3.renderScene(0);
         if (this.var_230) {
            this.var_1e7.sub_12b(var1, (int)this.var_ec);
            this.var_1e7.sub_3a2();
         }

      }
   }

   public final void handleKeystate(int var1) {
      if (this.var_160) {
         if (this.var_230) {
            this.var_1e7.sub_401(var1);
            if (var1 == 4) {
               this.var_1e7.sub_23d();
            }

            if (var1 == 32) {
               this.var_1e7.sub_2c1();
            }

            if (var1 == 2) {
               this.var_1e7.sub_1e5((int)this.var_ec);
            }

            if (var1 == 64) {
               this.var_1e7.sub_215((int)this.var_ec);
            }

            if (var1 == 256) {
               this.var_1e7.sub_148();
            }

            if (var1 == 262144) {
               this.var_1e7.sub_2e7();
            }

            if (var1 == 16384) {
               this.var_1e7.sub_2e7();
            }

            if (var1 == 8192) {
               this.var_1e7.sub_2d7();
            }
         }

      }
   }
}
