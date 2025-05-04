package AE;

import AE.PaintCanvas.AEGraphics3D;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
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

public final class JSRMesh extends AbstractMesh {
   private static int[] uvOffsets = new int[]{1, 2, 2, 2, 1, 2, 1, 0, 0, 2};
   private static int[] uvRotations = new int[]{0, 0, 0, 0, 1, 0, 0, 1, 0, 1};
   private int animLenght;
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

   public JSRMesh(int var1, String var2, int var3) {
      this.resourceId = var1;
      initializeMaterials();
      var2 = var2;
      JSRMesh var11 = this;

      try {
         Object3D[] var4 = null;
         if (!var2.endsWith(".m3g")) {
            var4 = Loader.load(AEFile.readFileBytes(var2 + ".m3g"), 0);
         } else {
            var4 = Loader.load(AEFile.readFileBytes(var2), 0);
         }

         for(int var12 = 0; var12 < var4.length; ++var12) {
            int var5;
            Node var16;
            if (!(var4[var12] instanceof World)) {
               if (var4[var12] instanceof javax.microedition.m3g.Group) {
                  boolean var13 = false;
                  if ((var5 = var11.getAnimationLenght((javax.microedition.m3g.Group)var4[var12], 0)) == 0) {
                     for(int var14 = 0; var14 < ((javax.microedition.m3g.Group)var4[var12]).getChildCount(); ++var14) {
                        if ((var16 = ((javax.microedition.m3g.Group)var4[var12]).getChild(var14)) instanceof Mesh) {
                           Transform var17 = new Transform();
                           var16.getTransform(var17);
                           ((javax.microedition.m3g.Group)var4[var12]).removeChild(var16);
                           var16.setTransform(var17);
                           var11.appendNode(var16);
                        }
                     }
                  } else {
                     var11.appendNode((javax.microedition.m3g.Group)var4[var12]);
                     var11.animLenght = var11.animLenght < var5 ? var5 : var11.animLenght;
                  }
               }
            } else {
               for(var5 = 0; var5 < ((World)var4[var12]).getChildCount(); ++var5) {
                  Node var6 = ((World)var4[var12]).getChild(var5);
                  boolean var7 = false;
                  int var15 = var11.getAnimationLenght(var6, 0);
                  if (var6 instanceof javax.microedition.m3g.Group) {
                     if (var15 == 0) {
                        for(int var8 = 0; var8 < ((javax.microedition.m3g.Group)var6).getChildCount(); ++var8) {
                           if ((var16 = ((javax.microedition.m3g.Group)var6).getChild(var8)) instanceof Mesh) {
                              Transform var9 = new Transform();
                              var16.getTransform(var9);
                              ((javax.microedition.m3g.Group)var6).removeChild(var16);
                              var16.setTransform(var9);
                              var11.appendNode(var16);
                           }
                        }
                     } else {
                        var11.appendNode(var6);
                        var11.animLenght = var11.animLenght < var15 ? var15 : var11.animLenght;
                     }
                  } else if (var6 instanceof Mesh) {
                     var11.appendNode(var6);
                     var11.animLenght = var11.animLenght < var15 ? var15 : var11.animLenght;
                  }
               }
            }
         }

         if (var11.animLenght != 0) {
            var11.animStartFrame = 0;
            var11.animEndFrame = var11.animLenght;
            var11.animCurrentFrame = 0;
            var11.sysTime = -1L;
            var11.hasAnimation = true;
            var11.animationMode = 2;
         }
      } catch (Exception var10) {
         this.opaqueNodes = null;
         this.transparentNodes = null;
      }

      this.radius = var3;
   }

   private JSRMesh(JSRMesh var1) {
      initializeMaterials();
      this.radius = var1.radius;
      this.opaqueNodes = var1.opaqueNodes;
      this.transparentNodes = var1.transparentNodes;
      this.renderLayer = var1.renderLayer;
      this.draw = var1.draw;
      this.animLenght = var1.animLenght;
      this.animStartFrame = var1.animStartFrame;
      this.animEndFrame = var1.animEndFrame;
      this.animCurrentFrame = var1.animCurrentFrame;
      this.sysTime = var1.sysTime;
      this.hasAnimation = var1.hasAnimation;
      this.animationMode = var1.animationMode;
      this.resourceId = var1.resourceId;
      this.needsUvFix = var1.needsUvFix;
      this.texture = var1.texture;
   }

