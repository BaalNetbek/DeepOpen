package AbyssEngine;

import java.io.InputStream;
import javax.microedition.lcdui.AlertType;
import javax.microedition.media.Manager;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public final class SoundManager implements PlayerListener {
   private static final String[] MUSIC_FILES_PATHS = new String[]{"/data/sound/gof2_theme.mid", "/data/sound/gof2_hangar.mid", "/data/sound/gof2_bar.mid", "/data/sound/gof2_gaction.mid", "/data/sound/gof2_gneutral.mid"};
   private static final String[] SFX_FILES_PATHS = new String[]{"/data/sound/fx_boost_01.wav", "/data/sound/fx_explosion_01.wav", "/data/sound/fx_message_03.wav", "/data/sound/fx_message_02.wav", "/data/sound/fx_menu_04.wav", "/data/sound/fx_explosion_03.wav", "/data/sound/fx_mining_05.wav", "/data/sound/fx_boost_02.wav", "/data/sound/wpn_rocket_02.wav", "/data/sound/wpn_rocket_03.wav", "/data/sound/wpn_rocket_04.wav", "/data/sound/fx_thunder_01.wav", "/data/sound/wpn_nuke_02.wav", "/data/sound/fx_message_05.wav"};
   private static int currentMusicIdx;
   private static long var_11c;
   private static final long[] SFX_DURATIONS = new long[]{1663L, 1542L, 297L, 551L, 50L, 790L, 2147L, 3087L, 576L, 884L, 892L, 3194L, 1697L, 215L};
   private static short[] SFX_VOLUMES = new short[]{100, 95, 60, 70, 50, 80, 100, 100, 60, 60, 60, 100, 100, 80};
   private static javax.microedition.media.Player var_21c;
   private javax.microedition.media.Player[] var_262;
   private VolumeControl var_2ad;
   private int musicVolume = 100;
   private int sfxVolume = 100;
   private boolean var_46e = false;
   private int var_4a5;

   public SoundManager() {
      this.var_262 = new javax.microedition.media.Player[SFX_FILES_PATHS.length];
   }

   public final void setMusicVolume(int var1) {
      if (var_21c != null && var_21c.getState() != 0) {
         this.var_2ad = (VolumeControl)var_21c.getControl("VolumeControl");
         this.var_2ad.setLevel(var1);
      }

      this.musicVolume = var1;
   }

   public final void setSfxVolume(int var1) {
      if (this.var_262 != null) {
         for(int var2 = 0; var2 < this.var_262.length; ++var2) {
            if (this.var_262[var2] != null && this.var_262[var2].getState() != 0) {
               this.var_2ad = (VolumeControl)this.var_262[var2].getControl("VolumeControl");
               this.var_2ad.setLevel(var1);
            }
         }
      }

      this.sfxVolume = var1;
   }

   public final int getMusicVolume() {
      return this.musicVolume;
   }

   public final void stopMusic() {
      if (var_21c != null) {
         if (var_21c.getState() != 0) {
            var_21c.deallocate();
         }

         var_21c.close();
         var_21c = null;
      }

      this.var_2ad = null;
   }

   public static boolean isMusicPlaying() {
      if (!GameStatus.musicOn) {
         return false;
      } else {
         return var_21c != null && var_21c.getState() == 400;
      }
   }

   public final void playMusic(int var1) {
      if (GameStatus.musicOn && GameStatus.var_9fc != var1) {
         sub_2a5();

         try {
            (var_21c = Manager.createPlayer(this.getClass().getResourceAsStream(MUSIC_FILES_PATHS[var1]), "audio/midi")).addPlayerListener(this);
            var_21c.setLoopCount(-1);
            var_21c.start();
            GameStatus.var_9fc = var1;
            VolumeControl var5;
            (var5 = (VolumeControl)var_21c.getControl("VolumeControl")).setLevel(40);

            try {
               var5.setMute(false);
            } catch (Exception var3) {
            }

            GameStatus.var_9da = false;
         } catch (Exception var4) {
            GameStatus.var_9da = true;
            if (var_21c != null) {
               var_21c.deallocate();
               var_21c.close();
            }

            var_21c = null;
         }
      }
   }

   public final void playSfx(int var1, int var2) {
      if (GameStatus.sfxOn) {
         if (System.currentTimeMillis() > var_11c + SFX_DURATIONS[currentMusicIdx] || SFX_VOLUMES[var1] >= SFX_VOLUMES[currentMusicIdx]) {
            try {
               if (this.var_262[var1] == null) {
                  try {
                     InputStream var3 = this.getClass().getResourceAsStream(SFX_FILES_PATHS[var1]);
                     this.var_262[var1] = Manager.createPlayer(var3, "audio/x-wav");
                     this.var_262[var1].setLoopCount(1);
                     this.var_262[var1].prefetch();
                  } catch (Exception var4) {
                     var4.printStackTrace();
                  }
               }

               ((VolumeControl)this.var_262[var1].getControl("VolumeControl")).setLevel(var2 - var2 / 3);
               this.var_262[var1].stop();
               this.var_262[var1].setMediaTime(0L);
               this.var_262[var1].start();
               currentMusicIdx = var1;
               var_11c = System.currentTimeMillis();
               return;
            } catch (Exception var5) {
               var5.printStackTrace();
            }
         }

      }
   }

   public final void playSfx(int var1) {
      this.playSfx(var1, this.sfxVolume);
   }

   public final void sub_260() {
      try {
         try {
            VolumeControl var1 = (VolumeControl)var_21c.getControl("VolumeControl");
            this.var_4a5 = var1.getLevel();
            var1.setMute(true);
         } catch (Exception var2) {
         }

         var_21c.stop();

         for(int var4 = 0; var4 < this.var_262.length; ++var4) {
            if (this.var_262[var4] != null) {
               this.var_262[var4].stop();
            }
         }

      } catch (Exception var3) {
      }
   }

   public final void sub_287() {
      try {
         if (GameStatus.musicOn) {
            try {
               VolumeControl var1;
               (var1 = (VolumeControl)var_21c.getControl("VolumeControl")).setMute(false);
               AlertType.ERROR.playSound(GameStatus.display);
               var1.setLevel(this.var_4a5);
            } catch (Exception var3) {
            }

            var_21c.start();
         }

         GameStatus.var_9da = false;
      } catch (Exception var4) {
         GameStatus.var_9da = true;
         if (var_21c != null) {
            try {
               var_21c.deallocate();
               var_21c.close();
            } catch (Exception var2) {
            }
         }

         var_21c = null;
      }
   }

   public static void sub_2a5() {
      try {
         if (var_21c.getState() == 400) {
            var_21c.stop();
            var_21c.deallocate();
            var_21c.close();
         }

      } catch (Exception var0) {
      }
   }

   public final void playerUpdate(javax.microedition.media.Player var1, String var2, Object var3) {
      if (var2.compareTo("deviceUnavailable") == 0 && var_21c != null && var_21c.getState() != 0) {
         this.var_46e = true;
      }

      if (var2.compareTo("deviceAvailable") == 0 && this.var_46e) {
         this.var_46e = false;
         GameStatus.var_9da = true;
      }

   }
}
