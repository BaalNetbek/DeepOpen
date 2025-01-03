package AbyssEngine;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class Hud {
   private String[][] actionmenuLabels = new String[][]{{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}};
   private byte[][] actionmenuButtonsState;
   private static int var_2be;
   private static int drawSecondaryIcon = -1;
   private int var_341;
   private int var_38f;
   private int var_3e2;
   private int var_429;
   private Image var_489;
   private Image var_4ca;
   private Image var_568;
   private Sprite var_590;
   private Sprite var_5f3;
   private Sprite var_60b;
   private Image var_655;
   private Image var_67b;
   private Sprite var_6c4;
   private Sprite var_721;
   private Sprite var_72c;
   private Image var_786;
   private Image var_7e9;
   public Image var_831;
   private Image factionLogo;
   private int var_8bd;
   private String tempLogMsg;
   private String logMsg = "";
   private boolean drawBoostIcon;
   private boolean var_986;
   private boolean var_9c7;
   private boolean var_9f9;
   private int var_a21;
   public boolean drawUI;
   private boolean cargoFull;
   private boolean actionmenuOpen;
   private int var_b01;
   private int actionmenuSelectDir;
   private int indicatorIconsOffset;
   private boolean var_bdf;
   private boolean actionmenuConverging;
   private boolean var_c34;
   private boolean var_c84;
   private int var_ce3;
   private int var_cee;
   private int var_d45;
   private Item[] secondaries;
   private Item[] specialEquipment;
   private ListEntry[] logMessages;
   private int firstLogEntryLifeTime;
   private boolean displayLog_;
   private int var_ef4;
   private boolean var_f29;

   public Hud() {
      this.sub_a6();
   }

   private void sub_a6() {
      try {
         this.logMessages = new ListEntry[20];
         String var1 = "/data/interface/items.png";
         this.var_831 = AEImage.loadImage("/data/interface/items.png", true);
         var1 = "/data/interface/hud_hull_alarm_numbers.png";
         this.var_72c = new Sprite(AEImage.loadImage("/data/interface/hud_hull_alarm_numbers.png", true), 13, 12);
         var1 = "/data/interface/hud_hull_alarm.png";
         this.var_786 = AEImage.loadImage("/data/interface/hud_hull_alarm.png", true);
         var1 = "/data/interface/hud_symbol_shield_png24.png";
         this.var_489 = AEImage.loadImage("/data/interface/hud_symbol_shield_png24.png", true);
         var1 = "/data/interface/hud_symbol_hull_png24.png";
         this.var_4ca = AEImage.loadImage("/data/interface/hud_symbol_hull_png24.png", true);
         var1 = "/data/interface/hud_hull_bar_empty_png24.png";
         this.var_655 = AEImage.loadImage("/data/interface/hud_hull_bar_empty_png24.png", true);
         var1 = "/data/interface/hud_hull_bar_full_png24.png";
         this.var_67b = AEImage.loadImage("/data/interface/hud_hull_bar_full_png24.png", true);
         var1 = "/data/interface/hud_hull_bar_hit_png24.png";
         AEImage.loadImage("/data/interface/hud_hull_bar_hit_png24.png", true);
         var1 = "/data/interface/hud_panel_upper_left_png24.png";
         Image var7 = AEImage.loadImage("/data/interface/hud_panel_upper_left_png24.png", true);
         this.var_6c4 = new Sprite(var7);
         var1 = "/data/interface/hud_panel_lower_left_png24.png";
         var7 = AEImage.loadImage("/data/interface/hud_panel_lower_left_png24.png", true);
         this.var_721 = new Sprite(var7);
         var1 = "/data/interface/hud_icons.png";
         var7 = AEImage.loadImage("/data/interface/hud_icons.png", true);
         this.indicatorIconsOffset = var7.getHeight();
         this.var_590 = new Sprite(var7, this.indicatorIconsOffset, this.indicatorIconsOffset);
         var1 = "/data/interface/quickmenu_crosshair_anim.png";
         var7 = AEImage.loadImage("/data/interface/quickmenu_crosshair_anim.png", true);
         this.var_5f3 = new Sprite(var7, var7.getHeight(), var7.getHeight());
         this.var_5f3.defineReferencePixel(this.var_5f3.getWidth() >> 1, this.var_5f3.getHeight() >> 1);
         var1 = "/data/interface/quickmenu.png";
         var7 = AEImage.loadImage("/data/interface/quickmenu.png", true);
         this.var_60b = new Sprite(var7, var7.getHeight(), var7.getHeight());
         this.var_60b.defineReferencePixel(this.var_60b.getWidth() >> 1, this.var_60b.getHeight() >> 1);
         var1 = "/data/interface/hud_hit_png24.png";
         this.var_568 = AEImage.loadImage("/data/interface/hud_hit_png24.png", true);
         this.var_38f = GameStatus.screenWidth / 2 - 21;
         this.var_3e2 = this.var_38f + 14;
         this.var_429 = this.var_3e2 + 14;
         this.var_341 = GameStatus.screenWidth >> 1;
         this.drawBoostIcon = Status.getShip().getBoostRegenTime() > 0;
         this.var_986 = Status.getShip().getShield() > 0;
         this.var_9c7 = Status.getShip().getAdditionalArmour() > 0;
         this.var_9f9 = Status.getShip().getFirePower() > 0;
         this.actionmenuButtonsState = new byte[5][4];
         Item[] var8 = Status.getShip().getEquipment(1);
         boolean var2 = true;
         if (var8 != null) {
            for(int var3 = 0; var3 < var8.length; ++var3) {
               if (var8[var3] != null) {
                  var2 = false;
               }
            }
         }

         if (!var2) {
            this.actionmenuButtonsState[0][0] = 1;
            this.sub_192();
            this.actionmenuLabels[0][0] = GameStatus.langManager.getLangString(124);
         } else {
            this.actionmenuLabels[0][0] = "";
            this.actionmenuButtonsState[0][0] = 0;
         }

         Item var6 = Status.getShip().getEquipedOfSubType(21);
         Item var9 = Status.getShip().getEquipedOfSubType(18);
         if (var6 == null && var9 == null) {
            this.specialEquipment = null;
         } else {
            this.specialEquipment = new Item[var6 != null && var9 != null ? 2 : 1];
         }

         int var5 = 0;
         if (var6 != null) {
            this.actionmenuButtonsState[0][1] = 1;
            this.actionmenuLabels[0][1] = GameStatus.langManager.getLangString(569 + var6.getIndex());
            ++var5;
            this.specialEquipment[0] = var6;
         } else {
            this.actionmenuButtonsState[0][1] = 0;
            this.actionmenuLabels[0][1] = "";
         }

         if (var9 != null) {
            this.actionmenuButtonsState[0][3] = 1;
            this.actionmenuLabels[0][3] = GameStatus.langManager.getLangString(569 + var9.getIndex());
            this.specialEquipment[var5] = var9;
         } else {
            this.actionmenuButtonsState[0][3] = 0;
            this.actionmenuLabels[0][3] = "";
         }

         if (Status.getWingmenNames() != null) {
            this.actionmenuButtonsState[0][2] = 1;
            this.actionmenuButtonsState[3][0] = 1;
            this.actionmenuButtonsState[3][1] = 1;
            this.actionmenuButtonsState[3][2] = 1;
            this.actionmenuButtonsState[3][3] = 1;
            this.actionmenuLabels[0][2] = GameStatus.langManager.getLangString(146);
            this.actionmenuLabels[3][0] = GameStatus.langManager.getLangString(147);
            this.actionmenuLabels[3][1] = GameStatus.langManager.getLangString(148);
            this.actionmenuLabels[3][2] = GameStatus.langManager.getLangString(149);
            this.actionmenuLabels[3][3] = GameStatus.langManager.getLangString(151);
         } else {
            this.actionmenuLabels[0][2] = "";
         }

         this.logMsg = "";
         this.cargoFull = false;
         this.actionmenuOpen = false;
         this.drawUI = true;
         this.var_f29 = false;
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   private void sub_192() {
      this.secondaries = Status.getShip().getEquipment(1);
      Item[] var1 = this.secondaries;

      for(int var2 = 0; var2 < this.actionmenuLabels[1].length; ++var2) {
         if (var2 < var1.length && var1[var2] != null && var1[var2].getAmount() > 0) {
            this.actionmenuButtonsState[1][var2] = 1;
            this.actionmenuLabels[1][var2] = var1[var2].toString() + "(" + var1[var2].getAmount() + ")";
         } else {
            if (var2 < var1.length && var1[var2] == null) {
               this.actionmenuButtonsState[1][var2] = 0;
            }

            this.actionmenuLabels[1][var2] = "";
         }
      }

   }

   public final void sub_1e9() {
      this.var_4ca = null;
      this.var_489 = null;
   }

   private void sub_264() {
      int var1 = 25 - this.var_ef4;
      int var2 = Face_.var_268 - 2;
      if (this.var_ef4 > 0 && this.var_ef4 < var2) {
         ++this.var_ef4;
      }

      GameStatus.graphics.setClip(0, var2 + 25, GameStatus.screenWidth, var2 * 2);

      for(int var3 = 0; var3 < 4; ++var3) {
         ListEntry var4;
         if ((var4 = this.logMessages[var3]) != null) {
            boolean var5;
            if (var5 = var4.var_638 != null) {
               Face_.sub_158(var4.itemId, var4.var_638, (GameStatus.screenWidth >> 1) + Face_.faceWidth / 2 - (SymbolMapManager_.sub_25b(var4.label, 0) >> 1) - 5, var1 + var3 * var2 - 2, 24);
            }

            SymbolMapManager_.sub_161(var4.label, (GameStatus.screenWidth >> 1) + (var5 ? Face_.faceWidth / 2 - 5 : 0), var1 + var3 * var2, var3 != 1 && (var3 != 2 || this.var_ef4 != var2) ? 1 : (var4.displayedCountType == 1 ? 2 : 0), 24);
         }
      }

      GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
   }

   private void appendLogEntry(ListEntry var1) {
      for(int var2 = 1; var2 < this.logMessages.length; ++var2) {
         if (this.logMessages[var2] == null) {
            this.logMessages[var2] = var1;
            this.displayLog_ = true;
            return;
         }
      }

   }

   private void updateLogDisplay(int var1) {
      this.firstLogEntryLifeTime += var1;
      if (this.firstLogEntryLifeTime <= 4000) {
         if (this.var_ef4 == 0 && this.firstLogEntryLifeTime > 2000) {
            this.var_ef4 = 1;
         }

      } else {
         this.firstLogEntryLifeTime = 0;

         for(var1 = 1; var1 < this.logMessages.length; ++var1) {
            this.logMessages[var1 - 1] = this.logMessages[var1];
         }

         if (this.logMessages[1] == null) {
            this.displayLog_ = false;
         }

         this.var_ef4 = 0;
      }
   }

   public final void sub_30d(int var1, PlayerEgo var2) {
      switch(var1) {
      case 1:
         if (!this.var_9f9) {
            return;
         }

         this.tempLogMsg = GameStatus.langManager.getLangString(13) + " " + GameStatus.langManager.getLangString(15);
         break;
      case 2:
         if (!this.var_9f9) {
            return;
         }

         this.tempLogMsg = GameStatus.langManager.getLangString(13) + " " + GameStatus.langManager.getLangString(16);
         break;
      case 3:
         if (!this.drawBoostIcon || !var2.sub_3c9()) {
            return;
         }

         this.tempLogMsg = GameStatus.langManager.getLangString(154);
         break;
      case 4:
         if (!this.drawBoostIcon) {
            return;
         }

         this.tempLogMsg = GameStatus.langManager.getLangString(155);
         break;
      case 5:
         this.tempLogMsg = GameStatus.langManager.getLangString(292) + " " + GameStatus.langManager.getLangString(15);
         break;
      case 6:
         this.tempLogMsg = GameStatus.langManager.getLangString(292) + " " + GameStatus.langManager.getLangString(16);
         break;
      case 7:
         this.tempLogMsg = GameStatus.langManager.getLangString(276);
         break;
      case 8:
         this.tempLogMsg = GameStatus.langManager.getLangString(263);
         break;
      case 9:
         this.tempLogMsg = GameStatus.langManager.getLangString(264);
         break;
      case 10:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + Status.getStation().getName() + " " + GameStatus.langManager.getLangString(40);
         break;
      case 11:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + GameStatus.langManager.getLangString(274);
         break;
      case 12:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + GameStatus.langManager.getLangString(271);
         break;
      case 13:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + GameStatus.langManager.getLangString(272);
         break;
      case 14:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + GameStatus.langManager.getLangString(273);
         break;
      case 15:
         this.tempLogMsg = GameStatus.langManager.getLangString(270) + ": " + GameStatus.langManager.getLangString(269);
         break;
      case 16:
         this.tempLogMsg = GameStatus.langManager.getLangString(147);
         break;
      case 17:
         this.tempLogMsg = GameStatus.langManager.getLangString(148);
         break;
      case 18:
         this.tempLogMsg = GameStatus.langManager.getLangString(149);
         break;
      case 19:
         this.tempLogMsg = this.actionmenuLabels[4][3];
         break;
      case 20:
         this.tempLogMsg = GameStatus.langManager.getLangString(265);
         break;
      case 21:
         this.tempLogMsg = GameStatus.langManager.getLangString(254);
         break;
      case 22:
         this.tempLogMsg = GameStatus.langManager.getLangString(266);
         break;
      case 23:
         this.tempLogMsg = GameStatus.langManager.getLangString(267);
         break;
      case 24:
         this.tempLogMsg = GameStatus.langManager.getLangString(268);
      }

      if (!this.isSimilarEntryInLog(this.tempLogMsg)) {
         this.appendLogEntry(new ListEntry(this.tempLogMsg));
         SymbolMapManager_.sub_25b(this.tempLogMsg, 0);
      }
   }

   private boolean isSimilarEntryInLog(String var1) {
      for(int var2 = this.logMessages.length - 1; var2 > 0; --var2) {
         if (this.logMessages[var2] != null && this.logMessages[var2].label.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   public final void sub_362() {
      this.var_bdf = true;
   }

   public final void sub_3ae(int var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this.cargoFull = var3;
      ListEntry var7;
      if (var4) {
         this.logMsg = GameStatus.langManager.getLangString(261);
         this.logMsg = Status.replaceTokens(this.logMsg, GameStatus.langManager.getLangString(569 + (Status.getMission().getType() == 3 ? 116 : 117)), "#N");
         this.logMsg = Status.replaceTokens(this.logMsg, "1", "#Q");
         (var7 = new ListEntry(this.logMsg)).var_638 = this.var_831;
         var7.itemId = var1;
      } else {
         if (var3) {
            this.logMsg = GameStatus.langManager.getLangString(159);
            this.appendLogEntry(new ListEntry(this.logMsg, 1));
            return;
         }

         if (var2 <= 0) {
            return;
         }

         if (var5) {
            this.logMsg = var2 + "t " + GameStatus.langManager.getLangString(var1 + 569);
         } else if (var6) {
            this.logMsg = GameStatus.langManager.getLangString(var1 + 569);
         } else {
            this.logMsg = var2 + "x " + GameStatus.langManager.getLangString(var1 + 569);
         }

         (var7 = new ListEntry(this.logMsg)).var_638 = this.var_831;
         var7.itemId = var1;
         if (var6) {
            var7.isSelectable = true;
         }
      }

      this.appendLogEntry(var7);
   }

   public final boolean sub_3fa() {
      return this.cargoFull;
   }

   public final void sub_41b(long var1, long var3, PlayerEgo var5, boolean var6) {
      if (this.drawUI) {
         if (this.var_bdf) {
            this.var_bdf = false;
            GameStatus.graphics.drawImage(this.var_568, GameStatus.screenWidth >> 1, GameStatus.screenHeight, 33);
         }

         this.var_6c4.setTransform(0);
         this.var_6c4.setPosition(0, 0);
         this.var_6c4.paint(GameStatus.graphics);
         this.var_6c4.setTransform(2);
         this.var_6c4.setPosition(GameStatus.screenWidth - this.var_6c4.getWidth(), 0);
         this.var_6c4.paint(GameStatus.graphics);
         this.var_721.setTransform(0);
         this.var_721.setPosition(0, GameStatus.screenHeight - this.var_721.getHeight());
         this.var_721.paint(GameStatus.graphics);
         this.var_721.setTransform(2);
         this.var_721.setPosition(GameStatus.screenWidth - this.var_721.getWidth(), GameStatus.screenHeight - this.var_721.getHeight());
         this.var_721.paint(GameStatus.graphics);
         int var7 = this.var_67b.getWidth();
         boolean var8 = false;
         int var12;
         if (this.var_9c7) {
            GameStatus.graphics.drawImage(this.var_4ca, 4, GameStatus.screenHeight - this.var_721.getHeight() - 3, 36);
            GameStatus.graphics.drawImage(this.var_655, this.var_721.getWidth(), GameStatus.screenHeight - 13, 40);
            var12 = (int)((float)var5.player.sub_76e() / 100.0F * (float)var7);
            GameStatus.graphics.drawRegion(this.var_67b, var7 - var12, 0, var12, this.var_67b.getHeight(), 0, this.var_721.getWidth(), GameStatus.screenHeight - 13, 40);
         }

         if (this.var_986) {
            GameStatus.graphics.drawImage(this.var_489, GameStatus.screenWidth - 4, GameStatus.screenHeight - this.var_721.getHeight() - 3, 40);
            GameStatus.graphics.drawRegion(this.var_655, 0, 0, var7, this.var_67b.getHeight(), 2, GameStatus.screenWidth - this.var_721.getWidth(), GameStatus.screenHeight - 13, 36);
            var12 = (int)((float)var5.player.sub_73a() / 100.0F * (float)var7);
            GameStatus.graphics.drawRegion(this.var_67b, var7 - var12, 0, var12, this.var_67b.getHeight(), 2, GameStatus.screenWidth - this.var_721.getWidth(), GameStatus.screenHeight - 13, 36);
         }

         if (!this.var_c84 && drawSecondaryIcon > 0 && (var7 = var5.sub_82f()) >= 0 && this.secondaries[drawSecondaryIcon - 1] != null) {
            Face_.sub_158(var7, this.var_831, 0, GameStatus.screenHeight - SymbolMapManager_.sub_2c2(), 36);
            SymbolMapManager_.sub_161("x" + this.secondaries[drawSecondaryIcon - 1].getAmount(), 4, GameStatus.screenHeight - 2, 1, 33);
         }

         var7 = this.var_590.getHeight();
         if (this.drawBoostIcon) {
            if (var5.sub_3c9()) {
               this.var_590.setFrame(4);
            } else {
               this.var_590.setFrame(5);
            }

            this.var_590.setPosition(2, 2);
            this.var_590.paint(GameStatus.graphics);
            GameStatus.graphics.setClip(2, var7 + 2 - (int)(var5.getBoostCharge() * (float)var7), var7, var7);
            this.var_590.setFrame(4);
            this.var_590.setPosition(2, 2);
            this.var_590.paint(GameStatus.graphics);
            GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
         }

         if (var5.sub_a15()) {
            this.var_590.setFrame(1);
         } else {
            this.var_590.setFrame(2);
         }

         this.var_590.setPosition(4 + this.indicatorIconsOffset, 2);
         if (this.logMessages[1] == null || !this.logMessages[1].label.equals(GameStatus.langManager.getLangString(276)) || Layout.sub_119()) {
            this.var_590.paint(GameStatus.graphics);
         }

         if (this.var_9f9) {
            if (var6) {
               this.var_590.setFrame(14);
            } else {
               this.var_590.setFrame(15);
            }

            this.var_590.setPosition(2, 4 + this.indicatorIconsOffset);
            this.var_590.paint(GameStatus.graphics);
         }

         if (var5.isCloakPresent()) {
            this.var_590.setFrame(12);
            this.var_590.setPosition(GameStatus.screenWidth - 2 - this.indicatorIconsOffset, 2);
            this.var_590.paint(GameStatus.graphics);
            if (var5.sub_4ed()) {
               GameStatus.graphics.setClip(GameStatus.screenWidth - 2 - this.indicatorIconsOffset, var7 + 2 - (int)((1.0F - var5.getCloakCharge()) * (float)var7), var7, var7);
            } else {
               GameStatus.graphics.setClip(GameStatus.screenWidth - 2 - this.indicatorIconsOffset, var7 + 2 - (int)(var5.getCloakCharge() * (float)var7), var7, var7);
            }

            this.var_590.setFrame(11);
            this.var_590.setPosition(GameStatus.screenWidth - 2 - this.indicatorIconsOffset, 2);
            this.var_590.paint(GameStatus.graphics);
            GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
         }

         this.var_590.setFrame(17);
         this.var_590.setPosition(GameStatus.screenWidth - 4 - 2 * this.indicatorIconsOffset, 2);
         this.var_590.paint(GameStatus.graphics);
         if (Status.getShip().getFreeCargo() > 0) {
            this.var_590.setFrame(7);
         } else {
            this.var_590.setFrame(9);
         }

         this.var_590.setPosition(GameStatus.screenWidth - 2 - this.indicatorIconsOffset, 4 + this.indicatorIconsOffset);
         this.var_590.paint(GameStatus.graphics);
         int var10;
         if (this.var_c84) {
            var10 = this.actionmenuSelectDir;
            this.var_d45 = (int)((long)this.var_d45 - var1 / 10L);
            boolean var11 = false;
            if (this.var_d45 <= 0) {
               this.var_d45 = 0;
               var11 = true;
            }

            var12 = (GameStatus.screenWidth >> 1) + (var10 == 2 ? this.var_d45 : (var10 == 4 ? -this.var_d45 : 0));
            var10 = (GameStatus.screenHeight >> 1) + (var10 == 1 ? -this.var_d45 : (var10 == 3 ? this.var_d45 : 0));
            this.var_60b.setRefPixelPosition(var12, var10);
            this.var_60b.setFrame(2);
            if (!var11 || var11 && Layout.sub_119()) {
               this.var_60b.paint(GameStatus.graphics);
               if (this.var_b01 == 1) {
                  if ((var7 = var5.sub_82f()) >= 0) {
                     Face_.sub_158(var7, this.var_831, var12, var10, 3);
                  }
               } else if (this.var_b01 == 3) {
                  this.var_60b.setRefPixelPosition(var12, var10);
                  this.var_60b.setFrame(6 + this.actionmenuSelectDir);
                  this.var_60b.paint(GameStatus.graphics);
               }
            }

            this.var_ce3 = (int)((long)this.var_ce3 + var1);
            if (this.var_ce3 > 1500) {
               this.var_c84 = false;
            }
         }

         if (var3 > 0L) {
            SymbolMapManager_.sub_161(Time.timeToHMS(var3), GameStatus.screenWidth >> 1, 2, var3 < 10000L ? 2 : 1, 24);
         } else if (this.logMessages[1] != null && this.logMessages[1].var_638 != null && !this.logMessages[1].isSelectable) {
            SymbolMapManager_.sub_161(Status.getShip().getCurrentLoad() + " / " + Status.getShip().getCargoPlus() + "t", GameStatus.screenWidth >> 1, 2, 1, 24);
         } else if (var5.sub_be6()) {
            var10 = Status.getShip().getCurrentLoad() + var5.getMiningProgress();
            SymbolMapManager_.sub_161(var10 + " / " + Status.getShip().getCargoPlus() + "t", GameStatus.screenWidth >> 1, 2, var10 > Status.getShip().getCargoPlus() ? 2 : 1, 24);
         } else if (Status.getMission().getType() == 12) {
            SymbolMapManager_.sub_161(var5.var_887.egoScore + " : " + var5.var_887.challengerScore, GameStatus.screenWidth >> 1, 2, 1, 24);
         }

         if (this.displayLog_) {
            this.updateLogDisplay((int)var1);
            this.sub_264();
         }

         var10 = var5.sub_794();
         if (this.var_a21 > var10) {
            this.var_8bd = 0;
         }

         if (var10 < 100 && this.var_8bd < 3000) {
            if (Layout.sub_119()) {
               this.var_8bd = (int)((long)this.var_8bd + var1);
               GameStatus.graphics.drawImage(this.var_786, this.var_341, 41, 17);
               this.var_72c.setFrame(var10 / 10);
               this.var_72c.setPosition(this.var_38f, 55);
               this.var_72c.paint(GameStatus.graphics);
               this.var_72c.setFrame(var10 % 10);
               this.var_72c.setPosition(this.var_3e2, 55);
               this.var_72c.paint(GameStatus.graphics);
               this.var_72c.setFrame(10);
               this.var_72c.setPosition(this.var_429, 55);
               this.var_72c.paint(GameStatus.graphics);
            }
         } else if (var10 < 100) {
            if (this.var_7e9 == null) {
               String var9 = "/data/interface/hud_hull_alarm_shipicon.png";
               this.var_7e9 = AEImage.loadImage("/data/interface/hud_hull_alarm_shipicon.png", true);
            }

            GameStatus.graphics.drawImage(this.var_7e9, (GameStatus.screenWidth >> 1) - 4, GameStatus.screenHeight - this.var_721.getHeight() + 15, 40);
            SymbolMapManager_.sub_161(var10 + "%", GameStatus.screenWidth >> 1, GameStatus.screenHeight - this.var_721.getHeight() + 15, 0, 33);
         }

         this.var_a21 = var10;
      }
   }

   public final void sub_47c(int var1, int var2, int var3, boolean var4) {
      if (var1 > 9) {
         this.var_72c.setFrame(var1 / 10);
         this.var_72c.setPosition(var2, var3);
         this.var_72c.paint(GameStatus.graphics);
      }

      this.var_72c.setFrame(var1 % 10);
      this.var_72c.setPosition(var2 + 14, var3);
      this.var_72c.paint(GameStatus.graphics);
   }

   public final void sub_4bc() {
      if (!Status.inAlienOrbit()) {
         if (this.factionLogo == null) {
            this.factionLogo = AEImage.loadImage("/data/interface/logo_" + Status.getSystem().getRace() + ".png", true);
         }

         int var1 = this.factionLogo.getWidth() + 6;
         GameStatus.graphics.drawImage(this.factionLogo, 3, 3, 20);
         SymbolMapManager_.sub_102(Status.getStation().getName(), var1, 3, 0);
         SymbolMapManager_.sub_102(Status.getSystem().getName() + " " + GameStatus.langManager.getLangString(41), var1, 3 + SymbolMapManager_.sub_2c2(), 1);
         SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(220) + ": " + GameStatus.langManager.getLangString(225 + Status.getSystem().getSafety()), var1, 3 + 2 * SymbolMapManager_.sub_2c2(), 1);
      }

   }

   public final void sub_51c(int var1) {
      int var2 = GameStatus.screenHeight >> 1;
      int var3 = GameStatus.screenWidth >> 1;
      if (this.actionmenuConverging) {
         if (this.var_ce3 > 25) {
            this.var_ce3 -= 25;
            this.var_5f3.nextFrame();
         }

         this.var_ce3 += var1;
         this.var_5f3.setRefPixelPosition(var3, var2);
         this.var_5f3.paint(GameStatus.graphics);
      } else {
         if (this.var_c34) {
            int var4 = (this.var_5f3.getHeight() >> 1) + 5 + (this.var_60b.getHeight() >> 1);
            if (this.var_b01 == 0) {
               this.var_5f3.setRefPixelPosition(var3, var2);
               this.var_5f3.paint(GameStatus.graphics);
               var4 = (this.var_5f3.getHeight() >> 1) + 5 + (this.var_60b.getHeight() >> 1);
            } else {
               if (this.var_d45 > 0) {
                  this.var_d45 -= var1 / 3;
                  if (this.var_d45 < 0) {
                     this.var_d45 = 0;
                  }
               }

               this.var_5f3.setRefPixelPosition(var3, var2);
               this.var_5f3.paint(GameStatus.graphics);
            }

            if (this.var_cee > var4) {
               this.var_cee -= var1;
               if (this.var_cee < var4) {
                  this.var_cee = var4;
               }
            }

            Item[] var7 = null;
            if (this.var_b01 == 1) {
               var7 = Status.getShip().getEquipment(1);
            } else {
               var7 = this.specialEquipment;
            }

            for(var4 = 0; var4 < 4; ++var4) {
               this.var_60b.setFrame(this.actionmenuButtonsState[this.var_b01][var4]);
               if (var4 == this.actionmenuSelectDir - 1) {
                  this.var_60b.setFrame(2);
               }

               int var5 = var3 + (var4 == 1 ? this.var_cee : (var4 == 3 ? -this.var_cee : 0));
               int var6 = var2 + (var4 == 0 ? -this.var_cee : (var4 == 2 ? this.var_cee : 0));
               this.var_60b.setRefPixelPosition(var5, var6);
               this.var_60b.paint(GameStatus.graphics);
               if (this.var_b01 == 0) {
                  if (this.actionmenuButtonsState[this.var_b01][var4] > 0) {
                     this.var_60b.setFrame(var4 + 3);
                     this.var_60b.paint(GameStatus.graphics);
                  }
               } else if (this.var_b01 == 1) {
                  if (var4 < var7.length && var7[var4] != null && var7[var4].getAmount() > 0) {
                     Face_.sub_158(var7[var4].getIndex(), this.var_831, var5, var6, 3);
                     SymbolMapManager_.sub_161("x" + var7[var4].getAmount(), var5, var6 + (Face_.var_268 >> 1), 1, 24);
                  }
               } else if (this.var_b01 == 3) {
                  this.var_60b.setFrame(var4 + 7);
                  this.var_60b.paint(GameStatus.graphics);
               }
            }

            if (this.actionmenuSelectDir > 0) {
               SymbolMapManager_.sub_161(this.actionmenuLabels[this.var_b01][this.actionmenuSelectDir - 1], var3, 10, 0, 24);
            }

            if (Status.wingmenTimeRemaining > 0) {
               SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(152) + ": " + Time.timeToHMS((long)Status.wingmenTimeRemaining), var3, GameStatus.screenHeight - 30, 0, 24);
            }
         }

      }
   }

   public final boolean sub_527(int var1, Level var2, MGameContext var3) {
      if (var1 == 8192) {
         if (this.actionmenuOpen && this.var_c34) {
            this.actionmenuOpen = false;
            this.actionmenuConverging = false;
            return this.actionmenuOpen;
         }

         if (!this.actionmenuConverging) {
            this.var_c84 = false;
            this.var_ce3 = 0;
            this.actionmenuSelectDir = 0;
            this.var_cee = AEMath.max(GameStatus.screenWidth, GameStatus.screenHeight) + this.var_60b.getHeight();
            this.var_5f3.setFrame(0);
            this.var_c34 = false;
            this.actionmenuConverging = true;
            this.var_ce3 = 0;
            this.actionmenuOpen = true;
            GameStatus.soundManager.playSfx(4);
            return this.actionmenuOpen;
         }
      }

      if (this.actionmenuConverging && this.var_5f3.getFrame() >= this.var_5f3.getRawFrameCount() - 1) {
         this.actionmenuConverging = false;
         this.var_c34 = true;
         this.var_b01 = 0;
         return this.actionmenuOpen;
      } else {
         int var4 = this.var_b01;
         if (this.actionmenuOpen && this.var_c34) {
            int var5 = this.actionmenuSelectDir;
            if (this.actionmenuSelectDir > 0 && var1 == 256) {
               var1 = this.actionmenuSelectDir == 1 ? 2 : (this.actionmenuSelectDir == 2 ? 32 : (this.actionmenuSelectDir == 3 ? 64 : 4));
            }

            if (this.actionmenuSelectDir == 0 || this.actionmenuSelectDir == 1 && var1 != 2 || this.actionmenuSelectDir == 2 && var1 != 32 || this.actionmenuSelectDir == 3 && var1 != 64 || this.actionmenuSelectDir == 4 && var1 != 4) {
               switch(var1) {
               case 2:
                  this.actionmenuSelectDir = 1;
                  GameStatus.soundManager.playSfx(4);
                  break;
               case 4:
                  this.actionmenuSelectDir = 4;
                  GameStatus.soundManager.playSfx(4);
                  break;
               case 32:
                  this.actionmenuSelectDir = 2;
                  GameStatus.soundManager.playSfx(4);
                  break;
               case 64:
                  this.actionmenuSelectDir = 3;
                  GameStatus.soundManager.playSfx(4);
                  break;
               case 256:
                  if (this.actionmenuSelectDir == 0) {
                     this.sub_527(8192, var2, var3);
                  }
               }

               if (this.actionmenuSelectDir == 0 && this.actionmenuSelectDir == 3 && !var2.getPlayerEgo().sub_4ed() && !var2.getPlayerEgo().sub_4d2() || this.actionmenuSelectDir > 0 && this.actionmenuLabels[this.var_b01][this.actionmenuSelectDir - 1].equals("")) {
                  this.actionmenuSelectDir = var5;
               }

               return this.actionmenuOpen;
            }

            switch(this.var_b01) {
            case 1:
               if (var1 != 2 && var1 != 32 && var1 != 64 && var1 != 4 && var1 != 256) {
                  this.actionmenuOpen = true;
                  break;
               }

               var5 = var1 == 2 ? 0 : (var1 == 32 ? 1 : (var1 == 64 ? 2 : (var1 == 4 ? 3 : this.actionmenuSelectDir)));
               if (!this.actionmenuLabels[1][var5].equals("")) {
                  var1 = Status.getShip().getEquipment(1)[var5].getIndex();
                  var2.getPlayerEgo().sub_863(Status.getShip().getEquipment(1)[var5].getIndex());
                  GameStatus.var_9c3 = var1;
                  drawSecondaryIcon = this.actionmenuSelectDir;
               }

               this.actionmenuOpen = false;
               break;
            case 3:
               KIPlayer[] var6;
               if ((var6 = var2.getShips()) == null) {
                  break;
               }

               var_2be = var1 == 2 ? 2 : (var1 == 32 ? 4 : (var1 == 64 ? 3 : (var1 == 4 ? 1 : 0)));
               this.sub_30d(var_2be == 2 ? 16 : (var_2be == 4 ? 17 : (var_2be == 1 ? 19 : 18)), var2.getPlayerEgo());
               this.actionmenuLabels[3][3] = this.actionmenuLabels[3][3].equals(GameStatus.langManager.getLangString(151)) ? GameStatus.langManager.getLangString(150) : GameStatus.langManager.getLangString(151);

               for(var1 = 0; var1 < var6.length; ++var1) {
                  if (var6[var1].sub_32a() && !var6[var1].isOutOfScope()) {
                     if (var_2be == 0) {
                        this.actionmenuOpen = true;
                     } else {
                        var6[var1].sub_16f(var_2be, var_2be == 4 ? var3.sub_1fa() : null);
                     }
                  }
               }

               this.actionmenuOpen = false;
               break;
            default:
               switch(var1) {
               case 2:
                  if (!this.actionmenuLabels[0][0].equals("")) {
                     this.sub_192();
                     this.var_b01 = 1;
                     this.actionmenuOpen = true;
                     GameStatus.soundManager.playSfx(4);
                  }
                  break;
               case 4:
                  if (!Status.getMission().isEmpty() && Status.getMission().getType() != 11 && Status.getMission().getType() != 0 && Status.getMission().getType() != 23) {
                     this.sub_30d(21, var2.getPlayerEgo());
                     GameStatus.soundManager.playSfx(4);
                  } else if (!this.actionmenuLabels[0][3].equals("")) {
                     this.var_f29 = true;
                     GameStatus.soundManager.playSfx(4);
                  }

                  this.actionmenuOpen = false;
                  break;
               case 32:
                  if (!this.actionmenuLabels[0][1].equals("")) {
                     var2.getPlayerEgo().toggleCloaking_();
                     GameStatus.soundManager.playSfx(4);
                  }

                  this.actionmenuOpen = false;
                  break;
               case 64:
                  if (!this.actionmenuLabels[0][2].equals("")) {
                     this.var_b01 = 3;
                     this.actionmenuOpen = true;
                     GameStatus.soundManager.playSfx(4);
                  }
                  break;
               case 256:
                  this.actionmenuOpen = false;
               }
            }

            if (!this.actionmenuOpen) {
               this.var_c84 = this.var_b01 != 0 || this.actionmenuSelectDir != 2 && this.actionmenuSelectDir != 4;
               this.var_d45 = (this.var_5f3.getHeight() >> 1) + 5 + (this.var_60b.getHeight() >> 1);
               this.var_ce3 = 0;
            }
         }

         if (this.var_b01 != var4) {
            this.actionmenuSelectDir = 0;
            this.var_cee = AEMath.max(GameStatus.screenWidth, GameStatus.screenHeight) + this.var_60b.getHeight();
            this.var_d45 = (this.var_5f3.getHeight() >> 1) + 5 + (this.var_60b.getHeight() >> 1);
         }

         return this.actionmenuOpen;
      }
   }

   public final boolean sub_58b() {
      return this.var_f29;
   }

   public final void sub_5df(boolean var1) {
      this.var_f29 = false;
   }
}
