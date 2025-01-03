package AbyssEngine;

public final class PlayerBigShip extends KIPlayer {
   private AbstractBounding[] bounds;
   private long frametime;
   private boolean var_87;
   private AEVector3D var_eb = new AEVector3D();
   private AEVector3D var_114 = new AEVector3D();
   private AEVector3D var_177 = new AEVector3D();
   private AEVector3D var_1ee = new AEVector3D();
   private AEVector3D var_263 = new AEVector3D();
   private Player var_273;
   private int var_281;
   private boolean destroyed;
   private int var_31d;
   private AEVector3D[] var_34b;
   private AEVector3D[] var_361;
   private int posX;
   private int posY;
   private int posZ;

   public PlayerBigShip(int var1, int var2, Player var3, AbstractMesh var4, int var5, int var6, int var7) {
      super(var1, var2, var3, (AbstractMesh)null, var5, var6, var7);
      this.var_eb.set(var5, var6, var7);
      this.posX = var5;
      this.posY = var6;
      this.posZ = var7;
      this.var_87 = false;
      if (!Status.getMission().isCampaignMission() || Status.getCurrentCampaignMission() != 40 && Status.getCurrentCampaignMission() != 41) {
         Generator var8 = new Generator();
         this.var_a3b = var8.getLootList();
         if (this.var_a3b != null) {
            for(var2 = 0; var2 < this.var_a3b.length; var2 += 2) {
               int[] var10000;
               if (var1 == 14) {
                  var10000 = this.var_a3b;
                  var10000[var2 + 1] *= 5 + GameStatus.random.nextInt(8);
               } else {
                  var10000 = this.var_a3b;
                  var10000[var2 + 1] *= 1 + GameStatus.random.nextInt(4);
               }
            }
         }
      } else {
         this.var_a3b = null;
      }

      this.player.invincible_ = true;
   }

   public final void sub_22() {
      super.destroy();
      this.bounds = null;
      this.var_273 = null;
      this.var_eb = null;
      this.var_114 = null;
      this.var_177 = null;
      this.var_1ee = null;
   }

   public final void sub_59(boolean var1) {
      this.var_87 = var1;
   }

   public final void setPosition(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
      this.targetX = var1;
      this.targetY = var2;
      this.targetZ = var3;
      this.var_379.moveTo(var1, var2, var3);
      this.player.var_46b = this.var_379.sub_85f();
      this.var_eb = this.var_379.sub_22a(this.var_eb);
      this.var_114 = this.var_379.sub_22a(this.var_114);
      if (this.bounds != null) {
         for(var1 = 0; var1 < this.bounds.length; ++var1) {
            this.bounds[var1].sub_e2(this.player.var_46b.getPositionX(), this.player.var_46b.getPositionY(), this.player.var_46b.getPositionZ());
         }
      }

   }

   public final void setBounds(AbstractBounding[] var1) {
      this.bounds = var1;
   }

   public final AEVector3D sub_a9(AEVector3D var1) {
      var1.set(this.posX, this.posY, this.posZ);
      return var1;
   }

   public final void sub_da(int var1) {
      this.posZ += var1;
      this.var_379.sub_202(var1);
      this.player.var_46b = this.var_379.sub_85f();
      this.var_eb = this.var_379.sub_22a(this.var_eb);
      if (this.bounds != null) {
         for(var1 = 0; var1 < this.bounds.length; ++var1) {
            this.bounds[var1].sub_e2(this.player.var_46b.getPositionX(), this.player.var_46b.getPositionY(), this.player.var_46b.getPositionZ());
         }
      }

   }

