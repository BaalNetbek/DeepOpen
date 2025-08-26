package GOF2;

import java.util.Random;

import AE.AEGeometry;
import AE.ITexture;
import AE.ParticlesMesh;

/**
 * Fading explosion effect made of 3D sprites. Using AE.ParticlesMesh.
 * 
 * @author fishlabs
 *   
 */
public final class Impact {
	public AEGeometry mesh;
	private final int[] particlesLifeTime;
	private final int baseLifeTime;
	private final int spread;
	private final int lifeTimeRange;

	public Impact(final int particleCount, final ITexture texture, final int var3, final int u1, int v1, final int u2, final int v2, final int particleSize, final int baseLifeTime, final int lifeTimeRange, final byte blending) {
		this.spread = particleSize; // #NAME_ERROR Both names need verification.
		this.baseLifeTime = baseLifeTime;
		this.lifeTimeRange = lifeTimeRange;
		this.particlesLifeTime = new int[particleCount];
		final short textureSize = 256;
		this.mesh = new ParticlesMesh(textureSize, u1, v1, u2, v2, particleSize, particleCount, blending);
		this.mesh.setTexture(texture);
		ParticlesMesh particlesMesh = (ParticlesMesh)this.mesh;
		final int[] postions = particlesMesh.getPositions();
		final int[] scales = particlesMesh.getScales();
		final Random random = new Random();  // why not use AE.GlobalStatus.random?
		
		for(int i = 0; i < particleCount; ++i) {
			postions[i * 3] = random.nextInt(particleSize >> 1) - (particleSize >> 2);
			postions[i * 3 + 1] = random.nextInt(particleSize >> 1) - (particleSize >> 2);
			postions[i * 3 + 2] = random.nextInt(particleSize >> 1) - (particleSize >> 2);
			scales[i] = 0;
			this.particlesLifeTime[i] = random.nextInt(lifeTimeRange) - (lifeTimeRange >> 1) + this.baseLifeTime;
		}

	}

	public final void explode() {
		ParticlesMesh particlesMesh = (ParticlesMesh)this.mesh;
		final int[] positions = particlesMesh.getPositions();
		final int[] scales = particlesMesh.getScales();
		final int[] colors = particlesMesh.getColors();
		final Random random = new Random();

		for(int i = 0; i < scales.length; ++i) {
			positions[i * 3] = random.nextInt(this.spread >> 1) - (this.spread >> 2);
			positions[i * 3 + 1] = random.nextInt(this.spread >> 1) - (this.spread >> 2);
			positions[i * 3 + 2] = random.nextInt(this.spread >> 1) - (this.spread >> 2);
			scales[i] = 0;
			colors[i] = -1;
			this.particlesLifeTime[i] = random.nextInt(this.lifeTimeRange) - (this.lifeTimeRange >> 1) + this.baseLifeTime;
		}

	}

	public final void update(final int frameTime) {
		final int[] scales = ((ParticlesMesh)this.mesh).getScales();
		final int[] colors = ((ParticlesMesh)this.mesh).getColors();
		final int scaleDelta = frameTime * this.spread / this.baseLifeTime;

		for(int i = 0; i < scales.length; ++i) {
			if (this.particlesLifeTime[i] > 0 && this.particlesLifeTime[i] < this.baseLifeTime) {
				scales[i] += scaleDelta;
			} else if (this.particlesLifeTime[i] > -1024) {
			 // int alpha = AEMath.max(0, (colors[i] >>> 24) - (frameTime >> 2)); suggestion
				int alpha = (colors[i] >>> 24) - (frameTime >> 2);
				alpha = alpha < 0 ? 0 : alpha;
				// Actually useless for ALPHA_ADD blending.
				colors[i] = alpha << 24 | 0x00ffffff; 
			} else {
				scales[i] = 0;
			}

			this.particlesLifeTime[i] -= frameTime;
		}

	}
}
