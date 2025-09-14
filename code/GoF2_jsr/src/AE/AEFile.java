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

    /**
     * Resizes image using nearest neighbour algorithm 
     * @param src source image
     * @param new_x target width
     * @param new_y target heigh
     * @return resized image
     */
	public static Image resizeImage(Image src, final int new_x, final int new_y) {
		try {
			final float ratio_x = (float)new_x / (float)src.getWidth();
			final float ratio_y = (float)new_y / (float)src.getHeight();
			final int src_x = src.getWidth();
			final int src_y = src.getHeight();
			final int[] srcPix = new int[src_x * src_y];
			final int[] scaledPix = new int[(int)(src_x * ratio_x) * (int)(src_y * ratio_y)];
			src.getRGB(srcPix, 0, src_x, 0, 0, src_x, src_y);
			final int newCols = (int)(src_x * ratio_x);
			final int srcRows = srcPix.length / src_x;
			final int newRows = scaledPix.length / newCols;

			for(int x = 0; x < newCols; ++x) {
				for(int y = 0; y < newRows; ++y) {
					float near_x = (float)x * (float)(src_x - 1) / (newCols - 1);
					int nearest_x;
					if (near_x - Math.floor(near_x) > 0.5D) {
						nearest_x = (int)Math.ceil(near_x);
					} else {
						nearest_x = (int)Math.floor(near_x);
					}

					float near_y = (float)y * (float)(srcRows - 1) / (newRows - 1);
					int nearest_y;
					if (near_y - Math.floor(near_y) > 0.5D) {
						nearest_y = (int)Math.ceil(near_y);
					} else {
						nearest_y = (int)Math.floor(near_y);
					}

					scaledPix[x + y * newCols] = srcPix[nearest_x + nearest_y * src_x];
				}
			}

			src = Image.createRGBImage(scaledPix, (int)(src_x * ratio_x), (int)(src_y * ratio_y), true);
			System.gc();
		} catch (final Exception var16) {
			var16.printStackTrace();
		}

		return src;
	}

	public static Image loadImage(final String path, final boolean encrypted) {
		Image loaded = null;
		if (encrypted) {
			try {
				final InputStream var8 = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(path);
				DataInputStream dis = new DataInputStream(var8);
				int size = dis.available();
				final byte[] decrypted = new byte[size];
				dis.read(decrypted, 0, size);
				dis.close();
				int lenRotated;
				if (size < 100) {
					lenRotated = 10 + size % 10;
				} else if (size < 200) {
					lenRotated = 50 + size % 20;
				} else if (size < 300) {
					lenRotated = 80 + size % 20;
				} else {
					lenRotated = 100 + size % 50;
				}

				for(int i = 0; i < lenRotated; ++i) {
					final byte var5 = decrypted[i];
					decrypted[i] = decrypted[size - i - 1];
					decrypted[size - i - 1] = var5;
				}

				loaded = Image.createImage(decrypted, 0, size);
				System.gc();
			} catch (final Exception var7) {
			}
		} else {
			try {
				loaded = Image.createImage(path);
			} catch (final Exception var6) {
			}
		}

		return loaded;
	}

	public static Image loadCryptedImage(final String path) {
		return loadImage(path, true);
	}

	public static byte[] readFileBytes(final String path) {
		byte[] read = null;

		try {
			final InputStream is = (clazs == null ? (clazs = getClass("java.lang.Class")) : clazs).getResourceAsStream(path);
			DataInputStream dis = new DataInputStream(is);
            int size = dis.available();
			read = new byte[size];
			dis.read(read, 0, size);
			dis.close();
		} catch (final Exception e) {
		}

		return read;
	}

	private static Class getClass(final String className) {
		try {
			return Class.forName(className);
		} catch (final ClassNotFoundException var1) {
			throw new NoClassDefFoundError(var1.getMessage());
		}
	}
}
