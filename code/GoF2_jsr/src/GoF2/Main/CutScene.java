package GoF2.Main;

import AE.AECamera;
import AE.CameraControllerGroup;
import AE.CameraTrack;
import AE.GlobalStatus;
import AE.Group;
import AE.IApplicationModule;
import AE.LookAtCamera;
import AE.TargetFollowCamera;
import AE.Math.AEMath;
import AE.Math.AEMatrix;
import GoF2.Globals;
import GoF2.KIPlayer;
import GoF2.Level;
import GoF2.PlayerEgo;
import GoF2.PlayerFighter;
import GoF2.Status;

public final class CutScene extends IApplicationModule {
	private long renderAtTime;
	private long lastRenderAtTime;
	private long frameTimeMs;
	private long lifeTime;
	private boolean loaded;
	private CameraControllerGroup cameraControllers_unused;
	private AECamera gameCam_;
	private AECamera hangarCam;
	private CameraTrack cameraTrack;
	private TargetFollowCamera followingCam_unused;
	private LookAtCamera lookAtcam_unused;
	private PlayerEgo egoPlayer;
	public Level level;
	private final int sceneId;
	private Group cameraGroupOfOne_;
	private float forcedSpeedCCW;
	private float forcedSpeedCW;
	private float rotation;
	private int shipPosY;
	private int shipPosZ;

	public CutScene(final int var1) {
		this.sceneId = var1;
	}

	public final void OnRelease() {
		if (this.egoPlayer != null) {
			this.egoPlayer.OnRelease();
		}

		this.egoPlayer = null;
		this.gameCam_ = null;
		if (this.level != null) {
			this.level.onRelease();
		}

		this.level = null;
		this.lookAtcam_unused = null;
		this.followingCam_unused = null;
		this.cameraControllers_unused = null;
		this.loaded = false;
		System.gc();
	}

