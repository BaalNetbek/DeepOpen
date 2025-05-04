package AE;

import javax.microedition.lcdui.Image;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.Texture2D;

public final class JSRTexture extends ITexture {
   private Texture2D[] textures;

   public JSRTexture(JSRTexture var1) {
      if (var1 != null) {
         this.textures = new Texture2D[1];

         try {
            Image2D var3 = var1.textures[0].getImage();
            this.textures[0] = new Texture2D(var3);
            this.textures[0].setBlending(228);
            this.textures[0].setWrapping(241, 241);
            return;
         } catch (Exception var2) {
            this.textures = null;
         }
      }

   }

   public JSRTexture(String[] var1) {
      if (var1 != null) {
         this.textures = new Texture2D[var1.length];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            try {
               Image var3 = AEFile.loadImage(var1[var2] + ".png", false);
               Image2D var5 = new Image2D(100, var3);
               this.textures[var2] = new Texture2D(var5);
               this.textures[var2].setBlending(227);
               this.textures[var2].setWrapping(241, 241);
            } catch (Exception var4) {
               this.textures = null;
            }
         }
      }

   }

   public final Texture2D[] getTexturesArray() {
      return this.textures;
   }

   public final void OnRelease() {
      if (this.textures != null) {
         for(int var1 = 0; var1 < this.textures.length; ++var1) {
            this.textures[var1] = null;
         }

         this.textures = null;
      }

   }
}
