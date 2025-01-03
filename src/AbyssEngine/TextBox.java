package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class TextBox {
   private int posX;
   private int posY;
   private int width;
   private int height;
   int topPadding;
   private String[] rows;
   private int var_20d;
   private boolean verticalBar;
   private int vBarPercent;
   private String var_2f5;
   private byte[] var_310;
   private int topPadding2;
   private boolean hide;
   int font;
   private Image[] var_3dd;
   private int var_438;

   public TextBox(int var1, int var2, int var3, int var4, String var5) {
      this.posX = var1;
      this.posY = var2;
      this.width = var3;
      this.height = var4;
      this.var_438 = var4;
      this.topPadding = 0;
      if (var5 != null) {
         this.sub_e0(var5);
      }

      this.hide = false;
      this.font = 0;
   }

   public final void zeroTopPadding() {
      this.topPadding = 0;
   }

   public final void setHide(boolean var1) {
      this.hide = true;
   }

   public final void setFont(int var1) {
      this.font = var1;
   }

   public final void sub_e0(String var1) {
      this.var_2f5 = var1;
      if (this.var_310 == null) {
         this.topPadding2 = 0;
      }

      if (var1 != null) {
         if (this.var_310 == null) {
            this.rows = SymbolMapManager_.sub_3b2(var1, this.width + 3);
         } else {
            this.rows = SymbolMapManager_.sub_3a1(var1, this.width + 3, 1, Face_.faceWidth + 3, Face_.faceHeight);
         }

         this.var_20d = SymbolMapManager_.sub_31c(this.rows) + this.topPadding2;
         this.height = AEMath.min(this.var_438, SymbolMapManager_.sub_31c(this.rows) + 13);
         if (this.var_310 != null) {
            this.height = AEMath.max(this.height, Face_.faceHeight + 10);
         }

         this.verticalBar = this.var_20d > this.height;
         if (this.verticalBar) {
            if (this.var_310 == null) {
               this.rows = SymbolMapManager_.sub_3b2(var1, this.width + 3);
            } else {
               this.rows = SymbolMapManager_.sub_3a1(var1, this.width - 3, 1, Face_.faceWidth + 3, Face_.faceHeight);
            }

            this.var_20d = SymbolMapManager_.sub_31c(this.rows) + this.topPadding2;
            this.vBarPercent = (int)((float)this.height / (float)this.var_20d * (float)this.height) - 1;
         }

      }
   }

   public final int sub_115() {
      return this.height + SymbolMapManager_.sub_2c2() + (this.verticalBar ? 6 : 0);
   }

   public final int sub_164() {
      if (this.rows == null) {
         return 0;
      } else {
         return this.var_310 == null ? this.rows.length : AEMath.max(this.rows.length, Face_.faceHeight / this.rows.length + 1);
      }
   }

   public final void sub_173(byte[] var1, String var2, boolean var3) {
      this.var_310 = var1;
      SymbolMapManager_.sub_3b2(var2, this.width - Face_.faceWidth - 2);
      this.var_3dd = Face_.faceFromByteArray(var1);
      this.topPadding2 = 3;
      this.sub_e0(this.var_2f5);
   }

   public final void scrollUp(int var1) {
      if (this.var_20d > this.height) {
         this.topPadding -= var1 / 10;
         if (this.topPadding < -(this.var_20d - this.height)) {
            this.topPadding = -(this.var_20d - this.height);
         }
      }

   }

   public final void scrollDown(int var1) {
      this.topPadding += var1 / 10;
      if (this.topPadding > 0) {
         this.topPadding = 0;
      }

   }

   public final void draw() {
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.setClip(this.posX - 2, this.posY, this.width + 5, this.height);
      if (this.var_310 != null) {
         Face_.drawFace(this.var_3dd, this.posX, this.posY + this.topPadding + 2, 0);
      }

      if (this.rows != null) {
         if (this.hide) {
            SymbolMapManager_.sub_22a(this.rows, this.posX + this.width - 3, this.posY + this.topPadding + this.topPadding2, this.font, 2);
         } else if (this.var_310 == null) {
            SymbolMapManager_.sub_185(this.rows, this.posX + 2, this.posY + this.topPadding + this.topPadding2, this.font);
         } else {
            SymbolMapManager_.sub_1c7(this.rows, this.posX + 2, this.posY + this.topPadding + this.topPadding2, this.font, Face_.faceWidth + 3, Face_.faceHeight);
         }
      }

      GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
      if (this.verticalBar) {
         int var1 = this.posY + 2 - (int)((float)this.topPadding / (float)this.var_20d * (float)(this.height - 7));
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         GameStatus.graphics.drawLine(this.posX + this.width, this.posY + 2, this.posX + this.width, this.posY + this.height - 4);
         GameStatus.graphics.setColor(-35072);
         GameStatus.graphics.fillRect(this.posX + this.width - 1, var1, 3, this.vBarPercent);
         GameStatus.graphics.setColor(-4827904);
         GameStatus.graphics.drawLine(this.posX + this.width - 1, var1 + 1, this.posX + this.width - 1, var1 + this.vBarPercent - 2);
         GameStatus.graphics.drawLine(this.posX + this.width - 1, var1 + this.vBarPercent - 1, this.posX + this.width - 1, var1 + this.vBarPercent - 1);
         GameStatus.graphics.setColor(-11520);
         GameStatus.graphics.drawLine(this.posX + this.width, var1 + 1, this.posX + this.width, var1 + this.vBarPercent - 2);
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      }

   }
}
