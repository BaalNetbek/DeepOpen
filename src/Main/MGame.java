package Main;

import AbyssEngine.AEResourceManager;
import AbyssEngine.AEVector3D;
import AbyssEngine.AbstractMesh;
import AbyssEngine.AbstractScene;
import AbyssEngine.AutoPilotList;
import AbyssEngine.Camera;
import AbyssEngine.Class_198;
import AbyssEngine.Class_23e;
import AbyssEngine.Class_661;
import AbyssEngine.Class_7flight;
import AbyssEngine.Class_876;
import AbyssEngine.Class_c53cameraRelated;
import AbyssEngine.Class_fd6;
import AbyssEngine.Dialogue;
import AbyssEngine.FileRead;
import AbyssEngine.Galaxy;
import AbyssEngine.GameStatus;
import AbyssEngine.GraphNode;
import AbyssEngine.Hud;
import AbyssEngine.Item;
import AbyssEngine.KIPlayer;
import AbyssEngine.Layout;
import AbyssEngine.Level;
import AbyssEngine.MGameContext;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.PlayerEgo;
import AbyssEngine.PlayerJumpgate;
import AbyssEngine.Popup;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;

public final class MGame extends AbstractScene {
   private int var_47;
   private long currentTimeMs;
   private long lastFrameTimeMs;
   private long frameTime;
   private long var_187;
   private long var_1c2;
   private long var_209;
   private boolean var_287;
   private PlayerEgo playerEgo;
   private Camera var_3c0;
   private Class_198 var_446;
   private Class_c53cameraRelated var_450;
   private Class_fd6 var_48b;
   private boolean isIntro;
   private boolean var_53f;
   private boolean var_55d;
   private boolean var_58b;
   private boolean egoDead;
   private int var_5ea;
   private Hud var_611;
   private Level var_630;
   private Class_7flight var_689;
   private MGameContext var_6d2;
   private Class_23e var_736;
   private OptionsWindow var_75e;
   private Dialogue var_7af;
   private Dialogue var_7f9;
   private StarMap starMap;
   private AutoPilotList var_865;
   private Popup popup;
   private boolean jumpgateReached;
   private boolean var_947;
   private boolean var_976;
   private boolean autopilotMenuOpen;
   private boolean var_9d5;
   private boolean var_a07;
   private boolean var_a4e;
   private boolean var_a7d;
   private boolean var_b21;
   private boolean actionMenuOpen;
   private int keysPressed;
   private boolean var_c3b;
   private boolean var_c80;
   private boolean var_ce4;
   private AbstractMesh var_d04;
   private boolean var_d4a;
   private AEVector3D var_d9c = new AEVector3D();

   public final void freeResources() {
      if (this.playerEgo != null) {
         this.playerEgo.sub_1181();
      }

      this.playerEgo = null;
      if (this.starMap != null) {
         this.starMap.sub_98();
      }

      this.starMap = null;
      if (this.var_7af != null) {
         this.var_7af.sub_146();
      }

      this.var_7af = null;
      this.var_3c0 = null;
      if (this.var_630 != null) {
         this.var_630.sub_c8b();
      }

      this.var_630 = null;
      if (this.var_6d2 != null) {
         this.var_6d2.sub_42();
      }

      this.var_6d2 = null;
      if (this.var_736 != null) {
         this.var_736.sub_39();
      }

      this.var_736 = null;
      this.var_611.sub_1e9();
      if (this.var_75e != null) {
         this.var_75e.sub_e6();
      }

      if (this.var_d04 != null) {
         this.var_d04.destroy();
         this.var_d04 = null;
      }

      this.var_450.sub_43((GraphNode)null);
      this.var_450.sub_54((GraphNode)null);
      this.var_446 = null;
      this.var_3c0 = null;
      this.var_75e = null;
      this.var_689 = null;
      this.var_287 = false;
      this.var_865 = null;
      System.gc();
   }