   private static void initializeMaterials() {
      if (opaquePmode == null) {
         (opaquePmode = new PolygonMode()).setCulling(160);
         opaquePmode.setShading(165);
      }

      if (transparentPmode == null) {
         (transparentPmode = new PolygonMode()).setCulling(162);
         transparentPmode.setShading(164);
      }

      if (composMode == null) {
         (composMode = new CompositingMode()).setBlending(64);
         composMode.setDepthTestEnable(true);
         composMode.setDepthWriteEnable(false);
      }

   }

   private void appendNode(Node var1) {
      boolean var10000;
      label85: {
         Node var2 = var1;
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
               Node var4;
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

      Node[] var7;
      if (var10000) {
         if (this.transparentNodes == null) {
            this.transparentNodes = new Node[1];
            this.transparentNodes[0] = var1;
         } else {
            var7 = new Node[this.transparentNodes.length + 1];
            System.arraycopy(this.transparentNodes, 0, var7, 0, this.transparentNodes.length);
            var7[this.transparentNodes.length] = var1;
            this.transparentNodes = var7;
         }
      } else if (this.opaqueNodes == null) {
         this.opaqueNodes = new Node[1];
         this.opaqueNodes[0] = var1;
      } else {
         var7 = new Node[this.opaqueNodes.length + 1];
         System.arraycopy(this.opaqueNodes, 0, var7, 0, this.opaqueNodes.length);
         var7[this.opaqueNodes.length] = var1;
         this.opaqueNodes = var7;
      }
   }

   private int getAnimationLenght(Node var1, int var2) {
      int var3;
      for(var3 = 0; var3 < var1.getAnimationTrackCount(); ++var3) {
         KeyframeSequence var4;
         if ((var4 = var1.getAnimationTrack(var3).getKeyframeSequence()).getDuration() > var2) {
            var2 = var4.getDuration();
         }
      }

      if (var1 instanceof javax.microedition.m3g.Group) {
         for(var3 = 0; var3 < ((javax.microedition.m3g.Group)var1).getChildCount(); ++var3) {
            Node var7 = ((javax.microedition.m3g.Group)var1).getChild(var3);

            for(int var5 = 0; var5 < var7.getAnimationTrackCount(); ++var5) {
               KeyframeSequence var6;
               if ((var6 = var7.getAnimationTrack(var5).getKeyframeSequence()).getDuration() > var2) {
                  var2 = var6.getDuration();
               }
            }

            if (var7 instanceof javax.microedition.m3g.Group) {
               var2 = this.getAnimationLenght((javax.microedition.m3g.Group)var7, var2);
            }
         }
      }

      return var2;
   }

   public final void render() {
      if (this.opaqueNodes != null) {
         if (this.needsUvFix) {
            if (this.resourceId == 6769) {
               this.texture.setTranslation(0.0F, (float)this.animStartFrame * 0.0118F, 0.0F);
            } else if (this.resourceId == 6778) {
               this.texture.setTranslation((float)(this.animStartFrame % 5) * 0.01171875F, (float)(this.animStartFrame / 5) * 0.01171875F, 0.0F);
            } else {
               this.texture.setTranslation(0.0F, (float)this.animStartFrame * 0.0625F, 0.0F);
            }
         }

         this.matrix.toFloatArray(m_matrix);
         localToWorldTransform.set(m_matrix);
         int var1;
         if (this.animLenght > 0) {
            for(var1 = 0; var1 < this.opaqueNodes.length; ++var1) {
               if (this.animationMode == 3) {
                  this.opaqueNodes[var1].animate(this.animEndFrame - this.animCurrentFrame);
               } else {
                  try {
                     this.opaqueNodes[var1].animate(this.animCurrentFrame);
                  } catch (Exception var2) {
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
            if (this.resourceId == 6769) {
               this.texture.setTranslation(0.0F, (float)this.animStartFrame * 0.0118F, 0.0F);
            } else if (this.resourceId == 6781) {
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
                  this.texture.setTranslation(0.0F, (float)uvOffsets[this.animStartFrame] * 0.0625F, 0.0F);
                  this.texture.setOrientation((float)uvRotations[this.animStartFrame] * 180.0F, 1.0F, 0.0F, 0.0F);
                  if (uvRotations[this.animStartFrame] > 0) {
                     this.texture.translate(1.0625F, 0.0625F, 0.0F);
                     this.texture.setScale(-1.0F, 1.0F, 1.0F);
                  }
               }
            } else {
               this.texture.setTranslation(0.0F, (float)this.animStartFrame * 0.0625F, 0.0F);
            }
         }

         this.matrix.toFloatArray(m_matrix);
         localToWorldTransform.set(m_matrix);
         int var1;
         if (this.animLenght > 0) {
            for(var1 = 0; var1 < this.transparentNodes.length; ++var1) {
               if (this.animationMode == 3) {
                  this.transparentNodes[var1].animate(this.animEndFrame - this.animCurrentFrame);
               } else {
                  try {
                     this.transparentNodes[var1].animate(this.animCurrentFrame);
                  } catch (Exception var3) {
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

   public final void update(long var1) {
      if (this.hasAnimation) {
         if (this.sysTime == -1L) {
            this.sysTime = var1;
         }

         this.animCurrentFrame = this.animStartFrame + (int)((var1 - this.sysTime) / (long)this.animationFrameTime);
         if (this.animCurrentFrame > this.animEndFrame) {
            if (this.animationMode == 2) {
               this.animCurrentFrame = this.animStartFrame;
               this.sysTime = var1 - (long)((this.animCurrentFrame - this.animStartFrame) * this.animationFrameTime);
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

   public final void setAnimationSpeed(int var1) {
      this.animationFrameTime = var1 > 0 ? var1 : 1;
   }

   public final void setAnimationRangeInTime(int var1, int var2) {
      this.animStartFrame = (var1 < 0 || var1 > this.animLenght) && !this.needsUvFix ? 0 : var1;
      this.animEndFrame = (var2 < 0 || var2 > this.animLenght) && !this.needsUvFix ? this.animLenght : var2;
      this.animCurrentFrame = this.animCurrentFrame >= var1 && !this.needsUvFix ? (this.animCurrentFrame > var2 ? var2 : this.animCurrentFrame) : var1;
      this.sysTime = -1L;
   }

   public final void setAnimationMode(byte var1) {
      if (!this.needsUvFix) {
         this.animationMode = var1;
         this.sysTime = -1L;
         this.hasAnimation = this.animLenght > 0;
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

   public final void setTexture(ITexture var1) {
      int var2;
      if (this.opaqueNodes != null) {
         for(var2 = 0; var2 < this.opaqueNodes.length; ++var2) {
            this.setupMaterial(this.opaqueNodes[var2], ((JSRTexture)var1).getTexturesArray(), false);
         }
      }

      if (this.transparentNodes != null) {
         for(var2 = 0; var2 < this.transparentNodes.length; ++var2) {
            this.setupMaterial(this.transparentNodes[var2], ((JSRTexture)var1).getTexturesArray(), true);
         }
      }

   }

   private void setupMaterial(Node var1, Texture2D[] var2, boolean var3) {
      int var4;
      if (var1 instanceof Mesh) {
         var4 = ((Mesh)var1).getUserID();

         for(int var9 = 0; var9 < ((Mesh)var1).getSubmeshCount(); ++var9) {
            Appearance var6 = ((Mesh)var1).getAppearance(var9);
            if (var3) {
               var6.setCompositingMode(composMode);
               var6.setPolygonMode(transparentPmode);
            } else {
               var6.setCompositingMode((CompositingMode)null);
               var6.setPolygonMode(opaquePmode);
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
                     this.texture = (Texture2D)((Texture2D)var2[var4].duplicate());
                     var6.setTexture(0, this.texture);
                  } else {
                     var6.setTexture(0, var2[var4]);
                  }
               } else if (var7) {
                  this.texture = (Texture2D)((Texture2D)var2[0].duplicate());
                  var6.setTexture(0, this.texture);
               } else {
                  var6.setTexture(0, var2[0]);
               }
            }

            ((Mesh)var1).setAppearance(var9, var6);
            if (var7) {
               this.needsUvFix = true;
            }
         }

      } else {
         if (var1 instanceof javax.microedition.m3g.Group) {
            for(var4 = 0; var4 < ((javax.microedition.m3g.Group)var1).getChildCount(); ++var4) {
               Node var5 = ((javax.microedition.m3g.Group)var1).getChild(var4);
               this.setupMaterial(var5, var2, var3);
            }
         }

      }
   }

   public final void OnRelease() {
   }
}
