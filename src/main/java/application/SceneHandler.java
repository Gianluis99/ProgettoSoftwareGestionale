package application;

import java.io.IOException;
import java.util.Optional;

import application.model.SoundHandler;
import application.net.stuff.Other;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneHandler {

    private Scene scene;
	private Stage stage;
	private AnchorPane anchorLogin;
	private AnchorPane anchorRegister;
	private AnchorPane anchorHome;
	private AnchorPane fatturePane;
	private AnchorPane calendarPane;
	private AnchorPane statistichePane;


	private static SceneHandler instance = null;
	
	private SceneHandler() {}

	public static SceneHandler getInstance() {
		if(instance == null)
			instance = new SceneHandler();
		
		return instance;
	}
	
	public Stage getStage() {
		return stage;
	}
	public void init(Stage primaryStage) throws Exception {		
		stage = primaryStage;
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/login.fxml"));
		 anchorLogin= (AnchorPane) loader.load();
		 loader = new FXMLLoader(getClass().getResource("/application/fxml/registrazione.fxml"));
		 anchorRegister= (AnchorPane) loader.load();
		 
		 
		scene = new Scene(anchorLogin, 634,487);
		scene.getStylesheets().add(getClass().getResource("/application/css/styleLogin.css").toExternalForm());
	    
		
		
		primaryStage.setTitle("ManageSoft");
		stage.getIcons().add(new Image(getClass().getResource("/application/images/LogoWhite.png").toExternalForm()));

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();


	}
	
	public void setHomeScene() throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/Home.fxml"));
		 anchorHome=(AnchorPane) loader.load();
		
		scene.setRoot(anchorHome);
		stage.hide();
    	stage.setResizable(true);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleHome.css").toExternalForm());
		stage.setMinWidth(1135);
    	stage.setMinHeight(700);
	
    	stage.show();
	}
	
	public void setRegisterScene() {
		stage.hide();
    	stage.setResizable(false);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleLogin.css").toExternalForm());

    	scene.setRoot(anchorRegister);
    
    	stage.setMinWidth(418);
    	stage.setMinHeight(543);
		stage.setWidth(418);
    	stage.setHeight(543);
    	stage.show();

	}
	
	public void setLoginScene() {
    	stage.hide();
    	stage.sizeToScene();

    	stage.setResizable(false);
    	stage.setFullScreen(false);

    	
		scene.setRoot(anchorLogin);
    	stage.setResizable(false);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleLogin.css").toExternalForm());
		stage.setMinWidth(640);
    	stage.setMinHeight(535);
		stage.setWidth(640);
    	stage.setHeight(535);
    	stage.show();
    	
    	
	}
	
	public void refreshStyle(String s) {
		scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource(s).toExternalForm());

	}
	
	public void setFattureScene() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/paneFatture.fxml"));
		fatturePane= (AnchorPane) loader.load();
		
		scene.setRoot(fatturePane);
		stage.hide();
    	stage.setResizable(true);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleHome.css").toExternalForm());
		stage.setMinWidth(1135);
    	stage.setMinHeight(700);
		
    	stage.show();
	}
	
	public void setCalendarScene() throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/paneCalendar.fxml"));
		calendarPane= (AnchorPane) loader.load();
		
		scene.setRoot(calendarPane);
		stage.hide();
    	stage.setResizable(true);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleHome.css").toExternalForm());
		stage.setMinWidth(1135);
    	stage.setMinHeight(700);
		
    	stage.show();
		
	}
	
	

	public void setStatisticheScene() throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/paneStatistiche.fxml"));
		statistichePane= (AnchorPane) loader.load();
		 
		scene.setRoot(statistichePane);
		stage.hide();
    	stage.setResizable(true);
    	scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("/application/css/styleHome.css").toExternalForm());
		stage.setMinWidth(1135);
    	stage.setMinHeight(700);
		
    	stage.show();
		
	}
	
	
	
	public void showHelpChat(double x, double y) {
		Stage stage=new Stage();
		stage.initOwner(SceneHandler.getInstance().getStage());
        stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.requestFocus();
		FXMLLoader fx= new FXMLLoader(getClass().getResource("/application/fxml/chatClientStage.fxml"));
    	AnchorPane root;
		try {
			root = (AnchorPane) fx.load();
	    	Scene scene=new Scene(root,400,500);
	    	scene.getStylesheets().add(getClass().getResource("/application/css/styleUtility.css").toExternalForm());
	    	stage.setTitle("chat");
	    	//per conoscere le coordinate del bottone help
	    	
	    	
	    	//lo stage partirà in prossimita del bottone help
	    	
	          stage.setX(x);
		    	stage.setY(y);
	         stage.setScene(scene);
			stage.show(); 
		} catch (IOException e) {
			SceneHandler.getInstance().showInfo(Other.ERROR_GENERIC);
			e.printStackTrace();
		}
		
		
    }
	
	
    
    
	
	
	 public void showInfo(String mex) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("");
			alert.setContentText(mex);
			alert.show();
	    }
	 
	 public boolean showAlert(String mex) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Attenzione!");
			alert.setHeaderText("");
			alert.setContentText(mex);
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
		
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES)
				return true;
			return false;
				
	    }
	
	 
	 public void errorInfoSound(String mex) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("");
			alert.setContentText(mex);
			alert.show();
			SoundHandler.getInstance().playErrorSound();
	    }
	
	 public void successInfoSound(String mex) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("");
			alert.setContentText(mex);
			alert.show();
			SoundHandler.getInstance().playSuccessSound();
	    }
	 
	  


	
	 
}


