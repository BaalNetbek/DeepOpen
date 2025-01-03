package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class Class_23e {
   private Class_85e[] var_4b;
   private Class_85e var_86;
   private String[] var_128;
   private byte[] var_163;
   private long var_18f;
   private int var_1b8;
   private String var_213;
   private long var_2bb;
   private Image[] var_2db;
   private int var_30f;

   public final void sub_39() {
      this.var_4b = null;
      this.var_86 = null;
   }

   public final void sub_96(Class_85e[] var1) {
      this.var_4b = var1;
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2].sub_76(this);
         }
      }

   }

   public final void sub_cf(Class_85e var1) {
      this.var_86 = var1;
   }

   public final Class_85e sub_133(int var1) {
      return this.var_4b[var1];
   }

   public final void sub_193() {
      this.var_2bb = 501L;
   }

   public final void sub_1f5(long var1, long var3, PlayerEgo var5) {
      int var13;
      if (this.var_86 == null) {
         this.var_2bb += var3;
         if (this.var_2bb > 500L) {
            this.var_2bb = 0L;
            PlayerEgo var2 = var5;
            long var8 = var1;
            Class_23e var11 = this;
            if (this.var_4b == null) {
               return;
            }

            var13 = 0;

            while(true) {
               if (var13 >= var11.var_4b.length) {
                  return;
               }

               if (var11.var_4b[var13].sub_18e(var8, var2)) {
                  int var12;
                  if ((var12 = var11.var_4b[var13].sub_5e()) >= 21) {
                     int var4 = var12 == 24 ? 2 : (var12 == 23 ? 0 : (var12 == 21 ? 3 : 1));
                     var11.var_163 = Face_.getRandomFace(true, var4);
                  } else {
                     var11.var_163 = IndexManager.CHAR_IMAGES[var12];
                  }

                  var11.var_2db = Face_.faceFromByteArray(var11.var_163);
                  var11.var_30f = var12 == 19 ? 3 : 1;
                  var11.var_128 = SymbolMapManager_.sub_3a1(GameStatus.langManager.getLangString(var11.var_4b[var13].sub_50()), GameStatus.screenWidth - 10, var11.var_30f, Face_.faceWidth + 3, Face_.faceHeight + 3);
                  var11.var_18f = var8;
                  var11.var_1b8 = var11.var_128.length * (int)(2000.0F * (float)GameStatus.screenWidth / 240.0F) + 1500;
                  break;
               }

               ++var13;
            }
         }
      } else if (var1 > this.var_18f + 2000L) {
         try {
            var13 = this.var_86.sub_5e();
            this.var_213 = GameStatus.langManager.getLangString(var13 + 819);
            Layout.drawTitleBarWindow(this.var_213, 0, 0, GameStatus.screenWidth - 1, AEMath.max(Face_.faceHeight + 21, (this.var_128.length + 2) * SymbolMapManager_.sub_2c2()), true);
         } catch (Exception var10) {
            var10.printStackTrace();
         }

         Face_.drawFace(this.var_2db, 5, 17, 0);
         SymbolMapManager_.sub_1c7(this.var_128, 7, 17, this.var_30f, Face_.faceWidth + 3, Face_.faceHeight + 3);
         if (var1 > this.var_18f + 2000L + (long)this.var_1b8) {
            this.var_18f = 0L;
            this.var_86.sub_163();
            this.var_86 = null;
         }
      }

   }
}
