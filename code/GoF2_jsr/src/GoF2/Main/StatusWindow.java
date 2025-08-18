package GOF2.Main;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import AE.AEFile;
import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import GOF2.Achievements;
import GOF2.Layout;
import GOF2.ListItem;

public final class StatusWindow extends HangarList {
	private final Sprite sprite;

	public StatusWindow(final String[] var1, final String var2) {
		super(var1, var2);
		final Image var6 = AEFile.loadImage("/data/interface/medals.png", true);
		this.sprite = new Sprite(var6, 31, 15);
		setRowHeight(17);
		final int[] medals = Achievements.getMedals();
		int achievedCnt = 0;
		if (medals != null) {
			for(int i = 0; i < medals.length; ++i) {
				if (medals[i] > 0) {
					++achievedCnt;
				}
			}

			final ListItem[] var9 = new ListItem[medals.length];

			for(int i = 0; i < medals.length; ++i) {
				var9[i] = new ListItem(i, medals[i]);
			}

			super.setEntries(1, var9);
		}

		this.unused_2_ = GlobalStatus.gameText.getText(63) + ": " + achievedCnt + "/" + Achievements.VALUES.length;
	}

	public final void draw() {
		super.draw();
		if (this.selectedTab == 1) {
			drawFrame();
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
		Font.drawString(this.unused_2_, this.posX + this.width - 3, this.posY + this.height - 13, 1, Font.TOP|Font.RIGHT);
	}

	public final void drawItem(final Object var1, final int var2) {
		final ListItem var3 = (ListItem)var1;
		this.sprite.setFrame(var3.medalTier);
		this.sprite.setPosition(this.innerLeftMargin + 4, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 1);
		this.sprite.paint(GlobalStatus.graphics);
		Layout.drawStringWidthLimited(GlobalStatus.gameText.getText(745 + var3.itemId), this.innerLeftMargin + 4 + 31 + 5, this.itemListPosY + (var2 - this.scrollPos) * this.rowHeight + 2, this.width - this.innerLeftMargin - 31 - 4 - 5 - this.listRightPadding - 2, this.selectedEntry == var2 ? 2 : 1);
	}
}
