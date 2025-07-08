package GoF2;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public final class Galaxy {
	private static AEVector3D tempSysPos1 = new AEVector3D();
	private static AEVector3D tempSysPos2 = new AEVector3D();
	private static boolean[] visitedStations = new boolean[100];

	public static SolarSystem loadSystem_(final int var0) {
		if (var0 < 0) {
			return null;
		}
		//new FileRead();
		final SolarSystem[] var1 = FileRead.loadSystemsBinary();
		if (Status.getPlayingTime() == 0L) {
			final boolean[] var2 = Status.getVisibleSystems();

			for(int var3 = 0; var3 < var2.length; ++var3) {
				var2[var3] = var1[var3].isVisibleByDeafult();
			}
		}

		return var1[var0];
	}

	public static Station getStation(final int var0) {
		return var0 < 0 ? Status.voidStation : new FileRead().loadStation(var0);
	}

	public static void setVisitedStations(final boolean[] var0) {
		visitedStations = var0;
	}

	public static boolean[] getVisitedStations() {
		return visitedStations;
	}

	public static void visitStation(final int var0) {
		visitedStations[var0] = true;
	}

	public static int invDistancePercent(final int x1, final int y1, final int x2, final int y2) {
		return 100 - (AEMath.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) << 12) >> 12);
	}

	public static int distancePercent(final int var0, final int var1, final int var2, final int var3) {
		return AEMath.sqrt((var2 - var0) * (var2 - var0) + (var3 - var1) * (var3 - var1) << 12) >> 12;
	}

	public static float distance(final SolarSystem var0, final SolarSystem var1) {
		if (var0.equals(var1)) {
			return 0.0F;
		}
		tempSysPos1.set(var0.getPosX(), var0.getPosY(), var0.getPosZ() / 10);
		tempSysPos2.set(var1.getPosX(), var1.getPosY(), var1.getPosZ() / 10);
		tempSysPos1.subtract(tempSysPos2);
		return (AEMath.sqrt(tempSysPos1.x * tempSysPos1.x + tempSysPos1.y * tempSysPos1.y + tempSysPos1.z * tempSysPos1.z << 12) >> 12) * 18.85F;
	}

	public static int[] getAsteroidProbabilities(final Station var0) {
		final boolean var1 = Status.inAlienOrbit();
		SolarSystem[] var2 = null;
		Item[] var3;
		if (!var1) {
			var3 = null;
			var2 = FileRead.loadSystemsBinary();
		}

		var3 = Globals.getItems();
		final int[] var4 = new int[11];
		final int[] var5 = new int[11];

		for(int astId = 154, i = 0; astId < 164; ++astId) {
			var5[i] = astId;
			if (var1) {
				var4[i] = 0;
			} else {
				final SolarSystem solarSystem = var2[var3[astId].getLowestPriceSystemId()];
				final SolarSystem solarSystem2 = var2[var0.getSystemIndex()];
				var4[i] = (byte)invDistancePercent(solarSystem2.getPosX(), solarSystem2.getPosY(), solarSystem.getPosX(), solarSystem.getPosY());
				if (var4[i] < 50) {
					var4[i] = 0;
				}

				++i;
			}
		}

		var5[10] = 164;
		var4[10] = var1 ? 100 : 0;

		int var9;
		boolean var12;
		do {
			var12 = true;

			for(int i = 1; i < var4.length; ++i) {
				if (var4[i - 1] < var4[i]) {
					var9 = var4[i - 1];
					final int var11 = var5[i - 1];
					var4[i - 1] = var4[i];
					var5[i - 1] = var5[i];
					var4[i] = var9;
					var5[i] = var11;
					var12 = false;
				}
			}
		} while(!var12);

		for(int i = 0; i < var4.length; ++i) {
			if (var4[i] > 0) {
				var4[i] = (byte)(var4[i] - i * 2);
			}
		}

		final int[] var10 = new int[var4.length << 1];

		for(int i = 0; i < var10.length; i += 2) {
			var10[i] = var5[i / 2];
			var10[i + 1] = var4[i / 2];
		}

		return var10;
	}
}
