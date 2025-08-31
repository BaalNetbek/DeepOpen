package GOF2;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import AE.AEFile;
import AE.AEGeometry;
import AE.AECamera;
import AE.GlobalStatus;
import AE.TargetFollowCamera;
import AE.Vec3f;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;

public final class Radar {
    private int[] asteroidsInFront;
    private Image lockonEnemy;
    private Image radarIconEnemy;
    private Image hudLockOnFriend;
    private Image hudRadarIconFriend;
    private Image lockonWaypoint;
    private Image lockonNeutral;
    private Image radarIconNeutral2;
    private Image bracketEnemyFar;
    private Image bracketFriendFar;
    private Image lockonWaypoint2;
    private Image radarIconNeutral;
    private Image radarIconEnemyLock;
    private Image radarIconFriendLock;
    private Image radarIconNeutralLock;
    private Image bracketBox;
    private Image crate;
    private Image asteroid;
    private Image spacejunk;
    private Image crateVoid;
    private Image vortex;
    private Image waypoint;
    private Image mapJumpgate;
    private Image sideMission;
    private Image mainMission;
    private Sprite logosSmall;
    private Sprite scanProcessAnim;
    private Sprite meteorClass;
    private Sprite bars;
    private int screenProjectionX;
    private int screenProjectionY;
    private final int elipsoidCenterX;
    private final int elipsoidCenterY;
    private final int elipsoidWidth;
    private final int elipsoidHeight;
    private boolean inViewFrustum;
    private KIPlayer[] enemies;
    private KIPlayer[] landmarks;
    private KIPlayer[] asteroids;
    private AEGeometry[] planets;
    private Route playerRoute;
    private Level level;
    private final AEMatrix camInvMatrix = new AEMatrix();
    private AEVector3D tempCameraSpacePos = new AEVector3D();
    private AEVector3D tempPos = new AEVector3D();
    private AEVector3D playerPos = new AEVector3D();
    private AEVector3D tempContextPosition = new AEVector3D();
    private final Vec3f floatVector = new Vec3f();
    private int enemyArmorBarHeight;
    private final String[] stationaryPlayersNames = new String[4];
    private String closestStatPlayerDistanceVisible;
    private int enemyScanPassedTime;
    private int asteroidScanPassedTime;
    private int planetScanPassedTime;
    private int landmarkScanPassedTime;
    public KIPlayer contextShip;
    private KIPlayer targetedPlayer;
    private KIPlayer contextAsteroid;
    private KIPlayer targetedAsteroid;
    private AEGeometry contextPlanet;
    public AEGeometry targetedPlanet;
    public KIPlayer tractorBeamTarget;
    public KIPlayer contextLandmark;
    public KIPlayer targetedLandmark;
    public boolean draw;
    private boolean onPlanetCourse_;
    private int targetedPlanetLocalIndex;
    private boolean showCargo;
    private boolean showAasteroids;
    private boolean scanerPresent;
    private boolean drillPresent;
    private boolean tractorBeamPresent;
    private boolean tractorBeamAutomatic;
    private boolean pastIntro;
    private int tractorBeamScanTime;
    private int scanTime;
    private int activeEnemyEnemiesCount;

