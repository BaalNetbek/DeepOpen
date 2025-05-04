package AE;

import AE.Math.AEVector3D;
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

public final class ParticlesMesh extends AbstractMesh {
   private static Transform calcTransform = new Transform();
   private static AEVector3D tempPos = new AEVector3D();
   private Appearance appearance;
   private static PolygonMode polygonMode;
   private int fragCount;
   private Mesh fragment;
   private int[] fragPos;
   private int[] fragColors;
   private int[] fragScales;
   private float defualtScale;
   private byte materialType;

   public ParticlesMesh(int var1, int var2, int var3, int var4, int var5, int var6, int var7, byte var8) {
      this.materialType = var8;
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
      this.fragCount = var7;
      this.fragPos = new int[var7 * 3];
      this.fragColors = new int[var7];

      for(var7 = 0; var7 < this.fragColors.length; ++var7) {
         this.fragColors[var7] = -1;
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
      this.fragment = new Mesh(var16, var12, this.appearance);
      this.radius = var6 >> 1;
      this.defualtScale = (float)(var6 >> 1);
      this.fragScales = null;
   }

   private ParticlesMesh(ParticlesMesh var1) {
      this.fragCount = var1.fragCount;
      this.fragPos = new int[3 * this.fragCount];
      this.fragColors = new int[this.fragCount];
      System.arraycopy(var1.fragColors, 0, this.fragColors, 0, this.fragColors.length);
      System.arraycopy(var1.fragPos, 0, this.fragPos, 0, this.fragPos.length);
      this.fragment = var1.fragment;
      this.radius = var1.radius;
      this.defualtScale = var1.defualtScale;
      this.draw = var1.draw;
      this.renderLayer = var1.renderLayer;
      if (var1.fragScales != null) {
         this.fragScales = new int[this.fragCount];
         System.arraycopy(var1.fragScales, 0, this.fragScales, 0, this.fragScales.length);
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
      if (this.materialType == 0) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.fragCount; var1 += 3) {
            tempPos.set(this.fragPos[var1], this.fragPos[var1 + 1], this.fragPos[var1 + 2]);
            tempPos = this.matrix.transformVector(tempPos);
            calcTransform.setIdentity();
            calcTransform.postTranslate((float)tempPos.x, (float)tempPos.y, (float)tempPos.z);
            if (this.fragScales != null) {
               calcTransform.postScale((float)(this.fragScales[var2] >> 1), (float)(this.fragScales[var2] >> 1), (float)(this.fragScales[var2] >> 1));
            } else {
               calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
            }

            this.fragment.getVertexBuffer().setDefaultColor(this.fragColors[var2]);
            AEGraphics3D.graphics3D.render(this.fragment, calcTransform);
            ++var2;
         }
      }

   }

   public final void renderTransparent() {
      if (this.materialType != 0) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.fragCount; var1 += 3) {
            tempPos.set(this.fragPos[var1], this.fragPos[var1 + 1], this.fragPos[var1 + 2]);
            tempPos = this.matrix.transformVector(tempPos);
            calcTransform.setIdentity();
            calcTransform.postTranslate((float)tempPos.x, (float)tempPos.y, (float)tempPos.z);
            if (this.fragScales != null) {
               calcTransform.postScale((float)(this.fragScales[var2] >> 1), (float)(this.fragScales[var2] >> 1), (float)(this.fragScales[var2] >> 1));
            } else {
               calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
            }

            this.fragment.getVertexBuffer().setDefaultColor(this.fragColors[var2]);
            AEGraphics3D.graphics3D.render(this.fragment, calcTransform);
            ++var2;
         }
      }

   }

   public final void setTexture(ITexture var1) {
      Texture2D var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage());
      this.appearance.setTexture(0, var2);
   }

   public final int[] getPositions() {
      return this.fragPos;
   }

   public final int[] getColors() {
      return this.fragColors;
   }

   public final int[] getScales() {
      if (this.fragScales == null) {
         this.fragScales = new int[this.fragCount];

         for(int var1 = 0; var1 < this.fragScales.length; ++var1) {
            this.fragScales[var1] = (int)this.defualtScale;
         }
      }

      return this.fragScales;
   }
}
