package GoF2.Main;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import GoF2.Achievements;
import GoF2.Layout;
import GoF2.ListItem;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class StatusWindow extends HangarList {
   private Sprite sprite;

   public StatusWindow(String[] var1, String var2) {
      super(var1, var2);
      String var5 = "/data/interface/medals.png";
      Image var6 = AEFile.loadImage("/data/interface/medals.png", true);
      this.sprite = new Sprite(var6, 31, 15);
      this.setRowHeight(17);
      int[] var7 = Achievements.getMedals();
      int var8 = 0;
      if (var7 != null) {
         for(int var3 = 0; var3 < var7.length; ++var3) {
            if (var7[var3] > 0) {
               ++var8;
            }
         }

         ListItem[] var9 = new ListItem[var7.length];

         for(int var4 = 0; var4 < var7.length; ++var4) {
            var9[var4] = new ListItem(var4, var7[var4]);
         }

         super.setEntries(1, var9);
      }

      this.unused_2_ = GlobalStatus.gameText.getText(63) + ": " + var8 + "/" + Achievements.medalTresholds.length;
   }

   public final void draw() {
      super.draw();
      if (this.selectedTab == 1) {
         this.drawFrame();
      }

   }

   public final void drawFrame() {
      GlobalStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GlobalStatus.graphics.fillRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      GlobalStatus.graphics.setColor(0);
      GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 15, this.width - 3, this.posY + this.height - 15);
      GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 1, this.width - 3, this.posY + this.height - 1);
      GlobalStatus.graphics.drawRect(this.posX + 3, this.posY + this.height - 13, this.width - 6, 10);
      GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GlobalStatus.graphics.drawRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      Font.drawString(this.unused_2_, this.posX + this.width - 3, this.posY + this.height - 13, 1, 18);
   }

   public final void drawItem(Object var1, int var2) {
      ListItem var3 = (ListItem)var1;
      this.sprite.setFrame(var3.medalTier);
      this.sprite.setPosition(this.innerLeftMargin + 4, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1);
      this.sprite.paint(GlobalStatus.graphics);
      Layout.drawStringWidthLimited(GlobalStatus.gameText.getText(745 + var3.itemId), this.innerLeftMargin + 4 + 31 + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 2, this.width - this.innerLeftMargin - 31 - 4 - 5 - this.listRightPadding - 2, this.selectedEntry == var2 ? 2 : 1);
   }
}
