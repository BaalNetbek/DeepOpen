package AbyssEngine;

public final class Level {
   private int var_dd;
   private AbstractBounding var_2de;
   private final int[] var_2ed;
   private AEVector3D var_387;
   private Class_e19 var_3ad;
   private AbstractMesh var_3e4;
   private Class_6fcMesh var_43d;
   private Class_6fcMesh var_4fc;
   private AbstractMesh[] projectileMesh;
   private AbstractMesh[] var_6e9;
   private Skybox skybox;
   private PlayerEgo playerEgo;
   private KIPlayer[] ships;
   private KIPlayer[] asteroids;
   private KIPlayer[] stationaries;
   private Class_661 var_7df;
   private Class_661 var_863;
   private Class_661 var_8c5;
   private Class_85e[] var_8d0;
   private int var_902;
   private int var_977;
   private int var_9e4;
   private int var_9fe;
   public int var_a19;
   public int challengerScore;
   public int egoScore;
   public Class_876 var_b00;
   public Class_876 var_b24;
   private int var_b69;
   private int var_b87;
   private boolean var_ba8;
   private int var_c04;
   private boolean var_c13;
   public static float starR;
   public static float starG;
   public static float starB;
   public static int spaceR;
   public static int spaceG;
   public static int spaceB;
   public static int bgR;
   public static int bgG;
   public static int bgB;
   private float explosionR;
   private float explosionG;
   private float explosionB;
   private int var_f60;
   private int var_fa1;
   private boolean exploding_;
   private int var_ff8;
   private int var_1005;
   private int var_1045;
   private int var_108e;
   private int var_10e2;
   private int var_1104;
   private int var_1149;
   private int var_1172;
   private Class_661 var_11a5;
   private int var_11c4;
   private boolean var_1219;
   private boolean firstWarning;
   public static boolean var_1297 = false;
   public static boolean var_12a2 = false;
   public static int var_12b5;
   public static Station autopilotDestination;
   public static boolean var_1311 = false;
   private AEVector3D var_1353;

   public Level() {
      this(3);
   }

   public Level(int var1) {
      this.var_2ed = new int[]{630, 0, 175, 1570, 0, 340, 1024, 0, 600, 1260, 0, 650, 880, 0, 950};
      this.exploding_ = false;
      this.var_1353 = new AEVector3D();
      this.var_dd = var1;
      this.var_b87 = 0;
      this.var_ba8 = false;
   }

   public static void sub_16a() {
      var_1297 = true;
   }

