package AbyssEngine;

public final class PlayerFighter extends KIPlayer {
   private int var_26 = 5000;
   private int var_4c = 50000;
   private int var_7a = 500;
   private float var_a7 = 2.0F;
   private float var_f1 = 2.0F;
   private int var_17d = 20;
   private AEVector3D pos = new AEVector3D();
   private AEVector3D initPos = new AEVector3D();
   private AEVector3D var_2aa = new AEVector3D();
   private AEVector3D var_311 = new AEVector3D();
   private static Matrix var_31e = new Matrix();
   private int var_33d;
   private boolean var_358;
   private int var_395;
   private int var_3b7;
   private int var_3cd;
   private int var_3e3;
   private Player var_3fa;
   private boolean var_410;
   private long var_44b;
   private int var_49f;
   private int var_4de;
   private boolean var_514;
   private static Class_661 var_561 = new Class_661(new int[]{20000, 0, 20000, 20000, 0, -20000, -20000, 0, -20000, -20000, 0, 20000});
   private static Class_661 var_595 = new Class_661(new int[]{40000, 0, 40000, 40000, 0, -40000, -40000, 0, -40000, -40000, 0, 40000});
   private Class_661 var_5b7;
   private int var_5ed;
   private int var_699;
   private Class_1271 var_6d7;
   private boolean var_739;
   private float speed;
   private float var_7a0;
   private AbstractBounding[] var_7b6;
   private boolean var_7e2;
   private boolean var_80a;
   private boolean var_821;
   private int var_87b;

   public PlayerFighter(int var1, int var2, Player var3, AbstractMesh var4, int var5, int var6, int var7) {
      super(var1, var2, var3, (AbstractMesh)null, var5, var6, var7);
      this.race = var2;
      this.initPos.set(var5, var6, var7);
      this.var_9d8 = true;
      this.var_6d7 = new Class_1271(var2 != 9 && var2 != 8 ? 0 : 1);
      this.var_6d7.sub_000(50);
      this.var_6d7.sub_10a(this.initPos);
      this.var_358 = true;
      this.speed = this.var_a7;
      this.var_7a0 = this.var_f1;
      var_561.sub_e1(true);
      var_595.sub_e1(true);
      this.var_50b = var2 == 9 ? var_595.sub_3ad() : var_561.sub_3ad();
      this.var_739 = false;
      if (var2 == 9) {
         this.var_a3b = null;
      } else {
         Generator var8 = new Generator();
         this.var_a3b = var8.getLootList();
      }

      this.var_4c = Status.inAlienOrbit() ? 100000 : '썐';
      this.var_80a = true;
      this.var_49f = this.player.getHitpoints();
      this.var_4de = 0;
      this.var_514 = false;
      this.var_d43 = 0;
      this.var_eb1 = true;
   }

   public final void sub_22() {
      super.destroy();
      if (this.var_6d7 != null) {
         this.var_6d7.sub_60();
      }

      this.var_6d7 = null;
      this.var_3fa = null;
      this.var_7b6 = null;
   }

   public final void sub_19(boolean var1) {
      this.var_907 = var1;
      if (var1) {
         this.var_a3b = null;
         this.var_a3b = new int[2];
         this.var_a3b[0] = Status.getMission().getType() == 5 ? 116 : 117;
         this.var_a3b[1] = 1;
      }

   }

   public final boolean sub_5f() {
      return this.var_be0;
   }

   public final boolean sub_bd() {
      return this.var_af6;
   }

   public final boolean sub_102() {
      return !this.var_9d8;
   }

   public final boolean add() {
      return false;
   }

   public final void setPosition(int var1, int var2, int var3) {
      this.targetX = var1;
      this.targetY = var2;
      this.targetZ = var3;
      this.var_379.moveTo(var1, var2, var3);
      this.initPos.set(var1, var2, var3);
      if (this.var_6d7 != null) {
         this.var_6d7.sub_10a(this.initPos);
      }

   }