   public final void loadResources() {
      if (this.var_630 == null) {
         this.var_630 = new Level();
      }

      if (this.var_630.sub_198()) {
         this.playerEgo = this.var_630.getPlayerEgo();
         this.var_611 = new Hud();
         this.var_736 = new Class_23e();
         this.var_736.sub_96(this.var_630.sub_872());
         this.var_3c0 = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 750, 500, 32000);
         this.var_446 = new Class_198();
         this.var_446.sub_25(this.var_3c0);
         GameStatus.renderer.sub_19(this.var_3c0);
         this.var_450 = new Class_c53cameraRelated(this.playerEgo.var_50e, this.var_3c0);
         this.var_48b = new Class_fd6(this.playerEgo.var_50e, this.var_3c0);
         this.var_48b.sub_120(false);
         this.var_446.sub_5c(this.var_48b);
         this.var_446.sub_5c(this.var_450);
         this.var_6d2 = new MGameContext(this.var_630);
         this.var_689 = new Class_7flight(this.var_450, this.var_48b, this.var_630, this.var_611, this.var_6d2);
         Class_7flight.sub_1b8(this.var_450, this.var_630);
         this.playerEgo.sub_295(this.var_450, this.var_48b);
         this.isIntro = Status.getCurrentCampaignMission() == 0;
         this.egoDead = false;
         this.var_47 = 0;
         this.var_53f = false;
         this.var_58b = false;
         this.var_209 = 0L;
         this.autopilotMenuOpen = false;
         this.jumpgateReached = false;
         this.var_947 = false;
         this.currentTimeMs = System.currentTimeMillis();
         this.lastFrameTimeMs = System.currentTimeMillis();
         if (Status.baseArmour >= 0) {
            this.playerEgo.player.setHitPoints(Status.baseArmour);
         }

         if (Status.shield >= 0) {
            this.playerEgo.player.sub_386(Status.shield);
         }

         if (Status.additionalArmour >= 0) {
            this.playerEgo.player.sub_3aa(Status.additionalArmour);
         }

         this.playerEgo.sub_127();
         Status.baseArmour = Status.getShip().getBaseArmour();
         Status.shield = Status.getShip().getShield();
         Status.additionalArmour = Status.getShip().getAdditionalArmour();
         if (!Status.inAlienOrbit()) {
            Status.lastVisitedNonVoidOrbit = Status.getStation().getId();
         }

         this.var_a7d = false;
         this.var_976 = false;
         this.var_9d5 = false;
         this.var_a07 = false;
         Layout.sub_4cc(false);
         System.gc();
         this.currentTimeMs = System.currentTimeMillis();
         this.lastFrameTimeMs = System.currentTimeMillis();
         this.var_209 = 0L;
         this.var_287 = true;
         this.var_b21 = false;
         this.actionMenuOpen = false;
         this.var_c3b = false;
         this.var_c80 = false;
         this.var_ce4 = false;
         this.var_d4a = false;
         this.var_a4e = false;
         if (Status.getShip().isSimilarEquiped(GameStatus.var_9c3)) {
            this.var_630.getPlayerEgo().sub_863(GameStatus.var_9c3);
         }

         if (Status.getCurrentCampaignMission() != 0 && Status.getCurrentCampaignMission() != 1) {
            if (GameStatus.random.nextInt(2) == 0) {
               GameStatus.soundManager.playMusic(4);
            } else {
               GameStatus.soundManager.playMusic(0);
            }
         } else {
            GameStatus.soundManager.playMusic(4);
         }
      }
   }

   public final boolean isLoaded() {
      return this.var_287;
   }

   public final void renderScene(int var1) {
      this.keysPressed = var1;

      try {
         if (this.var_287) {
            this.currentTimeMs = System.currentTimeMillis();
            this.frameTime = this.currentTimeMs - this.lastFrameTimeMs;
            this.lastFrameTimeMs = this.currentTimeMs;
            this.var_1c2 += this.frameTime;
            this.var_187 += this.frameTime;
            if (!this.var_53f) {
               Class_7flight var10000 = this.var_689;
               var10000.var_500 += this.frameTime;
               if (!this.var_58b) {
                  this.var_209 += this.frameTime;
               }

               Status.incPlayingTime(this.frameTime);
               Layout.addTicks((int)this.frameTime);
               Status.wingmenTimeRemaining = (int)((long)Status.wingmenTimeRemaining - this.frameTime);
            }

            if (!this.egoDead) {
               if (this.var_53f) {
                  if (this.var_75e != null && !this.actionMenuOpen && !this.var_55d && !this.autopilotMenuOpen && !this.jumpgateReached && !this.var_a7d) {
                     this.var_75e.sub_12b(var1, (int)this.frameTime);
                     if (this.var_187 > 150L) {
                        this.var_187 = 0L;
                        if ((var1 & 2) != 0) {
                           this.var_75e.sub_1e5((int)this.frameTime);
                        }

                        if ((var1 & 64) != 0) {
                           this.var_75e.sub_215((int)this.frameTime);
                        }
                     }
                  }

                  if (!this.var_287) {
                     return;
                  } else {
                     this.var_630.sub_9ff(this.frameTime);
                     this.playerEgo.sub_11da(!this.var_58b);
                     GameStatus.graphics3D.bindTarget(GameStatus.graphics);
                     GameStatus.renderer.sub_95();
                     GameStatus.graphics3D.releaseTarget();
                     if (this.autopilotMenuOpen) {
                        this.var_865.draw();
                        return;
                     } else if (this.jumpgateReached) {
                        this.popup.sub_19a();
                        return;
                     } else if (this.var_a7d) {
                        this.var_7f9.sub_1c3(var1, (int)this.frameTime);
                        if (this.var_976) {
                           this.starMap.sub_101(0, (int)this.frameTime);
                        }

                        this.var_7f9.sub_364();
                        return;
                     } else if (this.actionMenuOpen) {
                        this.var_611.sub_41b(this.var_53f ? 0L : this.frameTime, (long)this.var_689.var_4c9 - this.var_689.var_500, this.playerEgo, this.isIntro);
                        this.var_611.sub_51c((int)this.frameTime);
                        return;
                     } else if (this.var_55d) {
                        Layout.addTicks((int)this.frameTime);
                        this.var_7af.sub_1c3(var1, (int)this.frameTime);
                        this.var_7af.sub_304();
                        return;
                     } else {
                        this.var_75e.sub_3a2();
                        return;
                     }
                  }
               }

               if (this.playerEgo.sub_f9b()) {
                  Status.sub_1e5(Galaxy.getStation(this.var_6d2.sub_12d()));
                  Level.sub_16a();
                  Status.baseArmour = this.playerEgo.player.getHitpoints();
                  Status.shield = this.playerEgo.player.sub_49f();
                  Status.additionalArmour = this.playerEgo.player.sub_53c();
                  GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
                  return;
               }

               if (this.var_c3b && this.sub_20d()) {
                  return;
               }

               if (this.var_976) {
                  int var4 = (int)this.frameTime;
                  this.starMap.sub_101(var1, var4);
               }

               if ((this.var_689.sub_123() || !this.var_689.sub_182()) && !this.jumpgateReached && (this.sub_1ce() || this.var_976)) {
                  return;
               }

               if (this.var_7f9 == null && this.var_689.var_500 > 5000L) {
                  if (!GameStatus.boosterHelpShown && Status.getShip().hasBooster()) {
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(306));
                     this.var_a7d = true;
                     this.var_53f = true;
                     GameStatus.boosterHelpShown = true;
                     return;
                  }

                  if (!GameStatus.jumpDriveHelpShown && Status.getShip().hasJumpDrive()) {
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(304));
                     this.var_a7d = true;
                     this.var_53f = true;
                     GameStatus.jumpDriveHelpShown = true;
                     return;
                  }

                  if (!GameStatus.cloakHelpShown && Status.getShip().hasCloak()) {
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(305));
                     this.var_a7d = true;
                     this.var_53f = true;
                     GameStatus.cloakHelpShown = true;
                     return;
                  }

                  if (!GameStatus.interplanetHelpShown && !this.playerEgo.sub_a15() && Status.getCurrentCampaignMission() > 9) {
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(300));
                     this.var_a7d = true;
                     this.var_53f = true;
                     GameStatus.interplanetHelpShown = true;
                     return;
                  }

                  if (Status.wingmenNames != null) {
                     if (!GameStatus.wingmenHelpShown) {
                        this.var_53f = true;
                        this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(320));
                        this.var_a7d = true;
                        GameStatus.wingmenHelpShown = true;
                        return;
                     }

                     if (!this.var_d4a && Status.wingmenTimeRemaining <= 0) {
                        this.var_53f = true;
                        this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(153), Status.wingmenNames[0], Status.var_8f8wingmenVar2);
                        this.var_a7d = true;
                        this.var_d4a = true;
                        return;
                     }
                  }

                  if (!GameStatus.reputationHelpShown && Status.getStanding().atWar()) {
                     this.var_53f = true;
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(324));
                     this.var_a7d = true;
                     GameStatus.reputationHelpShown = true;
                     return;
                  }

                  if (!GameStatus.miningHelpShown && this.playerEgo.sub_be6() && !GameStatus.miningHelpShown) {
                     this.var_53f = true;
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(307));
                     this.var_a7d = true;
                     GameStatus.miningHelpShown = true;
                     return;
                  }

                  if (!GameStatus.asteroidHelpShown && this.playerEgo.sub_be6() && Status.getCurrentCampaignMission() > 3) {
                     this.var_53f = true;
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(308));
                     this.var_a7d = true;
                     GameStatus.asteroidHelpShown = true;
                     return;
                  }

                  if (!GameStatus.cargoFullHelpShown && this.var_611.sub_3fa() && Status.getCurrentCampaignMission() > 6) {
                     this.var_53f = true;
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(319));
                     this.var_a7d = true;
                     GameStatus.cargoFullHelpShown = true;
                     return;
                  }
               }

               if (this.var_209 > 5000L && !this.var_55d && !Status.getMission().hasFailed() && !Status.getMission().hasWon()) {
                  this.sub_17e();
               }
            }

            if (this.playerEgo.isBoostActive() && !this.playerEgo.sub_f55()) {
               this.var_5ea = 750 + (int)(this.playerEgo.getCurrentBoostedSpeed() * 150.0F);
               this.var_3c0.sub_ba(this.var_5ea);
            }

            if (Status.getMission().hasFailed() || this.egoDead || !this.sub_16c()) {
               if (!this.egoDead) {
                  this.sub_11d();
               }

               this.var_58b = this.var_689.sub_50((int)this.frameTime, this.var_450);
               if (this.var_689 != null) {
                  this.sub_2f();
                  this.sub_bf();
                  if (!this.egoDead && !this.var_58b) {
                     if (Level.var_1311 && this.var_689.var_500 > 7500L) {
                        Level.var_1311 = false;
                        this.var_c80 = true;
                        this.sub_225();
                     }

                     if (this.isIntro) {
                        this.playerEgo.sub_6c9((int)this.frameTime, 0);
                     }

                     boolean var2 = this.playerEgo.sub_a15() && !this.playerEgo.isLookingBack() || this.playerEgo.sub_e45() || this.playerEgo.sub_ea4();
                     if ((var1 & 4) != 0) {
                        if (var2) {
                           this.var_611.sub_30d(7, this.playerEgo);
                        } else {
                           this.playerEgo.sub_c3e((int)this.frameTime);
                        }
                     }

                     if ((var1 & 32) != 0) {
                        if (var2) {
                           this.var_611.sub_30d(7, this.playerEgo);
                        } else {
                           this.playerEgo.sub_c71((int)this.frameTime);
                        }
                     }

                     if ((var1 & 2) != 0) {
                        if (var2) {
                           this.var_611.sub_30d(7, this.playerEgo);
                        } else if (GameStatus.invertedControlsOn) {
                           this.playerEgo.sub_c7e((int)this.frameTime);
                        } else {
                           this.playerEgo.sub_cc3((int)this.frameTime);
                        }
                     }

                     if ((var1 & 64) != 0) {
                        if (var2) {
                           this.var_611.sub_30d(7, this.playerEgo);
                        } else if (GameStatus.invertedControlsOn) {
                           this.playerEgo.sub_cc3((int)this.frameTime);
                        } else {
                           this.playerEgo.sub_c7e((int)this.frameTime);
                        }
                     }

                     if ((var1 & 256) != 0 && !this.var_c3b && (this.playerEgo.isLookingBack() || !this.playerEgo.sub_a15() && !this.playerEgo.sub_e45())) {
                        this.playerEgo.sub_6c9((int)this.frameTime, 0);
                     }
                  }

                  if (this.playerEgo.sub_b5d()) {
                     if (Status.getMission().isCampaignMission()) {
                        int var6;
                        if ((var6 = Status.getCurrentCampaignMission()) == 29 || var6 == 41 || var6 == 40 && this.var_630.getShips()[0].player.sub_ace()) {
                           this.playerEgo.player.setHitPoints(0);
                           return;
                        }

                        if (var6 != 42) {
                           Status.nextCampaignMission();
                        }

                        if (var6 == 40) {
                           Level.var_12b5 = this.var_630.getShips()[0].player.getHitpoints();
                        }

                        this.var_630.sub_b30();
                        Status.setMission(Mission.var_dc);
                     }

                     Status.setMission(Mission.var_dc);
                     if (Status.inAlienOrbit()) {
                        Status.setCurrentStation_andInitSystem_(Galaxy.getStation(Status.lastVisitedNonVoidOrbit));
                     } else {
                        Status.setCurrentStation_andInitSystem_(Status.voidStation);
                     }

                     Status.baseArmour = this.playerEgo.player.getHitpoints();
                     Status.shield = this.playerEgo.player.sub_49f();
                     Status.additionalArmour = this.playerEgo.player.sub_53c();
                     Status.sub_1e5(Status.getStation());
                     Level.autopilotDestination = null;
                     Level.var_1297 = true;
                     Level.var_12a2 = true;
                     GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
                  }

               }
            }
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   private void sub_2f() {
      try {
         this.var_630.sub_a3b(this.frameTime);
         this.var_630.sub_966(this.frameTime);
         this.playerEgo.sub_bb7((int)this.frameTime, this.var_6d2, this.var_611, this.var_736, this.keysPressed);
         this.playerEgo.sub_11da(!this.var_58b);
         if (this.var_d04 != null) {
            GameStatus.renderer.sub_87(this.var_d04);
         }

         this.var_689.sub_6a();
         GameStatus.graphics3D.bindTarget(GameStatus.graphics);
         this.var_446.sub_109(true);
         GameStatus.renderer.sub_cc(System.currentTimeMillis());
         GameStatus.graphics3D.releaseTarget();
      } catch (Exception var2) {
         GameStatus.graphics3D.releaseTarget();
         var2.printStackTrace();
      }
   }

   private void sub_bf() {
      this.var_630.sub_9ca();
      if (this.var_58b) {
         if (Status.getCurrentCampaignMission() > 1) {
            GameStatus.loadingScreen.sub_d5();
            this.var_611.sub_4bc();
         }

         if (this.var_689.sub_123()) {
            this.var_736.sub_1f5(this.var_689.var_500, this.frameTime, this.playerEgo);
            return;
         }
      } else if (this.egoDead) {
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(156), GameStatus.screenWidth / 2, 20, 0, 8);
         if (this.var_209 > 3000L) {
            this.popup.sub_19a();
            return;
         }
      } else {
         this.playerEgo.sub_1227(!this.var_58b, GameStatus.renderer.getCamera());
         if (!this.playerEgo.sub_be6()) {
            this.var_6d2.render(this.playerEgo.player, GameStatus.renderer.getCamera(), this.var_450, this.var_611, (int)this.frameTime);
         }

         this.var_611.sub_41b(this.var_53f ? 0L : this.frameTime, (long)this.var_689.var_4c9 - this.var_689.var_500, this.playerEgo, this.isIntro);
         this.var_736.sub_1f5(this.var_689.var_500, this.frameTime, this.playerEgo);
         this.var_6d2.sub_18f(this.var_611);
      }

   }

   private void sub_11d() {
      if (this.playerEgo.getHP() <= 0) {
         this.egoDead = true;
         GameStatus.langManager.getLangString(156);
      }

      if (this.var_630.sub_ba1((int)this.frameTime)) {
         if (this.var_7af == null) {
            this.var_7af = new Dialogue();
         }

         this.var_7af.sub_f5(Status.getMission(), 2);
         this.var_55d = true;
         this.var_53f = true;
      }

      if (this.var_209 > 5000L) {
         this.var_209 = 0L;
         if (this.var_689.var_4c9 > 0 && this.var_689.var_500 > (long)this.var_689.var_4c9 && (Status.getCurrentCampaignMission() == 42 || this.var_630.var_b00 != null && !this.var_630.var_b00.sub_7d())) {
            if (this.var_7af == null) {
               this.var_7af = new Dialogue();
            }

            this.var_7af.sub_f5(Status.getMission(), 2);
            this.var_55d = true;
            this.var_53f = true;
         }
      }

      if (this.egoDead) {
         this.var_209 = 0L;
         this.var_450.sub_1df();
         GameStatus.vibrate(0);
         if (this.popup == null) {
            this.popup = new Popup();
         }

         this.popup.sub_8f("", false);
      }

   }

   private boolean sub_16c() {
      if (this.var_209 > 5000L) {
         Mission var1 = Status.missionCompleted_(false, this.var_689.var_500);
         if (this.var_630.sub_a83((int)this.var_689.var_500) || var1 != null) {
            if (Status.getMission().getType() != 5 && Status.getMission().getType() != 3) {
               if (Status.getMission().isCampaignMission() && Status.getCurrentCampaignMission() == 127) {
                  Status.nextCampaignMission();
                  this.var_630.sub_b30();
                  Status.setMission(Mission.var_dc);
               } else {
                  if (!Status.getMission().isCampaignMission()) {
                     Status.incMissionCount();
                  }

                  if (Status.getMission().isCampaignMission() && (!Status.getMission().isCampaignMission() || !Dialogue.sub_de(Status.getCurrentCampaignMission()))) {
                     return false;
                  }

                  if (this.var_7af == null) {
                     this.var_7af = new Dialogue();
                  }

                  this.var_7af.sub_f5(var1 != null ? var1 : Status.getMission(), 1);
                  this.var_55d = true;
                  this.var_53f = true;
               }

               return true;
            } else {
               Status.getMission().setType(11);
               Status.getMission().setTargetStation(Status.getMission().getAgent().getStationId());
               if (this.var_7af == null) {
                  this.var_7af = new Dialogue();
               }

               this.var_7af.sub_f5(Status.getMission(), 1);
               Status.getMission().setWon(false);
               String var2 = Status.replaceTokens(GameStatus.langManager.getLangString(439), Status.getMission().getTargetStationName(), "#S");
               Status.getMission().getAgent().setMessage(var2);
               Status.setMission(Mission.var_dc);
               this.var_630.var_b00 = null;
               this.var_630.var_b24 = null;
               this.var_55d = true;
               this.var_53f = true;
               return true;
            }
         }
      }

      return false;
   }

   private void sub_17e() {
      if (this.var_689.sub_123() && (Dialogue.sub_84(Status.getCurrentCampaignMission()) || !Status.getMission().isCampaignMission()) && !Status.getMission().isEmpty() && Status.getMission().getType() != 8 && Status.getMission().getType() != 0 && Status.getMission().sub_12c()) {
         if (!Status.getMission().isCampaignMission() && Status.getMission().getType() == 11) {
            return;
         }

         if (this.var_7af == null) {
            this.var_7af = new Dialogue(Status.getMission(), this.var_630, 0);
         }

         this.var_53f = true;
         this.var_55d = true;
      }

   }

   private boolean sub_1ce() {
      this.var_9d5 = this.var_630.sub_911(this.playerEgo.sub_8c1());
      this.var_a07 = this.var_630.sub_92f(this.playerEgo.sub_8c1());
      if (!Status.getMission().isEmpty() && Status.getMission().getType() != 11 && Status.getMission().getType() != 0 && Status.getMission().getType() != 23) {
         if ((this.var_a07 || this.var_9d5) && this.playerEgo.sub_a15()) {
            this.var_611.sub_30d(21, this.playerEgo);
         }

         return false;
      } else if (this.var_9d5 && this.playerEgo.sub_a43() && !this.playerEgo.sub_ea4() && !this.playerEgo.sub_eb2()) {
         this.playerEgo.sub_ff3(this.var_450, true);
         return false;
      } else {
         if (this.var_9d5) {
            Status.baseArmour = this.playerEgo.player.getHitpoints();
            Status.shield = this.playerEgo.player.sub_49f();
            Status.additionalArmour = this.playerEgo.player.sub_53c();
            if (this.playerEgo.sub_a15() && Level.autopilotDestination != null) {
               if (!this.jumpgateReached) {
                  if (this.popup == null) {
                     this.popup = new Popup();
                  }

                  this.popup.sub_8f(GameStatus.langManager.getLangString(295) + ": " + Level.autopilotDestination.getName() + "\n" + GameStatus.langManager.getLangString(242), true);
                  this.popup.sub_c6();
                  this.jumpgateReached = true;
                  this.var_53f = true;
                  this.playerEgo.sub_97e((KIPlayer)null);
               }

               return false;
            }

            if (this.var_976) {
               this.var_187 = 0L;
               return true;
            }

            if (this.playerEgo.sub_a43()) {
               if (Level.autopilotDestination != null) {
                  if (!this.jumpgateReached) {
                     if (this.popup == null) {
                        this.popup = new Popup();
                     }

                     this.popup.sub_8f(GameStatus.langManager.getLangString(295) + ": " + Level.autopilotDestination.getName() + "\n" + GameStatus.langManager.getLangString(242), true);
                     this.popup.sub_c6();
                     this.jumpgateReached = true;
                     this.var_53f = true;
                     this.playerEgo.sub_97e((KIPlayer)null);
                  }

                  return false;
               }

               if (this.starMap == null) {
                  this.starMap = new StarMap(false, (Mission)null, false, -1);
               }

               this.starMap.setJumpMapMode(true, false);
               this.playerEgo.sub_97e((KIPlayer)null);
               this.var_976 = true;
               return true;
            }
         } else if ((this.var_a07 || this.playerEgo.sub_998() == this.var_630.sub_5b7()[0] && this.playerEgo.sub_bc1()) && this.playerEgo.sub_a80() && !Status.inAlienOrbit()) {
            Medals.checkForNewMedal(this.playerEgo);
            GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
            return true;
         }

         return false;
      }
   }

   public final void sub_1da() {
      if (!this.var_53f) {
         if (this.var_75e == null) {
            this.var_75e = new OptionsWindow();
         }

         this.var_75e.sub_38c();
         this.var_53f = true;
      }

   }

   private boolean sub_20d() {
      int var1;
      if (this.var_c80) {
         this.var_d9c = this.var_d04.sub_216(this.var_d9c);
         this.var_3c0.sub_18f((int)this.frameTime * 3, 0, 0);
         this.var_3c0.sub_18f(0, 0, -((int)this.frameTime) << 1);
         var1 = (int)this.frameTime << 2;
         this.var_d04.sub_5c5(var1, var1, var1);
      } else {
         this.var_d9c = ((PlayerJumpgate)this.var_630.sub_5b7()[1]).sub_52(this.var_d9c);
         this.var_3c0.sub_18f((int)this.frameTime * 5, 0, 0);
         this.var_3c0.sub_18f(0, 0, -((int)this.frameTime) * 3);
      }

      if ((var1 = this.var_3c0.sub_31f()) < this.var_d9c.z - 2000) {
         if (!this.var_c80) {
            ((PlayerJumpgate)this.var_630.sub_5b7()[1]).sub_25();
         }

         if (!this.var_a4e) {
            GameStatus.soundManager.playSfx(7);
            this.var_a4e = true;
         }
      }

      if (this.var_c80 ? this.playerEgo.var_50e.sub_31f() > this.var_d9c.z - 1000 : this.var_630.sub_5b7()[1].var_25d.sub_942() > 60 && this.var_630.sub_5b7()[1].var_25d.sub_942() < 79) {
         if (!this.var_ce4 && this.var_c80) {
            this.var_d04.sub_9aa((byte)3);
            this.var_d04.sub_918(50);
            this.var_ce4 = true;
         }

         if (this.playerEgo.speed < 100) {
            PlayerEgo var10000 = this.playerEgo;
            var10000.speed += 5;
         }

         this.var_48b.sub_120(false);
      }

      if (this.var_c80 && this.var_ce4 && this.var_d04.sub_942() >= 20) {
         this.var_d04.sub_7af(0, 0, 0);
      }

      if (var1 < this.var_d9c.z - 15000) {
         Status.sub_1e5(Level.autopilotDestination);
         Level.sub_16a();
         if (!this.var_c80) {
            Status.jumpgateUsed();
         }

         if (Level.autopilotDestination.equals(Status.voidStation)) {
            Level.var_1297 = true;
            Level.var_12a2 = true;
            Status.setCurrentStation_andInitSystem_(Status.voidStation);
         }

         Level.autopilotDestination = null;
         Status.baseArmour = this.playerEgo.player.getHitpoints();
         Status.shield = this.playerEgo.player.sub_49f();
         Status.additionalArmour = this.playerEgo.player.sub_53c();
         GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
         return true;
      } else {
         return false;
      }
   }

   private void sub_225() {
      this.var_53f = false;
      this.var_c3b = true;
      this.var_58b = true;
      this.playerEgo.sub_534(false);
      this.var_450.sub_383(false);
      this.var_48b.sub_120(true);
      if (this.var_c80) {
         this.var_d04 = AEResourceManager.getGeometryResource(6783);
         this.var_d04.setRenderLayer(2);
         this.var_d9c = this.playerEgo.var_50e.sub_216(this.var_d9c);
         this.var_d9c.add(this.playerEgo.var_50e.getDirection());
         this.var_d04.sub_1f3(this.var_d9c);
         this.var_d04.sub_9aa((byte)1);
         this.var_d04.sub_918(150);
      } else {
         this.var_d9c = ((PlayerJumpgate)this.var_630.sub_5b7()[1]).sub_52(this.var_d9c);
      }

      AEVector3D var10000 = this.var_d9c;
      var10000.z -= 5000;
      this.playerEgo.sub_b32(this.var_d9c);
      this.playerEgo.var_50e.setRotation(0, 0, 0);
      this.var_48b.sub_36(this.playerEgo.var_50e);
      this.var_48b.sub_68(this.var_3c0);
      this.var_48b.sub_bf(new AEVector3D(0, 4096, 0), 1);
      var10000 = this.var_d9c;
      var10000.x -= 2000;
      var10000 = this.var_d9c;
      var10000.y += 300;
      var10000 = this.var_d9c;
      var10000.z += 4000;
      GameStatus.renderer.getCamera().sub_1f3(this.var_d9c);
   }

   public final void handleKeystate(int var1) {
      if (this.var_287) {
         if (this.var_53f) {
            if (this.actionMenuOpen) {
               this.actionMenuOpen = this.var_611.sub_527(var1, this.var_630, this.var_6d2);
               this.var_53f = this.actionMenuOpen;
               if (!this.var_53f) {
                  this.playerEgo.sub_de8();
                  if (this.var_611.sub_58b()) {
                     if (Status.inAlienOrbit()) {
                        Level.autopilotDestination = Galaxy.getStation(Status.lastVisitedNonVoidOrbit);
                        this.var_c80 = true;
                        this.sub_225();
                        this.var_53f = false;
                        this.var_611.sub_5df(false);
                        return;
                     }

                     if (this.starMap == null) {
                        this.starMap = new StarMap(false, (Mission)null, false, -1);
                     }

                     this.var_c80 = true;
                     this.starMap.setJumpMapMode(true, true);
                     if (!Status.inAlienOrbit()) {
                        this.starMap.askForJumpIntoAlienWorld();
                     }

                     this.var_976 = true;
                     this.var_53f = false;
                  }

                  this.var_611.sub_5df(false);
                  return;
               }
            }

            if (this.var_a7d && var1 == 256) {
               this.var_a7d = false;
               this.var_53f = false;
               this.var_7f9 = null;
               this.playerEgo.sub_de8();
               return;
            }

            if (this.var_55d) {
               if (!this.var_7af.sub_178(var1)) {
                  this.var_53f = false;
                  this.var_55d = false;
                  if (Status.getMission().hasFailed()) {
                     if (Status.getMission().isCampaignMission() || Status.getCurrentCampaignMission() == 42) {
                        GameStatus.var_bfb.setScene(GameStatus.scenes[0]);
                        GameStatus.soundManager.playMusic(0);
                        Status.startNewGame();
                        return;
                     }

                     if (Status.getMission().getType() == 12) {
                        Status.changeCredits(-Status.getMission().getReward());
                     } else {
                        Item[] var4;
                        if ((Status.getMission().getType() == 3 || Status.getMission().getType() == 5 || Status.getMission().getType() == 11) && (var4 = Status.getShip().getCargo()) != null) {
                           for(int var5 = 0; var5 < var4.length; ++var5) {
                              if (var4[var5].sub_95c() && var4[var5].getIndex() == 116 || var4[var5].getIndex() == 117) {
                                 Status.getShip().sub_755(var4[var5]);
                                 break;
                              }
                           }
                        }
                     }

                     Status.setFreelanceMission(Mission.var_dc);
                     this.var_630.sub_b30();
                     this.var_689.var_4c9 = 0;
                     Status.setMission(Mission.var_dc);
                     this.playerEgo.sub_5f1((Class_661)null);
                     if (this.playerEgo.sub_aa7()) {
                        this.playerEgo.sub_97e((KIPlayer)null);
                     }

                     this.playerEgo.sub_66d();
                     this.var_630.sub_6ce((Class_661)null);
                     if (!Status.inAlienOrbit()) {
                        this.var_865 = new AutoPilotList(this.var_630);
                     }
                  } else if (!Status.getMission().hasWon() && !Status.getCampaignMission().hasWon()) {
                     this.var_689.sub_10c();
                  } else {
                     boolean var3 = Status.getCampaignMission().hasWon() && !Status.getMission().isCampaignMission();
                     Mission var2;
                     if ((var2 = Status.getMission().hasWon() ? Status.getMission() : Status.getCampaignMission()).isInstantActionMission()) {
                        GameStatus.var_bfb.setScene(GameStatus.scenes[0]);
                        GameStatus.soundManager.playMusic(0);
                        return;
                     }

                     if (var2.isCampaignMission()) {
                        Status.nextCampaignMission();
                     } else {
                        Status.setFreelanceMission(Mission.var_dc);
                     }

                     Status.changeCredits(var2.getReward());
                     this.var_689.var_4c9 = 0;
                     if (var2.isCampaignMission() && Status.getCurrentCampaignMission() == 15) {
                        Status.setCurrentStation_andInitSystem_((new FileRead()).loadStation(98));
                        GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
                        return;
                     }

                     if (var2.isCampaignMission() && Status.getCurrentCampaignMission() == 22) {
                        GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
                        return;
                     }

                     if (var2.isCampaignMission() && Status.getCurrentCampaignMission() == 42) {
                        this.var_689.var_4c9 = 60000;
                        this.var_689.var_500 = 0L;
                        this.var_630.var_b24 = new Class_876(3, this.var_689.var_4c9, this.var_630);
                        this.var_630.var_b00 = new Class_876(3, this.var_689.var_4c9, this.var_630);
                     }

                     if (!var3) {
                        this.var_630.sub_b30();
                        Status.setMission(Mission.var_dc);
                        this.playerEgo.sub_5f1((Class_661)null);
                        if (this.playerEgo.sub_aa7()) {
                           this.playerEgo.sub_97e((KIPlayer)null);
                        }

                        this.playerEgo.sub_66d();
                        this.var_630.sub_6ce((Class_661)null);
                        if (!Status.inAlienOrbit()) {
                           this.var_865 = new AutoPilotList(this.var_630);
                        }
                     }
                  }

                  this.var_187 = 0L;
                  return;
               }
            } else {
               if (this.autopilotMenuOpen) {
                  if (var1 == 1024) {
                     this.autopilotMenuOpen = false;
                     this.var_53f = false;
                  }

                  if (var1 == 2 && this.autopilotMenuOpen) {
                     this.var_865.down();
                  }

                  if (var1 == 64 && this.autopilotMenuOpen) {
                     this.var_865.up();
                  }

                  if (var1 == 256) {
                     GameStatus.soundManager.playSfx(13);
                     this.autopilotMenuOpen = false;
                     if (this.playerEgo != null) {
                        this.playerEgo.sub_de8();
                     }

                     this.var_53f = false;
                     switch(this.var_865.getSelection()) {
                     case 0:
                        this.var_689.sub_24c();
                        return;
                     case 1:
                        this.playerEgo.sub_97e(this.var_630.sub_5b7()[1]);
                        this.var_611.sub_30d(12, this.playerEgo);
                        return;
                     case 2:
                        this.playerEgo.sub_97e(this.var_630.sub_5b7()[0]);
                        this.var_611.sub_30d(10, this.playerEgo);
                        return;
                     case 3:
                        this.playerEgo.sub_97e(this.var_630.sub_617());
                        this.var_611.sub_30d(14, this.playerEgo);
                        return;
                     case 4:
                        if (this.var_630.sub_6a6() != null) {
                           this.playerEgo.sub_97e(this.var_630.sub_6a6().sub_176());
                           this.var_611.sub_30d(13, this.playerEgo);
                        }
                     }
                  }

                  return;
               }

               if (this.jumpgateReached) {
                  if (var1 == 256) {
                     this.jumpgateReached = false;
                     if (this.popup.sub_9a()) {
                        if (this.playerEgo.isLookingBack()) {
                           this.playerEgo.sub_2b9(false);
                        }

                        this.sub_225();
                        this.playerEgo.sub_de8();
                        return;
                     }

                     if (this.starMap == null) {
                        this.starMap = new StarMap(false, (Mission)null, false, -1);
                     }

                     this.starMap.setJumpMapMode(true, false);
                     this.var_976 = true;
                     this.var_53f = false;
                  }

                  if (var1 == 4) {
                     this.popup.sub_c6();
                  }

                  if (var1 == 32) {
                     this.popup.sub_ff();
                     return;
                  }
               } else if (this.var_75e != null && !this.actionMenuOpen) {
                  this.var_75e.sub_401(var1);
                  if (var1 == 256 && this.var_75e.sub_148()) {
                     this.var_53f = false;
                     return;
                  }

                  if (var1 == 16384) {
                     this.var_75e.sub_2e7();
                  }

                  if (var1 == 8192 && this.var_75e.sub_2d7()) {
                     this.var_53f = false;
                  }

                  if (var1 == 4) {
                     this.var_75e.sub_23d();
                  }

                  if (var1 == 32) {
                     this.var_75e.sub_2c1();
                     return;
                  }
               }
            }
         } else if (this.var_976) {
            this.var_976 = this.starMap.sub_a9(var1);
            if (!this.var_976 && this.var_9d5) {
               this.playerEgo.sub_ff3(this.var_450, false);
               this.playerEgo.sub_97e((KIPlayer)null);
               this.playerEgo.var_50e.sub_85f().setOrientation(this.var_630.sub_5b7()[1].var_25d.getDirection());
               this.playerEgo.sub_b32(this.var_630.sub_5b7()[1].var_25d.sub_237());
               this.playerEgo.var_50e.sub_202(4096);
            } else if (this.starMap.var_1071) {
               this.var_976 = false;
               this.sub_225();
            }

            if (!this.var_976) {
               this.starMap.sub_98();
               this.starMap = null;
               return;
            }
         } else if (!this.egoDead) {
            if (var1 == 16384) {
               if (this.var_75e == null) {
                  this.var_75e = new OptionsWindow();
               }

               this.var_75e.sub_38c();
               this.var_53f = !this.var_53f;
               if (this.var_53f) {
                  return;
               }
            }

            if (!this.var_58b && Status.getCurrentCampaignMission() > 1) {
               if (!this.playerEgo.sub_be6()) {
                  this.actionMenuOpen = this.var_611.sub_527(var1, this.var_630, this.var_6d2);
                  this.var_53f = this.actionMenuOpen;
                  if (this.actionMenuOpen && !GameStatus.var_554) {
                     this.var_7f9 = new Dialogue(GameStatus.langManager.getLangString(321));
                     this.var_a7d = true;
                     GameStatus.var_554 = true;
                  }
               }

               if (this.var_53f) {
                  return;
               }

               if (var1 == 131072) {
                  this.var_b21 = !this.var_b21;
                  if (!this.playerEgo.sub_2b9(this.var_b21)) {
                     if (this.var_47 != 1) {
                        Class_7flight.sub_206(this.var_450);
                        this.var_47 = 1;
                     } else {
                        Class_7flight.sub_1b8(this.var_450, this.var_630);
                        this.var_47 = 0;
                     }
                  }
               }

               if (var1 == 256) {
                  if (this.var_6d2.targetedStation != null) {
                     if (!this.playerEgo.sub_a15()) {
                        this.playerEgo.sub_97e(this.var_6d2.targetedStation);
                        if (this.var_6d2.targetedStation.equals(this.var_630.sub_5b7()[0])) {
                           this.var_611.sub_30d(10, this.playerEgo);
                        } else if (this.var_6d2.targetedStation.equals(this.var_630.sub_5b7()[3])) {
                           this.var_611.sub_30d(15, this.playerEgo);
                        } else {
                           this.var_611.sub_30d(12, this.playerEgo);
                        }

                        GameStatus.soundManager.playSfx(13);
                     } else if (!this.playerEgo.isLookingBack()) {
                        this.var_611.sub_30d(6, this.playerEgo);
                        this.playerEgo.sub_97e((KIPlayer)null);
                        this.var_6d2.targetedStation = null;
                        this.var_6d2.contextStation = null;
                        this.playerEgo.sub_de8();
                     }
                  } else {
                     if (this.var_6d2.var_fd4 != null && !this.playerEgo.sub_f55()) {
                        if (Status.getCurrentCampaignMission() < 10) {
                           this.var_611.sub_30d(21, this.playerEgo);
                           return;
                        }

                        this.playerEgo.sub_f13(this.var_450);
                        this.playerEgo.sub_327();
                        this.playerEgo.sub_de8();
                        GameStatus.soundManager.playSfx(13);
                        return;
                     }

                     if (this.playerEgo.sub_be6()) {
                        if (this.playerEgo.sub_be6()) {
                           this.playerEgo.sub_10a5();
                        }
                     } else {
                        if (this.var_6d2.sub_1bd() != null && !this.playerEgo.sub_e45()) {
                           this.var_611.sub_30d(11, this.playerEgo);
                           GameStatus.soundManager.playSfx(13);
                        } else if (this.playerEgo.sub_e45()) {
                           this.var_611.sub_30d(6, this.playerEgo);
                        }

                        this.playerEgo.sub_113a(this.var_6d2.sub_1bd(), this.var_450, this.var_6d2);
                     }
                  }
               }

               if (var1 == 65536 && !this.playerEgo.isBoostActive()) {
                  this.playerEgo.sub_308();
                  this.var_611.sub_30d(3, this.playerEgo);
               }

               if (var1 == 32768 && this.playerEgo.player.sub_9e5(1)) {
                  this.playerEgo.sub_6c9((int)this.frameTime, 1);
               }

               if (var1 == 512 && Status.getCurrentCampaignMission() > 0) {
                  GameStatus.soundManager.playSfx(4);
                  this.isIntro = !this.isIntro;
                  this.var_611.sub_30d(this.isIntro ? 1 : 2, this.playerEgo);
               }

               if (var1 == 1024) {
                  GameStatus.soundManager.playSfx(4);
                  if (this.playerEgo.sub_a15()) {
                     this.playerEgo.sub_97e((KIPlayer)null);
                     this.var_611.sub_30d(6, this.playerEgo);
                     return;
                  }

                  if (!this.autopilotMenuOpen) {
                     if (this.playerEgo.sub_e45()) {
                        this.playerEgo.sub_113a(this.var_6d2.sub_1bd(), this.var_450, this.var_6d2);
                        this.var_611.sub_30d(6, this.playerEgo);
                        return;
                     }

                     if (this.playerEgo.sub_ea4()) {
                        this.playerEgo.sub_113a(this.var_6d2.targetedStation, this.var_450, this.var_6d2);
                        this.var_611.sub_30d(6, this.playerEgo);
                        return;
                     }

                     if (!Status.inAlienOrbit()) {
                        if (this.var_865 == null || this.var_630.sub_6a6() != null && this.var_630.sub_6a6().sub_1d7().var_35b) {
                           this.var_865 = new AutoPilotList(this.var_630);
                        }

                        this.autopilotMenuOpen = true;
                        this.var_53f = true;
                        return;
                     }
                  }
               }
            } else {
               if ((var1 == 4096 || var1 == 2048) && Status.getCurrentCampaignMission() < 2) {
                  Status.nextCampaignMission();
                  GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
                  return;
               }

               if (var1 == 256 && this.var_689.var_500 > 3000L) {
                  this.var_689.sub_aa();
                  this.playerEgo.sub_de8();
               }

               if (var1 == 65536 && !this.playerEgo.isBoostActive()) {
                  this.playerEgo.sub_308();
                  this.var_611.sub_30d(3, this.playerEgo);
                  return;
               }
            }
         } else if (this.var_209 > 3000L && var1 != 32 && var1 != 4 && var1 == 256) {
            GameStatus.var_bfb.setScene(GameStatus.scenes[0]);
            GameStatus.soundManager.playMusic(0);
            return;
         }

      }
   }
}
