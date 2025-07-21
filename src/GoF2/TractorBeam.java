package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Group;
import AE.Math.AEVector3D;
import AE.ParticleSystemMesh;

public final class TractorBeam {
   private AEVector3D shipToTarget = new AEVector3D();
   private AEVector3D wastePos_shipDir_ = new AEVector3D();
   private AEVector3D beamSrcPos_ = new AEVector3D();
   private AEVector3D toTargetDir = new AEVector3D();
   private KIPlayer target = null;
   private static int[][] typeUV = new int[][]{{16, 13, 47, 19}, {16, 13, 47, 19}, {16, 13, 47, 19}};
   private int width = 100;
   private int[] vertexPositions;
   private int[] vertexWorldPositions;
   private int[] uvs;
   private boolean active = false;
   private AbstractMesh beamMesh = AbstractMesh.newPlaneStrip(0, 10, (byte)2);

   public TractorBeam(Group var1, int var2) {
      this.beamMesh.setTexture(AEResourceManager.getTextureResource(1));
      this.beamMesh.setRenderLayer(2);
      this.vertexWorldPositions = new int[120];
      this.vertexPositions = new int[120];
      this.uvs = new int[80];

      for(int var3 = 0; var3 < this.uvs.length; var3 += 8) {
         this.uvs[var3] = typeUV[var2][0];
         this.uvs[var3 + 1] = typeUV[var2][1];
         this.uvs[var3 + 2] = typeUV[var2][2];
         this.uvs[var3 + 3] = typeUV[var2][1];
         this.uvs[var3 + 4] = typeUV[var2][2];
         this.uvs[var3 + 5] = typeUV[var2][3];
         this.uvs[var3 + 6] = typeUV[var2][0];
         this.uvs[var3 + 7] = typeUV[var2][3];
      }

   }

   public final void render() {
      if (this.active) {
         GlobalStatus.renderer.drawNodeInVF(this.beamMesh);
      }

   }

