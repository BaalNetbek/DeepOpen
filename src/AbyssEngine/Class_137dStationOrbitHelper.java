package AbyssEngine;

public final class Class_137dStationOrbitHelper {
   private float[] stationParams;
   private float[] var_208;
   private float[] var_336;
   private int var_41a = 0;
   private float var_486;
   private float[] var_50c;
   private float[] var_53e;
   private float[] var_5b2;
   private int[] var_5e8 = new int[3];
   private int[] var_609 = new int[3];
   public AEVector3D var_654 = new AEVector3D();
   public AEVector3D var_724 = new AEVector3D();
   public int var_758 = 0;
   private int[][] var_7ad;
   private long var_7d7;

   public Class_137dStationOrbitHelper(int[][] var1, int var2, long var3) {
      this.var_7d7 = var3;
      if (var1 != null && var1[var2] != null) {
         this.var_7ad = new int[var1[var2].length / 8][];

         for(int var5 = 0; var5 < var1[var2].length / 8; ++var5) {
            this.var_7ad[var5] = new int[8];
            System.arraycopy(var1[var2], var5 << 3, this.var_7ad[var5], 0, 8);
         }

         if (this.var_7ad != null) {
            this.var_41a = this.var_7ad.length;
            this.stationParams = new float[(this.var_41a + 1) * 3];
            this.var_208 = new float[(this.var_41a + 1) * 3];
            this.var_336 = new float[this.var_41a + 1];
            this.var_50c = new float[(this.var_41a + 1) * 3];
            this.var_53e = new float[(this.var_41a + 1) * 3];
            this.var_5b2 = new float[3];
            this.sub_153();
            this.sub_f7();
         }
      }

   }

   public final void sub_62(long var1) {
      if (this.var_41a >= 2) {
         if (var1 > this.var_7d7) {
            var1 %= this.var_7d7;
         }

         if (this.var_41a == 2) {
            this.sub_7a((float)var1 / (float)this.var_7ad[1][0], 0);
         } else {
            for(int var3 = this.var_41a - 1; var3 >= 0; --var3) {
               if ((long)this.var_7ad[var3][0] < var1) {
                  float var4;
                  if (var3 != 0 && var3 == this.var_41a - 1) {
                     var4 = (float)(var1 - (long)this.var_7ad[var3][0]) / (float)(this.var_7d7 - (long)this.var_7ad[var3][0]);
                  } else {
                     var4 = (float)(var1 - (long)this.var_7ad[var3][0]) / (float)(this.var_7ad[var3 + 1][0] - this.var_7ad[var3][0]);
                  }

                  this.sub_7a(var4, var3);
                  return;
               }
            }

         }
      }
   }

