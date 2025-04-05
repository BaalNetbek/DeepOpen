package AbyssEngine;

public final class Class_7flight {
   private Level var_8c;
   private int var_be;
   private boolean var_1a2;
   private boolean var_25e;
   private boolean var_269;
   private Class_fd6 var_28b;
   private int var_2e6;
   private AEVector3D var_2f0 = new AEVector3D();
   private AEVector3D var_33a = new AEVector3D();
   private AEVector3D var_39e = new AEVector3D();
   public boolean var_428;
   private Hud var_456;
   private MGameContext var_473;
   private AbstractMesh var_47e;
   public int var_4c9;
   public long var_500;

   public Class_7flight(Class_c53cameraRelated var1, Class_fd6 var2, Level var3, Hud var4, MGameContext var5) {
      this.var_456 = var4;
      this.var_473 = var5;
      this.var_8c = var3;
      this.var_be = 0;
      this.var_1a2 = true;
      this.var_25e = true;
      this.var_269 = false;
      this.var_28b = var2;
      this.var_428 = false;
      this.var_4c9 = var3.sub_8aa();
      this.var_500 = 0L;
      var1.sub_383(false);
      var2.sub_120(true);
      if (var3.getPlayerEgo() == null) {
         this.var_25e = false;
      }

      if (Status.getCurrentCampaignMission() == 0) {
         var4.drawUI = false;
         var5.var_10b4 = false;
         var3.getPlayerEgo().sub_95f(true);
         var3.getPlayerEgo().sub_93d(true);
         GameStatus.renderer.getCamera().moveTo(0, 0, 0);
         GameStatus.renderer.getCamera().setRotation(0, 1800, 0);
         this.var_269 = true;
         var1.sub_383(false);
         var2.sub_120(false);
         var3.getPlayerEgo().sub_ae0(0, 0, 120000);
      } else if (Status.getCurrentCampaignMission() == 1) {
         var4.drawUI = false;
         var5.var_10b4 = false;
         var3.getPlayerEgo().var_50e.setRotation(300, 300, 1000);
         var3.getPlayerEgo().sub_95f(true);
         var3.getPlayerEgo().sub_93d(true);
         var3.getPlayerEgo().sub_ae0(0, 0, 0);
         var3.getPlayerEgo().turnEngines_(false);
         GameStatus.renderer.getCamera().moveTo(1500, 600, -3000);
         this.var_269 = true;
         var2.sub_120(true);
         var2.sub_36(var3.getPlayerEgo().var_50e);
      } else {
         if (Level.var_1297) {
            this.var_2f0.x = 500 + GameStatus.random.nextInt(500);
            this.var_2f0.y = 500 + GameStatus.random.nextInt(500);
            this.var_2f0.z = 10000;
         } else {
            this.var_2f0.x = 1000 + GameStatus.random.nextInt(2000);
            this.var_2f0.y = 500 + GameStatus.random.nextInt(2000);
            this.var_2f0.z = 9000;
         }

         AEVector3D var10000;
         if (GameStatus.random.nextInt(2) == 0) {
            var10000 = this.var_2f0;
            var10000.x = -var10000.x;
         }

         if (GameStatus.random.nextInt(2) == 0) {
            var10000 = this.var_2f0;
            var10000.y = -var10000.y;
         }

         Matrix var6;
         (var6 = new Matrix()).sub_695(var3.getPlayerEgo().var_50e.sub_753());
         this.var_33a = var6.sub_a67(this.var_2f0, this.var_33a);
         this.var_33a.add(var3.getPlayerEgo().var_50e.sub_216(this.var_2f0));
         GameStatus.renderer.getCamera().sub_1f3(this.var_33a);
         var1.sub_188(this.var_33a);
         var2.sub_bf(var3.getPlayerEgo().var_50e.getUp(), 2);
         if (Status.inAlienOrbit() || Level.var_12a2) {
            if (Status.getCurrentCampaignMission() < 43) {
               this.var_39e = var3.getPlayerEgo().var_50e.sub_36a(this.var_39e);
               this.var_39e.scale(8192);
               this.var_2f0.subtract(this.var_39e);
               ((PlayerWormHole)var3.sub_5b7()[3]).sub_55(true);
               var3.sub_5b7()[3].setPosition(this.var_2f0.x, this.var_2f0.y, this.var_2f0.z);
               var3.sub_5b7()[3].sub_73(true);
            }

            Level.var_12a2 = false;
         }
      }

      var3.getPlayerEgo().sub_534(false);
   }

