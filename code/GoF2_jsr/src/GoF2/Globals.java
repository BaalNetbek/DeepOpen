package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.GlobalStatus;
import AE.GraphNode;
import AE.Group;

public final class Globals {
   private static String pathMeshes = "/data/3d/";
   private static String pathTextures = "/data/textures/";
   public static final short[][] barMeshIDs = new short[][]{{14025}, {14027}, {14029}};
   public static final short[] barFiguresMeshIDs = new short[]{14223, 14225, 14226, 14223, 14227, 14223, 14228, 14229};
   public static final short[][] hangarMeshIDs = new short[][]{{14007, 14008, 14009, 14010, 14011, 14012, 14013}, {14014, 14015, 14016, 14017, 14018, 14019}, {14020, 14021, 14022, 14023, 14024}};
   public static final short[] projectilesMeshIDs = new short[]{6754, 6755, 6756, 6760, 6761, 6762, 6763, 6764, 6765, -1, -1, -1, 6788, 6789, 6790, 6791, 6792, 6793, 6794, 6795, 6796, 6797, 6798, 6799, 6800, 6801, 6802, 6803, 6781, 6781, 6781, 6791, 6789, 6788, 6790, 6792, 6797, 6794, 6791, 6796, 6788, -1, -1, -1, -1, -1, -1, 6784, 6785, 6786};
   public static final byte[] CHAR_KEITH;
   private static byte[] CHAR_BRENT;
   private static byte[] CHAR_GUNANT;
   private static byte[] CHAR_NORRIS;
   private static byte[] CHAR_MKKT_BKKT;
   private static byte[] CHAR_TOMMY;
   private static byte[] CHAR_CARLA;
   private static byte[] CHAR_ERRKT;
   private static byte[] CHAR_JEAN;
   private static byte[] CHAR_PIRATE_CHIEF;
   private static byte[] CHAR_PIRATE_1;
   private static byte[] CHAR_PIRATE_2;
   private static byte[] CHAR_SECURITY_GUY;
   private static byte[] CHAR_SECURITY_GIRL;
   private static byte[] CHAR_KIDNAPPER;
   private static byte[] CHAR_STORY;
   public static final byte[] CHAR_COMPUTER;
   private static byte[] CHAR_INFO_PIC;
   private static byte[] CHAR_TERRAN_OFFICER;
   private static byte[] CHAR_VOID;
   private static byte[] CHAR_KHADOR;
   private static byte[] CHAR_NIVELIAN_SECURITY;
   public static final byte[][] CHAR_IMAGES;
   private static Ship[] ships;
   private static Item[] items;

   private Globals() {
   }

   public static void OnInitialize() {
      new FileRead();
      ships = FileRead.loadShipsBinary();
      items = FileRead.loadItemsBinary();
   }

   public static Ship[] getShips() {
      return ships;
   }

   public static Item[] getItems() {
      return items;
   }

   public static String getItemName(int var0) {
      return GlobalStatus.gameText.getText(var0 + 569);
   }

   public static int[] getRaceUVkeyframeId_(int var0) {
      switch(var0) {
      case 2:
         return new int[]{2, 2};
      case 3:
         return new int[]{3, 3};
      case 8:
         return new int[]{0, 0};
      default:
         return new int[]{1, 1};
      }
   }

   public static String getRandomName(int var0, boolean isMale) {
      new FileRead();
      String[] firstNames = FileRead.loadNamesBinary(var0, isMale, true);
      String[] lastNames = FileRead.loadNamesBinary(var0, isMale, false);
      String firstName = firstNames == null ? "" : firstNames[GlobalStatus.random.nextInt(firstNames.length)];
      String lastName = lastNames == null ? "" : lastNames[GlobalStatus.random.nextInt(lastNames.length)];
      //if (lastName == ""/null) return firstName;
      return firstName + " " + lastName; 
   }

