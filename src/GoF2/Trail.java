package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.ParticleSystemMesh;

public final class Trail {
   private static int[] UV = new int[]{80, 255, 95, 254, 32, 240, 63, 239};
   private int width = 60;
   private int[] vertices;
   private int[] tempVerts;
   private int[] uvs;
   private AbstractMesh mesh;
   private AEVector3D sourcePos = new AEVector3D();
   private boolean stop = false;

   public Trail(int var1) {
      int var2 = var1 == 0 ? 16 : 13;
      this.mesh = AbstractMesh.newPlaneStrip(0, var2, (byte)2);
      this.mesh.setTexture(AEResourceManager.getTextureResource(1));
      this.mesh.setRenderLayer(2);
      this.tempVerts = new int[var2 * 12];
      this.vertices = new int[var2 * 12];
      int var3 = var1 << 2;
      int var4 = UV[var3 + 1];
      int var5 = UV[var3 + 3];
      this.uvs = new int[var2 * 8];

      for(var2 = 0; var2 < this.uvs.length; var2 += 8) {
         this.uvs[var2] = UV[var3];
         this.uvs[var2 + 1] = var4;
         this.uvs[var2 + 2] = UV[var3 + 2];
         this.uvs[var2 + 3] = var4;
         this.uvs[var2 + 4] = UV[var3 + 2];
         this.uvs[var2 + 5] = var5;
         this.uvs[var2 + 6] = UV[var3];
         this.uvs[var2 + 7] = var5;
         if (var1 == 1) {
            --var4;
            --var5;
         } else {
            var4 -= 2;
            var5 -= 2;
         }
      }

   }

   public final void OnRelease() {
      this.mesh.OnRelease();
      this.mesh = null;
   }

   public final void setWidth(int var1) {
      this.width = var1;
   }

   public final void update(AEVector3D var1) {
      if (this.stop) {
         this.sourcePos.set(var1);
         this.stop = false;
      }

      int var8 = this.sourcePos.z;
      int var7 = this.sourcePos.y;
      int var6 = this.sourcePos.x;
      int var5 = var1.z;
      int var4 = var1.y;
      int var3 = var1.x;
      Trail var2 = this;
      this.tempVerts[0] = var3 - this.width;
      this.tempVerts[1] = var4;
      this.tempVerts[2] = var5;
      this.tempVerts[3] = var3 + this.width;
      this.tempVerts[4] = var4;
      this.tempVerts[5] = var5;
      this.tempVerts[6] = var6 + this.width;
      this.tempVerts[7] = var7;
      this.tempVerts[8] = var8;
      this.tempVerts[9] = var6 - this.width;
      this.tempVerts[10] = var7;
      this.tempVerts[11] = var8;

      for(var6 = this.tempVerts.length - 1; var6 >= 23; var6 -= 12) {
         var2.tempVerts[var6] = var2.tempVerts[var6 - 12];
         var2.tempVerts[var6 - 1] = var2.tempVerts[var6 - 13];
         var2.tempVerts[var6 - 2] = var2.tempVerts[var6 - 14];
         var2.tempVerts[var6 - 3] = var2.tempVerts[var6 - 15];
         var2.tempVerts[var6 - 4] = var2.tempVerts[var6 - 16];
         var2.tempVerts[var6 - 5] = var2.tempVerts[var6 - 17];
         var2.tempVerts[var6 - 6] = var2.tempVerts[var6 - 18];
         var2.tempVerts[var6 - 7] = var2.tempVerts[var6 - 19];
         var2.tempVerts[var6 - 8] = var2.tempVerts[var6 - 20];
         var2.tempVerts[var6 - 9] = var2.tempVerts[var6 - 21];
         var2.tempVerts[var6 - 10] = var2.tempVerts[var6 - 22];
         var2.tempVerts[var6 - 11] = var2.tempVerts[var6 - 23];
      }

      for(var6 = 0; var6 < var2.tempVerts.length; var6 += 3) {
         var2.vertices[var6] = var2.tempVerts[var6] - var3;
         var2.vertices[var6 + 1] = var2.tempVerts[var6 + 1] - var4;
         var2.vertices[var6 + 2] = var2.tempVerts[var6 + 2] - var5;
      }

      var2.mesh.moveTo(var3, var4, var5);
      ((ParticleSystemMesh)var2.mesh).setMeshData_(var2.vertices, var2.uvs);
      this.sourcePos.set(var1);
   }

   public final void reset(AEVector3D var1) {
      for(int var2 = 0; var2 < this.tempVerts.length; var2 += 3) {
         this.tempVerts[var2] = var1.x;
         this.tempVerts[var2 + 1] = var1.y;
         this.tempVerts[var2 + 2] = var1.z;
      }

      this.sourcePos.set(var1);
      this.stop = true;
   }

   public final void render() {
      GlobalStatus.renderer.drawNodeInVF(this.mesh);
   }
}