	public final void resetCamera() {
		switch(this.sceneId) {
		case 4:
			if (this.gameCam_ == null) {
				this.gameCam_ = AECamera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 1024, 200, 28000);
				this.gameCam_.setRotationOrder((short)2);
			}

			GlobalStatus.renderer.setActiveCamera(this.gameCam_);
			this.gameCam_.moveTo(920, 500, -1240);
			this.gameCam_.setRotation(-160, AEMath.Q_PI_HALF, 0);
			return;
		case 23:
			GlobalStatus.renderer.setActiveCamera(this.hangarCam);
			KIPlayer[] var1;
			if ((var1 = this.level.getEnemies())[0].getMeshId() >= 0) {
				((PlayerFighter)var1[0]).setExhaustVisible(false);
				return;
			}
			break;
		default:
			if (this.cameraTrack != null) {
				GlobalStatus.renderer.setActiveCamera(this.cameraTrack.getCamera());
			}
		}

	}

	public final void OnInitialize() {
		try {
			if (this.level == null) {
				this.level = new Level(this.sceneId);
			}

			if (!this.level.createSpace()) {
				return;
			}

			this.egoPlayer = this.level.getPlayer();
			if (this.egoPlayer != null) {
				this.egoPlayer.setActive(false);
			}

			switch(this.sceneId) {
			case 0:
				this.cameraTrack = new CameraTrack(1);
				GlobalStatus.renderer.setActiveCamera(this.cameraTrack.getCamera());
				break;
			case 2:
				this.cameraTrack = new CameraTrack(0);
				GlobalStatus.renderer.setActiveCamera(this.cameraTrack.getCamera());
				break;
			case 4:
				resetCamera();
				break;
			case 23:
				this.hangarCam = AECamera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 900, 10, 31768);
				this.hangarCam.moveTo(0, 1700, 1500);
				this.hangarCam.setRotation(-AEMath.Q_SIXTEENTH, 0, 0);
				GlobalStatus.renderer.setActiveCamera(this.hangarCam);
				this.cameraGroupOfOne_ = new Group();
				this.cameraGroupOfOne_.uniqueAppend_(this.hangarCam);
				this.cameraGroupOfOne_.translate(0, 0, 10240);
				this.rotation = AEMath.Q_THREE_EIGHTHS;
				this.cameraGroupOfOne_.updateTransform(true);
				KIPlayer var1 = this.level.getEnemies()[0];
				if (var1.getMeshId() >= 0) {
					this.shipPosY = var1.geometry.getPosY();
					this.shipPosZ = var1.geometry.getPosZ();
				}
			}
		} catch (final Exception var2) {
			var2.printStackTrace();
		}

		System.gc();
		this.lifeTime = 0L;
		this.renderAtTime = System.currentTimeMillis();
		this.lastRenderAtTime = System.currentTimeMillis();
		this.loaded = true;
	}

	public final boolean isLoaded() {
		return this.loaded;
	}

	public final void replacePlayerShip(final int var1, final int var2) {
		if (this.level.getEnemies() != null && this.level.getEnemies()[0] != null && this.level.getEnemies()[0].geometry != null) {
			final AEMatrix var3 = this.level.getEnemies()[0].geometry.getToParentTransform();
			this.level.getEnemies()[0].setGroup(Globals.getShipGroup(var1, var2), var1);
			this.level.getEnemies()[0].geometry.setTransform(var3);
		}

	}

	public final void renderScene(final int var1) {
		if (this.loaded) {
			this.renderAtTime = System.currentTimeMillis();
			this.frameTimeMs = this.renderAtTime - this.lastRenderAtTime;
			this.lastRenderAtTime = this.renderAtTime;
			this.lifeTime += this.frameTimeMs;
			if (this.cameraTrack != null && (this.sceneId == 2 || this.lifeTime < 10000L)) {
				this.cameraTrack.update(this.frameTimeMs);
			}

			try {
				this.level.updateOrbit_(this.frameTimeMs);
				this.level.render(this.frameTimeMs);
				GlobalStatus.graphics3D.bindTarget(GlobalStatus.graphics);
				GlobalStatus.renderer.renderFrame(System.currentTimeMillis());
				GlobalStatus.graphics3D.clear();
				GlobalStatus.graphics3D.releaseTarget();
			} catch (final Exception var4) {
				GlobalStatus.graphics3D.releaseTarget();
				var4.printStackTrace();
			}

			if (this.sceneId == 23) {
				final int var6 = AEMath.sin((int)this.lifeTime) >> 7;
				KIPlayer[] var8;
				if ((var8 = this.level.getEnemies())[0].getMeshId() >= 0) {
					var8[0].geometry.moveTo(0, this.shipPosY + var6, this.shipPosZ);
				}

				boolean var7 = false;
				if ((var1 & 4) != 0) {
					this.forcedSpeedCCW = this.frameTimeMs;
					var7 = true;
				} else {
					this.forcedSpeedCCW *= 0.9F;
				}

				if ((var1 & 32) != 0) {
					this.forcedSpeedCW = this.frameTimeMs;
					var7 = true;
				} else {
					this.forcedSpeedCW *= 0.9F;
				}

				this.rotation += this.forcedSpeedCCW - this.forcedSpeedCW;
				if (!var7) {
					this.rotation += (int)this.frameTimeMs / 6;
				}

				this.cameraGroupOfOne_.setRotation(0, (int)this.rotation, 0);
				this.cameraGroupOfOne_.updateTransform(true);
			} else if (this.sceneId == 4) {
				if (this.lifeTime > 500L) {
					this.lifeTime = 0L;
					final KIPlayer[] var5 = this.level.getEnemies();

					for(int var3 = 0; var3 < Status.getStation().getBarAgents().length; ++var3) {
						if (GlobalStatus.random.nextInt(100) < 10 && !var5[var3].mainMesh_.hasAnimation()) {
							var5[var3].mainMesh_.setAnimationMode((byte)1);
						}
					}
				}
			} else {
				this.level.renderRockets_();
			}
		}
	}

	public final void handleKeystate(final int var1) {
	}
}
