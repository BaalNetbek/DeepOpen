package GOF2.Main;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import AE.GlobalStatus;

public class GOF2MIDlet extends MIDlet implements Runnable {
	public int state;
	public static final int RUNNING = 1;
	public static final int RESUME = 2;
	public static final int PAUSE = 3;
	public static final int PRE_PAUSE = 4;
	private GOF2Canvas canvas;
	private Display display;
	private boolean started = false;
	private boolean selfDestructing = false;
	private Thread thread;

	public GOF2MIDlet() {
		try {
			this.display = Display.getDisplay(this);
			GlobalStatus.display = this.display;
			GlobalStatus.midlet = this;
			this.canvas = new GOF2Canvas(this);
			this.thread = new Thread(this);
			this.state = 1;
		} catch (final Exception var1) {
		}
	}

	public Display getDisplay() {
		return this.display;
	}

	public void startApp() {
		try {
			this.display.setCurrent(this.canvas);
			this.canvas.setFullScreenMode(true);
			if (!this.started) {
				this.thread.start();
				this.started = true;
			}

		} catch (final Exception var1) {
		}
	}

	public void pauseApp() {
		pause();
	}

	public void resume() {
		this.state = 2;
	}

	public void pause() {
		this.state = 4;
	}

	public void destroyApp(final boolean var1) {
		try {
			this.selfDestructing = true;
			this.canvas.OnRelease();
			notifyDestroyed();
		} catch (final Exception var2) {
		}
	}

	public void run() {
		try {
			while(true) {
				switch(this.state) {
				case 1:
					if (GlobalStatus.soundDeviceUnavailable && !this.selfDestructing && GlobalStatus.currentMusic != -1) {
						GlobalStatus.soundManager.playMusic(GlobalStatus.currentMusic);
					}

					this.canvas.update_();
					this.canvas.synchronizeKeyState();
					break;
				case 2:
					if (GlobalStatus.soundManager != null && GlobalStatus.musicOn) {
						GlobalStatus.soundManager.resume();
					}

					GlobalStatus.paused = false;
					this.state = 1;
					break;
				case 3:
					this.canvas.handlePausedState();
					this.canvas.synchronizeKeyState();
					break;
				case 4:
					this.canvas.pause();
					this.state = 3;
				}

				try {
					Thread.sleep(2L);
				} catch (final InterruptedException var2) {
				}
			}
		} catch (final Exception var3) {
			var3.printStackTrace();
		}
	}

	public void quit() {
		destroyApp(false);
	}
}
