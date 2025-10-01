package AE;

import AE.Math.AEMatrix;

public final class AEResourceManager {
	private static int[] textureIds;
	private static ITexture[] textures;
	private static String[] texturePaths;
	private static boolean[] loadedTextures;
	private static int[] meshIds;
	private static AEGeometry[] meshes;
	private static int[] radii_;
	private static String[] meshPaths;
	private static boolean[] loadedMeshes;
	private static int[] meshsTextureIds;

	public static void addTextureResource(final int resId, final String path) {
		if (texturePaths == null) {
			texturePaths = new String[1];
			texturePaths[0] = path;
			textures = new ITexture[1];
			textureIds = new int[1];
			textureIds[0] = resId;
			loadedTextures = new boolean[1];
			loadedTextures[0] = false;
		} else {
			final String[] extPaths = new String[texturePaths.length + 1];
			System.arraycopy(texturePaths, 0, extPaths, 0, texturePaths.length);
			extPaths[texturePaths.length] = path;
			texturePaths = extPaths;
			
			final ITexture[] extTexs = new ITexture[textures.length + 1];
			System.arraycopy(textures, 0, extTexs, 0, textures.length);
			textures = extTexs;
			
			final int[] extTexIDs = new int[textureIds.length + 1];
			System.arraycopy(textureIds, 0, extTexIDs, 0, textureIds.length);
			extTexIDs[textureIds.length] = resId;
			textureIds = extTexIDs;
			
			final boolean[] extLoaded = new boolean[loadedTextures.length + 1];
			System.arraycopy(loadedTextures, 0, extLoaded, 0, loadedTextures.length);
			extLoaded[loadedTextures.length] = false;
			loadedTextures = extLoaded;
		}
	}

	public static void addGeometryResource(final int resId, final String path, final int radius, final int textureResId) {
		if (meshPaths == null) {
			meshPaths = new String[1];
			meshPaths[0] = path;
			radii_ = new int[1];
			radii_[0] = radius;
			meshes = new AEGeometry[1];
			meshIds = new int[1];
			meshIds[0] = resId;
			loadedMeshes = new boolean[1];
			loadedMeshes[0] = false;
			meshsTextureIds = new int[1];
			meshsTextureIds[0] = textureResId;
		} else {
			final String[] extPaths = new String[meshPaths.length + 1];
			System.arraycopy(meshPaths, 0, extPaths, 0, meshPaths.length);
			extPaths[meshPaths.length] = path;
			meshPaths = extPaths;
			
			int[] extRadii = new int[radii_.length + 1];
			System.arraycopy(radii_, 0, extRadii, 0, radii_.length);
			extRadii[radii_.length] = radius;
			radii_ = extRadii;
			
			final AEGeometry[] extMeshes = new AEGeometry[meshes.length + 1];
			System.arraycopy(meshes, 0, extMeshes, 0, meshes.length);
			meshes = extMeshes;
			
			extRadii = new int[meshIds.length + 1];
			System.arraycopy(meshIds, 0, extRadii, 0, meshIds.length);
			extRadii[meshIds.length] = resId;
			meshIds = extRadii;
			
			final boolean[] extLoaded = new boolean[loadedMeshes.length + 1];
			System.arraycopy(loadedMeshes, 0, extLoaded, 0, loadedMeshes.length);
			extLoaded[loadedMeshes.length] = false;
			loadedMeshes = extLoaded;
			
			final int[] extMeshTexIds = new int[meshsTextureIds.length + 1];
			System.arraycopy(meshsTextureIds, 0, extMeshTexIds, 0, meshsTextureIds.length);
			extMeshTexIds[meshsTextureIds.length] = textureResId;
			meshsTextureIds = extMeshTexIds;
		}
	}

	public static void addSkyboxResource(final int id, final String path, final int texture) {
		addGeometryResource(id, path, -1, texture);
	}

	public static ITexture getTextureResource(final int resourceId) {
		for(int i = 0; i < textureIds.length; ++i) {
			if (resourceId == textureIds[i]) {
				loadedTextures[i] = true;
				if (textures[i] == null) {
					if (resourceId == 1) {
						return textures[i] = new JSRTexture((JSRTexture)getTextureResource(0));
					}
					return textures[i] = new JSRTexture(new String[] {texturePaths[i]});
				}

				return textures[i];
			}
		}

		return null;
	}

	public static AEGeometry getGeometryResource(int resourceId) {
		AEGeometry geometry;
		for(int i = 0; i < meshIds.length; ++i) {
			if (resourceId == meshIds[i]) {
				loadedMeshes[i] = true;
				if (meshes[i] == null) {
					if (radii_[i] == -1) {					
						final String var4 = meshPaths[i];
						geometry = new BackGroundMesh(var4);
					} 
					else {				
						if (meshPaths[i].endsWith(".m3g")) {
							geometry = new JSRMesh(meshIds[i], meshPaths[i], radii_[i]);
						} else {
							geometry = new AEMesh(meshPaths[i], radii_[i]);
						}
					}

					meshes[i] = geometry;
					if (meshes[i] != null && meshsTextureIds[i] != Integer.MIN_VALUE) {
						meshes[i].setTexture(getTextureResource(meshsTextureIds[i]));
					}

					return meshes[i];
				}

				return (AEGeometry)meshes[i].clone();
			}
		}

		System.out.println("ERROR | AEResourceManager.getGeometryResource(" + resourceId + ") not found !");
		return null;
	}

	public static void OnRelease() {
		for(int i = 0; i < meshes.length; ++i) {
			if (meshes[i] != null) {
				meshes[i].OnRelease();
				meshes[i] = null;
			}
		}

		for(int i = 0; i < textures.length; ++i) {
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
