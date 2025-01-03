package AbyssEngine;

public class BlasterGun extends AbstractMesh implements Class_786 {
   protected Gun gun;
   private AbstractMesh[] var_18b;
   protected AbstractMesh var_1c8;
   protected static AEVector3D var_211;

   public BlasterGun(Gun var1, AbstractMesh var2) {
      this.gun = var1;
      var_211 = new AEVector3D();
      if (var2 == null) {
         this.var_18b = null;
      } else {
         this.var_18b = new AbstractMesh[var1.projectilesPos.length];

         for(int var3 = 0; var3 < this.var_18b.length; ++var3) {
            var_211.set(var2.sub_821());
            this.var_18b[var3] = (AbstractMesh)var2.sub_2b();
            this.var_18b[var3].sub_7af(var_211.x, var_211.y, var_211.z);
            this.var_18b[var3].setFlag_(2);
         }

      }
   }

   public void destroy() {
      this.gun.sub_183();
      this.gun = null;
   }

   public void render() {
      this.gun.sub_341();
      if (this.gun.inAir && this.var_18b != null) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.gun.projectilesPos.length; ++var2) {
            if (this.gun.projectilesPos[var2].x != 50000) {
               this.var_18b[var2].sub_1f3(this.gun.projectilesPos[var2]);
               var_211.set(this.gun.projectilesDir[var2]);
               var_211.normalize();
               this.var_18b[var2].sub_85f().setOrientation(var_211);
               GameStatus.var_8ce.sub_87(this.var_18b[var2]);
            } else {
               ++var1;
            }
         }

         if (var1 >= this.gun.projectilesTimeLeft.length) {
            this.gun.inAir = false;
         }
      }

   }

   public void sub_181(long var1) {
      this.gun.sub_318(var1);
   }

   public final GraphNode sub_2b() {
      return null;
   }

   public final void setTexture(AbstractTexture var1) {
   }
}
