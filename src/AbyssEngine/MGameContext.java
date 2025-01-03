package AbyssEngine;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class MGameContext {
   private int[] var_31;
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
   private Image var_741;
   private Image var_781;
   private Sprite logosSmall;
   private Sprite scanProcessAnim;
   private Sprite meteorClass;
   private Sprite bars;
   private int var_8a8;
   private int var_8f0;
   private int var_974;
   private int var_99a;
   private int var_9c8;
   private int var_a1c;
   private boolean var_a3e;
   private KIPlayer[] var_a55;
   private KIPlayer[] var_a71;
   private KIPlayer[] var_ad3;
   private AbstractMesh[] var_aeb;
   private Class_661 var_b26;
   private Level var_b73;
   private Matrix var_b8e = new Matrix();
   private AEVector3D var_bc4 = new AEVector3D();
   private AEVector3D var_c0c = new AEVector3D();
   private AEVector3D var_c50 = new AEVector3D();
   private AEVector3D var_cad = new AEVector3D();
   private Vec3f floatVector = new Vec3f();
   private int var_d13;
   private String[] stationaryPlayersNames = new String[4];
   private String closestStatPlayerDistanceVisible;
   private int var_de6;
   private int var_e11;
   private int var_e35;
   private int var_e89;
   public KIPlayer contextShip;
   private KIPlayer targetedPlayer;
   private KIPlayer contextAsteroid;
   private KIPlayer var_f73;
   private AbstractMesh var_f8d;
   public AbstractMesh var_fd4;
   public KIPlayer var_fdf;
   public KIPlayer contextStation;
   public KIPlayer targetedStation;
   public boolean var_10b4;
   private boolean var_10e1;
   private int var_115c;
   private boolean scanCargo;
   private boolean scanAtypeAsteroids;
   private boolean scanerPresent;
   private boolean drillPresent;
   private boolean tractorBeamPresent;
   private boolean var_128a;
   private boolean pastIntro;
   private int var_1316;
   private int scanTime;
   private int var_1381;

   public MGameContext(Level var1) {
      this.var_b73 = var1;
      this.var_974 = GameStatus.screenWidth / 2;
      this.var_99a = GameStatus.screenHeight / 2;
      this.var_9c8 = (this.var_974 - 5) * (this.var_974 - 5);
      this.var_a1c = (this.var_99a - 20) * (this.var_99a - 20);
      this.stationaryPlayersNames[0] = (Status.inAlienOrbit() ? GameStatus.langManager.getLangString(238) : Status.getStation().getName()) + " " + GameStatus.langManager.getLangString(40);
      this.stationaryPlayersNames[1] = GameStatus.langManager.getLangString(271);
      this.stationaryPlayersNames[2] = "";
      this.stationaryPlayersNames[3] = GameStatus.langManager.getLangString(269);
      AbstractMesh[] var5;
      if ((var5 = var1.sub_b85()) != null) {
         for(int var2 = 0; var2 < var5.length; ++var2) {
            if (var5[var2] instanceof RocketGun) {
               ((RocketGun)var5[var2]).sub_8a(this);
            }
         }
      }

      try {
         String var6 = "/data/interface/hud_lockon_neutral.png";
         this.lockonNeutral = AEImage.loadImage("/data/interface/hud_lockon_neutral.png", true);
         var6 = "/data/interface/hud_radaricon_neutral.png";
         this.radarIconNeutral2 = AEImage.loadImage("/data/interface/hud_radaricon_neutral.png", true);
         var6 = "/data/interface/hud_radaricon_neutral_lock.png";
         this.radarIconNeutralLock = AEImage.loadImage("/data/interface/hud_radaricon_neutral_lock.png", true);
         var6 = "/data/interface/hud_lockon_enemy.png";
         this.lockonEnemy = AEImage.loadImage("/data/interface/hud_lockon_enemy.png", true);
         var6 = "/data/interface/hud_radaricon_enemy.png";
         this.radarIconEnemy = AEImage.loadImage("/data/interface/hud_radaricon_enemy.png", true);
         var6 = "/data/interface/hud_radaricon_enemy_lock.png";
         this.radarIconEnemyLock = AEImage.loadImage("/data/interface/hud_radaricon_enemy_lock.png", true);
         var6 = "/data/interface/hud_lockon_friend.png";
         this.hudLockOnFriend = AEImage.loadImage("/data/interface/hud_lockon_friend.png", true);
         var6 = "/data/interface/hud_radaricon_friend.png";
         this.hudRadarIconFriend = AEImage.loadImage("/data/interface/hud_radaricon_friend.png", true);
         var6 = "/data/interface/hud_radaricon_friend_lock.png";
         this.radarIconFriendLock = AEImage.loadImage("/data/interface/hud_radaricon_friend_lock.png", true);
         var6 = "/data/interface/hud_lockon_waypoint.png";
         this.lockonWaypoint = AEImage.loadImage("/data/interface/hud_lockon_waypoint.png", true);
         var6 = "/data/interface/hud_lockon_waypoint.png";
         this.lockonWaypoint2 = AEImage.loadImage("/data/interface/hud_lockon_waypoint.png", true);
         var6 = "/data/interface/hud_radaricon_neutral.png";
         this.radarIconNeutral = AEImage.loadImage("/data/interface/hud_radaricon_neutral.png", true);
         var6 = "/data/interface/hud_bars.png";
         Image var7 = AEImage.loadImage("/data/interface/hud_bars.png", true);
         this.bars = new Sprite(var7, 2, var7.getHeight());
         var6 = "/data/interface/logos_small.png";
         Image var9 = AEImage.loadImage("/data/interface/logos_small.png", true);
         this.logosSmall = new Sprite(var9, var9.getHeight(), var9.getHeight());
         this.logosSmall.defineReferencePixel(var9.getHeight(), var9.getHeight());
         var6 = "/data/interface/bracket_enemy_far.png";
         this.bracketEnemyFar = AEImage.loadImage("/data/interface/bracket_enemy_far.png", true);
         var6 = "/data/interface/bracket_friend_far.png";
         this.bracketFriendFar = AEImage.loadImage("/data/interface/bracket_friend_far.png", true);
         var6 = "/data/interface/bracket_box.png";
         this.bracketBox = AEImage.loadImage("/data/interface/bracket_box.png", true);
         var6 = "/data/interface/hud_crate.png";
         this.crate = AEImage.loadImage("/data/interface/hud_crate.png", true);
         var6 = "/data/interface/hud_spacejunk.png";
         this.spacejunk = AEImage.loadImage("/data/interface/hud_spacejunk.png", true);
         var6 = "/data/interface/hud_asteroid.png";
         this.asteroid = AEImage.loadImage("/data/interface/hud_asteroid.png", true);
         var6 = "/data/interface/hud_crate_void.png";
         this.crateVoid = AEImage.loadImage("/data/interface/hud_crate_void.png", true);
         var6 = "/data/interface/hud_vortex.png";
         this.vortex = AEImage.loadImage("/data/interface/hud_vortex.png", true);
         var6 = "/data/interface/hud_waypoint.png";
         this.waypoint = AEImage.loadImage("/data/interface/hud_waypoint.png", true);
         var6 = "/data/interface/menu_map_jumpgate.png";
         this.mapJumpgate = AEImage.loadImage("/data/interface/menu_map_jumpgate.png", true);
         var6 = "/data/interface/hud_scanprocess_anim_png24.png";
         var9 = AEImage.loadImage("/data/interface/hud_scanprocess_anim_png24.png", true);
         this.scanProcessAnim = new Sprite(var9, var9.getHeight(), var9.getHeight());
         this.scanProcessAnim.defineReferencePixel(var9.getHeight() / 2, var9.getHeight() / 2);
         var6 = "/data/interface/hud_meteor_class.png";
         var9 = AEImage.loadImage("/data/interface/hud_meteor_class.png", true);
         this.meteorClass = new Sprite(var9, 11, 11);
         this.meteorClass.defineReferencePixel(11, 11);
         Item var10 = Status.getShip().getEquipedOfSubType(19);
         Item var8 = Status.getShip().getEquipedOfSubType(17);
         Item var3 = Status.getShip().getEquipedOfSubType(13);
         this.drillPresent = var10 != null;
         if (var8 != null) {
            this.scanerPresent = true;
            this.scanCargo = var8.getAttribute(29) == 1;
            this.scanAtypeAsteroids = var8.getAttribute(28) == 1;
            this.scanTime = var8.getAttribute(27);
         } else {
            this.scanerPresent = false;
            this.scanCargo = false;
            this.scanAtypeAsteroids = false;
            this.scanTime = 8000;
         }

         if (var3 != null) {
            this.tractorBeamPresent = true;
            this.var_128a = var3.getAttribute(21) == 1;
            this.var_1316 = var3.getAttribute(22);
         } else {
            this.tractorBeamPresent = false;
            this.var_1316 = 0;
            this.var_128a = false;
         }

         this.pastIntro = Status.getCurrentCampaignMission() > 0;
         this.var_1381 = 0;
         this.var_10b4 = true;
         this.var_31 = new int[5];

         for(int var11 = 0; var11 < this.var_31.length; ++var11) {
            this.var_31[var11] = -1;
         }

      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public final void sub_42() {
      this.var_a55 = null;
      this.var_a71 = null;
      this.var_b26 = null;
      this.var_b73 = null;
      this.var_c50 = null;
      this.var_cad = null;
      this.var_bc4 = null;
   }

   private void sub_85(Camera var1, AEVector3D var2) {
      this.var_c0c.set(var2);
      this.var_bc4 = var1.sub_8f0(this.var_b8e).sub_a19(this.var_c0c, this.var_bc4);
      this.var_bc4.y = -this.var_bc4.y;
      this.var_bc4.z = -this.var_bc4.z;
      var1.sub_fa(this.var_c0c);
      this.var_8a8 = this.var_c0c.x;
      this.var_8f0 = this.var_c0c.y;
      this.var_a3e = this.var_8a8 <= GameStatus.screenWidth && this.var_8a8 >= 0 && this.var_8f0 <= GameStatus.screenHeight && this.var_8f0 >= 0;
      if (this.var_bc4.z < 0 || !this.var_a3e) {
         AEVector3D var4 = this.var_bc4;
         int var3 = this.var_8f0;
         int var9 = this.var_8a8;
         float var5 = (float)(this.var_974 - var9);
         float var6 = (float)(this.var_99a + -10 - var3);
         float var8;
         if ((var8 = var5 * var5 / (float)this.var_9c8 + var6 * var6 / (float)this.var_a1c) >= 0.0F) {
            float var7 = (float)AEMath.sqrt((long)(var8 * 4096.0F)) / 4096.0F;
            var8 = (var8 - var7) / var8;
            if (0.0F <= var8 && var8 <= 1.0F) {
               var4.x = (int)((float)var9 + var5 * var8);
               var4.y = (int)((float)var3 + var6 * var8);
            }
         }

         this.var_bc4 = var4;
         this.var_8a8 = this.var_bc4.x;
         this.var_8f0 = this.var_bc4.y;
      }

   }

   private void sub_b8(Camera var1, KIPlayer var2) {
      this.sub_85(var1, var2.sub_a9(this.var_c0c));
   }

   public final void render(Player var1, Camera var2, Class_c53cameraRelated var3, Hud var4, int var5) {
      if (!this.var_10b4) {
         KIPlayer[] var23;
         if ((var23 = this.var_b73.getShips()) != null) {
            for(int var24 = 0; var24 < var23.length; ++var24) {
               var23[var24].var_efb = true;
            }
         }

      } else {
         Mission var6;
         boolean var7 = !(var6 = Status.getMission()).isEmpty() && var6.getType() != 11 && var6.getType() != 0;
         boolean var22 = this.var_b73.getPlayerEgo().isLookingBack();
         boolean var8 = Status.inAlienOrbit();
         this.var_a55 = this.var_b73.getShips();
         this.var_a71 = this.var_b73.sub_5b7();
         this.var_b26 = this.var_b73.sub_6a6();
         this.var_ad3 = this.var_b73.sub_5cf();
         this.var_aeb = this.var_b73.sub_8ce().sub_3d();
         if (!var8) {
            this.var_10e1 = this.var_b73.getPlayerEgo().sub_9f7() && !this.var_b73.getPlayerEgo().sub_f55() && this.var_b73.getPlayerEgo().sub_998() == this.var_b73.sub_8ce().getLocalPlanets()[Status.getSystem().verifyStationInSystem(Status.getSystem().getStations()[this.var_115c])];
         } else {
            this.var_10e1 = this.var_b73.getPlayerEgo().sub_9f7() && !this.var_b73.getPlayerEgo().sub_f55();
         }

         if (this.var_b26 != null && this.var_b26.sub_176() != null) {
            this.sub_b8(var2, this.var_b26.sub_176());
            this.closestStatPlayerDistanceVisible = this.sub_1ab(this.var_b26.sub_176().var_c7, this.var_b26.sub_176().var_282, this.var_b26.sub_176().var_2f9, var1);
            if (this.var_a3e) {
               GameStatus.graphics.drawImage(this.lockonWaypoint, this.var_8a8, this.var_8f0, 3);
               SymbolMapManager_.sub_102(this.closestStatPlayerDistanceVisible, this.var_8a8 - 8, this.var_8f0 + 8, 1);
            } else if (this.scanerPresent) {
               GameStatus.graphics.drawImage(this.waypoint, this.var_8a8, this.var_8f0, 3);
            }
         }

         boolean var9 = this.var_f73 != null || this.var_fdf != null || this.var_fd4 != null;
         boolean var10 = false;
         int var12;
         int var13;
         if (this.var_a71 != null) {
            for(int var11 = 0; var11 < this.var_a71.length; ++var11) {
               if (var11 != 2 && this.var_a71[var11] != null && this.var_a71[var11].sub_63()) {
                  this.sub_b8(var2, this.var_a71[var11]);
                  if (this.var_a3e) {
                     if (!var9 && !var10 && !var22 && var11 != 3) {
                        var12 = Crosshair.screenPos.x - 30;
                        var13 = Crosshair.screenPos.y - 30;
                        if (this.var_8a8 > var12 && this.var_8a8 < var12 + 60 && this.var_8f0 > var13 && this.var_8f0 < var13 + 60) {
                           if (this.contextStation != this.var_a71[var11]) {
                              this.var_e89 = 0;
                           }

                           this.contextStation = this.var_a71[var11];
                           var10 = true;
                        }
                     }

                     this.var_c50 = var1.sub_a3c(this.var_c50);
                     this.var_cad = this.var_a71[var11].player.sub_a3c(this.var_cad);
                     if (var11 != 3) {
                        GameStatus.graphics.drawImage(this.lockonWaypoint2, this.var_8a8, this.var_8f0, 3);
                     }

                     SymbolMapManager_.sub_102(this.stationaryPlayersNames[var11], var11 == 0 ? this.var_8a8 + 25 : this.var_8a8 + 5, this.var_8f0, 0);
                     if (var11 < 2) {
                        if (var11 == 0 && !var8) {
                           SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(37) + ": " + Status.getStation().getTecLevel(), var11 == 0 ? this.var_8a8 + 25 : this.var_8a8 + 5, this.var_8f0 + 10, 1);
                           this.closestStatPlayerDistanceVisible = this.sub_1ab(this.var_a71[var11].targetX, this.var_a71[var11].targetY, this.var_a71[var11].targetZ, var1);
                           SymbolMapManager_.sub_102(this.closestStatPlayerDistanceVisible, var11 == 0 ? this.var_8a8 + 25 : this.var_8a8 + 5, this.var_8f0 + 20, 1);
                        } else {
                           this.closestStatPlayerDistanceVisible = this.sub_1ab(this.var_a71[var11].targetX, this.var_a71[var11].targetY, this.var_a71[var11].targetZ, var1);
                           SymbolMapManager_.sub_102(this.closestStatPlayerDistanceVisible, var11 == 0 ? this.var_8a8 + 25 : this.var_8a8 + 5, this.var_8f0 + 10, 1);
                        }
                     }
                  } else if (var11 == 3) {
                     GameStatus.graphics.drawImage(this.vortex, this.var_8a8, this.var_8f0, 3);
                  }
               }
            }

            if (!var22) {
               if (this.targetedStation != null) {
                  if (this.targetedStation.isOutOfScope()) {
                     this.targetedStation = null;
                  }

                  this.targetedStation = null;
               }

               if (var10 && !this.var_b73.getPlayerEgo().sub_e45() && !this.var_b73.getPlayerEgo().sub_a15()) {
                  this.var_e89 += var5;
                  if (this.var_e89 > this.scanTime) {
                     this.targetedStation = this.contextStation;
                  }

                  if (this.var_e89 > 0) {
                     if (this.contextStation != null && !this.contextStation.equals(this.targetedStation)) {
                        this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.var_e89 / (float)this.scanTime)));
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GameStatus.graphics);
                     } else if (Layout.sub_119()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GameStatus.graphics);
                     }
                  }
               } else {
                  if (this.targetedStation == null) {
                     this.contextStation = null;
                  }

                  this.var_e89 = 0;
               }
            }
         }

         var9 = this.var_f73 != null || this.var_fdf != null || this.targetedStation != null;
         boolean var28 = false;
         boolean var14;
         boolean var15;
         int var18;
         int var19;
         if (this.var_aeb != null) {
            for(var12 = 1; var12 < this.var_aeb.length; ++var12) {
               this.sub_85(var2, this.var_aeb[var12].sub_22a(this.var_c0c));
               if (this.var_a3e) {
                  int[] var30;
                  var8 = (var30 = Status.getSystem().getStations())[var12 - 1] == Status.getStation().getId();
                  var14 = false;
                  if (Status.getSystem().getJumpgateStationVerifiedIndex() == var12 - 1 && !var8) {
                     GameStatus.graphics.drawImage(this.mapJumpgate, this.var_8a8 + 10, this.var_8f0 - 10, 0);
                     var14 = true;
                  }

                  var15 = false;
                  Mission var16 = Status.getCampaignMission();
                  Mission var17 = Status.getFreelanceMission();
                  if (var16 != null && !var16.isEmpty() && var30[var12 - 1] == var16.setCampaignMission() && !var8) {
                     if (this.var_781 == null) {
                        this.var_781 = AEImage.loadCryptedImage("/data/interface/menu_map_mainmission.png");
                     }

                     GameStatus.graphics.drawImage(this.var_781, this.var_8a8 + 10 + (var14 ? 14 : 0), this.var_8f0 - 10, 0);
                     var15 = true;
                  }

                  if (var17 != null && !var17.isEmpty() && var30[var12 - 1] == var17.setCampaignMission() && !var8) {
                     if (this.var_741 == null) {
                        this.var_741 = AEImage.loadCryptedImage("/data/interface/menu_map_sidemission.png");
                     }

                     GameStatus.graphics.drawImage(this.var_741, this.var_8a8 + 10 + (var14 ? 14 : 0), this.var_8f0 - 10, 0);
                     var15 = true;
                  }

                  if (!var9 && !var28 && !var22) {
                     var18 = Skybox.var_5a9 == var12 ? 100 : 30;
                     var19 = Crosshair.screenPos.x - (var18 >> 1);
                     int var20 = Crosshair.screenPos.y - (var18 >> 1);
                     if (this.var_8a8 > var19 && this.var_8a8 < var19 + var18 && this.var_8f0 > var20 && this.var_8f0 < var20 + var18) {
                        if (var12 != Skybox.var_5a9) {
                           if (this.var_f8d != this.var_aeb[var12]) {
                              this.var_e35 = 0;
                           }

                           this.var_115c = var12 - 1;
                           this.var_f8d = this.var_aeb[var12];
                           var28 = true;
                        }

                        SymbolMapManager_.sub_102(Status.getPlanetNames()[var12 - 1], this.var_8a8 + 10 + (var14 ? 14 : 0) + (var15 ? 14 : 0), this.var_8f0 - 10, 0);
                     }
                  }
               }
            }

            if (!var10 && !var22) {
               if (this.var_fd4 != null) {
                  this.var_fd4 = null;
               }

               if (var28 && !this.var_b73.getPlayerEgo().sub_e45()) {
                  this.var_e35 += var5;
                  if (this.var_e35 > this.scanTime) {
                     if (var7) {
                        var4.sub_30d(21, this.var_b73.getPlayerEgo());
                     } else {
                        this.var_fd4 = this.var_f8d;
                        if (this.var_10e1) {
                           this.var_b73.getPlayerEgo().sub_f13(var3);
                        }
                     }
                  }

                  if (this.var_e35 > 0 && !var7) {
                     if (this.var_f8d != null && !this.var_f8d.equals(this.var_fd4)) {
                        this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.var_e35 / (float)this.scanTime)));
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GameStatus.graphics);
                     } else if (!this.var_b73.getPlayerEgo().sub_f55() && Layout.sub_119()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GameStatus.graphics);
                     }
                  }
               } else {
                  if (this.var_fd4 == null) {
                     this.var_f8d = null;
                  }

                  this.var_e35 = 0;
               }
            }
         }

         if (this.targetedPlayer != null && (this.targetedPlayer.isCrate() || this.targetedPlayer.isOutOfScope())) {
            this.targetedPlayer = null;
            var9 = false;
            this.contextShip = null;
         }

         boolean var29 = false;
         int var36;
         int var37;
         if (this.scanerPresent && this.var_a55 != null) {
            for(var13 = 0; var13 < this.var_a55.length; ++var13) {
               KIPlayer var25;
               if ((var25 = this.var_a55[var13]).player.sub_ace() && (!var25.isCrate() || var25.var_9d8)) {
                  this.sub_b8(var2, var25);
                  var14 = var25.var_9d8 && (var25.isOutOfScope() || var25.isCrate());
                  if (!var25.isOutOfScope() && !var25.isCrate() || var25.var_9d8) {
                     var15 = var25.equals(this.targetedPlayer);
                     Gun[] var33;
                     if (!var25.isOutOfScope() && this.var_b73.getPlayerEgo().player.sub_9e5(1) && (var33 = this.var_b73.getPlayerEgo().player.guns[1]) != null) {
                        GameStatus.graphics.setStrokeStyle(1);

                        for(var37 = 0; var37 < var33.length; ++var37) {
                           Gun var40;
                           if ((var40 = var33[var37]) != null && (var40.subType == 7 || var40.subType == 6) && var40.inAir) {
                              this.var_c50.set(var40.projectilesPos[0]);
                              this.var_cad = var25.sub_a9(this.var_cad);
                              this.var_cad.subtract(this.var_c50);
                              if ((var19 = this.var_cad.getLength()) < var40.sub_141()) {
                                 float var41;
                                 if ((var41 = (float)var19 / (float)var40.sub_141()) > 1.0F) {
                                    var41 = 1.0F;
                                 }

                                 int var21 = -16777216 | (int)(var41 * 255.0F) << 16 | (int)((1.0F - var41) * 255.0F) << 8;
                                 GameStatus.graphics.setColor(var21);
                                 GameStatus.var_8ce.getCamera().sub_fa(this.var_c50);
                                 this.var_cad.x = this.var_8a8;
                                 this.var_cad.y = this.var_8f0;
                                 this.var_cad.z = 0;
                                 this.var_cad.subtract(this.var_c50);
                                 this.var_cad.z = 0;
                                 AEVector3D var10000 = this.var_cad;
                                 var10000.x <<= 12;
                                 var10000 = this.var_cad;
                                 var10000.y <<= 12;
                                 this.var_cad.normalize();
                                 this.var_cad.scale(16);
                                 this.var_c50.add(this.var_cad);
                                 GameStatus.graphics.drawLine(this.var_8a8, this.var_8f0, this.var_c50.x, this.var_c50.y);
                              }
                           }
                        }

                        GameStatus.graphics.setStrokeStyle(0);
                     }

                     if (!var25.isAsteroid && !var25.var_99f && !var14 && var25.player.var_3df) {
                        ++this.var_1381;
                     }

                     Image var35;
                     if (!this.var_a3e) {
                        if (var14) {
                           GameStatus.graphics.drawImage(this.radarIconNeutral, this.var_8a8, this.var_8f0, 3);
                           GameStatus.graphics.drawImage(this.var_a55[var13].var_99f ? this.spacejunk : (this.var_a55[var13].race == 9 ? this.crateVoid : this.crate), this.var_8a8, this.var_8f0, 3);
                        } else {
                           var35 = var25.player.var_3df ? (var15 ? this.radarIconEnemyLock : this.radarIconEnemy) : (var25.player.var_427 ? (var15 ? this.radarIconFriendLock : this.hudRadarIconFriend) : (var15 ? this.radarIconNeutralLock : this.radarIconNeutral2));
                           GameStatus.graphics.drawImage(var35, this.var_8a8, this.var_8f0, 3);
                        }
                     } else {
                        this.var_c50 = var1.sub_a3c(this.var_c50);
                        this.var_cad = var25.player.sub_a3c(this.var_cad);
                        if (this.var_c50.x - this.var_cad.x <= 24000 && this.var_c50.x - this.var_cad.x >= -24000 && this.var_c50.y - this.var_cad.y <= 24000 && this.var_c50.y - this.var_cad.y >= -24000 && this.var_c50.z - this.var_cad.z <= 24000 && this.var_c50.z - this.var_cad.z >= -24000 && var25.sub_4a3() != 9996) {
                           label862: {
                              var25.var_efb = true;
                              Image var10001;
                              Graphics var42;
                              if (var14) {
                                 var42 = GameStatus.graphics;
                                 var10001 = this.bracketBox;
                              } else {
                                 var36 = var25.player.var_3df ? 0 : (var25.player.var_427 ? 6 : 4);
                                 this.var_d13 = (int)((float)var25.player.sub_6d4() / 100.0F * 16.0F);
                                 this.bars.setFrame(var36 + 1);
                                 this.bars.setPosition(this.var_8a8 + 8 + 2, this.var_8f0 - 8);
                                 this.bars.paint(GameStatus.graphics);
                                 this.bars.setFrame(var36);
                                 GameStatus.graphics.setClip(0, this.var_8f0 - 8 + 16 - this.var_d13, GameStatus.screenWidth, this.var_d13);
                                 this.bars.paint(GameStatus.graphics);
                                 GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
                                 this.var_d13 = (int)((float)var25.player.sub_6f7() / 100.0F * 16.0F);
                                 this.bars.setFrame(3);
                                 this.bars.setPosition(this.var_8a8 + 8 + 7, this.var_8f0 - 8);
                                 this.bars.paint(GameStatus.graphics);
                                 this.bars.setFrame(2);
                                 GameStatus.graphics.setClip(0, this.var_8f0 - 8 + 16 - this.var_d13, GameStatus.screenWidth, this.var_d13);
                                 this.bars.paint(GameStatus.graphics);
                                 GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
                                 if (!var15) {
                                    break label862;
                                 }

                                 var42 = GameStatus.graphics;
                                 var10001 = var25.player.var_3df ? this.lockonEnemy : (var25.player.var_427 ? this.hudLockOnFriend : this.lockonNeutral);
                              }

                              var42.drawImage(var10001, this.var_8a8, this.var_8f0, 3);
                           }
                        } else {
                           var35 = var14 ? this.bracketBox : (var15 ? (var25.player.var_3df ? this.bracketEnemyFar : (var25.player.var_427 ? this.bracketFriendFar : this.lockonNeutral)) : (var25.player.var_3df ? this.radarIconEnemy : (var25.player.var_427 ? this.hudRadarIconFriend : this.radarIconNeutral2)));
                           GameStatus.graphics.drawImage(var35, this.var_8a8, this.var_8f0, 3);
                           var25.var_efb = false;
                        }

                        if (!this.var_b73.getPlayerEgo().sub_a15() && (!var25.var_99f || var14) && (!var29 || var25.var_e74) && (var14 && var25.isOutOfScope() || !var14)) {
                           if (var14 && this.var_fdf == null && this.var_128a) {
                              this.var_fdf = var25;
                              var29 = true;
                              this.contextShip = var25;
                           } else {
                              var36 = Crosshair.screenPos.x - 30;
                              var37 = Crosshair.screenPos.y - 30;
                              if (this.var_8a8 > var36 && this.var_8a8 < var36 + 60 && this.var_8f0 > var37 && this.var_8f0 < var37 + 60 && (var14 && this.tractorBeamPresent || this.pastIntro && !this.var_10e1)) {
                                 if (this.contextShip != var25) {
                                    this.var_de6 = 0;
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

            if (!this.var_b73.getPlayerEgo().sub_e45() && !var22 && !var9) {
               boolean var31 = this.contextShip != null && this.contextShip.var_9d8 && (this.contextShip.isOutOfScope() || this.contextShip.isCrate());
               if (this.targetedPlayer != null && (this.targetedPlayer.isCrate() || this.targetedPlayer.isOutOfScope())) {
                  this.targetedPlayer = null;
               }

               if (var29) {
                  this.var_de6 += var5;
                  if (this.var_de6 > (!var31 && !this.contextShip.var_e74 ? this.scanTime : this.var_1316)) {
                     if (!var31 && !this.contextShip.var_e74) {
                        if (this.targetedPlayer != this.contextShip) {
                           this.targetedPlayer = this.contextShip;
                           if (this.scanCargo) {
                              this.var_c50 = var1.sub_a3c(this.var_c50);
                              this.var_cad = this.targetedPlayer.player.sub_a3c(this.var_cad);
                              if (this.var_c50.x - this.var_cad.x < 24000 || this.var_c50.x - this.var_cad.x > -24000 || this.var_c50.y - this.var_cad.y < 24000 || this.var_c50.y - this.var_cad.y > -24000 || this.var_c50.z - this.var_cad.z < 24000 || this.var_c50.z - this.var_cad.z > -24000) {
                                 int[] var26;
                                 if ((var26 = this.targetedPlayer.var_a3b) != null) {
                                    for(int var32 = 0; var32 < var26.length; var32 += 2) {
                                       if (var26[var32 + 1] > 0) {
                                          var4.sub_3ae(var26[0], 1, false, false, false, true);
                                          break;
                                       }
                                    }
                                 } else {
                                    var4.sub_30d(22, this.var_b73.getPlayerEgo());
                                 }
                              }
                           }
                        }
                     } else {
                        this.var_fdf = this.contextShip;
                        if (!this.tractorBeamPresent) {
                           var4.sub_30d(9, this.var_b73.getPlayerEgo());
                           this.var_fdf = null;
                        }
                     }

                     this.var_de6 = 0;
                  }

                  if (this.var_de6 > (!var31 && !this.contextShip.var_e74 ? 0 : 500) && this.contextShip != null && !this.contextShip.equals(!var31 && !this.contextShip.var_e74 ? this.targetedPlayer : this.var_fdf)) {
                     this.scanProcessAnim.setFrame((int)((float)(this.scanProcessAnim.getRawFrameCount() - 2) * ((float)(this.var_de6 - (!var31 && !this.contextShip.var_e74 ? 0 : 500)) / (float)(!var31 && !this.contextShip.var_e74 ? this.scanTime : this.var_1316 - 500))));
                     this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                     this.scanProcessAnim.paint(GameStatus.graphics);
                  }
               } else {
                  if (var31 && this.var_fdf == null || !var31 && this.targetedPlayer == null) {
                     this.contextShip = null;
                  }

                  this.var_de6 = 0;
               }
            }
         }

         var13 = 0;

         for(int var27 = 0; var27 < this.var_31.length; ++var27) {
            this.var_31[var27] = -1;
         }

         var9 = this.contextShip != null && this.targetedPlayer == null || this.var_fdf != null || this.targetedStation != null || this.var_fd4 != null;
         var8 = false;
         if (!this.var_b73.getPlayerEgo().sub_1045() && this.var_ad3 != null) {
            var14 = false;
            if (this.scanAtypeAsteroids) {
               var14 = this.var_b73.sub_8dc(this.var_b73.getPlayerEgo().sub_8c1());
            }

            int var34;
            boolean var39;
            for(var34 = 0; var34 < this.var_ad3.length; ++var34) {
               KIPlayer var38;
               if ((var38 = this.var_ad3[var34]).var_9d8 && (var38.isOutOfScope() || var38.isCrate()) || !var38.isOutOfScope() && !var38.isCrate()) {
                  this.sub_b8(var2, var38);
                  var39 = var38.var_9d8 && (var38.isOutOfScope() || var38.isCrate());
                  if (!this.var_a3e) {
                     if (var39) {
                        GameStatus.graphics.drawImage(this.radarIconNeutral, this.var_8a8, this.var_8f0, 3);
                        GameStatus.graphics.drawImage(this.asteroid, this.var_8a8, this.var_8f0, 3);
                     }
                  } else {
                     if (!var9 && !var29 && !var8 && !var22 && ((PlayerAsteroid)var38).sub_80() > 15 && !this.var_10e1) {
                        if (var39 && this.var_fdf == null && this.var_128a) {
                           this.var_fdf = var38;
                           var8 = true;
                           this.contextShip = var38;
                        } else {
                           var18 = Crosshair.screenPos.x - 20;
                           var19 = Crosshair.screenPos.y - 20;
                           if (this.var_8a8 > var18 && this.var_8a8 < var18 + 40 && this.var_8f0 > var19 && this.var_8f0 < var19 + 40 && !((PlayerAsteroid)var38).var_973 && this.pastIntro && var13 < 4) {
                              this.var_31[var13] = var34;
                              ++var13;
                           }
                        }
                     }

                     if (var14 && (var18 = ((PlayerAsteroid)var38).sub_1bb()) == 0) {
                        this.meteorClass.setFrame(var18);
                        this.meteorClass.setRefPixelPosition(this.var_8a8, this.var_8f0);
                        this.meteorClass.paint(GameStatus.graphics);
                     }

                     if (var39) {
                        GameStatus.graphics.drawImage(this.bracketBox, this.var_8a8, this.var_8f0, 3);
                     }
                  }
               }
            }

            var34 = 999999;
            var36 = -1;

            for(var37 = 0; var37 < 5; ++var37) {
               if ((var18 = this.var_31[var37]) >= 0) {
                  this.var_bc4 = this.var_ad3[var18].sub_a9(this.var_bc4);
                  this.var_c0c = var1.sub_a3c(this.var_c0c);
                  this.var_bc4.subtract(this.var_c0c);
                  if ((var19 = this.var_bc4.getLength()) < var34) {
                     var34 = var19;
                     var36 = var18;
                  }
               }
            }

            if (var36 >= 0) {
               if (this.contextAsteroid != this.var_ad3[var36]) {
                  this.var_e11 = 0;
               }

               this.contextAsteroid = this.var_ad3[var36];
               var8 = true;
            }

            if (!var29 && !var22) {
               if (this.var_f73 != null) {
                  if (this.var_f73.isOutOfScope()) {
                     this.var_f73 = null;
                  }

                  this.var_f73 = null;
               }

               var39 = this.contextAsteroid != null && this.contextAsteroid.var_9d8 && (this.contextAsteroid.isOutOfScope() || this.contextAsteroid.isCrate());
               if (var8 && !this.var_b73.getPlayerEgo().sub_e45()) {
                  this.var_e11 += var5;
                  if (this.var_e11 > this.scanTime - 200) {
                     if (var39) {
                        if (!this.tractorBeamPresent) {
                           var4.sub_30d(9, this.var_b73.getPlayerEgo());
                        } else {
                           this.var_fdf = this.contextAsteroid;
                        }
                     } else if (!this.drillPresent) {
                        var4.sub_30d(20, this.var_b73.getPlayerEgo());
                     } else {
                        this.var_f73 = this.contextAsteroid;
                     }
                  }

                  if (this.var_e11 > 500) {
                     if (this.contextAsteroid != null && !this.contextAsteroid.equals(var39 ? this.var_fdf : this.var_f73)) {
                        if ((var18 = (int)((float)(this.scanProcessAnim.getRawFrameCount() - 1) * ((float)(this.var_e11 - 500) / (float)(this.scanTime - 500)))) < this.scanProcessAnim.getRawFrameCount() - 1) {
                           this.scanProcessAnim.setFrame(var18);
                           this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                           this.scanProcessAnim.paint(GameStatus.graphics);
                        }
                     } else if (Layout.sub_119()) {
                        this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                        this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                        this.scanProcessAnim.paint(GameStatus.graphics);
                     }
                  }
               } else {
                  if (var39 && this.var_fdf == null || !var39 && this.var_f73 == null) {
                     this.contextAsteroid = null;
                  }

                  this.var_e11 = 0;
               }
            }
         }

         if (this.var_1381 > 0) {
            if (GameStatus.var_9fc != 3) {
               GameStatus.soundManager.playMusic(3);
            }
         } else if (GameStatus.var_9fc != 4 && GameStatus.var_9fc != 0) {
            GameStatus.soundManager.playMusic(4);
         }

         this.var_1381 = 0;
      }
   }

   public final int sub_12d() {
      return Status.getSystem().getStations()[this.var_115c];
   }

   public final void sub_18f(Hud var1) {
      if (this.var_10b4) {
         if (this.var_fd4 != null) {
            SymbolMapManager_.sub_161(Status.getPlanetNames()[this.var_115c], GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2, 1, 18);
         } else {
            KIPlayer var2;
            if ((var2 = this.var_f73 != null ? this.var_f73 : (this.targetedPlayer != null ? this.targetedPlayer : (this.targetedStation != null ? this.targetedStation : null))) != null) {
               boolean var3 = var2.equals(this.var_f73);
               boolean var4 = var2.equals(this.targetedStation);
               String var5 = this.targetedStation == this.var_a71[0] ? this.stationaryPlayersNames[0] : (this.targetedStation == this.var_a71[3] ? this.stationaryPlayersNames[3] : this.stationaryPlayersNames[1]);
               if (var3) {
                  Face_.sub_158(((PlayerAsteroid)var2).var_9d3, var1.var_831, GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2, 40);
                  this.meteorClass.setFrame(((PlayerAsteroid)var2).sub_1bb());
                  this.meteorClass.setRefPixelPosition(GameStatus.screenWidth - 2, GameStatus.screenHeight - 2);
                  this.meteorClass.paint(GameStatus.graphics);
                  SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(569 + ((PlayerAsteroid)var2).var_9d3), GameStatus.screenWidth - 4 - this.meteorClass.getHeight(), GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2, 1, 18);
                  return;
               }

               if (!var4) {
                  this.logosSmall.setFrame(var2.race);
                  this.logosSmall.setRefPixelPosition(GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2);
                  this.logosSmall.paint(GameStatus.graphics);
               }

               if (var2.name != null && var2.name.equals(GameStatus.langManager.getLangString(833))) {
                  if (Layout.sub_119()) {
                     SymbolMapManager_.sub_161(var2.name, GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2, 2, 18);
                     return;
                  }
               } else {
                  String var6 = var4 ? var5 : (var2.name != null ? var2.name : GameStatus.langManager.getLangString(229 + var2.race));
                  if (!var4) {
                     var6 = var6 + " " + var2.player.sub_6d4() + "%";
                  } else {
                     if (Status.inAlienOrbit()) {
                        this.logosSmall.setFrame(9);
                     } else {
                        this.logosSmall.setFrame(Status.getSystem().getRace());
                     }

                     this.logosSmall.setRefPixelPosition(GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2);
                     this.logosSmall.paint(GameStatus.graphics);
                  }

                  SymbolMapManager_.sub_161(var6, GameStatus.screenWidth - 2, GameStatus.screenHeight - SymbolMapManager_.sub_2c2() - 2, 1, 18);
               }
            }

         }
      }
   }

   private String sub_1ab(int var1, int var2, int var3, Player var4) {
      this.var_c50 = var4.sub_a3c(this.var_c50);
      this.floatVector.x = (float)(this.var_c50.x >> 1);
      this.floatVector.y = (float)(this.var_c50.y >> 1);
      this.floatVector.z = (float)(this.var_c50.z >> 1);
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

   public final KIPlayer sub_1bd() {
      return this.var_f73;
   }

   public final void sub_002() {
      this.var_f73 = null;
   }

   public final KIPlayer sub_1fa() {
      return this.targetedPlayer;
   }
}
