package Main;

import AbyssEngine.AEImage;
import AbyssEngine.Face_;
import AbyssEngine.GameStatus;
import AbyssEngine.IndexManager;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Medals;
import AbyssEngine.Popup;
import AbyssEngine.Standing;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import AbyssEngine.Time;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class StatusBlock {
   private boolean infoOpen;
   private Popup infoMedal;
   private StatusWindow var_14b;
   private String[] var_1d1;
   private String[] var_1de;
   private Sprite var_22c;
   private Sprite var_288;
   private Sprite var_2e3;
   private Sprite var_33f;
   private Image var_372;
   private Image var_3b2;
   private int posY;
   private int scrollRows;
   private int posX;
   private int width;
   private int height;
   private int scrollThumbSize;
   private int scrollPos;
   private Image[] var_51c;

   public StatusBlock() {
      this.var_14b = new StatusWindow(new String[]{GameStatus.langManager.getLangString(64), GameStatus.langManager.getLangString(63)}, GameStatus.langManager.getLangString(64));
      this.infoOpen = false;
      this.infoMedal = new Popup(20, GameStatus.screenHeight / 3, GameStatus.screenWidth - 40);
      this.sub_6d();
      this.posY = 35;
      this.posX = 9;
      this.scrollPos = this.posY;
      this.scrollRows = GameStatus.screenHeight - this.posY - 16 - 5;
      this.width = GameStatus.screenWidth - 2 * this.posX;
      this.height = 66 + Face_.faceHeight + 4 + 6 * (SymbolMapManager_.sub_2c2() + 2) + this.var_1d1.length * (SymbolMapManager_.sub_2c2() + 2);
      this.scrollThumbSize = 0;
      if (this.height > this.scrollRows) {
         this.scrollThumbSize = (int)((float)this.scrollRows / (float)this.height * (float)this.scrollRows);
         this.width -= 5;
      }

      String var1 = "/data/interface/logos_small.png";
      Image var2 = AEImage.loadImage("/data/interface/logos_small.png", true);
      this.var_33f = new Sprite(var2, var2.getHeight(), var2.getHeight());
      var1 = null;
      if (GameStatus.screenWidth < 240) {
         var1 = "/data/interface/standing_small_0.png";
         var2 = AEImage.loadImage("/data/interface/standing_small_0.png", true);
      } else {
         var1 = "/data/interface/standing_0.png";
         var2 = AEImage.loadImage("/data/interface/standing_0.png", true);
      }

      this.var_22c = new Sprite(var2, var2.getWidth() / 3, var2.getHeight());
      this.var_22c.defineReferencePixel(0, this.var_22c.getHeight());
      if (GameStatus.screenWidth < 240) {
         var1 = "/data/interface/standing_small_1.png";
         var2 = AEImage.loadImage("/data/interface/standing_small_1.png", true);
      } else {
         var1 = "/data/interface/standing_1.png";
         var2 = AEImage.loadImage("/data/interface/standing_1.png", true);
      }

      this.var_288 = new Sprite(var2);
      this.var_288.defineReferencePixel(0, this.var_288.getHeight());
      if (GameStatus.screenWidth < 240) {
         var1 = "/data/interface/standing_small_2.png";
         var2 = AEImage.loadImage("/data/interface/standing_small_2.png", true);
      } else {
         var1 = "/data/interface/standing_2.png";
         var2 = AEImage.loadImage("/data/interface/standing_2.png", true);
      }

      this.var_2e3 = new Sprite(var2);
      this.var_2e3.defineReferencePixel(this.var_2e3.getWidth() >> 1, 0);
   }

   public final void sub_37() {
      if (this.var_14b != null) {
         this.var_14b.sub_82();
      }

      this.var_14b = null;
      this.infoMedal = null;
      this.var_1d1 = null;
      this.var_1de = null;
      this.var_22c = null;
      this.var_288 = null;
      this.var_2e3 = null;
      this.var_33f = null;
   }

   public final void sub_6d() {
      this.var_1d1 = new String[11];
      this.var_1de = new String[11];
      this.var_1d1[0] = GameStatus.langManager.getLangString(77);
      this.var_1de[0] = GameStatus.langManager.getLangString(532 + Status.getShip().getIndex());
      this.var_1d1[1] = GameStatus.langManager.getLangString(290);
      this.var_1de[1] = "" + Status.getShip().getFirePower();
      this.var_1d1[2] = GameStatus.langManager.getLangString(291);
      this.var_1de[2] = "" + Status.getShip().getCombinedHP();
      this.var_1d1[3] = GameStatus.langManager.getLangString(33);
      this.var_1de[3] = "" + Status.getMissionCount();
      this.var_1d1[4] = GameStatus.langManager.getLangString(71);
      this.var_1de[4] = "" + Status.getKills();
      this.var_1d1[5] = GameStatus.langManager.getLangString(282);
      this.var_1de[5] = "" + Status.getCargoSalvaged();
      this.var_1d1[6] = GameStatus.langManager.getLangString(280);
      this.var_1de[6] = "" + Status.getStationsVisited();
      this.var_1d1[7] = GameStatus.langManager.getLangString(287);
      this.var_1de[7] = "" + Status.getJumpgateUsed();
      this.var_1d1[8] = GameStatus.langManager.getLangString(281);
      this.var_1de[8] = "" + Status.getGoodsProduced();
      this.var_1d1[9] = GameStatus.langManager.getLangString(283);
      this.var_1de[9] = "" + Status.minedOre;
      this.var_1d1[10] = GameStatus.langManager.getLangString(284);
      this.var_1de[10] = "" + Status.minedCores;
   }

   public final boolean sub_8f(int var1) {
      if ((var1 == 256 || var1 == 16384) && this.var_14b.sub_1e5() == 1 && !this.infoOpen) {
         ListEntry var3 = (ListEntry)((ListEntry)this.var_14b.sub_1a3());
         if (Medals.getMedals()[var3.itemId] != 0) {
            String var2 = Status.replaceToken(GameStatus.langManager.getLangString(782 + var3.itemId), Medals.medalTresholds[var3.itemId][var3.medalTier - 1] + "");
            if (var3.itemId == 2 && var3.medalTier == 2) {
               var2 = var2 + "\n\n" + GameStatus.langManager.getLangString(134);

               for(var1 = 0; var1 < Status.minedOreTypes.length; ++var1) {
                  if (!Status.minedOreTypes[var1]) {
                     var2 = var2 + "\n- " + GameStatus.langManager.getLangString(var1 + 723);
                  }
               }
            } else if (var3.itemId == 3 && var3.medalTier == 2) {
               var2 = var2 + "\n\n" + GameStatus.langManager.getLangString(134);

               for(var1 = 0; var1 < Status.minedCoreTypes.length; ++var1) {
                  if (!Status.minedCoreTypes[var1]) {
                     var2 = var2 + "\n- " + GameStatus.langManager.getLangString(var1 + 734);
                  }
               }
            } else if (var3.itemId == 9 && var3.medalTier == 2) {
               var2 = var2 + "\n\n" + GameStatus.langManager.getLangString(134);

               for(var1 = 0; var1 < Status.drinksVar1.length; ++var1) {
                  if (!Status.drinksVar1[var1]) {
                     var2 = var2 + "\n- " + GameStatus.langManager.getLangString(var1 + 701);
                  }
               }
            } else if (var3.itemId == 13 && var3.medalTier == 2) {
               var2 = var2 + "\n\n" + GameStatus.langManager.getLangString(134);

               for(var1 = 0; var1 < Status.blueprints.length; ++var1) {
                  if (!Status.blueprints[var1].unlocked) {
                     var2 = var2 + "\n- " + GameStatus.langManager.getLangString(569 + Status.blueprints[var1].getIndex());
                  }
               }
            } else if (var3.itemId == 14 && var3.medalTier == 2) {
               var2 = var2 + "\n\n" + GameStatus.langManager.getLangString(134);

               for(var1 = 0; var1 < Status.blueprints.length; ++var1) {
                  if (Status.blueprints[var1].timesProduced == 0) {
                     var2 = var2 + "\n- " + GameStatus.langManager.getLangString(569 + Status.blueprints[var1].getIndex());
                  }
               }
            }

            this.infoMedal.sub_8f(var2, false);
            this.infoOpen = true;
         }

         return true;
      } else if (!this.infoOpen) {
         if (var1 == 4) {
            this.var_14b.sub_2eb();
         }

         if (var1 == 32) {
            this.var_14b.sub_2f6();
         }

         if (this.var_14b.selectedTab == 1) {
            if (var1 == 2) {
               this.var_14b.sub_b4();
            }

            if (var1 == 64) {
               this.var_14b.sub_6b();
            }
         }

         return var1 != 8192;
      } else {
         if ((this.var_14b.selectedTab == 1 || this.var_14b.selectedTab == 2) && var1 == 256) {
            this.infoOpen = false;
         }

         return true;
      }
   }

   private void sub_a2(int var1, String var2) {
      int var3 = this.width;
      GameStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GameStatus.graphics.fillRect(this.posX, var1, var3, 17);
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawRect(this.posX + 1, var1 + 1, var3 - 3, 16);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.drawRect(this.posX, var1, var3 - 1, 18);
      Layout.sub_39a(this.posX, var1, false);
      SymbolMapManager_.sub_102(var2, this.posX + 6, var1 + 4, 0);
   }

   public final void sub_d8(int var1, int var2) {
      Layout.screenFillMenuBackground();
      this.var_14b.sub_6c();
      if (this.var_14b.selectedTab == 0) {
         if (this.scrollThumbSize > 0) {
            if ((var1 & 2) != 0) {
               this.scrollPos += var2 / 8;
               if (this.scrollPos > this.posY) {
                  this.scrollPos = this.posY;
               }
            }

            if ((var1 & 64) != 0) {
               this.scrollPos -= var2 / 8;
               if (this.scrollPos + this.height < this.posY + this.scrollRows) {
                  this.scrollPos = this.posY + this.scrollRows - this.height;
               }
            }
         }

         StatusBlock var7 = this;
         var2 = SymbolMapManager_.sub_2c2();
         GameStatus.graphics.setColor(0);
         GameStatus.graphics.setClip(0, this.posY, GameStatus.screenWidth, this.scrollRows);
         int var3 = this.scrollPos;
         this.sub_a2(var3, GameStatus.langManager.getLangString(819));
         var3 += 22;
         if (this.var_51c == null) {
            this.var_51c = Face_.faceFromByteArray(IndexManager.CHAR_KEITH);
         }

         Face_.drawFace(this.var_51c, this.posX, var3, 20);
         String var4;
         if (Medals.gotAllGoldMedals()) {
            if (this.var_3b2 == null) {
               var4 = "/data/interface/faces/Brille_golden.png";
               this.var_3b2 = AEImage.loadImage("/data/interface/faces/Brille_golden.png", true);
            }

            GameStatus.graphics.drawImage(this.var_3b2, this.posX + 1, var3 + 1, 20);
         } else if (Medals.gotAllMedals()) {
            if (this.var_372 == null) {
               var4 = "/data/interface/faces/Brille_schwarz.png";
               this.var_372 = AEImage.loadImage("/data/interface/faces/Brille_schwarz.png", true);
            }

            GameStatus.graphics.drawImage(this.var_372, this.posX + 1, var3 + 1, 20);
         }

         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(80), this.posX + Face_.faceWidth + 4, var3, 1, 17);
         SymbolMapManager_.sub_161(Layout.formatCredits(Status.getCredits()), this.posX + this.width, var3, 1, 18);
         var3 += var2 + 2;
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(158), this.posX + Face_.faceWidth + 4, var3, 1, 17);
         SymbolMapManager_.sub_161(Status.getLevel() + "", this.posX + this.width, var3, 1, 18);
         var3 += var2 + 2;
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(70), this.posX + Face_.faceWidth + 4, var3, 1, 17);
         SymbolMapManager_.sub_161(Time.timeToHMS(Status.getPlayingTime()), this.posX + this.width, var3, 1, 18);
         var3 = this.scrollPos + 18 + 4 + Face_.faceHeight + 4;
         this.sub_a2(var3, GameStatus.langManager.getLangString(298));
         var3 += 26;
         Standing var8 = Status.getStanding();

         int var5;
         for(var5 = 0; var5 < 2; ++var5) {
            int var6 = var7.posX + 6;
            var7.var_33f.setFrame(var5 == 0 ? 0 : 2);
            var7.var_33f.setPosition(var6, var3);
            var7.var_33f.paint(GameStatus.graphics);
            var6 += var7.var_33f.getHeight() + 4;
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(var5 == 0 ? 229 : 231), var6, var3 + SymbolMapManager_.sub_2c2() + var7.var_2e3.getHeight(), 1);
            var7.var_22c.setTransform(0);
            var7.var_22c.setFrame(var8.isEnemy(var5 == 0 ? 0 : 2) ? 0 : (var8.isFriend(var5 == 0 ? 0 : 2) ? 1 : 2));
            var7.var_22c.setRefPixelPosition(var6, var3 + var7.var_22c.getHeight());
            var7.var_22c.paint(GameStatus.graphics);
            var6 += var7.var_22c.getWidth() + 1;
            var7.var_288.setTransform(0);
            var7.var_288.setRefPixelPosition(var6, var3 + var7.var_22c.getHeight());
            var7.var_288.paint(GameStatus.graphics);
            var6 = var7.posX + var7.width - 2;
            var7.var_33f.setFrame(var5 == 0 ? 1 : 3);
            var7.var_33f.setPosition(var6 - var7.var_33f.getHeight(), var3);
            var7.var_33f.paint(GameStatus.graphics);
            var6 -= 4 + var7.var_33f.getHeight();
            SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(var5 == 0 ? 230 : 232), var6, var3 + SymbolMapManager_.sub_2c2() + var7.var_2e3.getHeight(), 1, 18);
            var7.var_22c.setTransform(2);
            var7.var_22c.setFrame(var8.isEnemy(var5 == 0 ? 1 : 3) ? 0 : (var8.isFriend(var5 == 0 ? 1 : 3) ? 1 : 2));
            var7.var_22c.setRefPixelPosition(var6, var3 + var7.var_22c.getHeight());
            var7.var_22c.paint(GameStatus.graphics);
            var6 -= var7.var_22c.getWidth() + 1;
            var7.var_288.setTransform(2);
            var7.var_288.setRefPixelPosition(var6, var3 + var7.var_22c.getHeight());
            var7.var_288.paint(GameStatus.graphics);
            var6 = (GameStatus.screenWidth >> 1) - (int)((float)var8.getStanding(var5 == 0 ? 0 : 1) / 100.0F * (float)(var7.var_22c.getWidth() + 1 + var7.var_288.getWidth()));
            var7.var_2e3.setRefPixelPosition(var6, var3 + var7.var_22c.getHeight());
            var7.var_2e3.paint(GameStatus.graphics);
            var3 += 3 * SymbolMapManager_.sub_2c2() + 4;
         }

         var7.sub_a2(var3, GameStatus.langManager.getLangString(299));
         var3 += 22;

         for(var5 = 0; var5 < var7.var_1d1.length; ++var5) {
            SymbolMapManager_.sub_161(var7.var_1d1[var5], var7.posX + 2, var3, 1, 17);
            SymbolMapManager_.sub_161(var7.var_1de[var5], var7.posX + var7.width, var3, 0, 18);
            var3 += var2 + 2;
         }

         GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
         var2 = var7.width;
         var3 = var7.height;
         int var9 = var7.scrollPos;
         if ((var5 = var7.scrollThumbSize) > 0) {
            var3 = (int)((float)(var7.posY - var9) / (float)(var3 - var7.scrollRows) * (float)(var7.scrollRows - var5));
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GameStatus.graphics.drawLine(var7.posX + var2 + 3, var7.posY, var7.posX + var2 + 3, var7.posY + var7.scrollRows - 1);
            GameStatus.graphics.setColor(-35072);
            GameStatus.graphics.fillRect(var7.posX + var2 + 2, var7.posY + var3, 3, var5);
            GameStatus.graphics.setColor(-4827904);
            GameStatus.graphics.drawLine(var7.posX + var2 + 2, var7.posY + 1 + var3, var7.posX + var2 + 2, var7.posY + var5 - 2 + var3);
            GameStatus.graphics.drawLine(var7.posX + var2 + 2, var7.posY + var5 - 1 + var3, var7.posX + var2 + 3, var7.posY + var5 - 1 + var3);
            GameStatus.graphics.setColor(-11520);
            GameStatus.graphics.drawLine(var7.posX + var2 + 3, var7.posY + 1 + var3, var7.posX + var2 + 3, var7.posY + var5 - 2 + var3);
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
         }
      }

      if (this.var_14b.sub_1e5() == 1) {
         Layout.sub_3c6(((ListEntry)this.var_14b.sub_1a3()).medalTier > 0 ? GameStatus.langManager.getLangString(212) : "", GameStatus.langManager.getLangString(65));
      } else {
         Layout.sub_3c6("", GameStatus.langManager.getLangString(65));
      }

      if (this.infoOpen) {
         this.infoMedal.sub_19a();
      }

   }
}
