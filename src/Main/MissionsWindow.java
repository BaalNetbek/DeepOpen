package Main;

import AE.AEFile;
import AE.Math.AEMath;
import AE.PaintCanvas.ImageFactory;
import AE.GlobalStatus;
import GoF2.Item;
import GoF2.GameText;
import GoF2.Layout;
import GoF2.Achievements;
import GoF2.Mission;
import GoF2.Popup;
import GoF2.Status;
import AE.PaintCanvas.Font;
import javax.microedition.lcdui.Image;

public final class MissionsWindow {
   private StarMap starMap;
   private Popup popup;
   private boolean popupOpen;
   private int innerWindowOffsetX;
   private int innerWindowOffsetY;
   private int scrollPos;
   private int contentHeight;
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
   private boolean hangarNeedsUpdate;
   private Image[] clientsFace;
   private Image mainMission;
   private Image sideMission;
   private int campainMissionActions;
   private boolean alienShipMission;

   public MissionsWindow() {
      this.init();
   }

   public final void init() {
      this.innerWindowOffsetX = 9;
      this.innerWindowOffsetY = 20;
      this.alienShipMission = false;
      String var1;
      if (this.mainMission == null) {
         var1 = "/data/interface/menu_map_mainmission.png";
         this.mainMission = AEFile.loadImage("/data/interface/menu_map_mainmission.png", true);
         var1 = "/data/interface/menu_map_sidemission.png";
         this.sideMission = AEFile.loadImage("/data/interface/menu_map_sidemission.png", true);
      }

      if (!Status.gameWon()) {
         var1 = Status.replaceTokens(GlobalStatus.gameText.getText(GameText.CAMPAIGN_MISSION_DESC[Status.getCurrentCampaignMission()]), Status.getCampaignMission().getTargetStationName(), "#");
         this.storyMissionRows = Font.splitToLines(var1, GlobalStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      } else if (Achievements.gotAllGoldMedals() && Status.getShip().getIndex() != 8) {
         this.alienShipMission = true;
         var1 = GlobalStatus.gameText.getText(326);
         this.storyMissionRows = Font.splitToLines(var1, GlobalStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      }

      if (Status.getFreelanceMission().isEmpty()) {
         this.freelanceRows = Font.splitToLines(GlobalStatus.gameText.getText(69), GlobalStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      } else {
         this.clientsFace = ImageFactory.faceFromByteArray(Status.getFreelanceMission().getAgent().getImageParts());
         this.freelanceRows = Font.splitToLines(Status.getFreelanceMission().getAgent().getMessage(), GlobalStatus.screenWidth - 2 * this.innerWindowOffsetX - 4);
      }

      this.scrollPos = this.innerWindowOffsetY;
      this.innerWindowsHeight = GlobalStatus.screenHeight - this.scrollPos - 16 - 5;
      this.innerWindowWidth = GlobalStatus.screenWidth - 2 * this.innerWindowOffsetX;
      this.contentHeight = (Status.gameWon() && !this.alienShipMission ? 0 : Font.getTotalSpacingY(this.storyMissionRows)) + Font.getTotalSpacingY(this.freelanceRows) + 8 + 36 + 4 + ImageFactory.faceHeight;
      if (Status.getFreelanceMission().isEmpty()) {
         this.contentHeight -= ImageFactory.faceHeight + 4;
      }

      this.scrollThumbSize = 0;
      if (this.contentHeight > this.innerWindowsHeight) {
         this.scrollThumbSize = (int)((float)this.innerWindowsHeight / (float)this.contentHeight * (float)this.innerWindowsHeight);
         this.innerWindowWidth -= 5;
      }

      this.actionsOpen = false;
      this.campainMissionActions = !Status.gameWon() && Status.getCampaignMission().isVisible() ? 1 : 0;
      int var2;
      if ((var2 = Status.getFreelanceMission().isEmpty() ? this.campainMissionActions : this.campainMissionActions + 2) > 0) {
         this.highlightedActions = new int[var2];

         for(var2 = 0; var2 < this.highlightedActions.length; ++var2) {
            this.highlightedActions[var2] = 0;
         }

         this.highlightedActions[0] = 1;
         this.actionChoice = 0;
         if (this.campainMissionActions > 0) {
            this.actionsWidth = 18 + Font.getTextWidth(GlobalStatus.gameText.getText(245) + " (" + GlobalStatus.gameText.getText(278) + ")", 0);
         } else {
            this.actionsWidth = 0;
         }

         if (!Status.getFreelanceMission().isEmpty() && (var2 = AEMath.max(18 + Font.getTextWidth(GlobalStatus.gameText.getText(244), 0), 18 + Font.getTextWidth(GlobalStatus.gameText.getText(245) + " (" + GlobalStatus.gameText.getText(279) + ")", 0))) > this.actionsWidth) {
            this.actionsWidth = var2;
         }

         this.popup = new Popup();
         this.actionsPosY = GlobalStatus.screenHeight - 40 - this.highlightedActions.length * 10;
      }

      boolean var3 = false;
      this.hangarNeedsUpdate = false;
   }

   public final boolean handleKeystate(int var1) {
      if (this.mapOpen) {
         if (!this.starMap.handleKeystate(var1)) {
            this.mapOpen = false;
         }

         return true;
      } else {
         Item[] var2;
         if (!this.popupOpen) {
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
                     if (this.campainMissionActions > 0) {
                        if (this.actionChoice == 0) {
                           var3 = Status.getCampaignMission();
                        } else {
                           var3 = Status.getFreelanceMission();
                        }
                     } else {
                        if (this.actionChoice != 0) {
                           this.popup.set(GlobalStatus.gameText.getText(240), true);
                           this.popupOpen = true;
                           this.actionsOpen = false;
                           return true;
                        }

                        var3 = Status.getFreelanceMission();
                     }

                     this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
                     if (this.starMap == null) {
                        ((ModStation)GlobalStatus.scenes[1]).starMap = new StarMap(true, var3, false, -1);
                        this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
                     } else {
                        this.starMap.init(true, var3, false, -1);
                     }

                     this.mapOpen = true;
                     break;
                  case 2:
                     this.popup.set(GlobalStatus.gameText.getText(240), true);
                     this.popupOpen = true;
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
               this.popup.left();
            }

            if (var1 == 32) {
               this.popup.right();
            }

            if (var1 == 256) {
               if (this.popup.getChoice()) {
                  if (Status.getFreelanceMission().getType() != 0 && Status.getFreelanceMission().getType() != 3 && Status.getFreelanceMission().getType() != 5) {
                     if (Status.getFreelanceMission().getType() == 11) {
                        Status.setPassengers(0);
                     }
                  } else if ((var2 = Status.getShip().getCargo()) != null) {
                     for(var1 = 0; var1 < var2.length; ++var1) {
                        if (var2[var1].setUnsaleable() && var2[var1].getIndex() == 116) {
                           Status.getShip().removeCargo(var2[var1]);
                           this.hangarNeedsUpdate = true;
                           break;
                        }
                     }
                  }

                  if (!Status.getFreelanceMission().getAgent().isGenericAgent_()) {
                     Status.getFreelanceMission().getAgent().setOfferAccepted(false);
                  }

                  Status.setFreelanceMission(Mission.emptyMission_);
                  this.init();
               }

               this.popupOpen = false;
            }

            return true;
         }
      }
   }

   public final void draw(int var1, int var2) {
      if (this.mapOpen) {
         this.starMap.update(var1, var2);
      } else {
         Layout.drawBG();
         Layout.drawNonFullScreenWindow(GlobalStatus.gameText.getText(33), false);
         if (this.scrollThumbSize > 0 && !this.actionsOpen) {
            if ((var1 & 2) != 0) {
               this.scrollPos += var2 / 8;
               if (this.scrollPos > this.innerWindowOffsetY) {
                  this.scrollPos = this.innerWindowOffsetY;
               }
            }

            if ((var1 & 64) != 0) {
               this.scrollPos -= var2 / 8;
               if (this.scrollPos + this.contentHeight < this.innerWindowOffsetY + this.innerWindowsHeight) {
                  this.scrollPos = this.innerWindowOffsetY + this.innerWindowsHeight - this.contentHeight;
               }
            }
         }

         Layout.drawFooter(this.highlightedActions == null ? "" : GlobalStatus.gameText.getText(136), GlobalStatus.gameText.getText(65));
         GlobalStatus.graphics.setColor(0);
         GlobalStatus.graphics.setClip(this.innerWindowOffsetX, this.innerWindowOffsetY, this.innerWindowWidth, this.innerWindowsHeight);
         var2 = this.scrollPos;
         if (!Status.gameWon() || this.alienShipMission) {
            this.drawMissionSubsection(var2, GlobalStatus.gameText.getText(278), this.mainMission);
            var2 += 20;
            Font.drawLines(this.storyMissionRows, this.innerWindowOffsetX + 4, var2, 1);
            var2 += Font.getTotalSpacingY(this.storyMissionRows) + 4;
         }

         this.drawMissionSubsection(var2, GlobalStatus.gameText.getText(279), this.sideMission);
         Mission var3;
         if ((var3 = Status.getFreelanceMission()).isEmpty()) {
            var2 += 20;
         } else {
            var2 += 22;
            ImageFactory.drawChar(this.clientsFace, this.innerWindowOffsetX, var2, 20);
            Font.drawString(var3.getAgent().fullName, this.innerWindowOffsetX + this.innerWindowWidth, var2, var3.getAgent().getEvent() > 0 ? 0 : 1, 18);
            Font.drawString(var3.getAgent().getStationName(), this.innerWindowOffsetX + this.innerWindowWidth, var2 + (Font.getFontSpacingY() << 1), 1, 18);
            var2 += ImageFactory.faceHeight + 2;
         }

         Font.drawLines(this.freelanceRows, this.innerWindowOffsetX + 4, var2, 1);
         GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
         MissionsWindow var5;
         if ((var5 = this).scrollThumbSize > 0) {
            var2 = (int)((float)(var5.innerWindowOffsetY - var5.scrollPos) / (float)(var5.contentHeight - var5.innerWindowsHeight) * (float)(var5.innerWindowsHeight - var5.scrollThumbSize));
            GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GlobalStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.innerWindowsHeight - 1);
            GlobalStatus.graphics.setColor(-35072);
            GlobalStatus.graphics.fillRect(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var2, 3, var5.scrollThumbSize);
            GlobalStatus.graphics.setColor(-4827904);
            GlobalStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var5.scrollThumbSize - 2 + var2);
            GlobalStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 2, var5.innerWindowOffsetY + var5.scrollThumbSize - 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.scrollThumbSize - 1 + var2);
            GlobalStatus.graphics.setColor(-11520);
            GlobalStatus.graphics.drawLine(var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + 1 + var2, var5.innerWindowOffsetX + var5.innerWindowWidth + 3, var5.innerWindowOffsetY + var5.scrollThumbSize - 2 + var2);
            GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         }

         if (this.actionsOpen) {
            Layout.fillClip();
            Layout.drawFooter(this.highlightedActions == null ? "" : GlobalStatus.gameText.getText(136), GlobalStatus.gameText.getText(65));
            Layout.drawFilledTitleBarWindow("", 1, this.actionsPosY, this.actionsWidth, 12 + (this.highlightedActions.length + 1) * 10);
            if (this.campainMissionActions > 0) {
               Layout.drawTextItem(GlobalStatus.gameText.getText(245) + " (" + GlobalStatus.gameText.getText(278) + ")", 1, this.actionsPosY + 15, this.actionsWidth, this.highlightedActions[0] == 1, false, true);
            }

            if (!Status.getFreelanceMission().isEmpty()) {
               var1 = this.campainMissionActions * 10;
               Layout.drawTextItem(GlobalStatus.gameText.getText(245) + " (" + GlobalStatus.gameText.getText(279) + ")", 1, this.actionsPosY + 15 + var1, this.actionsWidth, this.highlightedActions[this.campainMissionActions] == 1, false, true);
               Layout.drawTextItem(GlobalStatus.gameText.getText(244), 1, this.actionsPosY + 15 + 10 + var1, this.actionsWidth, this.highlightedActions[this.campainMissionActions + 1] == 1, false, true);
            }
         }

         if (this.popupOpen) {
            this.popup.draw();
         }

      }
   }

   private void drawMissionSubsection(int var1, String var2, Image var3) {
      GlobalStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GlobalStatus.graphics.fillRect(this.innerWindowOffsetX, var1, this.innerWindowWidth, 17);
      GlobalStatus.graphics.setColor(0);
      GlobalStatus.graphics.drawRect(this.innerWindowOffsetX + 1, var1 + 1, this.innerWindowWidth - 3, 16);
      GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GlobalStatus.graphics.drawRect(this.innerWindowOffsetX, var1, this.innerWindowWidth - 1, 18);
      Layout.drawMenuPanelCorner(this.innerWindowOffsetX, var1, false);
      GlobalStatus.graphics.drawImage(var3, this.innerWindowOffsetX + 6, var1 + 4, 20);
      Font.drawString(var2, this.innerWindowOffsetX + 6 + var3.getWidth() + 4, var1 + 4, 0);
   }

   public final boolean hangarNeedsUpdate() {
      return this.hangarNeedsUpdate;
   }

   public final void setHangarUpdate(boolean var1) {
      this.hangarNeedsUpdate = false;
   }
}
