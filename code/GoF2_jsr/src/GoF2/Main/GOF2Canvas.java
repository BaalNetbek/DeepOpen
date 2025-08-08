package GOF2.Main;

import java.util.Random;

import javax.microedition.lcdui.game.GameCanvas;

import AE.AEResourceManager;
import AE.GlobalStatus;
import AE.IApplicationModule;
import AE.Renderer;
import AE.SoundManager;
import AE.PaintCanvas.AEGraphics3D;
import AE.PaintCanvas.Font;
import GOF2.ApplicationManager;
import GOF2.GameText;
import GOF2.Globals;
import GOF2.Layout;
import GOF2.LoadingScreen;
import GOF2.RecordHandler;
import GOF2.Status;

public final class GOF2Canvas extends GameCanvas {
	private final GOF2MIDlet midlet;
	private int keyStates;
	private int keysState;
	private int lastKeyStates;
	private int lastFrameSignleKeyRelease;
	private int newKeysPressed;
	private int tempKeysPressed;
	private ApplicationManager applicationManager = null;
	private LoadingScreen loadingScreen;
	private final boolean unused355_;
	private boolean resized;
	private boolean initialized;
	private long pausedTime;
	private long sysTime;

	GOF2Canvas(final GOF2MIDlet var1) {
		super(false);
		GlobalStatus.checkDeviceControlSupport();
		this.resized = true;
		this.initialized = false;
		this.midlet = var1;
		this.unused355_ = false;
		this.pausedTime = 0L;
		this.sysTime = 0L;
		GlobalStatus.display = var1.getDisplay();
		GlobalStatus.random = new Random();

		try {
			GlobalStatus.gameVersion = "v " + var1.getAppProperty("MIDlet-Version");
		} catch (final Exception var2) {
			var2.printStackTrace();
		}
	}

	public final void pause() {
		if (this.applicationManager.GetCurrentApplicationModule() == GlobalStatus.scenes[2]) {
			((MGame)GlobalStatus.scenes[2]).pause();
		}

		GlobalStatus.soundManager.stop();
		GlobalStatus.paused = true;
	}

	protected final void hideNotify() {
		this.midlet.pause();
	}

	final synchronized void update_() {
		if (this.resized) {
			this.resized = false;
			GlobalStatus.screenWidth = getWidth();
			GlobalStatus.screenHeight = getHeight();
			setFullScreenMode(true);
			this.flushGraphics();
			GlobalStatus.screenWidth = getWidth();
			GlobalStatus.screenHeight = getHeight();
			setFullScreenMode(true);
		} else if (!this.initialized) {
			this.initialized = true;
			GlobalStatus.graphics = getGraphics();
			GlobalStatus.screenWidth = getWidth();
			GlobalStatus.screenHeight = getHeight();
			(GlobalStatus.gameText = new GameText()).setLanguage(GlobalStatus.language);
			GlobalStatus.screenWidth = getWidth();
			GlobalStatus.screenHeight = getHeight();
			setFullScreenMode(true);
			Font.setGraphics(GlobalStatus.graphics);
			Font.addCharMap("/data/interface/font_w.png", 0, 15, 16);
			Font.setPrimarySymMapSpacing(0);
			Font.setMainFontSpacingY(11);
			Font.setSymMapSetOffsetY(-2, 0);
			Font.addCharMap("/data/interface/font_g.png", 1, 15, 16);
			Font.setSymMapSpacing(0, 1);
			Font.setSpacingY(11, 1);
			Font.setSymMapSetOffsetY(-2, 1);
			Font.addCharMap("/data/interface/font_r.png", 2, 15, 16);
			Font.setSymMapSpacing(0, 2);
			Font.setSpacingY(11, 2);
			Font.setSymMapSetOffsetY(-2, 2);
			Font.addCharMap("/data/interface/font_void.png", 2, 15, 16);
			Font.setSymMapSpacing(0, 3);
			Font.setSpacingY(11, 3);
			Font.setSymMapSetOffsetY(-2, 3);
			Layout.OnInitialize();
			this.loadingScreen = new LoadingScreen();
			this.loadingScreen.setGameCanvas(this);
			this.loadingScreen.startLoading_(true);
			GlobalStatus.loadingScreen = this.loadingScreen;
			GlobalStatus.resetHints();
			new RecordHandler().readOptions();
			GlobalStatus.musicOn = false;
			GlobalStatus.graphics3D = new AEGraphics3D();
			(GlobalStatus.renderer = new Renderer(GlobalStatus.graphics3D)).addLayer();
			GlobalStatus.renderer.addLayer();
			GlobalStatus.renderer.addLayer();
			GlobalStatus.soundManager = new SoundManager();
			Globals.buildResourceList();
			Globals.OnInitialize();
			Status.setCredits(0);
			this.applicationManager = new ApplicationManager(this.midlet, this.loadingScreen);
			GlobalStatus.applicationManager = this.applicationManager;
			(GlobalStatus.scenes = new IApplicationModule[4])[3] = new Intro();
			GlobalStatus.scenes[0] = new ModMainMenu();
			GlobalStatus.scenes[1] = new ModStation();
			GlobalStatus.scenes[2] = new MGame();
			Status.startNewGame();
			this.applicationManager.SetCurrentApplicationModule(GlobalStatus.scenes[3]);
		} else {
			Layout.navigationDelayDownTick();
			this.applicationManager.renderScene(this.keyStates);
			if (this.newKeysPressed == 16384) {
				Layout.selectNavigationButton(true);
				this.tempKeysPressed = this.newKeysPressed;
			} else if (this.newKeysPressed == 8192) {
				Layout.selectNavigationButton(false);
				this.tempKeysPressed = this.newKeysPressed;
			}

			if (Layout.navigationDelayPassed()) {
				this.applicationManager.handleKeystate(this.tempKeysPressed);
				this.tempKeysPressed = -1;
			}

			if (this.newKeysPressed != -1 && this.tempKeysPressed == -1) {
				this.applicationManager.handleKeystate(this.newKeysPressed);
			}

			this.newKeysPressed = -1;
			this.lastFrameSignleKeyRelease = -1;
			this.flushGraphics();
		}
	}

