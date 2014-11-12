package com.arcing.naviaux.music.window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.arcing.naviaux.music.reference.ImageReference;
import com.arcing.naviaux.music.references.Reference;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public AboutDialog(int x, int y) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(275, 150);
		setTitle("About");
		setLayout(null);
		setResizable(false);
		setLocation(x + 50, y + 50);
		
		JLabel icon = new JLabel(new ImageIcon(new ImageReference().ICON));
		JLabel version = new JLabel(Reference.VERSION);
		JTextArea about = new JTextArea(Reference.ABOUT);
		JTextArea copyright = new JTextArea(Reference.COPYRIGHT);
		
		icon.setSize(64, 64);
		icon.setLocation(5, 10);
		version.setSize(200, 15);
		version.setLocation(80, 5);
		about.setSize(175, 60);
		about.setLocation(80, 20);
		about.setEditable(false);
		about.setBackground(getBackground());
		about.setFont(version.getFont());
		about.setLineWrap(true);
		copyright.setSize(260, 30);
		copyright.setLocation(10, 150 - 65);
		copyright.setEditable(false);
		copyright.setBackground(getBackground());
		copyright.setFont(version.getFont());
		copyright.setLineWrap(true);
		
		add(icon);
		add(version);
		add(about);
		add(copyright);
	}
	
}
