package AbyssEngine;

public final class PlayerEgo {
   private static int boostSpeed = 5;
   private static int boostDuration = 5000;
   private static int boostLoadingTime = 20000;
   private static int[][] var_33c = new int[][]{{1, 0, 228, -72}, {7, 0, 215, 46}, {11, 0, 165, 147}, {17, 0, 224, 404}, {23, 0, 140, -21}, {24, 0, 195, 88}, {28, 0, 269, -69}, {33, 0, 255, -176}, {36, 0, 255, 1015}};
   public Player player;
   public Group var_50e;
   private Group var_5f8;
   private AbstractMesh var_62e;
   private AbstractMesh var_749;
   private AEVector3D vecUp;
   private AEVector3D vecRight;
   private DummyClass_ var_7a2;
   private Crosshair crosshair;
   private Explosion var_82c;
   public Level var_887;
   private Class_661 var_910;
   public int speed;
   private int relPitchSpeed;
   private int relYawSpeed;
   private int roll;
   private int currentSecondaryId = -1;
   private long var_b37;
   private long var_b53;
   private long var_b91;
   private int var_ba3;
   private boolean var_bb6;
   private long var_bd4;
   private int boostTime;
   private boolean boostActive;
   private int shipYaw;
   private boolean collisionsOn;
   private boolean beingPushedAway;
   private boolean boostingEnabled;
   private AEVector3D laggingPos;
   private Matrix var_d58 = new Matrix();
   private float handling;
   private int var_d98;
   private boolean autopilotActive;
   private KIPlayer autopilotTarget;
   private boolean asteroidFieldTarget_;
   private AEVector3D var_e91;
   private boolean var_eb4;
   private Class_c53cameraRelated var_ed8;
   private Camera var_ef5;
   private Group var_f22;
   private Group var_f7c;
   private Group var_fd2;
   private boolean lookBack = false;
   private float var_1016;
   private float var_106f;
   private boolean cloakOn;
   private Item cloak;
   private Class_a6e var_1142;
   private KIPlayer var_11a4;
   private boolean var_11b7;
   private int var_11d2;
   private AEVector3D var_1203;
   private boolean var_1256;
   private int var_1283;
   private int var_12ac;
   private int var_12fc;
   private int var_1358;
   private MiningGame miningGame;
   private boolean var_1399;
   private boolean var_13c9;
   private boolean var_13df;
   private int var_140d;
   private boolean var_141f;
   private int var_1475;
   private int cloakTime;
   private int cloakDuration;
   private int cloakLoadingTime;
   private float var_1562;
   private Hud var_15a9;
   public MGameContext var_15f4;
   public Class_23e var_1653;
   private boolean var_1686;
   private int[][] var_16c0;
   private int var_16ce;
   private boolean var_16ef;

   public PlayerEgo(Player var1) {
      this.player = var1;
      this.player.sub_169(true);
      this.vecUp = new AEVector3D();
      this.vecRight = new AEVector3D();
      this.var_e91 = new AEVector3D();
      this.laggingPos = new AEVector3D();
      this.roll = 1;
      this.var_7a2 = new DummyClass_();
      this.crosshair = new Crosshair();
      this.var_ba3 = this.player.sub_4f2();
      this.speed = 2;
      this.boostActive = false;
      this.collisionsOn = true;
      this.autopilotActive = false;
      this.autopilotTarget = null;
      boostLoadingTime = Status.getShip().getBoostRegenTime();
      boostSpeed = 2 + (int)((float)Status.getShip().getBoostPercent() / 100.0F * 2.0F);
      boostDuration = Status.getShip().getBoostDuration();
      this.boostingEnabled = boostLoadingTime > 0;
      this.handling = Status.getShip().getHandling();
      this.var_d98 = Status.getShip().getAddHandlingPercent();
      this.handling += (float)this.var_d98 / 100.0F;

      try {
         this.var_1562 = (float)Status.getShip().getShield() / ((float)Status.getShip().getShieldRegenTime() / 100.0F);
      } catch (Exception var2) {
         this.var_1562 = 0.0F;
      }

      this.var_11b7 = false;
      this.var_1256 = false;
      this.var_1686 = false;
      this.cloak = Status.getShip().getEquipedOfSubType(21);
      this.cloakTime = -1;
      this.cloakDuration = this.cloak == null ? 0 : this.cloak.getAttribute(33);
      this.cloakLoadingTime = this.cloak == null ? 0 : this.cloak.getAttribute(34);
      this.cloakOn = false;
      this.var_141f = false;
   }

