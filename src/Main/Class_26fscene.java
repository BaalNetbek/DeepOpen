package Main;

import AbyssEngine.AEMath;
import AbyssEngine.AbstractScene;
import AbyssEngine.Camera;
import AbyssEngine.Class_198;
import AbyssEngine.Class_8e4superOrbitHelper;
import AbyssEngine.Class_c53cameraRelated;
import AbyssEngine.Class_fd6;
import AbyssEngine.GameStatus;
import AbyssEngine.Group;
import AbyssEngine.IndexManager;
import AbyssEngine.KIPlayer;
import AbyssEngine.Level;
import AbyssEngine.Matrix;
import AbyssEngine.PlayerEgo;
import AbyssEngine.PlayerFighter;
import AbyssEngine.Status;

public final class Class_26fscene extends AbstractScene {
   private long var_1d;
   private long var_36;
   private long frameTimeMs;
   private long var_ed;
   private boolean var_103;
   private Class_198 var_19c;
   private Camera var_222;
   private Camera var_313;
   private Class_8e4superOrbitHelper var_371;
   private Class_c53cameraRelated var_3c4;
   private Class_fd6 var_407;
   private PlayerEgo egoPlayer;
   public Level level;
   private int var_4cb;
   private Group var_526;
   private float var_613;
   private float var_629;
   private float var_676;
   private int var_682;
   private int var_704;

   public Class_26fscene(int var1) {
      this.var_4cb = var1;
   }

   public final void freeResources() {
      if (this.egoPlayer != null) {
         this.egoPlayer.sub_1181();
      }

      this.egoPlayer = null;
      this.var_222 = null;
      if (this.level != null) {
         this.level.sub_c8b();
      }

      this.level = null;
      this.var_407 = null;
      this.var_3c4 = null;
      this.var_19c = null;
      this.var_103 = false;
      System.gc();
   }

   public final void sub_2c() {
      switch(this.var_4cb) {
      case 4:
         if (this.var_222 == null) {
            this.var_222 = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 1024, 200, 28000);
            this.var_222.sub_5a7((short)2);
         }

         GameStatus.var_8ce.sub_19(this.var_222);
         this.var_222.moveTo(920, 500, -1240);
         this.var_222.setRotation(-160, 2048, 0);
         return;
      case 23:
         GameStatus.var_8ce.sub_19(this.var_313);
         KIPlayer[] var1;
         if ((var1 = this.level.getShips())[0].sub_4a3() >= 0) {
            ((PlayerFighter)var1[0]).sub_2b8(false);
            return;
         }
         break;
      default:
         if (this.var_371 != null) {
            GameStatus.var_8ce.sub_19(this.var_371.sub_15c());
         }
      }

   }

   public final void loadResources() {
      try {
         if (this.level == null) {
            this.level = new Level(this.var_4cb);
         }

         if (!this.level.sub_198()) {
            return;
         }

         this.egoPlayer = this.level.getPlayerEgo();
         if (this.egoPlayer != null) {
            this.egoPlayer.sub_8f2(false);
         }

         switch(this.var_4cb) {
         case 0:
            this.var_371 = new Class_8e4superOrbitHelper(1);
            GameStatus.var_8ce.sub_19(this.var_371.sub_15c());
            break;
         case 2:
            this.var_371 = new Class_8e4superOrbitHelper(0);
            GameStatus.var_8ce.sub_19(this.var_371.sub_15c());
            break;
         case 4:
            this.sub_2c();
            break;
         case 23:
            this.var_313 = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 900, 10, 31768);
            this.var_313.moveTo(0, 1700, 1500);
            this.var_313.setRotation(-256, 0, 0);
            GameStatus.var_8ce.sub_19(this.var_313);
            this.var_526 = new Group();
            this.var_526.sub_25(this.var_313);
            this.var_526.sub_18f(0, 0, 10240);
            this.var_676 = 1536.0F;
            this.var_526.sub_109(true);
            KIPlayer[] var1;
            if ((var1 = this.level.getShips())[0].sub_4a3() >= 0) {
               this.var_682 = var1[0].var_379.sub_2c5();
               this.var_704 = var1[0].var_379.sub_31f();
            }
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      System.gc();
      this.var_ed = 0L;
      this.var_1d = System.currentTimeMillis();
      this.var_36 = System.currentTimeMillis();
      this.var_103 = true;
   }

   public final boolean isLoaded() {
      return this.var_103;
   }

   public final void sub_7c(int var1, int var2) {
      if (this.level.getShips() != null && this.level.getShips()[0] != null && this.level.getShips()[0].var_379 != null) {
         Matrix var3 = this.level.getShips()[0].var_379.sub_85f();
         this.level.getShips()[0].sub_ca(IndexManager.buildShip_(var1, var2), var1);
         this.level.getShips()[0].var_379.sub_8c9(var3);
      }

   }

   public final void renderScene(int var1) {
      if (this.var_103) {
         this.var_1d = System.currentTimeMillis();
         this.frameTimeMs = this.var_1d - this.var_36;
         this.var_36 = this.var_1d;
         this.var_ed += this.frameTimeMs;
         if (this.var_371 != null && (this.var_4cb == 2 || this.var_ed < 10000L)) {
            this.var_371.sub_4d(this.frameTimeMs);
         }

         Class_26fscene var2 = this;

         try {
            var2.level.sub_a3b(var2.frameTimeMs);
            var2.level.sub_966(var2.frameTimeMs);
            GameStatus.graphics3D.bindTarget(GameStatus.graphics);
            GameStatus.var_8ce.sub_cc(System.currentTimeMillis());
            GameStatus.graphics3D.clear();
            GameStatus.graphics3D.releaseTarget();
         } catch (Exception var4) {
            GameStatus.graphics3D.releaseTarget();
            var4.printStackTrace();
         }

         if (this.var_4cb == 23) {
            int var6 = AEMath.sin((int)this.var_ed) >> 7;
            KIPlayer[] var8;
            if ((var8 = this.level.getShips())[0].sub_4a3() >= 0) {
               var8[0].var_379.moveTo(0, this.var_682 + var6, this.var_704);
            }

            boolean var7 = false;
            if ((var1 & 4) != 0) {
               this.var_613 = (float)this.frameTimeMs;
               var7 = true;
            } else {
               this.var_613 *= 0.9F;
            }

            if ((var1 & 32) != 0) {
               this.var_629 = (float)this.frameTimeMs;
               var7 = true;
            } else {
               this.var_629 *= 0.9F;
            }

            this.var_676 += this.var_613 - this.var_629;
            if (!var7) {
               this.var_676 += (float)((int)this.frameTimeMs / 6);
            }

            this.var_526.setRotation(0, (int)this.var_676, 0);
            this.var_526.sub_109(true);
         } else {
            if (this.var_4cb == 4) {
               if (this.var_ed > 500L) {
                  this.var_ed = 0L;
                  KIPlayer[] var5 = this.level.getShips();

                  for(int var3 = 0; var3 < Status.getStation().getBarAgents().length; ++var3) {
                     if (GameStatus.random.nextInt(100) < 10 && !var5[var3].var_25d.sub_a37()) {
                        var5[var3].var_25d.sub_9aa((byte)1);
                     }
                  }

                  return;
               }
            } else {
               this.level.sub_9ca();
            }

         }
      }
   }

   public final void handleKeystate(int var1) {
   }
}
