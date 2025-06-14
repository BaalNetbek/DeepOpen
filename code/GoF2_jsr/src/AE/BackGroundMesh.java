package AE;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Loader;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;

import AE.PaintCanvas.AEGraphics3D;

public final class BackGroundMesh extends AbstractMesh {
	private static Transform transform = new Transform();
	private static float[] tranformFloatArr = new float[16];
	private Node mesh;
	private static CompositingMode compositing;

	public BackGroundMesh(final String var1) {
		final String var2 = var1;
		final BackGroundMesh var5 = this;

		try {
			Object3D[] var3 = null;
			if (!var2.endsWith(".m3g")) {
				var3 = Loader.load(AEFile.readFileBytes(var2 + ".m3g"), 0);
			} else {
				var3 = Loader.load(AEFile.readFileBytes(var2), 0);
			}

			for(int var6 = 0; var6 < var3.length; ++var6) {
				if (var3[var6] instanceof javax.microedition.m3g.Group) {
					var5.mesh = (Node)var3[var6];
					break;
				}
			}
		} catch (final Exception var4) {
			this.mesh = null;
		}

		this.radius = 0;
		if (compositing == null) {
			(compositing = new CompositingMode()).setBlending(64);
			compositing.setDepthTestEnable(true);
			compositing.setDepthWriteEnable(false);
		}

	}

	private BackGroundMesh(final BackGroundMesh var1) {
		this.radius = 0;
		this.mesh = var1.mesh;
		this.renderLayer = var1.renderLayer;
		this.draw = var1.draw;
	}

	public final void render() {
		this.matrix.toFloatArray(tranformFloatArr);
		tranformFloatArr[3] = tranformFloatArr[7] = tranformFloatArr[11] = 0.0F;
		tranformFloatArr[7] = 0.0F;
		transform.set(tranformFloatArr);
		AEGraphics3D.graphics3D.render(this.mesh, transform);
	}

	public final void appendToRender(final Camera var1, final Renderer var2) {
		if (this.draw) {
			this.matrix = var1.localTransformation.getInverse(this.matrix);
			var2.drawNode(this.renderLayer, this);
		}

	}

	public final GraphNode clone() {
		return new BackGroundMesh(this);
	}

	public final void setTexture(final ITexture var1) {
		this.setTexture((javax.microedition.m3g.Group)this.mesh, ((JSRTexture)var1).getTexturesArray());
	}

	private void setTexture(final javax.microedition.m3g.Group var1, final Texture2D[] var2) {
		for(int var3 = 0; var3 < var1.getChildCount(); ++var3) {
			Node var4;
			if ((var4 = var1.getChild(var3)) instanceof Mesh) {
				final int var5 = ((Mesh)var4).getUserID();

				for(int var6 = 0; var6 < ((Mesh)var4).getSubmeshCount(); ++var6) {
					Appearance var7;
					(var7 = ((Mesh)var4).getAppearance(var6)).setMaterial((Material)null);
					var7.setCompositingMode(compositing);
					if (var7.getTexture(0) != null) {
						if (var2 != null) {
							if (var5 < var2.length) {
								var7.setTexture(0, var2[var5]);
							} else {
								var7.setTexture(0, var2[0]);
							}
						} else {
							var7.setTexture(0, (Texture2D)null);
						}
					}
				}
			} else if (var4 instanceof javax.microedition.m3g.Group) {
				this.setTexture((javax.microedition.m3g.Group)var4, var2);
			}
		}

	}

	public final void OnRelease() {
		this.mesh = null;
	}
}
