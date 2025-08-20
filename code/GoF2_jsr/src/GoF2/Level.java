package GOF2;

import AE.AEResourceManager;
import AE.AbstractGun;
import AE.AbstractMesh;
import AE.BoundingAAB;
import AE.BoundingSphere;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.Math.AEMatrix;

public final class Level {
	private static final int STATION_TERRAN = 0;
	private static final int STATION_NIVELIAN = 1;
	private static final int STATION_VOSSK = 2;
//	private static final int STATION_DEEP_SCIENCE = 7;
//	private static final int STATION_VALKYRIE = 8;
	
	private int currentMod;
	private BoundingVolume asteroidField;
	private final int[] LEVEL_BAR_CHAR_POSITIONS; // make it static. why not?
	private AEVector3D asteroidFieldCenter;
	private Waypoint asteroidsWaypoint;
	private AbstractMesh skybox;
	private Sparks gunSparks;
	private Sparks missilesSparks;
	private AbstractMesh[] egoGuns;
	private AbstractMesh[] enemyGuns;
	private StarSystem starSystem;
	private PlayerEgo ego;
	private KIPlayer[] ships;
	private KIPlayer[] asteroids;
	private KIPlayer[] stationaries;
	private Route missionRoute;
	private Route kamikazePath;
	private Route enemyRoute_;
	private RadioMessage[] radioMessages;
	private int enemiesIntact;
	private int friendsAlive;
	private int asteroidsIntact;
	private int enemiesDestroyed;
	public int capturedCargoCount;
	public int challengerScore;
	public int egoScore;
	public Objective successObjective;
	public Objective failObjective_;
	private int timeLimit;
	private int spaceCreationStep__;
	private boolean spaceCreated_;
	private int mgameIntroCamRotY;
	private boolean stolenFromFriend;
	public static float starR;
	public static float starG;
	public static float starB;
	public static int spaceR;
	public static int spaceG;
	public static int spaceB;
	public static int bgR;
	public static int bgG;
	public static int bgB;
	private float explosionR;
	private float explosionG;
	private float explosionB;
	private int flashIntensity;
	private int lastFlashDuration;
	private boolean exploding_;
	private int flashType;
	private int localFightersCnt;
	private int jumperCnt;
	private int bigShipsCnt;
	private int raidersCnt;
	private int alienRespawnTick;
	private int commonRespawnTick;
	private int jumperRespawnTick;
	private Route raidRoute;
	private int raidWavesCounter;
	private boolean friendlyFireAlerted;
	private boolean localsAlarmed;
	public static boolean initStreamOutPosition = false;
	public static boolean comingFromAlienWorld = false;
	public static int lastMissionFreighterHitpoints;
	public static Station programmedStation;
	public static boolean driveJumping = false;
	private AEVector3D tempVec;

	public Level() {
		this(3);
	}

	public Level(final int var1) {
		this.LEVEL_BAR_CHAR_POSITIONS = new int[] {
		      630, 0, 175,
		      1570, 0, 340,
		      1024, 0, 600,
		      1260, 0, 650,
		      880, 0, 950
		      };
		this.exploding_ = false;
		this.tempVec = new AEVector3D();
		this.currentMod = var1;
		this.spaceCreationStep__ = 0;
		this.spaceCreated_ = false;
	}

	public static void setInitStreamOut() {
		initStreamOutPosition = true;
	}

