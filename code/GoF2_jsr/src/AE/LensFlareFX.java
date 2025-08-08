package AE;

import javax.microedition.lcdui.Image;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public final class LensFlareFX {
	private final Image[] flares = new Image[4];
	private final Image mainFlare;

	public LensFlareFX() {
		for(int var1 = 0; var1 < this.flares.length; ++var1) {
			this.flares[var1] = AEFile.loadImage("/data/interface/lens" + var1 + ".png", true);
		}

		final Image var5 = AEFile.loadImage("/data/interface/lens1.png", true);
		final int var10002 = var5.getWidth() / 4;
		final int var3 = var5.getHeight() / 4;
		final int var2 = var10002;
		this.mainFlare = AEFile.resizeImage(AEFile.loadImage("/data/interface/lens1.png", true), var2, var3);
	}

	public final void render2D(final AEVector3D var1) {
		int var2 = var1.x;
		int var3 = var1.y;
		if (var1.z < 0 && var2 > -GlobalStatus.screenWidth && var2 < GlobalStatus.screenWidth << 1 && var3 > -GlobalStatus.screenHeight && var3 < GlobalStatus.screenHeight << 1) {
			final int var7 = GlobalStatus.screenWidth >> 1;
			final int var4 = GlobalStatus.screenHeight >> 1;
			var2 = var7 - var2;
			var3 = var4 - var3;
			final int var5 = AEMath.sqrt(var2 * var2 + var3 * var3);
			var2 = -(var2 << AEMath.Q);
			var3 = -(var3 << AEMath.Q);
			if (var5 > 0) {
				var2 /= var5;
				var3 /= var5;
			}

			if (this.flares != null) {
				for(int var6 = 0; var6 < this.flares.length; ++var6) {
					switch(var6) {
					case 0:
						if (var5 > 2000) {
							GlobalStatus.graphics.drawImage(this.flares[var6], var7 + (var2 * var5 / 2 >> AEMath.Q), var4 + (var3 * var5 / 2 >> AEMath.Q), 3);
							if (this.mainFlare != null) {
								GlobalStatus.graphics.drawImage(this.mainFlare, var7 + (var2 * var5 / 3 >> AEMath.Q), var4 + (var3 * var5 / 3 >> AEMath.Q), 3);
							}
						}
						break;
					case 1:
						if (var5 > 3000) {
							GlobalStatus.graphics.drawImage(this.flares[var6], var7 + (var2 * var5 / 8 >> AEMath.Q), var4 + (var3 * var5 / 8 >> AEMath.Q), 3);
							if (this.mainFlare != null) {
								GlobalStatus.graphics.drawImage(this.mainFlare, var7 - (var2 * var5 / 2 >> AEMath.Q), var4 - (var3 * var5 / 2 >> AEMath.Q), 3);
							}
						}
						break;
					case 2:
						if (var5 > 4000) {
							GlobalStatus.graphics.drawImage(this.flares[var6], var7 - (var2 * var5 / 4 >> AEMath.Q), var4 - (var3 * var5 / 4 >> AEMath.Q), 3);
							if (this.mainFlare != null) {
								GlobalStatus.graphics.drawImage(this.mainFlare, var7 - (var2 * var5 / 6 >> AEMath.Q), var4 - (var3 * var5 / 6 >> AEMath.Q), 3);
							}
						}
						break;
					case 3:
						if (var5 > 5000) {
							GlobalStatus.graphics.drawImage(this.flares[var6], var7 - (var2 * var5 / 7 >> AEMath.Q), var4 - (var3 * var5 / 7 >> AEMath.Q), 3);
							if (this.mainFlare != null) {
								GlobalStatus.graphics.drawImage(this.mainFlare, var7 - (var2 * var5 / 10 >> AEMath.Q), var4 - (var3 * var5 / 10 >> AEMath.Q), 3);
							}
						}
					}
				}
			}
		}

	}
}