   public final boolean sub_50(int var1, Class_c53cameraRelated var2) {
      Class_85e[] var3 = this.var_8c.sub_872();
      PlayerEgo var4 = this.var_8c.getPlayerEgo();
      Camera var5 = GameStatus.renderer.getCamera();
      KIPlayer[] var6 = this.var_8c.getShips();
      int var7;
      if (this.var_25e) {
         this.var_2e6 += var1;
         if (Status.getCurrentCampaignMission() != 0) {
            if (Status.getCurrentCampaignMission() == 1) {
               var4.var_50e.sub_5c5(0, var1 >> 3, 0);
               if (var6[0].sub_a9(this.var_2f0).z < -1800) {
                  var6[0].var_379.sub_202(var1 >> 1);
               } else {
                  ((PlayerFighter)var6[0]).state = 5;
                  ((PlayerFighter)var6[0]).sub_2b8(false);
               }

               if (var3[2].sub_f2()) {
                  GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
                  return false;
               }
            }
         } else if (!var3[1].sub_8e()) {
            var5.sub_5c5(0, var1 >> 4, 0);
            var6[0].sub_5cc();
            var6[1].sub_5cc();
         } else if (this.var_be == 0) {
            this.var_28b.sub_120(true);
            var4.sub_95f(false);
            var4.var_50e.setRotation(0, 2048, 0);
            var5.moveTo(-1000, -500, 110000);
            this.var_be = 1;
         } else {
            if (this.var_be < 6) {
               for(var7 = 0; var7 < 2; ++var7) {
                  int var8 = AEMath.sin(this.var_2e6) >> 10;
                  var6[var7].var_379.sub_18f(0, var8, 0);
               }

               var6[0].sub_5cc();
               var6[1].sub_5cc();
            }

            if (var3[2].sub_f2() && this.var_be == 1) {
               var4.sub_95f(true);
               this.var_28b.sub_36(var6[0].var_379);
               var5.sub_1f3(var6[0].sub_a9(this.var_2f0));
               var5.sub_18f(1000, 700, 1500);
               this.var_be = 2;
            }

            if (var3[3].sub_f2() && this.var_be == 2) {
               this.var_28b.sub_36(var6[1].var_379);
               var5.sub_1f3(var6[1].sub_a9(this.var_2f0));
               var5.sub_18f(-2300, 300, 200);
               this.var_be = 3;
            }

            if (var3[5].sub_f2() && this.var_be == 3) {
               var6[2].sub_5fc();
               this.var_28b.sub_36(var6[2].var_379);
               var5.sub_1f3(var6[2].sub_a9(this.var_2f0));
               var5.sub_18f(1000, 200, 6000);
               this.var_be = 4;
            }

            if (var3[6].sub_f2() && this.var_be == 4) {
               this.var_28b.sub_36(var6[1].var_379);
               var5.sub_1f3(var6[1].sub_a9(this.var_2f0));
               var5.sub_18f(-1300, 300, 1700);
               this.var_be = 5;
            }

            if (var3[7].sub_f2() && this.var_be == 5) {
               this.var_473.var_10b4 = true;
               this.var_8c.getPlayerEgo().sub_534(true);
               var4.sub_95f(false);
               this.var_28b.sub_36(var4.var_50e);
               this.var_28b.sub_120(false);
               var2.sub_383(true);
               this.var_be = 6;
               GameStatus.soundManager.playMusic(3);
            }

            if (var3[8].sub_f2() && this.var_be == 6) {
               var6[0].sub_5fc();
               var6[1].sub_5fc();
               var6[2].sub_5fc();
               ((PlayerFighter)var6[0]).sub_2b8(true);
               ((PlayerFighter)var6[1]).sub_2b8(true);
               var4.sub_93d(false);
               this.var_1a2 = false;
               this.var_be = 7;
            }

            if (var3[10].sub_8e() && this.var_be == 7) {
               this.var_8c.getPlayerEgo().sub_534(false);
               this.var_473.var_10b4 = false;
               var4.sub_93d(true);
               var4.player.removeGuns();
               var2.sub_383(false);
               this.var_28b.sub_120(true);
               this.var_28b.sub_36(var4.var_50e);
               var4.var_50e.setRotation(0, 2048, 0);
               var5.sub_1f3(var4.var_50e.sub_237());
               var5.sub_18f(1000, -200, -60000);
               this.var_be = 8;
               this.var_2e6 = 0;
            }

            if (var3[12].sub_f2() && this.var_be == 8) {
               var4.sub_95f(true);
               this.var_be = 9;
               var4.sub_d47();
               this.var_8c.setupExplosionFlashColors(3);
               var4.turnEngines_(false);
            }

            if (this.var_be == 9) {
               var4.var_50e.sub_5c5(var1 >> 1, var1 >> 1, var1 >> 1);
               if (var3[16].sub_f2()) {
                  this.var_be = 10;
               }
            }

            if (this.var_be == 10) {
               var4.sub_95f(false);
               var5.sub_1f3(var4.var_50e.sub_237());
               var5.setRotation(0, 0, 0);
               var5.sub_18f(-1000, -700, -1500);
               if (var3[14].sub_f2()) {
                  var4.turnEngines_(true);
                  this.var_be = 11;
                  var4.var_50e.setRotation(0, 0, 0);
                  var5.sub_1f3(var4.var_50e.sub_237());
                  var5.sub_18f(1000, 200, 15000);
               }
            }

            if (var3[21].sub_f2()) {
               Status.nextCampaignMission();
               GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
               Level.var_1297 = false;
               return false;
            }
         }

         if (this.var_8c.getPlayerEgo() != null && Status.getCurrentCampaignMission() > 1 && this.var_2e6 > 7000) {
            this.var_2e6 = 0;
            this.var_25e = false;
            this.var_269 = true;
            this.var_28b.sub_120(false);
            var2.sub_3a9(GameStatus.renderer.getCamera().sub_237());
            var2.sub_383(true);
            sub_1b8(var2, this.var_8c);
            this.var_1a2 = false;
            this.var_8c.getPlayerEgo().sub_534(true);
            if (Level.autopilotDestination != null && !Level.autopilotDestination.equals(Status.getStation())) {
               this.var_456.sub_30d(5, this.var_8c.getPlayerEgo());
            }

            Level.var_1297 = false;
            if (!Level.var_1311) {
               this.sub_24c();
            }
         }
      }

      if (Status.getCurrentCampaignMission() == 5 && this.var_be == 0) {
         this.var_2f0 = var4.sub_8c1();
         var6[0].setPosition(this.var_2f0.x + 5000, this.var_2f0.y, this.var_2f0.z + 30000);
         var6[0].var_379.sub_5c5(0, 2048, 0);
         var6[0].sub_5fc();
         this.var_be = 1;
      }

      if (Status.getMission().isCampaignMission() && !Status.getMission().isEmpty()) {
         switch(Status.getCurrentCampaignMission()) {
         case 14:
            if (var3[1].sub_f2() && this.var_be == 0) {
               this.var_456.drawUI = false;
               this.var_473.var_10b4 = false;
               this.var_8c.setupExplosionFlashColors(4);
               var4.sub_93d(true);
               var4.speed = 0;
               var4.player.removeGuns();
               var2.sub_383(false);
               this.var_28b.sub_120(true);
               this.var_28b.sub_36(var4.var_50e);
               var5.sub_1f3(var4.sub_8c1());
               var5.sub_18f(1000, 700, 1000);

               for(var7 = 0; var7 < this.var_8c.getShips().length; ++var7) {
                  if (this.var_8c.getShips()[var7].race == 8) {
                     this.var_8c.getShips()[var7].player.setHitPoints(0);
                  }
               }

               this.var_be = 1;
            } else if (this.var_be == 1) {
               var4.var_50e.sub_5c5(0, var1 >> 1, 0);
               var5.sub_18f(0, 0, var1);
            }
            break;
         case 16:
         case 21:
            if (var3[0].sub_8e() && this.var_be == 0) {
               this.var_456.drawUI = false;
               this.var_473.var_10b4 = false;
               var4.sub_93d(true);
               var2.sub_43(this.var_8c.getShips()[0].var_379);
               this.var_2f0.set(300, 400, 3500);
               var2.sub_400(this.var_2f0);
               this.var_2f0.set(0, 0, 0);
               var2.sub_3dd(this.var_2f0);
               this.var_be = 1;
            } else if (var3[Status.getCurrentCampaignMission() == 16 ? 1 : 0].sub_f2() && this.var_be == 1) {
               var4.sub_93d(false);
               this.var_456.drawUI = true;
               this.var_473.var_10b4 = true;
               sub_1b8(var2, this.var_8c);
               this.var_be = 2;
            }
            break;
         case 24:
            if (var3[0].sub_8e() && this.var_be == 0) {
               this.var_456.drawUI = false;
               this.var_473.var_10b4 = false;
               this.var_28b.sub_120(true);
               var2.sub_383(false);
               var4.player.setHitPoints(9999999);
               var4.var_50e.setRotation(0, 1024, 0);
               var4.sub_93d(true);
               var4.player.removeGuns();
               this.var_28b.sub_36(var4.var_50e);
               var5.sub_1f3(var4.var_50e.sub_216(this.var_2f0));
               var5.sub_18f(30500, 700, 1000);
               this.var_2f0 = var4.var_50e.sub_216(this.var_2f0);
               this.var_39e = var4.var_50e.sub_36a(this.var_39e);
               this.var_39e.scale(40960);
               this.var_2f0.add(this.var_39e);
               this.var_8c.sub_5b7()[3].setPosition(this.var_2f0.x, this.var_2f0.y, this.var_2f0.z);

               for(var7 = 0; var7 < this.var_8c.getShips().length; ++var7) {
                  this.var_8c.getShips()[var7].player.sub_218((Player[])null);
               }

               this.var_be = 1;
            } else if (var3[0].sub_f2() && this.var_be == 1) {
               ((PlayerWormHole)this.var_8c.sub_5b7()[3]).sub_55(false);
               this.var_8c.sub_5b7()[3].sub_73(true);
               this.var_be = 2;
            }
            break;
         case 29:
            if (var3[0].sub_8e() && this.var_be == 0) {
               this.var_be = 1;
               var4.player.sub_79a(false);
               this.var_456.drawUI = false;
               this.var_473.var_10b4 = false;
               this.var_28b.sub_120(true);
               var2.sub_383(false);
               var4.sub_93d(true);
               this.var_28b.sub_36(var4.var_50e);
               var5.sub_1f3(var4.var_50e.sub_216(this.var_2f0));
               this.var_2f0 = var4.var_50e.getDirection();
               this.var_2f0.scale(16384);
               new AEVector3D();
               AEVector3D var9 = null;
               (var9 = var4.var_50e.getRight()).scale(1024);
               this.var_2f0.add(var9);
               var5.sub_19c(this.var_2f0);
               this.var_47e = AEResourceManager.getGeometryResource(18);
               this.var_47e.sub_8c9(var4.var_50e.sub_85f());
               this.var_47e.sub_7af(768, 768, 768);
            } else if (var3[2].sub_f2() && this.var_be == 1) {
               this.var_be = 2;
               this.var_456.drawUI = true;
               this.var_473.var_10b4 = true;
               this.var_28b.sub_120(false);
               var2.sub_383(true);
               var4.sub_93d(false);
               var4.player.sub_79a(true);
               this.var_47e = null;
               this.var_4c9 = 180000;
               this.var_500 = 0L;
               this.var_8c.var_b00 = new Class_876(3, this.var_4c9, this.var_8c);
            }

            if (this.var_47e != null) {
               this.var_47e.sub_202(var1 * 3);
            }
            break;
         case 40:
            if (this.var_be == 0) {
               var6[0].race = 1;
               this.var_be = 1;
            } else if (this.var_be == 1 && var6[0].sub_a9(this.var_39e).z >= ((PlayerWormHole)this.var_8c.sub_5b7()[3]).sub_a9(this.var_2f0).z) {
               this.var_be = 2;
            } else if (this.var_be == 2) {
               ((PlayerBigShip)var6[0]).sub_da(var6[0].sub_a9(this.var_39e).z - 200000 - var1);
               if (var6[0].sub_a9(this.var_39e).z > 500000) {
                  var6[0].setPosition(0, 0, -200000);
                  var6[0].sub_34(false);
                  this.var_be = 3;
               }
            }
            break;
         case 41:
            if (this.var_be == 0 && var6[0].var_379.sub_31f() > -10000) {
               this.var_be = 1;
               var6[0].sub_1515sth(0);
               var6[0].player.setMaxHP(9999999);
               this.var_8c.sub_5b7()[3].setPosition(5000, -40000, 10000);
            }
         }
      }

      return this.var_1a2;
   }

