package AE.PaintCanvas;

import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import GoF2.Layout;

public final class ImageFactory {
   public static int itemFrameWidth = 31;
   private static int itemFrameHeight = 15;
   private static int itemWidth = 31;
   public static int itemHeight = 15;
   public static int faceWidth = 42;
   public static int faceHeight = 52;
   private static byte[][] faces = new byte[][]{{11, 11, 9, 11, 4}, {5, 4, 6, 10}, {5, 5, 5, 5}, {2, 2, 2, 2}, {2, 2, 3, 3}, {0, 0, 0, 0, 0}, {2, 3, 5, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {1, 1, 1, 1}, {4, 4, 4, 14}, {2, 0, 0, 0}};

   private ImageFactory() {
   }

   public static byte[] createChar(boolean var0, int var1) {
      if (var1 == 3) {
         var1 = GlobalStatus.random.nextInt(4) == 0 ? 0 : 2;
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
         var4[var2] = (byte)GlobalStatus.random.nextInt(faces[var1][var2 - 1]);
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
               var1[var10001] = AEFile.loadImage("/data/interface/faces/" + var2 + "_" + var5 + "_" + var6 + ".png", true);
            }
         }

         return var1;
      }
   }

   public static void drawChar(Image[] var0, int var1, int var2, int var3) {
      GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GlobalStatus.graphics.drawRect(var1, var2, faceWidth - 1, faceHeight - 1);

      for(int var4 = 0; var4 < var0.length; ++var4) {
         if (var0[var4] != null) {
            GlobalStatus.graphics.drawImage(var0[var4], var1 + 1, var2 + 1, var3);
         }
      }

   }

   public static void drawItem(int var0, int var1, Image var2, Image var3, int var4, int var5, int var6) {
      GlobalStatus.graphics.drawRegion(var3, var1 * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, var4, var5, 6);
      GlobalStatus.graphics.drawRegion(var2, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var4, var5, 6);
   }

   public static void drawItemFrameless(int var0, Image var1, int var2, int var3, int var4) {
      GlobalStatus.graphics.drawRegion(var1, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var2, var3, var4);
   }

   public static void drawShip(int var0, int var1, Image var2, Image var3, int var4, int var5, int var6) {
      var1 = var1 == 0 ? 0 : (var1 == 2 ? 1 : (var1 == 8 ? 3 : 2));
      GlobalStatus.graphics.drawRegion(var3, var1 * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, var4, var5, 6);
      GlobalStatus.graphics.drawRegion(var2, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var4, var5, 6);
   }
}
