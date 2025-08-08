package AE;

import AE.PaintCanvas.IGraphics3D;
import AE.PaintCanvas.RenderLayer;

public final class Renderer {
	private Camera camera = null;
	private GraphNode cameraTopGroup;
	private RenderLayer[] layers = new RenderLayer[1];
	private boolean[] needGraphicsClear;
	private final IGraphics3D graphics3D;

	public Renderer(final IGraphics3D var1) {
		this.layers[0] = new RenderLayer();
		this.needGraphicsClear = new boolean[1];
		this.needGraphicsClear[0] = true;
		this.graphics3D = var1;
	}

	public final void setActiveCamera(final Camera var1) {
		this.camera = var1;
		this.camera.setActive();

		for(this.cameraTopGroup = this.camera; this.cameraTopGroup.getGroup() != null; this.cameraTopGroup = this.cameraTopGroup.getGroup()) {
		}

	}

	public final Camera getCamera() {
		return this.camera;
	}

	public final void drawNodeInVF(final GraphNode var1) {
		try {
			if (var1 != null && this.layers != null && this.camera != null) {
				var1.updateTransform(false);
				if (this.cameraTopGroup != null) {
					this.cameraTopGroup.updateTransform(false);
				} else {
					this.camera.updateTransform(false);
				}

				var1.appendToRender(this.camera, this);
			}

		} catch (final Exception var2) {
			var2.printStackTrace();
		}
	}

	public final void renderStillFrame() {
		for(int i = 0; i < this.layers.length; ++i) {
			this.layers[i].render();
			if (this.needGraphicsClear[i]) {
				this.graphics3D.clear();
			}

			this.layers[i].reset();
		}

	}

	public final void renderFrame(final long var1) {
		for(int i = 0; i < this.layers.length; ++i) {
			this.layers[i].update(var1);
			this.layers[i].render();
			if (this.needGraphicsClear[i]) {
				this.graphics3D.clear();
			}

			this.layers[i].reset();
		}

	}

	public final void addLayer() {
		final RenderLayer[] var2 = new RenderLayer[this.layers.length + 1];
		System.arraycopy(this.layers, 0, var2, 0, this.layers.length);
		var2[this.layers.length] = new RenderLayer();
		this.layers = var2;
		final boolean[] var3 = new boolean[this.needGraphicsClear.length + 1];
		System.arraycopy(this.needGraphicsClear, 0, var3, 0, this.needGraphicsClear.length);
		var3[this.needGraphicsClear.length] = true;
		this.needGraphicsClear = var3;
	}

	public final void drawNode(final int var1, final AbstractMesh var2) {
		this.layers[var1].appendNode(var2);
	}
}
