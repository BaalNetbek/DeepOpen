package GoF2;

import AE.GlobalStatus;
import AE.PaintCanvas.Font;

public final class Popup {
   private int posX;
   private int posY;
   private int width;
   private int height;
   private String[] rows;
   private int choicePosY;
   private boolean currentChoice;
   private boolean isChoice;

   public Popup(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.width = var3;
      this.height = GlobalStatus.screenHeight / 2;
   }

   public Popup() {
      this(GlobalStatus.screenWidth < 176 ? 10 : 20, GlobalStatus.screenHeight / 2, GlobalStatus.screenWidth - (GlobalStatus.screenWidth < 176 ? 20 : 40));
   }

   public final void setAsWarning(String var1) {
      this.set(var1, false);
   }

   public final void set(String var1, boolean var2) {
      this.currentChoice = true;
      this.isChoice = var2;
      this.rows = Font.splitToLines(var1, this.width - 14);
      this.posY = (GlobalStatus.screenHeight >> 1) - (Font.getTotalSpacingY(this.rows) + 2 * Font.getFontSpacingY() + 4 >> 1);
      this.choicePosY = this.posY + this.rows.length * Font.getFontSpacingY() + 4 + 14 + 2;
      this.height = this.choicePosY + Font.getFontSpacingY() + 7 - this.posY;
   }

   public final boolean getChoice() {
      return this.isChoice ? this.currentChoice : false;
   }

   public final void left() {
      if (!this.currentChoice && this.isChoice) {
         this.currentChoice = true;
      }

   }

   public final void right() {
      if (this.currentChoice && this.isChoice) {
         this.currentChoice = false;
      }

   }

   public final void draw() {
      Layout.fillClip();
      Layout.drawTitleBarWindow("", this.posX, this.posY, this.width, this.height, true);
      Font.drawLines(this.rows, this.posX + 7, this.posY + 14 + 4, 0);
      GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      if (this.isChoice) {
         Font.drawString(GlobalStatus.gameText.getText(38), this.posX + this.width / 3, this.choicePosY, this.currentChoice ? 2 : 1, 24);
         Font.drawString(GlobalStatus.gameText.getText(39), this.posX + this.width - this.width / 3, this.choicePosY, this.currentChoice ? 1 : 2, 24);
      } else {
         int var10001 = this.posX + this.width / 2;
         String var1 = "OK";
         Font.drawString("OK", var10001 - Font.getTextWidth("OK", 0) / 2, this.choicePosY, 2);
      }
   }
}
