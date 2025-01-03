package AbyssEngine;

public final class Class_876 {
   private int var_69;
   private int firstEnemyOfAllShips_;
   private int enemyCount_;
   private Level level;

   public Class_876(int var1, int var2, Level var3) {
      this.var_69 = var1;
      this.firstEnemyOfAllShips_ = var2;
      this.level = var3;
   }

   public Class_876(int var1, int var2, int var3, Level var4) {
      this(var1, var2, var4);
      this.enemyCount_ = var3;
   }

   public final boolean sub_47(int var1) {
      int var2;
      int var3;
      KIPlayer[] var4;
      KIPlayer[] var5;
      KIPlayer[] var6;
      switch(this.var_69) {
      case 0:
         if (this.level.sub_840() == 0) {
            return true;
         }

         return false;
      case 1:
         if (this.level.getShips()[this.firstEnemyOfAllShips_].isOutOfScope()) {
            return true;
         }

         return false;
      case 2:
         if (this.level.sub_6a6().sub_1d7().var_35b) {
            return true;
         }

         return false;
      case 3:
         if (var1 > this.firstEnemyOfAllShips_) {
            return true;
         }

         return false;
      case 4:
         if (this.level.sub_872()[this.firstEnemyOfAllShips_].sub_f2()) {
            return true;
         }

         return false;
      case 5:
         if (this.level.sub_85e() == 0) {
            return true;
         }

         return false;
      case 6:
         if (this.level.getShips()[this.firstEnemyOfAllShips_].isOutOfScope()) {
            return true;
         }

         return false;
      case 7:
         var6 = this.level.getShips();
         var1 = 0;

         for(var3 = 0; var3 < this.firstEnemyOfAllShips_; ++var3) {
            if (var6[var3].isOutOfScope()) {
               ++var1;
            }
         }

         if (var1 == this.firstEnemyOfAllShips_) {
            return true;
         }

         return false;
      case 8:
         var5 = this.level.sub_5cf();
         var1 = 0;

         for(var2 = 0; var2 < var5.length; ++var2) {
            if (var5[var2].isOutOfScope()) {
               ++var1;
            }
         }

         if (var1 > this.firstEnemyOfAllShips_) {
            return true;
         }

         return false;
      case 9:
         var6 = this.level.sub_5cf();

         for(var1 = 0; var1 < var6.length; ++var1) {
            if (var1 >= this.firstEnemyOfAllShips_) {
               if (0 >= this.firstEnemyOfAllShips_) {
                  return true;
               }

               return false;
            }
         }

         return false;
      case 10:
         var4 = this.level.sub_5cf();

         for(var2 = 0; var2 < var4.length; ++var2) {
            if (var2 >= this.firstEnemyOfAllShips_) {
               return false;
            }
         }

         return false;
      case 11:
         return ((PlayerFighter)this.level.getShips()[this.firstEnemyOfAllShips_]).sub_5f();
      case 12:
         return ((PlayerFighter)this.level.getShips()[this.firstEnemyOfAllShips_]).sub_bd();
      case 13:
         return false;
      case 14:
         if (this.level.sub_7f9() >= this.firstEnemyOfAllShips_) {
            return true;
         }

         return false;
      case 15:
         return this.level.getShips()[this.firstEnemyOfAllShips_].player.sub_ace();
      case 16:
         var6 = this.level.getShips();

         for(var1 = 0; var1 < var6.length; ++var1) {
            if (!((PlayerFighter)var6[var1]).sub_5f()) {
               return false;
            }
         }

         return true;
      case 17:
         var4 = this.level.getShips();

         for(var2 = 0; var2 < var4.length; ++var2) {
            if (((PlayerFighter)var4[var2]).sub_bd()) {
               return true;
            }
         }

         return false;
      case 18:
         var5 = this.level.getShips();
         var1 = 0;

         for(var2 = this.firstEnemyOfAllShips_; var2 < this.enemyCount_; ++var2) {
            if (var5[var2].isOutOfScope()) {
               ++var1;
            }
         }

         if (var1 == this.enemyCount_ - this.firstEnemyOfAllShips_) {
            return true;
         }

         return false;
      case 19:
         return this.level.sub_ade();
      case 20:
      case 21:
         var4 = this.level.getShips();
         var2 = 0;

         for(var3 = this.firstEnemyOfAllShips_; var3 < this.enemyCount_; ++var3) {
            if (var4[var3].isOutOfScope() && var4[var3].race == 8) {
               ++var2;
            }
         }

         if (this.var_69 == 20) {
            if (var2 == this.enemyCount_ - this.firstEnemyOfAllShips_ && this.level.egoScore > this.level.challengerScore) {
               return true;
            }

            return false;
         } else {
            if (var2 == this.enemyCount_ - this.firstEnemyOfAllShips_ && this.level.egoScore <= this.level.challengerScore) {
               return true;
            }

            return false;
         }
      case 22:
         if (this.level.sub_872()[this.level.sub_872().length - 1].sub_f2()) {
            return true;
         }

         return false;
      case 23:
         return this.level.getShips()[this.firstEnemyOfAllShips_].var_e74;
      case 24:
      default:
         return false;
      case 25:
         if (this.level.getShips()[this.firstEnemyOfAllShips_].var_145 == 0) {
            return true;
         } else {
            return false;
         }
      }
   }

   public final boolean sub_7d() {
      return this.var_69 == 3;
   }
}
