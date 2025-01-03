package AbyssEngine;

public final class Class_e19 extends KIPlayer {
   public int var_c7;
   public int var_282;
   public int var_2f9;
   public boolean var_35b;

   public Class_e19(int var1, int var2, int var3, Class_661 var4) {
      super(0, -1, new Player(2000.0F, 0, 0, 0, 0), (AbstractMesh)null, var1, var2, var3);
      this.var_50b = var4;
      this.player.sub_a98(false);
      this.var_c7 = var1;
      this.var_282 = var2;
      this.var_2f9 = var3;
      this.targetX = var1;
      this.targetX = var2;
      this.targetZ = var3;
      this.var_35b = false;
   }

   public final AEVector3D sub_a9(AEVector3D var1) {
      var1.x = this.var_c7;
      var1.y = this.var_282;
      var1.z = this.var_2f9;
      return var1;
   }

   public final void sub_34(boolean var1) {
      this.player.sub_a98(var1);
   }

   public final void sub_af() {
      this.var_35b = false;
      this.player.sub_a98(false);
   }

   public final void update(long var1) {
   }

   public final void sub_109doSth() {
   }

   public final boolean sub_162(int var1, int var2, int var3) {
      return false;
   }
}
