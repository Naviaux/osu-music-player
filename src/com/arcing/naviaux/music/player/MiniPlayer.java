package com.arcing.naviaux.music.player;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.arcing.naviaux.music.library.Library;
import com.arcing.naviaux.music.reference.ImageReference;

public class MiniPlayer extends JFrame {
	private static final long serialVersionUID = 1L;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Color transparent = new Color(0, 0, 0, 1);
	boolean autoHide = false;
	static boolean alwaysOnTop = true;
	static JLabel playing = new JLabel("Now Playing");
	static int position = 0;
	
	public static final int TOP_RIGHT = 0;
	public static final int BOTTOM_RIGHT = 1;
	public static final int TOP_LEFT = 2;
	public static final int BOTTOM_LEFT = 3;
	
	public MiniPlayer(Container window) throws IOException, URISyntaxException {
		// set window items
		this.setSize(400, 30);
		this.setUndecorated(true);
		this.setAlwaysOnTop(alwaysOnTop);
		this.setLayout(null);
		this.setBackground(transparent);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageReference ir = new ImageReference();
		this.setIconImage(ir.ICON);
		
		// create content items
		JLabel background = new JLabel(new ImageIcon(position < 2 ? ir.BGRIGHT : ir.BGLEFT ));
		JButton display = new JButton(new ImageIcon(ir.DISPLAY));
		JButton next = new JButton(new ImageIcon(ir.NEXT));
		JButton stop = new JButton(new ImageIcon(ir.STOP));
		JButton pause = new JButton(new ImageIcon(ir.PAUSE));
		JButton play = new JButton(new ImageIcon(ir.PLAY));
		JButton previous = new JButton(new ImageIcon(ir.PREVIOUS));
		JButton repeat = new JButton(new ImageIcon(PlayingManager.repeat == 0 ? ir.REPEAT_OFF : PlayingManager.repeat == 1 ? ir.REPEAT_ONE : ir.REPEAT_ALL));
		JButton shuffle = new JButton(new ImageIcon(PlayingManager.shuffle ? ir.SHUFFLE_ON : ir.SHUFFLE_OFF));
		JButton selectionWindow = new JButton(new ImageIcon(ir.SELECTION));
		JLabel trackCurrent = new JLabel();
		JLabel trackBG = new JLabel();
		
		// sizes
		playing.setSize(390, 30);
		background.setSize(400, 30);
		display.setSize(30, 30);
		next.setSize(30, 30);
		stop.setSize(30, 30);
		pause.setSize(30, 30);
		play.setSize(30, 30);
		previous.setSize(30, 30);
		repeat.setSize(30, 30);
		shuffle.setSize(30, 30);
		selectionWindow.setSize(30, 30);
		trackBG.setSize(400, 5);
		trackCurrent.setSize(0, 5);
		
		// fonts
		playing.setFont(new Font(playing.getFont().getFontName(), 0, 22));
		playing.setForeground(Color.WHITE);
		
		display.setSelected(false);
		
		// backgrounds
		display.setBorder(null);
		display.setContentAreaFilled(false);
		display.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		next.setBorder(null);
		next.setContentAreaFilled(false);
		next.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		stop.setBorder(null);
		stop.setContentAreaFilled(false);
		stop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pause.setBorder(null);
		pause.setContentAreaFilled(false);
		pause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		play.setBorder(null);
		play.setContentAreaFilled(false);
		play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previous.setBorder(null);
		previous.setContentAreaFilled(false);
		previous.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		repeat.setBorder(null);
		repeat.setContentAreaFilled(false);
		repeat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		shuffle.setBorder(null);
		shuffle.setContentAreaFilled(false);
		shuffle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		selectionWindow.setBorder(null);
		selectionWindow.setContentAreaFilled(false);
		selectionWindow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		trackCurrent.setBackground(new Color(60, 225, 255));
		trackCurrent.setOpaque(true);
		trackBG.setBackground(Color.DARK_GRAY);
		trackBG.setOpaque(true);
		
		// actionlisteners
		getRootPane().addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				if (arg0.getWheelRotation() < 0)
					PlayingManager.increaseVolume();
				else
					PlayingManager.decreaseVolume();
				Thread displayVol = new Thread(new Runnable() {
					@Override
					public void run() {
						displayVolume();
						try { Thread.sleep(1500);
						} catch (InterruptedException e) {}
						updateSongText();
					}
				});
				displayVol.start();
			}
		});
		
		display.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				autoHide = !autoHide;
			}
		});
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.nextTrack();
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.stopPlaying();
			}
		});
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.pausePlaying();
			}
		});
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!PlayingManager.isPlaying() || PlayingManager.isPaused())
				PlayingManager.playSelected();
			}
		});
		previous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.previousTrack();
			}
		});
		repeat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (PlayingManager.repeat == PlayingManager.REPEAT_NONE) {
					PlayingManager.setRepeat(PlayingManager.REPEAT_ONE);
					repeat.setIcon(new ImageIcon(ir.REPEAT_ONE));
				} else if (PlayingManager.repeat == PlayingManager.REPEAT_ONE) {
					PlayingManager.setRepeat(PlayingManager.REPEAT_ALL);
					repeat.setIcon(new ImageIcon(ir.REPEAT_ALL));
				} else {
					PlayingManager.setRepeat(PlayingManager.REPEAT_NONE);
					repeat.setIcon(new ImageIcon(ir.REPEAT_OFF));
				}
			}
		});
		shuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayingManager.shuffle = !PlayingManager.shuffle;
				if (PlayingManager.shuffle) {
					shuffle.setIcon(new ImageIcon(ir.SHUFFLE_ON));
				} else {
					shuffle.setIcon(new ImageIcon(ir.SHUFFLE_OFF));
				}
			}
		});
		selectionWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.setVisible(true);
				dispose();
			}
		});
		
		// popup menu
		PopupMenu popup = new PopupMenu();
		MenuItem onTop = new MenuItem("Always on Top");
		
		onTop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alwaysOnTop = !alwaysOnTop;
				setAlwaysOnTop(alwaysOnTop);
			}
		});
		
		popup.add(onTop);
		
		// add content
		this.getRootPane().addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					popup.show(getGlassPane(), arg0.getX(), arg0.getY());
				}
			}
			public void mousePressed(MouseEvent arg0) {} public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {} public void mouseClicked(MouseEvent arg0) {}
		});
		Thread timeUpdate = null;
		if (position == TOP_RIGHT || position < 0 || position > 3) {
			// set window position
			this.setLocation(tk.getScreenSize().width - 400, 0);
			// set content position
			playing.setLocation(5, 0);
			playing.setHorizontalAlignment(JLabel.RIGHT);
			background.setLocation(0, 0);
			display.setLocation(400 - 35, 35);
			next.setLocation(400 - 5 - 30 * 2, 35);
			stop.setLocation(400 - 5 - 30 * 3, 35);
			pause.setLocation(400 - 5 - 30 * 4, 35);
			play.setLocation(400 - 5 - 30 * 5, 35);
			previous.setLocation(400 - 5 - 30 * 6, 35);
			repeat.setLocation(400 - 5 - 30 * 7, 35);
			shuffle.setLocation(400 - 5 - 30 * 8, 35);
			selectionWindow.setLocation(400 - 5 - 30 * 9, 35);
			trackBG.setLocation(0, 30);
			trackCurrent.setSize(400, 5);
			trackCurrent.setLocation(0, 30);
			getRootPane().addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {
					if (arg0.getLocationOnScreen().y > 75 || arg0.getLocationOnScreen().x < tk.getScreenSize().width - 400) {
						setSize(400, 30);
						if (autoHide) {
							playing.setVisible(false);
							background.setIcon(new ImageIcon(ir.ACCESSTOP));
							setSize(100,30);
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					setSize(400, 80);
					if (autoHide) {
						playing.setVisible(true);
						background.setIcon(new ImageIcon(ir.BGRIGHT));
					}
				}
				public void mouseClicked(MouseEvent arg0) {}
			});
			timeUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
//						trackCurrent.setSize((int) (PlayingManager.getTime() / PlayingManager.getLength() * 400), 5);
						trackCurrent.setLocation((int) (400 - (PlayingManager.getTime() / PlayingManager.getLength() * 400)), 30);
						try { Thread.sleep(500);
						} catch (InterruptedException e) { }
					}
				}
			});
		} else if (position == TOP_LEFT) {
			// set window position
			this.setLocation(0, 0);
			// set content position
			playing.setLocation(5, 0);
			playing.setHorizontalAlignment(JLabel.LEFT);
			background.setLocation(0, 0);
			display.setLocation(5, 35);
			previous.setLocation(5 + 30, 35);
			play.setLocation(5 + 30 * 2, 35);
			pause.setLocation(5 + 30 * 3, 35);
			stop.setLocation(5 + 30 * 4, 35);
			next.setLocation(5 + 30 * 5, 35);
			repeat.setLocation(5 + 30 * 6, 35);
			shuffle.setLocation(5 + 30 * 7, 35);
			selectionWindow.setLocation(5 + 30 * 8, 35);
			trackBG.setLocation(0, 30);
			trackCurrent.setSize(0, 5);
			trackCurrent.setLocation(0, 30);
			getRootPane().addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {
					if (arg0.getLocationOnScreen().y > 75 || arg0.getLocationOnScreen().x > 400) {
						setSize(400, 30);
						if (autoHide) {
							playing.setVisible(false);
							background.setIcon(new ImageIcon(ir.ACCESSTOP));
							setSize(100,30);
							setLocation(300, 0);
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					setSize(400, 80);
					if (autoHide) {
						playing.setVisible(true);
						background.setIcon(new ImageIcon(ir.BGLEFT));
						setLocation(0, 0);
					}
				}
				public void mouseClicked(MouseEvent arg0) {}
			});
			timeUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						trackCurrent.setSize((int) (PlayingManager.getTime() / PlayingManager.getLength() * 400), 5);
//						trackCurrent.setLocation((int) (400 - (PlayingManager.getTime() / PlayingManager.getLength() * 400)), 30);
						try { Thread.sleep(500);
						} catch (InterruptedException e) { }
					}
				}
			});
		} else if (position == BOTTOM_RIGHT) {
			// set window position
			this.setLocation(tk.getScreenSize().width - 400, tk.getScreenSize().height - 30);
			// set content position
			playing.setLocation(5, 0);
			playing.setHorizontalAlignment(JLabel.RIGHT);
			background.setLocation(0, 0);
			display.setLocation(400 - 35, 20);
			next.setLocation(400 - 5 - 30 * 2, 20);
			stop.setLocation(400 - 5 - 30 * 3, 20);
			pause.setLocation(400 - 5 - 30 * 4, 20);
			play.setLocation(400 - 5 - 30 * 5, 20);
			previous.setLocation(400 - 5 - 30 * 6, 20);
			repeat.setLocation(400 - 5 - 30 * 7, 20);
			shuffle.setLocation(400 - 5 - 30 * 8, 20);
			selectionWindow.setLocation(400 - 5 - 30 * 9, 20);
			display.setVisible(false);
			next.setVisible(false);
			stop.setVisible(false);
			pause.setVisible(false);
			play.setVisible(false);
			previous.setVisible(false);
			repeat.setVisible(false);
			shuffle.setVisible(false);
			selectionWindow.setVisible(false);
			trackBG.setLocation(0, 45);
			trackCurrent.setSize(400, 5);
			trackCurrent.setLocation(0, 45);
			getRootPane().addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {
					if (arg0.getLocationOnScreen().y < tk.getScreenSize().height - 75 || arg0.getLocationOnScreen().x < tk.getScreenSize().width - 400) {
						setSize(400, 30);
						setLocation(tk.getScreenSize().width - 400, tk.getScreenSize().height - 30);
						playing.setLocation(5, 0);
						background.setLocation(0, 0);
						display.setVisible(false);
						next.setVisible(false);
						stop.setVisible(false);
						pause.setVisible(false);
						play.setVisible(false);
						previous.setVisible(false);
						repeat.setVisible(false);
						shuffle.setVisible(false);
						selectionWindow.setVisible(false);
						if (autoHide) {
							playing.setVisible(false);
							background.setIcon(new ImageIcon(ir.ACCESSBOT));
							setSize(100,30);
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					setSize(400, 80);
					setLocation(tk.getScreenSize().width - 400, tk.getScreenSize().height - 80);
					playing.setLocation(5, 50);
					background.setLocation(0, 50);
					display.setVisible(true);
					next.setVisible(true);
					stop.setVisible(true);
					pause.setVisible(true);
					play.setVisible(true);
					previous.setVisible(true);
					repeat.setVisible(true);
					shuffle.setVisible(true);
					selectionWindow.setVisible(true);
					if (autoHide) {
						playing.setVisible(true);
						background.setIcon(new ImageIcon(ir.BGRIGHT));
					}
				}
				public void mouseClicked(MouseEvent arg0) {}
			});
			timeUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
//						trackCurrent.setSize((int) (PlayingManager.getTime() / PlayingManager.getLength() * 400), 5);
						trackCurrent.setLocation((int) (400 - (PlayingManager.getTime() / PlayingManager.getLength() * 400)), 45);
						try { Thread.sleep(500);
						} catch (InterruptedException e) { }
					}
				}
			});
		} else if (position == BOTTOM_LEFT) {
			// set window position
			this.setLocation(0, tk.getScreenSize().height - 30);
			// set content position
			playing.setLocation(5, 0);
			playing.setHorizontalAlignment(JLabel.LEFT);
			background.setLocation(0, 0);
			display.setLocation(5, 20);
			previous.setLocation(5 + 30, 20);
			play.setLocation(5 + 30 * 2, 20);
			pause.setLocation(5 + 30 * 3, 20);
			stop.setLocation(5 + 30 * 4, 20);
			next.setLocation(5 + 30 * 5, 20);
			repeat.setLocation(5 + 30 * 6, 20);
			shuffle.setLocation(5 + 30 * 7, 20);
			selectionWindow.setLocation(5 + 30 * 8, 20);
			display.setVisible(false);
			previous.setVisible(false);
			play.setVisible(false);
			pause.setVisible(false);
			stop.setVisible(false);
			next.setVisible(false);
			repeat.setVisible(false);
			shuffle.setVisible(false);
			selectionWindow.setVisible(false);
			trackBG.setLocation(0, 45);
			trackCurrent.setSize(0, 5);
			trackCurrent.setLocation(0, 45);
			getRootPane().addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {
					if (arg0.getLocationOnScreen().y < tk.getScreenSize().height - 75 || arg0.getLocationOnScreen().x > 400) {
						setSize(400, 30);
						setLocation(0, tk.getScreenSize().height - 30);
						playing.setLocation(5, 0);
						background.setLocation(0, 0);
						display.setVisible(false);
						previous.setVisible(false);
						play.setVisible(false);
						pause.setVisible(false);
						stop.setVisible(false);
						next.setVisible(false);
						repeat.setVisible(false);
						shuffle.setVisible(false);
						selectionWindow.setVisible(false);
						if (autoHide) {
							playing.setVisible(false);
							background.setIcon(new ImageIcon(ir.ACCESSBOT));
							setSize(100,30);
							setLocation(300, tk.getScreenSize().height - 30);
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					setSize(400, 80);
					setLocation(0, tk.getScreenSize().height - 80);
					playing.setLocation(5, 50);
					background.setLocation(0, 50);
					display.setVisible(true);
					previous.setVisible(true);
					play.setVisible(true);
					pause.setVisible(true);
					stop.setVisible(true);
					next.setVisible(true);
					repeat.setVisible(true);
					shuffle.setVisible(true);
					selectionWindow.setVisible(true);
					if (autoHide) {
						playing.setVisible(true);
						background.setIcon(new ImageIcon(ir.BGLEFT));
						setLocation(0, tk.getScreenSize().height - 80);
					}
				}
				public void mouseClicked(MouseEvent arg0) {}
			});
			timeUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						trackCurrent.setSize((int) (PlayingManager.getTime() / PlayingManager.getLength() * 400), 5);
//						trackCurrent.setLocation((int) (400 - (PlayingManager.getTime() / PlayingManager.getLength() * 400)), 30);
						try { Thread.sleep(500);
						} catch (InterruptedException e) { }
					}
				}
			});
		}
		this.add(popup);
		this.add(playing);
		this.add(background);
		this.add(display);
		this.add(next);
		this.add(stop);
		this.add(pause);
		this.add(play);
		this.add(previous);
		this.add(repeat);
		this.add(shuffle);
		this.add(selectionWindow);
		this.add(trackCurrent);
		this.add(trackBG);
		if (timeUpdate != null)
			timeUpdate.start();
	}
	public static void toggleTop() {
		alwaysOnTop = !alwaysOnTop;
	}
	public static void updateSongText() {
		playing.setText(Library.getLibraryNames()[Library.selectedSong]);
	}
	public static void displayVolume() {
		playing.setText(Math.round(PlayingManager.getVolume() * 100) + "%");
	}
	public static void changePosition(int newPosition) {
		position = newPosition;
	}
}
