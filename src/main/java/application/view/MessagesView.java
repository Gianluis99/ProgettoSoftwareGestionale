package application.view;

import java.util.ArrayList;

import application.modelObject.HelpMessage;
import application.net.client.Client;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MessagesView {

	public MessagesView() {}
	
	
	//mostra messaggi nella chat
	public void showMessages( VBox vbox) {
    	ArrayList<HelpMessage> messages=Client.getInstance().getMessages();
    	for(var e: messages) {
    		Text t=new Text(e.getText());
	    	t.setTextAlignment(TextAlignment.CENTER);
    		t.setWrappingWidth(190);
    		Font f=Font.font(null, FontWeight.BOLD, 15);
    		t.setFont(f);
    		
    		HBox h=new HBox();
    		h.getChildren().add(t);
    		
    		if(e.isSender()) {//==1
    			h.setAlignment(Pos.CENTER_RIGHT);
    			
    		}
    		else {//==0
    			
    	    	h.setAlignment(Pos.CENTER_LEFT);
    			t.setFill(Color.GREEN);
    			

    		}
    		vbox.getChildren().add(h);
    	}
	}
	
	 public void addMessage(String mes,VBox v) {
	    	Text t=new Text(mes);
	    	t.setTextAlignment(TextAlignment.CENTER);
	    	t.setWrappingWidth(190);
			Font f=Font.font(null, FontWeight.BOLD, 15);
			t.setFont(f);

			HBox h=new HBox();
			h.getChildren().add(t);
			
			h.setAlignment(Pos.CENTER_RIGHT);
			v.getChildren().add(h);
	    }
}
