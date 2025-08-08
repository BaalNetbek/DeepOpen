package AE;

import AE.Math.AEMath;

public final class EaseInOut {
	private int minValue;
	private int range;
	private int phase;
	private int currentValue;

	public EaseInOut(final int var1, final int var2) {
		this.minValue = var1;
		this.range = var2 - var1;
		this.phase = AEMath.Q_PI_THREE_QUARTERS;
	}

	public final void Increase(final int var1) {
		this.phase += var1;
		this.phase = this.phase > AEMath.Q_PI_FIVE_QUARTERS ? AEMath.Q_PI_FIVE_QUARTERS : this.phase;
		this.currentValue = (((AEMath.sin(this.phase) >> 1) + AEMath.Q_PI_HALF) * this.range >> AEMath.Q) + this.minValue;
	}

	public final void SetRange(final int var1, final int var2) {
		this.minValue = var1;
		this.range = var2 - var1;
		this.phase = AEMath.Q_PI_THREE_QUARTERS;
	}

	public final int GetValue() {
		return this.currentValue;
	}

	public final boolean IsAtMaxPhase(final boolean var1) {
		return this.phase == AEMath.Q_PI_FIVE_QUARTERS;
	}
}
