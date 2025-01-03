package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class LensFlareFX {
   private Image[] var_74 = new Image[4];
   private Image var_1c3;

   public LensFlareFX() {
      for(int var1 = 0; var1 < this.var_74.length; ++var1) {
         this.var_74[var1] = AEImage.loadImage("/data/interface/lens" + var1 + ".png", true);
      }

      String var4 = "/data/interface/lens1.png";
      Image var5 = AEImage.loadImage("/data/interface/lens1.png", true);
      int var10002 = var5.getWidth() / 4;
      int var3 = var5.getHeight() / 4;
      int var2 = var10002;
      String var6 = "/data/interface/lens1.png";
      var4 = "/data/interface/lens1.png";
      this.var_1c3 = AEImage.resizeImage(AEImage.loadImage("/data/interface/lens1.png", true), var2, var3);
   }

   public final void render2D(AEVector3D var1) {
      int var2 = var1.x;
      int var3 = var1.y;
      if (var1.z < 0 && var2 > -GameStatus.screenWidth && var2 < GameStatus.screenWidth << 1 && var3 > -GameStatus.screenHeight && var3 < GameStatus.screenHeight << 1) {
         int var7 = GameStatus.screenWidth >> 1;
         int var4 = GameStatus.screenHeight >> 1;
         var2 = var7 - var2;
         var3 = var4 - var3;
         int var5 = AEMath.sqrt((long)(var2 * var2 + var3 * var3));
         var2 = -(var2 << 12);
         var3 = -(var3 << 12);
         if (var5 > 0) {
            var2 /= var5;
            var3 /= var5;
         }

         if (this.var_74 != null) {
            for(int var6 = 0; var6 < this.var_74.length; ++var6) {
               switch(var6) {
               case 0:
                  if (var5 > 2000) {
                     GameStatus.graphics.drawImage(this.var_74[var6], var7 + (var2 * var5 / 2 >> 12), var4 + (var3 * var5 / 2 >> 12), 3);
                     if (this.var_1c3 != null) {
                        GameStatus.graphics.drawImage(this.var_1c3, var7 + (var2 * var5 / 3 >> 12), var4 + (var3 * var5 / 3 >> 12), 3);
                     }
                  }
                  break;
               case 1:
                  if (var5 > 3000) {
                     GameStatus.graphics.drawImage(this.var_74[var6], var7 + (var2 * var5 / 8 >> 12), var4 + (var3 * var5 / 8 >> 12), 3);
                     if (this.var_1c3 != null) {
                        GameStatus.graphics.drawImage(this.var_1c3, var7 - (var2 * var5 / 2 >> 12), var4 - (var3 * var5 / 2 >> 12), 3);
                     }
                  }
                  break;
               case 2:
                  if (var5 > 4000) {
                     GameStatus.graphics.drawImage(this.var_74[var6], var7 - (var2 * var5 / 4 >> 12), var4 - (var3 * var5 / 4 >> 12), 3);
                     if (this.var_1c3 != null) {
                        GameStatus.graphics.drawImage(this.var_1c3, var7 - (var2 * var5 / 6 >> 12), var4 - (var3 * var5 / 6 >> 12), 3);
                     }
                  }
                  break;
               case 3:
                  if (var5 > 5000) {
                     GameStatus.graphics.drawImage(this.var_74[var6], var7 - (var2 * var5 / 7 >> 12), var4 - (var3 * var5 / 7 >> 12), 3);
                     if (this.var_1c3 != null) {
                        GameStatus.graphics.drawImage(this.var_1c3, var7 - (var2 * var5 / 10 >> 12), var4 - (var3 * var5 / 10 >> 12), 3);
                     }
                  }
               }
            }
         }
      }

   }
}
