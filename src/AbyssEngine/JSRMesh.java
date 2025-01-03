package AbyssEngine;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.KeyframeSequence;
import javax.microedition.m3g.Loader;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.World;

public final class JSRMesh extends AbstractMesh {
   private static int[] var_56 = new int[]{1, 2, 2, 2, 1, 2, 1, 0, 0, 2};
   private static int[] var_10e = new int[]{0, 0, 0, 0, 1, 0, 0, 1, 0, 1};
   private int var_137;
   private int var_15f;
   private int var_174;
   private int var_18c;
   private long var_1cf;
   private boolean var_1e9;
   private byte var_236;
   private static Transform var_24c = new Transform();
   private static float[] m_matrix = new float[16];
   private javax.microedition.m3g.Node[] var_2f4;
   private javax.microedition.m3g.Node[] var_338;
   private static PolygonMode var_344;
   private static PolygonMode var_38a;
   private static CompositingMode var_3cc;
   private boolean var_41c = false;
   private Texture2D var_45d = null;
   private static Transform var_4aa = new Transform();

   public JSRMesh(int var1, String var2, int var3) {
      this.var_1fa = var1;
      sub_31();
      var2 = var2;
      JSRMesh var11 = this;

      try {
         Object3D[] var4 = null;
         if (!var2.endsWith(".m3g")) {
            var4 = Loader.load(AEImage.loadFileBytes(var2 + ".m3g"), 0);
         } else {
            var4 = Loader.load(AEImage.loadFileBytes(var2), 0);
         }

         for(int var12 = 0; var12 < var4.length; ++var12) {
            int var5;
            javax.microedition.m3g.Node var16;
            if (!(var4[var12] instanceof World)) {
               if (var4[var12] instanceof javax.microedition.m3g.Group) {
                  boolean var13 = false;
                  if ((var5 = var11.sub_85((javax.microedition.m3g.Group)var4[var12], 0)) == 0) {
                     for(int var14 = 0; var14 < ((javax.microedition.m3g.Group)var4[var12]).getChildCount(); ++var14) {
                        if ((var16 = ((javax.microedition.m3g.Group)var4[var12]).getChild(var14)) instanceof Mesh) {
                           Transform var17 = new Transform();
                           var16.getTransform(var17);
                           ((javax.microedition.m3g.Group)var4[var12]).removeChild(var16);
                           var16.setTransform(var17);
                           var11.sub_46(var16);
                        }
                     }
                  } else {
                     var11.sub_46((javax.microedition.m3g.Group)var4[var12]);
                     var11.var_137 = var11.var_137 < var5 ? var5 : var11.var_137;
                  }
               }
            } else {
               for(var5 = 0; var5 < ((World)var4[var12]).getChildCount(); ++var5) {
                  javax.microedition.m3g.Node var6 = ((World)var4[var12]).getChild(var5);
                  boolean var7 = false;
                  int var15 = var11.sub_85(var6, 0);
                  if (var6 instanceof javax.microedition.m3g.Group) {
                     if (var15 == 0) {
                        for(int var8 = 0; var8 < ((javax.microedition.m3g.Group)var6).getChildCount(); ++var8) {
                           if ((var16 = ((javax.microedition.m3g.Group)var6).getChild(var8)) instanceof Mesh) {
                              Transform var9 = new Transform();
                              var16.getTransform(var9);
                              ((javax.microedition.m3g.Group)var6).removeChild(var16);
                              var16.setTransform(var9);
                              var11.sub_46(var16);
                           }
                        }
                     } else {
                        var11.sub_46(var6);
                        var11.var_137 = var11.var_137 < var15 ? var15 : var11.var_137;
                     }
                  } else if (var6 instanceof Mesh) {
                     var11.sub_46(var6);
                     var11.var_137 = var11.var_137 < var15 ? var15 : var11.var_137;
                  }
               }
            }
         }

         if (var11.var_137 != 0) {
            var11.var_15f = 0;
            var11.var_174 = var11.var_137;
            var11.var_18c = 0;
            var11.var_1cf = -1L;
            var11.var_1e9 = true;
            var11.var_236 = 2;
         }
      } catch (Exception var10) {
         this.var_2f4 = null;
         this.var_338 = null;
      }

      this.var_1ef = var3;
   }

