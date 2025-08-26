package AE;

import AE.Math.AEMatrix;

public abstract class AEGeometry extends SceneObject {
	protected int renderLayer;
	protected AEMatrix matrix = new AEMatrix();

	public AEGeometry(final AEGeometry mesh) {
		super(mesh);
		this.renderLayer = mesh.renderLayer;
	}

	public AEGeometry() {
		this.renderLayer = 0;
	}

	public final void setRenderLayer(final int idx) {
		this.renderLayer = idx;
	}

	public void appendToRender(final AECamera camera, final Renderer renderer) {
		if (this.draw && camera.isInViewFrustum(this.boundingSphere) != 0) {
			this.matrix = camera.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			renderer.addToLayer(this.renderLayer, this);
		}

	}

	public void forceAppendToRender(final AECamera camera, final Renderer renderer) {
		if (this.draw) {
			this.matrix = camera.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			renderer.addToLayer(this.renderLayer, this);
		}

	}

	public abstract void render();

	public void renderTransparent() {
	}

	public abstract GraphNode clone();

	public static AEGeometry newPlaneStrip(final int resourceId, final int quadCount, final byte blending) {
		return new ParticleSystemMesh(resourceId, quadCount, blending);
	}

	public abstract void setTexture(ITexture texture);

	public abstract void OnRelease();

	public void update(final long currentTime) {
	}
}
