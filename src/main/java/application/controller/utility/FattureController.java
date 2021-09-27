package application.controller.utility;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import application.SceneHandler;
import application.model.FattureHandler;
import application.model.GeneratePdf;
import application.modelObject.Cliente;
import application.modelObject.Fattura;
import application.modelObject.Prodotto;
import application.net.client.Client;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FattureController {

	
	
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Cliente> clienti;
	private FattureHandler fh;
	
	
    @FXML
    private TextField inserisciScontoText;

    @FXML
    private TextField ivaProdottoText;

    @FXML
    private TextField inserisciImponibileText;

    @FXML
    private Label prezzoLabelTot;

    @FXML
    private TextField numeroOrdineText;

    @FXML
    private ChoiceBox<Prodotto> inserisciProdottoChoice;
    
    @FXML
    private ChoiceBox<Cliente> ClienteChoiceBox;

    @FXML
    private Label IvaLabelTot;

    @FXML
    private DatePicker scadenzaDate;


    @FXML
    private TextField numeroRicevutaText;

    @FXML
    private Button buttonReturn;

    @FXML
    private TableView<Prodotto> prodottiTable;
    
    @FXML
    private TableColumn<Prodotto, String> columnQuantitaProdotto;

    @FXML
    private TableColumn<Prodotto, String> columnNomeProdotto;

    @FXML
    private TableColumn<Prodotto, String> columnIdProdottoProdotto;
    
    @FXML
    private TableColumn<Prodotto, String> columnIvaProdotto;

    @FXML
    private TableColumn<Prodotto, String> columnPrezzoProdotto;
    
    @FXML
    private TableColumn<Prodotto, String> columnCategoriaProdotto;


    @FXML
    private DatePicker ordineDate;

    @FXML
    private TextField IvaClienteText;

    @FXML
    private TextField quantitaProdottoText;
    
    @FXML
    private Button addProdottoButton;
    
    @FXML
    private Button esportaPdfButton;

    @FXML
    void initialize() {
    	fh=new FattureHandler();
    
    	
    	startProdotti();
    	startClienti();

    	columnCategoriaProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	columnIdProdottoProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	columnIvaProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	columnNomeProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	columnPrezzoProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	columnQuantitaProdotto.prefWidthProperty().bind(prodottiTable.widthProperty().divide(6));
    	
    	columnCategoriaProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("categoria"));
    	columnIdProdottoProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("id"));
    	columnIvaProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("iva"));
    	columnNomeProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("nome"));
    	columnPrezzoProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("prezzo"));
    	columnQuantitaProdotto.setCellValueFactory(new PropertyValueFactory<Prodotto,String>("quantita"));


    }
    
    @FXML
    void ReturnToHome(ActionEvent event) {
    	try {
			SceneHandler.getInstance().setHomeScene();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
    }
    
    @FXML
    void addProdotto(ActionEvent event) {
    	boolean res1=Pattern.matches("[0-9]+", quantitaProdottoText.getText());
    	boolean res2=Pattern.matches("[0-9]+", ivaProdottoText.getText());
    	if(!res1) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci la quantità del prodotto!");
    		return;
    	}
    	if(!res2) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci iva del prodotto!");
    		return;
    	}
    	if(inserisciProdottoChoice.getValue()==null) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci un prodotto esistente oppure inseriscine uno nuovo!");
    		return;
    	}
    	if( Integer.parseInt(quantitaProdottoText.getText())>inserisciProdottoChoice.getValue().getNumDisponibili()) {
    		SceneHandler.getInstance().errorInfoSound("Attenzione quantità non disponibile!");
    		return;
    	}
    	
    	

    	Prodotto p=inserisciProdottoChoice.getValue();
    	int qnt=Integer.parseInt(quantitaProdottoText.getText());
    	int iva=Integer.parseInt( ivaProdottoText.getText());
    	
    	
    	prodottiTable.getItems().add(fh.addProdotto(p, qnt, iva));
    	
 
    
    	
    	prezzoLabelTot.setText(Integer.toString(fh.getTotalePrezzo()));
    	IvaLabelTot.setText(Integer.toString(fh.getTotaleIva()));
    }
    
    @FXML
    void esportaPdf(ActionEvent event) {
    	if(prodottiTable.getItems().isEmpty()) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci almeno un prodotto!");
    		return;
    	}
    		
    	if(ClienteChoiceBox.getValue()==null) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci il Cliente !");
    		return;
    	}
    	if(IvaClienteText.getText().isEmpty()) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci la partita iva del Cliente !");
    		return;
    	}
    	if(ordineDate.getValue()==null) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci la data della fattura!");
    		return;
    	}
    	if(scadenzaDate.getValue()==null) {
    		SceneHandler.getInstance().errorInfoSound("Inserisci la Data di scandenza !");
    		return;
    	}
    	if(!areNumber())
    		return;
    	
    	
    	fh.setTotalePrezzo((fh.getTotaleIva()+fh.getTotalePrezzo()
    	+Integer.parseInt(inserisciImponibileText.getText()))-
    			Integer.parseInt(inserisciScontoText.getText()));
    	
    	if(!fh.updateProdotti())
    			return;
    	//Client.getInstance().updateCont(fh.getTotalePrezzo(),false);
    	
    	String dataFattura=ordineDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    	String scadenzaData=scadenzaDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    	Cliente cliente=ClienteChoiceBox.getValue();
    	cliente.setIva(IvaClienteText.getText());
    	
    	Fattura fattura=new Fattura(numeroRicevutaText.getText(), numeroOrdineText.getText(),
    			dataFattura, cliente, fh.getProdottiEnd(), scadenzaData, inserisciImponibileText.getText()
    			, inserisciScontoText.getText(), fh.getTotaleIva(), fh.getTotaleIva());
    	
    	if(GeneratePdf.getInstance().generatePdfFattura(fattura)==1) {
    		SceneHandler.getInstance().successInfoSound("Pdf fattura scaricata correttamente!");
    		clear();
    		startProdotti();
    	}
    	
    }
    
    
    @FXML
    void clearAll(ActionEvent event) {
    	if(SceneHandler.getInstance().showAlert("Sei sicuro di voler cancellare tutto?")) {
    		clear();
    	}
    	
    	
    }
    
    private void clear() {
    	fh.clearProdottiEnd();
		fh.setTotaleIva(0);
		fh.setTotalePrezzo(0);
		IvaLabelTot.setText("0");
		prezzoLabelTot.setText("0");
	
	
		numeroRicevutaText.clear();
		numeroOrdineText.clear();
		ordineDate.setValue(null);
	
		prodottiTable.getItems().clear();
		ivaProdottoText.clear();
		quantitaProdottoText.clear();
	
		IvaClienteText.clear();
		
		inserisciImponibileText.clear();
		inserisciScontoText.clear();
		scadenzaDate.setValue(null);
    }
    
    private void startProdotti() {
    	 prodotti=Client.getInstance().getProdotti();
    	inserisciProdottoChoice.setItems(FXCollections.observableArrayList(prodotti));
    }
    
    private void startClienti() {
   	 clienti=Client.getInstance().getClienti();
   	ClienteChoiceBox.setItems(FXCollections.observableArrayList(clienti));
   }
   
  private boolean areNumber() {
	  if(!(Pattern.matches("[0-9]+", numeroRicevutaText.getText()))) {
		  SceneHandler.getInstance().errorInfoSound("Inserisci il numero della Ricevuta!");
		  return false;
	  }
	  if(!(Pattern.matches("[0-9]+", numeroOrdineText.getText()))) {
		  SceneHandler.getInstance().errorInfoSound("Inserisci il numero dell'ordine!");
		  return false;
	  }
	  if(!(Pattern.matches("[0-9]+", inserisciImponibileText.getText()))) {
		  SceneHandler.getInstance().errorInfoSound("Inserisci il valore dell'imponibile!");
		  return false;
	  }
	  if(!inserisciScontoText.getText().isEmpty()) {
		  if(!(Pattern.matches("[0-9]+", inserisciScontoText.getText()))) {
			  SceneHandler.getInstance().errorInfoSound("Inserisci correttamente lo sconto!");
			  return false;
		  }
	  }
	  return true;
  }

}
