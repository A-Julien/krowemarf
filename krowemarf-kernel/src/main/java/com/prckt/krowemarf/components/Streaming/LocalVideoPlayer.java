package com.prckt.krowemarf.components.Streaming;

import java.awt.event.KeyEvent;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class LocalVideoPlayer extends MediaFrame {
	private final String videoPath = "/src/_MultimediaFiles/MarioCubed.mpg";
	
	private EmbeddedMediaPlayer eVideoPlayer = null;
	private MediaPlayerFactory videoPlayerFactory = null;
	
	public LocalVideoPlayer() {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), pathVLC);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		try {
			this.videoPlayerFactory = new MediaPlayerFactory();
			
			this.eVideoPlayer = this.videoPlayerFactory.newEmbeddedMediaPlayer();
			this.eVideoPlayer.setVideoSurface(videoPlayerFactory.newVideoSurface(this.canvas));
			this.eVideoPlayer.toggleFullScreen();
			this.eVideoPlayer.setEnableMouseInputHandling(false);
			this.eVideoPlayer.setEnableKeyInputHandling(false);
			
			this.eVideoPlayer.prepareMedia(videoPath);
			this.eVideoPlayer.play();
		} catch(Exception e) { e.printStackTrace(); }
	}

	@Override
	public void setFrameTitle() { this.mediaFrame.setTitle("Krowemarf - Video"); }

	@Override
	public void keyPressedActionPerformed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 27:
				this.mediaFrame.setSize(750, 500);
				this.mediaFrame.setVisible(true);
				break;
		}
	}
}
