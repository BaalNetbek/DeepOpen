package GoF2;

public final class Standing {
   private int[] stand = new int[2];

   public Standing() {
      this.stand[0] = 30;
      this.stand[1] = 0;
   }

   public final void setStanding(int[] var1) {
      this.stand = var1;
   }

   public final int[] getStanding() {
      return this.stand;
   }

   public final int getStanding(int var1) {
      return this.stand[var1];
   }

   public final void rehabilitate(int var1) {
      if (var1 == 1) {
         this.stand[0] = 35;
      } else if (var1 == 0) {
         this.stand[0] = -35;
      } else if (var1 == 3) {
         this.stand[1] = 35;
      } else {
         if (var1 == 2) {
            this.stand[1] = -35;
         }

      }
   }

   public final boolean atWar() {
      int var1 = this.stand[0];
      int var2 = this.stand[1];
      return var1 > 60 || var1 < -60 || var2 > 60 || var2 < -60;
   }

   public final boolean isEnemy(int var1) {
      if (var1 == 1) {
         return this.stand[0] > 60;
      } else if (var1 == 0) {
         return this.stand[0] < -60;
      } else if (var1 == 3) {
         return this.stand[1] > 60;
      } else if (var1 == 2) {
         return this.stand[1] < -60;
      } else {
         return false;
      }
   }

   public final boolean isFriend(int var1) {
      if (var1 == 1) {
         return this.stand[0] < -60;
      } else if (var1 == 0) {
         return this.stand[0] > 60;
      } else if (var1 == 3) {
         return this.stand[1] < -60;
      } else if (var1 == 2) {
         return this.stand[1] > 60;
      } else {
         return false;
      }
   }

   public static int enemyRace(int var0) {
      switch(var0) {
      case 0:
         return 1;
      case 1:
         return 0;
      case 2:
         return 3;
      case 3:
         return 2;
      default:
         return 8;
      }
   }

   private void subtract(int var1, int var2) {
      int[] var10000 = this.stand;
      var10000[var1] += var2;
      if (this.stand[var1] > 100) {
         this.stand[var1] = 100;
      } else {
         if (this.stand[var1] < -100) {
            this.stand[var1] = -100;
         }

      }
   }

   public final void applyKill(int var1) {
      int var2 = Status.inAlienOrbit() ? 9 : Status.getSystem().getRace();
      int var3 = var1;
      byte var4 = 5;
      if (var1 == 8) {
         switch(var2) {
         case 0:
            var3 = 1;
            break;
         case 1:
            var3 = 0;
            break;
         case 2:
            var3 = 3;
            break;
         case 3:
            var3 = 2;
         }

         var4 = 1;
      }

      this.applyDelict(var3, var4);
   }

   public final void applyStealCargo(int var1) {
      this.applyDelict(var1, 2);
   }

   public final void increase(int var1) {
      this.applyDelict(var1, -7);
   }

   public final void applyDisable(int var1) {
      this.applyDelict(var1, 2);
   }

   private void applyDelict(int var1, int var2) {
      if (var1 == 1) {
         this.subtract(0, var2);
      } else if (var1 == 0) {
         this.subtract(0, -var2);
      } else if (var1 == 3) {
         this.subtract(1, var2);
      } else {
         if (var1 == 2) {
            this.subtract(1, -var2);
         }

      }
   }
}
