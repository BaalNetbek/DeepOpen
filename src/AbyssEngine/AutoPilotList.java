package AbyssEngine;

public final class AutoPilotList {
   private int selection;
   private int posX;
   private int posY;
   private int width;
   private String[] rows = new String[5];
   private int var_1a3 = 0;

   public AutoPilotList(Level var1) {
      if (Level.autopilotDestination != null) {
         this.rows[0] = GameStatus.langManager.getLangString(270) + ": " + Level.autopilotDestination.getName();
         ++this.var_1a3;
      }

      if (Status.getSystem().currentOrbitHasJumpgate()) {
         this.rows[1] = GameStatus.langManager.getLangString(271);
         ++this.var_1a3;
      }

      this.rows[2] = Status.getStation().getName() + " " + GameStatus.langManager.getLangString(40);
      ++this.var_1a3;
      this.rows[3] = GameStatus.langManager.getLangString(273);
      ++this.var_1a3;
      if (var1.getPlayerEgo().sub_647() != null && !var1.getPlayerEgo().sub_647().sub_1d7().var_35b) {
         this.rows[4] = GameStatus.langManager.getLangString(294);
         ++this.var_1a3;
      }

      this.width = 0;

      for(int var3 = 0; var3 < this.rows.length; ++var3) {
         int var2;
         if (this.rows[var3] != null && (var2 = SymbolMapManager_.sub_25b(this.rows[var3], 0)) + 19 > this.width) {
            this.width = var2 + 19;
         }
      }

      this.posX = (GameStatus.screenWidth - this.width) / 2;
      this.posY = (GameStatus.screenHeight - (this.var_1a3 * 10 + 12)) / 2;

      while(this.rows[this.selection] == null) {
         this.up();
      }

   }

   public final int getSelection() {
      return this.selection;
   }

   public final void down() {
      if (this.selection > 0) {
         --this.selection;
      } else {
         this.selection = 4;
      }

      if (this.rows[this.selection] == null) {
         this.down();
      }

   }

   public final void up() {
      if (this.selection < 4) {
         ++this.selection;
      } else {
         this.selection = 0;
      }

      if (this.rows[this.selection] == null) {
         this.up();
      }

   }

   public final void draw() {
      Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(293), this.posX, this.posY, this.width, 12 + (this.var_1a3 + 1) * 12 - 5);
      int var1 = 0;

      for(int var2 = 0; var2 < this.rows.length; ++var2) {
         if (this.rows[var2] != null) {
            Layout.scale(this.rows[var2], this.posX, this.posY + 14 + var1++ * 12, this.width, this.selection == var2);
         }
      }

   }
}
