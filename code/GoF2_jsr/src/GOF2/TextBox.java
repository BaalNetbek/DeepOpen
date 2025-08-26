package GOF2;

import javax.microedition.lcdui.Image;

import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;

public final class TextBox {
    private final int posX;
    private final int posY;
    private final int width;
    private int height;
    int topPadding;
    private String[] rows;
    private int fontSpacingY__;
    private boolean verticalBar;
    private int vBarPercent;
    private String rawText;
    private byte[] faceIds;
    private int topPadding2;
    private boolean hide;
    int font;
    private Image[] faceImgs;
    private final int heightLimit;

    public TextBox(final int var1, final int var2, final int var3, final int var4, final String var5) {
        this.posX = var1;
        this.posY = var2;
        this.width = var3;
        this.height = var4;
        this.heightLimit = var4;
        this.topPadding = 0;
        if (var5 != null) {
            setText(var5);
        }

        this.hide = false;
        this.font = 0;
    }

    public final void zeroTopPadding() {
        this.topPadding = 0;
    }

    public final void setHide(final boolean var1) {
        this.hide = true;
    }

    public final void setFont(final int var1) {
        this.font = var1;
    }

    public final void setText(final String var1) {
        this.rawText = var1;
        if (this.faceIds == null) {
            this.topPadding2 = 0;
        }

        if (var1 != null) {
            if (this.faceIds == null) {
                this.rows = Font.splitToLines(var1, this.width + 3);
            } else {
                this.rows = Font.splitToLines(var1, this.width + 3, 1, ImageFactory.faceWidth + 3, ImageFactory.faceHeight);
            }

            this.fontSpacingY__ = Font.getTotalSpacingY(this.rows) + this.topPadding2;
            this.height = AEMath.min(this.heightLimit, Font.getTotalSpacingY(this.rows) + 13);
            if (this.faceIds != null) {
                this.height = AEMath.max(this.height, ImageFactory.faceHeight + 10);
            }

            this.verticalBar = this.fontSpacingY__ > this.height;
            if (this.verticalBar) {
                if (this.faceIds == null) {
                    this.rows = Font.splitToLines(var1, this.width + 3);
                } else {
                    this.rows = Font.splitToLines(var1, this.width - 3, 1, ImageFactory.faceWidth + 3, ImageFactory.faceHeight);
                }

                this.fontSpacingY__ = Font.getTotalSpacingY(this.rows) + this.topPadding2;
                this.vBarPercent = (int)((float)this.height / (float)this.fontSpacingY__ * this.height) - 1;
            }

        }
    }

    public final int getHeight_() {
        return this.height + Font.getSpacingY() + (this.verticalBar ? 6 : 0);
    }

    public final int getTextHeight_() {
        if (this.rows == null) {
            return 0;
        }
        return this.faceIds == null ? this.rows.length : AEMath.max(this.rows.length, ImageFactory.faceHeight / this.rows.length + 1);
    }

    public final void set(final byte[] var1, final String var2, final boolean var3) {
        this.faceIds = var1;
        Font.splitToLines(var2, this.width - ImageFactory.faceWidth - 2);
        this.faceImgs = ImageFactory.faceFromByteArray(var1);
        this.topPadding2 = 3;
        setText(this.rawText);
    }

    public final void scrollUp(final int var1) {
        if (this.fontSpacingY__ > this.height) {
            this.topPadding -= var1 / 10;
            if (this.topPadding < -(this.fontSpacingY__ - this.height)) {
                this.topPadding = -(this.fontSpacingY__ - this.height);
            }
        }

    }

    public final void scrollDown(final int var1) {
        this.topPadding += var1 / 10;
        if (this.topPadding > 0) {
            this.topPadding = 0;
        }

    }

    public final void draw() {
        GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
        GlobalStatus.graphics.setClip(this.posX - 2, this.posY, this.width + 5, this.height);
        if (this.faceIds != null) {
            ImageFactory.drawChar(this.faceImgs, this.posX, this.posY + this.topPadding + 2, 0);
        }

        if (this.rows != null) {
            if (this.hide) {
                Font.drawLinesAligned(this.rows, this.posX + this.width - 3, this.posY + this.topPadding + this.topPadding2, this.font, Font.RIGHT);
            } else if (this.faceIds == null) {
                Font.drawLines(this.rows, this.posX + 2, this.posY + this.topPadding + this.topPadding2, this.font);
            } else {
                Font.drawLinesWithIndent(this.rows, this.posX + 2, this.posY + this.topPadding + this.topPadding2, this.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight);
            }
        }

        GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
        if (this.verticalBar) {
            final int var1 = this.posY + 2 - (int)((float)this.topPadding / (float)this.fontSpacingY__ * (this.height - 7));
            GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GlobalStatus.graphics.drawLine(this.posX + this.width, this.posY + 2, this.posX + this.width, this.posY + this.height - 4);
            GlobalStatus.graphics.setColor(0xFFFF7700);
            GlobalStatus.graphics.fillRect(this.posX + this.width - 1, var1, 3, this.vBarPercent);
            GlobalStatus.graphics.setColor(0xFFB65500);
            GlobalStatus.graphics.drawLine(this.posX + this.width - 1, var1 + 1, this.posX + this.width - 1, var1 + this.vBarPercent - 2);
            GlobalStatus.graphics.drawLine(this.posX + this.width - 1, var1 + this.vBarPercent - 1, this.posX + this.width - 1, var1 + this.vBarPercent - 1);
            GlobalStatus.graphics.setColor(0xFFFFD300);
            GlobalStatus.graphics.drawLine(this.posX + this.width, var1 + 1, this.posX + this.width, var1 + this.vBarPercent - 2);
            GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
        }

    }
}
