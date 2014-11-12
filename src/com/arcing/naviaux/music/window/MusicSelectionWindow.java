package com.arcing.naviaux.music.window;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.arcing.naviaux.music.window.components.*;

public class MusicSelectionWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	public MusicSelectionWindow() {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		setTitle("Selection Window");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResource("/icon.png")));
		} catch (IOException e) {}
		
		JMenuBar menuBar = new WindowMenu();
		
		JTabbedPane displayAllMusic = new JTabbedPane();
		JPanel mainMusic = new AllMusic();
		displayAllMusic.addTab("All", mainMusic);
		//displayAllMusic.addTab("Playlists", null);
		displayAllMusic.setTabPlacement(JTabbedPane.LEFT);
		displayAllMusic.setSize(500, 480);
		displayAllMusic.setLocation(0, 18);
		
		displayAllMusic.addHierarchyBoundsListener(new HierarchyBoundsListener() {
			
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
				menuBar.setSize(getWidth(), 20);
				displayAllMusic.setSize(getWidth() - 12, getHeight() - 53);
				mainMusic.setSize(getWidth()-75, getHeight()-62);
			}
			
			@Override
			public void ancestorMoved(HierarchyEvent arg0) {}
		});
		
		add(menuBar);
		add(displayAllMusic);
	}
}