   public final boolean sub_198() {
      if (this.var_b87 == 0 || this.var_ba8) {
         this.var_7df = null;
         this.var_8c5 = null;
         this.var_863 = null;
         this.var_b24 = null;
         this.var_b00 = null;
         this.var_43d = new Class_6fcMesh(AEResourceManager.sub_10b(1), 33, 225, 63, 255, 10, 700, 100, 500);
         this.var_4fc = new Class_6fcMesh(AEResourceManager.sub_10b(1), 33, 225, 63, 255, 10, 700, 100, 500);
         Level var1 = this;
         if (this.var_3e4 == null) {
            this.var_3e4 = AEResourceManager.getGeometryResource(9991);
         }

         int var4;
         int var6;
         if (this.var_dd != 23 && this.var_dd != 4) {
            boolean var2 = var_12a2 || Status.getStation().sub_fc() || Status.inAlienOrbit();
            if (Status.getCurrentCampaignMission() > 42) {
               var2 = false;
            }

            this.stationaries = new KIPlayer[4];
            if (Status.inEmptyOrbit()) {
               this.stationaries[0] = null;
            } else {
               this.stationaries[0] = new PlayerStation(Status.getStation());
            }

            AEVector3D var3 = new AEVector3D(this.var_1353);
            this.var_c04 = 0;
            GameStatus.random.setSeed(Status.getStation() != null ? (long)(Status.getStation().getId() << 1) : -1L);
            var4 = 1;

            while(true) {
               if (var4 >= 3) {
                  GameStatus.random.setSeed(System.currentTimeMillis());
                  if (Status.inAlienOrbit()) {
                     var4 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GameStatus.random.nextInt(50000));
                     int var16 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GameStatus.random.nextInt(50000));
                     var6 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GameStatus.random.nextInt(50000));
                     var1.stationaries[2].setPosition(var4, var16, var6);
                     var1.stationaries[2].var_25d.setRotation(-4096 + GameStatus.random.nextInt(8192), -4096 + GameStatus.random.nextInt(8192), -4096 + GameStatus.random.nextInt(8192));
                  }

                  if (!Status.gameWon()) {
                     var1.stationaries[3] = new PlayerWormHole(6805, AEResourceManager.getGeometryResource(6805), -40000 + GameStatus.random.nextInt(80000), -20000 + GameStatus.random.nextInt(40000), '鱀' + GameStatus.random.nextInt(40000), var2);
                     var1.stationaries[3].sub_3b3(var1);
                  }

                  var1.var_c04 += 2048;
                  if (!var_1297) {
                     var1.var_c04 = 0;
                  }
                  break;
               }

               if (var4 != 1 || Status.getSystem() != null && Status.getSystem().currentOrbitHasJumpgate()) {
                  if (Status.getStation() != null) {
                     if (var4 == 0) {
                        var1.var_c04 = Status.getStation().getName().length() + Status.getStation().getTecLevel() * 3;
                        var1.var_c04 = var1.var_c04 % 16 << 8;
                     } else {
                        var1.var_c04 += GameStatus.random.nextInt(2) == 0 ? -250 - GameStatus.random.nextInt(500) : 250 + GameStatus.random.nextInt(500);
                     }

                     var1.var_1353.z = var4 == 1 ? 90000 : 120000;
                     AEVector3D var10000 = var1.var_1353;
                     var10000.z += var1.var_c04 * 3;
                     Matrix var5;
                     (var5 = new Matrix()).sub_695(var1.var_c04);
                     (var3 = var5.sub_a67(var1.var_1353, var3)).y = 0;
                  }

                  var1.stationaries[var4] = new PlayerJumpgate(15, AEResourceManager.getGeometryResource(15), var3.x, var3.y, var3.z, var4 != 2);
               } else {
                  var1.stationaries[var4] = null;
               }

               ++var4;
            }
         }

         var1.skybox = new Skybox();
         SolarSystem var10;
         if ((var10 = Status.getSystem()) == null) {
            starR = 10.0F;
            starG = 136.0F;
            starB = 10.0F;
         } else {
            starR = (float)var10.spaceShadeR;
            starG = (float)var10.spaceShadeG;
            starB = (float)var10.spaceShadeB;
         }

         spaceR = (int)(starR / 3.0F);
         spaceG = (int)(starG / 3.0F);
         spaceB = (int)(starB / 3.0F);
         if (this.var_dd == 3) {
            var1 = this;

            try {
               Ship var11 = Status.getShip();
               Item[] var13 = Status.getShip().getEquipment();
               int[] var15;
               (var15 = new int[3])[0] = var11.countEquippedOfType(0);
               var15[1] = var11.countEquippedOfType(1);
               var15[2] = var11.countEquippedOfType(2);
               Player var17;
               (var17 = new Player(1200.0F, Status.getShip().getBaseArmour(), var15[0], var15[1], var15[2])).sub_434(Status.getShip().getShield());
               var17.sub_443(Status.getShip().getAdditionalArmour());
               var1.playerEgo = new PlayerEgo(var17);
               var1.playerEgo.sub_54(Status.getShip().getIndex(), Status.getShip().getRace());
               var1.playerEgo.sub_591(var1);
               var1.playerEgo.var_50e.setRotation(0, var1.var_c04, 0);
               int[] var20 = new int[]{0, 0, 0};
               Explosion var18 = new Explosion(var20.length / 3);
               var1.playerEgo.sub_dab(var18);
               Gun[][] var21 = new Gun[3][];

               for(var6 = 0; var6 < 3; ++var6) {
                  if (var15[var6] > 0) {
                     var21[var6] = new Gun[var15[var6]];
                  }
               }

               if (var13 != null) {
                  for(var6 = 0; var6 < var13.length; ++var6) {
                     if (var13[var6] != null) {
                        Gun var7 = null;
                        if (var13[var6].sub_848()) {
                           int var22 = var13[var6].getType() == 1 ? var13[var6].getAmount() : -1;
                           (var7 = var1.sub_25a(var13[var6].getIndex(), var6, var13[var6].getSubType(), var22, var13[var6].getAttribute(9), var13[var6].getAttribute(11), var13[var6].getAttribute(12), var13[var6].getAttribute(13))).index = var13[var6].getIndex();
                           var7.subType = var13[var6].getSubType();
                           var7.sub_f4(var13[var6].getAttribute(14));
                           int var10001;
                           int var10004;
                           Gun[] var23;
                           switch(var13[var6].getType()) {
                           case 0:
                              var7.sub_176(var15[0] - 1, var11.countEquippedOfType(0));
                              var23 = var21[0];
                              var10004 = var15[0];
                              var10001 = var15[0];
                              var15[0] = var10004 - 1;
                              var23[var10001 - 1] = var7;
                              break;
                           case 1:
                              var23 = var21[1];
                              var10004 = var15[1];
                              var10001 = var15[1];
                              var15[1] = var10004 - 1;
                              var23[var10001 - 1] = var7;
                              break;
                           case 2:
                              var23 = var21[2];
                              var10004 = var15[2];
                              var10001 = var15[2];
                              var15[2] = var10004 - 1;
                              var23[var10001 - 1] = var7;
                           }
                        }
                     }
                  }
               }

               for(var6 = 0; var6 < var21.length; ++var6) {
                  if (var21[var6] != null) {
                     var1.playerEgo.sub_721(var21[var6], var6);
                  }
               }
            } catch (Exception var8) {
               var8.printStackTrace();
            }

            if (var_1297 && this.playerEgo.var_50e != null) {
               Station[] var9;
               if ((var9 = Status.getLastVisitedStations()) != null && var9[1] != null && Status.getSystem() != null && !Status.getSystem().currentOrbitHasJumpgate()) {
                  int[] var12 = Status.getSystem().getStations();
                  int var14 = 0;

                  for(var4 = 0; var4 < var12.length; ++var4) {
                     if (var12[var4] == var9[1].getId()) {
                        var14 = var4;
                        break;
                     }
                  }

                  AEVector3D var19;
                  (var19 = new AEVector3D(this.skybox.sub_3d()[var14 + 1].sub_28c())).scale(16384);
                  this.playerEgo.sub_b32(var19);
                  var19.x = -var19.x;
                  var19.y = -var19.y;
                  var19.z = -var19.z;
                  var19.normalize();
                  this.playerEgo.var_50e.sub_85f().setOrientation(var19);
               } else {
                  this.playerEgo.sub_ae0(this.stationaries[2].var_25d.sub_29c(), this.stationaries[2].var_25d.sub_2c5(), this.stationaries[2].var_25d.sub_31f());
               }
            } else {
               this.playerEgo.sub_ae0(10, 10, 10000);
            }

            if (Status.getCurrentCampaignMission() == 1) {
               this.playerEgo.sub_ae0(0, 0, -110000);
            }
         }
      }

      if (this.var_b87 != 1 && !this.var_ba8) {
         if (this.var_ba8) {
            this.var_b87 = 0;
            return true;
         }
      } else {
         if (this.var_dd != 4) {
            this.sub_212();
         }

         if (Status.getMission() == null) {
            Status.setMission(Mission.var_dc);
         }

         if ((this.var_dd != 3 || Status.getMission().isCampaignMission()) && (this.var_dd != 3 || !Status.gameWon())) {
            if (this.var_dd != 3) {
               this.sub_358();
               this.var_dd = 3;
            } else if (!Status.getMission().isEmpty() && Status.getMission().isCampaignMission()) {
               this.sub_313();
            }
         } else {
            this.setupMissionEnviroment_();
         }

         this.sub_2ba();
         this.sub_375();
         this.sub_3c3();
         if (this.playerEgo != null) {
            this.playerEgo.sub_5f1(this.var_7df);
         }

         this.var_902 = this.ships != null ? this.ships.length : 0;
         this.var_9e4 = 0;
         this.var_9e4 = this.asteroids != null ? this.asteroids.length : 0;
         this.var_11c4 = 0;
         this.var_1219 = false;
         this.firstWarning = false;
         this.var_a19 = 0;
         this.var_9fe = 0;
         this.var_c13 = false;
         this.var_ba8 = true;
      }

      ++this.var_b87;
      return this.var_ba8;
   }

   private void sub_212() {
      int var1 = 154;
      int var2 = 0;
      boolean var3 = false;
      this.asteroids = new KIPlayer[50];
      int[] var4 = Galaxy.sub_180(Status.getStation());
      GameStatus.random.setSeed((long)Status.getStation().getId());
      int var5 = -50000 + GameStatus.random.nextInt(100000);
      int var6 = -50000 + GameStatus.random.nextInt(100000);
      int var7 = 10000 + GameStatus.random.nextInt(100000);
      GameStatus.random.setSeed(System.currentTimeMillis());
      this.var_387 = new AEVector3D(var5, var6, var7);
      this.var_3ad = new Class_e19(var5, var6, var7, (Class_661)null);
      this.var_2de = new Class_5bfcollision(var5, var6, var7, 0, 0, 0, 50000);

      for(int var8 = 0; var8 < this.asteroids.length; ++var8) {
         if (Status.inAlienOrbit()) {
            var1 = 164;
         } else if (Status.inEmptyOrbit()) {
            var1 = 154;
         } else {
            var3 = false;

            while(!var3) {
               if (GameStatus.random.nextInt(100) < var4[var2 + 1]) {
                  if ((var1 = var4[var2]) < 164) {
                     var3 = true;
                  }

                  var2 += 2;
                  if (var2 >= var4.length) {
                     var2 = 0;
                  }
               } else {
                  var2 = 0;
               }
            }
         }

         var3 = var8 < this.asteroids.length / 2;
         Class_e19 var9 = null;
         if (var3) {
            var9 = new Class_e19(0, 0, 20000, (Class_661)null);
         } else {
            var9 = new Class_e19(var5, var6, var7, (Class_661)null);
         }

         KIPlayer[] var10000 = this.asteroids;
         int var12 = Status.inAlienOrbit() ? 6804 : 6769;
         boolean var13 = false;
         boolean var14 = false;
         boolean var15 = false;
         char var19 = '\uea60';
         int var17 = var9.var_c7 - 30000 + GameStatus.random.nextInt(var19);
         int var18 = var9.var_282 - 30000 + GameStatus.random.nextInt(var19);
         int var20 = var9.var_2f9 - 30000 + GameStatus.random.nextInt(var19);
         PlayerAsteroid var21;
         (var21 = new PlayerAsteroid(var12, AEResourceManager.getGeometryResource(var12), var1, var3, var17, var18, var20)).sub_3b3(this);
         var21.sub_20d(this.var_387);
         if (this.var_dd == 23) {
            var21.var_25d.setRenderLayer(1);
         } else {
            var21.var_25d.setRenderLayer(2);
         }

         Explosion var16 = new Explosion(1);
         var21.sub_3c7(var16);
         var10000[var8] = var21;
      }

   }

   private Gun sub_25a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      Gun var9 = null;
      Object var10 = null;
      var10 = null;
      Class_6fcMesh var11 = this.var_43d;
      AbstractMesh var14;
      switch(var3) {
      case 0:
      case 1:
      case 3:
         if (IndexManager.projectilesMeshIDs[var1] >= 0) {
            int var15 = var3 == 0 ? 800 : 400;
            var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, var15), new AEVector3D());
            var14 = AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[var1]);
            if (var3 == 3) {
               var3 = 300 + 70 * (var1 - 28);
               var14.sub_7af(var3, var3, var3);
               var14.sub_980(var1 - 28 + 10, var1 - 28 + 10);
               var14.sub_9aa((byte)1);
               (var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 200), new AEVector3D())).sub_64(20);
            }

            var10 = new BlasterGun(var9, var14);
         } else {
            var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 400), new AEVector3D());
            var10 = new LaserGun(var9, var1, this);
         }
         break;
      case 2:
         var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 200), new AEVector3D());
         var10 = new BlasterGun(var9, AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[var1]));
         break;
      case 4:
      case 5:
         var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(), new AEVector3D());
         (var14 = AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[var1])).setRenderLayer(2);
         var10 = new RocketGun(var9, var14, var3 == 5);
         var11 = this.var_4fc;
         break;
      case 6:
      case 7:
         var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(0, -200, 400), new AEVector3D());
         AbstractMesh var12 = null;
         int var13;
         if (var3 == 7) {
            var12 = AEResourceManager.getGeometryResource(18);
            var13 = var1 - 44 + 1 << 9;
            var12.sub_7af(var13, var13, var13);
         } else {
            var12 = AEResourceManager.getGeometryResource(16);
            var13 = var1 - 41 + 1 << 9;
            var12.sub_7af(var13, var13, var13);
         }

         var12.setRenderLayer(2);
         var10 = new RocketGun(var9, var12, false);
         var11 = this.var_4fc;
         break;
      case 8:
         var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 0), new AEVector3D());
         var10 = new BlasterGun(var9, AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[var1]));
      }

      var9.sub_240(this);
      var9.sub_1df(var11);
      if (this.projectileMesh == null) {
         this.projectileMesh = new AbstractMesh[]{(AbstractMesh)var10};
      } else {
         AbstractMesh[] var16 = new AbstractMesh[this.projectileMesh.length + 1];
         System.arraycopy(this.projectileMesh, 0, var16, 0, this.projectileMesh.length);
         var16[var16.length - 1] = (AbstractMesh)var10;
         this.projectileMesh = var16;
      }

      return var9;
   }

   private void sub_2ba() {
      if (Status.getWingmenNames() != null && Status.wingmenTimeRemaining <= 0) {
         Status.setWingmenNames((String[])null);
         Status.wingmenTimeRemaining = 0;
         Status.var_8f8wingmenVar2 = null;
      } else {
         if (Status.getWingmenNames() != null && this.playerEgo != null) {
            KIPlayer[] var1 = new KIPlayer[Status.getWingmenNames().length];

            for(int var2 = 0; var2 < var1.length; ++var2) {
               GameStatus.random.setSeed((long)(Status.getWingmenNames()[var2].length() * 5));
               int var3 = IndexManager.pickShipId(Status.wingmenRace_);
               GameStatus.random.setSeed(System.currentTimeMillis());
               var1[var2] = this.createShip(5, 0, var3, (Class_e19)null);
               if (var_1297 && this.playerEgo.var_50e != null) {
                  ((PlayerFighter)var1[var2]).setPosition(this.stationaries[2].var_25d.sub_29c() - 3000, this.stationaries[2].var_25d.sub_2c5() + 1000, this.stationaries[2].var_25d.sub_31f() + 5000);
               } else {
                  ((PlayerFighter)var1[var2]).setPosition(this.playerEgo.var_50e.sub_29c() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_2c5() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_31f() + 2000);
               }

               var1[var2].sub_366(true, var2);
               var1[var2].player.setAlwaysFriend(true);
               var1[var2].player.setHitPoints(600);
               var1[var2].name = Status.getWingmenNames()[var2];
               var1[var2].race = Status.wingmenRace_;
               if (Status.getMission().getType() == 12) {
                  var1[var2].var_eb1 = false;
               }
            }

            if (this.ships != null) {
               KIPlayer[] var4 = new KIPlayer[this.ships.length + var1.length];
               System.arraycopy(this.ships, 0, var4, 0, this.ships.length);
               System.arraycopy(var1, 0, var4, this.ships.length, var1.length);
               this.ships = var4;
               return;
            }

            this.ships = var1;
         }

      }
   }

   private void setupMissionEnviroment_() {
      Mission var1;
      if ((var1 = Status.getMission()) != null) {
         int var2;
         int var3;
         if (Status.inAlienOrbit()) {
            var2 = 1 + GameStatus.random.nextInt(3);
            this.ships = new KIPlayer[var2];

            for(var3 = 0; var3 < var2; ++var3) {
               this.ships[var3] = this.createShip(9, 0, IndexManager.pickShipId(9), (Class_e19)null);
               this.ships[var3].setPosition(-40000 + GameStatus.random.nextInt(80000), -40000 + GameStatus.random.nextInt(80000), -40000 + GameStatus.random.nextInt(80000));
               this.ships[var3].player.setAlwaysEnemy(true);
            }

         } else {
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            Class_661 var12;
            int var13;
            if (var1.isEmpty()) {
               this.var_dd = 0;
               this.sub_358();
               this.var_dd = 3;
               boolean var10 = Status.getSystem().getId() == 15 && Status.getCurrentCampaignMission() < 16;
               boolean var15 = Status.getStation().getId() == 78;
               var13 = GameStatus.random.nextInt(100);
               var5 = 0;
               Mission var17;
               if ((var17 = Status.getFreelanceMission()) != null && (var17.getType() == 0 || var17.getType() == 11)) {
                  var5 = (int)((float)var17.getDifficulty() / 10.0F * 5.0F);
               }

               var7 = Status.getSystem().getSafety();
               boolean var11 = !var10 && var13 < (var7 == 0 ? 80 : (var7 == 1 ? 60 : (var7 == 2 ? 35 : 10)));
               this.var_11a5 = new Class_661(new int[]{-50000 + GameStatus.random.nextInt(100000), 0, '썐' + GameStatus.random.nextInt(50000)});
               var13 = Status.getSystem().getRace();
               var8 = GameStatus.random.nextInt(100) < 75 ? 8 : Standing.oppositeFaction(var13);
               this.var_10e2 = var11 ? 0 + GameStatus.random.nextInt(4) : 0;
               this.var_1045 = var15 ? 0 : 0 + GameStatus.random.nextInt(2);
               this.var_108e = !var15 && !var10 ? 0 + GameStatus.random.nextInt(5) : 0;
               this.var_1005 = (var15 ? 0 : GameStatus.random.nextInt(2)) + (var10 ? 0 : var7) + this.var_108e / 4;
               if (Status.getStation().getId() == 10 || this.var_1005 + this.var_1045 + this.var_108e + this.var_10e2 + var5 == 0) {
                  this.var_1005 = 4;
               }

               this.ships = new KIPlayer[this.var_1005 + this.var_1045 + this.var_108e + this.var_10e2 + var5];
               var12 = new Class_661(new int[]{0, 0, 10000});

               for(var2 = 0; var2 < this.var_1005; ++var2) {
                  this.ships[var2] = this.createShip(var13, 0, IndexManager.pickShipId(var13), var12.sub_176());
               }

               AbstractMesh var14 = null;
               if (this.var_1045 > 0) {
                  (var14 = AEResourceManager.getGeometryResource(6783)).setRenderLayer(2);
               }

               for(var6 = this.var_1005; var6 < this.var_1005 + this.var_1045; ++var6) {
                  this.ships[var6] = this.createShip(var13, 0, IndexManager.pickShipId(var8), (Class_e19)null);
                  this.ships[var6].sub_52b();
                  Class_661 var19 = new Class_661(new int[]{-200000 + GameStatus.random.nextInt(400000), -100000 + GameStatus.random.nextInt(200000), '썐' + GameStatus.random.nextInt(100000)});
                  this.ships[var6].sub_3d9(var19);
                  this.ships[var6].sub_ea(true);
                  this.ships[var6].sub_654(var14);
               }

               boolean var18 = var13 == 0 && GameStatus.random.nextInt(100) < 30;

               for(var7 = this.var_1005 + this.var_1045; var7 < this.var_1005 + this.var_1045 + this.var_108e; ++var7) {
                  if (var18 && var7 == this.var_1005 + this.var_1045) {
                     this.ships[var7] = this.createShip(var13, 1, 14, (Class_e19)null);
                     ((PlayerBigShip)this.ships[var7]).sub_59(false);
                     this.ships[var7].setPosition(-40000 + GameStatus.random.nextInt(80000), -5000 + GameStatus.random.nextInt(10000), '鱀' + GameStatus.random.nextInt(80000));
                  } else {
                     this.ships[var7] = this.createShip(var13, 1, var13 == 1 ? 13 : 15, (Class_e19)null);
                     ((PlayerBigShip)this.ships[var7]).sub_59(true);
                     this.ships[var7].setPosition((-80000 + GameStatus.random.nextInt(60000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1), -20000 + GameStatus.random.nextInt(40000), -80000 + GameStatus.random.nextInt(160000));
                  }
               }

               if (var11) {
                  var7 = IndexManager.pickShipId(var8);

                  for(var9 = this.var_1005 + this.var_1045 + this.var_108e; var9 < this.ships.length - var5; ++var9) {
                     this.ships[var9] = this.createShip(var8, 0, var7, this.var_11a5.sub_176());
                  }
               }

               for(var7 = this.var_1005 + this.var_1045 + this.var_108e + this.var_10e2; var7 < this.ships.length; ++var7) {
                  this.ships[var7] = this.createShip(8, 0, IndexManager.pickShipId(8), (Class_e19)null);
                  this.var_1353 = this.playerEgo.sub_8c1();
                  this.ships[var7].setPosition(-30000 + this.var_1353.x + GameStatus.random.nextInt(60000), -30000 + this.var_1353.y + GameStatus.random.nextInt(60000), -30000 + this.var_1353.z + GameStatus.random.nextInt(60000));
               }

            } else {
               var2 = Status.getSystem().getRace();
               var3 = GameStatus.random.nextInt(100) < 75 ? 8 : Standing.oppositeFaction(var2);
               boolean var4 = GameStatus.random.nextInt(2) == 0;
               switch(var1.getType()) {
               case 1:
                  this.var_8c5 = new Class_661(new int[]{-50000 + GameStatus.random.nextInt(100000), 0, var4 ? -50000 : '썐', -50000 + GameStatus.random.nextInt(100000), 0, var4 ? -75000 : 75000, -50000 + GameStatus.random.nextInt(100000), 0, var4 ? -100000 : 100000});
                  var9 = 3 + (int)(5.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var13 = 2 + GameStatus.random.nextInt(6);
                  this.ships = new KIPlayer[var9 + var13];

                  for(var13 = 0; var13 < var9; ++var13) {
                     this.ships[var13] = this.createShip(var3, 0, IndexManager.pickShipId(var3), (Class_e19)null);
                     this.ships[var13].player.setAlwaysEnemy(true);
                  }

                  for(var13 = var9; var13 < this.ships.length; ++var13) {
                     this.ships[var13] = this.createShip(var2, 0, IndexManager.pickShipId(var2), this.var_8c5.sub_11e(GameStatus.random.nextInt(this.var_8c5.sub_2c6())));
                     this.ships[var13].player.setAlwaysFriend(true);
                  }

                  this.var_b00 = new Class_876(7, var9, this);
                  return;
               case 2:
                  AEVector3D var16 = new AEVector3D();
                  this.var_8c5 = new Class_661(new int[]{var16.x + (var4 ? 1 : -1) * (20000 + GameStatus.random.nextInt(20000)), var16.y, var16.z + (var4 ? 1 : -1) * (20000 + GameStatus.random.nextInt(20000)), var16.x, var16.y, var16.z});
                  var7 = var1.getCommodityAmount();
                  var9 = 2 + (int)(4.0F * ((float)var1.getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var9 + var7];

                  for(var13 = 0; var13 < var9; ++var13) {
                     this.ships[var13] = this.createShip(var3, 0, IndexManager.pickShipId(var3), (Class_e19)null);
                     ((PlayerFighter)this.ships[var13]).setPosition(this.var_8c5.sub_11e(0).var_c7 + var13 * 2000, this.var_8c5.sub_11e(0).var_282 + var13 * 2000, this.var_8c5.sub_11e(0).var_2f9 + var13 * 2000);
                     this.ships[var13].player.setAlwaysEnemy(true);
                     this.ships[var13].sub_3d9(this.var_8c5.sub_3ad());
                     this.ships[var13].sub_3ea().sub_285(0);
                  }

                  var13 = 0;

                  for(var8 = var9; var8 < this.ships.length; ++var8) {
                     this.ships[var8] = this.createShip(var2, 0, IndexManager.pickShipId(var2), (Class_e19)null);
                     this.ships[var8].player.setAlwaysFriend(true);
                     var3 = this.asteroids.length / 2 + var13++;
                     var16.set(this.asteroids[var3].sub_a9(var16));
                     this.ships[var8].setPosition(var16.x, var16.y + 2000, var16.z);
                     this.ships[var8].var_9d8 = false;
                     this.ships[var8].var_a3b = null;
                     this.ships[var8].sub_1515sth(0);
                     this.ships[var8].player.setHitPoints(this.ships[var8].player.sub_66c() * 3);
                  }

                  this.var_b00 = new Class_876(18, 0, var9, this);
                  this.var_b24 = new Class_876(18, var9, var9 + var7, this);
                  return;
               case 3:
               case 5:
                  this.var_7df = new Class_661(new int[]{('鱀' + GameStatus.random.nextInt(80000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1), 0, ('鱀' + GameStatus.random.nextInt(80000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1)});
                  this.ships = new KIPlayer[var1.getCommodityAmount()];

                  for(var6 = 0; var6 < this.ships.length; ++var6) {
                     this.ships[var6] = this.createShip(8, 0, IndexManager.pickShipId(8), this.var_7df.sub_11e(0));
                     this.ships[var6].sub_5cc();
                  }

                  ((PlayerFighter)this.ships[this.ships.length - 1]).sub_19(true);
                  ((PlayerFighter)this.ships[this.ships.length - 1]).name = GameStatus.langManager.getLangString(833);
                  this.var_b00 = new Class_876(11, this.ships.length - 1, this);
                  this.var_b24 = new Class_876(12, this.ships.length - 1, this);
                  return;
               case 4:
                  if (GameStatus.random.nextInt(2) == 0) {
                     this.var_7df = new Class_661(new int[]{this.var_387.x, this.var_387.y, this.var_387.z});
                  } else {
                     this.var_7df = sub_4a1(2 + GameStatus.random.nextInt(2));
                  }

                  var8 = 2 + (int)(5.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var8];

                  for(var3 = 0; var3 < this.ships.length; ++var3) {
                     this.ships[var3] = this.createShip(8, 0, IndexManager.pickShipId(8), this.var_7df.sub_11e(GameStatus.random.nextInt(this.var_7df.sub_2c6())));
                     this.ships[var3].sub_5cc();
                  }

                  this.var_b00 = new Class_876(18, 0, var8, this);
                  return;
               case 6:
                  this.var_7df = new Class_661(new int[]{('\uea60' + GameStatus.random.nextInt(80000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1), 0, ('\uea60' + GameStatus.random.nextInt(80000)) * (GameStatus.random.nextInt(2) == 0 ? 1 : -1)});
                  this.ships = new KIPlayer[1];
                  this.ships[0] = this.createShip(8, 0, IndexManager.pickShipId(8), this.var_7df.sub_11e(0));
                  this.ships[0].sub_5cc();
                  var7 = Status.getMission().getDifficulty() * AEMath.min(Status.getLevel(), 20);
                  this.ships[0].player.setMaxHP(var7 + 300);
                  this.var_b00 = new Class_876(1, 0, this);
                  return;
               case 7:
                  var12 = new Class_661(new int[]{-20000 + GameStatus.random.nextInt(40000), 0, 20000 + GameStatus.random.nextInt(40000)});
                  var2 = (int)(2.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var6 = 15 + (int)(35.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var6 + var2];

                  for(var7 = 0; var7 < this.ships.length - var2; ++var7) {
                     this.ships[var7] = this.sub_3f7(var12.sub_11e(0), 9996);
                     this.ships[var7].player.setAlwaysEnemy(true);
                  }

                  for(var7 = this.ships.length - var2; var7 < this.ships.length; ++var7) {
                     this.ships[var7] = this.createShip(8, 0, IndexManager.pickShipId(8), (Class_e19)null);
                  }

                  this.var_b00 = new Class_876(7, this.ships.length - var2, this);
                  this.var_b69 = 121000;
                  return;
               case 9:
                  this.var_8c5 = new Class_661(new int[]{10000, 0, 100000, 10000, 0, 150000, 10000, 0, 200000});
                  var2 = 2 + (int)(6.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var2 + 5];
                  var3 = Standing.oppositeFaction(var1.getClientRace());
                  var9 = var1.getClientRace();

                  for(var13 = 0; var13 < this.ships.length; ++var13) {
                     if (var13 < var2) {
                        this.ships[var13] = this.createShip(var3, 0, IndexManager.pickShipId(var3), this.var_8c5.sub_11e(GameStatus.random.nextInt(this.var_8c5.sub_2c6())));
                        this.ships[var13].sub_5cc();
                        this.ships[var13].player.setAlwaysEnemy(true);
                     } else {
                        this.ships[var13] = this.createShip(var9, 1, var9 == 1 ? 13 : 15, (Class_e19)null);
                        this.ships[var13].player.setMaxHP(100 + (AEMath.min(Status.getLevel(), 20) << 1) + (Status.getCurrentCampaignMission() << 1));
                        ((PlayerBigShip)this.ships[var13]).sub_59(true);
                        this.ships[var13].player.setAlwaysFriend(true);
                        this.ships[var13].var_9d8 = false;
                        this.ships[var13].var_a3b = null;
                     }
                  }

                  ((PlayerBigShip)this.ships[var2]).setPosition(-2500, -300, 27000);
                  ((PlayerBigShip)this.ships[var2 + 1]).setPosition(6500, 3000, 24000);
                  ((PlayerBigShip)this.ships[var2 + 2]).setPosition(-4000, -2000, 19000);
                  ((PlayerBigShip)this.ships[var2 + 3]).setPosition(9000, -6000, 17000);
                  ((PlayerBigShip)this.ships[var2 + 4]).setPosition(3000, 7000, 15000);
                  this.var_b00 = new Class_876(18, 0, var2, this);
                  this.var_b24 = new Class_876(18, var2, var2 + 5, this);
                  return;
               case 10:
                  this.var_7df = new Class_661(new int[]{-2500 + GameStatus.random.nextInt(5000), -2500 + GameStatus.random.nextInt(5000), 80000 + GameStatus.random.nextInt(30000), -2500 + GameStatus.random.nextInt(5000), -2500 + GameStatus.random.nextInt(5000), 120000 + GameStatus.random.nextInt(30000)});
                  var2 = 2 + GameStatus.random.nextInt(2);
                  var13 = 2 + (int)(2.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var3 = Standing.oppositeFaction(var1.getClientRace());
                  this.ships = new KIPlayer[var2 + var13];

                  for(var9 = 0; var9 < var2; ++var9) {
                     this.ships[var9] = this.createShip(var3, 1, var3 == 1 ? 13 : 15, this.var_7df.sub_11e(1));
                     this.ships[var9].sub_5cc();
                     this.ships[var9].player.setAlwaysEnemy(true);
                     ((PlayerBigShip)((PlayerBigShip)this.ships[var9])).sub_59(false);
                     ((PlayerBigShip)((PlayerBigShip)this.ships[var9])).setPosition(this.var_7df.sub_11e(1).var_c7 + -10000 + GameStatus.random.nextInt(20000), this.var_7df.sub_11e(1).var_282 + -10000 + GameStatus.random.nextInt(20000), this.var_7df.sub_11e(1).var_2f9 + -10000 + GameStatus.random.nextInt(20000));
                  }

                  for(var9 = var2; var9 < this.ships.length; ++var9) {
                     this.ships[var9] = this.createShip(var3, 0, IndexManager.pickShipId(var3), this.var_7df.sub_11e(GameStatus.random.nextInt(this.var_7df.sub_2c6())));
                     this.ships[var9].sub_5cc();
                     this.ships[var9].player.setAlwaysEnemy(true);
                  }

                  this.var_b00 = new Class_876(7, var2, this);
               case 8:
               default:
                  return;
               case 11:
                  return;
               case 12:
                  this.var_7df = sub_4a1(3 + GameStatus.random.nextInt(2));
                  if ((var5 = 3 + (int)(4.0F * ((float)var1.getDifficulty() / 10.0F))) % 2 == 0) {
                     ++var5;
                  }

                  if (var1.isCampaignMission()) {
                     var5 = 7;
                     this.var_7df = new Class_661(new int[]{80000, -20000, 80000, 70000, 0, -80000, -100000, 10000, -80000, -80000, 20000, 90000});
                  }

                  this.ships = new KIPlayer[var5 + 1];
                  this.ships[0] = this.createShip(var1.getAgent().getRace(), 0, IndexManager.pickShipId(var1.getAgent().getRace()), (Class_e19)null);
                  ((PlayerFighter)this.ships[0]).setPosition(this.playerEgo.var_50e.sub_29c() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_2c5() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_31f() + 1000);
                  ((PlayerFighter)this.ships[0]).loadShips(3);
                  ((PlayerFighter)this.ships[0]).sub_1e8(3);
                  this.ships[0].player.setHitPoints(9999999);
                  this.ships[0].sub_3d9(this.var_7df.sub_3ad());
                  this.ships[0].player.setAlwaysFriend(true);
                  this.ships[0].name = var1.getAgent().getFullName();
                  this.ships[0].var_a3b = null;

                  for(var6 = 1; var6 < this.ships.length; ++var6) {
                     this.ships[var6] = this.createShip(8, 0, IndexManager.pickShipId(8), this.var_7df.sub_11e(GameStatus.random.nextInt(this.var_7df.sub_2c6())));
                     this.ships[var6].sub_5cc();
                  }

                  this.var_b00 = new Class_876(20, 1, var5 + 1, this);
                  this.var_b24 = new Class_876(21, 1, var5 + 1, this);
               }
            }
         }
      }
   }

   private void sub_313() {
      AEVector3D var1 = new AEVector3D();
      int var2;
      int var3;
      int var4;
      int var5;
      label214:
      switch(Status.getCurrentCampaignMission()) {
      case 0:
         this.ships = new KIPlayer[3];

         for(var2 = 0; var2 < this.ships.length; ++var2) {
            this.ships[var2] = this.createShip(8, 0, 10, (Class_e19)null);
            this.ships[var2].sub_5cc();
            this.ships[var2].player.setAlwaysEnemy(true);
            var1 = this.asteroids[this.asteroids.length - 1 - var2].sub_a9(var1);
            this.ships[var2].setPosition(var1.x, var1.y, var1.z + 2000);
            this.ships[var2].var_9d8 = false;
            this.ships[var2].var_a3b = null;
            this.ships[var2].player.setHitPoints(150);
            if (var2 < 3) {
               ((PlayerFighter)this.ships[var2]).sub_2b8(false);
            }
         }

         this.ships[2].setPosition(0, 0, -40000);
         this.var_8c5 = new Class_661(new int[]{0, 0, -30000, 0, 0, 0});
         this.ships[2].sub_3d9(this.var_8c5);
         this.playerEgo.player.setHitPoints(9999999);
         break;
      case 1:
         this.ships = new KIPlayer[1];
         this.ships[0] = this.createShip(3, 0, 1, (Class_e19)null);
         this.ships[0].setPosition(300, 50, -8000);
         this.var_8c5 = new Class_661(new int[]{0, 0, -5000, 0, 0, 0});
         this.ships[0].sub_3d9(this.var_8c5);
         this.ships[0].sub_1515sth(0);
      case 2:
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 15:
      case 17:
      case 18:
      case 19:
      case 20:
      case 22:
      case 23:
      case 27:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 37:
      case 39:
      default:
         break;
      case 4:
         this.ships = new KIPlayer[1];
         this.ships[0] = this.createShip(8, 0, 1, (Class_e19)null);
         this.ships[0].sub_2ab(false);
         this.ships[0].player.setAlwaysEnemy(true);
         this.ships[0].setPosition(0, 0, -200000);
         this.ships[0].sub_5cc();
         break;
      case 7:
         this.var_7df = new Class_661(new int[]{20000, 7000, 120000});
         this.ships = new KIPlayer[4];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(8, 0, 2, this.var_7df.sub_176());
            this.ships[var5].sub_5cc();
         }

         this.ships[3] = this.createShip(3, 0, 1, (Class_e19)null);
         this.ships[3].player.setAlwaysFriend(true);
         this.ships[3].setPosition(this.playerEgo.var_50e.sub_29c() + 700, this.playerEgo.var_50e.sub_2c5() + 50, this.playerEgo.var_50e.sub_31f() + 1000);
         this.ships[3].sub_3d9(this.var_7df.sub_3ad());
         this.ships[3].player.setHitPoints(9999999);
         this.ships[3].name = GameStatus.langManager.getLangString(821);
         ((PlayerFighter)this.ships[3]).sub_227(0);
         this.var_b00 = new Class_876(18, 0, 3, this);
         break;
      case 14:
         this.var_8c5 = new Class_661(new int[]{0, 0, 50000});
         this.ships = new KIPlayer[7];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(8, 0, 0, this.var_8c5.sub_176());
         }

         for(var5 = 3; var5 < 5; ++var5) {
            this.ships[var5] = this.createShip(0, 0, 1, this.var_8c5.sub_176());
         }

         for(var5 = 5; var5 < 7; ++var5) {
            this.ships[var5] = this.createShip(0, 1, 14, this.var_8c5.sub_176());
         }

         this.var_b00 = new Class_876(22, 0, this);
         break;
      case 16:
         this.var_8c5 = new Class_661(new int[]{0, 0, 130000});
         this.ships = new KIPlayer[7];

         for(var5 = 0; var5 < 4; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, this.var_8c5.sub_176());
         }

         for(var5 = 4; var5 < 7; ++var5) {
            this.ships[var5] = this.createShip(0, 0, 1, (Class_e19)null);
            this.ships[var5].player.setAlwaysFriend(true);
            this.ships[var5].player.setHitPoints(600);
            ((PlayerFighter)this.ships[var5]).setPosition(this.playerEgo.var_50e.sub_29c() + -2000 + GameStatus.random.nextInt(4000), this.playerEgo.var_50e.sub_2c5() + -1700 + GameStatus.random.nextInt(3400), this.playerEgo.var_50e.sub_31f() + 2000 + -2000 + GameStatus.random.nextInt(4000));
         }

         this.var_b00 = new Class_876(18, 0, 4, this);
         break;
      case 21:
         this.var_8c5 = new Class_661(new int[]{40000, -40000, 120000});
         this.var_7df = this.var_8c5.sub_3ad();
         this.var_8c5.sub_e1(true);
         this.ships = new KIPlayer[3];
         this.ships[1] = this.createShip(0, 0, 0, this.var_8c5.sub_176());
         this.ships[2] = this.createShip(0, 0, 0, this.var_8c5.sub_176());
         this.ships[0] = this.createShip(0, 0, 1, this.var_8c5.sub_176());
         this.ships[0].name = GameStatus.langManager.getLangString(833);

         for(var5 = 0; var5 < this.ships.length; ++var5) {
            this.ships[var5].sub_5cc();
            this.ships[var5].sub_3d9(this.var_8c5.sub_3ad());
            this.ships[var5].var_379.setRotation(0, 2048, 0);
         }

         this.var_b00 = new Class_876(22, 0, this);
         this.var_b24 = new Class_876(7, 1, this);
         break;
      case 24:
         this.var_8c5 = new Class_661(new int[]{100000, 0, 0, 100000, 0, -30000});
         this.ships = new KIPlayer[3];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, this.var_8c5.sub_176());
            this.ships[var5].sub_3d9(this.var_8c5.sub_3ad());
         }

         this.stationaries[3].sub_73(false);
         this.var_b00 = new Class_876(22, 0, this);
         break;
      case 25:
      case 29:
         this.ships = new KIPlayer[3];
         var5 = 0;

         while(true) {
            if (var5 >= 3) {
               break label214;
            }

            this.ships[var5] = this.createShip(9, 0, 8, (Class_e19)null);
            var2 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            var3 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            var4 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            this.ships[var5].setPosition(var2, var3, var4);
            ++var5;
         }
      case 26:
         this.ships = new KIPlayer[2];

         for(var5 = 0; var5 < 2; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, (Class_e19)null);
            ((PlayerFighter)this.ships[var5]).var_379.sub_8c9(this.playerEgo.var_50e.sub_85f());
            ((PlayerFighter)this.ships[var5]).setPosition(this.playerEgo.var_50e.sub_29c() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_2c5() + -700 + GameStatus.random.nextInt(1400), this.playerEgo.var_50e.sub_31f() + 2000);
         }

         this.var_b00 = new Class_876(7, 2, this);
         break;
      case 28:
         this.var_1353 = this.stationaries[3].sub_a9(this.var_1353);
         this.var_8c5 = new Class_661(new int[]{this.var_1353.x, this.var_1353.y, this.var_1353.z});
         this.ships = new KIPlayer[5];
         var5 = 0;

         while(true) {
            if (var5 >= 5) {
               break label214;
            }

            this.ships[var5] = this.createShip(9, 0, 8, this.var_8c5.sub_176());
            ++var5;
         }
      case 36:
         Status.getMission().setAgent(new Agent(-1, GameStatus.langManager.getLangString(826), 29, 5, 1, true, -1, -1, -1));
         this.setupMissionEnviroment_();
         break;
      case 38:
         this.var_8c5 = new Class_661(new int[]{0, 10000, 50000});
         this.ships = new KIPlayer[7];

         for(var3 = 0; var3 < 2; ++var3) {
            this.ships[var3] = this.createShip(2, 1, 15, this.var_8c5.sub_176());
            this.ships[var3].player.setAlwaysFriend(true);
            ((PlayerBigShip)((PlayerBigShip)this.ships[var3])).sub_59(false);
            ((PlayerBigShip)((PlayerBigShip)this.ships[var3])).setPosition(this.var_8c5.sub_176().var_c7 + -10000 + GameStatus.random.nextInt(20000), this.var_8c5.sub_176().var_282 + -10000 + GameStatus.random.nextInt(20000), this.var_8c5.sub_176().var_2f9 + -10000 + GameStatus.random.nextInt(20000));
         }

         for(var3 = 2; var3 < this.ships.length; ++var3) {
            this.ships[var3] = this.createShip(3, 0, IndexManager.pickShipId(3), this.var_8c5.sub_176());
            this.ships[var3].sub_5cc();
            this.ships[var3].player.setAlwaysEnemy(true);
         }

         this.var_b00 = new Class_876(18, 2, 7, this);
         this.var_b24 = new Class_876(7, 2, this);
         break;
      case 40:
         this.var_8c5 = new Class_661(new int[]{-20000, -3000, 200000});
         this.var_863 = new Class_661(new int[]{-20000, -3000, 65000, -20000, -3000, 200000});
         this.ships = new KIPlayer[9];
         this.ships[0] = this.createShip(0, 1, 13, (Class_e19)null);
         this.ships[0].player.setAlwaysFriend(true);
         this.ships[0].name = GameStatus.langManager.getLangString(826);
         this.ships[0].player.setMaxHP(1200 + 5 * Status.getLevel());
         ((PlayerBigShip)((PlayerBigShip)this.ships[0])).sub_59(true);
         ((PlayerBigShip)((PlayerBigShip)this.ships[0])).setPosition(this.var_863.sub_11e(0).var_c7, this.var_863.sub_11e(0).var_282, this.var_863.sub_11e(0).var_2f9);

         for(var4 = 1; var4 < 5; ++var4) {
            this.ships[var4] = this.createShip(0, 0, IndexManager.pickShipId(0), (Class_e19)null);
            this.ships[var4].sub_3d9(this.var_863.sub_3ad());
            this.ships[var4].player.setAlwaysFriend(true);
            if (var4 == 2) {
               this.ships[2].name = GameStatus.langManager.getLangString(827);
            }
         }

         for(var4 = 5; var4 < 9; ++var4) {
            this.ships[var4] = this.createShip(9, 0, 8, this.var_8c5.sub_176());
            this.ships[var4].player.setAlwaysEnemy(true);
         }

         this.stationaries[3].setPosition(-20000, -3000, 200000);
         if (var_1297) {
            this.playerEgo.sub_ae0(-65000, 0, 80000);
            this.playerEgo.var_50e.setRotation(0, 1024, 0);
         }

         this.var_b24 = new Class_876(7, 1, this);
         break;
      case 41:
         this.ships = new KIPlayer[5];
         this.ships[0] = this.createShip(1, 1, 13, (Class_e19)null);
         this.ships[0].player.setAlwaysFriend(true);
         this.ships[0].name = GameStatus.langManager.getLangString(826);
         this.ships[0].player.setHitPoints(var_12b5);
         ((PlayerBigShip)((PlayerBigShip)this.ships[0])).sub_59(true);
         ((PlayerBigShip)((PlayerBigShip)this.ships[0])).setPosition(0, 0, -200000);

         for(var4 = 1; var4 < 5; ++var4) {
            this.ships[var4] = this.createShip(9, 0, 8, (Class_e19)null);
            this.ships[var4].player.setAlwaysEnemy(true);
            var5 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            var2 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            var3 = (GameStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GameStatus.random.nextInt(80000));
            this.ships[var4].setPosition(var5, var2, var3);
         }

         this.playerEgo.sub_ae0(3000, 2000, -220000);
         this.playerEgo.var_50e.setRotation(0, 0, 0);
         this.var_b00 = new Class_876(25, 0, this);
         this.var_b24 = new Class_876(7, 1, this);
      }

      this.var_8d0 = this.sub_4ab(Status.getCurrentCampaignMission());
   }

   private void sub_358() {
      int var1;
      int var3;
      label88:
      switch(this.var_dd) {
      case 4:
         int var11;
         var1 = (var11 = Status.getSystem().getRace() == 1 ? 2 : (Status.getSystem().getRace() == 0 ? 0 : 1)) == 0 ? 20 : 6;
         Agent[] var9;
         var3 = (var9 = Status.getStation().getBarAgents()).length;
         this.ships = new KIPlayer[var3 + var1];
         boolean[] var5 = new boolean[this.var_2ed.length / 3];

         int var6;
         for(var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = false;
         }

         for(var6 = 0; var6 < var3; ++var6) {
            short var7 = IndexManager.barFiguresMeshIDs[var9[var6].getRace()];
            if (var9[var6].getRace() == 0 && !var9[var6].sub_302()) {
               var7 = 14224;
            }

            boolean var8 = false;

            int var13;
            do {
               var13 = GameStatus.random.nextInt(var5.length);
            } while(var5[var13]);

            var5[var13] = true;
            this.ships[var6] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var7), this.var_2ed[var13 * 3], this.var_2ed[var13 * 3 + 1], this.var_2ed[var13 * 3 + 2]);
            this.ships[var6].var_25d.setRenderLayer(2);
            this.ships[var6].var_25d.sub_a04();
         }

         if (var1 <= 0) {
            return;
         }

         GameStatus.random.setSeed((long)(Status.getStation().getId() + 1000));
         var6 = 4096 / var1;
         int var12 = var3;

         while(true) {
            if (var12 >= this.ships.length) {
               break label88;
            }

            short var14 = IndexManager.barMeshIDs[var11][GameStatus.random.nextInt(IndexManager.barMeshIDs[var11].length)];
            this.ships[var12] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var14), 0, 0, 0);
            this.ships[var12].var_25d.setRenderLayer(2);
            this.ships[var12].var_25d.setRotation(0, var12 * var6, 0);
            ++var12;
         }
      case 23:
         var1 = Status.getSystem().getRace() == 1 ? 2 : (Status.getSystem().getRace() == 0 ? 0 : 1);
         this.ships = new KIPlayer[7];
         this.ships[0] = this.createShip(Status.getShip().getRace(), 0, Status.getShip().getIndex(), (Class_e19)null);
         this.ships[0].setPosition(0, 1200, 10240 - Ship.previewPivotShift[Status.getShip().getIndex()] + 100);
         this.ships[0].var_379.setRotation(0, 2048, 0);
         this.ships[0].sub_3d9(new Class_661(new int[]{0, 500, -100000}));
         ((PlayerFighter)this.ships[0]).sub_262();
         ((PlayerFighter)this.ships[0]).sub_2b8(false);
         this.ships[0].sub_5cc();
         this.ships[0].player.setAlwaysFriend(true);
         GameStatus.random.setSeed((long)Status.getStation().getId());
         int var2 = 0;

         for(var3 = 1; var3 < 6; ++var3) {
            short var4 = IndexManager.hangarMeshIDs[var1][GameStatus.random.nextInt(IndexManager.hangarMeshIDs[var1].length - 3)];
            if (var3 == 5) {
               var4 = IndexManager.hangarMeshIDs[var1][IndexManager.hangarMeshIDs[var1].length - 2];
            } else if (var3 == 1) {
               var4 = IndexManager.hangarMeshIDs[var1][IndexManager.hangarMeshIDs[var1].length - 1];
            }

            this.ships[var3] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var4), 0, 0, var2 << 12);
            this.ships[var3].var_25d.setRenderLayer(1);
            this.ships[var3].var_25d.setRotation(0, 2048, 0);
            ++var2;
         }

         short var10 = IndexManager.hangarMeshIDs[var1][IndexManager.hangarMeshIDs[var1].length - 3];
         this.ships[this.ships.length - 1] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var10), 0, 0, 8192);
         this.ships[this.ships.length - 1].var_25d.setRenderLayer(2);
         this.ships[this.ships.length - 1].var_25d.setRotation(0, 2048, 0);
         break;
      default:
         return;
      }

      GameStatus.random.setSeed(System.currentTimeMillis());
   }

   private void sub_375() {
      this.var_6e9 = null;
      int var1 = (int)((float)AEMath.min(Status.getLevel(), 20) / 1.8F) + (int)((float)Status.getCurrentCampaignMission() / 5.0F);
      int var2 = 0;
      if (this.ships != null) {
         int var3;
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            if (this.ships[var3] != null && this.ships[var3].var_eb1) {
               ++var2;
               if (this.ships[var3].sub_32a()) {
                  ++var2;
               }
            }
         }

         this.var_6e9 = new AbstractMesh[var2];
         var3 = 0;

         for(var2 = 0; var2 < this.ships.length; ++var2) {
            Gun var6;
            if (this.ships[var2] != null && this.ships[var2].var_eb1) {
               int var4 = var1 + 2;
               if (Status.getMission().getType() == 12 && this.ships[var2].player.isAlwaysFriend()) {
                  var4 = Status.getShip().getFirePower() + 2;
               } else if (Status.getCurrentCampaignMission() == 4) {
                  var4 = 1;
               }

               (var6 = new Gun(0, var4, 4, -1, 3000, 600 - (Status.getCurrentCampaignMission() << 1), 16.0F, (AEVector3D)null, (AEVector3D)null)).sub_c1(true);
               var6.sub_240(this);
               var6.sub_1df(this.var_43d);
               int var5 = this.ships[var2].race == 9 ? 7 : (this.ships[var2].race == 0 ? 1 : (this.ships[var2].race == 1 ? 3 : 4));
               this.var_6e9[var3] = new BlasterGun(var6, AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[var5]));
               this.var_6e9[var3].setRenderLayer(2);
               ++var3;
               this.ships[var2].sub_43a(var6, 0);
            }

            if (this.ships[var2].sub_32a() && this.ships[var2].var_eb1) {
               (var6 = new Gun(18, 0, 4, -1, 3000, 400, 16.0F, (AEVector3D)null, (AEVector3D)null)).sub_c1(true);
               var6.sub_240(this);
               var6.sub_1df(this.var_43d);
               var6.index = 18;
               var6.subType = 1;
               this.var_6e9[var3] = new BlasterGun(var6, AEResourceManager.getGeometryResource(IndexManager.projectilesMeshIDs[18]));
               this.var_6e9[var3].setRenderLayer(2);
               ++var3;
               this.ships[var2].sub_43a(var6, 1);
            }
         }
      }

   }

   private void sub_3c3() {
      if (GameStatus.var_bfb.sub_28() != GameStatus.scenes[1]) {
         Player[] var1;
         int var2;
         if (this.ships != null && this.playerEgo != null) {
            var1 = new Player[this.ships.length];

            for(var2 = 0; var2 < var1.length; ++var2) {
               var1[var2] = this.ships[var2].player;
            }

            this.playerEgo.player.sub_218(var1);
         }

         if (this.asteroids != null && this.playerEgo != null) {
            var1 = new Player[this.asteroids.length];

            for(var2 = 0; var2 < var1.length; ++var2) {
               var1[var2] = this.asteroids[var2].player;
            }

            this.playerEgo.player.sub_266(var1);
         }

         if (this.ships != null) {
            for(int var7 = 0; var7 < this.ships.length; ++var7) {
               var2 = this.ships[var7].race;
               boolean var3 = this.ships[var7].sub_32a();
               int var4 = 0;

               for(int var5 = 0; var5 < this.ships.length; ++var5) {
                  if (this.ships[var5] != this.ships[var7] && (this.ships[var5].race != var2 || var3)) {
                     ++var4;
                  }
               }

               Player[] var9 = new Player[(this.playerEgo == null ? 0 : 1) + var4];
               int var6;
               Mission var8;
               if (((var8 = Status.getMission()).getType() != 12 || var7 % 2 != 1) && var8.getType() != 2 && var8.getType() != 9 && (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 40) && (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 41)) {
                  var4 = 0;
                  if (this.playerEgo != null) {
                     var9[0] = this.playerEgo.player;
                     var4 = 1;
                  }

                  for(var6 = 0; var6 < this.ships.length; ++var6) {
                     if (this.ships[var6] != this.ships[var7] && (this.ships[var6].race != var2 || var3)) {
                        var9[var4++] = this.ships[var6].player;
                     }
                  }
               } else {
                  if (this.ships[var7].player.isAlwaysFriend()) {
                     var9 = new Player[1];
                  } else {
                     var4 = 0;

                     for(var6 = 0; var6 < this.ships.length; ++var6) {
                        if (this.ships[var6] != this.ships[var7] && (this.ships[var6].race != var2 || var3)) {
                           var9[var4++] = this.ships[var6].player;
                        }
                     }
                  }

                  var9[var9.length - 1] = this.playerEgo.player;
               }

               this.ships[var7].player.sub_266(var9);
            }
         }

      }
   }

   private KIPlayer sub_3f7(Class_e19 var1, int var2) {
      var2 = var1 != null ? var1.var_c7 : 0;
      int var3 = var1 != null ? var1.var_282 : 0;
      int var6 = var1 != null ? var1.var_2f9 : 0;
      var2 = var2 + GameStatus.random.nextInt(20000) - 10000;
      var3 = var3 + GameStatus.random.nextInt(20000) - 10000;
      var6 = var6 + GameStatus.random.nextInt(20000) - 10000;
      AbstractMesh var4;
      (var4 = AEResourceManager.getGeometryResource(9996)).setRenderLayer(2);
      Player var5 = null;
      var5 = null;
      var5 = new Player(1000.0F, 1, 0, 0, 0);
      PlayerJunk var7;
      (var7 = new PlayerJunk(9996, var5, var4, var2, var3, var6)).sub_3c7(new Explosion(1));
      var7.var_25d.sub_5c5(var2, var3, var6);
      var7.sub_3b3(this);
      return var7;
   }

   private KIPlayer createShip(int var1, int var2, int var3, Class_e19 var4) {
      int var5 = var4 != null ? var4.var_c7 : 0;
      int var6 = var4 != null ? var4.var_282 : 0;
      int var12 = var4 != null ? var4.var_2f9 : 0;
      int var7 = GameStatus.random.nextInt(40000) - 20000;
      int var8 = GameStatus.random.nextInt(40000) - 20000;
      int var9 = GameStatus.random.nextInt(40000) - 20000;
      var5 += var7;
      var6 += var8;
      var12 += var9;
      Object var14 = null;
      Explosion var15 = null;
      Player var13 = null;
      var7 = 20 + AEMath.min(Status.getLevel(), 20) * 15 + (Status.getCurrentCampaignMission() << 2);
      int var10 = 40 + AEMath.min(Status.getLevel(), 20) * 5;
      int var11 = 15000;
      if (var2 == 1) {
         var7 <<= 2;
         var10 *= 3;
         var11 = 15000 * 3;
         if (var3 == 14) {
            var7 *= 5;
         }
      }

      (var13 = new Player(2000.0F, var7, 1, 1, 0)).sub_137(var10, var11);
      switch(var2) {
      case 0:
         var14 = new PlayerFighter(var3, var1, var13, (AbstractMesh)null, var5, var6, var12);
         var15 = new Explosion(1);
         ((KIPlayer)var14).sub_ca(IndexManager.buildShip_(var3, var1), var3);
         break;
      case 1:
         var14 = new PlayerBigShip(var3, var1, var13, (AbstractMesh)null, var5, var6, var12);
         AbstractBounding[] var16;
         (var16 = new AbstractBounding[1])[0] = new BoundingBox(var5, var6, var12, 0, 300, 0, 4000, 4000, 15000);
         ((PlayerBigShip)((PlayerBigShip)var14)).setBounds(var16);
         (var15 = new Explosion(1)).sub_b1();
         ((KIPlayer)var14).sub_ca(IndexManager.buildShip_(var3, var1), var3);
      }

      ((KIPlayer)var14).sub_3b3(this);
      ((KIPlayer)var14).sub_3c7(var15);
      return (KIPlayer)var14;
   }

   private static Class_661 sub_4a1(int var0) {
      int[] var3 = new int[var0 * 3];

      for(int var1 = 0; var1 < var3.length; var1 += 3) {
         int var2 = GameStatus.random.nextInt(2) == 0 ? 1 : -1;
         var3[var1] = '썐' + GameStatus.random.nextInt(30000);
         var3[var1] *= var2;
         var3[var1 + 1] = -10000 + GameStatus.random.nextInt(20000);
         if (var1 != 0) {
            var3[var1 + 2] = var3[var1 - 3 + 2] + '썐' + GameStatus.random.nextInt(30000);
         } else {
            var3[var1 + 2] = '썐' + GameStatus.random.nextInt(30000);
         }
      }

      return new Class_661(var3);
   }

   private Class_85e[] sub_4ab(int var1) {
      this.var_8d0 = null;
      switch(var1) {
      case 0:
         this.var_8d0 = new Class_85e[22];
         this.var_8d0[0] = new Class_85e(844, 17, 5, 15000);
         this.var_8d0[1] = new Class_85e(845, 0, 6, 0);
         this.var_8d0[2] = new Class_85e(846, 0, 6, 1);
         this.var_8d0[3] = new Class_85e(847, 10, 6, 2);
         this.var_8d0[4] = new Class_85e(848, 9, 6, 3);
         this.var_8d0[5] = new Class_85e(849, 9, 6, 4);
         this.var_8d0[6] = new Class_85e(850, 11, 6, 5);
         this.var_8d0[7] = new Class_85e(851, 9, 6, 6);
         this.var_8d0[8] = new Class_85e(852, 0, 6, 7);
         this.var_8d0[9] = new Class_85e(854, 0, 9, new int[]{0, 1, 2});
         this.var_8d0[10] = new Class_85e(855, 0, 6, 9);
         this.var_8d0[11] = new Class_85e(856, 0, 6, 10);
         this.var_8d0[12] = new Class_85e(857, 15, 6, 11);
         this.var_8d0[13] = new Class_85e(858, 0, 6, 12);
         this.var_8d0[14] = new Class_85e(859, 0, 6, 13);
         this.var_8d0[15] = new Class_85e(860, 15, 6, 14);
         this.var_8d0[16] = new Class_85e(861, 0, 6, 15);
         this.var_8d0[17] = new Class_85e(862, 15, 6, 16);
         this.var_8d0[18] = new Class_85e(863, 0, 6, 17);
         this.var_8d0[19] = new Class_85e(864, 0, 6, 18);
         this.var_8d0[20] = new Class_85e(865, 15, 6, 19);
         this.var_8d0[21] = new Class_85e(866, 0, 6, 20);
         break;
      case 1:
         this.var_8d0 = new Class_85e[3];
         this.var_8d0[0] = new Class_85e(867, 2, 5, 10000);
         this.var_8d0[1] = new Class_85e(868, 2, 6, 0);
         this.var_8d0[2] = new Class_85e(869, 2, 6, 1);
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 15:
      case 17:
      case 18:
      case 19:
      case 20:
      case 22:
      case 23:
      case 25:
      case 26:
      case 27:
      case 28:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 39:
      default:
         break;
      case 7:
         this.var_8d0 = new Class_85e[2];
         this.var_8d0[0] = new Class_85e(909, 2, 16, 0);
         this.var_8d0[1] = new Class_85e(910, 0, 6, 0);
         break;
      case 14:
         this.var_8d0 = new Class_85e[4];
         this.var_8d0[0] = new Class_85e(968, 18, 5, 6000);
         this.var_8d0[1] = new Class_85e(969, 0, 20, 2);
         this.var_8d0[2] = new Class_85e(970, 0, 6, 1);
         this.var_8d0[3] = new Class_85e(971, 18, 6, 2);
         break;
      case 16:
         this.var_8d0 = new Class_85e[2];
         this.var_8d0[0] = new Class_85e(983, 19, 5, 6000);
         this.var_8d0[1] = new Class_85e(984, 0, 6, 0);
         break;
      case 21:
         this.var_8d0 = new Class_85e[3];
         this.var_8d0[0] = new Class_85e(1018, 14, 8, 0);
         this.var_8d0[1] = new Class_85e(1019, 0, 6, 0);
         this.var_8d0[2] = new Class_85e(1020, 14, 21, 0);
         break;
      case 24:
         this.var_8d0 = new Class_85e[2];
         this.var_8d0[0] = new Class_85e(1047, 6, 22, 3);
         this.var_8d0[1] = new Class_85e(1048, 6, 6, 0);
         break;
      case 29:
         this.var_8d0 = new Class_85e[5];
         this.var_8d0[0] = new Class_85e(1073, 0, 23, 0);
         this.var_8d0[1] = new Class_85e(1074, 0, 6, 0);
         this.var_8d0[2] = new Class_85e(1075, 0, 6, 1);
         this.var_8d0[3] = new Class_85e(1076, 19, 5, 120000);
         this.var_8d0[4] = new Class_85e(1077, 0, 6, 3);
         break;
      case 38:
         this.var_8d0 = new Class_85e[1];
         this.var_8d0[0] = new Class_85e(1146, 21, 5, 5000);
         break;
      case 40:
         this.var_8d0 = new Class_85e[2];
         this.var_8d0[0] = new Class_85e(1163, 7, 12, 0);
         this.var_8d0[1] = new Class_85e(1164, 0, 24, 0);
         break;
      case 41:
         this.var_8d0 = new Class_85e[1];
         this.var_8d0[0] = new Class_85e(1163, 7, 12, 0);
      }

      return this.var_8d0;
   }

   public final PlayerEgo getPlayerEgo() {
      return this.playerEgo;
   }

   public final KIPlayer[] getShips() {
      return this.ships;
   }

   public final KIPlayer[] sub_5b7() {
      return this.stationaries;
   }

   public final KIPlayer[] sub_5cf() {
      return this.asteroids;
   }

   public final Class_e19 sub_617() {
      return this.var_3ad;
   }

   public final Class_661 sub_6a6() {
      return this.var_7df;
   }

   public final void sub_6ce(Class_661 var1) {
      this.var_7df = null;
   }

   public final void setupExplosionFlashColors(int var1) {
      if (this.var_ff8 < 3 || this.var_f60 <= this.var_fa1 >> 1) {
         this.exploding_ = true;
         this.var_ff8 = var1;
         this.var_fa1 = var1 >= 3 ? 10000 : (var1 == 1 ? 7000 : 2000);
         this.var_f60 = this.var_fa1;
         if (var1 == 3) {
            this.explosionR = 255.0F;
            this.explosionG = 255.0F;
            this.explosionB = 255.0F;
            this.playerEgo.sub_1281();
         } else if (var1 == 2) {
            this.explosionR = (float)bgR * 1.5F;
            this.explosionG = (float)bgG * 1.5F;
            this.explosionB = (float)bgB * 1.5F;
         } else if (var1 == 4) {
            this.explosionR = 0.0F;
            this.explosionG = 0.0F;
            this.explosionB = 255.0F;
         } else {
            float var2 = var1 == 1 ? 8.0F : 5.0F;
            this.explosionR = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgR * var2)));
            this.explosionG = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgG * var2)));
            this.explosionB = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgB * var2)));
         }
      }
   }

   public final void sub_753(boolean var1) {
      --this.var_902;
      ++this.var_9fe;
      if (!var1) {
         Status.incKills();
         ++this.egoScore;
      } else {
         ++this.challengerScore;
      }

      this.setupExplosionFlashColors(1);
   }

   public final void sub_7a1() {
      ++Status.var_ea8;
      --this.var_902;
   }

   public final void sub_7c2() {
      --this.var_977;
   }

   public static void sub_7cd(int var0) {
      if (Status.wingmenNames.length > 1) {
         Status.wingmenNames[var0] = null;
         String[] var3 = new String[Status.wingmenNames.length - 1];
         int var1 = 0;

         for(int var2 = 0; var2 < Status.wingmenNames.length; ++var2) {
            if (Status.wingmenNames[var2] != null) {
               var3[var1++] = Status.wingmenNames[var2];
            }
         }

         Status.wingmenNames = var3;
      } else {
         Status.wingmenNames = null;
         Status.var_8f8wingmenVar2 = null;
      }
   }

   public final void sub_7df() {
      --this.var_9e4;
   }

   public final int sub_7f9() {
      return 0;
   }

   public final int sub_840() {
      return this.var_902;
   }

   public final int sub_85e() {
      return this.var_977;
   }

   public final Class_85e[] sub_872() {
      return this.var_8d0;
   }

   public final int sub_8aa() {
      return this.var_b69;
   }

   public final Skybox sub_8ce() {
      return this.skybox;
   }

   public final boolean sub_8dc(AEVector3D var1) {
      return this.var_2de.sub_4f(var1.x, var1.y, var1.z);
   }

   public final boolean sub_911(AEVector3D var1) {
      return this.stationaries[1] != null ? this.stationaries[1].sub_162(var1.x, var1.y, var1.z) : false;
   }

   public final boolean sub_92f(AEVector3D var1) {
      return this.stationaries != null && this.stationaries[0] != null && !Status.inEmptyOrbit() ? this.stationaries[0].sub_162(var1.x, var1.y, var1.z) : false;
   }

   public final void sub_966(long var1) {
      if (this.exploding_) {
         GameStatus.graphics.setColor((int)this.explosionR, (int)this.explosionG, (int)this.explosionB);
      } else {
         GameStatus.graphics.setColor(bgR, bgG, bgB);
      }

      GameStatus.graphics.fillRect(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
      this.skybox.sub_116();
      GameStatus.var_8ce.sub_87(this.var_3e4);
      int var3;
      if (this.projectileMesh != null) {
         for(var3 = 0; var3 < this.projectileMesh.length; ++var3) {
            this.projectileMesh[var3].render();
         }
      }

      if (this.var_6e9 != null) {
         for(var3 = 0; var3 < this.var_6e9.length; ++var3) {
            this.var_6e9[var3].render();
         }
      }

      if (this.ships != null) {
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            this.ships[var3].update(var1);
            this.ships[var3].sub_109doSth();
         }
      }

      if (this.asteroids != null) {
         for(var3 = 0; var3 < this.asteroids.length; ++var3) {
            this.asteroids[var3].update(var1);
            this.asteroids[var3].sub_109doSth();
         }
      }

      if (this.stationaries != null) {
         for(var3 = 0; var3 < this.stationaries.length; ++var3) {
            if (this.stationaries[var3] != null) {
               this.stationaries[var3].update(var1);
               this.stationaries[var3].sub_109doSth();
            }
         }
      }

      this.skybox.sub_b9();
   }

   public final void sub_9ca() {
      this.skybox.sub_126();
      if (this.projectileMesh != null) {
         for(int var1 = 0; var1 < this.projectileMesh.length; ++var1) {
            if (this.projectileMesh[var1] instanceof RocketGun) {
               ((RocketGun)this.projectileMesh[var1]).sub_30();
            }
         }
      }

   }

   public final void sub_9ff(long var1) {
      GameStatus.graphics.setColor(bgR, bgG, bgB);
      GameStatus.graphics.fillRect(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
      this.skybox.sub_116();
      GameStatus.var_8ce.sub_87(this.var_3e4);
      int var3;
      if (this.projectileMesh != null) {
         for(var3 = 0; var3 < this.projectileMesh.length; ++var3) {
            this.projectileMesh[var3].render();
         }
      }

      if (this.var_6e9 != null) {
         for(var3 = 0; var3 < this.var_6e9.length; ++var3) {
            this.var_6e9[var3].render();
         }
      }

      if (this.ships != null) {
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            this.ships[var3].sub_109doSth();
         }
      }

      if (this.asteroids != null) {
         for(var3 = 0; var3 < this.asteroids.length; ++var3) {
            this.asteroids[var3].sub_109doSth();
         }
      }

      if (this.stationaries != null) {
         for(var3 = 0; var3 < this.stationaries.length; ++var3) {
            if (this.stationaries[var3] != null) {
               this.stationaries[var3].sub_109doSth();
            }
         }
      }

      this.skybox.sub_b9();
   }

   public final void sub_a3b(long var1) {
      if (Status.inAlienOrbit() && GameStatus.random.nextInt(25) == 0) {
         this.setupExplosionFlashColors(2);
      }

      if (this.exploding_) {
         this.var_f60 = (int)((long)this.var_f60 - var1);
         if (this.var_f60 < 0) {
            this.exploding_ = false;
         }

         float var3 = (float)this.var_f60 / (float)this.var_fa1;
         this.explosionR = AEMath.max((float)bgR, this.explosionR * var3);
         this.explosionG = AEMath.max((float)bgG, this.explosionG * var3);
         this.explosionB = AEMath.max((float)bgB, this.explosionB * var3);
      }

      int var4;
      KIPlayer var5;
      Level var8;
      if (Status.getMission() == null || Status.getMission().isEmpty()) {
         var4 = (int)var1;
         var8 = this;
         this.var_1149 += var4;
         this.var_1172 += var4;
         if (this.var_1172 > 20000) {
            this.var_1172 = 0;
            if (this.ships != null) {
               for(var4 = 0; var4 < var8.ships.length; ++var4) {
                  if ((var5 = var8.ships[var4]).sub_279() && var5.isOutOfScope() && !var5.player.sub_ace()) {
                     var5.sub_2e5();
                     if (var4 < var8.var_1005 + var8.var_1045) {
                        ((PlayerFighter)var5).setPosition(0, 0, 0);
                     } else {
                        ((PlayerBigShip)var5).setPosition(10000, 0, -70000);
                     }
                     break;
                  }
               }
            }
         }

         if (var8.var_1149 > 40000) {
            var8.var_1149 = 0;
            var4 = 0;
            if (var8.var_10e2 > 0 && var8.ships != null) {
               for(int var10 = 0; var10 < var8.ships.length; ++var10) {
                  if (var10 >= var8.ships.length - var8.var_10e2 && !var8.ships[var10].sub_32a() && var8.ships[var10].isOutOfScope() && !var8.ships[var10].player.sub_ace()) {
                     ++var4;
                  }
               }
            }

            if (var8.ships != null) {
               boolean var11 = false;

               for(int var6 = 0; var6 < var8.ships.length; ++var6) {
                  KIPlayer var7 = var8.ships[var6];
                  if ((var8.var_1005 > 0 && var6 < var8.var_1005 || var8.var_1045 > 0 && var6 < var8.var_1005 + var8.var_1045 && var6 > var8.var_1005) && var7.isOutOfScope() && !var7.player.sub_ace()) {
                     var7.sub_2e5();
                     ((PlayerFighter)var7).setPosition(0, 0, 0);
                  }

                  if (var4 >= 2 && var8.var_11c4 < 2 && var8.var_10e2 > 0 && var6 >= var8.ships.length - var8.var_10e2 && var7.isOutOfScope() && !var7.player.sub_ace()) {
                     var11 = true;
                     var7.sub_2e5();
                     ((PlayerFighter)var7).setPosition(var7.targetX, var7.targetY, var8.playerEgo.sub_8c1().z + '鱀');
                  }
               }

               if (var11) {
                  ++var8.var_11c4;
               }
            }
         }
      }

      if (Status.getStation().sub_fc() || Status.inAlienOrbit()) {
         var4 = (int)var1;
         var8 = this;
         this.var_1104 += var4;
         if (this.var_1104 > 40000) {
            this.var_1104 = 0;
            if (this.ships != null) {
               for(var4 = 0; var4 < var8.ships.length; ++var4) {
                  if ((var5 = var8.ships[var4]).race == 9 && var5.isOutOfScope() && !var5.player.sub_ace()) {
                     var5.sub_2e5();
                     PlayerFighter var10000;
                     int var10001;
                     int var10002;
                     int var10003;
                     if (!Status.inAlienOrbit() && Status.getStation().sub_fc()) {
                        var10000 = (PlayerFighter)var5;
                        var10001 = var8.stationaries[3].sub_a9(var8.var_1353).x - 10000 + GameStatus.random.nextInt(20000);
                        var10002 = var8.stationaries[3].sub_a9(var8.var_1353).y - 10000 + GameStatus.random.nextInt(20000);
                        var10003 = var8.stationaries[3].sub_a9(var8.var_1353).z - 10000 + GameStatus.random.nextInt(20000);
                     } else {
                        var10000 = (PlayerFighter)var5;
                        var10001 = var8.playerEgo.sub_8c1().x - 30000 + GameStatus.random.nextInt(60000);
                        var10002 = var8.playerEgo.sub_8c1().y - 30000 + GameStatus.random.nextInt(60000);
                        var10003 = var8.playerEgo.sub_8c1().z + GameStatus.random.nextInt(2) == 0 ? 30000 : -30000;
                     }

                     var10000.setPosition(var10001, var10002, var10003);
                  }
               }
            }
         }
      }

      int var9;
      if (this.projectileMesh != null) {
         for(var9 = 0; var9 < this.projectileMesh.length; ++var9) {
            ((Class_786)this.projectileMesh[var9]).sub_181(var1);
         }
      }

      if (this.var_6e9 != null) {
         for(var9 = 0; var9 < this.var_6e9.length; ++var9) {
            ((Class_786)this.var_6e9[var9]).sub_181(var1);
         }
      }

   }

   public final boolean sub_a83(int var1) {
      return this.var_b00 != null ? this.var_b00.sub_47(var1) : false;
   }

   public final void sub_ac1() {
      this.var_c13 = true;
   }

   public final boolean sub_ade() {
      return this.var_c13;
   }

   public final void sub_b30() {
      this.var_b00 = null;
      this.var_b24 = null;
   }

   public final AbstractMesh[] sub_b85() {
      return this.projectileMesh;
   }

   public final boolean sub_ba1(int var1) {
      return this.var_b24 != null ? this.var_b24.sub_47(var1) : false;
   }

   private void createRadioMessage(boolean var1, int var2) {
      if (this.playerEgo != null && this.playerEgo.var_1653 != null && Status.getMission().isEmpty()) {
         this.var_8d0 = new Class_85e[1];
         var2 = var2 == 2 ? 24 : (var2 == 0 ? 23 : (var2 == 3 ? 21 : 22));
         int var3;
         short var4 = (short)((var3 = var1 ? 250 : 247) + GameStatus.random.nextInt(3));
         this.var_8d0[0] = new Class_85e(var4, var2, 5, 0);
         this.playerEgo.var_1653.sub_96(this.var_8d0);
         this.playerEgo.var_1653.sub_193();
      }

   }

   public final void alarmAllFriends(int var1, boolean var2) {
      if (this.ships != null) {
         for(int var3 = 0; var3 < this.ships.length; ++var3) {
            if (this.ships[var3].race == var1) {
               this.ships[var3].player.setAlwaysEnemy(true);
            }
         }
      }

      if (!this.firstWarning && var2) {
         this.firstWarning = true;
         this.createRadioMessage(true, var1);
      }

   }

   public final void sub_c61(int var1) {
      if (!this.var_1219) {
         this.var_1219 = true;
         this.createRadioMessage(false, var1);
      }

   }

   public final void sub_c8b() {
      AEResourceManager.sub_18f();
      int var1;
      if (this.stationaries != null) {
         for(var1 = 0; var1 < this.stationaries.length; ++var1) {
            if (this.stationaries[var1] != null) {
               this.stationaries[var1].destroy();
               this.stationaries[var1] = null;
            }
         }
      }

      this.stationaries = null;
      if (this.var_43d != null) {
         ((Class_6fcMesh)this.var_43d).destroy();
      }

      this.var_43d = null;
      if (this.var_4fc != null) {
         ((Class_6fcMesh)this.var_4fc).destroy();
      }

      this.var_4fc = null;
      if (this.projectileMesh != null) {
         for(var1 = 0; var1 < this.projectileMesh.length; ++var1) {
            this.projectileMesh[var1].destroy();
         }
      }

      this.projectileMesh = null;
      if (this.var_6e9 != null) {
         for(var1 = 0; var1 < this.var_6e9.length; ++var1) {
            this.var_6e9[var1].destroy();
         }
      }

      this.var_6e9 = null;
      if (this.playerEgo != null) {
         this.playerEgo.sub_1181();
      }

      this.playerEgo = null;
      if (this.ships != null) {
         for(var1 = 0; var1 < this.ships.length; ++var1) {
            this.ships[var1].destroy();
         }
      }

      this.ships = null;
      if (this.var_7df != null) {
         this.var_7df.sub_62();
      }

      this.var_7df = null;
      if (this.var_863 != null) {
         this.var_863.sub_62();
      }

      this.var_863 = null;
      if (this.var_8c5 != null) {
         this.var_8c5.sub_62();
      }

      this.var_8c5 = null;
      this.var_8d0 = null;
      this.var_b00 = null;
      this.var_b24 = null;
      this.var_b87 = 0;
      System.gc();
   }
}
