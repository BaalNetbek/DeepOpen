package GoF2;

import AE.GlobalStatus;

public final class Dialogue {
	private static short[][] CAMPAIGN_BRIEFING = {{16, 853}, {0, 0}, {0, 883, 2, 884, 0, 885, 2, 886, 16, 887}, {0, 0}, {2, 896}, {0, 0}, {0, 0}, {2, 906, 16, 907, 16, 908}, {0, 0}, {0, 0}, {0, 0}, {0, 947}, {0, 0}, {0, 0}, {0, 967}, {0, 0}, {1, 981, 0, 982}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {1, 1016, 16, 1017}, {0, 0}, {5, 1026}, {6, 1041, 0, 1042, 19, 1043, 6, 1044, 0, 1045, 6, 1046}, {0, 0}, {6, 1055, 0, 1056, 6, 1057}, {0, 0}, {0, 1068, 19, 1069, 0, 1070}, {0, 1071, 0, 1072}, {0, 0}, {0, 0}, {0, 1095}, {0, 0}, {0, 0}, {0, 0}, {7, 1133, 0, 1134}, {0, 0}, {0, 1145}, {0, 0}, {0, 1158, 7, 1159, 0, 1160, 8, 1161, 0, 1162}, {0, 1165, 7, 1166}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
	private static short[][] CAMPAIGN_SUCCESS = {{0, 0}, {0, 870, 2, 871, 0, 872, 2, 873, 0, 874, 2, 875, 0, 876, 2, 877, 0, 878, 2, 879, 0, 880, 2, 881, 0, 882}, {2, 888, 0, 889, 16, 890}, {2, 891, 0, 892, 2, 893, 0, 894, 2, 895}, {0, 897, 2, 898}, {2, 899, 0, 900, 2, 901, 0, 902, 2, 903, 16, 904}, {2, 905}, {2, 911, 0, 912, 2, 913, 0, 914, 2, 915}, {2, 916, 0, 917, 2, 918, 0, 919, 2, 920, 0, 921, 2, 922, 0, 923, 2, 924}, {17, 925, 17, 926, 2, 927, 0, 928, 2, 929, 0, 930, 2, 931, 0, 932, 2, 933, 16, 934}, {3, 935, 0, 936, 3, 937, 0, 938, 3, 939, 0, 940, 3, 941, 0, 942, 3, 943, 0, 944, 3, 945, 0, 946}, {0, 948, 4, 949, 0, 950, 4, 951, 0, 952, 4, 953, 0, 954, 4, 955, 0, 956}, {3, 957, 0, 958, 3, 959, 0, 960, 3, 961, 0, 962, 3, 963, 16, 964}, {3, 965, 0, 966}, {17, 972}, {1, 973, 0, 974, 1, 975, 0, 976, 1, 977, 12, 978, 0, 979, 1, 980}, {1, 985, 0, 986, 1, 987}, {1, 988, 0, 989, 1, 990, 0, 991, 1, 992, 0, 993, 1, 994, 0, 995, 1, 996, 0, 997, 1, 998, 0, 999, 1, 1000, 0, 1001, 1, 1002}, {0, 1003, 13, 1004, 0, 1005, 13, 1006, 0, 1007, 1, 1008, 0, 1009}, {1, 1010, 0, 1011, 1, 1012, 0, 1013, 1, 1014}, {0, 1015}, {1, 1021}, {5, 1022, 0, 1023, 5, 1024, 16, 1025}, {6, 1027, 0, 1028, 6, 1029, 0, 1030, 6, 1031, 0, 1032, 6, 1033, 0, 1034, 6, 1035, 0, 1036, 6, 1037, 0, 1038, 6, 1039, 0, 1040}, {0, 0}, {6, 1049, 0, 1050, 6, 1051, 0, 1052, 6, 1053, 0, 1054}, {6, 1058, 0, 1059}, {16, 1060, 0, 1061, 6, 1062, 0, 1063, 6, 1064, 0, 1065, 6, 1066, 0, 1067}, {0, 0}, {0, 1078}, {1, 1079, 0, 1080, 1, 1081}, {1, 1083, 0, 1084, 1, 1085, 0, 1086, 1, 1087, 0, 1088, 1, 1089, 0, 1090, 1, 1091, 0, 1092, 1, 1093, 0, 1094}, {20, 1096, 0, 1097, 20, 1098, 0, 1099, 20, 1100, 0, 1101, 20, 1102, 0, 1103}, {20, 1104, 0, 1105, 6, 1106, 0, 1107, 6, 1108, 0, 1109, 6, 1110, 0, 1111}, {1, 1112, 0, 1113, 1, 1114, 0, 1115, 1, 1116, 0, 1117, 1, 1118, 0, 1119, 1, 1120}, {7, 1121, 0, 1122, 7, 1123, 0, 1124, 7, 1125, 0, 1126, 7, 1127, 0, 1128, 7, 1129, 0, 1130, 7, 1131, 0, 1132}, {7, 1135, 0, 1136}, {1, 1137, 0, 1138, 1, 1139, 0, 1140, 1, 1141, 0, 1142, 1, 1143, 0, 1144}, {0, 1147, 1, 1148}, {1, 1149, 0, 1150, 1, 1151, 0, 1152, 1, 1153, 0, 1154, 1, 1155, 0, 1156, 1, 1157}, {0, 0}, {0, 1167, 7, 1168, 0, 1169, 7, 1170, 0, 1171}, {1, 1172, 0, 1173, 1, 1174, 0, 1175, 1, 1176, 0, 1177, 1, 1178, 0, 1179, 1, 1180}, {0, 1181, 1, 1182, 0, 1183, 1, 1184, 0, 1185, 6, 1186, 0, 1187, 6, 1188, 1, 1189, 6, 1190, 1, 1191, 0, 1192, 6, 1193}, {0, 1194, 0, 1195, 0, 1196, 0, 1197}, {0, 0}};
	private byte[] face;
	private String message;
	private String name;
	private TextBox textBox;
	private int type;
	private int page;
	private Mission mission;
	private final Popup popup;
	private boolean popupOpen;
	private Level level;

	public Dialogue(final Mission var1, final Level var2, final int var3) {
		this();
		this.level = var2;
		set(var1, var3);
	}

	public Dialogue() {
		this.popup = new Popup();
		this.popup.set(GlobalStatus.gameText.getText(216), true);
		this.popupOpen = false;
	}

	public Dialogue(final String var1, final String var2, final byte[] var3) {
		this();
		if (this.textBox == null) {
			this.textBox = new TextBox(15, 25, GlobalStatus.screenWidth - 10 - 20, GlobalStatus.screenHeight - 22 - 8 - 40 + 13, (String)null);
		}

		this.textBox.set(var3, var2, true);
		this.textBox.setText(var1);
		this.name = var2;
		this.textBox.topPadding = 0;
	}

	public Dialogue(final String var1) {
		this(var1, GlobalStatus.gameText.getText(835), Globals.CHAR_COMPUTER);
	}

	public static boolean hasBriefingDialogue(final int var0) {
		if (var0 < CAMPAIGN_BRIEFING.length) {
			return CAMPAIGN_BRIEFING[var0][1] != 0;
		}
		return false;
	}

	public static boolean hasSuccessDialogue(final int var0) {
		if (var0 < CAMPAIGN_BRIEFING.length) {
			return CAMPAIGN_SUCCESS[var0][1] != 0;
		}
		return false;
	}

	public final void set(final Mission var1, final int var2) {
		this.mission = var1;
		this.type = var2;
		Agent var4;
		if (var2 == 2) {
			var4 = var1.getAgent();
			if (var4 != null && !var4.isGenericAgent_()) {
				var4.setAccepted(false);
			}

			var1.setFailed(true);
		} else if (var2 == 1) {
			var4 = null;
			var1.setWon(true);
		}

		this.page = 0;
		if (this.textBox == null) {
			this.textBox = new TextBox(15, 25, GlobalStatus.screenWidth - 10 - 20, GlobalStatus.screenHeight - 22 - 8 - 40 + 13, (String)null);
		}

		try {
			loadContent_();
		} catch (final Exception var3) {
			var3.printStackTrace();
		}

		GlobalStatus.soundManager.playSfx(3);
	}

	public final void OnRelease() {
		this.face = null;
		this.textBox = null;
	}

	public final boolean OnKeyPress_(final int var1) {
		if (!this.popupOpen) {
			if (var1 == 16384) {
				if (this.page < length() - 1) {
					++this.page;
					loadContent_();
				} else {
					return false;
				}
			}

			if (var1 == 8192) {
				if (this.page > 0) {
					--this.page;
					loadContent_();
				}
			}
		}

		if (var1 == 256 && (!lastPageOn() || !firstPageOn())) {
			this.popupOpen = !this.popupOpen;
			if (!this.popupOpen && this.popup.getChoice()) {
				return false;
			}
		}

		if (var1 == 4 && this.popupOpen) {
			this.popup.left();
		}

		if (var1 == 32 && this.popupOpen) {
			this.popup.right();
		}

		return true;
	}

	public final boolean handleScrollPress_(final int var1, final int var2) {
		if ((var1 & 64) != 0) {
			this.textBox.scrollUp(var2);
		}

		if ((var1 & 2) != 0) {
			this.textBox.scrollDown(var2);
		}

		return true;
	}

	private int length() {
		if (!this.mission.isCampaignMission()) {
			return 1;
		}
		if (this.type == 0) {
			return CAMPAIGN_BRIEFING[Status.getCurrentCampaignMission()].length / 2;
		} else {
			return this.type == 1 ? CAMPAIGN_SUCCESS[Status.getCurrentCampaignMission()].length / 2 : 1;
		}
	}

	private boolean firstPageOn() {
		return this.page == 0;
	}

	private boolean lastPageOn() {
		return this.page == length() - 1;
	}

	private void loadContent_() {
		short var1 = -1;
		if (!this.mission.isCampaignMission() && (this.type != 2 || Status.getCurrentCampaignMission() != 42)) {
			if (this.page % 2 == 0) {
				this.face = this.mission.getClientImage();
				this.name = this.mission.getClientName();
			} else {
				this.face = Globals.CHAR_IMAGES[0];
				this.name = GlobalStatus.gameText.getText(819);
			}

			if (this.type == 0) {
				if (this.mission.getType() == 12) {
					this.message = GlobalStatus.gameText.getText(194);
				} else if (this.mission.getType() == 7) {
					this.message = GlobalStatus.gameText.getText(200);
				} else {
					this.message = GlobalStatus.gameText.getText(GameText.freelanceWelcomings[GlobalStatus.random.nextInt(GameText.freelanceWelcomings.length)]);
				}
			} else if (this.type == 1) {
				final boolean var5 = this.mission.getAgent() != null ? this.mission.getAgent().isSpecialAgent() : false;
				if (this.mission.getType() == 12) {
					this.message = GlobalStatus.gameText.getText(192) + (var5 ? "\n\n" + Status.replaceTokens(GlobalStatus.gameText.getText(211), this.mission.getAgent().getStationName(), "#S") : "");
					this.message = Status.replaceTokens(Status.replaceTokens(this.message, this.level.egoScore + "", "#Q1"), this.level.challengerScore + "", "#Q2");
				} else {
					this.message = GlobalStatus.gameText.getText(GameText.freelanceSuccess[GlobalStatus.random.nextInt(GameText.freelanceSuccess.length)]) + "\n\n" + (var5 ? Status.replaceTokens(GlobalStatus.gameText.getText(211), this.mission.getAgent().getStationName(), "#S") : GlobalStatus.gameText.getText(97));
				}

				Status.getStanding().increase(this.mission.getClientRace());
			} else if (this.mission.getType() == 12) {
				this.message = Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(193), this.level.egoScore + "", "#Q1"), this.level.challengerScore + "", "#Q2");
			} else {
				this.message = GlobalStatus.gameText.getText(GameText.freelanceFail[GlobalStatus.random.nextInt(GameText.freelanceFail.length)]) + "\n\n" + GlobalStatus.gameText.getText(213);
			}
		} else {
			var1 = this.type == 0 ? CAMPAIGN_BRIEFING[Status.getCurrentCampaignMission()][2 * this.page] : this.type == 1 ? CAMPAIGN_SUCCESS[Status.getCurrentCampaignMission()][2 * this.page] : 16;
			this.face = Globals.CHAR_IMAGES[var1];
			this.name = GlobalStatus.gameText.getText(var1 + 819);
			if (this.type == 0) {
				this.message = GlobalStatus.gameText.getText(CAMPAIGN_BRIEFING[Status.getCurrentCampaignMission()][2 * this.page + 1]);
			} else if (this.type == 1) {
				this.message = GlobalStatus.gameText.getText(CAMPAIGN_SUCCESS[Status.getCurrentCampaignMission()][2 * this.page + 1]);
			} else {
				int var2;
				final String var4 = (var2 = Status.getCurrentCampaignMission()) != 38 && var2 != 40 && var2 != 41 ? "" : GlobalStatus.gameText.getText(256);
				this.message = GlobalStatus.gameText.getText(213) + "\n" + var4 + "\n\n" + GlobalStatus.gameText.getText(156);
			}
		}

