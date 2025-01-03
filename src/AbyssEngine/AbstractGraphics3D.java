package AbyssEngine;

import javax.microedition.lcdui.Graphics;

public abstract class AbstractGraphics3D {
   public abstract void bindTarget(Graphics var1);

   public abstract void releaseTarget();

   public abstract void clear();
}
