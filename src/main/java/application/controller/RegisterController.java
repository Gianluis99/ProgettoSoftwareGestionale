package application.controller;

import application.SceneHandler;
import application.net.client.Client;
import application.net.stuff.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class RegisterController {

	
	
    @FXML
    private Button SignInButton;
    

    @FXML
    private Button buttonRegisterTop;
    

    @FXML
    private TextField textSignEmail;

    @FXML
    private PasswordField textSignPassword;

    @FXML
    private TextField textRegistration;
    
    @FXML
    private Text info;

    
    @FXML
    void initialize() {
    	 buttonRegisterTop.setTooltip(new Tooltip("Return to login"));
    }

    @FXML
    void registration(ActionEvent event) {
    	String res=Client.getInstance().checkRegistration(textSignEmail.getText(), textSignPassword.getText());
    	if(res.equals(Protocol.SUCCESS)) {
    		try {
        		textRegistration.clear();
				SceneHandler.getInstance().setHomeScene();
			} 
    		catch (Exception e) {
				textRegistration.setText(Protocol.ERROR);
				e.printStackTrace();
			}
    	}
    	else {
    		textRegistration.clear();
    		textRegistration.setText(res);
    		Client.getInstance().reset();
    	}
    	
    }
    
    @FXML
    void returnToLogin(ActionEvent event) {
		SceneHandler.getInstance().setLoginScene();
		Client.getInstance().reset();


    }
    
   
}
