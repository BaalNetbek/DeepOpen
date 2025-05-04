package GoF2;

import AE.GlobalStatus;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class Player {
   public Gun[][] guns;
   private Player[] coPlayers;
   float radius;
   private int empPoints;
   private int maxEmpPoints;
   private int hp;
   private int maxHp;
   private float shield;
   private int maxShield;
   private int armorPoints;
   private int maxArmorPoints;
   private int percentHP;
   private int percentShield;
   private int percentArmor;
   private int percentEMP;
   private int threeSecTick;
   private boolean active;
   private boolean vulnerable;
   public boolean enemy;
   public boolean friend;
   public boolean hidden;
   public Matrix transform;
   private AEVector3D hitVector = new AEVector3D();
   private KIPlayer kiPlayer;
   private boolean playShootSound;
   public boolean killedByKI;
   private float reamingNukeStun;
   private float empForce;
   private int egoInflictDamage;
   private int dealtEmpDamage;
   private boolean permanentEnemy;
   private int empImpactForce;
   private int elapsedEMPstun;
   private boolean stunnedEMP;
   private boolean tempEnemy_;
   private boolean permanentFriend;
   public boolean invincible_;
   public int posX;
   public int posY;
   public int posZ;

   public Player(float var1, int var2, int var3, int var4, int var5) {
      this.radius = var1;
      this.hp = var2;
      this.maxHp = var2;
      this.updateDamageRate();
      this.guns = new Gun[3][];
      if (var3 > 0) {
         this.guns[0] = new Gun[var3];
      }

      if (var4 > 0) {
         this.guns[1] = new Gun[var4];
      }

      if (var5 > 0) {
         this.guns[2] = new Gun[var5];
      }

      this.transform = new Matrix();
      this.active = true;
      this.vulnerable = true;
      this.kiPlayer = null;
      this.playShootSound = false;
      this.killedByKI = false;
      this.invincible_ = false;
   }

   public final boolean isAsteroid() {
      return this.kiPlayer != null ? this.kiPlayer.isAsteroid : false;
   }

   public final void setAlwaysEnemy(boolean var1) {
      this.tempEnemy_ = var1;
      this.enemy = true;
      this.friend = false;
      this.permanentEnemy = true;
   }

   public final void setAlwaysFriend(boolean var1) {
      this.permanentFriend = var1;
      this.enemy = false;
      this.friend = true;
      this.permanentEnemy = false;
   }

   public final boolean isAlwaysFriend() {
      return this.permanentFriend;
   }

   public final void setEmpData(int var1, int var2) {
      this.empPoints = var1;
      if (this.empPoints > this.maxEmpPoints) {
         this.maxEmpPoints = this.empPoints;
      }

      this.updateDamageRate();
      this.empImpactForce = var2;
   }

   public final void setPlayShootSound(boolean var1) {
      this.playShootSound = true;
   }

   public final void setRadius(int var1) {
      this.radius = (float)var1;
   }

   public final void setKIPlayer(KIPlayer var1) {
      this.kiPlayer = var1;
   }

   public final KIPlayer getKIPlayer() {
      return this.kiPlayer;
   }

   public final void reset() {
      this.hp = this.maxHp;
      this.shield = (float)this.maxShield;
      this.updateDamageRate();
      this.active = true;
      this.vulnerable = true;
      this.transform.identity();
      this.killedByKI = false;
      this.permanentEnemy = false;
      this.egoInflictDamage = 0;
      this.dealtEmpDamage = 0;
      this.hidden = false;
   }

   public final void setEnemies(Player[] var1) {
      this.coPlayers = var1;
      if (this.guns != null) {
         for(int var3 = 0; var3 < this.guns.length; ++var3) {
            if (this.guns[var3] != null) {
               for(int var2 = 0; var2 < this.guns[var3].length; ++var2) {
                  if (this.guns[var3][var2] != null) {
                     this.guns[var3][var2].setTargets(this.coPlayers);
                  }
               }
            }
         }
      }

   }

   public final void addEnemies(Player[] var1) {
      if (this.coPlayers == null) {
         this.setEnemies(var1);
      } else {
         Player[] var2 = new Player[this.coPlayers.length + var1.length];

         int var3;
         for(var3 = 0; var3 < this.coPlayers.length; ++var3) {
            var2[var3] = this.coPlayers[var3];
         }

         for(var3 = 0; var3 < var1.length; ++var3) {
            var2[this.coPlayers.length + var3] = var1[var3];
         }

         this.setEnemies(var2);
      }
   }

   public final Player[] getEnemies() {
      return this.coPlayers;
   }

   public final Player getEnemy(int var1) {
      return this.coPlayers[var1];
   }

   public final void setHitPoints(int var1) {
      this.hp = var1;
      if (this.hp > this.maxHp) {
         this.maxHp = this.hp;
      }

      this.updateDamageRate();
   }

   public final void setShieldHP(int var1) {
      this.shield = (float)var1;
      if (this.shield > (float)this.maxShield) {
         this.shield = (float)this.maxShield;
      }

      this.updateDamageRate();
   }

   public final void setArmorHP(int var1) {
      this.armorPoints = var1;
      if (this.armorPoints > this.maxArmorPoints) {
         this.armorPoints = this.maxArmorPoints;
      }

      this.updateDamageRate();
   }

   public final void setMaxHP(int var1) {
      this.maxHp = var1;
      this.hp = var1;
      this.updateDamageRate();
   }

   public final void setMaxShieldHP(int var1) {
      this.maxShield = var1;
      this.shield = (float)var1;
      this.updateDamageRate();
   }

   public final void setMaxArmorHP(int var1) {
      this.maxArmorPoints = var1;
      this.armorPoints = var1;
      this.updateDamageRate();
   }

   public final int getShieldHP() {
      return (int)this.shield;
   }

   public final int getCombinedHP() {
      return (int)this.shield + this.armorPoints + this.hp;
   }

   public final int getArmorHP() {
      return this.armorPoints;
   }

   public final int getMaxArmorHP() {
      return this.maxArmorPoints;
   }

   public final void regenerateShield(float var1) {
      if (this.shield + var1 < (float)this.maxShield) {
         this.shield += var1;
      } else {
         this.shield = (float)this.maxShield;
      }

      this.updateDamageRate();
   }

   public final void regenerateArmor() {
      if (this.armorPoints + 1 < this.maxArmorPoints) {
         ++this.armorPoints;
      } else {
         this.armorPoints = this.maxArmorPoints;
      }

      this.updateDamageRate();
   }

   public final void regenerateHull() {
      if (this.hp + 1 < this.maxHp) {
         ++this.hp;
      } else {
         this.hp = this.maxHp;
      }

      this.updateDamageRate();
   }

   public final int getHitpoints() {
      return this.hp;
   }

   public final int getMaxHitpoints() {
      return this.maxHp;
   }

   private void updateDamageRate() {
      this.percentHP = (int)((float)this.hp / (float)this.maxHp * 100.0F);
      this.percentShield = (int)(this.shield / (float)this.maxShield * 100.0F);
      this.percentArmor = (int)((float)this.armorPoints / (float)this.maxArmorPoints * 100.0F);
      this.percentEMP = (int)((float)this.empPoints / (float)this.maxEmpPoints * 100.0F);
   }

   public final int getDamageRate() {
      return this.percentHP;
   }

   public final int getEmpDamageRate() {
      return this.percentEMP;
   }

   public final int getShieldDamageRate() {
      return this.percentShield;
   }

   public final int getArmorDamageRate() {
      return this.percentArmor;
   }

   public final void setVulnerable_(boolean var1) {
      this.vulnerable = var1;
   }

   public final void setHitVector_(int var1, int var2, int var3) {
      this.hitVector.x = var1;
      this.hitVector.y = var2;
      this.hitVector.z = var3;
   }

   public final void setBombForce(float var1) {
      this.reamingNukeStun = var1;
   }

   public final void setEmpForce_(float var1) {
      this.empForce = var1;
   }

   public final float getBombForce() {
      return this.reamingNukeStun;
   }

   public final float setEmpForce_() {
      return this.empForce;
   }

   public final AEVector3D getHitVector() {
      return this.hitVector;
   }

   public final void damageEmp(int var1, boolean var2) {
      if (this.vulnerable && this.active && this.empPoints > 0 && this.hp > 0) {
         if (!var2 && this.kiPlayer != null && !this.tempEnemy_ && this.kiPlayer.race != 9 && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
            this.dealtEmpDamage += var1;
            if (this.dealtEmpDamage > this.maxEmpPoints / 3) {
               this.permanentEnemy = true;
               this.kiPlayer.level.friendTurnedEnemy(this.kiPlayer.race);
            }
         }

         this.empPoints -= var1;
         if (this.empPoints <= 0) {
            if (!var2 && this.kiPlayer != null) {
               if (!this.tempEnemy_ && this.kiPlayer.race != 9 && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
                  this.kiPlayer.level.alarmAllFriends(this.kiPlayer.race, false);
               }

               if (!this.kiPlayer.isAsteroid && !this.kiPlayer.junk) {
                  Status.getStanding().applyDisable(this.kiPlayer.race);
               }
            }

            float var3 = (float)this.empImpactForce;
            this.empForce = var3;
            this.stunnedEMP = true;
            this.empPoints = 0;
            this.elapsedEMPstun = 0;
         }

         this.updateDamageRate();
      }
   }

   public final void damageHP(int var1, boolean var2) {
      if (this.vulnerable && this.active && this.hp > 0) {
         if (!var2 && this.kiPlayer != null && !this.tempEnemy_ && this.kiPlayer.race != 9 && Status.getSystem() != null && this.kiPlayer.race == Status.getSystem().getRace()) {
            this.egoInflictDamage += var1;
            if (this.egoInflictDamage > this.maxHp / 3) {
               this.permanentEnemy = true;
               this.kiPlayer.level.friendTurnedEnemy(this.kiPlayer.race);
            }

            if (this.kiPlayer != null && this.egoInflictDamage > this.maxHp - this.maxHp / 3) {
               this.kiPlayer.level.alarmAllFriends(this.kiPlayer.race, true);
            }
         }

         if ((var1 = (int)this.shield - var1) < 0) {
            this.shield = 0.0F;
            if ((var1 = this.armorPoints - -var1) < 0) {
               this.armorPoints = 0;
               this.hp -= -var1;
            } else {
               this.armorPoints = var1;
            }
         } else {
            this.shield = (float)var1;
         }

         if (this.hp <= 0) {
            this.hp = 0;
            if (var2) {
               this.killedByKI = true;
            } else if (this.kiPlayer != null && !this.kiPlayer.isAsteroid && !this.kiPlayer.junk) {
               Status.getStanding().applyKill(this.kiPlayer.race);
            }
         }

         this.updateDamageRate();
      }
   }

   public final boolean isAlwaysEnemy() {
      return this.permanentEnemy;
   }

   public final void setAlwaysEnemy() {
      this.permanentEnemy = true;
   }

   public final boolean hasGunOfType(int var1) {
      if (var1 < 3 && var1 >= 0) {
         return this.guns[var1] != null;
      } else {
         return false;
      }
   }

   public final AEVector3D getPosition(AEVector3D var1) {
      return this.transform.getPosition(var1);
   }

   public final void setActive(boolean var1) {
      this.active = var1;
   }

   public final boolean isActive() {
      return this.active;
   }

   public final boolean isDead() {
      return this.hp <= 0;
   }

   public final void removeAllGuns() {
      this.guns = null;
   }

   public final void resetGunDelay(int var1) {
      if (this.guns != null && 0 < this.guns.length && this.guns[0] != null) {
         for(var1 = 0; var1 < this.guns[0].length; ++var1) {
            this.guns[0][var1].timeSinceLastShot = 0;
         }
      }

   }

   public final void playShootSound__(int var1, long var2, boolean var4, Matrix var5) {
      if (this.guns != null && var1 < this.guns.length && var1 >= 0 && this.guns[var1] != null) {
         for(int var6 = 0; var6 < this.guns[var1].length; ++var6) {
            if (this.guns[var1][var6].timeSinceLastShot > this.guns[var1][var6].reloadTimeMilis && this.guns[var1][var6].shoot(var5, var2, var4)) {
               this.guns[var1][var6].timeSinceLastShot = 0;
               if (this.playShootSound) {
                  switch(this.guns[var1][var6].subType) {
                  case 4:
                     GlobalStatus.soundManager.playSfx(8);
                     break;
                  case 5:
                     GlobalStatus.soundManager.playSfx(9);
                     break;
                  case 6:
                     GlobalStatus.soundManager.playSfx(10);
                     break;
                  case 7:
                     GlobalStatus.soundManager.playSfx(10);
                  }
               }
            }
         }
      }

   }

   public final void shoot_(int var1, long var2, boolean var4) {
      this.playShootSound__(var1, var2, false, this.transform);
   }

   public final boolean shoot(int var1, int var2, long var3, boolean var5) {
      Matrix var13 = this.transform;
      boolean var4 = false;
      long var9 = var3;
      int var12 = var2;
      var2 = var1;
      Player var11 = this;
      boolean var6 = true;
      if (this.guns != null && var1 < this.guns.length && var1 >= 0 && this.guns[var1] != null) {
         for(int var7 = 0; var7 < var11.guns[var2].length; ++var7) {
            Gun var8;
            if (((var8 = var11.guns[var2][var7]).subType == 7 || var8.subType == 6) && var8.projectilesTimeLeft[0] >= 0) {
               var8.ignite();
            } else if (var8.index == var12 && var8.timeSinceLastShot > var8.reloadTimeMilis) {
               if (var8.shoot(var13, var9, var4)) {
                  if (var11.playShootSound) {
                     switch(var11.guns[var2][var7].subType) {
                     case 4:
                        GlobalStatus.soundManager.playSfx(8);
                        break;
                     case 5:
                        GlobalStatus.soundManager.playSfx(9);
                        break;
                     case 6:
                        GlobalStatus.soundManager.playSfx(10);
                        break;
                     case 7:
                        GlobalStatus.soundManager.playSfx(10);
                     }
                  }

                  var8.timeSinceLastShot = 0;
                  break;
               }

               if (var8.ammo <= 0) {
                  var6 = false;
               }
            }
         }
      }

      return var6;
   }

   public final void update(long var1) {
      this.threeSecTick = (int)((long)this.threeSecTick + var1);
      if (this.threeSecTick > 3000) {
         this.threeSecTick = 0;
      }

      if (this.stunnedEMP) {
         this.elapsedEMPstun = (int)((long)this.elapsedEMPstun + var1);
         this.empPoints = (int)((float)this.elapsedEMPstun / (float)this.empImpactForce * (float)this.maxEmpPoints);
         if (this.empPoints > this.maxEmpPoints) {
            this.empPoints = this.maxEmpPoints;
            this.stunnedEMP = false;
            this.elapsedEMPstun = 0;
         }

         this.updateDamageRate();
      }

   }
}
