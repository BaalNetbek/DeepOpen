package AbyssEngine;

import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

public abstract class AbstractMesh extends Class_13a3 {
   protected int renderLayer;
   protected Matrix matrix = new Matrix();
   protected Light sunLight = null;
   protected Light sunShine = null;
   

   public AbstractMesh(AbstractMesh var1) {
      super(var1);
      this.renderLayer = var1.renderLayer;
   }

   public AbstractMesh() {
      this.renderLayer = 0;
   }

   public final void setRenderLayer(int var1) {
      this.renderLayer = var1;
   }

   public void appendToRender(Camera var1, Class_db var2) {
      if (this.draw && var1.sub_14a(this.var_19f) != 0) {
         this.matrix = var1.tempTransform.getInverse(this.matrix);
         this.matrix.multiply(this.tempTransform);
         /*
         if (this.light != null) {
             AEGraphics3D.graphics3D.resetLights();
             float arr[] = new float[16];
             this.matrix.scaledToFloatArray(arr);
             Transform tranforma = new Transform();
             tranforma.set(arr);
             AEGraphics3D.graphics3D.addLight(light, tranforma); 
             
             Light ambient = new Light();
             ambient.setIntensity(2.0F);
             ambient.setMode(Light.AMBIENT);
             
             this.matrix.identity();
             this.matrix.scaledToFloatArray(arr);
             tranforma.set(arr);
             AEGraphics3D.graphics3D.addLight(light, tranforma); 
  	 }
         */
         var2.sub_177(this.renderLayer, this);
      }

   }

   public void forceAppendToRender(Camera camera, Class_db var2) {
      if (this.draw) {
         camera.tempTransform.getInverse(this.matrix);
         this.matrix.multiply(this.tempTransform);
         
         if (this.sunLight != null) {
             AEGraphics3D.graphics3D.resetLights();
             float arr[] = new float[16];
             this.matrix.scaledToFloatArray(arr);
             Transform tranforma = new Transform();
             tranforma.set(arr);
             this.sunShine.setColor(Level.maxLight());
             AEGraphics3D.graphics3D.addLight(this.sunShine, tranforma); 
             AEGraphics3D.graphics3D.addLight(this.sunLight, tranforma); 
             
             AEGraphics3D.addAmbientLight();
  	 }
         
         var2.sub_177(this.renderLayer, this);
      }

   }

   public abstract void render();

   public void renderTransparent() {
   }

   public abstract GraphNode sub_2b();

   public static AbstractMesh sub_976unk(int var0, int var1, byte var2) {
      return new Class_4cMesh(0, var1, (byte)2);
   }

   public abstract void setTexture(AbstractTexture var1);

   public abstract void destroy();

   public void sub_181(long var1) {
   }
}
