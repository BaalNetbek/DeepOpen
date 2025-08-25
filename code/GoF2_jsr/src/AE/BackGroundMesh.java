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

	public BackGroundMesh(final String path) {
		try {
			Object3D[] mesh = null;
			if (!path.endsWith(".m3g")) {
				mesh = Loader.load(AEFile.readFileBytes(path + ".m3g"), 0);
			} else {
				mesh = Loader.load(AEFile.readFileBytes(path), 0);
			}

			for(int i = 0; i < mesh.length; ++i) {
				if (mesh[i] instanceof javax.microedition.m3g.Group) {
					this.mesh = (Node)mesh[i];
					break;
				}
			}
		} catch (final Exception e) {
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

	public final void appendToRender(final AECamera var1, final Renderer var2) {
		if (this.draw) {
			this.matrix = var1.localTransformation.getInverse(this.matrix);
			var2.addToLayer(this.renderLayer, this);
		}

	}

	public final GraphNode clone() {
		return new BackGroundMesh(this);
	}

	public final void setTexture(final ITexture var1) {
		this.setTexture((javax.microedition.m3g.Group)this.mesh, ((JSRTexture)var1).getTexturesArray());
	}

	private void setTexture(final javax.microedition.m3g.Group var1, final Texture2D[] textures) {
		for(int i = 0; i < var1.getChildCount(); ++i) {
			Node node;
			if ((node = var1.getChild(i)) instanceof Mesh) {
				final int uid = ((Mesh)node).getUserID();

				for(int j = 0; j < ((Mesh)node).getSubmeshCount(); ++j) {
					Appearance appearance;
					(appearance = ((Mesh)node).getAppearance(j)).setMaterial((Material)null);
					appearance.setCompositingMode(compositing);
					if (appearance.getTexture(0) != null) {
						if (textures != null) {
							if (uid < textures.length) {
								appearance.setTexture(0, textures[uid]);
							} else {
								appearance.setTexture(0, textures[0]);
							}
						} else {
							appearance.setTexture(0, (Texture2D)null);
						}
					}
				}
			} else if (node instanceof javax.microedition.m3g.Group) {
				this.setTexture((javax.microedition.m3g.Group)node, textures);
			}
		}

	}

	public final void OnRelease() {
		this.mesh = null;
	}
}
