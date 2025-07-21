package Main;

import AE.AEFile;
import AE.GlobalStatus;
import GoF2.Layout;
import GoF2.Achievements;
import GoF2.Status;
import AE.PaintCanvas.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class NewMedalPopup {
   private int posX;
   private int posY;
   private int width;
   private int height;
   private String[] name;
   private String[] description;
   private int okPosY;
   private int tier;
   private Sprite medalImg;

   private NewMedalPopup(int var1, int var2, int var3) {
      this.posX = 10;
      this.posY = var2;
      this.width = var3;
      this.height = GlobalStatus.screenHeight / 2;
   }

   public NewMedalPopup() {
      this(10, GlobalStatus.screenHeight / 2, GlobalStatus.screenWidth - 20);
   }

   public final void set(int var1, int var2) {
      this.tier = var2;
      String var3 = "/data/interface/medals.png";
      Image var4 = AEFile.loadImage("/data/interface/medals.png", true);
      this.medalImg = new Sprite(var4, 31, 15);
      this.name = Font.splitToLines(GlobalStatus.gameText.getText(var1 + 745), this.width - 14 - 31 - 4);
      this.description = Font.splitToLines(Status.replaceToken(GlobalStatus.gameText.getText(var1 + 782), Achievements.VALUES[var1][var2 - 1] + ""), this.width - 14);
      this.posY = (GlobalStatus.screenHeight >> 1) - (Font.getTotalSpacingY(this.description) + 15 + 4 + 2 * Font.getFontSpacingY() >> 1);
      this.okPosY = this.posY + this.description.length * Font.getFontSpacingY() + 15 + 4 + 14 + 2;
      this.height = this.okPosY + Font.getFontSpacingY() + 7 - this.posY;
   }

   public final void draw() {
      Layout.fillClip();
      Layout.drawTitleBarWindow(GlobalStatus.gameText.getText(178), this.posX, this.posY, this.width, this.height, true);
      this.medalImg.setFrame(this.tier);
      this.medalImg.setPosition(this.posX + 7, this.posY + 14 + 2);
      this.medalImg.paint(GlobalStatus.graphics);
      Font.drawLines(this.name, this.posX + 7 + 31 + 4, this.posY + 14 + 2, 1);
      Font.drawLines(this.description, this.posX + 7, this.posY + 15 + 2 + 14, 0);
      GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      int var10001 = this.posX + this.width / 2;
      String var1 = "OK";
      Font.drawString("OK", var10001 - Font.getTextWidth("OK", 0) / 2, this.okPosY, 2);
   }
}
