package Main;

import AbyssEngine.AEImage;
import AbyssEngine.AEMath;
import AbyssEngine.Face_;
import AbyssEngine.GameStatus;
import AbyssEngine.Item;
import AbyssEngine.LangManager;
import AbyssEngine.Layout;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.Popup;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;

public final class MissionsWindow {
   private StarMap var_2d;
   private Popup var_77;
   private boolean var_10f;
   private int innerWindowOffsetX;
   private int innerWindowOffsetY;
   private int scrollPos;
   private int var_1bd;
   private int innerWindowsHeight;
   private int innerWindowWidth;
   private int scrollThumbSize;
   private boolean actionsOpen;
   private boolean mapOpen;
   private int actionsPosY;
   private int[] highlightedActions;
   private int actionChoice;
   private int actionsWidth;
   private String[] storyMissionRows;
   private String[] freelanceRows;
   private boolean var_4f2;
   private Image[] var_51b;
   private Image var_539;
   private Image var_594;
   private int var_5bb;
   private boolean var_5d2;

   public MissionsWindow() {
      this.sub_2e();
   }

   public final void sub_2e() {
      this.innerWindowOffsetX = 9;
      this.innerWindowOffsetY = 20;
      this.var_5d2 = false;
      String var1;
      if (this.var_539 == null) {
         var1 = "/data/interface/menu_map_mainmission.png";
         this.var_539 = AEImage.loadImage("/data/interface/menu_map_mainmission.png", true);
         var1 = "/data/interface/menu_map_sidemission.png";
         this.var_594 = AEImage.loadImage("/data/interface/menu_map_sidemission.png", true);
      }

      if (!Status.gameWon()) {
         var1 = Status.replaceTokens(GameStatus.langManager.getLangString(LangManager.egoToughts[Status.getCurrentCampaignMission()]), Status.getCampaignMission().getTargetStationName(), "#");
         this.storyMissionRows = SymbolMapManager_.sub_3b2(var1, GameStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      } else if (Medals.gotAllGoldMedals() && Status.getShip().getIndex() != 8) {
         this.var_5d2 = true;
         var1 = GameStatus.langManager.getLangString(326);
         this.storyMissionRows = SymbolMapManager_.sub_3b2(var1, GameStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      }

      if (Status.getFreelanceMission().isEmpty()) {
         this.freelanceRows = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(69), GameStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      } else {
         this.var_51b = Face_.faceFromByteArray(Status.getFreelanceMission().getAgent().sub_471());
         this.freelanceRows = SymbolMapManager_.sub_3b2(Status.getFreelanceMission().getAgent().getMessage(), GameStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      }

      this.scrollPos = this.innerWindowOffsetY;
      this.innerWindowsHeight = GameStatus.screenHeight - this.scrollPos - 16 - 5;
      this.innerWindowWidth = GameStatus.screenWidth - 2 * this.innerWindowOffsetX;
      this.var_1bd = (Status.gameWon() && !this.var_5d2 ? 0 : SymbolMapManager_.sub_31c(this.storyMissionRows)) + SymbolMapManager_.sub_31c(this.freelanceRows) + 8 + 36 + 4 + Face_.faceHeight;
      if (Status.getFreelanceMission().isEmpty()) {
         this.var_1bd -= Face_.faceHeight + 4;
      }

      this.scrollThumbSize = 0;
      if (this.var_1bd > this.innerWindowsHeight) {
         this.scrollThumbSize = (int)((float)this.innerWindowsHeight / (float)this.var_1bd * (float)this.innerWindowsHeight);
         this.innerWindowWidth -= 5;
      }

      this.actionsOpen = false;
      this.var_5bb = !Status.gameWon() && Status.getCampaignMission().sub_12c() ? 1 : 0;
      int var2;
      if ((var2 = Status.getFreelanceMission().isEmpty() ? this.var_5bb : this.var_5bb + 2) > 0) {
         this.highlightedActions = new int[var2];

         for(var2 = 0; var2 < this.highlightedActions.length; ++var2) {
            this.highlightedActions[var2] = 0;
         }

         this.highlightedActions[0] = 1;
         this.actionChoice = 0;
         if (this.var_5bb > 0) {
            this.actionsWidth = 18 + SymbolMapManager_.sub_25b(GameStatus.langManager.getLangString(245) + " (" + GameStatus.langManager.getLangString(278) + ")", 0);
         } else {
            this.actionsWidth = 0;
         }

         if (!Status.getFreelanceMission().isEmpty() && (var2 = AEMath.max(18 + SymbolMapManager_.sub_25b(GameStatus.langManager.getLangString(244), 0), 18 + SymbolMapManager_.sub_25b(GameStatus.langManager.getLangString(245) + " (" + GameStatus.langManager.getLangString(279) + ")", 0))) > this.actionsWidth) {
            this.actionsWidth = var2;
         }

         this.var_77 = new Popup();
         this.actionsPosY = GameStatus.screenHeight - 40 - this.highlightedActions.length * 10;
      }

      boolean var3 = false;
      this.var_4f2 = false;
   }

   public final boolean sub_5c(int var1) {
      if (this.mapOpen) {
         if (!this.var_2d.sub_a9(var1)) {
            this.mapOpen = false;
         }

         return true;
      } else {
         Item[] var2;
         if (!this.var_10f) {
            if (this.actionsOpen) {
               if (var1 == 64) {
                  this.highlightedActions[this.actionChoice] = 0;
                  if (this.actionChoice < this.highlightedActions.length - 1) {
                     ++this.actionChoice;
                  } else {
                     this.actionChoice = 0;
                  }

                  this.highlightedActions[this.actionChoice] = 1;
               }

               if (var1 == 2) {
                  this.highlightedActions[this.actionChoice] = 0;
                  if (this.actionChoice > 0) {
                     --this.actionChoice;
                  } else {
                     this.actionChoice = this.highlightedActions.length - 1;
                  }

                  this.highlightedActions[this.actionChoice] = 1;
               }

               if (var1 == 256) {
                  switch(this.actionChoice) {
                  case 0:
                  case 1:
                     var2 = null;
                     Mission var3;
                     if (this.var_5bb > 0) {
                        if (this.actionChoice == 0) {
                           var3 = Status.getCampaignMission();
                        } else {
                           var3 = Status.getFreelanceMission();
                        }
                     } else {
                        if (this.actionChoice != 0) {
                           this.var_77.sub_8f(GameStatus.langManager.getLangString(240), true);
                           this.var_10f = true;
                           this.actionsOpen = false;
                           return true;
                        }

                        var3 = Status.getFreelanceMission();
                     }

                     this.var_2d = ((InsideStation)GameStatus.scenes[1]).var_85d;
                     if (this.var_2d == null) {
                        ((InsideStation)GameStatus.scenes[1]).var_85d = new StarMap(true, var3, false, -1);
                        this.var_2d = ((InsideStation)GameStatus.scenes[1]).var_85d;
                     } else {
                        this.var_2d.sub_4e(true, var3, false, -1);
                     }

                     this.mapOpen = true;
                     break;
                  case 2:
                     this.var_77.sub_8f(GameStatus.langManager.getLangString(240), true);
                     this.var_10f = true;
                  }

                  this.actionsOpen = false;
               }
            }

            if (var1 == 8192) {
               return false;
            } else {
               if (var1 == 16384 && this.highlightedActions != null) {
                  this.actionsOpen = !this.actionsOpen;
               }

               return true;
            }
         } else {
            if (var1 == 4) {
               this.var_77.sub_c6();
            }

            if (var1 == 32) {
               this.var_77.sub_ff();
            }

            if (var1 == 256) {
               if (this.var_77.sub_9a()) {
                  if (Status.getFreelanceMission().getType() != 0 && Status.getFreelanceMission().getType() != 3 && Status.getFreelanceMission().getType() != 5) {
                     if (Status.getFreelanceMission().getType() == 11) {
                        Status.sub_236(0);
                     }
                  } else if ((var2 = Status.getShip().getCargo()) != null) {
                     for(var1 = 0; var1 < var2.length; ++var1) {
                        if (var2[var1].sub_95c() && var2[var1].getIndex() == 116) {
                           Status.getShip().sub_755(var2[var1]);
                           this.var_4f2 = true;
                           break;
                        }
                     }
                  }

                  if (!Status.getFreelanceMission().getAgent().sub_41f()) {
                     Status.getFreelanceMission().getAgent().setAccepted(false);
                  }

                  Status.setFreelanceMission(Mission.var_dc);
                  this.sub_2e();
               }

               this.var_10f = false;
            }

            return true;
         }
      }
   }

   public final void sub_bc(int var1, int var2) {
      if (this.mapOpen) {
         this.var_2d.sub_101(var1, var2);
      } else {
         Layout.screenFillMenuBackground();
         Layout.sub_2db(GameStatus.langManager.getLangString(33), false);
         if (this.scrollThumbSize > 0 && !this.actionsOpen) {
            if ((var1 & 2) != 0) {
               this.scrollPos += var2 / 8;
               if (this.scrollPos > this.innerWindowOffsetY) {
                  this.scrollPos = this.innerWindowOffsetY;
               }
            }

            if ((var1 & 64) != 0) {
               this.scrollPos -= var2 / 8;
               if (this.scrollPos + this.var_1bd < this.innerWindowOffsetY + this.innerWindowsHeight) {
                  this.scrollPos = this.innerWindowOffsetY + this.innerWindowsHeight - this.var_1bd;
               }
            }
         }

         Layout.sub_3c6(this.highlightedActions == null ? "" : GameStatus.langManager.getLangString(136), GameStatus.langManager.getLangString(65));
         GameStatus.graphics.setColor(0);
         GameStatus.graphics.setClip(this.innerWindowOffsetX, this.innerWindowOffsetY, this.innerWindowWidth, this.innerWindowsHeight);
         var2 = this.scrollPos;
         if (!Status.gameWon() || this.var_5d2) {
            this.sub_10b(var2, GameStatus.langManager.getLangString(278), this.var_539);
            var2 += 20;
            SymbolMapManager_.sub_185(this.storyMissionRows, this.innerWindowOffsetX + 4, var2, 1);
            var2 += SymbolMapManager_.sub_31c(this.storyMissionRows) + 4;
         }

         this.sub_10b(var2, GameStatus.langManager.getLangString(279), this.var_594);
         Mission var3;
         if ((var3 = Status.getFreelanceMission()).isEmpty()) {
            var2 += 20;
         } else {
            var2 += 22;
            Face_.drawFace(this.var_51b, this.innerWindowOffsetX, var2, 20);
            SymbolMapManager_.sub_161(var3.getAgent().fullName, this.innerWindowOffsetX + this.innerWindowWidth, var2, var3.getAgent().sub_35e() > 0 ? 0 : 1, 18);
            SymbolMapManager_.sub_161(var3.getAgent().getStationName(), this.innerWindowOffsetX + this.innerWindowWidth, var2 + (SymbolMapManager_.sub_2c2() << 1), 1, 18);
            var2 += Face_.faceHeight + 2;
         }

         SymbolMapManager_.sub_185(this.freelanceRows, this.innerWindowOffsetX + 4, var2, 1);
         GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
         MissionsWindow var5;
         if ((var5 = this).scrollThumbSize > 0) {
            var2 = (int)((float)(var5.innerWindowOffsetY - var5.scrollPos) / (float)(var5.var_1bd - var5.innerWindowsHeight) * (float)(var5.innerWindowsHeight - var5.scrollThumbSize));
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GameStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.innerWindowsHeight - 1);
            GameStatus.graphics.setColor(-35072);
            GameStatus.graphics.fillRect(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var2, 3, var5.scrollThumbSize);
            GameStatus.graphics.setColor(-4827904);
            GameStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var5.scrollThumbSize - 2 + var2);
            GameStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var5.scrollThumbSize - 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.scrollThumbSize - 1 + var2);
            GameStatus.graphics.setColor(-11520);
            GameStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.scrollThumbSize - 2 + var2);
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         }

         if (this.actionsOpen) {
            Layout.sub_189();
            Layout.sub_3c6(this.highlightedActions == null ? "" : GameStatus.langManager.getLangString(136), GameStatus.langManager.getLangString(65));
            Layout.drawFilledTitleBarWindow("", 1, this.actionsPosY, this.actionsWidth, 12 + (this.highlightedActions.length + 1) * 10);
            if (this.var_5bb > 0) {
               Layout.sub_1df(GameStatus.langManager.getLangString(245) + " (" + GameStatus.langManager.getLangString(278) + ")", 1, this.actionsPosY + 15, this.actionsWidth, this.highlightedActions[0] == 1, false, true);
            }

            if (!Status.getFreelanceMission().isEmpty()) {
               var1 = this.var_5bb * 10;
               Layout.sub_1df(GameStatus.langManager.getLangString(245) + " (" + GameStatus.langManager.getLangString(279) + ")", 1, this.actionsPosY + 15 + var1, this.actionsWidth, this.highlightedActions[this.var_5bb] == 1, false, true);
               Layout.sub_1df(GameStatus.langManager.getLangString(244), 1, this.actionsPosY + 15 + 10 + var1, this.actionsWidth, this.highlightedActions[this.var_5bb + 1] == 1, false, true);
            }
         }

         if (this.var_10f) {
            this.var_77.sub_19a();
         }

      }
   }

   private void sub_10b(int var1, String var2, Image var3) {
      GameStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GameStatus.graphics.fillRect(this.innerWindowOffsetX, var1, this.innerWindowWidth, 17);
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawRect(this.innerWindowOffsetX + 1, var1 + 1, this.innerWindowWidth - 3, 16);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.drawRect(this.innerWindowOffsetX, var1, this.innerWindowWidth - 1, 18);
      Layout.sub_39a(this.innerWindowOffsetX, var1, false);
      GameStatus.graphics.drawImage(var3, this.innerWindowOffsetX + 6, var1 + 4, 20);
      SymbolMapManager_.sub_102(var2, this.innerWindowOffsetX + 6 + var3.getWidth() + 4, var1 + 4, 0);
   }

   public final boolean sub_162() {
      return this.var_4f2;
   }

   public final void sub_179(boolean var1) {
      this.var_4f2 = false;
   }
}
