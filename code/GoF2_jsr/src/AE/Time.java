package AE;

public final class Time {
	private static String seconds;
	private static String minutes;
	private static String hours;

	private Time() {
	}

	public static String timeToHMS(final long time) {
		final int s = (int)(time / 1000L % 60L);
		final int m = (int)(time / 60000L % 60L);
		int h = (int)(time / 3600000L % 24L);
		seconds = (s < 10 ? "0" : "") + s;
		minutes = (m < 10 ? "0" : "") + m;
		if (h == 0) {
			return (minutes + ":" + seconds);
		}
		final int days = (int)(time / 3600000L / 24L);
		h += days * 24;
		hours = (h < 10 ? "0" : "") + h;
		return (hours + ":" + minutes + ":" + seconds);
	}

	public static String timeToHM(final long time) {
		final int m = (int)(time / 60000L % 60L);
		int h = (int)(time / 3600000L % 24L);
		minutes = (m < 10 ? "0" : "") + m;
		final int days = (int)(time / 3600000L / 24L);
		h += days * 24;
		hours = (h < 10 ? "0" : "") + h;
		return (hours + ":" + minutes);
	}
}
