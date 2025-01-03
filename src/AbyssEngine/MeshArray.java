package AbyssEngine;

public final class MeshArray {
   private AbstractMesh[] meshes = null;
   private int size = 0;

   public final void append(AbstractMesh var1) {
      if (this.meshes == null) {
         this.meshes = new AbstractMesh[1];
         this.meshes[0] = var1;
         this.size = 1;
      } else if (this.size == this.meshes.length) {
         AbstractMesh[] var2 = new AbstractMesh[this.meshes.length + 1];
         System.arraycopy(this.meshes, 0, var2, 0, this.meshes.length);
         var2[this.meshes.length] = var1;
         this.meshes = var2;
         ++this.size;
      } else {
         this.meshes[this.size] = var1;
         ++this.size;
      }
   }

   public final void reset() {
      this.size = 0;
   }

   public final void sub_4a(long var1) {
      for(int var3 = 0; var3 < this.size; ++var3) {
         this.meshes[var3].sub_181(var1);
      }

   }

   public final void render() {
      int var1;
      for(var1 = 0; var1 < this.size; ++var1) {
         this.meshes[var1].render();
      }

      for(var1 = 0; var1 < this.size; ++var1) {
         this.meshes[var1].renderTransparent();
      }

   }
}
