package AE;

import AE.Math.AEMath;
import AE.PaintCanvas.AEGraphics3D;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

public final class ParticleSystemMesh extends AbstractMesh {
   private static Transform calcTransform = new Transform();
   private static float[] transformValues = new float[16];
   private Appearance appearance;
   private int quadCount;
   private Mesh mesh;
   private VertexBuffer vertexBuffer;
   private VertexArray vertexArray;
   private VertexArray uvArray;
   private TriangleStripArray tStrips;
   private short[] vertices;
   private byte[] uvs;
   private static float[] zeroBias3D = new float[]{0.0F, 0.0F, 0.0F};
   private static float[] zeroBias2D = new float[]{0.0F, 0.0F};
   private int textureWidth;
   private int color = -1;

   public ParticleSystemMesh(int var1, int var2, byte var3) {
      this.resourceId = var1;
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

      this.quadCount = var2;
      this.vertices = new short[var2 * 12];
      this.uvs = new byte[var2 * 8];
      this.vertexBuffer = new VertexBuffer();
      this.vertexArray = new VertexArray(var2 * 4, 3, 2);
      this.uvArray = new VertexArray(var2 * 4, 2, 1);
      this.vertexArray.set(0, var2 * 4, this.vertices);
      this.uvArray.set(0, var2 * 4, this.uvs);
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

   private ParticleSystemMesh(ParticleSystemMesh var1) {
      this.quadCount = var1.quadCount;
      this.mesh = var1.mesh;
      this.vertexBuffer = var1.vertexBuffer;
      this.vertexArray = var1.vertexArray;
      this.uvArray = var1.uvArray;
      this.tStrips = var1.tStrips;
      this.vertices = var1.vertices;
      this.uvs = var1.uvs;
      this.textureWidth = var1.textureWidth;
      this.radius = var1.radius;
      this.draw = var1.draw;
      this.renderLayer = var1.renderLayer;
   }

   public final GraphNode clone() {
      return new ParticleSystemMesh(this);
   }

   public final void OnRelease() {
      this.mesh = null;
      this.vertexBuffer = null;
      this.vertexArray = null;
      this.uvArray = null;
      this.tStrips = null;
      this.vertices = null;
      this.uvs = null;
   }

   public final void render() {
      this.matrix.toFloatArray(transformValues);
      calcTransform.set(transformValues);
      AEGraphics3D.graphics3D.render(this.mesh, calcTransform);
   }

   public final void appendToRender(Camera var1, Renderer var2) {
      if (this.draw) {
         this.matrix = var1.tempTransform.getInverse(this.matrix);
         this.matrix.multiply(this.tempTransform);
         var2.drawNode(this.renderLayer, this);
      }

   }

   public final void setMeshData_(int[] var1, int[] var2) {
      int var3;
      for(var3 = 0; var3 < var1.length; ++var3) {
         this.vertices[var3] = (short)var1[var3];
      }

      this.vertexArray.set(0, 4 * this.quadCount, this.vertices);
      this.mesh.getVertexBuffer().setPositions(this.vertexArray, 1.0F, zeroBias3D);

      for(var3 = 0; var3 < var2.length; ++var3) {
         this.uvs[var3] = (byte)var2[var3];
      }

      this.uvArray.set(0, 4 * this.quadCount, this.uvs);
      this.mesh.getVertexBuffer().setTexCoords(0, this.uvArray, 1.0F / (float)this.textureWidth, zeroBias2D);
      this.mesh.getVertexBuffer().setDefaultColor(this.color);
      this.radius = 0;

      for(var3 = 0; var3 < var1.length; var3 += 3) {
         int var4 = var1[var3] * var1[var3] + var1[var3 + 1] * var1[var3 + 1] + var1[var3 + 2] * var1[var3 + 2];
         if (this.radius < var4) {
            this.radius = var4;
         }
      }

      this.radius = AEMath.sqrt((long)this.radius);
   }

   public final void setTexture(ITexture var1) {
      Texture2D var2;
      (var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage())).setBlending(227);
      var2.setFiltering(208, 210);
      var2.setWrapping(240, 240);
      this.appearance.setTexture(0, var2);
      this.textureWidth = var2.getImage().getWidth();
   }
}
