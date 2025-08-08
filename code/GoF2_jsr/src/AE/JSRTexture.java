package AE;

import javax.microedition.lcdui.Image;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.Texture2D;

public final class JSRTexture extends ITexture {
	private Texture2D[] textures;

	public JSRTexture(final JSRTexture var1) {
		if (var1 != null) {
			this.textures = new Texture2D[1];

			try {
				final Image2D var3 = var1.textures[0].getImage();
				this.textures[0] = new Texture2D(var3);
				this.textures[0].setBlending(228);
				this.textures[0].setWrapping(241, 241);
			} catch (final Exception var2) {
				this.textures = null;
			}
		}

	}

	public JSRTexture(final String[] var1) {
		if (var1 != null) {
			this.textures = new Texture2D[var1.length];

			for(int i = 0; i < var1.length; ++i) {
				try {
					final Image var3 = AEFile.loadImage(var1[i] + ".png", false);
					final Image2D var5 = new Image2D(100, var3);
					this.textures[i] = new Texture2D(var5);
					this.textures[i].setBlending(227);
					this.textures[i].setWrapping(241, 241);
				} catch (final Exception var4) {
					this.textures = null;
				}
			}
		}

	}

	public final Texture2D[] getTexturesArray() {
		return this.textures;
	}

	public final void OnRelease() {
		if (this.textures != null) {
			for(int i = 0; i < this.textures.length; ++i) {
				this.textures[i] = null;
			}

			this.textures = null;
		}

	}
}