	public final boolean createSpace() {
		if (this.spaceCreationStep__ == 0 || this.spaceCreated_) {
			this.missionRoute = null;
			this.enemyRoute_ = null;
			this.kamikazePath = null;
			this.failObjective_ = null;
			this.successObjective = null;
			this.gunSparks = new Sparks(AEResourceManager.getTextureResource(1), 33, 225, 63, 255, 10, 700, 100, 500);
			this.missilesSparks = new Sparks(AEResourceManager.getTextureResource(1), 33, 225, 63, 255, 10, 700, 100, 500);
			if (this.skybox == null) {
				this.skybox = AEResourceManager.getGeometryResource(9991);
			}

			// not ModStation, SpaceLounge
			if (this.currentMod != 23 && this.currentMod != 4) {
				boolean wormholeActive = comingFromAlienWorld || Status.getStation().isAttackedByAliens() || Status.inAlienOrbit();
				if (Status.getCurrentCampaignMission() > 42) {
					wormholeActive = false;
				}

				this.stationaries = new KIPlayer[4];
				if (Status.inEmptyOrbit()) {
					this.stationaries[0] = null;
				} else {
					this.stationaries[0] = new PlayerStation(Status.getStation());
				}

				AEVector3D var3 = new AEVector3D(this.tempVec);
				this.mgameIntroCamRotY = 0;
				GlobalStatus.random.setSeed(Status.getStation() != null ? (long)(Status.getStation().getIndex() << 1) : -1L);
				for(int i = 1; i < 3; i++) {
					if (i == 1 && (Status.getSystem() == null || !Status.getSystem().currentOrbitHasJumpgate())) {
						this.stationaries[i] = null;
					} else {
						if (Status.getStation() != null) {
							if (i == 0) {
								this.mgameIntroCamRotY = Status.getStation().getName().length() + Status.getStation().getTecLevel() * 3;
								this.mgameIntroCamRotY = this.mgameIntroCamRotY % 16 << 8;
							} else {
								this.mgameIntroCamRotY += GlobalStatus.random.nextInt(2) == 0 ?
																-250 - GlobalStatus.random.nextInt(500):
																 250 + GlobalStatus.random.nextInt(500);
							}

							this.tempVec.z = i == 1 ? 90000 : 120000;
							final AEVector3D var10000 = this.tempVec;
							var10000.z += this.mgameIntroCamRotY * 3;
							AEMatrix var5 = new AEMatrix();
							var5.setEulerY(this.mgameIntroCamRotY);
							var3 = var5.transformVectorNoScale(this.tempVec, var3);
							var3.y = 0;
						}

						this.stationaries[i] = new PlayerJumpgate(
								15, AEResourceManager.getGeometryResource(15),
								var3.x,  var3.y, var3.z, i != 2);
					}
				}
				GlobalStatus.random.setSeed(System.currentTimeMillis());
				if (Status.inAlienOrbit()) {
					final int x,y,z;
					x = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (50000 + GlobalStatus.random.nextInt(50000));
					y = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (50000 + GlobalStatus.random.nextInt(50000));
					z = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (50000 + GlobalStatus.random.nextInt(50000));
					this.stationaries[2].setPosition(x, y, z);
					this.stationaries[2].mainMesh_.setRotation(
							-AEMath.Q_PI + GlobalStatus.random.nextInt(AEMath.Q_PI_2),
							-AEMath.Q_PI + GlobalStatus.random.nextInt(AEMath.Q_PI_2),
							-AEMath.Q_PI + GlobalStatus.random.nextInt(AEMath.Q_PI_2));
				}

				if (!Status.gameWon()) {
					this.stationaries[3] = new PlayerWormHole(
						 6805, AEResourceManager.getGeometryResource(6805),
				      -40000 + GlobalStatus.random.nextInt(80000),
				      -20000 + GlobalStatus.random.nextInt(40000),
				       40000 + GlobalStatus.random.nextInt(40000), wormholeActive);
					this.stationaries[3].setLevel(this);
				}

				this.mgameIntroCamRotY += AEMath.Q_PI_HALF;
				if (!initStreamOutPosition) {
					this.mgameIntroCamRotY = 0;
				}
			}

			this.starSystem = new StarSystem();
			final SolarSystem currSys = Status.getSystem();
			if (currSys == null) {
				starR = 10.0F;
				starG = 136.0F;
				starB = 10.0F;
			} else {
				starR = currSys.starR;
				starG = currSys.starG;
				starB = currSys.starB;
			}

			spaceR = (int)(starR / 3.0F);
			spaceG = (int)(starG / 3.0F);
			spaceB = (int)(starB / 3.0F);
			if (this.currentMod == 3) {
				try {
					final Ship egoShip = Status.getShip();
					final Item[] eqdItems = Status.getShip().getEquipment();
					int[] eqdGunsCounts = new int[3];
					eqdGunsCounts[0] = egoShip.getUsedSlots(Item.PRIMARY);
					eqdGunsCounts[1] = egoShip.getUsedSlots(Item.SECONDARY);
					eqdGunsCounts[2] = egoShip.getUsedSlots(Item.TURRET);
					Player player = new Player(1200.0F, Status.getShip().getBaseArmour(), eqdGunsCounts[0], eqdGunsCounts[1], eqdGunsCounts[2]);
					player.setMaxShieldHP(Status.getShip().getShield());
					player.setMaxArmorHP(Status.getShip().getAdditionalArmour());
					this.ego = new PlayerEgo(player);
					this.ego.setShip(Status.getShip().getIndex(), Status.getShip().getRace());
					this.ego.setLevel(this);
					this.ego.shipGrandGroup_.setRotation(0, this.mgameIntroCamRotY, 0);
					final int[] wtf = {0, 0, 0}; // legacy code propably
					final Explosion explosion = new Explosion(wtf.length / 3);
					this.ego.setExplosion(explosion);
					final Gun[][] eqdGuns = new Gun[3][];

					for(int i = 0; i < 3; ++i) {
						if (eqdGunsCounts[i] > 0) {
							eqdGuns[i] = new Gun[eqdGunsCounts[i]];
						}
					}

					if (eqdItems != null) {
						for(int i = 0; i < eqdItems.length; ++i) {
							if (eqdItems[i] != null) {
								Gun gun = null;
								if (eqdItems[i].isWeapon()) {
									final int ammount = eqdItems[i].getType() == 1 ? eqdItems[i].getAmount() : -1;
									gun  = this.createGun(
											eqdItems[i].getIndex(), i, eqdItems[i].getSort(), ammount,
									      eqdItems[i].getAttribute(Item.DAMAGE),  eqdItems[i].getAttribute(Item.RELOAD),
									      eqdItems[i].getAttribute(Item.RANGE), eqdItems[i].getAttribute(Item.VELOCITY));
									gun.index = eqdItems[i].getIndex();
									gun.subType = eqdItems[i].getSort();
									gun.setMagnitude(eqdItems[i].getAttribute(Item.EXPLOSION_RANGE));

									switch(eqdItems[i].getType()) {
									case Item.PRIMARY:
										gun.setOffset(eqdGunsCounts[0] - 1, egoShip.getUsedSlots(Item.PRIMARY));
										eqdGuns[Item.PRIMARY][--eqdGunsCounts[0]] = gun;
										break;
									case Item.SECONDARY:
										eqdGuns[Item.SECONDARY][--eqdGunsCounts[1]] = gun;
										break;
									case Item.TURRET:
										eqdGuns[Item.TURRET][--eqdGunsCounts[2]] = gun;
									}
								}
							}
						}
					}

					for(int i = 0; i < eqdGuns.length; ++i) {
						if (eqdGuns[i] != null) {
							this.ego.setGuns(eqdGuns[i], i);
						}
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}

				if (initStreamOutPosition && this.ego.shipGrandGroup_ != null) {
					Station[] lastVisited;
					if ((lastVisited = Status.getStationStack()) != null && lastVisited[1] != null
					      && Status.getSystem() != null && !Status.getSystem().currentOrbitHasJumpgate()) {
						final int[] var12 = Status.getSystem().getStations();
						int var14 = 0;

						for(int i = 0; i < var12.length; ++i) {
							if (var12[i] == lastVisited[1].getIndex()) {
								var14 = i;
								break;
							}
						}

						AEVector3D var19 = new AEVector3D(this.starSystem.getPlanets()[var14 + 1].getLocalPos());
						var19.scale(AEMath.Q_4);
						this.ego.setPosition_(var19);
						var19.x = -var19.x;
						var19.y = -var19.y;
						var19.z = -var19.z;
						var19.normalize();
						this.ego.shipGrandGroup_.getToParentTransform().setOrientation(var19);
					} else {
						this.ego.setPosition_(
								this.stationaries[2].mainMesh_.getPosX(),
						      this.stationaries[2].mainMesh_.getPosY(),
						      this.stationaries[2].mainMesh_.getPosZ());
					}
				} else {
					this.ego.setPosition_(10, 10, 10000);
				}

				if (Status.getCurrentCampaignMission() == 1) {
					this.ego.setPosition_(0, 0, -110000);
				}
			}
		}

		if (this.spaceCreationStep__ != 1 && !this.spaceCreated_) {
			if (this.spaceCreated_) {
				this.spaceCreationStep__ = 0;
				return true;
			}
		} else {
			if (this.currentMod != 4) {
				createAsteroids();
			}

			if (Status.getMission() == null) {
				Status.setMission(Mission.emptyMission_);
			}

			if ((this.currentMod != 3 || Status.getMission().isCampaignMission()) && (this.currentMod != 3 || !Status.gameWon())) {
				if (this.currentMod != 3) {
					createScene();
					this.currentMod = 3;
				} else if (!Status.getMission().isEmpty() && Status.getMission().isCampaignMission()) {
					createCampaignMission();
				}
			} else {
				createMission();
			}

			createWingmen();
			assignGuns();
			connectPlayers();
			if (this.ego != null) {
				this.ego.setRoute(this.missionRoute);
			}

			this.enemiesIntact = this.ships != null ? this.ships.length : 0;
			this.asteroidsIntact = 0;
			this.asteroidsIntact = this.asteroids != null ? this.asteroids.length : 0;
			this.raidWavesCounter = 0;
			this.friendlyFireAlerted = false;
			this.localsAlarmed = false;
			this.capturedCargoCount = 0;
			this.enemiesDestroyed = 0;
			this.stolenFromFriend = false;
			this.spaceCreated_ = true;
		}

		++this.spaceCreationStep__;
		return this.spaceCreated_;
	}

	private void createAsteroids() {
		int ore = 154;
		int j = 0;
		boolean oreGood = false;
		this.asteroids = new KIPlayer[50];
		final int[] orePropabilites = Galaxy.getAsteroidProbabilities(Status.getStation());
		GlobalStatus.random.setSeed(Status.getStation().getIndex());
		final int field_x = -50000 + GlobalStatus.random.nextInt(100000);
		final int field_y = -50000 + GlobalStatus.random.nextInt(100000);
		final int field_z = 10000 + GlobalStatus.random.nextInt(100000);
		GlobalStatus.random.setSeed(System.currentTimeMillis());
		this.asteroidFieldCenter = new AEVector3D(field_x, field_y, field_z);
		this.asteroidsWaypoint = new Waypoint(field_x, field_y, field_z, (Route)null);
		this.asteroidField = new BoundingSphere(field_x, field_y, field_z, 0, 0, 0, 50000);

		for(int i = 0; i < this.asteroids.length; ++i) {
			if (Status.inAlienOrbit()) {
				ore = 164;
			} else if (Status.inEmptyOrbit()) {
				ore = 154;
			} else {
				oreGood = false;

				while(!oreGood) {
					if (GlobalStatus.random.nextInt(100) < orePropabilites[j + 1]) {
						ore = orePropabilites[j];
						if (ore < 164) {
							oreGood = true;
						}

						j += 2;
						if (j >= orePropabilites.length) {
							j = 0;
						}
					} else {
						j = 0;
					}
				}
			}

			boolean closeStation = i < this.asteroids.length / 2;
			Waypoint wayPoint = null;
			if (closeStation) {
				wayPoint = new Waypoint(0, 0, 20000, (Route)null); // here is why asteroids spawn in hangar
			} else {
				wayPoint = new Waypoint(field_x, field_y, field_z, (Route)null);
			}

			final int meshResId = Status.inAlienOrbit() ? 6804 : 6769;
			final int x = wayPoint.x - 30000 + GlobalStatus.random.nextInt(60000);
			final int y = wayPoint.y - 30000 + GlobalStatus.random.nextInt(60000);
			final int z = wayPoint.z - 30000 + GlobalStatus.random.nextInt(60000); 
			final PlayerAsteroid asteroid = new PlayerAsteroid(
					meshResId, AEResourceManager.getGeometryResource(meshResId),
					ore, closeStation, x, y, z);
			asteroid.setLevel(this);
			asteroid.setAsteroidCenter(this.asteroidFieldCenter);
			if (this.currentMod == 23) { // hangar
				asteroid.mainMesh_.setRenderLayer(1);
			} else {
				asteroid.mainMesh_.setRenderLayer(2);
			}
			asteroid.setExplosion(new Explosion(1));
			this.asteroids[i] = asteroid;
		}

	}

	private Gun createGun(final int itemIdx, final int equipmentIdx, int type, final int ammo, final int dmg, final int reload, final int range, final int velocity) {
		Gun gun = null;
		Object gunObject = null;
		Sparks sparks = this.gunSparks;
		AbstractMesh gunGeometry;
		switch(type) {
		case Item.LASER:
		case Item.BLASTER:
		case Item.THERMO:
			if (Globals.TYPE_WEAPONS[itemIdx] >= 0) {
				final int z = type == 0 ? 800 : 400;
				gun = new Gun(equipmentIdx, dmg, 15, ammo, range, reload, velocity, new AEVector3D(0, 0, z), new AEVector3D());
				gunGeometry = AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[itemIdx]);
				if (type == Item.THERMO) {
					final int scale = 300 + 70 * (itemIdx - 28); // 1st thermo idx
					gunGeometry.setScale(scale, scale, scale);
					gunGeometry.setAnimationRangeInTime(itemIdx - 28 + 10, itemIdx - 28 + 10);
					gunGeometry.setAnimationMode((byte)1);
					gun = new Gun(equipmentIdx, dmg, 15, ammo, range, reload, velocity, new AEVector3D(0, 0, 200), new AEVector3D());
					gun.setSpread(20);
				}

				gunObject = new ObjectGun(gun, gunGeometry);
			} else { // Laser
				gun = new Gun(equipmentIdx, dmg, 1, ammo, range, reload, velocity, new AEVector3D(0, 0, 400), new AEVector3D());
				gunObject = new LaserGun(gun, itemIdx, this);
			}
			break;
		case Item.AUTOCANNON:
			gun = new Gun(equipmentIdx, dmg, 15, ammo, range, reload, velocity, new AEVector3D(0, 0, 200), new AEVector3D());
			gunObject = new ObjectGun(gun, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[itemIdx]));
			break;
		case Item.ROCKET:
		case Item.TORPEDO:
			gun = new Gun(equipmentIdx, dmg, 1, ammo, range, reload, velocity, new AEVector3D(), new AEVector3D());
			gunGeometry = AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[itemIdx]);
			gunGeometry.setRenderLayer(2);
			gunObject = new RocketGun(gun, gunGeometry, type == 5);
			sparks = this.missilesSparks;
			break;
		case Item.EMP_BOMB:
		case Item.NUKE:
			gun = new Gun(equipmentIdx, dmg, 1, ammo, range, reload, velocity, new AEVector3D(0, -200, 400), new AEVector3D());
			AbstractMesh var12 = null;
			int var13;
			if (type == Item.NUKE) {
				var12 = AEResourceManager.getGeometryResource(18); 
				var13 = itemIdx - 44 + 1 << 9;
			} else { // EMP Bomb
				var12 = AEResourceManager.getGeometryResource(16);
				var13 = itemIdx - 41 + 1 << 9;
			}
			var12.setScale(var13, var13, var13);