   private void sub_7a(float var1, int var2) {
      float var3;
      float var4 = (var3 = var1 * var1) * var1;
      float var5 = var4 * 2.0F - 3.0F * var3 + 1.0F;
      var1 += var4 - var3 * 2.0F;
      float var6 = -2.0F * var4 + 3.0F * var3;
      var3 = var4 - var3;
      this.var_5e8[0] = this.var_7ad[var2][1];
      this.var_5e8[1] = this.var_7ad[var2][2];
      this.var_5e8[2] = this.var_7ad[var2][3];
      if (var2 == this.var_7ad.length - 1) {
         this.var_609[0] = this.var_7ad[0][1];
         this.var_609[1] = this.var_7ad[0][2];
         this.var_609[2] = this.var_7ad[0][3];
      } else {
         this.var_609[0] = this.var_7ad[var2 + 1][1];
         this.var_609[1] = this.var_7ad[var2 + 1][2];
         this.var_609[2] = this.var_7ad[var2 + 1][3];
      }

      this.var_654.x = (int)(var5 * (float)this.var_5e8[0] + var1 * this.stationParams[var2 * 3] + var6 * (float)this.var_609[0] + var3 * this.stationParams[(var2 + 1) * 3]);
      this.var_654.y = (int)(var5 * (float)this.var_5e8[1] + var1 * this.stationParams[var2 * 3 + 1] + var6 * (float)this.var_609[1] + var3 * this.stationParams[(var2 + 1) * 3 + 1]);
      this.var_654.z = (int)(var5 * (float)this.var_5e8[2] + var1 * this.stationParams[var2 * 3 + 2] + var6 * (float)this.var_609[2] + var3 * this.stationParams[(var2 + 1) * 3 + 2]);
      this.var_5e8[0] = this.var_7ad[var2][4];
      this.var_5e8[1] = this.var_7ad[var2][5];
      this.var_5e8[2] = this.var_7ad[var2][6];
      if (var2 == this.var_7ad.length - 1) {
         this.var_609[0] = this.var_7ad[0][4];
         this.var_609[1] = this.var_7ad[0][5];
         this.var_609[2] = this.var_7ad[0][6];
      } else {
         this.var_609[0] = this.var_7ad[var2 + 1][4];
         this.var_609[1] = this.var_7ad[var2 + 1][5];
         this.var_609[2] = this.var_7ad[var2 + 1][6];
      }

      this.var_724.x = (int)(var5 * (float)this.var_5e8[0] + var1 * this.var_208[var2 * 3] + var6 * (float)this.var_609[0] + var3 * this.var_208[(var2 + 1) * 3]);
      this.var_724.y = (int)(var5 * (float)this.var_5e8[1] + var1 * this.var_208[var2 * 3 + 1] + var6 * (float)this.var_609[1] + var3 * this.var_208[(var2 + 1) * 3 + 1]);
      this.var_724.z = (int)(var5 * (float)this.var_5e8[2] + var1 * this.var_208[var2 * 3 + 2] + var6 * (float)this.var_609[2] + var3 * this.var_208[(var2 + 1) * 3 + 2]);
      this.var_5e8[0] = this.var_7ad[var2][7];
      if (var2 == this.var_7ad.length - 1) {
         this.var_609[0] = this.var_7ad[0][7];
      } else {
         this.var_609[0] = this.var_7ad[var2 + 1][7];
      }

      this.var_758 = (int)(var5 * (float)this.var_5e8[0] + var1 * this.var_336[var2] + var6 * (float)this.var_609[0] + var3 * this.var_336[var2 + 1]);
   }

   private void sub_f7() {
      if (this.var_41a <= 1) {
         this.var_486 = 0.0F;
      } else {
         this.var_486 = 0.0F;

         int var1;
         for(var1 = 0; var1 < this.var_41a; ++var1) {
            int var2 = 1;
            float var3 = 1.0F;

            for(float var4 = 0.0F; (double)var3 > 0.005D; var2 <<= 1) {
               var3 = 1.0F / (float)var2;
               this.var_5b2[0] = (float)this.var_7ad[var1][1];
               this.var_5b2[1] = (float)this.var_7ad[var1][2];
               this.var_5b2[2] = (float)this.var_7ad[var1][3];
               this.var_50c[var1] = 0.0F;

               for(int var5 = 1; var5 <= var2; ++var5) {
                  float var6 = (float)var5 * var3;
                  this.sub_7a(var6, var1);
                  float[] var10000 = this.var_50c;
                  var10000[var1] = (float)((double)var10000[var1] + Math.sqrt((double)(((float)this.var_654.x - this.var_5b2[0]) * ((float)this.var_654.x - this.var_5b2[0]) + ((float)this.var_654.y - this.var_5b2[1]) * ((float)this.var_654.y - this.var_5b2[1]) + ((float)this.var_654.z - this.var_5b2[2]) * ((float)this.var_654.z - this.var_5b2[2]))));
                  this.var_5b2[0] = (float)this.var_654.x;
                  this.var_5b2[1] = (float)this.var_654.y;
                  this.var_5b2[2] = (float)this.var_654.z;
               }

               var3 = (this.var_50c[var1] - var4) / this.var_50c[var1];
               var4 = this.var_50c[var1];
            }

            this.var_486 += this.var_50c[var1];
         }

         for(var1 = 0; var1 < this.var_41a; ++var1) {
            if (var1 == 0) {
               this.var_53e[var1] = this.var_50c[var1] / this.var_486;
            } else {
               this.var_53e[var1] = this.var_53e[var1 - 1] + this.var_50c[var1] / this.var_486;
            }
         }

      }
   }

