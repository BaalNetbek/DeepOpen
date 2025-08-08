package GOF2.Main;

import AE.GlobalStatus;
import AE.IApplicationModule;
import GOF2.FileRead;
import GOF2.Status;

public final class ModMainMenu extends IApplicationModule {
	private long frameStartTime;
	private long lastFrameStartTime;
	private long frameTime;
	private long totalTimeRendered;
	private boolean drawUI;
	private CutScene cutScene;
	private OptionsWindow optionsWindow;
	private boolean optionWindowOpen = true;
	private static int stationIndex = 0;

	public final void OnRelease() {
		if (this.cutScene != null) {
			this.cutScene.OnRelease();
		}

		this.cutScene = null;
		this.drawUI = false;
		System.gc();
	}

	public final void OnInitialize() {
		if (this.cutScene == null) {
			stationIndex = GlobalStatus.random.nextInt(100);
			Status.setStation(new FileRead().loadStation(stationIndex));
			this.cutScene = new CutScene(2);
		}

		if (!this.cutScene.isLoaded()) {
			this.cutScene.OnInitialize();
		} else {
			Status.setPlayingTime(0L);
			this.optionsWindow = new OptionsWindow();
			this.optionsWindow.reset_(0);
			System.gc();
			this.frameStartTime = System.currentTimeMillis();
			this.lastFrameStartTime = System.currentTimeMillis();
			this.drawUI = true;
			this.optionWindowOpen = true;
		}
	}

	public final boolean isLoaded() {
		return this.drawUI;
	}

	public final void renderScene(final int var1) {
		if (this.drawUI) {
			this.frameStartTime = System.currentTimeMillis();
			this.frameTime = this.frameStartTime - this.lastFrameStartTime;
			this.lastFrameStartTime = this.frameStartTime;
			this.totalTimeRendered += this.frameTime;
			this.cutScene.renderScene(0);
			if (this.optionWindowOpen) {
				this.optionsWindow.scrollAndTick_(var1, (int)this.frameTime);
				this.optionsWindow.draw();
			}

		}
	}

	public final void handleKeystate(final int var1) {
		if (this.drawUI && this.optionWindowOpen) {
			this.optionsWindow.handleKeystate(var1);
			if (var1 == 4) {
				this.optionsWindow.optionsLeft();
			}

			if (var1 == 32) {
				this.optionsWindow.optionsRight();
			}

			if (var1 == 2) {
				this.optionsWindow.scrollUp((int)this.frameTime);
			}

			if (var1 == 64) {
				this.optionsWindow.scrollDown((int)this.frameTime);
			}

			if (var1 == 256) {
				this.optionsWindow.update();
			}

			if (var1 == 262144) {
				this.optionsWindow.update1_();
			}

			if (var1 == 16384) {
				this.optionsWindow.update1_();
			}

			if (var1 == 8192) {
				this.optionsWindow.goBack();
			}
		}
	}
}
