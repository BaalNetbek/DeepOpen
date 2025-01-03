package Main;

import AbyssEngine.GameStatus;
import AbyssEngine.Layout;
import AbyssEngine.SymbolMapManager_;

public class TabbedWindow {
   protected int posX;
   protected int posY;
   protected int width;
   protected int height;
   protected String[] tabNames;
   protected Object[][] perTabEntries;
   protected int[] var_353;
   protected int var_37d;
   protected int var_3f8;
   private int var_445;
   protected boolean showTabs;
   protected int selectedTab;
   protected int selectedEntry;
   protected int scrollPos;
   protected int var_664;
   protected int innerLeftMargin;
   protected int var_71b;
   protected int rowHeight = 8;
   protected int var_78b;
   protected int var_7e0;
   private boolean var_846;
   protected boolean var_888;
   private int var_8bb;
   protected String titleBarText;

   public TabbedWindow(int var1, int var2, int var3, int var4, String[] var5, String var6) {
      this.sub_35(var1, var2, var3, var4, var5, var6);
      this.selectedTab = 0;
      this.var_846 = true;
      this.var_888 = true;
   }

   private void sub_35(int var1, int var2, int var3, int var4, String[] var5, String var6) {
      if (var1 == 0 && var2 == 0 && var3 == GameStatus.screenWidth && var4 == GameStatus.screenHeight) {
         var2 = 1;
         var1 = 1;
         var3 -= 3;
         var4 = var4 - 4 - 14;
      }

      this.posX = var1;
      this.posY = var2;
      this.width = var3;
      this.height = var4;
      this.showTabs = true;
      if (var5 == null) {
         var5 = new String[]{""};
         this.showTabs = false;
      }

      this.titleBarText = var6;
      this.tabNames = var5;
      this.perTabEntries = new Object[var5.length][200];
      this.var_353 = new int[var5.length];

      for(var3 = 0; var3 < this.var_353.length; ++var3) {
         this.var_353[var3] = 200;
      }

      this.var_7e0 = 0;
      this.selectedEntry = 0;
      this.selectedTab = 0;
      this.scrollPos = 0;
      this.rowHeight = this.sub_get72b();
      this.var_78b = this.sub_149();
      this.innerLeftMargin = var1 + 6;
      if (this.showTabs) {
         this.var_71b = var2 + 14 + this.var_78b;
      } else {
         this.var_71b = var2 + 14;
      }

      for(var3 = 0; var3 < var5.length; ++var3) {
         this.selectedTab = var3;
         this.sub_238();
      }

   }

   public final void sub_8b(int var1) {
      this.rowHeight = var1;
   }

   public int sub_get72b() {
      return this.rowHeight;
   }

   public int sub_149() {
      return 16;
   }

