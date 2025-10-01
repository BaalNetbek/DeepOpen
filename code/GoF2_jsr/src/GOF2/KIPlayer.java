package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.GlobalStatus;
import AE.AEGroup;
import AE.Math.AEMath;
import AE.Math.AEVector3D;

/**
 * AIPlayer, parent for classes representing all interactive 3D 
 * objects in space (also figures in SpaceLongue),
 * e.g. asteroids, ships, stations, jumpgates
 * and NOT planets, projectiles etc.
 * 
 * KI, Künstliche Intelligenz (ger.)  
 * 
 * @author Fishlabs 2009
 */
public abstract class KIPlayer {

    // States
    public final static int ACTIVE = 1;
    
    public final static int DYING = 3;
    public final static int DEAD = 4;
    public final static int SLEEPING = 5;
    public final static int JUMPING = 6;

    protected short state = 0;
	protected int speed = 2;
	public Player player;
	public AEGeometry mainMesh_;
	public AEGroup geometry;
	protected int meshId;
	protected int shipId_;
	protected int race;
	protected Level level;
	protected Route activeRoute_;
	protected Explosion explosion;
	protected AEVector3D tempVector_ = new AEVector3D();
	protected AEVector3D position = new AEVector3D();
	protected int targetX;
	protected int targetY;
	protected int targetZ;
	protected boolean carriesMissionCrate;
	public boolean isAsteroid;
	public boolean junk;
	public boolean hasCargo;
	protected int[] cargo;
	public AEGeometry waste;
	protected AEGeometry jumpMesh;
	protected boolean diedWithMissionCrate;
	protected boolean lostMissionCrateToEgo;
	protected boolean unusedc3a_;
	protected int crateLifeTime;
	protected boolean wingman;
	protected int wingmanId;
	protected int wingmanCommand;
	protected KIPlayer wingmanTarget;
	protected boolean jumper;
	protected int jumpTick;
	public String name;
	public boolean stunned;
	public boolean armed;
	protected boolean visible;
	public boolean withinRenderDistance = true;

	public KIPlayer(final int meshId, final int race, final Player player, final AEGeometry mesh, final int x, final int y, final int z) {
		this.race = race;
		this.player = player;
		this.mainMesh_ = mesh;
		this.meshId = meshId;
		if (this.mainMesh_ != null) {
			this.mainMesh_.moveTo(x, y, z);
			this.mainMesh_.setRotation(0, 0, 0);
			this.player.transform = mesh.getToParentTransform();
		}

		this.player.setKIPlayer(this);
		this.armed = false;
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.isAsteroid = false;
		this.junk = false;
		this.hasCargo = false;
		this.carriesMissionCrate = false;
		this.diedWithMissionCrate = false;
		this.lostMissionCrateToEgo = false;
		this.unusedc3a_ = false;
		this.wingman = false;
		if (this.tempVector_ == null) {
			this.tempVector_ = new AEVector3D();
		}

		this.jumper = false;
		this.name = null;
		this.visible = true;
	}

	public final boolean isVisible() {
		return this.visible;
	}

	public final void setVisible(final boolean var1) {
		this.visible = var1;
		if (this.geometry != null) {
			this.geometry.setDraw(var1);
		}

		if (this.mainMesh_ != null) {
			this.mainMesh_.setDraw(var1);
		}

	}

	public final void setGroup(final AEGroup var1, final int var2) {
		this.shipId_ = var2;
		this.geometry = var1;
		this.geometry.moveTo(this.targetX, this.targetY, this.targetZ);
		this.geometry.setRotation(0, 0, 0);
		this.player.transform = var1.getToParentTransform();
	}

	public final void setJumper(final boolean var1) {
		this.jumper = true;
	}

	public final boolean isJumper() {
		return this.jumper;
	}

	public void OnRelease() {
		this.player = null;
		if (this.mainMesh_ != null) {
			this.mainMesh_.OnRelease();
		}

		this.mainMesh_ = null;
		this.geometry = null;
		this.level = null;
		this.activeRoute_ = null;
		if (this.explosion != null) {
			this.explosion.OnRelease();
		}

		this.explosion = null;
		this.jumpMesh = null;
	}

	public void setActive(final boolean var1) {
		this.player.setActive(var1);
	}

	public final void setInitActive(final boolean var1) {
		setActive(false);
	}

