package AbyssEngine;

public final class Agent {
   public String fullName;
   public String var_70;
   public String var_ad;
   private int itemId;
   private int itemQuantity;
   private int sellingPrice;
   private int messageId;
   private int stationId;
   private int systemId;
   private int race;
   private boolean var_4b9;
   private int isTouched;
   private int dialogueStage;
   private int type;
   public int var_5d5;
   private int diplomacyPrice;
   private int secretSystemId;
   private int blueprintItemId;
   private String message = "";
   private String stationName;
   public String systemName;
   private boolean accepted;
   private boolean var_894;
   private byte[] face;
   private Mission mission;
   public boolean var_99e;
   public boolean var_9cd;

   public Agent(int var1, String var2, int var3, int var4, int var5, boolean var6, int var7, int var8, int var9) {
      this.messageId = var1;
      this.fullName = var2;
      this.stationId = var3;
      this.systemId = var4;
      this.race = var5;
      this.var_4b9 = var6;
      this.isTouched = 0;
      this.secretSystemId = var7;
      if (var7 >= 0) {
         this.type = 4;
      }

      this.blueprintItemId = var8;
      if (var8 >= 0) {
         this.type = 3;
      }

      this.sellingPrice = var9;
      this.dialogueStage = var1 >= 0 ? 0 : 1;
      this.var_5d5 = 0;
      this.accepted = false;
      this.var_894 = false;
      this.var_99e = false;
      this.var_9cd = false;
   }

   public final int sub_112() {
      return this.messageId;
   }

   public final String getFullName() {
      return this.fullName;
   }

   public final int sub_1b1() {
      return this.type;
   }

   public final int getRace() {
      return this.race;
   }

   public final int getSystemId() {
      return this.systemId;
   }

   public final int getStationId() {
      return this.stationId;
   }

   public final boolean sub_302() {
      return this.var_4b9;
   }

   public final int sub_35e() {
      return this.isTouched;
   }

   public final void sub_3c2(int var1) {
      this.isTouched = var1;
   }

   public final boolean sub_3f0() {
      return this.dialogueStage == 0;
   }

   public final boolean sub_41f() {
      return this.dialogueStage == 1;
   }

   public final void sub_460(byte[] var1) {
      this.face = var1;
   }

   public final byte[] sub_471() {
      return this.face;
   }

   public final void sub_48a(int var1) {
      this.type = var1;
   }

   public final void sub_4e1(Mission var1) {
      this.mission = var1;
   }

   public final Mission sub_4f9() {
      return this.mission;
   }

   public final boolean sub_547() {
      return this.isTouched > 0;
   }

   public final int sub_569() {
      return this.secretSystemId;
   }

   public final int sub_582() {
      return this.blueprintItemId;
   }

   public final void sub_5c4(int var1) {
      this.diplomacyPrice = var1;
   }

   public final int sub_5d0() {
      return this.diplomacyPrice;
   }

   public final int sub_634() {
      return this.var_5d5;
   }

   public final String sub_689(int var1) {
      return this.var_70;
   }

   public final String[] sub_6c1() {
      String[] var1;
      (var1 = new String[1 + this.var_5d5])[0] = this.fullName;
      if (this.var_5d5 > 0) {
         var1[1] = this.var_70;
      }

      if (this.var_5d5 > 1) {
         var1[2] = this.var_ad;
      }

      return var1;
   }

   public final String getMessage() {
      return this.message;
   }

   public final void setMessage(String var1) {
      this.message = var1;
   }

   public final int sub_773() {
      return this.itemId;
   }

   public final int sub_7ae() {
      return this.itemQuantity;
   }

   public final int sub_7d5() {
      return this.sellingPrice;
   }

   public final void sub_82f(int var1, int var2, int var3) {
      this.itemId = var1;
      this.itemQuantity = var2;
      this.sellingPrice = var3;
   }

   public final boolean isAccepted() {
      return this.accepted;
   }

   public final void setAccepted(boolean var1) {
      this.accepted = var1;
   }

   public final boolean sub_8fc() {
      return this.var_894;
   }

   public final void sub_938(boolean var1) {
      this.var_894 = var1;
   }

   public final String getStationName() {
      return this.stationName;
   }

   public final void setAgentsStationName(String var1) {
      this.stationName = var1.equals("") ? null : var1;
   }

   public final void setAgentsSystemName(String var1) {
      this.systemName = var1.equals("") ? null : var1;
   }
}
