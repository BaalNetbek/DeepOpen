package GoF2;

import AE.AEResourceManager;
import AE.AbstractMesh;
import AE.Camera;
import AE.GlobalStatus;
import AE.LookAtCamera;
import AE.TargetFollowCamera;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public final class LevelScript {
   private Level level;
   private int step;
   private boolean cinematicBreak_;
   private boolean active;
   private boolean startSequenceIsOver;
   private LookAtCamera lookAtCam;
   private int passedTime;
   private AEVector3D tempVec = new AEVector3D();
   private AEVector3D approachStationCamPos = new AEVector3D();
   private AEVector3D tempVec2 = new AEVector3D();
   public boolean unused_428;
   private Hud hud;
   private Radar radar;
   private AbstractMesh probe;
   public int timeLimit;
   public long timePassed;

   public LevelScript(TargetFollowCamera var1, LookAtCamera var2, Level var3, Hud var4, Radar var5) {
      this.hud = var4;
      this.radar = var5;
      this.level = var3;
      this.step = 0;
      this.cinematicBreak_ = true;
      this.active = true;
      this.startSequenceIsOver = false;
      this.lookAtCam = var2;
      this.unused_428 = false;
      this.timeLimit = var3.getTimeLimit();
      this.timePassed = 0L;
      var1.setLookAtCam(false);
      var2.setLookAt(true);
      if (var3.getPlayer() == null) {
         this.active = false;
      }

      if (Status.getCurrentCampaignMission() == 0) {
         var4.drawUI = false;
         var5.draw = false;
         var3.getPlayer().setFreeze(true);
         var3.getPlayer().setComputerControlled(true);
         GlobalStatus.renderer.getCamera().moveTo(0, 0, 0);
         GlobalStatus.renderer.getCamera().setRotation(0, 1800, 0);
         this.startSequenceIsOver = true;
         var1.setLookAtCam(false);
         var2.setLookAt(false);
         var3.getPlayer().setPosition_(0, 0, 120000);
      } else if (Status.getCurrentCampaignMission() == 1) {
         var4.drawUI = false;
         var5.draw = false;
         var3.getPlayer().shipGrandGroup_.setRotation(300, 300, 1000);
         var3.getPlayer().setFreeze(true);
         var3.getPlayer().setComputerControlled(true);
         var3.getPlayer().setPosition_(0, 0, 0);
         var3.getPlayer().setExhaustVisible(false);
         GlobalStatus.renderer.getCamera().moveTo(1500, 600, -3000);
         this.startSequenceIsOver = true;
         var2.setLookAt(true);
         var2.setTarget(var3.getPlayer().shipGrandGroup_);
      } else {
         if (Level.initStreamOutPosition) {
            this.tempVec.x = 500 + GlobalStatus.random.nextInt(500);
            this.tempVec.y = 500 + GlobalStatus.random.nextInt(500);
            this.tempVec.z = 10000;
         } else {
            this.tempVec.x = 1000 + GlobalStatus.random.nextInt(2000);
            this.tempVec.y = 500 + GlobalStatus.random.nextInt(2000);
            this.tempVec.z = 9000;
         }

         AEVector3D var10000;
         if (GlobalStatus.random.nextInt(2) == 0) {
            var10000 = this.tempVec;
            var10000.x = -var10000.x;
         }

         if (GlobalStatus.random.nextInt(2) == 0) {
            var10000 = this.tempVec;
            var10000.y = -var10000.y;
         }

         Matrix var6;
         (var6 = new Matrix()).setEulerY(var3.getPlayer().shipGrandGroup_.getEulerY());
         this.approachStationCamPos = var6.transformVectorNoScale(this.tempVec, this.approachStationCamPos);
         this.approachStationCamPos.add(var3.getPlayer().shipGrandGroup_.getPosition(this.tempVec));
         GlobalStatus.renderer.getCamera().moveTo(this.approachStationCamPos);
         var1.setCameraPosition(this.approachStationCamPos);
         var2.setOrientationLock(var3.getPlayer().shipGrandGroup_.getUp(), 2);
         if (Status.inAlienOrbit() || Level.comingFromAlienWorld) {
            if (Status.getCurrentCampaignMission() < 43) {
               this.tempVec2 = var3.getPlayer().shipGrandGroup_.getDirection(this.tempVec2);
               this.tempVec2.scale(8192);
               this.tempVec.subtract(this.tempVec2);
               ((PlayerWormHole)var3.getLandmarks()[3]).reset(true);
               var3.getLandmarks()[3].setPosition(this.tempVec.x, this.tempVec.y, this.tempVec.z);
               var3.getLandmarks()[3].setVisible(true);
            }

            Level.comingFromAlienWorld = false;
         }
      }

      var3.getPlayer().setCollide(false);
   }

   public final boolean process(int var1, TargetFollowCamera var2) {
      RadioMessage[] var3 = this.level.getMessages();
      PlayerEgo var4 = this.level.getPlayer();
      Camera var5 = GlobalStatus.renderer.getCamera();
      KIPlayer[] var6 = this.level.getEnemies();
      int var7;
      if (this.active) {
         this.passedTime += var1;
         if (Status.getCurrentCampaignMission() != 0) {
            if (Status.getCurrentCampaignMission() == 1) {
               var4.shipGrandGroup_.rotateEuler(0, var1 >> 3, 0);
               if (var6[0].getPosition(this.tempVec).z < -1800) {
                  var6[0].geometry.moveForward(var1 >> 1);
               } else {
                  ((PlayerFighter)var6[0]).state = 5;
                  ((PlayerFighter)var6[0]).setExhaustVisible(false);
               }

               if (var3[2].isOver()) {
                  GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[1]);
                  return false;
               }
            }
         } else if (!var3[1].isTriggered()) {
            var5.rotateEuler(0, var1 >> 4, 0);
            var6[0].setToSleep();
            var6[1].setToSleep();
         } else if (this.step == 0) {
            this.lookAtCam.setLookAt(true);
            var4.setFreeze(false);
            var4.shipGrandGroup_.setRotation(0, 2048, 0);
            var5.moveTo(-1000, -500, 110000);
            this.step = 1;
         } else {
            if (this.step < 6) {
               for(var7 = 0; var7 < 2; ++var7) {
                  int var8 = AEMath.sin(this.passedTime) >> 10;
                  var6[var7].geometry.translate(0, var8, 0);
               }

               var6[0].setToSleep();
               var6[1].setToSleep();
            }

            if (var3[2].isOver() && this.step == 1) {
               var4.setFreeze(true);
               this.lookAtCam.setTarget(var6[0].geometry);
               var5.moveTo(var6[0].getPosition(this.tempVec));
               var5.translate(1000, 700, 1500);
               this.step = 2;
            }

            if (var3[3].isOver() && this.step == 2) {
               this.lookAtCam.setTarget(var6[1].geometry);
               var5.moveTo(var6[1].getPosition(this.tempVec));
               var5.translate(-2300, 300, 200);
               this.step = 3;
            }

            if (var3[5].isOver() && this.step == 3) {
               var6[2].awake();
               this.lookAtCam.setTarget(var6[2].geometry);
               var5.moveTo(var6[2].getPosition(this.tempVec));
               var5.translate(1000, 200, 6000);
               this.step = 4;
            }

            if (var3[6].isOver() && this.step == 4) {
               this.lookAtCam.setTarget(var6[1].geometry);
               var5.moveTo(var6[1].getPosition(this.tempVec));
               var5.translate(-1300, 300, 1700);
               this.step = 5;
            }

            if (var3[7].isOver() && this.step == 5) {
               this.radar.draw = true;
               this.level.getPlayer().setCollide(true);
               var4.setFreeze(false);
               this.lookAtCam.setTarget(var4.shipGrandGroup_);
               this.lookAtCam.setLookAt(false);
               var2.setLookAtCam(true);
               this.step = 6;
               GlobalStatus.soundManager.playMusic(3);
            }

            if (var3[8].isOver() && this.step == 6) {
               var6[0].awake();
               var6[1].awake();
               var6[2].awake();
               ((PlayerFighter)var6[0]).setExhaustVisible(true);
               ((PlayerFighter)var6[1]).setExhaustVisible(true);
               var4.setComputerControlled(false);
               this.cinematicBreak_ = false;
               this.step = 7;
            }

            if (var3[10].isTriggered() && this.step == 7) {
               this.level.getPlayer().setCollide(false);
               this.radar.draw = false;
               var4.setComputerControlled(true);
               var4.player.removeAllGuns();
               var2.setLookAtCam(false);
               this.lookAtCam.setLookAt(true);
               this.lookAtCam.setTarget(var4.shipGrandGroup_);
               var4.shipGrandGroup_.setRotation(0, 2048, 0);
               var5.moveTo(var4.shipGrandGroup_.getPostition());
               var5.translate(1000, -200, -60000);
               this.step = 8;
               this.passedTime = 0;
            }

            if (var3[12].isOver() && this.step == 8) {
               var4.setFreeze(true);
               this.step = 9;
               var4.fakeExplode();
               this.level.flashScreen(3);
               var4.setExhaustVisible(false);
            }

            if (this.step == 9) {
               var4.shipGrandGroup_.rotateEuler(var1 >> 1, var1 >> 1, var1 >> 1);
               if (var3[16].isOver()) {
                  this.step = 10;
               }
            }

            if (this.step == 10) {
               var4.setFreeze(false);
               var5.moveTo(var4.shipGrandGroup_.getPostition());
               var5.setRotation(0, 0, 0);
               var5.translate(-1000, -700, -1500);
               if (var3[14].isOver()) {
                  var4.setExhaustVisible(true);
                  this.step = 11;
                  var4.shipGrandGroup_.setRotation(0, 0, 0);
                  var5.moveTo(var4.shipGrandGroup_.getPostition());
                  var5.translate(1000, 200, 15000);
               }
            }

            if (var3[21].isOver()) {
               Status.nextCampaignMission();
               GlobalStatus.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[2]);
               Level.initStreamOutPosition = false;
               return false;
            }
         }

         if (this.level.getPlayer() != null && Status.getCurrentCampaignMission() > 1 && this.passedTime > 7000) {
            this.passedTime = 0;
            this.active = false;
            this.startSequenceIsOver = true;
            this.lookAtCam.setLookAt(false);
            var2.setPostition(GlobalStatus.renderer.getCamera().getPostition());
            var2.setLookAtCam(true);
            resetCamera(var2, this.level);
            this.cinematicBreak_ = false;
            this.level.getPlayer().setCollide(true);
            if (Level.programmedStation != null && !Level.programmedStation.equals(Status.getStation())) {
               this.hud.hudEvent(5, this.level.getPlayer());
            }

            Level.initStreamOutPosition = false;
            if (!Level.driveJumping) {
               this.setAutoPilotToProgrammedStation();
            }
         }
      }

      if (Status.getCurrentCampaignMission() == 5 && this.step == 0) {
         this.tempVec = var4.getPosition();
         var6[0].setPosition(this.tempVec.x + 5000, this.tempVec.y, this.tempVec.z + 30000);
         var6[0].geometry.rotateEuler(0, 2048, 0);
         var6[0].awake();
         this.step = 1;
      }

      if (Status.getMission().isCampaignMission() && !Status.getMission().isEmpty()) {
         switch(Status.getCurrentCampaignMission()) {
         case 14:
            if (var3[1].isOver() && this.step == 0) {
               this.hud.drawUI = false;
               this.radar.draw = false;
               this.level.flashScreen(4);
               var4.setComputerControlled(true);
               var4.speed = 0;
               var4.player.removeAllGuns();
               var2.setLookAtCam(false);
               this.lookAtCam.setLookAt(true);
               this.lookAtCam.setTarget(var4.shipGrandGroup_);
               var5.moveTo(var4.getPosition());
               var5.translate(1000, 700, 1000);

               for(var7 = 0; var7 < this.level.getEnemies().length; ++var7) {
                  if (this.level.getEnemies()[var7].race == 8) {
                     this.level.getEnemies()[var7].player.setHitPoints(0);
                  }
               }

               this.step = 1;
            } else if (this.step == 1) {
               var4.shipGrandGroup_.rotateEuler(0, var1 >> 1, 0);
               var5.translate(0, 0, var1);
            }
            break;
         case 16:
         case 21:
            if (var3[0].isTriggered() && this.step == 0) {
               this.hud.drawUI = false;
               this.radar.draw = false;
               var4.setComputerControlled(true);
               var2.setTarget(this.level.getEnemies()[0].geometry);
               this.tempVec.set(300, 400, 3500);
               var2.setCamOffset(this.tempVec);
               this.tempVec.set(0, 0, 0);
               var2.setTargetOffset(this.tempVec);
               this.step = 1;
            } else if (var3[Status.getCurrentCampaignMission() == 16 ? 1 : 0].isOver() && this.step == 1) {
               var4.setComputerControlled(false);
               this.hud.drawUI = true;
               this.radar.draw = true;
               resetCamera(var2, this.level);
               this.step = 2;
            }
            break;
         case 24:
            if (var3[0].isTriggered() && this.step == 0) {
               this.hud.drawUI = false;
               this.radar.draw = false;
               this.lookAtCam.setLookAt(true);
               var2.setLookAtCam(false);
               var4.player.setHitPoints(9999999);
               var4.shipGrandGroup_.setRotation(0, 1024, 0);
               var4.setComputerControlled(true);
               var4.player.removeAllGuns();
               this.lookAtCam.setTarget(var4.shipGrandGroup_);
               var5.moveTo(var4.shipGrandGroup_.getPosition(this.tempVec));
               var5.translate(30500, 700, 1000);
               this.tempVec = var4.shipGrandGroup_.getPosition(this.tempVec);
               this.tempVec2 = var4.shipGrandGroup_.getDirection(this.tempVec2);
               this.tempVec2.scale(40960);
               this.tempVec.add(this.tempVec2);
               this.level.getLandmarks()[3].setPosition(this.tempVec.x, this.tempVec.y, this.tempVec.z);

               for(var7 = 0; var7 < this.level.getEnemies().length; ++var7) {
                  this.level.getEnemies()[var7].player.setEnemies((Player[])null);
               }

               this.step = 1;
            } else if (var3[0].isOver() && this.step == 1) {
               ((PlayerWormHole)this.level.getLandmarks()[3]).reset(false);
               this.level.getLandmarks()[3].setVisible(true);
               this.step = 2;
            }
            break;
         case 29:
            if (var3[0].isTriggered() && this.step == 0) {
               this.step = 1;
               var4.player.setVulnerable_(false);
               this.hud.drawUI = false;
               this.radar.draw = false;
               this.lookAtCam.setLookAt(true);
               var2.setLookAtCam(false);
               var4.setComputerControlled(true);
               this.lookAtCam.setTarget(var4.shipGrandGroup_);
               var5.moveTo(var4.shipGrandGroup_.getPosition(this.tempVec));
               this.tempVec = var4.shipGrandGroup_.getDirection();
               this.tempVec.scale(16384);
               new AEVector3D();
               AEVector3D var9 = null;
               (var9 = var4.shipGrandGroup_.getRight()).scale(1024);
               this.tempVec.add(var9);
               var5.translate(this.tempVec);
               this.probe = AEResourceManager.getGeometryResource(18);
               this.probe.setTransform(var4.shipGrandGroup_.getToParentTransform());
               this.probe.setScale(768, 768, 768);
            } else if (var3[2].isOver() && this.step == 1) {
               this.step = 2;
               this.hud.drawUI = true;
               this.radar.draw = true;
               this.lookAtCam.setLookAt(false);
               var2.setLookAtCam(true);
               var4.setComputerControlled(false);
               var4.player.setVulnerable_(true);
               this.probe = null;
               this.timeLimit = 180000;
               this.timePassed = 0L;
               this.level.successObjective = new Objective(3, this.timeLimit, this.level);
            }

            if (this.probe != null) {
               this.probe.moveForward(var1 * 3);
            }
            break;
         case 40:
            if (this.step == 0) {
               var6[0].race = 1;
               this.step = 1;
            } else if (this.step == 1 && var6[0].getPosition(this.tempVec2).z >= ((PlayerWormHole)this.level.getLandmarks()[3]).getPosition(this.tempVec).z) {
               this.step = 2;
            } else if (this.step == 2) {
               ((PlayerFixedObject)var6[0]).moveForward(var6[0].getPosition(this.tempVec2).z - 200000 - var1);
               if (var6[0].getPosition(this.tempVec2).z > 500000) {
                  var6[0].setPosition(0, 0, -200000);
                  var6[0].setActive(false);
                  this.step = 3;
               }
            }
            break;
         case 41:
            if (this.step == 0 && var6[0].geometry.getPosZ() > -10000) {
               this.step = 1;
               var6[0].setSpeed(0);
               var6[0].player.setMaxHP(9999999);
               this.level.getLandmarks()[3].setPosition(5000, -40000, 10000);
            }
         }
      }

      return this.cinematicBreak_;
   }

   public final void renderProbe__() {
      if (this.probe != null) {
         GlobalStatus.renderer.drawNodeInVF(this.probe);
      }

   }

   public final void skipSequence() {
      if (Status.getCurrentCampaignMission() > 0) {
         this.passedTime = 7001;
      }

   }

   public final void startSequence() {
      this.startSequenceIsOver = false;
      this.passedTime = 0;
   }

   public final boolean startSequenceOver() {
      return this.startSequenceIsOver;
   }

   public final boolean isActive() {
      return this.active;
   }

   public static void resetCamera(TargetFollowCamera var0, Level var1) {
      if (var1.getPlayer() != null) {
         var0.setTarget(var1.getPlayer().shipGrandGroup_);
         var0.setTargetOffset(new AEVector3D(0, 850, 0));
         var0.setCamOffset(new AEVector3D(0, 700, -2000));
         var0.followTargetPosition();
      }

   }

   public static void lookBehind(TargetFollowCamera var0) {
      var0.setTargetOffset(new AEVector3D(0, 300, 0));
      var0.setCamOffset(new AEVector3D(0, 600, 3100));
   }

   public final void setAutoPilotToProgrammedStation() {
      if (Level.programmedStation != null) {
         if (Status.getStation().equals(Level.programmedStation)) {
            Level.programmedStation = null;
            return;
         }

         SolarSystem var10000 = Status.getSystem();
         Station var2 = Level.programmedStation;
         if (var10000.stationIsInSystem(var2.getId())) {
            this.level.getPlayer().setAutoPilot(this.level.getStarSystem().getPlanetTargets()[Status.getSystem().getStationEnumIndex(Level.programmedStation.getId())]);
            return;
         }

         if (Status.getSystem().currentOrbitHasJumpgate()) {
            this.level.getPlayer().setAutoPilot(this.level.getLandmarks()[1]);
            return;
         }

         int var1;
         if ((var1 = Status.getSystem().getJumpGateEnumIndex()) >= 0) {
            this.level.getPlayer().setAutoPilot(this.level.getStarSystem().getPlanetTargets()[var1]);
         }
      }

   }
}
