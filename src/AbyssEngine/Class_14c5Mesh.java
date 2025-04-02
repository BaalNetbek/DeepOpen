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

public final class Class_14c5Mesh extends AbstractMesh {
   private static Transform var_63 = new Transform();
   private static AEVector3D var_3b4 = new AEVector3D();
   private Appearance var_3c2;
   private static PolygonMode var_4a8;
   private int var_52c;
   private Mesh var_57b;
   private int[] var_588;
   private int[] var_5c8;
   private int[] someArrOld_;
   private float someArrNew_;
   private byte var_674;

   public Class_14c5Mesh(int var1, int var2, int var3, int var4, int var5, int var6, int var7, byte var8) {
      this.var_674 = var8;
      this.var_3c2 = new Appearance();
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

      this.var_3c2.setCompositingMode(var9);
      if (var_4a8 == null) {
         (var_4a8 = new PolygonMode()).setCulling(160);
         var_4a8.setShading(164);
         var_4a8.setPerspectiveCorrectionEnable(false);
      }

      this.var_3c2.setPolygonMode(var_4a8);
      this.var_52c = var7;
      this.var_588 = new int[var7 * 3];
      this.var_5c8 = new int[var7];

      for(var7 = 0; var7 < this.var_5c8.length; ++var7) {
         this.var_5c8[var7] = -1;
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
      this.var_57b = new Mesh(var16, var12, this.var_3c2);
      this.radius = var6 >> 1;
      this.someArrNew_ = (float)(var6 >> 1);
      this.someArrOld_ = null;
   }

   private Class_14c5Mesh(Class_14c5Mesh var1) {
      this.var_52c = var1.var_52c;
      this.var_588 = new int[3 * this.var_52c];
      this.var_5c8 = new int[this.var_52c];
      System.arraycopy(var1.var_5c8, 0, this.var_5c8, 0, this.var_5c8.length);
      System.arraycopy(var1.var_588, 0, this.var_588, 0, this.var_588.length);
      this.var_57b = var1.var_57b;
      this.radius = var1.radius;
      this.someArrNew_ = var1.someArrNew_;
      this.draw = var1.draw;
      this.renderLayer = var1.renderLayer;
      if (var1.someArrOld_ != null) {
         this.someArrOld_ = new int[this.var_52c];
         System.arraycopy(var1.someArrOld_, 0, this.someArrOld_, 0, this.someArrOld_.length);
      }

   }

   public final GraphNode sub_2b() {
      return new Class_14c5Mesh(this);
   }

   public final void destroy() {
   }

   public final void sub_11b(Camera var1, Class_db var2) {
      if (this.draw) {
         this.matrix = var1.var_14c.sub_8ac(this.matrix);
         this.matrix.multiply(this.var_14c);
         var2.sub_177(this.renderLayer, this);
      }

   }

   public final void sub_b2(Camera var1, Class_db var2) {
      this.sub_11b(var1, var2);
   }

   public final void render() {
      if (this.var_674 == 0) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.var_52c; var1 += 3) {
            var_3b4.set(this.var_588[var1], this.var_588[var1 + 1], this.var_588[var1 + 2]);
            var_3b4 = this.matrix.sub_990(var_3b4);
            var_63.setIdentity();
            var_63.postTranslate((float)var_3b4.x, (float)var_3b4.y, (float)var_3b4.z);
            if (this.someArrOld_ != null) {
               var_63.postScale((float)(this.someArrOld_[var2] >> 1), (float)(this.someArrOld_[var2] >> 1), (float)(this.someArrOld_[var2] >> 1));
            } else {
               var_63.postScale(this.someArrNew_, this.someArrNew_, this.someArrNew_);
            }

            this.var_57b.getVertexBuffer().setDefaultColor(this.var_5c8[var2]);
            AEGraphics3D.graphics3D.render(this.var_57b, var_63);
            ++var2;
         }
      }

   }

   public final void renderTransparent() {
      if (this.var_674 != 0) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.var_52c; var1 += 3) {
            var_3b4.set(this.var_588[var1], this.var_588[var1 + 1], this.var_588[var1 + 2]);
            var_3b4 = this.matrix.sub_990(var_3b4);
            var_63.setIdentity();
            var_63.postTranslate((float)var_3b4.x, (float)var_3b4.y, (float)var_3b4.z);
            if (this.someArrOld_ != null) {
               var_63.postScale((float)(this.someArrOld_[var2] >> 1), (float)(this.someArrOld_[var2] >> 1), (float)(this.someArrOld_[var2] >> 1));
            } else {
               var_63.postScale(this.someArrNew_, this.someArrNew_, this.someArrNew_);
            }

            this.var_57b.getVertexBuffer().setDefaultColor(this.var_5c8[var2]);
            AEGraphics3D.graphics3D.render(this.var_57b, var_63);
            ++var2;
         }
      }

   }

   public final void setTexture(AbstractTexture var1) {
      Texture2D var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage());
      this.var_3c2.setTexture(0, var2);
   }

   public final int[] sub_46() {
      return this.var_588;
   }

   public final int[] sub_53() {
      return this.var_5c8;
   }

   public final int[] updateSomeArray_() {
      if (this.someArrOld_ == null) {
         this.someArrOld_ = new int[this.var_52c];

         for(int var1 = 0; var1 < this.someArrOld_.length; ++var1) {
            this.someArrOld_[var1] = (int)this.someArrNew_;
         }
      }

      return this.someArrOld_;
   }
}