    public Radar(final Level var1) {
        this.level = var1;
        this.elipsoidCenterX = GlobalStatus.screenWidth / 2;
        this.elipsoidCenterY = GlobalStatus.screenHeight / 2;
        this.elipsoidWidth = (this.elipsoidCenterX - 5) * (this.elipsoidCenterX - 5);
        this.elipsoidHeight = (this.elipsoidCenterY - 20) * (this.elipsoidCenterY - 20);
        this.stationaryPlayersNames[0] = (Status.inAlienOrbit() ? GlobalStatus.gameText.getText(238) : Status.getStation().getName()) + " " + GlobalStatus.gameText.getText(40);
        this.stationaryPlayersNames[1] = GlobalStatus.gameText.getText(271);
        this.stationaryPlayersNames[2] = "";
        this.stationaryPlayersNames[3] = GlobalStatus.gameText.getText(269);
        AEGeometry[] guns = var1.getPlayerGuns_();
        if (guns != null) {
            for(int i = 0; i < guns.length; ++i) {
                if (guns[i] instanceof RocketGun) {
                    ((RocketGun)guns[i]).setRadar(this);
                }
            }
        }

        try {
            this.lockonNeutral = AEFile.loadImage("/data/interface/hud_lockon_neutral.png", true);
            this.radarIconNeutral2 = AEFile.loadImage("/data/interface/hud_radaricon_neutral.png", true);
            this.radarIconNeutralLock = AEFile.loadImage("/data/interface/hud_radaricon_neutral_lock.png", true);
            this.lockonEnemy = AEFile.loadImage("/data/interface/hud_lockon_enemy.png", true);
            this.radarIconEnemy = AEFile.loadImage("/data/interface/hud_radaricon_enemy.png", true);
            this.radarIconEnemyLock = AEFile.loadImage("/data/interface/hud_radaricon_enemy_lock.png", true);
            this.hudLockOnFriend = AEFile.loadImage("/data/interface/hud_lockon_friend.png", true);
            this.hudRadarIconFriend = AEFile.loadImage("/data/interface/hud_radaricon_friend.png", true);
            this.radarIconFriendLock = AEFile.loadImage("/data/interface/hud_radaricon_friend_lock.png", true);
            this.lockonWaypoint = AEFile.loadImage("/data/interface/hud_lockon_waypoint.png", true);
            this.lockonWaypoint2 = AEFile.loadImage("/data/interface/hud_lockon_waypoint.png", true);
            this.radarIconNeutral = AEFile.loadImage("/data/interface/hud_radaricon_neutral.png", true);
            final Image var7 = AEFile.loadImage("/data/interface/hud_bars.png", true);
            this.bars = new Sprite(var7, 2, var7.getHeight());
            Image var9 = AEFile.loadImage("/data/interface/logos_small.png", true);
            this.logosSmall = new Sprite(var9, var9.getHeight(), var9.getHeight());
            this.logosSmall.defineReferencePixel(var9.getHeight(), var9.getHeight());
            this.bracketEnemyFar = AEFile.loadImage("/data/interface/bracket_enemy_far.png", true);
            this.bracketFriendFar = AEFile.loadImage("/data/interface/bracket_friend_far.png", true);
            this.bracketBox = AEFile.loadImage("/data/interface/bracket_box.png", true);
            this.crate = AEFile.loadImage("/data/interface/hud_crate.png", true);
            this.spacejunk = AEFile.loadImage("/data/interface/hud_spacejunk.png", true);
            this.asteroid = AEFile.loadImage("/data/interface/hud_asteroid.png", true);
            this.crateVoid = AEFile.loadImage("/data/interface/hud_crate_void.png", true);
            this.vortex = AEFile.loadImage("/data/interface/hud_vortex.png", true);
            this.waypoint = AEFile.loadImage("/data/interface/hud_waypoint.png", true);
            this.mapJumpgate = AEFile.loadImage("/data/interface/menu_map_jumpgate.png", true);
            var9 = AEFile.loadImage("/data/interface/hud_scanprocess_anim_png24.png", true);
            this.scanProcessAnim = new Sprite(var9, var9.getHeight(), var9.getHeight());
            this.scanProcessAnim.defineReferencePixel(var9.getHeight() / 2, var9.getHeight() / 2);
            var9 = AEFile.loadImage("/data/interface/hud_meteor_class.png", true);
            this.meteorClass = new Sprite(var9, 11, 11);
            this.meteorClass.defineReferencePixel(11, 11);
            final Item var10 = Status.getShip().getFirstEquipmentOfSort(Item.MINING_LASER);
            final Item var8 = Status.getShip().getFirstEquipmentOfSort(Item.SCANNER);
            final Item var3 = Status.getShip().getFirstEquipmentOfSort(Item.TRACTOR_BEAM);
            this.drillPresent = var10 != null;
            if (var8 != null) {
                this.scanerPresent = true;
                this.showCargo = var8.getAttribute(Item.SHOW_CARGO) == 1;
                this.showAasteroids = var8.getAttribute(Item.SHOW_ASTEROID) == 1;
                this.scanTime = var8.getAttribute(Item.SCAN_TIME);
            } else {
                this.scanerPresent = false;
                this.showCargo = false;
                this.showAasteroids = false;
                this.scanTime = 8000;
            }

            if (var3 != null) {
                this.tractorBeamPresent = true;
                this.tractorBeamAutomatic = var3.getAttribute(Item.TRACTOR_AUTOMATIC) == 1;
                this.tractorBeamScanTime = var3.getAttribute(Item.TRACTOR_SCAN_TIME);
            } else {
                this.tractorBeamPresent = false;
                this.tractorBeamScanTime = 0;
                this.tractorBeamAutomatic = false;
            }

            this.pastIntro = Status.getCurrentCampaignMission() > 0;
            this.activeEnemyEnemiesCount = 0;
            this.draw = true;
            this.asteroidsInFront = new int[5];

            for(int i = 0; i < this.asteroidsInFront.length; ++i) {
                this.asteroidsInFront[i] = -1;
            }

        } catch (final Exception var4) {
            var4.printStackTrace();
        }
    }

    public final void OnRelease() {
        this.enemies = null;
        this.landmarks = null;
        this.playerRoute = null;
        this.level = null;
        this.playerPos = null;
        this.tempContextPosition = null;
        this.tempCameraSpacePos = null;
    }

    private void elipsoidIntersect(final AECamera var1, final AEVector3D var2) {
        this.tempPos.set(var2);
        this.tempCameraSpacePos = var1.getInverseTransform(this.camInvMatrix).transformVector2(this.tempPos, this.tempCameraSpacePos);
        this.tempCameraSpacePos.y = -this.tempCameraSpacePos.y;
        this.tempCameraSpacePos.z = -this.tempCameraSpacePos.z;
        var1.getScreenPosition(this.tempPos);
        this.screenProjectionX = this.tempPos.x;
        this.screenProjectionY = this.tempPos.y;
        this.inViewFrustum = this.screenProjectionX <= GlobalStatus.screenWidth && this.screenProjectionX >= 0 && this.screenProjectionY <= GlobalStatus.screenHeight && this.screenProjectionY >= 0;
        if (this.tempCameraSpacePos.z < 0 || !this.inViewFrustum) {
            final AEVector3D var4 = this.tempCameraSpacePos;
            final int var3 = this.screenProjectionY;
            final int var9 = this.screenProjectionX;
            final float var5 = this.elipsoidCenterX - var9;
            final float var6 = this.elipsoidCenterY + -10 - var3;
            float var8 = var5 * var5 / this.elipsoidWidth + var6 * var6 / this.elipsoidHeight;
            if (var8 >= 0.0F) {
                final float var7 = AEMath.sqrt((long)(var8 * AEMath.TO_Q)) * AEMath.TO_FLOAT;
                var8 = (var8 - var7) / var8;
                if (0.0F <= var8 && var8 <= 1.0F) {
                    var4.x = (int)(var9 + var5 * var8);
                    var4.y = (int)(var3 + var6 * var8);
                }
            }

            this.tempCameraSpacePos = var4;
            this.screenProjectionX = this.tempCameraSpacePos.x;
            this.screenProjectionY = this.tempCameraSpacePos.y;
        }

    }

