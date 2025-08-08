package GOF2;

import AE.IApplicationModule;
import GOF2.Main.GOF2MIDlet;

public final class ApplicationManager {
	private IApplicationModule scene = null;
	private final GOF2MIDlet midlet;
	private final LoadingScreen loadingScreen;

	public ApplicationManager(final GOF2MIDlet var1, final LoadingScreen var2) {
		this.midlet = var1;
		this.loadingScreen = var2;
	}

	public final void SetCurrentApplicationModule(final IApplicationModule var1) {
		if (this.scene != null) {
			if (this.loadingScreen != null) {
				this.loadingScreen.startLoading_(true);
			}

			GameText.init();
			this.scene.OnRelease();
		}

		this.scene = var1;
	}

	public final IApplicationModule GetCurrentApplicationModule() {
		return this.scene;
	}

	public final void Quit() {
		this.midlet.quit();
	}

	public final void handleKeystate(final int var1) {
		if (this.scene != null) {
			this.scene.handleKeystate(var1);
		}

	}

	public final void renderScene(final int var1) {
		if (this.scene != null) {
			if (this.scene.isLoaded()) {
				this.scene.renderScene(var1);
				return;
			}

			this.scene.OnInitialize();
			if (this.scene.isLoaded() && this.loadingScreen != null) {
				this.loadingScreen.close();
			}

			System.gc();
		}

	}
}