   public final void sub_6a() {
      if (this.var_47e != null) {
         GameStatus.renderer.sub_87(this.var_47e);
      }

   }

   public final void sub_aa() {
      if (Status.getCurrentCampaignMission() > 0) {
         this.var_2e6 = 7001;
      }

   }

   public final void sub_10c() {
      this.var_269 = false;
      this.var_2e6 = 0;
   }

   public final boolean sub_123() {
      return this.var_269;
   }

   public final boolean sub_182() {
      return this.var_25e;
   }

   public static void sub_1b8(Class_c53cameraRelated var0, Level var1) {
      if (var1.getPlayerEgo() != null) {
         var0.sub_43(var1.getPlayerEgo().var_50e);
         var0.sub_3dd(new AEVector3D(0, 850, 0));
         var0.sub_400(new AEVector3D(0, 700, -2000));
         var0.sub_249();
      }

   }

   public static void sub_206(Class_c53cameraRelated var0) {
      var0.sub_3dd(new AEVector3D(0, 300, 0));
      var0.sub_400(new AEVector3D(0, 600, 3100));
   }

   public final void sub_24c() {
      if (Level.autopilotDestination != null) {
         if (Status.getStation().equals(Level.autopilotDestination)) {
            Level.autopilotDestination = null;
            return;
         }

         SolarSystem var10000 = Status.getSystem();
         Station var2 = Level.autopilotDestination;
         if (var10000.stationIsInSystem(var2.getId())) {
            this.var_8c.getPlayerEgo().sub_97e(this.var_8c.sub_8ce().getLocalPlanets()[Status.getSystem().verifyStationInSystem(Level.autopilotDestination.getId())]);
            return;
         }

         if (Status.getSystem().currentOrbitHasJumpgate()) {
            this.var_8c.getPlayerEgo().sub_97e(this.var_8c.sub_5b7()[1]);
            return;
         }

         int var1;
         if ((var1 = Status.getSystem().getJumpgateStationVerifiedIndex()) >= 0) {
            this.var_8c.getPlayerEgo().sub_97e(this.var_8c.sub_8ce().getLocalPlanets()[var1]);
         }
      }

   }
}
