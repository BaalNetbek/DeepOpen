package AbyssEngine;

public final class Class_85e {
   private Class_23e var_a;
   private int var_51;
   private int var_66;
   private int var_a0;
   private int var_104;
   private int[] var_1a9;
   private boolean var_24b;
   private boolean var_2a8;
   private int var_2ca;

   public Class_85e(int var1, int var2, int var3, int var4) {
      this.var_51 = var1;
      this.var_66 = var2;
      this.var_a0 = var3;
      this.var_104 = var4;
      this.var_1a9 = new int[]{var4};
      this.var_24b = false;
      this.var_2a8 = false;
   }

   public Class_85e(int var1, int var2, int var3, int[] var4) {
      this.var_51 = 854;
      this.var_66 = 0;
      this.var_a0 = 9;
      this.var_104 = var4[0];
      this.var_1a9 = var4;
      this.var_24b = false;
   }

   public final int sub_50() {
      return this.var_51;
   }

   public final int sub_5e() {
      return this.var_66;
   }

   public final void sub_76(Class_23e var1) {
      this.var_a = var1;
   }

   public final boolean sub_8e() {
      return this.var_24b;
   }

   public final boolean sub_f2() {
      return this.var_2a8;
   }

   public final void sub_163() {
      this.var_2a8 = true;
   }

   public final boolean sub_18e(long var1, PlayerEgo var3) {
      if (this.var_24b) {
         return false;
      } else {
         boolean var4;
         label252: {
            var4 = false;
            boolean var10000;
            Player[] var2;
            int var6;
            int var7;
            Player[] var8;
            int var9;
            Player[] var10;
            switch(this.var_a0) {
            case 0:
               if (var3.sub_647() != null) {
                  var4 = var3.sub_647().sub_229() > this.var_2ca && this.var_2ca == this.var_104;
                  this.var_2ca = var3.sub_647().sub_229();
               }
               break label252;
            case 1:
               var10 = var3.player.getCoPlayers();
               var6 = 0;

               while(true) {
                  if (var6 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (var10[this.var_1a9[var6]].sub_ae3()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 2:
               var10 = var3.player.getCoPlayers();
               var6 = 0;

               while(true) {
                  if (var6 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (var10[this.var_1a9[var6]].var_427 && var10[this.var_1a9[var6]].sub_ae3()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 3:
               var4 = var3.var_887.sub_840() <= 0;
               break label252;
            case 4:
               var4 = var3.var_887.sub_85e() <= 0;
               break label252;
            case 5:
               var4 = var1 >= (long)this.var_104;
               break label252;
            case 6:
               var10000 = this.var_a.sub_133(this.var_104).var_24b;
               break;
            case 7:
            case 13:
            default:
               break label252;
            case 8:
               var8 = var3.player.getCoPlayers();
               var7 = 0;

               while(true) {
                  if (var7 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (!var8[var7].isAsteroid() && var8[this.var_1a9[var7]].sub_ace()) {
                     var4 = true;
                     break label252;
                  }

                  ++var7;
               }
            case 9:
               var4 = true;
               var2 = var3.player.getCoPlayers();
               var9 = 0;

               while(true) {
                  if (var9 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (!var2[this.var_1a9[var9]].sub_ae3()) {
                     var4 = false;
                     break label252;
                  }

                  ++var9;
               }
            case 10:
               var2 = var3.player.getCoPlayers();
               var6 = 0;

               while(true) {
                  if (var6 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (var2[this.var_1a9[var6]].var_427 && var2[this.var_1a9[var6]].sub_ace()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 11:
               var10000 = ((Class_876)null).sub_47((int)var1);
               break;
            case 12:
               var8 = var3.player.getCoPlayers();
               var9 = 0;

               while(true) {
                  if (var9 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (var8[this.var_1a9[var9]].getHitpoints() < var8[this.var_1a9[var9]].sub_66c() / 2) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 14:
               var10000 = ((PlayerFighter)var3.player.getCoPlayers()[this.var_104].getKIPlayer()).sub_5f();
               break;
            case 15:
               var4 = false;
               var2 = var3.player.getCoPlayers();
               var6 = 0;

               while(true) {
                  if (var6 >= var2.length) {
                     break label252;
                  }

                  if (var2[var6].sub_ae3() && !var2[var6].isAsteroid()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 16:
               var8 = var3.player.getCoPlayers();
               var7 = 0;

               while(true) {
                  if (var7 >= var8.length) {
                     break label252;
                  }

                  if (!var8[var7].isAsteroid() && var8[var7].sub_ace() && !var8[var7].isAlwaysFriend()) {
                     var4 = true;
                     break label252;
                  }

                  ++var7;
               }
            case 17:
               var2 = var3.player.getCoPlayers();
               var4 = true;
               var6 = 0;

               while(true) {
                  if (var6 >= var2.length) {
                     break label252;
                  }

                  if (var6 != this.var_104 && !var2[var6].isAsteroid() && !var2[var6].sub_ae3()) {
                     var4 = false;
                     break label252;
                  }

                  ++var6;
               }
            case 18:
               var4 = ((PlayerFighter)var3.player.getCoPlayers()[this.var_104].getKIPlayer()).sub_102() || ((PlayerFighter)var3.player.getCoPlayers()[this.var_104].getKIPlayer()).add();
               break label252;
            case 19:
               var8 = var3.player.getCoPlayers();
               var9 = 0;

               while(true) {
                  if (var9 >= this.var_1a9.length) {
                     break label252;
                  }

                  if (var8[this.var_1a9[var9]].getHitpoints() < var8[this.var_1a9[var9]].sub_66c() / 4 * 3) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 20:
               var4 = false;
               var6 = 0;
               var2 = var3.player.getCoPlayers();
               var9 = 0;

               while(true) {
                  if (var9 >= var2.length) {
                     break label252;
                  }

                  if (var2[var9].sub_ae3()) {
                     ++var6;
                  }

                  if (var6 >= this.var_104) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 21:
               if (!var3.player.getCoPlayers()[this.var_104].getKIPlayer().var_e74) {
                  break label252;
               }

               var10000 = true;
               break;
            case 22:
               var4 = var3.var_887.var_a19 >= this.var_104;
               break label252;
            case 23:
               MGameContext var5;
               var4 = (var5 = var3.var_15f4).targetedStation != null && var5.targetedStation instanceof PlayerStation;
               break label252;
            case 24:
               var10000 = !var3.player.getCoPlayers()[this.var_104].sub_ace() && !var3.player.getCoPlayers()[this.var_104].sub_ae3();
            }

            var4 = var10000;
         }

         this.var_24b = var4;
         if (this.var_24b) {
            this.var_a.sub_cf(this);
         }

         return var4;
      }
   }
}
