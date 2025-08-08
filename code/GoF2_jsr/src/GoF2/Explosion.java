package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.Math.AEMath;

public final class Explosion {
	// SMALL < MEDIUM < BIG
	public final static int MEDIUM = 0;
	public final static int BIG = 1;
	public final static int SMALL = 2;
	public final static int HUGE = 3;
	public final static int EMP = 4;
	private int[] delays;
	//private int[] unused_1af;
	private int animationPlayTime;
	private AbstractMesh[] explosions;
	private final AbstractMesh coreExplosion;

	public Explosion(int var1) {
		if (var1 > 1) {
			this.explosions = new AbstractMesh[var1];
			this.delays = new int[var1];

			for(int i = 0; i < this.explosions.length; ++i) {
				this.explosions[i] = AEResourceManager.getGeometryResource(9992);
				this.explosions[i].setAnimationSpeed(100);
				this.explosions[i].setAnimationRangeInTime(1, 20);
				this.explosions[i].setScale(AEMath.Q_2, AEMath.Q_2, AEMath.Q_2);
				this.explosions[i].setRenderLayer(2);
				this.explosions[i].disableAnimation();
				if (i == 0) {
					this.delays[i] = 0;
				} else {
					this.delays[i] = this.delays[i - 1] + GlobalStatus.random.nextInt(1000) + 1000;
				}
			}
		}

		this.coreExplosion = AEResourceManager.getGeometryResource(9992);
		this.coreExplosion.setAnimationSpeed(100);
		this.coreExplosion.setAnimationRangeInTime(1, 20);
		this.coreExplosion.setScale(AEMath.Q_2, AEMath.Q_2, AEMath.Q_2);
		this.coreExplosion.setRenderLayer(2);
		this.coreExplosion.disableAnimation();
	}

	public final void reset() {
		this.animationPlayTime = 0;
	}

	public final void setBig() {
		if (this.coreExplosion != null) {
			this.coreExplosion.setScale(AEMath.Q_8, AEMath.Q_8, AEMath.Q_8);
			this.coreExplosion.setAnimationSpeed(200);
		}

	}

	public final void OnRelease() {
		this.delays = null;
		//this.unused_1af = null;
		if (this.explosions != null) {
			for(int i = 0; i < this.explosions.length; ++i) {
				if (this.explosions[i] != null) {
					this.explosions[i].OnRelease();
				}
			}
		}

		this.explosions = null;
	}

	public final void start(final int var1, final int var2, final int var3) {
		if (this.explosions == null) {
			this.coreExplosion.moveTo(var1, var2, var3);
			this.coreExplosion.setAnimationMode((byte)1);
			this.animationPlayTime = 0;
		} else {
			for(int i = 0; i < this.explosions.length; ++i) {
				this.explosions[i].moveTo(var1, var2, var3);
			}

			this.coreExplosion.moveTo(var1, var2, var3);
		}
	}

	public final boolean canExplode() {
		return this.coreExplosion != null && this.coreExplosion.hasAnimation();
	}

	public final void update(final long var1) {
		this.animationPlayTime = (int)(this.animationPlayTime + var1);
		if (this.explosions != null) {
			for(int i = 0; i < this.explosions.length; ++i) {
				if (this.animationPlayTime > this.delays[i]) {
					if (i == this.explosions.length - 1) {
						this.coreExplosion.setScale(AEMath.Q_4, AEMath.Q_4, AEMath.Q_4);
						this.coreExplosion.setAnimationMode((byte)1);
					}

					this.explosions[i].setAnimationMode((byte)1);
				}
			}

		} else {
			GlobalStatus.renderer.drawNodeInVF(this.coreExplosion);
		}
	}

	public final boolean isOver() {
		if (this.explosions != null) {
			return this.animationPlayTime > this.delays[this.delays.length - 1] + 1000;
		}
		return this.animationPlayTime > 2000;
	}
}
