package AbyssEngine;

public final class PlayerAsteroid extends KIPlayer {
   private static AEVector3D var_1c5 = new AEVector3D();
   private static AEVector3D var_249 = new AEVector3D();
   private int frametime;
   private int var_312;
   private int var_356;
   private int var_3fe;
   private boolean var_42f;
   private int var_54b;
   private int var_694;
   private int var_707;
   private int passedTime;
   private static AEVector3D var_793;
   private boolean var_7f2;
   private Matrix var_80b = new Matrix();
   private int var_880;
   private int var_8aa;
   private AbstractMesh var_956;
   public boolean var_973;
   public int var_9d3;

   public PlayerAsteroid(int var1, AbstractMesh var2, int var3, boolean var4, int var5, int var6, int var7) {
      super(var1, -1, new Player(1500.0F, 30, 0, 0, 0), var2, var5, var6, var7);
      this.player.setKIPlayer(this);
      this.var_9d3 = var3;
      this.var_312 = var5;
      this.var_356 = var6;
      this.var_3fe = var7;
      var_249.set(var5, var6, var7);
      this.var_42f = var4;
      this.var_973 = false;
      this.var_54b = 1024 + GameStatus.random.nextInt(2448);
      this.var_694 = 1024 + GameStatus.random.nextInt(2448);
      this.var_707 = 1024 + GameStatus.random.nextInt(2448);
      this.var_880 = (int)((float)((this.var_54b + this.var_694 + this.var_707) / 3) / 3072.0F * 100.0F);
      this.player.setMaxHP(30 + (int)((float)this.var_880 / 100.0F * 100.0F));
      this.var_8aa = AEMath.min(7, 2 + (int)((float)(this.var_880 + 15) / 100.0F * 5.0F));
      this.var_727.set(-4096 + GameStatus.random.nextInt(8192), -4096 + GameStatus.random.nextInt(8192), -4096 + GameStatus.random.nextInt(8192));
      this.var_727.normalize();
      this.var_25d.sub_85f().setOrientation(this.var_727);
      this.var_25d.sub_1f3(var_249);
      this.var_25d.sub_109(true);
      this.var_25d.sub_980(var3 - 154, var3 - 154);
      this.var_25d.sub_a04();
      this.var_7f2 = true;
      this.isAsteroid = true;
      this.var_9d8 = true;
      this.state = 0;
   }

   public final void sub_22() {
      super.destroy();
      this.var_956 = null;
   }

   public final int sub_80() {
      return this.var_880;
   }

   public final int sub_e2() {
      return this.var_8aa;
   }

   public final int sub_1bb() {
      return 7 - this.var_8aa;
   }

   public final void sub_20d(AEVector3D var1) {
      var_793 = var1;
      if (this.var_42f) {
         this.sub_296();
      }

   }

   public final void sub_26f(boolean var1) {
      this.var_7f2 = var1;
   }

