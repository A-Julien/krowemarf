package com.prckt.krowemarf.components.Streaming;

import java.awt.event.KeyEvent;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class YoutubePlayer extends MediaFrame {
	private final String youtubeURL = "https://www.youtube.com/watch?v=WuTFI5qftCE";
	
	private EmbeddedMediaPlayer eYoutubePlayer = null;
	private MediaPlayerFactory youtubePlayerFactory = null;
	
	public YoutubePlayer() {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), pathVLC);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		try {
			this.youtubePlayerFactory = new MediaPlayerFactory();
	
			this.eYoutubePlayer = this.youtubePlayerFactory.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(this.mediaFrame));
			this.eYoutubePlayer.setVideoSurface(youtubePlayerFactory.newVideoSurface(this.canvas));
			this.eYoutubePlayer.toggleFullScreen();
			this.eYoutubePlayer.setEnableMouseInputHandling(false);
			this.eYoutubePlayer.setEnableKeyInputHandling(false);
			
			this.eYoutubePlayer.setPlaySubItems(true);
			
			this.eYoutubePlayer.playMedia(youtubeURL);
		} catch(Exception e) { e.printStackTrace(); }
	}

	@Override
	public void setFrameTitle() { this.mediaFrame.setTitle("Krowemarf - Video"); }

	@Override
	public void keyPressedActionPerformed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 17:
				this.eYoutubePlayer.stop();
				System.exit(0);
				break;
			case 27:
				this.mediaFrame.setSize(1500, 800);
				this.mediaFrame.setVisible(true);
				break;
			case 81:
				this.eYoutubePlayer.stop();
				System.exit(0);
				break;
		}
	}
}
