package GoF2;

import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import javax.microedition.lcdui.Image;

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

   public final void setMessages(RadioMessage[] var1) {
      this.messages = var1;
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2].setRadio(this);
         }
      }

   }

   public final void setCurrentMessage(RadioMessage var1) {
      this.currentMessage = var1;
   }

   public final RadioMessage getMessageFromQueue(int var1) {
      return this.messages[var1];
   }

   public final void showMessage() {
      this.msgCheckTick = 501L;
   }

   public final void draw(long var1, long var3, PlayerEgo var5) {
      int var13;
      if (this.currentMessage == null) {
         this.msgCheckTick += var3;
         if (this.msgCheckTick > 500L) {
            this.msgCheckTick = 0L;
            PlayerEgo var2 = var5;
            long var8 = var1;
            Radio var11 = this;
            if (this.messages == null) {
               return;
            }

            var13 = 0;

            while(true) {
               if (var13 >= var11.messages.length) {
                  return;
               }

               if (var11.messages[var13].triggered(var8, var2)) {
                  int var12;
                  if ((var12 = var11.messages[var13].getImageID()) >= 21) {
                     int var4 = var12 == 24 ? 2 : (var12 == 23 ? 0 : (var12 == 21 ? 3 : 1));
                     var11.face = ImageFactory.createChar(true, var4);
                  } else {
                     var11.face = Globals.CHAR_IMAGES[var12];
                  }

                  var11.faceImages = ImageFactory.faceFromByteArray(var11.face);
                  var11.font = var12 == 19 ? 3 : 1;
                  var11.rows = Font.splitToLines(GlobalStatus.gameText.getText(var11.messages[var13].getTextID()), GlobalStatus.screenWidth - 10, var11.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight + 3);
                  var11.msgStartTime = var8;
                  var11.msgTime = var11.rows.length * (int)(2000.0F * (float)GlobalStatus.screenWidth / 240.0F) + 1500;
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
         } catch (Exception var10) {
            var10.printStackTrace();
         }

         ImageFactory.drawChar(this.faceImages, 5, 17, 0);
         Font.drawLinesWithIndent(this.rows, 7, 17, this.font, ImageFactory.faceWidth + 3, ImageFactory.faceHeight + 3);
         if (var1 > this.msgStartTime + 2000L + (long)this.msgTime) {
            this.msgStartTime = 0L;
            this.currentMessage.finish();
            this.currentMessage = null;
         }
      }

   }
}
