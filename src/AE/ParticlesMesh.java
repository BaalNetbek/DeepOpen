package AE;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

import AE.Math.AEVector3D;
import AE.PaintCanvas.AEGraphics3D;

public final class ParticlesMesh extends AbstractMesh {
   private static Transform calcTransform = new Transform();
   private static AEVector3D tempPos = new AEVector3D();
   private Appearance appearance;
   private static PolygonMode polygonMode;
   private int count;
   private Mesh particle;
   private int[] positions;
   private int[] vertexColors;
   private int[] scales;
   private float defualtScale;
   private byte blending;

   public ParticlesMesh(int var1, int var2, int var3, int var4, int var5, int var6, int var7, byte var8) {
      this.blending = var8;
      this.appearance = new Appearance();
      CompositingMode var9 = new CompositingMode();
      switch(var8) {
      case 0:
         var9.setBlending(68);
         var9.setDepthWriteEnable(true);
         var9.setDepthTestEnable(true);
         break;
      case 1:
      case 3:
         var9.setBlending(64);
         var9.setDepthWriteEnable(false);
         var9.setDepthTestEnable(true);
         break;
      case 2:
         var9.setBlending(65);
         var9.setDepthWriteEnable(false);
         var9.setDepthTestEnable(true);
      }

      this.appearance.setCompositingMode(var9);
      if (polygonMode == null) {
         (polygonMode = new PolygonMode()).setCulling(160);
         polygonMode.setShading(164);
         polygonMode.setPerspectiveCorrectionEnable(false);
      }

      this.appearance.setPolygonMode(polygonMode);
      this.count = var7;
      this.positions = new int[var7 * 3];
      this.vertexColors = new int[var7];

      for(var7 = 0; var7 < this.vertexColors.length; ++var7) {
         this.vertexColors[var7] = -1;
      }

      VertexBuffer var16 = new VertexBuffer();
      VertexArray var17 = new VertexArray(4, 3, 1);
      VertexArray var18 = new VertexArray(4, 2, 1);
      byte[] var10 = new byte[]{-1, 1, 0, 1, 1, 0, 1, -1, 0, -1, -1, 0};
      var17.set(0, 4, var10);
      byte[] var13 = new byte[]{(byte)var2, (byte)var3, (byte)var4, (byte)var3, (byte)var4, (byte)var5, (byte)var2, (byte)var5};
      var18.set(0, 4, var13);
      float[] var14 = new float[]{0.0F, 0.0F, 0.0F};
      var16.setPositions(var17, 1.0F, var14);
      var14 = new float[]{0.0F, 0.0F};
      var16.setTexCoords(0, var18, 1.0F / (float)var1, var14);
      int[] var11 = new int[]{0, 2, 1, 3, 2, 0};
      int[] var15 = new int[]{3, 3};
      TriangleStripArray var12 = new TriangleStripArray(var11, var15);
      this.particle = new Mesh(var16, var12, this.appearance);
      this.radius = var6 >> 1;
      this.defualtScale = (float)(var6 >> 1);
      this.scales = null;
   }

   private ParticlesMesh(ParticlesMesh var1) {
      this.count = var1.count;
      this.positions = new int[3 * this.count];
      this.vertexColors = new int[this.count];
      System.arraycopy(var1.vertexColors, 0, this.vertexColors, 0, this.vertexColors.length);
      System.arraycopy(var1.positions, 0, this.positions, 0, this.positions.length);
      this.particle = var1.particle;
      this.radius = var1.radius;
      this.defualtScale = var1.defualtScale;
      this.draw = var1.draw;
      this.renderLayer = var1.renderLayer;
      if (var1.scales != null) {
         this.scales = new int[this.count];
         System.arraycopy(var1.scales, 0, this.scales, 0, this.scales.length);
      }

   }

   public final GraphNode clone() {
      return new ParticlesMesh(this);
   }

   public final void OnRelease() {
   }

   public final void appendToRender(Camera var1, Renderer var2) {
      if (this.draw) {
         this.matrix = var1.tempTransform.getInverse(this.matrix);
         this.matrix.multiply(this.tempTransform);
         var2.drawNode(this.renderLayer, this);
      }

   }

   public final void forceAppendToRender(Camera var1, Renderer var2) {
      this.appendToRender(var1, var2);
   }

   public final void render() {
      if (this.blending == 0) {
         int var1 = 0;

         for(int i = 0; i < this.count; var1 += 3) {
            tempPos.set(this.positions[var1], this.positions[var1 + 1], this.positions[var1 + 2]);
            tempPos = this.matrix.transformVector(tempPos);
            calcTransform.setIdentity();
            calcTransform.postTranslate((float)tempPos.x, (float)tempPos.y, (float)tempPos.z);
            if (this.scales != null) {
               calcTransform.postScale((float)(this.scales[i] >> 1), (float)(this.scales[i] >> 1), (float)(this.scales[i] >> 1));
            } else {
               calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
            }

            this.particle.getVertexBuffer().setDefaultColor(this.vertexColors[i]);
            AEGraphics3D.graphics3D.render(this.particle, calcTransform);
            ++i;
         }
      }

   }

   public final void renderTransparent() {
      if (this.blending != 0) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.count; var1 += 3) {
            tempPos.set(this.positions[var1], this.positions[var1 + 1], this.positions[var1 + 2]);
            tempPos = this.matrix.transformVector(tempPos);
            calcTransform.setIdentity();
            calcTransform.postTranslate((float)tempPos.x, (float)tempPos.y, (float)tempPos.z);
            if (this.scales != null) {
               calcTransform.postScale((float)(this.scales[var2] >> 1), (float)(this.scales[var2] >> 1), (float)(this.scales[var2] >> 1));
            } else {
               calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
            }

            this.particle.getVertexBuffer().setDefaultColor(this.vertexColors[var2]);
            AEGraphics3D.graphics3D.render(this.particle, calcTransform);
            ++var2;
         }
      }

   }

   public final void setTexture(ITexture var1) {
      Texture2D var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage());
      this.appearance.setTexture(0, var2);
   }

   public final int[] getPositions() {
      return this.positions;
   }

   public final int[] getColors() {
      return this.vertexColors;
   }

   public final int[] getScales() {
      if (this.scales == null) {
         this.scales = new int[this.count];

         for(int var1 = 0; var1 < this.scales.length; ++var1) {
            this.scales[var1] = (int)this.defualtScale;
         }
      }

      return this.scales;
   }
}
