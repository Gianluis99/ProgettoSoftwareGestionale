package application.controller.utility;


import application.model.CalendarHandler;
import application.modelObject.Evento;
import application.view.CalendarView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CalendarEventController {
	
	private Evento evento;
	private CalendarView cv;
	private CalendarHandler ch;
	@FXML
    private Label dataText;

    @FXML
    private Text luogoText;

    @FXML
    private Label titoloLabel;

    @FXML
    private Text descrizioneText;
  
    @FXML
    private AnchorPane anchorPane;
    
   
    public void setEvent(Evento even) {
    	this.evento=even;
    	this.cv=new CalendarView();
    	this.ch=new CalendarHandler();
    	dataText.setText(evento.getData());
    	luogoText.setText(evento.getLuogo());
    	titoloLabel.setText(evento.getTitolo());
    	descrizioneText.setText(evento.getDescrizione());
    	if(ch.differenceOfDay(evento.getData())==0)
    		cv.highlightsPane(anchorPane);
    }
    
 
}
