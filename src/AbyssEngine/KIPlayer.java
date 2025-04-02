package AbyssEngine;

public abstract class KIPlayer {
   protected short state = 0;
   protected int var_145 = 2;
   public Player player;
   public AbstractMesh var_25d;
   public Group var_379;
   protected int var_3ce;
   protected int var_3f3;
   protected int race;
   protected Level level;
   protected Class_661 var_50b;
   protected Explosion explosion;
   protected AEVector3D var_727 = new AEVector3D();
   protected AEVector3D positon = new AEVector3D();
   protected int targetX;
   protected int targetY;
   protected int targetZ;
   protected boolean var_907;
   public boolean isAsteroid;
   public boolean var_99f;
   public boolean var_9d8;
   protected int[] var_a3b;
   public AbstractMesh waste;
   protected AbstractMesh var_aa5;
   protected boolean var_af6;
   protected boolean var_be0;
   protected boolean var_c3a;
   protected int var_c61;
   protected boolean var_cc1;
   protected int var_cf1;
   protected int var_d43;
   protected KIPlayer var_d6c;
   protected boolean var_d9e;
   protected int var_dbe;
   public String name;
   public boolean var_e74;
   public boolean var_eb1;
   protected boolean var_ee3;
   public boolean var_efb = true;

   public KIPlayer(int var1, int var2, Player var3, AbstractMesh var4, int var5, int var6, int var7) {
      this.race = var2;
      this.player = var3;
      this.var_25d = var4;
      this.var_3ce = var1;
      if (this.var_25d != null) {
         this.var_25d.moveTo(var5, var6, var7);
         this.var_25d.setRotation(0, 0, 0);
         this.player.var_46b = var4.sub_85f();
      }

      this.player.setKIPlayer(this);
      this.var_eb1 = false;
      this.targetX = var5;
      this.targetY = var6;
      this.targetZ = var7;
      this.isAsteroid = false;
      this.var_99f = false;
      this.var_9d8 = false;
      this.var_907 = false;
      this.var_af6 = false;
      this.var_be0 = false;
      this.var_c3a = false;
      this.var_cc1 = false;
      if (this.var_727 == null) {
         this.var_727 = new AEVector3D();
      }

      this.var_d9e = false;
      this.name = null;
      this.var_ee3 = true;
   }

   public final boolean sub_63() {
      return this.var_ee3;
   }

   public final void sub_73(boolean var1) {
      this.var_ee3 = var1;
      if (this.var_379 != null) {
         this.var_379.setDraw(var1);
      }

      if (this.var_25d != null) {
         this.var_25d.setDraw(var1);
      }

   }

   public final void sub_ca(Group var1, int var2) {
      this.var_3f3 = var2;
      this.var_379 = var1;
      this.var_379.moveTo(this.targetX, this.targetY, this.targetZ);
      this.var_379.setRotation(0, 0, 0);
      this.player.var_46b = var1.sub_85f();
   }

   public final void sub_ea(boolean var1) {
      this.var_d9e = true;
   }

   public final boolean sub_279() {
      return this.var_d9e;
   }

   public void destroy() {
      this.player = null;
      if (this.var_25d != null) {
         this.var_25d.destroy();
      }

      this.var_25d = null;
      this.var_379 = null;
      this.level = null;
      this.var_50b = null;
      if (this.explosion != null) {
         this.explosion.sub_fa();
      }

      this.explosion = null;
      this.var_aa5 = null;
   }

   public void sub_34(boolean var1) {
      this.player.sub_a98(var1);
   }

   public final void sub_2ab(boolean var1) {
      this.sub_34(false);
   }

   public final void sub_2cb(Hud var1) {
      if (this.isOutOfScope() || this.isCrate()) {
         this.var_9d8 = false;
         this.sub_34(false);
      }

      this.waste = null;
      Item var2 = null;

      for(int var3 = 0; var3 < this.var_a3b.length; var3 += 2) {
         if (this.var_a3b[var3 + 1] > 0) {
            int var4 = AEMath.max(1, GameStatus.random.nextInt(this.var_a3b[var3 + 1]));
            var2 = IndexManager.getItems()[this.var_a3b[var3]].anyAmountCopies(var4);
            int[] var10000 = this.var_a3b;
            var10000[var3 + 1] -= var4;
            break;
         }
      }

      if (var2 != null) {
         if (this.player.var_427) {
            this.level.sub_ac1();
         }

         Status.getStanding().incStanding(this.race);
         boolean var5 = this.var_907 && (var2.getIndex() == 116 || var2.getIndex() == 117);
         if (Status.getShip().sub_839(var2.getAmount())) {
            Status.incCargoSalvaged(var2.getAmount());
            if (var5) {
               var2.sub_986(true);
            }

            Status.getShip().addItem(var2);
            Level var6 = this.level;
            var6.var_a19 += var2.getAmount();
            if (var5) {
               this.var_be0 = true;
            } else if (this.race == 9) {
               Status.alienJunkSalvaged += var2.getAmount();
            } else if (var2.getIndex() >= 132 && var2.getIndex() < 154) {
               Status.drinksVar1[var2.getIndex() - 132] = true;
            }

            var1.sub_3ae(var2.getIndex(), var2.getAmount(), false, var5, false, false);
            return;
         }

         if (var5) {
            this.var_af6 = true;
         }

         var1.sub_3ae(var2.getIndex(), var2.getAmount(), true, false, false, false);
      }

   }

