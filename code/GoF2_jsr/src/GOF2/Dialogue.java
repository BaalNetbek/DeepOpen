package GOF2;

import AE.GlobalStatus;
import AE.PaintCanvas.Font;
import GOF2.Main.GOF2Canvas;

public final class Dialogue {
    private static short[][] CAMPAIGN_BRIEFING = {
/*  0 */  {Globals.FACE_COMPUTER, 853},
/*  1 */  {Globals.FACE_KEITH, 0},
/*  2 */  {Globals.FACE_KEITH, 883, Globals.FACE_GUNANT, 884, Globals.FACE_KEITH, 885, Globals.FACE_GUNANT, 886, Globals.FACE_COMPUTER, 887},
/*  3 */  {Globals.FACE_KEITH, 0},
/*  4 */  {Globals.FACE_GUNANT, 896},
/*  5 */  {Globals.FACE_KEITH, 0},
/*  6 */  {Globals.FACE_KEITH, 0},
/*  7 */  {Globals.FACE_GUNANT, 906, Globals.FACE_COMPUTER, 907, Globals.FACE_COMPUTER, 908},
/*  9 */  {Globals.FACE_KEITH, 0},
/* 10 */  {Globals.FACE_KEITH, 0},
/* 11 */  {Globals.FACE_KEITH, 0},
/* 12 */  {Globals.FACE_KEITH, 947},
/* 13 */  {Globals.FACE_KEITH, 0},
/* 14 */  {Globals.FACE_KEITH, 0},
/* 15 */  {Globals.FACE_KEITH, 967},
/* 16 */  {Globals.FACE_KEITH, 0},
/* 17 */  {Globals.FACE_BRENT, 981, Globals.FACE_KEITH, 982},
/* 18 */  {Globals.FACE_KEITH, 0},
/* 19 */  {Globals.FACE_KEITH, 0},
/* 20 */  {Globals.FACE_KEITH, 0},
/* 21 */  {Globals.FACE_KEITH, 0},
/* 22 */  {Globals.FACE_BRENT, 1016, Globals.FACE_COMPUTER, 1017},
/* 23 */  {Globals.FACE_KEITH, 0},
/* 24 */  {Globals.FACE_TOMMY, 1026},
/* 25 */  {Globals.FACE_CARLA, 1041, Globals.FACE_KEITH, 1042, Globals.FACE_VOID, 1043, Globals.FACE_CARLA, 1044, Globals.FACE_KEITH, 1045,
           Globals.FACE_CARLA, 1046
           },
/* 26 */  {Globals.FACE_KEITH, 0},
/* 27 */  {Globals.FACE_CARLA, 1055, Globals.FACE_KEITH, 1056, Globals.FACE_CARLA, 1057},
/* 28 */  {Globals.FACE_KEITH, 0},
/* 29 */  {Globals.FACE_KEITH, 1068, Globals.FACE_VOID, 1069, Globals.FACE_KEITH, 1070},
/* 30 */  {Globals.FACE_KEITH, 1071, Globals.FACE_KEITH, 1072},
/* 31 */  {Globals.FACE_KEITH, 0},
/* 32 */  {Globals.FACE_KEITH, 0},
/* 33 */  {Globals.FACE_KEITH, 1095},
/* 34 */  {Globals.FACE_KEITH, 0},
/* 35 */  {Globals.FACE_KEITH, 0},
/* 36 */  {Globals.FACE_KEITH, 0},
/* 37 */  {Globals.FACE_ERRKT, 1133, Globals.FACE_KEITH, 1134},
/* 38 */  {Globals.FACE_KEITH, 0},
/* 39 */  {Globals.FACE_KEITH, 1145},
/* 40 */  {Globals.FACE_KEITH, 0},
/* 41 */  {Globals.FACE_KEITH, 1158, Globals.FACE_ERRKT, 1159, Globals.FACE_KEITH, 1160, Globals.FACE_JEAN, 1161, Globals.FACE_KEITH, 1162},
/* 42 */  {Globals.FACE_KEITH, 1165, Globals.FACE_ERRKT, 1166},
/* 43 */  {Globals.FACE_KEITH, 0},
/* 44 */  {Globals.FACE_KEITH, 0},
/* 45 */  {Globals.FACE_KEITH, 0},
/* 46 */  {Globals.FACE_KEITH, 0}};
    private static short[][] CAMPAIGN_SUCCESS = {
/*  0 */  {Globals.FACE_KEITH, 0},
/*  1 */  {Globals.FACE_KEITH, 870, Globals.FACE_GUNANT, 871, Globals.FACE_KEITH, 872, Globals.FACE_GUNANT, 873, Globals.FACE_KEITH, 874,
           Globals.FACE_GUNANT, 875, Globals.FACE_KEITH, 876, Globals.FACE_GUNANT, 877, Globals.FACE_KEITH, 878, Globals.FACE_GUNANT, 879,
           Globals.FACE_KEITH, 880, Globals.FACE_GUNANT, 881, Globals.FACE_KEITH, 882
           },
/*  2 */  {Globals.FACE_GUNANT, 888, Globals.FACE_KEITH, 889, Globals.FACE_COMPUTER, 890},
/*  3 */  {Globals.FACE_GUNANT, 891, Globals.FACE_KEITH, 892, Globals.FACE_GUNANT, 893, Globals.FACE_KEITH, 894, Globals.FACE_GUNANT, 895},
/*  4 */  {Globals.FACE_KEITH, 897, Globals.FACE_GUNANT, 898},
/*  5 */  {Globals.FACE_GUNANT, 899, Globals.FACE_KEITH, 900, Globals.FACE_GUNANT, 901, Globals.FACE_KEITH, 902, Globals.FACE_GUNANT, 903,
           Globals.FACE_COMPUTER, 904},
/*  6 */  {Globals.FACE_GUNANT, 905},
/*  7 */  {Globals.FACE_GUNANT, 911, Globals.FACE_KEITH, 912, Globals.FACE_GUNANT, 913, Globals.FACE_KEITH, 914, Globals.FACE_GUNANT, 915},
/*  8 */  {Globals.FACE_GUNANT, 916, Globals.FACE_KEITH, 917, Globals.FACE_GUNANT, 918, Globals.FACE_KEITH, 919, Globals.FACE_GUNANT, 920,
           Globals.FACE_KEITH, 921, Globals.FACE_GUNANT, 922, Globals.FACE_KEITH, 923, Globals.FACE_GUNANT, 924
           },          
/*  9 */  {Globals.FACE_INFO_PIC, 925, Globals.FACE_INFO_PIC, 926, Globals.FACE_GUNANT, 927, Globals.FACE_KEITH, 928, Globals.FACE_GUNANT, 929,
            Globals.FACE_KEITH, 930, Globals.FACE_GUNANT, 931, Globals.FACE_KEITH, 932, Globals.FACE_GUNANT, 933, Globals.FACE_COMPUTER, 934
            },
/* 10 */  {Globals.FACE_NORRIS, 935, Globals.FACE_KEITH, 936, Globals.FACE_NORRIS, 937, Globals.FACE_KEITH, 938, Globals.FACE_NORRIS, 939,
           Globals.FACE_KEITH, 940, Globals.FACE_NORRIS, 941, Globals.FACE_KEITH, 942, Globals.FACE_NORRIS, 943, Globals.FACE_KEITH, 944,
           Globals.FACE_NORRIS, 945, Globals.FACE_KEITH, 946
           },          
/* 11 */  {Globals.FACE_KEITH, 948, Globals.FACE_MKKT_BKKT, 949, Globals.FACE_KEITH, 950, Globals.FACE_MKKT_BKKT, 951, Globals.FACE_KEITH, 952,
           Globals.FACE_MKKT_BKKT, 953, Globals.FACE_KEITH, 954, Globals.FACE_MKKT_BKKT, 955, Globals.FACE_KEITH, 956
           },          
/* 12 */  {Globals.FACE_NORRIS, 957, Globals.FACE_KEITH, 958, Globals.FACE_NORRIS, 959, Globals.FACE_KEITH, 960, Globals.FACE_NORRIS, 961,
           Globals.FACE_KEITH, 962, Globals.FACE_NORRIS, 963, Globals.FACE_COMPUTER, 964
           },          
/* 13 */  {Globals.FACE_NORRIS, 965, Globals.FACE_KEITH, 966},
/* 14 */  {Globals.FACE_INFO_PIC, 972},
/* 15 */  {Globals.FACE_BRENT, 973, Globals.FACE_KEITH, 974, Globals.FACE_BRENT, 975, Globals.FACE_KEITH, 976, Globals.FACE_BRENT, 977,
           Globals.FACE_SECURITY_GUY, 978, Globals.FACE_KEITH, 979, Globals.FACE_BRENT, 980
           },          
/* 16 */  {Globals.FACE_BRENT, 985, Globals.FACE_KEITH, 986, Globals.FACE_BRENT, 987},
/* 17 */  {Globals.FACE_BRENT, 988, Globals.FACE_KEITH, 989, Globals.FACE_BRENT, 990, Globals.FACE_KEITH, 991, Globals.FACE_BRENT, 992,
           Globals.FACE_KEITH, 993, Globals.FACE_BRENT, 994, Globals.FACE_KEITH, 995, Globals.FACE_BRENT, 996, Globals.FACE_KEITH, 997,
           Globals.FACE_BRENT, 998, Globals.FACE_KEITH, 999, Globals.FACE_BRENT, 1000,Globals.FACE_KEITH, 1001,Globals.FACE_BRENT, 1002
           },          
/* 18 */  {Globals.FACE_KEITH, 1003, Globals.FACE_SECURITY_GIRL, 1004, Globals.FACE_KEITH, 1005, Globals.FACE_SECURITY_GIRL, 1006,
           Globals.FACE_KEITH, 1007, Globals.FACE_BRENT, 1008, Globals.FACE_KEITH, 1009
           },
/* 19 */  {Globals.FACE_BRENT, 1010, Globals.FACE_KEITH, 1011, Globals.FACE_BRENT, 1012, Globals.FACE_KEITH, 1013,
           Globals.FACE_BRENT, 1014
           },
/* 20 */  {Globals.FACE_KEITH, 1015},
/* 21 */  {Globals.FACE_BRENT, 1021},
/* 22 */  {Globals.FACE_TOMMY, 1022, Globals.FACE_KEITH, 1023, Globals.FACE_TOMMY, 1024, Globals.FACE_COMPUTER, 1025},
/* 23 */  {Globals.FACE_CARLA, 1027, Globals.FACE_KEITH, 1028, Globals.FACE_CARLA, 1029, Globals.FACE_KEITH, 1030,
           Globals.FACE_CARLA, 1031, Globals.FACE_KEITH, 1032, Globals.FACE_CARLA, 1033, Globals.FACE_KEITH, 1034,
           Globals.FACE_CARLA, 1035, Globals.FACE_KEITH, 1036, Globals.FACE_CARLA, 1037, Globals.FACE_KEITH, 1038,
           Globals.FACE_CARLA, 1039, Globals.FACE_KEITH, 1040
           },
/* 24 */  {Globals.FACE_KEITH, 0},
/* 25 */  {Globals.FACE_CARLA, 1049, Globals.FACE_KEITH, 1050, Globals.FACE_CARLA, 1051, Globals.FACE_KEITH, 1052,
           Globals.FACE_CARLA, 1053, Globals.FACE_KEITH, 1054
           },
/* 26 */  {Globals.FACE_CARLA, 1058, Globals.FACE_KEITH, 1059},
/* 27 */  {Globals.FACE_COMPUTER, 1060, Globals.FACE_KEITH, 1061, Globals.FACE_CARLA, 1062, Globals.FACE_KEITH, 1063,
            Globals.FACE_CARLA, 1064, Globals.FACE_KEITH, 1065, Globals.FACE_CARLA, 1066, Globals.FACE_KEITH, 1067
           },
/* 28 */  {Globals.FACE_KEITH, 0},
/* 29 */  {Globals.FACE_KEITH, 1078},
/* 31 */  {Globals.FACE_BRENT, 1079, Globals.FACE_KEITH, 1080, Globals.FACE_BRENT, 1081},
/* 32 */  {Globals.FACE_BRENT, 1083, Globals.FACE_KEITH, 1084, Globals.FACE_BRENT, 1085, Globals.FACE_KEITH, 1086,
           Globals.FACE_BRENT, 1087, Globals.FACE_KEITH, 1088, Globals.FACE_BRENT, 1089, Globals.FACE_KEITH, 1090,
           Globals.FACE_BRENT, 1091, Globals.FACE_KEITH, 1092, Globals.FACE_BRENT, 1093, Globals.FACE_KEITH, 1094
           },
/* 33 */  {Globals.FACE_KHADOR, 1096, Globals.FACE_KEITH, 1097, Globals.FACE_KHADOR, 1098, Globals.FACE_KEITH, 1099,
           Globals.FACE_KHADOR, 1100, Globals.FACE_KEITH, 1101, Globals.FACE_KHADOR, 1102, Globals.FACE_KEITH, 1103
           },
/* 34 */  {Globals.FACE_KHADOR, 1104, Globals.FACE_KEITH, 1105, Globals.FACE_CARLA, 1106, Globals.FACE_KEITH, 1107, 
           Globals.FACE_CARLA, 1108, Globals.FACE_KEITH, 1109, Globals.FACE_CARLA, 1110, Globals.FACE_KEITH, 1111
            },
/* 35 */  {Globals.FACE_BRENT, 1112, Globals.FACE_KEITH, 1113, Globals.FACE_BRENT, 1114, Globals.FACE_KEITH, 1115,
           Globals.FACE_BRENT, 1116, Globals.FACE_KEITH, 1117, Globals.FACE_BRENT, 1118, Globals.FACE_KEITH, 1119,
           Globals.FACE_BRENT, 1120
           },          
/* 36 */  {Globals.FACE_ERRKT, 1121, Globals.FACE_KEITH, 1122, Globals.FACE_ERRKT, 1123, Globals.FACE_KEITH, 1124,
           Globals.FACE_ERRKT, 1125, Globals.FACE_KEITH, 1126, Globals.FACE_ERRKT, 1127, Globals.FACE_KEITH, 1128,
           Globals.FACE_ERRKT, 1129, Globals.FACE_KEITH, 1130, Globals.FACE_ERRKT, 1131, Globals.FACE_KEITH, 1132
           },
/* 37 */  {Globals.FACE_ERRKT, 1135, Globals.FACE_KEITH, 1136},
/* 38 */  {Globals.FACE_BRENT, 1137, Globals.FACE_KEITH, 1138, Globals.FACE_BRENT, 1139, Globals.FACE_KEITH, 1140,
           Globals.FACE_BRENT, 1141, Globals.FACE_KEITH, 1142, Globals.FACE_BRENT, 1143, Globals.FACE_KEITH, 1144
           },
/* 39 */  {Globals.FACE_KEITH, 1147, Globals.FACE_BRENT, 1148},
/* 40 */  {Globals.FACE_BRENT, 1149, Globals.FACE_KEITH, 1150, Globals.FACE_BRENT, 1151, Globals.FACE_KEITH, 1152,
           Globals.FACE_BRENT, 1153, Globals.FACE_KEITH, 1154, Globals.FACE_BRENT, 1155, Globals.FACE_KEITH, 1156,
           Globals.FACE_BRENT, 1157
           },
/* 41 */  {Globals.FACE_KEITH, 0},
/* 42 */  {Globals.FACE_KEITH, 1167, Globals.FACE_ERRKT, 1168, Globals.FACE_KEITH, 1169, Globals.FACE_ERRKT, 1170,
           Globals.FACE_KEITH, 1171
           },
/* 43 */  {Globals.FACE_BRENT, 1172, Globals.FACE_KEITH, 1173, Globals.FACE_BRENT, 1174, Globals.FACE_KEITH, 1175,
           Globals.FACE_BRENT, 1176, Globals.FACE_KEITH, 1177, Globals.FACE_BRENT, 1178, Globals.FACE_KEITH, 1179,
           Globals.FACE_BRENT, 1180
           },
/* 44 */  {Globals.FACE_KEITH, 1181, Globals.FACE_BRENT, 1182, Globals.FACE_KEITH, 1183, Globals.FACE_BRENT, 1184,
           Globals.FACE_KEITH, 1185, Globals.FACE_CARLA, 1186, Globals.FACE_KEITH, 1187, Globals.FACE_CARLA, 1188, 
           Globals.FACE_BRENT, 1189, Globals.FACE_CARLA, 1190, Globals.FACE_BRENT, 1191, Globals.FACE_KEITH, 1192,
           Globals.FACE_CARLA, 1193
           },
/* 45 */  {Globals.FACE_KEITH, 1194, Globals.FACE_KEITH, 1195, Globals.FACE_KEITH, 1196, Globals.FACE_KEITH, 1197},
/* 46 */  {Globals.FACE_KEITH, 0}
        };
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
                var4.setOfferAccepted(false);
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
            if (var1 == GOF2Canvas.LSB) {
                if (this.page < length() - 1) {
                    ++this.page;
                    loadContent_();
                } else {
                    return false;
                }
            }

