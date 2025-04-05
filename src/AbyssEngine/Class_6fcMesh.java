package AbyssEngine;

public final class Class_6fcMesh extends AbstractMesh {
   private Class_490 var_14;
   private boolean var_39;
   private boolean var_4a;

   private Class_6fcMesh(AbstractTexture var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, byte var10) {
      this.var_14 = new Class_490(var6, var1, 256, var2, var3, var4, var5, var9, var7, var8, (byte)2);
      this.var_14.var_38.setRenderLayer(2);
      this.var_39 = true;
      this.var_4a = var6 > 1;
   }

   public Class_6fcMesh(AbstractTexture var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this(var1, 33, 225, 63, 255, 10, 700, 100, 500, (byte)2);
   }

   public final void moveTo(int var1, int var2, int var3) {
      this.var_14.var_38.moveTo(var1, var2, var3);
   }

   public final void sub_16(AEVector3D var1) {
      int var4 = var1.z;
      int var3 = var1.y;
      int var2 = var1.x;
      this.var_14.var_38.moveTo(var2, var3, var4);
      this.var_14.sub_4b();
      this.var_39 = false;
   }

   public final void sub_181(long var1) {
      if (!this.var_39) {
         this.var_14.sub_a6((int)var1);
      }
   }

   public final void render() {
      if (!this.var_39) {
         GameStatus.renderer.sub_87(this.var_14.var_38);
      }
   }

   public final GraphNode sub_2b() {
      return null;
   }

   public final void setTexture(AbstractTexture var1) {
   }

   public final void destroy() {
      Class_490 var1;
      if ((var1 = this.var_14).var_38 != null) {
         var1.var_38.destroy();
      }

      var1.var_38 = null;
   }
}
