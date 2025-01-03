package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class Crosshair {
   private int[] worldPosition;
   private Image image;
   public static AEVector3D worldPos;
   public static AEVector3D screenPos;

   public Crosshair() {
      worldPos = new AEVector3D();
      screenPos = new AEVector3D();

      try {
         String var1 = "/data/interface/hud_crosshair_png24.png";
         this.image = AEImage.loadImage("/data/interface/hud_crosshair_png24.png", true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.worldPosition = new int[3];
   }

   public final void sub_5c(Matrix var1, Camera var2) {
      this.worldPosition[0] = var1.getPositionX() + 5 * var1.getDirectionX();
      this.worldPosition[1] = var1.getPositionY() + 5 * var1.getDirectionY();
      this.worldPosition[2] = var1.getPositionZ() + 5 * var1.getDirectionZ();
      worldPos.set(this.worldPosition[0], this.worldPosition[1], this.worldPosition[2]);
      screenPos.set(worldPos);
      var2.sub_fa(screenPos);
   }

   public final void updatePosition() {
      this.worldPosition = null;
      screenPos = null;
      this.image = null;
   }

   public final void draw() {
      GameStatus.graphics.drawImage(this.image, screenPos.x, screenPos.y, 3);
   }
}
