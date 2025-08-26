package GOF2.Main;

import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import GOF2.Layout;

public class TabbedWindow_ {
	protected static final int ITEM_HEIGHT = 14;
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	protected String[] tabNames;
	protected Object[][] perTabEntries;
	protected int[] listsLengths;
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

	public TabbedWindow_(final int posX, final int posY, final int width, final int height, final String[] tabNames, final String title) {
		init(posX, posY, width, height, tabNames, title);
		this.selectedTab = 0;
		this.drawBackGround = true;
		this.highlightSelection = true;
	}

	private void init(int posX, int posY, int width, int height, String[] tabNames, final String title) {
		if (posX == 0 && posY == 0 && width == GlobalStatus.screenWidth && height == GlobalStatus.screenHeight) {
			posY = 1;
			posX = 1;
			width -= 3;
			height = height - 4 - ITEM_HEIGHT;
		}

		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.showTabs = true;
		if (tabNames == null) {
			tabNames = new String[]{""};
			this.showTabs = false;
		}

		this.titleBarText = title;
		this.tabNames = tabNames;
		this.perTabEntries = new Object[tabNames.length][200];
		this.listsLengths = new int[tabNames.length];

		for(width = 0; width < this.listsLengths.length; ++width) {
			this.listsLengths[width] = 200;
		}

		this.unused2412_ = 0;
		this.selectedEntry = 0;
		this.selectedTab = 0;
		this.scrollPos = 0;
		this.rowHeight = getRowHeight();
		this.tabHeight = getTabHeight();
		this.innerLeftMargin = posX + 6;
		if (this.showTabs) {
			this.itemListPosY = posY + ITEM_HEIGHT + this.tabHeight;
		} else {
			this.itemListPosY = posY + ITEM_HEIGHT;
		}

		for(width = 0; width < tabNames.length; ++width) {
			this.selectedTab = width;
			updateScroll();
		}

	}

	public final void setRowHeight(final int var1) {
		this.rowHeight = var1;
	}

	public int getRowHeight() {
		return this.rowHeight;
	}

	public int getTabHeight() {
		return 16;
	}

	public final void setEntries(final int var1, final Object[] var2) {
		if (var1 > this.perTabEntries.length - 1) {
			System.out.println("ERROR: Tab " + var1 + " not available!");
		} else {
			this.perTabEntries[var1] = new Object[200];

			for(int i = 0; i < var2.length; ++i) {
				this.perTabEntries[var1][i] = var2[i];
			}

			updateScroll();
		}
	}

	public final Object getSelectedItem() {
		if (this.selectedEntry < 0) {
			this.selectedEntry = 0;
			return null;
		}
		return this.perTabEntries[this.selectedTab] == null ? null : this.perTabEntries[this.selectedTab][this.selectedEntry];
	}

	public final int getCurrentTab() {
		return this.selectedTab;
	}

	public final void setCurrentTab(final int var1) {
		if (var1 < this.perTabEntries.length) {
			this.selectedTab = var1;
			this.selectedEntry = 0;
			this.scrollPos = 0;
			updateScroll();
		}

		draw();
		scrollDown();
		scrollUp();
	}

	public void updateScroll() {
		this.scrollThumbSize = 0;
		this.listRightPadding = 0;
		int var1;
		if (this.perTabEntries[this.selectedTab] != null) {
			for(var1 = 0; var1 < this.perTabEntries[this.selectedTab].length; ++var1) {
				if (this.perTabEntries[this.selectedTab][var1] == null) {
					this.displayedEntriesCount = var1 - 1;
					this.listsLengths[this.selectedTab] = var1;
					break;
				}
			}
		}

		this.displayedEntriesCount = this.listsLengths[this.selectedTab] - 1;

		for(var1 = 0; var1 < this.listsLengths[this.selectedTab]; ++var1) {
			if (this.itemListPosY + (var1 + 1) * this.rowHeight > this.posY + this.height - 12) {
				this.displayedEntriesCount = var1 - 1;
				this.scrollThumbSize = (int)((this.height - this.itemListPosY + this.posY) * ((float)var1 / (float)this.listsLengths[this.selectedTab]));
				break;
			}
		}

		this.scrollThumbPos = (int)((this.height - this.itemListPosY + this.posY) * ((float)this.scrollPos / (float)this.listsLengths[this.selectedTab]));
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

		updateScroll();
	}

	public void scrollDown() {
		if (this.selectedEntry != this.perTabEntries[this.selectedTab].length - 1) {
			if (this.perTabEntries[this.selectedTab][this.selectedEntry + 1] != null) {
				++this.selectedEntry;
				if (this.selectedEntry > this.scrollPos + this.displayedEntriesCount - 1 && this.selectedEntry < this.listsLengths[this.selectedTab] - 1) {
					++this.scrollPos;
				}
			}

			updateScroll();
		}
	}

	public void leftAction() {
		if (this.selectedTab > 0) {
			--this.selectedTab;
			this.selectedEntry = 0;
			this.scrollPos = 0;
		}

		updateScroll();
	}

	public void rightAction() {
		if (this.selectedTab < this.tabNames.length - 1) {
			++this.selectedTab;
			this.selectedEntry = 0;
			this.scrollPos = 0;
		}

		updateScroll();
	}

	public void draw() {
		drawBG();
		Layout.drawFilledTitleBarWindow(this.titleBarText, this.posX, this.posY, this.width, this.height);
		drawTabs();
		drawItems();
		drawScroll();
	}

