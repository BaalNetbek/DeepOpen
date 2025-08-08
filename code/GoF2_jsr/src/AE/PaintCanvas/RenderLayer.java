package AE.PaintCanvas;

import AE.AbstractMesh;

/**
 * Container for render layers. Used in Renderer to manage rendering order.
 * Layers are being rendered on top of previous which helps managing depth illusion.
 * @author fishlabs
 *
 */
public final class RenderLayer {
	private AbstractMesh[] meshes = null;
	private int size = 0;

	public final void appendNode(final AbstractMesh var1) {
		if (this.meshes == null) {
			this.meshes = new AbstractMesh[1];
			this.meshes[0] = var1;
			this.size = 1;
		} else {
			if (this.size == this.meshes.length) {
				final AbstractMesh[] var2 = new AbstractMesh[this.meshes.length + 1];
				System.arraycopy(this.meshes, 0, var2, 0, this.meshes.length);
				var2[this.meshes.length] = var1;
				this.meshes = var2;
			} else {
				this.meshes[this.size] = var1;
			}
			++this.size;
		}
	}

	public final void reset() {
		this.size = 0;
	}

	public final void update(final long currentTime) {
		for(int i = 0; i < this.size; ++i) {
			this.meshes[i].update(currentTime);
		}

	}

	public final void render() {
		int i;
		for(i = 0; i < this.size; ++i) {
			this.meshes[i].render();
		}

		for(i = 0; i < this.size; ++i) {
			this.meshes[i].renderTransparent();
		}

	}
}
