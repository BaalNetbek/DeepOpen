package AbyssEngine;

import javax.microedition.lcdui.Image;

public final class ListEntry {
   private Agent var_fe;
   public BluePrint bluePrint;
   public Ship ship;
   public Item item;
   private Mission var_3d5;
   public boolean isSelectable;
   public String label;
   private String var_470;
   public int medalTier = -1;
   public int itemId = 0;
   public int displayedCountType = -1;
   public Image var_638;
   private boolean var_68c;
   public int var_6b0;
   public ProducedGood var_6dc;

   public ListEntry(ListEntry var1) {
      this.var_fe = var1.var_fe;
      this.bluePrint = var1.bluePrint;
      this.ship = var1.ship;
      this.item = var1.item;
      this.var_3d5 = var1.var_3d5;
      this.isSelectable = var1.isSelectable;
      this.label = var1.label;
      this.var_470 = var1.var_470;
      this.medalTier = var1.medalTier;
      this.itemId = var1.itemId;
      this.displayedCountType = var1.displayedCountType;
      this.var_638 = var1.var_638;
      this.var_68c = var1.var_68c;
      this.var_6b0 = var1.var_6b0;
      this.var_6dc = var1.var_6dc;
   }

   public ListEntry(BluePrint var1) {
      this.bluePrint = var1;
      this.isSelectable = true;
   }

   public ListEntry(Ship var1) {
      this.ship = var1;
      this.isSelectable = true;
   }

   public ListEntry(Item var1) {
      this.item = var1;
      this.isSelectable = true;
   }

   public ListEntry(ProducedGood var1) {
      this.var_6dc = var1;
      this.isSelectable = true;
   }

   public ListEntry(String var1, boolean var2, int var3) {
      this.label = var1;
      this.var_68c = true;
      this.displayedCountType = var3;
      this.isSelectable = var2;
   }

   public ListEntry(String var1) {
      this.label = var1;
      this.isSelectable = false;
   }

   public ListEntry(String var1, int var2) {
      this.label = var1;
      this.displayedCountType = var2;
      this.isSelectable = false;
   }

   public ListEntry(int var1) {
      this.medalTier = var1;
      this.isSelectable = true;
   }

   public ListEntry(int var1, int var2) {
      this.itemId = var1;
      this.medalTier = var2;
      this.isSelectable = true;
   }

   public final boolean isShip() {
      return this.ship != null;
   }

   public final boolean isItem() {
      return this.item != null;
   }

   private boolean hasItem() {
      return this.item != null;
   }

   public final boolean sub_25c() {
      return !this.isSelectable && this.label != null && !this.var_68c;
   }

   public final boolean sub_272() {
      return this.medalTier >= 0;
   }

   public final boolean sub_2f7() {
      return this.var_68c;
   }

   public final boolean sub_315() {
      return this.var_68c && this.displayedCountType == 0;
   }

   public final boolean sub_370() {
      return this.var_68c && this.displayedCountType == 1;
   }

   public final boolean isBluePrint() {
      return this.bluePrint != null;
   }

   public final boolean sub_3e2() {
      return this.var_6dc != null;
   }

   public final int getPrice() {
      if (this.isShip()) {
         return this.ship.getPrice();
      } else if (this.isItem()) {
         return this.item.getPrice();
      } else {
         return this.hasItem() ? this.item.getPrice() : 0;
      }
   }

   public final int getIndex() {
      if (this.isShip()) {
         return this.ship.getIndex();
      } else if (this.isItem()) {
         return this.item.getIndex();
      } else if (this.hasItem()) {
         return this.item.getIndex();
      } else if (this.isBluePrint()) {
         return this.bluePrint.productId;
      } else {
         return this.sub_3e2() ? this.var_6dc.index : 999999;
      }
   }
}
