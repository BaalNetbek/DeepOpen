package AbyssEngine;

public final class Gun {
   private static final short[][][] positions = new short[][][]{{{0, 0, 0}}, {{270, 0, 100}, {-270, 0, 100}}, {{320, 0, 100}, {0, 0, 100}, {-320, 0, 100}}, {{370, 0, 100}, {170, 100, 150}, {-170, 100, 150}, {-370, 0, 100}}, {{350, 100, 200}, {270, -100, 150}, {0, 0, 100}, {-270, -100, 150}, {-350, 100, 200}}, {{500, 100, 150}, {500, -100, 150}, {200, 0, 100}, {-200, 0, 100}, {-500, -100, 150}, {-500, 100, 150}}, {{500, -100, 150}, {400, 100, 150}, {200, -50, 100}, {0, 0, 150}, {-200, -50, 100}, {-400, 100, 150}, {-500, -100, 150}}, {{550, -100, 100}, {550, 100, 100}, {300, 0, 100}, {100, 0, 150}, {-100, 0, 150}, {-300, 0, 100}, {-550, 100, 100}, {-550, -100, 100}}, {{550, -100, 100}, {550, 100, 100}, {300, 0, 100}, {100, 100, 150}, {0, -100, 100}, {-100, 100, 150}, {-300, 0, 100}, {-550, 100, 100}, {-550, -100, 100}}, {{600, -120, 100}, {600, 120, 100}, {400, 0, 100}, {200, 100, 150}, {200, -100, 100}, {-200, -100, 100}, {-200, 100, 150}, {-400, 0, 100}, {-600, 120, 100}, {-600, -120, 100}}};
   private Level level;
   private Player[] targets;
   public AEVector3D[] projectilesPos;
   AEVector3D[] projectilesDir;
   private AEVector3D var_176;
   private AEVector3D var_1b4;
   private Player var_20f;
   public int[] projectilesTimeLeft;
   private int var_2a2;
   private int damage;
   public int var_334;
   public float projectileSpeed;
   public int range;
   public AEVector3D var_3de;
   public boolean var_42d;
   private AEVector3D var_441;
   public int timeSinceLastShot;
   public int reloadTimeMilis;
   boolean inAir;
   private boolean var_547;
   public int index;
   public int subType;
   private int var_5b0;
   private Class_6fcMesh var_5bc;
   private boolean var_5de;
   private boolean var_627;
   private int var_633;

   public Gun(int var1, int var2, int var3, int var4, int var5, int var6, float var7, AEVector3D var8, AEVector3D var9) {
      this.damage = var2;
      this.projectileSpeed = var7;
      this.var_2a2 = var1;
      this.var_3de = var8;
      this.range = var5;
      this.reloadTimeMilis = var6;
      this.timeSinceLastShot = 0;
      if (var4 < 0) {
         var4 = Integer.MAX_VALUE;
      }

      this.var_334 = var4;
      this.projectilesTimeLeft = new int[var3];
      this.projectilesPos = new AEVector3D[var3];
      this.projectilesDir = new AEVector3D[var3];
      this.var_176 = new AEVector3D();
      this.var_1b4 = new AEVector3D();

      for(var1 = 0; var1 < var3; ++var1) {
         this.projectilesPos[var1] = new AEVector3D(50000, 0, 0);
         this.projectilesDir[var1] = new AEVector3D();
         this.projectilesTimeLeft[var1] = -1;
      }

      this.var_547 = true;
      this.var_42d = false;
      this.var_441 = new AEVector3D();
      this.inAir = false;
      this.var_5bc = null;
      this.targets = null;
      this.var_5de = false;
      this.var_627 = false;
      this.var_633 = 0;
   }

   public final void sub_64(int var1) {
      this.var_633 = 1000;
   }

   public final void sub_c1(boolean var1) {
      this.var_627 = true;
   }

   public final void sub_f4(int var1) {
      this.var_5b0 = var1;
   }

   public final int sub_141() {
      return this.var_5b0;
   }

   public final void sub_176(int var1, int var2) {
      short[] var3 = positions[var2 - 1][var1];
      this.var_441.set(this.var_3de);
      this.var_3de = new AEVector3D(var3[0] + this.var_441.x, var3[1] + this.var_441.y, var3[2] + this.var_441.z);
   }