   public final void update(long var1) {
      this.frametime = (int)var1;
      if (!this.player.sub_ace()) {
         this.var_973 = false;
      } else {
         this.passedTime += this.frametime;
         int var6;
         int var7;
         if (this.player.getHitpoints() <= 0 && this.state == 0) {
            var_249 = this.var_25d.sub_216(var_249);
            if (this.var_956 == null) {
               this.var_956 = AEResourceManager.getGeometryResource(6782);
               this.var_956.setRenderLayer(2);
               this.var_956.sub_918(100);
               this.player.setKIPlayer(this);
            }

            this.var_956.sub_8c9(this.var_25d.sub_85f());
            this.var_956.sub_9aa((byte)1);
            this.player.var_46b = this.var_956.sub_85f();
            var_249.subtract(this.level.getPlayerEgo().sub_8c1());
            var7 = AEMath.min(40000, var_249.getLength());
            float var5 = 1.0F - (float)var7 / 40000.0F;
            var6 = GameStatus.soundManager.getMusicVolume();
            GameStatus.soundManager.playSfx(5, (int)((float)var6 * var5));
            this.explosion.sub_141(this.var_25d.sub_29c(), this.var_25d.sub_2c5(), this.var_25d.sub_31f());
            this.state = 3;
            this.level.sub_7df();
            this.passedTime = 0;
            ++Status.asteroidsDestroyed;
            if (this.var_9d8 && (this.var_8aa == 7 && GameStatus.random.nextInt(100) < 40 || this.var_8aa < 7 && GameStatus.random.nextInt(100) < 20) && this.var_9d3 != 164) {
               this.var_a3b = new int[2];
               this.var_a3b[0] = this.var_9d3 + (this.var_8aa == 7 ? 11 : 0);
               this.var_a3b[1] = this.var_8aa == 7 ? 1 : 1 + GameStatus.random.nextInt(3);
               this.setWasteMesh(1);
            } else {
               this.var_9d8 = false;
            }
         } else {
            if (this.state == 4) {
               this.sub_34(false);
            } else if (this.state != 3 && this.var_7f2) {
               this.var_25d.roll((int)var1 >> 2);
            }

            float var3;
            if ((var3 = this.player.sub_892()) > 0.0F) {
               this.var_727.set(this.player.sub_8db());
               this.var_727.scale((int)(1024.0F * var3 * (1.0F - (float)AEMath.min(60, this.var_880) / 100.0F)));
               this.var_25d.sub_19c(this.var_727);
               this.var_312 += this.var_727.x;
               this.var_356 += this.var_727.y;
               this.var_3fe += this.var_727.z;
               this.var_25d.sub_5c5((int)var1 >> 1, (int)var1 >> 1, (int)var1 >> 1);
               if ((var3 *= 0.98F) < 0.05F) {
                  var3 = 0.0F;
               }

               this.player.setNukeStun(var3);
            }

            this.var_727 = GameStatus.var_8ce.getCamera().sub_22a(this.var_727);
            this.positon.set(this.var_312, this.var_356, this.var_3fe);
            this.positon.subtract(this.var_727, var_1c5);
            int var4 = var_1c5.getLength();
            if (!this.var_42f) {
               if (var4 > 30000) {
                  var_1c5.normalize();
                  var_1c5.scale(30000);
                  var_1c5.add(this.var_727);
                  this.var_25d.sub_1f3(var_1c5);
                  float var2;
                  var4 = (int)((var2 = 30000.0F / (float)var4) * (float)this.var_54b);
                  var7 = (int)(var2 * (float)this.var_694);
                  var6 = (int)(var2 * (float)this.var_707);
                  this.var_25d.sub_7af(var4, var7, var6);
                  this.var_973 = true;
               } else {
                  this.var_25d.sub_7af(this.var_54b, this.var_694, this.var_707);
                  this.var_25d.moveTo(this.var_312, this.var_356, this.var_3fe);
                  this.var_973 = false;
               }
            } else if (var4 > 35000 && this.passedTime > 10000) {
               var6 = GameStatus.random.nextInt(20000) - 10000;
               var4 = GameStatus.random.nextInt(20000) - 10000;
               var7 = GameStatus.random.nextInt(2000) + 30000;
               this.passedTime = 0;
               var_1c5.set(var6, var4, var7);
               if (this.level.getPlayerEgo() != null) {
                  this.var_80b = this.level.getPlayerEgo().var_50e.sub_8a0();
               } else {
                  this.var_80b.identity();
               }

               this.positon = this.var_80b.sub_a19(var_1c5, this.positon);
               this.var_25d.sub_1f3(this.positon);
               this.var_312 = this.positon.x;
               this.var_356 = this.positon.y;
               this.var_3fe = this.positon.z;
               this.sub_34(true);
               this.sub_296();
            }

            if (this.var_956 != null) {
               this.var_956.sub_8c9(this.var_25d.sub_85f());
            }

         }
      }
   }

   private void sub_296() {
      this.var_727.set(this.var_312, this.var_356, this.var_3fe);
      this.positon.set(var_793);
      this.positon.subtract(this.var_727, var_1c5);
      float var1 = (float)AEMath.max(0, 100000 - var_1c5.getLength()) / 100000.0F;
      int var2 = (int)((float)this.var_54b * var1);
      int var3 = (int)((float)this.var_694 * var1);
      int var4 = (int)((float)this.var_707 * var1);
      if (var2 + var3 + var4 < 256) {
         this.var_880 = 0;
         this.var_8aa = 0;
         this.sub_34(false);
      } else {
         this.var_25d.sub_7af(var2, var3, var4);
         this.var_880 = (int)((float)((var2 + var3 + var4) / 3) / 3072.0F * 100.0F);
         this.var_8aa = AEMath.min(7, 2 + (int)((float)(this.var_880 + 15) / 100.0F * 5.0F));
      }
   }

   public final void sub_109doSth() {
      if (this.var_8aa > 0) {
         if (this.waste != null) {
            GameStatus.var_8ce.sub_87(this.waste);
         }

         if (this.state == 3) {
            GameStatus.var_8ce.sub_87(this.var_956);
            if (this.passedTime > 3000) {
               this.state = 4;
               return;
            }
         } else if (this.state == 0) {
            GameStatus.var_8ce.sub_87(this.var_25d);
         }
      }

   }

   public final boolean sub_1b8(AEVector3D var1) {
      return this.sub_162(var1.x, var1.y, var1.z);
   }

   public final AEVector3D sub_005(AEVector3D var1) {
      (var_249 = this.var_25d.sub_216(var_249)).subtract(var1);
      var1.set(var_249);
      var1.normalize();
      return var1;
   }

   public final boolean sub_162(int var1, int var2, int var3) {
      return (float)(var1 - this.var_312) < this.player.radius && (float)(var1 - this.var_312) > -this.player.radius && (float)(var2 - this.var_356) < this.player.radius && (float)(var2 - this.var_356) > -this.player.radius && (float)(var3 - this.var_3fe) < this.player.radius && (float)(var3 - this.var_3fe) > -this.player.radius;
   }
}
