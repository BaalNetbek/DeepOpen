package AbyssEngine;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

public final class Skybox {
   private final int[] var_64 = new int[]{-4616, -10639, -13667, -2949, -17362, 7030, -398, -7882, 15401, 7177, 3255, -16472, 12676, 6461, -10634, -10046, 12526, -7558, -10697, -13537, -5158, 18625, 4517, -684, -10685, -11195, 8126, 5693, -4063, 16472, 3209, 11007, 13435, 9044, -11172, 10999, 1211, -19330, 1470, -9770, 4503, 16022, -7591, 6356, -16362, 5962, 16281, -9492, 11976, -9221, -8374, 11035, 9992, 9033, -16959, -5712, -3570, -12936, 12109, 3078};
   private final int[] var_fb = new int[]{5, 1, 2, 3, 0, 2, 4, 5, 4, 4, 1, 5, 2, 3, 2, 3, 2, 2, 2, 5};
   private AbstractMesh[] var_153;
   private Class_198 var_1f8;
   private AEVector3D var_25a;
   private LensFlareFX lensFlare;
   private Sprite sun;
   private Sprite planet;
   private Image[] localPlanetsImgs;
   private KIPlayer[] localPlanets;
   private AbstractMesh[] nebulaPivots;
   private Image[] nebulaImgs;
   private boolean[] var_509;
   private boolean[] var_54f;
   public static int var_5a9;
   private boolean inAlienSpace;
   private Light light;
   private int switcher;
   Matrix transf;

   public Skybox() {
      // this.switcher = 0;
      // this.light = new Light();
      // this.light.setIntensity(2.0F);

      // this.light.setMode(Light.OMNI);
      new Transform();
      new Matrix();
      new Matrix();
      this.var_1f8 = new Class_198();
      this.inAlienSpace = Status.getSystem() == null;
      Object var1 = null;
      Image var2;
      int var4;
      int var13;
      if (!this.inAlienSpace) {
         int[] var8 = Status.getSystem().getStations();
         this.var_153 = new AbstractMesh[var8.length + 1];
         this.localPlanets = new KIPlayer[var8.length];
         var2 = AEImage.loadImage("/data/interface/planet_" + Status.getStation().getPlanetTextureId() + ".png", true);
         this.planet = new Sprite(var2);
         this.planet.defineReferencePixel(this.planet.getWidth() / 2, this.planet.getHeight() / 2);
         GameStatus.random.setSeed((long)(Status.getStation().getId() * 300));
         boolean[] var10 = new boolean[24];
         int var3 = 0;
         var4 = 0;
         boolean var5 = false;

         for(var13 = 0; var13 < this.var_153.length; ++var13) {
            this.var_153[var13] = AEResourceManager.getGeometryResource(6781);
            this.var_153[var13].setRenderLayer(1);
            if (var13 == 0) {
               this.var_153[var13].sub_9aa((byte)1);
               this.var_153[var13].sub_7af(64, 64, 64);
               var4 = var3 = GameStatus.random.nextInt(24);
            } else {
               boolean var6;
               if (var8[var13 - 1] == Status.getStation().getId()) {
                  this.var_153[var13].setDraw(false);
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

                        var6 = AEMath.abs((var3 = GameStatus.random.nextInt(24)) - var4) > 3 && !var10[var3];
                     }
                  } else {
                     var3 = var4;
                  }

                  var_5a9 = var13;
               } else {
                  this.localPlanets[var13 - 1] = new PlayerStatic(0, this.var_153[var13], 0, 0, 0);
                  this.var_153[var13].setDraw(false);

                  for(var6 = false; !var6; var6 = AEMath.abs((var3 = 7 + GameStatus.random.nextInt(11)) - var4) > 2 && !var10[var3]) {
                  }
               }
            }

            var10[var3] = true;
            if (var3 == var4 && var13 != 0) {
               this.var_153[var13].setRotation(0, var3 * 170 + 128, 0);
            } else if (var13 > 0 && var8[var13 - 1] == Status.getStation().getId()) {
               this.var_153[var13].setRotation(0, var3 * 170, 0);
            } else {
               this.var_153[var13].setRotation(-128 + GameStatus.random.nextInt(256), var3 * 170, 0);
            }

            this.var_153[var13].sub_202(-20000);
            this.var_153[var13].sub_109(true);
            this.var_153[var13].sub_181(1L);
            this.var_1f8.sub_25(this.var_153[var13]);
         }

