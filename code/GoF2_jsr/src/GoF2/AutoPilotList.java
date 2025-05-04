package GoF2;

import AE.GlobalStatus;
import AE.PaintCanvas.Font;

public final class AutoPilotList {
   private int selection;
   private int posX;
   private int posY;
   private int width;
   private String[] rows = new String[5];
   private int rowsNum = 0;

   public AutoPilotList(Level var1) {
      if (Level.programmedStation != null) {
         this.rows[0] = GlobalStatus.gameText.getText(270) + ": " + Level.programmedStation.getName();
         ++this.rowsNum;
      }

      if (Status.getSystem().currentOrbitHasJumpgate()) {
         this.rows[1] = GlobalStatus.gameText.getText(271);
         ++this.rowsNum;
      }

      this.rows[2] = Status.getStation().getName() + " " + GlobalStatus.gameText.getText(40);
      ++this.rowsNum;
      this.rows[3] = GlobalStatus.gameText.getText(273);
      ++this.rowsNum;
      if (var1.getPlayer().getRoute() != null && !var1.getPlayer().getRoute().getLastWaypoint().reached_) {
         this.rows[4] = GlobalStatus.gameText.getText(294);
         ++this.rowsNum;
      }

      this.width = 0;

      for(int var3 = 0; var3 < this.rows.length; ++var3) {
         int var2;
         if (this.rows[var3] != null && (var2 = Font.getTextWidth(this.rows[var3], 0)) + 19 > this.width) {
            this.width = var2 + 19;
         }
      }

      this.posX = (GlobalStatus.screenWidth - this.width) / 2;
      this.posY = (GlobalStatus.screenHeight - (this.rowsNum * 10 + 12)) / 2;

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
      Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(293), this.posX, this.posY, this.width, 12 + (this.rowsNum + 1) * 12 - 5);
      int var1 = 0;

      for(int var2 = 0; var2 < this.rows.length; ++var2) {
         if (this.rows[var2] != null) {
            Layout.drawTextItem(this.rows[var2], this.posX, this.posY + 14 + var1++ * 12, this.width, this.selection == var2);
         }
      }

   }
}
