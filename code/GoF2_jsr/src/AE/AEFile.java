package AE;

import java.io.DataInputStream;
import java.io.InputStream;

import javax.microedition.lcdui.Image;
/**
 * Class with static methods abstracting access to files, especially images.
 *
 * @author fishlabs
 *
 */
public final class AEFile {
	private static Class clazs;

	public static Image resizeImage(Image var0, final int var1, final int var2) {
		try {
			final float var17 = (float)var1 / (float)var0.getWidth();
			final float var18 = (float)var2 / (float)var0.getHeight();
			final int var3 = var0.getWidth();
			final int var4 = var0.getHeight();
			final int[] var5 = new int[var3 * var4];
			final int[] var6 = new int[(int)(var3 * var17) * (int)(var4 * var18)];
			var0.getRGB(var5, 0, var3, 0, 0, var3, var4);
			final int var8 = (int)(var3 * var17);
			final int var7 = var3;
			final int var9 = var5.length / var3;
			final int var10 = var6.length / var8;

			for(int i = 0; i < var8; ++i) {
				for(int j = 0; j < var10; ++j) {
					float var13 = (float)i * (float)(var7 - 1) / (var8 - 1);
					int var19;
					if (var13 - Math.floor(var13) > 0.5D) {
						var19 = (int)Math.ceil(var13);
					} else {
						var19 = (int)Math.floor(var13);
					}

					var13 = (float)j * (float)(var9 - 1) / (var10 - 1);
					int var20;
					if (var13 - Math.floor(var13) > 0.5D) {
						var20 = (int)Math.ceil(var13);
					} else {
						var20 = (int)Math.floor(var13);
					}

					var6[i + j * var8] = var5[var19 + var20 * var7];
				}
			}

			var0 = Image.createRGBImage(var6, (int)(var3 * var17), (int)(var4 * var18), true);
			System.gc();
		} catch (final Exception var16) {
			var16.printStackTrace();
		}

		return var0;
	}

	public static Image loadImage(final String var0, final boolean var1) {
		Image var2 = null;
		if (var1) {
			try {
				final InputStream var8 = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(var0);
				DataInputStream var9;
				int var11;
				final byte[] var3 = new byte[var11 = (var9 = new DataInputStream(var8)).available()];
				var9.read(var3, 0, var11);
				var9.close();
				int var10;
				if (var11 < 100) {
					var10 = 10 + var11 % 10;
				} else if (var11 < 200) {
					var10 = 50 + var11 % 20;
				} else if (var11 < 300) {
					var10 = 80 + var11 % 20;
				} else {
					var10 = 100 + var11 % 50;
				}

				for(int i = 0; i < var10; ++i) {
					final byte var5 = var3[i];
					var3[i] = var3[var11 - i - 1];
					var3[var11 - i - 1] = var5;
				}

				var2 = Image.createImage(var3, 0, var11);
				System.gc();
			} catch (final Exception var7) {
			}
		} else {
			try {
				var2 = Image.createImage(var0);
			} catch (final Exception var6) {
			}
		}

		return var2;
	}

	public static Image loadCryptedImage(final String var0) {
		return loadImage(var0, true);
	}

	public static byte[] readFileBytes(final String var0) {
		byte[] var1 = null;

		try {
			final InputStream var4 = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(var0);
			int var2;
			DataInputStream var5;
			var1 = new byte[var2 = (var5 = new DataInputStream(var4)).available()];
			var5.read(var1, 0, var2);
			var5.close();
		} catch (final Exception var3) {
		}

		return var1;
	}

	private static Class getClass(final String var0) {
		try {
			return Class.forName(var0);
		} catch (final ClassNotFoundException var1) {
			throw new NoClassDefFoundError(var1.getMessage());
		}
	}
}