   public final void sub_54(int var1, int var2) {
      this.var_50e = new Group();
      this.var_5f8 = IndexManager.buildShip_(var1, var2);
      var1 = 0;

      for(GraphNode var4 = this.var_5f8.sub_97(); var4 != null; var4 = var4.sub_007()) {
         if (var4.sub_a() == 13067 || var4.sub_a() == 13068 || var4.sub_a() == 13070 || var4.sub_a() == 13064 || var4.sub_a() == 13065 || var4.sub_a() == 13071) {
            ++var1;
         }
      }

      if (var1 > 0) {
         this.var_16c0 = new int[var1][3];
      }

      var2 = 0;

      for(GraphNode var3 = this.var_5f8.sub_97(); var3 != null; var3 = var3.sub_007()) {
         if (var3.sub_a() == 13067 || var3.sub_a() == 13068 || var3.sub_a() == 13070 || var3.sub_a() == 13064 || var3.sub_a() == 13065 || var3.sub_a() == 13071) {
            this.vecUp = var3.sub_7ff(this.vecUp);
            this.var_16c0[var2][0] = this.vecUp.x;
            this.var_16c0[var2][1] = this.vecUp.y;
            this.var_16c0[var2][2] = this.vecUp.z;
            ++var2;
         }
      }

      this.var_50e.sub_25(this.var_5f8);
      if (Status.getShip().getEquipedOfSubType(13) != null) {
         this.var_1142 = new Class_a6e(this.var_50e, Status.getShip().getEquipedOfSubType(13).getIndex() - 68);
      }

   }

   public final void turnEngines_(boolean var1) {
      for(GraphNode var2 = this.var_5f8.sub_97(); var2 != null; var2 = var2.sub_007()) {
         if (var2.sub_a() == 13067 || var2.sub_a() == 13068 || var2.sub_a() == 13070 || var2.sub_a() == 13064 || var2.sub_a() == 13065 || var2.sub_a() == 13071) {
            var2.setDraw(var1);
         }
      }

   }

   public final void sub_127() {
      this.var_ba3 = this.player.sub_4f2();
   }

   public final boolean toggleCloaking_() {
      if (this.cloakOn) {
         this.player.hidden = false;
         this.cloakOn = false;
      } else if (this.cloakTime < 0) {
         this.player.hidden = true;
         this.cloakOn = true;
         this.cloakTime = 0;
      }

      return this.cloakOn;
   }

   public final boolean isCloakPresent() {
      return this.cloak != null;
   }

   public final boolean isLookingBack() {
      return this.lookBack;
   }

   public final void sub_295(Class_c53cameraRelated var1, Class_fd6 var2) {
      this.var_ed8 = var1;
   }

