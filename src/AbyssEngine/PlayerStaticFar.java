package AbyssEngine;

public class PlayerStaticFar extends PlayerStatic {
   protected AbstractBounding[] var_120;

   public PlayerStaticFar(int var1, AbstractMesh var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
      this.targetX = var3;
      this.targetY = var4;
      this.targetZ = var5;
      if (var2 != null) {
         short var6 = 7500;
         this.player.radius = (float)var6;
         this.var_25d.setRenderLayer(2);
      }
   }

   public void sub_109doSth() {
      super.sub_109doSth();
   }

   public void update(long var1) {
      if (this.var_25d != null) {
         this.var_727 = GameStatus.renderer.getCamera().sub_22a(this.var_727);
         this.positon.set(this.posX, this.posY, this.posZ);
         this.positon.subtract(this.var_727, var_2e9);
         int var3;
         if ((var3 = var_2e9.getLength()) > 28000) {
            var_2e9.normalize();
            var_2e9.scale(28000);
            var_2e9.add(this.var_727);
            this.var_25d.sub_1f3(var_2e9);
            var3 = (int)(28000.0F / (float)var3 * 4096.0F);
            this.var_25d.sub_7af(var3, var3, var3);
         } else {
            this.var_25d.sub_7af(4096, 4096, 4096);
            this.var_25d.moveTo(this.posX, this.posY, this.posZ);
         }
      }
   }

   public boolean sub_162(int var1, int var2, int var3) {
      return (float)(var1 - this.posX) < this.player.radius && (float)(var1 - this.posX) > -this.player.radius && (float)(var2 - this.posY) < this.player.radius && (float)(var2 - this.posY) > -this.player.radius && (float)(var3 - this.posZ) < this.player.radius && (float)(var3 - this.posZ) > -this.player.radius;
   }

   public final AEVector3D sub_52(AEVector3D var1) {
      var1.x = this.targetX;
      var1.y = this.targetY;
      var1.z = this.targetZ;
      return var1;
   }

   public boolean sub_1b8(AEVector3D var1) {
      return this.sub_162(var1.x, var1.y, var1.z);
   }

   public AEVector3D sub_005(AEVector3D var1) {
      return this.var_120 != null ? this.var_120[0].sub_3d(var1) : null;
   }
}
