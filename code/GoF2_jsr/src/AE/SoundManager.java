package AE;

import java.io.InputStream;

import javax.microedition.lcdui.AlertType;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public final class SoundManager implements PlayerListener {
	private static final String[] MUSIC_FILES_PATHS = {
	      "/data/sound/gof2_theme.mid",
	      "/data/sound/gof2_hangar.mid",
	      "/data/sound/gof2_bar.mid",
	      "/data/sound/gof2_gaction.mid",
	      "/data/sound/gof2_gneutral.mid"
	      };
	private static final String[] SFX_FILES_PATHS = {
	      "/data/sound/fx_boost_01.wav",
	      "/data/sound/fx_explosion_01.wav",
	      "/data/sound/fx_message_03.wav",
	      "/data/sound/fx_message_02.wav",
	      "/data/sound/fx_menu_04.wav",
	      "/data/sound/fx_explosion_03.wav",
	      "/data/sound/fx_mining_05.wav",
	      "/data/sound/fx_boost_02.wav",
	      "/data/sound/wpn_rocket_02.wav",
	      "/data/sound/wpn_rocket_03.wav",
	      "/data/sound/wpn_rocket_04.wav",
	      "/data/sound/fx_thunder_01.wav",
	      "/data/sound/wpn_nuke_02.wav",
	      "/data/sound/fx_message_05.wav"
	      };
	private static int lastSfxIndex;
	private static long sfxStartTime;
	private static final long[] SFX_DURATIONS = {
	      1663L, 1542L, 297L, 551L, 50L, 790L, 2147L,
	      3087L, 576L, 884L, 892L, 3194L, 1697L, 215L};
	private static short[] SFX_VOLUMES = {
	      100, 95, 60, 70, 50, 80, 100,
	      100, 60, 60, 60, 100, 100, 80};
	private static Player musicPlayer;
	private final Player[] sfxPlayers;
	private VolumeControl tmpVolumeController;
	private int musicVolume = 100;
	private int sfxVolume = 100;
	private boolean deviceAvailabe_ = false;
	private int tmpMusicVolume;

	public SoundManager() {
		this.sfxPlayers = new Player[SFX_FILES_PATHS.length];
	}

	public final void setMusicVolume(final int var1) {
		if (musicPlayer != null && musicPlayer.getState() != 0) {
			this.tmpVolumeController = (VolumeControl)musicPlayer.getControl("VolumeControl");
			this.tmpVolumeController.setLevel(var1);
		}

		this.musicVolume = var1;
	}

	public final void setSfxVolume(final int var1) {
		if (this.sfxPlayers != null) {
			for(int i = 0; i < this.sfxPlayers.length; ++i) {
				if (this.sfxPlayers[i] != null && this.sfxPlayers[i].getState() != 0) {
					this.tmpVolumeController = (VolumeControl)this.sfxPlayers[i].getControl("VolumeControl");
					this.tmpVolumeController.setLevel(var1);
				}
			}
		}

		this.sfxVolume = var1;
	}

	public final int getMusicVolume() {
		return this.musicVolume;
	}

	public final void OnRelease() {
		if (musicPlayer != null) {
			if (musicPlayer.getState() != 0) {
				musicPlayer.deallocate();
			}

			musicPlayer.close();
			musicPlayer = null;
		}

		this.tmpVolumeController = null;
	}

	public static boolean isMusicPlaying() {
		if (!GlobalStatus.musicOn) {
			return false;
		}
		return musicPlayer != null && musicPlayer.getState() == 400;
	}

	public final void playMusic(final int var1) {
		if (GlobalStatus.musicOn && GlobalStatus.currentMusic != var1) {
			stopMusic__();

			try {
				(musicPlayer = Manager.createPlayer(getClass().getResourceAsStream(MUSIC_FILES_PATHS[var1]), "audio/midi")).addPlayerListener(this);
				musicPlayer.setLoopCount(-1);
				musicPlayer.start();
				GlobalStatus.currentMusic = var1;
				VolumeControl var5 = (VolumeControl)musicPlayer.getControl("VolumeControl");
				var5.setLevel(40);

				try {
					var5.setMute(false);
				} catch (final Exception var3) {
				}

				GlobalStatus.soundDeviceUnavailable = false;
			} catch (final Exception var4) {
				GlobalStatus.soundDeviceUnavailable = true;
				if (musicPlayer != null) {
					musicPlayer.deallocate();
					musicPlayer.close();
				}

				musicPlayer = null;
			}
		}
	}

	public final void playSfx(final int var1, final int var2) {
		if (GlobalStatus.sfxOn && (System.currentTimeMillis() > sfxStartTime + SFX_DURATIONS[lastSfxIndex] || SFX_VOLUMES[var1] >= SFX_VOLUMES[lastSfxIndex])) {
			try {
				if (this.sfxPlayers[var1] == null) {
					try {
						final InputStream var3 = getClass().getResourceAsStream(SFX_FILES_PATHS[var1]);
						this.sfxPlayers[var1] = Manager.createPlayer(var3, "audio/x-wav");
						this.sfxPlayers[var1].setLoopCount(1);
						this.sfxPlayers[var1].prefetch();
					} catch (final Exception var4) {
						var4.printStackTrace();
					}
				}

				((VolumeControl)this.sfxPlayers[var1].getControl("VolumeControl")).setLevel(var2 - var2 / 3);
				this.sfxPlayers[var1].stop();
				this.sfxPlayers[var1].setMediaTime(0L);
				this.sfxPlayers[var1].start();
				lastSfxIndex = var1;
				sfxStartTime = System.currentTimeMillis();
			} catch (final Exception var5) {
				var5.printStackTrace();
			}
		}
	}

	public final void playSfx(final int var1) {
		this.playSfx(var1, this.sfxVolume);
	}

	public final void stop() {
		try {
			try {
				final VolumeControl var1 = (VolumeControl)musicPlayer.getControl("VolumeControl");
				this.tmpMusicVolume = var1.getLevel();
				var1.setMute(true);
			} catch (final Exception var2) {
			}

			musicPlayer.stop();

			for(int i = 0; i < this.sfxPlayers.length; ++i) {
				if (this.sfxPlayers[i] != null) {
					this.sfxPlayers[i].stop();
				}
			}

		} catch (final Exception var3) {
		}
	}

	public final void resume() {
		try {
			if (GlobalStatus.musicOn) {
				try {
					VolumeControl var1 = (VolumeControl)musicPlayer.getControl("VolumeControl");
					var1.setMute(false);
					AlertType.ERROR.playSound(GlobalStatus.display);
					var1.setLevel(this.tmpMusicVolume);
				} catch (final Exception var3) {
				}

				musicPlayer.start();
			}

			GlobalStatus.soundDeviceUnavailable = false;
		} catch (final Exception var4) {
			GlobalStatus.soundDeviceUnavailable = true;
			if (musicPlayer != null) {
				try {
					musicPlayer.deallocate();
					musicPlayer.close();
				} catch (final Exception var2) {
				}
			}

			musicPlayer = null;
		}
	}

	public static void stopMusic__() {
		try {
			if (musicPlayer.getState() == 400) {
				musicPlayer.stop();
				musicPlayer.deallocate();
				musicPlayer.close();
			}

		} catch (final Exception var0) {
		}
	}

	public final void playerUpdate(final Player var1, final String var2, final Object var3) {
		if (var2.compareTo("deviceUnavailable") == 0 && musicPlayer != null && musicPlayer.getState() != 0) {
			this.deviceAvailabe_ = true;
		}

		if (var2.compareTo("deviceAvailable") == 0 && this.deviceAvailabe_) {
			this.deviceAvailabe_ = false;
			GlobalStatus.soundDeviceUnavailable = true;
		}

	}
}
