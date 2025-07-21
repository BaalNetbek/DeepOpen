package AE;

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
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;
import javax.microedition.m3g.World;


import AE.Math.AEVector3D;
import AE.Math.Matrix;
import AE.PaintCanvas.AEGraphics3D;

public final class JSRMesh extends AbstractMesh {
   private static int[] uvOffsets = new int[]{1, 2, 2, 2, 1, 2, 1, 0, 0, 2};
   private static int[] uvRotations = new int[]{0, 0, 0, 0, 1, 0, 0, 1, 0, 1};
   private int animLength;
   private int animStartFrame;
   private int animEndFrame;
   private int animCurrentFrame;
   private long sysTime;
   private boolean hasAnimation;
   private byte animationMode;
   private static Transform localToWorldTransform = new Transform();
   private static float[] m_matrix = new float[16];
   private javax.microedition.m3g.Node[] opaqueNodes;
   private javax.microedition.m3g.Node[] transparentNodes;
   private static PolygonMode opaquePmode;
   private static PolygonMode transparentPmode;
   private static CompositingMode composMode;
   private boolean needsUvFix = false;
   private Texture2D texture = null;
   private static Transform textureTransform = new Transform();
     
   public JSRMesh(int resourceId, String path, int radius) {
      this.resourceId = resourceId;
      initializeMaterials();
  	/*
	 if (this.resourceId == 6781 || this.resourceId == 13999) {

             this.sunLight = new Light();
             this.sunLight.setIntensity(0.9F);
             this.sunLight.setColor(0xffffff);
             this.sunLight.setMode(Light.DIRECTIONAL);
             
             this.sunShine = new Light();
             this.sunShine.setIntensity(2.0F);
             this.sunShine.setColor(0xffffff);
             this.sunShine.setMode(Light.DIRECTIONAL);
             
	 } */
      try {
	
         Object3D[] tree = null;
         if (!path.endsWith(".m3g")) {
            tree = Loader.load(AEFile.readFileBytes(path + ".m3g"), 0);
         } else {
            tree = Loader.load(AEFile.readFileBytes(path), 0);
         }

         for(int i = 0; i < tree.length; ++i) {
            int j;
            javax.microedition.m3g.Node meshNode;
            if (!(tree[i] instanceof World)) {
               if (tree[i] instanceof javax.microedition.m3g.Group) {

                  if ((j = this.getAnimationLength((javax.microedition.m3g.Group)tree[i], 0)) == 0) {
                     for(int var14 = 0; var14 < ((javax.microedition.m3g.Group)tree[i]).getChildCount(); ++var14) {
                        if ((meshNode = ((javax.microedition.m3g.Group)tree[i]).getChild(var14)) instanceof Mesh) {
                           Transform transform = new Transform();
                           meshNode.getTransform(transform);
                           ((javax.microedition.m3g.Group)tree[i]).removeChild(meshNode);
                           meshNode.setTransform(transform);
                           fixNormals((Mesh)meshNode);
                           this.appendNode(meshNode);
                        }
                     }
                  } else {
                     this.appendNode((javax.microedition.m3g.Group)tree[i]);
                     this.animLength = this.animLength < j ? j : this.animLength;
                  }
               }
            } else {
               for(j = 0; j < ((World)tree[i]).getChildCount(); ++j) {
                  javax.microedition.m3g.Node groupNode = ((World)tree[i]).getChild(j);

                  int animLenght = this.getAnimationLength(groupNode, 0);
                  if (groupNode instanceof javax.microedition.m3g.Group) {
                     if (animLenght == 0) {
                        for(int k = 0; k < ((javax.microedition.m3g.Group)groupNode).getChildCount(); ++k) {
                           if ((meshNode = ((javax.microedition.m3g.Group)groupNode).getChild(k)) instanceof Mesh) {
                              Transform transform = new Transform();
                              meshNode.getTransform(transform);
                              ((javax.microedition.m3g.Group)groupNode).removeChild(meshNode);
                              meshNode.setTransform(transform);
                              fixNormals((Mesh)meshNode);
                              this.appendNode(meshNode);
                           }
                        }
                     } else {
                        this.appendNode(groupNode);
                        this.animLength = this.animLength < animLenght ? animLenght : this.animLength;
                     }
                  } else if (groupNode instanceof Mesh) {
                     fixNormals((Mesh)groupNode);
                     this.appendNode(groupNode);
                     this.animLength = this.animLength < animLenght ? animLenght : this.animLength;
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
      } catch (Exception var10) {
         this.opaqueNodes = null;
         this.transparentNodes = null;
      }

      this.radius = radius;
   }

	
	private void fixNormals(Mesh mesh) {
	    VertexBuffer vertexBuffer = mesh.getVertexBuffer();
	    if (vertexBuffer == null) return;
	    
	    VertexArray normalArray = vertexBuffer.getNormals();
	    if (normalArray == null) return; 
	    
	    
	    int vertexCount = normalArray.getVertexCount();
	    int componentCount = normalArray.getComponentCount();
	    if (componentCount != 3) return;
	    int encoding = normalArray.getComponentType(); 
    
	    if (encoding == 1) { // byte
	        byte[] normals = new byte[vertexCount * componentCount];
	        normalArray.get(0, vertexCount, normals);
	        
	        for (int i = 0; i < vertexCount; i++) {
	            int idx = i * 3;

	            byte y = normals[idx + 1];
	            byte z = normals[idx + 2];
	            normals[idx + 1] = z;
	            normals[idx + 2] = (byte) (y != -128 ? -y : 127);
	        }
	        
	        VertexArray rotatedNormals = new VertexArray(vertexCount, componentCount, encoding);
	        rotatedNormals.set(0, vertexCount, normals);
	        vertexBuffer.setNormals(rotatedNormals);
	        vertexBuffer.setTexCoords(1, rotatedNormals, 1f/127f, new float[] {0.5F, 0.5F ,0.5F});
	        
	    } else if (encoding == 2) { // short
	    	System.out.println("Found short encoded normals in mesh: " + resourceId);
	        short[] normals = new short[vertexCount * componentCount];
	        normalArray.get(0, vertexCount, normals);
	        
	        for (int i = 0; i < vertexCount; i++) {
	            int idx = i * 3;
	            short y = normals[idx + 1];
	            short z = normals[idx + 2];
	            normals[idx + 1] = z;
	            normals[idx + 2] = (short)(y != -32768 ? -y : 32767);
	        }
	        

	        VertexArray rotatedNormals = new VertexArray(vertexCount, componentCount, encoding);
	        rotatedNormals.set(0, vertexCount, normals);
	        vertexBuffer.setNormals(rotatedNormals);
	        vertexBuffer.setTexCoords(1, rotatedNormals, 1f/127f, new float[] {0.5F, 0.5F ,0.5F});
	    } else {
		return;
	    }
	    
	    
	}
	
	public void rotateUV(Matrix m) {
	    VertexBuffer vertexBuffer;
	    VertexArray normalArray;
	    if (opaqueNodes != null)
	    for (int i = 0; i < opaqueNodes.length; i++) {
		if (opaqueNodes[i] instanceof Mesh) {
        		vertexBuffer = ((Mesh) opaqueNodes[i]).getVertexBuffer();
        		normalArray = vertexBuffer.getNormals();
        		int vertexCount = normalArray.getVertexCount();
        		byte[] normals = new byte[vertexCount * 3];
        		normalArray.get(0, vertexCount, normals);
        		short[] texcoords = new short[vertexCount * 3];
        	        for (int j = 0; j < vertexCount; j++) {
        	            int idx = j * 3;
        		    int x = ((int)normals[idx])<<5;
        		    int y = ((int)normals[idx + 1])<<5;
        		    int z = ((int)normals[idx + 2])<<5;
        		    AEVector3D v = new AEVector3D(x,y,z);
//        		    AEVector3D lighDir = m.getDirection();
//        		    if (0 < (lighDir.x*v.x >> 12) + (lighDir.y*v.y >> 12) + (lighDir.z*v.z >> 12)) {
//        			texcoords[idx] = 4096;
//                		texcoords[idx + 1] =0;
//                		texcoords[idx + 2] =0;
//        		    }
//        		    else {
        			v = m.transformVectorNoScale(v);
        			texcoords[idx] = (short) v.x;
                		texcoords[idx + 1] =(short) v.y;
                		texcoords[idx + 2] =(short) v.z;
//        		    }
        	        }
        	        normalArray = new VertexArray(vertexCount, 3, 2);
        	        normalArray.set(0,vertexCount, texcoords);
        		vertexBuffer.setTexCoords(1, normalArray, 1.0f/127.0f/2.0f/32.0f, new float[] {0.5F, 0.5F ,0.5F});
		}
	    }
	}
	

   private JSRMesh(JSRMesh var1) {
      initializeMaterials();
      this.radius = var1.radius;
      this.opaqueNodes = var1.opaqueNodes;
      this.transparentNodes = var1.transparentNodes;
      this.renderLayer = var1.renderLayer;
      this.draw = var1.draw;
      this.animLength = var1.animLength;
      this.animStartFrame = var1.animStartFrame;
      this.animEndFrame = var1.animEndFrame;
      this.animCurrentFrame = var1.animCurrentFrame;
      this.sysTime = var1.sysTime;
      this.hasAnimation = var1.hasAnimation;
      this.animationMode = var1.animationMode;
      this.resourceId = var1.resourceId;
      this.needsUvFix = var1.needsUvFix;
      this.texture = var1.texture;
      this.sunLight = var1.sunLight;
      this.sunShine = var1.sunShine;
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
         (composMode = new CompositingMode()).setBlending(CompositingMode.ALPHA_ADD);
         composMode.setDepthTestEnable(true);
         composMode.setDepthWriteEnable(false);
      }

   }

   private void appendNode(javax.microedition.m3g.Node var1) {
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
         if (this.transparentNodes == null) {
            this.transparentNodes = new javax.microedition.m3g.Node[1];
            this.transparentNodes[0] = var1;
         } else {
            var7 = new javax.microedition.m3g.Node[this.transparentNodes.length + 1];
            System.arraycopy(this.transparentNodes, 0, var7, 0, this.transparentNodes.length);
            var7[this.transparentNodes.length] = var1;
            this.transparentNodes = var7;
         }
      } else if (this.opaqueNodes == null) {
         this.opaqueNodes = new javax.microedition.m3g.Node[1];
         this.opaqueNodes[0] = var1;
      } else {
         var7 = new javax.microedition.m3g.Node[this.opaqueNodes.length + 1];
         System.arraycopy(this.opaqueNodes, 0, var7, 0, this.opaqueNodes.length);
         var7[this.opaqueNodes.length] = var1;
         this.opaqueNodes = var7;
      }
   }

   private int getAnimationLength(javax.microedition.m3g.Node var1, int var2) {
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
               var2 = this.getAnimationLength((javax.microedition.m3g.Group)var7, var2);
            }
         }
      }

