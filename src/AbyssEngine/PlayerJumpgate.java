package AbyssEngine;

public final class PlayerJumpgate extends PlayerStaticFar {
   private boolean var_4d;

   public PlayerJumpgate(int var1, AbstractMesh var2, int var3, int var4, int var5, boolean var6) {
      super(15, var2, var3, var4, var5);
      this.sub_73(var6);
      if (var6) {
         this.var_120 = new AbstractBounding[1];
         this.var_120[0] = new Class_5bfcollision(var3, var4, var5, 0, 0, 0, 15000);
         this.var_25d.sub_918(100);
         this.var_25d.sub_980(1, 20);
         this.var_25d.sub_9aa((byte)2);
      }

      var2.setRotation(-1024, 0, 0);
      this.var_4d = false;
   }

   public final void sub_25() {
      if (!this.var_4d) {
         this.var_25d.sub_918(50);
         this.var_25d.sub_980(21, 79);
         this.var_25d.sub_9aa((byte)1);
         this.var_4d = true;
      }

   }

   public final void setPosition(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
      this.var_25d.moveTo(var1, var2, var3);
   }

   public final void update(long var1) {
      if (this.var_25d.sub_942() == 79) {
         this.var_25d.sub_918(100);
         this.var_25d.sub_980(38, 60);
         this.var_25d.sub_9aa((byte)2);
      }

      super.update(var1);
   }
}
