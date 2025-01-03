package AbyssEngine;

public abstract class AbstractMesh extends Class_13a3 {
   protected int flag_;
   protected Matrix matrix = new Matrix();

   public AbstractMesh(AbstractMesh var1) {
      super(var1);
      this.flag_ = var1.flag_;
   }

   public AbstractMesh() {
      this.flag_ = 0;
   }

   public final void setFlag_(int var1) {
      this.flag_ = var1;
   }

   public void sub_11b(Camera var1, Class_db var2) {
      if (this.draw && var1.sub_14a(this.var_19f) != 0) {
         this.matrix = var1.var_14c.sub_8ac(this.matrix);
         this.matrix.multiply(this.var_14c);
         var2.sub_177(this.flag_, this);
      }

   }

   public void sub_b2(Camera var1, Class_db var2) {
      if (this.draw) {
         this.matrix = var1.var_14c.sub_8ac(this.matrix);
         this.matrix.multiply(this.var_14c);
         var2.sub_177(this.flag_, this);
      }

   }

   public abstract void render();

   public void renderTransparent() {
   }

   public abstract GraphNode sub_2b();

   public static AbstractMesh sub_976unk(int var0, int var1, byte var2) {
      return new Class_4cMesh(0, var1, (byte)2);
   }

   public abstract void setTexture(AbstractTexture var1);

   public abstract void destroy();

   public void sub_181(long var1) {
   }
}
