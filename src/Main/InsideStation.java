package Main;

import AbyssEngine.AEImage;
import AbyssEngine.AEMath;
import AbyssEngine.AbstractScene;
import AbyssEngine.Dialogue;
import AbyssEngine.GameStatus;
import AbyssEngine.Generator;
import AbyssEngine.IndexManager;
import AbyssEngine.Item;
import AbyssEngine.Layout;
import AbyssEngine.Level;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.Popup;
import AbyssEngine.ProducedGood;
import AbyssEngine.RecordHandler;
import AbyssEngine.Standing;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;

public final class InsideStation extends AbstractScene {
   private long var_60;
   private long var_9f;
   private long var_d4;
   private long var_101;
   private long var_139;
   private int var_14a;
   private int[] var_179;
   private Image factionLogo;
   private Class_26fscene var_2bc;
   private OptionsWindow var_315;
   private boolean menuOpen;
   private boolean optionsOpen;
   private boolean var_3ba;
   private boolean missionsOpen;
   private boolean barOpen;
   private boolean hangarOpen;
   private boolean mapOpen;
   private boolean statusOpen;
   private boolean var_4f0;
   private boolean var_517;
   private boolean var_523;
   private boolean var_57e;
   private Popup bribeOffer;
   private Bar var_5bf;
   private Hangar var_643;
   private StatusBlock var_6ed;
   private MissionsWindow var_78a;
   private Dialogue var_7a6;
   private Dialogue help;
   private NewMedalPopup var_82e;
   public StarMap var_85d;
   private Mission var_899;
   private int menuPosX;
   private int menuPosY;
   private int menuWidth;
   private boolean var_9bb = false;
   private boolean var_9ec = false;
   private boolean var_a94;
   private int[][] var_abd;
   private int var_b11;
   private int var_b98;
   private int var_bcc;
   private int var_be8;
   private boolean var_c8c;
   private boolean var_ca1;
   private boolean var_ccc;
   private boolean loaded;

   public final Class_26fscene sub_4e() {
      return this.var_2bc;
   }

   public final void freeResources() {
      if (this.var_643 != null) {
         this.var_643.sub_e();
      }

      this.var_643 = null;
      this.var_179 = null;
      if (this.var_315 != null) {
         this.var_315.sub_e6();
      }

      this.var_315 = null;
      if (this.var_5bf != null) {
         this.var_5bf.sub_7e();
      }

      this.var_5bf = null;
      this.bribeOffer = null;
      if (this.var_2bc != null) {
         this.var_2bc.freeResources();
      }

      this.var_2bc = null;
      if (this.var_85d != null) {
         this.var_85d.sub_98();
      }

      this.var_85d = null;
      if (this.var_6ed != null) {
         this.var_6ed.sub_37();
      }

      this.var_6ed = null;
      this.loaded = false;
      this.factionLogo = null;
      System.gc();
   }

   public final void sub_a9() {
      this.var_9bb = true;
   }