   public final void sub_16f(int var1, KIPlayer var2) {
      super.sub_16f(var1, var2);
      switch(var1) {
      case 0:
         this.var_514 = false;
         this.var_358 = false;
         break;
      case 1:
         this.var_699 = this.var_699 == 0 ? 1 : 0;
         break;
      case 2:
         this.var_514 = false;
         this.var_358 = false;
         break;
      case 3:
      case 4:
         this.var_395 = 5001;
         if (this.speed != 3.0F) {
            this.var_3b7 = 5001;
         }

         if (var1 == 3 && this.level.sub_6a6() != null) {
            this.var_5b7 = this.level.sub_6a6().sub_38e();
            this.var_5ed = this.var_5b7.sub_229();
         }
      }

      this.speed = this.var_a7;
      this.var_7a0 = this.var_f1;
   }

   public final void loadShips(int var1) {
      this.var_358 = false;
      this.var_a7 = (float)var1;
      this.speed = this.var_a7;
   }

   public final void sub_1e8(int var1) {
      this.var_358 = false;
      this.var_f1 = (float)3;
      this.var_7a0 = this.var_f1;
   }

   public final void sub_227(int var1) {
      this.var_17d = 0;
   }

   public final void sub_262() {
      this.var_6d7 = null;
   }

   public final void sub_2b8(boolean var1) {
      for(GraphNode var2 = this.var_379.sub_97(); var2 != null; var2 = var2.sub_007()) {
         if (var2.sub_a() == 13067 || var2.sub_a() == 13068 || var2.sub_a() == 13070 || var2.sub_a() == 13064 || var2.sub_a() == 13065 || var2.sub_a() == 13071) {
            var2.setDraw(var1);
         }
      }

   }