    private void elipsoidIntersect(final AECamera var1, final KIPlayer var2) {
        this.elipsoidIntersect(var1, var2.getPosition(this.tempPos));
    }
    /** Thats a big function
     * 
     * @param var1
     * @param var2
     * @param var3
     * @param var4
     * @param var5
     */
    public final void draw(final Player var1, final AECamera var2, final TargetFollowCamera var3, final Hud var4, final int var5) {
        if (!this.draw) {
            KIPlayer[] var23 = this.level.getEnemies();
            if (var23 != null) {
                for(int i = 0; i < var23.length; ++i) {
                    var23[i].withinRenderDistance = true;
                }
            }

        } else {
            Mission var6;
            final boolean var7 = !(var6 = Status.getMission()).isEmpty() && var6.getType() != Mission.TYPE_11 && var6.getType() != Mission.TYPE_0;
            final boolean var22 = this.level.getPlayer().isLookingBack();
            final boolean inAlienOrbit = Status.inAlienOrbit();
            this.enemies = this.level.getEnemies();
            this.landmarks = this.level.getLandmarks();
            this.playerRoute = this.level.getPlayerRoute();
            this.asteroids = this.level.getAsteroids();
            this.planets = this.level.getStarSystem().getPlanets();
				if (!inAlienOrbit) {
					this.onPlanetCourse_ = this.level.getPlayer().goingToPlanet() && !this.level.getPlayer().isDockingToPlanet()
					      && this.level.getPlayer()
					            .getAutoPilotTarget() == this.level.getStarSystem().getPlanetTargets()[Status.getSystem()
					                  .getStationEnumIndex(Status.getSystem().getStations()[this.targetedPlanetLocalIndex])];
				} else {
					this.onPlanetCourse_ = this.level.getPlayer().goingToPlanet() && !this.level.getPlayer().isDockingToPlanet();
				}

            if (this.playerRoute != null && this.playerRoute.getDockingTarget_() != null) {
                this.elipsoidIntersect(var2, this.playerRoute.getDockingTarget_());
                this.closestStatPlayerDistanceVisible = calcDistance(this.playerRoute.getDockingTarget_().x, this.playerRoute.getDockingTarget_().y, this.playerRoute.getDockingTarget_().z, var1);
                if (this.inViewFrustum) {
                    GlobalStatus.graphics.drawImage(this.lockonWaypoint, this.screenProjectionX, this.screenProjectionY, 3);
					Font.drawString(
							this.closestStatPlayerDistanceVisible,
							this.screenProjectionX - 8,
							this.screenProjectionY + 8,
							1
					);
                } else if (this.scanerPresent) {
                    GlobalStatus.graphics.drawImage(this.waypoint, this.screenProjectionX, this.screenProjectionY, Graphics.HCENTER|Graphics.VCENTER);
                }
            }

            boolean var9 = this.targetedAsteroid != null || this.tractorBeamTarget != null || this.targetedPlanet != null;
            boolean aimingAtLandmark = false;
            
            if (this.landmarks != null) {
                for(int i = 0; i < this.landmarks.length; ++i) {
                    if (i != 2 && this.landmarks[i] != null && this.landmarks[i].isVisible()) {
                        this.elipsoidIntersect(var2, this.landmarks[i]);
                        if (this.inViewFrustum) {
                            if (!var9 && !aimingAtLandmark && !var22 && i != 3) {
                           	  final int xX = Crosshair.screenPos.x - 30;
                           	  final int xY = Crosshair.screenPos.y - 30;
                                if (this.screenProjectionX > xX && this.screenProjectionX < xX + 60 && this.screenProjectionY > xY && this.screenProjectionY < xY + 60) {
                                    if (this.contextLandmark != this.landmarks[i]) {
                                        this.landmarkScanPassedTime = 0;
                                    }

                                    this.contextLandmark = this.landmarks[i];
                                    aimingAtLandmark = true;
                                }
                            }

                            this.playerPos = var1.getPosition(this.playerPos);
                            this.tempContextPosition = this.landmarks[i].player.getPosition(this.tempContextPosition);
                            if (i != 3) {
                                GlobalStatus.graphics.drawImage(this.lockonWaypoint2, this.screenProjectionX, this.screenProjectionY, 3);
                            }

							Font.drawString(
									this.stationaryPlayersNames[i],
									i == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5,
									this.screenProjectionY,
									0
							);
							if (i < 2) {
                                if (i == 0 && !inAlienOrbit) {
									Font.drawString(
											GlobalStatus.gameText.getText(37) + ": " + Status.getStation().getTecLevel(),
											i == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5,
											this.screenProjectionY + 10,
											1
									);
									this.closestStatPlayerDistanceVisible = calcDistance(
											this.landmarks[i].targetX,
											this.landmarks[i].targetY,
											this.landmarks[i].targetZ,
											var1
									);
									Font.drawString(
											this.closestStatPlayerDistanceVisible,
											i == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5,
											this.screenProjectionY + 20,
											1
									);
							       } else {
                                    this.closestStatPlayerDistanceVisible = calcDistance(this.landmarks[i].targetX, this.landmarks[i].targetY, this.landmarks[i].targetZ, var1);
									Font.drawString(
											this.closestStatPlayerDistanceVisible,
											i == 0 ? this.screenProjectionX + 25 : this.screenProjectionX + 5,
											this.screenProjectionY + 10,
											1
									);
								 }
                            }
                        } else if (i == 3) {
                            GlobalStatus.graphics.drawImage(this.vortex, this.screenProjectionX, this.screenProjectionY, 3);
                        }
                    }
                }

                if (!var22) {
                    if (this.targetedLandmark != null) {
                        if (this.targetedLandmark.isDead()) {
                            this.targetedLandmark = null;
                        }

                        this.targetedLandmark = null;
                    }

                    if (aimingAtLandmark && !this.level.getPlayer().isDockingToAsteroid() && !this.level.getPlayer().isAutoPilot()) {
                        this.landmarkScanPassedTime += var5;
                        if (this.landmarkScanPassedTime > this.scanTime) {
                            this.targetedLandmark = this.contextLandmark;
                        }

                        if (this.landmarkScanPassedTime > 0) {
                            if (this.contextLandmark != null && !this.contextLandmark.equals(this.targetedLandmark)) {
                                this.scanProcessAnim.setFrame((int)((this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.landmarkScanPassedTime / (float)this.scanTime)));
                                this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                this.scanProcessAnim.paint(GlobalStatus.graphics);
                            } else if (Layout.quickClockHigh_()) {
                                this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                                this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                this.scanProcessAnim.paint(GlobalStatus.graphics);
                            }
                        }
                    } else {
                        if (this.targetedLandmark == null) {
                            this.contextLandmark = null;
                        }

                        this.landmarkScanPassedTime = 0;
                    }
                }
            }

            var9 = this.targetedAsteroid != null || this.tractorBeamTarget != null || this.targetedLandmark != null;
            boolean var28 = false;
            boolean var14;
            boolean var15;
            int var18;
            int var19;
            if (this.planets != null) {
                for(int i = 1; i < this.planets.length; ++i) {
                    this.elipsoidIntersect(var2, this.planets[i].getLocalPos(this.tempPos));
                    if (this.inViewFrustum) {
                        int[] var30;
                        final boolean var8 = (var30 = Status.getSystem().getStations())[i - 1] == Status.getStation().getIndex();
                        var14 = false;
                        if (Status.getSystem().getJumpGateEnumIndex() == i - 1 && !var8) {
                            GlobalStatus.graphics.drawImage(this.mapJumpgate, this.screenProjectionX + 10, this.screenProjectionY - 10, 0);
                            var14 = true;
                        }

                        var15 = false;
                        final Mission var16 = Status.getCampaignMission();
                        final Mission var17 = Status.getFreelanceMission();
                        if (var16 != null && !var16.isEmpty() && var30[i - 1] == var16.getTargetStation() && !var8) {
                            if (this.mainMission == null) {
                                this.mainMission = AEFile.loadCryptedImage("/data/interface/menu_map_mainmission.png");
                            }

                            GlobalStatus.graphics.drawImage(this.mainMission, this.screenProjectionX + 10 + (var14 ? 14 : 0), this.screenProjectionY - 10, 0);
                            var15 = true;
                        }

                        if (var17 != null && !var17.isEmpty() && var30[i - 1] == var17.getTargetStation() && !var8) {
                            if (this.sideMission == null) {
                                this.sideMission = AEFile.loadCryptedImage("/data/interface/menu_map_sidemission.png");
                            }

                            GlobalStatus.graphics.drawImage(this.sideMission, this.screenProjectionX + 10 + (var14 ? 14 : 0), this.screenProjectionY - 10, 0);
                            var15 = true;
                        }

                        if (!var9 && !var28 && !var22) {
                            var18 = StarSystem.currentPlanetEnumIndex == i ? 100 : 30; //planet scan radius
                            var19 = Crosshair.screenPos.x - (var18 >> 1);
                            final int var20 = Crosshair.screenPos.y - (var18 >> 1);
                            if (this.screenProjectionX > var19 && this.screenProjectionX < var19 + var18 && this.screenProjectionY > var20 && this.screenProjectionY < var20 + var18) {
                                if (i != StarSystem.currentPlanetEnumIndex) {
                                    if (this.contextPlanet != this.planets[i]) {
                                        this.planetScanPassedTime = 0;
                                    }

                                    this.targetedPlanetLocalIndex = i - 1;
                                    this.contextPlanet = this.planets[i];
                                    var28 = true;
                                }

								Font.drawString(
										Status.getPlanetNames()[i - 1],
										this.screenProjectionX + 10 + (var14 ? 14 : 0) + (var15 ? 14 : 0),
										this.screenProjectionY - 10, 0
								);
							   }
                        }
                    }
                }

                if (!aimingAtLandmark && !var22) {
                    if (this.targetedPlanet != null) {
                        this.targetedPlanet = null;
                    }

                    if (var28 && !this.level.getPlayer().isDockingToAsteroid()) {
                        this.planetScanPassedTime += var5;
                        if (this.planetScanPassedTime > this.scanTime) {
                            if (var7) {
                                var4.hudEvent(Hud.EVENT_CANT_ON_MISSION, this.level.getPlayer());
                            } else {
                                this.targetedPlanet = this.contextPlanet;
                                if (this.onPlanetCourse_) {
                                    this.level.getPlayer().stopPlanetDock_(var3);
                                }
                            }
                        }

                        if (this.planetScanPassedTime > 0 && !var7) {
                            if (this.contextPlanet != null && !this.contextPlanet.equals(this.targetedPlanet)) {
                                this.scanProcessAnim.setFrame((int)((this.scanProcessAnim.getRawFrameCount() - 2) * ((float)this.planetScanPassedTime / (float)this.scanTime)));
                                this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                this.scanProcessAnim.paint(GlobalStatus.graphics);
                            } else if (!this.level.getPlayer().isDockingToPlanet() && Layout.quickClockHigh_()) {
                                this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                                this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                this.scanProcessAnim.paint(GlobalStatus.graphics);
                            }
                        }
                    } else {
                        if (this.targetedPlanet == null) {
                            this.contextPlanet = null;
                        }

                        this.planetScanPassedTime = 0;
                    }
                }
            }

            if (this.targetedPlayer != null && (this.targetedPlayer.isDying() || this.targetedPlayer.isDead())) {
                this.targetedPlayer = null;
                var9 = false;
                this.contextShip = null;
            }

            boolean var29 = false;

            if (this.scanerPresent && this.enemies != null) {
                for(int i = 0; i < this.enemies.length; ++i) {
                    KIPlayer var25 = this.enemies[i];
                    if (var25.player.isActive() && (!var25.isDying() || var25.hasCargo)) {
                        this.elipsoidIntersect(var2, var25);
                        var14 = var25.hasCargo && (var25.isDead() || var25.isDying());
                        if (!var25.isDead() && !var25.isDying() || var25.hasCargo) {
                            var15 = var25.equals(this.targetedPlayer);
                            Gun[] var33;
                            if (!var25.isDead() && this.level.getPlayer().player.hasGunOfType(Item.SECONDARY) && (var33 = this.level.getPlayer().player.guns[1]) != null) {
                                
                           	  GlobalStatus.graphics.setStrokeStyle(Graphics.DOTTED);
                                for(int j = 0; j < var33.length; ++j) {
                                    Gun var40 = var33[j];
                                    if (var40 != null && (var40.subType == Item.NUKE || var40.subType == Item.EMP_BOMB) && var40.inAir) {
                                        this.playerPos.set(var40.projectilesPos[0]);
                                        this.tempContextPosition = var25.getPosition(this.tempContextPosition);
                                        this.tempContextPosition.subtract(this.playerPos);
                                        if ((var19 = this.tempContextPosition.getLength()) < var40.getMagnitude()) {
                                            float var41;
                                            if ((var41 = (float)var19 / (float)var40.getMagnitude()) > 1.0F) {
                                                var41 = 1.0F;
                                            }

                                            final int var21 = -(1 << 24) | (int)(var41 * 255.0F) << 16 | (int)((1.0F - var41) * 255.0F) << 8;
                                            GlobalStatus.graphics.setColor(var21);
                                            GlobalStatus.renderer.getCamera().getScreenPosition(this.playerPos);
                                            this.tempContextPosition.x = this.screenProjectionX;
                                            this.tempContextPosition.y = this.screenProjectionY;
                                            this.tempContextPosition.z = 0;
                                            this.tempContextPosition.subtract(this.playerPos);
                                            this.tempContextPosition.z = 0;
                                            this.tempContextPosition.x <<= AEMath.Q;
                                            this.tempContextPosition.y <<= AEMath.Q;
                                            this.tempContextPosition.normalize();
                                            this.tempContextPosition.scale(AEMath.Q_512th);
                                            this.playerPos.add(this.tempContextPosition);
                                            GlobalStatus.graphics.drawLine(this.screenProjectionX, this.screenProjectionY, this.playerPos.x, this.playerPos.y);
                                        }
                                    }
                                }

                                GlobalStatus.graphics.setStrokeStyle(Graphics.SOLID);
                            }

                            if (!var25.isAsteroid && !var25.junk && !var14 && var25.player.enemy) {
                                ++this.activeEnemyEnemiesCount;
                            }

                            Image var35;
                            if (!this.inViewFrustum) {
                                if (var14) {
                                    GlobalStatus.graphics.drawImage(this.radarIconNeutral, this.screenProjectionX, this.screenProjectionY, 3);
                                    GlobalStatus.graphics.drawImage(this.enemies[i].junk ? this.spacejunk : this.enemies[i].race == 9 ? this.crateVoid : this.crate, this.screenProjectionX, this.screenProjectionY, 3);
                                } else {
                                    var35 = var25.player.enemy ? var15 ? this.radarIconEnemyLock : this.radarIconEnemy : var25.player.friend ? var15 ? this.radarIconFriendLock : this.hudRadarIconFriend : var15 ? this.radarIconNeutralLock : this.radarIconNeutral2;
                                    GlobalStatus.graphics.drawImage(var35, this.screenProjectionX, this.screenProjectionY, 3);
                                }
                            } else {
                                this.playerPos = var1.getPosition(this.playerPos);
                                this.tempContextPosition = var25.player.getPosition(this.tempContextPosition);
                                if (this.playerPos.x - this.tempContextPosition.x <= 24000 && this.playerPos.x - this.tempContextPosition.x >= -24000 && this.playerPos.y - this.tempContextPosition.y <= 24000 && this.playerPos.y - this.tempContextPosition.y >= -24000 && this.playerPos.z - this.tempContextPosition.z <= 24000 && this.playerPos.z - this.tempContextPosition.z >= -24000 && var25.getMeshId() != 9996) {
                                    label862: {
                                        var25.withinRenderDistance = true;
                                        Image var10001;
                                        Graphics var42;
                                        if (var14) {
                                            var42 = GlobalStatus.graphics;
                                            var10001 = this.bracketBox;
                                        } else {
                                            int var36 = var25.player.enemy ? 0 : var25.player.friend ? 6 : 4;
                                            this.enemyArmorBarHeight = (int)(var25.player.getDamageRate() / 100.0F * 16.0F);
                                            this.bars.setFrame(var36 + 1);
                                            this.bars.setPosition(this.screenProjectionX + 8 + 2, this.screenProjectionY - 8);
                                            this.bars.paint(GlobalStatus.graphics);
                                            this.bars.setFrame(var36);
                                            GlobalStatus.graphics.setClip(0, this.screenProjectionY - 8 + 16 - this.enemyArmorBarHeight, GlobalStatus.screenWidth, this.enemyArmorBarHeight);
                                            this.bars.paint(GlobalStatus.graphics);
                                            GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
                                            this.enemyArmorBarHeight = (int)(var25.player.getEmpDamageRate() / 100.0F * 16.0F);
                                            this.bars.setFrame(3);
                                            this.bars.setPosition(this.screenProjectionX + 8 + 7, this.screenProjectionY - 8);
                                            this.bars.paint(GlobalStatus.graphics);
                                            this.bars.setFrame(2);
                                            GlobalStatus.graphics.setClip(0, this.screenProjectionY - 8 + 16 - this.enemyArmorBarHeight, GlobalStatus.screenWidth, this.enemyArmorBarHeight);
                                            this.bars.paint(GlobalStatus.graphics);
                                            GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
                                            if (!var15) {
                                                break label862;
                                            }

                                            var42 = GlobalStatus.graphics;
                                            var10001 = var25.player.enemy ? this.lockonEnemy : var25.player.friend ? this.hudLockOnFriend : this.lockonNeutral;
                                        }

                                        var42.drawImage(var10001, this.screenProjectionX, this.screenProjectionY, 3);
                                    }
                                } else {
                                    var35 = var14 ? this.bracketBox : var15 ? var25.player.enemy ? this.bracketEnemyFar : var25.player.friend ? this.bracketFriendFar : this.lockonNeutral : var25.player.enemy ? this.radarIconEnemy : var25.player.friend ? this.hudRadarIconFriend : this.radarIconNeutral2;
                                    GlobalStatus.graphics.drawImage(var35, this.screenProjectionX, this.screenProjectionY, 3);
                                    var25.withinRenderDistance = false;
                                }

                                if (!this.level.getPlayer().isAutoPilot() && (!var25.junk || var14) && (!var29 || var25.stunned) && (var14 && var25.isDead() || !var14)) {
                                    if (var14 && this.tractorBeamTarget == null && this.tractorBeamAutomatic) {
                                        this.tractorBeamTarget = var25;
                                        var29 = true;
                                        this.contextShip = var25;
                                    } else {
                                    	 final int var36 = Crosshair.screenPos.x - 30;
                                    	 final int var37 = Crosshair.screenPos.y - 30;
                                        if (this.screenProjectionX > var36 && this.screenProjectionX < var36 + 60 && this.screenProjectionY > var37 && this.screenProjectionY < var37 + 60 && (var14 && this.tractorBeamPresent || this.pastIntro && !this.onPlanetCourse_)) {
                                            if (this.contextShip != var25) {
                                                this.enemyScanPassedTime = 0;
                                            }

                                            this.contextShip = var25;
                                            var29 = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (!this.level.getPlayer().isDockingToAsteroid() && !var22 && !var9) {
                    final boolean var31 = this.contextShip != null && this.contextShip.hasCargo && (this.contextShip.isDead() || this.contextShip.isDying());
                    if (this.targetedPlayer != null && (this.targetedPlayer.isDying() || this.targetedPlayer.isDead())) {
                        this.targetedPlayer = null;
                    }

                    if (var29) {
                        this.enemyScanPassedTime += var5;
                        if (this.enemyScanPassedTime > (!var31 && !this.contextShip.stunned ? this.scanTime : this.tractorBeamScanTime)) {
                            if (!var31 && !this.contextShip.stunned) {
                                if (this.targetedPlayer != this.contextShip) {
                                    this.targetedPlayer = this.contextShip;
                                    if (this.showCargo) {
                                        this.playerPos = var1.getPosition(this.playerPos);
                                        this.tempContextPosition = this.targetedPlayer.player.getPosition(this.tempContextPosition);
                                        if (this.playerPos.x - this.tempContextPosition.x < 24000 || this.playerPos.x - this.tempContextPosition.x > -24000 || this.playerPos.y - this.tempContextPosition.y < 24000 || this.playerPos.y - this.tempContextPosition.y > -24000 || this.playerPos.z - this.tempContextPosition.z < 24000 || this.playerPos.z - this.tempContextPosition.z > -24000) {
                                            int[] var26 = this.targetedPlayer.cargo;
                                            if (var26 != null) {
                                                for(int i = 0; i < var26.length; i += 2) {
                                                    if (var26[i + 1] > 0) {
                                                        var4.catchCargo(var26[0], 1, false, false, false, true);
                                                        break;
                                                    }
                                                }
                                            } else {
                                                var4.hudEvent(22, this.level.getPlayer());
                                            }
                                        }
                                    }
                                }
                            } else {
                                this.tractorBeamTarget = this.contextShip;
                                if (!this.tractorBeamPresent) {
                                    var4.hudEvent(9, this.level.getPlayer());
                                    this.tractorBeamTarget = null;
                                }
                            }

                            this.enemyScanPassedTime = 0;
                        }

                        if (this.enemyScanPassedTime > (!var31 && !this.contextShip.stunned ? 0 : 500) && this.contextShip != null && !this.contextShip.equals(!var31 && !this.contextShip.stunned ? this.targetedPlayer : this.tractorBeamTarget)) {
                            this.scanProcessAnim.setFrame((int)((this.scanProcessAnim.getRawFrameCount() - 2) * ((float)(this.enemyScanPassedTime - (!var31 && !this.contextShip.stunned ? 0 : 500)) / (float)(!var31 && !this.contextShip.stunned ? this.scanTime : this.tractorBeamScanTime - 500))));
                            this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                            this.scanProcessAnim.paint(GlobalStatus.graphics);
                        }
                    } else {
                        if ((var31 ? this.tractorBeamTarget == null : this.targetedPlayer == null)) {
                            this.contextShip = null;
                        }

                        this.enemyScanPassedTime = 0;
                    }
                }
            }

            int k = 0;

            for(int i = 0; i < this.asteroidsInFront.length; ++i) {
                this.asteroidsInFront[i] = -1;
            }

            var9 = this.contextShip != null && this.targetedPlayer == null || this.tractorBeamTarget != null || this.targetedLandmark != null || this.targetedPlanet != null;
            boolean var8 = false;
            if (!this.level.getPlayer().isLockedOnAsteroid_() && this.asteroids != null) {
                var14 = false;
                if (this.showAasteroids) {
                    var14 = this.level.isInAsteroidCenterRange(this.level.getPlayer().getPosition());
                }

                //int var34;
                boolean var39;
                for(int i = 0; i < this.asteroids.length; ++i) {
                    KIPlayer var38;
                    if ((var38 = this.asteroids[i]).hasCargo && (var38.isDead() || var38.isDying()) || !var38.isDead() && !var38.isDying()) {
                        this.elipsoidIntersect(var2, var38);
                        var39 = var38.hasCargo && (var38.isDead() || var38.isDying());
                        if (!this.inViewFrustum) {
                            if (var39) {
                                GlobalStatus.graphics.drawImage(this.radarIconNeutral, this.screenProjectionX, this.screenProjectionY, 3);
                                GlobalStatus.graphics.drawImage(this.asteroid, this.screenProjectionX, this.screenProjectionY, 3);
                            }
                        } else {
                            if (!var9 && !var29 && !var8 && !var22 && ((PlayerAsteroid)var38).getMass_SizeCoef__() > 15 && !this.onPlanetCourse_) {
                                if (var39 && this.tractorBeamTarget == null && this.tractorBeamAutomatic) {
                                    this.tractorBeamTarget = var38;
                                    var8 = true;
                                    this.contextShip = var38;
                                } else {
                                    var18 = Crosshair.screenPos.x - 20;
                                    var19 = Crosshair.screenPos.y - 20;
												if (this.screenProjectionX > var18 && this.screenProjectionX < var18 + 40
												      && this.screenProjectionY > var19 && this.screenProjectionY < var19 + 40
												      && !((PlayerAsteroid) var38).clampedByDistance && this.pastIntro
												      && k < 4) {
													this.asteroidsInFront[k] = i;
													++k;
												}
                                }
                            }

                            if (var14 && (var18 = ((PlayerAsteroid)var38).getQualityFrameIndex()) == 0) {
                                this.meteorClass.setFrame(var18);
                                this.meteorClass.setRefPixelPosition(this.screenProjectionX, this.screenProjectionY);
                                this.meteorClass.paint(GlobalStatus.graphics);
                            }

                            if (var39) {
                                GlobalStatus.graphics.drawImage(this.bracketBox, this.screenProjectionX, this.screenProjectionY, 3);
                            }
                        }
                    }
                }

                int var34 = 999999;
                int var36 = -1;

                for(int i = 0; i < 5; ++i) {
                    var18 = this.asteroidsInFront[i];
                    if (var18 >= 0) {
                        this.tempCameraSpacePos = this.asteroids[var18].getPosition(this.tempCameraSpacePos);
                        this.tempPos = var1.getPosition(this.tempPos);
                        this.tempCameraSpacePos.subtract(this.tempPos);
                        if ((var19 = this.tempCameraSpacePos.getLength()) < var34) {
                            var34 = var19;
                            var36 = var18;
                        }
                    }
                }

                if (var36 >= 0) {
                    if (this.contextAsteroid != this.asteroids[var36]) {
                        this.asteroidScanPassedTime = 0;
                    }

                    this.contextAsteroid = this.asteroids[var36];
                    var8 = true;
                }

                if (!var29 && !var22) {
                    if (this.targetedAsteroid != null) {
                        if (this.targetedAsteroid.isDead()) {
                            this.targetedAsteroid = null;
                        }

                        this.targetedAsteroid = null;
                    }

                    var39 = this.contextAsteroid != null && this.contextAsteroid.hasCargo && (this.contextAsteroid.isDead() || this.contextAsteroid.isDying());
                    if (var8 && !this.level.getPlayer().isDockingToAsteroid()) {
                        this.asteroidScanPassedTime += var5;
                        if (this.asteroidScanPassedTime > this.scanTime - 200) {
                            if (var39) {
                                if (!this.tractorBeamPresent) {
                                    var4.hudEvent(9, this.level.getPlayer());
                                } else {
                                    this.tractorBeamTarget = this.contextAsteroid;
                                }
                            } else if (!this.drillPresent) {
                                var4.hudEvent(20, this.level.getPlayer());
                            } else {
                                this.targetedAsteroid = this.contextAsteroid;
                            }
                        }

                        if (this.asteroidScanPassedTime > 500) {
                            if (this.contextAsteroid != null && !this.contextAsteroid.equals(var39 ? this.tractorBeamTarget : this.targetedAsteroid)) {
                                var18 = (int)((this.scanProcessAnim.getRawFrameCount() - 1) * ((float)(this.asteroidScanPassedTime - 500) / (float)(this.scanTime - 500)));
                                if (var18 < this.scanProcessAnim.getRawFrameCount() - 1) {
                                    this.scanProcessAnim.setFrame(var18);
                                    this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                    this.scanProcessAnim.paint(GlobalStatus.graphics);
                                }
                            } else if (Layout.quickClockHigh_()) {
                                this.scanProcessAnim.setFrame(this.scanProcessAnim.getRawFrameCount() - 2);
                                this.scanProcessAnim.setRefPixelPosition(Crosshair.screenPos.x, Crosshair.screenPos.y);
                                this.scanProcessAnim.paint(GlobalStatus.graphics);
                            }
                        }
                    } else {
                        if ((var39 ? this.tractorBeamTarget == null : this.targetedAsteroid == null)) {
                            this.contextAsteroid = null;
                        }

                        this.asteroidScanPassedTime = 0;
                    }
                }
            }

            if (this.activeEnemyEnemiesCount > 0) {
                if (GlobalStatus.currentMusic != 3) {
                    GlobalStatus.soundManager.playMusic(3);
                }
            } else if (GlobalStatus.currentMusic != 4 && GlobalStatus.currentMusic != 0) {
                GlobalStatus.soundManager.playMusic(4);
            }

            this.activeEnemyEnemiesCount = 0;
        }
    }

    public final int getPlanetDockIndex() {
        return Status.getSystem().getStations()[this.targetedPlanetLocalIndex];
    }

    public final void drawCurrentLock(final Hud var1) {
        if (this.draw) {
            if (this.targetedPlanet != null) {
                Font.drawString(
                  Status.getPlanetNames()[this.targetedPlanetLocalIndex],
                  GlobalStatus.screenWidth - 2,
                  GlobalStatus.screenHeight - Font.getSpacingY() - 2,
                  1,
                  Font.TOP|Font.RIGHT
                );
            } else {
                KIPlayer var2 = this.targetedAsteroid != null ? this.targetedAsteroid : this.targetedPlayer != null ? this.targetedPlayer : this.targetedLandmark != null ? this.targetedLandmark : null;
                if (var2 != null) {
                    final boolean var3 = var2.equals(this.targetedAsteroid);
                    final boolean var4 = var2.equals(this.targetedLandmark);
                    final String var5 = this.targetedLandmark == this.landmarks[0] ? this.stationaryPlayersNames[0] : this.targetedLandmark == this.landmarks[3] ? this.stationaryPlayersNames[3] : this.stationaryPlayersNames[1];
                    if (var3) {
                        ImageFactory.drawItemFrameless(((PlayerAsteroid)var2).oreItemId, var1.items, GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getSpacingY() - 2, Graphics.BOTTOM | Graphics.RIGHT);
                        this.meteorClass.setFrame(((PlayerAsteroid)var2).getQualityFrameIndex());
                        this.meteorClass.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - 2);
                        this.meteorClass.paint(GlobalStatus.graphics);
						Font.drawString(
								GlobalStatus.gameText.getText(569 + ((PlayerAsteroid) var2).oreItemId),
								GlobalStatus.screenWidth - 4 - this.meteorClass.getHeight(),
								GlobalStatus.screenHeight - Font.getSpacingY() - 2,
								1,
								Font.TOP|Font.RIGHT
						);
						return;
                    }

                    if (!var4) {
                        this.logosSmall.setFrame(var2.race);
                        this.logosSmall.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getSpacingY() - 2);
                        this.logosSmall.paint(GlobalStatus.graphics);
                    }

                    if (var2.name != null && var2.name.equals(GlobalStatus.gameText.getText(833))) {
                        if (Layout.quickClockHigh_()) {
							Font.drawString(
									var2.name, GlobalStatus.screenWidth - 2,
									GlobalStatus.screenHeight - Font.getSpacingY() - 2,
									2,
									Font.TOP|Font.RIGHT
							);
						}
                    } else {
                        String var6 = var4 ? var5 : var2.name != null ? var2.name : GlobalStatus.gameText.getText(229 + var2.race);
                        if (!var4) {
                            var6 = var6 + " " + var2.player.getDamageRate() + "%";
                        } else {
                            if (Status.inAlienOrbit()) {
                                this.logosSmall.setFrame(9);
                            } else {
                                this.logosSmall.setFrame(Status.getSystem().getRace());
                            }

                            this.logosSmall.setRefPixelPosition(GlobalStatus.screenWidth - 2, GlobalStatus.screenHeight - Font.getSpacingY() - 2);
                            this.logosSmall.paint(GlobalStatus.graphics);
                        }

						Font.drawString(
								var6,
								GlobalStatus.screenWidth - 2,
								GlobalStatus.screenHeight - Font.getSpacingY() - 2,
								1,
								Font.TOP | Font.RIGHT
						);
                    }
                }

            }
        }
    }

    private String calcDistance(int x, int y, int z, final Player player) {
        this.playerPos = player.getPosition(this.playerPos);
        this.floatVector.x = this.playerPos.x >> 1;
        this.floatVector.y = this.playerPos.y >> 1;
        this.floatVector.z = this.playerPos.z >> 1;
        x >>= 1;
        y >>= 1;
        z >>= 1;
        final float distSqr = (
      		  (this.floatVector.x - x) * (this.floatVector.x - x) 
      		  + (this.floatVector.y - y) * (this.floatVector.y - y)
      		  + (this.floatVector.z - z) * (this.floatVector.z - z)) / AEMath.Q_1;
        // square root approximation
        // b = sqrt(distSqr)
        float a = 65536.0F;
        float b = 65536.0F;
        for(int i = 0; i < 20; ++i) {
            a *= 0.5F;
            float var10 = distSqr - b * b;
            if (var10 > 0.0F) {
                b += a;
            } else if (var10 < 0.0F) {
                b -= a;
            }
        }

        int meters = (int)b * 12; // BUG: causes incositancy with speed units, should be * 8, like in later versions
        String distance = meters + "m";
        if (meters >= 1000) {
            y = meters % 1000;
            if (y >= 100) {
                distance = "" + y;
            } else {
                distance = "0";
            }

            distance = distance.substring(0, 1);
            distance = meters / 1000 + "." + distance + "km";
        }

        return distance;
    }

    public final KIPlayer getLockedAsteroid() {
        return this.targetedAsteroid;
    }

    public final void unlockAsteroid() {
        this.targetedAsteroid = null;
    }

    public final KIPlayer getLockedEnemy() {
        return this.targetedPlayer;
    }
}
