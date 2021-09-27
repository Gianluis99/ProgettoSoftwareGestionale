package application.view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TransitionView {

	public void setOpenMenuTransition(AnchorPane vbar, Button b1,Button b2) {
		TranslateTransition transi=new TranslateTransition();
    	transi.setDuration(Duration.seconds(0.3));
    	transi.setNode(vbar);//aggiungiamo la transizione alla barra
    	transi.setToX(0);
    	transi.play();
    	vbar.setTranslateX(-200); //settiamo posizione
    	
    	transi.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                b1.setVisible(false);
                b2.setVisible(true);
            }
        });
    	
	}
	
	
	public void setCloseMenuTransition(AnchorPane vbar, Button b1,Button b2) {
		TranslateTransition transi=new TranslateTransition();
    	transi.setDuration(Duration.seconds(0.3));
    	transi.setNode(vbar); 
    	transi.setToX(-200);
    	transi.play();
    	
    	
    	vbar.setTranslateX(0);
    
    	transi.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                b1.setVisible(true);
                b2.setVisible(false);
            }
        });
    	
	}
	
	
	
	//cambio Pane
	public void changePaneTransition(Node node) {
	 	
    	FadeTransition t=new FadeTransition(Duration.millis(500));
    	t.setNode(node);
    	//effetto transizione
    	t.setFromValue(0.2);
    	t.setToValue(1);
    	t.setCycleCount(1);
    	t.setAutoReverse(false);
    	
    	t.play();
		
	}
}
