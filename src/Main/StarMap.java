package Main;

import AE.PaintCanvas.AEGraphics3D;
import AE.AEFile;
import AE.Math.AEMath;
import AE.AEResourceManager;
import AE.Math.AEVector3D;
import AE.AbstractMesh;
import AE.Camera;
import AE.EaseInOut;
import AE.CameraControllerGroup;
import GoF2.FileRead;
import AE.GlobalStatus;
import AE.Group;
import AE.ParticlesMesh;
import GoF2.Layout;
import GoF2.Level;
import AE.Math.Matrix;
import GoF2.Achievements;
import GoF2.Mission;
import GoF2.Popup;
import GoF2.ProducedGood;
import GoF2.SolarSystem;
import GoF2.Station;
import GoF2.Status;
import AE.PaintCanvas.Font;
import GoF2.SystemPathFinder;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public final class StarMap {
   private final int[] planetSizes = new int[]{320, 192, 256, 256, 192, 256, 192, 192, 320, 256, 192, 192, 320, 256, 320, 256, 256, 256, 320, 192};
   private int tmpMapInnerHeight;
   private int windowFrameWidth;
   private int windowFrameHeight;
   private int mapInnerWidth;
   private int mapInnerHeight;
   private int state;
   private float curScreenPosX;
   private float curScreenPosY;
   private float scrollX;
   private float scrollY;
   private int backGroundTileX;
   private int backGroundTileY;
   private int frameTime;
   private boolean curPushLeft;
   private boolean curPushRight;
   private boolean curPushUp;
   private boolean curPushDown;
   private float curSpeedX;
   private float curSpeedY;
   private float curDirX;
   private float curDirY;
   private Image fog;
   private Image bluePrintIcon;
   private Image mainMissionIcon;
   private Image sideMissionIcon;
   private Image jumpGateIcon;
   private Image checkMark;
   private Image raceLogoBig;
   private Sprite cursorLine;
   private Sprite systemStar;
   private Sprite logos;
   private SolarSystem[] systems;
   private Station[] selectedSystemStations;
   private Popup popup;
   private int selectedSystem;
   private int selectedPlanet;
   private AbstractMesh backgroundStars;
   private AbstractMesh[] stars;
   private Group galaxyMapGroup;
   private Camera starNetCamera_;
   private Camera lastCamera;
   private AEVector3D tmpStarScreenPos2;
   private AEVector3D tmpStarScreenPos1;
   private Matrix matrix;
   private AbstractMesh[] localStarAndPlanetsMeshes;
   private AbstractMesh[] localOrbits;
   private int[] planetRevolutAngs;
   private int[] distsToStar;
   private int[] pathToDestination_;
   private Group localSystem;
   private boolean galaxyMapView;
   private boolean overviewOnly_;
   private boolean destinationConfirmPopupOpen;
   private boolean fromJumpGate;
   private boolean fromStation;
   private EaseInOut smoothCamTransitionX;
   private EaseInOut smoothCamTransitionY;
   private EaseInOut smoothCamTransitionZ;
   private boolean scopingOut;
   private Image sunGlow;
   private int newSysAnimTimer;
   private int pathDisplayDelay;
   private int highlightedPathDot;
   private float curSqrSize;
   private boolean galaxyMapUnlocked;
   public boolean destSelected;
   private AbstractMesh wormhole;
   private AbstractMesh arrow;
   private Image[] legendItemIcons = new Image[5];
   private boolean[] occupiedOrbits_ = new boolean[10];
   private int destinationOrMissionSystem_;
   private boolean legendOpen;
   private int legendWindowWidth;
   private int discoveredSystemId;
   private boolean discoverSystemCutscene;
   private int newSystemAnimTime;
   private boolean jumpToAlienWorldPopupOpen;

   public StarMap(boolean var1, Mission var2, boolean var3, int var4) {
      this.tmpMapInnerHeight = GlobalStatus.screenHeight - 14;
      this.state = 0;
      this.selectedSystem = -1;
      this.windowFrameWidth = 2;
      this.windowFrameHeight = 15;
      this.mapInnerWidth = GlobalStatus.screenWidth - 4;
      this.mapInnerHeight = this.tmpMapInnerHeight - this.windowFrameHeight;
      String var5 = "/data/interface/fog.png";
      this.fog = AEFile.loadImage("/data/interface/fog.png", true);
      var5 = "/data/interface/menu_map_jumpgate.png";
      this.jumpGateIcon = AEFile.loadImage("/data/interface/menu_map_jumpgate.png", true);
      var5 = "/data/interface/menu_map_mainmission.png";
      this.mainMissionIcon = AEFile.loadImage("/data/interface/menu_map_mainmission.png", true);
      var5 = "/data/interface/menu_map_sidemission.png";
      this.sideMissionIcon = AEFile.loadImage("/data/interface/menu_map_sidemission.png", true);
      var5 = "/data/interface/map_blueprint.png";
      this.bluePrintIcon = AEFile.loadImage("/data/interface/map_blueprint.png", true);
      var5 = "/data/interface/menu_map_visited.png";
      this.checkMark = AEFile.loadImage("/data/interface/menu_map_visited.png", true);
      var5 = "/data/interface/menu_map_direction.png";
      Image var7 = AEFile.loadImage("/data/interface/menu_map_direction.png", true);
      this.cursorLine = new Sprite(var7);
      var5 = "/data/interface/logos_small.png";
      var7 = AEFile.loadImage("/data/interface/logos_small.png", true);
      this.logos = new Sprite(var7, var7.getHeight(), var7.getHeight());
      this.backGroundTileX = this.fog.getWidth() << 1;
      this.backGroundTileY = this.fog.getHeight() << 1;
      new FileRead();
      this.systems = FileRead.loadSystemsBinary();
      this.backgroundStars = new ParticlesMesh(256, 0, 48, 14, 62, 200, 200, (byte)2);
      this.backgroundStars.setTexture(AEResourceManager.getTextureResource(0));
      int[] var16 = ((ParticlesMesh)this.backgroundStars).getPositions();
      GlobalStatus.random.setSeed(10000L);

      for (int i = 0; i < var16.length; i += 3) {
         var16[i] = 5000 - GlobalStatus.random.nextInt(20000);
         var16[i + 1] = 5000 - GlobalStatus.random.nextInt(20000);
         var16[i + 2] = 2000 + GlobalStatus.random.nextInt(5000);
      }

      this.backgroundStars.setRenderLayer(2);
      var5 = "/data/interface/map_sun_glow.png";
      this.sunGlow = AEFile.loadImage("/data/interface/map_sun_glow.png", true);
      this.galaxyMapGroup = new CameraControllerGroup();
      this.stars = new AbstractMesh[this.systems.length];

      for(int i = 0; i < this.stars.length; ++i) {
         int var6 = this.systems[i].getStarTextureIndex();
         this.stars[i] = AEResourceManager.getGeometryResource(6781);
         this.stars[i].setAnimationRangeInTime(var6, var6);
         this.stars[i].setAnimationMode((byte)1);
         this.stars[i].setRenderLayer(2);
         this.stars[i].setRadius(5000);
         this.stars[i].rotateEuler(0, 2048, 0);
         this.stars[i].setScale(1024, 1024, 1024);
         this.stars[i].moveTo(-8000 + (int)((float)(100 - this.systems[i].getPosX()) / 100.0F * 10000.0F), -7000 + (int)((float)(100 - this.systems[i].getPosY()) / 100.0F * 9000.0F), 2000 + (int)((float)(100 - this.systems[i].getPosZ()) / 100.0F * 2500.0F));
         this.galaxyMapGroup.uniqueAppend_(this.stars[i]);
      }

      if (Status.getCurrentCampaignMission() >= 32 && Status.wormholeSystem >= 0) {
         this.wormhole = AEResourceManager.getGeometryResource(6805);
         this.wormhole.setDraw(true);
         this.wormhole.setAnimationSpeed(30);
         this.wormhole.setScale(512, 512, 512);
         this.wormhole.setAnimationMode((byte)2);
         this.wormhole.moveTo(this.stars[Status.wormholeSystem].getPostition());
         this.galaxyMapGroup.uniqueAppend_(this.wormhole);
      }

      this.galaxyMapGroup.updateTransform(true);
      this.tmpStarScreenPos2 = new AEVector3D();
      this.tmpStarScreenPos1 = new AEVector3D();
      new AEVector3D();
      this.matrix = new Matrix();
      GlobalStatus.random.setSeed(System.currentTimeMillis());
      this.init(var1, var2, var3, var4);
   }

   public final void init(boolean var1, Mission var2, boolean var3, int var4) {
      this.overviewOnly_ = var1;
      this.discoverSystemCutscene = var3;
      this.discoveredSystemId = var4;
      this.curScreenPosX = (float)(GlobalStatus.screenWidth >> 1);
      this.curScreenPosY = (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1));
      this.scrollX = 0.0F;
      this.scrollY = 0.0F;
      this.lastCamera = GlobalStatus.renderer.getCamera();
      if (this.starNetCamera_ == null) {
         this.starNetCamera_ = Camera.create(this.mapInnerWidth, this.mapInnerHeight + 20, 1000, 10, 31768);
         this.starNetCamera_.translate(0, 0, -2500);
         this.starNetCamera_.rotateEuler(0, 2048, 0);
         this.starNetCamera_.moveTo((int)this.scrollX * 20, (int)this.scrollY * 20, 0);
         this.starNetCamera_.updateTransform(true);
      }

      GlobalStatus.renderer.setActiveCamera(this.starNetCamera_);
      this.galaxyMapUnlocked = Status.getCurrentCampaignMission() >= 16;
      this.state = this.galaxyMapUnlocked ? 0 : 3;
      this.selectedSystem = Status.getSystem().getId();
      if (this.state == 3) {
         this.initStarSysMap();
         this.starNetCamera_.setFoV(500);
         this.galaxyMapView = false;
         this.selectedPlanet = 0;

         while(this.selectedSystemStations[this.selectedPlanet].getId() != Status.getStation().getId()) {
            this.handleKeystate(32);
         }

         this.updateCameraTrack();
      } else {
         this.galaxyMapView = true;
         if (var3) {
            this.tmpStarScreenPos2.set(this.stars[var4].getPostition());
         } else {
            this.tmpStarScreenPos2.set(this.stars[Status.getSystem().getId()].getPostition());
         }

         this.starNetCamera_.moveTo(this.tmpStarScreenPos2.x, this.tmpStarScreenPos2.y, 0);
         this.scrollX = (float)(this.tmpStarScreenPos2.x / 20);
         this.scrollY = (float)(this.tmpStarScreenPos2.y / 20);
         this.navigateMap(0.0F, 0.0F);
         this.starNetCamera_.updateTransform(true);
         this.starNetCamera_.getScreenPosition(this.tmpStarScreenPos2);
         this.navigateMap((float)this.tmpStarScreenPos2.x - this.curScreenPosX, (float)this.tmpStarScreenPos2.y - this.curScreenPosY);
         this.scopingOut = false;
      }

      this.legendOpen = false;
      this.fromJumpGate = false;
      this.fromStation = false;
      this.destSelected = false;

      for(int var8 = 0; var8 < this.stars.length; ++var8) {
         this.stars[var8].setDraw(Status.getVisibleSystems()[var8]);
      }

      this.pathToDestination_ = null;
      this.destinationOrMissionSystem_ = -1;
      int var5;
      int var7;
      if (var1) {
         Mission var9 = var2;
         if (!var2.isEmpty() && var2.isVisible() && Status.getSystem().getNeighbourSystems() != null) {
            for(var5 = 0; var5 < this.systems.length; ++var5) {
               if (this.systems[var5].getStations() != null) {
                  for(var7 = 0; var7 < this.systems[var5].getStations().length; ++var7) {
                     if (var9.getTargetStation() == this.systems[var5].getStations()[var7]) {
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

      this.legendWindowWidth = 0;
      short[] var10 = new short[]{223, 224, 271, 279, 278, 132};

      for(var5 = 0; var5 < var10.length; ++var5) {
         if ((var7 = Font.getTextWidth(GlobalStatus.gameText.getText(var10[var5]), 1)) > this.legendWindowWidth) {
            this.legendWindowWidth = var7;
         }
      }

      this.legendWindowWidth += 32;
      this.newSystemAnimTime = 0;
   }

   public final void OnRelease() {
       if (this.backgroundStars != null) {
	         this.backgroundStars.OnRelease();
	         this.backgroundStars = null;
	      }
      this.starNetCamera_ = null;
      this.wormhole = null;
      this.arrow = null;
   }

   public final boolean handleKeystate(int var1) {
      if (var1 == 256) {
         if (this.state == 0 && this.selectedSystem >= 0 && !this.overviewOnly_ && !this.discoverSystemCutscene) {
            if (this.destinationConfirmPopupOpen) {
               this.destinationConfirmPopupOpen = false;
               if (this.jumpToAlienWorldPopupOpen && this.popup.getChoice()) {
                  Level.programmedStation = Status.voidStation;
                  this.destSelected = true;
                  GlobalStatus.renderer.setActiveCamera(this.lastCamera);
               }

               this.jumpToAlienWorldPopupOpen = false;
               return true;
            }

            if (!this.fromStation && !Status.getSystem().inJumpageRange(this.systems[this.selectedSystem].getId())) {
               if (this.popup == null) {
                  this.popup = new Popup();
               }

               this.popup.set(GlobalStatus.gameText.getText(241), false);
               this.destinationConfirmPopupOpen = true;
            } else {
               this.state = 1;
            }
         } else if (this.state == 3) {
            if (this.destinationConfirmPopupOpen) {
               if (this.popup.getChoice()) {
                  if (!this.overviewOnly_) {
                     if (this.fromJumpGate) {
                        Level.programmedStation = this.selectedSystemStations[this.selectedPlanet];
                        this.destSelected = true;
                        GlobalStatus.renderer.setActiveCamera(this.lastCamera);
                        return true;
                     }

                     if (this.fromJumpGate) {
                        Status.departStation(this.selectedSystemStations[this.selectedPlanet]);
                        Level.programmedStation = null;
                        Level.setInitStreamOut();
                        Status.jumpgateUsed();
                     } else if (Status.getCurrentCampaignMission() != 3) {
                        Status.baseArmour = -1;
                        Status.shield = -1;
                        Status.additionalArmour = -1;
                        Status.departStation(Status.getStation());
                        if (!this.selectedSystemStations[this.selectedPlanet].equals(Status.getStation())) {
                           Level.programmedStation = this.selectedSystemStations[this.selectedPlanet];
                        }

                        if (this.fromStation) {
                           Level.driveJumping = true;
                        }

                        Achievements.resetNewMedals();
                     }

                     GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[2]);
                  }

                  return true;
               }

               this.destinationConfirmPopupOpen = false;
               return true;
            }

            if (!this.overviewOnly_ && this.selectedSystemStations[this.selectedPlanet].getId() != Status.getStation().getId()) {
               if (this.popup == null) {
                  this.popup = new Popup();
               }

               this.popup.set(GlobalStatus.gameText.getText(295) + ": " + this.selectedSystemStations[this.selectedPlanet].getName() + "\n" + GlobalStatus.gameText.getText(242), true);
               this.destinationConfirmPopupOpen = true;
            }
         }
      }

      if (var1 == 16384 && !this.destinationConfirmPopupOpen && !this.discoverSystemCutscene) {
         this.legendOpen = !this.legendOpen;
      }

      if (var1 == 8192) {
         if (this.state == 0) {
            GlobalStatus.renderer.setActiveCamera(this.lastCamera);
            return false;
         }

         if (this.state == 3) {
            if (!this.galaxyMapUnlocked) {
               GlobalStatus.renderer.setActiveCamera(this.lastCamera);
               return false;
            }

            this.scopingOut = true;
            this.selectedPlanet = 0;
            this.updateCameraTrack();
         }
      }

      if (this.destinationConfirmPopupOpen) {
         if (var1 == 4) {
            this.popup.left();
         } else if (var1 == 32) {
            this.popup.right();
         }
      } else if (this.state == 3) {
         if (var1 != 64 && var1 != 32) {
            if (var1 == 2 || var1 == 4) {
               --this.selectedPlanet;
               if (this.selectedPlanet < 0) {
                  this.selectedPlanet = this.selectedSystemStations.length - 1;
               }

               this.updateCameraTrack();
            }
         } else {
            ++this.selectedPlanet;
            if (this.selectedPlanet >= this.selectedSystemStations.length) {
               this.selectedPlanet = 0;
            }

            this.updateCameraTrack();
         }
      }

      return true;
   }

   private void updateCameraTrack() {
      int var1 = this.scopingOut ? 0 : this.selectedPlanet + 2;
      int var2 = this.scopingOut ? -6000 : -4000;
      this.tmpStarScreenPos2 = this.localStarAndPlanetsMeshes[var1].getLocalPos(this.tmpStarScreenPos2);
      this.smoothCamTransitionX.SetRange(this.starNetCamera_.getPosX(), this.tmpStarScreenPos2.x);
      this.smoothCamTransitionY.SetRange(this.starNetCamera_.getPosY(), this.tmpStarScreenPos2.y);
      this.smoothCamTransitionZ.SetRange(this.starNetCamera_.getPosZ(), this.tmpStarScreenPos2.z + var2);
   }

   public final void update(int var1, int var2) {
      if (this.discoverSystemCutscene) {
         var1 = 0;
         this.newSystemAnimTime += var2;
      }

      this.frameTime = var2;
      if (this.state == 0) {
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

         this.navigateMap(var8, var3);
         this.curSqrSize = -1.0F;

         for(var1 = 0; var1 < this.systems.length; ++var1) {
            if (Status.getVisibleSystems()[var1]) {
               this.starNetCamera_.getScreenPosition(this.stars[var1].getLocalPos(this.tmpStarScreenPos2));
               if (this.tmpStarScreenPos2.z < 0 && this.curScreenPosX > (float)(this.tmpStarScreenPos2.x - 20) && this.curScreenPosX < (float)(this.tmpStarScreenPos2.x + 20) && this.curScreenPosY > (float)(this.tmpStarScreenPos2.y - 20) && this.curScreenPosY < (float)(this.tmpStarScreenPos2.y + 20)) {
                  float var9 = ((float)this.tmpStarScreenPos2.x - this.curScreenPosX) / 4.0F;
                  float var4 = ((float)this.tmpStarScreenPos2.y - this.curScreenPosY) / 4.0F;
                  float var5 = (var9 < 0.0F ? -var9 : var9) * 4.0F;
                  float var6 = (var4 < 0.0F ? -var4 : var4) * 4.0F;
                  this.curSqrSize = var5 > var6 ? var5 : var6;
                  this.curSqrSize = 10.0F - this.curSqrSize;
                  if ((float)this.tmpStarScreenPos2.x < this.curScreenPosX) {
                     var8 = var9;
                  } else if ((float)this.tmpStarScreenPos2.x > this.curScreenPosX) {
                     var8 = var9;
                  }

                  if ((float)this.tmpStarScreenPos2.y > this.curScreenPosY) {
                     var3 = var4;
                  } else if ((float)this.tmpStarScreenPos2.y < this.curScreenPosY) {
                     var3 = var4;
                  }

                  if (AEMath.abs((int)var8) > AEMath.abs((int)((float)this.tmpStarScreenPos2.x - this.curScreenPosX))) {
                     var8 *= 0.5F;
                  }

                  if (AEMath.abs((int)var3) > AEMath.abs((int)((float)this.tmpStarScreenPos2.y - this.curScreenPosY))) {
                     var3 *= 0.5F;
                  }

                  if (!this.curPushLeft && !this.curPushRight && !this.curPushUp && !this.curPushDown) {
                     this.navigateMap(var8, var3);
                  }
                  break;
               }
            }
         }
      } else if (this.state == 1) {
         this.tmpStarScreenPos2 = this.starNetCamera_.getLocalPos(this.tmpStarScreenPos2);
         if (this.tmpStarScreenPos2.z < this.stars[this.selectedSystem].getLocalPosZ() - 150) {
            this.tmpStarScreenPos2.subtract(this.stars[this.selectedSystem].getLocalPos());
            this.tmpStarScreenPos2.scale(-1024);
            this.starNetCamera_.translate(this.tmpStarScreenPos2);
         } else {
            if (this.galaxyMapView) {
               this.initStarSysMap();
               this.starNetCamera_.setFoV(500);
            } else {
               for(var2 = 0; var2 < this.localStarAndPlanetsMeshes.length; ++var2) {
                  this.localStarAndPlanetsMeshes[var2] = null;
               }

               this.localStarAndPlanetsMeshes = null;
               if (this.wormhole != null && this.localSystem != null) {
                  this.localSystem.removeNode(this.wormhole);
               }

               this.localSystem = null;

               for(var2 = 0; var2 < this.localOrbits.length; ++var2) {
                  this.localOrbits[var2] = null;
               }

               this.localOrbits = null;
               this.starNetCamera_.setFoV(1000);
               this.starNetCamera_.setRotation(0, 2048, 0);
               this.starNetCamera_.moveTo((int)this.scrollX * 20, (int)this.scrollY * 20, this.starNetCamera_.getPosZ());
               if (this.wormhole != null && this.galaxyMapGroup != null) {
                  this.galaxyMapGroup.uniqueAppend_(this.wormhole);
                  this.wormhole.getToParentTransform().identity();
                  this.wormhole.setScale(512, 512, 512);
                  this.wormhole.moveTo(this.stars[Status.wormholeSystem].getPostition());
               }
            }

            this.state = 2;
         }
      } else if (this.state == 2) {
         this.tmpStarScreenPos2 = this.starNetCamera_.getPosition(this.tmpStarScreenPos2);
         if (this.galaxyMapView) {
            if (this.tmpStarScreenPos2.z > this.stars[this.selectedSystem].getPosZ() - 6000) {
               this.tmpStarScreenPos1.set(this.stars[this.selectedSystem].getPosition(this.tmpStarScreenPos1));
               AEVector3D var10000 = this.tmpStarScreenPos1;
               var10000.z -= 6000;
               this.tmpStarScreenPos2.subtract(this.tmpStarScreenPos1);
               this.tmpStarScreenPos2.scale(-1024);
               this.starNetCamera_.translate(this.tmpStarScreenPos2);
            } else {
               this.galaxyMapView = false;
               this.state = 3;
               this.updateCameraTrack();
            }
         } else if (this.tmpStarScreenPos2.z > 0) {
            this.tmpStarScreenPos1.set(this.stars[this.selectedSystem].getPosition(this.tmpStarScreenPos1));
            this.tmpStarScreenPos1.z = 0;
            this.tmpStarScreenPos2.subtract(this.tmpStarScreenPos1);
            this.tmpStarScreenPos2.scale(-1024);
            this.starNetCamera_.translate(0, 0, this.tmpStarScreenPos2.z);
         } else {
            this.galaxyMapView = true;
            this.state = 0;
         }
      } else if (this.state == 3) {

         this.smoothCamTransitionX.Increase(var2 << 1);
         this.smoothCamTransitionY.Increase(var2 << 1);
         this.smoothCamTransitionZ.Increase(var2 << 1);
         this.starNetCamera_.moveTo(this.smoothCamTransitionX.GetValue(), this.smoothCamTransitionY.GetValue(), this.smoothCamTransitionZ.GetValue());
         if (this.scopingOut && this.smoothCamTransitionX.IsAtMaxPhase(true) && this.smoothCamTransitionY.IsAtMaxPhase(true) && this.smoothCamTransitionZ.IsAtMaxPhase(true)) {
            this.state = 1;
            this.scopingOut = false;
         }
      }

      int var11;
      if (this.localSystem != null) {
         for(var2 = 2; var2 < this.localStarAndPlanetsMeshes.length; ++var2) {
            this.matrix.identity();
            var11 = this.planetRevolutAngs[var2 - 2];
            this.matrix.addEulerAngles(0, var11, 0);
            this.tmpStarScreenPos2.set(0, 0, this.distsToStar[var2 - 2]);
            this.tmpStarScreenPos1.set(0, 0, 0);
            this.tmpStarScreenPos1 = this.matrix.transformVector(this.tmpStarScreenPos2, this.tmpStarScreenPos1);
            this.localStarAndPlanetsMeshes[var2].moveTo(this.tmpStarScreenPos1);
            this.tmpStarScreenPos2.set(this.localStarAndPlanetsMeshes[var2].getPosition(this.tmpStarScreenPos1));
            this.tmpStarScreenPos2.normalize();
         }
      }

      this.draw();
      StarMap var10 = this;

      try {
         GlobalStatus.graphics3D.bindTarget(GlobalStatus.graphics);
         GlobalStatus.renderer.drawNodeInVF(this.backgroundStars);
         if (var10.localSystem != null) {
            GlobalStatus.renderer.drawNodeInVF(var10.localSystem);
         } else {
            var10.newSysAnimTimer += var10.frameTime;
            var2 = 0;

            while(true) {
               if (var2 >= var10.stars.length) {
                  GlobalStatus.renderer.drawNodeInVF(var10.galaxyMapGroup);
                  break;
               }

               boolean var12 = false;
               if (var2 == var10.selectedSystem && var10.discoverSystemCutscene && var10.newSystemAnimTime < 4000) {
                  var11 = (int)((float)var10.newSystemAnimTime / 4000.0F * (float)(1024 + (AEMath.sin(var10.newSysAnimTimer + (var2 << 8)) >> 5)));
               } else {
                  var11 = 1024 + (AEMath.sin(var10.newSysAnimTimer + (var2 << 8)) >> 5);
               }

               var10.stars[var2].setScale(var11, var11, var11);
               ++var2;
            }
         }

         GlobalStatus.renderer.renderFrame(System.currentTimeMillis());
         GlobalStatus.graphics3D.clear();
         GlobalStatus.graphics3D.releaseTarget();
      } catch (Exception var7) {
         GlobalStatus.graphics3D.releaseTarget();
         var7.printStackTrace();
      }

      this.drawKey();
   }

   private void initStarSysMap() {
      int var1 = this.systems[this.selectedSystem].getStations().length;
      this.selectedSystemStations = new Station[var1];
      new FileRead();
      this.selectedSystemStations = FileRead.loadStationsBinary(this.systems[this.selectedSystem]);
      this.planetRevolutAngs = new int[var1];
      this.distsToStar = new int[var1];
      GlobalStatus.random.setSeed((long)(this.systems[this.selectedSystem].getId() * 1000));
      this.localStarAndPlanetsMeshes = new AbstractMesh[var1 + 2];
      this.localSystem = new Group();

      int var2;
      for(var2 = 0; var2 < this.occupiedOrbits_.length; ++var2) {
         this.occupiedOrbits_[var2] = false;
      }

      int var3;
      for(var2 = 0; var2 < this.localStarAndPlanetsMeshes.length; ++var2) {
         if (var2 > 1) {
            this.localStarAndPlanetsMeshes[var2] = AEResourceManager.getGeometryResource(6778);
         } else {
            var3 = this.systems[this.selectedSystem].getStarTextureIndex();
            this.localStarAndPlanetsMeshes[var2] = AEResourceManager.getGeometryResource(6781);
            this.localStarAndPlanetsMeshes[var2].setAnimationRangeInTime(var3, var3);
         }

         this.localStarAndPlanetsMeshes[var2].setAnimationMode((byte)1);
         this.localStarAndPlanetsMeshes[var2].setRadius(5000);
         if (var2 > 1) {
            Matrix var7 = new Matrix();
            int var4 = 0;
            boolean var5 = false;

            int var6;
            while(!var5) {
               var6 = GlobalStatus.random.nextInt(this.occupiedOrbits_.length);
               if (!this.occupiedOrbits_[var6]) {
                  this.occupiedOrbits_[var6] = true;
                  var4 = 4096 / this.occupiedOrbits_.length * var6;
                  var5 = true;
               }
            }

            this.planetRevolutAngs[var2 - 2] = var4;
            var7.addEulerAngles(0, var4, 0);
            var6 = var2 == 2 ? 512 : this.distsToStar[var2 - 3];
            this.distsToStar[var2 - 2] = var6 + 128 + GlobalStatus.random.nextInt(376);
            AEVector3D var9 = new AEVector3D(0, 0, this.distsToStar[var2 - 2]);
            AEVector3D var10 = new AEVector3D();
            var10 = var7.transformVector(var9, var10);
            this.localStarAndPlanetsMeshes[var2].translate(var10);
            var3 = this.selectedSystemStations[var2 - 2].getPlanetTextureId();
            var4 = this.planetSizes[var3];
            this.localStarAndPlanetsMeshes[var2].setScale(var4, var4, var4);
            (new AEVector3D(this.localStarAndPlanetsMeshes[var2].getPostition())).normalize();
            this.localStarAndPlanetsMeshes[var2].setAnimationRangeInTime(var3, var3);
         } else {
            this.localStarAndPlanetsMeshes[var2].translate(0, 0, var2 * 32);
            this.localStarAndPlanetsMeshes[var2].rotateEuler(0, 2048, 256);
            this.localStarAndPlanetsMeshes[var2].setScale(256, 256, 256);
         }

         this.localStarAndPlanetsMeshes[var2].setRenderLayer(2);
         this.localSystem.uniqueAppend_(this.localStarAndPlanetsMeshes[var2]);
      }

      this.localStarAndPlanetsMeshes[1].setDraw(false);
      this.localStarAndPlanetsMeshes[0].setDraw(false);
      this.localOrbits = new AbstractMesh[var1];

      for(var2 = 0; var2 < this.localOrbits.length; ++var2) {
         this.localOrbits[var2] = AEResourceManager.getGeometryResource(6779);
         this.localOrbits[var2].setRenderLayer(2);
         this.localOrbits[var2].rotateEuler(-1024, 0, 0);
         this.localSystem.uniqueAppend_(this.localOrbits[var2]);
         var3 = this.distsToStar[var2];
         this.localOrbits[var2].setScale(var3 << 1, var3 << 1, var3 << 1);
      }

      this.localSystem.moveTo(this.starNetCamera_.getPosition(this.tmpStarScreenPos2));
      this.localSystem.setRotation(-256, 0, 256);
      this.smoothCamTransitionX = new EaseInOut(0, 0);
      this.smoothCamTransitionY = new EaseInOut(0, 0);
      this.smoothCamTransitionZ = new EaseInOut(0, 0);
      this.localSystem.updateTransform(true);
      Image var8 = AEFile.loadImage("/data/interface/sun_" + this.systems[this.selectedSystem].getStarTextureIndex() + ".png", true);
      this.systemStar = new Sprite(var8);
      this.systemStar.defineReferencePixel(this.systemStar.getWidth(), this.systemStar.getHeight());
      if (this.wormhole != null && this.selectedSystem == Status.wormholeSystem) {
         if (this.galaxyMapGroup != null) {
            this.galaxyMapGroup.removeNode(this.wormhole);
         }

         this.wormhole.setScale(256, 256, 256);
         this.wormhole.moveTo(this.localStarAndPlanetsMeshes[this.systems[this.selectedSystem].getStationEnumIndex(Status.wormholeStation) + 2].getPostition());
         this.localSystem.uniqueAppend_(this.wormhole);
      }

      if (this.arrow == null) {
         this.arrow = AEResourceManager.getGeometryResource(13999);
      }

      if (this.systems[this.selectedSystem].getId() == Status.getSystem().getId()) {
         this.arrow.setTransform(this.localStarAndPlanetsMeshes[this.systems[this.selectedSystem].getStationEnumIndex(Status.getStation().getId()) + 2].getToParentTransform());
         this.arrow.setRotation(512, 0, -1024);
         this.localSystem.uniqueAppend_(this.arrow);
      }

      GlobalStatus.random.setSeed(System.currentTimeMillis());
   }

   private void navigateMap(float var1, float var2) {
      if (!this.destinationConfirmPopupOpen) {
         if (this.curScreenPosX >= (float)(GlobalStatus.screenWidth >> 1) && var1 > 0.0F || this.curScreenPosX <= (float)(GlobalStatus.screenWidth >> 1) && var1 < 0.0F) {
            this.scrollX -= var1;
         }

         if (this.curScreenPosY >= (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1)) && var2 > 0.0F || this.curScreenPosY <= (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1)) && var2 < 0.0F) {
            this.scrollY -= var2;
         }

         if (this.scrollX >= 0.0F) {
            this.scrollX = 0.0F;
            this.curScreenPosX += var1;
            if (this.curScreenPosX > (float)(GlobalStatus.screenWidth >> 1)) {
               this.curScreenPosX = (float)(GlobalStatus.screenWidth >> 1);
            }

            if (this.curScreenPosX <= (float)(this.windowFrameWidth + 5)) {
               this.curScreenPosX = (float)(this.windowFrameWidth + 5);
            }
         }

         if (this.scrollY >= 0.0F) {
            this.scrollY = 0.0F;
            this.curScreenPosY += var2;
            if (this.curScreenPosY > (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1))) {
               this.curScreenPosY = (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1));
            }

            if (this.curScreenPosY <= (float)(this.windowFrameHeight + 5)) {
               this.curScreenPosY = (float)(this.windowFrameHeight + 5);
            }
         }

         if (this.scrollX <= (float)(-this.backGroundTileX + this.mapInnerWidth)) {
            this.scrollX = (float)(-this.backGroundTileX + this.mapInnerWidth);
            this.curScreenPosX += var1;
            if (this.curScreenPosX < (float)(GlobalStatus.screenWidth >> 1)) {
               this.curScreenPosX = (float)(GlobalStatus.screenWidth >> 1);
            }

            if (this.curScreenPosX >= (float)(this.windowFrameWidth + this.mapInnerWidth - 5)) {
               this.curScreenPosX = (float)(this.windowFrameWidth + this.mapInnerWidth - 5);
            }
         }

         if (this.scrollY <= (float)(-this.backGroundTileY + this.mapInnerHeight)) {
            this.scrollY = (float)(-this.backGroundTileY + this.mapInnerHeight);
            this.curScreenPosY += var2;
            if (this.curScreenPosY < (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1))) {
               this.curScreenPosY = (float)(this.windowFrameHeight + (this.mapInnerHeight >> 1));
            }

            if (this.curScreenPosY >= (float)(this.windowFrameHeight + this.mapInnerHeight - 5)) {
               this.curScreenPosY = (float)(this.windowFrameHeight + this.mapInnerHeight - 5);
            }
         }

         this.starNetCamera_.moveTo((int)this.scrollX * 20, (int)this.scrollY * 20, 0);
      }
   }

   public final boolean scopedOnSystem() {
      return this.state == 3;
   }

   public final void setJumpMapMode(boolean var1, boolean var2) {
      this.fromJumpGate = var1;
      this.fromStation = var2;
   }

   private void drawKey() {
	  if (localStarAndPlanetsMeshes != null && localStarAndPlanetsMeshes[0] != null)
	      GlobalStatus.renderer.setLight(localStarAndPlanetsMeshes[0].getLocalTransform(),  AEGraphics3D.omni);
      int var1;
      int var3;
      switch(this.state) {
      case 0:
         GlobalStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);
         this.starNetCamera_.getScreenPosition(this.stars[Status.getSystem().getId()].getLocalPos(this.tmpStarScreenPos1));
         var1 = this.selectedSystem;
         this.selectedSystem = -1;
         int[] var2 = Status.getSystem().getNeighbourSystems();

         float var6;
         float var7;
         float var12;
         for(var3 = 0; var3 < this.systems.length; ++var3) {
            if (Status.getVisibleSystems()[var3]) {
               this.starNetCamera_.getScreenPosition(this.stars[var3].getLocalPos(this.tmpStarScreenPos2));
               if (this.tmpStarScreenPos2.z < 0) {
                  if (this.curScreenPosX > (float)(this.tmpStarScreenPos2.x - 10) && this.curScreenPosX < (float)(this.tmpStarScreenPos2.x + 10) && this.curScreenPosY > (float)(this.tmpStarScreenPos2.y - 10) && this.curScreenPosY < (float)(this.tmpStarScreenPos2.y + 10)) {
                     this.selectedSystem = var3;
                  }

                  if (var2 != null && !this.fromStation) {
                     boolean var4 = false;

                     for(int var5 = 0; var5 < var2.length; ++var5) {
                        if (var2[var5] == var3) {
                           var4 = true;
                           break;
                        }
                     }

                     if (var4) {
                        var12 = (float)(this.tmpStarScreenPos2.x - this.tmpStarScreenPos1.x) / 10.0F;
                        var6 = (float)(this.tmpStarScreenPos2.y - this.tmpStarScreenPos1.y) / 10.0F;
                        var7 = var12 * 2.0F;
                        float var8 = var6 * 2.0F;

                        for(int var10 = 0; var10 < 8; ++var10) {
                           GlobalStatus.graphics.setColor(var10 == this.highlightedPathDot ? -4138775 : -12950906);
                           GlobalStatus.graphics.fillArc((int)((float)this.tmpStarScreenPos1.x + var7) - 2, (int)((float)this.tmpStarScreenPos1.y + var8) - 2, 4, 4, 0, 360);
                           var7 += var12;
                           var8 += var6;
                        }
                     }
                  }
               }

               if (var1 < 0) {
                  this.drawOnScreenInfo(var3, false);
               }
            }
         }

         if (this.pathToDestination_ != null) {
            for(var3 = 0; var3 < this.pathToDestination_.length - 1; ++var3) {
               this.starNetCamera_.getScreenPosition(this.stars[this.pathToDestination_[var3]].getLocalPos(this.tmpStarScreenPos1));
               this.starNetCamera_.getScreenPosition(this.stars[this.pathToDestination_[var3 + 1]].getLocalPos(this.tmpStarScreenPos2));
               float var11 = (float)(this.tmpStarScreenPos2.x - this.tmpStarScreenPos1.x) / 10.0F;
               var12 = (float)(this.tmpStarScreenPos2.y - this.tmpStarScreenPos1.y) / 10.0F;
               var6 = var11 * 2.0F;
               var7 = var12 * 2.0F;

               for(int var13 = 0; var13 < 8; ++var13) {
                  GlobalStatus.graphics.setColor((var3 << 3) + var13 == this.highlightedPathDot ? -1 : -4740812);
                  GlobalStatus.graphics.fillArc((int)((float)this.tmpStarScreenPos1.x + var6) - 2, (int)((float)this.tmpStarScreenPos1.y + var7) - 2, 4, 4, 0, 360);
                  var6 += var11;
                  var7 += var12;
               }
            }
         }

         if (this.selectedSystem >= 0 && (!this.discoverSystemCutscene || this.newSystemAnimTime >= 4000)) {
            this.drawOnScreenInfo(this.selectedSystem, false);
         }

         if (!this.fromStation) {
            this.pathDisplayDelay += this.frameTime;
            if (this.pathDisplayDelay > 400) {
               ++this.highlightedPathDot;
               if (this.highlightedPathDot > (this.pathToDestination_ == null ? 20 : this.pathToDestination_.length << 3)) {
                  this.highlightedPathDot = 0;
               }
            }
         }
         this.raceLogoBig = null; 
         break;
      case 3:
	  if (raceLogoBig == null) {
         this.raceLogoBig = AEFile.loadImage("/data/interface/logo_" + this.systems[this.selectedSystem].getRace() + ".png", true);
	  }
	  GlobalStatus.graphics.drawImage(this.raceLogoBig, 5, 18, 20);

         for(var3 = 0; var3 < this.selectedSystemStations.length; ++var3) {
            if (var3 != this.selectedPlanet) {
               this.drawOnScreenInfo(var3, true);
            }
         }

         this.drawOnScreenInfo(this.selectedPlanet, true);
         break;
      default:
         Layout.drawFooter("", "");
      }

      if (this.localSystem == null && !this.discoverSystemCutscene) {
         if ((var1 = (int)(this.curSqrSize * 1.5F)) > 0) {
            GlobalStatus.graphics.setColor(-8086094);
            GlobalStatus.graphics.drawLine((int)this.curScreenPosX, (int)this.curScreenPosY + var1, (int)this.curScreenPosX - var1, (int)this.curScreenPosY);
            GlobalStatus.graphics.drawLine((int)this.curScreenPosX - var1, (int)this.curScreenPosY, (int)this.curScreenPosX, (int)this.curScreenPosY - var1);
            GlobalStatus.graphics.drawLine((int)this.curScreenPosX, (int)this.curScreenPosY - var1, (int)this.curScreenPosX + var1, (int)this.curScreenPosY);
            GlobalStatus.graphics.drawLine((int)this.curScreenPosX + var1, (int)this.curScreenPosY, (int)this.curScreenPosX, (int)this.curScreenPosY + var1);
         }

         this.cursorLine.setTransform(0);
         this.cursorLine.setRefPixelPosition((int)this.curScreenPosX, (int)this.curScreenPosY + var1);
         this.cursorLine.paint(GlobalStatus.graphics);
         this.cursorLine.setTransform(3);
         this.cursorLine.setRefPixelPosition((int)this.curScreenPosX, (int)this.curScreenPosY - var1);
         this.cursorLine.paint(GlobalStatus.graphics);
         this.cursorLine.setTransform(5);
         this.cursorLine.setRefPixelPosition((int)this.curScreenPosX - var1, (int)this.curScreenPosY);
         this.cursorLine.paint(GlobalStatus.graphics);
         this.cursorLine.setTransform(6);
         this.cursorLine.setRefPixelPosition((int)this.curScreenPosX + var1, (int)this.curScreenPosY);
         this.cursorLine.paint(GlobalStatus.graphics);
      }

      if (this.state == 3) {
         Layout.drawNonFullScreenWindow(GlobalStatus.gameText.getText(72) + ": " + this.systems[this.selectedSystem].getName() + " " + GlobalStatus.gameText.getText(41), false);
         Layout.drawFooter(GlobalStatus.gameText.getText(223), GlobalStatus.gameText.getText(65));
         Font.drawString(!this.overviewOnly_ && Status.getSystem().inJumpageRange(this.systems[this.selectedSystem].getId()) && this.selectedSystemStations[this.selectedPlanet].getId() != Status.getStation().getId() ? GlobalStatus.gameText.getText(222) : "", GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight - 4, 1, 40);
      } else {
         Layout.drawNonFullScreenWindow(GlobalStatus.gameText.getText(72), false);
         if (this.discoverSystemCutscene) {
            Layout.drawFooter("", GlobalStatus.gameText.getText(65));
         } else {
            Layout.drawFooter(GlobalStatus.gameText.getText(223), GlobalStatus.gameText.getText(65));
            Font.drawString(this.overviewOnly_ ? "" : GlobalStatus.gameText.getText(221), GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight - 4, 1, 40);
         }
      }

      if (this.legendOpen && (this.state == 0 || this.state == 3)) {
         Layout.drawFilledTitleBarWindow(GlobalStatus.gameText.getText(223), 1, GlobalStatus.screenHeight - 16 - 90 - 4, this.legendWindowWidth, 94);
         int var9 = GlobalStatus.screenHeight - 3 - 16 - 13;
         GlobalStatus.graphics.drawImage(this.bluePrintIcon, 10, var9, 20);
         Font.drawString(GlobalStatus.gameText.getText(132), 25, var9, 1, 17);
         var9 -= 15;
         GlobalStatus.graphics.drawImage(this.checkMark, 10, var9, 20);
         Font.drawString(GlobalStatus.gameText.getText(224), 25, var9, 1, 17);
         var9 -= 15;
         GlobalStatus.graphics.drawImage(this.jumpGateIcon, 10, var9, 20);
         Font.drawString(GlobalStatus.gameText.getText(271), 25, var9, 1, 17);
         var9 -= 15;
         GlobalStatus.graphics.drawImage(this.sideMissionIcon, 10, var9, 20);
         Font.drawString(GlobalStatus.gameText.getText(279), 25, var9, 1, 17);
         var9 -= 15;
         GlobalStatus.graphics.drawImage(this.mainMissionIcon, 10, var9, 20);
         Font.drawString(GlobalStatus.gameText.getText(278), 25, var9, 1, 17);
      }

      if (this.destinationConfirmPopupOpen) {
         this.popup.draw();
      }

   }

   private void drawOnScreenInfo(int var1, boolean var2) {
      int var3;
      for(var3 = 0; var3 < this.legendItemIcons.length; ++var3) {
         this.legendItemIcons[var3] = null;
      }

      if (var2 && this.selectedSystemStations[var1].isDiscovered() || !var2 && this.systems[var1].isFullyDiscovered()) {
         this.legendItemIcons[0] = this.checkMark;
      }

      Mission var7 = Status.getCampaignMission();
      Mission var4 = Status.getFreelanceMission();
      if (var7 != null && !var7.isEmpty() && (var2 && this.selectedSystemStations[var1].getId() == var7.getTargetStation() || !var2 && this.systems[var1].getStationEnumIndex(var7.getTargetStation()) >= 0 || Status.getCurrentCampaignMission() > 32 && (var2 && this.selectedSystemStations[var1].getId() == Status.wormholeStation && var7.getTargetStation() == -1 || !var2 && this.systems[var1].getStationEnumIndex(Status.wormholeStation) >= 0 && var7.getTargetStation() == -1))) {
         this.legendItemIcons[1] = this.mainMissionIcon;
      }

      if (var4 != null && !var4.isEmpty() && (var2 && this.selectedSystemStations[var1].getId() == var4.getTargetStation() || !var2 && this.systems[var1].getStationEnumIndex(var4.getTargetStation()) >= 0)) {
         this.legendItemIcons[2] = this.sideMissionIcon;
      }

      if (var2 && this.systems[this.selectedSystem].getJumpgateStationIndex() == this.selectedSystemStations[var1].getId()) {
         this.legendItemIcons[3] = this.jumpGateIcon;
      }

      ProducedGood[] var8;
      int var9;
      if ((var8 = Status.getWaitingGoods()) != null) {
         for(var9 = 0; var9 < var8.length; ++var9) {
            if (var8[var9] != null && (var2 && var8[var9].stationId == this.selectedSystemStations[var1].getId() || !var2 && this.systems[var1].getStationEnumIndex(var8[var9].stationId) >= 0)) {
               this.legendItemIcons[4] = this.bluePrintIcon;
               break;
            }
         }
      }

      if (var2) {
         this.starNetCamera_.getScreenPosition(this.localStarAndPlanetsMeshes[var1 + 2].getLocalPos(this.tmpStarScreenPos2));
      } else {
         this.starNetCamera_.getScreenPosition(this.stars[var1].getLocalPos(this.tmpStarScreenPos2));
      }

      boolean var11 = false;
      boolean var10 = false;
      if (var2) {
         var9 = this.tmpStarScreenPos2.y - 4;
         var3 = this.tmpStarScreenPos2.x + 10 + Font.getTextWidth(this.selectedSystemStations[var1].getName(), 0) + 7;
         Font.drawString(this.selectedSystemStations[var1].getName(), this.tmpStarScreenPos2.x + 10, var9, this.selectedPlanet == var1 ? 2 : (this.selectedSystemStations[var1].isDiscovered() ? 0 : 1), 17);
         if (var1 == this.selectedPlanet) {
            Font.drawString(GlobalStatus.gameText.getText(37) + ": " + this.selectedSystemStations[var1].getTecLevel(), this.tmpStarScreenPos2.x + 10, var9 + Font.getFontSpacingY(), this.selectedSystemStations[var1].isDiscovered() ? 0 : 1);
         }
      } else {
         var9 = this.tmpStarScreenPos2.y - Font.getFontSpacingY() - 4;
         byte var5 = 10;
         if (this.selectedSystem == var1) {
            this.logos.setFrame(this.systems[var1].getRace());
            this.logos.setPosition(this.tmpStarScreenPos2.x + 10, var9 - 4);
            this.logos.paint(GlobalStatus.graphics);
            var5 = 27;
         }

         var3 = this.tmpStarScreenPos2.x + var5 + Font.getTextWidth(this.systems[var1].getName(), 0) + 7;
         boolean var6 = true;
         if (this.overviewOnly_ && !var2 && this.destinationOrMissionSystem_ == this.systems[var1].getId()) {
            var6 = Layout.quickClockHigh_();
         }

         if (var6) {
            Font.drawString(this.systems[var1].getName(), this.tmpStarScreenPos2.x + var5, var9 - 2, this.selectedSystem != var1 && this.selectedSystem != Status.getSystem().getId() ? 1 : 2, 17);
         }

         if (var1 == this.selectedSystem) {
            Font.drawString(GlobalStatus.gameText.getText(219) + ": ", this.tmpStarScreenPos2.x + 10, var9 + 2 * Font.getFontSpacingY(), 0);
            Font.drawString(GlobalStatus.gameText.getText(229 + this.systems[this.selectedSystem].getRace()), this.tmpStarScreenPos2.x + 10, var9 + 3 * Font.getFontSpacingY(), 1);
            Font.drawString(GlobalStatus.gameText.getText(220) + ": ", this.tmpStarScreenPos2.x + 10, var9 + 4 * Font.getFontSpacingY(), 0);
            Font.drawString(GlobalStatus.gameText.getText(225 + this.systems[this.selectedSystem].getSafety()), this.tmpStarScreenPos2.x + 10, var9 + 5 * Font.getFontSpacingY(), 1);
         }
      }

      for(int var12 = 0; var12 < this.legendItemIcons.length; ++var12) {
         if (this.legendItemIcons[var12] != null) {
            GlobalStatus.graphics.drawImage(this.legendItemIcons[var12], var3, var9 - 1, 20);
            var3 += this.legendItemIcons[var12].getWidth() + 2;
         }
      }

   }

   private void draw() {
      GlobalStatus.graphics.setColor(0);
      GlobalStatus.graphics.fillRect(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
      if (this.state == 3) {
         Layout.drawWindowFrame(GlobalStatus.gameText.getText(72) + ": " + this.systems[this.selectedSystem].getName() + " " + GlobalStatus.gameText.getText(41));
      } else {
         Layout.drawWindowFrame(GlobalStatus.gameText.getText(72));
      }

      int var3;
      if (this.localSystem != null) {
         SolarSystem var4 = this.systems[this.selectedSystem];
         GlobalStatus.graphics.setColor(var4.starR, var4.starG, var4.starB);
         GlobalStatus.graphics.fillRect(this.windowFrameWidth, this.windowFrameHeight, this.mapInnerWidth, this.mapInnerHeight);
         this.tmpStarScreenPos2 = this.localStarAndPlanetsMeshes[0].getLocalPos(this.tmpStarScreenPos2);
         this.starNetCamera_.getScreenPosition(this.tmpStarScreenPos2);
         int var5 = this.tmpStarScreenPos2.x;
         var3 = this.tmpStarScreenPos2.y;
         this.systemStar.setTransform(0);
         this.systemStar.setRefPixelPosition(var5, var3);
         this.systemStar.paint(GlobalStatus.graphics);
         this.systemStar.setTransform(5);
         this.systemStar.setRefPixelPosition(var5 - 1, var3);
         this.systemStar.paint(GlobalStatus.graphics);
         this.systemStar.setTransform(3);
         this.systemStar.setRefPixelPosition(var5 - 1, var3 - 1);
         this.systemStar.paint(GlobalStatus.graphics);
         this.systemStar.setTransform(6);
         this.systemStar.setRefPixelPosition(var5, var3 - 1);
         this.systemStar.paint(GlobalStatus.graphics);
      } else {
         GlobalStatus.graphics.drawImage(this.fog, (int)((float)this.windowFrameWidth + this.scrollX), (int)((float)this.windowFrameHeight + this.scrollY), 0);
         GlobalStatus.graphics.drawImage(this.fog, (int)((float)this.windowFrameWidth + this.scrollX) + (this.backGroundTileX >> 1), (int)((float)this.windowFrameHeight + this.scrollY), 0);
         GlobalStatus.graphics.drawImage(this.fog, (int)((float)this.windowFrameWidth + this.scrollX), (int)((float)this.windowFrameHeight + this.scrollY) + (this.backGroundTileY >> 1), 0);
         GlobalStatus.graphics.drawImage(this.fog, (int)((float)this.windowFrameWidth + this.scrollX) + (this.backGroundTileX >> 1), (int)((float)this.windowFrameHeight + this.scrollY) + (this.backGroundTileY >> 1), 0);

         for(int var1 = 0; var1 < this.stars.length; ++var1) {
            if (this.stars[var1].isVisible() && (!this.discoverSystemCutscene || this.discoveredSystemId != var1 || this.newSystemAnimTime >= 4000)) {
               int[] var2;
               if ((var2 = this.systems[var1].getNeighbourSystems()) != null) {
                  this.starNetCamera_.getScreenPosition(this.stars[var1].getLocalPos(this.tmpStarScreenPos2));
                  GlobalStatus.graphics.setColor(Layout.uiOuterTopRightOutlineColor);

                  for(var3 = 0; var3 < var2.length; ++var3) {
                     if (this.stars[var2[var3]].isVisible() && (!this.discoverSystemCutscene || this.discoveredSystemId != var3 || this.newSystemAnimTime >= 4000)) {
                        this.starNetCamera_.getScreenPosition(this.stars[var2[var3]].getLocalPos(this.tmpStarScreenPos1));
                        GlobalStatus.graphics.drawLine(this.tmpStarScreenPos2.x, this.tmpStarScreenPos2.y, this.tmpStarScreenPos1.x, this.tmpStarScreenPos1.y);
                     }
                  }
               }

               GlobalStatus.renderer.getCamera().getScreenPosition(this.stars[var1].getLocalPos(this.tmpStarScreenPos2));
               if (this.tmpStarScreenPos2.z < 0) {
                  GlobalStatus.graphics.drawImage(this.sunGlow, this.tmpStarScreenPos2.x, this.tmpStarScreenPos2.y, 3);
               }
            }
         }

      }
   }

   public final void askForJumpIntoAlienWorld() {
      this.jumpToAlienWorldPopupOpen = true;
      if (this.popup == null) {
         this.popup = new Popup();
      }

      this.popup.set(GlobalStatus.gameText.getText(243), true);
      this.destinationConfirmPopupOpen = true;
   }
}