   public final void update(long var1) {
      this.frametime = var1;
      this.player.sub_c78(var1);
      this.player.var_3df = this.race != 8 && this.race != 9 ? Status.getStanding().isEnemy(this.race) : true;
      this.player.var_427 = this.race != 8 && this.race != 9 ? Status.getStanding().isFriend(this.race) : false;
      if (this.player.sub_992()) {
         this.player.var_3df = true;
         this.player.var_427 = false;
      }

      if (this.player.isAlwaysFriend()) {
         this.player.var_427 = true;
         this.player.var_3df = false;
      }

      if (this.state != 6) {
         float var3 = this.player.sub_892();
         float var4 = this.player.sub_8c2();
         if (var3 > 0.0F) {
            if ((var3 *= 0.98F) < 0.05F) {
               var3 = 0.0F;
            }

            this.player.setNukeStun(var3);
         }

         if (var4 > 0.0F) {
            this.var_e74 = true;
            if ((var4 -= (float)var1) < 0.05F) {
               var4 = 0.0F;
               this.var_e74 = false;
            }

            this.player.sub_84b(var4);
         }
      }

      if (!this.var_e74 && this.var_87 && this.state != 3 && this.state != 4) {
         this.sub_da((int)var1);
      }

      this.var_727 = GameStatus.var_8ce.getCamera().sub_22a(this.var_727);
      this.positon.set(this.posX, this.posY, this.posZ);
      this.positon.subtract(this.var_727, this.var_263);
      int var6;
      int var9;
      if ((var6 = this.var_263.getLength()) > 28000) {
         this.var_379.sub_8c9(this.var_379.sub_85f());
         this.var_263.normalize();
         this.var_263.scale(28000);
         this.var_263.add(this.var_727);
         this.var_379.sub_1f3(this.var_263);
         var9 = (int)(28000.0F / (float)var6 * 4096.0F);
         this.var_379.sub_7af(var9, var9, var9);
      } else {
         this.var_379.sub_7af(4096, 4096, 4096);
         this.var_379.moveTo(this.posX, this.posY, this.posZ);
      }

      GraphNode var8;
      if (this.player.getHitpoints() <= 0 && this.state != 3 && this.state != 4) {
         if (this.player.var_3df) {
            this.level.sub_753(this.player.var_54e);
         } else {
            this.level.sub_7c2();
         }

         this.state = 3;
         if (this.explosion != null) {
            this.positon = this.var_379.sub_22a(this.positon);
            this.explosion.sub_141(this.positon.x, this.positon.y, this.positon.z);
         }

         this.var_9d8 = this.sub_6ef();
         if (this.var_9d8) {
            this.setWasteMesh(0);
         }

         boolean var7 = false;

         for(var8 = this.var_379.sub_97(); var8 != null; var8 = var8.sub_007()) {
            if (var8.sub_a() == 13067 || var8.sub_a() == 13068 || var8.sub_a() == 13070 || var8.sub_a() == 13064 || var8.sub_a() == 13065 || var8.sub_a() == 13071) {
               var8.setDraw(false);
            }
         }

         GameStatus.soundManager.playSfx(1);
         this.var_31d = 10000;
         var9 = 0;

         for(var8 = this.var_379.sub_97(); var8 != null; var8 = var8.sub_007()) {
            ++var9;
         }

         this.destroyed = var9 > 3;
         this.var_34b = new AEVector3D[var9];
         this.var_361 = new AEVector3D[var9];
         var6 = 0;

         for(GraphNode var5 = this.var_379.sub_97(); var5 != null; var5 = var5.sub_007()) {
            this.var_727 = var5.sub_85f().getPosition(this.var_727);
            this.var_727.normalize();
            this.var_727.scale(100 + GameStatus.random.nextInt(100));
            this.var_34b[var6] = new AEVector3D(this.var_727);
            this.var_361[var6] = new AEVector3D(-10 + GameStatus.random.nextInt(20), -10 + GameStatus.random.nextInt(20), -10 + GameStatus.random.nextInt(20));
            ++var6;
         }
      }

      boolean var11 = false;
      int var10;
      switch(this.state) {
      case 3:
         var11 = true;
         if (this.explosion != null) {
            if (this.explosion.sub_185()) {
               this.state = 4;
            }
         } else {
            this.state = 4;
         }
         break;
      case 4:
         var11 = true;
         this.var_c61 = (int)((long)this.var_c61 + var1);
         if (this.var_9d8 && this.player.sub_ace() && this.waste != null) {
            var6 = (int)var1 >> 1;
            this.waste.sub_5c5(var6, var6, var6);
            if (this.var_c61 > 45000 && this.explosion != null) {
               if (this.var_c61 < 90000) {
                  this.explosion.sub_141(this.var_eb.x, this.var_eb.y, this.var_eb.z);
                  if (this.level.getPlayerEgo().var_15f4.var_fdf == this) {
                     this.level.getPlayerEgo().var_15f4.var_fdf = null;
                  }

                  this.var_c61 = 90000;
               } else if (this.explosion.sub_185()) {
                  this.waste = null;
                  this.var_c61 = 0;
                  this.sub_34(false);
               }
            }
         } else if (this.var_c61 > 45000) {
            this.sub_34(false);
         }
         break;
      case 5:
         Player[] var12;
         if (this.state != 4 && this.state != 3 && (var12 = this.player.getCoPlayers()) != null) {
            this.var_273 = null;

            for(var10 = 0; var10 < var12.length; ++var10) {
               if (var12[var10].sub_ace()) {
                  this.var_727 = var12[var10].sub_a3c(this.var_727);
                  if (this.var_eb.x - this.var_727.x < 50000 && this.var_eb.x - this.var_727.x > -50000 && this.var_eb.y - this.var_727.y < 50000 && this.var_eb.y - this.var_727.y > -50000 && this.var_eb.z - this.var_727.z < 50000 && this.var_eb.z - this.var_727.z > -50000) {
                     this.var_273 = this.player.sub_2f3(var10);
                     this.var_727 = this.var_273.sub_a3c(this.var_727);
                     this.var_177.x = this.var_727.x;
                     this.var_177.y = this.var_727.y;
                     this.var_177.z = this.var_727.z;
                     break;
                  }
               }
            }
         }

         this.var_1ee.x = this.var_177.x - this.var_eb.x;
         this.var_1ee.y = this.var_177.y - this.var_eb.y;
         this.var_1ee.z = this.var_177.z - this.var_eb.z;
         if (this.var_1ee.x < 50000 && this.var_1ee.x > -50000 && this.var_1ee.y < 50000 && this.var_1ee.y > -50000 && this.var_1ee.z < 50000 && this.var_1ee.z > -50000) {
            this.state = 1;
            this.player.sub_a98(true);
         }
      }

      if (var11) {
         var6 = (int)var1 >> 1;
         this.var_31d -= var6;
         if (this.var_31d < 0) {
            this.var_31d = 0;
         }

         var10 = 1;

         for(var8 = this.var_379.sub_97(); var8 != null; var8 = var8.sub_007()) {
            this.var_34b[var10 - 1].scale(4096 - (int)var1);
            var8.sub_19c(this.var_34b[var10 - 1]);
            var8.sub_5c5(this.var_361[var10 - 1].x, this.var_361[var10 - 1].y, this.var_361[var10 - 1].z);
            ++var10;
         }
      }

      this.player.var_7ae = this.posX;
      this.player.var_803 = this.posY;
      this.player.var_83e = this.posZ;
   }

