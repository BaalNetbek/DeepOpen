package AbyssEngine;

public final class Class_8e4superOrbitHelper {
   private int var_79;
   private long var_ae;
   private Camera var_f6;
   private Class_137dStationOrbitHelper var_1b6;
   private final int[][] var_20e = new int[][]{{0, 0, 0, 70000, 0, -300, 0, 800, 10000, 40000, 5000, -5000, -128, 1400, 0, 800, 20000, 0, 0, -70000, 0, 2048, 0, 800, 40000, -45000, -5000, 40000, 128, 3072, 0, 800, 60000, 0, 0, 70000, 0, 3796, 0, 800}, {0, -1000, 600, -7000, 0, 2248, 0, 1000, 3000, 0, 600, -7000, -128, 2049, 0, 1000, 6000, 1000, 1000, -7000, -256, 1848, 0, 1000, 20000000, 1000, 600, -7000, -256, 1848, 0, 1000}};

   public Class_8e4superOrbitHelper(int var1) {
      this.var_f6 = Camera.sub_1b1(GameStatus.screenWidth, GameStatus.screenHeight, 800, 600, 100000);
      this.var_f6.sub_5a7((short)2);
      this.var_79 = 0;
      this.var_1b6 = new Class_137dStationOrbitHelper(this.var_20e, var1, (long)this.var_20e[var1][this.var_20e[var1].length - 8]);
   }

   public final void sub_4d(long var1) {
      if (var1 <= 700L) {
         if (this.var_ae == 0L) {
            this.var_ae = var1;
            this.var_79 = 0;
         }

         this.var_79 = (int)((long)this.var_79 + var1);
         this.var_1b6.sub_62((long)this.var_79);
         this.var_f6.moveTo(this.var_1b6.var_654.x, this.var_1b6.var_654.y, this.var_1b6.var_654.z);
         this.var_f6.setRotation(this.var_1b6.var_724.x, this.var_1b6.var_724.y, this.var_1b6.var_724.z);
         this.var_f6.sub_ba(this.var_1b6.var_758);
      }
   }

   public final Camera sub_15c() {
      return this.var_f6;
   }
}
