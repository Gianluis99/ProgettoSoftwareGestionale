 package application.controller.prodotti;

import java.io.File;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.SceneHandler;
import application.model.ModelUtility;
import application.modelObject.Fornitore;
import application.net.client.Client;
import application.net.stuff.Protocol;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ProdottoController {

	
	ArrayList<Fornitore> fornitori;
	File selectedFile;
	
	
    @FXML
    private TextField prezzoProdottoText;

    @FXML
    private ChoiceBox<Fornitore> fornitoriProdottoBox;

    @FXML
    private TextField nomeProdottoText;

    @FXML
    private Button caricaUrlProdottoButton;
    
    @FXML
    private TextField caricaImmagineProdottotext;

    @FXML
    private TextField categoriaProdottoText;

    @FXML
    private Button caricaProdottoButton;

    @FXML
    private DatePicker dataCaricoProdotti;

    @FXML
    private TextField codiceProdottoText;

    @FXML
    private TextField quantitaProdottoText;
    
    @FXML
    private ImageView imageview;

    @FXML
    void initialize() {
    	fornitori=Client.getInstance().getFornitori();
    	fornitoriProdottoBox.setItems(FXCollections.observableArrayList(fornitori));
    	caricaImmagineProdottotext.setEditable(false);
    
    }
    
    @FXML
    void caricaUrlProdotto(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File png", "*.png"));
    	
    	 selectedFile = fileChooser.showOpenDialog(null);
    	
		 if(selectedFile==null)
			 return;
		 
		 String s=selectedFile.getAbsolutePath();
		 caricaImmagineProdottotext.setText(s);
	
	

		 
    }

    @FXML
    void caricaProdottoButton(ActionEvent event) {
    	if(fornitoriProdottoBox.getValue()==null) {
    		SceneHandler.getInstance().showInfo("Attenzione! Inserisci un fornitore! se non ci sono fornitori inseriscine uno.");
    		return;
    	}
    	if(caricaImmagineProdottotext.getText().equals("")) {
    		SceneHandler.getInstance().showInfo("Attenzione! Inserisci un immagine del prodotto!");
    		return;
    	}
    	if(dataCaricoProdotti.getValue()==null) {
    		SceneHandler.getInstance().showInfo("Attenzione! Inserisci una data di carico!");
    		return;
    	}
    	
    	Fornitore f=fornitoriProdottoBox.getValue();
    	String nomeF=f.getNome();
    	String cognomeF=f.getCognome();
    	
    	//trasformo la data in una stringa per questo ho dovuto fare il controllo precedentemente
    	String data = dataCaricoProdotti.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	
    	byte [] b=ModelUtility.fileToByte(selectedFile);
    	
    	
    	String res=Client.getInstance().addProdotto(codiceProdottoText.getText(),nomeProdottoText.getText(),categoriaProdottoText.getText(),
    			prezzoProdottoText.getText(),data,quantitaProdottoText.getText(),nomeF,cognomeF,b);
    	
    	if(res.equals(Protocol.PRODOTTO_SUCCESS)) {
    		try {
        		clearAll();
        		SceneHandler.getInstance().successInfoSound(res);
			} catch (Exception e) {
				SceneHandler.getInstance().errorInfoSound("Errore del Server");
				e.printStackTrace();
			}
    	}
    	else {
			SceneHandler.getInstance().errorInfoSound(res);
    	}
    	
    }
    
    private void clearAll() {
    	caricaImmagineProdottotext.clear();
    	categoriaProdottoText.clear();
    	codiceProdottoText.clear();
    	quantitaProdottoText.clear();
    	prezzoProdottoText.clear();
    	nomeProdottoText.clear();
    	dataCaricoProdotti.setValue(null);
    
    }
}
