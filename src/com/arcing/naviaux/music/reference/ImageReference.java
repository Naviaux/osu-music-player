package com.arcing.naviaux.music.reference;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReference {
	
	private Class<? extends ImageReference> c;
	public BufferedImage ACCESSTOP;
	public BufferedImage ACCESSBOT;
	public BufferedImage BGLEFT;
	public BufferedImage BGRIGHT;
	public BufferedImage DISPLAY;
	public BufferedImage ICON;
	public BufferedImage NEXT;
	public BufferedImage PAUSE;
	public BufferedImage PLAY;
	public BufferedImage PREVIOUS;
	public BufferedImage REPEAT_ALL;
	public BufferedImage REPEAT_OFF;
	public BufferedImage REPEAT_ONE;
	public BufferedImage SELECTION;
	public BufferedImage SHUFFLE_OFF;
	public BufferedImage SHUFFLE_ON;
	public BufferedImage STOP;
	
	public ImageReference() {
		try { 
			c = this.getClass();
			ACCESSTOP = ImageIO.read(c.getResource("/accesstop.png"));
			ACCESSBOT = ImageIO.read(c.getResource("/accessbot.png"));
			BGLEFT = ImageIO.read(c.getResource("/backgroundleft.png"));
			BGRIGHT = ImageIO.read(c.getResource("/backgroundright.png"));
			DISPLAY = ImageIO.read(c.getResource("/display.png"));
			ICON = ImageIO.read(c.getResource("/icon.png"));
			NEXT = ImageIO.read(c.getResource("/next.png"));
			PAUSE = ImageIO.read(c.getResource("/pause.png"));
			PLAY = ImageIO.read(c.getResource("/play.png"));
			PREVIOUS = ImageIO.read(c.getResource("/previous.png"));
			REPEAT_ALL = ImageIO.read(c.getResource("/repeatall.png"));
			REPEAT_OFF = ImageIO.read(c.getResource("/repeatoff.png"));
			REPEAT_ONE = ImageIO.read(c.getResource("/repeatone.png"));
			SELECTION = ImageIO.read(c.getResource("/selectionwindow.png"));
			SHUFFLE_OFF = ImageIO.read(c.getResource("/shuffleoff.png"));
			SHUFFLE_ON = ImageIO.read(c.getResource("/shuffleon.png"));
			STOP = ImageIO.read(c.getResource("/stop.png"));
		} catch (IOException e) { }
	}
}