   public final void update(long var1, Radar var3, Level var4, Hud var5) {
      KIPlayer var6 = var3.tractorBeamTarget;
      if (this.target != null || var6 != null) {
         if (this.target == null) {
            if (!var6.cargoAvailable_()) {
               return;
            }

            if (var6.waste == null) {
               var6.createCrate(0);
            }

            this.shipToTarget = var6.waste.getPosition(this.shipToTarget);
            this.shipToTarget.subtract(var4.getPlayer().getPosition());
            if (var6.isDead() || var6.isDying()) {
               var6.setActive(false);
            }

            this.target = var6;
            this.active = true;
            return;
         }

         if (this.target.waste == null) {
            var3.tractorBeamTarget = null;
            var3.contextShip = null;
            return;
         }

         this.wastePos_shipDir_ = this.target.waste.getLocalPos(this.wastePos_shipDir_);
         this.beamSrcPos_ = var4.getPlayer().shipGrandGroup_.getLocalPos(this.beamSrcPos_);
         this.shipToTarget = this.wastePos_shipDir_.subtract(this.beamSrcPos_, this.shipToTarget);
         this.toTargetDir.set(this.shipToTarget);
         this.wastePos_shipDir_ = var4.getPlayer().shipGrandGroup_.getDirection(this.wastePos_shipDir_);
         this.wastePos_shipDir_.scale(1024);
         this.beamSrcPos_.add(this.wastePos_shipDir_);
         AEVector3D var10000 = this.toTargetDir;
         var10000.x /= 10;
         var10000 = this.toTargetDir;
         var10000.y /= 10;
         var10000 = this.toTargetDir;
         var10000.z /= 10;

         int var7;
         int var8;
         for(var7 = 0; var7 < this.vertexWorldPositions.length >> 1; var7 += 12) {
            var8 = var7 / 12;
            this.vertexWorldPositions[var7] = this.beamSrcPos_.x + var8 * this.toTargetDir.x + this.width;
            this.vertexWorldPositions[var7 + 1] = this.beamSrcPos_.y + var8 * this.toTargetDir.y;
            this.vertexWorldPositions[var7 + 2] = this.beamSrcPos_.z + var8 * this.toTargetDir.z;
            this.vertexWorldPositions[var7 + 3] = this.beamSrcPos_.x + var8 * this.toTargetDir.x - this.width;
            this.vertexWorldPositions[var7 + 4] = this.beamSrcPos_.y + var8 * this.toTargetDir.y;
            this.vertexWorldPositions[var7 + 5] = this.beamSrcPos_.z + var8 * this.toTargetDir.z;
            this.vertexWorldPositions[var7 + 6] = this.beamSrcPos_.x + (var8 + 1) * this.toTargetDir.x - this.width;
            this.vertexWorldPositions[var7 + 7] = this.beamSrcPos_.y + (var8 + 1) * this.toTargetDir.y;
            this.vertexWorldPositions[var7 + 8] = this.beamSrcPos_.z + (var8 + 1) * this.toTargetDir.z;
            this.vertexWorldPositions[var7 + 9] = this.beamSrcPos_.x + (var8 + 1) * this.toTargetDir.x + this.width;
            this.vertexWorldPositions[var7 + 10] = this.beamSrcPos_.y + (var8 + 1) * this.toTargetDir.y;
            this.vertexWorldPositions[var7 + 11] = this.beamSrcPos_.z + (var8 + 1) * this.toTargetDir.z;
         }

         for(var7 = this.vertexWorldPositions.length >> 1; var7 < this.vertexWorldPositions.length; var7 += 12) {
            var8 = (var7 - (this.vertexWorldPositions.length >> 1)) / 12;
            this.vertexWorldPositions[var7] = this.beamSrcPos_.x + var8 * this.toTargetDir.x;
            this.vertexWorldPositions[var7 + 1] = this.beamSrcPos_.y + var8 * this.toTargetDir.y + this.width;
            this.vertexWorldPositions[var7 + 2] = this.beamSrcPos_.z + var8 * this.toTargetDir.z + this.width;
            this.vertexWorldPositions[var7 + 3] = this.beamSrcPos_.x + var8 * this.toTargetDir.x;
            this.vertexWorldPositions[var7 + 4] = this.beamSrcPos_.y + var8 * this.toTargetDir.y - this.width;
            this.vertexWorldPositions[var7 + 5] = this.beamSrcPos_.z + var8 * this.toTargetDir.z - this.width;
            this.vertexWorldPositions[var7 + 6] = this.beamSrcPos_.x + (var8 + 1) * this.toTargetDir.x;
            this.vertexWorldPositions[var7 + 7] = this.beamSrcPos_.y + (var8 + 1) * this.toTargetDir.y - this.width;
            this.vertexWorldPositions[var7 + 8] = this.beamSrcPos_.z + (var8 + 1) * this.toTargetDir.z - this.width;
            this.vertexWorldPositions[var7 + 9] = this.beamSrcPos_.x + (var8 + 1) * this.toTargetDir.x;
            this.vertexWorldPositions[var7 + 10] = this.beamSrcPos_.y + (var8 + 1) * this.toTargetDir.y + this.width;
            this.vertexWorldPositions[var7 + 11] = this.beamSrcPos_.z + (var8 + 1) * this.toTargetDir.z + this.width;
         }

         for(var7 = 0; var7 < this.vertexWorldPositions.length; var7 += 3) {
            this.vertexPositions[var7] = this.vertexWorldPositions[var7] - this.beamSrcPos_.x;
            this.vertexPositions[var7 + 1] = this.vertexWorldPositions[var7 + 1] - this.beamSrcPos_.y;
            this.vertexPositions[var7 + 2] = this.vertexWorldPositions[var7 + 2] - this.beamSrcPos_.z;
         }

         this.beamMesh.moveTo(this.beamSrcPos_.x, this.beamSrcPos_.y, this.beamSrcPos_.z);
         ((ParticleSystemMesh)this.beamMesh).setMeshData_(this.vertexPositions, this.uvs);
         if (this.shipToTarget.getLength() <= 400) {
            this.target.captureCrate(var5);
            this.active = false;
            this.target = null;
            var3.tractorBeamTarget = null;
            var3.contextShip = null;
            return;
         }

         var3.tractorBeamTarget = null;
         this.shipToTarget.normalize();
         this.shipToTarget.scale((int)var1 * 10);
         this.target.waste.translate(-this.shipToTarget.x, -this.shipToTarget.y, -this.shipToTarget.z);
      }

   }
}
