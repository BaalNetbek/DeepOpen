package AbyssEngine;

import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

public final class AEGraphics3D extends AbstractGraphics3D {
   public static Graphics3D graphics3D;
   private static Background background;
   public static Light ambient;
   public static Light omni;
   public static Transform identity = new Transform();
   
   public AEGraphics3D() {
      graphics3D = Graphics3D.getInstance();
      Light var1;
      (var1 = new Light()).setIntensity(2.0F);
      var1.setMode(Light.OMNI);
      Light var2;
      (var2 = new Light()).setIntensity(1.50F);
      var2.setMode(Light.AMBIENT);
      AEGraphics3D.omni = var1;
      AEGraphics3D.ambient = var2;

      Transform var3 = new Transform();
      Transform var4 = new Transform();
      

      graphics3D.addLight(var1, var3);
      graphics3D.addLight(var2, var4);
      if (background == null) {
         (background = new Background()).setColorClearEnable(false);
         background.setDepthClearEnable(true);
      }

   }
   
   public static void setLight(Camera camera, Matrix lighLocalMatrix,  Light light){
	 if (light != null) {
		 Matrix matrix = new Matrix();
		 Transform transform = new Transform();
		 float arr[] = new float[16];
		 camera.tempTransform.getInverse(matrix);
		 matrix.multiply(lighLocalMatrix);
		 matrix.scaledToFloatArray(arr);
		 transform.set(arr);
		 
		 AEGraphics3D.graphics3D.resetLights();
		 AEGraphics3D.graphics3D.addLight(light, transform); 
		 AEGraphics3D.addAmbientLight();
	}
   }
   
   public static void setLights(Camera camera, Matrix lightSrcMatrices [],  Light lights []){
	 if (lights != null) {
	 	try{	
			AEGraphics3D.graphics3D.resetLights();
			Matrix matrix = new Matrix();
			Matrix cameraInv = new Matrix();
			Transform lighLocalTransform = new Transform();
			float arr[] = new float[16];
			camera.tempTransform.getInverse(cameraInv);
			for (int i = 0; i<lights.length; i++){
				 matrix.set(cameraInv);
				 matrix.multiply(lightSrcMatrices[i]);
				 matrix.scaledToFloatArray(arr);
				 lighLocalTransform.set(arr);
				 AEGraphics3D.graphics3D.addLight(lights[i], lighLocalTransform); 
			 }
			 AEGraphics3D.addAmbientLight();
			 }
		catch (Exception e){
			System.out.println(e);
		}
	}
	
   }
   
   public static final void addAmbientLight(){
	 AEGraphics3D.ambient.setColor(Level.skyLight());
	 AEGraphics3D.graphics3D.addLight(AEGraphics3D.ambient, AEGraphics3D.identity);
   }

   public final void bindTarget(Graphics var1) {
      try {
         graphics3D.bindTarget(var1);
      } catch (Exception var2) {
         graphics3D.releaseTarget();
      }
   }

   public final void clear() {
      try {
         graphics3D.clear(background);
      } catch (Exception var1) {
      }
   }

   public final void releaseTarget() {
      try {
         graphics3D.releaseTarget();
      } catch (Exception var1) {
      }
   }
}
