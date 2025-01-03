package Main;

import AbyssEngine.AEImage;
import AbyssEngine.BluePrint;
import AbyssEngine.Face_;
import AbyssEngine.GameStatus;
import AbyssEngine.IndexManager;
import AbyssEngine.Item;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Popup;
import AbyssEngine.ProducedGood;
import AbyssEngine.Ship;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;

public class ThemedWindow extends TabbedWindow {
   public Image var_1b7;
   public Image var_21b;
   public Image var_2b9;
   public Image var_32b;
   protected String var_3bf;
   private Image var_451;
   private Image var_559;
   private boolean var_5da;
   public boolean var_5eb;
   private Popup var_5fb;
   private int[] var_634;
   private int var_6b2;
   private Item[] var_6bf;
   private BluePrint var_6d4;
   private int var_6e8;
   private int var_732;
   private boolean var_794;
   private boolean var_809;
   private int var_861;
   private int var_8a2;
   private int var_905;
   private int var_968;
   private int var_990;
   private int var_99d;

   public ThemedWindow(String[] var1, String var2) {
      this(0, 0, GameStatus.screenWidth, GameStatus.screenHeight, var1, var2);
   }

   private ThemedWindow(int var1, int var2, int var3, int var4, String[] var5, String var6) {
      super(0, 0, var3, var4, var5, var6);
      this.var_5da = false;
      this.var_5fb = new Popup();
      if (this.var_451 == null) {
         this.var_451 = AEImage.loadImage("/data/interface/logo_" + Status.getSystem().getRace() + "_relief.png", true);
      }

   }

   public final void sub_82() {
      this.var_21b = null;
      this.var_1b7 = null;
      this.var_2b9 = null;
      this.var_6bf = null;
   }

   public final void sub_9e(Ship var1) {
      String var2 = null;
      if (this.var_21b == null) {
         try {
            var2 = "/data/interface/items.png";
            this.var_21b = AEImage.loadImage("/data/interface/items.png", true);
            var2 = "/data/interface/item_types.png";
            this.var_1b7 = AEImage.loadImage("/data/interface/item_types.png", true);
            var2 = "/data/interface/item_types_sel.png";
            this.var_559 = AEImage.loadImage("/data/interface/item_types_sel.png", true);
            var2 = "/data/interface/ships.png";
            this.var_2b9 = AEImage.loadImage("/data/interface/ships.png", true);
            var2 = "/data/interface/ships_color.png";
            this.var_32b = AEImage.loadImage("/data/interface/ships_color.png", true);
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

      ListEntry[] var9 = new ListEntry[var1.getEquipment().length + 2 + var1.sub_9e3()];
      byte var3 = 0;
      int var8 = var3 + 1;
      var9[0] = new ListEntry(GameStatus.langManager.getLangString(77));
      ++var8;
      var9[1] = new ListEntry(var1);

      for(int var4 = 0; var4 < 4; ++var4) {
         if (var1.sub_9ca(var4) > 0) {
            var9[var8++] = new ListEntry(GameStatus.langManager.getLangString(var4 == 0 ? 123 : (var4 == 1 ? 124 : (var4 == 2 ? 125 : 127))), var4);
            Item[] var5 = var1.getEquipment(var4);

            for(int var6 = 0; var6 < var5.length; var9[var8 - 1].var_6b0 = var6++) {
               if (var5[var6] != null) {
                  var9[var8++] = new ListEntry(var5[var6]);
               } else {
                  var9[var8++] = new ListEntry(var4);
               }
            }
         }
      }

      this.perTabEntries[0] = var9;
      if (this.perTabEntries[0] == null) {
         this.var_353[0] = 0;
      } else if (this.perTabEntries[0].length == 0) {
         this.perTabEntries[0] = null;
         this.var_353[0] = 0;
      } else {
         this.var_353[0] = this.perTabEntries[0].length;
      }

      this.var_664 = this.perTabEntries[0].length;
      this.var_353[0] = this.var_664;
      this.sub_238();
   }

   public final void sub_d4(BluePrint[] var1) {
      if (var1 != null) {
         int var2 = 1;

         int var3;
         for(var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3].unlocked) {
               ++var2;
            }
         }

         var3 = var2;
         boolean var4 = true;
         ProducedGood[] var5;
         if ((var5 = Status.getWaitingGoods()) != null) {
            for(int var6 = 0; var6 < var5.length; ++var6) {
               if (var5[var6] != null) {
                  ++var2;
               }
            }

            if (var2 > var3) {
               ++var2;
               var4 = false;
            }
         }

         ListEntry[] var8;
         (var8 = new ListEntry[var2])[0] = new ListEntry(GameStatus.langManager.getLangString(131));
         var2 = 1;

         for(var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3].unlocked) {
               var8[var2++] = new ListEntry(var1[var3]);
            }
         }

         if (!var4) {
            var8[var2++] = new ListEntry(GameStatus.langManager.getLangString(132));

            for(var3 = 0; var3 < var5.length; ++var3) {
               if (var5[var3] != null) {
                  var8[var2++] = new ListEntry(var5[var3]);
               }
            }
         }

