package Main;

import AbyssEngine.GameStatus;
import AbyssEngine.Item;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Popup;
import AbyssEngine.Status;

public final class Hangar {
   private static int var_1c9 = 1;
   private long var_28e;
   private int var_29d;
   private boolean var_2c2;
   private ThemedWindow var_2fa;
   private ListEntry var_33e;
   private ShipPreview var_35c;
   private Popup var_3a1;
   private Popup var_3ab;
   private boolean var_3fc;
   private int var_411;
   private int[] var_43e;
   private int var_492;
   private int var_49d;
   private int var_4f1;
   private int var_54a;

   public final void sub_e() {
      this.var_43e = null;
      if (this.var_2fa != null) {
         this.var_2fa.sub_82();
      }

      this.var_2fa = null;
      this.var_33e = null;
      this.var_35c = null;
      this.var_3a1 = null;
      this.var_3ab = null;
      this.var_2c2 = false;
      System.gc();
   }

   public final void sub_3f() {
      this.var_2fa = new ThemedWindow(new String[]{GameStatus.langManager.getLangString(77), GameStatus.langManager.getLangString(79), GameStatus.langManager.getLangString(130), "", ""}, GameStatus.langManager.getLangString(62));
      this.var_2fa.sub_9e(Status.getShip());
      Status.calcCargoPrices();
      Item[] var1 = Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems());
      this.var_2fa.buildCategorizedItemList_(var1);
      this.var_2fa.sub_d4(Status.getBluePrints());
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            Item var3;
            int var4 = (var3 = var1[var2]).getPrice();
            int var5 = var3.getIndex();
            if (var4 > Status.highestItemPrices[var5] || Status.highestItemPrices[var5] == 0) {
               Status.highestItemPrices[var5] = var4;
               Status.highestItemPriceSystems[var5] = (byte)Status.getSystem().getId();
            }

