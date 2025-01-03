package Main;

import AbyssEngine.Camera;
import AbyssEngine.Face_;
import AbyssEngine.FileRead;
import AbyssEngine.GameStatus;
import AbyssEngine.Group;
import AbyssEngine.IndexManager;
import AbyssEngine.Item;
import AbyssEngine.LangManager;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Ship;
import AbyssEngine.SolarSystem;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import AbyssEngine.TextBox;
import javax.microedition.lcdui.Image;

public final class ShipPreview {
   private final short[] var_82 = new short[]{327, 327, 327, 327, 327, 327, 327, 327, 328, 329, 329, 330, 331, 331, 331, 331, 331, 331, 331, 331, 331, 331, 332, 332, 333, 334, 335, 336, 337, 337, 337, 338, 338, 339, 340, 340, 341, 342, 341, 343, 343, 344, 344, 344, 345, 346, 347, 348, 348, 348, 349, 349, 349, 349, 350, 351, 351, 351, 352, 353, 0, 0, 0, 354, 354, 354, 354, 355, 356, 356, 356, 357, 357, 357, 358, 359, 360, 360, 360, 360, 361, 362, 362, 362, 362, 363, 364, 364, 364, 364, 365, 366, 366, 366, 367, 367, 368, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 371, 371, 371, 371, 371, 371, 371, 371, 371, 371, 371, 372, 372, 372, 372, 372, 372, 372, 372, 372, 372, 372};
   private final short[] var_b2 = new short[]{0, 1, 4, 5, 6, 7, 8, 35, 36};
   private final String[] var_126 = new String[]{null, null, null, null, null, null, null, null, null, null, null, "ms", "m", "km/h", "m", null, null, "ms", null, "%", "%", null, "ms", "%", "ms", "ms", "%", "ms", null, null, "%", "%", null, "ms", "ms", null, null};
   private ListEntry var_17c;
   private Image var_1a0;
   private Image var_27f;
   private Image var_2e1;
   private Image var_362;
   private TextBox var_3d7;
   private TextBox var_423;
   private boolean drawShip;
   private Camera var_48f;
   private Camera var_4c3;
   private Group var_4ee;
   private float yaw;
   private float pitch;
   private float speedPitchDown;
   private float speedPitchUp;
   private float speedYawCCW;
   private float speedYawCW;
   private String var_646;
   private String var_6d6;

   public ShipPreview() {
      this.var_3d7 = new TextBox(10, 40, GameStatus.screenWidth - 20, GameStatus.screenHeight - 40 - 16 - 20, "");
      this.var_3d7.setFont(1);
      this.var_423 = new TextBox(10, 40, GameStatus.screenWidth - 20, GameStatus.screenHeight - 40 - 16 - 20, "");
      this.var_423.setHide(true);
      this.var_423.setFont(0);
   }