   private void sub_153() {
      if (this.var_41a > 1) {
         this.stationParams[0] = 0.0F;
         this.stationParams[1] = 0.0F;
         this.stationParams[2] = 0.0F;
         this.stationParams[(this.var_41a - 1) * 3] = 0.0F;
         this.stationParams[(this.var_41a - 1) * 3 + 1] = 0.0F;
         this.stationParams[(this.var_41a - 1) * 3 + 2] = 0.0F;

         int var1;
         for(var1 = 0; var1 < this.var_41a; ++var1) {
            if (var1 == this.var_41a - 1) {
               this.var_5e8[0] = this.var_7ad[0][1];
               this.var_5e8[1] = this.var_7ad[0][2];
               this.var_5e8[2] = this.var_7ad[0][3];
            } else {
               this.var_5e8[0] = this.var_7ad[var1 + 1][1];
               this.var_5e8[1] = this.var_7ad[var1 + 1][2];
               this.var_5e8[2] = this.var_7ad[var1 + 1][3];
            }

            if (var1 == 0) {
               this.var_609[0] = this.var_7ad[this.var_7ad.length - 1][1];
               this.var_609[1] = this.var_7ad[this.var_7ad.length - 1][2];
               this.var_609[2] = this.var_7ad[this.var_7ad.length - 1][3];
            } else {
               this.var_609[0] = this.var_7ad[var1 - 1][1];
               this.var_609[1] = this.var_7ad[var1 - 1][2];
               this.var_609[2] = this.var_7ad[var1 - 1][3];
            }

            this.stationParams[var1 * 3] = 0.5F * (float)(this.var_5e8[0] - this.var_609[0]);
            this.stationParams[var1 * 3 + 1] = 0.5F * (float)(this.var_5e8[1] - this.var_609[1]);
            this.stationParams[var1 * 3 + 2] = 0.5F * (float)(this.var_5e8[2] - this.var_609[2]);
         }

         this.stationParams[this.var_41a * 3] = this.stationParams[0];
         this.stationParams[this.var_41a * 3 + 1] = this.stationParams[1];
         this.stationParams[this.var_41a * 3 + 2] = this.stationParams[2];
         this.var_208[0] = 0.0F;
         this.var_208[1] = 0.0F;
         this.var_208[2] = 0.0F;
         this.var_208[(this.var_41a - 1) * 3] = 0.0F;
         this.var_208[(this.var_41a - 1) * 3 + 1] = 0.0F;
         this.var_208[(this.var_41a - 1) * 3 + 2] = 0.0F;

         for(var1 = 0; var1 < this.var_41a; ++var1) {
            if (var1 == this.var_41a - 1) {
               this.var_5e8[0] = this.var_7ad[0][4];
               this.var_5e8[1] = this.var_7ad[0][5];
               this.var_5e8[2] = this.var_7ad[0][6];
            } else {
               this.var_5e8[0] = this.var_7ad[var1 + 1][4];
               this.var_5e8[1] = this.var_7ad[var1 + 1][5];
               this.var_5e8[2] = this.var_7ad[var1 + 1][6];
            }

            if (var1 == 0) {
               this.var_609[0] = this.var_7ad[this.var_7ad.length - 1][4];
               this.var_609[1] = this.var_7ad[this.var_7ad.length - 1][5];
               this.var_609[2] = this.var_7ad[this.var_7ad.length - 1][6];
            } else {
               this.var_609[0] = this.var_7ad[var1 - 1][4];
               this.var_609[1] = this.var_7ad[var1 - 1][5];
               this.var_609[2] = this.var_7ad[var1 - 1][6];
            }

            this.var_208[var1 * 3] = 0.5F * (float)(this.var_5e8[0] - this.var_609[0]);
            this.var_208[var1 * 3 + 1] = 0.5F * (float)(this.var_5e8[1] - this.var_609[1]);
            this.var_208[var1 * 3 + 2] = 0.5F * (float)(this.var_5e8[2] - this.var_609[2]);
         }

         this.var_208[this.var_41a * 3] = this.var_208[0];
         this.var_208[this.var_41a * 3 + 1] = this.var_208[1];
         this.var_208[this.var_41a * 3 + 2] = this.var_208[2];
         this.var_336[0] = 0.0F;
         this.var_336[this.var_41a - 1] = 0.0F;

         for(var1 = 0; var1 < this.var_41a; ++var1) {
            if (var1 == this.var_41a - 1) {
               this.var_5e8[0] = this.var_7ad[0][7];
            } else {
               this.var_5e8[0] = this.var_7ad[var1 + 1][7];
            }

            if (var1 == 0) {
               this.var_609[0] = this.var_7ad[this.var_7ad.length - 1][7];
            } else {
               this.var_609[0] = this.var_7ad[var1 - 1][7];
            }

            this.var_336[var1] = 0.5F * (float)(this.var_5e8[0] - this.var_609[0]);
         }

         this.var_336[this.var_41a] = this.var_336[0];
      }

   }
}
