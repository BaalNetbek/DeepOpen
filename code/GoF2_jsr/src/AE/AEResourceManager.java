package AE;

import AE.Math.AEMatrix;

public final class AEResourceManager {
	private static int[] textureIds;
	private static ITexture[] textures;
	private static String[] texturePaths;
	private static boolean[] loadedTextures;
	private static int[] meshIds;
	private static AbstractMesh[] meshes;
	private static int[] radii_;
	private static String[] meshPaths;
	private static boolean[] loadedMeshes;
	private static int[] meshsTextureIds;

	public static void addTextureResource(final int var0, final String var1) {
		if (texturePaths == null) {
			(texturePaths = new String[1])[0] = var1;
			textures = new ITexture[1];
			(textureIds = new int[1])[0] = var0;
			(loadedTextures = new boolean[1])[0] = false;
		} else {
			final String[] var2 = new String[texturePaths.length + 1];
			System.arraycopy(texturePaths, 0, var2, 0, texturePaths.length);
			var2[texturePaths.length] = var1;
			texturePaths = var2;
			final ITexture[] var4 = new ITexture[textures.length + 1];
			System.arraycopy(textures, 0, var4, 0, textures.length);
			textures = var4;
			final int[] var5 = new int[textureIds.length + 1];
			System.arraycopy(textureIds, 0, var5, 0, textureIds.length);
			var5[textureIds.length] = var0;
			textureIds = var5;
			final boolean[] var3 = new boolean[loadedTextures.length + 1];
			System.arraycopy(loadedTextures, 0, var3, 0, loadedTextures.length);
			var3[loadedTextures.length] = false;
			loadedTextures = var3;
		}
	}

	public static void addGeometryResource(final int var0, final String var1, final int var2, final int var3) {
		if (meshPaths == null) {
			(meshPaths = new String[1])[0] = var1;
			(radii_ = new int[1])[0] = var2;
			meshes = new AbstractMesh[1];
			(meshIds = new int[1])[0] = var0;
			(loadedMeshes = new boolean[1])[0] = false;
			(meshsTextureIds = new int[1])[0] = var3;
		} else {
			final String[] var4 = new String[meshPaths.length + 1];
			System.arraycopy(meshPaths, 0, var4, 0, meshPaths.length);
			var4[meshPaths.length] = var1;
			meshPaths = var4;
			int[] var7 = new int[radii_.length + 1];
			System.arraycopy(radii_, 0, var7, 0, radii_.length);
			var7[radii_.length] = var2;
			radii_ = var7;
			final AbstractMesh[] var8 = new AbstractMesh[meshes.length + 1];
			System.arraycopy(meshes, 0, var8, 0, meshes.length);
			meshes = var8;
			var7 = new int[meshIds.length + 1];
			System.arraycopy(meshIds, 0, var7, 0, meshIds.length);
			var7[meshIds.length] = var0;
			meshIds = var7;
			final boolean[] var5 = new boolean[loadedMeshes.length + 1];
			System.arraycopy(loadedMeshes, 0, var5, 0, loadedMeshes.length);
			var5[loadedMeshes.length] = false;
			loadedMeshes = var5;
			final int[] var6 = new int[meshsTextureIds.length + 1];
			System.arraycopy(meshsTextureIds, 0, var6, 0, meshsTextureIds.length);
			var6[meshsTextureIds.length] = var3;
			meshsTextureIds = var6;
		}
	}

	public static void addSkyboxResource(final int var0, final String var1, final int var2) {
		addGeometryResource(9991, var1, -1, 1);
	}

	public static ITexture getTextureResource(final int var0) {
		for(int i = 0; i < textureIds.length; ++i) {
			if (var0 == textureIds[i]) {
				loadedTextures[i] = true;
				if (textures[i] == null) {
					final String[] var2 = {texturePaths[i]};
					if (var0 == 1) {
						final ITexture[] var10000 = textures;
						final ITexture var3 = getTextureResource(0);
						return var10000[i] = new JSRTexture((JSRTexture)var3);
					}

					return textures[i] = new JSRTexture(var2);
				}

				return textures[i];
			}
		}

		return null;
	}

	public static AbstractMesh getGeometryResource(int var0) {
		for(int i = 0; i < meshIds.length; ++i) {
			if (var0 == meshIds[i]) {
				loadedMeshes[i] = true;
				if (meshes[i] == null) {
					AbstractMesh[] var10000;
					int var10001;
					Object var10002;
					if (radii_[i] == -1) {
						var10000 = meshes;
						var10001 = i;
						final String var4 = meshPaths[i];
						var10002 = new BackGroundMesh(var4);
					} else {
						var10000 = meshes;
						var10001 = i;
						final int var5 = meshIds[i];
						final String var10003 = meshPaths[i];
						final int var3 = radii_[i];
						final String var2 = var10003;
						var0 = var5;
						if (var2.endsWith(".m3g")) {
							var10002 = new JSRMesh(var0, var2, var3);
						} else {
							var10002 = new AEMesh(var2, var3);
						}
					}

					var10000[var10001] = (AbstractMesh)var10002;
					if (meshes[i] != null && meshsTextureIds[i] != Integer.MIN_VALUE) {
						meshes[i].setTexture(getTextureResource(meshsTextureIds[i]));
					}

					return meshes[i];
				}

				return (AbstractMesh)meshes[i].clone();
			}
		}

		System.out.println("ERROR | AEResourceManager.getGeometryResource(" + var0 + ") not found !");
		return null;
	}

	public static void OnRelease() {
		int i;
		for(i = 0; i < meshes.length; ++i) {
			if (meshes[i] != null) {
				meshes[i].OnRelease();
				meshes[i] = null;
			}
		}

		for(i = 0; i < textures.length; ++i) {
			if (textures[i] != null) {
				textures[i].OnRelease();
				textures[i] = null;
			}
		}

	}

	public static void initGeometryTranforms() {
		for(int i = 0; i < meshes.length; ++i) {
			if (meshes[i] != null) {
				meshes[i].setTransform(new AEMatrix());
			}
		}

	}
}
