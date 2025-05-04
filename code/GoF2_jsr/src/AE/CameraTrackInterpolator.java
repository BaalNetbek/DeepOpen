package AE;

import AE.Math.AEVector3D;

public final class CameraTrackInterpolator {
   private float[] interpolatedPos;
   private float[] interpolatedRot;
   private float[] interpolatedFoV;
   private int keyframeCnt = 0;
   private float totalPathLength;
   private float[] segmentLengths;
   private float[] segmentLengthsNormalized;
   private float[] tempPos;
   private int[] currentInterpolationStartPoint = new int[3];
   private int[] currentInterpolationEndPoint = new int[3];
   public AEVector3D currentPos = new AEVector3D();
   public AEVector3D currentRot = new AEVector3D();
   public int currentFoV = 0;
   private int[][] keyFrames;
   private long totalTrackLenght;

   public CameraTrackInterpolator(int[][] var1, int var2, long var3) {
      this.totalTrackLenght = var3;
      if (var1 != null && var1[var2] != null) {
         this.keyFrames = new int[var1[var2].length / 8][];

         for(int var5 = 0; var5 < var1[var2].length / 8; ++var5) {
            this.keyFrames[var5] = new int[8];
            System.arraycopy(var1[var2], var5 << 3, this.keyFrames[var5], 0, 8);
         }

         if (this.keyFrames != null) {
            this.keyframeCnt = this.keyFrames.length;
            this.interpolatedPos = new float[(this.keyframeCnt + 1) * 3];
            this.interpolatedRot = new float[(this.keyframeCnt + 1) * 3];
            this.interpolatedFoV = new float[this.keyframeCnt + 1];
            this.segmentLengths = new float[(this.keyframeCnt + 1) * 3];
            this.segmentLengthsNormalized = new float[(this.keyframeCnt + 1) * 3];
            this.tempPos = new float[3];
            this.computeInterpolatedValues();
            this.computeLengths();
         }
      }

   }

   public final void update(long var1) {
      if (this.keyframeCnt >= 2) {
         if (var1 > this.totalTrackLenght) {
            var1 %= this.totalTrackLenght;
         }

         if (this.keyframeCnt == 2) {
            this.interpolate((float)var1 / (float)this.keyFrames[1][0], 0);
         } else {
            for(int var3 = this.keyframeCnt - 1; var3 >= 0; --var3) {
               if ((long)this.keyFrames[var3][0] < var1) {
                  float var4;
                  if (var3 != 0 && var3 == this.keyframeCnt - 1) {
                     var4 = (float)(var1 - (long)this.keyFrames[var3][0]) / (float)(this.totalTrackLenght - (long)this.keyFrames[var3][0]);
                  } else {
                     var4 = (float)(var1 - (long)this.keyFrames[var3][0]) / (float)(this.keyFrames[var3 + 1][0] - this.keyFrames[var3][0]);
                  }

                  this.interpolate(var4, var3);
                  return;
               }
            }

         }
      }
   }

