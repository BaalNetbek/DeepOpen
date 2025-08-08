package AE;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Group;
import javax.microedition.m3g.KeyframeSequence;
import javax.microedition.m3g.Loader;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.World;

import AE.PaintCanvas.AEGraphics3D;
/**
 * Manages mesh loaded from .m3g file.
 * @author fishlabs
 *
 */
public final class JSRMesh extends AbstractMesh {
	private static int[] uvOffsets = {1, 2, 2, 2, 1, 2, 1, 0, 0, 2};
	private static int[] uvRotations = {0, 0, 0, 0, 1, 0, 0, 1, 0, 1};
	private int animLength;
	private int animStartFrame;
	private int animEndFrame;
	private int animCurrentFrame;
	private long sysTime;
	private boolean hasAnimation;
	private byte animationMode;
	private static Transform localToWorldTransform = new Transform();
	private static float[] m_matrix = new float[16];
	private Node[] opaqueNodes;
	private Node[] transparentNodes;
	private static PolygonMode opaquePmode;
	private static PolygonMode transparentPmode;
	private static CompositingMode composMode;
	private boolean needsUvFix = false;
	private Texture2D texture = null;
	private static Transform textureTransform = new Transform();

	public JSRMesh(final int resourceId, String filePath, final int radius) {
		this.resourceId = resourceId;
		initializeMaterials();

		try {
			Object3D[] object3D = null;
			//filePath = filePath.endsWith(".m3g") ? filePath : filePath + ".m3g";
			if (!filePath.endsWith(".m3g")) {
				object3D = Loader.load(AEFile.readFileBytes(filePath + ".m3g"), 0);
			} else {
				object3D = Loader.load(AEFile.readFileBytes(filePath), 0);
			}

			for(int i = 0; i < object3D.length; ++i) {
				int animLen;
				Node node;
				if (!(object3D[i] instanceof World)) {
					if (object3D[i] instanceof Group) {
						animLen = this.getAnimationLength((Group)object3D[i], 0);
						if (animLen == 0) {
							for(int j = 0; j < ((Group)object3D[i]).getChildCount(); ++j) {
								if ((node = ((Group)object3D[i]).getChild(j)) instanceof Mesh) {
									final Transform transform = new Transform();
									node.getTransform(transform);
									((Group)object3D[i]).removeChild(node);
									node.setTransform(transform);
									this.appendNode(node);
								}
							}
						} else {
							this.appendNode((Group)object3D[i]);
							this.animLength = this.animLength < animLen ? animLen : this.animLength;
						}
					}
				} else {
					for(int j = 0; j < ((World)object3D[i]).getChildCount(); ++j) {
						final Node child = ((World)object3D[i]).getChild(j);
						animLen = this.getAnimationLength(child, 0);
						if (child instanceof Group) {
							if (animLen == 0) {
								for(int k = 0; k < ((Group)child).getChildCount(); ++k) {
									if ((node = ((Group)child).getChild(k)) instanceof Mesh) {
										final Transform transform = new Transform();
										node.getTransform(transform);
										((Group)child).removeChild(node);
										node.setTransform(transform);
										this.appendNode(node);
									}
								}
							} else {
								this.appendNode(child);
								this.animLength = this.animLength < animLen ? animLen : this.animLength;
							}
						} else if (child instanceof Mesh) {
							this.appendNode(child);
							this.animLength = this.animLength < animLen ? animLen : this.animLength;
						}
					}
				}
			}

			if (this.animLength != 0) {
				this.animStartFrame = 0;
				this.animEndFrame = this.animLength;
				this.animCurrentFrame = 0;
				this.sysTime = -1L;
				this.hasAnimation = true;
				this.animationMode = 2;
			}
		} catch (final Exception var10) {
			this.opaqueNodes = null;
			this.transparentNodes = null;
		}

		this.radius = radius;
	}

	private JSRMesh(final JSRMesh mesh) {
		initializeMaterials();
		this.radius = mesh.radius;
		this.opaqueNodes = mesh.opaqueNodes;
		this.transparentNodes = mesh.transparentNodes;
		this.renderLayer = mesh.renderLayer;
		this.draw = mesh.draw;
		this.animLength = mesh.animLength;
		this.animStartFrame = mesh.animStartFrame;
		this.animEndFrame = mesh.animEndFrame;
		this.animCurrentFrame = mesh.animCurrentFrame;
		this.sysTime = mesh.sysTime;
		this.hasAnimation = mesh.hasAnimation;
		this.animationMode = mesh.animationMode;
		this.resourceId = mesh.resourceId;
		this.needsUvFix = mesh.needsUvFix;
		this.texture = mesh.texture;
	}

