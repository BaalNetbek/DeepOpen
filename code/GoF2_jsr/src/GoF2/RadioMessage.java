package GoF2;

public final class RadioMessage {
   private Radio radio;
   private int textId;
   private int imageId;
   private int triggerCondition;
   private int triggerType;
   private int[] conditionsGroup;
   private boolean triggered;
   private boolean finished;
   private int lastWaypoint_;

   public RadioMessage(int var1, int var2, int var3, int var4) {
      this.textId = var1;
      this.imageId = var2;
      this.triggerCondition = var3;
      this.triggerType = var4;
      this.conditionsGroup = new int[]{var4};
      this.triggered = false;
      this.finished = false;
   }

   public RadioMessage(int var1, int var2, int var3, int[] var4) {
      this.textId = 854;
      this.imageId = 0;
      this.triggerCondition = 9;
      this.triggerType = var4[0];
      this.conditionsGroup = var4;
      this.triggered = false;
   }

   public final int getTextID() {
      return this.textId;
   }

   public final int getImageID() {
      return this.imageId;
   }

   public final void setRadio(Radio var1) {
      this.radio = var1;
   }

   public final boolean isTriggered() {
      return this.triggered;
   }

   public final boolean isOver() {
      return this.finished;
   }

   public final void finish() {
      this.finished = true;
   }

   public final boolean triggered(long var1, PlayerEgo var3) {
      if (this.triggered) {
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
            switch(this.triggerCondition) {
            case 0:
               if (var3.getRoute() != null) {
                  var4 = var3.getRoute().getCurrent() > this.lastWaypoint_ && this.lastWaypoint_ == this.triggerType;
                  this.lastWaypoint_ = var3.getRoute().getCurrent();
               }
               break label252;
            case 1:
               var10 = var3.player.getEnemies();
               var6 = 0;

               while(true) {
                  if (var6 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (var10[this.conditionsGroup[var6]].isDead()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 2:
               var10 = var3.player.getEnemies();
               var6 = 0;

               while(true) {
                  if (var6 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (var10[this.conditionsGroup[var6]].friend && var10[this.conditionsGroup[var6]].isDead()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 3:
               var4 = var3.level.getEnemiesLeft() <= 0;
               break label252;
            case 4:
               var4 = var3.level.getFriendsLeft() <= 0;
               break label252;
            case 5:
               var4 = var1 >= (long)this.triggerType;
               break label252;
            case 6:
               var10000 = this.radio.getMessageFromQueue(this.triggerType).triggered;
               break;
            case 7:
            case 13:
            default:
               break label252;
            case 8:
               var8 = var3.player.getEnemies();
               var7 = 0;

               while(true) {
                  if (var7 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (!var8[var7].isAsteroid() && var8[this.conditionsGroup[var7]].isActive()) {
                     var4 = true;
                     break label252;
                  }

                  ++var7;
               }
            case 9:
               var4 = true;
               var2 = var3.player.getEnemies();
               var9 = 0;

               while(true) {
                  if (var9 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (!var2[this.conditionsGroup[var9]].isDead()) {
                     var4 = false;
                     break label252;
                  }

                  ++var9;
               }
            case 10:
               var2 = var3.player.getEnemies();
               var6 = 0;

               while(true) {
                  if (var6 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (var2[this.conditionsGroup[var6]].friend && var2[this.conditionsGroup[var6]].isActive()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 11:
               var10000 = ((Objective)null).achieved((int)var1);
               break;
            case 12:
               var8 = var3.player.getEnemies();
               var9 = 0;

               while(true) {
                  if (var9 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (var8[this.conditionsGroup[var9]].getHitpoints() < var8[this.conditionsGroup[var9]].getMaxHitpoints() / 2) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 14:
               var10000 = ((PlayerFighter)var3.player.getEnemies()[this.triggerType].getKIPlayer()).lostMissionCrateToEgo();
               break;
            case 15:
               var4 = false;
               var2 = var3.player.getEnemies();
               var6 = 0;

               while(true) {
                  if (var6 >= var2.length) {
                     break label252;
                  }

                  if (var2[var6].isDead() && !var2[var6].isAsteroid()) {
                     var4 = true;
                     break label252;
                  }

                  ++var6;
               }
            case 16:
               var8 = var3.player.getEnemies();
               var7 = 0;

               while(true) {
                  if (var7 >= var8.length) {
                     break label252;
                  }

                  if (!var8[var7].isAsteroid() && var8[var7].isActive() && !var8[var7].isAlwaysFriend()) {
                     var4 = true;
                     break label252;
                  }

                  ++var7;
               }
            case 17:
               var2 = var3.player.getEnemies();
               var4 = true;
               var6 = 0;

               while(true) {
                  if (var6 >= var2.length) {
                     break label252;
                  }

                  if (var6 != this.triggerType && !var2[var6].isAsteroid() && !var2[var6].isDead()) {
                     var4 = false;
                     break label252;
                  }

                  ++var6;
               }
            case 18:
               var4 = ((PlayerFighter)var3.player.getEnemies()[this.triggerType].getKIPlayer()).hasCargo() || ((PlayerFighter)var3.player.getEnemies()[this.triggerType].getKIPlayer()).unk151_();
               break label252;
            case 19:
               var8 = var3.player.getEnemies();
               var9 = 0;

               while(true) {
                  if (var9 >= this.conditionsGroup.length) {
                     break label252;
                  }

                  if (var8[this.conditionsGroup[var9]].getHitpoints() < var8[this.conditionsGroup[var9]].getMaxHitpoints() / 4 * 3) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 20:
               var4 = false;
               var6 = 0;
               var2 = var3.player.getEnemies();
               var9 = 0;

               while(true) {
                  if (var9 >= var2.length) {
                     break label252;
                  }

                  if (var2[var9].isDead()) {
                     ++var6;
                  }

                  if (var6 >= this.triggerType) {
                     var4 = true;
                     break label252;
                  }

                  ++var9;
               }
            case 21:
               if (!var3.player.getEnemies()[this.triggerType].getKIPlayer().stunned) {
                  break label252;
               }

               var10000 = true;
               break;
            case 22:
               var4 = var3.level.capturedCargoCount >= this.triggerType;
               break label252;
            case 23:
               Radar var5;
               var4 = (var5 = var3.radar).targetedStation != null && var5.targetedStation instanceof PlayerStation;
               break label252;
            case 24:
               var10000 = !var3.player.getEnemies()[this.triggerType].isActive() && !var3.player.getEnemies()[this.triggerType].isDead();
            }

            var4 = var10000;
         }

         this.triggered = var4;
         if (this.triggered) {
            this.radio.setCurrentMessage(this);
         }

         return var4;
      }
   }
}