   public final void loadResources() {
      if (this.var_2bc == null) {
         this.var_2bc = new Class_26fscene(23);
      }

      if (!this.var_2bc.isLoaded()) {
         this.var_2bc.loadResources();
      } else {
         this.var_b98 = 50;
         long var1 = Status.lastLoadingTime;
         Status.sub_1e5(Status.getStation());
         Item[] var2;
         int var3;
         String[] var5;
         int var6;
         if (Status.getPlayingTime() - var1 > 30000L) {
            new Generator();
            var5 = null;
            if ((var2 = Status.getStation().getShopItems()) != null) {
               for(var3 = 0; var3 < var2.length; ++var3) {
                  var6 = var2[var3].getAmount();
                  int var4;
                  if ((var4 = GameStatus.random.nextInt(3)) < var6) {
                     var2[var3].changeAmount(-var4);
                  }
               }
            }
         }

         Status.getStation().sub_1a2();
         Medals.applyNewMedals();
         this.var_3ba = false;
         this.optionsOpen = false;
         this.missionsOpen = false;
         this.barOpen = false;
         this.hangarOpen = false;
         this.mapOpen = false;
         this.menuOpen = true;
         this.statusOpen = false;
         this.var_57e = false;
         this.var_523 = false;
         this.var_179 = new int[6];

         for(var6 = 0; var6 < this.var_179.length; ++var6) {
            this.var_179[var6] = 0;
            if (var6 == this.var_14a) {
               this.var_179[var6] = 1;
            }
         }

         this.menuPosX = 1;
         this.menuPosY = GameStatus.screenHeight - 37 - this.var_179.length * 10;
         (var5 = new String[6])[0] = GameStatus.langManager.getLangString(62);
         var5[1] = GameStatus.langManager.getLangString(218);
         var5[2] = GameStatus.langManager.getLangString(72);
         var5[3] = GameStatus.langManager.getLangString(33);
         var5[4] = GameStatus.langManager.getLangString(64);
         var5[5] = GameStatus.langManager.getLangString(66);

         for(int var7 = 0; var7 < var5.length; ++var7) {
            if ((var3 = SymbolMapManager_.sub_25b(var5[var7], 1)) > this.menuWidth) {
               this.menuWidth = var3 + 20;
            }
         }

         this.var_bcc = 0;
         this.var_be8 = 0;
         this.var_c8c = false;
         this.var_ca1 = false;
         this.var_ccc = false;
         this.var_9ec = false;
         this.var_a94 = false;
         this.var_139 = 0L;
         this.bribeOffer = new Popup();
         this.factionLogo = AEImage.loadImage("/data/interface/logo_" + Status.getSystem().getRace() + ".png", true);
         if (!this.var_9bb) {
            if (!this.var_517 && this.help == null && !this.var_c8c && Status.getStanding().isEnemy(Status.getSystem().getRace())) {
               Standing var8 = Status.getStanding();
               var6 = (var3 = Status.getSystem().getRace()) != 1 && var3 != 0 ? 1 : 0;
               this.var_be8 = (int)((float)AEMath.abs(var8.getStanding(var6)) / 100.0F * 2800.0F);
               this.var_be8 += -100 + GameStatus.random.nextInt(200);
               String var9 = Status.replaceTokens(GameStatus.langManager.getLangString(85), "" + this.var_be8, "#C");
               this.bribeOffer.sub_8f(var9, true);
               this.var_57e = true;
               this.var_3ba = true;
            }

            if (Status.getCurrentCampaignMission() == 20 && Status.getCampaignMission().setCampaignMission() == Status.getStation().getId()) {
               var2 = Status.getStation().getShopItems();

               for(var3 = 0; var3 < var2.length; ++var3) {
                  if (var2[var3].getIndex() == 41) {
                     var2[var3].setPrice(0);
                  }
               }

               Status.getStation().sub_334(IndexManager.getItems()[41].getCopyInAmmount(10, 0));
            }

            if (Status.getCurrentCampaignMission() == 27 && Status.getCampaignMission().setCampaignMission() == Status.getStation().getId()) {
               Status.getShip().sub_7a6(131);
            }
         }

         if (!this.var_9ec && !this.var_9bb) {
            (new RecordHandler()).sub_115(3);
            this.var_9ec = true;
         }

         Level.autopilotDestination = null;
         if (Status.getCurrentCampaignMission() == 1) {
            Status.setShip(IndexManager.getShips()[0]);
            Status.getShip().setRace(8);
            Item var10;
            (var10 = IndexManager.getItems()[90].singleCopy()).sub_986(true);
            Status.getShip().setEquipment(var10, 0);
            Item var11 = IndexManager.getItems()[81].singleCopy();
            Status.getShip().setEquipment(var11, 1);
            var11.sub_986(true);
            this.var_2bc.sub_7c(Status.getShip().getIndex(), 8);
         }

         GameStatus.soundManager.playMusic(1);
         this.var_101 = 0L;
         System.gc();
         this.var_60 = System.currentTimeMillis();
         this.var_9f = System.currentTimeMillis();
         this.loaded = true;
      }
   }

