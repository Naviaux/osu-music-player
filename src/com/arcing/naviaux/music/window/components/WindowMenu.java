package com.arcing.naviaux.music.window.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import com.arcing.naviaux.music.library.Library;
import com.arcing.naviaux.music.player.MiniPlayer;
import com.arcing.naviaux.music.player.PlayingManager;
import com.arcing.naviaux.music.window.AboutDialog;

public class WindowMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	public WindowMenu() {
		setSize(500, 20);
		// Main Menus
		JMenu fileMenu = new JMenu("File");
		JMenu controlMenu = new JMenu("Controls");
		JMenu playerMenu =  new JMenu("Player");
		JMenu help = new JMenu("Help");
		
		// File Items
		JMenuItem addSong = new JMenuItem("Add Song");
		JMenuItem addFolder = new JMenuItem("Add Folder");
		JMenuItem miniSwitchF = new JMenuItem("Mini Player");
		
		addSong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Library.addSongToLibrary();
			}
		});
		addFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Library.addFolderToLibrary();
			}
		});
		
		miniSwitchF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getParent().getParent().getParent().getParent().setVisible(false);
				try { new MiniPlayer(getParent().getParent().getParent().getParent()).setVisible(true);
				} catch (IOException | URISyntaxException e) { e.printStackTrace(); }
			}
		});
		
		fileMenu.add(addSong);
		fileMenu.add(addFolder);
		fileMenu.add(miniSwitchF);
		
		// Control Items
		JMenuItem play = new JMenuItem("Play");
		JMenuItem pause = new JMenuItem("Pause/Resume");
		JMenuItem stop = new JMenuItem("Stop");
		JMenu repeat = new JMenu("Repeat");
		JMenuItem r_none = new JMenuItem("None");
		JMenuItem r_one = new JMenuItem("One");
		JMenuItem r_all = new JMenuItem("All");
		JMenuItem shuffle = new JMenuItem("Shuffle");
		
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.newSong();
				PlayingManager.playSelected();
			}
		});
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (PlayingManager.isPaused() == false)
					PlayingManager.pausePlaying();
				else {
					PlayingManager.playSelected();
				}
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.stopPlaying();
			}
		});
		r_none.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.setRepeat(PlayingManager.REPEAT_NONE);
			}
		});
		r_one.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.setRepeat(PlayingManager.REPEAT_ONE);
			}
		});
		r_all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.setRepeat(PlayingManager.REPEAT_ALL);
			}
		});
		shuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.toggleShuffle();
			}
		});
		
		repeat.add(r_none);
		repeat.add(r_one);
		repeat.add(r_all);
		controlMenu.add(play);
		controlMenu.add(pause);
		controlMenu.add(stop);
		controlMenu.add(repeat);
		
		// player items
		JMenuItem onTop = new JMenuItem("Always on Top");
		JMenu position = new JMenu("Position");
		JRadioButtonMenuItem topLeft = new JRadioButtonMenuItem("Top Left", false);
		JRadioButtonMenuItem topRight = new JRadioButtonMenuItem("Top Right", true);
		JRadioButtonMenuItem botLeft = new JRadioButtonMenuItem("Bottom Left", false);
		JRadioButtonMenuItem botRight = new JRadioButtonMenuItem("Bottom Right", false);
		JMenuItem miniSwitchP = new JMenuItem("Mini Player");
		
		position.add(topLeft);
		position.add(topRight);
		position.add(botLeft);
		position.add(botRight);
		
		onTop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiniPlayer.toggleTop();
			}
		});
		miniSwitchP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getParent().getParent().getParent().getParent().setVisible(false);
				try { new MiniPlayer(getParent().getParent().getParent().getParent()).setVisible(true);
				} catch (IOException | URISyntaxException e) { e.printStackTrace(); }
			}
		});
		topLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiniPlayer.changePosition(MiniPlayer.TOP_LEFT);
				topRight.setSelected(false);
				botLeft.setSelected(false);
				botRight.setSelected(false);
			}
		});
		topRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiniPlayer.changePosition(MiniPlayer.TOP_RIGHT);
				topLeft.setSelected(false);
				botLeft.setSelected(false);
				botRight.setSelected(false);
			}
		});
		botLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiniPlayer.changePosition(MiniPlayer.BOTTOM_LEFT);
				topLeft.setSelected(false);
				topRight.setSelected(false);
				botRight.setSelected(false);
			}
		});
		botRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiniPlayer.changePosition(MiniPlayer.BOTTOM_RIGHT);
				topLeft.setSelected(false);
				topRight.setSelected(false);
				botLeft.setSelected(false);
			}
		});
		
		playerMenu.add(miniSwitchP);
		playerMenu.add(onTop);
		playerMenu.add(position);
		
		// help items
		JMenuItem about = new JMenuItem("About");
		
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AboutDialog(getParent().getParent().getParent().getParent().getLocation().x, getParent().getParent().getParent().getParent().getLocation().y);
			}
		});
		
		help.add(about);
		
		add(fileMenu);
		add(controlMenu);
		add(playerMenu);
		add(help);
	}
}
