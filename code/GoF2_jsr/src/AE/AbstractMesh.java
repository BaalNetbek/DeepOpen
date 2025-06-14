package AE;

import AE.Math.Matrix;

public abstract class AbstractMesh extends AEGeometry {
	protected int renderLayer;
	protected Matrix matrix = new Matrix();

	public AbstractMesh(final AbstractMesh var1) {
		super(var1);
		this.renderLayer = var1.renderLayer;
	}

	public AbstractMesh() {
		this.renderLayer = 0;
	}

	public final void setRenderLayer(final int var1) {
		this.renderLayer = var1;
	}

	public void appendToRender(final Camera var1, final Renderer var2) {
		if (this.draw && var1.isInViewFrustum(this.boundingSphere) != 0) {
			this.matrix = var1.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			var2.drawNode(this.renderLayer, this);
		}

	}

	public void forceAppendToRender(final Camera var1, final Renderer var2) {
		if (this.draw) {
			this.matrix = var1.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			var2.drawNode(this.renderLayer, this);
		}

	}

	public abstract void render();

	public void renderTransparent() {
	}

	public abstract GraphNode clone();

	public static AbstractMesh newPlaneStrip(final int var0, final int var1, final byte var2) {
		return new ParticleSystemMesh(0, var1, (byte)2);
	}

	public abstract void setTexture(ITexture var1);

	public abstract void OnRelease();

	public void update(final long var1) {
	}
}
