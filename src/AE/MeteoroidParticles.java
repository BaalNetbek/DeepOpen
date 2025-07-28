package AE;

import AE.Math.AEQuaternion;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class MeteoroidParticles {
   private int[] vertexCoords;
   private int[] lifeTimes;
   private int[] offsets;
   private int[] uvs;
   private AEVector3D tempVec2 = new AEVector3D();
   private AEVector3D tempVec = new AEVector3D();
   private AEQuaternion tempQuat;
   private AbstractMesh[] particles;
   private int particleSpawnTick;

   public MeteoroidParticles() {
      this.offsets = new int[20];
      this.vertexCoords = new int[12];
      this.lifeTimes = new int[20];
      this.uvs = new int[8];
      this.uvs[0] = 1;
      this.uvs[1] = 48;
      this.uvs[2] = 14;
      this.uvs[3] = 48;
      this.uvs[4] = 14;
      this.uvs[5] = 63;
      this.uvs[6] = 1;
      this.uvs[7] = 63;
      this.vertexCoords[0] = -100;
      this.vertexCoords[1] = 0;
      this.vertexCoords[2] = 0;
      this.vertexCoords[3] = 100;
      this.vertexCoords[4] = 0;
      this.vertexCoords[5] = 0;
      this.vertexCoords[6] = 100;
      this.vertexCoords[7] = 0;
      this.vertexCoords[8] = 1000;
      this.vertexCoords[9] = -100;
      this.vertexCoords[10] = 0;
      this.vertexCoords[11] = 1000;
      this.particles = new AbstractMesh[20];

      for (int i = 0; i < this.particles.length; i++) {
         this.particles[i] = AbstractMesh.newPlaneStrip(0, 1, (byte)2);
         this.particles[i].setTexture(AEResourceManager.getTextureResource(0));
         this.particles[i].setRenderLayer(2);
         ((ParticleSystemMesh)this.particles[i]).setMeshData_(this.vertexCoords, this.uvs);
         this.particles[i].moveTo(100000, 100000, 100000);
      }

      this.tempQuat = new AEQuaternion();
   }

   public final void OnRelease() {
      for (int var1 = 0; var1 < this.particles.length; var1++) {
         this.particles[var1].OnRelease();
      }

      this.particles = null;
      this.vertexCoords = null;
      this.uvs = null;
   }

   public final void update(long var1, Matrix var3) {
      int var4 = 0;
      this.particleSpawnTick = (int)((long)this.particleSpawnTick + var1);

      for (int i = 0; i < 20; i++) {
         if (this.particleSpawnTick > 400 && this.lifeTimes[i] <= 0 && var4 == 0) {
            this.particleSpawnTick = 0;
            var4 = GlobalStatus.random.nextInt(18000) - 9000;
            int var6 = GlobalStatus.random.nextInt(18000) - 9000;
            this.tempVec.set(var4, var6, 20000);
            this.tempVec2 = var3.transformVector2(this.tempVec, this.tempVec2);
            this.tempQuat = var3.toQuaterion(this.tempQuat);
            this.particles[i].moveTo(this.tempVec2);
            this.particles[i].setToParentFromQuaterion(this.tempQuat);
            this.lifeTimes[i] = 4000;
            this.offsets[i] = 300 + GlobalStatus.random.nextInt(300);
            var4 = 1;
         } else {
            this.lifeTimes[i] = (int)((long)this.lifeTimes[i] - var1);
            this.particles[i].moveForward(-this.offsets[i]);
         }
      }
   }

   public final void render() {
      for (int var1 = 0; var1 < this.particles.length; var1++) {
         GlobalStatus.renderer.drawNodeInVF(this.particles[var1]);
      }
   }
}