   public final void sub_16a(int var1, Object[] var2) {
      if (var1 > this.perTabEntries.length - 1) {
         System.out.println("ERROR: Tab " + var1 + " not available!");
      } else {
         this.perTabEntries[var1] = new Object[200];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.perTabEntries[var1][var3] = var2[var3];
         }

         this.sub_238();
      }
   }

   public final Object sub_1a3() {
      if (this.selectedEntry < 0) {
         this.selectedEntry = 0;
         return null;
      } else {
         return this.perTabEntries[this.selectedTab] == null ? null : this.perTabEntries[this.selectedTab][this.selectedEntry];
      }
   }

   public final int sub_1e5() {
      return this.selectedTab;
   }

   public final void sub_205(int var1) {
      if (var1 < this.perTabEntries.length) {
         this.selectedTab = var1;
         this.selectedEntry = 0;
         this.scrollPos = 0;
         this.sub_238();
      }

      this.sub_6c();
      this.sub_6b();
      this.sub_b4();
   }

   public void sub_238() {
      this.var_37d = 0;
      this.var_3f8 = 0;
      int var1;
      if (this.perTabEntries[this.selectedTab] != null) {
         for(var1 = 0; var1 < this.perTabEntries[this.selectedTab].length; ++var1) {
            if (this.perTabEntries[this.selectedTab][var1] == null) {
               this.var_664 = var1 - 1;
               this.var_353[this.selectedTab] = var1;
               break;
            }
         }
      }

      this.var_664 = this.var_353[this.selectedTab] - 1;

      for(var1 = 0; var1 < this.var_353[this.selectedTab]; ++var1) {
         if (this.var_71b + (var1 + 1) * this.rowHeight > this.posY + this.height - 12) {
            this.var_664 = var1 - 1;
            this.var_37d = (int)((float)(this.height - this.var_71b + this.posY) * ((float)var1 / (float)this.var_353[this.selectedTab]));
            break;
         }
      }

      this.var_445 = (int)((float)(this.height - this.var_71b + this.posY) * ((float)this.scrollPos / (float)this.var_353[this.selectedTab]));
      if (this.var_37d > 0) {
         this.var_3f8 = 5;
      }

   }

   public void sub_b4() {
      if (this.selectedEntry > 0) {
         --this.selectedEntry;
         if (this.selectedEntry < this.scrollPos + 1) {
            --this.scrollPos;
         }
      }

      this.sub_238();
   }

   public void sub_6b() {
      if (this.selectedEntry != this.perTabEntries[this.selectedTab].length - 1) {
         if (this.perTabEntries[this.selectedTab][this.selectedEntry + 1] != null) {
            ++this.selectedEntry;
            if (this.selectedEntry > this.scrollPos + this.var_664 - 1 && this.selectedEntry < this.var_353[this.selectedTab] - 1) {
               ++this.scrollPos;
            }
         }

         this.sub_238();
      }
   }

   public void sub_2eb() {
      if (this.selectedTab > 0) {
         --this.selectedTab;
         this.selectedEntry = 0;
         this.scrollPos = 0;
      }

      this.sub_238();
   }

   public void sub_2f6() {
      if (this.selectedTab < this.tabNames.length - 1) {
         ++this.selectedTab;
         this.selectedEntry = 0;
         this.scrollPos = 0;
      }

      this.sub_238();
   }

   public void sub_6c() {
      this.sub_19();
      Layout.drawFilledTitleBarWindow(this.titleBarText, this.posX, this.posY, this.width, this.height);
      this.sub_307();
      this.sub_171();
      this.sub_312();
   }

   public void sub_171() {
      for(int var1 = this.scrollPos; var1 < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][var1] != null && var1 < this.scrollPos + this.var_664 + 1; ++var1) {
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         if (var1 == this.selectedEntry && this.var_888) {
            GameStatus.graphics.fillRect(this.posX + 1, this.var_71b + (var1 - this.scrollPos) * this.rowHeight + 1, this.width - 5, this.rowHeight + 1);
         }

         this.sub_e4(this.perTabEntries[this.selectedTab][var1], var1);
      }

   }

   protected void sub_307() {
      if (this.showTabs) {
         GameStatus.graphics.setColor(0);
         GameStatus.graphics.drawLine(this.posX + (this.width >> 1), this.posY + 14, this.posX + (this.width >> 1), this.posY + 14 + 14);
         GameStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 1, this.posY + 14, this.posX + this.width - (this.width >> 1) - 1, this.posY + 14 + 14);
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         GameStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1, this.posY + 14, this.posX + (this.width >> 1) - 1, this.posY + 14 + 14);
         GameStatus.graphics.drawLine(this.posX + (this.width >> 1) + 1, this.posY + 14, this.posX + (this.width >> 1) + 1, this.posY + 14 + 14);
         GameStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 2, this.posY + 14, this.posX + this.width - (this.width >> 1) - 2, this.posY + 14 + 14);
         GameStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1), this.posY + 14, this.posX + this.width - (this.width >> 1), this.posY + 14 + 14);
         switch(this.selectedTab) {
         case 0:
            GameStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1, this.posY + 14 + 14, this.posX + this.width - 3, this.posY + 14 + 14);
            GameStatus.graphics.setColor(0);
            GameStatus.graphics.fillRect(this.posX + (this.width >> 1) + 2, this.posY + 14 + 1, (this.width >> 1) - 3, this.posY + 14 - 2);
            GameStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
            GameStatus.graphics.fillRect(this.posX + (this.width >> 1) + 3, this.posY + 14 + 2, (this.width >> 1) - 5, this.posY + 14 - 3);
            break;
         case 1:
            GameStatus.graphics.drawLine(this.posX + 3, this.posY + 14 + 14, this.posX + (this.width >> 1) + 1, this.posY + 14 + 14);
            GameStatus.graphics.setColor(0);
            GameStatus.graphics.fillRect(this.posX + 3, this.posY + 14 + 1, (this.width >> 1) - 4, this.posY + 14 - 2);
            GameStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
            GameStatus.graphics.fillRect(this.posX + 4, this.posY + 14 + 2, (this.width >> 1) - 6, this.posY + 14 - 3);
         }

         Layout.sub_39a(this.posX + 2, this.posY + 14, this.selectedTab == 0);
         Layout.sub_39a(this.posX + (this.width >> 1) + 1, this.posY + 14, this.selectedTab == 1);
         SymbolMapManager_.sub_161(this.tabNames[0], this.posX + this.width / 4, this.posY + 14 + 1, this.selectedTab == 0 ? 2 : 1, 24);
         SymbolMapManager_.sub_161(this.tabNames[1], this.posX + this.width - this.width / 4, this.posY + 14 + 1, this.selectedTab == 1 ? 2 : 1, 24);
      }

   }

   protected final void sub_312() {
      if (this.var_37d > 0) {
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         GameStatus.graphics.drawLine(this.posX + this.width - 7, this.var_71b + 2, this.posX + this.width - 7, this.posY + this.height - 3 - 14);
         GameStatus.graphics.setColor(-35072);
         GameStatus.graphics.fillRect(this.posX + this.width - 8, this.var_71b + 2 + this.var_445, 3, this.var_37d - 14 - 2);
         GameStatus.graphics.setColor(-4827904);
         GameStatus.graphics.drawLine(this.posX + this.width - 8, this.var_71b + 3 + this.var_445, this.posX + this.width - 8, this.var_71b + this.var_445 + this.var_37d - 15);
         GameStatus.graphics.drawLine(this.posX + this.width - 8, this.var_71b + this.var_445 + this.var_37d - 15, this.posX + this.width - 8 + 1, this.var_71b + this.var_445 + this.var_37d - 15);
         GameStatus.graphics.setColor(-11520);
         GameStatus.graphics.drawLine(this.posX + this.width - 7, this.var_71b + 2 + this.var_445 + 1, this.posX + this.width - 7, this.var_71b + this.var_445 + this.var_37d - 14 - 2);
         GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      }

   }

   protected void sub_19() {
      if (this.var_846) {
         GameStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);
         GameStatus.graphics.fillRect(this.posX, this.posY, this.width, this.height);
      }

   }

   protected void sub_e4(Object var1, int var2) {
      SymbolMapManager_.sub_102(var1.toString(), this.innerLeftMargin, this.var_71b + (var2 - this.scrollPos) * this.rowHeight, this.var_8bb);
   }

   public final int sub_324() {
      return this.selectedEntry;
   }

   public final void sub_346(boolean var1) {
      this.var_846 = false;
   }

   public final void sub_391(boolean var1) {
      this.var_888 = true;
   }

   public final void sub_3ac() {
      this.var_8bb = 1 - this.var_8bb;
   }
}
