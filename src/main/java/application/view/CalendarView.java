package application.view;

import java.io.IOException;
import java.util.ArrayList;

import application.controller.utility.CalendarEventController;
import application.model.CalendarHandler;
import application.modelObject.Evento;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CalendarView {

	private  CalendarEventController calendarEvController;
	private CalendarHandler ch;
	private ArrayList<Evento>eventi;
	
	public CalendarView() {
		ch=new CalendarHandler();
		eventi=ch.getEvents();

	}
	
	public void updateEvents(GridPane gridPane) {
		eventi=ch.getEvents();
    	int row=1;
 		
    	//view
 		for(var e:eventi) {
 			try {
 				
 	    	FXMLLoader  loader=new FXMLLoader(getClass().getResource("/application/fxml/eventoItem.fxml"));
 	    	AnchorPane anchorPane=loader.load();
 	    	
 	    	calendarEvController=loader.getController();
 	    	calendarEvController.setEvent(e);
 	    	
 	    	
 	    	gridPane.add(anchorPane,0,row++);
 	    	
 			} catch (IOException e1) {
 				e1.printStackTrace();
 			}
 	    	}
    	
	}
	
	// se evento odierno si evidenzia in rosso
	 public void highlightsPane(AnchorPane pane) {
	    	pane.setStyle("-fx-border-color:red;-fx-border-width:3");
	    	
	    }
}
