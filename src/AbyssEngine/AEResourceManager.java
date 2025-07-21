package AbyssEngine;

import javax.microedition.m3g.Texture2D;

public final class AEResourceManager {
   private static int[] var_c0;
   private static AbstractTexture[] var_ea;
   private static String[] var_142;
   private static boolean[] var_173;
   private static int[] meshIds;
   private static AbstractMesh[] meshes;
   private static int[] radii_;
   private static String[] paths;
   private static boolean[] loadedMeshes;
   private static int[] tranparent_;

   public static void sub_53(int var0, String var1) {
      if (var_142 == null) {
         (var_142 = new String[1])[0] = var1;
         var_ea = new AbstractTexture[1];
         (var_c0 = new int[1])[0] = var0;
         (var_173 = new boolean[1])[0] = false;
      } else {
         String[] var2 = new String[var_142.length + 1];
         System.arraycopy(var_142, 0, var2, 0, var_142.length);
         var2[var_142.length] = var1;
         var_142 = var2;
         AbstractTexture[] var4 = new AbstractTexture[var_ea.length + 1];
         System.arraycopy(var_ea, 0, var4, 0, var_ea.length);
         var_ea = var4;
         int[] var5 = new int[var_c0.length + 1];
         System.arraycopy(var_c0, 0, var5, 0, var_c0.length);
         var5[var_c0.length] = var0;
         var_c0 = var5;
         boolean[] var3 = new boolean[var_173.length + 1];
         System.arraycopy(var_173, 0, var3, 0, var_173.length);
         var3[var_173.length] = false;
         var_173 = var3;
      }
   }

   public static void sub_ad(int var0, String var1, int var2, int var3) {
      if (paths == null) {
         (paths = new String[1])[0] = var1;
         (radii_ = new int[1])[0] = var2;
         meshes = new AbstractMesh[1];
         (meshIds = new int[1])[0] = var0;
         (loadedMeshes = new boolean[1])[0] = false;
         (tranparent_ = new int[1])[0] = var3;
      } else {
         String[] var4 = new String[paths.length + 1];
         System.arraycopy(paths, 0, var4, 0, paths.length);
         var4[paths.length] = var1;
         paths = var4;
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
         int[] var6 = new int[tranparent_.length + 1];
         System.arraycopy(tranparent_, 0, var6, 0, tranparent_.length);
         var6[tranparent_.length] = var3;
         tranparent_ = var6;
      }
   }

   public static void setupSkybox_(int var0, String var1, int var2) {
      sub_ad(9991, var1, -1, 1);
   }

   public static AbstractTexture sub_10b(int var0) {
      for(int var1 = 0; var1 < var_c0.length; ++var1) {
         if (var0 == var_c0[var1]) {
            var_173[var1] = true;
            if (var_ea[var1] == null) {
               String[] var2 = new String[]{var_142[var1]};
               if (var0 == 1) {
                  AbstractTexture[] var10000 = var_ea;
                  AbstractTexture var3 = sub_10b(0);
                  return var10000[var1] = new JSRTexture((JSRTexture)var3);
               }
               
               return var_ea[var1] = new JSRTexture(var2);
            }

            return var_ea[var1];
         }
      }

      return null;
   }
   public static Texture2D getSpecTexture() {
       Texture2D spec = ((JSRTexture)sub_10b(2)).getTexturesArray()[0];
       spec.setFiltering(Texture2D.FILTER_LINEAR, Texture2D.FILTER_LINEAR);
       spec.setBlending(Texture2D.FUNC_ADD);
       return spec;
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
                  String var4 = paths[var1];
                  var10002 = new JSRMesh2(var4);
               } else {
                  var10000 = meshes;
                  var10001 = var1;
                  int var5 = meshIds[var1];
                  String var10003 = paths[var1];
                  int var3 = radii_[var1];
                  String var2 = var10003;
                  var0 = var5;
                  if (var2.endsWith(".m3g"))
                	  var10002 = new JSRMesh(var0, var2, var3);
                  else
                	  var10002 = new AEMesh(var2, var3);
                  //var10002 = var2.endsWith(".m3g") ? new JSRMesh(var0, var2, var3) : new AEMesh(var2, var3);
               }

               var10000[var10001] = (AbstractMesh)var10002;
               if (meshes[var1] != null && tranparent_[var1] != Integer.MIN_VALUE) {
                  meshes[var1].setTexture(sub_10b(tranparent_[var1]));
               }

               return meshes[var1];
            }

            return (AbstractMesh)meshes[var1].sub_2b();
         }
      }

      System.out.println("ERROR | AEResourceManager.getGeometryResource(" + var0 + ") not found !");
      return null;
   }

   public static void destroy() {
      int var0;
      for(var0 = 0; var0 < meshes.length; ++var0) {
         if (meshes[var0] != null) {
            meshes[var0].destroy();
            meshes[var0] = null;
         }
      }

      for(var0 = 0; var0 < var_ea.length; ++var0) {
         if (var_ea[var0] != null) {
            var_ea[var0].destroy();
            var_ea[var0] = null;
         }
      }

   }

   public static void sub_18f() {
      for(int var0 = 0; var0 < meshes.length; ++var0) {
         if (meshes[var0] != null) {
            meshes[var0].sub_8c9(new Matrix());
         }
      }

   }
}