            if (var4 < Status.lowestItemPrices[var5] || Status.lowestItemPrices[var5] == 0) {
               Status.lowestItemPrices[var5] = var4;
               Status.lowestItemPriceSystems[var5] = (byte)Status.getSystem().getId();
            }
         }
      }

      Status.getShip().adjustPrice();
      this.var_35c = new ShipPreview();
      this.var_3a1 = new Popup(20, GameStatus.screenHeight / 3, GameStatus.screenWidth - 40);
      this.var_492 = 0;
      this.var_3ab = new Popup(10, GameStatus.screenHeight / 3, GameStatus.screenWidth - 20);
      this.var_411 = GameStatus.screenWidth - 10;
      this.var_43e = new int[3];
      this.var_43e[0] = this.var_411 / 3 - 2;
      this.var_43e[1] = this.var_43e[0];
      this.var_43e[2] = this.var_411 - this.var_43e[0] - this.var_43e[1] - 4;
      this.var_2fa.sub_205(var_1c9);
      System.gc();
      this.var_2c2 = true;
   }

   public final int sub_65() {
      return this.var_2fa.sub_1e5();
   }

   public final boolean sub_be() {
      return this.var_492 == 0 && !this.var_2fa.sub_1d5();
   }

   public final ListEntry sub_107() {
      return this.var_33e;
   }

   public final boolean sub_16b(int var1) {
      this.var_33e = (ListEntry)((ListEntry)this.var_2fa.sub_1a3());
      if (var1 == 256) {
         if (this.var_3fc && this.var_28e > 200L) {
            this.var_3fc = false;
            return true;
         }

         if (this.var_492 == 4) {
            this.var_492 = 0;
         } else if (this.var_492 == 0) {
            if (this.var_2fa.sub_1e5() == 2) {
               this.var_54a = this.var_2fa.sub_324();
            }

            this.var_2fa.sub_326();
         } else if (this.var_492 != 1) {
            if (this.var_492 == 2) {
               this.var_492 = 0;
            } else if (this.var_492 == 3) {
               this.var_492 = 0;
               this.var_2fa.sub_9e(Status.getShip());
            }
         }
      }

      if (this.var_3fc) {
         return true;
      } else {
         if (var1 == 2 && this.var_492 == 0) {
            this.var_2fa.sub_b4();
         }

         if (var1 == 64 && this.var_492 == 0) {
            this.var_2fa.sub_6b();
         }

         if (var1 == 4 && (this.var_492 == 4 || this.var_492 == 0)) {
            this.var_2fa.sub_2eb();
         }

         if (var1 == 32 && (this.var_492 == 4 || this.var_492 == 0)) {
            this.var_2fa.sub_2f6();
         }

         if (var1 == 8192) {
            if (this.var_492 == 4) {
               var_1c9 = this.var_2fa.sub_1e5();
               return false;
            }

            if (this.var_492 == 0 && !this.var_2fa.var_5eb) {
               if (this.var_2fa.sub_228()) {
                  this.var_2fa.sub_27b(false);
                  return true;
               }

               if (this.var_2fa.sub_1e5() == 3) {
                  this.var_2fa.sub_205(0);
                  return true;
               }

               if (this.var_2fa.sub_1e5() != 4) {
                  if (this.var_2fa.sub_1d5()) {
                     return true;
                  }

                  this.var_35c.sub_1b9(false);
                  return false;
               }

               if (!this.var_2fa.sub_1d5()) {
                  this.var_2fa.sub_205(2);

                  for(var1 = 0; var1 < this.var_54a - 1; ++var1) {
                     this.var_2fa.sub_6b();
                  }

                  this.var_2fa.sub_6c();
               }

               return true;
            }

            if (this.var_492 == 1) {
               this.var_492 = 0;
            } else if (this.var_492 == 5) {
               this.var_492 = 4;
            }
         }

         if (var1 == 16384) {
            if (this.var_492 == 4) {
               this.var_492 = 5;
            } else if (this.var_492 == 0 && this.var_33e != null) {
               if (this.var_33e.isItem() || this.var_33e.isShip() || this.var_33e.isBluePrint() || this.var_33e.sub_3e2()) {
                  this.var_35c.sub_5c((ListEntry)((ListEntry)this.var_2fa.sub_1a3()), this.var_2fa.var_21b, this.var_2fa.var_1b7, this.var_2fa.var_2b9, this.var_2fa.var_32b, true);
                  this.var_492 = 1;
               }

               if (this.var_33e != null) {
                  if (this.var_33e.sub_272()) {
                     Popup var10000 = this.var_3ab;
                     String var2 = GameStatus.langManager.getLangString(82);
                     var10000.sub_8f(var2, false);
                     this.var_3fc = true;
                  } else {
                     this.var_35c.sub_1b9(this.var_33e.isShip());
                  }
               }
            }
         }

         return true;
      }
   }

   public final boolean sub_1a0(int var1, int var2) {
      if (!this.var_2c2) {
         return true;
      } else {
         this.var_29d = var2;
         this.var_28e += (long)var2;
         if (this.var_492 == 1 && this.var_35c.isShipDrawn()) {
            this.var_35c.updateRotation(var1, var2);
         }

         if (this.var_492 == 0) {
            this.var_2fa.sub_6c();
            this.var_2fa.sub_9e();
         }

         if (this.var_492 == 1) {
            if (this.var_35c.isShipDrawn()) {
               this.var_35c.sub_219();
            }

            this.var_35c.sub_17d();
         } else if (this.var_492 == 2) {
            this.var_2fa.sub_6c();
            this.var_2fa.sub_9e();
            this.var_3a1.sub_19a();
         }

         label154: {
            String var10000;
            String var10001;
            if (!this.var_3fc && this.var_492 != 2) {
               label155: {
                  if (this.var_492 == 1 && this.var_2fa.sub_1e5() == 0 && !this.var_33e.isShip()) {
                     var10000 = "";
                  } else {
                     if (this.var_492 == 0 && (this.var_2fa.sub_1a3() == null || !((ListEntry)this.var_2fa.sub_1a3()).isSelectable)) {
                        Layout.sub_3c6("", this.var_492 == 0 && this.var_2fa.sub_228() && !this.var_2fa.var_5eb ? GameStatus.langManager.getLangString(246) : GameStatus.langManager.getLangString(65));
                        break label154;
                     }

                     var10000 = this.var_492 == 1 ? "" : (this.var_2fa.sub_1e5() == 3 && !((ListEntry)this.var_2fa.sub_1a3()).isItem() && !((ListEntry)this.var_2fa.sub_1a3()).isShip() ? "" : GameStatus.langManager.getLangString(212));
                     if (this.var_492 == 0 && this.var_2fa.sub_228() && !this.var_2fa.var_5eb) {
                        var10001 = GameStatus.langManager.getLangString(246);
                        break label155;
                     }
                  }

                  var10001 = GameStatus.langManager.getLangString(65);
               }
            } else {
               var10000 = "";
               var10001 = "";
            }

            Layout.sub_3c6(var10000, var10001);
         }

         if (this.var_3fc) {
            this.var_3ab.sub_19a();
         }

         this.var_33e = (ListEntry)((ListEntry)this.var_2fa.sub_1a3());
         if (this.var_3fc) {
            return true;
         } else {
            if (this.var_492 == 0 && this.var_2fa.sub_1d5()) {
               if ((var1 & 4) == 0) {
                  this.var_49d = 0;
               } else {
                  this.var_49d += var2;
               }

               if ((var1 & 32) == 0) {
                  this.var_4f1 = 0;
               } else {
                  this.var_4f1 += var2;
               }

               if (this.var_49d > 500) {
                  this.var_2fa.sub_29e();
                  this.var_49d = 0;
               }

               if (this.var_4f1 > 500) {
                  this.var_2fa.sub_2c7();
                  this.var_4f1 = 0;
               }
            }

            if ((var1 & 2) != 0 && this.var_492 == 1) {
               this.var_35c.sub_ae(var2);
            }

            if ((var1 & 64) != 0 && this.var_492 == 1) {
               this.var_35c.sub_ef(var2);
            }

            return true;
         }
      }
   }
}