	public final void captureCrate(final Hud var1) {
		if (isDead() || isDying()) {
			this.hasCargo = false;
			setActive(false);
		}

		this.waste = null;
		Item var2 = null;

		for(int i = 0; i < this.cargo.length; i += 2) {
			if (this.cargo[i + 1] > 0) {
				final int var4 = AEMath.max(1, GlobalStatus.random.nextInt(this.cargo[i + 1]));
				var2 = Globals.getItems()[this.cargo[i]].makeItem(var4);
				final int[] var10000 = this.cargo;
				var10000[i + 1] -= var4;
				break;
			}
		}

		if (var2 != null) {
			if (this.player.friend) {
				this.level.stealFriendCargo();
			}

			Status.getStanding().applyStealCargo(this.race);
			final boolean var5 = this.carriesMissionCrate && (var2.getIndex() == 116 || var2.getIndex() == 117);
			if (Status.getShip().spaceAvailable(var2.getAmount())) {
				Status.incCargoSalvaged(var2.getAmount());
				if (var5) {
					var2.setUnsaleable(true);
				}

				Status.getShip().addCargo(var2);
				final Level var6 = this.level;
				var6.capturedCargoCount += var2.getAmount();
				if (var5) {
					this.lostMissionCrateToEgo = true;
				} else if (this.race == Globals.VOID) {
					Status.alienJunkSalvaged += var2.getAmount();
				} else if (var2.getIndex() >= 132 && var2.getIndex() < 154) {
					Status.drinkTypesPossesed[var2.getIndex() - 132] = true;
				}

				var1.catchCargo(var2.getIndex(), var2.getAmount(), false, var5, false, false);
				return;
			}

			if (var5) {
				this.diedWithMissionCrate = true;
			}

			var1.catchCargo(var2.getIndex(), var2.getAmount(), true, false, false, false);
		}

	}

	public final boolean isWingman() {
		return this.wingman;
	}

	public final void setWingman(final boolean set, final int id) {
		this.wingman = set;
		this.wingmanId = id;
		this.wingmanCommand = 2;
	}

	public void setWingmanCommand(final int cmd, final KIPlayer target) {
		this.wingmanCommand = cmd;
		this.wingmanTarget = target;
	}

	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	public final void setLevel(final Level lvl) {
		this.level = lvl;
	}

	public final void setExplosion(final Explosion expl) {
		this.explosion = expl;
	}

	public final void setRoute(final Route var1) {
		this.activeRoute_ = var1;
	}

	public final Route getRoute() {
		return this.activeRoute_;
	}

	public final void addGun(final Gun gun, final int var2) {
		if (this.player.guns != null && var2 <= 3 && var2 >= 0) { //why <=3 and not < 3? primary, secondary, turret did it forget something?
			this.player.guns[var2] = new Gun[1];
			this.player.guns[var2][0] = gun;
		}

	}
	/** or just id/customId? because PlayerFixedObject for example*/
	public final int getMeshId() {
		return this.meshId;
	}

	public AEVector3D getPosition(final AEVector3D var1) {
		if (this.mainMesh_ != null) {
			return this.mainMesh_.getLocalPos(var1);
		}
		return this.geometry != null ? this.geometry.getLocalPos(var1) : null;
	}

	public final boolean isDead() {
		return this.state == 4;
	}

	public final boolean isDying() {
		return this.state == 3;
	}

	public final void setDead() {
		this.state = 4;
		setActive(false);
	}

	public final void setToSleep() {
		this.state = 5;
		this.player.setActive(false);
	}

	public final void awake() {
		this.state = 1;
		this.player.setActive(true);
	}

	public final void setJumpMesh(final AEGeometry var1) {
		this.jumpMesh = var1;
	}
	/**
	 * @param type 0-box, 1-asteroid, 2-junk, 3-void_junk
	 */
	public final void createCrate(final int type) {
		switch (type) {
		case 0:
			this.waste = AEResourceManager.getGeometryResource(17);
			this.waste.setScale(AEMath.Q_4, AEMath.Q_4, AEMath.Q_4);
			break;
		case 1:
		default:
			this.waste = AEResourceManager.getGeometryResource(6769);
			this.waste.setScale(AEMath.Q_EIGHTH, AEMath.Q_EIGHTH, AEMath.Q_EIGHTH);
			break;
		case 2:
			this.waste = AEResourceManager.getGeometryResource(9996);
			this.waste.setScale(AEMath.Q_HALF, AEMath.Q_HALF, AEMath.Q_HALF);
			break;
		case 3:
			this.waste = AEResourceManager.getGeometryResource(6767);
			this.waste.setScale(AEMath.Q_HALF, AEMath.Q_HALF, AEMath.Q_HALF);
			break;
		}

		this.waste.setRenderLayer(2);
		this.waste.moveTo(this.mainMesh_ != null ? this.mainMesh_.getPostition() : this.geometry.getPostition());
		this.player.transform = this.waste.getToParentTransform();
		this.player.setKIPlayer(this);
	}

	public final boolean cargoAvailable_() {
		boolean available = false;
		if (this.cargo != null) {
			for(int i = 0; i < this.cargo.length; i += 2) {
				if (this.cargo[i + 1] > 0) {
					available = true;
					break;
				}
			}
		}

		return available;
	}

	public abstract void update(long var1);

	public abstract boolean outerCollide(int var1, int var2, int var3);

	public boolean outerCollide(final AEVector3D var1) {
		return false;
	}

	public AEVector3D getProjectionVector_(final AEVector3D var1) {
		return null;
	}

	public void revive() {
	}

	public void setPosition(final int var1, final int var2, final int var3) {
	}

	public void appendToRender() {
		if (this.mainMesh_ != null) {
			GlobalStatus.renderer.drawNodeInVF(this.mainMesh_);
		} else {
			GlobalStatus.renderer.drawNodeInVF(this.geometry);
		}
	}
}
