package GoF2;

import javax.microedition.m3g.Light;

import AE.AEResourceManager;
import AE.AbstractGun;
import AE.AbstractMesh;
import AE.BoundingAAB;
import AE.BoundingSphere;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class Level {
   private int currentMod;
   private BoundingVolume asteroidField;
   private final int[] barFigurePositions;
   private AEVector3D asteroidFieldCenter;
   private Waypoint asteroidsWaypoint;
   private AbstractMesh skybox;
   private Sparks gunSparks;
   private Sparks missilesSparks;
   private AbstractMesh[] egoGuns;
   private AbstractMesh[] enemyGuns;
   private StarSystem starSystem;
   private PlayerEgo ego;
   private KIPlayer[] ships;
   private KIPlayer[] asteroids;
   private KIPlayer[] stationaries;
   private Route missionRoute;
   private Route kamikazePath;
   private Route enemyRoute_;
   private RadioMessage[] radioMessages;
   private int enemiesIntact;
   private int friendsAlive;
   private int asteroidsIntact;
   private int enemiesDestroyed;
   public int capturedCargoCount;
   public int challengerScore;
   public int egoScore;
   public Objective successObjective;
   public Objective failObjective_;
   private int timeLimit;
   private int spaceCreationStep__;
   private boolean spaceCreated_;
   private int mgameIntroCamRotY;
   private boolean stolenFromFriend;
   public static float starR;
   public static float starG;
   public static float starB;
   public static int spaceR;
   public static int spaceG;
   public static int spaceB;
   public static int bgR;
   public static int bgG;
   public static int bgB;
   public static float explosionR;
   public static float explosionG;
   public static float explosionB;
   private int flashIntensity;
   private int lastFlashDuration;
   public static  boolean exploding_;
   private int flashType;
   private int localFightersCnt;
   private int jumperCnt;
   private int bigShipsCnt;
   private int raidersCnt;
   private int alienRespawnTick;
   private int commonRespawnTick;
   private int jumperRespawnTick;
   private Route raidRoute;
   private int raidWavesCounter;
   private boolean friendlyFireAlerted;
   private boolean localsAlarmed;
   public static boolean initStreamOutPosition = false;
   public static boolean comingFromAlienWorld = false;
   public static int lastMissionFreighterHitpoints;
   public static Station programmedStation;
   public static boolean driveJumping = false;
   private AEVector3D tempVec;

   public Level() {
      this(3);
   }
   
   public static int starLight() {
       float max = Math.max(Level.starB, Level.starG);
       max = Math.max(max, Level.starR);
       return  (int)(Level.starR/max*255) << 16
	       | (int)(Level.starG/max*255) << 8
	       | (int)(Level.starB/max*255)
	       ;
   }
   
   public static int skyNormalizedLight() {
       int R = (int) Math.min(255, Level.spaceG*1.5+50);
       int G = (int) Math.min(255, Level.spaceR*1.5+50);
       int B = (int) Math.min(255, Level.spaceB*1.5+50);
       return  (int)(R) << 16
	       | (int)(G) << 8
	       | (int)(B)
	       ;
   }
   
   public static int skyLight() {
       if (Level.exploding_)
	       return  (int)(Level.explosionR) << 16
		       | (int)(Level.explosionG) << 8
		       | (int)(Level.explosionB)
		       ;   
       return  (int)(Level.bgG) << 16
	       | (int)(Level.bgG) << 8
	       | (int)(Level.bgB)
	       ;
   }
   
   public static int maxLight() {
       int maxR = (int) Math.max(Level.starR, Level.spaceR);
       int maxG = (int) Math.max(Level.starG, Level.spaceG);
       int maxB = (int) Math.max(Level.starB, Level.spaceB);
       float max = Math.max(maxB, maxG);
       max = Math.max(maxR, max);
       return  (int)(maxR/max*255) << 16
	       | (int)(maxG/max*255) << 8
	       | (int)(maxB/max*255)
	       ;
   }
   
   
   
   
   public Level(int var1) {
      this.barFigurePositions = new int[]{630, 0, 175, 1570, 0, 340, 1024, 0, 600, 1260, 0, 650, 880, 0, 950};
      Level.exploding_ = false;
      this.tempVec = new AEVector3D();
      this.currentMod = var1;
      this.spaceCreationStep__ = 0;
      this.spaceCreated_ = false;
   }

   public static void setInitStreamOut() {
      initStreamOutPosition = true;
   }

   public final boolean createSpace() {
      if (this.spaceCreationStep__ == 0 || this.spaceCreated_) {
         this.missionRoute = null;
         this.enemyRoute_ = null;
         this.kamikazePath = null;
         this.failObjective_ = null;
         this.successObjective = null;
         this.gunSparks = new Sparks(AEResourceManager.getTextureResource(1), 33, 225, 63, 255, 10, 700, 100, 500);
         this.missilesSparks = new Sparks(AEResourceManager.getTextureResource(1), 33, 225, 63, 255, 10, 700, 100, 500);
         Level var1 = this;
         if (this.skybox == null) {
            this.skybox = AEResourceManager.getGeometryResource(9991);
         }

         int var4;
         int var6;
         if (this.currentMod != 23 && this.currentMod != 4) {
            boolean var2 = comingFromAlienWorld || Status.getStation().isAttackedByAliens() || Status.inAlienOrbit();
            if (Status.getCurrentCampaignMission() > 42) {
               var2 = false;
            }

            this.stationaries = new KIPlayer[4];
            if (Status.inEmptyOrbit()) {
               this.stationaries[0] = null;
            } else {
               this.stationaries[0] = new PlayerStation(Status.getStation());
            }

            AEVector3D var3 = new AEVector3D(this.tempVec);
            this.mgameIntroCamRotY = 0;
            GlobalStatus.random.setSeed(Status.getStation() != null ? (long)(Status.getStation().getId() << 1) : -1L);
            var4 = 1;

            while(true) {
               if (var4 >= 3) {
                  GlobalStatus.random.setSeed(System.currentTimeMillis());
                  if (Status.inAlienOrbit()) {
                     var4 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GlobalStatus.random.nextInt(50000));
                     int var16 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GlobalStatus.random.nextInt(50000));
                     var6 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * ('썐' + GlobalStatus.random.nextInt(50000));
                     var1.stationaries[2].setPosition(var4, var16, var6);
                     var1.stationaries[2].mainMesh_.setRotation(-4096 + GlobalStatus.random.nextInt(8192), -4096 + GlobalStatus.random.nextInt(8192), -4096 + GlobalStatus.random.nextInt(8192));
                  }

                  if (!Status.gameWon()) {
                     var1.stationaries[3] = new PlayerWormHole(6805, AEResourceManager.getGeometryResource(6805), -40000 + GlobalStatus.random.nextInt(80000), -20000 + GlobalStatus.random.nextInt(40000), '鱀' + GlobalStatus.random.nextInt(40000), var2);
                     var1.stationaries[3].setLevel(var1);
                  }

                  var1.mgameIntroCamRotY += 2048;
                  if (!initStreamOutPosition) {
                     var1.mgameIntroCamRotY = 0;
                  }
                  break;
               }

               if (var4 != 1 || Status.getSystem() != null && Status.getSystem().currentOrbitHasJumpgate()) {
                  if (Status.getStation() != null) {
                     if (var4 == 0) {
                        var1.mgameIntroCamRotY = Status.getStation().getName().length() + Status.getStation().getTecLevel() * 3;
                        var1.mgameIntroCamRotY = var1.mgameIntroCamRotY % 16 << 8;
                     } else {
                        var1.mgameIntroCamRotY += GlobalStatus.random.nextInt(2) == 0 ? -250 - GlobalStatus.random.nextInt(500) : 250 + GlobalStatus.random.nextInt(500);
                     }

                     var1.tempVec.z = var4 == 1 ? 90000 : 120000;
                     AEVector3D var10000 = var1.tempVec;
                     var10000.z += var1.mgameIntroCamRotY * 3;
                     Matrix var5;
                     (var5 = new Matrix()).setEulerY(var1.mgameIntroCamRotY);
                     (var3 = var5.transformVectorNoScale(var1.tempVec, var3)).y = 0;
                  }

                  var1.stationaries[var4] = new PlayerJumpgate(15, AEResourceManager.getGeometryResource(15), var3.x, var3.y, var3.z, var4 != 2);
               } else {
                  var1.stationaries[var4] = null;
               }

               ++var4;
            }
         }

         var1.starSystem = new StarSystem();
         SolarSystem var10;
         if ((var10 = Status.getSystem()) == null) {
            starR = 10.0F;
            starG = 136.0F;
            starB = 10.0F;
         } else {
            starR = (float)var10.starR;
            starG = (float)var10.starG;
            starB = (float)var10.starB;
         }

         spaceR = (int)(starR / 3.0F);
         spaceG = (int)(starG / 3.0F);
         spaceB = (int)(starB / 3.0F);
         if (this.currentMod == 3) {
            var1 = this;

            try {
               Ship var11 = Status.getShip();
               Item[] var13 = Status.getShip().getEquipment();
               int[] var15;
               (var15 = new int[3])[0] = var11.getUsedSlots(0);
               var15[1] = var11.getUsedSlots(1);
               var15[2] = var11.getUsedSlots(2);
               Player var17;
               (var17 = new Player(1200.0F, Status.getShip().getBaseArmour(), var15[0], var15[1], var15[2])).setMaxShieldHP(Status.getShip().getShield());
               var17.setMaxArmorHP(Status.getShip().getAdditionalArmour());
               var1.ego = new PlayerEgo(var17);
               var1.ego.setShip(Status.getShip().getIndex(), Status.getShip().getRace());
               var1.ego.setLevel(var1);
               var1.ego.shipGrandGroup_.setRotation(0, var1.mgameIntroCamRotY, 0);
               int[] var20 = new int[]{0, 0, 0};
               Explosion var18 = new Explosion(var20.length / 3);
               var1.ego.setExplosion(var18);
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
                        if (var13[var6].isWeapon()) {
                           int var22 = var13[var6].getType() == 1 ? var13[var6].getAmount() : -1;
                           (var7 = var1.createGun(var13[var6].getIndex(), var6, var13[var6].getSort(), var22, var13[var6].getAttribute(9), var13[var6].getAttribute(11), var13[var6].getAttribute(12), var13[var6].getAttribute(13))).index = var13[var6].getIndex();
                           var7.subType = var13[var6].getSort();
                           var7.setMagnitude(var13[var6].getAttribute(14));
                           int var10001;
                           int var10004;
                           Gun[] var23;
                           switch(var13[var6].getType()) {
                           case 0:
                              var7.setOffset(var15[0] - 1, var11.getUsedSlots(0));
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
                     var1.ego.setGuns(var21[var6], var6);
                  }
               }
            } catch (Exception var8) {
               var8.printStackTrace();
            }

            if (initStreamOutPosition && this.ego.shipGrandGroup_ != null) {
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
                  (var19 = new AEVector3D(this.starSystem.getPlanets()[var14 + 1].getLocalPos())).scale(16384);
                  this.ego.setPosition_(var19);
                  var19.x = -var19.x;
                  var19.y = -var19.y;
                  var19.z = -var19.z;
                  var19.normalize();
                  this.ego.shipGrandGroup_.getToParentTransform().setOrientation(var19);
               } else {
                  this.ego.setPosition_(this.stationaries[2].mainMesh_.getPosX(), this.stationaries[2].mainMesh_.getPosY(), this.stationaries[2].mainMesh_.getPosZ());
               }
            } else {
               this.ego.setPosition_(10, 10, 10000);
            }

            if (Status.getCurrentCampaignMission() == 1) {
               this.ego.setPosition_(0, 0, -110000);
            }
         }
      }

      if (this.spaceCreationStep__ != 1 && !this.spaceCreated_) {
         if (this.spaceCreated_) {
            this.spaceCreationStep__ = 0;
            return true;
         }
      } else {
         if (this.currentMod != 4) {
            this.createAsteroids();
         }

         if (Status.getMission() == null) {
            Status.setMission(Mission.emptyMission_);
         }

         if ((this.currentMod != 3 || Status.getMission().isCampaignMission()) && (this.currentMod != 3 || !Status.gameWon())) {
            if (this.currentMod != 3) {
               this.createScene();
               this.currentMod = 3;
            } else if (!Status.getMission().isEmpty() && Status.getMission().isCampaignMission()) {
               this.createCampaignMission();
            }
         } else {
            this.createMission();
         }

         this.createWingmen();
         this.assignGuns();
         this.connectPlayers();
         if (this.ego != null) {
            this.ego.setRoute(this.missionRoute);
         }

         this.enemiesIntact = this.ships != null ? this.ships.length : 0;
         this.asteroidsIntact = 0;
         this.asteroidsIntact = this.asteroids != null ? this.asteroids.length : 0;
         this.raidWavesCounter = 0;
         this.friendlyFireAlerted = false;
         this.localsAlarmed = false;
         this.capturedCargoCount = 0;
         this.enemiesDestroyed = 0;
         this.stolenFromFriend = false;
         this.spaceCreated_ = true;
      }

      ++this.spaceCreationStep__;
      return this.spaceCreated_;
   }

   private void createAsteroids() {
      int var1 = 154;
      int var2 = 0;
      boolean var3 = false;
      this.asteroids = new KIPlayer[50];
      int[] var4 = Galaxy.getAsteroidProbabilities(Status.getStation());
      GlobalStatus.random.setSeed((long)Status.getStation().getId());
      int var5 = -50000 + GlobalStatus.random.nextInt(100000);
      int var6 = -50000 + GlobalStatus.random.nextInt(100000);
      int var7 = 10000 + GlobalStatus.random.nextInt(100000);
      GlobalStatus.random.setSeed(System.currentTimeMillis());
      this.asteroidFieldCenter = new AEVector3D(var5, var6, var7);
      this.asteroidsWaypoint = new Waypoint(var5, var6, var7, (Route)null);
      this.asteroidField = new BoundingSphere(var5, var6, var7, 0, 0, 0, 50000);

      for(int var8 = 0; var8 < this.asteroids.length; ++var8) {
         if (Status.inAlienOrbit()) {
            var1 = 164;
         } else if (Status.inEmptyOrbit()) {
            var1 = 154;
         } else {
            var3 = false;

            while(!var3) {
               if (GlobalStatus.random.nextInt(100) < var4[var2 + 1]) {
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
         Waypoint var9 = null;
         if (var3) {
            var9 = new Waypoint(0, 0, 20000, (Route)null);
         } else {
            var9 = new Waypoint(var5, var6, var7, (Route)null);
         }

         KIPlayer[] var10000 = this.asteroids;
         int var12 = Status.inAlienOrbit() ? 6804 : 6769;
         boolean var13 = false;
         boolean var14 = false;
         boolean var15 = false;
         char var19 = '\uea60';
         int var17 = var9.x - 30000 + GlobalStatus.random.nextInt(var19);
         int var18 = var9.y - 30000 + GlobalStatus.random.nextInt(var19);
         int var20 = var9.z - 30000 + GlobalStatus.random.nextInt(var19);
         PlayerAsteroid var21;
         (var21 = new PlayerAsteroid(var12, AEResourceManager.getGeometryResource(var12), var1, var3, var17, var18, var20)).setLevel(this);
         var21.setAsteroidCenter(this.asteroidFieldCenter);
         if (this.currentMod == 23) {
            var21.mainMesh_.setRenderLayer(1);
         } else {
            var21.mainMesh_.setRenderLayer(2);
         }

         Explosion var16 = new Explosion(1);
         var21.setExplosion(var16);
         var10000[var8] = var21;
      }

   }

   private Gun createGun(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      Gun var9 = null;
      Object var10 = null;
      var10 = null;
      Sparks var11 = this.gunSparks;
      AbstractMesh var14;
      switch(var3) {
      case 0:
      case 1:
      case 3:
         if (Globals.TYPE_WEAPONS[var1] >= 0) {
            int var15 = var3 == 0 ? 800 : 400;
            var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, var15), new AEVector3D());
            var14 = AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[var1]);
            if (var3 == 3) {
               var3 = 300 + 70 * (var1 - 28);
               var14.setScale(var3, var3, var3);
               var14.setAnimationRangeInTime(var1 - 28 + 10, var1 - 28 + 10);
               var14.setAnimationMode((byte)1);
               (var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 200), new AEVector3D())).setSpread(20);
            }

            var10 = new ObjectGun(var9, var14);
         } else {
            var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 400), new AEVector3D());
            var10 = new LaserGun(var9, var1, this);
         }
         break;
      case 2:
         var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 200), new AEVector3D());
         var10 = new ObjectGun(var9, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[var1]));
         break;
      case 4:
      case 5:
         var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(), new AEVector3D());
         (var14 = AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[var1])).setRenderLayer(2);
         var10 = new RocketGun(var9, var14, var3 == 5);
         var11 = this.missilesSparks;
         break;
      case 6:
      case 7:
         var9 = new Gun(var2, var5, 1, var4, var7, var6, (float)var8, new AEVector3D(0, -200, 400), new AEVector3D());
         AbstractMesh var12 = null;
         int var13;
         if (var3 == 7) {
            var12 = AEResourceManager.getGeometryResource(18);
            var13 = var1 - 44 + 1 << 9;
            var12.setScale(var13, var13, var13);
         } else {
            var12 = AEResourceManager.getGeometryResource(16);
            var13 = var1 - 41 + 1 << 9;
            var12.setScale(var13, var13, var13);
         }

         var12.setRenderLayer(2);
         var10 = new RocketGun(var9, var12, false);
         var11 = this.missilesSparks;
         break;
      case 8:
         var9 = new Gun(var2, var5, 15, var4, var7, var6, (float)var8, new AEVector3D(0, 0, 0), new AEVector3D());
         var10 = new ObjectGun(var9, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[var1]));
      }

      var9.setLevel(this);
      var9.setSparks(var11);
      if (this.egoGuns == null) {
         this.egoGuns = new AbstractMesh[]{(AbstractMesh)var10};
      } else {
         AbstractMesh[] var16 = new AbstractMesh[this.egoGuns.length + 1];
         System.arraycopy(this.egoGuns, 0, var16, 0, this.egoGuns.length);
         var16[var16.length - 1] = (AbstractMesh)var10;
         this.egoGuns = var16;
      }

      return var9;
   }

   private void createWingmen() {
      if (Status.getWingmenNames() != null && Status.wingmenTimeRemaining <= 0) {
         Status.setWingmenNames((String[])null);
         Status.wingmenTimeRemaining = 0;
         Status.wingmanFace = null;
      } else {
         if (Status.getWingmenNames() != null && this.ego != null) {
            KIPlayer[] var1 = new KIPlayer[Status.getWingmenNames().length];

            for(int var2 = 0; var2 < var1.length; ++var2) {
               GlobalStatus.random.setSeed((long)(Status.getWingmenNames()[var2].length() * 5));
               int var3 = Globals.getRandomEnemyFighter(Status.wingmanRace);
               GlobalStatus.random.setSeed(System.currentTimeMillis());
               var1[var2] = this.createShip(5, 0, var3, (Waypoint)null);
               if (initStreamOutPosition && this.ego.shipGrandGroup_ != null) {
                  ((PlayerFighter)var1[var2]).setPosition(this.stationaries[2].mainMesh_.getPosX() - 3000, this.stationaries[2].mainMesh_.getPosY() + 1000, this.stationaries[2].mainMesh_.getPosZ() + 5000);
               } else {
                  ((PlayerFighter)var1[var2]).setPosition(this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosZ() + 2000);
               }

               var1[var2].setWingman(true, var2);
               var1[var2].player.setAlwaysFriend(true);
               var1[var2].player.setHitPoints(600);
               var1[var2].name = Status.getWingmenNames()[var2];
               var1[var2].race = Status.wingmanRace;
               if (Status.getMission().getType() == 12) {
                  var1[var2].armed = false;
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

   private void createMission() {
      Mission var1;
      if ((var1 = Status.getMission()) != null) {
         int var2;
         int var3;
         if (Status.inAlienOrbit()) {
            var2 = 1 + GlobalStatus.random.nextInt(3);
            this.ships = new KIPlayer[var2];

            for(var3 = 0; var3 < var2; ++var3) {
               this.ships[var3] = this.createShip(9, 0, Globals.getRandomEnemyFighter(9), (Waypoint)null);
               this.ships[var3].setPosition(-40000 + GlobalStatus.random.nextInt(80000), -40000 + GlobalStatus.random.nextInt(80000), -40000 + GlobalStatus.random.nextInt(80000));
               this.ships[var3].player.setAlwaysEnemy(true);
            }

         } else {
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            Route var12;
            int var13;
            if (var1.isEmpty()) {
               this.currentMod = 0;
               this.createScene();
               this.currentMod = 3;
               boolean var10 = Status.getSystem().getId() == 15 && Status.getCurrentCampaignMission() < 16;
               boolean var15 = Status.getStation().getId() == 78;
               var13 = GlobalStatus.random.nextInt(100);
               var5 = 0;
               Mission var17;
               if ((var17 = Status.getFreelanceMission()) != null && (var17.getType() == 0 || var17.getType() == 11)) {
                  var5 = (int)((float)var17.getDifficulty() / 10.0F * 5.0F);
               }

               var7 = Status.getSystem().getSafety();
               boolean var11 = !var10 && var13 < (var7 == 0 ? 80 : (var7 == 1 ? 60 : (var7 == 2 ? 35 : 10)));
               this.raidRoute = new Route(new int[]{-50000 + GlobalStatus.random.nextInt(100000), 0, '썐' + GlobalStatus.random.nextInt(50000)});
               var13 = Status.getSystem().getRace();
               var8 = GlobalStatus.random.nextInt(100) < 75 ? 8 : Standing.enemyRace(var13);
               this.raidersCnt = var11 ? 0 + GlobalStatus.random.nextInt(4) : 0;
               this.jumperCnt = var15 ? 0 : 0 + GlobalStatus.random.nextInt(2);
               this.bigShipsCnt = !var15 && !var10 ? 0 + GlobalStatus.random.nextInt(5) : 0;
               this.localFightersCnt = (var15 ? 0 : GlobalStatus.random.nextInt(2)) + (var10 ? 0 : var7) + this.bigShipsCnt / 4;
               if (Status.getStation().getId() == 10 || this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt + var5 == 0) {
                  this.localFightersCnt = 4;
               }

               this.ships = new KIPlayer[this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt + var5];
               var12 = new Route(new int[]{0, 0, 10000});

               for(var2 = 0; var2 < this.localFightersCnt; ++var2) {
                  this.ships[var2] = this.createShip(var13, 0, Globals.getRandomEnemyFighter(var13), var12.getDockingTarget_());
               }

               AbstractMesh var14 = null;
               if (this.jumperCnt > 0) {
                  (var14 = AEResourceManager.getGeometryResource(6783)).setRenderLayer(2);
               }

               for(var6 = this.localFightersCnt; var6 < this.localFightersCnt + this.jumperCnt; ++var6) {
                  this.ships[var6] = this.createShip(var13, 0, Globals.getRandomEnemyFighter(var8), (Waypoint)null);
                  this.ships[var6].setDead();
                  Route var19 = new Route(new int[]{-200000 + GlobalStatus.random.nextInt(400000), -100000 + GlobalStatus.random.nextInt(200000), '썐' + GlobalStatus.random.nextInt(100000)});
                  this.ships[var6].setRoute(var19);
                  this.ships[var6].setJumper(true);
                  this.ships[var6].setJumpMesh(var14);
               }

               boolean var18 = var13 == 0 && GlobalStatus.random.nextInt(100) < 30;

               for(var7 = this.localFightersCnt + this.jumperCnt; var7 < this.localFightersCnt + this.jumperCnt + this.bigShipsCnt; ++var7) {
                  if (var18 && var7 == this.localFightersCnt + this.jumperCnt) {
                     this.ships[var7] = this.createShip(var13, 1, 14, (Waypoint)null);
                     ((PlayerFixedObject)this.ships[var7]).setMoving(false);
                     this.ships[var7].setPosition(-40000 + GlobalStatus.random.nextInt(80000), -5000 + GlobalStatus.random.nextInt(10000), '鱀' + GlobalStatus.random.nextInt(80000));
                  } else {
                     this.ships[var7] = this.createShip(var13, 1, var13 == 1 ? 13 : 15, (Waypoint)null);
                     ((PlayerFixedObject)this.ships[var7]).setMoving(true);
                     this.ships[var7].setPosition((-80000 + GlobalStatus.random.nextInt(60000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1), -20000 + GlobalStatus.random.nextInt(40000), -80000 + GlobalStatus.random.nextInt(160000));
                  }
               }

               if (var11) {
                  var7 = Globals.getRandomEnemyFighter(var8);

                  for(var9 = this.localFightersCnt + this.jumperCnt + this.bigShipsCnt; var9 < this.ships.length - var5; ++var9) {
                     this.ships[var9] = this.createShip(var8, 0, var7, this.raidRoute.getDockingTarget_());
                  }
               }

               for(var7 = this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt; var7 < this.ships.length; ++var7) {
                  this.ships[var7] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), (Waypoint)null);
                  this.tempVec = this.ego.getPosition();
                  this.ships[var7].setPosition(-30000 + this.tempVec.x + GlobalStatus.random.nextInt(60000), -30000 + this.tempVec.y + GlobalStatus.random.nextInt(60000), -30000 + this.tempVec.z + GlobalStatus.random.nextInt(60000));
               }

            } else {
               var2 = Status.getSystem().getRace();
               var3 = GlobalStatus.random.nextInt(100) < 75 ? 8 : Standing.enemyRace(var2);
               boolean var4 = GlobalStatus.random.nextInt(2) == 0;
               switch(var1.getType()) {
               case 1:
                  this.enemyRoute_ = new Route(new int[]{-50000 + GlobalStatus.random.nextInt(100000), 0, var4 ? -50000 : '썐', -50000 + GlobalStatus.random.nextInt(100000), 0, var4 ? -75000 : 75000, -50000 + GlobalStatus.random.nextInt(100000), 0, var4 ? -100000 : 100000});
                  var9 = 3 + (int)(5.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var13 = 2 + GlobalStatus.random.nextInt(6);
                  this.ships = new KIPlayer[var9 + var13];

                  for(var13 = 0; var13 < var9; ++var13) {
                     this.ships[var13] = this.createShip(var3, 0, Globals.getRandomEnemyFighter(var3), (Waypoint)null);
                     this.ships[var13].player.setAlwaysEnemy(true);
                  }

                  for(var13 = var9; var13 < this.ships.length; ++var13) {
                     this.ships[var13] = this.createShip(var2, 0, Globals.getRandomEnemyFighter(var2), this.enemyRoute_.getWaypoint(GlobalStatus.random.nextInt(this.enemyRoute_.length())));
                     this.ships[var13].player.setAlwaysFriend(true);
                  }

                  this.successObjective = new Objective(7, var9, this);
                  return;
               case 2:
                  AEVector3D var16 = new AEVector3D();
                  this.enemyRoute_ = new Route(new int[]{var16.x + (var4 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(20000)), var16.y, var16.z + (var4 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(20000)), var16.x, var16.y, var16.z});
                  var7 = var1.getCommodityAmount_();
                  var9 = 2 + (int)(4.0F * ((float)var1.getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var9 + var7];

                  for(var13 = 0; var13 < var9; ++var13) {
                     this.ships[var13] = this.createShip(var3, 0, Globals.getRandomEnemyFighter(var3), (Waypoint)null);
                     ((PlayerFighter)this.ships[var13]).setPosition(this.enemyRoute_.getWaypoint(0).x + var13 * 2000, this.enemyRoute_.getWaypoint(0).y + var13 * 2000, this.enemyRoute_.getWaypoint(0).z + var13 * 2000);
                     this.ships[var13].player.setAlwaysEnemy(true);
                     this.ships[var13].setRoute(this.enemyRoute_.clone());
                     this.ships[var13].getRoute().reachWaypoint_(0);
                  }

                  var13 = 0;

                  for(var8 = var9; var8 < this.ships.length; ++var8) {
                     this.ships[var8] = this.createShip(var2, 0, Globals.getRandomEnemyFighter(var2), (Waypoint)null);
                     this.ships[var8].player.setAlwaysFriend(true);
                     var3 = this.asteroids.length / 2 + var13++;
                     var16.set(this.asteroids[var3].getPosition(var16));
                     this.ships[var8].setPosition(var16.x, var16.y + 2000, var16.z);
                     this.ships[var8].hasCargo = false;
                     this.ships[var8].cargo = null;
                     this.ships[var8].setSpeed(0);
                     this.ships[var8].player.setHitPoints(this.ships[var8].player.getMaxHitpoints() * 3);
                  }

                  this.successObjective = new Objective(18, 0, var9, this);
                  this.failObjective_ = new Objective(18, var9, var9 + var7, this);
                  return;
               case 3:
               case 5:
                  this.missionRoute = new Route(new int[]{('鱀' + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1), 0, ('鱀' + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1)});
                  this.ships = new KIPlayer[var1.getCommodityAmount_()];

                  for(var6 = 0; var6 < this.ships.length; ++var6) {
                     this.ships[var6] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(0));
                     this.ships[var6].setToSleep();
                  }

                  ((PlayerFighter)this.ships[this.ships.length - 1]).setMissionCrate(true);
                  ((PlayerFighter)this.ships[this.ships.length - 1]).name = GlobalStatus.gameText.getText(833);
                  this.successObjective = new Objective(11, this.ships.length - 1, this);
                  this.failObjective_ = new Objective(12, this.ships.length - 1, this);
                  return;
               case 4:
                  if (GlobalStatus.random.nextInt(2) == 0) {
                     this.missionRoute = new Route(new int[]{this.asteroidFieldCenter.x, this.asteroidFieldCenter.y, this.asteroidFieldCenter.z});
                  } else {
                     this.missionRoute = createRoute(2 + GlobalStatus.random.nextInt(2));
                  }

                  var8 = 2 + (int)(5.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var8];

                  for(var3 = 0; var3 < this.ships.length; ++var3) {
                     this.ships[var3] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
                     this.ships[var3].setToSleep();
                  }

                  this.successObjective = new Objective(18, 0, var8, this);
                  return;
               case 6:
                  this.missionRoute = new Route(new int[]{('\uea60' + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1), 0, ('\uea60' + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1)});
                  this.ships = new KIPlayer[1];
                  this.ships[0] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(0));
                  this.ships[0].setToSleep();
                  var7 = Status.getMission().getDifficulty() * AEMath.min(Status.getLevel(), 20);
                  this.ships[0].player.setMaxHP(var7 + 300);
                  this.successObjective = new Objective(1, 0, this);
                  return;
               case 7:
                  var12 = new Route(new int[]{-20000 + GlobalStatus.random.nextInt(40000), 0, 20000 + GlobalStatus.random.nextInt(40000)});
                  var2 = (int)(2.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var6 = 15 + (int)(35.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var6 + var2];

                  for(var7 = 0; var7 < this.ships.length - var2; ++var7) {
                     this.ships[var7] = this.createJunk(var12.getWaypoint(0), 9996);
                     this.ships[var7].player.setAlwaysEnemy(true);
                  }

                  for(var7 = this.ships.length - var2; var7 < this.ships.length; ++var7) {
                     this.ships[var7] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), (Waypoint)null);
                  }

                  this.successObjective = new Objective(7, this.ships.length - var2, this);
                  this.timeLimit = 121000;
                  return;
               case 9:
                  this.enemyRoute_ = new Route(new int[]{10000, 0, 100000, 10000, 0, 150000, 10000, 0, 200000});
                  var2 = 2 + (int)(6.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  this.ships = new KIPlayer[var2 + 5];
                  var3 = Standing.enemyRace(var1.getClientRace());
                  var9 = var1.getClientRace();

                  for(var13 = 0; var13 < this.ships.length; ++var13) {
                     if (var13 < var2) {
                        this.ships[var13] = this.createShip(var3, 0, Globals.getRandomEnemyFighter(var3), this.enemyRoute_.getWaypoint(GlobalStatus.random.nextInt(this.enemyRoute_.length())));
                        this.ships[var13].setToSleep();
                        this.ships[var13].player.setAlwaysEnemy(true);
                     } else {
                        this.ships[var13] = this.createShip(var9, 1, var9 == 1 ? 13 : 15, (Waypoint)null);
                        this.ships[var13].player.setMaxHP(100 + (AEMath.min(Status.getLevel(), 20) << 1) + (Status.getCurrentCampaignMission() << 1));
                        ((PlayerFixedObject)this.ships[var13]).setMoving(true);
                        this.ships[var13].player.setAlwaysFriend(true);
                        this.ships[var13].hasCargo = false;
                        this.ships[var13].cargo = null;
                     }
                  }

                  ((PlayerFixedObject)this.ships[var2]).setPosition(-2500, -300, 27000);
                  ((PlayerFixedObject)this.ships[var2 + 1]).setPosition(6500, 3000, 24000);
                  ((PlayerFixedObject)this.ships[var2 + 2]).setPosition(-4000, -2000, 19000);
                  ((PlayerFixedObject)this.ships[var2 + 3]).setPosition(9000, -6000, 17000);
                  ((PlayerFixedObject)this.ships[var2 + 4]).setPosition(3000, 7000, 15000);
                  this.successObjective = new Objective(18, 0, var2, this);
                  this.failObjective_ = new Objective(18, var2, var2 + 5, this);
                  return;
               case 10:
                  this.missionRoute = new Route(new int[]{-2500 + GlobalStatus.random.nextInt(5000), -2500 + GlobalStatus.random.nextInt(5000), 80000 + GlobalStatus.random.nextInt(30000), -2500 + GlobalStatus.random.nextInt(5000), -2500 + GlobalStatus.random.nextInt(5000), 120000 + GlobalStatus.random.nextInt(30000)});
                  var2 = 2 + GlobalStatus.random.nextInt(2);
                  var13 = 2 + (int)(2.0F * ((float)Status.getMission().getDifficulty() / 10.0F));
                  var3 = Standing.enemyRace(var1.getClientRace());
                  this.ships = new KIPlayer[var2 + var13];

                  for(var9 = 0; var9 < var2; ++var9) {
                     this.ships[var9] = this.createShip(var3, 1, var3 == 1 ? 13 : 15, this.missionRoute.getWaypoint(1));
                     this.ships[var9].setToSleep();
                     this.ships[var9].player.setAlwaysEnemy(true);
                     ((PlayerFixedObject)((PlayerFixedObject)this.ships[var9])).setMoving(false);
                     ((PlayerFixedObject)((PlayerFixedObject)this.ships[var9])).setPosition(this.missionRoute.getWaypoint(1).x + -10000 + GlobalStatus.random.nextInt(20000), this.missionRoute.getWaypoint(1).y + -10000 + GlobalStatus.random.nextInt(20000), this.missionRoute.getWaypoint(1).z + -10000 + GlobalStatus.random.nextInt(20000));
                  }

                  for(var9 = var2; var9 < this.ships.length; ++var9) {
                     this.ships[var9] = this.createShip(var3, 0, Globals.getRandomEnemyFighter(var3), this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
                     this.ships[var9].setToSleep();
                     this.ships[var9].player.setAlwaysEnemy(true);
                  }

                  this.successObjective = new Objective(7, var2, this);
               case 8:
               default:
                  return;
               case 11:
                  return;
               case 12:
                  this.missionRoute = createRoute(3 + GlobalStatus.random.nextInt(2));
                  if ((var5 = 3 + (int)(4.0F * ((float)var1.getDifficulty() / 10.0F))) % 2 == 0) {
                     ++var5;
                  }

                  if (var1.isCampaignMission()) {
                     var5 = 7;
                     this.missionRoute = new Route(new int[]{80000, -20000, 80000, 70000, 0, -80000, -100000, 10000, -80000, -80000, 20000, 90000});
                  }

                  this.ships = new KIPlayer[var5 + 1];
                  this.ships[0] = this.createShip(var1.getAgent().getRace(), 0, Globals.getRandomEnemyFighter(var1.getAgent().getRace()), (Waypoint)null);
                  ((PlayerFighter)this.ships[0]).setPosition(this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosZ() + 1000);
                  ((PlayerFighter)this.ships[0]).loadShips(3);
                  ((PlayerFighter)this.ships[0]).setRotate(3);
                  this.ships[0].player.setHitPoints(9999999);
                  this.ships[0].setRoute(this.missionRoute.clone());
                  this.ships[0].player.setAlwaysFriend(true);
                  this.ships[0].name = var1.getAgent().getFullName();
                  this.ships[0].cargo = null;

                  for(var6 = 1; var6 < this.ships.length; ++var6) {
                     this.ships[var6] = this.createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
                     this.ships[var6].setToSleep();
                  }

                  this.successObjective = new Objective(20, 1, var5 + 1, this);
                  this.failObjective_ = new Objective(21, 1, var5 + 1, this);
               }
            }
         }
      }
   }

   private void createCampaignMission() {
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
            this.ships[var2] = this.createShip(8, 0, 10, (Waypoint)null);
            this.ships[var2].setToSleep();
            this.ships[var2].player.setAlwaysEnemy(true);
            var1 = this.asteroids[this.asteroids.length - 1 - var2].getPosition(var1);
            this.ships[var2].setPosition(var1.x, var1.y, var1.z + 2000);
            this.ships[var2].hasCargo = false;
            this.ships[var2].cargo = null;
            this.ships[var2].player.setHitPoints(150);
            if (var2 < 3) {
               ((PlayerFighter)this.ships[var2]).setExhaustVisible(false);
            }
         }

         this.ships[2].setPosition(0, 0, -40000);
         this.enemyRoute_ = new Route(new int[]{0, 0, -30000, 0, 0, 0});
         this.ships[2].setRoute(this.enemyRoute_);
         this.ego.player.setHitPoints(9999999);
         break;
      case 1:
         this.ships = new KIPlayer[1];
         this.ships[0] = this.createShip(3, 0, 1, (Waypoint)null);
         this.ships[0].setPosition(300, 50, -8000);
         this.enemyRoute_ = new Route(new int[]{0, 0, -5000, 0, 0, 0});
         this.ships[0].setRoute(this.enemyRoute_);
         this.ships[0].setSpeed(0);
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
         this.ships[0] = this.createShip(8, 0, 1, (Waypoint)null);
         this.ships[0].setInitActive(false);
         this.ships[0].player.setAlwaysEnemy(true);
         this.ships[0].setPosition(0, 0, -200000);
         this.ships[0].setToSleep();
         break;
      case 7:
         this.missionRoute = new Route(new int[]{20000, 7000, 120000});
         this.ships = new KIPlayer[4];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(8, 0, 2, this.missionRoute.getDockingTarget_());
            this.ships[var5].setToSleep();
         }

         this.ships[3] = this.createShip(3, 0, 1, (Waypoint)null);
         this.ships[3].player.setAlwaysFriend(true);
         this.ships[3].setPosition(this.ego.shipGrandGroup_.getPosX() + 700, this.ego.shipGrandGroup_.getPosY() + 50, this.ego.shipGrandGroup_.getPosZ() + 1000);
         this.ships[3].setRoute(this.missionRoute.clone());
         this.ships[3].player.setHitPoints(9999999);
         this.ships[3].name = GlobalStatus.gameText.getText(821);
         ((PlayerFighter)this.ships[3]).setBoostProb(0);
         this.successObjective = new Objective(18, 0, 3, this);
         break;
      case 14:
         this.enemyRoute_ = new Route(new int[]{0, 0, 50000});
         this.ships = new KIPlayer[7];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(8, 0, 0, this.enemyRoute_.getDockingTarget_());
         }

         for(var5 = 3; var5 < 5; ++var5) {
            this.ships[var5] = this.createShip(0, 0, 1, this.enemyRoute_.getDockingTarget_());
         }

         for(var5 = 5; var5 < 7; ++var5) {
            this.ships[var5] = this.createShip(0, 1, 14, this.enemyRoute_.getDockingTarget_());
         }

         this.successObjective = new Objective(22, 0, this);
         break;
      case 16:
         this.enemyRoute_ = new Route(new int[]{0, 0, 130000});
         this.ships = new KIPlayer[7];

         for(var5 = 0; var5 < 4; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
         }

         for(var5 = 4; var5 < 7; ++var5) {
            this.ships[var5] = this.createShip(0, 0, 1, (Waypoint)null);
            this.ships[var5].player.setAlwaysFriend(true);
            this.ships[var5].player.setHitPoints(600);
            ((PlayerFighter)this.ships[var5]).setPosition(this.ego.shipGrandGroup_.getPosX() + -2000 + GlobalStatus.random.nextInt(4000), this.ego.shipGrandGroup_.getPosY() + -1700 + GlobalStatus.random.nextInt(3400), this.ego.shipGrandGroup_.getPosZ() + 2000 + -2000 + GlobalStatus.random.nextInt(4000));
         }

         this.successObjective = new Objective(18, 0, 4, this);
         break;
      case 21:
         this.enemyRoute_ = new Route(new int[]{40000, -40000, 120000});
         this.missionRoute = this.enemyRoute_.clone();
         this.enemyRoute_.setLoop(true);
         this.ships = new KIPlayer[3];
         this.ships[1] = this.createShip(0, 0, 0, this.enemyRoute_.getDockingTarget_());
         this.ships[2] = this.createShip(0, 0, 0, this.enemyRoute_.getDockingTarget_());
         this.ships[0] = this.createShip(0, 0, 1, this.enemyRoute_.getDockingTarget_());
         this.ships[0].name = GlobalStatus.gameText.getText(833);

         for(var5 = 0; var5 < this.ships.length; ++var5) {
            this.ships[var5].setToSleep();
            this.ships[var5].setRoute(this.enemyRoute_.clone());
            this.ships[var5].geometry.setRotation(0, 2048, 0);
         }

         this.successObjective = new Objective(22, 0, this);
         this.failObjective_ = new Objective(7, 1, this);
         break;
      case 24:
         this.enemyRoute_ = new Route(new int[]{100000, 0, 0, 100000, 0, -30000});
         this.ships = new KIPlayer[3];

         for(var5 = 0; var5 < 3; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
            this.ships[var5].setRoute(this.enemyRoute_.clone());
         }

         this.stationaries[3].setVisible(false);
         this.successObjective = new Objective(22, 0, this);
         break;
      case 25:
      case 29:
         this.ships = new KIPlayer[3];
         var5 = 0;

         while(true) {
            if (var5 >= 3) {
               break label214;
            }

            this.ships[var5] = this.createShip(9, 0, 8, (Waypoint)null);
            var2 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            var3 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            var4 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            this.ships[var5].setPosition(var2, var3, var4);
            ++var5;
         }
      case 26:
         this.ships = new KIPlayer[2];

         for(var5 = 0; var5 < 2; ++var5) {
            this.ships[var5] = this.createShip(9, 0, 8, (Waypoint)null);
            ((PlayerFighter)this.ships[var5]).geometry.setTransform(this.ego.shipGrandGroup_.getToParentTransform());
            ((PlayerFighter)this.ships[var5]).setPosition(this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400), this.ego.shipGrandGroup_.getPosZ() + 2000);
         }

         this.successObjective = new Objective(7, 2, this);
         break;
      case 28:
         this.tempVec = this.stationaries[3].getPosition(this.tempVec);
         this.enemyRoute_ = new Route(new int[]{this.tempVec.x, this.tempVec.y, this.tempVec.z});
         this.ships = new KIPlayer[5];
         var5 = 0;

         while(true) {
            if (var5 >= 5) {
               break label214;
            }

            this.ships[var5] = this.createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
            ++var5;
         }
      case 36:
         Status.getMission().setAgent(new Agent(-1, GlobalStatus.gameText.getText(826), 29, 5, 1, true, -1, -1, -1));
         this.createMission();
         break;
      case 38:
         this.enemyRoute_ = new Route(new int[]{0, 10000, 50000});
         this.ships = new KIPlayer[7];

         for(var3 = 0; var3 < 2; ++var3) {
            this.ships[var3] = this.createShip(2, 1, 15, this.enemyRoute_.getDockingTarget_());
            this.ships[var3].player.setAlwaysFriend(true);
            ((PlayerFixedObject)((PlayerFixedObject)this.ships[var3])).setMoving(false);
            ((PlayerFixedObject)((PlayerFixedObject)this.ships[var3])).setPosition(this.enemyRoute_.getDockingTarget_().x + -10000 + GlobalStatus.random.nextInt(20000), this.enemyRoute_.getDockingTarget_().y + -10000 + GlobalStatus.random.nextInt(20000), this.enemyRoute_.getDockingTarget_().z + -10000 + GlobalStatus.random.nextInt(20000));
         }

         for(var3 = 2; var3 < this.ships.length; ++var3) {
            this.ships[var3] = this.createShip(3, 0, Globals.getRandomEnemyFighter(3), this.enemyRoute_.getDockingTarget_());
            this.ships[var3].setToSleep();
            this.ships[var3].player.setAlwaysEnemy(true);
         }

         this.successObjective = new Objective(18, 2, 7, this);
         this.failObjective_ = new Objective(7, 2, this);
         break;
      case 40:
         this.enemyRoute_ = new Route(new int[]{-20000, -3000, 200000});
         this.kamikazePath = new Route(new int[]{-20000, -3000, 65000, -20000, -3000, 200000});
         this.ships = new KIPlayer[9];
         this.ships[0] = this.createShip(0, 1, 13, (Waypoint)null);
         this.ships[0].player.setAlwaysFriend(true);
         this.ships[0].name = GlobalStatus.gameText.getText(826);
         this.ships[0].player.setMaxHP(1200 + 5 * Status.getLevel());
         ((PlayerFixedObject)((PlayerFixedObject)this.ships[0])).setMoving(true);
         ((PlayerFixedObject)((PlayerFixedObject)this.ships[0])).setPosition(this.kamikazePath.getWaypoint(0).x, this.kamikazePath.getWaypoint(0).y, this.kamikazePath.getWaypoint(0).z);

         for(var4 = 1; var4 < 5; ++var4) {
            this.ships[var4] = this.createShip(0, 0, Globals.getRandomEnemyFighter(0), (Waypoint)null);
            this.ships[var4].setRoute(this.kamikazePath.clone());
            this.ships[var4].player.setAlwaysFriend(true);
            if (var4 == 2) {
               this.ships[2].name = GlobalStatus.gameText.getText(827);
            }
         }

         for(var4 = 5; var4 < 9; ++var4) {
            this.ships[var4] = this.createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
            this.ships[var4].player.setAlwaysEnemy(true);
         }

         this.stationaries[3].setPosition(-20000, -3000, 200000);
         if (initStreamOutPosition) {
            this.ego.setPosition_(-65000, 0, 80000);
            this.ego.shipGrandGroup_.setRotation(0, 1024, 0);
         }

         this.failObjective_ = new Objective(7, 1, this);
         break;
      case 41:
         this.ships = new KIPlayer[5];
         this.ships[0] = this.createShip(1, 1, 13, (Waypoint)null);
         this.ships[0].player.setAlwaysFriend(true);
         this.ships[0].name = GlobalStatus.gameText.getText(826);
         this.ships[0].player.setHitPoints(lastMissionFreighterHitpoints);
         ((PlayerFixedObject)((PlayerFixedObject)this.ships[0])).setMoving(true);
         ((PlayerFixedObject)((PlayerFixedObject)this.ships[0])).setPosition(0, 0, -200000);

         for(var4 = 1; var4 < 5; ++var4) {
            this.ships[var4] = this.createShip(9, 0, 8, (Waypoint)null);
            this.ships[var4].player.setAlwaysEnemy(true);
            var5 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            var2 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            var3 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
            this.ships[var4].setPosition(var5, var2, var3);
         }

         this.ego.setPosition_(3000, 2000, -220000);
         this.ego.shipGrandGroup_.setRotation(0, 0, 0);
         this.successObjective = new Objective(25, 0, this);
         this.failObjective_ = new Objective(7, 1, this);
      }

      this.radioMessages = this.createRadioMessages(Status.getCurrentCampaignMission());
   }

   private void createScene() {
      int var1;
      int var3;
      label88:
      switch(this.currentMod) {
      case 4:
         int var11;
         var1 = (var11 = Status.getSystem().getRace() == 1 ? 2 : (Status.getSystem().getRace() == 0 ? 0 : 1)) == 0 ? 20 : 6;
         Agent[] var9;
         var3 = (var9 = Status.getStation().getBarAgents()).length;
         this.ships = new KIPlayer[var3 + var1];
         boolean[] var5 = new boolean[this.barFigurePositions.length / 3];

         int var6;
         for(var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = false;
         }

         for(var6 = 0; var6 < var3; ++var6) {
            short var7 = Globals.BAR_FIGURES[var9[var6].getRace()];
            if (var9[var6].getRace() == 0 && !var9[var6].isMale()) {
               var7 = 14224;
            }

            boolean var8 = false;

            int var13;
            do {
               var13 = GlobalStatus.random.nextInt(var5.length);
            } while(var5[var13]);

            var5[var13] = true;
            this.ships[var6] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var7), this.barFigurePositions[var13 * 3], this.barFigurePositions[var13 * 3 + 1], this.barFigurePositions[var13 * 3 + 2]);
            this.ships[var6].mainMesh_.setRenderLayer(2);
            this.ships[var6].mainMesh_.disableAnimation();
         }

         if (var1 <= 0) {
            return;
         }

         GlobalStatus.random.setSeed((long)(Status.getStation().getId() + 1000));
         var6 = 4096 / var1;
         int var12 = var3;

         while(true) {
            if (var12 >= this.ships.length) {
               break label88;
            }

            short var14 = Globals.BAR_MESHES[var11][GlobalStatus.random.nextInt(Globals.BAR_MESHES[var11].length)];
            this.ships[var12] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var14), 0, 0, 0);
            this.ships[var12].mainMesh_.setRenderLayer(2);
            this.ships[var12].mainMesh_.setRotation(0, var12 * var6, 0);
            ++var12;
         }
      case 23:
         var1 = Status.getSystem().getRace() == 1 ? 2 : (Status.getSystem().getRace() == 0 ? 0 : 1);
         this.ships = new KIPlayer[7];
         this.ships[0] = this.createShip(Status.getShip().getRace(), 0, Status.getShip().getIndex(), (Waypoint)null);
         this.ships[0].setPosition(0, 1200, 10240 - Ship.SHIP_HANGAR_OFFSETS[Status.getShip().getIndex()] + 100);
         this.ships[0].geometry.setRotation(0, 2048, 0);
         this.ships[0].setRoute(new Route(new int[]{0, 500, -100000}));
         ((PlayerFighter)this.ships[0]).removeTrail();
         ((PlayerFighter)this.ships[0]).setExhaustVisible(false);
         this.ships[0].setToSleep();
         this.ships[0].player.setAlwaysFriend(true);
         GlobalStatus.random.setSeed((long)Status.getStation().getId());
         int var2 = 0;

         for(var3 = 1; var3 < 6; ++var3) {
            short var4 = Globals.HANGAR_MESHES[var1][GlobalStatus.random.nextInt(Globals.HANGAR_MESHES[var1].length - 3)];
            if (var3 == 5) {
               var4 = Globals.HANGAR_MESHES[var1][Globals.HANGAR_MESHES[var1].length - 2];
            } else if (var3 == 1) {
               var4 = Globals.HANGAR_MESHES[var1][Globals.HANGAR_MESHES[var1].length - 1];
            }

            this.ships[var3] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var4), 0, 0, var2 << 12);
            this.ships[var3].mainMesh_.setRenderLayer(1);
            this.ships[var3].mainMesh_.setRotation(0, 2048, 0);
            ++var2;
         }

         short var10 = Globals.HANGAR_MESHES[var1][Globals.HANGAR_MESHES[var1].length - 3];
         this.ships[this.ships.length - 1] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var10), 0, 0, 8192);
         this.ships[this.ships.length - 1].mainMesh_.setRenderLayer(2);
         this.ships[this.ships.length - 1].mainMesh_.setRotation(0, 2048, 0);
         break;
      default:
         return;
      }

      GlobalStatus.random.setSeed(System.currentTimeMillis());
   }

   private void assignGuns() {
      this.enemyGuns = null;
      int var1 = (int)((float)AEMath.min(Status.getLevel(), 20) / 1.8F) + (int)((float)Status.getCurrentCampaignMission() / 5.0F);
      int var2 = 0;
      if (this.ships != null) {
         int var3;
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            if (this.ships[var3] != null && this.ships[var3].armed) {
               ++var2;
               if (this.ships[var3].isWingman()) {
                  ++var2;
               }
            }
         }

         this.enemyGuns = new AbstractMesh[var2];
         var3 = 0;

         for(var2 = 0; var2 < this.ships.length; ++var2) {
            Gun var6;
            if (this.ships[var2] != null && this.ships[var2].armed) {
               int var4 = var1 + 2;
               if (Status.getMission().getType() == 12 && this.ships[var2].player.isAlwaysFriend()) {
                  var4 = Status.getShip().getFirePower() + 2;
               } else if (Status.getCurrentCampaignMission() == 4) {
                  var4 = 1;
               }

               (var6 = new Gun(0, var4, 4, -1, 3000, 600 - (Status.getCurrentCampaignMission() << 1), 16.0F, (AEVector3D)null, (AEVector3D)null)).setFriendGun(true);
               var6.setLevel(this);
               var6.setSparks(this.gunSparks);
               int var5 = this.ships[var2].race == 9 ? 7 : (this.ships[var2].race == 0 ? 1 : (this.ships[var2].race == 1 ? 3 : 4));
               this.enemyGuns[var3] = new ObjectGun(var6, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[var5]));
               this.enemyGuns[var3].setRenderLayer(2);
               ++var3;
               this.ships[var2].addGun(var6, 0);
            }

            if (this.ships[var2].isWingman() && this.ships[var2].armed) {
               (var6 = new Gun(18, 0, 4, -1, 3000, 400, 16.0F, (AEVector3D)null, (AEVector3D)null)).setFriendGun(true);
               var6.setLevel(this);
               var6.setSparks(this.gunSparks);
               var6.index = 18;
               var6.subType = 1;
               this.enemyGuns[var3] = new ObjectGun(var6, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[18]));
               this.enemyGuns[var3].setRenderLayer(2);
               ++var3;
               this.ships[var2].addGun(var6, 1);
            }
         }
      }

   }

   private void connectPlayers() {
      if (GlobalStatus.applicationManager.GetCurrentApplicationModule() != GlobalStatus.scenes[1]) {
         Player[] var1;
         int var2;
         if (this.ships != null && this.ego != null) {
            var1 = new Player[this.ships.length];

            for(var2 = 0; var2 < var1.length; ++var2) {
               var1[var2] = this.ships[var2].player;
            }

            this.ego.player.setEnemies(var1);
         }

         if (this.asteroids != null && this.ego != null) {
            var1 = new Player[this.asteroids.length];

            for(var2 = 0; var2 < var1.length; ++var2) {
               var1[var2] = this.asteroids[var2].player;
            }

            this.ego.player.addEnemies(var1);
         }

         if (this.ships != null) {
            for(int var7 = 0; var7 < this.ships.length; ++var7) {
               var2 = this.ships[var7].race;
               boolean var3 = this.ships[var7].isWingman();
               int var4 = 0;

               for(int var5 = 0; var5 < this.ships.length; ++var5) {
                  if (this.ships[var5] != this.ships[var7] && (this.ships[var5].race != var2 || var3)) {
                     ++var4;
                  }
               }

               Player[] var9 = new Player[(this.ego == null ? 0 : 1) + var4];
               int var6;
               Mission var8;
               if (((var8 = Status.getMission()).getType() != 12 || var7 % 2 != 1) && var8.getType() != 2 && var8.getType() != 9 && (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 40) && (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 41)) {
                  var4 = 0;
                  if (this.ego != null) {
                     var9[0] = this.ego.player;
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

                  var9[var9.length - 1] = this.ego.player;
               }

               this.ships[var7].player.addEnemies(var9);
            }
         }

      }
   }

   private KIPlayer createJunk(Waypoint var1, int var2) {
      var2 = var1 != null ? var1.x : 0;
      int var3 = var1 != null ? var1.y : 0;
      int var6 = var1 != null ? var1.z : 0;
      var2 = var2 + GlobalStatus.random.nextInt(20000) - 10000;
      var3 = var3 + GlobalStatus.random.nextInt(20000) - 10000;
      var6 = var6 + GlobalStatus.random.nextInt(20000) - 10000;
      AbstractMesh var4;
      (var4 = AEResourceManager.getGeometryResource(9996)).setRenderLayer(2);
      Player var5 = null;
      var5 = null;
      var5 = new Player(1000.0F, 1, 0, 0, 0);
      PlayerJunk var7;
      (var7 = new PlayerJunk(9996, var5, var4, var2, var3, var6)).setExplosion(new Explosion(1));
      var7.mainMesh_.rotateEuler(var2, var3, var6);
      var7.setLevel(this);
      return var7;
   }

   private KIPlayer createShip(int var1, int var2, int var3, Waypoint var4) {
      int var5 = var4 != null ? var4.x : 0;
      int var6 = var4 != null ? var4.y : 0;
      int var12 = var4 != null ? var4.z : 0;
      int var7 = GlobalStatus.random.nextInt(40000) - 20000;
      int var8 = GlobalStatus.random.nextInt(40000) - 20000;
      int var9 = GlobalStatus.random.nextInt(40000) - 20000;
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

      (var13 = new Player(2000.0F, var7, 1, 1, 0)).setEmpData(var10, var11);
      switch(var2) {
      case 0:
         var14 = new PlayerFighter(var3, var1, var13, (AbstractMesh)null, var5, var6, var12);
         var15 = new Explosion(1);
         ((KIPlayer)var14).setGroup(Globals.getShipGroup(var3, var1), var3);
         break;
      case 1:
         var14 = new PlayerFixedObject(var3, var1, var13, (AbstractMesh)null, var5, var6, var12);
         BoundingVolume[] var16;
         (var16 = new BoundingVolume[1])[0] = new BoundingAAB(var5, var6, var12, 0, 300, 0, 4000, 4000, 15000);
         ((PlayerFixedObject)((PlayerFixedObject)var14)).setBounds(var16);
         (var15 = new Explosion(1)).setBig();
         ((KIPlayer)var14).setGroup(Globals.getShipGroup(var3, var1), var3);
      }

      ((KIPlayer)var14).setLevel(this);
      ((KIPlayer)var14).setExplosion(var15);
      return (KIPlayer)var14;
   }

   private static Route createRoute(int var0) {
      int[] var3 = new int[var0 * 3];

      for(int var1 = 0; var1 < var3.length; var1 += 3) {
         int var2 = GlobalStatus.random.nextInt(2) == 0 ? 1 : -1;
         var3[var1] = '썐' + GlobalStatus.random.nextInt(30000);
         var3[var1] *= var2;
         var3[var1 + 1] = -10000 + GlobalStatus.random.nextInt(20000);
         if (var1 != 0) {
            var3[var1 + 2] = var3[var1 - 3 + 2] + '썐' + GlobalStatus.random.nextInt(30000);
         } else {
            var3[var1 + 2] = '썐' + GlobalStatus.random.nextInt(30000);
         }
      }

      return new Route(var3);
   }

   private RadioMessage[] createRadioMessages(int var1) {
      this.radioMessages = null;
      switch(var1) {
      case 0:
         this.radioMessages = new RadioMessage[22];
         this.radioMessages[0] = new RadioMessage(844, 17, 5, 15000);
         this.radioMessages[1] = new RadioMessage(845, 0, 6, 0);
         this.radioMessages[2] = new RadioMessage(846, 0, 6, 1);
         this.radioMessages[3] = new RadioMessage(847, 10, 6, 2);
         this.radioMessages[4] = new RadioMessage(848, 9, 6, 3);
         this.radioMessages[5] = new RadioMessage(849, 9, 6, 4);
         this.radioMessages[6] = new RadioMessage(850, 11, 6, 5);
         this.radioMessages[7] = new RadioMessage(851, 9, 6, 6);
         this.radioMessages[8] = new RadioMessage(852, 0, 6, 7);
         this.radioMessages[9] = new RadioMessage(854, 0, 9, new int[]{0, 1, 2});
         this.radioMessages[10] = new RadioMessage(855, 0, 6, 9);
         this.radioMessages[11] = new RadioMessage(856, 0, 6, 10);
         this.radioMessages[12] = new RadioMessage(857, 15, 6, 11);
         this.radioMessages[13] = new RadioMessage(858, 0, 6, 12);
         this.radioMessages[14] = new RadioMessage(859, 0, 6, 13);
         this.radioMessages[15] = new RadioMessage(860, 15, 6, 14);
         this.radioMessages[16] = new RadioMessage(861, 0, 6, 15);
         this.radioMessages[17] = new RadioMessage(862, 15, 6, 16);
         this.radioMessages[18] = new RadioMessage(863, 0, 6, 17);
         this.radioMessages[19] = new RadioMessage(864, 0, 6, 18);
         this.radioMessages[20] = new RadioMessage(865, 15, 6, 19);
         this.radioMessages[21] = new RadioMessage(866, 0, 6, 20);
         break;
      case 1:
         this.radioMessages = new RadioMessage[3];
         this.radioMessages[0] = new RadioMessage(867, 2, 5, 10000);
         this.radioMessages[1] = new RadioMessage(868, 2, 6, 0);
         this.radioMessages[2] = new RadioMessage(869, 2, 6, 1);
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
         this.radioMessages = new RadioMessage[2];
         this.radioMessages[0] = new RadioMessage(909, 2, 16, 0);
         this.radioMessages[1] = new RadioMessage(910, 0, 6, 0);
         break;
      case 14:
         this.radioMessages = new RadioMessage[4];
         this.radioMessages[0] = new RadioMessage(968, 18, 5, 6000);
         this.radioMessages[1] = new RadioMessage(969, 0, 20, 2);
         this.radioMessages[2] = new RadioMessage(970, 0, 6, 1);
         this.radioMessages[3] = new RadioMessage(971, 18, 6, 2);
         break;
      case 16:
         this.radioMessages = new RadioMessage[2];
         this.radioMessages[0] = new RadioMessage(983, 19, 5, 6000);
         this.radioMessages[1] = new RadioMessage(984, 0, 6, 0);
         break;
      case 21:
         this.radioMessages = new RadioMessage[3];
         this.radioMessages[0] = new RadioMessage(1018, 14, 8, 0);
         this.radioMessages[1] = new RadioMessage(1019, 0, 6, 0);
         this.radioMessages[2] = new RadioMessage(1020, 14, 21, 0);
         break;
      case 24:
         this.radioMessages = new RadioMessage[2];
         this.radioMessages[0] = new RadioMessage(1047, 6, 22, 3);
         this.radioMessages[1] = new RadioMessage(1048, 6, 6, 0);
         break;
      case 29:
         this.radioMessages = new RadioMessage[5];
         this.radioMessages[0] = new RadioMessage(1073, 0, 23, 0);
         this.radioMessages[1] = new RadioMessage(1074, 0, 6, 0);
         this.radioMessages[2] = new RadioMessage(1075, 0, 6, 1);
         this.radioMessages[3] = new RadioMessage(1076, 19, 5, 120000);
         this.radioMessages[4] = new RadioMessage(1077, 0, 6, 3);
         break;
      case 38:
         this.radioMessages = new RadioMessage[1];
         this.radioMessages[0] = new RadioMessage(1146, 21, 5, 5000);
         break;
      case 40:
         this.radioMessages = new RadioMessage[2];
         this.radioMessages[0] = new RadioMessage(1163, 7, 12, 0);
         this.radioMessages[1] = new RadioMessage(1164, 0, 24, 0);
         break;
      case 41:
         this.radioMessages = new RadioMessage[1];
         this.radioMessages[0] = new RadioMessage(1163, 7, 12, 0);
      }

      return this.radioMessages;
   }

   public final PlayerEgo getPlayer() {
      return this.ego;
   }

   public final KIPlayer[] getEnemies() {
      return this.ships;
   }

   public final KIPlayer[] getLandmarks() {
      return this.stationaries;
   }

   public final KIPlayer[] getAsteroids() {
      return this.asteroids;
   }

   public final Waypoint getAsteroidWaypoint() {
      return this.asteroidsWaypoint;
   }

   public final Route getPlayerRoute() {
      return this.missionRoute;
   }

   public final void setPlayerRoute(Route var1) {
      this.missionRoute = null;
   }

   public final void flashScreen(int var1) {
      if (this.flashType < 3 || this.flashIntensity <= this.lastFlashDuration >> 1) {
	  Level.exploding_ = true;
         this.flashType = var1;
         this.lastFlashDuration = var1 >= 3 ? 10000 : (var1 == 1 ? 7000 : 2000);
         this.flashIntensity = this.lastFlashDuration;
         if (var1 == 3) {
            Level.explosionR = 255.0F;
            Level.explosionG = 255.0F;
            Level.explosionB = 255.0F;
            this.ego.hit();
         } else if (var1 == 2) {
             Level.explosionR = (float)bgR * 1.5F;
             Level.explosionG = (float)bgG * 1.5F;
             Level.explosionB = (float)bgB * 1.5F;
         } else if (var1 == 4) {
             Level.explosionR = 0.0F;
             Level.explosionG = 0.0F;
             Level.explosionB = 255.0F;
         } else {
            float var2 = var1 == 1 ? 8.0F : 5.0F;
            Level.explosionR = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgR * var2)));
            Level.explosionG = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgG * var2)));
            Level.explosionB = (float)AEMath.max(10, AEMath.min(255, (int)((float)bgB * var2)));
         }
      }
   }

   public final void enemyDied(boolean var1) {
      --this.enemiesIntact;
      ++this.enemiesDestroyed;
      if (!var1) {
         Status.incKills();
         ++this.egoScore;
      } else {
         ++this.challengerScore;
      }

      this.flashScreen(1);
   }

   public final void junkDied() {
      ++Status.destroyedJunk;
      --this.enemiesIntact;
   }

   public final void friendDied() {
      --this.friendsAlive;
   }

   public static void wingmanDied(int var0) {
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
         Status.wingmanFace = null;
      }
   }

   public final void asteroidDied() {
      --this.asteroidsIntact;
   }

   public final int unknown7f9_() {
      return 0;
   }

   public final int getEnemiesLeft() {
      return this.enemiesIntact;
   }

   public final int getFriendsLeft() {
      return this.friendsAlive;
   }

   public final RadioMessage[] getMessages() {
      return this.radioMessages;
   }

   public final int getTimeLimit() {
      return this.timeLimit;
   }

   public final StarSystem getStarSystem() {
      return this.starSystem;
   }

   public final boolean isInAsteroidCenterRange(AEVector3D var1) {
      return this.asteroidField.outerCollide_(var1.x, var1.y, var1.z);
   }

   public final boolean collideStream(AEVector3D var1) {
      return this.stationaries[1] != null ? this.stationaries[1].outerCollide(var1.x, var1.y, var1.z) : false;
   }

   public final boolean collideStation(AEVector3D var1) {
      return this.stationaries != null && this.stationaries[0] != null && !Status.inEmptyOrbit() ? this.stationaries[0].outerCollide(var1.x, var1.y, var1.z) : false;
   }

   public final void render(long var1) {
       if(Status.inAlienOrbit()) {
		  Matrix m = new Matrix();
		  m.translateTo(0,0,-4096);
		      Light omni;
		      (omni = new Light()).setIntensity(2.0F);
		      omni.setMode(Light.OMNI);
		  GlobalStatus.renderer.setLight(m, omni);
	      }
      if (Level.exploding_) {
         GlobalStatus.graphics.setColor((int)Level.explosionR, (int)Level.explosionG, (int)Level.explosionB);
      } else {
         GlobalStatus.graphics.setColor(bgR, bgG, bgB);
      }

      GlobalStatus.graphics.fillRect(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
      this.starSystem.render_();
      GlobalStatus.renderer.drawNodeInVF(this.skybox);
      int var3;
      if (this.egoGuns != null) {
         for(var3 = 0; var3 < this.egoGuns.length; ++var3) {
            this.egoGuns[var3].render();
         }
      }

      if (this.enemyGuns != null) {
         for(var3 = 0; var3 < this.enemyGuns.length; ++var3) {
            this.enemyGuns[var3].render();
         }
      }

      if (this.ships != null) {
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            this.ships[var3].update(var1);
            this.ships[var3].appendToRender();
         }
      }

      if (this.asteroids != null) {
         for(var3 = 0; var3 < this.asteroids.length; ++var3) {
            this.asteroids[var3].update(var1);
            this.asteroids[var3].appendToRender();
         }
      }

      if (this.stationaries != null) {
         for(var3 = 0; var3 < this.stationaries.length; ++var3) {
            if (this.stationaries[var3] != null) {
               this.stationaries[var3].update(var1);
               this.stationaries[var3].appendToRender();
            }
         }
      }

      this.starSystem.renderSunStreak__();
   }

   public final void renderRockets_() {
      this.starSystem.render2D();
      if (this.egoGuns != null) {
         for(int var1 = 0; var1 < this.egoGuns.length; ++var1) {
            if (this.egoGuns[var1] instanceof RocketGun) {
               ((RocketGun)this.egoGuns[var1]).renderRocket_();
            }
         }
      }

   }

   public final void render2(long var1) {
      if(Status.inAlienOrbit()) {
	  Matrix m = new Matrix();
	  m.translateTo(0,0,-4096);
	      Light omni;
	      (omni = new Light()).setIntensity(2.0F);
	      omni.setMode(Light.OMNI);
	  GlobalStatus.renderer.setLight(m, omni);
      }
      GlobalStatus.graphics.setColor(bgR, bgG, bgB);
      GlobalStatus.graphics.fillRect(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
      this.starSystem.render_();
      GlobalStatus.renderer.drawNodeInVF(this.skybox);
      int var3;
      if (this.egoGuns != null) {
         for(var3 = 0; var3 < this.egoGuns.length; ++var3) {
            this.egoGuns[var3].render();
         }
      }

      if (this.enemyGuns != null) {
         for(var3 = 0; var3 < this.enemyGuns.length; ++var3) {
            this.enemyGuns[var3].render();
         }
      }

      if (this.ships != null) {
         for(var3 = 0; var3 < this.ships.length; ++var3) {
            this.ships[var3].appendToRender();
         }
      }

      if (this.asteroids != null) {
         for(var3 = 0; var3 < this.asteroids.length; ++var3) {
            this.asteroids[var3].appendToRender();
         }
      }

      if (this.stationaries != null) {
         for(var3 = 0; var3 < this.stationaries.length; ++var3) {
            if (this.stationaries[var3] != null) {
               this.stationaries[var3].appendToRender();
            }
         }
      }

      this.starSystem.renderSunStreak__();
   }

   public final void updateOrbit_(long var1) {
      if (Status.inAlienOrbit() && GlobalStatus.random.nextInt(25) == 0) {
         this.flashScreen(2);
      }

      if (Level.exploding_) {
         this.flashIntensity = (int)((long)this.flashIntensity - var1);
         if (this.flashIntensity < 0) {
             Level.exploding_ = false;
         }

         float var3 = (float)this.flashIntensity / (float)this.lastFlashDuration;
         Level.explosionR = AEMath.max((float)bgR, Level.explosionR * var3);
         Level.explosionG = AEMath.max((float)bgG, Level.explosionG * var3);
         Level.explosionB = AEMath.max((float)bgB, Level.explosionB * var3);
      }

      int var4;
      KIPlayer var5;
      Level var8;
      if (Status.getMission() == null || Status.getMission().isEmpty()) {
         var4 = (int)var1;
         var8 = this;
         this.commonRespawnTick += var4;
         this.jumperRespawnTick += var4;
         if (this.jumperRespawnTick > 20000) {
            this.jumperRespawnTick = 0;
            if (this.ships != null) {
               for(var4 = 0; var4 < var8.ships.length; ++var4) {
                  if ((var5 = var8.ships[var4]).isJumper() && var5.isDead() && !var5.player.isActive()) {
                     var5.revive();
                     if (var4 < var8.localFightersCnt + var8.jumperCnt) {
                        ((PlayerFighter)var5).setPosition(0, 0, 0);
                     } else {
                        ((PlayerFixedObject)var5).setPosition(10000, 0, -70000);
                     }
                     break;
                  }
               }
            }
         }

         if (var8.commonRespawnTick > 40000) {
            var8.commonRespawnTick = 0;
            var4 = 0;
            if (var8.raidersCnt > 0 && var8.ships != null) {
               for(int var10 = 0; var10 < var8.ships.length; ++var10) {
                  if (var10 >= var8.ships.length - var8.raidersCnt && !var8.ships[var10].isWingman() && var8.ships[var10].isDead() && !var8.ships[var10].player.isActive()) {
                     ++var4;
                  }
               }
            }

            if (var8.ships != null) {
               boolean var11 = false;

               for(int var6 = 0; var6 < var8.ships.length; ++var6) {
                  KIPlayer var7 = var8.ships[var6];
                  if ((var8.localFightersCnt > 0 && var6 < var8.localFightersCnt || var8.jumperCnt > 0 && var6 < var8.localFightersCnt + var8.jumperCnt && var6 > var8.localFightersCnt) && var7.isDead() && !var7.player.isActive()) {
                     var7.revive();
                     ((PlayerFighter)var7).setPosition(0, 0, 0);
                  }

                  if (var4 >= 2 && var8.raidWavesCounter < 2 && var8.raidersCnt > 0 && var6 >= var8.ships.length - var8.raidersCnt && var7.isDead() && !var7.player.isActive()) {
                     var11 = true;
                     var7.revive();
                     ((PlayerFighter)var7).setPosition(var7.targetX, var7.targetY, var8.ego.getPosition().z + '鱀');
                  }
               }

               if (var11) {
                  ++var8.raidWavesCounter;
               }
            }
         }
      }

      if (Status.getStation().isAttackedByAliens() || Status.inAlienOrbit()) {
         var4 = (int)var1;
         var8 = this;
         this.alienRespawnTick += var4;
         if (this.alienRespawnTick > 40000) {
            this.alienRespawnTick = 0;
            if (this.ships != null) {
               for(var4 = 0; var4 < var8.ships.length; ++var4) {
                  if ((var5 = var8.ships[var4]).race == 9 && var5.isDead() && !var5.player.isActive()) {
                     var5.revive();
                     PlayerFighter var10000;
                     int var10001;
                     int var10002;
                     int var10003;
                     if (!Status.inAlienOrbit() && Status.getStation().isAttackedByAliens()) {
                        var10000 = (PlayerFighter)var5;
                        var10001 = var8.stationaries[3].getPosition(var8.tempVec).x - 10000 + GlobalStatus.random.nextInt(20000);
                        var10002 = var8.stationaries[3].getPosition(var8.tempVec).y - 10000 + GlobalStatus.random.nextInt(20000);
                        var10003 = var8.stationaries[3].getPosition(var8.tempVec).z - 10000 + GlobalStatus.random.nextInt(20000);
                     } else {
                        var10000 = (PlayerFighter)var5;
                        var10001 = var8.ego.getPosition().x - 30000 + GlobalStatus.random.nextInt(60000);
                        var10002 = var8.ego.getPosition().y - 30000 + GlobalStatus.random.nextInt(60000);
                        var10003 = var8.ego.getPosition().z + GlobalStatus.random.nextInt(2) == 0 ? 30000 : -30000;
                     }

                     var10000.setPosition(var10001, var10002, var10003);
                  }
               }
            }
         }
      }

      int var9;
      if (this.egoGuns != null) {
         for(var9 = 0; var9 < this.egoGuns.length; ++var9) {
            ((AbstractGun)this.egoGuns[var9]).update(var1);
         }
      }

      if (this.enemyGuns != null) {
         for(var9 = 0; var9 < this.enemyGuns.length; ++var9) {
            ((AbstractGun)this.enemyGuns[var9]).update(var1);
         }
      }

   }

   public final boolean checkObjective(int var1) {
      return this.successObjective != null ? this.successObjective.achieved(var1) : false;
   }

   public final void stealFriendCargo() {
      this.stolenFromFriend = true;
   }

   public final boolean friendCargoWasStolen() {
      return this.stolenFromFriend;
   }

   public final void removeObjectives() {
      this.successObjective = null;
      this.failObjective_ = null;
   }

   public final AbstractMesh[] getPlayerGuns_() {
      return this.egoGuns;
   }

   public final boolean checkGameOver(int var1) {
      return this.failObjective_ != null ? this.failObjective_.achieved(var1) : false;
   }

   private void createRadioMessage(boolean var1, int var2) {
      if (this.ego != null && this.ego.radio != null && Status.getMission().isEmpty()) {
         this.radioMessages = new RadioMessage[1];
         var2 = var2 == 2 ? 24 : (var2 == 0 ? 23 : (var2 == 3 ? 21 : 22));
         int var3;
         short var4 = (short)((var3 = var1 ? 250 : 247) + GlobalStatus.random.nextInt(3));
         this.radioMessages[0] = new RadioMessage(var4, var2, 5, 0);
         this.ego.radio.setMessages(this.radioMessages);
         this.ego.radio.showMessage();
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

      if (!this.localsAlarmed && var2) {
         this.localsAlarmed = true;
         this.createRadioMessage(true, var1);
      }

   }

   public final void friendTurnedEnemy(int var1) {
      if (!this.friendlyFireAlerted) {
         this.friendlyFireAlerted = true;
         this.createRadioMessage(false, var1);
      }

   }

   public final void onRelease() {
      AEResourceManager.initGeometryTranforms();
      int var1;
      if (this.stationaries != null) {
         for(var1 = 0; var1 < this.stationaries.length; ++var1) {
            if (this.stationaries[var1] != null) {
               this.stationaries[var1].OnRelease();
               this.stationaries[var1] = null;
            }
         }
      }

      this.stationaries = null;
      if (this.gunSparks != null) {
         ((Sparks)this.gunSparks).OnRelease();
      }

      this.gunSparks = null;
      if (this.missilesSparks != null) {
         ((Sparks)this.missilesSparks).OnRelease();
      }

      this.missilesSparks = null;
      if (this.egoGuns != null) {
         for(var1 = 0; var1 < this.egoGuns.length; ++var1) {
            this.egoGuns[var1].OnRelease();
         }
      }

      this.egoGuns = null;
      if (this.enemyGuns != null) {
         for(var1 = 0; var1 < this.enemyGuns.length; ++var1) {
            this.enemyGuns[var1].OnRelease();
         }
      }

      this.enemyGuns = null;
      if (this.ego != null) {
         this.ego.OnRelease();
      }

      this.ego = null;
      if (this.ships != null) {
         for(var1 = 0; var1 < this.ships.length; ++var1) {
            this.ships[var1].OnRelease();
         }
      }

      this.ships = null;
      if (this.missionRoute != null) {
         this.missionRoute.onRelease();
      }

      this.missionRoute = null;
      if (this.kamikazePath != null) {
         this.kamikazePath.onRelease();
      }

      this.kamikazePath = null;
      if (this.enemyRoute_ != null) {
         this.enemyRoute_.onRelease();
      }

      this.enemyRoute_ = null;
      this.radioMessages = null;
      this.successObjective = null;
      this.failObjective_ = null;
      this.spaceCreationStep__ = 0;
      System.gc();
   }
}
