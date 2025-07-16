package AE;

import java.io.DataInputStream;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Fog;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

import AE.PaintCanvas.AEGraphics3D;

public final class AEMesh extends AbstractMesh {
	private static float[] transformArray = new float[16];
	private DataInputStream aemFile;
	private VertexBuffer vertexBuffer;
	private Appearance appearance;
	private TriangleStripArray triangleStripArray;
	private Transform transform;
	private boolean isTransparent;
	private static PolygonMode opaquePMode;
	private static PolygonMode transparentPMode;
	private static CompositingMode compositingMode;

	public AEMesh(final String path, final int radius) {
		setupMaterial();
		try {
			if (!path.endsWith(".aem")) {
				this.aemFile = new DataInputStream(this.getClass().getResourceAsStream(path + ".aem"));
			} else {
				this.aemFile = new DataInputStream(this.getClass().getResourceAsStream(path));
			}

			final byte[] magic = new byte[7];
			this.aemFile.read(magic, 0, 7);
			int flags = this.aemFile.readUnsignedByte();
			
			if ((flags & 16) != 0) {
				final int[] faceIndc = new int[swapBytes(this.aemFile.readUnsignedShort())];

				for(int i = 0; i < faceIndc.length; ++i) {
					faceIndc[i] = swapBytes(this.aemFile.readUnsignedShort());
				}

				final int[] stripLens = new int[swapBytes(this.aemFile.readUnsignedShort())];

				for(int i = 0; i < stripLens.length; ++i) {
					stripLens[i] = swapBytes(this.aemFile.readUnsignedShort());
				}

				this.triangleStripArray = new TriangleStripArray(faceIndc, stripLens);
			}
			
			VertexArray vcArr = null;
			final short[] vcRaw = new short[swapBytes(this.aemFile.readUnsignedShort()) * 3];
			for(int i = 0; i < vcRaw.length; ++i) {
				vcRaw[i] = (short)swapBytes(this.aemFile.readShort());
			}
			vcArr = new VertexArray(vcRaw.length / 3, 3, 2);
			vcArr.set(0, vcRaw.length / 3, vcRaw);
			
			VertexArray uvArr = null;
			if ((flags & 2) != 0) {
				final short[] uvRaw = new short[vcRaw.length / 3 << 1];

				for(int i = 0; i < uvRaw.length; i += 2) {
					uvRaw[i] = (short)swapBytes(this.aemFile.readShort());
					uvRaw[i + 1] = (short)(255 - (short)swapBytes(this.aemFile.readShort()));
				}
				uvArr = new VertexArray(uvRaw.length / 2, 2, 2);
				uvArr.set(0, uvRaw.length / 2, uvRaw);
			}
			
			VertexArray normArr = null;
			if ((flags & 4) != 0) {
				final short[] normRaw = new short[vcRaw.length];

				for(int i = 0; i < normRaw.length; ++i) {
					normRaw[i] = (short)swapBytes(this.aemFile.readShort());
				}

				normArr = new VertexArray(normRaw.length / 3, 3, 2);
				normArr.set(0, normRaw.length / 3, normRaw);
			}

			this.vertexBuffer = new VertexBuffer();
			this.vertexBuffer.setPositions(vcArr, 1.0F, (float[])null);
			this.vertexBuffer.setNormals((VertexArray)null);
			this.vertexBuffer.setTexCoords(0, uvArr, 1.0f/255.0f, (float[])null);
			this.appearance = new Appearance();
			this.isTransparent = this.aemFile.read() != 0;
			if (this.isTransparent) {
				this.appearance.setCompositingMode(compositingMode);
				this.appearance.setPolygonMode(transparentPMode);
			} else {
				this.appearance.setCompositingMode((CompositingMode)null);
				this.appearance.setPolygonMode(opaquePMode);
			}

			this.transform = new Transform();
			this.transform.setIdentity();
			this.aemFile.close();
			System.gc();
		} catch (final Exception e) {
			System.out.println("Error loading aemesh = " + path);
			e.printStackTrace();
		}

		this.radius = radius;
	}

	private AEMesh(final AEMesh aem) {
		super(aem);
		setupMaterial();
		this.vertexBuffer = aem.vertexBuffer;
		this.triangleStripArray = aem.triangleStripArray;
		this.transform = aem.transform;
		this.appearance = aem.appearance;
		this.isTransparent = aem.isTransparent;
		this.renderLayer = aem.renderLayer;
		this.draw = aem.draw;
		this.radius = aem.radius;
		this.resourceId = aem.resourceId;
	}

	private static void setupMaterial() {
		if (opaquePMode == null) {
			(opaquePMode = new PolygonMode()).setCulling(PolygonMode.CULL_BACK);
			opaquePMode.setShading(PolygonMode.SHADE_FLAT);
			opaquePMode.setPerspectiveCorrectionEnable(false);
			opaquePMode.setLocalCameraLightingEnable(false);
			opaquePMode.setTwoSidedLightingEnable(false);
			opaquePMode.setWinding(PolygonMode.WINDING_CCW);
		}

		if (transparentPMode == null) {
			(transparentPMode = new PolygonMode()).setCulling(PolygonMode.CULL_NONE);
			transparentPMode.setShading(PolygonMode.SHADE_FLAT);
			transparentPMode.setPerspectiveCorrectionEnable(false);
		}

		if (compositingMode == null) {
			(compositingMode = new CompositingMode()).setBlending(CompositingMode.ALPHA);
			compositingMode.setDepthTestEnable(true);
			compositingMode.setDepthWriteEnable(false);
		}

	}

	public final void setTexture(final ITexture var1) {
		final Texture2D[] var2 = ((JSRTexture)var1).getTexturesArray();
		this.appearance.setTexture(0, var2[0]);
	}

	public final GraphNode clone() {
		return new AEMesh(this);
	}

	public final void OnRelease() {
		this.aemFile = null;
		this.matrix = null;
		this.group = null;
		this.parent = null;
		this.compositeTransformation = null;
		this.localTransformation = null;
		this.boundingSphere = null;
		if (this.appearance != null) {
			this.appearance.setCompositingMode((CompositingMode)null);
			this.appearance.setFog((Fog)null);
			this.appearance.setMaterial((Material)null);
			this.appearance.setPolygonMode((PolygonMode)null);
			this.appearance.setTexture(0, (Texture2D)null);
			this.appearance.setUserID(0);
			this.appearance = null;
		}

		this.vertexBuffer = null;
		this.appearance = null;
		this.triangleStripArray = null;
		this.transform = null;
		System.gc();
	}

	public final void render() {
		if (!this.isTransparent) {
			this.matrix.toFloatArray(transformArray);
			this.transform.set(transformArray);
			if (this.vertexBuffer != null && this.triangleStripArray != null && this.appearance != null && this.transform != null && AEGraphics3D.graphics3D != null) {
				AEGraphics3D.graphics3D.render(this.vertexBuffer, this.triangleStripArray, this.appearance, this.transform);
			}
		}

	}

	public final void renderTransparent() {
		if (this.isTransparent) {
			this.matrix.toFloatArray(transformArray);
			this.transform.set(transformArray);
			if (this.vertexBuffer != null && this.triangleStripArray != null && this.appearance != null && this.transform != null && AEGraphics3D.graphics3D != null) {
				AEGraphics3D.graphics3D.render(this.vertexBuffer, this.triangleStripArray, this.appearance, this.transform);
			}
		}

	}

	private static int swapBytes(final int x) {
		return (x & 0xFF) << 8 | x >> 8 & 0xFF;
	}
}