   private JSRMesh(JSRMesh var1) {
      sub_31();
      this.var_1ef = var1.var_1ef;
      this.var_2f4 = var1.var_2f4;
      this.var_338 = var1.var_338;
      this.flag_ = var1.flag_;
      this.draw = var1.draw;
      this.var_137 = var1.var_137;
      this.var_15f = var1.var_15f;
      this.var_174 = var1.var_174;
      this.var_18c = var1.var_18c;
      this.var_1cf = var1.var_1cf;
      this.var_1e9 = var1.var_1e9;
      this.var_236 = var1.var_236;
      this.var_1fa = var1.var_1fa;
      this.var_41c = var1.var_41c;
      this.var_45d = var1.var_45d;
   }

   private static void sub_31() {
      if (var_344 == null) {
         (var_344 = new PolygonMode()).setCulling(160);
         var_344.setShading(165);
      }

      if (var_38a == null) {
         (var_38a = new PolygonMode()).setCulling(162);
         var_38a.setShading(164);
      }

      if (var_3cc == null) {
         (var_3cc = new CompositingMode()).setBlending(64);
         var_3cc.setDepthTestEnable(true);
         var_3cc.setDepthWriteEnable(false);
      }

   }

   private void sub_46(javax.microedition.m3g.Node var1) {
      boolean var10000;
      label85: {
         javax.microedition.m3g.Node var2 = var1;
         int var3;
         if (!(var1 instanceof javax.microedition.m3g.Group)) {
            if (var1 instanceof Mesh) {
               for(var3 = 0; var3 < ((Mesh)var2).getSubmeshCount(); ++var3) {
                  Material var8;
                  if ((var8 = ((Mesh)var2).getAppearance(var3).getMaterial()) != null && var8.getColor(8192) == 65280 || var8.getColor(8192) == 255) {
                     var10000 = true;
                     break label85;
                  }
               }
            }
         } else {
            for(var3 = 0; var3 < ((javax.microedition.m3g.Group)var2).getChildCount(); ++var3) {
               javax.microedition.m3g.Node var4;
               if ((var4 = ((javax.microedition.m3g.Group)var2).getChild(var3)) instanceof Mesh) {
                  for(int var5 = 0; var5 < ((Mesh)var4).getSubmeshCount(); ++var5) {
                     Material var6;
                     if ((var6 = ((Mesh)var4).getAppearance(var5).getMaterial()) != null && var6.getColor(8192) == 65280 || var6.getColor(8192) == 255) {
                        var10000 = true;
                        break label85;
                     }
                  }
               }
            }
         }

         var10000 = false;
      }

      javax.microedition.m3g.Node[] var7;
      if (var10000) {
         if (this.var_338 == null) {
            this.var_338 = new javax.microedition.m3g.Node[1];
            this.var_338[0] = var1;
         } else {
            var7 = new javax.microedition.m3g.Node[this.var_338.length + 1];
            System.arraycopy(this.var_338, 0, var7, 0, this.var_338.length);
            var7[this.var_338.length] = var1;
            this.var_338 = var7;
         }
      } else if (this.var_2f4 == null) {
         this.var_2f4 = new javax.microedition.m3g.Node[1];
         this.var_2f4[0] = var1;
      } else {
         var7 = new javax.microedition.m3g.Node[this.var_2f4.length + 1];
         System.arraycopy(this.var_2f4, 0, var7, 0, this.var_2f4.length);
         var7[this.var_2f4.length] = var1;
         this.var_2f4 = var7;
      }
   }

   private int sub_85(javax.microedition.m3g.Node var1, int var2) {
      int var3;
      for(var3 = 0; var3 < var1.getAnimationTrackCount(); ++var3) {
         KeyframeSequence var4;
         if ((var4 = var1.getAnimationTrack(var3).getKeyframeSequence()).getDuration() > var2) {
            var2 = var4.getDuration();
         }
      }

      if (var1 instanceof javax.microedition.m3g.Group) {
         for(var3 = 0; var3 < ((javax.microedition.m3g.Group)var1).getChildCount(); ++var3) {
            javax.microedition.m3g.Node var7 = ((javax.microedition.m3g.Group)var1).getChild(var3);

            for(int var5 = 0; var5 < var7.getAnimationTrackCount(); ++var5) {
               KeyframeSequence var6;
               if ((var6 = var7.getAnimationTrack(var5).getKeyframeSequence()).getDuration() > var2) {
                  var2 = var6.getDuration();
               }
            }

            if (var7 instanceof javax.microedition.m3g.Group) {
               var2 = this.sub_85((javax.microedition.m3g.Group)var7, var2);
            }
         }
      }

      return var2;
   }

