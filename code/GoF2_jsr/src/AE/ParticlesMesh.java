package AE;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

import AE.Math.AEVector3D;
import AE.PaintCanvas.AEGraphics3D;

/**
 * Represents collection of 3D sprites.
 * @author fishlabs
 *
 */
public final class ParticlesMesh extends AbstractMesh {
	private static Transform calcTransform = new Transform();
	private static AEVector3D tempPos = new AEVector3D();
	private Appearance appearance;
	private static PolygonMode polygonMode;
	private final int count;
	private final Mesh particle;
	private final int[] positions;
	private final int[] vertexColors;
	private int[] scales;
	private final float defualtScale;
	private byte blending;
	/**
	 *
	 * @param uvScale uv coordinate scaling factor
	 * @param u1 top-right x texture coordinate (u)
	 * @param v1 top-right y texture coordinate (v)
	 * @param u2 bott-left x texture coordinate (u)
	 * @param v2 bott-left y texture coordinate (v)
	 * @param baseSize default particle size
	 * @param count particles count
	 * @param blending blending mode:
	 * 	0 	- replace;
	 * 	1,3 - alpha;
	 * 	2 	- add;
	 */
	public ParticlesMesh(final int uvScale, final int u1, final int v1, final int u2, final int v2, final int baseSize, final int count, final byte blending) {
		this.blending = blending;
		this.appearance = new Appearance();
		final CompositingMode composMode = new CompositingMode();
		switch(blending) {
		case 0:
			composMode.setBlending(CompositingMode.REPLACE);
			composMode.setDepthWriteEnable(true);
			composMode.setDepthTestEnable(true);
			break;
		case 1:
		case 3:
			composMode.setBlending(CompositingMode.ALPHA);
			composMode.setDepthWriteEnable(false);
			composMode.setDepthTestEnable(true);
			break;
		case 2:
			composMode.setBlending(CompositingMode.ALPHA_ADD);
			composMode.setDepthWriteEnable(false);
			composMode.setDepthTestEnable(true);
		}

		this.appearance.setCompositingMode(composMode);
		if (polygonMode == null) {
			polygonMode = new PolygonMode();
			polygonMode.setCulling(PolygonMode.CULL_BACK);
			polygonMode.setShading(PolygonMode.SHADE_FLAT);
			polygonMode.setPerspectiveCorrectionEnable(false);
		}

		this.appearance.setPolygonMode(polygonMode);
		this.count = count;
		this.positions = new int[count * 3];
		this.vertexColors = new int[count];

		for(int i = 0; i < this.vertexColors.length; ++i) {
			this.vertexColors[i] = 0xffffffff;
		}
		// Building a square
		final VertexBuffer vBuffer = new VertexBuffer();
		final VertexArray vcArr = new VertexArray(4, 3, 1);
		final VertexArray uvArr = new VertexArray(4, 2, 1);
		vcArr.set(0, 4, new byte[]{
				-1, 1, 0, // top-left
				1, 1, 0,  // top-right
				1, -1, 0, // bot-right
				-1, -1, 0 // bot-left
		});
		uvArr.set(0, 4, new byte[]{
				(byte)u1, (byte)v1,
				(byte)u2, (byte)v1,
				(byte)u2, (byte)v2,
				(byte)u1, (byte)v2
		});
		vBuffer.setPositions(vcArr, 1.0F, new float[]{0.0F, 0.0F, 0.0F});
		vBuffer.setTexCoords(0, uvArr, 1.0F / uvScale, new float[]{0.0F, 0.0F});

		final int[] tris = {
				0, 2, 1,
				3, 2, 0
		};
		final int[] strips = {3, 3};
		final TriangleStripArray tStrips = new TriangleStripArray(tris, strips);

		this.particle = new Mesh(vBuffer, tStrips, this.appearance);
		this.radius = baseSize >> 1;
		this.defualtScale = baseSize >> 1;
		this.scales = null;
	}

	private ParticlesMesh(final ParticlesMesh var1) {
		this.count = var1.count;
		this.positions = new int[3 * this.count];
		this.vertexColors = new int[this.count];
		System.arraycopy(var1.vertexColors, 0, this.vertexColors, 0, this.vertexColors.length);
		System.arraycopy(var1.positions, 0, this.positions, 0, this.positions.length);
		this.particle = var1.particle;
		this.radius = var1.radius;
		this.defualtScale = var1.defualtScale;
		this.draw = var1.draw;
		this.renderLayer = var1.renderLayer;
		if (var1.scales != null) {
			this.scales = new int[this.count];
			System.arraycopy(var1.scales, 0, this.scales, 0, this.scales.length);
		}

	}

	public final GraphNode clone() {
		return new ParticlesMesh(this);
	}

	public final void OnRelease() {
	}

	public final void appendToRender(final AECamera var1, final Renderer var2) {
		if (this.draw) {
			this.matrix = var1.localTransformation.getInverse(this.matrix);
			this.matrix.multiply(this.localTransformation);
			var2.drawNode(this.renderLayer, this);
		}

	}

	public final void forceAppendToRender(final AECamera var1, final Renderer var2) {
		appendToRender(var1, var2);
	}

	public final void render() {
		if (this.blending == 0) {
			int j = 0;
			for(int i = 0; i < this.count; i++) {
				tempPos.set(this.positions[j], this.positions[j + 1], this.positions[j + 2]);
				tempPos = this.matrix.transformVector(tempPos);
				calcTransform.setIdentity();
				calcTransform.postTranslate(tempPos.x, tempPos.y, tempPos.z);
				if (this.scales != null) {
					calcTransform.postScale(this.scales[i] >> 1, this.scales[i] >> 1, this.scales[i] >> 1);
				} else {
					calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
				}

				this.particle.getVertexBuffer().setDefaultColor(this.vertexColors[i]);
				AEGraphics3D.graphics3D.render(this.particle, calcTransform);
				j += 3;
			}
		}

	}

	public final void renderTransparent() {
		if (this.blending != 0) {
			int var1 = 0;

			for(int i = 0; i < this.count; var1 += 3) {
				tempPos.set(this.positions[var1], this.positions[var1 + 1], this.positions[var1 + 2]);
				tempPos = this.matrix.transformVector(tempPos);
				calcTransform.setIdentity();
				calcTransform.postTranslate(tempPos.x, tempPos.y, tempPos.z);
				if (this.scales != null) {
					calcTransform.postScale(this.scales[i] >> 1, this.scales[i] >> 1, this.scales[i] >> 1);
				} else {
					calcTransform.postScale(this.defualtScale, this.defualtScale, this.defualtScale);
				}

				this.particle.getVertexBuffer().setDefaultColor(this.vertexColors[i]);
				AEGraphics3D.graphics3D.render(this.particle, calcTransform);
				++i;
			}
		}

	}

	public final void setTexture(final ITexture var1) {
		final Texture2D var2 = new Texture2D(((JSRTexture)var1).getTexturesArray()[0].getImage());
		this.appearance.setTexture(0, var2);
		// using just
		// this.appearance.setTexture(0, ((JSRTexture)var1).getTexturesArray()[0]);
		// reduces memory usage a bit
	}

	public final int[] getPositions() {
		return this.positions;
	}

	public final int[] getColors() {
		return this.vertexColors;
	}

	public final int[] getScales() {
		if (this.scales == null) {
			this.scales = new int[this.count];

			for(int i = 0; i < this.scales.length; ++i) {
				this.scales[i] = (int)this.defualtScale;
			}
		}

		return this.scales;
	}
}
