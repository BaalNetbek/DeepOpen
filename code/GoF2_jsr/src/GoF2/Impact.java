package GoF2;

import AE.AbstractMesh;
import AE.ITexture;
import AE.ParticlesMesh;
import java.util.Random;

public final class Impact {
   public AbstractMesh mesh;
   private int[] particlesLifeTime;
   private int baseLifeTime;
   private int spread;
   private int lifeTimeRange;

   public Impact(int var1, ITexture var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, byte var11) {
      this.spread = var8;
      this.baseLifeTime = var9;
      this.lifeTimeRange = var10;
      this.particlesLifeTime = new int[var1];
      short var15 = 256;
      this.mesh = new ParticlesMesh(var15, var4, var5, var6, var7, var8, var1, var11);
      this.mesh.setTexture(var2);
      ParticlesMesh var13;
      int[] var16 = (var13 = (ParticlesMesh)this.mesh).getPositions();
      int[] var14 = var13.getScales();
      Random var17 = new Random();

      for(var5 = 0; var5 < var1; ++var5) {
         var16[var5 * 3] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var16[var5 * 3 + 1] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var16[var5 * 3 + 2] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var14[var5] = 0;
         this.particlesLifeTime[var5] = var17.nextInt(var10) - (var10 >> 1) + this.baseLifeTime;
      }

   }

   public final void explode() {
      ParticlesMesh var1;
      int[] var2 = (var1 = (ParticlesMesh)this.mesh).getPositions();
      int[] var3 = var1.getScales();
      int[] var6 = var1.getColors();
      Random var4 = new Random();

      for(int var5 = 0; var5 < var3.length; ++var5) {
         var2[var5 * 3] = var4.nextInt(this.spread >> 1) - (this.spread >> 2);
         var2[var5 * 3 + 1] = var4.nextInt(this.spread >> 1) - (this.spread >> 2);
         var2[var5 * 3 + 2] = var4.nextInt(this.spread >> 1) - (this.spread >> 2);
         var3[var5] = 0;
         var6[var5] = -1;
         this.particlesLifeTime[var5] = var4.nextInt(this.lifeTimeRange) - (this.lifeTimeRange >> 1) + this.baseLifeTime;
      }

   }

   public final void update(int var1) {
      int[] var2 = ((ParticlesMesh)this.mesh).getScales();
      int[] var3 = ((ParticlesMesh)this.mesh).getColors();
      int var4 = var1 * this.spread / this.baseLifeTime;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (this.particlesLifeTime[var5] > 0 && this.particlesLifeTime[var5] < this.baseLifeTime) {
            var2[var5] += var4;
         } else if (this.particlesLifeTime[var5] > -1024) {
            int var6 = (var6 = (var3[var5] >>> 24) - (var1 >> 2)) < 0 ? 0 : var6;
            var3[var5] = var6 << 24 | 16777215;
         } else {
            var2[var5] = 0;
         }

         int[] var10000 = this.particlesLifeTime;
         var10000[var5] -= var1;
      }

   }
}
