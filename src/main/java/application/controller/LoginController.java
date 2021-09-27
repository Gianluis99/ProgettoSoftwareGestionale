package application.controller;
import application.SceneHandler;
import application.net.client.Client;
import application.net.stuff.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController  {
	
    @FXML
    private Button SignInButton;

    @FXML
    private TextField textSignEmail;

    @FXML
    private TextField textWarnings;

    @FXML
    private PasswordField textSignPassword;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

   
    
    
    @FXML
    void initialize() {
    	emailLabel.setVisible(false);
    	passwordLabel.setVisible(false);
    	
    }
    
    @FXML
    void login(ActionEvent event) {
    	String res=Client.getInstance().checkLog(textSignEmail.getText(), textSignPassword.getText());
    	if(res.equals(Protocol.SUCCESS)) {
    		try {
    			
        		textWarnings.clear();
				SceneHandler.getInstance().setHomeScene();
			} catch (Exception e) {
	    		textWarnings.setText("Error during connection");
				e.printStackTrace();
			}
    	}
    	else {
    		textWarnings.clear();
    		textWarnings.setText(res);
    	
    		Client.getInstance().reset();
    	}
    }

    @FXML
    void register(ActionEvent event) {
		/*textWarnings.clear();
		SceneHandler.getInstance().setRegisterScene();*/

    }
    
    
    @FXML
    void emailTextAction(MouseEvent event) {
    	emailLabel.setVisible(true);
    	textSignEmail.setPromptText(null);;
    }
    
    
    @FXML
    void passwordTextShow(MouseEvent event) {
    	passwordLabel.setVisible(true);
    	textSignPassword.setPromptText(null);
    }
    
    @FXML
    void hidePasswordText(MouseEvent event) {
    	passwordLabel.setVisible(false);
    	textSignEmail.setPromptText("Inserisci la tua email");;


    }
    
    @FXML
    void hideEmailText(MouseEvent event) {
    	emailLabel.setVisible(false);
    	textSignPassword.setPromptText("Inserisci la tua password");


    }
   
}