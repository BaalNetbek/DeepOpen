package AbyssEngine;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

public final class Class_4cMesh extends AbstractMesh {
   private static Transform var_20 = new Transform();
   private static float[] var_2b = new float[16];
   private Appearance appearance;
   private int var_b7;
   private Mesh mesh;
   private VertexBuffer vertexBuffer;
   private VertexArray vertexArray;
   private VertexArray uvArray;
   private TriangleStripArray tStrips;
   private short[] var_237;
   private byte[] var_297;
   private static float[] zeroBias3D = new float[]{0.0F, 0.0F, 0.0F};
   private static float[] zeroBias2D = new float[]{0.0F, 0.0F};
   private int var_308;
   private int var_316 = -1;

   public Class_4cMesh(int var1, int var2, byte var3) {
      this.var_1fa = var1;
      if (this.appearance == null) {
         this.appearance = new Appearance();
         if (var3 != 0) {
            CompositingMode var5 = new CompositingMode();
            switch(var3) {
            case 1:
            case 3:
               var5.setBlending(64);
               break;
            case 2:
               var5.setBlending(65);
            }

            var5.setDepthWriteEnable(false);
            var5.setDepthTestEnable(true);
            this.appearance.setCompositingMode(var5);
         }

         PolygonMode var6;
         (var6 = new PolygonMode()).setCulling(162);
         var6.setShading(164);
         var6.setPerspectiveCorrectionEnable(false);
         this.appearance.setPolygonMode(var6);
      }

      this.var_b7 = var2;
      this.var_237 = new short[var2 * 12];
      this.var_297 = new byte[var2 * 8];
      this.vertexBuffer = new VertexBuffer();
      this.vertexArray = new VertexArray(var2 * 4, 3, 2);
      this.uvArray = new VertexArray(var2 * 4, 2, 1);
      this.vertexArray.set(0, var2 * 4, this.var_237);
      this.uvArray.set(0, var2 * 4, this.var_297);
      this.vertexBuffer.setPositions(this.vertexArray, 1.0F, zeroBias3D);
      this.vertexBuffer.setTexCoords(0, this.uvArray, 0.00390625F, zeroBias2D);
      int[] var7 = new int[var2 * 6];
      int var8 = 0;

      for(int var4 = 0; var4 < var7.length; var8 += 4) {
         var7[var4] = var8;
         var7[var4 + 1] = var8 + 2;
         var7[var4 + 2] = var8 + 1;
         var7[var4 + 3] = var8 + 3;
         var7[var4 + 4] = var8 + 2;
         var7[var4 + 5] = var8;
         var4 += 6;
      }

      int[] var9 = new int[var2 * 2];

      for(var2 = 0; var2 < var9.length; ++var2) {
         var9[var2] = 3;
      }

      this.tStrips = new TriangleStripArray(var7, var9);
      this.mesh = new Mesh(this.vertexBuffer, this.tStrips, this.appearance);
   }

   private Class_4cMesh(Class_4cMesh var1) {
      this.var_b7 = var1.var_b7;
      this.mesh = var1.mesh;
      this.vertexBuffer = var1.vertexBuffer;
      this.vertexArray = var1.vertexArray;
      this.uvArray = var1.uvArray;
      this.tStrips = var1.tStrips;
      this.var_237 = var1.var_237;
      this.var_297 = var1.var_297;
      this.var_308 = var1.var_308;
      this.var_1ef = var1.var_1ef;
      this.draw = var1.draw;
      this.flag_ = var1.flag_;
   }

   public final GraphNode sub_2b() {
      return new Class_4cMesh(this);
   }

   public final void destroy() {
      this.mesh = null;
      this.vertexBuffer = null;
      this.vertexArray = null;
      this.uvArray = null;
      this.tStrips = null;
      this.var_237 = null;
      this.var_297 = null;
   }

   public final void render() {
      this.matrix.scaledToFloatArray(var_2b);
      var_20.set(var_2b);
      AEGraphics3D.graphics3D.render(this.mesh, var_20);
   }

   public final void sub_11b(Camera var1, Class_db var2) {
      if (this.draw) {
         this.matrix = var1.var_14c.sub_8ac(this.matrix);
         this.matrix.multiply(this.var_14c);
         var2.sub_177(this.flag_, this);
      }

   }

   public final void sub_176(int[] var1, int[] var2) {
      int var3;
      for(var3 = 0; var3 < var1.length; ++var3) {
         this.var_237[var3] = (short)var1[var3];
      }

      this.vertexArray.set(0, 4 * this.var_b7, this.var_237);
      this.mesh.getVertexBuffer().setPositions(this.vertexArray, 1.0F, zeroBias3D);

      for(var3 = 0; var3 < var2.length; ++var3) {
         this.var_297[var3] = (byte)var2[var3];
      }

      this.uvArray.set(0, 4 * this.var_b7, this.var_297);
      this.mesh.getVertexBuffer().setTexCoords(0, this.uvArray, 1.0F / (float)this.var_308, zeroBias2D);
      this.mesh.getVertexBuffer().setDefaultColor(this.var_316);
      this.var_1ef = 0;

      for(var3 = 0; var3 < var1.length; var3 += 3) {
         int var4 = var1[var3] * var1[var3] + var1[var3 + 1] * var1[var3 + 1] + var1[var3 + 2] * var1[var3 + 2];
         if (this.var_1ef < var4) {
            this.var_1ef = var4;
         }
      }

      this.var_1ef = AEMath.sqrt((long)this.var_1ef);
   }

   public final void setTexture(AbstractTexture var1) {
      Texture2D var2;
      (var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage())).setBlending(227);
      var2.setFiltering(208, 210);
      var2.setWrapping(240, 240);
      this.appearance.setTexture(0, var2);
      this.var_308 = var2.getImage().getWidth();
   }
}
