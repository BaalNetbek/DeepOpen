package AbyssEngine;

public final class Popup {
   private int posY;
   private int posX;
   private int width;
   private int height;
   private String[] rows;
   private int choicePosY;
   private boolean currentChoice;
   private boolean isChoice;

   public Popup(int var1, int var2, int var3) {
      this.posY = var1;
      this.posX = var2;
      this.width = var3;
      this.height = GameStatus.screenHeight / 2;
   }

   public Popup() {
      this(GameStatus.screenWidth < 176 ? 10 : 20, GameStatus.screenHeight / 2, GameStatus.screenWidth - (GameStatus.screenWidth < 176 ? 20 : 40));
   }

   public final void sub_34(String var1) {
      this.sub_8f(var1, false);
   }

   public final void sub_8f(String var1, boolean var2) {
      this.currentChoice = true;
      this.isChoice = var2;
      this.rows = SymbolMapManager_.sub_3b2(var1, this.width - 14);
      this.posX = (GameStatus.screenHeight >> 1) - (SymbolMapManager_.sub_31c(this.rows) + 2 * SymbolMapManager_.sub_2c2() + 4 >> 1);
      this.choicePosY = this.posX + this.rows.length * SymbolMapManager_.sub_2c2() + 4 + 14 + 2;
      this.height = this.choicePosY + SymbolMapManager_.sub_2c2() + 7 - this.posX;
   }

   public final boolean sub_9a() {
      return this.isChoice ? this.currentChoice : false;
   }

   public final void sub_c6() {
      if (!this.currentChoice && this.isChoice) {
         this.currentChoice = true;
      }

   }

   public final void sub_ff() {
      if (this.currentChoice && this.isChoice) {
         this.currentChoice = false;
      }

   }

   public final void sub_19a() {
      Layout.sub_189();
      Layout.drawTitleBarWindow("", this.posY, this.posX, this.width, this.height, true);
      SymbolMapManager_.sub_185(this.rows, this.posY + 7, this.posX + 14 + 4, 0);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      if (this.isChoice) {
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(38), this.posY + this.width / 3, this.choicePosY, this.currentChoice ? 2 : 1, 24);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(39), this.posY + this.width - this.width / 3, this.choicePosY, this.currentChoice ? 1 : 2, 24);
      } else {
         int var10001 = this.posY + this.width / 2;
         String var1 = "OK";
         SymbolMapManager_.sub_102("OK", var10001 - SymbolMapManager_.sub_25b("OK", 0) / 2, this.choicePosY, 2);
      }
   }
}
