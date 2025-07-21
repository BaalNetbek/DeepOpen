package GoF2;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

import AE.AEFile;
import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.CameraControllerGroup;
import AE.GlobalStatus;
import AE.LensFlareFX;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class StarSystem {
   private final int[] astronomicalObjPlacings = new int[]{-4616, -10639, -13667, -2949, -17362, 7030, -398, -7882, 15401, 7177, 3255, -16472, 12676, 6461, -10634, -10046, 12526, -7558, -10697, -13537, -5158, 18625, 4517, -684, -10685, -11195, 8126, 5693, -4063, 16472, 3209, 11007, 13435, 9044, -11172, 10999, 1211, -19330, 1470, -9770, 4503, 16022, -7591, 6356, -16362, 5962, 16281, -9492, 11976, -9221, -8374, 11035, 9992, 9033, -16959, -5712, -3570, -12936, 12109, 3078};
   private final int[] planetSmallImages = new int[]{5, 1, 2, 3, 0, 2, 4, 5, 4, 4, 1, 5, 2, 3, 2, 3, 2, 2, 2, 5};
   private AbstractMesh[] starAndPlanets;
   private CameraControllerGroup cameraControler;
   private AEVector3D tempVec;
   private LensFlareFX lensFlare;
   private Sprite sun;
   private Sprite planet;
   private Image[] localPlanetsImgs;
   private KIPlayer[] localPlanets;
   private AbstractMesh[] nebulaPivots;
   private Image[] nebulaImgs;
   private boolean[] occupiedNebulaPos;
   private boolean[] occupiedAstroObjPos;
   public static int currentPlanetEnumIndex;
   private boolean inAlienSpace;
 //  private Light light;
 //  private int switcher;
   Matrix transf;

   public StarSystem() {
      // this.switcher = 0;
      // this.light = new Light();
      // this.light.setIntensity(2.0F);

      // this.light.setMode(Light.OMNI);
      new Transform();
      new Matrix();
      new Matrix();
      this.cameraControler = new CameraControllerGroup();
      this.inAlienSpace = Status.getSystem() == null;
      Object var1 = null;
      Image var2;
      int var4;
      int var13;
      if (!this.inAlienSpace) {
         int[] var8 = Status.getSystem().getStations();
         this.starAndPlanets = new AbstractMesh[var8.length + 1];
         this.localPlanets = new KIPlayer[var8.length];
         var2 = AEFile.loadImage("/data/interface/planet_" + Status.getStation().getPlanetTextureId() + ".png", true);
         var2 = AEFile.resizeImage(var2, (int)(var2.getWidth()*GlobalStatus.screenHeight/320*2), (int)(var2.getHeight()*GlobalStatus.screenHeight/320*2));
         this.planet = new Sprite(var2);
         this.planet.defineReferencePixel(this.planet.getWidth() / 2, this.planet.getHeight() / 2);
         GlobalStatus.random.setSeed((long)(Status.getStation().getId() * 300));
         boolean[] var10 = new boolean[24];
         int var3 = 0;
         var4 = 0;
         boolean var5 = false;
         int starElevation = (GlobalStatus.random.nextInt(256) - 128)%4096;
         for(var13 = 0; var13 < this.starAndPlanets.length; ++var13) {
             
            this.starAndPlanets[var13] = AEResourceManager.getGeometryResource(6778);
            this.starAndPlanets[var13].setRenderLayer(1);
            if (var13 == 0) {
               this.starAndPlanets[var13].setAnimationMode((byte)1);
               //this.var_153[var13].sub_7af(64,64,64);
               //this.var_153[var13].pitch(2048);
               this.starAndPlanets[var13].setSun(true);
               var4 = var3 = GlobalStatus.random.nextInt(24);
            } else {
               boolean var6;
               if (var8[var13 - 1] == Status.getStation().getId()) {
                  this.starAndPlanets[var13].setDraw(true);
                  var6 = false;
                  int var7;
                  if ((var7 = Status.getStation().getPlanetTextureId()) != 17 && var7 != 18) {
                     while(true) {
                        if (var6) {
                           if (AEMath.abs(var3 - var4) < 12) {
                              this.planet.setTransform(2);
                           }
                           break;
                        }

                        var6 = AEMath.abs((var3 = GlobalStatus.random.nextInt(24)) - var4) > 3 && !var10[var3];
                     }
                  } else {
                     var3 = var4;
                  }

                  currentPlanetEnumIndex = var13;
               } else {
                  this.localPlanets[var13 - 1] = new PlayerStatic(0, this.starAndPlanets[var13], 0, 0, 0);
                  this.starAndPlanets[var13].setDraw(false);

                  for(var6 = false; !var6; var6 = AEMath.abs((var3 = 7 + GlobalStatus.random.nextInt(11)) - var4) > 2 && !var10[var3]) {
                  }
               }
            }

            var10[var3] = true;
            if (var3 == var4 && var13 != 0) {
               this.starAndPlanets[var13].setRotation(0, var3 * 170 + 128, 0);
            } else if (var13 > 0 && var8[var13 - 1] == Status.getStation().getId()) {
               this.starAndPlanets[var13].setRotation(0, var3 * 170, 0);
            } else {
        	
               this.starAndPlanets[var13].setRotation(starElevation, (var3 * 170)%4096, 0);
            }
            
            this.starAndPlanets[var13].moveForward(-20000);
            if (var13 == 0) {
        	//this.var_153[var13].setRotation(4096-starElevation, 4096-((var3 * 170)%4096), 0);
            }
            this.starAndPlanets[var13].updateTransform(true);
            this.starAndPlanets[var13].update(1L);
            this.cameraControler.uniqueAppend_(this.starAndPlanets[var13]);
         }

         if ((var13 = GlobalStatus.random.nextInt(8)) > 0) {
            this.nebulaPivots = new AbstractMesh[var13];
            this.occupiedNebulaPos = new boolean[8];
            this.occupiedAstroObjPos = new boolean[this.astronomicalObjPlacings.length / 3];
            this.nebulaImgs = new Image[this.nebulaPivots.length];

            for(int var14 = 0; var14 < this.nebulaPivots.length; ++var14) {
               this.nebulaPivots[var14] = AEResourceManager.getGeometryResource(6781);
               this.nebulaPivots[var14].setRenderLayer(1);
               this.nebulaPivots[var14].setDraw(false);
               boolean var15 = false;
               int var9 = 0;

               while(!var15) {
                  var9 = GlobalStatus.random.nextInt(this.astronomicalObjPlacings.length / 3);
                  if (var15 = !this.occupiedAstroObjPos[var9]) {
                     this.occupiedAstroObjPos[var9] = true;
                  }
               }

               this.nebulaPivots[var14].moveTo(this.astronomicalObjPlacings[var9], this.astronomicalObjPlacings[var9 + 1], this.astronomicalObjPlacings[var9 + 2]);
               var15 = false;
               var9 = 0;

               while(!var15) {
                  var9 = GlobalStatus.random.nextInt(8);
                  if (var15 = !this.occupiedNebulaPos[var9]) {
                     this.occupiedNebulaPos[var9] = true;
                  }
               }
               //scaling 
               var2 = AEFile.loadImage("/data/interface/nebula" + var9 + ".png", true);
               this.nebulaImgs[var14] = AEFile.resizeImage(var2, (int)(var2.getWidth()*GlobalStatus.screenHeight/320), (int)(var2.getHeight()*GlobalStatus.screenHeight/320));
               this.cameraControler.uniqueAppend_(this.nebulaPivots[var14]);
            }
         }
      }

      GlobalStatus.random.setSeed(System.currentTimeMillis());
      this.tempVec = new AEVector3D();
      this.lensFlare = new LensFlareFX();
    //scaling
      var2 = AEFile.loadImage("/data/interface/sun_" + (this.inAlienSpace ? 0 : Status.getSystem().getStarTextureIndex()) + ".png", true);
      var2 = AEFile.resizeImage(var2, (int)(var2.getWidth()*GlobalStatus.screenHeight/320), (int)(var2.getHeight()*GlobalStatus.screenHeight/320));
      this.sun = new Sprite(var2);
      this.sun.defineReferencePixel(this.sun.getWidth(), this.sun.getHeight());
      if (!this.inAlienSpace) {
         Image[] var11 = new Image[6];
         int[] var12 = Status.getPlanetTextures();
         this.localPlanetsImgs = new Image[var12.length];

         for(var4 = 0; var4 < var12.length; ++var4) {
            var13 = this.planetSmallImages[var12[var4]];
            if (var11[var13] == null) {
        	var2 = AEFile.loadImage("/data/interface/star_" + var13 + ".png", true);
               var2 = AEFile.resizeImage(var2, (int)(var2.getWidth()*GlobalStatus.screenHeight/320), (int)(var2.getHeight()*GlobalStatus.screenHeight/320));
               var11[var13] = var2;
            }

            this.localPlanetsImgs[var4] = var11[var13];
         }
      }

   }

   public final AbstractMesh[] getPlanets() {
      return this.starAndPlanets;
   }

   public final KIPlayer[] getPlanetTargets() {
      return this.localPlanets;
   }

   public final void renderSunStreak__() {
      if (this.inAlienSpace) {
         this.tempVec.x = 0;
         this.tempVec.y = 0;
         this.tempVec.z = -4096;
      } else {
         this.tempVec = this.starAndPlanets[0].getLocalDirection(this.tempVec);
      }

      this.tempVec.x = -this.tempVec.x;
      this.tempVec.y = -this.tempVec.y;
      this.tempVec.z = -this.tempVec.z;
      this.tempVec.normalize();
      this.tempVec = GlobalStatus.renderer.getCamera().getLocalTransform().transformVectorNoScale(this.tempVec);
      this.tempVec.x = -this.tempVec.x;
      this.tempVec = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVec);
      this.cameraControler.moveTo(this.tempVec);
      this.cameraControler.updateTransform(true);
      if (this.inAlienSpace) {
         this.tempVec.x = 0;
         this.tempVec.y = 0;
         this.tempVec.z = 20000;
      } else {
         this.tempVec = this.starAndPlanets[0].getLocalPos(this.tempVec);
      }

      this.tempVec.subtract(GlobalStatus.renderer.getCamera().getLocalPos());
      this.tempVec.normalize();
      AEVector3D var1;
      (var1 = new AEVector3D(GlobalStatus.renderer.getCamera().getLocalDirection())).normalize();
      this.tempVec.subtract(var1);
      float var2 = (float)this.tempVec.getLength() / 8192.0F;
      Level.bgR = 0;
      Level.bgG = 0;
      Level.bgB = 0;
      Level.bgR = AEMath.max(Level.spaceR, (int)(Level.starR * var2));
      Level.bgG = AEMath.max(Level.spaceG, (int)(Level.starG * var2));
      Level.bgB = AEMath.max(Level.spaceB, (int)(Level.starB * var2));
   }

   public final void render_() {
      this.cameraControler.moveTo(GlobalStatus.renderer.getCamera().getLocalPos(this.tempVec));
      this.cameraControler.updateTransform(true);
      int var1;
      if (this.nebulaPivots != null) {
         for(var1 = 0; var1 < this.nebulaPivots.length; ++var1) {
            GlobalStatus.renderer.getCamera().getScreenPosition(this.nebulaPivots[var1].getLocalPos(this.tempVec));
            if (this.tempVec.z < 0) {
               GlobalStatus.graphics.drawImage(this.nebulaImgs[var1], this.tempVec.x, this.tempVec.y, 3);
            }
         }
      }
      if (this.starAndPlanets != null && this.starAndPlanets[0] != null); //&& (var_153[0].resourceId == 6781 var_153[0].resourceId == 6781)
	   Light sunLight = new Light();
           sunLight.setIntensity(0.9F);
           sunLight.setColor(0xffffff);
           sunLight.setMode(Light.DIRECTIONAL);
           
           Light sunShine = new Light();
           sunShine.setIntensity(2.0F);
           sunShine.setMode(Light.DIRECTIONAL);
      //GameStatus.renderer.appendNode(this.var_153[0]);
          sunShine.setColor(Level.starLight());
          //AEGraphics3D.graphics3D.addLight(this.sunShine, tranforma); 
          GlobalStatus.renderer.setLights( this.starAndPlanets[0].getLocalTransform().getNegative(), new Light[]{sunShine, sunLight}); 
	  //GameStatus.renderer.sub_87(var_153[var1]);
      
      for(var1 = 0; var1 < (this.inAlienSpace ? 1 : this.starAndPlanets.length); ++var1) {
         if (this.inAlienSpace) {
            this.tempVec.x = 0;
            this.tempVec.y = 0;
            this.tempVec.z = 0;
            GlobalStatus.renderer.getCamera().getScreenPosition(this.tempVec);
         } else {
            GlobalStatus.renderer.getCamera().getScreenPosition(this.starAndPlanets[var1].getLocalPos(this.tempVec));
         }
         
         if (this.tempVec.z < 0) {
            if (var1 == 0) {
               this.sun.setTransform(0);
               this.sun.setRefPixelPosition(this.tempVec.x, this.tempVec.y);
               this.sun.paint(GlobalStatus.graphics);
               this.sun.setTransform(5);
               this.sun.setRefPixelPosition(this.tempVec.x - 1, this.tempVec.y);
               this.sun.paint(GlobalStatus.graphics);
               this.sun.setTransform(3);
               this.sun.setRefPixelPosition(this.tempVec.x - 1, this.tempVec.y - 1);
               this.sun.paint(GlobalStatus.graphics);
               this.sun.setTransform(6);
               this.sun.setRefPixelPosition(this.tempVec.x, this.tempVec.y - 1);
               this.sun.paint(GlobalStatus.graphics);
            } else if (var1 == currentPlanetEnumIndex) {
               this.planet.setRefPixelPosition(this.tempVec.x, this.tempVec.y);
               this.planet.paint(GlobalStatus.graphics);
            } else {
               GlobalStatus.graphics.drawImage(this.localPlanetsImgs[var1 - 1], this.tempVec.x, this.tempVec.y, 3);
            }
         }
      }

   }

   public final void render2D() {
      if (this.inAlienSpace) {
         this.tempVec.x = 0;
         this.tempVec.y = 0;
         this.tempVec.z = 0;
         GlobalStatus.renderer.getCamera().getScreenPosition(this.tempVec);
         this.sun.setTransform(0);
         this.sun.setRefPixelPosition(this.tempVec.x, this.tempVec.y);
         this.sun.paint(GlobalStatus.graphics);
         this.sun.setTransform(5);
         this.sun.setRefPixelPosition(this.tempVec.x - 1, this.tempVec.y);
         this.sun.paint(GlobalStatus.graphics);
         this.sun.setTransform(3);
         this.sun.setRefPixelPosition(this.tempVec.x - 1, this.tempVec.y - 1);
         this.sun.paint(GlobalStatus.graphics);
         this.sun.setTransform(6);
         this.sun.setRefPixelPosition(this.tempVec.x, this.tempVec.y - 1);
         this.sun.paint(GlobalStatus.graphics);
      } else {
         GlobalStatus.renderer.getCamera().getScreenPosition(this.starAndPlanets[0].getLocalPos(this.tempVec));
      }

      this.lensFlare.render2D(this.tempVec);
   }
}
