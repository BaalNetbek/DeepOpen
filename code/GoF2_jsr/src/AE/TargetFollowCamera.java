package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public final class TargetFollowCamera implements CameraController_ {
   private GraphNode target;
   private GraphNode targetGrandParent;
   private GraphNode camera;
   private AEVector3D cameraUp;
   private AEVector3D cameraDir;
   private AEVector3D cameraPos;
   private AEVector3D cameraRight;
   private AEVector3D unused3fb_;
   private boolean followTarget;
   private AEVector3D cameraRotation_;
   private AEVector3D cameraPositionOffset;
   private LookAtCamera lookAtCam;
   private boolean lockedPosition;
   private boolean flipCameraDirection;
   private AEVector3D lastCameraUp;
   private AEVector3D lastCameraRight;
   private AEVector3D lastCameraDir;
   private boolean isShaking;
   private int shakeCounter;
   private int frametime;
   private boolean dirty;

   public TargetFollowCamera(GraphNode var1, GraphNode var2) {
      this(var1, var2, (AEVector3D)null);
   }

   private TargetFollowCamera(GraphNode var1, GraphNode var2, AEVector3D var3) {
      this.cameraRotation_ = new AEVector3D();
      this.cameraPositionOffset = new AEVector3D();
      this.lockedPosition = false;
      this.lastCameraUp = new AEVector3D();
      this.lastCameraRight = new AEVector3D();
      this.lastCameraDir = new AEVector3D();
      this.target = var1;
      this.camera = var2;
      this.followTarget = true;
      this.unused3fb_ = null;
      if (this.cameraUp == null) {
         this.cameraUp = new AEVector3D();
      }

      if (this.cameraDir == null) {
         this.cameraDir = new AEVector3D();
      }

      if (this.cameraPos == null) {
         this.cameraPos = new AEVector3D();
      }

      if (this.cameraRight == null) {
         this.cameraRight = new AEVector3D();
      }

      if (var1 != null && var2 != null) {
         this.lookAtCam = new LookAtCamera(var1, var2);

         for(this.targetGrandParent = var1; this.targetGrandParent.getGroup() != null; this.targetGrandParent = this.targetGrandParent.getGroup()) {
         }

         Object var4;
         for(var4 = var2; ((GraphNode)var4).getGroup() != null; var4 = ((GraphNode)var4).getGroup()) {
         }

         if (var4 == this.targetGrandParent) {
            this.targetGrandParent = null;
         }

         this.flipCameraDirection = var2 instanceof Camera;
         this.cameraPositionOffset = var2.getPostition().subtract(var1.getPostition(), new AEVector3D());
         this.dirty = true;
      }
   }

   public final void setTarget(GraphNode var1) {
      this.target = var1;
      if (this.lookAtCam == null && var1 != null) {
         this.lookAtCam = new LookAtCamera(var1, this.camera);
      }

      if (this.lookAtCam != null) {
         this.lookAtCam.setTarget(var1);
      }

   }

   public final void OnRelease(GraphNode var1) {
      this.camera = null;
      if (this.lookAtCam != null) {
         this.lookAtCam.setCamera((GraphNode)null);
      }

      this.flipCameraDirection = null instanceof Camera;
   }

   public final GraphNode getCamera() {
      return this.camera;
   }

   public final void setCameraPosition(AEVector3D var1) {
      this.cameraPos.set(var1);
   }

   public final void lockPosition() {
      this.lockedPosition = true;
      this.cameraPos = this.target.getLocalTransform().transformVector2(this.cameraPositionOffset, this.cameraPos);
      this.camera.moveTo(this.cameraPos);
   }

   public final void followTargetPosition() {
      this.lockedPosition = false;
   }

   public final void update() {
      if (this.dirty) {
         this.dirty = false;
         if (this.followTarget) {
            if (this.lockedPosition) {
               this.lookAtCam.update();
               return;
            }

            if (this.target != null) {
               this.lastCameraRight.set(this.cameraRight);
               this.lastCameraUp.set(this.cameraUp);
               this.lastCameraDir.set(this.cameraDir);
               this.cameraUp = this.target.getUp();
               this.cameraUp.normalize();
               this.cameraDir = this.target.getLocalTransform().transformVector2(this.cameraPositionOffset, this.cameraDir);
               AEVector3D var10000 = this.cameraPos;
               var10000.x -= this.cameraPos.x - this.cameraDir.x >> 3;
               var10000 = this.cameraPos;
               var10000.y -= this.cameraPos.y - this.cameraDir.y >> 3;
               var10000 = this.cameraPos;
               var10000.z -= this.cameraPos.z - this.cameraDir.z >> 3;
               this.cameraDir = this.target.getLocalTransform().transformVector2(this.cameraRotation_, this.cameraDir);
               var10000 = this.cameraDir;
               var10000.x -= this.cameraPos.x;
               var10000 = this.cameraDir;
               var10000.y -= this.cameraPos.y;
               var10000 = this.cameraDir;
               var10000.z -= this.cameraPos.z;
               if (this.flipCameraDirection) {
                  this.cameraDir.x = -this.cameraDir.x;
                  this.cameraDir.y = -this.cameraDir.y;
                  this.cameraDir.z = -this.cameraDir.z;
               }

               this.cameraDir.normalize();
               this.cameraRight = this.cameraUp.crossProduct(this.cameraDir, this.cameraRight);
               this.cameraUp = this.cameraDir.crossProduct(this.cameraRight, this.cameraUp);
               this.cameraRight.normalize();
               this.camera.setTransform(this.cameraRight, this.cameraUp, this.cameraDir);
               if (this.isShaking) {
                  if (this.shakeCounter > 1000) {
                     this.isShaking = false;
                  }

                  int var1 = AEMath.max(1, (int)((float)(this.frametime >> 1) * ((float)(1000 - this.shakeCounter) / 1000.0F)));
                  var10000 = this.cameraPos;
                  var10000.x += -var1 + GlobalStatus.random.nextInt(var1 << 1);
                  var10000 = this.cameraPos;
                  var10000.y += -var1 + GlobalStatus.random.nextInt(var1 << 1);
                  var10000 = this.cameraPos;
                  var10000.z += -var1 + GlobalStatus.random.nextInt(var1 << 1);
               }

               this.camera.moveTo(this.cameraPos);
            }
         }

      }
   }

   public final void tickUpdate(int var1) {
      this.frametime = var1;
      this.shakeCounter += var1;
      this.dirty = true;
   }

   public final void hit() {
      if (!this.isShaking) {
         this.isShaking = true;
         this.shakeCounter = 0;
      }

   }

   public final boolean isInLookAtMode() {
      return this.followTarget;
   }

   public final void setLookAtCam(boolean var1) {
      this.followTarget = var1;
      this.lookAtCam.setLookAt(var1);
   }

   public final void setPostition(AEVector3D var1) {
      this.cameraPos.set(var1);
   }

   public final void setTargetOffset(AEVector3D var1) {
      this.cameraRotation_.set(var1);
   }

   public final void setCamOffset(AEVector3D var1) {
      this.cameraPositionOffset.set(var1);
   }
}
