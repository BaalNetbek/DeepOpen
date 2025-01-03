package AbyssEngine;

public final class PlayerJunk extends KIPlayer {
   private long var_1b5;

   public PlayerJunk(int var1, Player var2, AbstractMesh var3, int var4, int var5, int var6) {
      super(9996, -1, var2, var3, var4, var5, var6);
      this.player.var_46b = var3.sub_85f();
      var1 = 4096 + GameStatus.random.nextInt(8096);
      var3.sub_7af(var1, var1, var1);
      this.var_99f = true;
   }

   public final void update(long var1) {
      this.var_1b5 = var1;
      if (this.player.getHitpoints() <= 0 && this.state != 3 && this.state != 4) {
         this.level.sub_7a1();
         this.state = 3;
         if (GameStatus.random.nextInt(100) < 10) {
            this.var_9d8 = true;
            this.var_a3b = new int[2];
            this.var_a3b[1] = 1 + GameStatus.random.nextInt(10);
            this.var_a3b[0] = 99;
            this.setWasteMesh(2);
            this.var_9d8 = true;
         } else {
            this.player.sub_a98(false);
            if (this.level.getPlayerEgo().var_15f4.var_fdf == this) {
               this.level.getPlayerEgo().var_15f4.var_fdf = null;
            }
         }

         if (this.explosion != null) {
            this.positon = this.var_25d.sub_22a(this.positon);
            this.explosion.sub_141(this.positon.x, this.positon.y, this.positon.z);
         }
      }

      switch(this.state) {
      case 3:
         if (this.explosion == null || this.explosion.sub_185()) {
            this.state = 4;
         }
      default:
      }
   }

   public final void sub_109doSth() {
      if (this.waste != null) {
         GameStatus.var_8ce.sub_87(this.waste);
      }

      if (this.state != 4 && this.state != 3) {
         super.sub_109doSth();
      }

      if (this.state == 3 && this.explosion != null) {
         this.explosion.sub_179(this.var_1b5);
      }

   }

   public final boolean sub_162(int var1, int var2, int var3) {
      return false;
   }
}
