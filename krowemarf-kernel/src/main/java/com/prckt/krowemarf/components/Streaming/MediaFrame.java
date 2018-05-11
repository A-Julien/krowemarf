package com.prckt.krowemarf.components.Streaming;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class MediaFrame {
	final String pathVLC = "C:/Program Files/VideoLAN/VLC";
	
	private KeyBoardListener keyBoardListener = null;
	
	JFrame mediaFrame = null;
	JPanel jPanelMedia = null;
	Canvas canvas = null;
	
	
	public MediaFrame() {
		initComponents();
	}
	
	private void initComponents() {
		this.mediaFrame = new JFrame();
		
		this.mediaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mediaFrame.setSize(1500, 800);
		this.mediaFrame.setLocationRelativeTo(null);
		this.mediaFrame.setVisible(true);
		
		this.jPanelMedia = new JPanel();
		this.jPanelMedia.setLayout(new BorderLayout());
		
		this.canvas = new Canvas();
		this.canvas.setBackground(Color.BLACK);
		
		this.jPanelMedia.add(canvas);
		this.mediaFrame.add(jPanelMedia);
		
		this.keyBoardListener = new KeyBoardListener();
		
		this.mediaFrame.addKeyListener(keyBoardListener);
	}
	
	public abstract void setFrameTitle();
	
	class KeyBoardListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			keyPressedActionPerformed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public abstract void keyPressedActionPerformed(KeyEvent e);
}
