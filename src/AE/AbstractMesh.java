package AE;

import javax.microedition.m3g.Light;


import AE.Math.Matrix;
import AE.PaintCanvas.AEGraphics3D;
import GoF2.Level;

public abstract class AbstractMesh extends AEGeometry {
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

   public void appendToRender(Camera var1, Renderer var2) {
      if (this.draw && var1.isInViewFrustum(this.boundingSphere) != 0) {
         this.matrix = var1.tempTransform.getInverse(this.matrix);
         try {
//         if (this instanceof JSRMesh) {
//            ((JSRMesh) this).rotateUV(AEGraphics3D.lightInv, this.matrix);
//         }
         }catch (Exception e) {
             e.printStackTrace();
         }
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
         var2.drawNode(this.renderLayer, this);
      }

   }

   public void forceAppendToRender(Camera camera, Renderer var2) {
      if (this.draw) {
	 
	 this.matrix = camera.tempTransform.getInverse(this.matrix);
	 //Matrix lightMatrix = new Matrix(matrix);
         this.matrix.multiply(this.tempTransform);
         

         //this.tempTransform.rotateAroundRight(2048);
         //lightMatrix.multiply(this.tempTransform);
         
         if (this.sunLight != null) {
             //AEGraphics3D.graphics3D.resetLights();
             //float arr[] = new float[16];
             //this.matrix.scaledToFloatArray(arr);
             //Transform tranforma = new Transform();
             //tranforma.set(arr);

             this.sunShine.setColor(Level.starLight());
             //AEGraphics3D.graphics3D.addLight(this.sunShine, tranforma); 
             AEGraphics3D.setLights(camera, this.tempTransform, new Light[]{this.sunShine, this.sunLight}); 
             //GameStatus.renderer.setLight(matrix, this.sunShine); 
             //AEGraphics3D.graphics3D.addLight(this.sunLight, tranforma); 
             
             //AEGraphics3D.addAmbientLight();
  	 }
         
         var2.drawNode(this.renderLayer, this);
      }

   }

   public abstract void render();

   public void renderTransparent() {
   }

   public abstract GraphNode clone();
   
   //public static AbstractMesh newParticlesMesh(int n1, int n2, int n3, int n4, int n5, int n6, int n7, byte, b1)

   public static AbstractMesh newPlaneStrip(int var0, int var1, byte var2) {
      return new ParticleSystemMesh(0, var1, (byte)2);
   }

   public abstract void setTexture(ITexture var1);

   public abstract void OnRelease();

   public void update(long var1) {
   }
   
   public final void setSun (boolean isSun) {
       if (isSun) {
	   this.sunLight = new Light();
           this.sunLight.setIntensity(0.9F);
           this.sunLight.setColor(0xffffff);
           this.sunLight.setMode(Light.DIRECTIONAL);
           
           this.sunShine = new Light();
           this.sunShine.setIntensity(2.0F);
           this.sunShine.setColor(0xffffff);
           this.sunShine.setMode(Light.DIRECTIONAL);
           return;
       }
       this.sunShine = this.sunLight = null;
   }
}