   private void sub_f0() {
      if (!this.var_517 && !this.var_523) {
         this.var_abd = null;
         int[] var1 = Medals.getNewMedals();
         int var2 = 0;

         int var3;
         for(var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] > 0) {
               ++var2;
            }
         }

         if (var2 > 0) {
            this.var_abd = new int[var2][2];
            var2 = 0;

            for(var3 = 0; var3 < var1.length; ++var3) {
               if (var1[var3] > 0) {
                  this.var_abd[var2++] = new int[]{var3, var1[var3]};
               }
            }

            this.var_517 = true;
            this.var_b11 = 0;
            this.var_82e = new NewMedalPopup();
            this.var_82e.sub_49(this.var_abd[0][0], this.var_abd[0][1]);
         }

      } else {
         ++this.var_b11;
         if (this.var_b11 >= this.var_abd.length) {
            this.var_517 = false;
         } else {
            this.var_82e.sub_49(this.var_abd[this.var_b11][0], this.var_abd[this.var_b11][1]);
         }
      }
   }

   public final boolean isLoaded() {
      return this.loaded;
   }

   public final void renderScene(int var1) {
      if (this.loaded) {
         Layout.sub_4cc(false);
         this.var_60 = System.currentTimeMillis();
         this.var_d4 = this.var_60 - this.var_9f;
         this.var_9f = this.var_60;
         this.var_101 += this.var_d4;
         this.var_139 += this.var_d4;
         Status.incPlayingTime(this.var_d4);
         Medals.updateMaxCredits(Status.getCredits());
         if (!this.missionsOpen && !this.barOpen && !this.hangarOpen && !this.mapOpen && !this.statusOpen && this.var_2bc != null) {
            this.var_2bc.renderScene(!this.optionsOpen && !this.var_57e && !this.var_523 && !this.var_3ba ? var1 : 0);
         }

         if (this.var_139 < 6000L) {
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(this.var_9bb ? 32 : 28), 5, GameStatus.screenHeight - 16 - SymbolMapManager_.sub_2c2(), 1);
         } else {
            this.var_9bb = false;
         }

         if (this.var_139 >= 6000L) {
            if (!this.var_3ba && !this.var_4f0) {
               if (!this.var_ccc) {
                  InsideStation var2 = this;
                  String var3 = GameStatus.langManager.getLangString(92) + "\n";
                  ProducedGood[] var4;
                  if ((var4 = Status.getWaitingGoods()) != null) {
                     for(int var5 = 0; var5 < var4.length; ++var5) {
                        ProducedGood var6;
                        if ((var6 = var4[var5]) != null && var6.stationId == Status.getStation().getId()) {
                           Item var9 = IndexManager.getItems()[var6.index].anyAmountCopies(var6.producedQuantity);
                           Status.getShip().addItem(var9);
                           var3 = var3 + "\n" + var9.getAmount() + "x " + GameStatus.langManager.getLangString(569 + var9.getIndex());
                           var4[var5] = null;
                           var2.var_3ba = true;
                        }
                     }
                  }

                  if (var2.var_3ba) {
                     var2.bribeOffer.sub_8f(var3, false);
                  }

                  this.var_ccc = true;
               }

               if (!this.var_ca1) {
                  this.sub_f0();
                  this.var_ca1 = true;
               }

               if (!this.var_517 && this.help == null && !GameStatus.fanWingmenNoticeShown && Medals.gotAllMedals()) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(325));
                  GameStatus.fanWingmenNoticeShown = true;
                  this.var_523 = true;
               }

               if (!this.var_517 && this.help == null && !GameStatus.voidxNoticeShown && Medals.gotAllGoldMedals()) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(326));
                  GameStatus.voidxNoticeShown = true;
                  this.var_523 = true;
               }
            }

            if (!this.var_3ba && !this.var_4f0) {
               Mission var7;
               if ((var7 = Status.missionCompleted_(true, 0L)) != null) {
                  this.var_899 = var7;
               }

               if (var7 != null) {
                  this.var_7a6 = new Dialogue(var7, (Level)null, 1);
                  this.var_4f0 = true;
               }
            }

            int var8 = var1;
            if (this.var_523) {
               var8 = 0;
            }

            if (this.var_4f0) {
               if (this.hangarOpen || this.barOpen) {
                  Layout.screenFillMenuBackground();
               }

               this.var_7a6.sub_1c3(var8, (int)this.var_d4);
               this.var_7a6.sub_304();
            } else if (this.optionsOpen) {
               this.var_315.sub_12b(var8, (int)this.var_d4);
               this.var_315.sub_3a2();
            } else if (this.missionsOpen) {
               Layout.addTicks((int)this.var_d4);
               this.var_78a.sub_bc(var8, (int)this.var_d4);
            } else if (this.barOpen) {
               Layout.addTicks((int)this.var_d4);
               this.var_5bf.sub_107(var8, (int)this.var_d4);
            } else if (this.hangarOpen) {
               Layout.addTicks((int)this.var_d4);
               this.var_643.sub_1a0(var8, (int)this.var_d4);
            } else if (this.mapOpen) {
               this.var_85d.sub_101(var8, (int)this.var_d4);
               if (this.var_85d != null && this.var_85d.sub_1ae() && this.help == null && !GameStatus.systemMapHelpShown) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(316));
                  GameStatus.systemMapHelpShown = true;
                  this.var_523 = true;
               }
            } else if (this.statusOpen) {
               Layout.addTicks((int)this.var_d4);
               this.var_6ed.sub_d8(var8, (int)this.var_d4);
            } else {
               Layout.addTicks((int)this.var_d4);
               GameStatus.graphics.drawImage(this.factionLogo, 3, 3, 0);
               SymbolMapManager_.sub_161(Status.getStation().getName(), this.factionLogo.getWidth() + 6, 3, 0, 17);
               SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(37) + ": " + Status.getStation().getTecLevel(), this.factionLogo.getWidth() + 6, 13, 1, 17);
               SymbolMapManager_.sub_161(Layout.formatCredits(Status.getCredits()), GameStatus.screenWidth - 3, GameStatus.screenHeight - 16 - 2, 1, 34);
               if (this.menuOpen) {
                  Layout.drawFilledTitleBarWindow("", this.menuPosX, this.menuPosY, this.menuWidth, 15 + (this.var_179.length + 1) * 10 - 5);
                  Layout.sub_1df(GameStatus.langManager.getLangString(62), this.menuPosX, this.menuPosY + 14, this.menuWidth, this.var_179[0] == 1, false, true);
                  Layout.sub_1df(GameStatus.langManager.getLangString(218), this.menuPosX, this.menuPosY + 14 + 10, this.menuWidth, this.var_179[1] == 1, false, true);
                  Layout.sub_1df(GameStatus.langManager.getLangString(72), this.menuPosX, this.menuPosY + 14 + 20, this.menuWidth, this.var_179[2] == 1, false, true);
                  Layout.sub_1df(GameStatus.langManager.getLangString(33), this.menuPosX, this.menuPosY + 14 + 30, this.menuWidth, this.var_179[3] == 1, false, true);
                  Layout.sub_1df(GameStatus.langManager.getLangString(64), this.menuPosX, this.menuPosY + 14 + 40, this.menuWidth, this.var_179[4] == 1, false, true);
                  Layout.sub_1df(GameStatus.langManager.getLangString(66), this.menuPosX, this.menuPosY + 14 + 50, this.menuWidth, this.var_179[5] == 1, false, true);
               } else if (!this.var_4f0 && !this.hangarOpen && !this.mapOpen && !this.optionsOpen && !this.missionsOpen && !this.var_3ba && !this.var_517 && !this.statusOpen) {
                  Layout.addTicks((int)this.var_d4);
                  Layout.sub_4cc(Layout.sub_108());
               }

               if (this.var_517) {
                  Layout.sub_3c6("", "");
                  this.var_82e.sub_006();
               } else if (this.var_3ba) {
                  Layout.sub_3c6("", "");
                  this.bribeOffer.sub_19a();
               } else {
                  Layout.sub_3c6(GameStatus.langManager.getLangString(67), Status.getCurrentCampaignMission() == 2 && Layout.sub_119() ? "" : GameStatus.langManager.getLangString(239));
               }
            }

            if (this.var_523) {
               if (this.help == null) {
                  this.var_523 = false;
                  return;
               }

               this.help.sub_1c3(var1, (int)this.var_d4);
               this.help.sub_364();
            }

         }
      }
   }

   public final void handleKeystate(int var1) {
      if (this.loaded) {
         Status.checkForLevelUp();
         if (this.var_139 < 6000L) {
            if (var1 == 256) {
               this.var_139 = 6001L;
            }

         } else if (this.var_523) {
            if (var1 == 256) {
               this.var_523 = false;
               this.help = null;
            }

         } else {
            int var2;
            Item[] var4;
            if (this.var_4f0) {
               if (!this.var_7a6.sub_178(var1)) {
                  if (this.var_899.getType() == 8) {
                     if (!this.var_899.isCampaignMission()) {
                        Status.getShip().sub_7fc(this.var_899.getCommodityIndex(), this.var_899.getCommodityAmount());
                        if (this.var_643 != null) {
                           this.var_643.sub_3f();
                        }
                     }
                  } else if (!this.var_899.isCampaignMission() && this.var_899.getType() == 11) {
                     Status.sub_236(0);
                     Status.var_f13 += this.var_899.getCommodityAmount();
                     if ((var4 = Status.getShip().getCargo()) != null) {
                        for(var2 = 0; var2 < var4.length; ++var2) {
                           if (var4[var2].sub_95c() && var4[var2].getIndex() == 116 || var4[var2].getIndex() == 117) {
                              Status.getShip().sub_755(var4[var2]);
                              if (this.var_643 != null) {
                                 this.var_643.sub_3f();
                              }
                              break;
                           }
                        }
                     }
                  } else if (this.var_899.getType() != 3 && this.var_899.getType() != 5 && this.var_899.getType() != 11 && this.var_899.getType() == 0) {
                     if ((var4 = Status.getShip().getCargo()) != null) {
                        for(var2 = 0; var2 < var4.length; ++var2) {
                           if (var4[var2].sub_95c() && var4[var2].getIndex() == 116) {
                              Status.getShip().sub_755(var4[var2]);
                              if (this.var_643 != null) {
                                 this.var_643.sub_3f();
                              }
                              break;
                           }
                        }
                     }

                     Status.var_d88 += this.var_899.getCommodityAmount();
                  }

                  if (this.var_899.isCampaignMission()) {
                     Status.nextCampaignMission();
                     if (Status.getCurrentCampaignMission() == 9 || Status.getCurrentCampaignMission() == 44) {
                        Status.sub_70a(this.var_899);
                        Status.setMission(Mission.var_dc);
                        this.var_4f0 = false;
                        GameStatus.var_bfb.setScene(this);
                        return;
                     }

                     if (Status.getCurrentCampaignMission() == 18) {
                        this.var_2bc.sub_7c(Status.getShip().getIndex(), 0);
                        Status.sub_70a(this.var_899);
                        this.var_4f0 = false;
                        return;
                     }
                  } else {
                     Status.incMissionCount();
                  }

                  Status.changeCredits(this.var_899.getReward());
                  Status.sub_70a(this.var_899);
                  this.var_4f0 = false;
               }
            } else if (this.barOpen) {
               if (!this.var_5bf.sub_c7(var1)) {
                  this.var_2bc.sub_2c();
                  this.barOpen = false;
                  GameStatus.soundManager.playMusic(1);
               }

            } else if (this.hangarOpen) {
               if (!GameStatus.var_264 && var1 == 4 && this.var_643.sub_be() && this.var_643.sub_65() == 1) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(310));
                  GameStatus.var_264 = true;
                  this.var_523 = true;
               } else if (!GameStatus.var_2cc && var1 == 32 && this.var_643.sub_be() && this.var_643.sub_65() == 1) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(312));
                  GameStatus.var_2cc = true;
                  this.var_523 = true;
               } else if (!GameStatus.var_2e8 && var1 == 256 && this.var_643.sub_be() && this.var_643.sub_65() == 2) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(313));
                  GameStatus.var_2e8 = true;
                  this.var_523 = true;
               } else if (!GameStatus.var_2b3 && var1 == 256 && this.var_643.sub_107() != null && this.var_643.sub_107().isItem() && this.var_643.sub_be() && this.var_643.sub_65() == 0) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(311));
                  GameStatus.var_2b3 = true;
                  this.var_523 = true;
               }

               if (!this.var_643.sub_16b(var1)) {
                  var1 = 0;
                  Item[] var6;
                  if ((var6 = Status.getShip().getCargo()) != null) {
                     for(int var3 = 0; var3 < var6.length; ++var3) {
                        if (var6[var3].getIndex() >= 132 && var6[var3].getIndex() < 154) {
                           var1 += var6[var3].getAmount();
                        }
                     }
                  }

                  if (var1 > this.var_bcc) {
                     Status.var_e59 += var1 - this.var_bcc;
                  }

                  this.var_2bc.sub_2c();
                  this.hangarOpen = false;
               }

            } else if (this.mapOpen) {
               if (!this.var_85d.sub_a9(var1)) {
                  this.mapOpen = false;
               }

            } else if (this.missionsOpen) {
               if (!this.var_78a.sub_5c(var1)) {
                  this.missionsOpen = false;
               }

            } else if (this.statusOpen) {
               if (!GameStatus.medalsHelpShown && var1 == 32) {
                  this.help = new Dialogue(GameStatus.langManager.getLangString(323));
                  GameStatus.medalsHelpShown = true;
                  this.var_523 = true;
               }

               if (!this.var_6ed.sub_8f(var1)) {
                  this.statusOpen = false;
               }

            } else if (this.var_517) {
               if (var1 == 256) {
                  this.sub_f0();
               }

            } else if (this.var_3ba) {
               if (var1 == 4) {
                  this.bribeOffer.sub_c6();
               }

               if (var1 == 32) {
                  this.bribeOffer.sub_ff();
               }

               if (var1 == 256) {
                  if (this.bribeOffer.sub_9a() && this.var_a94 || !this.bribeOffer.sub_9a() && this.var_a94 && this.var_57e) {
                     this.var_a94 = false;
                     Status.sub_1e5(Status.getStation());
                     Status.baseArmour = -1;
                     Status.shield = -1;
                     Status.additionalArmour = -1;
                     Medals.resetNewMedals();
                     GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
                     return;
                  }

                  if (this.var_57e) {
                     if (this.bribeOffer.sub_9a()) {
                        if (Status.getCredits() < this.var_be8) {
                           String var5 = Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(this.var_be8 - Status.getCredits()), "#C");
                           this.bribeOffer.sub_8f(var5, false);
                           this.var_a94 = true;
                           this.var_57e = true;
                           this.var_3ba = true;
                           return;
                        }

                        Status.changeCredits(-this.var_be8);
                        this.var_c8c = true;
                        this.var_57e = false;
                        this.var_3ba = false;
                     } else {
                        this.var_a94 = false;
                        Status.sub_1e5(Status.getStation());
                        Status.baseArmour = -1;
                        Status.shield = -1;
                        Status.additionalArmour = -1;
                        Medals.resetNewMedals();
                        GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
                     }
                  }

                  this.var_3ba = false;
               }

            } else {
               if (this.menuOpen && !this.optionsOpen) {
                  if (var1 == 256 && !this.var_4f0 && !this.var_517) {
                     switch(this.var_14a) {
                     case 0:
                        if (Status.getCurrentCampaignMission() < 5) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(257));
                           this.var_523 = true;
                           return;
                        }

                        if (this.var_643 == null) {
                           this.var_643 = new Hangar();
                           this.var_643.sub_3f();
                        }

                        if (this.var_5bf != null && this.var_5bf.sub_1b2()) {
                           this.var_643.sub_3f();
                           this.var_5bf.sub_1db(false);
                        } else if (this.var_78a != null && this.var_78a.sub_162()) {
                           this.var_643.sub_3f();
                           this.var_78a.sub_179(false);
                        }

                        this.var_bcc = 0;
                        if ((var4 = Status.getShip().getCargo()) != null) {
                           for(var2 = 0; var2 < var4.length; ++var2) {
                              if (var4[var2].getIndex() >= 132 && var4[var2].getIndex() < 154) {
                                 this.var_bcc += var4[var2].getAmount();
                              }
                           }
                        }

                        this.hangarOpen = true;
                        if (this.help == null && !GameStatus.shopHelpShown) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(309));
                           GameStatus.shopHelpShown = true;
                           this.var_523 = true;
                        }

                        return;
                     case 1:
                        if (Status.getCurrentCampaignMission() < 13) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(257));
                           this.var_523 = true;
                           return;
                        }

                        if (this.var_5bf == null) {
                           this.var_5bf = new Bar();
                        } else {
                           this.var_5bf.sub_5d();
                        }

                        GameStatus.soundManager.playMusic(2);
                        this.barOpen = true;
                        this.var_101 = -150L;
                        if (this.help == null && !GameStatus.barHelpShown) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(314));
                           GameStatus.barHelpShown = true;
                           this.var_523 = true;
                        }

                        return;
                     case 2:
                        if (Status.getCurrentCampaignMission() < 9) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(257));
                           this.var_523 = true;
                           return;
                        }

                        if (Status.getShip().getCurrentLoad() > Status.getShip().getCargoPlus()) {
                           this.bribeOffer.sub_34(GameStatus.langManager.getLangString(84));
                           this.var_3ba = true;
                           return;
                        }

                        if (this.var_85d == null) {
                           this.var_85d = new StarMap(false, (Mission)null, false, -1);
                        } else {
                           this.var_85d.sub_4e(false, (Mission)null, false, -1);
                        }

                        if (Status.getShip().isSimilarEquiped(85)) {
                           this.var_85d.setJumpMapMode(false, true);
                        }

                        this.mapOpen = true;
                        if (this.help == null && !GameStatus.galaxyMapHelpShown && Status.getCurrentCampaignMission() >= 16) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(315));
                           GameStatus.galaxyMapHelpShown = true;
                           this.var_523 = true;
                           return;
                        }

                        if (this.help == null && !GameStatus.systemMapHelpShown && Status.getCurrentCampaignMission() < 16) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(316));
                           GameStatus.systemMapHelpShown = true;
                           this.var_523 = true;
                        }

                        return;
                     case 3:
                        if (Status.getCurrentCampaignMission() < 13) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(257));
                           this.var_523 = true;
                           return;
                        }

                        if (this.var_78a == null) {
                           this.var_78a = new MissionsWindow();
                        } else {
                           this.var_78a.sub_2e();
                        }

                        this.missionsOpen = true;
                        if (this.help == null && !GameStatus.missionsHelpShown) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(318));
                           GameStatus.missionsHelpShown = true;
                           this.var_523 = true;
                        }

                        return;
                     case 4:
                        if (this.var_6ed == null) {
                           this.var_6ed = new StatusBlock();
                        }

                        this.statusOpen = true;
                        this.var_6ed.sub_6d();
                        if (this.help == null && !GameStatus.statusHelpShown) {
                           this.help = new Dialogue(GameStatus.langManager.getLangString(322));
                           GameStatus.statusHelpShown = true;
                           this.var_523 = true;
                        }

                        return;
                     case 5:
                        if (this.var_315 == null) {
                           this.var_315 = new OptionsWindow();
                        }

                        this.optionsOpen = true;
                        this.var_315.sub_33e(2);
                        return;
                     }
                  }

                  if (var1 == 64) {
                     this.var_179[this.var_14a] = 0;
                     if (this.var_14a < this.var_179.length - 1) {
                        ++this.var_14a;
                     } else {
                        this.var_14a = 0;
                     }

                     this.var_179[this.var_14a] = 1;
                  }

                  if (var1 == 2) {
                     this.var_179[this.var_14a] = 0;
                     if (this.var_14a > 0) {
                        --this.var_14a;
                     } else {
                        this.var_14a = this.var_179.length - 1;
                     }

                     this.var_179[this.var_14a] = 1;
                  }

                  if (var1 == 16384) {
                     this.menuOpen = !this.menuOpen;
                     return;
                  }
               }

               if (!this.optionsOpen && var1 == 8192) {
                  this.sub_161();
               } else if (this.optionsOpen) {
                  this.var_315.sub_401(var1);
                  if (this.var_315 != null) {
                     if (var1 == 4) {
                        this.var_315.sub_23d();
                     }

                     if (var1 == 32) {
                        this.var_315.sub_2c1();
                     }

                     if (var1 == 2) {
                        this.var_315.sub_1e5((int)this.var_d4);
                     }

                     if (var1 == 64) {
                        this.var_315.sub_215((int)this.var_d4);
                     }

                     if (var1 == 16384) {
                        this.var_315.sub_2e7();
                     }

                     if (var1 == 8192 && this.var_315.sub_2d7()) {
                        this.optionsOpen = !this.optionsOpen;
                        this.var_315.sub_33e(2);
                     }

                     if (var1 == 256 && !this.var_4f0 && !this.var_517 && this.var_315.sub_148()) {
                        this.optionsOpen = false;
                     }

                  }
               } else {
                  if (var1 == 16384) {
                     this.menuOpen = !this.menuOpen;
                  }

               }
            }
         }
      }
   }

   private void sub_161() {
      if (Status.getShip().getCurrentLoad() > Status.getShip().getCargoPlus()) {
         Popup var10000 = this.bribeOffer;
         String var2 = GameStatus.langManager.getLangString(84);
         var10000.sub_8f(var2, false);
         this.var_3ba = true;
      } else if (Status.getCurrentCampaignMission() == 6 || Status.getCurrentCampaignMission() == 7 && Status.getShip().getFirePower() == 0 && Status.getShip().getCombinedHP() == Status.getShip().getBaseHP()) {
         if (!Status.getShip().sub_6fa() && !Status.getShip().sub_6fa()) {
            this.help = new Dialogue(GameStatus.langManager.getLangString(258));
         } else {
            this.help = new Dialogue(GameStatus.langManager.getLangString(259));
         }

         this.var_523 = true;
      } else if (Status.getCurrentCampaignMission() == 20 && Status.getStation().getId() == Status.getCampaignMission().setCampaignMission()) {
         this.help = new Dialogue(GameStatus.langManager.getLangString(260));
         this.var_523 = true;
      } else {
         if (Status.getCurrentCampaignMission() == 21 && Status.getStation().getId() == Status.getCampaignMission().setCampaignMission()) {
            Status.getShip().getEquipment(1);
            if (Status.getShip().isSimilarEquiped(41)) {
               if (!this.var_3ba && !this.var_4f0 && !this.var_517) {
                  this.bribeOffer.sub_8f(GameStatus.langManager.getLangString(217), true);
                  this.bribeOffer.sub_c6();
                  this.var_3ba = true;
                  this.var_a94 = true;
               }

               return;
            }

            this.help = new Dialogue(GameStatus.langManager.getLangString(260));
            this.var_523 = true;
         } else if (!this.var_3ba && !this.var_4f0 && !this.var_517) {
            this.bribeOffer.sub_8f(GameStatus.langManager.getLangString(217), true);
            this.bribeOffer.sub_c6();
            this.var_3ba = true;
            this.var_a94 = true;
         }

      }
   }
}
