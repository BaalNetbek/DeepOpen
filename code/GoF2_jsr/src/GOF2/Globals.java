package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.AEGroup;

public final class Globals {
	public static final boolean AETrue = true;
	public static final boolean AEFalse = false;
	public static final int AENone = -1;
	
	public static final int STATIONS_COUNT = 100;
    public static final int VOSSK_STATION_ID = 100;
    public static final int SYSTEMS_COUNT = 22;
	public static final int ITEMS_COUNT = 176;
	public static final int SHIPS_COUNT = 37;
	public static final int SPECIAL_AGENTS_COUNT = 16;
    // races
    public static final int TERRAN = 0;
    public static final int VOSSK = 1;
    public static final int NIVELIAN = 2;
    public static final int MIDORIAN = 3;
    public static final int MULTIPOD = 4;
    public static final int CYBORG = 5;
    public static final int BOBOLIAN = 6;
    public static final int GREY = 7;
    public static final int PIRATE = 8;
    public static final int VOID = 9;
    public static final int WOMAN = 10;
    public static final int MISC = 11;

    private static String PATH_MESHES = "/data/3d/";
    private static String PATH_TEXTURES = "/data/textures/";
    public static final short[][] BAR_MESHES = {
          {14025},
          {14027},
          {14029}
          };
    public static final short[] BAR_FIGURES = {
          14223, 14225, 14226, 14223,
          14227, 14223, 14228, 14229
          };
    public static final short[][] HANGAR_MESHES = {
          {14007, 14008, 14009, 14010, 14011, 14012, 14013},
          {14014, 14015, 14016, 14017, 14018, 14019},
          {14020, 14021, 14022, 14023, 14024}
          };
    public static final short[] TYPE_WEAPONS = {
          6754, 6755, 6756, 6760, 6761, 6762, 6763, 6764,
          6765, AENone, AENone, AENone, 6788, 6789, 6790, 6791, 6792,
          6793, 6794, 6795, 6796, 6797, 6798, 6799, 6800,
          6801, 6802, 6803, 6781, 6781, 6781, 6791, 6789,
          6788, 6790, 6792, 6797, 6794, 6791, 6796, 6788,
          AENone, AENone, AENone, AENone, AENone, AENone, 6784, 6785, 6786
          };
    public static final byte[] CHAR_KEITH =			{TERRAN, 7, 7, 7, 7};
    private static byte[] CHAR_BRENT =				{TERRAN, 2, 2, 2, 2};
    private static byte[] CHAR_GUNANT =				{NIVELIAN, 1, 1, 1, 1};
    private static byte[] CHAR_NORRIS =				{TERRAN, 3, 3, 3, 3};
    private static byte[] CHAR_MKKT_BKKT =			{MULTIPOD, 0, 0, 0, 0};
    private static byte[] CHAR_TOMMY =				{TERRAN, 4, 4, 4, 4};
    private static byte[] CHAR_CARLA =				{WOMAN, 0, 0, 0, 0};
    private static byte[] CHAR_ERRKT =				{VOSSK, 0, 0, 0, 0};
    private static byte[] CHAR_JEAN =				{TERRAN, 3, 2, 3, 1};
    private static byte[] CHAR_PIRATE_CHIEF =		{TERRAN, 1, 2, 3, 4};
    private static byte[] CHAR_PIRATE_1 =			{TERRAN, 2, 3, 3, 5};
    private static byte[] CHAR_PIRATE_2 =			{TERRAN, 3, 4, 3, 6};
    private static byte[] CHAR_SECURITY_GUY =		{TERRAN, 6, 0, 5, 1};
    private static byte[] CHAR_SECURITY_GIRL =		{WOMAN, 1, 2, 3, 4};
    private static byte[] CHAR_KIDNAPPER =			{NIVELIAN,  1, 2, 1, 2};
    private static byte[] CHAR_STORY =				{MISC, 1, 0, 0, 0};
    public static final byte[] CHAR_COMPUTER =		{MISC, 1, 0, 0, 0};
    private static byte[] CHAR_INFO_PIC =			{MISC, 0, 0, 0, 0};
    private static byte[] CHAR_TERRAN_OFFICER =		{TERRAN,  2, 4, 4, 4};
    private static byte[] CHAR_VOID =				{VOID, 0, 0, 0, 0};
    private static byte[] CHAR_KHADOR =				{GREY, 0, 0, 0, 0};
    /** unused? */
    private static byte[] CHAR_MIDORIAN =	        {NIVELIAN, 0, 0, 0, 0}; 
    public static final byte[][] CHAR_IMAGES = { 
		CHAR_KEITH,          CHAR_BRENT,
        CHAR_GUNANT,         CHAR_NORRIS,
        CHAR_MKKT_BKKT,      CHAR_TOMMY,
        CHAR_CARLA,          CHAR_ERRKT,
        CHAR_JEAN,           CHAR_PIRATE_CHIEF,
        CHAR_PIRATE_1,       CHAR_PIRATE_2,
        CHAR_SECURITY_GUY,   CHAR_SECURITY_GIRL,
        CHAR_KIDNAPPER,      CHAR_STORY,
        CHAR_COMPUTER,       CHAR_INFO_PIC,
        CHAR_TERRAN_OFFICER, CHAR_VOID,
        CHAR_KHADOR,         CHAR_MIDORIAN
    };
    public static final int FACE_KEITH =            0;
    public static final int FACE_BRENT =            1;
    public static final int FACE_GUNANT =           2;
    public static final int FACE_NORRIS =           3;
    public static final int FACE_MKKT_BKKT =        4;
    public static final int FACE_TOMMY =            5;
    public static final int FACE_CARLA =            6;
    public static final int FACE_ERRKT =            7;
    public static final int FACE_JEAN =             8;
    public static final int FACE_PIRATE_CHIEF =	    9;
    public static final int FACE_PIRATE_1 =	        10;
    public static final int FACE_PIRATE_2 =	        11;
    public static final int FACE_SECURITY_GUY =	    12;
    public static final int FACE_SECURITY_GIRL =    13;
    public static final int FACE_KIDNAPPER =        14;
    public static final int FACE_STORY =            15;
    public static final int FACE_COMPUTER =         16;
    public static final int FACE_INFO_PIC =	        17;
    public static final int FACE_TERRAN_OFFICER =   18;
    public static final int FACE_VOID =	            19;
    public static final int FACE_KHADOR =           20;
    
