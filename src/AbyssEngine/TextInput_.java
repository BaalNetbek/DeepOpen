package AbyssEngine;

public final class TextInput_ {
   private static boolean var_12e;
   private static final char[][] var_1f1 = new char[][]{{'A', 'B', 'C', '2'}, {'D', 'E', 'F', '3'}, {'G', 'H', 'I', '4'}, {'J', 'K', 'L', '5'}, {'M', 'N', 'O', '6'}, {'P', 'Q', 'R', 'S', '7'}, {'T', 'U', 'V', '8'}, {'W', 'X', 'Y', 'Z', '9'}, {'0'}, {'1', '@', '.', '-', '_'}, {' '}};
   private static final char[][] var_23c;
   private int var_25b = 10;
   private int var_26d = 0;
   private int var_39a;
   private int var_3c6;
   private int var_414;
   private char var_461 = ' ';
   private String var_482 = "";
   private int var_4d7;
   private boolean var_4f8 = true;

   public TextInput_(int var1) {
   }

   private static int sub_24(int var0) {
      switch(var0) {
      case 2:
         return 0;
      case 4:
         return 2;
      case 32:
         return 4;
      case 64:
         return 6;
      case 256:
         return 3;
      case 512:
         return 5;
      case 1024:
         return 7;
      case 2048:
         return -2;
      case 4096:
         return 10;
      case 32768:
         return 9;
      case 65536:
         return 1;
      case 131072:
         return 8;
      default:
         return -1;
      }
   }

   public final void sub_53(int var1) {
      this.var_414 = 0;
      if (sub_24(var1) == -2) {
         var_12e = !var_12e;
      } else {
         this.var_4d7 = var1;
         if (var1 != 8192 && var1 != 524288) {
            if (var1 != this.var_26d && this.var_461 != ' ' && this.var_482.length() < this.var_25b - 1) {
               this.var_482 = this.var_482 + this.var_461;
               this.var_39a = 0;
            }

            this.var_3c6 = sub_24(var1);
            if (this.var_3c6 < 0) {
               return;
            }

            if (this.var_39a >= var_1f1[this.var_3c6].length) {
               this.var_39a = 0;
            }

            if (var_12e) {
               this.var_461 = var_23c[this.var_3c6][this.var_39a++];
            } else {
               this.var_461 = var_1f1[this.var_3c6][this.var_39a++];
            }

            if (!this.var_4f8 && this.var_461 == '@') {
               if (var_12e) {
                  this.var_461 = var_23c[this.var_3c6][this.var_39a++];
               } else {
                  this.var_461 = var_1f1[this.var_3c6][this.var_39a++];
               }
            }

            if (this.var_482.length() > 0 && this.var_482.charAt(this.var_482.length() - 1) != ' ') {
               this.var_461 = String.valueOf(this.var_461).charAt(0);
            }

            this.var_26d = var1;
         } else if (this.var_482.length() > 0) {
            this.var_414 = 0;
            this.var_461 = ' ';
            this.var_39a = 0;
            this.var_482 = this.var_482.substring(0, this.var_482.length() - 1);
            return;
         }

      }
   }

   public final int sub_ed(int var1) {
      this.var_414 += var1;
      if (this.var_4d7 == 16384) {
         return 0;
      } else if (this.var_482.length() >= this.var_25b - 1) {
         return 2;
      } else if ((this.var_414 > 1000 || this.var_461 == ' ') && this.var_4d7 != 0 && this.var_4d7 != 8192 && this.var_4d7 != 524288) {
         this.var_414 = 0;
         this.var_26d = 0;
         this.var_482 = this.var_482 + this.var_461;
         this.var_461 = ' ';
         this.var_4d7 = 0;
         this.var_39a = 0;
         return 0;
      } else {
         return 2;
      }
   }

   public final char sub_11b() {
      return this.var_461;
   }

   public final String sub_13f() {
      return this.var_482;
   }

   static {
      var_23c = new char[][]{{'a', 'b', 'c', '2'}, {'d', 'e', 'f', '3'}, {'g', 'h', 'i', '4'}, {'j', 'k', 'l', '5'}, {'m', 'n', 'o', '6'}, {'p', 'q', 'r', 's', '7'}, {'t', 'u', 'v', '8'}, {'w', 'x', 'y', 'z', '9'}, var_1f1[8], var_1f1[9], var_1f1[10]};
   }
}
