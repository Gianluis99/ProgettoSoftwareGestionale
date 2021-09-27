package application.controller;
import java.io.IOException;

import application.SceneHandler;
import application.net.client.Client;
import application.net.stuff.Other;
import application.view.TransitionView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
 
public class HomeController {

	
	
    @FXML
    private AnchorPane vbar;

    @FXML
    private Button Menu;
    
    @FXML
    private AnchorPane centralPane;
    
    @FXML
    private Button helpChatButton;

    @FXML
    private MenuItem exit;
    
    @FXML
    private Button MenuReturn;

    @FXML
    private MenuButton menuVertical;
    
    @FXML
    private BorderPane borderp;

    
    @FXML
    private MenuItem LogOut;
    
    @FXML
    private Hyperlink CopyRight;

    @FXML
    private Button returnButtonHome;
    
    @FXML
    private Button profileButton;
    
    
    //button menu
    @FXML
    private Button homeButton;
    
    @FXML
    private Button fornitoriButton;

    @FXML
    private Button magazzinoButton;
    
    @FXML
    private Button prodottiButton;
    
    @FXML
    private Button territoriButton;

    @FXML
    private Button clientiButton;
    
    @FXML
    private Button organizzazioneButton;
    

    
    private  AnchorPane currentScene;
    private String currentPane;
    private TransitionView tv;
   
 
    @FXML
   private  void initialize() {
    	tv=new TransitionView();
    	vbar.setTranslateX(0);
    	if(MenuReturn.isVisible())
    		Menu.setVisible(false);
    	loadPage("/application/fxml/paneHome.fxml");
    	currentPane="paneHome.fxml";
    }
    

    


	@FXML
   private void menuFirst(ActionEvent event) {
    	
    	borderp.setLeft(vbar);//aggiungiamo la bar al borderPane
    	tv.setOpenMenuTransition(vbar, Menu, MenuReturn);
    	
    
    }

    
    @FXML
   private void menuClose(ActionEvent event) {
    	
    	tv.setCloseMenuTransition(vbar, Menu, MenuReturn);
    	borderp.setLeft(null);
        //eliminiamo la bar al borderPane per permettere al centro di ingrandirsi
    }

   
    @FXML
   private void ReturnToLog(ActionEvent event) {
    	if(SceneHandler.getInstance().showAlert("Sei sicuro di voler fare il Log-out?")) {
    	SceneHandler.getInstance().setLoginScene();
    	Client.getInstance().reset();
    	}
    }

    @FXML
   private void exitFromApp(ActionEvent event) {
    	if(SceneHandler.getInstance().showAlert("Sei sicuro di voler uscire?")) {
    		Platform.exit();	
        	Client.getInstance().reset();
    	}
    	
    }
    
    @FXML
    private void goToPrivacy(ActionEvent event) {
    	loadPage("/application/fxml/panePrivacy.fxml");
    	currentPane="panePrivacy.fxml";
    }
    
    @FXML
   private void returnToHome(ActionEvent event) {
    	loadPage("/application/fxml/paneHome.fxml");
    	currentPane="paneHome.fxml";
    }
    
    @FXML
  private  void goToUtility(ActionEvent event) {
    	loadPage("/application/fxml/paneUtility.fxml");
    	currentPane="paneUtility.fxml";
    }
    

    @FXML
    void goToClienti(ActionEvent event) {
    	loadPage("/application/fxml/paneClienti.fxml");
    	currentPane="paneClienti.fxml";

    }
    
    @FXML
    void goToFornitori(ActionEvent event) {
    	loadPage("/application/fxml/paneFornitore.fxml");
    	currentPane="paneFornitore.fxml";
    }
    
    @FXML
    void goToAddProdotti(ActionEvent event) {
    	loadPage("/application/fxml/paneAddProdotti.fxml");
    	currentPane="paneAddProdotti.fxml";

    }

    @FXML
    void goToMagazzino(ActionEvent event) {
    	loadPage("/application/fxml/paneMagazzino.fxml");
    	currentPane="paneMagazzino.fxml";
    }
    
    @FXML
    void goToImpostazioni(ActionEvent event) {
    	loadPage("/application/fxml/paneSettings.fxml");
    	currentPane="paneSettings.fxml";
    }
    
    @FXML
    void goToProfileSettings(ActionEvent event) {
    	loadPage("/application/fxml/paneProfile.fxml");
    	currentPane="paneProfile.fxml";
    }
    
    @FXML
    void goToTerritori(ActionEvent event) {
    	loadPage("/application/fxml/paneTerritori.fxml");
    	currentPane="paneTerritori.fxml";
    }
    	
    @FXML
    void goToHelpChat(ActionEvent event) {
  
	    	//per conoscere le coordinate del bottone help
	    	Bounds p=helpChatButton.localToScreen(helpChatButton.getBoundsInLocal());
		    SceneHandler.getInstance().showHelpChat(p.getMinX()-316, p.getMinY()+46); 
			
	
    }
  
    
    
    //package visibilità
    //cambio del  centralPane
   private  void setNode(Node node) {
    	centralPane.getChildren().clear();
    	centralPane.getChildren().add((Node) node);
    	tv.changePaneTransition(node);
    	
    	
    }
    
    //inser url file fxml
     void loadPage(String s) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(s));

			currentScene= (AnchorPane) loader.load();
			setNode(currentScene);
	    	borderp.setCenter(currentScene);
	    	

			
		} catch (IOException e) {
			e.printStackTrace();
			SceneHandler.getInstance().errorInfoSound(Other.ERROR_GENERIC);

		}
    	
     }
    
     @FXML
     void returnBack(ActionEvent event) {
     	loadPage("/application/fxml/"+currentPane);

     }
    
    
}
