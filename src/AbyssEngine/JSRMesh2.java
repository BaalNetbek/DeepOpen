package AbyssEngine;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Loader;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;

public final class JSRMesh2 extends AbstractMesh {
   private static Transform var_b = new Transform();
   private static float[] var_96 = new float[16];
   private javax.microedition.m3g.Node var_109;
   private static CompositingMode var_124;

   public JSRMesh2(String var1) {
      String var2 = var1;
      JSRMesh2 var5 = this;

      try {
         Object3D[] var3 = null;
         if (!var2.endsWith(".m3g")) {
            var3 = Loader.load(AEImage.loadFileBytes(var2 + ".m3g"), 0);
         } else {
            var3 = Loader.load(AEImage.loadFileBytes(var2), 0);
         }

         for(int var6 = 0; var6 < var3.length; ++var6) {
            if (var3[var6] instanceof javax.microedition.m3g.Group) {
               var5.var_109 = (javax.microedition.m3g.Node)var3[var6];
               break;
            }
         }
      } catch (Exception var4) {
         this.var_109 = null;
      }

      this.radius = 0;
      if (var_124 == null) {
         (var_124 = new CompositingMode()).setBlending(65);
         var_124.setDepthTestEnable(true);
         var_124.setDepthWriteEnable(false);
      }

   }

   private JSRMesh2(JSRMesh2 var1) {
      this.radius = 0;
      this.var_109 = var1.var_109;
      this.renderLayer = var1.renderLayer;
      this.draw = var1.draw;
   }

   public final void render() {
      this.matrix.scaledToFloatArray(var_96);
      var_96[3] = var_96[7] = var_96[11] = 0.0F;
      var_96[7] = 0.0F;
      var_b.set(var_96);
      AEGraphics3D.graphics3D.render(this.var_109, var_b);
   }

   public final void appendToRender(Camera var1, Class_db var2) {
      if (this.draw) {
         this.matrix = var1.tempTransform.getInverse(this.matrix);
         var2.sub_177(this.renderLayer, this);
      }

   }

   public final GraphNode sub_2b() {
      return new JSRMesh2(this);
   }

   public final void setTexture(AbstractTexture var1) {
      this.sub_64((javax.microedition.m3g.Group)this.var_109, ((JSRTexture)var1).getTexturesArray());
   }

   private void sub_64(javax.microedition.m3g.Group var1, Texture2D[] var2) {
      for(int var3 = 0; var3 < var1.getChildCount(); ++var3) {
         javax.microedition.m3g.Node var4;
         if ((var4 = var1.getChild(var3)) instanceof Mesh) {
            int var5 = ((Mesh)var4).getUserID();

            for(int var6 = 0; var6 < ((Mesh)var4).getSubmeshCount(); ++var6) {
               Appearance var7;
               (var7 = ((Mesh)var4).getAppearance(var6)).setMaterial((Material)null);
               var7.setCompositingMode(var_124);
               if (var7.getTexture(0) != null) {
                  if (var2 != null) {
                     if (var5 < var2.length) {
                        var7.setTexture(0, var2[var5]);
                     } else {
                        var7.setTexture(0, var2[0]);
                     }
                  } else {
                     var7.setTexture(0, (Texture2D)null);
                  }
               }
            }
         } else if (var4 instanceof javax.microedition.m3g.Group) {
            this.sub_64((javax.microedition.m3g.Group)var4, var2);
         }
      }

   }

   public final void destroy() {
      this.var_109 = null;
   }
}
