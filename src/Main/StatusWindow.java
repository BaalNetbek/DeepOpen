package Main;

import AbyssEngine.AEImage;
import AbyssEngine.GameStatus;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Medals;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class StatusWindow extends ThemedWindow {
   private Sprite sprite;

   public StatusWindow(String[] var1, String var2) {
      super(var1, var2);
      String var5 = "/data/interface/medals.png";
      Image var6 = AEImage.loadImage("/data/interface/medals.png", true);
      this.sprite = new Sprite(var6, 31, 15);
      this.sub_8b(17);
      int[] var7 = Medals.getMedals();
      int var8 = 0;
      if (var7 != null) {
         for(int var3 = 0; var3 < var7.length; ++var3) {
            if (var7[var3] > 0) {
               ++var8;
            }
         }

         ListEntry[] var9 = new ListEntry[var7.length];

         for(int var4 = 0; var4 < var7.length; ++var4) {
            var9[var4] = new ListEntry(var4, var7[var4]);
         }

         super.sub_16a(1, var9);
      }

      this.var_3bf = GameStatus.langManager.getLangString(63) + ": " + var8 + "/" + Medals.medalTresholds.length;
   }

   public final void sub_6c() {
      super.sub_6c();
      if (this.selectedTab == 1) {
         this.sub_9e();
      }

   }

   public final void sub_9e() {
      GameStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GameStatus.graphics.fillRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 15, this.width - 3, this.posY + this.height - 15);
      GameStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 1, this.width - 3, this.posY + this.height - 1);
      GameStatus.graphics.drawRect(this.posX + 3, this.posY + this.height - 13, this.width - 6, 10);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.drawRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      SymbolMapManager_.sub_161(this.var_3bf, this.posX + this.width - 3, this.posY + this.height - 13, 1, 18);
   }

   public final void sub_e4(Object var1, int var2) {
      ListEntry var3 = (ListEntry)var1;
      this.sprite.setFrame(var3.medalTier);
      this.sprite.setPosition(this.innerLeftMargin + 4, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1);
      this.sprite.paint(GameStatus.graphics);
      Layout.sub_4e1(GameStatus.langManager.getLangString(745 + var3.itemId), this.innerLeftMargin + 4 + 31 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 2, this.width - this.innerLeftMargin - 31 - 4 - 5 - this.var_3f8 - 2, this.selectedEntry == var2 ? 2 : 1);
   }
}
