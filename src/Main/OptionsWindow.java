package Main;

import AbyssEngine.AEMath;
import AbyssEngine.Galaxy;
import AbyssEngine.GameRecord;
import AbyssEngine.GameStatus;
import AbyssEngine.LangManager;
import AbyssEngine.Layout;
import AbyssEngine.LoadingScreen;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.Popup;
import AbyssEngine.RecordHandler;
import AbyssEngine.SoundManager;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import AbyssEngine.TextBox;
import AbyssEngine.TextInput_;
import AbyssEngine.Time;
import javax.microedition.lcdui.Image;

public final class OptionsWindow {
   private static int var_191;
   private static int var_251;
   private static int var_2a3;
   private static int[] listEntriesCounts_ = new int[]{6, 4, 3, 4, 3, 3, 11, 0, 0, 2, 12, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   private Image var_34e;
   private int var_373;
   private int var_3be;
   private int selectedRow;
   private int subMenu;
   private String var_467;
   private Popup choiceConfirm;
   private boolean confirmPopupOpen;
   private boolean var_600;
   private GameRecord[] saves;
   private String var_696;
   private Class_1e2 var_6eb;
   private TextBox about;
   private TextBox instructions;
   private TextBox controls;
   private TextBox var_7bc;
   private TextInput_ var_817;
   private int frameTime;
   private int musicLevel;
   private int soundLevel;
   private int var_911;
   private boolean webConncetionRelatedConfirm_ = false;
   private String var_992 = null;
   private String var_a15 = null;
   private String var_a9e = null;
   private int var_ad1 = -1;
   private boolean var_b06 = false;
   private Image var_b34 = null;
   private int var_b51;

   public OptionsWindow() {
      this.sub_33();
      if (this.webConncetionRelatedConfirm_) {
         listEntriesCounts_[0] = 7;
      }

      this.var_34e = LoadingScreen.sub_4a();
      var_251 = this.var_34e.getHeight() + 25;
      this.var_b51 = var_251 + 16;
      var_2a3 = 117;
      var_191 = GameStatus.screenWidth - var_2a3 >> 1;
      this.var_373 = SymbolMapManager_.sub_2c2();
      this.selectedRow = 0;
      this.subMenu = 0;
      this.choiceConfirm = new Popup(20, GameStatus.screenHeight / 2, GameStatus.screenWidth - 40);
      this.confirmPopupOpen = false;
      this.var_600 = false;
      this.about = new TextBox(var_191 / 2 + 8, this.var_b51, var_2a3 + var_191 - 16, GameStatus.screenHeight - var_251 - 48, GameStatus.langManager.getLangString(23) + "\n\n" + GameStatus.langManager.getLangString(25));
      this.instructions = new TextBox(var_191 / 2 + 8, this.var_b51, var_2a3 + var_191 - 16, GameStatus.screenHeight - var_251 - 48, "");
      this.controls = new TextBox(var_191 / 2 + 8, this.var_b51, var_2a3 + var_191 - 16, GameStatus.screenHeight - var_251 - 48, GameStatus.langManager.getLangString(22));
      this.var_7bc = new TextBox(var_191 + 8, this.var_b51, var_2a3 - 16, GameStatus.screenHeight - var_251 - 48, "");
      String[] var1 = new String[LangManager.helpFull.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = GameStatus.langManager.getLangString(LangManager.helpTitles[var2]);
      }

      this.var_6eb = new Class_1e2(var_191, var_251 + 2, var_2a3, GameStatus.screenHeight - var_251 - 16 - 10, (String[])null);
      this.var_6eb.sub_8b(12);
      this.var_6eb.sub_16a(0, var1);
      this.var_6eb.sub_391(true);
      this.var_6eb.sub_346(false);
      this.var_6eb.sub_3ac();
      this.var_817 = new TextInput_(10);
      this.musicLevel = GameStatus.musicVolume;
      this.soundLevel = GameStatus.sfxVolume;
      listEntriesCounts_[12] = 2;
   }

   private static String sub_23(String var0) {
      int var1 = 0;
      int var2 = var0.length();
      StringBuffer var3 = new StringBuffer();

      while(true) {
         while(var1 < var2) {
            char var4;
            if ((var4 = var0.charAt(var1)) == '\\' && var1 + 6 <= var2 && var0.charAt(var1 + 1) == 'u') {
               var4 = (char)Integer.parseInt(var0.substring(var1 + 2, var1 + 6), 16);
               var3.append(var4);
               var1 += 6;
            } else {
               var3.append(var4);
               ++var1;
            }
         }

         return var3.toString();
      }
   }

   private void sub_33() {
      try {
         try {
            this.var_992 = GameStatus.midlet.getAppProperty("Operator-URL-ru");
            this.var_a9e = GameStatus.midlet.getAppProperty("Operator-Menu-ru");
         } catch (Exception var10) {
            this.webConncetionRelatedConfirm_ = false;
            this.var_992 = null;
            this.var_a15 = null;
            this.var_a9e = null;

            try {
               this.var_992 = GameStatus.midlet.getAppProperty("Operator-URL-gn");
               this.var_a9e = GameStatus.midlet.getAppProperty("Operator-Menu-gn");
            } catch (Exception var9) {
               this.webConncetionRelatedConfirm_ = false;
               return;
            }

            if (this.var_992 == null || this.var_a9e == null) {
               this.webConncetionRelatedConfirm_ = false;
               return;
            }
         }

         if (this.var_992 == null || this.var_a9e == null) {
            try {
               this.var_992 = GameStatus.midlet.getAppProperty("Operator-URL-gn");
               this.var_a9e = GameStatus.midlet.getAppProperty("Operator-Menu-gn");
            } catch (Exception var8) {
               this.webConncetionRelatedConfirm_ = false;
               return;
            }
         }

         try {
            this.var_a15 = GameStatus.midlet.getAppProperty("Operator-Prompt-ru");
         } catch (Exception var7) {
            this.var_a15 = null;

            try {
               this.var_a15 = GameStatus.midlet.getAppProperty("Operator-Prompt-gn");
            } catch (Exception var6) {
            }
         }

         if (this.var_a15 == null) {
            try {
               this.var_a15 = GameStatus.midlet.getAppProperty("Operator-Prompt-gn");
            } catch (Exception var5) {
            }
         }

         if (this.var_a15 != null) {
            this.var_a15 = sub_23(this.var_a15);
         }

         if (this.var_a9e != null) {
            this.var_a9e = sub_23(this.var_a9e);
         }

         System.out.println("wap_url: " + this.var_992);
         System.out.println("wap_menu: " + this.var_a9e);
         System.out.println("wap_prompt: " + this.var_a15);
         String var1 = GameStatus.midlet.getAppProperty("Operator-Mode");
         if (this.var_992 != null && var1 != null) {
            char[] var2 = new char[]{'1', '2'};

            for(int var3 = 0; var3 < var2.length; ++var3) {
               int var4;
               if ((var4 = var1.indexOf(String.valueOf(var2[var3]))) >= 0) {
                  this.var_ad1 = Integer.valueOf(String.valueOf(var1.charAt(var4))).intValue();
               }
            }

            System.out.println("wap_mode: " + this.var_ad1);
            if (this.var_ad1 < 1 || this.var_ad1 > 2) {
               this.var_ad1 = 2;
            }

            this.webConncetionRelatedConfirm_ = true;
         } else {
            throw new Exception();
         }
      } catch (Exception var11) {
         this.webConncetionRelatedConfirm_ = false;
      }
   }

   private void sub_70(GameRecord[] var1) {
      this.var_911 = 0;
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.var_467 = var2 + 1 + ".  " + (var1[var2] == null ? GameStatus.langManager.getLangString(26) : Time.timeToHM(var1[var2].playTime) + (var2 == 3 ? " AUTOSAVE" : " " + var1[var2].stationName));
            int var3;
            if ((var3 = SymbolMapManager_.sub_25b(this.var_467, 1)) > this.var_911) {
               this.var_911 = var3;
            }
         }

         this.var_911 += 24;
      } else {
         this.var_911 = var_2a3 + (var_2a3 >> 1);
      }
   }

   public final void sub_e6() {
      this.saves = null;
      this.choiceConfirm = null;
      this.var_34e = null;
   }

   public final void sub_12b(int var1, int var2) {
      this.frameTime = var2;
      Layout.addTicks(var2);
      if ((var1 & 64) != 0) {
         if (this.subMenu >= 15) {
            this.instructions.scrollUp(var2);
            return;
         }

         if (this.subMenu == 8) {
            this.about.scrollUp(var2);
            return;
         }

         if (this.subMenu == 7) {
            this.controls.scrollUp(var2);
            return;
         }
      } else if ((var1 & 2) != 0) {
         if (this.subMenu >= 15) {
            this.instructions.scrollDown(var2);
            return;
         }

         if (this.subMenu == 8) {
            this.about.scrollDown(var2);
            return;
         }

         if (this.subMenu == 7) {
            this.controls.scrollDown(var2);
         }
      }

   }

   public final boolean sub_148() {
      RecordHandler var1;
      switch(this.subMenu) {
      case 0:
         if (this.confirmPopupOpen && this.selectedRow == 5) {
            if (this.choiceConfirm.sub_9a()) {
               GameStatus.var_bfb.sub_54();
               return true;
            }

            this.confirmPopupOpen = false;
            return false;
         }

         switch(this.selectedRow) {
         case 0:
            if (Status.getPlayingTime() <= 0L) {
               Status.startNewGame();
               GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
               SoundManager.sub_2a5();
               return true;
            }

            if (!this.confirmPopupOpen) {
               this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(30), true);
               this.confirmPopupOpen = true;
               return false;
            }

            if (this.choiceConfirm.sub_9a()) {
               Status.startNewGame();
               GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
               SoundManager.sub_2a5();
               return true;
            }

            this.confirmPopupOpen = false;
            return false;
         case 1:
            this.subMenu = 1;
            if (this.saves == null) {
               var1 = new RecordHandler();
               this.saves = var1.readAllPreviews();
            }

            this.sub_70(this.saves);
            this.selectedRow = 0;
            return false;
         case 2:
            this.subMenu = 2;
            if (this.saves == null) {
               var1 = new RecordHandler();
               this.saves = var1.readAllPreviews();
            }

            this.sub_70(this.saves);
            this.selectedRow = 0;
            return false;
         case 3:
            this.subMenu = 3;
            this.selectedRow = 0;
            return false;
         case 4:
            this.subMenu = 4;
            this.selectedRow = 0;
            return false;
         case 5:
            if (!this.confirmPopupOpen) {
               this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(31), true);
               this.confirmPopupOpen = true;
            }

            return false;
         case 6:
            if (this.webConncetionRelatedConfirm_) {
               switch(this.var_ad1) {
               case 1:
                  try {
                     if (!this.confirmPopupOpen && this.var_a15 != null) {
                        this.choiceConfirm.sub_8f(this.var_a15, true);
                     } else if (this.var_a15 == null || this.choiceConfirm.sub_9a()) {
                        GameStatus.midlet.platformRequest(this.var_992);
                        GameStatus.var_bfb.sub_54();
                        return true;
                     }

                     if (this.var_a15 != null) {
                        this.confirmPopupOpen = !this.confirmPopupOpen;
                     }
                  } catch (Exception var3) {
                  }

                  return false;
               default:
                  try {
                     if (!this.confirmPopupOpen && this.var_a15 != null) {
                        this.choiceConfirm.sub_8f(this.var_a15, true);
                     } else if (this.var_a15 == null || this.choiceConfirm.sub_9a()) {
                        GameStatus.midlet.platformRequest(this.var_992);
                     }

                     if (this.var_a15 != null) {
                        this.confirmPopupOpen = !this.confirmPopupOpen;
                     }
                  } catch (Exception var2) {
                  }

                  return false;
               }
            }

            return false;
         default:
            return false;
         }
      case 1:
         if (this.selectedRow == this.saves.length || this.saves[this.selectedRow] != null) {
            if (Status.getPlayingTime() <= 0L) {
               this.confirmPopupOpen = false;
               this.loadGameRecord();
               return true;
            }

            if (!this.confirmPopupOpen) {
               this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(29), true);
               this.confirmPopupOpen = true;
            } else {
               if (this.choiceConfirm.sub_9a()) {
                  this.loadGameRecord();
               }

               this.confirmPopupOpen = false;
            }
         }
         break;
      case 2:
         if (this.confirmPopupOpen) {
            if (!this.choiceConfirm.sub_9a()) {
               this.confirmPopupOpen = false;
               return true;
            }

            GameStatus.loadingScreen.startLoading_(false);
            (var1 = new RecordHandler()).sub_115(this.selectedRow);
            this.saves[this.selectedRow] = var1.readPreview(this.selectedRow);
            this.sub_70(this.saves);
            GameStatus.loadingScreen.close();
            this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(28), false);
         } else if (this.selectedRow < this.saves.length && this.saves[this.selectedRow] == null) {
            GameStatus.loadingScreen.startLoading_(false);
            (var1 = new RecordHandler()).sub_115(this.selectedRow);
            this.saves = var1.readAllPreviews();
            GameStatus.loadingScreen.close();
            this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(28), false);
            this.confirmPopupOpen = true;
         } else {
            this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(27), true);
            this.confirmPopupOpen = true;
         }
         break;
      case 3:
         this.sub_2c1();
         break;
      case 4:
         switch(this.selectedRow) {
         case 0:
            this.subMenu = 6;
            this.selectedRow = 0;
            this.var_6eb.selectedEntry = 0;
            return false;
         case 1:
            this.subMenu = 7;
            return false;
         case 2:
            this.subMenu = 8;
            return false;
         default:
            return false;
         }
      case 5:
         if (this.confirmPopupOpen) {
            if (this.choiceConfirm.sub_9a()) {
               GameStatus.var_bfb.setScene(GameStatus.scenes[0]);
               GameStatus.soundManager.playMusic(0);
               return true;
            }

            this.confirmPopupOpen = false;
         } else {
            switch(this.selectedRow) {
            case 0:
               this.subMenu = 3;
               this.selectedRow = 0;
               return false;
            case 1:
               this.subMenu = 4;
               this.selectedRow = 0;
               return false;
            case 2:
               if (!this.confirmPopupOpen) {
                  this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(31), true);
                  this.confirmPopupOpen = true;
               }
            }
         }
         break;
      case 6:
         this.instructions.zeroTopPadding();
         this.instructions.sub_e0(GameStatus.langManager.getLangString(LangManager.helpFull[this.selectedRow]));
         this.subMenu = 15 + this.selectedRow;
         System.out.println(this.selectedRow + "  " + this.subMenu);
      case 7:
      case 8:
      case 10:
      default:
         break;
      case 9:
         switch(this.selectedRow) {
         case 0:
            if (Status.getPlayingTime() > 0L) {
               if (!this.confirmPopupOpen) {
                  this.choiceConfirm.sub_8f(GameStatus.langManager.getLangString(30), true);
                  this.confirmPopupOpen = true;
                  return false;
               }

               if (this.choiceConfirm.sub_9a()) {
                  this.subMenu = 11;
                  this.selectedRow = 0;
               }

               this.confirmPopupOpen = false;
            } else {
               this.subMenu = 11;
               this.selectedRow = 0;
            }
            break;
         case 1:
            this.subMenu = 10;
            this.selectedRow = 0;
         }
      }

      return false;
   }

   private void loadGameRecord() {
      RecordHandler var10000 = new RecordHandler();
      int var2 = this.selectedRow;
      RecordHandler var1 = var10000;
      new GameRecord();
      Object var3 = null;
      GameRecord var4 = var1.sub_106(var2);
      Status.startNewGame();
      Galaxy.setVisitedStations(var4.var_df);
      Status.setLastXP(var4.lastWorth);
      Status.setCredits(var4.credits);
      Status.setRating(var4.rating);
      Status.setPlayingTime(var4.playTime);
      Status.setKills(var4.kills);
      Status.setMissionCount(var4.missionCount);
      Status.setLevel(var4.level);
      Status.setLastXP(var4.lastWorth);
      Status.setGoodsProduced(var4.var_4b1);
      Status.sub_ca6(var4.var_4d6);
      Status.setJumpgateUsed(var4.var_552);
      Status.setCargoSalvaged(var4.var_5ee);
      Status.sub_690(var4.var_663);
      Status.setStationsVisited(var4.stationsVisited);
      Status.wormholeStation = var4.var_81e;
      Status.wormholeSystem = var4.var_883;
      Status.lastVisitedNonVoidOrbit = var4.var_8e6;
      Status.var_cae = var4.var_97f;
      Status.getCurrentCampaignMissionIndex(var4.var_73b);
      Status.setFreelanceMission(var4.var_9a3);
      Status.startStoryMission(var4.var_a05);
      Status.setLastVisitedStations(var4.var_a51);
      Status.minedOreTypes = var4.var_a74;
      Status.minedCoreTypes = var4.var_ad5;
      Status.var_d88 = var4.var_b28;
      Status.minedOre = var4.var_b4b;
      Status.minedCores = var4.var_b83;
      Status.var_e59 = var4.var_bce;
      Status.drinksVar1 = var4.var_c0d;
      Status.var_ea8 = var4.var_c47;
      Status.drinksVar2 = var4.var_c81;
      Status.var_f13 = var4.var_cbe;
      Status.invisibleTime = var4.var_cfa;
      Status.bombsUsed = var4.var_d55;
      Status.alienJunkSalvaged = var4.var_d83;
      Status.barInteractions = var4.var_dcf;
      Status.commandedWingmen = var4.var_de1;
      Status.asteroidsDestroyed = var4.var_e29;
      Status.maxFreeCargo = var4.var_e62;
      Status.missionsRejected = var4.var_e81;
      Status.askedToRepeate = var4.var_e95;
      Status.acceptedNotAskingDifficulty = var4.var_ea3;
      Status.acceptedNotAskingLocation = var4.var_ede;
      Medals.init();
      if (var4.var_a68 != null) {
         Medals.setMedals(var4.var_a68);
      }

      Status.setShip(var4.var_14f4);
      Status.setCurrentStation_andInitSystem_(var4.var_1521);
      Status.setMission(Mission.var_dc);
      Status.standing = var4.var_1566;
      Status.blueprints = var4.var_157c;
      Status.waitingGoods = var4.var_15d9;
      Status.SpecialAgents = var4.var_162f;
      Status.wingmenNames = var4.var_1646;
      Status.wingmenRace_ = var4.var_1685;
      Status.wingmenTimeRemaining = var4.var_16d5;
      Status.var_8f8wingmenVar2 = var4.var_1721;
      Status.var_9a8 = var4.var_175b;
      Status.visibleSystems = var4.var_17bb;
      Status.lowestItemPrices = var4.var_17e0;
      Status.highestItemPrices = var4.var_1829;
      Status.lowestItemPriceSystems = var4.var_186b;
      Status.highestItemPriceSystems = var4.var_187b;
      GameStatus.shopHelpShown = var4.var_f30;
      GameStatus.var_264 = var4.var_f50;
      GameStatus.var_2b3 = var4.var_f5f;
      GameStatus.var_2cc = var4.var_f6c;
      GameStatus.var_2e8 = var4.var_fc2;
      GameStatus.barHelpShown = var4.var_100f;
      GameStatus.galaxyMapHelpShown = var4.var_1038;
      GameStatus.systemMapHelpShown = var4.var_1047;
      GameStatus.var_40c = var4.var_108f;
      GameStatus.miningHelpShown = var4.var_10ed;
      GameStatus.asteroidHelpShown = var4.var_119d;
      GameStatus.missionsHelpShown = var4.var_11c5;
      GameStatus.cargoFullHelpShown = var4.var_11eb;
      GameStatus.wingmenHelpShown = var4.var_121f;
      GameStatus.var_554 = var4.var_1273;
      GameStatus.boosterHelpShown = var4.var_12c6;
      GameStatus.statusHelpShown = var4.var_12e2;
      GameStatus.medalsHelpShown = var4.var_132f;
      GameStatus.fanWingmenNoticeShown = var4.var_1378;
      GameStatus.voidxNoticeShown = var4.var_1392;
      GameStatus.reputationHelpShown = var4.var_13ae;
      GameStatus.buyingHelpShown = var4.var_13db;
      GameStatus.itemMountingHelpShown = var4.var_13f8;
      GameStatus.itemMounting2HelpShown = var4.var_1407;
      GameStatus.interplanetHelpShown = var4.var_1428;
      GameStatus.jumpDriveHelpShown = var4.var_1449;
      GameStatus.cloakHelpShown = var4.var_1459;
      Status.loadingsCount = var4.var_148e;
      Status.timeSpentLoading = var4.var_14c1;
      var4.var_df = null;
      ((InsideStation)((InsideStation)GameStatus.scenes[1])).sub_a9();
      GameStatus.var_bfb.setScene(GameStatus.scenes[1]);
   }

   public final void sub_1e5(int var1) {
      if (!this.confirmPopupOpen) {
         if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 13 && this.subMenu != 14 && this.subMenu < 15) {
            if (this.selectedRow > 0) {
               --this.selectedRow;
            } else {
               this.selectedRow = listEntriesCounts_[this.subMenu] - 1;
            }

            if (this.selectedRow == 2 && this.subMenu == 0 && Status.getPlayingTime() == 0L) {
               --this.selectedRow;
            }

            if (this.subMenu == 6) {
               this.var_6eb.sub_b4();
               return;
            }
         } else if (this.subMenu == 13) {
            this.var_7bc.scrollDown(var1);
         }

      }
   }

   public final void sub_215(int var1) {
      if (!this.confirmPopupOpen) {
         if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 13 && this.subMenu != 14 && this.subMenu < 15) {
            if (this.selectedRow < listEntriesCounts_[this.subMenu] - 1) {
               ++this.selectedRow;
            } else {
               this.selectedRow = 0;
            }

            if (this.selectedRow == 2 && this.subMenu == 0 && Status.getPlayingTime() == 0L) {
               ++this.selectedRow;
            }

            if (this.subMenu == 6) {
               this.var_6eb.sub_6b();
               return;
            }
         } else if (this.subMenu == 13) {
            this.var_7bc.scrollUp(var1);
         }

      }
   }

   public final void sub_23d() {
      if (this.confirmPopupOpen) {
         this.choiceConfirm.sub_c6();
      } else {
         if (this.subMenu == 3) {
            switch(this.selectedRow) {
            case 0:
               --this.musicLevel;
               this.updateMusicLevel();
               return;
            case 1:
               --this.soundLevel;
               this.updateSoundLevel();
               return;
            case 2:
               this.sub_2c1();
               return;
            case 3:
               this.sub_2c1();
            }
         }

      }
   }

   private void updateSoundLevel() {
      if (this.soundLevel < 0) {
         this.soundLevel = 3;
      }

      if (this.soundLevel > 3) {
         this.soundLevel = 0;
      }

      switch(this.soundLevel) {
      case 0:
         GameStatus.sfxOn = true;
         GameStatus.soundManager.setSfxVolume(60);
         break;
      case 1:
         GameStatus.sfxOn = true;
         GameStatus.soundManager.setSfxVolume(80);
         break;
      case 2:
         GameStatus.sfxOn = true;
         GameStatus.soundManager.setSfxVolume(100);
         break;
      case 3:
         GameStatus.sfxOn = false;
      }

      GameStatus.sfxVolume = this.soundLevel;
   }

   private void updateMusicLevel() {
      if (this.musicLevel < 0) {
         this.musicLevel = 3;
      }

      if (this.musicLevel > 3) {
         this.musicLevel = 0;
      }

      switch(this.musicLevel) {
      case 0:
         GameStatus.musicOn = true;
         GameStatus.soundManager.setMusicVolume(60);
         if (GameStatus.var_bfb.sub_28() != GameStatus.scenes[2] && !SoundManager.isMusicPlaying()) {
            GameStatus.soundManager.playMusic(0);
         }
         break;
      case 1:
         GameStatus.musicOn = true;
         GameStatus.soundManager.setMusicVolume(80);
         break;
      case 2:
         GameStatus.musicOn = true;
         GameStatus.soundManager.setMusicVolume(100);
         if (GameStatus.var_bfb.sub_28() != GameStatus.scenes[2] && !SoundManager.isMusicPlaying()) {
            GameStatus.soundManager.playMusic(0);
         }
         break;
      case 3:
         SoundManager.sub_2a5();
         GameStatus.musicOn = false;
      }

      GameStatus.musicVolume = this.musicLevel;
   }

   public final void sub_2c1() {
      if (this.confirmPopupOpen) {
         this.choiceConfirm.sub_ff();
      } else {
         if (this.subMenu == 3) {
            switch(this.selectedRow) {
            case 0:
               ++this.musicLevel;
               this.updateMusicLevel();
               return;
            case 1:
               ++this.soundLevel;
               this.updateSoundLevel();
               return;
            case 2:
               if (GameStatus.vibrationOn = !GameStatus.vibrationOn) {
                  GameStatus.vibrate(150);
                  return;
               }
               break;
            case 3:
               GameStatus.invertedControlsOn = !GameStatus.invertedControlsOn;
            }
         }

      }
   }

   public final boolean sub_2d7() {
      if (this.confirmPopupOpen) {
         return false;
      } else if (this.subMenu != 0 && this.subMenu != 5) {
         if (this.subMenu == 3) {
            (new RecordHandler()).writeOptions();
         }

         if (this.subMenu == 7) {
            this.selectedRow = 1;
            this.subMenu = 4;
            return false;
         } else if (this.subMenu == 6) {
            this.selectedRow = 0;
            this.subMenu = 4;
            return false;
         } else if (this.subMenu >= 15) {
            this.subMenu = 6;
            return false;
         } else if (this.subMenu == 10) {
            this.selectedRow = 1;
            this.subMenu = 9;
            return false;
         } else if (this.subMenu == 8) {
            this.selectedRow = 2;
            this.subMenu = 4;
            return false;
         } else if (this.subMenu == 12) {
            this.subMenu = 11;
            return false;
         } else if (this.subMenu == 11) {
            return false;
         } else {
            if (this.subMenu > 0 || this.subMenu == 9) {
               if (this.subMenu == 9) {
                  this.selectedRow = 0;
               } else {
                  this.selectedRow = this.subMenu;
               }

               this.subMenu = 0;
               if (this.var_600) {
                  this.sub_38c();
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public final boolean sub_2e7() {
      this.sub_148();
      return false;
   }

   public final void sub_33e(int var1) {
      this.selectedRow = var1;
      this.subMenu = 0;
   }

   public final void sub_38c() {
      this.subMenu = 5;
      this.selectedRow = 0;
      this.confirmPopupOpen = false;
      this.var_600 = true;
   }

   public final void sub_3a2() {
      GameStatus.graphics.drawImage(this.var_34e, GameStatus.screenWidth / 2, 10, 17);
      label197:
      switch(this.subMenu) {
      case 0:
         this.var_3be = Status.getPlayingTime() > 0L ? this.var_373 : 0;
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(67), var_191, var_251, var_2a3, ((Status.getPlayingTime() > 0L ? listEntriesCounts_[this.subMenu] : listEntriesCounts_[this.subMenu] - 1) + 2) * this.var_373);
         Layout.scale(GameStatus.langManager.getLangString(0), var_191, this.var_b51, var_2a3, this.selectedRow == 0);
         Layout.scale(GameStatus.langManager.getLangString(1), var_191, this.var_b51 + this.var_373, var_2a3, this.selectedRow == 1);
         if (Status.getPlayingTime() > 0L) {
            Layout.scale(GameStatus.langManager.getLangString(2), var_191, this.var_b51 + 2 * this.var_373, var_2a3, this.selectedRow == 2);
         }

         Layout.scale(GameStatus.langManager.getLangString(3), var_191, this.var_b51 + 2 * this.var_373 + this.var_3be, var_2a3, this.selectedRow == 3);
         Layout.scale(GameStatus.langManager.getLangString(4), var_191, this.var_b51 + 3 * this.var_373 + this.var_3be, var_2a3, this.selectedRow == 4);
         Layout.scale(GameStatus.langManager.getLangString(5), var_191, this.var_b51 + 4 * this.var_373 + this.var_3be, var_2a3, this.selectedRow == 5);
         if (this.webConncetionRelatedConfirm_) {
            Layout.scale(this.var_a9e, var_191, this.var_b51 + 5 * this.var_373 + this.var_3be, var_2a3, this.selectedRow == 6);
         }
         break;
      case 1:
      case 2:
         int var1 = 6 * this.var_373;
         if (this.subMenu == 2) {
            Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(2), GameStatus.screenWidth - this.var_911 >> 1, var_251, this.var_911, var1);
         } else {
            Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(1), GameStatus.screenWidth - this.var_911 >> 1, var_251, this.var_911, var1);
         }

         var1 = 0;

         while(true) {
            if (var1 >= this.saves.length) {
               break label197;
            }

            this.var_467 = var1 + 1 + ".  ";
            if (this.saves[var1] == null) {
               this.var_467 = this.var_467 + GameStatus.langManager.getLangString(26);
            } else {
               this.var_467 = this.var_467 + Time.timeToHM(this.saves[var1].playTime);
               this.var_696 = this.saves[var1].stationName;
               if (var1 == 3) {
                  this.var_467 = this.var_467 + " AUTOSAVE";
               } else {
                  this.var_467 = this.var_467 + " " + this.var_696;
               }
            }

            Layout.scale(this.var_467, GameStatus.screenWidth - this.var_911 >> 1, this.var_b51 + var1 * this.var_373, this.var_911, this.selectedRow == var1);
            ++var1;
         }
      case 3:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(3), var_191, var_251, var_2a3, (listEntriesCounts_[this.subMenu] + 2) * this.var_373);
         Layout.scale(GameStatus.langManager.getLangString(6) + "  " + GameStatus.langManager.getLangString(LangManager.soundLevels[this.musicLevel]), var_191, this.var_b51, var_2a3, this.selectedRow == 0);
         Layout.scale(GameStatus.langManager.getLangString(7) + "  " + GameStatus.langManager.getLangString(LangManager.soundLevels[this.soundLevel]), var_191, this.var_b51 + this.var_373, var_2a3, this.selectedRow == 1);
         Layout.scale(GameStatus.langManager.getLangString(11) + "  " + GameStatus.langManager.getLangString(GameStatus.vibrationOn ? 15 : 16), var_191, this.var_b51 + 2 * this.var_373, var_2a3, this.selectedRow == 2);
         Layout.scale(GameStatus.langManager.getLangString(14) + "  " + GameStatus.langManager.getLangString(GameStatus.invertedControlsOn ? 15 : 16), var_191, this.var_b51 + 3 * this.var_373, var_2a3, this.selectedRow == 3);
         break;
      case 4:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(4), var_191, var_251, var_2a3, (listEntriesCounts_[this.subMenu] + 2) * this.var_373);
         Layout.scale(GameStatus.langManager.getLangString(19), var_191, this.var_b51, var_2a3, this.selectedRow == 0);
         Layout.scale(GameStatus.langManager.getLangString(20), var_191, this.var_b51 + this.var_373, var_2a3, this.selectedRow == 1);
         Layout.scale(GameStatus.langManager.getLangString(21), var_191, this.var_b51 + 2 * this.var_373, var_2a3, this.selectedRow == 2);
         break;
      case 5:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(17), var_191, var_251, var_2a3, (listEntriesCounts_[this.subMenu] + 2) * this.var_373);
         Layout.scale(GameStatus.langManager.getLangString(3), var_191, this.var_b51, var_2a3, this.selectedRow == 0);
         Layout.scale(GameStatus.langManager.getLangString(4), var_191, this.var_b51 + this.var_373, var_2a3, this.selectedRow == 1);
         Layout.scale(GameStatus.langManager.getLangString(5), var_191, this.var_b51 + 2 * this.var_373, var_2a3, this.selectedRow == 2);
         break;
      case 6:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(19), var_191, var_251, var_2a3, AEMath.min(this.var_6eb.sub_18a() * SymbolMapManager_.sub_2c2() + 30, GameStatus.screenHeight - var_251 - 16 - 10));
         this.var_6eb.sub_171();
         this.var_6eb.sub_312();
         break;
      case 7:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(20), var_191 / 2, var_251, var_2a3 + var_191, AEMath.min(GameStatus.screenHeight - var_251 - 16 - 10, this.controls.sub_115()));
         this.controls.draw();
         break;
      case 8:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(21), var_191 / 2, var_251, var_2a3 + var_191, GameStatus.screenHeight - var_251 - 16 - 10);
         SymbolMapManager_.sub_102(GameStatus.gameVersion, (GameStatus.screenWidth >> 1) - (SymbolMapManager_.sub_250(GameStatus.gameVersion) >> 1), 10 + this.var_34e.getHeight() + 2, 1);
         this.about.draw();
      case 9:
      case 10:
      case 12:
      case 13:
      case 14:
      default:
         break;
      case 11:
         Layout.drawTitleBarWindow(GameStatus.langManager.getLangString(34), var_191, var_251, var_2a3, 4 * this.var_373, true);
         this.var_817.sub_ed(this.frameTime);
         SymbolMapManager_.sub_102(this.var_817.sub_13f() + this.var_817.sub_11b(), var_191 + 10, this.var_b51 + this.var_373, 0);
         if (Layout.sub_119()) {
            SymbolMapManager_.sub_102("_", var_191 + 10 + SymbolMapManager_.sub_250(this.var_817.sub_13f()), this.var_b51 + 2 + this.var_373, 0);
         }
         break;
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(LangManager.helpTitles[this.selectedRow]), var_191 / 2, var_251, var_2a3 + var_191, GameStatus.screenHeight - var_251 - 16 - 10);
         this.instructions.draw();
      }

      if (this.confirmPopupOpen) {
         this.choiceConfirm.sub_19a();
         Layout.sub_535("", "", false);
      } else if (this.subMenu == 0) {
         Layout.sub_535(GameStatus.langManager.getLangString(253), Status.getPlayingTime() == 0L ? "" : GameStatus.langManager.getLangString(65), false);
      } else if (this.subMenu == 5) {
         Layout.sub_535(GameStatus.langManager.getLangString(253), GameStatus.langManager.getLangString(18), false);
      } else {
         if (this.subMenu != 11) {
            if (this.subMenu != 7 && this.subMenu != 8 && this.subMenu != 14 && this.subMenu != 13 && (this.subMenu < 15 || this.subMenu > 24)) {
               Layout.sub_535(GameStatus.langManager.getLangString(35), GameStatus.langManager.getLangString(65), false);
               return;
            }

            Layout.sub_535("", GameStatus.langManager.getLangString(65), false);
         }

      }
   }

   public final boolean sub_401(int var1) {
      if (this.subMenu == 11) {
         if (var1 == 8192 && this.var_817.sub_13f().length() == 0) {
            this.selectedRow = 0;
            this.subMenu = 9;
         }

         if (var1 != 0 && var1 != -1) {
            this.var_817.sub_53(var1);
         }
      }

      return true;
   }
}
