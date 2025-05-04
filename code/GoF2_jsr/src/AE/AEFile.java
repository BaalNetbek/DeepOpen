package AE;

import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class AEFile {
   private static Class clazs;

   public static Image resizeImage(Image var0, int var1, int var2) {
      try {
         float var17 = (float)var1 / (float)var0.getWidth();
         float var18 = (float)var2 / (float)var0.getHeight();
         int var3 = var0.getWidth();
         int var4 = var0.getHeight();
         int[] var5 = new int[var3 * var4];
         int[] var6 = new int[(int)((float)var3 * var17) * (int)((float)var4 * var18)];
         var0.getRGB(var5, 0, var3, 0, 0, var3, var4);
         int[] var10000 = var5;
         int var8 = (int)((float)var3 * var17);
         int var7 = var3;
         var6 = var6;
         var5 = var5;
         int var9 = var10000.length / var3;
         int var10 = var6.length / var8;

         for(int var11 = 0; var11 < var8; ++var11) {
            for(int var12 = 0; var12 < var10; ++var12) {
               float var13 = (float)var11 * (float)(var7 - 1) / (float)(var8 - 1);
               boolean var14 = false;
               int var19;
               if ((double)var13 - Math.floor((double)var13) > 0.5D) {
                  var19 = (int)Math.ceil((double)var13);
               } else {
                  var19 = (int)Math.floor((double)var13);
               }

               var13 = (float)var12 * (float)(var9 - 1) / (float)(var10 - 1);
               boolean var15 = false;
               int var20;
               if ((double)var13 - Math.floor((double)var13) > 0.5D) {
                  var20 = (int)Math.ceil((double)var13);
               } else {
                  var20 = (int)Math.floor((double)var13);
               }

               var6[var11 + var12 * var8] = var5[var19 + var20 * var7];
            }
         }

         var0 = Image.createRGBImage(var6, (int)((float)var3 * var17), (int)((float)var4 * var18), true);
         System.gc();
      } catch (Exception var16) {
         var16.printStackTrace();
      }

      return var0;
   }

   public static Image loadImage(String var0, boolean var1) {
      Image var2 = null;
      if (var1) {
         try {
            InputStream var8 = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(var0);
            DataInputStream var9;
            int var11;
            byte[] var3 = new byte[var11 = (var9 = new DataInputStream(var8)).available()];
            var9.read(var3, 0, var11);
            var9.close();
            int var10;
            if (var11 < 100) {
               var10 = 10 + var11 % 10;
            } else if (var11 < 200) {
               var10 = 50 + var11 % 20;
            } else if (var11 < 300) {
               var10 = 80 + var11 % 20;
            } else {
               var10 = 100 + var11 % 50;
            }

            for(int var4 = 0; var4 < var10; ++var4) {
               byte var5 = var3[var4];
               var3[var4] = var3[var11 - var4 - 1];
               var3[var11 - var4 - 1] = var5;
            }

            var2 = Image.createImage(var3, 0, var11);
            System.gc();
         } catch (Exception var7) {
         }
      } else {
         try {
            var2 = Image.createImage(var0);
         } catch (Exception var6) {
         }
      }

      return var2;
   }

   public static Image loadCryptedImage(String var0) {
      return loadImage(var0, true);
   }

   public static byte[] readFileBytes(String var0) {
      byte[] var1 = null;

      try {
         InputStream var4 = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(var0);
         int var2;
         DataInputStream var5;
         var1 = new byte[var2 = (var5 = new DataInputStream(var4)).available()];
         var5.read(var1, 0, var2);
         var5.close();
      } catch (Exception var3) {
      }

      return var1;
   }

   private static Class getClass(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var1) {
         throw new NoClassDefFoundError(var1.getMessage());
      }
   }
}
