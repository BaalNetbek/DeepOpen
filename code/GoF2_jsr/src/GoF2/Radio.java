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

    public final void draw(final long nowTime, final long dt, final PlayerEgo playerEgo) {
       if (this.currentMessage == null) {
          this.msgCheckTick += dt;
          if (this.msgCheckTick > 500L) {
              this.msgCheckTick = 0L;
              if (this.messages != null) {
                  for (int i = 0; i < this.messages.length; ++i) {
                      if (this.messages[i].triggered(nowTime, playerEgo)) {
                          final int imageID = this.messages[i].getImageID();
                          if (imageID >= 21) {
										this.face = ImageFactory.createChar(true,
										      (imageID == 24) ? Globals.NIVELIAN :
										      (imageID == 23) ? Globals.TERRAN :
										      (imageID == 21) ? Globals.MIDORIAN 
				                  							 : Globals.VOSSK);
								  }
                          else {
                              this.face = Globals.CHAR_IMAGES[imageID];
                          }
                          this.faceImages = ImageFactory.faceFromByteArray(this.face);
                          this.font = ((imageID == 19) ? 3 : 1);
                          this.rows = Font.splitToLines(
                        		  GlobalStatus.gameText.getText(this.messages[i].getTextID()),
                        		  GlobalStatus.screenWidth - 10,
                        		  this.font,
                        		  ImageFactory.faceWidth + 3,
                        		  ImageFactory.faceHeight + 3
                  		  );
                          this.msgStartTime = nowTime;
                          this.msgTime = this.rows.length * (int)(2000.0f * GlobalStatus.screenWidth / 240.0f) + 1500;
                          return;
                      }
                  }
              }
          }
      } else if (nowTime > this.msgStartTime + 2000L) {
            try {
                this.title = GlobalStatus.gameText.getText(this.currentMessage.getImageID() + 819);
                Layout.drawTitleBarWindow(
               		 this.title, 0, 0,
               		 GlobalStatus.screenWidth - 1,
               		 AEMath.max(ImageFactory.faceHeight + 21, (this.rows.length + 2) * Font.getFontSpacingY()),
               		 true);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            ImageFactory.drawChar(this.faceImages, 5, 17, 0);
            Font.drawLinesWithIndent(this.rows, 7, 17, this.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight + 3);
            if (nowTime > this.msgStartTime + 2000L + this.msgTime) {
                this.msgStartTime = 0L;
                this.currentMessage.finish();
                this.currentMessage = null;
            }
        }

    }
}