	final void OnRelease() {
		this.applicationManager.GetCurrentApplicationModule().OnRelease();
		Globals.OnRelease();
		Layout.OnRelease();
		Font.OnRelease();
		GlobalStatus.soundManager.OnRelease();
		AEResourceManager.OnRelease();
		this.loadingScreen.close();
		this.loadingScreen = null;
	}

	public final void handlePausedState() {
		Layout.drawBG();
		Font.drawLinesAligned(Font.splitToLines(GlobalStatus.gameText.getText(81), GlobalStatus.screenWidth - 20), GlobalStatus.screenWidth >> 1, GlobalStatus.screenHeight >> 1, 1, 24);
		if (this.sysTime == 0L) {
			this.sysTime = System.currentTimeMillis();
		}

		final long var1 = System.currentTimeMillis();
		this.pausedTime += var1 - this.sysTime;
		this.sysTime = var1;
		if (this.newKeysPressed == 256) {
			if (Status.getPlayingTime() > this.pausedTime) {
				Status.incPlayingTime(-this.pausedTime);
			}

			this.pausedTime = 0L;
			this.sysTime = 0L;
			this.midlet.resume();
			this.newKeysPressed = 0;
			this.lastFrameSignleKeyRelease = 0;
			this.keysState = 0;
			this.lastKeyStates = 0;
			this.keyStates = 0;
		}

		this.flushGraphics();
	}

	final synchronized void synchronizeKeyState() {
		this.keyStates = this.keysState;
	}

	public final void keyPressed(final int var1) {
		this.lastKeyStates = this.keysState;
		if (var1 == -7) {
			this.keysState |= 8192;
		}

		if (var1 == -6) {
			this.keysState |= 16384;
		}

		if (var1 == 49 || var1 == 101 || var1 == 114) {
			this.keysState |= 32768;
		}

		if (var1 == 51 || var1 == 105 || var1 == 117) {
			this.keysState |= 65536;
		}

		if (var1 == 48 || var1 == 32) {
			this.keysState |= 131072;
		}

		if (var1 == -11) {
			this.keysState |= 262144;
		}

		if (var1 == -8) {
			this.keysState |= 524288;
		}

		if (var1 == -1 || var1 == 50 || var1 == 121 || var1 == 116) {
			this.keysState |= 2;
		}

		if (var1 == -2 || var1 == 56 || var1 == 98 || var1 == 118) {
			this.keysState |= 64;
		}

		if (var1 == -3 || var1 == 52 || var1 == 100 || var1 == 102) {
			this.keysState |= 4;
		}

		if (var1 == -4 || var1 == 54 || var1 == 106 || var1 == 107) {
			this.keysState |= 32;
		}

		if (var1 == -5 || var1 == 53 || var1 == 103 || var1 == 104) {
			this.keysState |= 256;
		}

		if (var1 == 55 || var1 == 99 || var1 == 120) {
			this.keysState |= 512;
		}

		if (var1 == 57 || var1 == 109 || var1 == 110) {
			this.keysState |= 1024;
		}

		if (var1 == 42) {
			this.keysState |= 2048;
		}

		if (var1 == 35) {
			this.keysState |= 4096;
		}

		this.newKeysPressed = this.keysState & ~this.lastKeyStates;
	}

	public final void keyRepeated(final int var1) {
		keyPressed(var1);
	}

	public final void keyReleased(final int var1) {
		this.lastKeyStates = this.keysState;
		if (var1 == -7) {
			this.keysState &= -8193;
		}

		if (var1 == -6) {
			this.keysState &= -16385;
		}

		if (var1 == 49 || var1 == 101 || var1 == 114) {
			this.keysState &= -32769;
		}

		if (var1 == 51 || var1 == 105 || var1 == 117) {
			this.keysState &= -65537;
		}

		if (var1 == 48 || var1 == 32) {
			this.keysState &= -131073;
		}

		if (var1 == -11) {
			this.keysState &= -262145;
		}

		if (var1 == -8) {
			this.keysState &= -524289;
		}

		if (var1 == -1 || var1 == 50 || var1 == 121 || var1 == 116) {
			this.keysState &= -3;
		}

		if (var1 == -2 || var1 == 56 || var1 == 98 || var1 == 118) {
			this.keysState &= -65;
		}

		if (var1 == -3 || var1 == 52 || var1 == 100 || var1 == 102) {
			this.keysState &= -5;
		}

		if (var1 == -4 || var1 == 54 || var1 == 106 || var1 == 107) {
			this.keysState &= -33;
		}

		if (var1 == -5 || var1 == 53 || var1 == 103 || var1 == 104) {
			this.keysState &= -257;
		}

		if (var1 == 55 || var1 == 99 || var1 == 120) {
			this.keysState &= -513;
		}

		if (var1 == 57 || var1 == 109 || var1 == 110) {
			this.keysState &= -1025;
		}

		if (var1 == 42) {
			this.keysState &= -2049;
		}

		if (var1 == 35) {
			this.keysState &= -4097;
		}

		this.lastFrameSignleKeyRelease = ~this.keysState & this.lastKeyStates;
	}
}