	private static void initializeMaterials() {
		if (opaquePmode == null) {
			(opaquePmode = new PolygonMode()).setCulling(PolygonMode.CULL_BACK);
			opaquePmode.setShading(PolygonMode.SHADE_SMOOTH);
		}

		if (transparentPmode == null) {
			(transparentPmode = new PolygonMode()).setCulling(PolygonMode.CULL_NONE);
			transparentPmode.setShading(PolygonMode.SHADE_FLAT);
		}

		if (composMode == null) {
			(composMode = new CompositingMode()).setBlending(CompositingMode.ALPHA);
			composMode.setDepthTestEnable(true);
			composMode.setDepthWriteEnable(false);
		}

	}

	private void appendNode(final Node newNode) {
		boolean isTransparent = false;
		
		Material mat;
		if (!(newNode instanceof Group)) {
			if (newNode instanceof Mesh) {
				for(int i = 0; i < ((Mesh)newNode).getSubmeshCount(); ++i) {
					mat = ((Mesh)newNode).getAppearance(i).getMaterial();
					if (mat != null && mat.getColor(Material.SPECULAR) == 0x00ff00 || mat.getColor(Material.SPECULAR) == 0x0000ff) {
						isTransparent = true;
						break;
					}
				}
			}
		} else {
			Node child;
			for(int i = 0; i < ((Group)newNode).getChildCount(); ++i) {
				child = ((Group)newNode).getChild(i);
				if (child instanceof Mesh) {
					for(int j = 0; j < ((Mesh)child).getSubmeshCount(); ++j) {
						mat = ((Mesh)child).getAppearance(j).getMaterial();
						if (mat != null && mat.getColor(Material.SPECULAR) == 0x00ff00 || mat.getColor(Material.SPECULAR) == 0x0000ff) {
							isTransparent = true;
							i = ((Group)newNode).getChildCount(); // To brake upper loop
							break;
						}
					}
				}
			}
		}
		

		Node[] newNodes;
		if (isTransparent) {
			if (this.transparentNodes == null) {
				this.transparentNodes = new Node[1];
				this.transparentNodes[0] = newNode;
			} else {
				newNodes = new Node[this.transparentNodes.length + 1];
				System.arraycopy(this.transparentNodes, 0, newNodes, 0, this.transparentNodes.length);
				newNodes[this.transparentNodes.length] = newNode;
				this.transparentNodes = newNodes;
			}
		} else if (this.opaqueNodes == null) {
			this.opaqueNodes = new Node[1];
			this.opaqueNodes[0] = newNode;
		} else {
			newNodes = new Node[this.opaqueNodes.length + 1];
			System.arraycopy(this.opaqueNodes, 0, newNodes, 0, this.opaqueNodes.length);
			newNodes[this.opaqueNodes.length] = newNode;
			this.opaqueNodes = newNodes;
		}
	}
	/**
	 * 
	 * @param node - m3g node 
	 * @param length - minimal length threshold to compare to
	 * @return Maximum of node or it's children's or given by argument animation length.
	 */
	private int getAnimationLength(final Node node, int length) {
		KeyframeSequence keyfs;
		for(int i = 0; i < node.getAnimationTrackCount(); ++i) {
			keyfs = node.getAnimationTrack(i).getKeyframeSequence();
			if (keyfs.getDuration() > length) {
				length = keyfs.getDuration();
			}
		}

		if (node instanceof Group) {
			for(int i = 0; i < ((Group)node).getChildCount(); ++i) {
				final Node child = ((Group)node).getChild(i);

				for(int j = 0; j < child.getAnimationTrackCount(); ++j) {
					keyfs = child.getAnimationTrack(j).getKeyframeSequence();
					if (keyfs.getDuration() > length) {
						length = keyfs.getDuration();
					}
				}

				if (child instanceof Group) {
					length = getAnimationLength(child, length);
				}
			}
		}

		return length;
	}
	
