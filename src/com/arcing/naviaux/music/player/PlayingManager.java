package com.arcing.naviaux.music.player;

import java.io.File;
import java.util.Random;

import javafx.scene.media.*;

import com.arcing.naviaux.music.library.Library;

public abstract class PlayingManager {
	// costs
	public static final int REPEAT_NONE = 0;
	public static final int REPEAT_ONE = 1;
	public static final int REPEAT_ALL = 2;
	

	static Media currentlyPlaying;
	static MediaPlayer player;
	
	static int repeat = 0;
	static boolean shuffle = false;
	static int[] played;
	static int tracksPlayed = 0;
	static double volume = 1.0;
	
	static int trackPlaying;
	
	public static void buildRepeatList() {
		played = new int[Library.getLibraryNames().length];
		tracksPlayed = 0;
	}
	public static boolean wasPlayed (int index) {
		if (tracksPlayed > (Library.getLibraryNames().length - 1)) {
			buildRepeatList();
			return false;
		}
		if (index > -1)
			for (int t = 0; t < tracksPlayed; t++)
				if (played[t] == index)
					return true;
		return false;
	}
	
	public static void playSelected() {
		if (player != null && player.getStatus() == MediaPlayer.Status.PAUSED) {
		} else {
			stopPlaying();
			if (trackPlaying == -1) 
				currentlyPlaying = new Media(new File(Library.getLibraryFiles()[Library.selectedSong]).toURI().toString());
			else
				currentlyPlaying = new Media(new File(Library.getLibraryFiles()[trackPlaying]).toURI().toString());
			player = new MediaPlayer(currentlyPlaying);
			player.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					nextTrack();
				}
			});
		}
		MiniPlayer.updateSongText();
		player.play();
		player.setVolume(volume);
	}
	
	public static void nextTrack() {
		if (repeat != REPEAT_ONE) {
			if (shuffle) {
				if (repeat == REPEAT_NONE) {
					if (tracksPlayed <= (Library.getLibraryNames().length - 1)) {
						Library.selectedSong = new Random().nextInt(Library.getLibraryNames().length - 1);
						while (wasPlayed(Library.selectedSong))
							Library.selectedSong = new Random().nextInt(Library.getLibraryNames().length - 1);
						playSelected();
					}
					wasPlayed(Library.selectedSong);
				} else if (repeat == REPEAT_ALL) {
					Library.selectedSong = new Random().nextInt(Library.getLibraryNames().length - 1);
					while (wasPlayed(Library.selectedSong))
						Library.selectedSong = new Random().nextInt(Library.getLibraryNames().length - 1);
					playSelected();
				}
			} else {
				if (repeat == REPEAT_NONE) {
					if (tracksPlayed < (Library.getLibraryNames().length - 1)) {
						Library.selectedSong++;
						if (Library.selectedSong >= Library.getLibraryNames().length)
							Library.selectedSong = 0;
						playSelected();
					}
				} else if (repeat == REPEAT_ALL) {
					if (tracksPlayed < (Library.getLibraryNames().length - 1)) {
						Library.selectedSong++;
						if (Library.selectedSong >= Library.getLibraryNames().length)
							Library.selectedSong = 0;
						playSelected();
					}
				}
			}
		} else {
			Library.selectedSong = trackPlaying;
			playSelected();
		}
	}
	public static void previousTrack() {
		if (repeat != REPEAT_ONE) {
			if (shuffle) {
				if ((tracksPlayed - 1) > 0) {
					tracksPlayed--;
					Library.selectedSong = played[tracksPlayed];
					playSelected();
				} else {
					newSong();
					nextTrack();
				}
			} else {
				Library.selectedSong--;
				if (Library.selectedSong > -1)
					playSelected();
				else {
					Library.selectedSong = Library.getLibraryNames().length - 1;
					playSelected();
				}
			}
		} else {
			nextTrack();
		}
	}
	public static void newSong() {
		player.stop();
		player.dispose();
		buildRepeatList();
	}
	public static void stopPlaying() {
		if (player != null)
			player.stop();
	}
	public static void pausePlaying() {
		if (player != null)
			player.pause();
	}
	public static boolean isPaused() {
		if (player != null)
			if (player.getStatus() == MediaPlayer.Status.PAUSED)
				return true;
		return false;
	}
	public static boolean isPlaying() {
		if (player != null)
			if (player.getStatus() == MediaPlayer.Status.PLAYING)
				return true;
		return false;
	}
	public static double getVolume() {
		if (player != null)
			return volume;
		return 0.0;
	}
	public static double getLength() {
		if (player != null)
			return player.getTotalDuration().toSeconds();
		return 0.0;
	}
	public static double getTime() {
		if (player != null)
			return player.getCurrentTime().toSeconds();
		return 0.0;
	}
	public static void setRepeat(int repeatMode) {
		repeat = repeatMode;
		if (repeatMode == REPEAT_ONE)
			trackPlaying = Library.selectedSong;
		else
			trackPlaying = -1;
	}
	public static void toggleShuffle() {
		shuffle = !shuffle;
	}
	public static void increaseVolume () {
		if (player != null)
			if (volume < 1) {
				volume += 0.02;
				player.setVolume(volume);
			}
	}
	public static void decreaseVolume () {
		if (player != null)
			if (volume > 0)
				volume -= 0.02;
				player.setVolume(volume);
	}
}