   private void interpolate(float var1, int var2) {
      float var3;
      float var4 = (var3 = var1 * var1) * var1;
      float var5 = var4 * 2.0F - 3.0F * var3 + 1.0F;
      var1 += var4 - var3 * 2.0F;
      float var6 = -2.0F * var4 + 3.0F * var3;
      var3 = var4 - var3;
      this.currentInterpolationStartPoint[0] = this.keyFrames[var2][1];
      this.currentInterpolationStartPoint[1] = this.keyFrames[var2][2];
      this.currentInterpolationStartPoint[2] = this.keyFrames[var2][3];
      if (var2 == this.keyFrames.length - 1) {
         this.currentInterpolationEndPoint[0] = this.keyFrames[0][1];
         this.currentInterpolationEndPoint[1] = this.keyFrames[0][2];
         this.currentInterpolationEndPoint[2] = this.keyFrames[0][3];
      } else {
         this.currentInterpolationEndPoint[0] = this.keyFrames[var2 + 1][1];
         this.currentInterpolationEndPoint[1] = this.keyFrames[var2 + 1][2];
         this.currentInterpolationEndPoint[2] = this.keyFrames[var2 + 1][3];
      }

      this.currentPos.x = (int)(var5 * (float)this.currentInterpolationStartPoint[0] + var1 * this.interpolatedPos[var2 * 3] + var6 * (float)this.currentInterpolationEndPoint[0] + var3 * this.interpolatedPos[(var2 + 1) * 3]);
      this.currentPos.y = (int)(var5 * (float)this.currentInterpolationStartPoint[1] + var1 * this.interpolatedPos[var2 * 3 + 1] + var6 * (float)this.currentInterpolationEndPoint[1] + var3 * this.interpolatedPos[(var2 + 1) * 3 + 1]);
      this.currentPos.z = (int)(var5 * (float)this.currentInterpolationStartPoint[2] + var1 * this.interpolatedPos[var2 * 3 + 2] + var6 * (float)this.currentInterpolationEndPoint[2] + var3 * this.interpolatedPos[(var2 + 1) * 3 + 2]);
      this.currentInterpolationStartPoint[0] = this.keyFrames[var2][4];
      this.currentInterpolationStartPoint[1] = this.keyFrames[var2][5];
      this.currentInterpolationStartPoint[2] = this.keyFrames[var2][6];
      if (var2 == this.keyFrames.length - 1) {
         this.currentInterpolationEndPoint[0] = this.keyFrames[0][4];
         this.currentInterpolationEndPoint[1] = this.keyFrames[0][5];
         this.currentInterpolationEndPoint[2] = this.keyFrames[0][6];
      } else {
         this.currentInterpolationEndPoint[0] = this.keyFrames[var2 + 1][4];
         this.currentInterpolationEndPoint[1] = this.keyFrames[var2 + 1][5];
         this.currentInterpolationEndPoint[2] = this.keyFrames[var2 + 1][6];
      }

      this.currentRot.x = (int)(var5 * (float)this.currentInterpolationStartPoint[0] + var1 * this.interpolatedRot[var2 * 3] + var6 * (float)this.currentInterpolationEndPoint[0] + var3 * this.interpolatedRot[(var2 + 1) * 3]);
      this.currentRot.y = (int)(var5 * (float)this.currentInterpolationStartPoint[1] + var1 * this.interpolatedRot[var2 * 3 + 1] + var6 * (float)this.currentInterpolationEndPoint[1] + var3 * this.interpolatedRot[(var2 + 1) * 3 + 1]);
      this.currentRot.z = (int)(var5 * (float)this.currentInterpolationStartPoint[2] + var1 * this.interpolatedRot[var2 * 3 + 2] + var6 * (float)this.currentInterpolationEndPoint[2] + var3 * this.interpolatedRot[(var2 + 1) * 3 + 2]);
      this.currentInterpolationStartPoint[0] = this.keyFrames[var2][7];
      if (var2 == this.keyFrames.length - 1) {
         this.currentInterpolationEndPoint[0] = this.keyFrames[0][7];
      } else {
         this.currentInterpolationEndPoint[0] = this.keyFrames[var2 + 1][7];
      }

      this.currentFoV = (int)(var5 * (float)this.currentInterpolationStartPoint[0] + var1 * this.interpolatedFoV[var2] + var6 * (float)this.currentInterpolationEndPoint[0] + var3 * this.interpolatedFoV[var2 + 1]);
   }

   private void computeLengths() {
      if (this.keyframeCnt <= 1) {
         this.totalPathLength = 0.0F;
      } else {
         this.totalPathLength = 0.0F;

         int var1;
         for(var1 = 0; var1 < this.keyframeCnt; ++var1) {
            int var2 = 1;
            float var3 = 1.0F;

            for(float var4 = 0.0F; (double)var3 > 0.005D; var2 <<= 1) {
               var3 = 1.0F / (float)var2;
               this.tempPos[0] = (float)this.keyFrames[var1][1];
               this.tempPos[1] = (float)this.keyFrames[var1][2];
               this.tempPos[2] = (float)this.keyFrames[var1][3];
               this.segmentLengths[var1] = 0.0F;

               for(int var5 = 1; var5 <= var2; ++var5) {
                  float var6 = (float)var5 * var3;
                  this.interpolate(var6, var1);
                  float[] var10000 = this.segmentLengths;
                  var10000[var1] = (float)((double)var10000[var1] + Math.sqrt((double)(((float)this.currentPos.x - this.tempPos[0]) * ((float)this.currentPos.x - this.tempPos[0]) + ((float)this.currentPos.y - this.tempPos[1]) * ((float)this.currentPos.y - this.tempPos[1]) + ((float)this.currentPos.z - this.tempPos[2]) * ((float)this.currentPos.z - this.tempPos[2]))));
                  this.tempPos[0] = (float)this.currentPos.x;
                  this.tempPos[1] = (float)this.currentPos.y;
                  this.tempPos[2] = (float)this.currentPos.z;
               }

               var3 = (this.segmentLengths[var1] - var4) / this.segmentLengths[var1];
               var4 = this.segmentLengths[var1];
            }

            this.totalPathLength += this.segmentLengths[var1];
         }

         for(var1 = 0; var1 < this.keyframeCnt; ++var1) {
            if (var1 == 0) {
               this.segmentLengthsNormalized[var1] = this.segmentLengths[var1] / this.totalPathLength;
            } else {
               this.segmentLengthsNormalized[var1] = this.segmentLengthsNormalized[var1 - 1] + this.segmentLengths[var1] / this.totalPathLength;
            }
         }

      }
   }

