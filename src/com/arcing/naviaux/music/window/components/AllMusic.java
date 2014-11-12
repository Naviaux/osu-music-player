package com.arcing.naviaux.music.window.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.arcing.naviaux.music.library.Library;
import com.arcing.naviaux.music.player.MiniPlayer;
import com.arcing.naviaux.music.player.PlayingManager;

public class AllMusic extends JPanel {
	private static final long serialVersionUID = 1L;
	static JList<String> mainMusic = new JList<String>(Library.getLibraryNames());
	public AllMusic() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(mainMusic, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(scrollPane);
		
		mainMusic.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Library.selectedSong = mainMusic.getSelectedIndex();
			}
		});
		
		mainMusic.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.isControlDown() == true) {
					if (arg0.getKeyCode() == 80) {
						if (!PlayingManager.isPaused())
							PlayingManager.newSong();
						PlayingManager.playSelected();
					} else if (arg0.getKeyCode() == 77) {
						getParent().getParent().getParent().getParent().getParent().setVisible(false);
						try { new MiniPlayer(getParent().getParent().getParent().getParent().getParent()).setVisible(true);
						} catch (IOException | URISyntaxException e) { e.printStackTrace(); }
					} else {
						//System.out.println("Key Pressed: " + arg0.getKeyCode());
					}
				}
			}
		});
		mainMusic.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					//PlayingManager.newSong();
					PlayingManager.playSelected();
				}
			}

			public void mouseReleased(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
		});
	}
	public static void updateLibrary() {
		mainMusic.setListData(Library.getLibraryNames());
	}
}
