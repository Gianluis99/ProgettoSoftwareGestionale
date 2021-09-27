package application.controller;


import application.net.client.Client;
import application.view.MessagesView;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class chatClientController extends AnimationTimer {

    @FXML
    private VBox vboxMessages;
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea chatClientEditableText;
	
    @FXML
    private ScrollPane scrollPane;

    private long pTime=0;
    private long frequency=500 *1000000;
    private MessagesView m=new MessagesView();
    	
    @FXML
    void initialize() {
    	chatClientEditableText.setWrapText(true);
    	
    	m.showMessages(vboxMessages);
    	/*Thread t = new Thread();
    	t.setDaemon(true);
    	t.start();
    	this.start();*/
    }
    
    @FXML
    void sendMessage(ActionEvent event) {
    	String message=chatClientEditableText.getText();
    	if(!message.isEmpty() ) {
    		Client.getInstance().sendHelpMessage(message);
    		m.addMessage(message, vboxMessages);
         		
    	}
    	chatClientEditableText.clear();
    		
    }

	@Override
	public void handle(long now) {
		if(now-pTime >= frequency)	{
			m.showMessages(vboxMessages);
		}
		pTime=now;	
	}

   


}
