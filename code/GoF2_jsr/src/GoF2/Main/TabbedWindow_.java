package GoF2.Main;

import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import GoF2.Layout;

public class TabbedWindow_ {
   protected int posX;
   protected int posY;
   protected int width;
   protected int height;
   protected String[] tabNames;
   protected Object[][] perTabEntries;
   protected int[] listsLenghts;
   protected int scrollThumbSize;
   protected int listRightPadding;
   private int scrollThumbPos;
   protected boolean showTabs;
   protected int selectedTab;
   protected int selectedEntry;
   protected int scrollPos;
   protected int displayedEntriesCount;
   protected int innerLeftMargin;
   protected int itemListPosY;
   protected int rowHeight = 8;
   protected int tabHeight;
   protected int unused2412_;
   private boolean drawBackGround;
   protected boolean highlightSelection;
   private int fontId_;
   protected String titleBarText;

   public TabbedWindow_(int var1, int var2, int var3, int var4, String[] var5, String var6) {
      this.init(var1, var2, var3, var4, var5, var6);
      this.selectedTab = 0;
      this.drawBackGround = true;
      this.highlightSelection = true;
   }

   private void init(int var1, int var2, int var3, int var4, String[] var5, String var6) {
      if (var1 == 0 && var2 == 0 && var3 == GlobalStatus.screenWidth && var4 == GlobalStatus.screenHeight) {
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
      this.listsLenghts = new int[var5.length];

      for(var3 = 0; var3 < this.listsLenghts.length; ++var3) {
         this.listsLenghts[var3] = 200;
      }

      this.unused2412_ = 0;
      this.selectedEntry = 0;
      this.selectedTab = 0;
      this.scrollPos = 0;
      this.rowHeight = this.getRowHeight();
      this.tabHeight = this.getTabHeight();
      this.innerLeftMargin = var1 + 6;
      if (this.showTabs) {
         this.itemListPosY = var2 + 14 + this.tabHeight;
      } else {
         this.itemListPosY = var2 + 14;
      }

      for(var3 = 0; var3 < var5.length; ++var3) {
         this.selectedTab = var3;
         this.updateScroll();
      }

   }

   public final void setRowHeight(int var1) {
      this.rowHeight = var1;
   }

   public int getRowHeight() {
      return this.rowHeight;
   }

   public int getTabHeight() {
      return 16;
   }

   public final void setEntries(int var1, Object[] var2) {
      if (var1 > this.perTabEntries.length - 1) {
         System.out.println("ERROR: Tab " + var1 + " not available!");
      } else {
         this.perTabEntries[var1] = new Object[200];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.perTabEntries[var1][var3] = var2[var3];
         }

         this.updateScroll();
      }
   }

   public final Object getSelectedItem() {
      if (this.selectedEntry < 0) {
         this.selectedEntry = 0;
         return null;
      } else {
         return this.perTabEntries[this.selectedTab] == null ? null : this.perTabEntries[this.selectedTab][this.selectedEntry];
      }
   }

   public final int getCurrentTab() {
      return this.selectedTab;
   }

   public final void setCurrentTab(int var1) {
      if (var1 < this.perTabEntries.length) {
         this.selectedTab = var1;
         this.selectedEntry = 0;
         this.scrollPos = 0;
         this.updateScroll();
      }

      this.draw();
      this.scrollDown();
      this.scrollUp();
   }

   public void updateScroll() {
      this.scrollThumbSize = 0;
      this.listRightPadding = 0;
      int var1;
      if (this.perTabEntries[this.selectedTab] != null) {
         for(var1 = 0; var1 < this.perTabEntries[this.selectedTab].length; ++var1) {
            if (this.perTabEntries[this.selectedTab][var1] == null) {
               this.displayedEntriesCount = var1 - 1;
               this.listsLenghts[this.selectedTab] = var1;
               break;
            }
         }
      }

      this.displayedEntriesCount = this.listsLenghts[this.selectedTab] - 1;

      for(var1 = 0; var1 < this.listsLenghts[this.selectedTab]; ++var1) {
         if (this.itemListPosY + (var1 + 1) * this.rowHeight > this.posY + this.height - 12) {
            this.displayedEntriesCount = var1 - 1;
            this.scrollThumbSize = (int)((float)(this.height - this.itemListPosY + this.posY) * ((float)var1 / (float)this.listsLenghts[this.selectedTab]));
            break;
         }
      }

      this.scrollThumbPos = (int)((float)(this.height - this.itemListPosY + this.posY) * ((float)this.scrollPos / (float)this.listsLenghts[this.selectedTab]));
      if (this.scrollThumbSize > 0) {
         this.listRightPadding = 5;
      }

   }

   public void scrollUp() {
      if (this.selectedEntry > 0) {
         --this.selectedEntry;
         if (this.selectedEntry < this.scrollPos + 1) {
            --this.scrollPos;
         }
      }

      this.updateScroll();
   }

   public void scrollDown() {
      if (this.selectedEntry != this.perTabEntries[this.selectedTab].length - 1) {
         if (this.perTabEntries[this.selectedTab][this.selectedEntry + 1] != null) {
            ++this.selectedEntry;
            if (this.selectedEntry > this.scrollPos + this.displayedEntriesCount - 1 && this.selectedEntry < this.listsLenghts[this.selectedTab] - 1) {
               ++this.scrollPos;
            }
         }

         this.updateScroll();
      }
   }