    public static final int FACE_MIDORIAN =	        21;
    public static final int FACE_NIVELIAN =         22;
    public static final int FACE_TERRAN =	        23;
    public static final int FACE_VOSSK =         	24;
    public static final int FACE_GENERICS_START = FACE_MIDORIAN;

    private static Ship[] ships;
    private static Item[] items;

    private Globals() {
    }

    public static void OnInitialize() {
        //new FileRead();
        ships = FileRead.loadShipsBinary();
        items = FileRead.loadItemsBinary();
    }

    public static Ship[] getShips() {
        return ships;
    }

    public static Item[] getItems() {
        return items;
    }

    public static String getItemName(final int var0) {
        return GlobalStatus.gameText.getText(var0 + 569);
    }

    public static int[] getRaceUVkeyframeId_(final int var0) {
        switch(var0) {
        case NIVELIAN:
            return new int[]{2, 2};
        case MIDORIAN:
            return new int[]{3, 3};
        case PIRATE:
            return new int[]{0, 0};
        default:
            return new int[]{1, 1};
        }
    }

    public static String getRandomName(final int race, final boolean isMale) {
        //new FileRead();
        final String[] firstNames = FileRead.loadNamesBinary(race, isMale, true);
        final String[] lastNames = FileRead.loadNamesBinary(race, isMale, false);
        final String firstName = firstNames == null ? "" : firstNames[GlobalStatus.random.nextInt(firstNames.length)];
        final String lastName = lastNames == null ? "" : lastNames[GlobalStatus.random.nextInt(lastNames.length)];
        //if (lastName == ""/null) return firstName;
        return firstName + " " + lastName;
    }

    public static String getRandomPlanetName() {
        return new FileRead().loadStation(GlobalStatus.random.nextInt(100)).getName();
    }

