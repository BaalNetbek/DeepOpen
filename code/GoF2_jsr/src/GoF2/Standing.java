package GoF2;

public final class Standing {
	private int[] stand = new int[2];

	public Standing() {
		this.stand[0] = 30;
		this.stand[1] = 0;
	}

	public final void setStanding(final int[] var1) {
		this.stand = var1;
	}

	public final int[] getStanding() {
		return this.stand;
	}

	public final int getStanding(final int var1) {
		return this.stand[var1];
	}

	public final void rehabilitate(final int var1) {
		switch (var1) {
		case 1:
			this.stand[0] = 35;
			break;
		case 0:
			this.stand[0] = -35;
			break;
		case 3:
			this.stand[1] = 35;
			break;
		default:
			if (var1 == 2) {
				this.stand[1] = -35;
			}
			break;
		}
	}

	public final boolean atWar() {
		final int var1 = this.stand[0];
		final int var2 = this.stand[1];
		return var1 > 60 || var1 < -60 || var2 > 60 || var2 < -60;
	}

	public final boolean isEnemy(final int var1) {
		switch (var1) {
		case 1:
			return this.stand[0] > 60;
		case 0:
			return this.stand[0] < -60;
		case 3:
			return this.stand[1] > 60;
		case 2:
			return this.stand[1] < -60;
		default:
			return false;
		}
	}

	public final boolean isFriend(final int var1) {
		switch (var1) {
		case 1:
			return this.stand[0] < -60;
		case 0:
			return this.stand[0] > 60;
		case 3:
			return this.stand[1] < -60;
		case 2:
			return this.stand[1] > 60;
		default:
			return false;
		}
	}

	public static int enemyRace(final int var0) {
		switch(var0) {
		case 0:
			return 1;
		case 1:
			return 0;
		case 2:
			return 3;
		case 3:
			return 2;
		default:
			return 8;
		}
	}

	private void subtract(final int var1, final int var2) {
		final int[] var10000 = this.stand;
		var10000[var1] += var2;
		if (this.stand[var1] > 100) {
			this.stand[var1] = 100;
		} else if (this.stand[var1] < -100) {
			this.stand[var1] = -100;
		}
	}

	public final void applyKill(final int var1) {
		final int var2 = Status.inAlienOrbit() ? 9 : Status.getSystem().getRace();
		int var3 = var1;
		byte var4 = 5;
		if (var1 == 8) {
			switch(var2) {
			case 0:
				var3 = 1;
				break;
			case 1:
				var3 = 0;
				break;
			case 2:
				var3 = 3;
				break;
			case 3:
				var3 = 2;
			}

			var4 = 1;
		}

		applyDelict(var3, var4);
	}

	public final void applyStealCargo(final int var1) {
		applyDelict(var1, 2);
	}

	public final void increase(final int var1) {
		applyDelict(var1, -7);
	}

	public final void applyDisable(final int var1) {
		applyDelict(var1, 2);
	}

	private void applyDelict(final int var1, final int var2) {
		switch (var1) {
		case 1:
			subtract(0, var2);
			break;
		case 0:
			subtract(0, -var2);
			break;
		case 3:
			subtract(1, var2);
			break;
		default:
			if (var1 == 2) {
				subtract(1, -var2);
			}
			break;
		}
	}
}