   private void computeInterpolatedValues() {
      if (this.keyframeCnt > 1) {
         this.interpolatedPos[0] = 0.0F;
         this.interpolatedPos[1] = 0.0F;
         this.interpolatedPos[2] = 0.0F;
         this.interpolatedPos[(this.keyframeCnt - 1) * 3] = 0.0F;
         this.interpolatedPos[(this.keyframeCnt - 1) * 3 + 1] = 0.0F;
         this.interpolatedPos[(this.keyframeCnt - 1) * 3 + 2] = 0.0F;

         int var1;
         for(var1 = 0; var1 < this.keyframeCnt; ++var1) {
            if (var1 == this.keyframeCnt - 1) {
               this.currentInterpolationStartPoint[0] = this.keyFrames[0][1];
               this.currentInterpolationStartPoint[1] = this.keyFrames[0][2];
               this.currentInterpolationStartPoint[2] = this.keyFrames[0][3];
            } else {
               this.currentInterpolationStartPoint[0] = this.keyFrames[var1 + 1][1];
               this.currentInterpolationStartPoint[1] = this.keyFrames[var1 + 1][2];
               this.currentInterpolationStartPoint[2] = this.keyFrames[var1 + 1][3];
            }

            if (var1 == 0) {
               this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][1];
               this.currentInterpolationEndPoint[1] = this.keyFrames[this.keyFrames.length - 1][2];
               this.currentInterpolationEndPoint[2] = this.keyFrames[this.keyFrames.length - 1][3];
            } else {
               this.currentInterpolationEndPoint[0] = this.keyFrames[var1 - 1][1];
               this.currentInterpolationEndPoint[1] = this.keyFrames[var1 - 1][2];
               this.currentInterpolationEndPoint[2] = this.keyFrames[var1 - 1][3];
            }

            this.interpolatedPos[var1 * 3] = 0.5F * (float)(this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
            this.interpolatedPos[var1 * 3 + 1] = 0.5F * (float)(this.currentInterpolationStartPoint[1] - this.currentInterpolationEndPoint[1]);
            this.interpolatedPos[var1 * 3 + 2] = 0.5F * (float)(this.currentInterpolationStartPoint[2] - this.currentInterpolationEndPoint[2]);
         }

         this.interpolatedPos[this.keyframeCnt * 3] = this.interpolatedPos[0];
         this.interpolatedPos[this.keyframeCnt * 3 + 1] = this.interpolatedPos[1];
         this.interpolatedPos[this.keyframeCnt * 3 + 2] = this.interpolatedPos[2];
         this.interpolatedRot[0] = 0.0F;
         this.interpolatedRot[1] = 0.0F;
         this.interpolatedRot[2] = 0.0F;
         this.interpolatedRot[(this.keyframeCnt - 1) * 3] = 0.0F;
         this.interpolatedRot[(this.keyframeCnt - 1) * 3 + 1] = 0.0F;
         this.interpolatedRot[(this.keyframeCnt - 1) * 3 + 2] = 0.0F;

         for(var1 = 0; var1 < this.keyframeCnt; ++var1) {
            if (var1 == this.keyframeCnt - 1) {
               this.currentInterpolationStartPoint[0] = this.keyFrames[0][4];
               this.currentInterpolationStartPoint[1] = this.keyFrames[0][5];
               this.currentInterpolationStartPoint[2] = this.keyFrames[0][6];
            } else {
               this.currentInterpolationStartPoint[0] = this.keyFrames[var1 + 1][4];
               this.currentInterpolationStartPoint[1] = this.keyFrames[var1 + 1][5];
               this.currentInterpolationStartPoint[2] = this.keyFrames[var1 + 1][6];
            }

            if (var1 == 0) {
               this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][4];
               this.currentInterpolationEndPoint[1] = this.keyFrames[this.keyFrames.length - 1][5];
               this.currentInterpolationEndPoint[2] = this.keyFrames[this.keyFrames.length - 1][6];
            } else {
               this.currentInterpolationEndPoint[0] = this.keyFrames[var1 - 1][4];
               this.currentInterpolationEndPoint[1] = this.keyFrames[var1 - 1][5];
               this.currentInterpolationEndPoint[2] = this.keyFrames[var1 - 1][6];
            }

            this.interpolatedRot[var1 * 3] = 0.5F * (float)(this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
            this.interpolatedRot[var1 * 3 + 1] = 0.5F * (float)(this.currentInterpolationStartPoint[1] - this.currentInterpolationEndPoint[1]);
            this.interpolatedRot[var1 * 3 + 2] = 0.5F * (float)(this.currentInterpolationStartPoint[2] - this.currentInterpolationEndPoint[2]);
         }

         this.interpolatedRot[this.keyframeCnt * 3] = this.interpolatedRot[0];
         this.interpolatedRot[this.keyframeCnt * 3 + 1] = this.interpolatedRot[1];
         this.interpolatedRot[this.keyframeCnt * 3 + 2] = this.interpolatedRot[2];
         this.interpolatedFoV[0] = 0.0F;
         this.interpolatedFoV[this.keyframeCnt - 1] = 0.0F;

         for(var1 = 0; var1 < this.keyframeCnt; ++var1) {
            if (var1 == this.keyframeCnt - 1) {
               this.currentInterpolationStartPoint[0] = this.keyFrames[0][7];
            } else {
               this.currentInterpolationStartPoint[0] = this.keyFrames[var1 + 1][7];
            }

            if (var1 == 0) {
               this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][7];
            } else {
               this.currentInterpolationEndPoint[0] = this.keyFrames[var1 - 1][7];
            }

            this.interpolatedFoV[var1] = 0.5F * (float)(this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
         }

         this.interpolatedFoV[this.keyframeCnt] = this.interpolatedFoV[0];
      }

   }
}
