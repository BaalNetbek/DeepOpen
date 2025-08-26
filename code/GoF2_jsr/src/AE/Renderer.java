package AE;

import AE.PaintCanvas.IGraphics3D;
import AE.PaintCanvas.RenderLayer;

public final class Renderer {
	private AECamera camera = null;
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

	public final void setActiveCamera(final AECamera var1) {
		this.camera = var1;
		this.camera.setActive();
		
		this.cameraTopGroup = this.camera;
		while(this.cameraTopGroup.getGroup() != null) {
			this.cameraTopGroup = this.cameraTopGroup.getGroup();
		}
//		for(this.cameraTopGroup = this.camera; this.cameraTopGroup.getGroup() != null; this.cameraTopGroup = this.cameraTopGroup.getGroup()) {
//		}

	}

	public final AECamera getCamera() {
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

		} catch (final Exception e) {
			e.printStackTrace();
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

	public final void renderFrame(final long currentTime) {
		for(int i = 0; i < this.layers.length; ++i) {
			this.layers[i].update(currentTime);
			this.layers[i].render();
			if (this.needGraphicsClear[i]) {
				this.graphics3D.clear();
			}

			this.layers[i].reset();
		}

	}

	public final void stackLayer() {
		final RenderLayer[] extLayers = new RenderLayer[this.layers.length + 1];
		System.arraycopy(this.layers, 0, extLayers, 0, this.layers.length);
		extLayers[this.layers.length] = new RenderLayer();
		this.layers = extLayers;
		final boolean[] extNDC = new boolean[this.needGraphicsClear.length + 1];
		System.arraycopy(this.needGraphicsClear, 0, extNDC, 0, this.needGraphicsClear.length);
		extNDC[this.needGraphicsClear.length] = true;
		this.needGraphicsClear = extNDC;
	}

	public final void addToLayer(final int layerIdx, final AEGeometry mesh) {
		this.layers[layerIdx].appendNode(mesh);
	}
}