   public final boolean sub_2b9(boolean var1) {
      if (this.var_eb4 && !this.sub_be6()) {
         this.lookBack = var1;
         if (var1) {
            if (this.var_ef5 == null) {
               this.var_ef5 = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 700, 300, 200000);
               this.var_ef5.sub_18f(0, 500, -1400);
               this.var_ef5.sub_5c5(0, 2048, 0);
               this.var_f22 = new Group();
               this.var_f22.sub_25(this.var_ef5);
               this.var_f22.sub_5a7((short)2);
               this.var_f22.sub_18f(var_33c[this.var_16ce][1], var_33c[this.var_16ce][2], var_33c[this.var_16ce][3]);
               this.var_f22.sub_5c5(0, 2048, 0);
               this.var_f7c = new Group();
               this.var_f7c.sub_25(this.var_f22);
            }

            this.var_f7c.sub_1f3(this.laggingPos);
            this.var_f7c.sub_8c9(this.var_50e.sub_85f());
            GameStatus.renderer.sub_19(this.var_ef5);
         } else {
            GameStatus.renderer.sub_19((Camera)this.var_ed8.sub_13c());
            Class_7flight.sub_1b8(this.var_ed8, this.var_887);
         }

         return true;
      } else {
         return false;
      }
   }

   public final void sub_308() {
      if (!this.boostActive && this.boostingEnabled && this.boostTime >= 0) {
         GameStatus.soundManager.playSfx(0);
         this.boostTime = 0;
         this.speed = boostSpeed;
         this.boostActive = true;
      }

   }

   public final void sub_327() {
      this.boostTime = 0;
      this.boostActive = true;
      this.speed = 8;
      boostDuration = 10000;
      boostLoadingTime = 0;
   }

   public final int getMiningProgress() {
      return this.miningGame != null ? this.miningGame.getMiningProgressRounded() : 0;
   }

   public final boolean isBoostActive() {
      return this.boostActive;
   }

   public final boolean sub_3c9() {
      return this.boostTime >= 0;
   }

   public final float getCurrentBoostedSpeed() {
      float var1;
      if ((var1 = (float)this.boostTime / (float)(boostDuration / 6)) < 1.0F) {
         return var1;
      } else {
         return var1 > 5.0F ? 6.0F - var1 : 1.0F;
      }
   }

   public final float getBoostCharge() {
      float var1;
      return (var1 = (float)this.boostTime / (float)boostLoadingTime) > 0.0F ? 1.0F : var1 + 1.0F;
   }

   public final float getCloakCharge() {
      float var1;
      return (var1 = this.cloakOn ? (float)this.cloakTime / (float)this.cloakDuration : (float)this.cloakTime / (float)this.cloakLoadingTime) > 0.0F ? var1 : 1.0F;
   }

   public final boolean sub_4d2() {
      return !this.cloakOn && this.cloakTime == -1;
   }

   public final boolean sub_4ed() {
      return this.cloakOn;
   }

   public final void sub_534(boolean var1) {
      this.collisionsOn = var1;
   }

   public final void sub_591(Level var1) {
      this.var_887 = var1;
   }

   public final void sub_5f1(Class_661 var1) {
      this.var_910 = var1;
   }

   public final Class_661 sub_647() {
      return this.var_910;
   }

   public final void sub_66d() {
      this.var_910 = null;
   }

   private boolean sub_6ae() {
      return this.player.getHitpoints() <= 0;
   }

   public final void sub_6c9(int var1, int var2) {
      if (this.lookBack) {
         this.var_d58.set(this.var_749.sub_8a0());
         this.player.sub_b96(2, (long)var1, false, this.var_d58);
      } else {
         if (var2 == 1) {
            if (!this.player.sub_c22(var2, this.currentSecondaryId, (long)var1, false)) {
               this.currentSecondaryId = -1;
               return;
            }
         } else {
            this.player.sub_bc8(var2, (long)var1, false);
         }

      }
   }

   public final void sub_721(Gun[] var1, int var2) {
      Player var6;
      if ((var6 = this.player).guns != null && var2 <= 3 && var2 >= 0) {
         var6.guns[var2] = new Gun[var1.length];
         var6.guns[var2] = var1;
      }

      if (!this.var_eb4) {
         this.var_eb4 = this.player.sub_9e5(2);
         if (this.var_eb4) {
            Item[] var8 = Status.getShip().getEquipment(2);
            short var9 = -1;
            short var4 = -1;
            byte var5 = -1;
            this.var_1475 = var8[0].getAttribute(15);
            switch(var8[0].getIndex()) {
            case 47:
               var9 = 6770;
               var4 = 6771;
               var5 = 91;
               break;
            case 48:
               var9 = 6772;
               var4 = 6773;
               var5 = 75;
               break;
            case 49:
               var9 = 6774;
               var4 = 6775;
               var5 = 121;
            }

            this.var_62e = AEResourceManager.getGeometryResource(var9);
            this.var_749 = AEResourceManager.getGeometryResource(var4);
            this.var_62e.setRenderLayer(2);
            this.var_749.setRenderLayer(2);
            this.var_749.sub_5a7((short)2);
            this.var_fd2 = new Group();

            for(var2 = 0; var2 < var_33c.length; ++var2) {
               if (var_33c[var2][0] == Status.getShip().getIndex()) {
                  this.var_62e.moveTo(var_33c[var2][1], var_33c[var2][2], var_33c[var2][3]);
                  this.var_749.moveTo(var_33c[var2][1], var_33c[var2][2], var_33c[var2][3]);
                  this.var_16ce = var2;
                  break;
               }
            }

            this.var_749.sub_18f(0, var5, 0);
            this.var_62e.sub_5c5(0, 2048, 0);
            this.var_749.sub_5c5(0, 2048, 0);
            this.var_fd2.sub_25(this.var_62e);
            this.var_fd2.sub_25(this.var_749);
         }
      }

   }

   public final int getHP() {
      return this.player.getHitpoints();
   }

   public final int sub_794() {
      return this.player.sub_6d4();
   }

   public final int sub_82f() {
      return this.currentSecondaryId;
   }

   public final void sub_863(int var1) {
      this.currentSecondaryId = var1;
   }

   public final AEVector3D sub_8c1() {
      return this.var_50e.sub_22a(this.vecUp);
   }

   public final void sub_8f2(boolean var1) {
      this.player.sub_a98(false);
   }

   public final void sub_93d(boolean var1) {
      this.var_141f = var1;
   }

   public final void sub_95f(boolean var1) {
      this.var_bb6 = var1;
   }

   public final void sub_97e(KIPlayer var1) {
      this.autopilotTarget = var1;
      this.asteroidFieldTarget_ = false;
      this.autopilotActive = this.autopilotTarget != null;
      if (this.autopilotTarget != null && this.autopilotTarget instanceof Class_e19) {
         this.asteroidFieldTarget_ = true;
      }

   }

   public final KIPlayer sub_998() {
      return this.autopilotTarget;
   }

   public final boolean sub_9f7() {
      return this.autopilotActive && !this.sub_a43() && !this.sub_a80() && !this.asteroidFieldTarget_;
   }

   public final boolean sub_a15() {
      return this.autopilotActive;
   }

   public final boolean sub_a27() {
      return this.autopilotTarget == this.var_887.sub_5b7()[3];
   }

   public final boolean sub_a43() {
      return this.autopilotTarget == this.var_887.sub_5b7()[1];
   }

   public final boolean sub_a80() {
      if (this.var_887.sub_5b7()[0] != null) {
         return this.autopilotTarget == this.var_887.sub_5b7()[0];
      } else {
         return false;
      }
   }

   public final boolean sub_aa7() {
      return this.asteroidFieldTarget_;
   }

   public final void sub_ae0(int var1, int var2, int var3) {
      this.var_50e.moveTo(var1, var2, var3);
      this.player.var_46b.translateTo(var1, var2, var3);
      this.vecUp.set(var1, var2, var3);
   }

   public final void sub_b32(AEVector3D var1) {
      this.var_50e.sub_1f3(var1);
      this.player.var_46b.translateTo(var1);
   }

   public final boolean sub_b5d() {
      return this.var_1686;
   }

   public final void sub_bb7(int var1, MGameContext var2, Hud var3, Class_23e var4, int var5) {
      if (this.var_15a9 == null) {
         this.var_15a9 = var3;
         this.var_15f4 = var2;
         this.var_1653 = var4;
      }

      if (!this.var_bb6) {
         this.laggingPos = this.var_50e.sub_22a(this.laggingPos);
         this.var_bd4 = (long)var1;
         if (this.boostTime < 0 && this.boostTime + var1 * 3 > 0) {
            var3.sub_30d(4, this);
            this.boostTime = 0;
         }

         this.boostTime += var1;
         int var9;
         if (this.boostActive) {
            var9 = 0;
            int var6 = (int)(this.getCurrentBoostedSpeed() * 2048.0F);

            GraphNode var7;
            int[] var8;
            for(var7 = this.var_5f8.sub_97(); var7 != null; var7 = var7.sub_007()) {
               if (var7.sub_a() == 13067 || var7.sub_a() == 13068 || var7.sub_a() == 13070 || var7.sub_a() == 13064 || var7.sub_a() == 13065 || var7.sub_a() == 13071) {
                  var8 = this.var_16c0[var9++];
                  var7.sub_7af(var8[0] + var6, var8[1] + var6, var8[2] + var6 * 2);
               }
            }

            if (this.boostTime > boostDuration) {
               var9 = 0;
               this.speed = 2;
               this.boostActive = false;
               this.boostTime = -boostLoadingTime;
               if (!this.boostingEnabled) {
                  boostDuration = 0;
                  boostSpeed = 2;
                  boostLoadingTime = 0;
               }

               for(var7 = this.var_5f8.sub_97(); var7 != null; var7 = var7.sub_007()) {
                  if (var7.sub_a() == 13067 || var7.sub_a() == 13068 || var7.sub_a() == 13070 || var7.sub_a() == 13064 || var7.sub_a() == 13065 || var7.sub_a() == 13071) {
                     var8 = this.var_16c0[var9++];
                     var7.sub_7af(var8[0], var8[1], var8[2]);
                  }
               }
            }
         }

         if (this.cloakOn) {
            if (this.var_1358 >= 0) {
               this.var_12fc = (int)((long)this.var_12fc + (this.var_bd4 << 3));
               this.var_1358 = (int)(-4.8828125E-4F * (float)((this.var_12fc - 4096) * (this.var_12fc - 4096)) + 8192.0F + 4096.0F);
               this.var_50e.sub_7af(this.var_1358, 4096, 4096);
            } else {
               this.var_50e.sub_7af(4096, 4096, 4096);
            }

            this.cloakTime = (int)((long)this.cloakTime + this.var_bd4);
            Status.invisibleTime += this.var_bd4;
            if (this.cloakTime > this.cloakDuration) {
               this.cloakTime = 0;
               this.cloakOn = false;
               this.player.hidden = false;
            }
         } else {
            if (this.var_12fc > 0) {
               this.var_12fc = (int)((long)this.var_12fc - (this.var_bd4 << 3));
               this.var_1358 = (int)(-4.8828125E-4F * (float)((this.var_12fc - 4096) * (this.var_12fc - 4096)) + 8192.0F + 4096.0F);
               this.var_50e.sub_7af(this.var_1358, 4096, 4096);
            } else {
               this.var_12fc = 0;
               this.var_50e.sub_7af(4096, 4096, 4096);
            }

            if (this.cloakTime >= 0) {
               this.cloakTime = (int)((long)this.cloakTime + this.var_bd4);
               if (this.cloakTime > this.cloakLoadingTime) {
                  this.cloakTime = -1;
               }
            }
         }

         if (this.var_1142 != null) {
            this.var_1142.sub_43(this.var_bd4, var2, this.var_887, var3);
         }

         this.var_ed8.sub_2e9((int)this.var_bd4);
         this.beingPushedAway = false;
         if (this.var_141f || this.var_13df || this.var_13c9) {
            this.beingPushedAway = true;
            if (this.var_13df) {
               this.var_140d = (int)((long)this.var_140d + this.var_bd4);
            }

            this.var_50e.sub_202(var1 * this.speed);
         }

         if (this.collisionsOn) {
            this.var_16ef = false;
            this.sub_c22(this.var_887.sub_5b7());
            this.sub_c22(this.var_887.getShips());
            this.sub_c22(this.var_887.sub_5cf());
         }

         if (this.var_11b7) {
            this.beingPushedAway = true;
            if (this.var_11a4.isOutOfScope()) {
               this.sub_113a((KIPlayer)null, this.var_ed8, var2);
               return;
            }

            this.sub_1092(var3, var5);
         }

         if (this.autopilotActive && this.autopilotTarget != null && !this.beingPushedAway) {
            if (this.asteroidFieldTarget_ && ((Class_e19)this.autopilotTarget).var_50b != null) {
               this.autopilotTarget = ((Class_e19)this.autopilotTarget).var_50b.sub_176();
            }

            if (this.autopilotTarget != null && !this.var_13c9) {
               this.vecRight = this.autopilotTarget.sub_a9(this.vecRight);
               this.sub_1102(this.vecRight);
            } else {
               this.sub_97e((KIPlayer)null);
            }
         } else if (!this.beingPushedAway) {
            if (this.lookBack) {
               ((Camera)this.var_ed8.sub_13c()).sub_109(true);
               this.var_ed8.sub_2aa();
               this.var_f7c.sub_1f3(this.laggingPos);
               this.vecUp = this.var_50e.getUp(this.vecUp);
               this.vecUp.scale(512);
               this.var_f7c.sub_19c(this.vecUp);
               this.relPitchSpeed = this.relYawSpeed = 0;
               this.sub_d1c((int)this.var_bd4);
            } else {
               this.var_50e.pitch((int)(this.handling * (float)(this.relPitchSpeed * var1 / 3)));
               this.shipYaw = (int)(this.handling * (float)(this.relYawSpeed * var1 / 3));
               this.var_50e.yaw(this.shipYaw);
               if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
                  this.sub_d1c((int)this.var_bd4);
               }
            }

            this.var_50e.sub_202(var1 * this.speed);
         }

         if (this.var_11b7 || !this.beingPushedAway && !this.lookBack) {
            this.var_5f8.setRotation(0, 0, -this.roll);
            if (this.roll > 0 && this.relYawSpeed == 0) {
               this.roll -= var1 / 2;
            } else if (this.roll < -var1 / 2 && this.relYawSpeed == 0) {
               this.roll += var1 / 2;
            }

            this.relPitchSpeed = this.relYawSpeed = 0;
         }

         if (this.var_eb4) {
            this.var_fd2.sub_8c9(this.var_50e.sub_85f());
            this.var_fd2.roll(-this.roll);
            if (this.lookBack) {
               this.var_f7c.sub_8c9(this.var_50e.sub_85f());
               this.var_f7c.roll(-this.roll);
               this.var_f7c.sub_109(true);
            }
         }

         if (Status.getShip().getShieldRegenTime() > 0) {
            this.var_b37 += (long)var1;
            if (this.var_b37 > 100L) {
               this.var_b37 = 0L;
               this.player.sub_56c(this.var_1562);
            }
         }

         if (Status.getShip().hasRepairBot()) {
            this.var_b53 += (long)var1;
            this.var_b91 += (long)var1;
            if (this.var_b53 > 600L) {
               this.var_b53 = 0L;
               if (this.player.getHitpoints() < this.player.sub_66c()) {
                  this.player.sub_5e9();
               }
            }

            if (this.var_b91 > 1000L) {
               this.var_b91 = 0L;
               if (this.player.sub_53c() < this.player.sub_561()) {
                  this.player.sub_5ce();
               }
            }
         }

         this.player.sub_c78((long)var1);
         if (this.lookBack) {
            this.crosshair.sub_5c(this.var_749.sub_8a0(), GameStatus.renderer.getCamera());
         } else {
            this.crosshair.sub_5c(this.var_50e.sub_85f(), GameStatus.renderer.getCamera());
         }

         this.player.var_46b = this.var_5f8.sub_8a0();
         if (this.var_910 != null) {
            var9 = this.var_910.sub_229();
            this.var_910.sub_31e(this.var_50e.sub_29c(), this.var_50e.sub_2c5(), this.var_50e.sub_31f());
            if (this.var_910.sub_229() != var9) {
               if (!this.var_910.sub_349() && this.var_910.sub_229() == 0) {
                  var3.sub_30d(24, this);
               } else {
                  var3.sub_30d(23, this);
               }
            }
         }

         if (this.var_ba3 > this.player.sub_4f2()) {
            GameStatus.vibrate(110);
            var3.sub_362();
            this.var_ba3 = this.player.sub_4f2();
         }

         if (this.laggingPos.getLength() > 500000) {
            this.var_50e.moveTo(20000, this.laggingPos.y, 20000);
         }

         if (this.sub_6ae()) {
            if (this.player.sub_ace()) {
               this.var_82c.sub_141(this.var_50e.sub_29c(), this.var_50e.sub_2c5(), this.var_50e.sub_31f());
            }

            this.player.sub_a98(false);
         }

      }
   }

   public final boolean sub_bc1() {
      return this.var_16ef;
   }

   public final boolean sub_be6() {
      return this.miningGame != null;
   }

   private void sub_c22(KIPlayer[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            KIPlayer var3;
            if ((var3 = var1[var2]) != null && var3.sub_1b8(this.var_50e.sub_216(this.vecRight))) {
               int var8;
               if (var3.sub_4a3() == 6805 && var3.sub_63()) {
                  if (!((PlayerWormHole)var3).sub_b7() && !this.sub_be6()) {
                     this.vecRight = var3.sub_a9(this.vecRight);
                     this.vecRight.subtract(this.var_50e.sub_216(this.vecUp));
                     int var9 = this.vecRight.getLength();
                     var8 = '鱀' - var9;
                     int var6;
                     int var7 = (var6 = AEMath.max(1, (int)((float)(this.var_bd4 << 1) * ((float)var8 / 40000.0F)))) << 1;
                     this.var_50e.sub_18f(-var6 + GameStatus.random.nextInt(var7), -var6 + GameStatus.random.nextInt(var7), -var6 + GameStatus.random.nextInt(var7));
                     var8 >>= 8;
                     this.vecRight.normalize();
                     this.vecUp.set(this.vecRight);
                     this.vecUp.scale(var8);
                     this.var_50e.sub_19c(this.vecUp);
                     if (var9 < 4096) {
                        this.var_1686 = true;
                     }
                  }
               } else {
                  AEVector3D var4;
                  if (var3.isAsteroid) {
                     if (!var3.isCrate() && !var3.isOutOfScope() && (!this.var_11b7 || var3 != this.var_11a4) && (var4 = var3.sub_005(this.var_50e.sub_216(this.vecRight))) != null) {
                        var3.player.sub_7d0(var4.x, var4.y, var4.z);
                        var3.player.setNukeStun(this.boostActive ? 0.7F : 0.4F);
                        short var5 = 9999;
                        var3.player.sub_96b(var5, false);
                        if (((PlayerAsteroid)var3).sub_80() > 30) {
                           byte var10 = 40;
                           this.player.sub_96b(var10, false);
                           this.var_ed8.sub_34a();
                        }
                     }
                  } else if ((var4 = var3.sub_005(this.var_50e.sub_216(this.vecRight))) != null) {
                     this.vecUp.set(this.var_50e.sub_36a(this.vecUp));
                     var4.subtract(this.vecUp);
                     var8 = var4.getLength();
                     var4.scale((int)((float)(this.var_bd4 >> 2) * (float)(var8 >> 7)));
                     this.vecRight = this.vecUp.add(var4, this.vecRight);
                     this.vecRight.normalize();
                     this.var_50e.sub_85f().setOrientation(this.vecRight);
                     this.var_50e.sub_202((int)this.var_bd4 * this.speed);
                     this.sub_d1c((int)this.var_bd4);
                     this.beingPushedAway = true;
                     if (var2 == 0) {
                        this.var_16ef = true;
                        return;
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   public final void sub_c3e(int var1) {
      if (this.lookBack) {
         var1 /= 200 / this.var_1475;
         this.var_f22.sub_5c5(0, var1, 0);
         this.var_62e.sub_5c5(0, var1, 0);
         this.var_749.sub_5c5(0, var1, 0);
      } else if (!this.var_11b7 && this.roll < 384) {
         this.roll += (int)(this.handling * (float)var1);
      }

      this.relYawSpeed = 1;
   }

   public final void sub_c71(int var1) {
      if (this.lookBack) {
         var1 /= 200 / this.var_1475;
         this.var_f22.sub_5c5(0, -var1, 0);
         this.var_62e.sub_5c5(0, -var1, 0);
         this.var_749.sub_5c5(0, -var1, 0);
      } else if (!this.var_11b7 && this.roll > -384) {
         this.roll -= (int)(this.handling * (float)var1);
      }

      this.relYawSpeed = -1;
   }

   public final void sub_c7e(int var1) {
      if (this.lookBack) {
         if (this.var_106f < 0.0F) {
            this.var_106f += (float)var1;
            this.var_749.sub_5c5(var1, 0, 0);
         }

         if (this.var_1016 < 0.0F) {
            float var2 = (float)var1 / 2.0F;
            this.var_1016 += var2;
            this.var_f22.sub_5c5((int)var2, 0, 0);
         }
      }

      this.relPitchSpeed = 1;
   }

   public final void sub_cc3(int var1) {
      if (this.lookBack) {
         if (this.var_106f > -500.0F) {
            this.var_106f -= (float)var1;
            this.var_749.sub_5c5(-var1, 0, 0);
         }

         if (this.var_1016 > -250.0F) {
            float var2 = (float)var1 / 2.0F;
            this.var_1016 -= var2;
            this.var_f22.sub_5c5(-((int)var2), 0, 0);
         }
      }

      this.relPitchSpeed = -1;
   }

   private void sub_d1c(int var1) {
      this.var_d58.identity();
      var1 = AEMath.min(var1, 60);
      this.vecUp = this.var_50e.getUp(this.vecUp);
      this.vecRight = this.var_50e.sub_548(this.vecRight);
      this.var_d58.identity();
      int var2 = this.var_d58.getUp().squaredLengthPrecise(this.vecRight);
      if (this.var_d58.getUp().squaredLengthPrecise(this.vecUp) < 0) {
         if (var2 <= 0) {
            this.var_50e.roll(var1 >> 1);
            return;
         }
      } else {
         if ((var2 <= 128 || var2 >= 3968) && (var2 >= -128 || var2 <= -3968)) {
            return;
         }

         if (var2 < 0) {
            this.var_50e.roll(var1 >> 1);
            return;
         }
      }

      this.var_50e.roll(-var1 >> 1);
   }

   public final void sub_d47() {
      this.var_82c.sub_141(this.var_50e.sub_29c(), this.var_50e.sub_2c5(), this.var_50e.sub_31f());
   }

   public final void sub_dab(Explosion var1) {
      this.var_82c = var1;
   }

   public final void sub_de8() {
      this.player.sub_b5e(0);
   }

   public final boolean sub_e45() {
      return this.var_11b7 && this.var_11d2 != 1;
   }

   public final boolean sub_ea4() {
      return false;
   }

   public final boolean sub_eb2() {
      return this.var_13c9;
   }

   public final void sub_f13(Class_c53cameraRelated var1) {
      var1.sub_383(false);
      this.var_13df = true;
      this.speed = 8;
      this.var_140d = 0;
   }

   public final boolean sub_f55() {
      return this.var_13df;
   }

   public final boolean sub_f9b() {
      return this.var_140d > 3000;
   }

   public final void sub_ff3(Class_c53cameraRelated var1, boolean var2) {
      this.vecUp = ((PlayerStaticFar)this.var_887.sub_5b7()[1]).sub_52(this.vecUp);
      if (var2) {
         this.var_13c9 = true;
         this.var_1399 = false;
      } else {
         this.speed = 2;
         this.vecRight = this.var_50e.sub_36a(this.vecRight);
         this.vecRight.scale(8192);
         this.vecUp.add(this.vecRight);
         this.sub_b32(this.vecUp);
         this.beingPushedAway = false;
         this.var_bb6 = false;
         var1.sub_383(true);
         this.var_1399 = false;
         this.var_13c9 = false;
         this.sub_97e((KIPlayer)null);
      }
   }

   public final boolean sub_1045() {
      return this.var_11b7;
   }

   private boolean sub_1092(Hud var1, int var2) {
      if (this.var_11a4 == null) {
         return false;
      } else {
         int var3;
         switch(this.var_11d2) {
         case 0:
            if (this.lookBack) {
               this.sub_2b9(false);
            }

            this.vecRight.set(this.var_11a4.var_25d.sub_216(this.vecRight));
            this.vecRight.subtract(this.var_50e.sub_216(this.laggingPos));
            if ((var3 = this.vecRight.getLength()) >= this.var_1283) {
               this.sub_1102(this.var_11a4.var_25d.sub_216(this.vecRight));
            } else if (this.var_1256) {
               if (this.var_11a4 instanceof PlayerAsteroid) {
                  ((PlayerAsteroid)this.var_11a4).sub_26f(false);
               }

               this.var_11d2 = 1;
            }

            if (var3 < this.var_1283 + 2000) {
               if (this.var_1203 == null) {
                  this.boostTime = boostDuration - boostDuration / 6;
                  this.var_ed8.sub_383(false);
                  this.var_1203 = new AEVector3D(this.var_50e.getUp(this.vecUp));
                  this.var_1203.normalize();
               }

               this.vecUp.set(this.var_50e.sub_36a(this.vecUp));
               this.vecUp.normalize();
               this.vecUp.subtract(this.var_1203);
               if (AEMath.abs(this.vecUp.getLength()) > 1500) {
                  if (this.var_12ac > -1024) {
                     var3 = -((int)this.var_bd4) >> 1;
                     this.var_12ac += var3;
                     this.var_50e.pitch(var3);
                  } else {
                     this.var_1256 = true;
                  }
               } else {
                  this.var_1256 = true;
               }
            }
            break;
         case 1:
            if (this.var_12ac > -1024) {
               var3 = -((int)this.var_bd4) >> 1;
               this.var_12ac += var3;
               this.var_50e.pitch(var3);
            } else if (this.miningGame == null) {
               this.miningGame = new MiningGame(((PlayerAsteroid)this.var_11a4).sub_e2(), ((PlayerAsteroid)this.var_11a4).var_9d3, var1);
               this.turnEngines_(false);
               GameStatus.soundManager.playSfx(6);
            } else if (!this.miningGame.sub_b7((int)this.var_bd4, var2)) {
               if (!this.miningGame.sub_57() && this.miningGame.getMiningProgressRounded() > 0) {
                  this.sub_10a5();
               } else if (this.miningGame.sub_57()) {
                  this.sub_10a5();
                  var1.sub_30d(8, this);
               }

               return true;
            }
         }

         return false;
      }
   }

   public final void sub_10a5() {
      int var1;
      int var2 = (var1 = ((PlayerAsteroid)this.var_11a4).var_9d3) - 154 + 165;
      int var3;
      int var4 = AEMath.min(var3 = Status.getShip().getFreeCargo(), this.miningGame.getMiningProgressRounded());
      if (var3 > 0) {
         Item var5 = null;
         boolean var6 = var3 >= 1;
         if (this.miningGame.sub_a8() && var6) {
            var5 = IndexManager.getItems()[var2].anyAmountCopies(1);
            Status.getShip().addItem(var5);
            this.var_15a9.sub_3ae(var2, 1, false, false, false, false);
            ++Status.minedCores;
            Status.minedCoreTypes[var2 - 165] = true;
            if ((var3 = Status.getShip().getFreeCargo()) < var4) {
               var4 = var3;
            }
         }

         if (var4 > 0) {
            var5 = IndexManager.getItems()[var1].anyAmountCopies(var4);
            Status.getShip().addItem(var5);
            this.var_15a9.sub_3ae(var1, var4, false, false, true, false);
            Status.minedOre += var4;
            Status.minedOreTypes[var1 - 154] = true;
         }

         if (Status.getShip().getFreeCargo() <= 0) {
            this.var_15a9.sub_3ae(var1, var4, true, false, true, false);
         }
      } else {
         this.var_15a9.sub_3ae(var1, 0, true, false, true, false);
      }

      this.turnEngines_(true);
      this.var_11a4.var_a3b = null;
      this.var_11a4.var_9d8 = false;
      this.var_11a4.player.setHitPoints(-1);
      --Status.asteroidsDestroyed;
      this.miningGame = null;
      this.sub_113a((KIPlayer)null, this.var_ed8, this.var_15f4);
   }

   private void sub_1102(AEVector3D var1) {
      this.vecRight.set(var1);
      this.vecRight.subtract(this.var_50e.sub_216(this.laggingPos));
      this.vecRight.normalize();
      this.vecUp.set(this.var_50e.sub_36a(this.vecUp));
      this.vecUp.normalize();
      this.vecRight.subtract(this.vecUp);
      this.vecRight.scale((int)((float)this.var_bd4 * 4.0F));
      this.var_e91 = this.vecUp.add(this.vecRight, this.var_e91);
      this.var_e91.normalize();
      this.var_50e.sub_85f().setOrientation(this.var_e91);
      if (this.relPitchSpeed == 0 && this.relYawSpeed == 0) {
         this.sub_d1c((int)this.var_bd4);
      }

      this.var_50e.sub_202((int)this.var_bd4 * this.speed);
   }

   public final void sub_113a(KIPlayer var1, Class_c53cameraRelated var2, MGameContext var3) {
      if (this.var_11b7) {
         ((PlayerAsteroid)this.var_11a4).sub_26f(true);
         this.var_11b7 = false;
         this.beingPushedAway = false;
         this.var_11a4 = null;
         this.var_1203 = null;
         var2.sub_383(true);
         GameStatus.renderer.sub_19((Camera)var2.sub_13c());
         Class_7flight.sub_1b8(var2, this.var_887);
         this.player.sub_b5e(0);
         this.miningGame = null;
         var3.sub_002();
      } else if (var1 != null) {
         this.var_12ac = 0;
         this.var_ed8 = var2;
         this.var_11b7 = true;
         this.var_11a4 = var1;
         this.vecUp = var1.var_25d.sub_7ff(this.vecUp);
         this.var_1283 = (this.vecUp.x + this.vecUp.y + this.vecUp.z) / 2;
         this.beingPushedAway = true;
         this.var_11d2 = 0;
      }
   }

   public final void sub_1181() {
      this.var_887 = null;
      if (this.var_82c != null) {
         this.var_82c.sub_fa();
      }

      this.var_82c = null;
      if (this.crosshair != null) {
         this.crosshair.updatePosition();
      }

      this.crosshair = null;
      this.var_7a2 = null;
      if (this.var_62e != null) {
         this.var_62e.destroy();
      }

      this.var_62e = null;
      if (this.var_749 != null) {
         this.var_749.destroy();
      }

      this.var_749 = null;
      System.gc();
   }

   public final void sub_11da(boolean var1) {
      if (this.sub_6ae()) {
         this.var_82c.sub_179(this.var_bd4);
      } else {
         if (this.var_82c.sub_154()) {
            this.var_82c.sub_179(this.var_bd4);
         }

         if (this.var_1358 >= 0) {
            GameStatus.renderer.sub_87(this.var_50e);
            if (this.var_eb4) {
               GameStatus.renderer.sub_87(this.var_fd2);
            }
         } else {
            this.var_50e.sub_109(true);
            if (this.var_eb4) {
               this.var_fd2.sub_109(true);
            }
         }

         if (this.var_1142 != null) {
            this.var_1142.sub_18();
         }

      }
   }

   public final void sub_1227(boolean var1, Camera var2) {
      if (this.miningGame != null) {
         this.miningGame.sub_e1();
      } else {
         if (!this.var_141f && !this.sub_6ae() && !this.var_bb6 && var1 && !this.var_11b7 && !this.var_13df && !this.var_13c9 && !this.autopilotActive) {
            this.crosshair.draw();
         }

      }
   }

   public final void sub_1281() {
      this.var_ed8.sub_34a();
   }

   public final Hud sub_12ab() {
      return this.var_15a9;
   }
}