   public final void render() {
      if (this.var_2f4 != null) {
         if (this.var_41c) {
            if (this.var_1fa == 6769) {
               this.var_45d.setTranslation(0.0F, (float)this.var_15f * 0.0118F, 0.0F);
            } else if (this.var_1fa == 6778) {
               this.var_45d.setTranslation((float)(this.var_15f % 5) * 0.01171875F, (float)(this.var_15f / 5) * 0.01171875F, 0.0F);
            } else {
               this.var_45d.setTranslation(0.0F, (float)this.var_15f * 0.0625F, 0.0F);
            }
         }

         this.matrix.scaledToFloatArray(m_matrix);
         var_24c.set(m_matrix);
         int var1;
         if (this.var_137 > 0) {
            for(var1 = 0; var1 < this.var_2f4.length; ++var1) {
               if (this.var_236 == 3) {
                  this.var_2f4[var1].animate(this.var_174 - this.var_18c);
               } else {
                  try {
                     this.var_2f4[var1].animate(this.var_18c);
                  } catch (Exception var2) {
                     System.out.println("JSRMesh.render() Exception id: " + this.var_1fa);
                  }
               }

               AEGraphics3D.graphics3D.render(this.var_2f4[var1], var_24c);
            }

            return;
         }

         for(var1 = 0; var1 < this.var_2f4.length; ++var1) {
            AEGraphics3D.graphics3D.render(this.var_2f4[var1], var_24c);
         }
      }

   }

   public final void renderTransparent() {
      if (this.var_338 != null) {
         if (this.var_41c) {
            var_4aa.setIdentity();
            this.var_45d.setTransform(var_4aa);
            this.var_45d.setScale(1.0F, 1.0F, 1.0F);
            if (this.var_1fa == 6769) {
               this.var_45d.setTranslation(0.0F, (float)this.var_15f * 0.0118F, 0.0F);
            } else if (this.var_1fa == 6781) {
               switch(this.var_15f) {
               case 10:
                  this.var_45d.setTranslation(0.4375F, 0.9375F, 0.0F);
                  this.var_45d.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
                  break;
               case 11:
                  this.var_45d.setTranslation(0.375F, 0.9375F, 0.0F);
                  this.var_45d.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
                  break;
               case 12:
                  this.var_45d.setTranslation(0.5F, 1.0F, 0.0F);
                  this.var_45d.setOrientation(180.0F, 1.0F, 0.0F, 0.0F);
                  break;
               default:
                  this.var_45d.setTranslation(0.0F, (float)var_56[this.var_15f] * 0.0625F, 0.0F);
                  this.var_45d.setOrientation((float)var_10e[this.var_15f] * 180.0F, 1.0F, 0.0F, 0.0F);
                  if (var_10e[this.var_15f] > 0) {
                     this.var_45d.translate(1.0625F, 0.0625F, 0.0F);
                     this.var_45d.setScale(-1.0F, 1.0F, 1.0F);
                  }
               }
            } else {
               this.var_45d.setTranslation(0.0F, (float)this.var_15f * 0.0625F, 0.0F);
            }
         }

         this.matrix.scaledToFloatArray(m_matrix);
         var_24c.set(m_matrix);
         int var1;
         if (this.var_137 > 0) {
            for(var1 = 0; var1 < this.var_338.length; ++var1) {
               if (this.var_236 == 3) {
                  this.var_338[var1].animate(this.var_174 - this.var_18c);
               } else {
                  try {
                     this.var_338[var1].animate(this.var_18c);
                  } catch (Exception var3) {
                     System.out.println(var3.getMessage() + "\n\n" + "id: " + this.var_1fa);
                  }
               }

               AEGraphics3D.graphics3D.render(this.var_338[var1], var_24c);
            }

            return;
         }

         for(var1 = 0; var1 < this.var_338.length; ++var1) {
            AEGraphics3D.graphics3D.render(this.var_338[var1], var_24c);
         }
      }

   }

