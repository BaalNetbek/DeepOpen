package AbyssEngine;

import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

public final class AEGraphics3D extends AbstractGraphics3D {
   public static Graphics3D graphics3D;
   private static Background background;

   public AEGraphics3D() {
      graphics3D = Graphics3D.getInstance();
      Light var1;
      (var1 = new Light()).setIntensity(1.0F);
      var1.setMode(129);
      Light var2;
      (var2 = new Light()).setIntensity(2.0F);
      var2.setMode(128);
      Transform var3 = new Transform();
      Transform var4 = new Transform();
      graphics3D.addLight(var1, var3);
      graphics3D.addLight(var2, var4);
      if (background == null) {
         (background = new Background()).setColorClearEnable(false);
         background.setDepthClearEnable(true);
      }

   }

   public final void bindTarget(Graphics var1) {
      try {
         graphics3D.bindTarget(var1);
      } catch (Exception var2) {
         graphics3D.releaseTarget();
      }
   }

   public final void clear() {
      try {
         graphics3D.clear(background);
      } catch (Exception var1) {
      }
   }

   public final void releaseTarget() {
      try {
         graphics3D.releaseTarget();
      } catch (Exception var1) {
      }
   }
}
