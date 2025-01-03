package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class Face_ {
   public static int var_c6 = 31;
   private static int var_1cb = 15;
   private static int var_1ed = 31;
   public static int var_268 = 15;
   public static int faceWidth = 42;
   public static int faceHeight = 52;
   private static byte[][] faces = new byte[][]{{11, 11, 9, 11, 4}, {5, 4, 6, 10}, {5, 5, 5, 5}, {2, 2, 2, 2}, {2, 2, 3, 3}, {0, 0, 0, 0, 0}, {2, 3, 5, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {1, 1, 1, 1}, {4, 4, 4, 14}, {2, 0, 0, 0}};

   private Face_() {
   }

   public static byte[] getRandomFace(boolean var0, int var1) {
      if (var1 == 3) {
         var1 = GameStatus.random.nextInt(4) == 0 ? 0 : 2;
      }

      if (!var0 && var1 == 0) {
         var1 = 10;
      }

      Object var3 = null;
      byte[] var4;
      if (var1 == 0) {
         var4 = new byte[faces[var1].length];
      } else {
         var4 = new byte[faces[var1].length + 1];
      }

      if (var1 == 5) {
         var1 = 0;
      }

      var4[0] = (byte)var1;

      for(int var2 = 1; var2 < var4.length; ++var2) {
         var4[var2] = (byte)GameStatus.random.nextInt(faces[var1][var2 - 1]);
      }

      return var4;
   }

   public static Image[] faceFromByteArray(byte[] var0) {
      if (var0 == null) {
         return null;
      } else {
         Image[] var1 = new Image[var0.length - 1];
         byte var2 = var0[0];

         for(int var3 = 1; var3 < var0.length; ++var3) {
            if (var0[var3] >= 0) {
               int var10001 = var3 - 1;
               int var10003 = var3 - 1;
               byte var6 = var0[var3];
               int var5 = var10003;
               var1[var10001] = AEImage.loadImage("/data/interface/faces/" + var2 + "_" + var5 + "_" + var6 + ".png", true);
            }
         }

         return var1;
      }
   }

   public static void drawFace(Image[] var0, int var1, int var2, int var3) {
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.drawRect(var1, var2, faceWidth - 1, faceHeight - 1);

      for(int var4 = 0; var4 < var0.length; ++var4) {
         if (var0[var4] != null) {
            GameStatus.graphics.drawImage(var0[var4], var1 + 1, var2 + 1, var3);
         }
      }

   }

   public static void sub_128(int var0, int var1, Image var2, Image var3, int var4, int var5, int var6) {
      GameStatus.graphics.drawRegion(var3, var1 * var_c6, 0, var_c6, var_1cb, 0, var4, var5, 6);
      GameStatus.graphics.drawRegion(var2, var0 * var_1ed, 0, var_1ed, var_268, 0, var4, var5, 6);
   }

   public static void sub_158(int var0, Image var1, int var2, int var3, int var4) {
      GameStatus.graphics.drawRegion(var1, var0 * var_1ed, 0, var_1ed, var_268, 0, var2, var3, var4);
   }

   public static void sub_1b6(int var0, int var1, Image var2, Image var3, int var4, int var5, int var6) {
      var1 = var1 == 0 ? 0 : (var1 == 2 ? 1 : (var1 == 8 ? 3 : 2));
      GameStatus.graphics.drawRegion(var3, var1 * var_c6, 0, var_c6, var_1cb, 0, var4, var5, 6);
      GameStatus.graphics.drawRegion(var2, var0 * var_1ed, 0, var_1ed, var_268, 0, var4, var5, 6);
   }
}