    public static int getRandomEnemyFighter(final int race) {
        int ship;
        if (race == VOID) {
            ship = Ship.SHIP_8;
        }
        else if (race == VOSSK) {
            ship = Ship.SHIP_9;
        }
        else {
            do {
                ship = GlobalStatus.random.nextInt(SHIPS_COUNT);
            } while (ship == Ship.SHIP_0 || 
                    ship == Ship.SHIP_9 ||
                    ship == Ship.SHIP_8 ||
                    ship == Ship.SHIP_10 ||
                    ship == Ship.SHIP_13 ||
                    ship == Ship.SHIP_14 ||
                    ship == Ship.SHIP_15);
        }
        return ship;
    }
    /**
     *
     * @param ship ship index
     * @param race race
     * @return ship's mesh group
     */
    public static AEGroup getShipGroup(final int ship, final int race) {
        final AEGroup var2 = new AEGroup();
        final int[] parts = FileRead.loadShipParts(ship);
        final int[] var6 = getRaceUVkeyframeId_(race);

		for (int i = 0; i < parts.length; i += 10) {
			AEGeometry mesh;
			if ((mesh = AEResourceManager.getGeometryResource(parts[i])).getID() >= 13064 && mesh.getID() <= 13071) {
				mesh.setAnimationMode((byte) 2);
			} else {
				mesh.setAnimationRangeInTime(var6[0], var6[1]);
				mesh.disableAnimation();
			}

			if (mesh.getID() == 13067 || mesh.getID() == 13068 || mesh.getID() == 13070 || mesh.getID() == 13064
					|| mesh.getID() == 13065 || mesh.getID() == 13071 || mesh.getID() == 13061 || mesh.getID() == 13063
					|| mesh.getID() == 13062 || ship == Ship.SHIP_14 || ship == Ship.SHIP_13 || ship == Ship.SHIP_15) {
				mesh.moveTo(
						parts[i + FileRead.POSITION_X],
						parts[i + FileRead.POSITION_Y],
						parts[i + FileRead.POSITION_Z]
				);
				mesh.setRotation(
						parts[i + FileRead.ROTATION_X],
						parts[i + FileRead.ROTATION_Y],
						parts[i + FileRead.ROTATION_Z]
				);
				mesh.setScale(
						parts[i + FileRead.SCALE_X],
						parts[i + FileRead.SCALE_Y],
						parts[i + FileRead.SCALE_Z]
				);
			}

			mesh.setRenderLayer(2);
			mesh.setDraw(true);
			var2.uniqueAppend_(mesh);
		}

        return var2;
    }

    public static void buildShip(final AEGroup var0, final int var1) {
        //new FileRead();
        int[] parts = FileRead.loadShipParts(var1);
        int i = parts.length - 10;

        for(GraphNode node = var0.getEndNode(); node != null; node = node.getParent()) {
        	node.moveTo(
					parts[i + FileRead.POSITION_X],
					parts[i + FileRead.POSITION_Y],
					parts[i + FileRead.POSITION_Z]
			);
        	node.setRotation(
					parts[i + FileRead.ROTATION_X],
					parts[i + FileRead.ROTATION_Y],
					parts[i + FileRead.ROTATION_Z]
			);
        	node.setScale(
					parts[i + FileRead.SCALE_X],
					parts[i + FileRead.SCALE_Y],
					parts[i + FileRead.SCALE_Z]
			);
            i -= 10;
        }

    }