   public static String getRandomPlanetName() {
      return (new FileRead()).loadStation(GlobalStatus.random.nextInt(100)).getName();
   }

   public static int getRandomEnemyFighter(int race) {
	   int ship = 0;
	   if (race == 9) {
		   ship = 8;
	   } 
	   else if (race == 1) {
		   ship = 9;
	   } 
	   else {
		   do {
			   ship = GlobalStatus.random.nextInt(37);
		   } while (ship == 0 ||
			        ship == 9 ||
			        ship == 8 ||
			        ship == 10 ||
			        ship == 13 ||
			        ship == 14 ||
			        ship == 15);
	   }
	   return ship;
   }
   /**
    * 
    * @param var0 ship index
    * @param var1 race
    * @return ship's mesh group
    */
   public static Group getShipGroup(int var0, int var1) {
      Group var2 = new Group();
      new FileRead();
      int[] var3 = FileRead.loadShipParts(var0);
      int[] var6 = getRaceUVkeyframeId_(var1);

      for(int var4 = 0; var4 < var3.length; var4 += 10) {
         AbstractMesh var5;
         if ((var5 = AEResourceManager.getGeometryResource(var3[var4])).getID() >= 13064 && var5.getID() <= 13071) {
            var5.setAnimationMode((byte)2);
         } else {
            var5.setAnimationRangeInTime(var6[0], var6[1]);
            var5.disableAnimation();
         }

         if (var5.getID() == 13067 || var5.getID() == 13068 || var5.getID() == 13070 || var5.getID() == 13064 || var5.getID() == 13065 || var5.getID() == 13071 || var5.getID() == 13061 || var5.getID() == 13063 || var5.getID() == 13062 || var0 == 14 || var0 == 13 || var0 == 15) {
            var5.moveTo(var3[var4 + 1], var3[var4 + 2], var3[var4 + 3]);
            var5.setRotation(var3[var4 + 4], var3[var4 + 5], var3[var4 + 6]);
            var5.setScale(var3[var4 + 7], var3[var4 + 8], var3[var4 + 9]);
         }

         var5.setRenderLayer(2);
         var5.setDraw(true);
         var2.uniqueAppend_(var5);
      }

      return var2;
   }

   public static void buildShip(Group var0, int var1) {
      new FileRead();
      int[] var4;
      int var2 = (var4 = FileRead.loadShipParts(var1)).length - 10;

      for(GraphNode var3 = var0.getEndNode(); var3 != null; var3 = var3.getParent()) {
         var3.moveTo(var4[var2 + 1], var4[var2 + 2], var4[var2 + 3]);
         var3.setRotation(var4[var2 + 4], var4[var2 + 5], var4[var2 + 6]);
         var3.setScale(var4[var2 + 7], var4[var2 + 8], var4[var2 + 9]);
         var2 -= 10;
      }

   }

