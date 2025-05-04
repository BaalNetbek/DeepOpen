package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.BoundingAAB;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class PlayerStation extends PlayerStaticFar {
   private final int[] partsCollisions = new int[]{-10, -791, -1776, 2509, 3625, 22248, -18, -786, 2466, 2499, 4681, 30734, -10, -1851, 29, 7568, 13971, 7568, 21, -2629, -6, 8627, 15527, 8627, 21, -9152, -7, 20335, 28573, 20340, -192, -1795, 0, 11008, 13859, 13952, 0, -2268, -7, 19164, 14804, 19177, -1250, -4456, 1261, 15033, 19181, 15027, -11, 3719, -29, 2512, 12400, 25744, 18, -729, 0, 2499, 3502, 16384, 18, -729, 0, 2499, 3502, 8192, 10, 13, -4, 11307, 10240, 11305, 0, -7924, 0, 19284, 26417, 19284, 10, 571, -4, 26296, 10240, 26296, 0, -1179, 0, 12650, 11632, 12650, -20, -648, 3, 21118, 12522, 11317, -1004, -659, 3, 15913, 11559, 11389, -2, -940, 7, 11358, 7008, 7610, 11, -1241, -970, 11392, 8260, 9453, -839, -3912, 2790, 13113, 12952, 13093, 21, 1102, 28, 12370, 7866, 12443, -356, 3009, 455, 15605, 16258, 15792, -10, 135, -2, 10652, 10483, 10652, -10, 3533, -2, 11360, 17279, 11360, 3, 1034, -8, 16067, 12281, 16067, -30, -1170, 1263, 17529, 15612, 20229, 37, -1069, 0, 33845, 11291, 33770, -10, 1016, -2, 7641, 12244, 7631, 1, 2565, 28, 11733, 15342, 7568, -10, 6381, 22, 7568, 22975, 7582, -1783, 7259, 22, 11115, 24731, 7582, 1117, 6019, 22, 11364, 22251, 7582, 18, -459, 16, 2499, 4043, 16350, 0, 0, 0, 1940, 1940, 1940, 0, 0, 0, 1940, 1940, 1940, 0, -5143, 0, 55879, 69796, 55879, 0, -4444, 0, 22222, 55555, 22222, -9617, 8646, -9617, 11781, 10784, 11781, 0, -15497, 0, 4866, 13737, 5263, 0, -4021, 0, 11493, 9778, 11493, 0, 12391, 0, 16896, 10793, 16896, 0, 3931, 0, 11240, 6126, 11240, -10361, 25852, -11525, 25379, 16129, 23051, 0, 0, 0, 0, 0, 0};
   private AbstractMesh[] stationParts;
   private int[] partPositions;
   private int collidingPart;
   private int maxPartDeflection;

   public PlayerStation(Station var1) {
      super(-1, (AbstractMesh)null, 0, 0, 0);
      this.player.setRadius(15000);
      new FileRead();
      int[] var2 = null;
      if (!Status.inAlienOrbit()) {
         var2 = FileRead.loadStationParts(var1.getId(), Status.getSystem().getRace());
      }

      int var3;
      if (var2 == null) {
         this.stationParts = new AbstractMesh[1];
         AbstractMesh[] var10000 = this.stationParts;
         Status.inAlienOrbit();
         var10000[0] = AEResourceManager.getGeometryResource(3337);
         this.stationParts[0].setRenderLayer(2);
      } else {
         this.stationParts = new AbstractMesh[var2.length / 7];

         for(int var7 = 0; var7 < this.stationParts.length; ++var7) {
            var3 = var7 * 7;
            this.stationParts[var7] = AEResourceManager.getGeometryResource(var2[var3]);
            this.stationParts[var7].setRotationOrder((short)0);
            this.stationParts[var7].moveTo(var2[var3 + 1], var2[var3 + 2], var2[var3 + 3]);
            this.stationParts[var7].setRotation(var2[var3 + 4], var2[var3 + 5], var2[var3 + 6]);
            this.stationParts[var7].setRenderLayer(2);
            if (this.stationParts[var7].getID() == 3334 || this.stationParts[var7].getID() == 3335) {
               this.stationParts[var7].setDraw(false);
            }
         }
      }

      this.maxPartDeflection = 0;
      this.boundingBoxes = new BoundingVolume[this.stationParts.length];
      Matrix var8 = new Matrix();

      for(var3 = 0; var3 < this.stationParts.length; ++var3) {
         int var9 = (this.stationParts[var3].getID() - 3301) * 6;
         this.tempVector_ = this.stationParts[var3].getPosition(this.tempVector_);
         if (AEMath.abs(this.tempVector_.x) > this.maxPartDeflection) {
            this.maxPartDeflection = AEMath.abs(this.tempVector_.x);
         }

         if (AEMath.abs(this.tempVector_.y) > this.maxPartDeflection) {
            this.maxPartDeflection = AEMath.abs(this.tempVector_.y);
         }

         if (AEMath.abs(this.tempVector_.z) > this.maxPartDeflection) {
            this.maxPartDeflection = AEMath.abs(this.tempVector_.z);
         }

         int var4 = this.tempVector_.x;
         int var5 = this.tempVector_.y;
         int var6 = this.tempVector_.z;
         var8.setRotation(this.stationParts[var3].getEulerX(), this.stationParts[var3].getEulerY(), this.stationParts[var3].getEulerZ());
         virtDistToCam_.set(this.partsCollisions[var9], this.partsCollisions[var9 + 1], this.partsCollisions[var9 + 2]);
         this.position = var8.transformVectorNoScale(virtDistToCam_, this.position);
         virtDistToCam_.set(this.partsCollisions[var9 + 3] + 5000, this.partsCollisions[var9 + 4] + 5000, this.partsCollisions[var9 + 5] + 5000);
         this.tempVector_ = var8.transformVectorNoScale(virtDistToCam_, this.tempVector_);
         this.boundingBoxes[var3] = new BoundingAAB(var4, var5, var6, this.position.x, this.position.y, this.position.z, this.tempVector_.x, this.tempVector_.y, this.tempVector_.z);
         if (Status.getSystem() != null) {
            var2 = Globals.getRaceUVkeyframeId_(Status.getSystem().getRace());
            this.stationParts[var3].setAnimationRangeInTime(var2[0], var2[1]);
            this.stationParts[var3].disableAnimation();
         }
      }

      this.maxPartDeflection += 5000;
      this.partPositions = new int[this.stationParts.length * 3];

      for(var3 = 0; var3 < this.stationParts.length; ++var3) {
         this.partPositions[var3 * 3] = this.stationParts[var3].getPosX();
         this.partPositions[var3 * 3 + 1] = this.stationParts[var3].getPosY();
         this.partPositions[var3 * 3 + 2] = this.stationParts[var3].getPosZ();
      }

   }

   public final void update(long var1) {
      if (this.stationParts != null) {
         for(int var3 = 0; var3 < this.stationParts.length; ++var3) {
            this.tempVector_ = GlobalStatus.renderer.getCamera().getTempTransformPos(this.tempVector_);
            this.position.set(this.partPositions[var3 * 3], this.partPositions[var3 * 3 + 1], this.partPositions[var3 * 3 + 2]);
            this.position.subtract(this.tempVector_, virtDistToCam_);
            int var2;
            if ((var2 = virtDistToCam_.getLength()) > 20000) {
               virtDistToCam_.normalize();
               virtDistToCam_.scale(20000);
               virtDistToCam_.add(this.tempVector_);
               this.stationParts[var3].moveTo(virtDistToCam_);
               var2 = (int)(20000.0F / (float)var2 * 4096.0F);
               this.stationParts[var3].setScale(var2, var2, var2);
            } else {
               this.stationParts[var3].setScale(4096, 4096, 4096);
               this.stationParts[var3].moveTo(this.partPositions[var3 * 3], this.partPositions[var3 * 3 + 1], this.partPositions[var3 * 3 + 2]);
            }
         }

      }
   }

   public final void appendToRender() {
      if (this.stationParts != null) {
         for(int var1 = 0; var1 < this.stationParts.length; ++var1) {
            GlobalStatus.renderer.drawNodeInVF(this.stationParts[var1]);
         }

      }
   }

   public final AEVector3D getPosition(AEVector3D var1) {
      var1.set(0, 0, 0);
      return var1;
   }

   public final boolean outerCollide(int var1, int var2, int var3) {
      return (float)var1 < this.player.radius && (float)var1 > -this.player.radius && (float)var2 < this.player.radius && (float)var2 > -this.player.radius && (float)var3 < this.player.radius && (float)var3 > -this.player.radius;
   }

   public final boolean outerCollide(AEVector3D var1) {
      int var4 = var1.z;
      int var3 = var1.y;
      int var2 = var1.x;
      PlayerStation var6 = this;
      if (var2 < this.maxPartDeflection && var2 > -this.maxPartDeflection && var3 < this.maxPartDeflection && var3 > -this.maxPartDeflection && var4 < this.maxPartDeflection && var4 > -this.maxPartDeflection && this.boundingBoxes != null) {
         for(int var5 = 0; var5 < var6.boundingBoxes.length; ++var5) {
            if (var6.boundingBoxes[var5].isPointInBounding(var2, var3, var4)) {
               var6.collidingPart = var5;
               return true;
            }
         }
      }

      return false;
   }

   public final AEVector3D getProjectionVector_(AEVector3D var1) {
      return this.boundingBoxes != null ? this.boundingBoxes[this.collidingPart].getProjectionVector(var1) : null;
   }

   public final void OnRelease() {
      if (this.stationParts != null) {
         for(int var1 = 0; var1 < this.stationParts.length; ++var1) {
            if (this.stationParts[var1] != null) {
               this.stationParts[var1].OnRelease();
               this.stationParts[var1] = null;
            }
         }
      }

      this.stationParts = null;
      this.partPositions = null;
   }
}