   public final void update(long var1) {
      if (this.state == 4 && !this.var_9d8) {
         this.sub_34(false);
      } else {
         this.var_3b7 = (int)((long)this.var_3b7 + var1);
         this.var_395 = (int)((long)this.var_395 + var1);
         if (this.race == 1) {
            this.race = 1;
         }

         this.var_44b = var1;
         this.pos = this.var_379.sub_216(this.pos);
         this.player.var_3df = this.race != 8 && this.race != 9 ? (this.sub_32a() ? false : Status.getStanding().isEnemy(this.race)) : true;
         this.player.var_427 = this.race != 8 && this.race != 9 ? (this.sub_32a() ? true : Status.getStanding().isFriend(this.race)) : false;
         if (this.player.sub_992()) {
            this.player.var_3df = true;
            this.player.var_427 = false;
         }

         if (this.player.isAlwaysFriend()) {
            this.player.var_427 = true;
            this.player.var_3df = false;
         }

         if (!this.var_cc1 && this.var_50b == null) {
            this.var_50b = var_561.sub_3ad();
         }

         this.var_3e3 = (int)((long)this.var_3e3 + var1);
         if (this.var_3e3 > 200) {
            if (this.var_6d7 != null) {
               this.var_6d7.sub_a6(this.pos);
            }

            this.var_3e3 = 0;
         }

         this.player.var_46b = this.var_379.sub_85f();
         if (this.var_cc1 && this.state != 4 && this.var_50b != null) {
            this.var_727 = this.level.getPlayerEgo().var_50e.sub_216(this.var_727);
            if (this.var_d43 == 2) {
               AEVector3D var10000;
               switch(this.var_cf1) {
               case 0:
                  this.positon = this.level.getPlayerEgo().var_50e.sub_548(this.positon);
                  var10000 = this.positon;
                  var10000.x >>= 1;
                  var10000 = this.positon;
                  var10000.y >>= 1;
                  var10000 = this.positon;
                  var10000.z >>= 1;
                  this.var_727.subtract(this.positon);
                  this.var_727.add(this.level.getPlayerEgo().var_50e.sub_36a(this.positon));
                  break;
               case 1:
                  this.positon = this.level.getPlayerEgo().var_50e.sub_548(this.positon);
                  var10000 = this.positon;
                  var10000.x >>= 1;
                  var10000 = this.positon;
                  var10000.y >>= 1;
                  var10000 = this.positon;
                  var10000.z >>= 1;
                  this.var_727.add(this.positon);
                  this.var_727.add(this.level.getPlayerEgo().var_50e.sub_36a(this.positon));
                  break;
               case 2:
                  this.positon = this.level.getPlayerEgo().var_50e.sub_36a(this.positon);
                  this.var_727.subtract(this.positon);
               }
            }

            if (this.var_50b.sub_2c6() > 1) {
               this.var_50b = new Class_661(new int[]{this.var_727.x, this.var_727.y, this.var_727.z});
            } else {
               this.var_50b.sub_9f(this.var_727);
            }

            this.var_50b.sub_e1(true);
         }

         int var4;
         int var5;
         if (this.state != 4 && this.state != 3) {
            this.player.sub_c78(var1);
            Player[] var3;
            if ((var3 = this.player.getCoPlayers()) != null) {
               if (!this.var_7e2) {
                  this.var_33d = -1;
               } else if (this.var_33d >= 0 && !var3[this.var_33d].sub_ace()) {
                  this.var_7e2 = false;
               }

               this.var_3fa = null;
               if (this.var_395 > 5000) {
                  PlayerFighter var12;
                  boolean var10001;
                  label655: {
                     if (this.var_739) {
                        var12 = this;
                     } else {
                        var12 = this;
                        if (GameStatus.random.nextInt(100) < 20) {
                           var10001 = true;
                           break label655;
                        }
                     }

                     var10001 = false;
                  }

                  label650: {
                     var12.var_739 = var10001;
                     this.var_395 = 0;
                     if (GameStatus.random.nextInt(100) < 30 && var3.length > 1) {
                        this.var_7e2 = false;

                        for(var4 = 0; var4 < 5 && !this.var_7e2; ++var4) {
                           this.var_33d = GameStatus.random.nextInt(var3.length);
                           if (var3[this.var_33d].sub_ace()) {
                              this.var_727 = var3[this.var_33d].sub_a3c(this.var_727);
                              if (this.race != 8 && this.player.sub_992() || this.pos.x - this.var_727.x < this.var_4c && this.pos.x - this.var_727.x > -this.var_4c && this.pos.y - this.var_727.y < this.var_4c && this.pos.y - this.var_727.y > -this.var_4c && this.pos.z - this.var_727.z < this.var_4c && this.pos.z - this.var_727.z > -this.var_4c) {
                                 this.var_7e2 = true;
                                 break;
                              }
                           }
                        }

                        if (this.var_7e2) {
                           break label650;
                        }
                     }

                     this.var_33d = 0;
                  }

                  if (this.var_cc1 && this.var_d43 == 4 && this.var_d6c != null) {
                     for(var4 = 0; var4 < var3.length; ++var4) {
                        if (var3[var4].equals(this.var_d6c.player) && var3[var4].sub_ace() && !var3[var4].isAlwaysFriend()) {
                           this.var_33d = var4;
                        }
                     }
                  }

                  if (var3[this.var_33d].sub_ace() && !var3[this.var_33d].sub_ae3()) {
                     this.var_727 = var3[this.var_33d].sub_a3c(this.var_727);
                     if (this.pos.x - this.var_727.x >= this.var_4c || this.pos.x - this.var_727.x <= -this.var_4c || this.pos.y - this.var_727.y >= this.var_4c || this.pos.y - this.var_727.y <= -this.var_4c || this.pos.z - this.var_727.z >= this.var_4c || this.pos.z - this.var_727.z <= -this.var_4c) {
                        this.var_33d = -1;
                     }
                  } else {
                     this.var_33d = -1;
                  }
               } else if (!this.var_7e2) {
                  for(var4 = 0; var4 < var3.length; ++var4) {
                     if (var3[var4] != null && var3[var4].sub_ace() && !var3[var4].sub_ae3()) {
                        this.var_727 = var3[var4].sub_a3c(this.var_727);
                        if (this.race != 8 && this.player.sub_992() || this.pos.x - this.var_727.x < this.var_4c && this.pos.x - this.var_727.x > -this.var_4c && this.pos.y - this.var_727.y < this.var_4c && this.pos.y - this.var_727.y > -this.var_4c && this.pos.z - this.var_727.z < this.var_4c && this.pos.z - this.var_727.z > -this.var_4c) {
                           this.var_33d = var4;
                           this.var_7e2 = true;
                           break;
                        }
                     }
                  }
               }

               if (!this.player.var_3df && this.var_33d == 0) {
                  this.var_33d = 1;
                  this.var_7e2 = false;
               }

               if (!this.var_cc1 && this.var_33d > 0) {
                  this.var_33d = -1;

                  for(var4 = 1; var4 < var3.length; ++var4) {
                     if (var3[var4].sub_ace() && !var3[var4].sub_ae3() && var3[var4].getKIPlayer() != null) {
                        var5 = var3[var4].getKIPlayer().race;
                        if (this.sub_32a() ? var3[var4].var_3df : this.race != 8 && var5 == 8 || this.race == 8 && var5 != 8 || this.race != 9 && var5 == 9 || this.race == 9 && var5 != 9 || this.race == 1 && var5 == 0 || this.race == 0 && var5 == 1 || this.race == 2 && var5 == 3 || this.race == 3 && var5 == 2) {
                           this.var_33d = var4;
                           this.var_7e2 = true;
                           break;
                        }
                     }
                  }
               }

               if (this.var_cc1 && !this.var_7e2 && this.var_d43 == 2) {
                  this.var_33d = -1;
               }

               this.var_410 = false;
               if (this.var_33d == -1 && this.var_50b != null) {
                  if (this.var_50b.sub_176() != null && this.player.sub_ace()) {
                     this.var_50b.sub_31e(this.player.var_46b.getPositionX(), this.player.var_46b.getPositionY(), this.player.var_46b.getPositionZ());
                     if (this.var_50b.sub_176() != null) {
                        this.var_2aa.x = this.var_50b.sub_176().var_c7;
                        this.var_2aa.y = this.var_50b.sub_176().var_282;
                        this.var_2aa.z = this.var_50b.sub_176().var_2f9;
                        this.var_410 = true;
                     }
                  } else {
                     this.var_33d = 0;
                     this.var_3fa = this.player.sub_2f3(this.var_33d);
                     if (this.var_3fa != null) {
                        this.var_727 = this.var_3fa.sub_a3c(this.var_727);
                        this.var_2aa.x = this.var_727.x;
                        this.var_2aa.y = this.var_727.y;
                        this.var_2aa.z = this.var_727.z;
                     }
                  }
               } else {
                  if (this.var_33d < 0) {
                     this.var_33d = 0;
                  }

                  this.var_3fa = this.player.sub_2f3(this.var_33d);
                  this.var_727 = this.var_3fa.sub_a3c(this.var_727);
                  this.var_2aa.x = this.var_727.x;
                  this.var_2aa.y = this.var_727.y;
                  this.var_2aa.z = this.var_727.z;
               }
            } else if (this.var_50b != null) {
               this.var_50b.sub_31e(this.player.var_46b.getPositionX(), this.player.var_46b.getPositionY(), this.player.var_46b.getPositionZ());
               if (this.var_50b.sub_176() != null) {
                  this.var_2aa.x = this.var_50b.sub_176().var_c7;
                  this.var_2aa.y = this.var_50b.sub_176().var_282;
                  this.var_2aa.z = this.var_50b.sub_176().var_2f9;
                  this.var_410 = true;
               }
            } else {
               this.state = 5;
            }

            if (this.var_d9e && this.var_410) {
               this.var_dbe = (int)((long)this.var_dbe + var1);
               if (this.var_dbe >= 20000) {
                  this.var_dbe = 0;
                  this.state = 6;
                  this.var_410 = false;
               }
            } else {
               this.var_dbe = 0;
            }

            if (this.var_cc1 && this.var_d43 == 3 && this.var_5b7 != null) {
               this.var_5b7.sub_31e(this.player.var_46b.getPositionX(), this.player.var_46b.getPositionY(), this.player.var_46b.getPositionZ());
               if (this.var_5b7.sub_229() > this.var_5ed) {
                  this.var_5b7 = null;
                  this.sub_16f(0, (KIPlayer)null);
               } else if (this.var_5b7.sub_176() != null) {
                  this.var_2aa.x = this.var_5b7.sub_176().var_c7;
                  this.var_2aa.y = this.var_5b7.sub_176().var_282;
                  this.var_2aa.z = this.var_5b7.sub_176().var_2f9;
                  this.var_410 = true;
               }
            }
         }

         this.var_311.x = this.var_2aa.x - this.pos.x;
         this.var_311.y = this.var_2aa.y - this.pos.y;
         this.var_311.z = this.var_2aa.z - this.pos.z;
         if (this.player.var_3df && (this.state == 5 || this.var_cc1 && this.var_d43 == 0)) {
            this.var_727 = this.level.getPlayerEgo().player.sub_a3c(this.var_727);
            this.var_311.x = this.var_727.x - this.pos.x;
            this.var_311.y = this.var_727.y - this.pos.y;
            this.var_311.z = this.var_727.z - this.pos.z;
            if (this.var_311.x < 25000 && this.var_311.x > -25000 && this.var_311.y < 25000 && this.var_311.y > -25000 && this.var_311.z < 25000 && this.var_311.z > -25000) {
               this.state = 1;
               this.player.sub_a98(true);
               if (this.var_cc1 && this.var_d43 == 0) {
                  this.var_d43 = 2;
               }
            }
         }

         int var6;
         int var7;
         float var9;
         if ((var7 = this.player.getHitpoints()) <= 0 && this.state != 3 && this.state != 4) {
            if (this.player.var_3df) {
               if (this.race == 8) {
                  if (!this.player.var_54e) {
                     Status.incPirateKills();
                  }
               } else if (this.race == 9) {
                  this.var_a3b = new int[2];
                  this.var_a3b[0] = 131;
                  this.var_a3b[1] = 1 + GameStatus.random.nextInt(3);
               }

               this.level.sub_753(this.player.var_54e);
            } else {
               this.level.sub_7c2();
               if (this.var_cc1) {
                  Level.sub_7cd(this.var_cf1);
               }
            }

            this.state = 3;
            this.var_87b = 0;
            if (this.explosion != null) {
               this.explosion.sub_141(this.pos.x, this.pos.y, this.pos.z);
            }

            var4 = AEMath.min(40000, this.var_311.getLength());
            var9 = 1.0F - (float)var4 / 40000.0F;
            var6 = GameStatus.soundManager.getMusicVolume();
            GameStatus.soundManager.playSfx(1, (int)((float)var6 * var9));
            this.var_9d8 = this.sub_6ef();
            if (this.var_9d8) {
               this.setWasteMesh(this.race == 9 ? 3 : 0);
            }

            this.var_821 = false;
         }

         switch(this.state) {
         case 0:
            this.state = 1;
            break;
         case 1:
            if (this.var_358) {
               if (this.var_49f > var7) {
                  this.var_4de += this.var_49f - var7;
                  this.var_49f = var7;
                  if ((float)this.var_4de / (float)this.player.sub_66c() * 100.0F > 40.0F) {
                     this.var_4de = 0;
                     this.var_3b7 = 10000;
                     this.var_514 = true;
                  }
               }

               if (this.var_3b7 > 5000 && this.speed != 3.0F) {
                  if (this.var_514 || GameStatus.random.nextInt(100) < this.var_17d) {
                     this.var_3cd = GameStatus.random.nextInt(3000) + 5000;
                  }

                  this.var_3b7 = 0;
                  this.speed = 3.0F;
                  this.var_7a0 = 1.3F;
               }

               if (this.speed > this.var_a7 && this.var_3b7 > this.var_3cd) {
                  this.var_3b7 = 0;
                  this.var_514 = false;
                  this.speed = this.var_a7;
                  this.var_7a0 = this.var_f1;
               }
            }

            if (this.var_3fa != null && !this.var_410 && this.var_311.x < 8000 && this.var_311.x > -8000 && this.var_311.y < 8000 && this.var_311.y > -8000 && this.var_311.z < 8000 && this.var_311.z > -8000) {
               this.var_311 = this.var_379.sub_548(this.var_311);
            }

            this.var_311.normalize();
            this.positon.set(this.var_311);
            this.var_311 = this.var_379.sub_8f0(var_31e).sub_ac3(this.positon, this.var_311);
            this.var_311.y = -this.var_311.y;
            var9 = this.player.sub_892();
            float var10 = this.player.sub_8c2();
            if (this.var_7e2 && !this.var_410 && this.var_3fa != null) {
               if (!this.var_3fa.hidden) {
                  if (this.var_311.x < this.var_7a && this.var_311.x > -this.var_7a && this.var_311.y < this.var_7a && this.var_311.y > -this.var_7a && this.var_2aa.x - this.pos.x < 35000 && this.var_2aa.x - this.pos.x > -35000 && this.var_2aa.y - this.pos.y < 35000 && this.var_2aa.y - this.pos.y > -35000 && this.var_2aa.z - this.pos.z < 35000 && this.var_2aa.z - this.pos.z > -35000) {
                     if (this.var_3fa.sub_ace() && !this.var_3fa.sub_ae3() && var9 < 0.05F && var10 < 0.05F) {
                        this.player.sub_bc8(this.var_699, var1, false);
                     } else {
                        this.var_7e2 = false;
                     }
                  }
               } else {
                  this.var_7e2 = false;
               }
            }

            if (!this.var_739) {
               this.var_727.set(this.var_379.sub_36a(this.var_727));
               this.positon.subtract(this.var_727);
               this.positon.normalize();
               this.positon.scale((int)((float)var1 * this.var_7a0));
               this.var_311 = this.var_727.add(this.positon, this.var_311);
               this.var_311.normalize();
               this.var_379.sub_85f().setOrientation(this.var_311);
            }

            if (!this.var_cc1 || this.var_d43 != 0) {
               if (var10 < 0.05F && var9 < 0.05F) {
                  this.var_379.sub_202((int)((float)var1 * this.speed));
               } else {
                  if (var9 > 0.0F) {
                     this.var_727.set(this.player.sub_8db());
                     this.var_727.scale((int)(512.0F * var9));
                     this.var_379.sub_19c(this.var_727);
                     var7 = (int)var1;
                     this.var_379.sub_5c5(var7, var7, var7);
                     if ((var9 *= 0.98F) < 0.05F) {
                        var9 = 0.0F;
                     }

                     this.player.setNukeStun(var9);
                  }

                  if (var10 > 0.0F) {
                     this.var_e74 = true;
                     var7 = (int)var1 >> 1;
                     this.var_379.sub_5c5(var7, var7, var7);
                     if ((var10 -= (float)var1) < 0.05F) {
                        var10 = 0.0F;
                        this.var_e74 = false;
                     }

                     this.player.sub_84b(var10);
                  }
               }
            }
         case 2:
         default:
            break;
         case 3:
            this.var_80a = false;
            var5 = (int)var1 >> 1;
            if (this.waste != null) {
               this.waste.sub_5c5(var5, var5, var5);
            }

            if (this.explosion != null) {
               if (this.explosion.sub_185()) {
                  this.state = 4;
                  this.explosion.sub_36();
               } else {
                  this.var_87b = (int)((long)this.var_87b + var1);
                  if (this.var_87b > this.var_26) {
                     this.var_87b = 0;
                     this.state = 4;
                  }
               }
            } else {
               this.state = 4;
            }
            break;
         case 4:
            this.var_c61 = (int)((long)this.var_c61 + var1);
            if (this.var_9d8 && this.player.sub_ace() && this.waste != null) {
               var5 = (int)var1 >> 1;
               this.waste.sub_5c5(var5, var5, var5);
               if (this.var_c61 > 45000 && this.explosion != null) {
                  if (this.var_c61 < 90000) {
                     this.explosion.sub_141(this.pos.x, this.pos.y, this.pos.z);
                     if (this.level.getPlayerEgo().var_15f4.var_fdf == this) {
                        this.level.getPlayerEgo().var_15f4.var_fdf = null;
                     }

                     this.var_c61 = 90000;
                  } else if (this.explosion.sub_185()) {
                     if (this.var_907 && !this.var_be0) {
                        this.var_af6 = true;
                     }

                     this.waste = null;
                     this.var_c61 = 0;
                     this.sub_34(false);
                  }
               }
            } else {
               if (this.var_907 && !this.var_be0) {
                  this.var_af6 = true;
               }

               if (this.var_c61 > 45000) {
                  this.sub_34(false);
               }
            }
            break;
         case 5:
            if (this.var_3fa != null && !this.var_3fa.hidden && this.var_311.x < this.var_4c && this.var_311.x > -this.var_4c && this.var_311.y < this.var_4c && this.var_311.y > -this.var_4c && this.var_311.z < this.var_4c && this.var_311.z > -this.var_4c) {
               this.state = 1;
               this.player.sub_a98(true);
            }
            break;
         case 6:
            this.speed *= 1.1F;
            this.var_379.sub_202((int)((float)var1 * this.speed));
            if (this.speed > 100.0F) {
               this.sub_52b();
            }
         }

         KIPlayer[] var11;
         if (this.var_80a && (var11 = this.level.sub_5b7()) != null) {
            for(var6 = 0; var6 < var11.length; ++var6) {
               AEVector3D var8;
               if (var11[var6] != null && var11[var6].sub_1b8(this.var_379.sub_216(this.positon)) && (var8 = var11[var6].sub_005(this.var_379.sub_216(this.positon))) != null) {
                  this.var_727.set(this.var_379.sub_36a(this.var_727));
                  var8.subtract(this.var_727);
                  var8.scale((int)((float)var1 * 5.0F));
                  this.pos = this.var_727.add(var8, this.pos);
                  this.pos.normalize();
                  this.var_379.sub_85f().setOrientation(this.pos);
                  this.var_379.sub_202((int)((float)var1 * this.speed));
               }
            }
         }

      }
   }

