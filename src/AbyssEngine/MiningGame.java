package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class MiningGame {
   private int nextLevelTreshold;
   private int levels;
   private int curLevel;
   private int levelProgress;
   private int nextMinedProgress;
   private int nextMinedThreshold;
   private float cursorAcceleration;
   private float driftSpeed;
   private float driftSuppress;
   private float pressCumulativeAcceleration;
   private float cursorPos;
   private float failProgress;
   private float miningProgress;
   private float minedTons;
   private int minedIndicatorHighlightCounter;
   private int frameTime;
   private float minigSpeed;
   private float controlability;
   private Image background;
   private Image cursor;
   private Image greenComplete;
   private Image greenEmpty;
   private Image redArea;
   private Image items;
   private Hud var_5f5;
   private int minedItemId;
   private int boardPosX;
   private int boardPosY;
   private int failedBarPosX;
   private int barsPadding;
   private boolean succeed;
   private int[] barRanges = new int[]{44, 39, 34, 29, 23, 18, 13};

   public MiningGame(int var1, int var2, Hud var3) {
      if (this.background == null) {
         String var4 = "/data/interface/mining_background.png";
         this.background = AEImage.loadImage("/data/interface/mining_background.png", true);
         var4 = "/data/interface/mining_cursor.png";
         this.cursor = AEImage.loadImage("/data/interface/mining_cursor.png", true);
         var4 = "/data/interface/mining_green_complete.png";
         this.greenComplete = AEImage.loadImage("/data/interface/mining_green_complete.png", true);
         var4 = "/data/interface/mining_green_empty.png";
         this.greenEmpty = AEImage.loadImage("/data/interface/mining_green_empty.png", true);
         var4 = "/data/interface/mining_redarea.png";
         this.redArea = AEImage.loadImage("/data/interface/mining_redarea.png", true);
      }

      this.var_5f5 = var3;
      this.items = var3.var_831;
      this.boardPosX = GameStatus.screenWidth / 2 - this.background.getWidth() / 2;
      this.boardPosY = GameStatus.screenHeight / 2 + this.background.getHeight() / 2;
      this.failedBarPosX = this.background.getWidth();
      this.barsPadding = 2;
      this.nextMinedThreshold = -1;
      this.driftSpeed = 1.0F;
      this.succeed = false;
      this.minedItemId = var2;
      this.levels = var1;
      Item var5 = Status.getShip().getEquipedOfSubType(19);
      this.nextLevelTreshold = 6000;
      if (var5 != null) {
         float var6 = (float)var5.getAttribute(30) / 100.0F;
         this.driftSuppress = 25.0F + var6 * 55.0F;
         this.controlability = 50.0F + var6 * 200.0F;
         this.minigSpeed = (float)var5.getAttribute(31) / 100.0F;
      }

      this.minedIndicatorHighlightCounter = 400;
   }

   public final boolean sub_57() {
      return this.failProgress >= 2500.0F;
   }

   public final int getMiningProgressRounded() {
      return (int)this.miningProgress;
   }

   public final boolean sub_a8() {
      return this.succeed && this.levels == 7;
   }

   public final boolean sub_b7(int var1, int var2) {
      if (this.curLevel >= this.levels) {
         return false;
      } else {
         this.frameTime = var1;
         int var3;
         int var4 = -(var3 = (2 * this.barsPadding + 3 * this.barRanges[this.curLevel]) / 2);
         this.nextMinedProgress += var1;
         if (AEMath.abs((int)this.cursorPos) > this.barRanges[this.curLevel] / 2) {
            this.failProgress += (float)var1;
            if (this.failProgress > 2500.0F) {
               this.failProgress = 2500.0F;
               this.miningProgress = 0.0F;
               return false;
            }
         } else {
            this.levelProgress += var1;
            float var5 = (var5 = 0.15F + (float)(this.curLevel + 1) / 7.0F * 2.35F) * this.minigSpeed;
            this.miningProgress += var5 / 1000.0F * (float)var1;
         }

         if (this.levelProgress > this.nextLevelTreshold) {
            this.levelProgress = 0;
            ++this.curLevel;
            this.nextLevelTreshold = (int)((float)this.nextLevelTreshold * 0.83F);
            if (this.curLevel >= this.levels) {
               this.succeed = true;
               return false;
            }

            this.nextMinedProgress = this.nextMinedThreshold + 1;
            this.cursorPos *= 0.88F;
         }

         if (this.nextMinedProgress > this.nextMinedThreshold) {
            this.nextMinedProgress = 0;
            this.nextMinedThreshold = 500 + (int)((float)GameStatus.random.nextInt(100) / 100.0F * 2000.0F);
            this.cursorAcceleration = GameStatus.random.nextInt(2) == 0 ? -1.0F : 1.0F;
            this.driftSpeed = 0.0F;
            this.pressCumulativeAcceleration /= 2.0F;
         }

         this.driftSpeed = (float)var1 / this.driftSuppress * this.cursorAcceleration;
         if ((var2 & 32) != 0) {
            this.pressCumulativeAcceleration -= (float)var1 / this.controlability;
         } else if ((var2 & 4) != 0) {
            this.pressCumulativeAcceleration += (float)var1 / this.controlability;
         } else {
            this.pressCumulativeAcceleration = 0.0F;
         }

         this.cursorPos += this.driftSpeed - this.pressCumulativeAcceleration;
         if (this.cursorPos > (float)var3) {
            this.cursorPos = (float)var3;
         } else if (this.cursorPos < (float)var4) {
            this.cursorPos = (float)var4;
         }

         return true;
      }
   }

   public final void sub_e1() {
      GameStatus.graphics.drawImage(this.background, this.boardPosX, this.boardPosY, 36);
      int var1 = this.levels * 7;
      int var2 = (this.curLevel + 1) * 7;
      GameStatus.graphics.setClip(0, this.boardPosY - var1, GameStatus.screenWidth, var1);
      GameStatus.graphics.drawImage(this.greenEmpty, this.boardPosX + 50, this.boardPosY, 36);
      if (this.curLevel > 0) {
         var1 = this.curLevel * 7;
         GameStatus.graphics.setClip(0, this.boardPosY - var1, GameStatus.screenWidth, var1);
         GameStatus.graphics.drawImage(this.greenComplete, this.boardPosX + 50, this.boardPosY, 36);
      }

      var1 = this.barRanges[this.curLevel];
      int var3 = (int)((float)this.levelProgress / (float)this.nextLevelTreshold * (float)var1);
      GameStatus.graphics.setClip((GameStatus.screenWidth >> 1) - (var3 >> 1), this.boardPosY - var2, var3, 7);
      GameStatus.graphics.drawImage(this.greenComplete, this.boardPosX + 50, this.boardPosY, 36);
      var2 = this.boardPosY - this.curLevel * 7;
      var3 = this.boardPosX + (this.failedBarPosX - 2 * this.barsPadding - var1 * 3) / 2;
      int var4 = (int)(this.failProgress / 2500.0F * (float)(var1 + 5));
      GameStatus.graphics.setClip(var3 + var1 - var4, var2 - 5, var4, 5);
      GameStatus.graphics.drawImage(this.redArea, this.boardPosX, this.boardPosY, 36);
      GameStatus.graphics.setClip(var3 + var1 * 2 + 2 * this.barsPadding, var2 - 5, var4, 5);
      GameStatus.graphics.drawImage(this.redArea, this.boardPosX, this.boardPosY, 36);
      GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
      var1 = (GameStatus.screenWidth >> 1) + (int)this.cursorPos;
      GameStatus.graphics.drawImage(this.cursor, var1, var2 + 2, 33);
      var1 = this.boardPosY - this.background.getHeight();
      if (this.levels == 7) {
         Face_.sub_158(this.minedItemId - 154 + 165, this.items, (GameStatus.screenWidth >> 1) - 1, var1 + 10, 3);
      }

      if ((int)this.miningProgress > (int)this.minedTons) {
         this.minedTons = this.miningProgress;
         this.minedIndicatorHighlightCounter = 0;
      }

      boolean var5 = this.minedIndicatorHighlightCounter < 400;
      String var6;
      SymbolMapManager_.sub_161(var6 = "    t " + GameStatus.langManager.getLangString(569 + this.minedItemId), GameStatus.screenWidth >> 1, this.boardPosY + SymbolMapManager_.sub_2c2(), 2, 24);
      if (var5) {
         this.var_5f5.sub_47c((int)this.miningProgress, (GameStatus.screenWidth >> 1) - (SymbolMapManager_.sub_25b(var6, 0) >> 1) - 16, this.boardPosY + SymbolMapManager_.sub_2c2() - 2, false);
         this.minedIndicatorHighlightCounter += this.frameTime;
      } else {
         String var10000 = (int)this.miningProgress + "";
         int var10001 = (GameStatus.screenWidth >> 1) - (SymbolMapManager_.sub_25b(var6, 0) >> 1);
         String var7 = "   ";
         SymbolMapManager_.sub_161(var10000, var10001 + SymbolMapManager_.sub_25b("   ", 0), this.boardPosY + SymbolMapManager_.sub_2c2(), 2, 18);
      }
   }
}