   public void leftAction() {
      if (this.selectedTab > 0) {
         --this.selectedTab;
         this.selectedEntry = 0;
         this.scrollPos = 0;
      }

      this.updateScroll();
   }

   public void rightAction() {
      if (this.selectedTab < this.tabNames.length - 1) {
         ++this.selectedTab;
         this.selectedEntry = 0;
         this.scrollPos = 0;
      }

      this.updateScroll();
   }

   public void draw() {
      this.drawBG();
      Layout.drawFilledTitleBarWindow(this.titleBarText, this.posX, this.posY, this.width, this.height);
      this.drawTabs();
      this.drawItems();
      this.drawScroll();
   }

   public void drawItems() {
      for(int var1 = this.scrollPos; var1 < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][var1] != null && var1 < this.scrollPos + this.displayedEntriesCount + 1; ++var1) {
         GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         if (var1 == this.selectedEntry && this.highlightSelection) {
            GlobalStatus.graphics.fillRect(this.posX + 1, this.itemListPosY + (var1 - this.scrollPos) * this.rowHeight + 1, this.width - 5, this.rowHeight + 1);
         }

         this.drawItem(this.perTabEntries[this.selectedTab][var1], var1);
      }

   }

   protected void drawTabs() {
      if (this.showTabs) {
         GlobalStatus.graphics.setColor(0);
         GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1), this.posY + 14, this.posX + (this.width >> 1), this.posY + 14 + 14);
         GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 1, this.posY + 14, this.posX + this.width - (this.width >> 1) - 1, this.posY + 14 + 14);
         GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1, this.posY + 14, this.posX + (this.width >> 1) - 1, this.posY + 14 + 14);
         GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) + 1, this.posY + 14, this.posX + (this.width >> 1) + 1, this.posY + 14 + 14);
         GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 2, this.posY + 14, this.posX + this.width - (this.width >> 1) - 2, this.posY + 14 + 14);
         GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1), this.posY + 14, this.posX + this.width - (this.width >> 1), this.posY + 14 + 14);
         switch(this.selectedTab) {
         case 0:
            GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1, this.posY + 14 + 14, this.posX + this.width - 3, this.posY + 14 + 14);
            GlobalStatus.graphics.setColor(0);
            GlobalStatus.graphics.fillRect(this.posX + (this.width >> 1) + 2, this.posY + 14 + 1, (this.width >> 1) - 3, this.posY + 14 - 2);
            GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
            GlobalStatus.graphics.fillRect(this.posX + (this.width >> 1) + 3, this.posY + 14 + 2, (this.width >> 1) - 5, this.posY + 14 - 3);
            break;
         case 1:
            GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + 14 + 14, this.posX + (this.width >> 1) + 1, this.posY + 14 + 14);
            GlobalStatus.graphics.setColor(0);
            GlobalStatus.graphics.fillRect(this.posX + 3, this.posY + 14 + 1, (this.width >> 1) - 4, this.posY + 14 - 2);
            GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
            GlobalStatus.graphics.fillRect(this.posX + 4, this.posY + 14 + 2, (this.width >> 1) - 6, this.posY + 14 - 3);
         }

         Layout.drawMenuPanelCorner(this.posX + 2, this.posY + 14, this.selectedTab == 0);
         Layout.drawMenuPanelCorner(this.posX + (this.width >> 1) + 1, this.posY + 14, this.selectedTab == 1);
         Font.drawString(this.tabNames[0], this.posX + this.width / 4, this.posY + 14 + 1, this.selectedTab == 0 ? 2 : 1, 24);
         Font.drawString(this.tabNames[1], this.posX + this.width - this.width / 4, this.posY + 14 + 1, this.selectedTab == 1 ? 2 : 1, 24);
      }

   }

   protected final void drawScroll() {
      if (this.scrollThumbSize > 0) {
         GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         GlobalStatus.graphics.drawLine(this.posX + this.width - 7, this.itemListPosY + 2, this.posX + this.width - 7, this.posY + this.height - 3 - 14);
         GlobalStatus.graphics.setColor(-35072);
         GlobalStatus.graphics.fillRect(this.posX + this.width - 8, this.itemListPosY + 2 + this.scrollThumbPos, 3, this.scrollThumbSize - 14 - 2);
         GlobalStatus.graphics.setColor(-4827904);
         GlobalStatus.graphics.drawLine(this.posX + this.width - 8, this.itemListPosY + 3 + this.scrollThumbPos, this.posX + this.width - 8, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15);
         GlobalStatus.graphics.drawLine(this.posX + this.width - 8, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15, this.posX + this.width - 8 + 1, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15);
         GlobalStatus.graphics.setColor(-11520);
         GlobalStatus.graphics.drawLine(this.posX + this.width - 7, this.itemListPosY + 2 + this.scrollThumbPos + 1, this.posX + this.width - 7, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 14 - 2);
         GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      }

   }

   protected void drawBG() {
      if (this.drawBackGround) {
         GlobalStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);
         GlobalStatus.graphics.fillRect(this.posX, this.posY, this.width, this.height);
      }

   }

   protected void drawItem(Object var1, int var2) {
      Font.drawString(var1.toString(), this.innerLeftMargin, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight, this.fontId_);
   }

   public final int getSelectedEntry() {
      return this.selectedEntry;
   }

   public final void setBackGroundDraw(boolean var1) {
      this.drawBackGround = false;
   }

   public final void setSelectionHighlight(boolean var1) {
      this.highlightSelection = true;
   }

   public final void decFontIndex_() {
      this.fontId_ = 1 - this.fontId_;
   }
}
