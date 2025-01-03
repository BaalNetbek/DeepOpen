package Main;

import AbyssEngine.AEImage;
import AbyssEngine.AEMath;
import AbyssEngine.AEResourceManager;
import AbyssEngine.AEVector3D;
import AbyssEngine.AbstractMesh;
import AbyssEngine.Camera;
import AbyssEngine.Class_1025;
import AbyssEngine.Class_198;
import AbyssEngine.FileRead;
import AbyssEngine.GameStatus;
import AbyssEngine.Group;
import AbyssEngine.Layout;
import AbyssEngine.Level;
import AbyssEngine.Matrix;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.Popup;
import AbyssEngine.ProducedGood;
import AbyssEngine.SolarSystem;
import AbyssEngine.Station;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import AbyssEngine.SystemPathFinder;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class StarMap {
   private final int[] var_e4 = new int[]{320, 192, 256, 256, 192, 256, 192, 192, 320, 256, 192, 192, 320, 256, 320, 256, 256, 256, 320, 192};
   private int var_123;
   private int var_201;
   private int var_254;
   private int var_292;
   private int var_2ce;
   private int var_349;
   private float var_35d;
   private float var_39b;
   private float var_3af;
   private float var_401;
   private int var_452;
   private int var_4b6;
   private int frameTime;
   private boolean curPushLeft;
   private boolean curPushRight;
   private boolean curPushUp;
   private boolean curPushDown;
   private float curSpeedX;
   private float curSpeedY;
   private float curDirX;
   private float curDirY;
   private Image var_764;
   private Image var_776;
   private Image var_78f;
   private Image var_7eb;
   private Image var_81d;
   private Image var_848;
   private Image var_871;
   private Sprite var_895;
   private Sprite var_8f6;
   private Sprite var_924;
   private SolarSystem[] systems;
   private Station[] var_993;
   private Popup var_9e6;
   private int selectedSystem;
   private int selectedPlanet;
   private AbstractMesh[] var_a6c;
   private Class_198 var_aad;
   private Camera var_b0a;
   private Camera var_b3a;
   private AEVector3D var_b6f;
   private AEVector3D var_b93;
   private Matrix var_bf7;
   private AbstractMesh[] var_c27;
   private AbstractMesh[] var_c63;
   private int[] var_c8d;
   private int[] var_cb1;
   private int[] pathToDestination_;
   private Group var_ce0;
   private boolean var_cfc;
   private boolean var_d20;
   private boolean var_d7c;
   private boolean jumpMapModeVar1_;
   private boolean jumpMapModeVar2_;
   private Class_1025 var_df9;
   private Class_1025 var_e4c;
   private Class_1025 var_e9b;
   private boolean var_ee8;
   private Image var_f1d;
   private int var_f2b;
   private int var_f8b;
   private int var_ffe;
   private float var_101a;
   private boolean var_105c;
   public boolean var_1071;
   private AbstractMesh var_10c8;
   private AbstractMesh var_110d;
   private Image[] var_114f = new Image[5];
   private boolean[] var_1168 = new boolean[10];
   private int destinationOrMissionSystem_;
   private boolean var_11d9;
   private int var_11e9;
   private int var_124c;
   private boolean var_1271;
   private int var_129f;
   private boolean var_1302;

   public StarMap(boolean var1, Mission var2, boolean var3, int var4) {
      this.var_123 = GameStatus.screenHeight - 14;
      this.var_349 = 0;
      this.selectedSystem = -1;
      this.var_201 = 2;
      this.var_254 = 15;
      this.var_292 = GameStatus.screenWidth - 4;
      this.var_2ce = this.var_123 - this.var_254;
      String var5 = "/data/interface/fog.png";
      this.var_764 = AEImage.loadImage("/data/interface/fog.png", true);
      var5 = "/data/interface/menu_map_jumpgate.png";
      this.var_81d = AEImage.loadImage("/data/interface/menu_map_jumpgate.png", true);
      var5 = "/data/interface/menu_map_mainmission.png";
      this.var_78f = AEImage.loadImage("/data/interface/menu_map_mainmission.png", true);
      var5 = "/data/interface/menu_map_sidemission.png";
      this.var_7eb = AEImage.loadImage("/data/interface/menu_map_sidemission.png", true);
      var5 = "/data/interface/map_blueprint.png";
      this.var_776 = AEImage.loadImage("/data/interface/map_blueprint.png", true);
      var5 = "/data/interface/menu_map_visited.png";
      this.var_848 = AEImage.loadImage("/data/interface/menu_map_visited.png", true);
      var5 = "/data/interface/menu_map_direction.png";
      Image var7 = AEImage.loadImage("/data/interface/menu_map_direction.png", true);
      this.var_895 = new Sprite(var7);
      var5 = "/data/interface/logos_small.png";
      var7 = AEImage.loadImage("/data/interface/logos_small.png", true);
      this.var_924 = new Sprite(var7, var7.getHeight(), var7.getHeight());
      this.var_452 = this.var_764.getWidth() << 1;
      this.var_4b6 = this.var_764.getHeight() << 1;
      new FileRead();
      this.systems = FileRead.loadSystemsBinary();
      var5 = "/data/interface/map_sun_glow.png";
      this.var_f1d = AEImage.loadImage("/data/interface/map_sun_glow.png", true);
      this.var_aad = new Class_198();
      this.var_a6c = new AbstractMesh[this.systems.length];

      for(int var8 = 0; var8 < this.var_a6c.length; ++var8) {
         int var6 = this.systems[var8].getStarTextureIndex();
         this.var_a6c[var8] = AEResourceManager.getGeometryResource(6781);
         this.var_a6c[var8].sub_980(var6, var6);
         this.var_a6c[var8].sub_9aa((byte)1);
         this.var_a6c[var8].setFlag_(2);
         this.var_a6c[var8].sub_48(5000);
         this.var_a6c[var8].sub_5c5(0, 2048, 0);
         this.var_a6c[var8].sub_7af(1024, 1024, 1024);
         this.var_a6c[var8].moveTo(-8000 + (int)((float)(100 - this.systems[var8].getPosX()) / 100.0F * 10000.0F), -7000 + (int)((float)(100 - this.systems[var8].getPosY()) / 100.0F * 9000.0F), 2000 + (int)((float)(100 - this.systems[var8].getPosZ()) / 100.0F * 2500.0F));
         this.var_aad.sub_25(this.var_a6c[var8]);
      }

      if (Status.getCurrentCampaignMission() >= 32 && Status.wormholeSystem >= 0) {
         this.var_10c8 = AEResourceManager.getGeometryResource(6805);
         this.var_10c8.setDraw(true);
         this.var_10c8.sub_918(30);
         this.var_10c8.sub_7af(512, 512, 512);
         this.var_10c8.sub_9aa((byte)2);
         this.var_10c8.sub_1f3(this.var_a6c[Status.wormholeSystem].sub_237());
         this.var_aad.sub_25(this.var_10c8);
      }

      this.var_aad.sub_109(true);
      this.var_b6f = new AEVector3D();
      this.var_b93 = new AEVector3D();
      new AEVector3D();
      this.var_bf7 = new Matrix();
      GameStatus.random.setSeed(System.currentTimeMillis());
      this.sub_4e(var1, var2, var3, var4);
   }

   public final void sub_4e(boolean var1, Mission var2, boolean var3, int var4) {
      this.var_d20 = var1;
      this.var_1271 = var3;
      this.var_124c = var4;
      this.var_35d = (float)(GameStatus.screenWidth >> 1);
      this.var_39b = (float)(this.var_254 + (this.var_2ce >> 1));
      this.var_3af = 0.0F;
      this.var_401 = 0.0F;
      this.var_b3a = GameStatus.var_8ce.getCamera();
      if (this.var_b0a == null) {
         this.var_b0a = Camera.sub_1b1(this.var_292, this.var_2ce + 20, 1000, 10, 31768);
         this.var_b0a.sub_18f(0, 0, -2500);
         this.var_b0a.sub_5c5(0, 2048, 0);
         this.var_b0a.moveTo((int)this.var_3af * 20, (int)this.var_401 * 20, 0);
         this.var_b0a.sub_109(true);
      }

      GameStatus.var_8ce.sub_19(this.var_b0a);
      this.var_105c = Status.getCurrentCampaignMission() >= 16;
      this.var_349 = this.var_105c ? 0 : 3;
      this.selectedSystem = Status.getSystem().getId();
      if (this.var_349 == 3) {
         this.sub_158();
         this.var_b0a.sub_ba(500);
         this.var_cfc = false;
         this.selectedPlanet = 0;

         while(this.var_993[this.selectedPlanet].getId() != Status.getStation().getId()) {
            this.sub_a9(32);
         }

         this.sub_dd();
      } else {
         this.var_cfc = true;
         if (var3) {
            this.var_b6f.set(this.var_a6c[var4].sub_237());
         } else {
            this.var_b6f.set(this.var_a6c[Status.getSystem().getId()].sub_237());
         }

         this.var_b0a.moveTo(this.var_b6f.x, this.var_b6f.y, 0);
         this.var_3af = (float)(this.var_b6f.x / 20);
         this.var_401 = (float)(this.var_b6f.y / 20);
         this.sub_188(0.0F, 0.0F);
         this.var_b0a.sub_109(true);
         this.var_b0a.sub_fa(this.var_b6f);
         this.sub_188((float)this.var_b6f.x - this.var_35d, (float)this.var_b6f.y - this.var_39b);
         this.var_ee8 = false;
      }

      this.var_11d9 = false;
      this.jumpMapModeVar1_ = false;
      this.jumpMapModeVar2_ = false;
      this.var_1071 = false;

      for(int var8 = 0; var8 < this.var_a6c.length; ++var8) {
         this.var_a6c[var8].setDraw(Status.getVisibleSystems()[var8]);
      }

      this.pathToDestination_ = null;
      this.destinationOrMissionSystem_ = -1;
      int var5;
      int var7;
      if (var1) {
         Mission var9 = var2;
         if (!var2.isEmpty() && var2.sub_12c() && Status.getSystem().getNeighbourSystems() != null) {
            for(var5 = 0; var5 < this.systems.length; ++var5) {
               if (this.systems[var5].getStations() != null) {
                  for(var7 = 0; var7 < this.systems[var5].getStations().length; ++var7) {
                     if (var9.setCampaignMission() == this.systems[var5].getStations()[var7]) {
                        this.destinationOrMissionSystem_ = var5;
                        break;
                     }
                  }
               }
            }

            if (this.destinationOrMissionSystem_ >= 0) {
               SystemPathFinder var6 = new SystemPathFinder();
               this.pathToDestination_ = var6.getSystemPath(this.systems, Status.getSystem().getId(), this.destinationOrMissionSystem_);
            }
         }
      }

      this.var_11e9 = 0;
      short[] var10 = new short[]{223, 224, 271, 279, 278, 132};

      for(var5 = 0; var5 < var10.length; ++var5) {
         if ((var7 = SymbolMapManager_.sub_25b(GameStatus.langManager.getLangString(var10[var5]), 1)) > this.var_11e9) {
            this.var_11e9 = var7;
         }
      }

      this.var_11e9 += 32;
      this.var_129f = 0;
   }

   public final void sub_98() {
      this.var_b0a = null;
      this.var_10c8 = null;
      this.var_110d = null;
   }

   public final boolean sub_a9(int var1) {
      if (var1 == 256) {
         if (this.var_349 == 0 && this.selectedSystem >= 0 && !this.var_d20 && !this.var_1271) {
            if (this.var_d7c) {
               this.var_d7c = false;
               if (this.var_1302 && this.var_9e6.sub_9a()) {
                  Level.autopilotDestination = Status.voidStation;
                  this.var_1071 = true;
                  GameStatus.var_8ce.sub_19(this.var_b3a);
               }

               this.var_1302 = false;
               return true;
            }

            if (!this.jumpMapModeVar2_ && !Status.getSystem().inJumpageRange(this.systems[this.selectedSystem].getId())) {
               if (this.var_9e6 == null) {
                  this.var_9e6 = new Popup();
               }

               this.var_9e6.sub_8f(GameStatus.langManager.getLangString(241), false);
               this.var_d7c = true;
            } else {
               this.var_349 = 1;
            }
         } else if (this.var_349 == 3) {
            if (this.var_d7c) {
               if (this.var_9e6.sub_9a()) {
                  if (!this.var_d20) {
                     if (this.jumpMapModeVar1_) {
                        Level.autopilotDestination = this.var_993[this.selectedPlanet];
                        this.var_1071 = true;
                        GameStatus.var_8ce.sub_19(this.var_b3a);
                        return true;
                     }

                     if (this.jumpMapModeVar1_) {
                        Status.sub_1e5(this.var_993[this.selectedPlanet]);
                        Level.autopilotDestination = null;
                        Level.sub_16a();
                        Status.jumpgateUsed();
                     } else if (Status.getCurrentCampaignMission() != 3) {
                        Status.baseArmour = -1;
                        Status.shield = -1;
                        Status.additionalArmour = -1;
                        Status.sub_1e5(Status.getStation());
                        if (!this.var_993[this.selectedPlanet].equals(Status.getStation())) {
                           Level.autopilotDestination = this.var_993[this.selectedPlanet];
                        }

                        if (this.jumpMapModeVar2_) {
                           Level.var_1311 = true;
                        }

                        Medals.resetNewMedals();
                     }

                     GameStatus.var_bfb.setScene(GameStatus.scenes[2]);
                  }

                  return true;
               }

               this.var_d7c = false;
               return true;
            }

            if (!this.var_d20 && this.var_993[this.selectedPlanet].getId() != Status.getStation().getId()) {
               if (this.var_9e6 == null) {
                  this.var_9e6 = new Popup();
               }

               this.var_9e6.sub_8f(GameStatus.langManager.getLangString(295) + ": " + this.var_993[this.selectedPlanet].getName() + "\n" + GameStatus.langManager.getLangString(242), true);
               this.var_d7c = true;
            }
         }
      }

      if (var1 == 16384 && !this.var_d7c && !this.var_1271) {
         this.var_11d9 = !this.var_11d9;
      }

      if (var1 == 8192) {
         if (this.var_349 == 0) {
            GameStatus.var_8ce.sub_19(this.var_b3a);
            return false;
         }

         if (this.var_349 == 3) {
            if (!this.var_105c) {
               GameStatus.var_8ce.sub_19(this.var_b3a);
               return false;
            }

            this.var_ee8 = true;
            this.selectedPlanet = 0;
            this.sub_dd();
         }
      }

      if (this.var_d7c) {
         if (var1 == 4) {
            this.var_9e6.sub_c6();
         } else if (var1 == 32) {
            this.var_9e6.sub_ff();
         }
      } else if (this.var_349 == 3) {
         if (var1 != 64 && var1 != 32) {
            if (var1 == 2 || var1 == 4) {
               --this.selectedPlanet;
               if (this.selectedPlanet < 0) {
                  this.selectedPlanet = this.var_993.length - 1;
               }

               this.sub_dd();
            }
         } else {
            ++this.selectedPlanet;
            if (this.selectedPlanet >= this.var_993.length) {
               this.selectedPlanet = 0;
            }

            this.sub_dd();
         }
      }

      return true;
   }

   private void sub_dd() {
      int var1 = this.var_ee8 ? 0 : this.selectedPlanet + 2;
      int var2 = this.var_ee8 ? -6000 : -4000;
      this.var_b6f = this.var_c27[var1].sub_22a(this.var_b6f);
      this.var_df9.sub_fe(this.var_b0a.sub_29c(), this.var_b6f.x);
      this.var_e4c.sub_fe(this.var_b0a.sub_2c5(), this.var_b6f.y);
      this.var_e9b.sub_fe(this.var_b0a.sub_31f(), this.var_b6f.z + var2);
   }

   public final void sub_101(int var1, int var2) {
      if (this.var_1271) {
         var1 = 0;
         this.var_129f += var2;
      }

      this.frameTime = var2;
      if (this.var_349 == 0) {
         this.curPushLeft = false;
         this.curPushRight = false;
         this.curPushUp = false;
         this.curPushDown = false;
         float var8 = 0.0F;
         float var3 = 0.0F;
         if ((var1 & 4) != 0) {
            var8 = -this.curSpeedX;
            this.curPushLeft = true;
         }

         if ((var1 & 32) != 0) {
            var8 = this.curSpeedX;
            this.curPushRight = true;
         }

         if ((var1 & 2) != 0) {
            var3 = -this.curSpeedY;
            this.curPushUp = true;
         }

         if ((var1 & 64) != 0) {
            var3 = this.curSpeedY;
            this.curPushDown = true;
         }

         if (!this.curPushLeft && !this.curPushRight) {
            this.curSpeedX *= 0.8F;
            var8 = this.curDirX < 0.0F ? -this.curSpeedX : this.curSpeedX;
         } else {
            this.curDirX = var8;
            if (this.curSpeedX < 0.8F) {
               this.curSpeedX = 0.8F;
            }

            this.curSpeedX *= 1.5F;
            if (this.curSpeedX > 5.5F) {
               this.curSpeedX = 5.5F;
            }
         }

         if (!this.curPushUp && !this.curPushDown) {
            this.curSpeedY *= 0.8F;
            var3 = this.curDirY < 0.0F ? -this.curSpeedY : this.curSpeedY;
         } else {
            this.curDirY = var3;
            if (this.curSpeedY < 0.8F) {
               this.curSpeedY = 0.8F;
            }

            this.curSpeedY *= 1.5F;
            if (this.curSpeedY > 5.5F) {
               this.curSpeedY = 5.5F;
            }
         }

         this.sub_188(var8, var3);
         this.var_101a = -1.0F;

         for(var1 = 0; var1 < this.systems.length; ++var1) {
            if (Status.getVisibleSystems()[var1]) {
               this.var_b0a.sub_fa(this.var_a6c[var1].sub_22a(this.var_b6f));
               if (this.var_b6f.z < 0 && this.var_35d > (float)(this.var_b6f.x - 20) && this.var_35d < (float)(this.var_b6f.x + 20) && this.var_39b > (float)(this.var_b6f.y - 20) && this.var_39b < (float)(this.var_b6f.y + 20)) {
                  float var9 = ((float)this.var_b6f.x - this.var_35d) / 4.0F;
                  float var4 = ((float)this.var_b6f.y - this.var_39b) / 4.0F;
                  float var5 = (var9 < 0.0F ? -var9 : var9) * 4.0F;
                  float var6 = (var4 < 0.0F ? -var4 : var4) * 4.0F;
                  this.var_101a = var5 > var6 ? var5 : var6;
                  this.var_101a = 10.0F - this.var_101a;
                  if ((float)this.var_b6f.x < this.var_35d) {
                     var8 = var9;
                  } else if ((float)this.var_b6f.x > this.var_35d) {
                     var8 = var9;
                  }

                  if ((float)this.var_b6f.y > this.var_39b) {
                     var3 = var4;
                  } else if ((float)this.var_b6f.y < this.var_39b) {
                     var3 = var4;
                  }

                  if (AEMath.abs((int)var8) > AEMath.abs((int)((float)this.var_b6f.x - this.var_35d))) {
                     var8 *= 0.5F;
                  }

                  if (AEMath.abs((int)var3) > AEMath.abs((int)((float)this.var_b6f.y - this.var_39b))) {
                     var3 *= 0.5F;
                  }

                  if (!this.curPushLeft && !this.curPushRight && !this.curPushUp && !this.curPushDown) {
                     this.sub_188(var8, var3);
                  }
                  break;
               }
            }
         }
      } else if (this.var_349 == 1) {
         this.var_b6f = this.var_b0a.sub_22a(this.var_b6f);
         if (this.var_b6f.z < this.var_a6c[this.selectedSystem].sub_32d() - 150) {
            this.var_b6f.subtract(this.var_a6c[this.selectedSystem].sub_28c());
            this.var_b6f.scale(-1024);
            this.var_b0a.sub_19c(this.var_b6f);
         } else {
            if (this.var_cfc) {
               this.sub_158();
               this.var_b0a.sub_ba(500);
            } else {
               for(var2 = 0; var2 < this.var_c27.length; ++var2) {
                  this.var_c27[var2] = null;
               }

               this.var_c27 = null;
               if (this.var_10c8 != null && this.var_ce0 != null) {
                  this.var_ce0.sub_7f(this.var_10c8);
               }

               this.var_ce0 = null;

               for(var2 = 0; var2 < this.var_c63.length; ++var2) {
                  this.var_c63[var2] = null;
               }

               this.var_c63 = null;
               this.var_b0a.sub_ba(1000);
               this.var_b0a.setRotation(0, 2048, 0);
               this.var_b0a.moveTo((int)this.var_3af * 20, (int)this.var_401 * 20, this.var_b0a.sub_31f());
               if (this.var_10c8 != null && this.var_aad != null) {
                  this.var_aad.sub_25(this.var_10c8);
                  this.var_10c8.sub_85f().identity();
                  this.var_10c8.sub_7af(512, 512, 512);
                  this.var_10c8.sub_1f3(this.var_a6c[Status.wormholeSystem].sub_237());
               }
            }

            this.var_349 = 2;
         }
      } else if (this.var_349 == 2) {
         this.var_b6f = this.var_b0a.sub_216(this.var_b6f);
         if (this.var_cfc) {
            if (this.var_b6f.z > this.var_a6c[this.selectedSystem].sub_31f() - 6000) {
               this.var_b93.set(this.var_a6c[this.selectedSystem].sub_216(this.var_b93));
               AEVector3D var10000 = this.var_b93;
               var10000.z -= 6000;
               this.var_b6f.subtract(this.var_b93);
               this.var_b6f.scale(-1024);
               this.var_b0a.sub_19c(this.var_b6f);
            } else {
               this.var_cfc = false;
               this.var_349 = 3;
               this.sub_dd();
            }
         } else if (this.var_b6f.z > 0) {
            this.var_b93.set(this.var_a6c[this.selectedSystem].sub_216(this.var_b93));
            this.var_b93.z = 0;
            this.var_b6f.subtract(this.var_b93);
            this.var_b6f.scale(-1024);
            this.var_b0a.sub_18f(0, 0, this.var_b6f.z);
         } else {
            this.var_cfc = true;
            this.var_349 = 0;
         }
      } else if (this.var_349 == 3) {
         this.var_df9.sub_b7(var2 << 1);
         this.var_e4c.sub_b7(var2 << 1);
         this.var_e9b.sub_b7(var2 << 1);
         this.var_b0a.moveTo(this.var_df9.sub_135(), this.var_e4c.sub_135(), this.var_e9b.sub_135());
         if (this.var_ee8 && this.var_df9.sub_14d(true) && this.var_e4c.sub_14d(true) && this.var_e9b.sub_14d(true)) {
            this.var_349 = 1;
            this.var_ee8 = false;
         }
      }

      int var11;
      if (this.var_ce0 != null) {
         for(var2 = 2; var2 < this.var_c27.length; ++var2) {
            this.var_bf7.identity();
            var11 = this.var_c8d[var2 - 2];
            this.var_bf7.addEulerAngles(0, var11, 0);
            this.var_b6f.set(0, 0, this.var_cb1[var2 - 2]);
            this.var_b93.set(0, 0, 0);
            this.var_b93 = this.var_bf7.sub_9e9(this.var_b6f, this.var_b93);
            this.var_c27[var2].sub_1f3(this.var_b93);
            this.var_b6f.set(this.var_c27[var2].sub_216(this.var_b93));
            this.var_b6f.normalize();
         }
      }

      this.sub_2b5();
      StarMap var10 = this;

      try {
         GameStatus.graphics3D.bindTarget(GameStatus.graphics);
         if (var10.var_ce0 != null) {
            GameStatus.var_8ce.sub_87(var10.var_ce0);
         } else {
            var10.var_f2b += var10.frameTime;
            var2 = 0;

            while(true) {
               if (var2 >= var10.var_a6c.length) {
                  GameStatus.var_8ce.sub_87(var10.var_aad);
                  break;
               }

               boolean var12 = false;
               if (var2 == var10.selectedSystem && var10.var_1271 && var10.var_129f < 4000) {
                  var11 = (int)((float)var10.var_129f / 4000.0F * (float)(1024 + (AEMath.sin(var10.var_f2b + (var2 << 8)) >> 5)));
               } else {
                  var11 = 1024 + (AEMath.sin(var10.var_f2b + (var2 << 8)) >> 5);
               }

               var10.var_a6c[var2].sub_7af(var11, var11, var11);
               ++var2;
            }
         }

         GameStatus.var_8ce.sub_cc(System.currentTimeMillis());
         GameStatus.graphics3D.clear();
         GameStatus.graphics3D.releaseTarget();
      } catch (Exception var7) {
         GameStatus.graphics3D.releaseTarget();
         var7.printStackTrace();
      }

      this.sub_23f();
   }

   private void sub_158() {
      int var1 = this.systems[this.selectedSystem].getStations().length;
      this.var_993 = new Station[var1];
      new FileRead();
      this.var_993 = FileRead.loadStationsBinary(this.systems[this.selectedSystem]);
      this.var_c8d = new int[var1];
      this.var_cb1 = new int[var1];
      GameStatus.random.setSeed((long)(this.systems[this.selectedSystem].getId() * 1000));
      this.var_c27 = new AbstractMesh[var1 + 2];
      this.var_ce0 = new Group();

      int var2;
      for(var2 = 0; var2 < this.var_1168.length; ++var2) {
         this.var_1168[var2] = false;
      }

      int var3;
      for(var2 = 0; var2 < this.var_c27.length; ++var2) {
         if (var2 > 1) {
            this.var_c27[var2] = AEResourceManager.getGeometryResource(6778);
         } else {
            var3 = this.systems[this.selectedSystem].getStarTextureIndex();
            this.var_c27[var2] = AEResourceManager.getGeometryResource(6781);
            this.var_c27[var2].sub_980(var3, var3);
         }

         this.var_c27[var2].sub_9aa((byte)1);
         this.var_c27[var2].sub_48(5000);
         if (var2 > 1) {
            Matrix var7 = new Matrix();
            int var4 = 0;
            boolean var5 = false;

            int var6;
            while(!var5) {
               var6 = GameStatus.random.nextInt(this.var_1168.length);
               if (!this.var_1168[var6]) {
                  this.var_1168[var6] = true;
                  var4 = 4096 / this.var_1168.length * var6;
                  var5 = true;
               }
            }

            this.var_c8d[var2 - 2] = var4;
            var7.addEulerAngles(0, var4, 0);
            var6 = var2 == 2 ? 512 : this.var_cb1[var2 - 3];
            this.var_cb1[var2 - 2] = var6 + 128 + GameStatus.random.nextInt(376);
            AEVector3D var9 = new AEVector3D(0, 0, this.var_cb1[var2 - 2]);
            AEVector3D var10 = new AEVector3D();
            var10 = var7.sub_9e9(var9, var10);
            this.var_c27[var2].sub_19c(var10);
            var3 = this.var_993[var2 - 2].getPlanetTextureId();
            var4 = this.var_e4[var3];
            this.var_c27[var2].sub_7af(var4, var4, var4);
            (new AEVector3D(this.var_c27[var2].sub_237())).normalize();
            this.var_c27[var2].sub_980(var3, var3);
         } else {
            this.var_c27[var2].sub_18f(0, 0, var2 * 32);
            this.var_c27[var2].sub_5c5(0, 2048, 256);
            this.var_c27[var2].sub_7af(256, 256, 256);
         }

         this.var_c27[var2].setFlag_(2);
         this.var_ce0.sub_25(this.var_c27[var2]);
      }

      this.var_c27[1].setDraw(false);
      this.var_c27[0].setDraw(false);
      this.var_c63 = new AbstractMesh[var1];

      for(var2 = 0; var2 < this.var_c63.length; ++var2) {
         this.var_c63[var2] = AEResourceManager.getGeometryResource(6779);
         this.var_c63[var2].setFlag_(2);
         this.var_c63[var2].sub_5c5(-1024, 0, 0);
         this.var_ce0.sub_25(this.var_c63[var2]);
         var3 = this.var_cb1[var2];
         this.var_c63[var2].sub_7af(var3 << 1, var3 << 1, var3 << 1);
      }

      this.var_ce0.sub_1f3(this.var_b0a.sub_216(this.var_b6f));
      this.var_ce0.setRotation(-256, 0, 256);
      this.var_df9 = new Class_1025(0, 0);
      this.var_e4c = new Class_1025(0, 0);
      this.var_e9b = new Class_1025(0, 0);
      this.var_ce0.sub_109(true);
      Image var8 = AEImage.loadImage("/data/interface/sun_" + this.systems[this.selectedSystem].getStarTextureIndex() + ".png", true);
      this.var_8f6 = new Sprite(var8);
      this.var_8f6.defineReferencePixel(this.var_8f6.getWidth(), this.var_8f6.getHeight());
      if (this.var_10c8 != null && this.selectedSystem == Status.wormholeSystem) {
         if (this.var_aad != null) {
            this.var_aad.sub_7f(this.var_10c8);
         }

         this.var_10c8.sub_7af(256, 256, 256);
         this.var_10c8.sub_1f3(this.var_c27[this.systems[this.selectedSystem].verifyStationInSystem(Status.wormholeStation) + 2].sub_237());
         this.var_ce0.sub_25(this.var_10c8);
      }

      if (this.var_110d == null) {
         this.var_110d = AEResourceManager.getGeometryResource(13999);
      }

      if (this.systems[this.selectedSystem].getId() == Status.getSystem().getId()) {
         this.var_110d.sub_8c9(this.var_c27[this.systems[this.selectedSystem].verifyStationInSystem(Status.getStation().getId()) + 2].sub_85f());
         this.var_110d.setRotation(512, 0, -1024);
         this.var_ce0.sub_25(this.var_110d);
      }

      GameStatus.random.setSeed(System.currentTimeMillis());
   }

   private void sub_188(float var1, float var2) {
      if (!this.var_d7c) {
         if (this.var_35d >= (float)(GameStatus.screenWidth >> 1) && var1 > 0.0F || this.var_35d <= (float)(GameStatus.screenWidth >> 1) && var1 < 0.0F) {
            this.var_3af -= var1;
         }

         if (this.var_39b >= (float)(this.var_254 + (this.var_2ce >> 1)) && var2 > 0.0F || this.var_39b <= (float)(this.var_254 + (this.var_2ce >> 1)) && var2 < 0.0F) {
            this.var_401 -= var2;
         }

         if (this.var_3af >= 0.0F) {
            this.var_3af = 0.0F;
            this.var_35d += var1;
            if (this.var_35d > (float)(GameStatus.screenWidth >> 1)) {
               this.var_35d = (float)(GameStatus.screenWidth >> 1);
            }

            if (this.var_35d <= (float)(this.var_201 + 5)) {
               this.var_35d = (float)(this.var_201 + 5);
            }
         }

         if (this.var_401 >= 0.0F) {
            this.var_401 = 0.0F;
            this.var_39b += var2;
            if (this.var_39b > (float)(this.var_254 + (this.var_2ce >> 1))) {
               this.var_39b = (float)(this.var_254 + (this.var_2ce >> 1));
            }

            if (this.var_39b <= (float)(this.var_254 + 5)) {
               this.var_39b = (float)(this.var_254 + 5);
            }
         }

         if (this.var_3af <= (float)(-this.var_452 + this.var_292)) {
            this.var_3af = (float)(-this.var_452 + this.var_292);
            this.var_35d += var1;
            if (this.var_35d < (float)(GameStatus.screenWidth >> 1)) {
               this.var_35d = (float)(GameStatus.screenWidth >> 1);
            }

            if (this.var_35d >= (float)(this.var_201 + this.var_292 - 5)) {
               this.var_35d = (float)(this.var_201 + this.var_292 - 5);
            }
         }

         if (this.var_401 <= (float)(-this.var_4b6 + this.var_2ce)) {
            this.var_401 = (float)(-this.var_4b6 + this.var_2ce);
            this.var_39b += var2;
            if (this.var_39b < (float)(this.var_254 + (this.var_2ce >> 1))) {
               this.var_39b = (float)(this.var_254 + (this.var_2ce >> 1));
            }

            if (this.var_39b >= (float)(this.var_254 + this.var_2ce - 5)) {
               this.var_39b = (float)(this.var_254 + this.var_2ce - 5);
            }
         }

         this.var_b0a.moveTo((int)this.var_3af * 20, (int)this.var_401 * 20, 0);
      }
   }

   public final boolean sub_1ae() {
      return this.var_349 == 3;
   }

   public final void setJumpMapMode(boolean var1, boolean var2) {
      this.jumpMapModeVar1_ = var1;
      this.jumpMapModeVar2_ = var2;
   }

   private void sub_23f() {
      int var1;
      int var3;
      switch(this.var_349) {
      case 0:
         GameStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);
         this.var_b0a.sub_fa(this.var_a6c[Status.getSystem().getId()].sub_22a(this.var_b93));
         var1 = this.selectedSystem;
         this.selectedSystem = -1;
         int[] var2 = Status.getSystem().getNeighbourSystems();

         float var6;
         float var7;
         float var12;
         for(var3 = 0; var3 < this.systems.length; ++var3) {
            if (Status.getVisibleSystems()[var3]) {
               this.var_b0a.sub_fa(this.var_a6c[var3].sub_22a(this.var_b6f));
               if (this.var_b6f.z < 0) {
                  if (this.var_35d > (float)(this.var_b6f.x - 10) && this.var_35d < (float)(this.var_b6f.x + 10) && this.var_39b > (float)(this.var_b6f.y - 10) && this.var_39b < (float)(this.var_b6f.y + 10)) {
                     this.selectedSystem = var3;
                  }

                  if (var2 != null && !this.jumpMapModeVar2_) {
                     boolean var4 = false;

                     for(int var5 = 0; var5 < var2.length; ++var5) {
                        if (var2[var5] == var3) {
                           var4 = true;
                           break;
                        }
                     }

                     if (var4) {
                        var12 = (float)(this.var_b6f.x - this.var_b93.x) / 10.0F;
                        var6 = (float)(this.var_b6f.y - this.var_b93.y) / 10.0F;
                        var7 = var12 * 2.0F;
                        float var8 = var6 * 2.0F;

                        for(int var10 = 0; var10 < 8; ++var10) {
                           GameStatus.graphics.setColor(var10 == this.var_ffe ? -4138775 : -12950906);
                           GameStatus.graphics.fillArc((int)((float)this.var_b93.x + var7) - 2, (int)((float)this.var_b93.y + var8) - 2, 4, 4, 0, 360);
                           var7 += var12;
                           var8 += var6;
                        }
                     }
                  }
               }

               if (var1 < 0) {
                  this.sub_29e(var3, false);
               }
            }
         }

         if (this.pathToDestination_ != null) {
            for(var3 = 0; var3 < this.pathToDestination_.length - 1; ++var3) {
               this.var_b0a.sub_fa(this.var_a6c[this.pathToDestination_[var3]].sub_22a(this.var_b93));
               this.var_b0a.sub_fa(this.var_a6c[this.pathToDestination_[var3 + 1]].sub_22a(this.var_b6f));
               float var11 = (float)(this.var_b6f.x - this.var_b93.x) / 10.0F;
               var12 = (float)(this.var_b6f.y - this.var_b93.y) / 10.0F;
               var6 = var11 * 2.0F;
               var7 = var12 * 2.0F;

               for(int var13 = 0; var13 < 8; ++var13) {
                  GameStatus.graphics.setColor((var3 << 3) + var13 == this.var_ffe ? -1 : -4740812);
                  GameStatus.graphics.fillArc((int)((float)this.var_b93.x + var6) - 2, (int)((float)this.var_b93.y + var7) - 2, 4, 4, 0, 360);
                  var6 += var11;
                  var7 += var12;
               }
            }
         }

         if (this.selectedSystem >= 0 && (!this.var_1271 || this.var_129f >= 4000)) {
            this.sub_29e(this.selectedSystem, false);
         }

         if (!this.jumpMapModeVar2_) {
            this.var_f8b += this.frameTime;
            if (this.var_f8b > 400) {
               ++this.var_ffe;
               if (this.var_ffe > (this.pathToDestination_ == null ? 20 : this.pathToDestination_.length << 3)) {
                  this.var_ffe = 0;
               }
            }
         }
         break;
      case 3:
         this.var_871 = AEImage.loadImage("/data/interface/logo_" + this.systems[this.selectedSystem].getRace() + ".png", true);
         GameStatus.graphics.drawImage(this.var_871, 5, 18, 20);

         for(var3 = 0; var3 < this.var_993.length; ++var3) {
            if (var3 != this.selectedPlanet) {
               this.sub_29e(var3, true);
            }
         }

         this.sub_29e(this.selectedPlanet, true);
         break;
      default:
         Layout.sub_3c6("", "");
      }

      if (this.var_ce0 == null && !this.var_1271) {
         if ((var1 = (int)(this.var_101a * 1.5F)) > 0) {
            GameStatus.graphics.setColor(-8086094);
            GameStatus.graphics.drawLine((int)this.var_35d, (int)this.var_39b + var1, (int)this.var_35d - var1, (int)this.var_39b);
            GameStatus.graphics.drawLine((int)this.var_35d - var1, (int)this.var_39b, (int)this.var_35d, (int)this.var_39b - var1);
            GameStatus.graphics.drawLine((int)this.var_35d, (int)this.var_39b - var1, (int)this.var_35d + var1, (int)this.var_39b);
            GameStatus.graphics.drawLine((int)this.var_35d + var1, (int)this.var_39b, (int)this.var_35d, (int)this.var_39b + var1);
         }

         this.var_895.setTransform(0);
         this.var_895.setRefPixelPosition((int)this.var_35d, (int)this.var_39b + var1);
         this.var_895.paint(GameStatus.graphics);
         this.var_895.setTransform(3);
         this.var_895.setRefPixelPosition((int)this.var_35d, (int)this.var_39b - var1);
         this.var_895.paint(GameStatus.graphics);
         this.var_895.setTransform(5);
         this.var_895.setRefPixelPosition((int)this.var_35d - var1, (int)this.var_39b);
         this.var_895.paint(GameStatus.graphics);
         this.var_895.setTransform(6);
         this.var_895.setRefPixelPosition((int)this.var_35d + var1, (int)this.var_39b);
         this.var_895.paint(GameStatus.graphics);
      }

      if (this.var_349 == 3) {
         Layout.sub_2db(GameStatus.langManager.getLangString(72) + ": " + this.systems[this.selectedSystem].getName() + " " + GameStatus.langManager.getLangString(41), false);
         Layout.sub_3c6(GameStatus.langManager.getLangString(223), GameStatus.langManager.getLangString(65));
         SymbolMapManager_.sub_161(!this.var_d20 && Status.getSystem().inJumpageRange(this.systems[this.selectedSystem].getId()) && this.var_993[this.selectedPlanet].getId() != Status.getStation().getId() ? GameStatus.langManager.getLangString(222) : "", GameStatus.screenWidth >> 1, GameStatus.screenHeight - 4, 1, 40);
      } else {
         Layout.sub_2db(GameStatus.langManager.getLangString(72), false);
         if (this.var_1271) {
            Layout.sub_3c6("", GameStatus.langManager.getLangString(65));
         } else {
            Layout.sub_3c6(GameStatus.langManager.getLangString(223), GameStatus.langManager.getLangString(65));
            SymbolMapManager_.sub_161(this.var_d20 ? "" : GameStatus.langManager.getLangString(221), GameStatus.screenWidth >> 1, GameStatus.screenHeight - 4, 1, 40);
         }
      }

      if (this.var_11d9 && (this.var_349 == 0 || this.var_349 == 3)) {
         Layout.drawFilledTitleBarWindow(GameStatus.langManager.getLangString(223), 1, GameStatus.screenHeight - 16 - 90 - 4, this.var_11e9, 94);
         int var9 = GameStatus.screenHeight - 3 - 16 - 13;
         GameStatus.graphics.drawImage(this.var_776, 10, var9, 20);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(132), 25, var9, 1, 17);
         var9 -= 15;
         GameStatus.graphics.drawImage(this.var_848, 10, var9, 20);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(224), 25, var9, 1, 17);
         var9 -= 15;
         GameStatus.graphics.drawImage(this.var_81d, 10, var9, 20);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(271), 25, var9, 1, 17);
         var9 -= 15;
         GameStatus.graphics.drawImage(this.var_7eb, 10, var9, 20);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(279), 25, var9, 1, 17);
         var9 -= 15;
         GameStatus.graphics.drawImage(this.var_78f, 10, var9, 20);
         SymbolMapManager_.sub_161(GameStatus.langManager.getLangString(278), 25, var9, 1, 17);
      }

      if (this.var_d7c) {
         this.var_9e6.sub_19a();
      }

   }

   private void sub_29e(int var1, boolean var2) {
      int var3;
      for(var3 = 0; var3 < this.var_114f.length; ++var3) {
         this.var_114f[var3] = null;
      }

      if (var2 && this.var_993[var1].sub_175() || !var2 && this.systems[var1].isFullyDiscovered()) {
         this.var_114f[0] = this.var_848;
      }

      Mission var7 = Status.getCampaignMission();
      Mission var4 = Status.getFreelanceMission();
      if (var7 != null && !var7.isEmpty() && (var2 && this.var_993[var1].getId() == var7.setCampaignMission() || !var2 && this.systems[var1].verifyStationInSystem(var7.setCampaignMission()) >= 0 || Status.getCurrentCampaignMission() > 32 && (var2 && this.var_993[var1].getId() == Status.wormholeStation && var7.setCampaignMission() == -1 || !var2 && this.systems[var1].verifyStationInSystem(Status.wormholeStation) >= 0 && var7.setCampaignMission() == -1))) {
         this.var_114f[1] = this.var_78f;
      }

      if (var4 != null && !var4.isEmpty() && (var2 && this.var_993[var1].getId() == var4.setCampaignMission() || !var2 && this.systems[var1].verifyStationInSystem(var4.setCampaignMission()) >= 0)) {
         this.var_114f[2] = this.var_7eb;
      }

      if (var2 && this.systems[this.selectedSystem].getJumpgateStationIndex() == this.var_993[var1].getId()) {
         this.var_114f[3] = this.var_81d;
      }

      ProducedGood[] var8;
      int var9;
      if ((var8 = Status.getWaitingGoods()) != null) {
         for(var9 = 0; var9 < var8.length; ++var9) {
            if (var8[var9] != null && (var2 && var8[var9].stationId == this.var_993[var1].getId() || !var2 && this.systems[var1].verifyStationInSystem(var8[var9].stationId) >= 0)) {
               this.var_114f[4] = this.var_776;
               break;
            }
         }
      }

      if (var2) {
         this.var_b0a.sub_fa(this.var_c27[var1 + 2].sub_22a(this.var_b6f));
      } else {
         this.var_b0a.sub_fa(this.var_a6c[var1].sub_22a(this.var_b6f));
      }

      boolean var11 = false;
      boolean var10 = false;
      if (var2) {
         var9 = this.var_b6f.y - 4;
         var3 = this.var_b6f.x + 10 + SymbolMapManager_.sub_25b(this.var_993[var1].getName(), 0) + 7;
         SymbolMapManager_.sub_161(this.var_993[var1].getName(), this.var_b6f.x + 10, var9, this.selectedPlanet == var1 ? 2 : (this.var_993[var1].sub_175() ? 0 : 1), 17);
         if (var1 == this.selectedPlanet) {
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(37) + ": " + this.var_993[var1].getTecLevel(), this.var_b6f.x + 10, var9 + SymbolMapManager_.sub_2c2(), this.var_993[var1].sub_175() ? 0 : 1);
         }
      } else {
         var9 = this.var_b6f.y - SymbolMapManager_.sub_2c2() - 4;
         byte var5 = 10;
         if (this.selectedSystem == var1) {
            this.var_924.setFrame(this.systems[var1].getRace());
            this.var_924.setPosition(this.var_b6f.x + 10, var9 - 4);
            this.var_924.paint(GameStatus.graphics);
            var5 = 27;
         }

         var3 = this.var_b6f.x + var5 + SymbolMapManager_.sub_25b(this.systems[var1].getName(), 0) + 7;
         boolean var6 = true;
         if (this.var_d20 && !var2 && this.destinationOrMissionSystem_ == this.systems[var1].getId()) {
            var6 = Layout.sub_119();
         }

         if (var6) {
            SymbolMapManager_.sub_161(this.systems[var1].getName(), this.var_b6f.x + var5, var9 - 2, this.selectedSystem != var1 && this.selectedSystem != Status.getSystem().getId() ? 1 : 2, 17);
         }

         if (var1 == this.selectedSystem) {
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(219) + ": ", this.var_b6f.x + 10, var9 + 2 * SymbolMapManager_.sub_2c2(), 0);
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(229 + this.systems[this.selectedSystem].getRace()), this.var_b6f.x + 10, var9 + 3 * SymbolMapManager_.sub_2c2(), 1);
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(220) + ": ", this.var_b6f.x + 10, var9 + 4 * SymbolMapManager_.sub_2c2(), 0);
            SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(225 + this.systems[this.selectedSystem].getSafety()), this.var_b6f.x + 10, var9 + 5 * SymbolMapManager_.sub_2c2(), 1);
         }
      }

      for(int var12 = 0; var12 < this.var_114f.length; ++var12) {
         if (this.var_114f[var12] != null) {
            GameStatus.graphics.drawImage(this.var_114f[var12], var3, var9 - 1, 20);
            var3 += this.var_114f[var12].getWidth() + 2;
         }
      }

   }

   private void sub_2b5() {
      GameStatus.graphics.setColor(0);
      GameStatus.graphics.fillRect(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
      if (this.var_349 == 3) {
         Layout.sub_2c1(GameStatus.langManager.getLangString(72) + ": " + this.systems[this.selectedSystem].getName() + " " + GameStatus.langManager.getLangString(41));
      } else {
         Layout.sub_2c1(GameStatus.langManager.getLangString(72));
      }

      int var3;
      if (this.var_ce0 != null) {
         SolarSystem var4 = this.systems[this.selectedSystem];
         GameStatus.graphics.setColor(var4.spaceShadeR, var4.spaceShadeG, var4.spaceShadeB);
         GameStatus.graphics.fillRect(this.var_201, this.var_254, this.var_292, this.var_2ce);
         this.var_b6f = this.var_c27[0].sub_22a(this.var_b6f);
         this.var_b0a.sub_fa(this.var_b6f);
         int var5 = this.var_b6f.x;
         var3 = this.var_b6f.y;
         this.var_8f6.setTransform(0);
         this.var_8f6.setRefPixelPosition(var5, var3);
         this.var_8f6.paint(GameStatus.graphics);
         this.var_8f6.setTransform(5);
         this.var_8f6.setRefPixelPosition(var5 - 1, var3);
         this.var_8f6.paint(GameStatus.graphics);
         this.var_8f6.setTransform(3);
         this.var_8f6.setRefPixelPosition(var5 - 1, var3 - 1);
         this.var_8f6.paint(GameStatus.graphics);
         this.var_8f6.setTransform(6);
         this.var_8f6.setRefPixelPosition(var5, var3 - 1);
         this.var_8f6.paint(GameStatus.graphics);
      } else {
         GameStatus.graphics.drawImage(this.var_764, (int)((float)this.var_201 + this.var_3af), (int)((float)this.var_254 + this.var_401), 0);
         GameStatus.graphics.drawImage(this.var_764, (int)((float)this.var_201 + this.var_3af) + (this.var_452 >> 1), (int)((float)this.var_254 + this.var_401), 0);
         GameStatus.graphics.drawImage(this.var_764, (int)((float)this.var_201 + this.var_3af), (int)((float)this.var_254 + this.var_401) + (this.var_4b6 >> 1), 0);
         GameStatus.graphics.drawImage(this.var_764, (int)((float)this.var_201 + this.var_3af) + (this.var_452 >> 1), (int)((float)this.var_254 + this.var_401) + (this.var_4b6 >> 1), 0);

         for(int var1 = 0; var1 < this.var_a6c.length; ++var1) {
            if (this.var_a6c[var1].sub_87() && (!this.var_1271 || this.var_124c != var1 || this.var_129f >= 4000)) {
               int[] var2;
               if ((var2 = this.systems[var1].getNeighbourSystems()) != null) {
                  this.var_b0a.sub_fa(this.var_a6c[var1].sub_22a(this.var_b6f));
                  GameStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);

                  for(var3 = 0; var3 < var2.length; ++var3) {
                     if (this.var_a6c[var2[var3]].sub_87() && (!this.var_1271 || this.var_124c != var3 || this.var_129f >= 4000)) {
                        this.var_b0a.sub_fa(this.var_a6c[var2[var3]].sub_22a(this.var_b93));
                        GameStatus.graphics.drawLine(this.var_b6f.x, this.var_b6f.y, this.var_b93.x, this.var_b93.y);
                     }
                  }
               }

               GameStatus.var_8ce.getCamera().sub_fa(this.var_a6c[var1].sub_22a(this.var_b6f));
               if (this.var_b6f.z < 0) {
                  GameStatus.graphics.drawImage(this.var_f1d, this.var_b6f.x, this.var_b6f.y, 3);
               }
            }
         }

      }
   }

   public final void askForJumpIntoAlienWorld() {
      this.var_1302 = true;
      if (this.var_9e6 == null) {
         this.var_9e6 = new Popup();
      }

      this.var_9e6.sub_8f(GameStatus.langManager.getLangString(243), true);
      this.var_d7c = true;
   }
}