   public final boolean sub_32a() {
      return this.var_cc1;
   }

   public final void sub_366(boolean var1, int var2) {
      this.var_cc1 = true;
      this.var_cf1 = var2;
      this.var_d43 = 2;
   }

   public void sub_16f(int var1, KIPlayer var2) {
      this.var_d43 = var1;
      this.var_d6c = var2;
   }

   public void sub_1515sth(int var1) {
      this.var_145 = var1;
   }

   public final void sub_3b3(Level var1) {
      this.level = var1;
   }

   public final void sub_3c7(Explosion var1) {
      this.explosion = var1;
   }

   public final void sub_3d9(Class_661 var1) {
      this.var_50b = var1;
   }

   public final Class_661 sub_3ea() {
      return this.var_50b;
   }

   public final void sub_43a(Gun var1, int var2) {
      Player var10000 = this.player;
      int var3 = var2;
      Gun var5 = var1;
      Player var4 = var10000;
      if (var10000.guns != null && var3 <= 3 && var3 >= 0) {
         var4.guns[var3] = new Gun[1];
         var4.guns[var3][0] = var5;
      }

   }

   public final int sub_4a3() {
      return this.var_3ce;
   }

   public AEVector3D sub_a9(AEVector3D var1) {
      if (this.var_25d != null) {
         return this.var_25d.sub_22a(var1);
      } else {
         return this.var_379 != null ? this.var_379.sub_22a(var1) : null;
      }
   }

   public final boolean isOutOfScope() {
      return this.state == 4;
   }

   public final boolean isCrate() {
      return this.state == 3;
   }

   public final void sub_52b() {
      this.state = 4;
      this.sub_34(false);
   }

   public final void sub_5cc() {
      this.state = 5;
      this.player.sub_a98(false);
   }

   public final void sub_5fc() {
      this.state = 1;
      this.player.sub_a98(true);
   }

   public final void sub_654(AbstractMesh var1) {
      this.var_aa5 = var1;
   }

   public final void setWasteMesh(int var1) {
      if (var1 == 0) {
         this.waste = AEResourceManager.getGeometryResource(17);
         this.waste.sub_7af(16384, 16384, 16384);
      } else if (var1 == 2) {
         this.waste = AEResourceManager.getGeometryResource(9996);
         this.waste.sub_7af(2048, 2048, 2048);
      } else if (var1 == 3) {
         this.waste = AEResourceManager.getGeometryResource(6767);
         this.waste.sub_7af(2048, 2048, 2048);
      } else {
         this.waste = AEResourceManager.getGeometryResource(6769);
         this.waste.sub_7af(512, 512, 512);
      }

      this.waste.setRenderLayer(2);
      this.waste.sub_1f3(this.var_25d != null ? this.var_25d.sub_237() : this.var_379.sub_237());
      this.player.var_46b = this.waste.sub_85f();
      this.player.setKIPlayer(this);
   }

   public final boolean sub_6ef() {
      boolean var1 = false;
      if (this.var_a3b != null) {
         for(int var2 = 0; var2 < this.var_a3b.length; var2 += 2) {
            if (this.var_a3b[var2 + 1] > 0) {
               var1 = true;
               break;
            }
         }
      }

      return var1;
   }

   public abstract void update(long var1);

   public abstract boolean sub_162(int var1, int var2, int var3);

   public boolean sub_1b8(AEVector3D var1) {
      return false;
   }

   public AEVector3D sub_005(AEVector3D var1) {
      return null;
   }

   public void sub_2e5() {
   }

   public void setPosition(int var1, int var2, int var3) {
   }

   public void sub_109doSth() {
      if (this.var_25d != null) {
         GameStatus.var_8ce.sub_87(this.var_25d);
      } else {
         GameStatus.var_8ce.sub_87(this.var_379);
      }
   }
}
