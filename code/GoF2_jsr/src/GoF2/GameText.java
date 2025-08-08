package GoF2;

import java.io.DataInputStream;
import java.io.InputStream;

import AE.GlobalStatus;

public final class GameText {
	public static final short[] LISTITEMWINDOW_KEY_TEXT_IDS = {157, 157, 157, 37, 36, 36, 36, 36, 36, 50, 42, 53, 54, 57, 43, 59, 52, 53, 60, 44, 58, 56, 45, 58, 53, 55, 58, 45, 46, 47, 59, 48, 49, 58, 53, 69};
	public static final short[] helpTitles = {112, 296, 275, 79, 130, 218, 72, 146, 297, 63, 298};
	public static final short[] helpFull = {306, 307, 308, 309, 312, 314, 315, 320, 321, 323, 324};
	public static final short[] soundLevels = {8, 9, 10, 16};
	public static final short[] tips = {165, 166, 167, 168, 169, 169, 170, 171, 172, 173, 174, 175, 176, 177};
	public static final short[] CAMPAIGN_MISSION_DESC = {389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 373, 374, 389, 375, 389, 376, 389, 377, 377, 389, 378, 379, 389, 389, 389, 380, 389, 389, 389, 381, 382, 383, 384, 385, 389, 386, 389, 387, 389, 389, 388, 389};
	public static final short[] MISSION_START_MSG = {201, 202, 203, 204, 205};
	public static final short[] MISSION_SUCCESS_MSG = {195, 196, 197, 198, 199};
	public static final short[] MISSION_LOST_MSG = {206, 207, 208, 209, 210};
	private static final int[] catrogoryLengths_ = {12, 5, 1, 2, 8, 5, 5, 5, 5, 6, 8, 17, 19, 9, 9, 4, 9, 12, 8, 12, 14, 9, 3, 14, 19, 9, 27, 5, 4, 13, 6, 17, 2, 9, 4, 7, 2, 10, 2, 4, 8, 6, 12, 12, 7, 9, 32};
	private static String[] langEntries = new String[1198];
	private static String[] unusedBuffer_ = new String[422];
	private static String[] ships = new String[22];
	private static String[] items = new String[162];
	private static String[] medals = new String[48];
	private static String[][] categories_ = new String[47][];
	private static int language;
	private static String langPath;
	private static int tempId;
	private static int categoryId_;
	private static int tempId2;

	public final void setLanguage(final int var1) {
		langPath = "/data/lang/";
		language = var1;
		switch(var1) {
		case 0:
			langPath = langPath + "de/";
			break;
		case 1:
			langPath = langPath + "en/";
			break;
		case 2:
			langPath = langPath + "es/";
			break;
		case 3:
			langPath = langPath + "fr/";
			break;
		case 4:
			langPath = langPath + "it/";
			break;
		case 5:
			langPath = langPath + "cz/";
			break;
		case 6:
			langPath = langPath + "ru/";
			break;
		case 7:
			langPath = langPath + "pl/";
			break;
		case 8:
			langPath = langPath + "pt/";
		}

		if (language == 0) {
			readLangFile(langEntries, "de");
		} else if (language == 6) {
			readLangFile(langEntries, "ru");
		} else {
			readLangFile(langEntries, "en");
		}

		init();
	}

	private void readLangFile(final String[] var1, final String var2) {
		InputStream var3 = null;

		int var6;
		try {
			var3 = getClass().getResourceAsStream(langPath + var2 + ".lang");
			final DataInputStream var5 = new DataInputStream(var3);

			for(var6 = 0; var6 < var1.length; ++var6) {
				var1[var6] = var5.readUTF();
			}

			var5.close();
		} catch (final Exception var4) {
			for(var6 = 0; var6 < var1.length; ++var6) {
				var1[var6] = "<ERROR|LANG>";
			}

		}
	}

	public final String getText(int var1) {
		if (var1 < 1198) {
			return langEntries[var1];
		}
		if (var1 < 422) {
			return unusedBuffer_[var1];
		} else if (var1 < 444) {
			tempId = var1 - 422;
			if (ships[tempId] == null) {
				readLangFile(ships, "ships");
			}

			return ships[tempId];
		} else if (var1 < 606) {
			tempId = var1 - 444;
			if (items[tempId] == null) {
				readLangFile(items, "items");
			}

			return items[tempId];
		} else if (var1 < 654) {
			tempId = var1 - 606;
			if (medals[tempId] == null) {
				readLangFile(medals, "medals");
			}

			return medals[tempId];
		} else {
			tempId = var1 - 654;
			categoryId_ = 0;

			for(var1 = 0; var1 < catrogoryLengths_.length; ++var1) {
				categoryId_ += catrogoryLengths_[var1];
				if (tempId < categoryId_) {
					tempId2 = tempId - (categoryId_ - catrogoryLengths_[var1]);
					categoryId_ = var1;
					break;
				}
			}

			if (categories_[categoryId_][tempId2] == null) {
				readLangFile(categories_[categoryId_], "" + categoryId_);
			}

			return categories_[categoryId_][tempId2];
		}
	}

	public static void init() {
		for(int i = 0; i < categories_.length; ++i) {
			categories_[i] = new String[catrogoryLengths_[i]];
		}

		ships = new String[22];
		items = new String[162];
		medals = new String[48];
	}

	static {
		language = GlobalStatus.language;
	}
}
