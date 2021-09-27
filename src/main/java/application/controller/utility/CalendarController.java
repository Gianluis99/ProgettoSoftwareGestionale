package application.controller.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.SceneHandler;
import application.model.CalendarHandler;
import application.model.ModelUtility;
import application.modelObject.Evento;
import application.net.client.Client;
import application.net.stuff.Protocol;
import application.view.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CalendarController {

	
	private CalendarHandler ch;
	private CalendarView cv;

    @FXML
    private TextField inserisciTitoloText;

    @FXML
    private DatePicker InserisciDatePicker;

 
    @FXML
    private TextArea inserisciDescrizioneText;

    @FXML
    private Button creaEventButton;

    @FXML
    private Button returnButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField inserisciLuogoText;

   
    @FXML
    void initialize() {
    	ch=new CalendarHandler();
    	cv=new CalendarView();
    	inserisciDescrizioneText.setWrapText(true);
    	gridPane.prefHeightProperty().bind(scrollPane.heightProperty());
		gridPane.prefWidthProperty().bind(scrollPane.widthProperty());
		ch.deleteOldDate();
    	cv.updateEvents(gridPane);
    	
    }
    
    private void clearAll() {
    	inserisciDescrizioneText.clear();
    	inserisciLuogoText.clear();
    	inserisciTitoloText.clear();
    }
    
    @FXML
    void returnToHome(ActionEvent event) {
    	try {
			SceneHandler.getInstance().setHomeScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    

    @FXML
    void addEvent(ActionEvent event) {
    	if(InserisciDatePicker.getValue()==null) {
    		SceneHandler.getInstance().errorInfoSound("Attenzione! Inserisci una data dell'evento!");
    		return;
    	}
    	String data=InserisciDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    	LocalDate d=InserisciDatePicker.getValue();
    	
    	
    	if(d.compareTo(ModelUtility.getInstance().getCurrentDate())<0) {
    		SceneHandler.getInstance().errorInfoSound("Attenzione! Hai inserito una data passata!");
    		return;
    	}
    	
    	if(inserisciTitoloText.getText().equals("")) {
    		SceneHandler.getInstance().errorInfoSound("Attenzione! Inserisci un titolo!");
    		return;
    	}

    	Evento e=new Evento(inserisciTitoloText.getText(),inserisciLuogoText.getText(),
    			inserisciDescrizioneText.getText(),data);
    	String result=Client.getInstance().insEvent(e);
    	
    	if(result.equals(Protocol.SUCCESS)) {
    		SceneHandler.getInstance().successInfoSound("Evento aggiunto con successo!");
        	clearAll();
        	cv.updateEvents(gridPane);

    	}
    	else
    		SceneHandler.getInstance().errorInfoSound(result);

    }

   
    
    
}