   public static void buildResourceList() {
      AEResourceManager.addTextureResource(0, pathTextures + "space");
      AEResourceManager.addTextureResource(1, pathTextures + "space");
      AEResourceManager.addSkyboxResource(9991, pathMeshes + "skybox.m3g", 1);
      AEResourceManager.addGeometryResource(18, pathMeshes + "nuke.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(16, pathMeshes + "emp.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(17, pathMeshes + "box.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(15, pathMeshes + "jumpgate.m3g", 5000, 0);
      AEResourceManager.addGeometryResource(3301, pathMeshes + "stat_arm0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3302, pathMeshes + "stat_arm1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3303, pathMeshes + "stat_bottom0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3304, pathMeshes + "stat_bottom1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3305, pathMeshes + "stat_bottom2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3306, pathMeshes + "stat_bottom3.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3307, pathMeshes + "stat_bottom4.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3308, pathMeshes + "stat_bottom5.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3309, pathMeshes + "stat_bridge0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3310, pathMeshes + "stat_bridge1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3311, pathMeshes + "stat_bridge2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3312, pathMeshes + "stat_connector0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3313, pathMeshes + "stat_hangar0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3314, pathMeshes + "stat_hangar1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3315, pathMeshes + "stat_hangar2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3316, pathMeshes + "stat_hangar3.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3317, pathMeshes + "stat_hangar4.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3318, pathMeshes + "stat_middle0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3319, pathMeshes + "stat_middle1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3320, pathMeshes + "stat_middle2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3321, pathMeshes + "stat_middle3.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3322, pathMeshes + "stat_top0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3323, pathMeshes + "stat_top1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3325, pathMeshes + "stat_top2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3326, pathMeshes + "stat_top3.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3327, pathMeshes + "stat_top4.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3328, pathMeshes + "stat_top5.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3329, pathMeshes + "stat_top6.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3330, pathMeshes + "stat_top7.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3331, pathMeshes + "stat_top8.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3332, pathMeshes + "stat_top9.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3324, pathMeshes + "stat_top10.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3333, pathMeshes + "stat_bridge3.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3334, pathMeshes + "stat_light0.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3335, pathMeshes + "stat_light1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3338, pathMeshes + "stat_vossk_arm1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3340, pathMeshes + "stat_vossk_bottom1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3339, pathMeshes + "stat_vossk_bottom2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3341, pathMeshes + "stat_vossk_middle1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3342, pathMeshes + "stat_vossk_middle2.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3343, pathMeshes + "stat_vossk_top1.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(3337, pathMeshes + "void_station.m3g", 32000, 0);
      AEResourceManager.addGeometryResource(13001, pathMeshes + "ship_00_[13001].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13002, pathMeshes + "ship_01_[13002].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13003, pathMeshes + "ship_02_[13003].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13004, pathMeshes + "ship_03_[13004].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13005, pathMeshes + "ship_04_[13005].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13006, pathMeshes + "ship_05_[13006].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13007, pathMeshes + "ship_06_[13007].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13008, pathMeshes + "ship_07_[13008].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13009, pathMeshes + "ship_08_[13009].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13010, pathMeshes + "ship_09_[13010].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13011, pathMeshes + "ship_10_[13011].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13012, pathMeshes + "ship_11_[13012].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13013, pathMeshes + "ship_12_[13013].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13014, pathMeshes + "ship_13_[13014].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13015, pathMeshes + "ship_14_[13015].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13016, pathMeshes + "ship_15_[13016].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13017, pathMeshes + "ship_16_[13017].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13018, pathMeshes + "ship_17_[13018].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13019, pathMeshes + "ship_18_[13019].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13020, pathMeshes + "ship_19_[13020].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13021, pathMeshes + "ship_20_[13021].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13022, pathMeshes + "ship_21_[13022].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13023, pathMeshes + "ship_22_[13023].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13024, pathMeshes + "ship_23_[13024].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13025, pathMeshes + "ship_24_[13025].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13026, pathMeshes + "ship_25_[13026].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13027, pathMeshes + "ship_26_[13027].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13028, pathMeshes + "ship_27_[13028].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13029, pathMeshes + "ship_28_[13029].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13030, pathMeshes + "ship_29_[13030].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13031, pathMeshes + "ship_30_[13031].m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13061, pathMeshes + "ship_void_body.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13062, pathMeshes + "ship_hsoc_body.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13063, pathMeshes + "ship_phantom_body.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13064, pathMeshes + "boost_red.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13065, pathMeshes + "boost_violet.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13067, pathMeshes + "boost_cyan.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13068, pathMeshes + "boost_green.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13070, pathMeshes + "boost_orange.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13071, pathMeshes + "boost_white.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(13072, pathMeshes + "ship_vt_00.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13073, pathMeshes + "ship_vt_01.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13074, pathMeshes + "ship_vt_02.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13075, pathMeshes + "ship_vt_03.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13076, pathMeshes + "ship_vt_04.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13077, pathMeshes + "ship_vt_05.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13078, pathMeshes + "ship_tb_00.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13079, pathMeshes + "ship_tb_01.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13080, pathMeshes + "ship_tb_02.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13081, pathMeshes + "ship_tb_03.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13082, pathMeshes + "ship_tb_04.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13083, pathMeshes + "ship_tb_05.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13084, pathMeshes + "ship_tb_06.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13085, pathMeshes + "ship_tb_07.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13086, pathMeshes + "ship_tb_08.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13087, pathMeshes + "ship_tb_09.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13088, pathMeshes + "ship_tb_10.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13089, pathMeshes + "ship_tb_11.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13090, pathMeshes + "ship_tb_12.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13091, pathMeshes + "ship_tb_13.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13092, pathMeshes + "ship_tb_14.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13093, pathMeshes + "ship_tb_15.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13094, pathMeshes + "ship_tb_16.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13095, pathMeshes + "ship_tt_00.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13096, pathMeshes + "ship_tt_01.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13097, pathMeshes + "ship_tt_02.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13098, pathMeshes + "ship_tt_03.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(13999, pathMeshes + "arrow.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14007, pathMeshes + "h1s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14008, pathMeshes + "h1s2_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14009, pathMeshes + "h1s3_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14010, pathMeshes + "h1s4_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14011, pathMeshes + "h1s5_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14012, pathMeshes + "h1s6_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14013, pathMeshes + "h1s7_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14014, pathMeshes + "h2s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14015, pathMeshes + "h2s2_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14016, pathMeshes + "h2s3_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14017, pathMeshes + "h2s4_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14018, pathMeshes + "h2s5_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14019, pathMeshes + "h2s6_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14020, pathMeshes + "h3s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14021, pathMeshes + "h3s2_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14022, pathMeshes + "h3s3_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14023, pathMeshes + "h3s4_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14024, pathMeshes + "h3s5_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14025, pathMeshes + "b1s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14027, pathMeshes + "b2s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14029, pathMeshes + "b3s1_.m3g", 15000, 0);
      AEResourceManager.addGeometryResource(14223, pathMeshes + "fig_terran_m.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14224, pathMeshes + "fig_terran_f.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14225, pathMeshes + "fig_vossk.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14226, pathMeshes + "fig_nivelian.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14227, pathMeshes + "fig_multipod.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14228, pathMeshes + "fig_bobolan.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(14229, pathMeshes + "fig_grey.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(9992, pathMeshes + "explosion.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(9996, pathMeshes + "spacejunk.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6767, pathMeshes + "void_waste.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6768, pathMeshes + "planet_sun.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6769, pathMeshes + "asteroid.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6804, pathMeshes + "asteroid_alien.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6770, pathMeshes + "gun_0.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6771, pathMeshes + "gun_1.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6772, pathMeshes + "gun_2.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6773, pathMeshes + "gun_3.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6774, pathMeshes + "gun_4.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6775, pathMeshes + "gun_5.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6778, pathMeshes + "3d_planet.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6779, pathMeshes + "orbit.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6781, pathMeshes + "map_3d_sun.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6782, pathMeshes + "asteroid_explo.m3g", 2000, 0);
      AEResourceManager.addGeometryResource(6783, pathMeshes + "jump.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6784, pathMeshes + "gunshot_0.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6785, pathMeshes + "gunshot_1.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6786, pathMeshes + "gunshot_2.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6805, pathMeshes + "vortex.m3g", 32000, 1);
      AEResourceManager.addGeometryResource(6806, pathMeshes + "vortex_dust.m3g", 32000, 1);
      AEResourceManager.addGeometryResource(6754, pathMeshes + "laser0.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6755, pathMeshes + "laser1.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6756, pathMeshes + "laser2.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6760, pathMeshes + "laser3.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6761, pathMeshes + "laser4.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6762, pathMeshes + "laser5.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6763, pathMeshes + "laser6.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6764, pathMeshes + "laser7.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6765, pathMeshes + "laser8.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6788, pathMeshes + "blaster0.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6789, pathMeshes + "blaster1.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6790, pathMeshes + "blaster2.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6791, pathMeshes + "blaster3.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6792, pathMeshes + "blaster4.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6793, pathMeshes + "blaster5.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6794, pathMeshes + "blaster6.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6795, pathMeshes + "blaster7.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6796, pathMeshes + "blaster8.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6797, pathMeshes + "blaster9.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6798, pathMeshes + "cannon0.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6799, pathMeshes + "cannon1.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6800, pathMeshes + "cannon2.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6801, pathMeshes + "cannon3.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6802, pathMeshes + "cannon4.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(6803, pathMeshes + "cannon5.m3g", 2000, 1);
      AEResourceManager.addGeometryResource(14230, pathMeshes + "ship_0.m3g", 2000, 0);
   }

   public static void OnRelease() {
      ships = null;
      items = null;
      AEResourceManager.OnRelease();
   }

   static {
      short[][] var10000 = new short[][]{{11, 22, 28, 5, 7}, {4, 30}, {3, 14}, {9, 16}};
      var10000 = new short[][]{{11, 22, 28, 5, 7}, {4, 30}, {3, 14}, {9, 16}};
      CHAR_KEITH = new byte[]{0, 7, 7, 7, 7};
      CHAR_BRENT = new byte[]{0, 2, 2, 2, 2};
      CHAR_GUNANT = new byte[]{2, 1, 1, 1, 1};
      CHAR_NORRIS = new byte[]{0, 3, 3, 3, 3};
      CHAR_MKKT_BKKT = new byte[]{4, 0, 0, 0, 0};
      CHAR_TOMMY = new byte[]{0, 4, 4, 4, 4};
      CHAR_CARLA = new byte[]{10, 0, 0, 0, 0};
      CHAR_ERRKT = new byte[]{1, 0, 0, 0, 0};
      CHAR_JEAN = new byte[]{0, 3, 2, 3, 1};
      CHAR_PIRATE_CHIEF = new byte[]{0, 1, 2, 3, 4};
      CHAR_PIRATE_1 = new byte[]{0, 2, 3, 3, 5};
      CHAR_PIRATE_2 = new byte[]{0, 3, 4, 3, 6};
      CHAR_SECURITY_GUY = new byte[]{0, 6, 0, 5, 1};
      CHAR_SECURITY_GIRL = new byte[]{10, 1, 2, 3, 4};
      CHAR_KIDNAPPER = new byte[]{2, 1, 2, 1, 2};
      CHAR_STORY = new byte[]{11, 1, 0, 0, 0};
      CHAR_COMPUTER = new byte[]{11, 1, 0, 0, 0};
      CHAR_INFO_PIC = new byte[]{11, 0, 0, 0, 0};
      CHAR_TERRAN_OFFICER = new byte[]{0, 2, 4, 4, 4};
      CHAR_VOID = new byte[]{9, 0, 0, 0, 0};
      CHAR_KHADOR = new byte[]{7, 0, 0, 0, 0};
      CHAR_NIVELIAN_SECURITY = new byte[]{2, 0, 0, 0, 0};
      CHAR_IMAGES = new byte[][]{CHAR_KEITH, CHAR_BRENT, CHAR_GUNANT, CHAR_NORRIS, CHAR_MKKT_BKKT, CHAR_TOMMY, CHAR_CARLA, CHAR_ERRKT, CHAR_JEAN, CHAR_PIRATE_CHIEF, CHAR_PIRATE_1, CHAR_PIRATE_2, CHAR_SECURITY_GUY, CHAR_SECURITY_GIRL, CHAR_KIDNAPPER, CHAR_STORY, CHAR_COMPUTER, CHAR_INFO_PIC, CHAR_TERRAN_OFFICER, CHAR_VOID, CHAR_KHADOR, CHAR_NIVELIAN_SECURITY};
   }
}