	public final void render() {
		if (this.opaqueNodes != null) {
			if (this.needsUvFix) {
				if (this.resourceId == 6769) {  // asteroid
					this.texture.setTranslation(0.0F, this.animStartFrame * 0.0118F, 0.0F);
				} else if (this.resourceId == 6778) { // 3d_planet
					this.texture.setTranslation(this.animStartFrame % 5 * 0.01171875F, this.animStartFrame / 5 * 0.01171875F, 0.0F);
				} else {
					this.texture.setTranslation(0.0F, this.animStartFrame * 0.0625F, 0.0F);
				}
			}

			this.matrix.toFloatArray(m_matrix);
			localToWorldTransform.set(m_matrix);
			int var1;
			if (this.animLength > 0) {
				for(var1 = 0; var1 < this.opaqueNodes.length; ++var1) {
					if (this.animationMode == 3) {
						this.opaqueNodes[var1].animate(this.animEndFrame - this.animCurrentFrame);
					} else {
						try {
							this.opaqueNodes[var1].animate(this.animCurrentFrame);
						} catch (final Exception var2) {
							System.out.println("JSRMesh.render() Exception id: " + this.resourceId);
						}
					}

					AEGraphics3D.graphics3D.render(this.opaqueNodes[var1], localToWorldTransform);
				}

				return;
			}

			for(var1 = 0; var1 < this.opaqueNodes.length; ++var1) {
				AEGraphics3D.graphics3D.render(this.opaqueNodes[var1], localToWorldTransform);
			}
		}

	}

	public final void renderTransparent() {
		if (this.transparentNodes != null) {
			if (this.needsUvFix) {
				textureTransform.setIdentity();
				this.texture.setTransform(textureTransform);
				this.texture.setScale(1.0F, 1.0F, 1.0F);
				if (this.resourceId == 6769) { // asteroid
					this.texture.setTranslation(0.0F, this.animStartFrame * 0.0118F, 0.0F);
				} else if (this.resourceId == 6781) { // map_3d_sun
					switch(this.animStartFrame) {
					case 10:
						this.texture.setTranslation(0.4375F, 0.9375F, 0.0F);
						this.texture.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
						break;
					case 11:
						this.texture.setTranslation(0.375F, 0.9375F, 0.0F);
						this.texture.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
						break;
					case 12:
						this.texture.setTranslation(0.5F, 1.0F, 0.0F);
						this.texture.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
						break;
					default:
						this.texture.setTranslation(0.0F, uvOffsets[this.animStartFrame] * 0.0625F, 0.0F);
						this.texture.setOrientation(uvRotations[this.animStartFrame] * 180.0F, 1.0F, 0.0F, 0.0F);
						if (uvRotations[this.animStartFrame] > 0) {
							this.texture.translate(1.0625F, 0.0625F, 0.0F);
							this.texture.setScale(-1.0F, 1.0F, 1.0F);
						}
					}
				} else {
					this.texture.setTranslation(0.0F, this.animStartFrame * 0.0625F, 0.0F);
				}
			}

			this.matrix.toFloatArray(m_matrix);
			localToWorldTransform.set(m_matrix);
			int var1;
			if (this.animLength > 0) {
				for(var1 = 0; var1 < this.transparentNodes.length; ++var1) {
					if (this.animationMode == 3) {
						this.transparentNodes[var1].animate(this.animEndFrame - this.animCurrentFrame);
					} else {
						try {
							this.transparentNodes[var1].animate(this.animCurrentFrame);
						} catch (final Exception var3) {
							System.out.println(var3.getMessage() + "\n\n" + "id: " + this.resourceId);
						}
					}

					AEGraphics3D.graphics3D.render(this.transparentNodes[var1], localToWorldTransform);
				}

				return;
			}

			for(var1 = 0; var1 < this.transparentNodes.length; ++var1) {
				AEGraphics3D.graphics3D.render(this.transparentNodes[var1], localToWorldTransform);
			}
		}

	}