      return var2;
   }

   public final void render() {
      /* if (this.light != null) {
	   AEGraphics3D.graphics3D.resetLights();
	         this.matrix.scaledToFloatArray(m_matrix);
	         var_24c.set(m_matrix);
	   AEGraphics3D.graphics3D.addLight(light, var_24c);

		  
	      }*/
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
         if (this.animLength > 0) {
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
      /* if (this.light != null) {
	   AEGraphics3D.graphics3D.resetLights();
	   AEGraphics3D.graphics3D.addLight(light, var_24c);

		  
	      }*/
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
         if (this.animLength > 0) {
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
      this.animStartFrame = (var1 < 0 || var1 > this.animLength) && !this.needsUvFix ? 0 : var1;
      this.animEndFrame = (var2 < 0 || var2 > this.animLength) && !this.needsUvFix ? this.animLength : var2;
      this.animCurrentFrame = this.animCurrentFrame >= var1 && !this.needsUvFix ? (this.animCurrentFrame > var2 ? var2 : this.animCurrentFrame) : var1;
      this.sysTime = -1L;
   }

   public final void setAnimationMode(byte var1) {
      if (!this.needsUvFix) {
         this.animationMode = var1;
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

   private void setupMaterial(javax.microedition.m3g.Node var1, Texture2D[] var2, boolean transparent) {
      int var4;
      if (var1 instanceof Mesh) {
         var4 = ((Mesh)var1).getUserID();

         for(int var9 = 0; var9 < ((Mesh)var1).getSubmeshCount(); ++var9) {
            Appearance var6 = ((Mesh)var1).getAppearance(var9);
            if (transparent) {
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
            (var8 = new Material()).setShininess(30.0F);
            if (transparent) {
               var8.setColor(Material.EMISSIVE, 0xffffffff);
            }
            else {
            	var8.setColor(Material.AMBIENT, 0xffffff);
            	var8.setColor(Material.DIFFUSE, 0xff888888);
            	var8.setColor(Material.EMISSIVE, 0);
            	var8.setColor(Material.SPECULAR, 0xffffffff);
            }

            var6.setMaterial(var8);
            if (var6.getTexture(0) != null && var2 != null) {
               if (var4 < var2.length) {
                  if (var7) {
                     this.texture = (Texture2D)((Texture2D)var2[var4].duplicate());
                     var6.setTexture(0, this.texture);
                     var6.setTexture(1, AEResourceManager.getSpecTexture());
                  } else {
                     var6.setTexture(0, var2[var4]);
                     var6.setTexture(1, AEResourceManager.getSpecTexture());
                  }
               } else if (var7) {
                  this.texture = (Texture2D)((Texture2D)var2[0].duplicate());
                  var6.setTexture(0, this.texture);
                  var6.setTexture(1, AEResourceManager.getSpecTexture());
               } else {
                  var6.setTexture(0, var2[0]);
                  var6.setTexture(1, AEResourceManager.getSpecTexture());
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
               javax.microedition.m3g.Node var5 = ((javax.microedition.m3g.Group)var1).getChild(var4);
               this.setupMaterial(var5, var2, transparent);
            }
         }

      }
   }

   public final void OnRelease() {
   }
}
