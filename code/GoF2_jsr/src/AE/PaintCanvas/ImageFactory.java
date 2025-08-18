package AE.PaintCanvas;

import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import GOF2.Globals;
import GOF2.Layout;

public final class ImageFactory {
	public static int itemFrameWidth = 31;
	private static int itemFrameHeight = 15;
	private static int itemWidth = 31;
	public static int itemHeight = 15;
	public static int faceWidth = 42;
	public static int faceHeight = 52;
	private static byte[][] faces = {
	      {11, 11, 9, 11, 4},
	      {5, 4, 6, 10},
	      {5, 5, 5, 5},
	      {2, 2, 2, 2},
	      {2, 2, 3, 3},
	      {0, 0, 0, 0, 0},
	      {2, 3, 5, 1},
	      {2, 2, 2, 2},
	      {1, 1, 1, 1},
	      {1, 1, 1, 1},
	      {4, 4, 4, 14},
	      {2, 0, 0, 0}};

	private ImageFactory() {
	}

	public static byte[] createChar(final boolean var0, int var1) {
		if (var1 == Globals.MIDORIAN) {
			var1 = GlobalStatus.random.nextInt(4) == 0 ? Globals.TERRAN : Globals.NIVELIAN;
		}

		if (!var0 && var1 == Globals.TERRAN) {
			var1 = Globals.WOMAN;
		}

		byte[] var4;
		if (var1 == Globals.TERRAN) {
			var4 = new byte[faces[var1].length];
		} else {
			var4 = new byte[faces[var1].length + 1];
		}

		if (var1 == Globals.CYBORG) {
			var1 = 0;
		}

		var4[0] = (byte)var1;

		for(int i = 1; i < var4.length; ++i) {
			var4[i] = (byte)GlobalStatus.random.nextInt(faces[var1][i - 1]);
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

	public static void drawChar(final Image[] var0, final int var1, final int var2, final int anchor) {
		GlobalStatus.graphics.setColor(Layout.uiInnerOutlineColor);
		GlobalStatus.graphics.drawRect(var1, var2, faceWidth - 1, faceHeight - 1);

		for(int i = 0; i < var0.length; ++i) {
			if (var0[i] != null) {
				GlobalStatus.graphics.drawImage(var0[i], var1 + 1, var2 + 1, anchor);
			}
		}

	}

	public static void drawItem(final int idx, final int type, final Image items, final Image frames, final int x, final int y, final int anchor) {
		GlobalStatus.graphics.drawRegion(frames, type * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, x, y, anchor);
		GlobalStatus.graphics.drawRegion(items, idx * itemWidth, 0, itemWidth, itemHeight, 0, x, y, anchor);
	}

	public static void drawItemFrameless(final int idx, final Image items, final int x, final int y, final int anchor) {
		GlobalStatus.graphics.drawRegion(items, idx * itemWidth, 0, itemWidth, itemHeight, 0, x, y, anchor);
	}

	public static void drawShip(final int var0, int var1, final Image var2, final Image var3, final int var4, final int var5, final int anchor) {
		var1 = var1 == 0 ? 0 : var1 == 2 ? 1 : var1 == 8 ? 3 : 2;
		GlobalStatus.graphics.drawRegion(var3, var1 * itemFrameWidth, 0, itemFrameWidth, itemFrameHeight, 0, var4, var5, anchor);
		GlobalStatus.graphics.drawRegion(var2, var0 * itemWidth, 0, itemWidth, itemHeight, 0, var4, var5, anchor);
	}
}
