package application.controller;


import application.SceneHandler;
import application.model.SoundHandler;
import application.net.client.Client;
import application.net.stuff.Other;
import application.net.stuff.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileSettingsController {
	
	
	   @FXML
	    private PasswordField oldPaswordText;
	   
	    @FXML
	    private Button buttonImageNotView;

	    @FXML
	    private Button buttonImageView;


	    @FXML
	    private Button settingsProfileButon;

	    @FXML
	    private PasswordField newPasswordText2;

	    @FXML
	    private PasswordField newPasswordText1;

	    @FXML
	    private TextField oldPassVisibletext;

	    @FXML
	    private Label emailText;
	    
	    @FXML
	    void initialize() {
	    	oldPassVisibletext.setVisible(false);
	    	buttonImageNotView.setVisible(true);
	    	buttonImageView.setVisible(false);
	    	
	    	String email=Client.getInstance().getEmail();
	    	if(!email.equals(Protocol.ERROR))
	    		emailText.setText(Client.getInstance().getEmail());
	    }

	    
	    @FXML
	    void saveAll(ActionEvent event) {
	    	if(!oldPaswordText.isVisible())
	    		oldPaswordText.setText(oldPassVisibletext.getText());
	    	
	    	String res=Client.getInstance().setPasswordUser(oldPaswordText.getText(), 
	    			newPasswordText1.getText(), newPasswordText2.getText());
	    	if(res.equals(Other.OK)) {
	    		SceneHandler.getInstance().showInfo("Password modificata con successo!");
	    		SoundHandler.getInstance().playSuccessSound();
	    		oldPaswordText.clear();
	    		newPasswordText1.clear();
	    		newPasswordText2.clear();
	    		oldPassVisibletext.clear();
	    		
	    	
	    	}
	    	else
	    	{
	    		SceneHandler.getInstance().showInfo(res);
	    		SoundHandler.getInstance().playErrorSound();
	    	}
	    		
	    }
	    

	    @FXML
	    void hideOldPassword(ActionEvent event) {
	    	oldPaswordText.setText(oldPassVisibletext.getText());

	    	oldPassVisibletext.setVisible(false);
	    	buttonImageView.setVisible(false);
	    	
	    	oldPaswordText.setVisible(true);
	    	buttonImageNotView.setVisible(true);

	    }

	    @FXML
	    void viewOldPassword(ActionEvent event) {
	    	oldPassVisibletext.setText(oldPaswordText.getText());
	    	oldPassVisibletext.setVisible(true);
	    	buttonImageView.setVisible(true);
	    	
	    	oldPaswordText.setVisible(false);
	    	buttonImageNotView.setVisible(false);
	    }

}
