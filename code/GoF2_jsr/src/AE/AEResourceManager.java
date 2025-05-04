package AE;

import AE.Math.Matrix;

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

   public static void addTextureResource(int var0, String var1) {
      if (texturePaths == null) {
         (texturePaths = new String[1])[0] = var1;
         textures = new ITexture[1];
         (textureIds = new int[1])[0] = var0;
         (loadedTextures = new boolean[1])[0] = false;
      } else {
         String[] var2 = new String[texturePaths.length + 1];
         System.arraycopy(texturePaths, 0, var2, 0, texturePaths.length);
         var2[texturePaths.length] = var1;
         texturePaths = var2;
         ITexture[] var4 = new ITexture[textures.length + 1];
         System.arraycopy(textures, 0, var4, 0, textures.length);
         textures = var4;
         int[] var5 = new int[textureIds.length + 1];
         System.arraycopy(textureIds, 0, var5, 0, textureIds.length);
         var5[textureIds.length] = var0;
         textureIds = var5;
         boolean[] var3 = new boolean[loadedTextures.length + 1];
         System.arraycopy(loadedTextures, 0, var3, 0, loadedTextures.length);
         var3[loadedTextures.length] = false;
         loadedTextures = var3;
      }
   }

   public static void addGeometryResource(int var0, String var1, int var2, int var3) {
      if (meshPaths == null) {
         (meshPaths = new String[1])[0] = var1;
         (radii_ = new int[1])[0] = var2;
         meshes = new AbstractMesh[1];
         (meshIds = new int[1])[0] = var0;
         (loadedMeshes = new boolean[1])[0] = false;
         (meshsTextureIds = new int[1])[0] = var3;
      } else {
         String[] var4 = new String[meshPaths.length + 1];
         System.arraycopy(meshPaths, 0, var4, 0, meshPaths.length);
         var4[meshPaths.length] = var1;
         meshPaths = var4;
         int[] var7 = new int[radii_.length + 1];
         System.arraycopy(radii_, 0, var7, 0, radii_.length);
         var7[radii_.length] = var2;
         radii_ = var7;
         AbstractMesh[] var8 = new AbstractMesh[meshes.length + 1];
         System.arraycopy(meshes, 0, var8, 0, meshes.length);
         meshes = var8;
         var7 = new int[meshIds.length + 1];
         System.arraycopy(meshIds, 0, var7, 0, meshIds.length);
         var7[meshIds.length] = var0;
         meshIds = var7;
         boolean[] var5 = new boolean[loadedMeshes.length + 1];
         System.arraycopy(loadedMeshes, 0, var5, 0, loadedMeshes.length);
         var5[loadedMeshes.length] = false;
         loadedMeshes = var5;
         int[] var6 = new int[meshsTextureIds.length + 1];
         System.arraycopy(meshsTextureIds, 0, var6, 0, meshsTextureIds.length);
         var6[meshsTextureIds.length] = var3;
         meshsTextureIds = var6;
      }
   }

   public static void addSkyboxResource(int var0, String var1, int var2) {
      addGeometryResource(9991, var1, -1, 1);
   }

   public static ITexture getTextureResource(int var0) {
      for(int var1 = 0; var1 < textureIds.length; ++var1) {
         if (var0 == textureIds[var1]) {
            loadedTextures[var1] = true;
            if (textures[var1] == null) {
               String[] var2 = new String[]{texturePaths[var1]};
               if (var0 == 1) {
                  ITexture[] var10000 = textures;
                  ITexture var3 = getTextureResource(0);
                  return var10000[var1] = new JSRTexture((JSRTexture)var3);
               }

               return textures[var1] = new JSRTexture(var2);
            }

            return textures[var1];
         }
      }

      return null;
   }

   public static AbstractMesh getGeometryResource(int var0) {
      for(int var1 = 0; var1 < meshIds.length; ++var1) {
         if (var0 == meshIds[var1]) {
            loadedMeshes[var1] = true;
            if (meshes[var1] == null) {
               AbstractMesh[] var10000;
               int var10001;
               Object var10002;
               if (radii_[var1] == -1) {
                  var10000 = meshes;
                  var10001 = var1;
                  String var4 = meshPaths[var1];
                  var10002 = new BackGroundMesh(var4);
               } else {
                  var10000 = meshes;
                  var10001 = var1;
                  int var5 = meshIds[var1];
                  String var10003 = meshPaths[var1];
                  int var3 = radii_[var1];
                  String var2 = var10003;
                  var0 = var5;
                  if (var2.endsWith(".m3g")) 
                	  var10002 = new JSRMesh(var0, var2, var3);
                  else 
                	  var10002 = new AEMesh(var2, var3);
               }

               var10000[var10001] = (AbstractMesh)var10002;
               if (meshes[var1] != null && meshsTextureIds[var1] != Integer.MIN_VALUE) {
                  meshes[var1].setTexture(getTextureResource(meshsTextureIds[var1]));
               }

               return meshes[var1];
            }

            return (AbstractMesh)meshes[var1].clone();
         }
      }

      System.out.println("ERROR | AEResourceManager.getGeometryResource(" + var0 + ") not found !");
      return null;
   }

   public static void OnRelease() {
      int var0;
      for(var0 = 0; var0 < meshes.length; ++var0) {
         if (meshes[var0] != null) {
            meshes[var0].OnRelease();
            meshes[var0] = null;
         }
      }

      for(var0 = 0; var0 < textures.length; ++var0) {
         if (textures[var0] != null) {
            textures[var0].OnRelease();
            textures[var0] = null;
         }
      }

   }

   public static void initGeometryTranforms() {
      for(int var0 = 0; var0 < meshes.length; ++var0) {
         if (meshes[var0] != null) {
            meshes[var0].setTransform(new Matrix());
         }
      }

   }
}
