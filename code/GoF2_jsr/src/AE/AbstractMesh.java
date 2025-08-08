package AE;

import AE.Math.Matrix;

public abstract class AbstractMesh extends AEGeometry {
	protected int renderLayer;
	protected Matrix matrix = new Matrix();

	public AbstractMesh(final AbstractMesh mesh) {
		super(mesh);
		this.renderLayer = mesh.renderLayer;
	}

	public AbstractMesh() {
		this.renderLayer = 0;
	}

	public final void setRenderLayer(final int idx) {
		this.renderLayer = idx;
	}

	public void appendToRender(final Camera camera, final Renderer renderer) {
		if (this.draw && camera.isInViewFrustum(this.boundingSphere) != 0) {
			this.matrix = camera.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			renderer.drawNode(this.renderLayer, this);
		}

	}

	public void forceAppendToRender(final Camera camera, final Renderer renderer) {
		if (this.draw) {
			this.matrix = camera.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			renderer.drawNode(this.renderLayer, this);
		}

	}

	public abstract void render();

	public void renderTransparent() {
	}

	public abstract GraphNode clone();

	public static AbstractMesh newPlaneStrip(final int resourceId, final int quadCount, final byte blending) {
		return new ParticleSystemMesh(resourceId, quadCount, blending);
	}

	public abstract void setTexture(ITexture texture);

	public abstract void OnRelease();

	public void update(final long currentTime) {
	}
}
