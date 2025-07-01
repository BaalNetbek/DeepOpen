package GoF2;

import javax.microedition.lcdui.Image;

import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
/** 
 * Manages radio messages displayed while in MGame that don't pause the game.
 * @author fishlabs
 *
 */
public final class Radio {
    private RadioMessage[] messages;
    private RadioMessage currentMessage;
    private String[] rows;
    private byte[] face;
    private long msgStartTime;
    private int msgTime;
    private String title;
    private long msgCheckTick;
    private Image[] faceImages;
    private int font;

    public final void OnRelease() {
        this.messages = null;
        this.currentMessage = null;
    }

    public final void setMessages(final RadioMessage[] var1) {
        this.messages = var1;
        if (var1 != null) {
            for(int i = 0; i < var1.length; ++i) {
                var1[i].setRadio(this);
            }
        }

    }

    public final void setCurrentMessage(final RadioMessage var1) {
        this.currentMessage = var1;
    }

    public final RadioMessage getMessageFromQueue(final int var1) {
        return this.messages[var1];
    }

    public final void showMessage() {
        this.msgCheckTick = 501L;
    }
    //refactor
    public final void draw(final long var1, final long var3, final PlayerEgo var5) {
        int var13;
        if (this.currentMessage == null) {
            this.msgCheckTick += var3;
            if (this.msgCheckTick > 500L) {
                this.msgCheckTick = 0L;
                final long var8 = var1;
                if (this.messages == null) {
                    return;
                }

                var13 = 0;

                while(true) {
                    if (var13 >= this.messages.length) {
                        return;
                    }

                    if (this.messages[var13].triggered(var8, var5)) {
                        int var12 = this.messages[var13].getImageID();
                        if (var12 >= 21) {
                            final int var4 = var12 == 24 ? 2 : var12 == 23 ? 0 : var12 == 21 ? 3 : 1;
                            this.face = ImageFactory.createChar(true, var4);
                        } else {
                            this.face = Globals.CHAR_IMAGES[var12];
                        }

                        this.faceImages = ImageFactory.faceFromByteArray(this.face);
                        this.font = var12 == 19 ? 3 : 1;
                        this.rows = Font.splitToLines(GlobalStatus.gameText.getText(this.messages[var13].getTextID()), GlobalStatus.screenWidth - 10, this.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight + 3);
                        this.msgStartTime = var8;
                        this.msgTime = this.rows.length * (int)(2000.0F * GlobalStatus.screenWidth / 240.0F) + 1500;
                        break;
                    }

                    ++var13;
                }
            }
        } else if (var1 > this.msgStartTime + 2000L) {
            try {
                var13 = this.currentMessage.getImageID();
                this.title = GlobalStatus.gameText.getText(var13 + 819);
                Layout.drawTitleBarWindow(this.title, 0, 0, GlobalStatus.screenWidth - 1, AEMath.max(ImageFactory.faceHeight + 21, (this.rows.length + 2) * Font.getFontSpacingY()), true);
            } catch (final Exception var10) {
                var10.printStackTrace();
            }

            ImageFactory.drawChar(this.faceImages, 5, 17, 0);
            Font.drawLinesWithIndent(this.rows, 7, 17, this.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight + 3);
            if (var1 > this.msgStartTime + 2000L + this.msgTime) {
                this.msgStartTime = 0L;
                this.currentMessage.finish();
                this.currentMessage = null;
            }
        }

    }
}
