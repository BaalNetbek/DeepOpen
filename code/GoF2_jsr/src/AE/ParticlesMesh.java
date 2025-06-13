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

/**
 * Represents collection of 3D sprites.
 * @author Fishlabs
 *
 */
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
   /**
    * 
    * @param var1 uv cooridinates scaling factor 
    * @param var2 top-right x texture cooridnate (u)	
    * @param var3 top-right y texture cooridnate (v)
    * @param var4 bott-left x texture cooridnate (u)
    * @param var5 bott-left y texture cooridnate (v)
    * @param var6 default particle size
    * @param var7 particles count
    * @param var8 blending mode:
    * 	0 	- replace;
    * 	1,3 - alpha;
    * 	2 	- add;
    */
   public ParticlesMesh(int var1, int var2, int var3, int var4, int var5, int var6, int var7, byte var8) {
      this.blending = var8;
      this.appearance = new Appearance();
      CompositingMode composMode = new CompositingMode();
      switch(var8) {
      case 0:
         composMode.setBlending(CompositingMode.REPLACE);
         composMode.setDepthWriteEnable(true);
         composMode.setDepthTestEnable(true);
         break;
      case 1:
      case 3:
         composMode.setBlending(CompositingMode.ALPHA);
         composMode.setDepthWriteEnable(false);
         composMode.setDepthTestEnable(true);
         break;
      case 2:
         composMode.setBlending(CompositingMode.ALPHA_ADD);
         composMode.setDepthWriteEnable(false);
         composMode.setDepthTestEnable(true);
      }

      this.appearance.setCompositingMode(composMode);
      if (polygonMode == null) {
    	 polygonMode = new PolygonMode();
         polygonMode.setCulling(PolygonMode.CULL_BACK);
         polygonMode.setShading(PolygonMode.SHADE_FLAT);
         polygonMode.setPerspectiveCorrectionEnable(false);
      }

      this.appearance.setPolygonMode(polygonMode);
      this.count = var7;
      this.positions = new int[var7 * 3];
      this.vertexColors = new int[var7];

      for(int i = 0; i < this.vertexColors.length; ++i) {
         this.vertexColors[i] = 0xffffffff;
      }
      // Building a square
      VertexBuffer vBuffer = new VertexBuffer();
      VertexArray vcArr = new VertexArray(4, 3, 1);
      VertexArray uvArr = new VertexArray(4, 2, 1);
      vcArr.set(0, 4, new byte[]{
    		  -1, 1, 0, // top-left
    		  1, 1, 0,  // top-right
    		  1, -1, 0, // bot-right
    		  -1, -1, 0 // bot-left
    		  });
      uvArr.set(0, 4, new byte[]{
    		  (byte)var2, (byte)var3, 
    		  (byte)var4, (byte)var3, 
    		  (byte)var4, (byte)var5,
    		  (byte)var2, (byte)var5
    		  });
      vBuffer.setPositions(vcArr, 1.0F, new float[]{0.0F, 0.0F, 0.0F});
      vBuffer.setTexCoords(0, uvArr, 1.0F / (float)var1, new float[]{0.0F, 0.0F});
      
      int[] tris = new int[]{
    		  0, 2, 1,
    		  3, 2, 0
    		  };
      int[] strips = new int[]{3, 3};
      TriangleStripArray tStrips = new TriangleStripArray(tris, strips);
      
      this.particle = new Mesh(vBuffer, tStrips, this.appearance);
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
         this.matrix = var1.localTransformation.getInverse(this.matrix);
         this.matrix.multiply(this.localTransformation);
         var2.drawNode(this.renderLayer, this);
      }

   }

   public final void forceAppendToRender(Camera var1, Renderer var2) {
      this.appendToRender(var1, var2);
   }

   public final void render() {
      if (this.blending == 0) {
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

   public final void renderTransparent() {
      if (this.blending != 0) {
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

   public final void setTexture(ITexture var1) {
      Texture2D var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage());
      this.appearance.setTexture(0, var2);
      // using just
      // this.appearance.setTexture(0, ((JSRTexture)var1).getTexturesArray()[0]);
      // reduces memory usage a bit
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

         for(int i = 0; i < this.scales.length; ++i) {
            this.scales[i] = (int)this.defualtScale;
         }
      }

      return this.scales;
   }
}