    public static void buildResourceList() {
        AEResourceManager.addTextureResource(0, PATH_TEXTURES + "space");
        AEResourceManager.addTextureResource(1, PATH_TEXTURES + "space");
        AEResourceManager.addSkyboxResource(9991, PATH_MESHES + "skybox.m3g", 1);
        AEResourceManager.addGeometryResource(18, PATH_MESHES + "nuke.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(16, PATH_MESHES + "emp.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(17, PATH_MESHES + "box.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(15, PATH_MESHES + "jumpgate.m3g", 5000, 0);
        AEResourceManager.addGeometryResource(3301, PATH_MESHES + "stat_arm0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3302, PATH_MESHES + "stat_arm1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3303, PATH_MESHES + "stat_bottom0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3304, PATH_MESHES + "stat_bottom1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3305, PATH_MESHES + "stat_bottom2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3306, PATH_MESHES + "stat_bottom3.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3307, PATH_MESHES + "stat_bottom4.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3308, PATH_MESHES + "stat_bottom5.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3309, PATH_MESHES + "stat_bridge0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3310, PATH_MESHES + "stat_bridge1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3311, PATH_MESHES + "stat_bridge2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3312, PATH_MESHES + "stat_connector0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3313, PATH_MESHES + "stat_hangar0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3314, PATH_MESHES + "stat_hangar1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3315, PATH_MESHES + "stat_hangar2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3316, PATH_MESHES + "stat_hangar3.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3317, PATH_MESHES + "stat_hangar4.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3318, PATH_MESHES + "stat_middle0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3319, PATH_MESHES + "stat_middle1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3320, PATH_MESHES + "stat_middle2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3321, PATH_MESHES + "stat_middle3.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3322, PATH_MESHES + "stat_top0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3323, PATH_MESHES + "stat_top1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3325, PATH_MESHES + "stat_top2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3326, PATH_MESHES + "stat_top3.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3327, PATH_MESHES + "stat_top4.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3328, PATH_MESHES + "stat_top5.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3329, PATH_MESHES + "stat_top6.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3330, PATH_MESHES + "stat_top7.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3331, PATH_MESHES + "stat_top8.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3332, PATH_MESHES + "stat_top9.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3324, PATH_MESHES + "stat_top10.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3333, PATH_MESHES + "stat_bridge3.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3334, PATH_MESHES + "stat_light0.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3335, PATH_MESHES + "stat_light1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3338, PATH_MESHES + "stat_vossk_arm1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3340, PATH_MESHES + "stat_vossk_bottom1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3339, PATH_MESHES + "stat_vossk_bottom2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3341, PATH_MESHES + "stat_vossk_middle1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3342, PATH_MESHES + "stat_vossk_middle2.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3343, PATH_MESHES + "stat_vossk_top1.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(3337, PATH_MESHES + "void_station.m3g", 32000, 0);
        AEResourceManager.addGeometryResource(13001, PATH_MESHES + "ship_00_[13001].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13002, PATH_MESHES + "ship_01_[13002].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13003, PATH_MESHES + "ship_02_[13003].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13004, PATH_MESHES + "ship_03_[13004].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13005, PATH_MESHES + "ship_04_[13005].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13006, PATH_MESHES + "ship_05_[13006].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13007, PATH_MESHES + "ship_06_[13007].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13008, PATH_MESHES + "ship_07_[13008].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13009, PATH_MESHES + "ship_08_[13009].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13010, PATH_MESHES + "ship_09_[13010].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13011, PATH_MESHES + "ship_10_[13011].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13012, PATH_MESHES + "ship_11_[13012].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13013, PATH_MESHES + "ship_12_[13013].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13014, PATH_MESHES + "ship_13_[13014].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13015, PATH_MESHES + "ship_14_[13015].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13016, PATH_MESHES + "ship_15_[13016].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13017, PATH_MESHES + "ship_16_[13017].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13018, PATH_MESHES + "ship_17_[13018].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13019, PATH_MESHES + "ship_18_[13019].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13020, PATH_MESHES + "ship_19_[13020].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13021, PATH_MESHES + "ship_20_[13021].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13022, PATH_MESHES + "ship_21_[13022].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13023, PATH_MESHES + "ship_22_[13023].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13024, PATH_MESHES + "ship_23_[13024].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13025, PATH_MESHES + "ship_24_[13025].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13026, PATH_MESHES + "ship_25_[13026].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13027, PATH_MESHES + "ship_26_[13027].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13028, PATH_MESHES + "ship_27_[13028].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13029, PATH_MESHES + "ship_28_[13029].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13030, PATH_MESHES + "ship_29_[13030].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13031, PATH_MESHES + "ship_30_[13031].m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13061, PATH_MESHES + "ship_void_body.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13062, PATH_MESHES + "ship_hsoc_body.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13063, PATH_MESHES + "ship_phantom_body.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13064, PATH_MESHES + "boost_red.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13065, PATH_MESHES + "boost_violet.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13067, PATH_MESHES + "boost_cyan.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13068, PATH_MESHES + "boost_green.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13070, PATH_MESHES + "boost_orange.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13071, PATH_MESHES + "boost_white.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(13072, PATH_MESHES + "ship_vt_00.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13073, PATH_MESHES + "ship_vt_01.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13074, PATH_MESHES + "ship_vt_02.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13075, PATH_MESHES + "ship_vt_03.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13076, PATH_MESHES + "ship_vt_04.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13077, PATH_MESHES + "ship_vt_05.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13078, PATH_MESHES + "ship_tb_00.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13079, PATH_MESHES + "ship_tb_01.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13080, PATH_MESHES + "ship_tb_02.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13081, PATH_MESHES + "ship_tb_03.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13082, PATH_MESHES + "ship_tb_04.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13083, PATH_MESHES + "ship_tb_05.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13084, PATH_MESHES + "ship_tb_06.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13085, PATH_MESHES + "ship_tb_07.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13086, PATH_MESHES + "ship_tb_08.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13087, PATH_MESHES + "ship_tb_09.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13088, PATH_MESHES + "ship_tb_10.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13089, PATH_MESHES + "ship_tb_11.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13090, PATH_MESHES + "ship_tb_12.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13091, PATH_MESHES + "ship_tb_13.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13092, PATH_MESHES + "ship_tb_14.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13093, PATH_MESHES + "ship_tb_15.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13094, PATH_MESHES + "ship_tb_16.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13095, PATH_MESHES + "ship_tt_00.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13096, PATH_MESHES + "ship_tt_01.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13097, PATH_MESHES + "ship_tt_02.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13098, PATH_MESHES + "ship_tt_03.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(13999, PATH_MESHES + "arrow.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14007, PATH_MESHES + "h1s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14008, PATH_MESHES + "h1s2_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14009, PATH_MESHES + "h1s3_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14010, PATH_MESHES + "h1s4_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14011, PATH_MESHES + "h1s5_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14012, PATH_MESHES + "h1s6_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14013, PATH_MESHES + "h1s7_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14014, PATH_MESHES + "h2s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14015, PATH_MESHES + "h2s2_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14016, PATH_MESHES + "h2s3_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14017, PATH_MESHES + "h2s4_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14018, PATH_MESHES + "h2s5_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14019, PATH_MESHES + "h2s6_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14020, PATH_MESHES + "h3s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14021, PATH_MESHES + "h3s2_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14022, PATH_MESHES + "h3s3_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14023, PATH_MESHES + "h3s4_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14024, PATH_MESHES + "h3s5_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14025, PATH_MESHES + "b1s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14027, PATH_MESHES + "b2s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14029, PATH_MESHES + "b3s1_.m3g", 15000, 0);
        AEResourceManager.addGeometryResource(14223, PATH_MESHES + "fig_terran_m.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14224, PATH_MESHES + "fig_terran_f.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14225, PATH_MESHES + "fig_vossk.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14226, PATH_MESHES + "fig_nivelian.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14227, PATH_MESHES + "fig_multipod.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14228, PATH_MESHES + "fig_bobolan.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(14229, PATH_MESHES + "fig_grey.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(9992, PATH_MESHES + "explosion.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(9996, PATH_MESHES + "spacejunk.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6767, PATH_MESHES + "void_waste.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6768, PATH_MESHES + "planet_sun.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6769, PATH_MESHES + "asteroid.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6804, PATH_MESHES + "asteroid_alien.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6770, PATH_MESHES + "gun_0.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6771, PATH_MESHES + "gun_1.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6772, PATH_MESHES + "gun_2.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6773, PATH_MESHES + "gun_3.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6774, PATH_MESHES + "gun_4.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6775, PATH_MESHES + "gun_5.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6778, PATH_MESHES + "3d_planet.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6779, PATH_MESHES + "orbit.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6781, PATH_MESHES + "map_3d_sun.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6782, PATH_MESHES + "asteroid_explo.m3g", 2000, 0);
        AEResourceManager.addGeometryResource(6783, PATH_MESHES + "jump.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6784, PATH_MESHES + "gunshot_0.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6785, PATH_MESHES + "gunshot_1.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6786, PATH_MESHES + "gunshot_2.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6805, PATH_MESHES + "vortex.m3g", 32000, 1);
        AEResourceManager.addGeometryResource(6806, PATH_MESHES + "vortex_dust.m3g", 32000, 1);
        AEResourceManager.addGeometryResource(6754, PATH_MESHES + "laser0.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6755, PATH_MESHES + "laser1.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6756, PATH_MESHES + "laser2.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6760, PATH_MESHES + "laser3.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6761, PATH_MESHES + "laser4.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6762, PATH_MESHES + "laser5.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6763, PATH_MESHES + "laser6.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6764, PATH_MESHES + "laser7.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6765, PATH_MESHES + "laser8.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6788, PATH_MESHES + "blaster0.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6789, PATH_MESHES + "blaster1.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6790, PATH_MESHES + "blaster2.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6791, PATH_MESHES + "blaster3.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6792, PATH_MESHES + "blaster4.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6793, PATH_MESHES + "blaster5.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6794, PATH_MESHES + "blaster6.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6795, PATH_MESHES + "blaster7.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6796, PATH_MESHES + "blaster8.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6797, PATH_MESHES + "blaster9.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6798, PATH_MESHES + "cannon0.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6799, PATH_MESHES + "cannon1.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6800, PATH_MESHES + "cannon2.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6801, PATH_MESHES + "cannon3.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6802, PATH_MESHES + "cannon4.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(6803, PATH_MESHES + "cannon5.m3g", 2000, 1);
        AEResourceManager.addGeometryResource(14230, PATH_MESHES + "ship_0.m3g", 2000, 0);
    }

    public static void OnRelease() {
        ships = null;
        items = null;
        AEResourceManager.OnRelease();
    }

//    static {
//        // optimization artifact, legacy code?
//        //short[][] var10000 = new short[][]{{11, 22, 28, 5, 7}, {4, 30}, {3, 14}, {9, 16}};
//        //var10000 = new short[][]{{11, 22, 28, 5, 7}, {4, 30}, {3, 14}, {9, 16}};
//    }
}