   public final void sub_109doSth() {
      if (this.waste != null) {
         GameStatus.renderer.sub_87(this.waste);
      }

      if (this.player.sub_ace() || this.state == 5) {
         if (this.state != 4 && this.state != 3) {
            if (this.var_efb) {
               GameStatus.renderer.sub_87(this.var_379);
               if (this.var_6d7 != null) {
                  this.var_6d7.sub_160();
               }
            } else {
               this.var_379.sub_109(true);
            }
         } else if ((this.state == 3 || this.state == 4) && this.explosion != null && !this.explosion.sub_185()) {
            this.explosion.sub_179(this.var_44b);
         }

         if (this.state == 6) {
            GameStatus.renderer.sub_87(this.var_aa5);
         }

      }
   }

   public final boolean sub_162(int var1, int var2, int var3) {
      return this.state != 4 && this.state != 3 ? false : false;
   }

   public final void sub_2e5() {
      IndexManager.sub_1b8(this.var_379, this.var_3f3);
      boolean var1 = this.player.sub_992();
      this.player.sub_20c();
      if (var1) {
         this.player.sub_9bb();
      }

      this.state = 1;
      this.waste = null;
      this.var_7e2 = false;
      this.var_33d = -1;
      this.var_50b.sub_ad();
      this.var_49f = this.player.getHitpoints();
      this.var_4de = 0;
      this.var_514 = false;
      this.var_c61 = 0;
      this.sub_34(true);
      this.var_dbe = 0;
      this.speed = this.var_a7;
      this.sub_2b8(true);
      if (this.race == 9) {
         this.var_a3b = null;
      } else {
         Generator var2 = new Generator();
         this.var_a3b = var2.getLootList();
      }
   }
}
