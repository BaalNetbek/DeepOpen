package AbyssEngine;

import java.io.DataInputStream;
import java.io.InputStream;

public final class LangManager {
   public static final short[] itemAtributes = new short[]{157, 157, 157, 37, 36, 36, 36, 36, 36, 50, 42, 53, 54, 57, 43, 59, 52, 53, 60, 44, 58, 56, 45, 58, 53, 55, 58, 45, 46, 47, 59, 48, 49, 58, 53, 69};
   public static final short[] helpTitles = new short[]{112, 296, 275, 79, 130, 218, 72, 146, 297, 63, 298};
   public static final short[] helpFull = new short[]{306, 307, 308, 309, 312, 314, 315, 320, 321, 323, 324};
   public static final short[] soundLevels = new short[]{8, 9, 10, 16};
   public static final short[] tips = new short[]{165, 166, 167, 168, 169, 169, 170, 171, 172, 173, 174, 175, 176, 177};
   public static final short[] egoToughts = new short[]{389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 389, 373, 374, 389, 375, 389, 376, 389, 377, 377, 389, 378, 379, 389, 389, 389, 380, 389, 389, 389, 381, 382, 383, 384, 385, 389, 386, 389, 387, 389, 389, 388, 389};
   public static final short[] freelanceWelcomings = new short[]{201, 202, 203, 204, 205};
   public static final short[] freelanceSuccess = new short[]{195, 196, 197, 198, 199};
   public static final short[] freelanceFail = new short[]{206, 207, 208, 209, 210};
   private static final int[] var_idk_ = new int[]{12, 5, 1, 2, 8, 5, 5, 5, 5, 6, 8, 17, 19, 9, 9, 4, 9, 12, 8, 12, 14, 9, 3, 14, 19, 9, 27, 5, 4, 13, 6, 17, 2, 9, 4, 7, 2, 10, 2, 4, 8, 6, 12, 12, 7, 9, 32};
   private static String[] langEntries = new String[1198];
   private static String[] unusedBuffer_ = new String[422];
   private static String[] ships = new String[22];
   private static String[] items = new String[162];
   private static String[] medals = new String[48];
   private static String[][] var_6ef = new String[47][];
   private static int language;
   private static String langPath;
   private static int var_7e7;
   private static int var_86b;
   private static int var_8b3;

   public final void setup(int var1) {
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
         this.loadLangFile(langEntries, "de");
      } else if (language == 6) {
         this.loadLangFile(langEntries, "ru");
      } else {
         this.loadLangFile(langEntries, "en");
      }

      init();
   }

   private void loadLangFile(String[] var1, String var2) {
      InputStream var3 = null;

      int var6;
      try {
         var3 = this.getClass().getResourceAsStream(langPath + var2 + ".lang");
         DataInputStream var5 = new DataInputStream(var3);

         for(var6 = 0; var6 < var1.length; ++var6) {
            var1[var6] = var5.readUTF();
         }

         var5.close();
      } catch (Exception var4) {
         for(var6 = 0; var6 < var1.length; ++var6) {
            var1[var6] = "<ERROR|LANG>";
         }

      }
   }

   public final String getLangString(int var1) {
      if (var1 < 1198) {
         return langEntries[var1];
      } else if (var1 < 422) {
         return unusedBuffer_[var1];
      } else if (var1 < 444) {
         var_7e7 = var1 - 422;
         if (ships[var_7e7] == null) {
            this.loadLangFile(ships, "ships");
         }

         return ships[var_7e7];
      } else if (var1 < 606) {
         var_7e7 = var1 - 444;
         if (items[var_7e7] == null) {
            this.loadLangFile(items, "items");
         }

         return items[var_7e7];
      } else if (var1 < 654) {
         var_7e7 = var1 - 606;
         if (medals[var_7e7] == null) {
            this.loadLangFile(medals, "medals");
         }

         return medals[var_7e7];
      } else {
         var_7e7 = var1 - 654;
         var_86b = 0;

         for(var1 = 0; var1 < var_idk_.length; ++var1) {
            var_86b += var_idk_[var1];
            if (var_7e7 < var_86b) {
               var_8b3 = var_7e7 - (var_86b - var_idk_[var1]);
               var_86b = var1;
               break;
            }
         }

         if (var_6ef[var_86b][var_8b3] == null) {
            this.loadLangFile(var_6ef[var_86b], "" + var_86b);
         }

         return var_6ef[var_86b][var_8b3];
      }
   }

   public static void init() {
      for(int var0 = 0; var0 < var_6ef.length; ++var0) {
         var_6ef[var0] = new String[var_idk_[var0]];
      }

      ships = new String[22];
      items = new String[162];
      medals = new String[48];
   }

   static {
      language = GameStatus.language;
   }
}