            if (var1 == GOF2Canvas.RSB) {
                if (this.page > 0) {
                    --this.page;
                    loadContent_();
                }
            }
        }

        if (var1 == GOF2Canvas.KEY_5 && (!lastPageOn() || !firstPageOn())) {
            this.popupOpen = !this.popupOpen;
            if (!this.popupOpen && this.popup.getChoice()) {
                return false;
            }
        }

        if (var1 == GOF2Canvas.LEFT && this.popupOpen) {
            this.popup.left();
        }

        if (var1 == GOF2Canvas.RIGHT && this.popupOpen) {
            this.popup.right();
        }

        return true;
    }

    public final boolean handleScrollPress_(final int var1, final int var2) {
        if ((var1 & GOF2Canvas.DOWN) != 0) {
            this.textBox.scrollUp(var2);
        }

        if ((var1 & GOF2Canvas.UP) != 0) {
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
                if (this.mission.getType() == Mission.TYPE_12) {
                    this.message = GlobalStatus.gameText.getText(194);
                } else if (this.mission.getType() == Mission.TYPE_7) {
                    this.message = GlobalStatus.gameText.getText(200);
                } else {
                    this.message = GlobalStatus.gameText.getText(GameText.MISSION_START_MSG[GlobalStatus.random.nextInt(GameText.MISSION_START_MSG.length)]);
                }
            } else if (this.type == 1) {
                final boolean var5 = this.mission.getAgent() != null ? this.mission.getAgent().isSpecialAgent() : false;
                if (this.mission.getType() == Mission.TYPE_12) {
                    this.message = GlobalStatus.gameText.getText(192) + (var5 ? "\n\n" + Status.replaceTokens(GlobalStatus.gameText.getText(211), this.mission.getAgent().getStationName(), "#S") : "");
                    this.message = Status.replaceTokens(Status.replaceTokens(this.message, this.level.egoScore + "", "#Q1"), this.level.challengerScore + "", "#Q2");
                } else {
                    this.message = GlobalStatus.gameText.getText(GameText.MISSION_SUCCESS_MSG[GlobalStatus.random.nextInt(GameText.MISSION_SUCCESS_MSG.length)]) + "\n\n" + (var5 ? Status.replaceTokens(GlobalStatus.gameText.getText(211), this.mission.getAgent().getStationName(), "#S") : GlobalStatus.gameText.getText(97));
                }

                Status.getStanding().increase(this.mission.getClientRace());
            } else if (this.mission.getType() == Mission.TYPE_12) {
                this.message = Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(193), this.level.egoScore + "", "#Q1"), this.level.challengerScore + "", "#Q2");
            } else {
                this.message = GlobalStatus.gameText.getText(GameText.MISSION_LOST_MSG[GlobalStatus.random.nextInt(GameText.MISSION_LOST_MSG.length)]) + "\n\n" + GlobalStatus.gameText.getText(213);
            }
        } else {
            var1 = (this.type == 0) ? CAMPAIGN_BRIEFING[Status.getCurrentCampaignMission()][2 * this.page] : this.type == 1 ? CAMPAIGN_SUCCESS[Status.getCurrentCampaignMission()][2 * this.page] : 16;
            this.face = Globals.CHAR_IMAGES[var1];
            this.name = GlobalStatus.gameText.getText(var1 + 819);
            if (this.type == 0) {
                this.message = GlobalStatus.gameText.getText(CAMPAIGN_BRIEFING[Status.getCurrentCampaignMission()][2 * this.page + 1]);
            } else if (this.type == 1) {
                this.message = GlobalStatus.gameText.getText(CAMPAIGN_SUCCESS[Status.getCurrentCampaignMission()][2 * this.page + 1]);
            } else {
                int var2 = Status.getCurrentCampaignMission();
                final String var4 = (var2 != 38 && var2 != 40 && var2 != 41 ? "" : GlobalStatus.gameText.getText(256));
                this.message = GlobalStatus.gameText.getText(213) + "\n" + var4 + "\n\n" + GlobalStatus.gameText.getText(156);
            }
        }

        this.textBox.set(this.face, this.name, var1 == Globals.FACE_COMPUTER ? true : this.page % 2 == 1);
        this.textBox.setText(this.message);
        this.textBox.topPadding = 0;
        byte var3;
        if (var1 == Globals.FACE_VOID) {
            var3 = Font.VOID;
        } else {
            var3 = Font.GRAY;
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
