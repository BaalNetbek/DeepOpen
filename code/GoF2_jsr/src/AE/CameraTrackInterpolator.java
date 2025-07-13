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
	private final int[] currentInterpolationStartPoint = new int[3];
	private final int[] currentInterpolationEndPoint = new int[3];
	public AEVector3D currentPos = new AEVector3D();
	public AEVector3D currentRot = new AEVector3D();
	public int currentFoV = 0;
	private int[][] keyFrames;
	private final long totalTrackLength;

	public CameraTrackInterpolator(final int[][] var1, final int var2, final long var3) {
		this.totalTrackLength = var3;
		if (var1 != null && var1[var2] != null) {
			this.keyFrames = new int[var1[var2].length / 8][];

			for(int i = 0; i < var1[var2].length / 8; ++i) {
				this.keyFrames[i] = new int[8];
				System.arraycopy(var1[var2], i << 3, this.keyFrames[i], 0, 8);
			}

			if (this.keyFrames != null) {
				this.keyframeCnt = this.keyFrames.length;
				this.interpolatedPos = new float[(this.keyframeCnt + 1) * 3];
				this.interpolatedRot = new float[(this.keyframeCnt + 1) * 3];
				this.interpolatedFoV = new float[this.keyframeCnt + 1];
				this.segmentLengths = new float[(this.keyframeCnt + 1) * 3];
				this.segmentLengthsNormalized = new float[(this.keyframeCnt + 1) * 3];
				this.tempPos = new float[3];
				computeInterpolatedValues();
				computeLengths();
			}
		}

	}

	public final void update(long var1) {
		if (this.keyframeCnt >= 2) {
			if (var1 > this.totalTrackLength) {
				var1 %= this.totalTrackLength;
			}

			if (this.keyframeCnt == 2) {
				interpolate((float)var1 / (float)this.keyFrames[1][0], 0);
			} else {
				for(int var3 = this.keyframeCnt - 1; var3 >= 0; --var3) {
					if (this.keyFrames[var3][0] < var1) {
						float var4;
						if (var3 != 0 && var3 == this.keyframeCnt - 1) {
							var4 = (float)(var1 - this.keyFrames[var3][0]) / (float)(this.totalTrackLength - this.keyFrames[var3][0]);
						} else {
							var4 = (float)(var1 - this.keyFrames[var3][0]) / (float)(this.keyFrames[var3 + 1][0] - this.keyFrames[var3][0]);
						}

						interpolate(var4, var3);
						return;
					}
				}

			}
		}
	}

	private void interpolate(float var1, final int var2) {
		float var3;
		final float var4 = (var3 = var1 * var1) * var1;
		final float var5 = var4 * 2.0F - 3.0F * var3 + 1.0F;
		var1 += var4 - var3 * 2.0F;
		final float var6 = -2.0F * var4 + 3.0F * var3;
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

		this.currentPos.x = (int)(var5 * this.currentInterpolationStartPoint[0] + var1 * this.interpolatedPos[var2 * 3] + var6 * this.currentInterpolationEndPoint[0] + var3 * this.interpolatedPos[(var2 + 1) * 3]);
		this.currentPos.y = (int)(var5 * this.currentInterpolationStartPoint[1] + var1 * this.interpolatedPos[var2 * 3 + 1] + var6 * this.currentInterpolationEndPoint[1] + var3 * this.interpolatedPos[(var2 + 1) * 3 + 1]);
		this.currentPos.z = (int)(var5 * this.currentInterpolationStartPoint[2] + var1 * this.interpolatedPos[var2 * 3 + 2] + var6 * this.currentInterpolationEndPoint[2] + var3 * this.interpolatedPos[(var2 + 1) * 3 + 2]);
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

		this.currentRot.x = (int)(var5 * this.currentInterpolationStartPoint[0] + var1 * this.interpolatedRot[var2 * 3] + var6 * this.currentInterpolationEndPoint[0] + var3 * this.interpolatedRot[(var2 + 1) * 3]);
		this.currentRot.y = (int)(var5 * this.currentInterpolationStartPoint[1] + var1 * this.interpolatedRot[var2 * 3 + 1] + var6 * this.currentInterpolationEndPoint[1] + var3 * this.interpolatedRot[(var2 + 1) * 3 + 1]);
		this.currentRot.z = (int)(var5 * this.currentInterpolationStartPoint[2] + var1 * this.interpolatedRot[var2 * 3 + 2] + var6 * this.currentInterpolationEndPoint[2] + var3 * this.interpolatedRot[(var2 + 1) * 3 + 2]);
		this.currentInterpolationStartPoint[0] = this.keyFrames[var2][7];
		if (var2 == this.keyFrames.length - 1) {
			this.currentInterpolationEndPoint[0] = this.keyFrames[0][7];
		} else {
			this.currentInterpolationEndPoint[0] = this.keyFrames[var2 + 1][7];
		}

		this.currentFoV = (int)(var5 * this.currentInterpolationStartPoint[0] + var1 * this.interpolatedFoV[var2] + var6 * this.currentInterpolationEndPoint[0] + var3 * this.interpolatedFoV[var2 + 1]);
	}

	private void computeLengths() {
		if (this.keyframeCnt <= 1) {
			this.totalPathLength = 0.0F;
		} else {
			this.totalPathLength = 0.0F;

			for(int i = 0; i < this.keyframeCnt; ++i) {
				int var2 = 1;
				float var4 = 0.0F;
				for(float var3 = 1.0F; var3 > 0.005D; var2 <<= 1) {
					var3 = 1.0F / var2;
					this.tempPos[0] = this.keyFrames[i][1];
					this.tempPos[1] = this.keyFrames[i][2];
					this.tempPos[2] = this.keyFrames[i][3];
					this.segmentLengths[i] = 0.0F;

					for(int j = 1; j <= var2; ++j) {
						final float var6 = j * var3;
						interpolate(var6, i);
						this.segmentLengths[i] = (float)(this.segmentLengths[i] + Math.sqrt((this.currentPos.x - this.tempPos[0]) * (this.currentPos.x - this.tempPos[0]) + (this.currentPos.y - this.tempPos[1]) * (this.currentPos.y - this.tempPos[1]) + (this.currentPos.z - this.tempPos[2]) * (this.currentPos.z - this.tempPos[2])));
						this.tempPos[0] = this.currentPos.x;
						this.tempPos[1] = this.currentPos.y;
						this.tempPos[2] = this.currentPos.z;
					}

					var3 = (this.segmentLengths[i] - var4) / this.segmentLengths[i];
					var4 = this.segmentLengths[i];
				}

				this.totalPathLength += this.segmentLengths[i];
			}

			for(int i = 0; i < this.keyframeCnt; ++i) {
				if (i == 0) {
					this.segmentLengthsNormalized[i] = this.segmentLengths[i] / this.totalPathLength;
				} else {
					this.segmentLengthsNormalized[i] = this.segmentLengthsNormalized[i - 1] + this.segmentLengths[i] / this.totalPathLength;
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

			for(int i = 0; i < this.keyframeCnt; ++i) {
				if (i == this.keyframeCnt - 1) {
					this.currentInterpolationStartPoint[0] = this.keyFrames[0][1];
					this.currentInterpolationStartPoint[1] = this.keyFrames[0][2];
					this.currentInterpolationStartPoint[2] = this.keyFrames[0][3];
				} else {
					this.currentInterpolationStartPoint[0] = this.keyFrames[i + 1][1];
					this.currentInterpolationStartPoint[1] = this.keyFrames[i + 1][2];
					this.currentInterpolationStartPoint[2] = this.keyFrames[i + 1][3];
				}

				if (i == 0) {
					this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][1];
					this.currentInterpolationEndPoint[1] = this.keyFrames[this.keyFrames.length - 1][2];
					this.currentInterpolationEndPoint[2] = this.keyFrames[this.keyFrames.length - 1][3];
				} else {
					this.currentInterpolationEndPoint[0] = this.keyFrames[i - 1][1];
					this.currentInterpolationEndPoint[1] = this.keyFrames[i - 1][2];
					this.currentInterpolationEndPoint[2] = this.keyFrames[i - 1][3];
				}

				this.interpolatedPos[i * 3] = 0.5F * (this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
				this.interpolatedPos[i * 3 + 1] = 0.5F * (this.currentInterpolationStartPoint[1] - this.currentInterpolationEndPoint[1]);
				this.interpolatedPos[i * 3 + 2] = 0.5F * (this.currentInterpolationStartPoint[2] - this.currentInterpolationEndPoint[2]);
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

			for(int i = 0; i < this.keyframeCnt; ++i) {
				if (i == this.keyframeCnt - 1) {
					this.currentInterpolationStartPoint[0] = this.keyFrames[0][4];
					this.currentInterpolationStartPoint[1] = this.keyFrames[0][5];
					this.currentInterpolationStartPoint[2] = this.keyFrames[0][6];
				} else {
					this.currentInterpolationStartPoint[0] = this.keyFrames[i + 1][4];
					this.currentInterpolationStartPoint[1] = this.keyFrames[i + 1][5];
					this.currentInterpolationStartPoint[2] = this.keyFrames[i + 1][6];
				}

				if (i == 0) {
					this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][4];
					this.currentInterpolationEndPoint[1] = this.keyFrames[this.keyFrames.length - 1][5];
					this.currentInterpolationEndPoint[2] = this.keyFrames[this.keyFrames.length - 1][6];
				} else {
					this.currentInterpolationEndPoint[0] = this.keyFrames[i - 1][4];
					this.currentInterpolationEndPoint[1] = this.keyFrames[i - 1][5];
					this.currentInterpolationEndPoint[2] = this.keyFrames[i - 1][6];
				}

				this.interpolatedRot[i * 3] = 0.5F * (this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
				this.interpolatedRot[i * 3 + 1] = 0.5F * (this.currentInterpolationStartPoint[1] - this.currentInterpolationEndPoint[1]);
				this.interpolatedRot[i * 3 + 2] = 0.5F * (this.currentInterpolationStartPoint[2] - this.currentInterpolationEndPoint[2]);
			}

			this.interpolatedRot[this.keyframeCnt * 3] = this.interpolatedRot[0];
			this.interpolatedRot[this.keyframeCnt * 3 + 1] = this.interpolatedRot[1];
			this.interpolatedRot[this.keyframeCnt * 3 + 2] = this.interpolatedRot[2];
			this.interpolatedFoV[0] = 0.0F;
			this.interpolatedFoV[this.keyframeCnt - 1] = 0.0F;

			for(int i = 0; i < this.keyframeCnt; ++i) {
				if (i == this.keyframeCnt - 1) {
					this.currentInterpolationStartPoint[0] = this.keyFrames[0][7];
				} else {
					this.currentInterpolationStartPoint[0] = this.keyFrames[i + 1][7];
				}

				if (i == 0) {
					this.currentInterpolationEndPoint[0] = this.keyFrames[this.keyFrames.length - 1][7];
				} else {
					this.currentInterpolationEndPoint[0] = this.keyFrames[i - 1][7];
				}

				this.interpolatedFoV[i] = 0.5F * (this.currentInterpolationStartPoint[0] - this.currentInterpolationEndPoint[0]);
			}

			this.interpolatedFoV[this.keyframeCnt] = this.interpolatedFoV[0];
		}

	}
}
