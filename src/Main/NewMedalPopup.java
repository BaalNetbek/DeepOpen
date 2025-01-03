package Main;

import AbyssEngine.AEImage;
import AbyssEngine.GameStatus;
import AbyssEngine.Layout;
import AbyssEngine.Medals;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
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
   private Sprite var_32d;

   private NewMedalPopup(int var1, int var2, int var3) {
      this.posX = 10;
      this.posY = var2;
      this.width = var3;
      this.height = GameStatus.screenHeight / 2;
   }

   public NewMedalPopup() {
      this(10, GameStatus.screenHeight / 2, GameStatus.screenWidth - 20);
   }

   public final void sub_49(int var1, int var2) {
      this.tier = var2;
      String var3 = "/data/interface/medals.png";
      Image var4 = AEImage.loadImage("/data/interface/medals.png", true);
      this.var_32d = new Sprite(var4, 31, 15);
      this.name = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(var1 + 745), this.width - 14 - 31 - 4);
      this.description = SymbolMapManager_.sub_3b2(Status.replaceToken(GameStatus.langManager.getLangString(var1 + 782), Medals.medalTresholds[var1][var2 - 1] + ""), this.width - 14);
      this.posY = (GameStatus.screenHeight >> 1) - (SymbolMapManager_.sub_31c(this.description) + 15 + 4 + 2 * SymbolMapManager_.sub_2c2() >> 1);
      this.okPosY = this.posY + this.description.length * SymbolMapManager_.sub_2c2() + 15 + 4 + 14 + 2;
      this.height = this.okPosY + SymbolMapManager_.sub_2c2() + 7 - this.posY;
   }

   public final void sub_006() {
      Layout.sub_189();
      Layout.drawTitleBarWindow(GameStatus.langManager.getLangString(178), this.posX, this.posY, this.width, this.height, true);
      this.var_32d.setFrame(this.tier);
      this.var_32d.setPosition(this.posX + 7, this.posY + 14 + 2);
      this.var_32d.paint(GameStatus.graphics);
      SymbolMapManager_.sub_185(this.name, this.posX + 7 + 31 + 4, this.posY + 14 + 2, 1);
      SymbolMapManager_.sub_185(this.description, this.posX + 7, this.posY + 15 + 2 + 14, 0);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      int var10001 = this.posX + this.width / 2;
      String var1 = "OK";
      SymbolMapManager_.sub_102("OK", var10001 - SymbolMapManager_.sub_25b("OK", 0) / 2, this.okPosY, 2);
   }
}
