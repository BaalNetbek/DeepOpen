package GoF2;

import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.ITexture;
import AE.Math.AEVector3D;

public final class Sparks extends AbstractMesh {
   private Impact impact;
   private boolean inactive;
   private boolean unused4a_;

   private Sparks(ITexture var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, byte var10) {
      this.impact = new Impact(var6, var1, 256, var2, var3, var4, var5, var9, var7, var8, (byte)2);
      this.impact.mesh.setRenderLayer(2);
      this.inactive = true;
      this.unused4a_ = var6 > 1;
   }

   public Sparks(ITexture var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this(var1, 33, 225, 63, 255, 10, 700, 100, 500, (byte)2);
   }

   public final void moveTo(int var1, int var2, int var3) {
      this.impact.mesh.moveTo(var1, var2, var3);
   }

   public final void explode(AEVector3D var1) {
      int var4 = var1.z;
      int var3 = var1.y;
      int var2 = var1.x;
      this.impact.mesh.moveTo(var2, var3, var4);
      this.impact.explode();
      this.inactive = false;
   }

   public final void update(long var1) {
      if (!this.inactive) {
         this.impact.update((int)var1);
      }
   }

   public final void render() {
      if (!this.inactive) {
         GlobalStatus.renderer.drawNodeInVF(this.impact.mesh);
      }
   }

   public final GraphNode clone() {
      return null;
   }

   public final void setTexture(ITexture var1) {
   }

   public final void OnRelease() {
      Impact var1;
      if ((var1 = this.impact).mesh != null) {
         var1.mesh.OnRelease();
      }

      var1.mesh = null;
   }
}