	public void drawItems() {
		for(int i = this.scrollPos; i < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][i] != null && i < this.scrollPos + this.displayedEntriesCount + 1; ++i) {
			GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
			if (i == this.selectedEntry && this.highlightSelection) {
				GlobalStatus.graphics.fillRect(this.posX + 1, this.itemListPosY + (i - this.scrollPos) * this.rowHeight + 1, this.width - 5, this.rowHeight + 1);
			}

			drawItem(this.perTabEntries[this.selectedTab][i], i);
		}

	}

	protected void drawTabs() {
		if (this.showTabs) {
			GlobalStatus.graphics.setColor(0);
			GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1),
													this.posY + ITEM_HEIGHT,
											      this.posX + (this.width >> 1),
											      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 1,
													this.posY + ITEM_HEIGHT,
											      this.posX + this.width - (this.width >> 1) - 1,
											      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
			GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1,
													this.posY + ITEM_HEIGHT,
											      this.posX + (this.width >> 1) - 1,
											      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) + 1,
													this.posY + ITEM_HEIGHT,
											      this.posX + (this.width >> 1) + 1,
								      			this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1) - 2,
													this.posY + ITEM_HEIGHT,
											      this.posX + this.width - (this.width >> 1) - 2,
											      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			GlobalStatus.graphics.drawLine(this.posX + this.width - (this.width >> 1),
													this.posY + ITEM_HEIGHT,
											      this.posX + this.width - (this.width >> 1),
											      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
			switch (this.selectedTab) {
				case 0:
					GlobalStatus.graphics.drawLine(this.posX + (this.width >> 1) - 1,
															this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
													      this.posX + this.width - 3,
													      this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
					GlobalStatus.graphics.setColor(0);
					GlobalStatus.graphics.fillRect(this.posX + (this.width >> 1) + 2,
															this.posY + ITEM_HEIGHT + 1,
													      (this.width >> 1) - 3,
													      this.posY + ITEM_HEIGHT - 2);
					GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
					GlobalStatus.graphics.fillRect(this.posX + (this.width >> 1) + 3,
															this.posY + ITEM_HEIGHT + 2,
													      (this.width >> 1) - 5,
													      this.posY + ITEM_HEIGHT - 3);
					break;
				case 1:
					GlobalStatus.graphics.drawLine(this.posX + 3, this.posY + ITEM_HEIGHT + ITEM_HEIGHT,
					      this.posX + (this.width >> 1) + 1, this.posY + ITEM_HEIGHT + ITEM_HEIGHT);
					GlobalStatus.graphics.setColor(0);
					GlobalStatus.graphics.fillRect(this.posX + 3, this.posY + ITEM_HEIGHT + 1, (this.width >> 1) - 4,
					      this.posY + ITEM_HEIGHT - 2);
					GlobalStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
					GlobalStatus.graphics.fillRect(this.posX + 4, this.posY + ITEM_HEIGHT + 2, (this.width >> 1) - 6,
					      this.posY + ITEM_HEIGHT - 3);
			}

			Layout.drawMenuPanelCorner(this.posX + 2, this.posY + ITEM_HEIGHT, this.selectedTab == 0);
			Layout.drawMenuPanelCorner(this.posX + (this.width >> 1) + 1, this.posY + ITEM_HEIGHT, this.selectedTab == 1);
			Font.drawString(this.tabNames[0], this.posX + this.width / 4, this.posY + ITEM_HEIGHT + 1,
			      this.selectedTab == 0 ? 2 : 1, Font.TOP|Font.HCENTER);
			Font.drawString(this.tabNames[1], this.posX + this.width - this.width / 4, this.posY + ITEM_HEIGHT + 1,
			      this.selectedTab == 1 ? 2 : 1, Font.TOP|Font.HCENTER);
		}

	}

	protected final void drawScroll() {
		if (this.scrollThumbSize > 0) {
			GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
			GlobalStatus.graphics.drawLine(this.posX + this.width - 7, this.itemListPosY + 2, this.posX + this.width - 7, this.posY + this.height - 3 - ITEM_HEIGHT);
			GlobalStatus.graphics.setColor(0xFFFF7700);
			GlobalStatus.graphics.fillRect(this.posX + this.width - 8, this.itemListPosY + 2 + this.scrollThumbPos, 3, this.scrollThumbSize - ITEM_HEIGHT - 2);
			GlobalStatus.graphics.setColor(0xFFB65500);
			GlobalStatus.graphics.drawLine(this.posX + this.width - 8, this.itemListPosY + 3 + this.scrollThumbPos, this.posX + this.width - 8, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15);
			GlobalStatus.graphics.drawLine(this.posX + this.width - 8, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15, this.posX + this.width - 8 + 1, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - 15);
			GlobalStatus.graphics.setColor(0xFFFFD300);
			GlobalStatus.graphics.drawLine(this.posX + this.width - 7, this.itemListPosY + 2 + this.scrollThumbPos + 1, this.posX + this.width - 7, this.itemListPosY + this.scrollThumbPos + this.scrollThumbSize - ITEM_HEIGHT - 2);
			GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
		}

	}

	protected void drawBG() {
		if (this.drawBackGround) {
			GlobalStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);
			GlobalStatus.graphics.fillRect(this.posX, this.posY, this.width, this.height);
		}

	}

	protected void drawItem(final Object var1, final int var2) {
		Font.drawString(var1.toString(), this.innerLeftMargin, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight, this.fontId_);
	}

	public final int getSelectedEntry() {
		return this.selectedEntry;
	}

	public final void setBackGroundDraw(final boolean var1) {
		this.drawBackGround = false;
	}

	public final void setSelectionHighlight(final boolean var1) {
		this.highlightSelection = true;
	}

	public final void decFontIndex_() {
		this.fontId_ = 1 - this.fontId_;
	}
}