         super.sub_16a(2, var8);
         this.var_664 = this.perTabEntries[2].length;
         this.var_353[2] = this.var_664;
         this.sub_238();
      }
   }

   public final void buildCategorizedItemList_(Item[] var1) {
      this.var_6bf = var1;
      if (var1 != null) {
         this.var_634 = new int[5];

         int var2;
         for(var2 = 0; var2 < var1.length; ++var2) {
            int var10002 = this.var_634[var1[var2].getType()]++;
         }

         var2 = 0;

         int var3;
         for(var3 = 0; var3 < this.var_634.length; ++var3) {
            if (this.var_634[var3] > 0) {
               ++var2;
            }
         }

         Ship[] var6;
         if ((var6 = Status.getStation().getShopShips()) != null) {
            var2 += 1 + var6.length;
         }

         ListEntry[] var7 = new ListEntry[var1.length + var2];
         int var4 = 0;
         int var5;
         if (var6 != null) {
            ++var4;
            var7[0] = new ListEntry(GameStatus.langManager.getLangString(68), -1);

            for(var5 = 0; var5 < var6.length; ++var5) {
               var7[var4++] = new ListEntry(var6[var5]);
            }
         }

         for(var5 = 0; var5 < 5; ++var5) {
            if (this.var_634[var5] > 0) {
               var7[var4++] = new ListEntry(GameStatus.langManager.getLangString(var5 == 0 ? 123 : (var5 == 1 ? 124 : (var5 == 2 ? 125 : (var5 == 3 ? 127 : 128)))), var5);

               for(var3 = 0; var3 < var1.length; ++var3) {
                  if (var1[var3].getType() == var5) {
                     var7[var4++] = new ListEntry(var1[var3]);
                  }
               }
            }
         }

         super.sub_16a(1, var7);
         this.var_6b2 = Status.getShip().getCurrentLoad();
         this.var_664 = this.perTabEntries[1].length;
         this.var_353[1] = this.var_664;
         this.sub_238();
      }
   }

   private void sub_16d(ListEntry var1) {
      this.perTabEntries[3] = null;
      this.var_353[3] = 0;
      boolean var2 = var1.isShip();
      int var3 = var1.medalTier;
      if (!var2 && !var1.sub_272()) {
         var3 = var1.item.getType();
      }

      int var4 = 0;
      int var6;
      if (var2) {
         Ship[] var5;
         if ((var5 = Status.getStation().getShopShips()) != null) {
            var4 = var5.length;
         }
      } else {
         Item[] var10;
         if ((var10 = Status.getShip().getCargo()) != null) {
            for(var6 = 0; var6 < var10.length; ++var6) {
               if (var10[var6].getType() == var3) {
                  ++var4;
               }
            }
         }
      }

      int var11 = !var2 && !var1.sub_272() ? 2 : 0;
      var6 = 2 + (var4 == 0 ? 2 : 1) + (var11 == 0 ? 0 : 1);
      ListEntry[] var12;
      (var12 = new ListEntry[var4 + var6 + var11])[0] = new ListEntry(GameStatus.langManager.getLangString(135));
      var12[1] = new ListEntry(var1);
      var6 = 2;
      if (!var2 && !var1.sub_272()) {
         ++var6;
         var12[2] = new ListEntry(GameStatus.langManager.getLangString(136));
         ++var6;
         var12[3] = new ListEntry(GameStatus.langManager.getLangString(137), true, 0);
         ++var6;
         var12[4] = new ListEntry(GameStatus.langManager.getLangString(138), true, 1);
      }

      var12[var6++] = new ListEntry(var2 ? GameStatus.langManager.getLangString(140) : GameStatus.langManager.getLangString(139));
      if (var4 > 0) {
         int var9;
         if (var2) {
            Ship[] var7 = Status.getStation().getShopShips();

            for(var9 = 0; var9 < var7.length; ++var9) {
               var12[var6++] = new ListEntry(var7[var9]);
            }
         } else {
            Item[] var8 = Status.getShip().getCargo();

            for(var9 = 0; var9 < var8.length; ++var9) {
               if (var8[var9].getType() == var3) {
                  var12[var6++] = new ListEntry(var8[var9]);
               }
            }
         }
      } else {
         var12[var6] = new ListEntry(GameStatus.langManager.getLangString(141), false, -1);
      }

      super.sub_16a(3, var12);
      this.var_664 = this.perTabEntries[3].length;
      this.var_353[3] = this.var_664;
      this.sub_238();
   }

   private void sub_198(BluePrint var1) {
      this.var_6d4 = var1;
      this.perTabEntries[4] = null;
      this.var_353[4] = 0;
      boolean var2 = false;
      Item[] var3 = Status.getShip().getCargo();
      Item[] var4 = IndexManager.getItems();
      int[] var9;
      ListEntry[] var10 = new ListEntry[(var9 = var1.getIngredientList()).length + 1];
      int var5 = 0;

      for(int var6 = 0; var6 < var9.length; ++var6) {
         boolean var7 = false;
         if (var3 != null) {
            for(int var8 = 0; var8 < var3.length; ++var8) {
               if (var3[var8].getIndex() == var9[var6]) {
                  var10[var5++] = new ListEntry(var3[var8]);
                  var3[var8].sub_54b(0);
                  var7 = true;
                  break;
               }
            }
         }

         if (!var7) {
            var10[var5++] = new ListEntry(var4[var9[var6]]);
            var4[var9[var6]].sub_54b(0);
         }
      }

      super.sub_16a(4, var10);
      this.var_664 = this.perTabEntries[4].length;
      this.var_353[4] = this.var_664;
      this.sub_238();
   }

   public final boolean sub_1d5() {
      return this.var_5da || this.var_5eb;
   }

   public final boolean sub_228() {
      return this.var_5da;
   }

   public final void sub_27b(boolean var1) {
      if (this.var_5da) {
         Item var2;
         (var2 = ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).setStationAmount(this.var_8a2);
         var2.sub_460(this.var_905);
         var2.sub_54b(this.var_968);
         Status.setCredits(this.var_990);
         this.var_6b2 = this.var_99d;
      }

      this.var_8a2 = 0;
      this.var_905 = 0;
      this.var_968 = 0;
      this.var_5da = false;
   }

   public final void sub_2eb() {
      if (this.var_5da && !this.var_5eb) {
         if (this.selectedTab == 1) {
            int var1;
            if ((var1 = ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.sub_6b2(false)) > 0) {
               --this.var_6b2;
            }

            Status.changeCredits(var1);
         } else if (this.selectedTab == 4) {
            int var2;
            Item var4;
            if ((var2 = (var4 = ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).sub_714(true)) > 0) {
               --this.var_6b2;
            } else if (var2 != 0) {
               --this.var_861;
            }

            Item[] var5;
            if ((var5 = Status.getShip().getCargo()) != null) {
               for(int var3 = 0; var3 < var5.length; ++var3) {
                  if (var5[var3].getIndex() == var4.getIndex()) {
                     var5[var3].sub_460(var4.getAmount());
                     var5[var3].sub_54b(var4.sub_590());
                  }
               }
            }
         }

         this.sub_238();
      } else if (!this.var_5eb && this.selectedTab < 3) {
         super.sub_2eb();
      } else {
         if (this.var_5eb) {
            this.var_5fb.sub_c6();
         }

      }
   }

   public final void sub_2f6() {
      if (this.var_5da && !this.var_5eb) {
         if (this.selectedTab == 1) {
            int var1;
            if ((var1 = ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.sub_6b2(true)) < 0) {
               ++this.var_6b2;
            }

            Status.changeCredits(var1);
         } else if (this.selectedTab == 4) {
            Item var4;
            if ((var4 = ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item).sub_590() < this.var_6d4.getRemainingAmount(var4.getIndex())) {
               int var2;
               if ((var2 = var4.sub_714(false)) < 0) {
                  ++this.var_6b2;
               } else if (var2 != 0) {
                  ++this.var_861;
               }
            }

            Item[] var5;
            if ((var5 = Status.getShip().getCargo()) != null) {
               for(int var3 = 0; var3 < var5.length; ++var3) {
                  if (var5[var3].getIndex() == var4.getIndex()) {
                     var5[var3].sub_460(var4.getAmount());
                     var5[var3].sub_54b(var4.sub_590());
                  }
               }
            }
         }

         this.sub_238();
      } else {
         if (!this.var_5eb) {
            if (this.selectedTab < 2) {
               super.sub_2f6();
               return;
            }
         } else {
            this.var_5fb.sub_ff();
         }

      }
   }

   public final void sub_29e() {
      if (this.var_5da) {
         for(int var1 = 0; var1 < 10; ++var1) {
            this.sub_2eb();
         }
      }

   }

   public final void sub_2c7() {
      if (this.var_5da) {
         for(int var1 = 0; var1 < 10; ++var1) {
            this.sub_2f6();
         }
      }

   }

   public final void sub_b4() {
      if (!this.var_5da && !this.var_5eb) {
         boolean var1 = false;
         int var2 = this.selectedEntry;

         for(int var3 = this.selectedEntry; var3 > 0; --var3) {
            if (((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][var3 - 1])).isSelectable) {
               var1 = true;
               break;
            }

            --var2;
         }

         if (this.selectedEntry == 0 || this.selectedEntry == 1 && !((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][0])).isSelectable) {
            var2 = this.var_353[this.selectedTab] - 1;
            this.selectedEntry = this.var_353[this.selectedTab] - 1;
            this.scrollPos = this.var_353[this.selectedTab] - this.var_664 - 1;
         }

         if (var1) {
            super.sub_b4();
         }

         if (var2 > 0 && !((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry])).isSelectable) {
            this.sub_b4();
         }

         this.sub_238();
      }

   }

   public final void sub_6b() {
      if (!this.var_5da && !this.var_5eb) {
         boolean var1 = false;
         int var2 = this.selectedEntry;

         for(int var3 = this.selectedEntry; var3 < this.var_353[this.selectedTab] - 1; ++var3) {
            if (((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][var3 + 1])).isSelectable) {
               var1 = true;
               break;
            }

            ++var2;
         }

         if (this.selectedEntry == this.var_353[this.selectedTab] - 1 || this.selectedEntry == this.var_353[this.selectedTab] - 3 && !((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry + 1])).isSelectable && !((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry + 2])).isSelectable) {
            var2 = 0;
            this.selectedEntry = 0;
            this.scrollPos = 0;
         }

         if (var1) {
            super.sub_6b();
         }

         if (var2 < this.var_353[this.selectedTab] - 1 && !((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry])).isSelectable) {
            this.sub_6b();
         }

         this.sub_238();
      }

   }

   public final int sub_326() {
      ListEntry var1;
      int var3;
      int var18;
      String var22;
      if (this.var_5eb) {
         this.var_5eb = false;
         if (this.selectedTab == 1 && !this.var_809) {
            return 0;
         }

         if (this.var_5da) {
            this.var_5da = this.var_5fb.sub_9a();
         }

         if (!this.var_794) {
            if (!this.var_809) {
               return 0;
            }

            this.var_809 = false;
            this.var_5eb = false;
            if (this.var_5fb.sub_9a()) {
               var1 = (ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry];
               Status.changeCredits(Status.getShip().getPrice() - var1.ship.getPrice());
               Ship var13;
               Item[] var16 = (var13 = Status.getShip()).getEquipment();
               Item[] var21 = var13.getCargo();
               Status.setShip(IndexManager.getShips()[var1.ship.getIndex()].cloneBase());
               Status.getShip().setRace(var1.ship.getRace());
               Status.getShip().adjustPrice();
               Status.getShip().refreshCargoSpace(var21);
               if (var16 != null) {
                  for(int var19 = 0; var19 < var16.length; ++var19) {
                     if (var16[var19] != null) {
                        Status.getShip().addItem(var16[var19]);
                     }
                  }
               }

               Ship[] var23 = Status.getStation().getShopShips();

               for(var18 = 0; var18 < var23.length; ++var18) {
                  if (var23[var18].getIndex() == var1.ship.getIndex()) {
                     var23[var18] = IndexManager.getShips()[var13.getIndex()].cloneBase();
                     var23[var18].setRace(var13.getRace());
                     break;
                  }
               }

               this.sub_9e(Status.getShip());
               this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
               this.sub_205(0);
               var22 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(143)), GameStatus.langManager.getLangString(532 + var1.ship.getIndex()), "#N");
               this.var_5fb.sub_34(var22);
               this.var_5eb = true;
               ((InsideStation)GameStatus.scenes[1]).sub_4e().sub_7c(Status.getShip().getIndex(), Status.getShip().getRace());
            }

            return 0;
         }

         this.var_794 = false;
         if (this.var_5fb.sub_9a()) {
            if (!this.var_5da) {
               Status.changeCredits(-((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.sub_590() * 10);
               this.var_5da = true;
               int var2 = this.var_861;

               for(var3 = 0; var3 < var2; ++var3) {
                  this.sub_2eb();
               }

               this.var_5da = false;
               return 0;
            }
         } else {
            this.var_5da = true;
            this.sub_27b(false);
         }

         this.var_861 = 0;
         this.var_5da = true;
      }

      if (this.perTabEntries[this.selectedTab] != null && this.perTabEntries[this.selectedTab][this.selectedEntry] != null) {
         var1 = (ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry];
         if (this.selectedTab == 4 && this.var_5da && this.var_861 > 0 && !this.var_5eb && !this.var_6d4.isStarted() && this.var_6d4.getProductionStationId() != Status.getStation().getId()) {
            String var11 = Status.replaceTokens(Status.replaceTokens(new String(GameStatus.langManager.getLangString(142)), this.var_6d4.getProductionStationName(), "#S"), Layout.formatCredits(var1.item.sub_590() * 10), "#C");
            this.var_5fb.sub_8f(var11, true);
            this.var_5eb = true;
            this.var_794 = true;
            return 0;
         } else {
            if (this.selectedTab == 0) {
               this.var_6e8 = this.selectedEntry;
               this.var_732 = this.scrollPos;
               this.sub_16d(var1);
               this.sub_205(3);
               if (!GameStatus.itemMounting2HelpShown && var1.sub_272()) {
                  this.var_5fb.sub_34(GameStatus.langManager.getLangString(301));
                  GameStatus.itemMounting2HelpShown = true;
                  this.var_5eb = true;
               }
            } else {
               Item var5;
               if (this.selectedTab == 1) {
                  if (var1.isShip()) {
                     if (var1.ship.getPrice() > Status.getCredits() + Status.getShip().getPrice()) {
                        this.var_5fb.sub_34(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var1.getPrice() - Status.getCredits() - Status.getShip().getPrice()), "#C"));
                        this.var_5eb = true;
                        return 0;
                     }

                     if (Status.var_9a8 > 0) {
                        this.var_5fb.sub_34(GameStatus.langManager.getLangString(161));
                        this.var_5eb = true;
                        return 0;
                     }

                     this.var_809 = true;
                     this.var_5fb.sub_8f(GameStatus.langManager.getLangString(144), true);
                     this.var_5eb = true;
                     return 0;
                  }

                  if (var1.item.sub_95c()) {
                     this.var_5fb.sub_34(GameStatus.langManager.getLangString(160));
                     this.var_5eb = true;
                  } else if (var1.item.getAmount() == 0 && Status.getCredits() < var1.item.getPrice()) {
                     this.var_5fb.sub_34(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var1.item.getPrice() - Status.getCredits()), "#C"));
                     this.var_5eb = true;
                  } else {
                     this.var_5da = !this.var_5da;
                     if (!this.var_5da) {
                        if (var1.isItem() && var1.item.getType() != 4 && !GameStatus.itemMountingHelpShown) {
                           this.var_5fb.sub_34(GameStatus.langManager.getLangString(303));
                           GameStatus.itemMountingHelpShown = true;
                           this.var_5eb = true;
                        }

                        if (var1.getIndex() >= 132 && var1.getIndex() < 154) {
                           Status.drinksVar1[var1.getIndex() - 132] = true;
                        }

                        Item[] var9;
                        if (var1.isItem() && var1.item.getType() == 1 && var1.item.getAmount() > 0 && (var9 = Status.getShip().getEquipment(1)) != null) {
                           for(var3 = 0; var3 < var9.length; ++var3) {
                              Item var20;
                              if ((var20 = var9[var3]) != null && var20.getIndex() == var1.item.getIndex()) {
                                 var5 = var1.item.anyAmountCopies(var1.item.getAmount() + var20.getAmount());
                                 if (this.var_6bf != null) {
                                    for(var18 = 0; var18 < this.var_6bf.length; ++var18) {
                                       if (this.var_6bf[var18].getIndex() == var5.getIndex()) {
                                          this.var_6bf[var18].sub_460(0);
                                       }
                                    }
                                 }

                                 Status.getShip().setEquipment(var5, var3);
                                 Status.getShip().sub_7fc(var5.getIndex(), var5.getAmount());
                                 this.sub_9e(Status.getShip());
                                 var22 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(87)), GameStatus.langManager.getLangString(569 + var5.getIndex()), "#N");
                                 this.var_5fb.sub_34(var22);
                                 this.var_5eb = true;
                                 break;
                              }
                           }
                        }

                        Status.getShip().refreshCargoSpace(Item.sub_797(this.var_6bf, true));
                        Status.getStation().setShopItems(Item.sub_797(this.var_6bf, false));
                        this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                        this.sub_238();
                     } else {
                        if (!GameStatus.buyingHelpShown) {
                           this.var_5fb.sub_34(GameStatus.langManager.getLangString(302));
                           GameStatus.buyingHelpShown = true;
                           this.var_5eb = true;
                        }

                        this.var_8a2 = var1.item.getStationAmount();
                        this.var_905 = var1.item.getAmount();
                        this.var_990 = Status.getCredits();
                        this.var_99d = this.var_6b2;
                     }
                  }
               } else if (this.selectedTab == 2 && !var1.sub_3e2()) {
                  this.sub_198(var1.bluePrint);
                  this.sub_205(4);
               } else {
                  String var4;
                  boolean var10;
                  if (this.selectedTab == 3) {
                     if (this.selectedEntry > 2) {
                        Item var7;
                        if ((var7 = ((ListEntry)this.perTabEntries[this.selectedTab][1]).item) != null && var7.sub_95c()) {
                           this.var_5fb.sub_34(GameStatus.langManager.getLangString(160));
                           this.var_5eb = true;
                           return 0;
                        }

                        if (var7 != null && var7.getSubType() == 20 && Status.var_9a8 > 0) {
                           this.var_5fb.sub_34(GameStatus.langManager.getLangString(160));
                           this.var_5eb = true;
                           return 0;
                        }

                        if (var1.isShip()) {
                           if (var1.ship.getPrice() > Status.getCredits() + Status.getShip().getPrice()) {
                              this.var_5fb.sub_34(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var1.getPrice() - Status.getCredits() - Status.getShip().getPrice()), "#C"));
                              this.var_5eb = true;
                              return 0;
                           }

                           if (Status.var_9a8 > 0) {
                              this.var_5fb.sub_34(GameStatus.langManager.getLangString(161));
                              this.var_5eb = true;
                              return 0;
                           }

                           this.var_809 = true;
                           this.var_5fb.sub_8f(GameStatus.langManager.getLangString(144), true);
                           this.var_5eb = true;
                           return 0;
                        }

                        if (var1.sub_2f7()) {
                           var10 = var7.getType() == 1;
                           if (var1.sub_370()) {
                              var4 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(88)), GameStatus.langManager.getLangString(569 + var7.getIndex()), "#N");
                              this.var_5fb.sub_34(var4);
                              var5 = var7.anyAmountCopies(var10 ? var7.getAmount() : 1);
                              Status.getShip().addItem(var5);
                              Status.getShip().sub_9be(var7);
                              this.sub_9e(Status.getShip());
                              this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                              this.sub_205(0);
                           } else if (var1.sub_315()) {
                              var4 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(86)), GameStatus.langManager.getLangString(569 + var7.getIndex()), "#N");
                              this.var_5fb.sub_34(var4);
                              var5 = var7.anyAmountCopies(var10 ? var7.getAmount() : 1);
                              Status.getStation().sub_334(var5);
                              Status.changeCredits(var7.sub_2ed());
                              Status.getShip().sub_9be(var7);
                              this.sub_9e(Status.getShip());
                              this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                              this.sub_205(0);
                           }

                           this.var_5eb = true;
                        } else {
                           ListEntry var12 = (ListEntry)this.perTabEntries[this.selectedTab][1];
                           if (Status.getShip().getEquipedOfSubType(var1.item.getSubType()) != null && !var1.item.sub_18e() && (!var12.isItem() || var12.item.getSubType() != var1.item.getSubType())) {
                              var4 = new String(GameStatus.langManager.getLangString(164));
                              this.var_5fb.sub_34(var4);
                              this.var_5eb = true;
                              return 0;
                           }

                           Item var6;
                           boolean var14;
                           String var15;
                           if (var12.sub_272()) {
                              var14 = var1.item.getType() == 1;
                              var15 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(87)), GameStatus.langManager.getLangString(569 + var1.item.getIndex()), "#N");
                              this.var_5fb.sub_34(var15);
                              var6 = var1.item.anyAmountCopies(var14 ? var1.item.getAmount() : 1);
                              Status.getShip().setEquipment(var6, var12.var_6b0);
                              Status.getShip().sub_7fc(var6.getIndex(), var14 ? var6.getAmount() : 1);
                              this.sub_9e(Status.getShip());
                              this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                              this.sub_205(0);
                           } else {
                              var14 = var12.item.getType() == 1;
                              var15 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(87)), GameStatus.langManager.getLangString(569 + var1.item.getIndex()), "#N");
                              this.var_5fb.sub_34(var15);
                              var6 = var12.item.anyAmountCopies(var14 ? var12.item.getAmount() : 1);
                              Status.getShip().addItem(var6);
                              Status.getShip().sub_98c(var12.item, var12.var_6b0);
                              Status.getShip().setEquipment(var1.item.anyAmountCopies(var14 ? var1.item.getAmount() : 1), var12.var_6b0);
                              Status.getShip().sub_7fc(var1.item.getIndex(), var14 ? var1.item.getAmount() : 1);
                              this.sub_9e(Status.getShip());
                              this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                              this.sub_205(0);
                           }
                        }

                        this.selectedEntry = this.var_6e8;
                        this.scrollPos = this.var_732;
                     }
                  } else if (this.selectedTab == 4) {
                     if (this.var_5da) {
                        boolean var8 = false;
                        var10 = false;
                        this.var_6d4.startProduction(var1.item, var1.item.sub_590(), Status.getStation().getId());
                        if (this.var_6d4.isComplete()) {
                           if (this.var_6d4.getProductionStationId() != Status.getStation().getId()) {
                              var4 = Status.replaceTokens(Status.replaceTokens(new String(GameStatus.langManager.getLangString(89)), GameStatus.langManager.getLangString(569 + this.var_6d4.getIndex()), "#N"), this.var_6d4.getProductionStationName(), "#S");
                              this.var_5fb.sub_34(var4);
                              Status.sub_5c(this.var_6d4);
                              this.sub_205(2);
                           } else {
                              var4 = Status.replaceTokens(new String(GameStatus.langManager.getLangString(90)), GameStatus.langManager.getLangString(569 + this.var_6d4.getIndex()), "#N");
                              this.var_5fb.sub_34(var4);
                              var5 = IndexManager.getItems()[this.var_6d4.getIndex()].anyAmountCopies(this.var_6d4.getTonsPerProduction2());
                              Status.getShip().addItem(var5);
                              this.sub_205(1);
                           }

                           var10 = true;
                           this.var_6d4.finishProduction();
                           var8 = true;
                        }

                        this.var_5da = false;
                        Status.getShip().refreshCargoSpace(Item.sub_797(Status.getShip().getCargo(), true));
                        this.buildCategorizedItemList_(Item.sub_7de(Status.getShip().getCargo(), Status.getStation().getShopItems()));
                        this.sub_d4(Status.getBluePrints());
                        this.sub_238();
                        if (var8) {
                           for(int var17 = 0; var17 < this.perTabEntries[this.selectedTab].length; ++var17) {
                              this.sub_6b();
                              if (!((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).isBluePrint() && ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).getIndex() == this.var_6d4.getIndex()) {
                                 break;
                              }
                           }
                        }

                        if (var10) {
                           this.var_5eb = true;
                        }

                        return 0;
                     }

                     this.var_861 = 0;
                     if (this.var_6d4.isStarted() && ((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry]).item.getAmount() > 0) {
                        this.var_5fb.sub_8f(GameStatus.langManager.getLangString(91), true);
                        this.var_5eb = true;
                     }

                     this.var_968 = var1.item.sub_590();
                     this.var_905 = var1.item.getAmount();
                     this.var_990 = Status.getCredits();
                     this.var_99d = this.var_6b2;
                     this.var_5da = !this.var_5da;
                  }
               }
            }

            return 0;
         }
      } else {
         return 0;
      }
   }

   public final void sub_238() {
      if (this.perTabEntries.length >= 3) {
         if (this.selectedTab == 1) {
            this.var_71b = this.posY + 14 + this.var_78b + this.rowHeight + 2;
         } else {
            this.var_71b = this.posY + 14 + this.var_78b;
         }
      }

      super.sub_238();
      if (this.var_353[this.selectedTab] > 0 && this.selectedEntry == 0 && !(this.var_353[this.selectedTab] == 0 ? false : ((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][this.selectedEntry])).isSelectable)) {
         ++this.selectedEntry;
         super.sub_238();
      }

   }

   public void sub_6c() {
      this.sub_19();
      Layout.drawFilledTitleBarWindow(this.titleBarText, this.posX, this.posY, this.width, this.height);
      GameStatus.graphics.drawImage(this.var_451, GameStatus.screenWidth - 20, GameStatus.screenHeight - 30 - 16, 40);
      if (this.selectedTab > 2) {
         this.var_78b = 1;
      } else {
         this.var_78b = SymbolMapManager_.sub_2c2() + 4;
      }

      this.sub_307();
      this.sub_171();
      this.sub_312();
      if (this.var_5eb) {
         this.var_5fb.sub_19a();
      }

   }

   public void sub_9e() {
      GameStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
      GameStatus.graphics.fillRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 15, this.width - 3, this.posY + this.height - 15);
      GameStatus.graphics.drawLine(this.posX + 3, this.posY + this.height - 1, this.width - 3, this.posY + this.height - 1);
      GameStatus.graphics.drawRect(this.posX + 3, this.posY + this.height - 13, this.width - 6, 10);
      GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
      GameStatus.graphics.drawRect(this.posX + 2, this.posY + this.height - 14, this.width - 4, 12);
      boolean var1;
      if ((var1 = this.var_6b2 > Status.getShip().getCargoPlus()) && Layout.sub_119() || !var1) {
         SymbolMapManager_.sub_161(this.var_6b2 + "/" + Status.getShip().getCargoPlus() + "t", this.innerLeftMargin, this.posY + this.height - 14, var1 ? 2 : 1, 17);
      }

      SymbolMapManager_.sub_161(Layout.formatCredits(Status.getCredits()), this.posX + this.width - 3, this.posY + this.height - 13, 1, 18);
   }

   protected final void sub_307() {
      if (this.perTabEntries.length < 3) {
         super.sub_307();
      } else {
         if (this.showTabs) {
            if (this.selectedTab > 2) {
               return;
            }

            GameStatus.graphics.setColor(0);
            GameStatus.graphics.drawLine(this.posX + this.width / 3, this.posY + 14, this.posX + this.width / 3, this.posY + 14 + 14);
            GameStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 1, this.posY + 14, this.posX + this.width - this.width / 3 - 1, this.posY + 14 + 14);
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GameStatus.graphics.drawLine(this.posX + this.width / 3 - 1, this.posY + 14, this.posX + this.width / 3 - 1, this.posY + 14 + 14);
            GameStatus.graphics.drawLine(this.posX + this.width / 3 + 1, this.posY + 14, this.posX + this.width / 3 + 1, this.posY + 14 + 14);
            GameStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 2, this.posY + 14, this.posX + this.width - this.width / 3 - 2, this.posY + 14 + 14);
            GameStatus.graphics.drawLine(this.posX + this.width - this.width / 3, this.posY + 14, this.posX + this.width - this.width / 3, this.posY + 14 + 14);
            switch(this.selectedTab) {
            case 0:
               GameStatus.graphics.drawLine(this.posX + this.width / 3 - 1, this.posY + 14 + 14, this.posX + this.width - 3, this.posY + 14 + 14);
               break;
            case 1:
               GameStatus.graphics.drawLine(this.posX + 3, this.posY + 14 + 14, this.posX + this.width / 3 + 1, this.posY + 14 + 14);
               GameStatus.graphics.drawLine(this.posX + this.width - this.width / 3 - 2, this.posY + 14 + 14, this.posX + this.width - 3, this.posY + 14 + 14);
               break;
            case 2:
               GameStatus.graphics.drawLine(this.posX + 3, this.posY + 14 + 14, this.posX + this.width - this.width / 3, this.posY + 14 + 14);
            }

            if (this.selectedTab != 0) {
               GameStatus.graphics.setColor(0);
               GameStatus.graphics.fillRect(this.posX + 3, this.posY + 14 + 1, this.width / 3 - 4, this.posY + 14 - 2);
               GameStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
               GameStatus.graphics.fillRect(this.posX + 4, this.posY + 14 + 2, this.width / 3 - 6, this.posY + 14 - 3);
            }

            if (this.selectedTab != 1) {
               GameStatus.graphics.setColor(0);
               GameStatus.graphics.fillRect(this.posX + this.width / 3 + 2, this.posY + 14 + 1, this.width / 3 - 2, this.posY + 14 - 2);
               GameStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
               GameStatus.graphics.fillRect(this.posX + this.width / 3 + 3, this.posY + 14 + 2, this.width / 3 - 4, this.posY + 14 - 3);
            }

            if (this.selectedTab != 2) {
               GameStatus.graphics.setColor(0);
               GameStatus.graphics.fillRect(this.posX + this.width - this.width / 3 + 1, this.posY + 14 + 1, this.width / 3 - 3, this.posY + 14 - 2);
               GameStatus.graphics.setColor(Layout.uiInactiveInnerLabelColor);
               GameStatus.graphics.fillRect(this.posX + this.width - this.width / 3 + 2, this.posY + 14 + 2, this.width / 3 - 5, this.posY + 14 - 3);
            }

            Layout.sub_39a(this.posX + 2, this.posY + 14, this.selectedTab == 0);
            Layout.sub_39a(this.posX + this.width / 3 + 1, this.posY + 14, this.selectedTab == 1);
            Layout.sub_39a(this.posX + this.width - this.width / 3, this.posY + 14, this.selectedTab == 2);
            SymbolMapManager_.sub_161(this.tabNames[0], this.posX + this.width / 6, this.posY + 14 + 1, this.selectedTab == 0 ? 2 : 1, 24);
            SymbolMapManager_.sub_161(this.tabNames[1], this.posX + this.width / 2, this.posY + 14 + 1, this.selectedTab == 1 ? 2 : 1, 24);
            SymbolMapManager_.sub_161(this.tabNames[2], this.posX + this.width - this.width / 6, this.posY + 14 + 1, this.selectedTab == 2 ? 2 : 1, 24);
         }

      }
   }

   public final void sub_171() {
      if (this.perTabEntries[this.selectedTab] != null) {
         if (this.perTabEntries.length < 3 || this.selectedTab != 1 && this.selectedTab != 4) {
            this.var_71b = this.posY + 14 + this.var_78b;
         } else {
            String var1 = GameStatus.langManager.getLangString(this.selectedTab == 1 ? 40 : 78);
            String var2 = this.selectedTab == 1 ? GameStatus.langManager.getLangString(78) : GameStatus.langManager.getLangString(129);
            this.var_71b = this.posY + 14 + this.var_78b + this.rowHeight - 1;
            GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
            GameStatus.graphics.drawLine(this.posX + 3, this.var_71b - 2, this.posX + this.width - 3, this.var_71b - 2);
            SymbolMapManager_.sub_102(var1, this.innerLeftMargin + 3, this.var_71b - this.rowHeight + 3, 0);
            SymbolMapManager_.sub_161(var2, this.posX + this.width - 4 - 2, this.var_71b - this.rowHeight + 3, 0, 18);
         }

         int var4 = -1;
         if (this.scrollPos == -1) {
            this.scrollPos = 0;
         }

         for(int var5 = this.scrollPos; var5 < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][var5] != null && var5 < this.scrollPos + this.var_664 + 1; ++var5) {
            if (((ListEntry)((ListEntry)this.perTabEntries[this.selectedTab][var5])).sub_25c()) {
               if (this.selectedEntry > var5) {
                  var4 = var5;
               }

               GameStatus.graphics.setColor(Layout.uiOuterDownLeftOutlineInnerLabalBgColor);
               GameStatus.graphics.fillRect(this.posX + 7, this.var_71b + (var5 - this.scrollPos) * this.rowHeight + 3, this.width - 7 - this.var_3f8 - 7, this.rowHeight - 4);
               GameStatus.graphics.setColor(0);
               GameStatus.graphics.drawRect(this.posX + 7, this.var_71b + (var5 - this.scrollPos) * this.rowHeight + 3, this.width - 7 - this.var_3f8 - 7, this.rowHeight - 4);
               GameStatus.graphics.setColor(Layout.uiInnerOutlineColor);
               GameStatus.graphics.drawRect(this.posX + 6, this.var_71b + (var5 - this.scrollPos) * this.rowHeight + 2, this.width - 7 - this.var_3f8 - 5, this.rowHeight - 2);
               Layout.sub_39a(this.posX + 6, this.var_71b + (var5 - this.scrollPos) * this.rowHeight + 2, false);
            }

            this.sub_e4(this.perTabEntries[this.selectedTab][var5], var5);
         }

         if (var4 >= 0) {
            Layout.sub_39a(this.posX + 6, this.var_71b + (var4 - this.scrollPos) * this.rowHeight + 2, true);
         }

      }
   }

   protected void sub_e4(Object var1, int var2) {
      ListEntry var9 = (ListEntry)((ListEntry)var1);
      String var3;
      String var10000;
      int var10001;
      int var10002;
      int var10003;
      if (this.selectedTab != 0 && this.selectedTab != 3) {
         if (this.selectedTab != 1 && this.selectedTab != 4) {
            if (this.selectedTab != 2) {
               return;
            }

            if (var9.sub_25c()) {
               var10000 = var9.label;
               var10001 = this.innerLeftMargin + 3;
               var10002 = this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 5;
               var10003 = 0;
            } else {
               if (!var9.isBluePrint() && !var9.sub_3e2()) {
                  return;
               }

               Face_.sub_128(var9.getIndex(), IndexManager.getItems()[var9.getIndex()].getType(), this.var_21b, this.var_1b7, this.innerLeftMargin + 1, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               if (var2 == this.selectedEntry) {
                  GameStatus.graphics.drawImage(this.var_559, this.innerLeftMargin + 1, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               }

               if (var9.isBluePrint()) {
                  boolean var10 = false;
                  Item[] var11 = Status.getShip().getCargo();
                  int[] var5 = var9.bluePrint.getIngredientList();
                  int[] var6 = var9.bluePrint.missingComponentsTons;
                  if (var11 != null) {
                     for(int var7 = 0; var7 < var5.length && !var10; ++var7) {
                        if (var6[var7] > 0) {
                           for(int var8 = 0; var8 < var11.length; ++var8) {
                              if (var11[var8].getIndex() == var5[var7]) {
                                 var10 = true;
                                 break;
                              }
                           }
                        }
                     }
                  }

                  Layout.sub_4e1((var9.bluePrint.getTonsPerProduction2() > 1 ? var9.bluePrint.getTonsPerProduction() + "x " : "") + IndexManager.getItemName(var9.getIndex()), this.innerLeftMargin + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - Face_.var_c6 - 20 - 9 - this.var_3f8 + 2, this.selectedEntry == var2 ? 2 : 0);
                  if (!var10 || var10 && Layout.sub_119()) {
                     SymbolMapManager_.sub_161((int)(var9.bluePrint.getCompletionRate() * 100.0F) + "%", this.posX + this.width - 6 - this.var_3f8, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
                  }

                  if (!var9.bluePrint.isStarted()) {
                     SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(133) + " " + var9.bluePrint.getProductionStationName(), this.innerLeftMargin + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 9, this.selectedEntry == var2 ? 2 : 1);
                  }

                  return;
               }

               SymbolMapManager_.sub_102((var9.var_6dc.producedQuantity > 1 ? var9.var_6dc.producedQuantity + "x " : "") + IndexManager.getItemName(var9.getIndex()), this.innerLeftMargin + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
               var10000 = GameStatus.langManager.getLangString(133) + " " + var9.var_6dc.stationName;
               var10001 = this.innerLeftMargin + Face_.var_c6 + 6;
               var10002 = this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 9;
               var10003 = this.selectedEntry == var2 ? 2 : 1;
            }
         } else {
            if (!var9.sub_25c()) {
               if (var9.sub_2f7()) {
                  SymbolMapManager_.sub_102(var9.label, this.innerLeftMargin + 3, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 5, this.selectedEntry == var2 ? 2 : 1);
                  return;
               }

               if (var9.isShip()) {
                  Face_.sub_1b6(var9.ship.getIndex(), var9.ship.getRace(), this.var_2b9, this.var_32b, this.innerLeftMargin + 3 + 20, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
                  SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(532 + var9.ship.getIndex()), this.innerLeftMargin + 20 + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
                  SymbolMapManager_.sub_161(Layout.formatCredits(var9.ship.getPrice()) + " (" + Layout.formatCredits(var9.ship.getPrice() - Status.getShip().getPrice()) + ")", this.innerLeftMargin + 20 + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
                  return;
               }

               var3 = this.selectedTab == 1 ? var9.item.getStationAmount() + "" : var9.item.getAmount() + "";
               String var4 = this.selectedTab == 1 ? var9.item.getAmount() + "" : this.var_6d4.getCurrentAmount(var9.item.getIndex()) + var9.item.sub_590() + "/" + this.var_6d4.getTotalAmount(var9.item.getIndex());
               Face_.sub_128(var9.item.getIndex(), var9.item.getType(), this.var_21b, this.var_1b7, this.innerLeftMargin + 3 + 20, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               if (var2 == this.selectedEntry) {
                  GameStatus.graphics.drawImage(this.var_559, this.innerLeftMargin + 3 + 20, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               }

               SymbolMapManager_.sub_161(var3, this.innerLeftMargin + 20, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
               Layout.sub_4e1(IndexManager.getItemName(var9.getIndex()), this.innerLeftMargin + 20 + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - Face_.var_c6 - 40 - 9 - this.var_3f8 + 2 - (this.selectedTab == 4 ? 10 : 0), this.selectedEntry == var2 ? 2 : 0);
               SymbolMapManager_.sub_161(Layout.formatCredits(var9.item.getPrice()), this.innerLeftMargin + 20 + Face_.var_c6 + 6, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
               SymbolMapManager_.sub_161(var4, this.posX + this.width - 6 - this.var_3f8, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 1, 18);
               if (this.var_5da && !this.var_5eb && var2 == this.selectedEntry && Layout.sub_119()) {
                  SymbolMapManager_.sub_161("<", this.innerLeftMargin + 20, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, 2, 18);
                  SymbolMapManager_.sub_161(">", this.posX + this.width - 6 - this.var_3f8, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, 2, 18);
               }

               return;
            }

            var10000 = var9.label + (var9.displayedCountType >= 0 ? " (" + this.var_634[var9.displayedCountType] + ")" : "");
            var10001 = this.innerLeftMargin + 3;
            var10002 = this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 5;
            var10003 = 0;
         }
      } else {
         if (var9.sub_2f7()) {
            SymbolMapManager_.sub_102(var9.label, this.innerLeftMargin + 3, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 5, this.selectedEntry == var2 ? 2 : 1);
            return;
         }

         if (!var9.sub_25c()) {
            if (var9.isShip()) {
               Face_.sub_1b6(var9.ship.getIndex(), var9.ship.getRace(), this.var_2b9, this.var_32b, this.innerLeftMargin + 1, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(532 + var9.ship.getIndex()), this.innerLeftMargin + Face_.var_c6 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.selectedEntry == var2 ? 2 : 0);
               SymbolMapManager_.sub_161(Layout.formatCredits(var9.ship.getPrice()), this.innerLeftMargin + Face_.var_c6 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
               return;
            }

            if (var9.isItem()) {
               Face_.sub_128(var9.item.getIndex(), var9.item.getType(), this.var_21b, this.var_1b7, this.innerLeftMargin + 1, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               if (var2 == this.selectedEntry) {
                  GameStatus.graphics.drawImage(this.var_559, this.innerLeftMargin + 1, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + this.sub_get72b() / 2 + 1, 6);
               }

               var3 = IndexManager.getItemName(var9.item.getIndex());
               if (var9.item.getType() == 1) {
                  var3 = var3 + " (" + var9.item.getAmount() + ")";
               }

               Layout.sub_4e1(var3, this.innerLeftMargin + Face_.var_c6 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1, this.width - this.innerLeftMargin - Face_.var_c6 - 9 - this.var_3f8 + 2, this.selectedEntry == var2 ? 2 : 0);
               SymbolMapManager_.sub_161(Layout.formatCredits(var9.item.getPrice()), this.innerLeftMargin + Face_.var_c6 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 1 + 8, this.selectedEntry == var2 ? 2 : 1, 0);
               return;
            }

            if (var9.sub_272()) {
               SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(69), this.innerLeftMargin + Face_.var_c6 + 5, this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 3, this.selectedEntry == var2 ? 2 : 1);
               return;
            }

            return;
         }

         var10000 = var9.label + (var9.displayedCountType >= 0 ? " (" + Status.getShip().countEquippedOfType(var9.displayedCountType) + "/" + Status.getShip().sub_9ca(var9.displayedCountType) + ")" : "");
         var10001 = this.innerLeftMargin + 5;
         var10002 = this.var_71b + (var2 - this.scrollPos) * this.rowHeight + 5;
         var10003 = 0;
      }

      SymbolMapManager_.sub_102(var10000, var10001, var10002, var10003);
   }

   public final int sub_get72b() {
      return 20;
   }

   public final int sub_149() {
      return SymbolMapManager_.sub_2c2() + 4;
   }
}
