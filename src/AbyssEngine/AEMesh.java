package AbyssEngine;

import java.io.DataInputStream;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Fog;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

public final class AEMesh extends AbstractMesh {
   private static float[] transformArray = new float[16];
   private DataInputStream aemFile;
   private VertexBuffer vertexBuffer;
   private Appearance appearance;
   private TriangleStripArray triangleStripArray;
   private Transform transform;
   private boolean isTransparent;
   private static PolygonMode opaquePMode;
   private static PolygonMode transparentPMode;
   private static CompositingMode compositingMode;

   public AEMesh(String var1, int var2) {
      setupMaterial();
      String var3 = var1;
      AEMesh var11 = this;

      try {
         if (!var3.endsWith(".aem")) {
            var11.aemFile = new DataInputStream(var11.getClass().getResourceAsStream(var3 + ".aem"));
         } else {
            var11.aemFile = new DataInputStream(var11.getClass().getResourceAsStream(var3));
         }

         byte[] var4 = new byte[7];
         var11.aemFile.read(var4, 0, 7);
         int var5;
         int var7;
         int var12;
         if (((var12 = var11.aemFile.readUnsignedByte()) & 16) != 0) {
            int[] var6 = new int[var5 = swapBytes(var11.aemFile.readUnsignedShort())];

            for(var7 = 0; var7 < var5; ++var7) {
               var6[var7] = swapBytes(var11.aemFile.readUnsignedShort());
            }

            int[] var13 = new int[var7 = swapBytes(var11.aemFile.readUnsignedShort())];

            for(int var8 = 0; var8 < var7; ++var8) {
               var13[var8] = swapBytes(var11.aemFile.readUnsignedShort());
            }

            var11.triangleStripArray = new TriangleStripArray(var6, var13);
         }

         short[] var15 = new short[(var5 = swapBytes(var11.aemFile.readUnsignedShort())) * 3];

         for(var7 = 0; var7 < var5 * 3; ++var7) {
            var15[var7] = (short)swapBytes(var11.aemFile.readShort());
         }

         VertexArray var16;
         (var16 = new VertexArray(var15.length / 3, 3, 2)).set(0, var15.length / 3, var15);
         VertexArray var14 = null;
         if ((var12 & 2) != 0) {
            short[] var17 = new short[var15.length / 3 << 1];

            for(int var9 = 0; var9 < var17.length; var9 += 2) {
               var17[var9] = (short)swapBytes(var11.aemFile.readShort());
               var17[var9 + 1] = (short)(255 - (short)swapBytes(var11.aemFile.readShort()));
            }

            (var14 = new VertexArray(var17.length / 2, 2, 2)).set(0, var17.length / 2, var17);
         }

         
         if ((var12 & 4) != 0) {
            short[] var19 = new short[var15.length];

            for(var12 = 0; var12 < var19.length; ++var12) {
               var19[var12] = (short)swapBytes(var11.aemFile.readShort());
            }

            (new VertexArray(var19.length / 3, 3, 2)).set(0, var19.length / 3, var19);
         }

         var11.vertexBuffer = new VertexBuffer();
         var11.vertexBuffer.setPositions(var16, 1.0F, (float[])null);
         var11.vertexBuffer.setNormals((VertexArray)null);
         var11.vertexBuffer.setTexCoords(0, var14, 0.003921569F, (float[])null);
         var11.appearance = new Appearance();
         var11.isTransparent = var11.aemFile.read() != 0;
         if (var11.isTransparent) {
            var11.appearance.setCompositingMode(compositingMode);
            var11.appearance.setPolygonMode(transparentPMode);
         } else {
            var11.appearance.setCompositingMode((CompositingMode)null);
            var11.appearance.setPolygonMode(opaquePMode);
         }

         var11.transform = new Transform();
         var11.transform.setIdentity();
         var11.aemFile.close();
         System.gc();
      } catch (Exception var10) {
         System.out.println("Error loading aemesh = " + var1);
         var10.printStackTrace();
      }

      this.radius = var2;
   }

   private AEMesh(AEMesh var1) {
      super(var1);
      setupMaterial();
      this.vertexBuffer = var1.vertexBuffer;
      this.triangleStripArray = var1.triangleStripArray;
      this.transform = var1.transform;
      this.appearance = var1.appearance;
      this.isTransparent = var1.isTransparent;
      this.renderLayer = var1.renderLayer;
      this.draw = var1.draw;
      this.radius = var1.radius;
      this.resourceId = var1.resourceId;
   }

   private static void setupMaterial() {
      if (opaquePMode == null) {
         (opaquePMode = new PolygonMode()).setCulling(160);
         opaquePMode.setShading(164);
         opaquePMode.setPerspectiveCorrectionEnable(false);
         opaquePMode.setLocalCameraLightingEnable(false);
         opaquePMode.setTwoSidedLightingEnable(false);
         opaquePMode.setWinding(168);
      }

      if (transparentPMode == null) {
         (transparentPMode = new PolygonMode()).setCulling(162);
         transparentPMode.setShading(164);
         transparentPMode.setPerspectiveCorrectionEnable(false);
      }

      if (compositingMode == null) {
         (compositingMode = new CompositingMode()).setBlending(64);
         compositingMode.setDepthTestEnable(true);
         compositingMode.setDepthWriteEnable(false);
      }

   }

   public final void setTexture(AbstractTexture var1) {
      Texture2D[] var2 = ((JSRTexture)var1).getTexturesArray();
      this.appearance.setTexture(0, var2[0]);
   }

   public final GraphNode sub_2b() {
      return new AEMesh(this);
   }

   public final void destroy() {
      this.aemFile = null;
      this.matrix = null;
      this.meshGroup = null;
      this.parent = null;
      this.currentTransform = null;
      this.tempTransform = null;
      this.var_19f = null;
      if (this.appearance != null) {
         this.appearance.setCompositingMode((CompositingMode)null);
         this.appearance.setFog((Fog)null);
         this.appearance.setMaterial((Material)null);
         this.appearance.setPolygonMode((PolygonMode)null);
         this.appearance.setTexture(0, (Texture2D)null);
         this.appearance.setUserID(0);
         this.appearance = null;
      }

      this.vertexBuffer = null;
      this.appearance = null;
      this.triangleStripArray = null;
      this.transform = null;
      System.gc();
   }

   public final void render() {
      if (!this.isTransparent) {
         this.matrix.scaledToFloatArray(transformArray);
         this.transform.set(transformArray);
         if (this.vertexBuffer != null && this.triangleStripArray != null && this.appearance != null && this.transform != null && AEGraphics3D.graphics3D != null) {
            AEGraphics3D.graphics3D.render(this.vertexBuffer, this.triangleStripArray, this.appearance, this.transform);
         }
      }

   }

   public final void renderTransparent() {
      if (this.isTransparent) {
         this.matrix.scaledToFloatArray(transformArray);
         this.transform.set(transformArray);
         if (this.vertexBuffer != null && this.triangleStripArray != null && this.appearance != null && this.transform != null && AEGraphics3D.graphics3D != null) {
            AEGraphics3D.graphics3D.render(this.vertexBuffer, this.triangleStripArray, this.appearance, this.transform);
         }
      }

   }

   private static int swapBytes(int var0) {
      return (var0 & 255) << 8 | var0 >> 8 & 255;
   }
}