			var12.setRenderLayer(2);
			gunObject = new RocketGun(gun, var12, false);
			sparks = this.missilesSparks;
			break;
		case Item.TURRET_SORT:
			gun = new Gun(equipmentIdx, dmg, 15, ammo, range, reload, velocity, new AEVector3D(0, 0, 0), new AEVector3D());
			gunObject = new ObjectGun(gun, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[itemIdx]));
		}

		gun.setLevel(this);
		gun.setSparks(sparks);
		if (this.egoGuns == null) {
			this.egoGuns = new AbstractMesh[]{(AbstractMesh)gunObject};
		} else {
			final AbstractMesh[] newGuns = new AbstractMesh[this.egoGuns.length + 1];
			System.arraycopy(this.egoGuns, 0, newGuns, 0, this.egoGuns.length);
			newGuns[newGuns.length - 1] = (AbstractMesh)gunObject;
			this.egoGuns = newGuns;
		}

		return gun;
	}

	private void createWingmen() {
		if (Status.getWingmenNames() != null && Status.wingmenTimeRemaining <= 0) {
			Status.setWingmenNames((String[])null);
			Status.wingmenTimeRemaining = 0;
			Status.wingmanFace = null;
		} else if (Status.getWingmenNames() != null && this.ego != null) {
			final KIPlayer[] wingmen = new KIPlayer[Status.getWingmenNames().length];

			for(int id = 0; id < wingmen.length; ++id) {
				GlobalStatus.random.setSeed(Status.getWingmenNames()[id].length() * 5);
				GlobalStatus.random.setSeed(System.currentTimeMillis());
				wingmen[id] = createShip(5, 0, Globals.getRandomEnemyFighter(Status.wingmanRace), (Waypoint)null);
				if (initStreamOutPosition && this.ego.shipGrandGroup_ != null) {
					((PlayerFighter) wingmen[id]).setPosition(
							this.stationaries[2].mainMesh_.getPosX() - 3000,
					      this.stationaries[2].mainMesh_.getPosY() + 1000,
					      this.stationaries[2].mainMesh_.getPosZ() + 5000);
				}
				else {
					((PlayerFighter) wingmen[id]).setPosition(
					      this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400),
					      this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400),
					      this.ego.shipGrandGroup_.getPosZ() + 2000);
				}

				wingmen[id].setWingman(true, id);
				wingmen[id].player.setAlwaysFriend(true);
				wingmen[id].player.setHitPoints(600);
				wingmen[id].name = Status.getWingmenNames()[id];
				wingmen[id].race = Status.wingmanRace;
				if (Status.getMission().getType() == 12) {
					wingmen[id].armed = false;
				}
			}

			if (this.ships != null) {
				final KIPlayer[] newShips = new KIPlayer[this.ships.length + wingmen.length];
				System.arraycopy(this.ships, 0, newShips, 0, this.ships.length);
				System.arraycopy(wingmen, 0, newShips, this.ships.length, wingmen.length);
				this.ships = newShips;
				return;
			}

			this.ships = wingmen;
		}
	}

	private void createMission() {
		Mission mission = Status.getMission();
		if (mission != null) {
			if (Status.inAlienOrbit()) {
				final int alienCnt = 1 + GlobalStatus.random.nextInt(3);
				this.ships = new KIPlayer[alienCnt];

				for(int i = 0; i < alienCnt; ++i) {
					this.ships[i] = createShip(9, 0, Globals.getRandomEnemyFighter(9), (Waypoint)null);
					this.ships[i].setPosition(
							-40000 + GlobalStatus.random.nextInt(80000),
					      -40000 + GlobalStatus.random.nextInt(80000),
					      -40000 + GlobalStatus.random.nextInt(80000));
					this.ships[i].player.setAlwaysEnemy(true);
				}

			} else {
				final int attackersCnt, attackerRace;
				int pirateCnt;
				Route route;
				if (mission.isEmpty()) {
					this.currentMod = 0;
					createScene();
					this.currentMod = 3;
					final boolean introOrThynome = Status.getSystem().getIndex() == 15 && Status.getCurrentCampaignMission() < 16;
					final boolean varHastra = Status.getStation().getIndex() == 78;
					pirateCnt = 0;
					Mission freelance;
					if ((freelance = Status.getFreelanceMission()) != null &&
							(freelance.getType() == 0 || // Courier
					      freelance.getType() == 11)) { // Passenger/Reach destination
						pirateCnt = (int) (5.0F * freelance.getDifficulty() / 10.0F); // just >>2
					}

					final int safety = Status.getSystem().getSafety();
					final boolean raid = !introOrThynome &&
							GlobalStatus.random.nextInt(100) < (
									safety == 0 ? 80 :
									safety == 1 ? 60 :
									safety == 2 ? 35 :
													  10);
					this.raidRoute = new Route(new int[] {
					      -50000 + GlobalStatus.random.nextInt(100000),
					      							0,
					      50000 + GlobalStatus.random.nextInt(50000)});
					final int localRace = Status.getSystem().getRace();
					attackerRace = GlobalStatus.random.nextInt(100) < 75 ? 8 : Standing.enemyRace(localRace);
					this.raidersCnt = raid ? 0 + GlobalStatus.random.nextInt(4) : 0;
					this.jumperCnt = varHastra ? 0 : 0 + GlobalStatus.random.nextInt(2);
					this.bigShipsCnt = !varHastra && !introOrThynome ? 0 + GlobalStatus.random.nextInt(5) : 0;
					this.localFightersCnt = (varHastra ? 0 : GlobalStatus.random.nextInt(2)) + (introOrThynome ? 0 : safety) + this.bigShipsCnt / 4;
					if (Status.getStation().getIndex() == 10 
							|| this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt + pirateCnt == 0) {
						this.localFightersCnt = 4;
					}

					this.ships = new KIPlayer[this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt + pirateCnt];
					route = new Route(new int[]{0, 0, 10000});

					for(int i = 0; i < this.localFightersCnt; ++i) {
						this.ships[i] = createShip(localRace, 0, Globals.getRandomEnemyFighter(localRace), route.getDockingTarget_());
					}

					AbstractMesh var14 = null;
					if (this.jumperCnt > 0) {
						(var14 = AEResourceManager.getGeometryResource(6783)).setRenderLayer(2);
					}

					for(int i = this.localFightersCnt; i < this.localFightersCnt + this.jumperCnt; ++i) {
						this.ships[i] = createShip(localRace, 0, Globals.getRandomEnemyFighter(attackerRace), (Waypoint)null);
						this.ships[i].setDead();
						final Route var19 = new Route(new int[] {
						      -200000 + GlobalStatus.random.nextInt(400000),
						      -100000 + GlobalStatus.random.nextInt(200000),
						      50000 + GlobalStatus.random.nextInt(100000)});
						this.ships[i].setRoute(var19);
						this.ships[i].setJumper(true);
						this.ships[i].setJumpMesh(var14);
					}

					final boolean var18 = localRace == Globals.TERRAN && GlobalStatus.random.nextInt(100) < 30;

					for(int i = this.localFightersCnt + this.jumperCnt; i < this.localFightersCnt + this.jumperCnt + this.bigShipsCnt; ++i) {
						if (var18 && i == this.localFightersCnt + this.jumperCnt) {
							this.ships[i] = createShip(localRace, 1, 14, (Waypoint)null);
							((PlayerFixedObject)this.ships[i]).setMoving(false);
							this.ships[i].setPosition(
									  -40000 + GlobalStatus.random.nextInt(80000),
										-5000 + GlobalStatus.random.nextInt(10000),
										40000 + GlobalStatus.random.nextInt(80000)
									);
						} else {
							this.ships[i] = createShip(localRace, 1, localRace == Globals.VOSSK ? 13 : 15, (Waypoint)null);
							((PlayerFixedObject)this.ships[i]).setMoving(true);
							this.ships[i].setPosition(
									  (-80000 + GlobalStatus.random.nextInt(60000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1),
										-20000 + GlobalStatus.random.nextInt(40000),
										-80000 + GlobalStatus.random.nextInt(160000)
									);
						}
					}

					if (raid) {
						attackersCnt = Globals.getRandomEnemyFighter(attackerRace);

						for(int i = this.localFightersCnt + this.jumperCnt + this.bigShipsCnt; i < this.ships.length - pirateCnt; ++i) {
							this.ships[i] = createShip(attackerRace, 0, attackersCnt, this.raidRoute.getDockingTarget_());
						}
					}

					for(int i = this.localFightersCnt + this.jumperCnt + this.bigShipsCnt + this.raidersCnt; i < this.ships.length; ++i) {
						this.ships[i] = createShip(8, 0, Globals.getRandomEnemyFighter(8), (Waypoint)null);
						this.tempVec = this.ego.getPosition();
						this.ships[i].setPosition(
										-30000 + this.tempVec.x + GlobalStatus.random.nextInt(60000),
										-30000 + this.tempVec.y + GlobalStatus.random.nextInt(60000),
										-30000 + this.tempVec.z + GlobalStatus.random.nextInt(60000)
									);
					}

				} else {
					final int enemyRace, supportCnt;
					final int localRace = Status.getSystem().getRace();
					attackerRace = GlobalStatus.random.nextInt(100) < 75 ? 8 : Standing.enemyRace(localRace);
					final boolean attackFromBehind = GlobalStatus.random.nextInt(2) == 0;
					switch(mission.getType()) {
					case 1: //Defense
						this.enemyRoute_ = new Route(new int[]{
								-50000 + GlobalStatus.random.nextInt(100000), 0, attackFromBehind ? -50000 : 50000,
								-50000 + GlobalStatus.random.nextInt(100000), 0, attackFromBehind ? -75000 : 75000,
								-50000 + GlobalStatus.random.nextInt(100000), 0, attackFromBehind ? -100000 : 100000});
						attackersCnt = 3 + (int)(5.0F * (Status.getMission().getDifficulty() / 10.0F));
						supportCnt = 2 + GlobalStatus.random.nextInt(6);
						this.ships = new KIPlayer[attackersCnt + supportCnt];

						for(int i = 0; i < attackersCnt; ++i) {
							this.ships[i] = createShip(attackerRace, 0, Globals.getRandomEnemyFighter(attackerRace), (Waypoint)null);
							this.ships[i].player.setAlwaysEnemy(true);
						}

						for(int i = attackersCnt; i < this.ships.length; ++i) {
							this.ships[i] = createShip(localRace, 0, Globals.getRandomEnemyFighter(localRace),
							      this.enemyRoute_.getWaypoint(GlobalStatus.random.nextInt(this.enemyRoute_.length())));
							this.ships[i].player.setAlwaysFriend(true);
						}

						this.successObjective = new Objective(7, attackersCnt, this);
						return;
					case 2: //Protection
						final AEVector3D stationPos = new AEVector3D();
						this.enemyRoute_ = new Route(new int[]{
							stationPos.x + (attackFromBehind ? -1 : 1) * (20000 + GlobalStatus.random.nextInt(20000)),
							stationPos.y, 
							stationPos.z + (attackFromBehind ? -1 : 1) * (20000 + GlobalStatus.random.nextInt(20000)),
							stationPos.x,
							stationPos.y,
							stationPos.z
							});
						supportCnt = mission.getCommodityAmount_();
						attackersCnt = 2 + (int)(4.0F * (mission.getDifficulty() / 10.0F));
						this.ships = new KIPlayer[attackersCnt + supportCnt];

						for(int i = 0; i < attackersCnt; ++i) {
							this.ships[i] = createShip(attackerRace, 0, Globals.getRandomEnemyFighter(attackerRace), (Waypoint)null);
							((PlayerFighter) this.ships[i]).setPosition(this.enemyRoute_.getWaypoint(0).x + i * 2000,
							      this.enemyRoute_.getWaypoint(0).y + i * 2000, this.enemyRoute_.getWaypoint(0).z + i * 2000);
							this.ships[i].player.setAlwaysEnemy(true);
							this.ships[i].setRoute(this.enemyRoute_.clone());
							this.ships[i].getRoute().reachWaypoint_(0);
						}

						int k = 0;

						for(int i = attackersCnt; i < this.ships.length; ++i) {
							this.ships[i] = createShip(localRace, 0, Globals.getRandomEnemyFighter(localRace), (Waypoint)null);
							this.ships[i].player.setAlwaysFriend(true);
							final int j = this.asteroids.length / 2 + k;
							k++;
							stationPos.set(this.asteroids[j].getPosition(stationPos));
							this.ships[i].setPosition(stationPos.x, stationPos.y + 2000, stationPos.z);
							this.ships[i].hasCargo = false;
							this.ships[i].cargo = null;
							this.ships[i].setSpeed(0);
							this.ships[i].player.setHitPoints(this.ships[i].player.getMaxHitpoints() * 3);
						}

						this.successObjective = new Objective(18, 0, attackersCnt, this);
						this.failObjective_ = new Objective(18, attackersCnt, attackersCnt + supportCnt, this);
						return;
					case 4: // Pirate hunting
						if (GlobalStatus.random.nextInt(2) == 0) {
							this.missionRoute = new Route(new int[]{this.asteroidFieldCenter.x, this.asteroidFieldCenter.y, this.asteroidFieldCenter.z});
						} else {
							this.missionRoute = createRoute(2 + GlobalStatus.random.nextInt(2));
						}

						pirateCnt = 2 + (int)((Status.getMission().getDifficulty() * 5.0F / 10.0F));
						this.ships = new KIPlayer[pirateCnt];

						for(int i = 0; i < this.ships.length; ++i) {
							this.ships[i] = createShip(8, 0, Globals.getRandomEnemyFighter(8),
							      this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
							this.ships[i].setToSleep();
						}

						this.successObjective = new Objective(18, 0, pirateCnt, this);
						return;
					case 3: // Recovery
					case 5: // Salvage
						this.missionRoute = new Route(new int[] {
						      (40000 + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1),
						      0,
						      (40000 + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1)});
						this.ships = new KIPlayer[mission.getCommodityAmount_()];

						for(int i = 0; i < this.ships.length; ++i) {
							this.ships[i] = createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(0));
							this.ships[i].setToSleep();
						}

						((PlayerFighter)this.ships[this.ships.length - 1]).setMissionCrate(true);
						((PlayerFighter)this.ships[this.ships.length - 1]).name = GlobalStatus.gameText.getText(833); //Kidnpapper
						this.successObjective = new Objective(11, this.ships.length - 1, this);
						this.failObjective_ = new Objective(12, this.ships.length - 1, this);
						return;
					case 6: // Wanted
						this.missionRoute = new Route(new int[] {
						      (60000 + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1),
						      0,
						      (60000 + GlobalStatus.random.nextInt(80000)) * (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1)});
						this.ships = new KIPlayer[1];
						this.ships[0] = createShip(8, 0, Globals.getRandomEnemyFighter(8), this.missionRoute.getWaypoint(0));
						this.ships[0].setToSleep();
						final int scalingHP = Status.getMission().getDifficulty() * AEMath.min(Status.getLevel(), 20);
						this.ships[0].player.setMaxHP(scalingHP + 300);
						this.successObjective = new Objective(1, 0, this);
						return;
					case 7: // Junk removal
						route = new Route(new int[]{-20000 + GlobalStatus.random.nextInt(40000), 0, 20000 + GlobalStatus.random.nextInt(40000)});
						pirateCnt = (int)(2.0F * (Status.getMission().getDifficulty() / 10.0F));
						final int junkCnt = 15 + (int)(35.0F * (Status.getMission().getDifficulty() / 10.0F));
						this.ships = new KIPlayer[junkCnt + pirateCnt];

						for(int i = 0; i < this.ships.length - pirateCnt; ++i) {
							this.ships[i] = createJunk(route.getWaypoint(0), 9996);
							this.ships[i].player.setAlwaysEnemy(true);
						}

						for(int i = this.ships.length - pirateCnt; i < this.ships.length; ++i) {
							this.ships[i] = createShip(8, 0, Globals.getRandomEnemyFighter(8), (Waypoint)null);
						}

						this.successObjective = new Objective(7, this.ships.length - pirateCnt, this);
						this.timeLimit = 121000;
						return;
					case 9: // Escort
						this.enemyRoute_ = new Route(new int[]{10000, 0, 100000, 10000, 0, 150000, 10000, 0, 200000});
						attackersCnt = 2 + (int)(6.0F * (Status.getMission().getDifficulty() / 10.0F));
						this.ships = new KIPlayer[attackersCnt + 5];
						enemyRace = Standing.enemyRace(mission.getClientRace());
						final int clientRace = mission.getClientRace();

						for(int i = 0; i < this.ships.length; ++i) {
							if (i < attackersCnt) {
								this.ships[i] = createShip(enemyRace, 0, Globals.getRandomEnemyFighter(enemyRace),
								      this.enemyRoute_.getWaypoint(GlobalStatus.random.nextInt(this.enemyRoute_.length())));
								this.ships[i].setToSleep();
								this.ships[i].player.setAlwaysEnemy(true);
							} else {
								this.ships[i] = createShip(clientRace, 1, clientRace == 1 ? 13 : 15, (Waypoint)null);
								this.ships[i].player.setMaxHP(100 + (AEMath.min(Status.getLevel(), 20) << 1) + (Status.getCurrentCampaignMission() << 1));
								((PlayerFixedObject)this.ships[i]).setMoving(true);
								this.ships[i].player.setAlwaysFriend(true);
								this.ships[i].hasCargo = false;
								this.ships[i].cargo = null;
							}
						}

						((PlayerFixedObject)this.ships[	attackersCnt  ]).setPosition(-2500, -300, 27000);
						((PlayerFixedObject)this.ships[attackersCnt + 1]).setPosition(6500, 3000, 24000);
						((PlayerFixedObject)this.ships[attackersCnt + 2]).setPosition(-4000, -2000, 19000);
						((PlayerFixedObject)this.ships[attackersCnt + 3]).setPosition(9000, -6000, 17000);
						((PlayerFixedObject)this.ships[attackersCnt + 4]).setPosition(3000, 7000, 15000);
						this.successObjective = new Objective(18, 0, attackersCnt, this);
						this.failObjective_ = new Objective(18, attackersCnt, attackersCnt + 5, this);
						return;
					case 10: //Intercept
						this.missionRoute = new Route(new int[] {
						      -2500 + GlobalStatus.random.nextInt(5000),
						      -2500 + GlobalStatus.random.nextInt(5000),
						      80000 + GlobalStatus.random.nextInt(30000),
						      -2500 + GlobalStatus.random.nextInt(5000),
						      -2500 + GlobalStatus.random.nextInt(5000),
						      120000 + GlobalStatus.random.nextInt(30000)});
						final int cargoShipsCnt = 2 + GlobalStatus.random.nextInt(2);
						final int enemyShipCnt = 2 + (int)((Status.getMission().getDifficulty() * 2.0F / 10.0F));
						enemyRace = Standing.enemyRace(mission.getClientRace());
						this.ships = new KIPlayer[cargoShipsCnt + enemyShipCnt];

						for(int i = 0; i < cargoShipsCnt; ++i) {
							this.ships[i] = createShip(enemyRace, 1, enemyRace == 1 ? 13 : 15, this.missionRoute.getWaypoint(1));
							this.ships[i].setToSleep();
							this.ships[i].player.setAlwaysEnemy(true);
							((PlayerFixedObject) this.ships[i]).setMoving(false);
							((PlayerFixedObject) this.ships[i]).setPosition(
							      this.missionRoute.getWaypoint(1).x + -10000 + GlobalStatus.random.nextInt(20000),
							      this.missionRoute.getWaypoint(1).y + -10000 + GlobalStatus.random.nextInt(20000),
							      this.missionRoute.getWaypoint(1).z + -10000 + GlobalStatus.random.nextInt(20000));
						}

						for(int i = cargoShipsCnt; i < this.ships.length; ++i) {
							this.ships[i] = createShip(enemyRace, 0, Globals.getRandomEnemyFighter(enemyRace),
							      this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
							this.ships[i].setToSleep();
							this.ships[i].player.setAlwaysEnemy(true);
						}

						this.successObjective = new Objective(7, cargoShipsCnt, this);
					case 8: // Purchase
					case 11: // Passenger/Reach destination
					default:	
						return;
					case 12: //Challenge
						this.missionRoute = createRoute(3 + GlobalStatus.random.nextInt(2));
						pirateCnt = 3 + (int)(4.0F * (mission.getDifficulty() / 10.0F));
						if (pirateCnt % 2 == 0) {
							++pirateCnt;
						}

						if (mission.isCampaignMission()) {
							pirateCnt = 7;
							this.missionRoute = new Route(new int[] {
							        80000,  -20000,  80000,
							        70000, 	 0,   -80000,
							      -100000,   10000, -80000,
							       -80000,   20000,  90000});
						}

						this.ships = new KIPlayer[pirateCnt + 1];
						this.ships[0] = createShip(mission.getAgent().getRace(), 0, Globals.getRandomEnemyFighter(mission.getAgent().getRace()), (Waypoint)null);
						((PlayerFighter) this.ships[0]).setPosition(
						      this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400),
						      this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400),
						      this.ego.shipGrandGroup_.getPosZ() + 1000);
						((PlayerFighter)this.ships[0]).setSpeed(3);
						((PlayerFighter)this.ships[0]).setRotate(3);
						this.ships[0].player.setHitPoints(9999999);
						this.ships[0].setRoute(this.missionRoute.clone());
						this.ships[0].player.setAlwaysFriend(true);
						this.ships[0].name = mission.getAgent().getFullName();
						this.ships[0].cargo = null;

						for(int i = 1; i < this.ships.length; ++i) {
							this.ships[i] = createShip(8, 0, Globals.getRandomEnemyFighter(8),
							      this.missionRoute.getWaypoint(GlobalStatus.random.nextInt(this.missionRoute.length())));
							this.ships[i].setToSleep();
						}

						this.successObjective = new Objective(20, 1, pirateCnt + 1, this);
						this.failObjective_ = new Objective(21, 1, pirateCnt + 1, this);
					}
				}
			}
		}
	}

	private void createCampaignMission() {
		AEVector3D var1 = new AEVector3D();
		int var2;
		int var3;
		int var4;
		int var5;
		//label214:
		switch(Status.getCurrentCampaignMission()) {
		case 0: // intro
		this.ships = new KIPlayer[3];

		for(int i = 0; i < this.ships.length; ++i) {
			this.ships[i] = createShip(8, 0, 10, (Waypoint)null);
			this.ships[i].setToSleep();
			this.ships[i].player.setAlwaysEnemy(true);
			var1 = this.asteroids[this.asteroids.length - 1 - i].getPosition(var1);
			this.ships[i].setPosition(var1.x, var1.y, var1.z + 2000);
			this.ships[i].hasCargo = false;
			this.ships[i].cargo = null;
			this.ships[i].player.setHitPoints(150);
			if (i < 3) {
				((PlayerFighter)this.ships[i]).setExhaustVisible(false);
			}
		}

		this.ships[2].setPosition(0, 0, -40000);
		this.enemyRoute_ = new Route(new int[]{0, 0, -30000, 0, 0, 0});
		this.ships[2].setRoute(this.enemyRoute_);
		this.ego.player.setHitPoints(9999999);
		break;
		case 1: // floating around Var Hastra
			this.ships = new KIPlayer[1];
			this.ships[0] = createShip(3, 0, 1, (Waypoint)null);
			this.ships[0].setPosition(300, 50, -8000);
			this.enemyRoute_ = new Route(new int[]{0, 0, -5000, 0, 0, 0});
			this.ships[0].setRoute(this.enemyRoute_);
			this.ships[0].setSpeed(0);
		case 2:
		case 3:
		case 5:
		case 6:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 15:
		case 17:
		case 18:
		case 19:
		case 20:
		case 22:
		case 23:
		case 27:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 37:
		case 39:
		default:
			break;
		case 4: // last mining
		this.ships = new KIPlayer[1];
		this.ships[0] = createShip(8, 0, 1, (Waypoint)null);
		this.ships[0].setInitActive(false);
		this.ships[0].player.setAlwaysEnemy(true);
		this.ships[0].setPosition(0, 0, -200000);
		this.ships[0].setToSleep();
		break;
		case 7: // hunting Var Hastra pirates
			this.missionRoute = new Route(new int[]{20000, 7000, 120000});
			this.ships = new KIPlayer[4];

			for(var5 = 0; var5 < 3; ++var5) {
				this.ships[var5] = createShip(8, 0, 2, this.missionRoute.getDockingTarget_());
				this.ships[var5].setToSleep();
			}

			this.ships[3] = createShip(3, 0, 1, (Waypoint)null);
			this.ships[3].player.setAlwaysFriend(true);
			this.ships[3].setPosition(
					this.ego.shipGrandGroup_.getPosX() + 700,
					this.ego.shipGrandGroup_.getPosY() + 50,
			      this.ego.shipGrandGroup_.getPosZ() + 1000);
			this.ships[3].setRoute(this.missionRoute.clone());
			this.ships[3].player.setHitPoints(9999999);
			this.ships[3].name = GlobalStatus.gameText.getText(821);
			((PlayerFighter)this.ships[3]).setBoostProb(0);
			this.successObjective = new Objective(18, 0, 3, this);
			break;
		case 14: // meeting convoi
			this.enemyRoute_ = new Route(new int[]{0, 0, 50000});
			this.ships = new KIPlayer[7];
			// three pirate Betties
			for(var5 = 0; var5 < 3; ++var5) {
				this.ships[var5] = createShip(8, 0, 0, this.enemyRoute_.getDockingTarget_());
			}
			// two terran Tenetas
			for(var5 = 3; var5 < 5; ++var5) {
				this.ships[var5] = createShip(0, 0, 1, this.enemyRoute_.getDockingTarget_());
			}
			// two Battle Crusiers
			for(var5 = 5; var5 < 7; ++var5) {
				this.ships[var5] = createShip(0, 1, 14, this.enemyRoute_.getDockingTarget_());
			}

			this.successObjective = new Objective(22, 0, this);
			break;
		case 16: // Void attack on Alioth
			this.enemyRoute_ = new Route(new int[]{0, 0, 130000});
			this.ships = new KIPlayer[7];

			for(var5 = 0; var5 < 4; ++var5) {
				this.ships[var5] = createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
			}

			for(var5 = 4; var5 < 7; ++var5) {
				this.ships[var5] = createShip(0, 0, 1, (Waypoint)null);
				this.ships[var5].player.setAlwaysFriend(true);
				this.ships[var5].player.setHitPoints(600);
				((PlayerFighter) this.ships[var5]).setPosition(
				      this.ego.shipGrandGroup_.getPosX() + -2000 + GlobalStatus.random.nextInt(4000),
				      this.ego.shipGrandGroup_.getPosY() + -1700 + GlobalStatus.random.nextInt(3400),
				      this.ego.shipGrandGroup_.getPosZ() + 2000 + -2000 + GlobalStatus.random.nextInt(4000));
			}

			this.successObjective = new Objective(18, 0, 4, this);
			break;
		case 21: // rescue Thomas Boyle
			this.enemyRoute_ = new Route(new int[]{40000, -40000, 120000});
			this.missionRoute = this.enemyRoute_.clone();
			this.enemyRoute_.setLoop(true);
			this.ships = new KIPlayer[3];
			this.ships[1] = createShip(0, 0, 0, this.enemyRoute_.getDockingTarget_());
			this.ships[2] = createShip(0, 0, 0, this.enemyRoute_.getDockingTarget_());
			this.ships[0] = createShip(0, 0, 1, this.enemyRoute_.getDockingTarget_());
			this.ships[0].name = GlobalStatus.gameText.getText(833); // "Kidnpapper"

			for(var5 = 0; var5 < this.ships.length; ++var5) {
				this.ships[var5].setToSleep();
				this.ships[var5].setRoute(this.enemyRoute_.clone());
				this.ships[var5].geometry.setRotation(0, AEMath.Q_PI_HALF, 0);
			}

			this.successObjective = new Objective(22, 0, this);
			this.failObjective_ = new Objective(7, 1, this);
			break;
		case 24:
			this.enemyRoute_ = new Route(new int[]{
					100000, 0, 0,
					100000, 0, -30000});
			this.ships = new KIPlayer[3];

			for(var5 = 0; var5 < 3; ++var5) {
				this.ships[var5] = createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
				this.ships[var5].setRoute(this.enemyRoute_.clone());
			}

			this.stationaries[3].setVisible(false);
			this.successObjective = new Objective(22, 0, this);
			break;
		case 25:
		case 29:
			this.ships = new KIPlayer[3];
			var5 = 0;
			for(var5 = 0; var5 < 3; var5++){
				this.ships[var5] = createShip(9, 0, 8, (Waypoint)null);
				var2 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				var3 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				var4 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				this.ships[var5].setPosition(var2, var3, var4);	
			}
			break;
			// while(true) {
			// 	if (var5 >= 3) {
			// 		break label214;
			// 	}

			// 	this.ships[var5] = createShip(9, 0, 8, (Waypoint)null);
			// 	var2 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
			// 	var3 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
			// 	var4 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
			// 	this.ships[var5].setPosition(var2, var3, var4);
			// 	++var5;
			// }
		case 26:
			this.ships = new KIPlayer[2];

			for(var5 = 0; var5 < 2; ++var5) {
				this.ships[var5] = createShip(9, 0, 8, (Waypoint)null);
				((PlayerFighter)this.ships[var5]).geometry.setTransform(this.ego.shipGrandGroup_.getToParentTransform());
				((PlayerFighter) this.ships[var5]).setPosition(
				      this.ego.shipGrandGroup_.getPosX() + -700 + GlobalStatus.random.nextInt(1400),
				      this.ego.shipGrandGroup_.getPosY() + -700 + GlobalStatus.random.nextInt(1400),
				      this.ego.shipGrandGroup_.getPosZ() + 2000);
			}

			this.successObjective = new Objective(7, 2, this);
			break;
		case 28:
			this.tempVec = this.stationaries[3].getPosition(this.tempVec);
			this.enemyRoute_ = new Route(new int[]{this.tempVec.x, this.tempVec.y, this.tempVec.z});
			this.ships = new KIPlayer[5];
			var5 = 0;
			for(var5 = 0; var5 < 5; var5++){
				this.ships[var5] = createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
			}
			break;
			// while(true) {
			// 	if (var5 >= 5) {
			// 		break label214;
			// 	}

			// 	this.ships[var5] = createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
			// 	++var5;
			// }
		case 36:
			Status.getMission().setAgent(new Agent(-1, GlobalStatus.gameText.getText(826), 29, 5, 1, true, -1, -1, -1));
			createMission();
			break;
		case 38:
			this.enemyRoute_ = new Route(new int[]{0, 10000, 50000});
			this.ships = new KIPlayer[7];

			for(var3 = 0; var3 < 2; ++var3) {
				this.ships[var3] = createShip(2, 1, 15, this.enemyRoute_.getDockingTarget_());
				this.ships[var3].player.setAlwaysFriend(true);
				((PlayerFixedObject)this.ships[var3]).setMoving(false);
				((PlayerFixedObject) this.ships[var3]).setPosition(
				      this.enemyRoute_.getDockingTarget_().x + -10000 + GlobalStatus.random.nextInt(20000),
				      this.enemyRoute_.getDockingTarget_().y + -10000 + GlobalStatus.random.nextInt(20000),
				      this.enemyRoute_.getDockingTarget_().z + -10000 + GlobalStatus.random.nextInt(20000));
			}

			for(var3 = 2; var3 < this.ships.length; ++var3) {
				this.ships[var3] = createShip(3, 0, Globals.getRandomEnemyFighter(3), this.enemyRoute_.getDockingTarget_());
				this.ships[var3].setToSleep();
				this.ships[var3].player.setAlwaysEnemy(true);
			}

			this.successObjective = new Objective(18, 2, 7, this);
			this.failObjective_ = new Objective(7, 2, this);
			break;
		case 40:
			this.enemyRoute_ = new Route(new int[]{
					-20000, -3000, 200000});
			this.kamikazePath = new Route(new int[]{
					-20000, -3000, 65000,
					-20000, -3000, 200000});
			this.ships = new KIPlayer[9];
			this.ships[0] = createShip(0, 1, 13, (Waypoint)null);
			this.ships[0].player.setAlwaysFriend(true);
			this.ships[0].name = GlobalStatus.gameText.getText(826);
			this.ships[0].player.setMaxHP(1200 + 5 * Status.getLevel());
			((PlayerFixedObject)this.ships[0]).setMoving(true);
			((PlayerFixedObject) this.ships[0]).setPosition(
					this.kamikazePath.getWaypoint(0).x,
			      this.kamikazePath.getWaypoint(0).y,
			      this.kamikazePath.getWaypoint(0).z);

			for(var4 = 1; var4 < 5; ++var4) {
				this.ships[var4] = createShip(0, 0, Globals.getRandomEnemyFighter(0), (Waypoint)null);
				this.ships[var4].setRoute(this.kamikazePath.clone());
				this.ships[var4].player.setAlwaysFriend(true);
				if (var4 == 2) {
					this.ships[2].name = GlobalStatus.gameText.getText(827);
				}
			}

			for(var4 = 5; var4 < 9; ++var4) {
				this.ships[var4] = createShip(9, 0, 8, this.enemyRoute_.getDockingTarget_());
				this.ships[var4].player.setAlwaysEnemy(true);
			}

			this.stationaries[3].setPosition(-20000, -3000, 200000);
			if (initStreamOutPosition) {
				this.ego.setPosition_(-65000, 0, 80000);
				this.ego.shipGrandGroup_.setRotation(0, 1024, 0);
			}

			this.failObjective_ = new Objective(7, 1, this);
			break;
		case 41:
			this.ships = new KIPlayer[5];
			this.ships[0] = createShip(1, 1, 13, (Waypoint)null);
			this.ships[0].player.setAlwaysFriend(true);
			this.ships[0].name = GlobalStatus.gameText.getText(826);
			this.ships[0].player.setHitPoints(lastMissionFreighterHitpoints);
			((PlayerFixedObject)this.ships[0]).setMoving(true);
			((PlayerFixedObject)this.ships[0]).setPosition(0, 0, -200000);

			for(var4 = 1; var4 < 5; ++var4) {
				this.ships[var4] = createShip(9, 0, 8, (Waypoint)null);
				this.ships[var4].player.setAlwaysEnemy(true);
				var5 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				var2 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				var3 = (GlobalStatus.random.nextInt(2) == 0 ? 1 : -1) * (20000 + GlobalStatus.random.nextInt(80000));
				this.ships[var4].setPosition(var5, var2, var3);
			}

			this.ego.setPosition_(3000, 2000, -220000);
			this.ego.shipGrandGroup_.setRotation(0, 0, 0);
			this.successObjective = new Objective(25, 0, this);
			this.failObjective_ = new Objective(7, 1, this);
		}

		this.radioMessages = createRadioMessages(Status.getCurrentCampaignMission());
	}

	private void createScene() {
		final int stationType;
		
		//label88:
		switch(this.currentMod) {
		case 4: // bar
			final int systemRace = Status.getSystem().getRace();
			stationType = 
				systemRace == Globals.VOSSK ? STATION_VOSSK : 
				systemRace == Globals.TERRAN ? STATION_TERRAN : STATION_NIVELIAN;
			final int numBarParts = (stationType == STATION_TERRAN) ? 20 : 6;
			Agent[] agents = Status.getStation().getBarAgents();
			this.ships = new KIPlayer[agents.length + numBarParts];
			final boolean[] positionsOccupied = new boolean[this.LEVEL_BAR_CHAR_POSITIONS.length / 3];

			//int var6;
			for(int i = 0; i < positionsOccupied.length; ++i) {
				positionsOccupied[i] = false;
			}

			for(int i = 0; i < agents.length; ++i) {
				short figureResID = Globals.BAR_FIGURES[agents[i].getRace()];
				if (agents[i].getRace() == Globals.TERRAN && !agents[i].isMale()) {
					figureResID = 14224;
				}

				int j;
				do {
					j = GlobalStatus.random.nextInt(positionsOccupied.length);
				} while(positionsOccupied[j]);

				positionsOccupied[j] = true;
				this.ships[i] = new PlayerStatic(
						-1, AEResourceManager.getGeometryResource(figureResID),
				      this.LEVEL_BAR_CHAR_POSITIONS[j * 3],
				      this.LEVEL_BAR_CHAR_POSITIONS[j * 3 + 1],
				      this.LEVEL_BAR_CHAR_POSITIONS[j * 3 + 2]);
				this.ships[i].mainMesh_.setRenderLayer(2);
				this.ships[i].mainMesh_.disableAnimation();
			}

			if (numBarParts <= 0) {
				return;
			}

			GlobalStatus.random.setSeed(Status.getStation().getIndex() + 1000);
				for(int i = agents.length; i < this.ships.length; i++) {
				final short var14 = Globals.BAR_MESHES[systemRace][GlobalStatus.random.nextInt(Globals.BAR_MESHES[systemRace].length)];
				this.ships[i] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var14), 0, 0, 0);
				this.ships[i].mainMesh_.setRenderLayer(2);
				this.ships[i].mainMesh_.setRotation(0, i * (AEMath.Q_PI / numBarParts), 0);
			}
			break;
			// int var12 = var3; //original decomp

			// while(true) {
			// 	if (var12 >= this.ships.length) {
			// 		break label88;
			// 	}

			// 	final short var14 = Globals.BAR_MESHES[var11][GlobalStatus.random.nextInt(Globals.BAR_MESHES[var11].length)];
			// 	this.ships[var12] = new PlayerStatic(-1, AEResourceManager.getGeometryResource(var14), 0, 0, 0);
			// 	this.ships[var12].mainMesh_.setRenderLayer(2);
			// 	this.ships[var12].mainMesh_.setRotation(0, var12 * var6, 0);
			// 	++var12;
			// }
		case 23: // hangar
			stationType = 
				Status.getSystem().getRace() == Globals.VOSSK ? STATION_VOSSK :
				Status.getSystem().getRace() == Globals.TERRAN ? STATION_TERRAN : STATION_NIVELIAN;
			this.ships = new KIPlayer[7];
			this.ships[0] = createShip(Status.getShip().getRace(), 0, Status.getShip().getIndex(), (Waypoint)null);
			this.ships[0].setPosition(0, 1200, 10240 - Ship.SHIP_HANGAR_OFFSETS[Status.getShip().getIndex()] + 100);
			this.ships[0].geometry.setRotation(0, AEMath.Q_PI_HALF, 0);
			this.ships[0].setRoute(new Route(new int[]{0, 500, -100000}));
			((PlayerFighter)this.ships[0]).removeTrail();
			((PlayerFighter)this.ships[0]).setExhaustVisible(false);
			this.ships[0].setToSleep();
			this.ships[0].player.setAlwaysFriend(true);
			GlobalStatus.random.setSeed(Status.getStation().getIndex());
			int var2 = 0;

			for(int i = 1; i < 6; ++i) {
				short var4 = Globals.HANGAR_MESHES[stationType][GlobalStatus.random.nextInt(Globals.HANGAR_MESHES[stationType].length - 3)];
				if (i == 5) {
					var4 = Globals.HANGAR_MESHES[stationType][Globals.HANGAR_MESHES[stationType].length - 2];
				} else if (i == 1) {
					var4 = Globals.HANGAR_MESHES[stationType][Globals.HANGAR_MESHES[stationType].length - 1];
				}

				this.ships[i] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var4), 0, 0, var2 << AEMath.Q);
				this.ships[i].mainMesh_.setRenderLayer(1);
				this.ships[i].mainMesh_.setRotation(0, AEMath.Q_PI_HALF, 0);
				++var2;
			}

			final short var10 = Globals.HANGAR_MESHES[stationType][Globals.HANGAR_MESHES[stationType].length - 3];
			this.ships[this.ships.length - 1] = new PlayerStaticFar(-1, AEResourceManager.getGeometryResource(var10), 0, 0, 8192);
			this.ships[this.ships.length - 1].mainMesh_.setRenderLayer(2);
			this.ships[this.ships.length - 1].mainMesh_.setRotation(0, AEMath.Q_PI_HALF, 0);
			break;
		default:
			return;
		}

		GlobalStatus.random.setSeed(System.currentTimeMillis());
	}
	// manages difficulty
	private void assignGuns() {
		this.enemyGuns = null;
		final int dmgBonus = (int)(AEMath.min(Status.getLevel(), 20) / 1.8F) + (int)(Status.getCurrentCampaignMission() / 5.0F);
		int gunCnt = 0;
		if (this.ships != null) {
			for(int i = 0; i < this.ships.length; ++i) {
				if (this.ships[i] != null && this.ships[i].armed) {
					++gunCnt;
					if (this.ships[i].isWingman()) {
						++gunCnt;
					}
				}
			}

			this.enemyGuns = new AbstractMesh[gunCnt];
			gunCnt = 0;

			for(int i = 0; i < this.ships.length; ++i) {
				Gun gun;
				final KIPlayer ship = this.ships[i];
				if (ship != null && ship.armed) {
					int dmg = dmgBonus + 2;
					if (Status.getMission().getType() == 12 && ship.player.isAlwaysFriend()) {
						dmg = Status.getShip().getFirePower() + 2;
					} else if (Status.getCurrentCampaignMission() == 4) {
						dmg = 1;
					}
					//bots shoot faster as campaign progresses		
					gun = new Gun(0, dmg, 4, -1, 3000,
							600 - (Status.getCurrentCampaignMission() << 1),
							16.0F, (AEVector3D) null, (AEVector3D) null);
					gun.setFriendGun(true);
					gun.setLevel(this);
					gun.setSparks(this.gunSparks);
					final int gunType = ship.race == Globals.VOID ? 7:
											  ship.race == Globals.TERRAN ? 1:
											  ship.race == Globals.VOSSK ? 3:
													             4;
					this.enemyGuns[gunCnt] = new ObjectGun(gun, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[gunType]));
					this.enemyGuns[gunCnt].setRenderLayer(2);
					++gunCnt;
					ship.addGun(gun, 0);
				}

				if (ship.isWingman() && ship.armed) {
					gun = new Gun(18, 0, 4, -1, 3000, 400, 16.0F, (AEVector3D)null, (AEVector3D)null);
					gun.setFriendGun(true);
					gun.setLevel(this);
					gun.setSparks(this.gunSparks);
					gun.index = 18;
					gun.subType = Item.BLASTER;
					this.enemyGuns[gunCnt] = new ObjectGun(gun, AEResourceManager.getGeometryResource(Globals.TYPE_WEAPONS[18]));
					this.enemyGuns[gunCnt].setRenderLayer(2);
					++gunCnt;
					ship.addGun(gun, 1);
				}
			}
		}

	}

	private void connectPlayers() {
		if (GlobalStatus.applicationManager.GetCurrentApplicationModule() != GlobalStatus.scenes[1]) {
			Player[] var1;
			int var2;
			if (this.ships != null && this.ego != null) {
				var1 = new Player[this.ships.length];

				for(var2 = 0; var2 < var1.length; ++var2) {
					var1[var2] = this.ships[var2].player;
				}

				this.ego.player.setEnemies(var1);
			}

			if (this.asteroids != null && this.ego != null) {
				var1 = new Player[this.asteroids.length];

				for(var2 = 0; var2 < var1.length; ++var2) {
					var1[var2] = this.asteroids[var2].player;
				}

				this.ego.player.addEnemies(var1);
			}

			if (this.ships != null) {
				for(int i = 0; i < this.ships.length; ++i) {
					var2 = this.ships[i].race;
					final boolean var3 = this.ships[i].isWingman();
					int var4 = 0;

					for(int j = 0; j < this.ships.length; ++j) {
						if (this.ships[j] != this.ships[i] && (this.ships[j].race != var2 || var3)) {
							++var4;
						}
					}

					Player[] var9 = new Player[(this.ego == null ? 0 : 1) + var4];
					//int var6;
					Mission var8 = Status.getMission();
					if ((var8.getType() != 12 || i % 2 != 1)
						&& var8.getType() != 2
						&& var8.getType() != 9 
						&& (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 40)
						&& (!var8.isCampaignMission() || Status.getCurrentCampaignMission() != 41))
					{
						var4 = 0;
						if (this.ego != null) {
							var9[0] = this.ego.player;
							var4 = 1;
						}

						for(int j = 0; j < this.ships.length; ++j) {
							if (this.ships[j] != this.ships[i] && (this.ships[j].race != var2 || var3)) {
								var9[var4] = this.ships[j].player;
								var4++;
							}
						}
					} else {
						if (this.ships[i].player.isAlwaysFriend()) {
							var9 = new Player[1];
						} else {
							var4 = 0;

							for(int j = 0; j < this.ships.length; ++j) {
								if (this.ships[j] != this.ships[i] && (this.ships[j].race != var2 || var3)) {
									var9[var4] = this.ships[j].player;
									var4++;
								}
							}
						}

						var9[var9.length - 1] = this.ego.player;
					}

					this.ships[i].player.addEnemies(var9);
				}
			}

		}
	}

	private KIPlayer createJunk(final Waypoint var1, int var2) {
		var2 = var1 != null ? var1.x : 0;
		int var3 = var1 != null ? var1.y : 0;
		int var6 = var1 != null ? var1.z : 0;
		var2 += GlobalStatus.random.nextInt(20000) - 10000;
		var3 += GlobalStatus.random.nextInt(20000) - 10000;
		var6 += GlobalStatus.random.nextInt(20000) - 10000;
		AbstractMesh mesh = AEResourceManager.getGeometryResource(9996);
		mesh.setRenderLayer(2);
		Player player = new Player(1000.0F, 1, 0, 0, 0);
		PlayerJunk junk = new PlayerJunk(9996, player, mesh, var2, var3, var6);
		junk.setExplosion(new Explosion(1));
		junk.mainMesh_.rotateEuler(var2, var3, var6);
		junk.setLevel(this);
		return junk;
	}
	/**
	 *
	 * @param race
	 * @param type
	 * @param shipId
	 * @param var4 ship spawns in proximity of 34 km of the point
	 * @return
	 */
	private KIPlayer createShip(final int race, final int type, final int shipId, final Waypoint var4) {
		int spawnX = var4 != null ? var4.x : 0;
		int spawnY = var4 != null ? var4.y : 0;
		int spawnZ = var4 != null ? var4.z : 0;
		spawnX += GlobalStatus.random.nextInt(40000) - 20000;
		spawnY += GlobalStatus.random.nextInt(40000) - 20000;
		spawnZ += GlobalStatus.random.nextInt(40000) - 20000;
		KIPlayer ship = null;
		Explosion explosion = null;
		Player player = null;
		int hp;
		hp = 20 + AEMath.min(Status.getLevel(), 20) * 15 + (Status.getCurrentCampaignMission() << 2);
		int empp = 40 + AEMath.min(Status.getLevel(), 20) * 5;
		int var11 = 15000;
		if (type == 1) {
			hp <<= 2;
			empp *= 3;
			var11 = 15000 * 3;
			if (shipId == 14) {
				hp *= 5;
			}
		}
		player = new Player(2000.0F, hp, 1, 1, 0);
		player.setEmpData(empp, var11);
		switch(type) {
		case 0:
			ship = new PlayerFighter(shipId, race, player, (AbstractMesh)null, spawnX, spawnY, spawnZ);
			explosion = new Explosion(1);
			ship.setGroup(Globals.getShipGroup(shipId, race), shipId);
			break;
		case 1:
			ship = new PlayerFixedObject(shipId, race, player, (AbstractMesh)null, spawnX, spawnY, spawnZ);
			final BoundingVolume[] bounds = new BoundingVolume[1];
			bounds[0] = new BoundingAAB(spawnX, spawnY, spawnZ, 0, 300, 0, 4000, 4000, 15000);
			((PlayerFixedObject)ship).setBounds(bounds);
			explosion = new Explosion(1);
			explosion.setBig();
			ship.setGroup(Globals.getShipGroup(shipId, race), shipId);
		}

		ship.setLevel(this);
		ship.setExplosion(explosion);
		return ship;
	}

	private static Route createRoute(final int len) {
		final int[] wayPoints = new int[len * 3];

		for(int i = 0; i < wayPoints.length; i += 3) {
			final int rndSign = GlobalStatus.random.nextInt(2) == 0 ? 1 : -1;
			wayPoints[i] = 50000 + GlobalStatus.random.nextInt(30000);
			wayPoints[i] *= rndSign;
			wayPoints[i + 1] = -10000 + GlobalStatus.random.nextInt(20000);
			if (i != 0) {
				wayPoints[i + 2] = wayPoints[i - 3 + 2] + 50000 + GlobalStatus.random.nextInt(30000);
			} else {
				wayPoints[i + 2] = 50000 + GlobalStatus.random.nextInt(30000);
			}
		}

		return new Route(wayPoints);
	}

	private RadioMessage[] createRadioMessages(final int missionIdx) {
		this.radioMessages = null;
		switch(missionIdx) {
		case 0:
			this.radioMessages = new RadioMessage[22];
			this.radioMessages[0] = new RadioMessage(844, 17, 5, 15000);
			this.radioMessages[1] = new RadioMessage(845, 0, 6, 0);
			this.radioMessages[2] = new RadioMessage(846, 0, 6, 1);
			this.radioMessages[3] = new RadioMessage(847, 10, 6, 2);
			this.radioMessages[4] = new RadioMessage(848, 9, 6, 3);
			this.radioMessages[5] = new RadioMessage(849, 9, 6, 4);
			this.radioMessages[6] = new RadioMessage(850, 11, 6, 5);
			this.radioMessages[7] = new RadioMessage(851, 9, 6, 6);
			this.radioMessages[8] = new RadioMessage(852, 0, 6, 7);
			this.radioMessages[9] = new RadioMessage(854, 0, 9, new int[]{0, 1, 2});
			this.radioMessages[10] = new RadioMessage(855, 0, 6, 9);
			this.radioMessages[11] = new RadioMessage(856, 0, 6, 10);
			this.radioMessages[12] = new RadioMessage(857, 15, 6, 11);
			this.radioMessages[13] = new RadioMessage(858, 0, 6, 12);
			this.radioMessages[14] = new RadioMessage(859, 0, 6, 13);
			this.radioMessages[15] = new RadioMessage(860, 15, 6, 14);
			this.radioMessages[16] = new RadioMessage(861, 0, 6, 15);
			this.radioMessages[17] = new RadioMessage(862, 15, 6, 16);
			this.radioMessages[18] = new RadioMessage(863, 0, 6, 17);
			this.radioMessages[19] = new RadioMessage(864, 0, 6, 18);
			this.radioMessages[20] = new RadioMessage(865, 15, 6, 19);
			this.radioMessages[21] = new RadioMessage(866, 0, 6, 20);
			break;
		case 1:
			this.radioMessages = new RadioMessage[3];
			this.radioMessages[0] = new RadioMessage(867, 2, 5, 10000);
			this.radioMessages[1] = new RadioMessage(868, 2, 6, 0);
			this.radioMessages[2] = new RadioMessage(869, 2, 6, 1);
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 15:
		case 17:
		case 18:
		case 19:
		case 20:
		case 22:
		case 23:
		case 25:
		case 26:
		case 27:
		case 28:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 39:
		default:
			break;
		case 7:
			this.radioMessages = new RadioMessage[2];
			this.radioMessages[0] = new RadioMessage(909, 2, 16, 0);
			this.radioMessages[1] = new RadioMessage(910, 0, 6, 0);
			break;
		case 14:
			this.radioMessages = new RadioMessage[4];
			this.radioMessages[0] = new RadioMessage(968, 18, 5, 6000);
			this.radioMessages[1] = new RadioMessage(969, 0, 20, 2);
			this.radioMessages[2] = new RadioMessage(970, 0, 6, 1);
			this.radioMessages[3] = new RadioMessage(971, 18, 6, 2);
			break;
		case 16:
			this.radioMessages = new RadioMessage[2];
			this.radioMessages[0] = new RadioMessage(983, 19, 5, 6000);
			this.radioMessages[1] = new RadioMessage(984, 0, 6, 0);
			break;
		case 21:
			this.radioMessages = new RadioMessage[3];
			this.radioMessages[0] = new RadioMessage(1018, 14, 8, 0);
			this.radioMessages[1] = new RadioMessage(1019, 0, 6, 0);
			this.radioMessages[2] = new RadioMessage(1020, 14, 21, 0);
			break;
		case 24:
			this.radioMessages = new RadioMessage[2];
			this.radioMessages[0] = new RadioMessage(1047, 6, 22, 3);
			this.radioMessages[1] = new RadioMessage(1048, 6, 6, 0);
			break;
		case 29:
			this.radioMessages = new RadioMessage[5];
			this.radioMessages[0] = new RadioMessage(1073, 0, 23, 0);
			this.radioMessages[1] = new RadioMessage(1074, 0, 6, 0);
			this.radioMessages[2] = new RadioMessage(1075, 0, 6, 1);
			this.radioMessages[3] = new RadioMessage(1076, 19, 5, 120000);
			this.radioMessages[4] = new RadioMessage(1077, 0, 6, 3);
			break;
		case 38:
			this.radioMessages = new RadioMessage[1];
			this.radioMessages[0] = new RadioMessage(1146, 21, 5, 5000);
			break;
		case 40:
			this.radioMessages = new RadioMessage[2];
			this.radioMessages[0] = new RadioMessage(1163, 7, 12, 0);
			this.radioMessages[1] = new RadioMessage(1164, 0, 24, 0);
			break;
		case 41:
			this.radioMessages = new RadioMessage[1];
			this.radioMessages[0] = new RadioMessage(1163, 7, 12, 0);
		}

		return this.radioMessages;
	}

	public final PlayerEgo getPlayer() {
		return this.ego;
	}

	public final KIPlayer[] getEnemies() {
		return this.ships;
	}

	public final KIPlayer[] getLandmarks() {
		return this.stationaries;
	}

	public final KIPlayer[] getAsteroids() {
		return this.asteroids;
	}

	public final Waypoint getAsteroidWaypoint() {
		return this.asteroidsWaypoint;
	}

	public final Route getPlayerRoute() {
		return this.missionRoute;
	}

	public final void setPlayerRoute(final Route var1) {
		this.missionRoute = null;
	}

	public final void flashScreen(final int var1) {
		if (this.flashType < 3 || this.flashIntensity <= this.lastFlashDuration >> 1) {
			this.exploding_ = true;
			this.flashType = var1;
			this.lastFlashDuration = var1 >= 3 ? 10000 : var1 == 1 ? 7000 : 2000;
			this.flashIntensity = this.lastFlashDuration;
			switch (var1) {
			case Explosion.HUGE:
				this.explosionR = 255.0F;
				this.explosionG = 255.0F;
				this.explosionB = 255.0F;
				this.ego.hit();
				break;
			case Explosion.SMALL: 
				this.explosionR = bgR * 1.5F;
				this.explosionG = bgG * 1.5F;
				this.explosionB = bgB * 1.5F;
				break;
			case Explosion.EMP:
				this.explosionR = 0.0F;
				this.explosionG = 0.0F;
				this.explosionB = 255.0F;
				break;
			default: { // Explosion.MEDIUM, Explosion.BIG
				final float var2 = var1 == 1 ? 8.0F : 5.0F;
				this.explosionR = AEMath.max(10, AEMath.min(255, (int)(bgR * var2)));
				this.explosionG = AEMath.max(10, AEMath.min(255, (int)(bgG * var2)));
				this.explosionB = AEMath.max(10, AEMath.min(255, (int)(bgB * var2)));
				break;
			}
			}
		}
	}

	public final void enemyDied(final boolean killedByEgo) {
		--this.enemiesIntact;
		++this.enemiesDestroyed;
		if (!killedByEgo) {
			Status.incKills();
			++this.egoScore;
		} else {
			++this.challengerScore;
		}

		flashScreen(Explosion.BIG);
	}

	public final void junkDied() {
		++Status.destroyedJunk;
		--this.enemiesIntact;
	}

	public final void friendDied() {
		--this.friendsAlive;
	}

	public static void wingmanDied(final int var0) {
		if (Status.wingmenNames.length > 1) {
			Status.wingmenNames[var0] = null;
			final String[] var3 = new String[Status.wingmenNames.length - 1];
			int var1 = 0;

			for(int i = 0; i < Status.wingmenNames.length; ++i) {
				if (Status.wingmenNames[i] != null) {
					var3[var1] = Status.wingmenNames[i];
					var1++;
				}
			}

			Status.wingmenNames = var3;
		} else {
			Status.wingmenNames = null;
			Status.wingmanFace = null;
		}
	}

	public final void asteroidDied() {
		--this.asteroidsIntact;
	}

	public final int unknown7f9_() {
		return 0;
	}
	/**
	 * @return not just enemies but actually every ai player
	 */
	public final int getEnemiesLeft() {
		return this.enemiesIntact;
	}

	public final int getFriendsLeft() {
		return this.friendsAlive;
	}

	public final RadioMessage[] getMessages() {
		return this.radioMessages;
	}

	public final int getTimeLimit() {
		return this.timeLimit;
	}

	public final StarSystem getStarSystem() {
		return this.starSystem;
	}

	public final boolean isInAsteroidCenterRange(final AEVector3D var1) {
		return this.asteroidField.outerCollide_(var1.x, var1.y, var1.z);
	}

	public final boolean collideStream(final AEVector3D var1) {
		return this.stationaries[1] != null ? this.stationaries[1].outerCollide(var1.x, var1.y, var1.z) : false;
	}

	public final boolean collideStation(final AEVector3D var1) {
		return this.stationaries != null && this.stationaries[0] != null && !Status.inEmptyOrbit()
		      ? this.stationaries[0].outerCollide(var1.x, var1.y, var1.z)
		      : false;
	}

	public final void render(final long var1) {
		if (this.exploding_) {
			GlobalStatus.graphics.setColor((int)this.explosionR, (int)this.explosionG, (int)this.explosionB);
		} else {
			GlobalStatus.graphics.setColor(bgR, bgG, bgB);
		}

		GlobalStatus.graphics.fillRect(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
		this.starSystem.render_();
		GlobalStatus.renderer.drawNodeInVF(this.skybox);
		int i;
		if (this.egoGuns != null) {
			for(i = 0; i < this.egoGuns.length; ++i) {
				this.egoGuns[i].render();
			}
		}

		if (this.enemyGuns != null) {
			for(i = 0; i < this.enemyGuns.length; ++i) {
				this.enemyGuns[i].render();
			}
		}

		if (this.ships != null) {
			for(i = 0; i < this.ships.length; ++i) {
				this.ships[i].update(var1);
				this.ships[i].appendToRender();
			}
		}

		if (this.asteroids != null) {
			for(i = 0; i < this.asteroids.length; ++i) {
				this.asteroids[i].update(var1);
				this.asteroids[i].appendToRender();
			}
		}

		if (this.stationaries != null) {
			for(i = 0; i < this.stationaries.length; ++i) {
				if (this.stationaries[i] != null) {
					this.stationaries[i].update(var1);
					this.stationaries[i].appendToRender();
				}
			}
		}

		this.starSystem.renderSunStreak__();
	}

	public final void renderRockets_() {
		this.starSystem.render2D();
		if (this.egoGuns != null) {
			for(int i = 0; i < this.egoGuns.length; ++i) {
				if (this.egoGuns[i] instanceof RocketGun) {
					((RocketGun)this.egoGuns[i]).renderRocket_();
				}
			}
		}

	}

	public final void render2(final long var1) {
		GlobalStatus.graphics.setColor(bgR, bgG, bgB);
		GlobalStatus.graphics.fillRect(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
		this.starSystem.render_();
		GlobalStatus.renderer.drawNodeInVF(this.skybox);
		int i;
		if (this.egoGuns != null) {
			for(i = 0; i < this.egoGuns.length; ++i) {
				this.egoGuns[i].render();
			}
		}

		if (this.enemyGuns != null) {
			for(i = 0; i < this.enemyGuns.length; ++i) {
				this.enemyGuns[i].render();
			}
		}

		if (this.ships != null) {
			for(i = 0; i < this.ships.length; ++i) {
				this.ships[i].appendToRender();
			}
		}

		if (this.asteroids != null) {
			for(i = 0; i < this.asteroids.length; ++i) {
				this.asteroids[i].appendToRender();
			}
		}

		if (this.stationaries != null) {
			for(i = 0; i < this.stationaries.length; ++i) {
				if (this.stationaries[i] != null) {
					this.stationaries[i].appendToRender();
				}
			}
		}

		this.starSystem.renderSunStreak__();
	}

	public final void updateOrbit_(final long var1) {
		if (Status.inAlienOrbit() && GlobalStatus.random.nextInt(25) == 0) {
			flashScreen(Explosion.SMALL);
		}

		if (this.exploding_) {
			this.flashIntensity = (int)(this.flashIntensity - var1);
			if (this.flashIntensity < 0) {
				this.exploding_ = false;
			}

			final float var3 = (float)this.flashIntensity / (float)this.lastFlashDuration;
			this.explosionR = AEMath.max(bgR, this.explosionR * var3);
			this.explosionG = AEMath.max(bgG, this.explosionG * var3);
			this.explosionB = AEMath.max(bgB, this.explosionB * var3);
		}

		int var4;
		KIPlayer var5;
		if (Status.getMission() == null || Status.getMission().isEmpty()) {
			var4 = (int)var1;
			//var8 = this;
			this.commonRespawnTick += var4;
			this.jumperRespawnTick += var4;
			if (this.jumperRespawnTick > 20000) {
				this.jumperRespawnTick = 0;
				if (this.ships != null) {
					for(int i = 0; i < this.ships.length; ++i) {
						if ((var5 = this.ships[i]).isJumper() && var5.isDead() && !var5.player.isActive()) {
							var5.revive();
							if (i < this.localFightersCnt + this.jumperCnt) {
								((PlayerFighter)var5).setPosition(0, 0, 0);
							} else {
								((PlayerFixedObject)var5).setPosition(10000, 0, -70000);
							}
							break;
						}
					}
				}
			}

			if (this.commonRespawnTick > 40000) {
				this.commonRespawnTick = 0;
				var4 = 0;
				if (this.raidersCnt > 0 && this.ships != null) {
					for(int i = 0; i < this.ships.length; ++i) {
						if (i >= this.ships.length - this.raidersCnt 
								&& !this.ships[i].isWingman()
								&& this.ships[i].isDead()
						      && !this.ships[i].player.isActive()) {
							++var4;
						}
					}
				}

				if (this.ships != null) {
					boolean var11 = false;

					for(int i = 0; i < this.ships.length; ++i) {
						final KIPlayer var7 = this.ships[i];
						if ((this.localFightersCnt > 0 && i < this.localFightersCnt || this.jumperCnt > 0
						      && i < this.localFightersCnt + this.jumperCnt && i > this.localFightersCnt) && var7.isDead()
						      && !var7.player.isActive()) {
							var7.revive();
							((PlayerFighter)var7).setPosition(0, 0, 0);
						}

						if (var4 >= 2 && this.raidWavesCounter < 2 && this.raidersCnt > 0
						      && i >= this.ships.length - this.raidersCnt && var7.isDead() && !var7.player.isActive()) {
							var11 = true;
							var7.revive();
							((PlayerFighter)var7).setPosition(var7.targetX, var7.targetY, this.ego.getPosition().z + 40000);
						}
					}

					if (var11) {
						++this.raidWavesCounter;
					}
				}
			}
		}

		if (Status.getStation().isAttackedByAliens() || Status.inAlienOrbit()) {
			//var4 = (int)var1;
			//var8 = this;
			this.alienRespawnTick += var1;
			if (this.alienRespawnTick > 40000) {
				this.alienRespawnTick = 0;
				if (this.ships != null) {
					for(int i = 0; i < this.ships.length; ++i) {
						if ((var5 = this.ships[i]).race == Globals.VOID && var5.isDead() && !var5.player.isActive()) {
							var5.revive();	
							int x, y, z;
							if (!Status.inAlienOrbit() && Status.getStation().isAttackedByAliens()) {
								x = this.stationaries[3].getPosition(this.tempVec).x - 10000 + GlobalStatus.random.nextInt(20000);
								y = this.stationaries[3].getPosition(this.tempVec).y - 10000 + GlobalStatus.random.nextInt(20000);
								z = this.stationaries[3].getPosition(this.tempVec).z - 10000 + GlobalStatus.random.nextInt(20000);
							} else {
								x = this.ego.getPosition().x - 30000 + GlobalStatus.random.nextInt(60000);
								y = this.ego.getPosition().y - 30000 + GlobalStatus.random.nextInt(60000);
								z = this.ego.getPosition().z + GlobalStatus.random.nextInt(2) == 0 ? 30000 : -30000;
							}

							((PlayerFighter)var5).setPosition(x, y, z);
						}
					}
				}
			}
		}

		int i;
		if (this.egoGuns != null) {
			for(i = 0; i < this.egoGuns.length; ++i) {
				((AbstractGun)this.egoGuns[i]).update(var1);
			}
		}

		if (this.enemyGuns != null) {
			for(i = 0; i < this.enemyGuns.length; ++i) {
				((AbstractGun)this.enemyGuns[i]).update(var1);
			}
		}

	}

	public final boolean checkObjective(final int var1) {
		return this.successObjective != null ? this.successObjective.achieved(var1) : false;
	}

	public final void stealFriendCargo() {
		this.stolenFromFriend = true;
	}

	public final boolean friendCargoWasStolen() {
		return this.stolenFromFriend;
	}

	public final void removeObjectives() {
		this.successObjective = null;
		this.failObjective_ = null;
	}

	public final AbstractMesh[] getPlayerGuns_() {
		return this.egoGuns;
	}

	public final boolean checkGameOver(final int var1) {
		return this.failObjective_ != null ? this.failObjective_.achieved(var1) : false;
	}

	/**
	 *
	 * @param alarmFriends false - warning; true - alarm friends
	 * @param var2 race 0-3
	 */
	private void createRadioMessage(final boolean alarmFriends, int var2) {
		if (this.ego != null && this.ego.radio != null && Status.getMission().isEmpty()) {
			this.radioMessages = new RadioMessage[1];
			var2 = var2 == Globals.VOSSK ? 24 : var2 == Globals.TERRAN ? 23 : var2 == Globals.MIDORIAN ? 21 : 22;
			final short var4 = (short)((alarmFriends ? 250 : 247) + GlobalStatus.random.nextInt(3));
			this.radioMessages[0] = new RadioMessage(var4, var2, 5, 0);
			this.ego.radio.setMessages(this.radioMessages);
			this.ego.radio.showMessage();
		}

	}

	public final void alarmAllFriends(final int race, final boolean bigDamage) {
		if (this.ships != null) {
			for(int i = 0; i < this.ships.length; ++i) {
				if (this.ships[i].race == race) {
					this.ships[i].player.setAlwaysEnemy(true);
				}
			}
		}

		if (!this.localsAlarmed && bigDamage) {
			this.localsAlarmed = true;
			createRadioMessage(true, race);
		}

	}

	public final void friendTurnedEnemy(final int var1) {
		if (!this.friendlyFireAlerted) {
			this.friendlyFireAlerted = true;
			createRadioMessage(false, var1);
		}

	}

	public final void onRelease() {
		AEResourceManager.initGeometryTranforms();
		int i;
		if (this.stationaries != null) {
			for(i = 0; i < this.stationaries.length; ++i) {
				if (this.stationaries[i] != null) {
					this.stationaries[i].OnRelease();
					this.stationaries[i] = null;
				}
			}
		}

		this.stationaries = null;
		if (this.gunSparks != null) {
			this.gunSparks.OnRelease();
		}

		this.gunSparks = null;
		if (this.missilesSparks != null) {
			this.missilesSparks.OnRelease();
		}

		this.missilesSparks = null;
		if (this.egoGuns != null) {
			for(i = 0; i < this.egoGuns.length; ++i) {
				this.egoGuns[i].OnRelease();
			}
		}

		this.egoGuns = null;
		if (this.enemyGuns != null) {
			for(i = 0; i < this.enemyGuns.length; ++i) {
				this.enemyGuns[i].OnRelease();
			}
		}

		this.enemyGuns = null;
		if (this.ego != null) {
			this.ego.OnRelease();
		}

		this.ego = null;
		if (this.ships != null) {
			for(i = 0; i < this.ships.length; ++i) {
				this.ships[i].OnRelease();
			}
		}

		this.ships = null;
		if (this.missionRoute != null) {
			this.missionRoute.onRelease();
		}

		this.missionRoute = null;
		if (this.kamikazePath != null) {
			this.kamikazePath.onRelease();
		}

		this.kamikazePath = null;
		if (this.enemyRoute_ != null) {
			this.enemyRoute_.onRelease();
		}

		this.enemyRoute_ = null;
		this.radioMessages = null;
		this.successObjective = null;
		this.failObjective_ = null;
		this.spaceCreationStep__ = 0;
		System.gc();
	}
}