   public final void sub_181(long var1) {
      if (this.var_1e9) {
         if (this.var_1cf == -1L) {
            this.var_1cf = var1;
         }

         this.var_18c = this.var_15f + (int)((var1 - this.var_1cf) / (long)this.var_45);
         if (this.var_18c > this.var_174) {
            if (this.var_236 == 2) {
               this.var_18c = this.var_15f;
               this.var_1cf = var1 - (long)((this.var_18c - this.var_15f) * this.var_45);
               return;
            }

            this.var_1e9 = false;
            this.var_18c = this.var_174;
            this.var_1cf = -1L;
         }
      }

   }

   public final int sub_942() {
      return this.var_18c;
   }

   public final void sub_918(int var1) {
      this.var_45 = var1 > 0 ? var1 : 1;
   }

   public final void sub_980(int var1, int var2) {
      this.var_15f = (var1 < 0 || var1 > this.var_137) && !this.var_41c ? 0 : var1;
      this.var_174 = (var2 < 0 || var2 > this.var_137) && !this.var_41c ? this.var_137 : var2;
      this.var_18c = this.var_18c >= var1 && !this.var_41c ? (this.var_18c > var2 ? var2 : this.var_18c) : var1;
      this.var_1cf = -1L;
   }

   public final void sub_9aa(byte var1) {
      if (!this.var_41c) {
         this.var_236 = var1;
         this.var_1cf = -1L;
         this.var_1e9 = this.var_137 > 0;
      }
   }

   public final void sub_a04() {
      this.var_1e9 = false;
   }

   public final boolean sub_a37() {
      return this.var_1e9;
   }

   public final GraphNode sub_2b() {
      return new JSRMesh(this);
   }

   public final void setTexture(AbstractTexture var1) {
      int var2;
      if (this.var_2f4 != null) {
         for(var2 = 0; var2 < this.var_2f4.length; ++var2) {
            this.sub_b6(this.var_2f4[var2], ((JSRTexture)var1).getTexturesArray(), false);
         }
      }

      if (this.var_338 != null) {
         for(var2 = 0; var2 < this.var_338.length; ++var2) {
            this.sub_b6(this.var_338[var2], ((JSRTexture)var1).getTexturesArray(), true);
         }
      }

   }

   private void sub_b6(javax.microedition.m3g.Node var1, Texture2D[] var2, boolean var3) {
      int var4;
      if (var1 instanceof Mesh) {
         var4 = ((Mesh)var1).getUserID();

         for(int var9 = 0; var9 < ((Mesh)var1).getSubmeshCount(); ++var9) {
            Appearance var6 = ((Mesh)var1).getAppearance(var9);
            if (var3) {
               var6.setCompositingMode(var_3cc);
               var6.setPolygonMode(var_38a);
            } else {
               var6.setCompositingMode((CompositingMode)null);
               var6.setPolygonMode(var_344);
            }

            boolean var7 = false;
            if (var6.getMaterial() != null) {
               var7 = var6.getMaterial().getColor(8192) == 16711680 || var6.getMaterial().getColor(8192) == 255;
            }

            var6.setMaterial((Material)null);
            Material var8;
            (var8 = new Material()).setShininess(50.0F);
            if (var3) {
               var8.setColor(4096, -1);
            }

            var6.setMaterial(var8);
            if (var6.getTexture(0) != null && var2 != null) {
               if (var4 < var2.length) {
                  if (var7) {
                     this.var_45d = (Texture2D)((Texture2D)var2[var4].duplicate());
                     var6.setTexture(0, this.var_45d);
                  } else {
                     var6.setTexture(0, var2[var4]);
                  }
               } else if (var7) {
                  this.var_45d = (Texture2D)((Texture2D)var2[0].duplicate());
                  var6.setTexture(0, this.var_45d);
               } else {
                  var6.setTexture(0, var2[0]);
               }
            }

            ((Mesh)var1).setAppearance(var9, var6);
            if (var7) {
               this.var_41c = true;
            }
         }

      } else {
         if (var1 instanceof javax.microedition.m3g.Group) {
            for(var4 = 0; var4 < ((javax.microedition.m3g.Group)var1).getChildCount(); ++var4) {
               javax.microedition.m3g.Node var5 = ((javax.microedition.m3g.Group)var1).getChild(var4);
               this.sub_b6(var5, var2, var3);
            }
         }

      }
   }

   public final void destroy() {
   }
}