		this.textBox.set(this.face, this.name, var1 == 16 ? true : this.page % 2 == 1);
		this.textBox.setText(this.message);
		this.textBox.topPadding = 0;
		byte var3;
		if (var1 == 19) {
			var3 = 3;
		} else {
			var3 = 1;
		}
		this.textBox.font = var3;
	}

	public final void draw() {
		Layout.drawMask(10, 10, GlobalStatus.screenWidth - 20, this.textBox.getHeight_());
		Layout.drawTitleBarWindow(this.name, 10, 10, GlobalStatus.screenWidth - 20, this.textBox.getHeight_(), false);
		this.textBox.draw();
		Layout.drawFooter(lastPageOn() ? this.mission.isCampaignMission() && this.type == 2 ? GlobalStatus.gameText.getText(67) : this.type != 2 && this.type != 1 ? GlobalStatus.gameText.getText(76) : GlobalStatus.gameText.getText(253) : GlobalStatus.gameText.getText(75), !firstPageOn() ? GlobalStatus.gameText.getText(74) : "", !lastPageOn() || !firstPageOn());
		if (this.popupOpen) {
			this.popup.draw();
		}

	}

	public final void drawInterupring_() {
		Layout.fillClip();
		Layout.drawTitleBarWindow(this.name, 10, 10, GlobalStatus.screenWidth - 20, this.textBox.getHeight_() + 20, true);
		this.textBox.draw();
		Layout.drawTextItem(GlobalStatus.gameText.getText(35), 10, 10 + this.textBox.getHeight_(), GlobalStatus.screenWidth - 20, true, true);
	}
}
