package Main;

import AbyssEngine.Layout;

public final class Class_1e2 extends TabbedWindow {
   private boolean var_37;

   public Class_1e2(int var1, int var2, int var3, int var4, String[] var5) {
      super(var1, var2, var3, var4, (String[])null, "");
      this.var_888 = false;
      this.innerLeftMargin = var1;
      this.var_37 = false;
   }

   public final void sub_6c() {
      this.sub_171();
      this.sub_312();
   }

   protected final void sub_19() {
   }

   public final void sub_6b() {
      if (this.perTabEntries[this.selectedTab][this.selectedEntry + 1] != null) {
         ++this.selectedEntry;
      } else {
         this.scrollPos = 0;
         this.selectedEntry = 0;
      }

      if (this.selectedEntry > this.scrollPos + this.var_664) {
         ++this.scrollPos;
      }

      this.sub_238();
   }

   public final void sub_b4() {
      if (this.selectedEntry > 0) {
         --this.selectedEntry;
      } else {
         int var1 = 1;

         while(this.perTabEntries[this.selectedTab][var1++] != null) {
         }

         this.selectedEntry = var1 - 2;
         this.scrollPos = var1 - 1 - this.var_664;
      }

      if (this.scrollPos > 0) {
         --this.scrollPos;
      }

      this.sub_238();
   }

   public final void sub_171() {
      for(int var1 = this.scrollPos; var1 < this.perTabEntries[this.selectedTab].length && this.perTabEntries[this.selectedTab][var1] != null && var1 < this.scrollPos + this.var_664 + 1; ++var1) {
         this.sub_e4(this.perTabEntries[this.selectedTab][var1], var1);
      }

   }

   protected final void sub_e4(Object var1, int var2) {
      Layout.scale(var1.toString(), this.innerLeftMargin, this.var_71b + (var2 - this.scrollPos) * this.rowHeight, this.var_37d > 0 ? this.width - 7 : this.width, var2 == this.selectedEntry && this.var_888);
   }

   public final int sub_18a() {
      return this.perTabEntries != null && this.perTabEntries[0] != null ? this.var_353[0] : 0;
   }
}