   public final void sub_5c(ListEntry var1, Image var2, Image var3, Image var4, Image var5, boolean var6) {
      this.var_17c = var1;
      this.var_1a0 = var2;
      this.var_27f = var3;
      this.var_2e1 = var4;
      this.var_362 = var5;
      String var9 = "";
      String var10 = "";
      if (!var1.isItem() && !var1.isBluePrint() && !var1.sub_3e2()) {
         if (var1.isShip()) {
            var9 = var9 + GameStatus.langManager.getLangString(60);
            var10 = var10 + var1.ship.getBaseHP();
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(61);
            var10 = var10 + "\n" + var1.ship.getBaseLoad();
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(123);
            var10 = var10 + "\n" + var1.ship.sub_9ca(0);
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(124);
            var10 = var10 + "\n" + var1.ship.sub_9ca(1);
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(125);
            var10 = var10 + "\n" + var1.ship.sub_9ca(2);
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(127);
            var10 = var10 + "\n" + var1.ship.sub_9ca(3);
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(59);
            var10 = var10 + "\n" + (int)(var1.ship.getHandling() * 100.0F);
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(36);
            var10 = var10 + "\n" + Layout.formatCredits(var1.getPrice());
            this.var_3d7.sub_e0(var9);
            this.var_423.sub_e0(var10);
            this.yaw = 1900.0F;
            this.pitch = 0.0F;
            this.speedPitchDown = 0.0F;
            this.speedPitchUp = 0.0F;
            this.speedYawCCW = 0.0F;
            this.speedYawCW = 0.0F;
         }

      } else {
         Item var11 = var1.isItem() ? var1.item : (var1.isBluePrint() ? IndexManager.getItems()[var1.bluePrint.getIndex()] : IndexManager.getItems()[var1.var_6dc.index]);

         int var12;
         int var14;
         for(var12 = 0; var12 < 37; ++var12) {
            boolean var7 = false;

            int var8;
            for(var8 = 0; var8 < this.var_b2.length; ++var8) {
               if (this.var_b2[var8] == var12) {
                  var7 = true;
                  break;
               }
            }

            if (!var7 && (var8 = var11.getAttribute(var12)) != -979797979) {
               if (!var9.equals("")) {
                  var9 = var9 + "\n";
                  var10 = var10 + "\n";
               }

               var9 = var9 + GameStatus.langManager.getLangString(LangManager.itemAtributes[var12]);
               if (var12 != 29 && var12 != 28) {
                  if (var12 == 21) {
                     var10 = var10 + GameStatus.langManager.getLangString(var8 == 0 ? 39 : 38);
                  } else if (var12 == 2) {
                     var10 = var10 + GameStatus.langManager.getLangString(var8 + 98);
                  } else {
                     label141: {
                        int var10000;
                        if (var12 == 13) {
                           var10000 = var8 * 250;
                        } else {
                           if (var12 != 12) {
                              break label141;
                           }

                           var14 = (var8 = (int)((float)var8 / 3600.0F * (float)(var11.getAttribute(13) * 250))) % 100;
                           var10000 = (var8 + var14) % 100 == 0 ? var8 + var14 : var8 - var14;
                        }

                        var8 = var10000;
                     }

                     var10 = var10 + var8;
                     if (this.var_126[var12] != null) {
                        var10 = var10 + this.var_126[var12];
                     }
                  }
               } else {
                  var10 = var10 + GameStatus.langManager.getLangString(var8 == 0 ? 39 : 38);
               }
            }
         }

         if (!var1.isBluePrint() && !var1.sub_3e2() && var6) {
            var9 = var9 + "\n" + GameStatus.langManager.getLangString(36);
            var10 = var10 + "\n" + Layout.formatCredits(var11.getPrice());
         }

         if (var1.isItem()) {
            new FileRead();
            var5 = null;
            SolarSystem[] var15 = FileRead.loadSystemsBinary();
            if (Status.lowestItemPrices[var1.getIndex()] > 0) {
               if (Status.lowestItemPriceSystems[var1.getIndex()] == Status.getSystem().getId()) {
                  this.var_646 = GameStatus.langManager.getLangString(95);
               } else {
                  this.var_646 = GameStatus.langManager.getLangString(93);
               }

               this.var_646 = Status.replaceTokens(this.var_646, Status.lowestItemPrices[var1.getIndex()] + "", "#C");
               this.var_646 = Status.replaceTokens(this.var_646, var15[Status.lowestItemPriceSystems[var1.getIndex()]].getName(), "#S");
            } else {
               this.var_646 = null;
            }

            if (Status.highestItemPrices[var1.getIndex()] > 0) {
               if (Status.highestItemPriceSystems[var1.getIndex()] == Status.getSystem().getId()) {
                  this.var_6d6 = GameStatus.langManager.getLangString(96);
               } else {
                  this.var_6d6 = GameStatus.langManager.getLangString(94);
               }

               this.var_6d6 = Status.replaceTokens(this.var_6d6, Status.highestItemPrices[var1.getIndex()] + "", "#C");
               this.var_6d6 = Status.replaceTokens(this.var_6d6, var15[Status.highestItemPriceSystems[var1.getIndex()]].getName(), "#S");
            } else {
               this.var_6d6 = null;
            }

            String var16 = GameStatus.langManager.getLangString(this.var_82[var1.getIndex()]);
            var9 = var9 + "\n\n" + var16;
            if (this.var_646 != null) {
               var9 = var9 + "\n\n" + this.var_646;
            }

            if (this.var_6d6 != null) {
               var9 = var9 + "\n\n" + this.var_6d6;
            }
         } else if (var1.isBluePrint() || var1.sub_3e2()) {
            String var13 = GameStatus.langManager.getLangString(this.var_82[var1.getIndex()]);
            var9 = var9 + "\n\n" + var13;
         }

         this.var_3d7.sub_e0(var9);
         this.var_423.sub_e0(var10);
         var12 = this.var_3d7.sub_164() - this.var_423.sub_164();

         for(var14 = 0; var14 < var12; ++var14) {
            var10 = var10 + "\n";
         }

         this.var_423.sub_e0(var10);
      }
   }

