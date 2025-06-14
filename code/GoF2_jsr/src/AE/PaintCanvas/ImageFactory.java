package AE.PaintCanvas;

import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import GoF2.Layout;

public final class ImageFactory {
	public static int itemFrameWidth = 31;
	private static int itemFrameHeight = 15;
	private static int itemWidth = 31;
	public static int itemHeight = 15;
	public static int faceWidth = 42;
	public static int faceHeight = 52;
	private static byte[][] faces = {{11, 11, 9, 11, 4}, {5, 4, 6, 10}, {5, 5, 5, 5}, {2, 2, 2, 2}, {2, 2, 3, 3}, {0, 0, 0, 0, 0}, {2, 3, 5, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {1, 1, 1, 1}, {4, 4, 4, 14}, {2, 0, 0, 0}};

	private ImageFactory() {
	}

	public static byte[] createChar(final boolean var0, int var1) {
		if (var1 == 3) {
			var1 = GlobalStatus.random.nextInt(4) == 0 ? 0 : 2;
		}

		if (!var0 && var1 == 0) {
			var1 = 10;
		}

		byte[] var4;
		if (var1 == 0) {
			var4 = new byte[faces[var1].length];
		} else {
			var4 = new byte[faces[var1].length + 1];
		}

		if (var1 == 5) {
			var1 = 0;
		}

		var4[0] = (byte)var1;

		for(int var2 = 1; var2 < var4.length; ++var2) {
			var4[var2] = (byte)GlobalStatus.random.nextInt(faces[var1][var2 - 1]);
		}

		return var4;
	}

	public static Image[] faceFromByteArray(final byte[] face) {
		if (face == null) {
			return null;
		}
		final Image[] faceImgs = new Image[face.length - 1];
		final byte race = face[0];

		for(int i = 1; i < face.length; ++i) {
			if (face[i] >= 0) {
				final int part = i - 1;
				faceImgs[part] = AEFile.loadImage("/data/interface/faces/" + race + "_" + part + "_" + face[i] + ".png", true);
			}
		}

		return faceImgs;
	}

	public static void drawChar(final Image[] var0, final int var1, final int var2, final int var3) {
		GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(var1, var2, faceWidth - 1, faceHeight - 1);

		for(int var4 = 0; var4 < var0.length; ++var4) {
			if (var0[var4] != null) {
				GlobalStatus.graphics.drawImage(var0[var4], var1 + 1, var2 + 1, var3);
			}
		}

	}

	public static void drawItem(final int var0, final int var1, final Image var2, final Image var3, final int var4, final int var5, final int var6) {
		GlobalStatus.graphics.drawRegion(var3, var1 * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, var4, var5, 6);
		GlobalStatus.graphics.drawRegion(var2, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var4, var5, 6);
	}

	public static void drawItemFrameless(final int var0, final Image var1, final int var2, final int var3, final int var4) {
		GlobalStatus.graphics.drawRegion(var1, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var2, var3, var4);
	}

	public static void drawShip(final int var0, int var1, final Image var2, final Image var3, final int var4, final int var5, final int var6) {
		var1 = var1 == 0 ? 0 : var1 == 2 ? 1 : var1 == 8 ? 3 : 2;
		GlobalStatus.graphics.drawRegion(var3, var1 * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, var4, var5, 6);
		GlobalStatus.graphics.drawRegion(var2, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var4, var5, 6);
	}
}
