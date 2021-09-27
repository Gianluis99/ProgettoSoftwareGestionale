package application.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHandler {

	private static SoundHandler instance=null;
	private Media errorSound;
	private Media successSound;
	private boolean soundON;
	private double level;
	
	
	private SoundHandler() {
		soundON=true;
		level=50;
		errorSound = new Media(getClass().getResource("/application/sound/Error-sound-effect.mp3").toExternalForm());
		successSound = new Media(getClass().getResource("/application/sound/success.mp3").toExternalForm());
		
	}
	
	public void setSoundON(boolean value) {
		soundON=value;
	}
	
	public boolean getSoundOK() {
		return soundON;
	}
	
	public static SoundHandler getInstance() {
		if(instance==null)
			instance=new SoundHandler();
		
		return instance;
	}
	
	
	public void playErrorSound() {
		if(!soundON) 
			return;
		MediaPlayer mediaPlayer = new MediaPlayer(errorSound);
		mediaPlayer.setVolume(level);
		mediaPlayer.play();
		
		
	}
	
	
	public void playSuccessSound() {
		if(!soundON)
			return ;
		MediaPlayer mediaPlayer = new MediaPlayer(successSound);
		mediaPlayer.setVolume(level);
		mediaPlayer.play();
		

	}

	
	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}
}