   public final void sub_ae(int var1) {
      this.var_3d7.scrollDown(var1);
      this.var_423.scrollDown(var1);
   }

   public final void sub_ef(int var1) {
      this.var_3d7.scrollUp(var1);
      this.var_423.scrollUp(var1);
   }

   public final boolean isShipDrawn() {
      return this.drawShip;
   }

   public final void sub_17d() {
      if (!this.drawShip) {
         Layout.screenFillMenuBackground();
         Layout.sub_2c1(GameStatus.langManager.getLangString(212));
      }

      if (!this.var_17c.isItem() && !this.var_17c.isBluePrint() && !this.var_17c.sub_3e2()) {
         if (this.var_17c.isShip()) {
            Face_.sub_1b6(this.var_17c.ship.getIndex(), this.var_17c.ship.getRace(), this.var_2e1, this.var_362, 5, 27, 6);
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(532 + this.var_17c.ship.getIndex()), 5 + Face_.faceWidth + 5, 21, 1);
         }
      } else {
         Item var1;
         Face_.sub_128((var1 = this.var_17c.isItem() ? this.var_17c.item : (this.var_17c.isBluePrint() ? IndexManager.getItems()[this.var_17c.bluePrint.getIndex()] : IndexManager.getItems()[this.var_17c.var_6dc.index])).getIndex(), var1.getType(), this.var_1a0, this.var_27f, 5, 27, 6);
         SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(569 + var1.getIndex()), 5 + Face_.var_c6 + 5, 18, 1);
      }

      this.var_3d7.draw();
      this.var_423.draw();
   }

   public final void updateRotation(int var1, int var2) {
      if (this.drawShip) {
         if ((var1 & 4) != 0) {
            this.speedYawCCW = (float)var2;
         } else {
            this.speedYawCCW *= 0.9F;
         }

         if ((var1 & 32) != 0) {
            this.speedYawCW = (float)var2;
         } else {
            this.speedYawCW *= 0.9F;
         }

         if ((var1 & 64) != 0) {
            this.speedPitchDown = (float)var2;
         } else {
            this.speedPitchDown *= 0.9F;
         }

         if ((var1 & 2) != 0) {
            this.speedPitchUp = (float)var2;
         } else {
            this.speedPitchUp *= 0.9F;
         }

         this.pitch += this.speedPitchDown - this.speedPitchUp;
         if (this.pitch > 768.0F) {
            this.pitch = 768.0F;
         } else if (this.pitch < -256.0F) {
            this.pitch = -256.0F;
         }

         this.yaw += this.speedYawCCW - this.speedYawCW;
      }

   }

   public final void sub_1b9(boolean var1) {
      this.drawShip = var1;
      if (var1) {
         this.var_4c3 = GameStatus.var_8ce.getCamera();
         this.var_48f = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 1000, 10, 31768);
         this.var_48f.sub_18f(0, 400, -Ship.previewZoomOut[this.var_17c.getIndex()]);
         this.var_48f.sub_5c5(256, 2048, 0);
         new Group();
         Group var2 = null;
         (var2 = IndexManager.buildShip_(this.var_17c.getIndex(), this.var_17c.ship.getRace())).sub_18f(0, 0, Ship.previewPivotShift[this.var_17c.getIndex()]);
         this.var_4ee = new Group();
         this.var_4ee.sub_25(var2);
      } else {
         if (this.var_4c3 != null) {
            GameStatus.var_8ce.sub_19(this.var_4c3);
         }

      }
   }

   public final void sub_219() {
      this.var_4ee.setRotation((int)this.pitch, (int)this.yaw, 0);
      Layout.screenFillMenuBackground();

      try {
         GameStatus.var_8ce.sub_19(this.var_48f);
         GameStatus.graphics3D.bindTarget(GameStatus.graphics);
         GameStatus.var_8ce.sub_87(this.var_4ee);
         GameStatus.var_8ce.sub_cc(System.currentTimeMillis());
         GameStatus.graphics3D.clear();
         GameStatus.graphics3D.releaseTarget();
      } catch (Exception var2) {
         GameStatus.graphics3D.releaseTarget();
         var2.printStackTrace();
      }

      Layout.sub_2db(GameStatus.langManager.getLangString(212), false);
   }
}
