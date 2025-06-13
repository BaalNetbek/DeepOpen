package GoF2;

import AE.AEFile;
import AE.AbstractMesh;
import AE.Camera;
import AE.GlobalStatus;
import AE.TargetFollowCamera;
import AE.Vec3f;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class Radar {
   private int[] asteroidsInFront;
   private Image lockonEnemy;
   private Image radarIconEnemy;
   private Image hudLockOnFriend;
   private Image hudRadarIconFriend;
   private Image lockonWaypoint;
   private Image lockonNeutral;
   private Image radarIconNeutral2;
   private Image bracketEnemyFar;
   private Image bracketFriendFar;
   private Image lockonWaypoint2;
   private Image radarIconNeutral;
   private Image radarIconEnemyLock;
   private Image radarIconFriendLock;
   private Image radarIconNeutralLock;
   private Image bracketBox;
   private Image crate;
   private Image asteroid;
   private Image spacejunk;
   private Image crateVoid;
   private Image vortex;
   private Image waypoint;
   private Image mapJumpgate;
   private Image sideMission;
   private Image mainMission;
   private Sprite logosSmall;
   private Sprite scanProcessAnim;
   private Sprite meteorClass;
   private Sprite bars;
   private int screenProjectionX;
   private int screenProjectionY;
   private int elipsoidCenterX;
   private int elipsoidCenterY;
   private int elipsoidWidth;
   private int elipsoidHeight;
   private boolean inViewFrustum;
   private KIPlayer[] enemies;
   private KIPlayer[] landmarks;
   private KIPlayer[] asteroids;
   private AbstractMesh[] planets;
   private Route playerRoute;
   private Level level;
   private Matrix camInvMatrix = new Matrix();
   private AEVector3D tempCameraSpacePos = new AEVector3D();
   private AEVector3D tempPos = new AEVector3D();
   private AEVector3D playerPos = new AEVector3D();
   private AEVector3D tempContextPosition = new AEVector3D();
   private Vec3f floatVector = new Vec3f();
   private int enemyArmorBarHeight;
   private String[] stationaryPlayersNames = new String[4];
   private String closestStatPlayerDistanceVisible;
   private int enemyScanPassedTime;
   private int asteroidScanPassedTime;
   private int planetScanPassedTime;
   private int landmarkScanPassedTime;
   public KIPlayer contextShip;
   private KIPlayer targetedPlayer;
   private KIPlayer contextAsteroid;
   private KIPlayer targetedAsteroid;
   private AbstractMesh contextPlanet;
   public AbstractMesh targetedPlanet;
   public KIPlayer tractorBeamTarget;
   public KIPlayer contextStation;
   public KIPlayer targetedStation;
   public boolean draw;
   private boolean onPlanetCourse_;
   private int targetedPlanetLocalIndex;
   private boolean showCargo;
   private boolean showAasteroids;
   private boolean scanerPresent;
   private boolean drillPresent;
   private boolean tractorBeamPresent;
   private boolean tractorBeamAutomatic;
   private boolean pastIntro;
   private int tractorBeamScanTime;
   private int scanTime;
   private int activeEnemyEnemiesCount;

   public Radar(Level var1) {
      this.level = var1;
      this.elipsoidCenterX = GlobalStatus.screenWidth / 2;
      this.elipsoidCenterY = GlobalStatus.screenHeight / 2;
      this.elipsoidWidth = (this.elipsoidCenterX - 5) * (this.elipsoidCenterX - 5);
      this.elipsoidHeight = (this.elipsoidCenterY - 20) * (this.elipsoidCenterY - 20);
      this.stationaryPlayersNames[0] = (Status.inAlienOrbit() ? GlobalStatus.gameText.getText(238) : Status.getStation().getName()) + " " + GlobalStatus.gameText.getText(40);
      this.stationaryPlayersNames[1] = GlobalStatus.gameText.getText(271);
      this.stationaryPlayersNames[2] = "";
      this.stationaryPlayersNames[3] = GlobalStatus.gameText.getText(269);
      AbstractMesh[] var5;
      if ((var5 = var1.getPlayerGuns_()) != null) {
         for(int var2 = 0; var2 < var5.length; ++var2) {
            if (var5[var2] instanceof RocketGun) {
               ((RocketGun)var5[var2]).setRadar(this);
            }
         }
      }

      try {
         String var6 = "/data/interface/hud_lockon_neutral.png";
         this.lockonNeutral = AEFile.loadImage("/data/interface/hud_lockon_neutral.png", true);
         var6 = "/data/interface/hud_radaricon_neutral.png";
         this.radarIconNeutral2 = AEFile.loadImage("/data/interface/hud_radaricon_neutral.png", true);
         var6 = "/data/interface/hud_radaricon_neutral_lock.png";
         this.radarIconNeutralLock = AEFile.loadImage("/data/interface/hud_radaricon_neutral_lock.png", true);
         var6 = "/data/interface/hud_lockon_enemy.png";
         this.lockonEnemy = AEFile.loadImage("/data/interface/hud_lockon_enemy.png", true);
         var6 = "/data/interface/hud_radaricon_enemy.png";
         this.radarIconEnemy = AEFile.loadImage("/data/interface/hud_radaricon_enemy.png", true);
         var6 = "/data/interface/hud_radaricon_enemy_lock.png";
         this.radarIconEnemyLock = AEFile.loadImage("/data/interface/hud_radaricon_enemy_lock.png", true);
         var6 = "/data/interface/hud_lockon_friend.png";
         this.hudLockOnFriend = AEFile.loadImage("/data/interface/hud_lockon_friend.png", true);
         var6 = "/data/interface/hud_radaricon_friend.png";
         this.hudRadarIconFriend = AEFile.loadImage("/data/interface/hud_radaricon_friend.png", true);
         var6 = "/data/interface/hud_radaricon_friend_lock.png";
         this.radarIconFriendLock = AEFile.loadImage("/data/interface/hud_radaricon_friend_lock.png", true);
         var6 = "/data/interface/hud_lockon_waypoint.png";
         this.lockonWaypoint = AEFile.loadImage("/data/interface/hud_lockon_waypoint.png", true);
         var6 = "/data/interface/hud_lockon_waypoint.png";
         this.lockonWaypoint2 = AEFile.loadImage("/data/interface/hud_lockon_waypoint.png", true);
         var6 = "/data/interface/hud_radaricon_neutral.png";
         this.radarIconNeutral = AEFile.loadImage("/data/interface/hud_radaricon_neutral.png", true);
         var6 = "/data/interface/hud_bars.png";
         Image var7 = AEFile.loadImage("/data/interface/hud_bars.png", true);
         this.bars = new Sprite(var7, 2, var7.getHeight());
         var6 = "/data/interface/logos_small.png";
         Image var9 = AEFile.loadImage("/data/interface/logos_small.png", true);
         this.logosSmall = new Sprite(var9, var9.getHeight(), var9.getHeight());
         this.logosSmall.defineReferencePixel(var9.getHeight(), var9.getHeight());
         var6 = "/data/interface/bracket_enemy_far.png";
         this.bracketEnemyFar = AEFile.loadImage("/data/interface/bracket_enemy_far.png", true);
         var6 = "/data/interface/bracket_friend_far.png";
         this.bracketFriendFar = AEFile.loadImage("/data/interface/bracket_friend_far.png", true);
         var6 = "/data/interface/bracket_box.png";
         this.bracketBox = AEFile.loadImage("/data/interface/bracket_box.png", true);
         var6 = "/data/interface/hud_crate.png";
         this.crate = AEFile.loadImage("/data/interface/hud_crate.png", true);
         var6 = "/data/interface/hud_spacejunk.png";
         this.spacejunk = AEFile.loadImage("/data/interface/hud_spacejunk.png", true);
         var6 = "/data/interface/hud_asteroid.png";
         this.asteroid = AEFile.loadImage("/data/interface/hud_asteroid.png", true);
         var6 = "/data/interface/hud_crate_void.png";
         this.crateVoid = AEFile.loadImage("/data/interface/hud_crate_void.png", true);
         var6 = "/data/interface/hud_vortex.png";
         this.vortex = AEFile.loadImage("/data/interface/hud_vortex.png", true);
         var6 = "/data/interface/hud_waypoint.png";
         this.waypoint = AEFile.loadImage("/data/interface/hud_waypoint.png", true);
         var6 = "/data/interface/menu_map_jumpgate.png";
         this.mapJumpgate = AEFile.loadImage("/data/interface/menu_map_jumpgate.png", true);
         var6 = "/data/interface/hud_scanprocess_anim_png24.png";
         var9 = AEFile.loadImage("/data/interface/hud_scanprocess_anim_png24.png", true);
         this.scanProcessAnim = new Sprite(var9, var9.getHeight(), var9.getHeight());
         this.scanProcessAnim.defineReferencePixel(var9.getHeight() / 2, var9.getHeight() / 2);
         var6 = "/data/interface/hud_meteor_class.png";
         var9 = AEFile.loadImage("/data/interface/hud_meteor_class.png", true);
         this.meteorClass = new Sprite(var9, 11, 11);
         this.meteorClass.defineReferencePixel(11, 11);
         Item var10 = Status.getShip().getFirstEquipmentOfSort(19);
         Item var8 = Status.getShip().getFirstEquipmentOfSort(17);
         Item var3 = Status.getShip().getFirstEquipmentOfSort(13);
         this.drillPresent = var10 != null;
         if (var8 != null) {
            this.scanerPresent = true;
            this.showCargo = var8.getAttribute(29) == 1;
            this.showAasteroids = var8.getAttribute(28) == 1;
            this.scanTime = var8.getAttribute(27);
         } else {
            this.scanerPresent = false;
            this.showCargo = false;
            this.showAasteroids = false;
            this.scanTime = 8000;
         }

         if (var3 != null) {
            this.tractorBeamPresent = true;
            this.tractorBeamAutomatic = var3.getAttribute(21) == 1;
            this.tractorBeamScanTime = var3.getAttribute(22);
         } else {
            this.tractorBeamPresent = false;
            this.tractorBeamScanTime = 0;
            this.tractorBeamAutomatic = false;
         }

         this.pastIntro = Status.getCurrentCampaignMission() > 0;
         this.activeEnemyEnemiesCount = 0;
         this.draw = true;
         this.asteroidsInFront = new int[5];

         for(int var11 = 0; var11 < this.asteroidsInFront.length; ++var11) {
            this.asteroidsInFront[var11] = -1;
         }

      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public final void OnRelease() {
      this.enemies = null;
      this.landmarks = null;
      this.playerRoute = null;
      this.level = null;
      this.playerPos = null;
      this.tempContextPosition = null;
      this.tempCameraSpacePos = null;
   }

   private void elipsoidIntersect(Camera var1, AEVector3D var2) {
      this.tempPos.set(var2);
      this.tempCameraSpacePos = var1.getInverseTransform(this.camInvMatrix).transformVector2(this.tempPos, this.tempCameraSpacePos);
      this.tempCameraSpacePos.y = -this.tempCameraSpacePos.y;
      this.tempCameraSpacePos.z = -this.tempCameraSpacePos.z;
      var1.getScreenPosition(this.tempPos);
      this.screenProjectionX = this.tempPos.x;
      this.screenProjectionY = this.tempPos.y;
      this.inViewFrustum = this.screenProjectionX <= GlobalStatus.screenWidth && this.screenProjectionX >= 0 && this.screenProjectionY <= GlobalStatus.screenHeight && this.screenProjectionY >= 0;
      if (this.tempCameraSpacePos.z < 0 || !this.inViewFrustum) {
         AEVector3D var4 = this.tempCameraSpacePos;
         int var3 = this.screenProjectionY;
         int var9 = this.screenProjectionX;
         float var5 = (float)(this.elipsoidCenterX - var9);
         float var6 = (float)(this.elipsoidCenterY + -10 - var3);
         float var8;
         if ((var8 = var5 * var5 / (float)this.elipsoidWidth + var6 * var6 / (float)this.elipsoidHeight) >= 0.0F) {
            float var7 = (float)AEMath.sqrt((long)(var8 * 4096.0F)) / 4096.0F;
            var8 = (var8 - var7) / var8;
            if (0.0F <= var8 && var8 <= 1.0F) {
               var4.x = (int)((float)var9 + var5 * var8);
               var4.y = (int)((float)var3 + var6 * var8);
            }
         }

         this.tempCameraSpacePos = var4;
         this.screenProjectionX = this.tempCameraSpacePos.x;
         this.screenProjectionY = this.tempCameraSpacePos.y;
      }

   }

   private void elipsoidIntersect(Camera var1, KIPlayer var2) {
      this.elipsoidIntersect(var1, var2.getPosition(this.tempPos));
   }

   public final void draw(Player var1, Camera var2, TargetFollowCamera var3, Hud var4, int var5) {
      if (!this.draw) {
         KIPlayer[] var23;
         if ((var23 = this.level.getEnemies()) != null) {
            for(int var24 = 0; var24 < var23.length; ++var24) {
               var23[var24].withinRenderDistance = true;
            }
         }

      } else {
         Mission var6;
         boolean var7 = !(var6 = Status.getMission()).isEmpty() && var6.getType() != 11 && var6.getType() != 0;
         boolean var22 = this.level.getPlayer().isLookingBack();
         boolean var8 = Status.inAlienOrbit();
         this.enemies = this.level.getEnemies();
         this.landmarks = this.level.getLandmarks();
         this.playerRoute = this.level.getPlayerRoute();
         this.asteroids = this.level.getAsteroids();
         this.planets = this.level.getStarSystem().getPlanets();
         if (!var8) {
            this.onPlanetCourse_ = this.level.getPlayer().goingToPlanet() && !this.level.getPlayer().isDockingToPlanet() && this.level.getPlayer().getAutoPilotTarget() == this.level.getStarSystem().getPlanetTargets()[Status.getSystem().getStationEnumIndex(Status.getSystem().getStations()[this.targetedPlanetLocalIndex])];
         } else {
            this.onPlanetCourse_ = this.level.getPlayer().goingToPlanet() && !this.level.getPlayer().isDockingToPlanet();
         }

         if (this.playerRoute != null && this.playerRoute.getDockingTarget_() != null) {
            this.elipsoidIntersect(var2, (KIPlayer)this.playerRoute.getDockingTarget_());
            this.closestStatPlayerDistanceVisible = this.calcDistance(this.playerRoute.getDockingTarget_().x, this.playerRoute.getDockingTarget_().y, this.playerRoute.getDockingTarget_().z, var1);
            if (this.inViewFrustum) {
               GlobalStatus.graphics.drawImage(this.lockonWaypoint, this.screenProjectionX, this.screenProjectionY, 3);
               Font.drawString(this.closestStatPlayerDistanceVisible, this.screenProjectionX - 8, this.screenProjectionY + 8, 1);
            } else if (this.scanerPresent) {
               GlobalStatus.graphics.drawImage(this.waypoint, this.screenProjectionX, this.screenProjectionY, 3);
            }
         }

         boolean var9 = this.targetedAsteroid != null || this.tractorBeamTarget != null || this.targetedPlanet != null;
         boolean var10 = false;
         int var12;
         int var13;
         if (this.landmarks != null) {
            for(int var11 = 0; var11 < this.landmarks.length; ++var11) {
               if (var11 != 2 && this.landmarks[var11] != null && this.landmarks[var11].isVisible()) {
                  this.elipsoidIntersect(var2, this.landmarks[var11]);
                  if (this.inViewFrustum) {
                     if (!var9 && !var10 && !var22 && var11 != 3) {
                        var12 = Crosshair.screenPos.x - 30;
                        var13 = Crosshair.screenPos.y - 30;
                        if (this.screenProjectionX > var12 && this.screenProjectionX < var12 + 60 && this.screenProjectionY > var13 && this.screenProjectionY < var13 + 60) {
                           if (this.contextStation != this.landmarks[var11]) {
                              this.landmarkScanPassedTime = 0;
                           }

                           this.contextStation = this.landmarks[var11];
                           var10 = true;
                        }
                     }

                     this.playerPos = var1.getPosition(this.playerPos);
                     this.tempContextPosition = this.landmarks[var11].player.getPosition(this.tempContextPosition);
                     if (var11 != 3) {
                        GlobalStatus.graphics.drawImage(this.lockonWaypoint2, this.screenProjectionX, this.screenProjectionY, 3);
                     }

                     Font.drawString(this.stationaryPlayersNames[var11], var11 == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5, this.screenProjectionY, 0);
                     if (var11 < 2) {
                        if (var11 == 0 && !var8) {
                           Font.drawString(GlobalStatus.gameText.getText(37) + ": " + Status.getStation().getTecLevel(), var11 == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5, this.screenProjectionY + 10, 1);
                           this.closestStatPlayerDistanceVisible = this.calcDistance(this.landmarks[var11].targetX, this.landmarks[var11].targetY, this.landmarks[var11].targetZ, var1);
                           Font.drawString(this.closestStatPlayerDistanceVisible, var11 == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5, this.screenProjectionY + 20, 1);
                        } else {
                           this.closestStatPlayerDistanceVisible = this.calcDistance(this.landmarks[var11].targetX, this.landmarks[var11].targetY, this.landmarks[var11].targetZ, var1);
                           Font.drawString(this.closestStatPlayerDistanceVisible, var11 == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5, this.screenProjectionY + 10, 1);
                        }
                     }
                  } else if (var11 == 3) {
                     GlobalStatus.graphics.drawImage(this.vortex, this.screenProjectionX, this.screenProjectionY, 3);
                  }
               }
            }

            if (!var22) {
               if (this.targetedStation != null) {
                  if (this.targetedStation.isDead()) {
                     this.targetedStation = null;
                  }

                  this.targetedStation = null;
               }

               if (var10 && !this.level.getPlayer().isDockingToAsteroid() && !this.level.getPlayer().isAutoPilot()) {
                  this.landmarkScanPassedTime += var5;
                  if (this.landmarkScanPassedTime > this.scanTime) {
                     this.targetedStation = this.contextStation;
                  }

                  if (this.landmarkScanPassedTime > 0) {
                     if (this.contextStation != null && !this.contextStation.equals(this.targetedStation)) {
                        this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.landmarkScanPassedTime / (float)this.scanTime)));
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GlobalStatus.graphics);
                     } else if (Layout.quickTickHigh_()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GlobalStatus.graphics);
                     }
                  }
               } else {
                  if (this.targetedStation == null) {
                     this.contextStation = null;
                  }

                  this.landmarkScanPassedTime = 0;
               }
            }
         }

         var9 = this.targetedAsteroid != null || this.tractorBeamTarget != null || this.targetedStation != null;
         boolean var28 = false;
         boolean var14;
         boolean var15;
         int var18;
         int var19;
         if (this.planets != null) {
            for(var12 = 1; var12 < this.planets.length; ++var12) {
               this.elipsoidIntersect(var2, this.planets[var12].getLocalPos(this.tempPos));
               if (this.inViewFrustum) {
                  int[] var30;
                  var8 = (var30 = Status.getSystem().getStations())[var12 - 1] == Status.getStation().getId();
                  var14 = false;
                  if (Status.getSystem().getJumpGateEnumIndex() == var12 - 1 && !var8) {
                     GlobalStatus.graphics.drawImage(this.mapJumpgate, this.screenProjectionX + 10, this.screenProjectionY - 10, 0);
                     var14 = true;
                  }

                  var15 = false;
                  Mission var16 = Status.getCampaignMission();
                  Mission var17 = Status.getFreelanceMission();
                  if (var16 != null && !var16.isEmpty() && var30[var12 - 1] == var16.setCampaignMission() && !var8) {
                     if (this.mainMission == null) {
                        this.mainMission = AEFile.loadCryptedImage("/data/interface/menu_map_mainmission.png");
                     }

                     GlobalStatus.graphics.drawImage(this.mainMission, this.screenProjectionX + 10 + (var14 ? 14 : 0), this.screenProjectionY - 10, 0);
                     var15 = true;
                  }

                  if (var17 != null && !var17.isEmpty() && var30[var12 - 1] == var17.setCampaignMission() && !var8) {
                     if (this.sideMission == null) {
                        this.sideMission = AEFile.loadCryptedImage("/data/interface/menu_map_sidemission.png");
                     }

                     GlobalStatus.graphics.drawImage(this.sideMission, this.screenProjectionX + 10 + (var14 ? 14 : 0), this.screenProjectionY - 10, 0);
                     var15 = true;
                  }

                  if (!var9 && !var28 && !var22) {
                     var18 = StarSystem.currentPlanetEnumIndex == var12 ? 100 : 30;
                     var19 = Crosshair.screenPos.x - (var18 >> 1);
                     int var20 = Crosshair.screenPos.y - (var18 >> 1);
                     if (this.screenProjectionX > var19 && this.screenProjectionX < var19 + var18 && this.screenProjectionY > var20 && this.screenProjectionY < var20 + var18) {
                        if (var12 != StarSystem.currentPlanetEnumIndex) {
                           if (this.contextPlanet != this.planets[var12]) {
                              this.planetScanPassedTime = 0;
                           }

                           this.targetedPlanetLocalIndex = var12 - 1;
                           this.contextPlanet = this.planets[var12];
                           var28 = true;
                        }

                        Font.drawString(Status.getPlanetNames()[var12 - 1], this.screenProjectionX + 10 + (var14 ? 14 : 0) + (var15 ? 14 : 0), this.screenProjectionY - 10, 0);
                     }
                  }
               }
            }

            if (!var10 && !var22) {
               if (this.targetedPlanet != null) {
                  this.targetedPlanet = null;
               }

               if (var28 && !this.level.getPlayer().isDockingToAsteroid()) {
                  this.planetScanPassedTime += var5;
                  if (this.planetScanPassedTime > this.scanTime) {
                     if (var7) {
                        var4.hudEvent(21, this.level.getPlayer());
                     } else {
                        this.targetedPlanet = this.contextPlanet;
                        if (this.onPlanetCourse_) {
                           this.level.getPlayer().stopPlanetDock_(var3);
                        }
                     }
                  }

                  if (this.planetScanPassedTime > 0 && !var7) {
                     if (this.contextPlanet != null && !this.contextPlanet.equals(this.targetedPlanet)) {
                        this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.planetScanPassedTime / (float)this.scanTime)));
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GlobalStatus.graphics);
                     } else if (!this.level.getPlayer().isDockingToPlanet() && Layout.quickTickHigh_()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GlobalStatus.graphics);
                     }
                  }
               } else {
                  if (this.targetedPlanet == null) {
                     this.contextPlanet = null;
                  }

                  this.planetScanPassedTime = 0;
               }
            }
         }

         if (this.targetedPlayer != null && (this.targetedPlayer.isDying() || this.targetedPlayer.isDead())) {
            this.targetedPlayer = null;
            var9 = false;
            this.contextShip = null;
         }

         boolean var29 = false;
         int var36;
         int var37;
         if (this.scanerPresent && this.enemies != null) {
            for(var13 = 0; var13 < this.enemies.length; ++var13) {
               KIPlayer var25;
               if ((var25 = this.enemies[var13]).player.isActive() && (!var25.isDying() || var25.hasCargo)) {
                  this.elipsoidIntersect(var2, var25);
                  var14 = var25.hasCargo && (var25.isDead() || var25.isDying());
                  if (!var25.isDead() && !var25.isDying() || var25.hasCargo) {
                     var15 = var25.equals(this.targetedPlayer);
                     Gun[] var33;
                     if (!var25.isDead() && this.level.getPlayer().player.hasGunOfType(1) && (var33 = this.level.getPlayer().player.guns[1]) != null) {
                        GlobalStatus.graphics.setStrokeStyle(1);

                        for(var37 = 0; var37 < var33.length; ++var37) {
                           Gun var40;
                           if ((var40 = var33[var37]) != null && (var40.subType == 7 || var40.subType == 6) && var40.inAir) {
                              this.playerPos.set(var40.projectilesPos[0]);
                              this.tempContextPosition = var25.getPosition(this.tempContextPosition);
                              this.tempContextPosition.subtract(this.playerPos);
                              if ((var19 = this.tempContextPosition.getLength()) < var40.getMagnitude()) {
                                 float var41;
                                 if ((var41 = (float)var19 / (float)var40.getMagnitude()) > 1.0F) {
                                    var41 = 1.0F;
                                 }

                                 int var21 = -16777216 | (int)(var41 * 255.0F) << 16 | (int)((1.0F - var41) * 255.0F) << 8;
                                 GlobalStatus.graphics.setColor(var21);
                                 GlobalStatus.renderer.getCamera().getScreenPosition(this.playerPos);
                                 this.tempContextPosition.x = this.screenProjectionX;
                                 this.tempContextPosition.y = this.screenProjectionY;
                                 this.tempContextPosition.z = 0;
                                 this.tempContextPosition.subtract(this.playerPos);
                                 this.tempContextPosition.z = 0;
                                 AEVector3D var10000 = this.tempContextPosition;
                                 var10000.x <<= 12;
                                 var10000 = this.tempContextPosition;
                                 var10000.y <<= 12;
                                 this.tempContextPosition.normalize();
                                 this.tempContextPosition.scale(16);
                                 this.playerPos.add(this.tempContextPosition);
                                 GlobalStatus.graphics.drawLine(this.screenProjectionX, this.screenProjectionY, this.playerPos.x, this.playerPos.y);
                              }
                           }
                        }

                        GlobalStatus.graphics.setStrokeStyle(0);
                     }

                     if (!var25.isAsteroid && !var25.junk && !var14 && var25.player.enemy) {
                        ++this.activeEnemyEnemiesCount;
                     }

                     Image var35;
                     if (!this.inViewFrustum) {
                        if (var14) {
                           GlobalStatus.graphics.drawImage(this.radarIconNeutral, this.screenProjectionX, this.screenProjectionY, 3);
                           GlobalStatus.graphics.drawImage(this.enemies[var13].junk ? this.spacejunk : (this.enemies[var13].race == 9 ? this.crateVoid : this.crate), this.screenProjectionX, this.screenProjectionY, 3);
                        } else {
                           var35 = var25.player.enemy ? (var15 ? this.radarIconEnemyLock : this.radarIconEnemy) : (var25.player.friend ? (var15 ? this.radarIconFriendLock : this.hudRadarIconFriend) : (var15 ? this.radarIconNeutralLock : this.radarIconNeutral2));
                           GlobalStatus.graphics.drawImage(var35, this.screenProjectionX, this.screenProjectionY, 3);
                        }
                     } else {
                        this.playerPos = var1.getPosition(this.playerPos);
                        this.tempContextPosition = var25.player.getPosition(this.tempContextPosition);
                        if (this.playerPos.x - this.tempContextPosition.x <= 24000 && this.playerPos.x - this.tempContextPosition.x >= -24000 && this.playerPos.y - this.tempContextPosition.y <= 24000 && this.playerPos.y - this.tempContextPosition.y >= -24000 && this.playerPos.z - this.tempContextPosition.z <= 24000 && this.playerPos.z - this.tempContextPosition.z >= -24000 && var25.getId_() != 9996) {
                           label862: {
                              var25.withinRenderDistance = true;
                              Image var10001;
                              Graphics var42;
                              if (var14) {
                                 var42 = GlobalStatus.graphics;
                                 var10001 = this.bracketBox;
                              } else {
                                 var36 = var25.player.enemy ? 0 : (var25.player.friend ? 6 : 4);
                                 this.enemyArmorBarHeight = (int)((float)var25.player.getDamageRate() / 100.0F * 16.0F);
                                 this.bars.setFrame(var36 + 1);
                                 this.bars.setPosition(this.screenProjectionX + 8 + 2, this.screenProjectionY - 8);
                                 this.bars.paint(GlobalStatus.graphics);
                                 this.bars.setFrame(var36);
                                 GlobalStatus.graphics.setClip(0, this.screenProjectionY - 8 + 16 - this.enemyArmorBarHeight, GlobalStatus.screenWidth, this.enemyArmorBarHeight);
                                 this.bars.paint(GlobalStatus.graphics);
                                 GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
                                 this.enemyArmorBarHeight = (int)((float)var25.player.getEmpDamageRate() / 100.0F * 16.0F);
                                 this.bars.setFrame(3);
                                 this.bars.setPosition(this.screenProjectionX + 8 + 7, this.screenProjectionY - 8);
                                 this.bars.paint(GlobalStatus.graphics);
                                 this.bars.setFrame(2);
                                 GlobalStatus.graphics.setClip(0, this.screenProjectionY - 8 + 16 - this.enemyArmorBarHeight, GlobalStatus.screenWidth, this.enemyArmorBarHeight);
                                 this.bars.paint(GlobalStatus.graphics);
                                 GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
                                 if (!var15) {
                                    break label862;
                                 }

                                 var42 = GlobalStatus.graphics;
                                 var10001 = var25.player.enemy ? this.lockonEnemy : (var25.player.friend ? this.hudLockOnFriend : this.lockonNeutral);
                              }

                              var42.drawImage(var10001, this.screenProjectionX, this.screenProjectionY, 3);
                           }
                        } else {
                           var35 = var14 ? this.bracketBox : (var15 ? (var25.player.enemy ? this.bracketEnemyFar : (var25.player.friend ? this.bracketFriendFar : this.lockonNeutral)) : (var25.player.enemy ? this.radarIconEnemy : (var25.player.friend ? this.hudRadarIconFriend : this.radarIconNeutral2)));
                           GlobalStatus.graphics.drawImage(var35, this.screenProjectionX, this.screenProjectionY, 3);
                           var25.withinRenderDistance = false;
                        }

                        if (!this.level.getPlayer().isAutoPilot() && (!var25.junk || var14) && (!var29 || var25.stunned) && (var14 && var25.isDead() || !var14)) {
                           if (var14 && this.tractorBeamTarget == null && this.tractorBeamAutomatic) {
                              this.tractorBeamTarget = var25;
                              var29 = true;
                              this.contextShip = var25;
                           } else {
                              var36 = Crosshair.screenPos.x - 30;
                              var37 = Crosshair.screenPos.y - 30;
                              if (this.screenProjectionX > var36 && this.screenProjectionX < var36 + 60 && this.screenProjectionY > var37 && this.screenProjectionY < var37 + 60 && (var14 && this.tractorBeamPresent || this.pastIntro && !this.onPlanetCourse_)) {
                                 if (this.contextShip != var25) {
                                    this.enemyScanPassedTime = 0;
                                 }

                                 this.contextShip = var25;
                                 var29 = true;
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (!this.level.getPlayer().isDockingToAsteroid() && !var22 && !var9) {
               boolean var31 = this.contextShip != null && this.contextShip.hasCargo && (this.contextShip.isDead() || this.contextShip.isDying());
               if (this.targetedPlayer != null && (this.targetedPlayer.isDying() || this.targetedPlayer.isDead())) {
                  this.targetedPlayer = null;
               }

               if (var29) {
                  this.enemyScanPassedTime += var5;
                  if (this.enemyScanPassedTime > (!var31 && !this.contextShip.stunned ? this.scanTime : this.tractorBeamScanTime)) {
                     if (!var31 && !this.contextShip.stunned) {
                        if (this.targetedPlayer != this.contextShip) {
                           this.targetedPlayer = this.contextShip;
                           if (this.showCargo) {
                              this.playerPos = var1.getPosition(this.playerPos);
                              this.tempContextPosition = this.targetedPlayer.player.getPosition(this.tempContextPosition);
                              if (this.playerPos.x - this.tempContextPosition.x < 24000 || this.playerPos.x - this.tempContextPosition.x > -24000 || this.playerPos.y - this.tempContextPosition.y < 24000 || this.playerPos.y - this.tempContextPosition.y > -24000 || this.playerPos.z - this.tempContextPosition.z < 24000 || this.playerPos.z - this.tempContextPosition.z > -24000) {
                                 int[] var26;
                                 if ((var26 = this.targetedPlayer.cargo) != null) {
                                    for(int var32 = 0; var32 < var26.length; var32 += 2) {
                                       if (var26[var32 + 1] > 0) {
                                          var4.catchCargo(var26[0], 1, false, false, false, true);
                                          break;
                                       }
                                    }
                                 } else {
                                    var4.hudEvent(22, this.level.getPlayer());
                                 }
                              }
                           }
                        }
                     } else {
                        this.tractorBeamTarget = this.contextShip;
                        if (!this.tractorBeamPresent) {
                           var4.hudEvent(9, this.level.getPlayer());
                           this.tractorBeamTarget = null;
                        }
                     }

                     this.enemyScanPassedTime = 0;
                  }

                  if (this.enemyScanPassedTime > (!var31 && !this.contextShip.stunned ? 0 : 500) && this.contextShip != null && !this.contextShip.equals(!var31 && !this.contextShip.stunned ? this.targetedPlayer : this.tractorBeamTarget)) {
                     this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)(this.enemyScanPassedTime - (!var31 && !this.contextShip.stunned ? 0 : 500)) / (float)(!var31 && !this.contextShip.stunned ? this.scanTime : this.tractorBeamScanTime - 500))));
                     this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                     this.scanProcessAnim.paint(GlobalStatus.graphics);
                  }
               } else {
                  if (var31 && this.tractorBeamTarget == null || !var31 && this.targetedPlayer == null) {
                     this.contextShip = null;
                  }

                  this.enemyScanPassedTime = 0;
               }
            }
         }

         var13 = 0;

         for(int var27 = 0; var27 < this.asteroidsInFront.length; ++var27) {
            this.asteroidsInFront[var27] = -1;
         }

         var9 = this.contextShip != null && this.targetedPlayer == null || this.tractorBeamTarget != null || this.targetedStation != null || this.targetedPlanet != null;
         var8 = false;
         if (!this.level.getPlayer().isLockedOnAsteroid_() && this.asteroids != null) {
            var14 = false;
            if (this.showAasteroids) {
               var14 = this.level.isInAsteroidCenterRange(this.level.getPlayer().getPosition());
            }

            int var34;
            boolean var39;
            for(var34 = 0; var34 < this.asteroids.length; ++var34) {
               KIPlayer var38;
               if ((var38 = this.asteroids[var34]).hasCargo && (var38.isDead() || var38.isDying()) || !var38.isDead() && !var38.isDying()) {
                  this.elipsoidIntersect(var2, var38);
                  var39 = var38.hasCargo && (var38.isDead() || var38.isDying());
                  if (!this.inViewFrustum) {
                     if (var39) {
                        GlobalStatus.graphics.drawImage(this.radarIconNeutral, this.screenProjectionX, this.screenProjectionY, 3);
                        GlobalStatus.graphics.drawImage(this.asteroid, this.screenProjectionX, this.screenProjectionY, 3);
                     }
                  } else {
                     if (!var9 && !var29 && !var8 && !var22 && ((PlayerAsteroid)var38).getMass_SizeCoef__() > 15 && !this.onPlanetCourse_) {
                        if (var39 && this.tractorBeamTarget == null && this.tractorBeamAutomatic) {
                           this.tractorBeamTarget = var38;
                           var8 = true;
                           this.contextShip = var38;
                        } else {
                           var18 = Crosshair.screenPos.x - 20;
                           var19 = Crosshair.screenPos.y - 20;
                           if (this.screenProjectionX > var18 && this.screenProjectionX < var18 + 40 && this.screenProjectionY > var19 && this.screenProjectionY < var19 + 40 && !((PlayerAsteroid)var38).clampedByDistance && this.pastIntro && var13 < 4) {
                              this.asteroidsInFront[var13] = var34;
                              ++var13;
                           }
                        }
                     }

                     if (var14 && (var18 = ((PlayerAsteroid)var38).getQualityFrameIndex()) == 0) {
                        this.meteorClass.setFrame(var18);
                        this.meteorClass.setRefPixelPosition(this.screenProjectionX, this.screenProjectionY);
                        this.meteorClass.paint(GlobalStatus.graphics);
                     }

                     if (var39) {
                        GlobalStatus.graphics.drawImage(this.bracketBox, this.screenProjectionX, this.screenProjectionY, 3);
                     }
                  }
               }
            }

            var34 = 999999;
            var36 = -1;

            for(var37 = 0; var37 < 5; ++var37) {
               if ((var18 = this.asteroidsInFront[var37]) >= 0) {
                  this.tempCameraSpacePos = this.asteroids[var18].getPosition(this.tempCameraSpacePos);
                  this.tempPos = var1.getPosition(this.tempPos);
                  this.tempCameraSpacePos.subtract(this.tempPos);
                  if ((var19 = this.tempCameraSpacePos.getLength()) < var34) {
                     var34 = var19;
                     var36 = var18;
                  }
               }
            }

            if (var36 >= 0) {
               if (this.contextAsteroid != this.asteroids[var36]) {
                  this.asteroidScanPassedTime = 0;
               }

               this.contextAsteroid = this.asteroids[var36];
               var8 = true;
            }

            if (!var29 && !var22) {
               if (this.targetedAsteroid != null) {
                  if (this.targetedAsteroid.isDead()) {
                     this.targetedAsteroid = null;
                  }

                  this.targetedAsteroid = null;
               }

               var39 = this.contextAsteroid != null && this.contextAsteroid.hasCargo && (this.contextAsteroid.isDead() || this.contextAsteroid.isDying());
               if (var8 && !this.level.getPlayer().isDockingToAsteroid()) {
                  this.asteroidScanPassedTime += var5;
                  if (this.asteroidScanPassedTime > this.scanTime - 200) {
                     if (var39) {
                        if (!this.tractorBeamPresent) {
                           var4.hudEvent(9, this.level.getPlayer());
                        } else {
                           this.tractorBeamTarget = this.contextAsteroid;
                        }
                     } else if (!this.drillPresent) {
                        var4.hudEvent(20, this.level.getPlayer());
                     } else {
                        this.targetedAsteroid = this.contextAsteroid;
                     }
                  }

                  if (this.asteroidScanPassedTime > 500) {
                     if (this.contextAsteroid != null && !this.contextAsteroid.equals(var39 ? this.tractorBeamTarget : this.targetedAsteroid)) {
                        if ((var18 = (int)((float)(this.scanProcessAnim.getRawFrameCount() - 1) * ((float)(this.asteroidScanPassedTime - 500) / (float)(this.scanTime - 500)))) < this.scanProcessAnim.getRawFrameCount() - 1) {
                           this.scanProcessAnim.setFrame(var18);
                           this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                           this.scanProcessAnim.paint(GlobalStatus.graphics);
                        }
                     } else if (Layout.quickTickHigh_()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GlobalStatus.graphics);
                     }
                  }
               } else {
                  if (var39 && this.tractorBeamTarget == null || !var39 && this.targetedAsteroid == null) {
                     this.contextAsteroid = null;
                  }

                  this.asteroidScanPassedTime = 0;
               }
            }
         }

         if (this.activeEnemyEnemiesCount > 0) {
            if (GlobalStatus.currentMusic != 3) {
               GlobalStatus.soundManager.playMusic(3);
            }
         } else if (GlobalStatus.currentMusic != 4 && GlobalStatus.currentMusic != 0) {
            GlobalStatus.soundManager.playMusic(4);
         }

         this.activeEnemyEnemiesCount = 0;
      }
   }

   public final int getPlanetDockIndex() {
      return Status.getSystem().getStations()[this.targetedPlanetLocalIndex];
   }

   public final void drawCurrentLock(Hud var1) {
      if (this.draw) {
         if (this.targetedPlanet != null) {
            Font.drawString(Status.getPlanetNames()[this.targetedPlanetLocalIndex], GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2, 1, 18);
         } else {
            KIPlayer var2;
            if ((var2 = this.targetedAsteroid != null ? this.targetedAsteroid : (this.targetedPlayer != null ? this.targetedPlayer : (this.targetedStation != null ? this.targetedStation : null))) != null) {
               boolean var3 = var2.equals(this.targetedAsteroid);
               boolean var4 = var2.equals(this.targetedStation);
               String var5 = this.targetedStation == this.landmarks[0] ? this.stationaryPlayersNames[0] : (this.targetedStation == this.landmarks[3] ? this.stationaryPlayersNames[3] : this.stationaryPlayersNames[1]);
               if (var3) {
                  ImageFactory.drawItemFrameless(((PlayerAsteroid)var2).oreItemId, var1.items, GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2, 40);
                  this.meteorClass.setFrame(((PlayerAsteroid)var2).getQualityFrameIndex());
                  this.meteorClass.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - 2);
                  this.meteorClass.paint(GlobalStatus.graphics);
                  Font.drawString(GlobalStatus.gameText.getText(569 + ((PlayerAsteroid)var2).oreItemId), GlobalStatus.screenWidth - 4 - this.meteorClass.getHeight(), GlobalStatus.screenHeight - Font.getFontSpacingY() - 2, 1, 18);
                  return;
               }

               if (!var4) {
                  this.logosSmall.setFrame(var2.race);
                  this.logosSmall.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2);
                  this.logosSmall.paint(GlobalStatus.graphics);
               }

               if (var2.name != null && var2.name.equals(GlobalStatus.gameText.getText(833))) {
                  if (Layout.quickTickHigh_()) {
                     Font.drawString(var2.name, GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2, 2, 18);
                     return;
                  }
               } else {
                  String var6 = var4 ? var5 : (var2.name != null ? var2.name : GlobalStatus.gameText.getText(229 + var2.race));
                  if (!var4) {
                     var6 = var6 + " " + var2.player.getDamageRate() + "%";
                  } else {
                     if (Status.inAlienOrbit()) {
                        this.logosSmall.setFrame(9);
                     } else {
                        this.logosSmall.setFrame(Status.getSystem().getRace());
                     }

                     this.logosSmall.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2);
                     this.logosSmall.paint(GlobalStatus.graphics);
                  }

                  Font.drawString(var6, GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getFontSpacingY() - 2, 1, 18);
               }
            }

         }
      }
   }

   private String calcDistance(int var1, int var2, int var3, Player var4) {
      this.playerPos = var4.getPosition(this.playerPos);
      this.floatVector.x = (float)(this.playerPos.x >> 1);
      this.floatVector.y = (float)(this.playerPos.y >> 1);
      this.floatVector.z = (float)(this.playerPos.z >> 1);
      var1 >>= 1;
      var2 >>= 1;
      var3 >>= 1;
      float var6 = ((this.floatVector.x - (float)var1) * (this.floatVector.x - (float)var1) + (this.floatVector.y - (float)var2) * (this.floatVector.y - (float)var2) + (this.floatVector.z - (float)var3) * (this.floatVector.z - (float)var3)) / 4096.0F;
      float var7 = 65536.0F;
      float var9 = 65536.0F;

      for(int var5 = 0; var5 < 20; ++var5) {
         var7 *= 0.5F;
         float var10;
         if ((var10 = var6 - var9 * var9) > 0.0F) {
            var9 += var7;
         } else if (var10 < 0.0F) {
            var9 -= var7;
         }
      }

      var1 = (var1 = (int)var9) * 12;
      String var8 = var1 + "m";
      if (var1 >= 1000) {
         if ((var2 = var1 % 1000) >= 100) {
            var8 = "" + var2;
         } else {
            var8 = "0";
         }

         var8 = var8.substring(0, 1);
         var8 = var1 / 1000 + "." + var8 + "km";
      }

      return var8;
   }

   public final KIPlayer getLockedAsteroid() {
      return this.targetedAsteroid;
   }

   public final void unlockAsteroid() {
      this.targetedAsteroid = null;
   }

   public final KIPlayer getLockedEnemy() {
      return this.targetedPlayer;
   }
}
