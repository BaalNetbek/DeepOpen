package AbyssEngine;

public final class PlayerWormHole extends PlayerStaticFar {
   private int var_6b;
   private int var_81;

   public PlayerWormHole(int var1, AbstractMesh var2, int var3, int var4, int var5, boolean var6) {
      super(6805, var2, var3, var4, var5);
      this.name = GameStatus.langManager.getLangString(269);
      this.sub_73(var6);
      this.var_379 = new Group();
      if (var6) {
         this.var_25d.sub_918(30);
         this.var_25d.setRotation(-128, -128, 0);
         this.var_25d.sub_9aa((byte)2);
      }

      this.var_379.sub_25(this.var_25d);
      this.var_25d.moveTo(0, 0, 0);
      this.var_379.moveTo(var3, var4, var5);
      char var7 = '鱀';
      this.player.radius = (float)var7;

      for(var1 = 0; var1 < 10; ++var1) {
         (var2 = AEResourceManager.getGeometryResource(6806)).sub_8c9(this.var_25d.sub_85f());
         var2.roll((var1 + 1) * GameStatus.random.nextInt(400));
         var2.sub_9aa((byte)2);
         var2.sub_918(20 + GameStatus.random.nextInt(50));
         this.var_379.sub_25(var2);
      }

      this.sub_55(false);
   }

   public final void sub_55(boolean var1) {
      this.var_6b = var1 ? '願' : 0;
      this.var_81 = 4096;
   }

   public final boolean sub_b7() {
      return this.var_6b > 40000;
   }

   public final void setPosition(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
      this.var_379.moveTo(var1, var2, var3);
   }

   public final void sub_109doSth() {
      if (this.var_ee3) {
         GameStatus.renderer.sub_87(this.var_379);
      }

   }

   public final void update(long var1) {
      if (this.var_ee3) {
         this.var_6b = (int)((long)this.var_6b + var1);
         int var2;
         int var5;
         if (this.var_6b < 0) {
            this.var_81 = 4096 - (int)((float)(-this.var_6b) / 3000.0F * 4096.0F);
            if (this.var_6b >= 0) {
               this.var_81 = 4096;
            }
         } else if (this.var_6b > 40000) {
            if ((var5 = Status.getCurrentCampaignMission()) == 40 || var5 == 42) {
               this.var_6b = 40000;
            }

            this.var_81 = 4096 - (int)((float)(this.var_6b - '鱀') / 3000.0F * 4096.0F);
            if (this.var_6b > 43000) {
               if (!Status.inAlienOrbit() && !Status.getStation().sub_fc()) {
                  this.var_ee3 = false;
                  this.var_379.setDraw(false);
               } else {
                  this.var_6b = -3000;
                  var2 = (20000 + GameStatus.random.nextInt(40000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1);
                  int var3 = (20000 + GameStatus.random.nextInt(40000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1);
                  int var4 = (20000 + GameStatus.random.nextInt(40000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1);
                  if (var5 == 29 || var5 == 41) {
                     this.var_727 = this.level.getPlayerEgo().sub_8c1();
                     var2 += this.var_727.x + var2 * 3;
                     var3 += this.var_727.y + var3 * 3;
                     var4 += this.var_727.z + var4 * 3;
                  }

                  this.setPosition(var2, var3, var4);
                  if (this.level.getPlayerEgo().sub_a27()) {
                     this.level.getPlayerEgo().sub_12ab().sub_30d(6, this.level.getPlayerEgo());
                     this.level.getPlayerEgo().sub_97e((KIPlayer)null);
                  }
               }
            }
         }

         this.var_727 = GameStatus.renderer.getCamera().sub_22a(this.var_727);
         this.positon.set(this.posX, this.posY, this.posZ);
         this.positon.subtract(this.var_727, var_2e9);
         if ((var5 = var_2e9.getLength()) > 28000) {
            var_2e9.normalize();
            var_2e9.scale(28000);
            var_2e9.add(this.var_727);
            this.var_379.sub_1f3(var_2e9);
            var2 = (int)(28000.0F / (float)var5 * (float)this.var_81);
            this.var_379.sub_7af(var2, var2, var2);
         } else {
            this.var_379.sub_7af(this.var_81, this.var_81, this.var_81);
            this.var_379.moveTo(this.posX, this.posY, this.posZ);
         }

         var_2e9 = this.var_379.sub_237();
         this.var_727.subtract(var_2e9);
         this.var_727.normalize();
         var_2e9.set(0, 4096, 0);
         this.positon = var_2e9.crossProduct(this.var_727, this.positon);
         this.positon.normalize();
         (var_2e9 = this.positon.crossProduct(this.var_727, var_2e9)).normalize();
         this.var_379.sub_90e(this.positon, var_2e9, this.var_727);
      }

   }

   public final AEVector3D sub_a9(AEVector3D var1) {
      var1.x = this.posX;
      var1.y = this.posY;
      var1.z = this.posZ;
      return var1;
   }
}
