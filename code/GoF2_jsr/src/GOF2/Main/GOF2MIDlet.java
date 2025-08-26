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
			this.state = RUNNING;
		} catch (final Exception e) {
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

		} catch (final Exception e) {
		}
	}

	public void pauseApp() {
		pause();
	}

	public void resume() {
		this.state = RESUME;
	}

	public void pause() {
		this.state = PRE_PAUSE;
	}

	public void destroyApp(final boolean var1) {
		try {
			this.selfDestructing = true;
			this.canvas.OnRelease();
			notifyDestroyed();
		} catch (final Exception e) {
		}
	}

	public void run() {
		try {
			while(true) {
				switch(this.state) {
				case RUNNING:
					if (GlobalStatus.soundDeviceUnavailable && !this.selfDestructing && GlobalStatus.currentMusic != -1) {
						GlobalStatus.soundManager.playMusic(GlobalStatus.currentMusic);
					}

					this.canvas.update_();
					this.canvas.synchronizeKeyState();
					break;
				case RESUME:
					if (GlobalStatus.soundManager != null && GlobalStatus.musicOn) {
						GlobalStatus.soundManager.resume();
					}

					GlobalStatus.paused = false;
					this.state = RUNNING;
					break;
				case PAUSE:
					this.canvas.handlePausedState();
					this.canvas.synchronizeKeyState();
					break;
				case PRE_PAUSE:
					this.canvas.pause();
					this.state = PAUSE;
				}

				try {
					Thread.sleep(2L);
				} catch (final InterruptedException var2) {
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void quit() {
		destroyApp(false);
	}
}