         if ((var13 = GameStatus.random.nextInt(8)) > 0) {
            this.nebulaPivots = new AbstractMesh[var13];
            this.var_509 = new boolean[8];
            this.var_54f = new boolean[this.var_64.length / 3];
            this.nebulaImgs = new Image[this.nebulaPivots.length];

            for(int var14 = 0; var14 < this.nebulaPivots.length; ++var14) {
               this.nebulaPivots[var14] = AEResourceManager.getGeometryResource(6781);
               this.nebulaPivots[var14].setRenderLayer(1);
               this.nebulaPivots[var14].setDraw(false);
               boolean var15 = false;
               int var9 = 0;

               while(!var15) {
                  var9 = GameStatus.random.nextInt(this.var_64.length / 3);
                  if (var15 = !this.var_54f[var9]) {
                     this.var_54f[var9] = true;
                  }
               }

               this.nebulaPivots[var14].moveTo(this.var_64[var9], this.var_64[var9 + 1], this.var_64[var9 + 2]);
               var15 = false;
               var9 = 0;

               while(!var15) {
                  var9 = GameStatus.random.nextInt(8);
                  if (var15 = !this.var_509[var9]) {
                     this.var_509[var9] = true;
                  }
               }

               this.nebulaImgs[var14] = AEImage.loadImage("/data/interface/nebula" + var9 + ".png", true);
               this.var_1f8.sub_25(this.nebulaPivots[var14]);
            }
         }
      }

      GameStatus.random.setSeed(System.currentTimeMillis());
      this.var_25a = new AEVector3D();
      this.lensFlare = new LensFlareFX();
      var2 = AEImage.loadImage("/data/interface/sun_" + (this.inAlienSpace ? 0 : Status.getSystem().getStarTextureIndex()) + ".png", true);
      this.sun = new Sprite(var2);
      this.sun.defineReferencePixel(this.sun.getWidth(), this.sun.getHeight());
      if (!this.inAlienSpace) {
         Image[] var11 = new Image[6];
         int[] var12 = Status.getPlanetTextures();
         this.localPlanetsImgs = new Image[var12.length];

         for(var4 = 0; var4 < var12.length; ++var4) {
            var13 = this.var_fb[var12[var4]];
            if (var11[var13] == null) {
               var11[var13] = AEImage.loadImage("/data/interface/star_" + var13 + ".png", true);
            }

            this.localPlanetsImgs[var4] = var11[var13];
         }
      }

   }

   public final AbstractMesh[] sub_3d() {
      return this.var_153;
   }

   public final KIPlayer[] getLocalPlanets() {
      return this.localPlanets;
   }

   public final void sub_b9() {
      if (this.inAlienSpace) {
         this.var_25a.x = 0;
         this.var_25a.y = 0;
         this.var_25a.z = -4096;
      } else {
         this.var_25a = this.var_153[0].sub_3b2(this.var_25a);
      }

      this.var_25a.x = -this.var_25a.x;
      this.var_25a.y = -this.var_25a.y;
      this.var_25a.z = -this.var_25a.z;
      this.var_25a.normalize();
      this.var_25a = GameStatus.renderer.getCamera().sub_8a0().sub_b26(this.var_25a);
      this.var_25a.x = -this.var_25a.x;
      this.var_25a = GameStatus.renderer.getCamera().sub_22a(this.var_25a);
      this.var_1f8.sub_1f3(this.var_25a);
      this.var_1f8.sub_109(true);
      if (this.inAlienSpace) {
         this.var_25a.x = 0;
         this.var_25a.y = 0;
         this.var_25a.z = 20000;
      } else {
         this.var_25a = this.var_153[0].sub_22a(this.var_25a);
      }

      this.var_25a.subtract(GameStatus.renderer.getCamera().sub_28c());
      this.var_25a.normalize();
      AEVector3D var1;
      (var1 = new AEVector3D(GameStatus.renderer.getCamera().sub_462())).normalize();
      this.var_25a.subtract(var1);
      float var2 = (float)this.var_25a.getLength() / 8192.0F;
      Level.bgR = 0;
      Level.bgG = 0;
      Level.bgB = 0;
      Level.bgR = AEMath.max(Level.spaceR, (int)(Level.starR * var2));
      Level.bgG = AEMath.max(Level.spaceG, (int)(Level.starG * var2));
      Level.bgB = AEMath.max(Level.spaceB, (int)(Level.starB * var2));
   }

   public final void sub_116() {
      this.var_1f8.sub_1f3(GameStatus.renderer.getCamera().sub_22a(this.var_25a));
      this.var_1f8.sub_109(true);
      int var1;
      if (this.nebulaPivots != null) {
         for(var1 = 0; var1 < this.nebulaPivots.length; ++var1) {
            GameStatus.renderer.getCamera().sub_fa(this.nebulaPivots[var1].sub_22a(this.var_25a));
            if (this.var_25a.z < 0) {
               GameStatus.graphics.drawImage(this.nebulaImgs[var1], this.var_25a.x, this.var_25a.y, 3);
            }
         }
      }
      if (this.var_153 != null && this.var_153[0] != null && var_153[0].resourceId == 6781)
	  GameStatus.renderer.appendNode(this.var_153[0]);
      
      for(var1 = 0; var1 < (this.inAlienSpace ? 1 : this.var_153.length); ++var1) {
         if (this.inAlienSpace) {
            this.var_25a.x = 0;
            this.var_25a.y = 0;
            this.var_25a.z = 0;
            GameStatus.renderer.getCamera().sub_fa(this.var_25a);
         } else {
            GameStatus.renderer.getCamera().sub_fa(this.var_153[var1].sub_22a(this.var_25a));
         }/*
         if (var1 == 0) {
             Matrix materix = new Matrix();
             float max = Math.max(Level.starB, Level.starG);
             max = Math.max(max, Level.starR);
             this.light.setColor(
      	       (int)(Level.starR/max*255) << 16
      	       | (int)(Level.starG/max*255) << 8
      	       | (int)(Level.starB/max*255)
      	       );
             switch(this.switcher) {
             case 1:
        	 transf = materix.sub_8ac (this.var_153[var1].sub_85f() );
        	 break;
             case 2:
        	 transf = this.var_153[var1].sub_85f();
        	 break;
             case 3:
        	 transf = materix.sub_8ac (this.var_153[var1].sub_8a0() );
        	 break;
            default:
        	 transf = this.var_153[var1].sub_8a0();
        	     break;
        	     
             }
             float arr[] = new float[16];
             transf.scaledToFloatArray(arr);
             Transform tranforma = new Transform();
             tranforma.set(arr);
             
             
      	   //AEGraphics3D.graphics3D.resetLights();
      	   //AEGraphics3D.graphics3D.addLight(light, tranforma);
         }*/
         if (this.var_25a.z < 0) {
            if (var1 == 0) {
               this.sun.setTransform(0);
               this.sun.setRefPixelPosition(this.var_25a.x, this.var_25a.y);
               this.sun.paint(GameStatus.graphics);
               this.sun.setTransform(5);
               this.sun.setRefPixelPosition(this.var_25a.x - 1, this.var_25a.y);
               this.sun.paint(GameStatus.graphics);
               this.sun.setTransform(3);
               this.sun.setRefPixelPosition(this.var_25a.x - 1, this.var_25a.y - 1);
               this.sun.paint(GameStatus.graphics);
               this.sun.setTransform(6);
               this.sun.setRefPixelPosition(this.var_25a.x, this.var_25a.y - 1);
               this.sun.paint(GameStatus.graphics);
            } else if (var1 == var_5a9) {
               this.planet.setRefPixelPosition(this.var_25a.x, this.var_25a.y);
               this.planet.paint(GameStatus.graphics);
            } else {
               GameStatus.graphics.drawImage(this.localPlanetsImgs[var1 - 1], this.var_25a.x, this.var_25a.y, 3);
            }
         }
      }

   }

   public final void sub_126() {
      if (this.inAlienSpace) {
         this.var_25a.x = 0;
         this.var_25a.y = 0;
         this.var_25a.z = 0;
         GameStatus.renderer.getCamera().sub_fa(this.var_25a);
         this.sun.setTransform(0);
         this.sun.setRefPixelPosition(this.var_25a.x, this.var_25a.y);
         this.sun.paint(GameStatus.graphics);
         this.sun.setTransform(5);
         this.sun.setRefPixelPosition(this.var_25a.x - 1, this.var_25a.y);
         this.sun.paint(GameStatus.graphics);
         this.sun.setTransform(3);
         this.sun.setRefPixelPosition(this.var_25a.x - 1, this.var_25a.y - 1);
         this.sun.paint(GameStatus.graphics);
         this.sun.setTransform(6);
         this.sun.setRefPixelPosition(this.var_25a.x, this.var_25a.y - 1);
         this.sun.paint(GameStatus.graphics);
      } else {
         GameStatus.renderer.getCamera().sub_fa(this.var_153[0].sub_22a(this.var_25a));
      }

      this.lensFlare.render2D(this.var_25a);
   }
}
