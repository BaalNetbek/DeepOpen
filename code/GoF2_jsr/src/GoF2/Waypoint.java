package GoF2;

import AE.AbstractMesh;
import AE.Math.AEVector3D;

public final class Waypoint extends KIPlayer {
   public int x;
   public int y;
   public int z;
   public boolean reached_;

   public Waypoint(int var1, int var2, int var3, Route var4) {
      super(0, -1, new Player(2000.0F, 0, 0, 0, 0), (AbstractMesh)null, var1, var2, var3);
      this.activeRoute_ = var4;
      this.player.setActive(false);
      this.x = var1;
      this.y = var2;
      this.z = var3;
      this.targetX = var1;
      this.targetX = var2;
      this.targetZ = var3;
      this.reached_ = false;
   }

   public final AEVector3D getPosition(AEVector3D var1) {
      var1.x = this.x;
      var1.y = this.y;
      var1.z = this.z;
      return var1;
   }

   public final void setActive(boolean var1) {
      this.player.setActive(var1);
   }

   public final void reset() {
      this.reached_ = false;
      this.player.setActive(false);
   }

   public final void update(long var1) {
   }

   public final void appendToRender() {
   }

   public final boolean outerCollide(int var1, int var2, int var3) {
      return false;
   }
}
