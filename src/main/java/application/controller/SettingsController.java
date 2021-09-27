package application.controller;

import application.SceneHandler;
import application.model.SoundHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class SettingsController {

    @FXML
    private ImageView audioImageMute;

    @FXML
    private Button buttonOn;
    
    @FXML
    private Button buttonOff;

    @FXML
    private ImageView audioImageMeta;

    @FXML
    private ImageView audioImageMax;

    @FXML
    private Button settingsButtonSave;

    @FXML
    private Slider audioSlider;
    
    
    @FXML
    void initialize() {
    	audioSlider.setValue(50);
    	audioImageMute.setVisible(false);
    	if(SoundHandler.getInstance().getSoundOK())
    		buttonOff.setVisible(false);
    	else
    		buttonOn.setVisible(false);

    }
    
    @FXML
    void saveAll(ActionEvent event) {
    		double value=audioSlider.getValue()*100;
    		SoundHandler.getInstance().setLevel(value);
    		
    		if(buttonOn.isVisible())
    			SoundHandler.getInstance().setSoundON(true);
    		else
    			SoundHandler.getInstance().setSoundON(false);
    		
    		if(audioSlider.getValue()==0 && buttonOn.isVisible()) {
    			buttonOff.setVisible(true);
    			buttonOn.setVisible(false);
    			SoundHandler.getInstance().setSoundON(false);
    			audioImageMute.setVisible(true);
    	    	audioImageMax.setVisible(false);

    		}
    		else if(audioSlider.getValue()>0 && !buttonOn.isVisible()) {
    			buttonOff.setVisible(false);
    			buttonOn.setVisible(true);
    			SoundHandler.getInstance().setSoundON(true);
    			audioImageMute.setVisible(false);
    	    	audioImageMax.setVisible(true);
    		}
    		
    		SceneHandler.getInstance().showInfo("Impostazioni correttamente salvate!");
    		SoundHandler.getInstance().playSuccessSound();

    }
    
    
    @FXML
    void onToOff(ActionEvent event) {
    	buttonOn.setVisible(false);
    	buttonOff.setVisible(true);
    	audioSlider.setValue(0);
    	audioImageMute.setVisible(true);
    	audioImageMax.setVisible(false);
    }

    @FXML
    void offToOn(ActionEvent event) {
    	buttonOn.setVisible(true);
    	buttonOff.setVisible(false);
    	audioSlider.setValue(50);
    	audioImageMute.setVisible(false);
    	audioImageMax.setVisible(true);


    }

    
/*
    @FXML
    void mouseClick(ActionEvent event) {
    	if(audioSlider.getValue()==0) {
    		audioImageMute.setVisible(true);
        	audioImageMax.setVisible(false);
    	}
    	else
    	{
    		audioImageMute.setVisible(false);
        	audioImageMax.setVisible(true);
    	}
    	
    }
    */
    
    
    
    
    
    
    
}
