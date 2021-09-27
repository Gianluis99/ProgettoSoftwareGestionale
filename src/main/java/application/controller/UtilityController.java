package application.controller;

import application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class UtilityController {
	
    @FXML
    private Button fattureButton;

    @FXML
    private AnchorPane paneUtility;

    @FXML
    private Button calendarButton;

    

    @FXML
    private Button statisticheButton;


    
    @FXML
    void goToFatture(ActionEvent event) {
    	try {
			SceneHandler.getInstance().setFattureScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void goToStatistiche(ActionEvent event) {
    	try {
			SceneHandler.getInstance().setStatisticheScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void goToCalendar(ActionEvent event) {
    	try {
			SceneHandler.getInstance().setCalendarScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

   
    
}