   public final void sub_109doSth() {
      if (this.waste != null) {
         GameStatus.var_8ce.sub_87(this.waste);
      }

      if (this.state == 3 || this.state == 4) {
         if (this.explosion != null && !this.explosion.sub_185()) {
            this.explosion.sub_179(this.frametime);
         }

         if (this.destroyed) {
            GameStatus.var_8ce.sub_87(this.var_379);
            return;
         }
      }

      if (this.player.sub_ace()) {
         GameStatus.var_8ce.sub_87(this.var_379);
      }
   }

   public final boolean sub_162(int var1, int var2, int var3) {
      if (this.state != 4 && this.state != 4) {
         for(int var4 = 0; var4 < this.bounds.length; ++var4) {
            if (this.bounds[var4].sub_4f(var1, var2, var3)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public final AEVector3D sub_005(AEVector3D var1) {
      return this.bounds != null ? this.bounds[this.var_281].sub_3d(var1) : null;
   }

   public final boolean sub_1b8(AEVector3D var1) {
      int var4 = var1.z;
      int var3 = var1.y;
      int var2 = var1.x;
      PlayerBigShip var6 = this;
      if (this.state != 4) {
         for(int var5 = 0; var5 < var6.bounds.length; ++var5) {
            if (var6.bounds[var5].sub_a0(var2, var3, var4)) {
               var6.var_281 = var5;
               return true;
            }
         }
      }

      return false;
   }
}