   public final void sub_183() {
      this.level = null;
      this.targets = null;
      this.projectilesPos = null;
      this.projectilesDir = null;
      this.var_176 = null;
      this.var_1b4 = null;
      this.var_20f = null;
      this.projectilesTimeLeft = null;
      this.var_3de = null;
      this.var_5bc = null;
   }

   public final void sub_1df(Class_6fcMesh var1) {
      this.var_5bc = var1;
   }

   public final void sub_240(Level var1) {
      this.level = var1;
   }

   public final void setTargets(Player[] var1) {
      this.targets = var1;
   }

   public final Player[] sub_2d2() {
      return this.targets;
   }

   public final boolean sub_2ea(Matrix var1, long var2, boolean var4) {
      Matrix var10001 = var1;
      var1 = null;
      Matrix var10 = var10001;
      Gun var9 = this;
      this.timeSinceLastShot = 0;
      this.var_42d = true;
      this.inAir = true;
      Item var3 = null;
      if (this.var_627 || (var3 = Status.getShip().getEquipment()[this.var_2a2]) != null && var3.getAmount() != 0) {
         for(int var11 = 0; var11 < var9.projectilesPos.length; ++var11) {
            if (var9.projectilesTimeLeft[var11] <= 0) {
               var9.projectilesPos[var11].set(var10.getPosition(var9.var_441));
               if (var9.var_3de != null) {
                  var9.var_441.set(var9.var_3de);
                  var9.var_176 = var10.sub_a67(var9.var_441, var9.var_176);
                  var9.projectilesPos[var11].add(var9.var_176);
               }

               var9.var_441.set(var10.getDirection(var9.var_441));
               var9.projectilesDir[var11].set(var9.var_441);
               AEVector3D var10000;
               if (var9.var_633 > 0) {
                  var10000 = var9.projectilesDir[var11];
                  var10000.x += -(var9.var_633 >> 1) + GameStatus.random.nextInt(var9.var_633);
                  var10000 = var9.projectilesDir[var11];
                  var10000.y += -(var9.var_633 >> 1) + GameStatus.random.nextInt(var9.var_633);
                  var10000 = var9.projectilesDir[var11];
                  var10000.z += -(var9.var_633 >> 1) + GameStatus.random.nextInt(var9.var_633);
               }

               var9.projectilesDir[var11].normalize();
               var9.projectilesDir[var11].scale((int)(var9.projectileSpeed * (float)var2) << 12);
               var10000 = var9.projectilesDir[var11];
               var10000.x >>= 12;
               var10000 = var9.projectilesDir[var11];
               var10000.y >>= 12;
               var10000 = var9.projectilesDir[var11];
               var10000.z >>= 12;
               var9.projectilesTimeLeft[var11] = var9.range;
               if (!var9.var_627 && var9.var_334 > 0 && var3 != null && var3.getType() == 1) {
                  --var9.var_334;
                  var3.changeAmount(-1);
                  if (var9.var_334 == 0) {
                     Status.getShip().sub_9be(var3);
                  }
               }

               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public final void sub_30a() {
      if (this.subType == 7) {
         ++Status.bombsUsed;
      }

      if (this.targets != null) {
         for(int var1 = 0; var1 < this.targets.length; ++var1) {
            this.var_20f = this.targets[var1];
            if ((this.subType != 6 || !this.var_20f.isAsteroid()) && this.var_20f.sub_ace()) {
               for(int var2 = 0; var2 < this.projectilesPos.length; ++var2) {
                  this.var_176.set(this.projectilesPos[var2]);
                  this.var_441 = this.var_20f.var_46b.getPosition(this.var_441);
                  this.var_441.subtract(this.var_176);
                  int var3;
                  if ((var3 = this.var_441.getLength()) < this.var_5b0) {
                     float var6;
                     if ((var6 = (float)(this.var_5b0 - var3) / (float)this.var_5b0) > 1.0F) {
                        var6 = 1.0F;
                     }

                     int var4 = IndexManager.getItems()[this.index].getAttribute(10);
                     if (this.subType == 7) {
                        if (var4 != -979797979) {
                           this.var_20f.sub_84b((float)var4 * var6);
                        }

                        this.var_20f.setNukeStun(var6);
                        if (this.var_20f.isAsteroid()) {
                           var6 *= 0.6F;
                        }

                        this.var_20f.sub_96b((int)((float)this.damage * var6), this.var_627);
                     } else {
                        this.var_20f.damageEmp((int)((float)var4 * var6), this.var_627);
                     }

                     this.var_441.normalize();
                     this.var_20f.sub_7d0(this.var_441.x, this.var_441.y, this.var_441.z);
                     this.projectilesTimeLeft[var2] = -1;
                     this.var_441.set(this.projectilesDir[var2]);
                     this.var_441.normalize();
                  }
               }
            }
         }
      }

      boolean var5 = this.subType == 7;
      this.level.setupExplosionFlashColors(var5 ? 3 : 4);
      GameStatus.soundManager.playSfx(var5 ? 11 : 12);
      if (this.var_5bc != null) {
         this.var_5bc.sub_16(this.projectilesPos[0]);
      }

   }

   public final void sub_318(long var1) {
      this.timeSinceLastShot = (int)((long)this.timeSinceLastShot + var1);
      if (this.var_5bc != null) {
         this.var_5bc.sub_181(var1);
      }

      if (this.inAir) {
         Gun var3 = this;
         boolean var6 = false;
         if (this.targets != null) {
            boolean var7;
            boolean var8 = (var7 = this.subType == 7 || this.subType == 6) || this.subType == 4 || this.subType == 5;

            label96:
            for(int var9 = 0; var9 < var3.targets.length; ++var9) {
               var3.var_20f = var3.targets[var9];
               if (var3.var_20f.sub_ace()) {
                  for(int var10 = 0; var10 < var3.projectilesPos.length; ++var10) {
                     var3.var_176.set(var3.projectilesPos[var10]);
                     var3.var_1b4.set(var3.projectilesDir[var10]);
                     int var4;
                     int var5;
                     int var13;
                     if (var3.var_20f.invincible_) {
                        var4 = var3.var_20f.var_7ae - var3.var_176.x + var3.var_1b4.x;
                        var5 = var3.var_20f.var_803 - var3.var_176.y + var3.var_1b4.y;
                        var13 = var3.var_20f.var_83e - var3.var_176.z + var3.var_1b4.z;
                     } else {
                        var4 = var3.var_20f.var_46b.getPositionX() - var3.var_176.x + var3.var_1b4.x;
                        var5 = var3.var_20f.var_46b.getPositionY() - var3.var_176.y + var3.var_1b4.y;
                        var13 = var3.var_20f.var_46b.getPositionZ() - var3.var_176.z + var3.var_1b4.z;
                     }

                     int var11 = (int)var3.var_20f.radius;
                     if (var4 < var11 && var4 > -var11 && var5 < var11 && var5 > -var11 && var13 < var11 && var13 > -var11) {
                        if (var8 && var3.var_20f.isAsteroid()) {
                           var3.var_20f.sub_96b(9999, false);
                           break label96;
                        }

                        if (var7) {
                           var3.sub_30a();
                           break label96;
                        }

                        if ((var4 = IndexManager.getItems()[var3.index].getAttribute(10)) != -979797979) {
                           var3.var_20f.damageEmp(var4, var3.var_627);
                        }

                        var3.var_20f.sub_96b(var3.damage, var3.var_627);
                        var3.var_20f.sub_7d0(-var3.var_1b4.x, -var3.var_1b4.y, -var3.var_1b4.z);
                        var3.projectilesTimeLeft[var10] = -1;
                        var3.var_441.set(var3.projectilesDir[var10]);
                        var3.var_441.normalize();
                        if (var3.var_5bc != null) {
                           var3.var_5bc.sub_16(var3.projectilesPos[var10]);
                        }
                     }
                  }
               }
            }
         }

         for(int var12 = 0; var12 < this.projectilesPos.length; ++var12) {
            if (this.projectilesTimeLeft[var12] > 0) {
               int[] var10000 = this.projectilesTimeLeft;
               var10000[var12] -= (int)var1;
               this.projectilesPos[var12].add(this.projectilesDir[var12]);
               if (this.projectilesTimeLeft[var12] <= 0 && (this.subType == 7 || this.subType == 6)) {
                  this.sub_30a();
               }
            } else {
               this.projectilesPos[var12].set(50000, 50000, 50000);
               this.projectilesDir[var12].set(0, 0, 0);
            }
         }
      }

   }

   public final void sub_341() {
      if (this.var_5bc != null) {
         this.var_5bc.render();
      }

   }
}