	public final void update(final long currentTime) {
		if (this.hasAnimation) {
			if (this.sysTime == -1L) {
				this.sysTime = currentTime;
			}

			this.animCurrentFrame = this.animStartFrame + (int)((currentTime - this.sysTime) / this.animationFrameTime);
			if (this.animCurrentFrame > this.animEndFrame) {
				if (this.animationMode == 2) {
					this.animCurrentFrame = this.animStartFrame;
					this.sysTime = currentTime - (this.animCurrentFrame - this.animStartFrame) * this.animationFrameTime;
					return;
				}

				this.hasAnimation = false;
				this.animCurrentFrame = this.animEndFrame;
				this.sysTime = -1L;
			}
		}

	}

	public final int getCurrentAnimFrame() {
		return this.animCurrentFrame;
	}

	public final void setAnimationSpeed(final int var1) {
		this.animationFrameTime = var1 > 0 ? var1 : 1;
	}

	public final void setAnimationRangeInTime(final int start, final int end) {
		this.animStartFrame = (start < 0 || start > this.animLength) && !this.needsUvFix ? 0 : start;
		this.animEndFrame = (end < 0 || end > this.animLength) && !this.needsUvFix ? this.animLength : end;
		this.animCurrentFrame = this.animCurrentFrame >= start && !this.needsUvFix ? this.animCurrentFrame > end ? end : this.animCurrentFrame : start;
		this.sysTime = -1L;
	}

	public final void setAnimationMode(final byte mode) {
		if (!this.needsUvFix) {
			this.animationMode = mode;
			this.sysTime = -1L;
			this.hasAnimation = this.animLength > 0;
		}
	}

	public final void disableAnimation() {
		this.hasAnimation = false;
	}

	public final boolean hasAnimation() {
		return this.hasAnimation;
	}

	public final GraphNode clone() {
		return new JSRMesh(this);
	}

	public final void setTexture(final ITexture var1) {
		int var2;
		if (this.opaqueNodes != null) {
			for(var2 = 0; var2 < this.opaqueNodes.length; ++var2) {
				setupMaterial(this.opaqueNodes[var2], ((JSRTexture)var1).getTexturesArray(), false);
			}
		}

		if (this.transparentNodes != null) {
			for(var2 = 0; var2 < this.transparentNodes.length; ++var2) {
				setupMaterial(this.transparentNodes[var2], ((JSRTexture)var1).getTexturesArray(), true);
			}
		}

	}

	private void setupMaterial(final Node node, final Texture2D[] textures, final boolean glowing) {
		if (node instanceof Mesh) {
			int textureIdx;
			textureIdx = ((Mesh)node).getUserID();

			for(int i = 0; i < ((Mesh)node).getSubmeshCount(); ++i) {
				final Appearance appearance = ((Mesh)node).getAppearance(i);
				if (glowing) {
					appearance.setCompositingMode(composMode);
					appearance.setPolygonMode(transparentPmode);
				} else {
					appearance.setCompositingMode((CompositingMode)null);
					appearance.setPolygonMode(opaquePmode);
				}

				boolean modifiesTexture = false;
				if (appearance.getMaterial() != null) {
					modifiesTexture = appearance.getMaterial().getColor(Material.SPECULAR) == 0xff0000 || appearance.getMaterial().getColor(Material.SPECULAR) == 0x0000ff;
				}

				appearance.setMaterial((Material)null);
				Material material = new Material();
				material.setShininess(50.0F);
				if (glowing) {
					material.setColor(Material.EMISSIVE, 0xFFFFFFFF);
				}

				appearance.setMaterial(material);
				if (appearance.getTexture(0) != null && textures != null) {
					if (textureIdx < textures.length) {
						if (modifiesTexture) {
							this.texture = (Texture2D)textures[textureIdx].duplicate();
							appearance.setTexture(0, this.texture);
						} else {
							appearance.setTexture(0, textures[textureIdx]);
						}
					} else if (modifiesTexture) {
						this.texture = (Texture2D)textures[0].duplicate();
						appearance.setTexture(0, this.texture);
					} else {
						appearance.setTexture(0, textures[0]);
					}
				}

				((Mesh)node).setAppearance(i, appearance);
				if (modifiesTexture) {
					this.needsUvFix = true;
				}
			}

		} else if (node instanceof Group) {
			for(int i = 0; i < ((Group)node).getChildCount(); ++i) {
				final Node child = ((Group)node).getChild(i);
				setupMaterial(child, textures, glowing);
			}
		}
	}

	public final void OnRelease() {
	}
}
