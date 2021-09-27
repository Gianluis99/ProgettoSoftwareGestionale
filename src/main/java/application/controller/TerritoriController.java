package application.controller;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.SceneHandler;
import application.model.SoundHandler;
import application.modelObject.Territorio;
import application.net.client.Client;
import application.net.stuff.Protocol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class TerritoriController {

	private boolean modifica=false;
	private boolean assegna=true;

	ObservableList<Territorio> territoriOb;
	Territorio tMod;
	
    @FXML
    private HBox boxAggiungiTerritiorio;

    @FXML
    private Button modificaTerritorioButton;
    
    @FXML
    private Button aggiungiEndButton;
    
    @FXML
    private Button annullaEndButton;
    
    @FXML
    private TextArea inserisciNotaText;


    @FXML
    private TextField inserisciNomeText;
    

    @FXML
    private TextField inserisciNumeroText;



    @FXML
    private Button aggiungiTerritorioButton;

    @FXML
    private TableView<Territorio> tableTerritori;

    @FXML
    private TableColumn<Territorio, String> nomeTerritorioCol;
    

    @FXML
    private TableColumn<Territorio, String> notaTerritorioCol;

    @FXML
    private TableColumn<Territorio, String> numTerritorioCol;
    

    @FXML
    private TableColumn<Territorio, String> assegnatoTerritorioCol;


    @FXML
    private Label labelTerritorio;
    
    
    /* assegna consegna*/
    @FXML
    private Button assegnaTerritorioButton;
    
    @FXML
    private Button consegnaTerritorioButton;
    
    
    @FXML
    private Button salvaConsegnaButtonEnd;
    
    @FXML
    private Button annullaConsegnaButtonEnd;

    @FXML
    private DatePicker dateAssegna;


    @FXML
    private Label assegnaNumeroLabel;

    @FXML
    private TextField cognomeAssegnaText;
    

    @FXML
    private TextField nomeAssegnaText;	


    @FXML
    private HBox assegnaBox;


    @FXML
    private Label labelDateAssegna;
    
    @FXML
    void initialize() {
    	boxAggiungiTerritiorio.setVisible(false);
    	//bind dimensione
    	nomeTerritorioCol.prefWidthProperty().bind(tableTerritori.widthProperty().divide(4) );
    	numTerritorioCol.prefWidthProperty().bind(tableTerritori.widthProperty().divide(4) );
    	notaTerritorioCol.prefWidthProperty().bind(tableTerritori.widthProperty().divide(4));
    	assegnatoTerritorioCol.prefWidthProperty().bind(tableTerritori.widthProperty().divide(4));

    	//set colonne
    	nomeTerritorioCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	numTerritorioCol.setCellValueFactory(new PropertyValueFactory<>("numeroTerritorio"));
    	notaTerritorioCol.setCellValueFactory(new PropertyValueFactory<>("nota"));
    	assegnatoTerritorioCol.setCellValueFactory(new PropertyValueFactory<>("assegnato"));

    	update();		
    	
    }
    
    
    @FXML
    void aggiungiTerritorio(ActionEvent event) {
		clearAdd();
    	modifica=false;
    	labelTerritorio.setText("Aggiungi un Territorio:");
    	inserisciNumeroText.setEditable(true);
    	
    	assegnaBox.setVisible(false);
    	boxAggiungiTerritiorio.setVisible(true);
    	tableTerritori.setVisible(false);
    }
    
    @FXML
    void aggiungiEnd(ActionEvent event) {
    	if(!modifica) {
    	String res=Client.getInstance().addTerritorio(inserisciNomeText.getText(),inserisciNumeroText.getText(),inserisciNotaText.getText());
    	
    	if(res.equals(Protocol.SUCCESS_TERRITORIO)) {
    		boxAggiungiTerritiorio.setVisible(false);
    		tableTerritori.setVisible(true);
    		SceneHandler.getInstance().showInfo(res);
    		SoundHandler.getInstance().playSuccessSound();
    		clearAdd();
    		update();
    	}
    	else {
		error(res);

    	}
    	}
    	else {
    		tMod.setNome(inserisciNomeText.getText());
    		tMod.setNota(inserisciNotaText.getText());
        	String res=Client.getInstance().editTerritorio(tMod);
        	if(res.equals(Protocol.SUCCESS)) {
        		SceneHandler.getInstance().showInfo("Territorio modificato con successo!");
        		SoundHandler.getInstance().playSuccessSound();
        		boxAggiungiTerritiorio.setVisible(false);
        		tableTerritori.setVisible(true);
        		clearAdd();
        		update();

        	}
        	else {
        		error(res);
        		
        	}
        		
        		

    	}
    }
    
    @FXML
    void annullaEnd(ActionEvent event) {
    	boxAggiungiTerritiorio.setVisible(false);
    	tableTerritori.setVisible(true);
    	clearAdd();
    } 
    
    @FXML
    void modificaTerritorio(ActionEvent event) {
    	modifica=true;
    	if(tableTerritori.getSelectionModel().getSelectedItem()==null) {
    		SceneHandler.getInstance().showInfo("seleziona prima un territorio!");
			SoundHandler.getInstance().playErrorSound();
			return;
    	}
    	//prendiamo il territorio selezionato
    	ObservableList<Territorio> select=tableTerritori.getSelectionModel().getSelectedItems();
    	tMod=select.get(0);
    	
    	//settiamo i text con il territorio selezionato 
    	labelTerritorio.setText("Modifica Territorio:"); 
    	inserisciNomeText.setText(tMod.getNome());
    	inserisciNotaText.setText(tMod.getNota());
    	inserisciNumeroText.setText(Integer.toString(tMod.getNumeroTerritorio()));
    	inserisciNumeroText.setEditable(false);//tranne numero che non si puo modficare
    	
    	boxAggiungiTerritiorio.setVisible(true);
    	tableTerritori.setVisible(false);
    	assegnaBox.setVisible(false);
    	
    	
    }
    

    @FXML
    void assegnaTerritorio(ActionEvent event) {
    	if(!isSelected())
    		return;
    	ObservableList<Territorio> select=tableTerritori.getSelectionModel().getSelectedItems();
    	tMod=select.get(0);
    	
    	if(tMod.getAssegnato().equals("Assegnato")) {
    		error("Territorio già assegnato!");
    		return;
    	}
    	clearAsse();
    	showAssegn();
    	
    	assegna=true;
    	labelDateAssegna.setText("Data assegnamento:");

    	nomeAssegnaText.setEditable(assegna);
    	cognomeAssegnaText.setEditable(assegna);
    	
    	
    	assegnaNumeroLabel.setText(Integer.toString(tMod.getNumeroTerritorio()));
    }

    @FXML
    void consegnaTerritorio(ActionEvent event) {
    	if(!isSelected())
    		return;
    	
    	ObservableList<Territorio> select=tableTerritori.getSelectionModel().getSelectedItems();
    	tMod=select.get(0);
    	
    	if(tMod.getAssegnato().equals("Non assegnato")) {
    		error("Non è possibile proseguire.Territorio non ancora assegnato!");
    		return;
    	}
    	
    	
    	
    	assegna=false;
    	labelDateAssegna.setText("Data consegna:");

    	String nome=Client.getInstance().getInfoTerritorio(tMod);
		String [] nomeCognome=nome.split("\\s+");

		nomeAssegnaText.setText(nomeCognome[0]);
		cognomeAssegnaText.setText(nomeCognome[1]);
		
		if(nome==Protocol.ERROR) {
			error(nome);
			return;
		}
    	nomeAssegnaText.setEditable(assegna);
    	cognomeAssegnaText.setEditable(assegna);
    	assegnaNumeroLabel.setText(Integer.toString(tMod.getNumeroTerritorio()));
    	dateAssegna.setValue(null);
    	
    	showAssegn();
    }

    
    
    @FXML
    void aggiungiConsegnaEnd(ActionEvent event) {
    	String res;
    	if(assegna) {
    		tMod.setAssegnato("Assegnato");
    		
    		String nome=nomeAssegnaText.getText();
    		String cognome=cognomeAssegnaText.getText();
    		
    		if(nome.isEmpty() || cognome.isEmpty()|| dateAssegna.getValue()==null) {
    			error("Attenzione, inserisci tutti i valori!");
        		return;
    		}
    		String date = dateAssegna.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    		
    		 res=Client.getInstance().assegnaTerritorio(tMod, nome,cognome, date);
    		
    		
    	}
    	else
    	{
    		tMod.setAssegnato("Non assegnato");
    		
    		if( dateAssegna.getValue()==null) {
    			error("Attenzione, inserisci la data!");
        		return;
    		}
    		String date = dateAssegna.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    		
    		 res=Client.getInstance().consegnaTerritorio(tMod, date);
    		
    		
    	}
    	
    	if(res.equals(Protocol.SUCCESS))
			SceneHandler.getInstance().showInfo("Operazione avvenuta con successo!");
		else
			error(res);
		
			
    	update();
    	
    	
    	assegnaBox.setVisible(false);
    	tableTerritori.setVisible(true);
    }

    @FXML
    void annullaConsegnaEnd(ActionEvent event) {
    	assegnaBox.setVisible(false);
    	tableTerritori.setVisible(true);
    }
    
    private void update() {
    	ArrayList<Territorio> territori=Client.getInstance().getTerritori();
    	if(!territori.isEmpty()) {
    		territoriOb=FXCollections.observableList(territori);
    		tableTerritori.setItems(territoriOb);
    	}
    }
    
    private void clearAdd() {
    	inserisciNomeText.clear();
		inserisciNotaText.clear();
		inserisciNumeroText.clear();
    }
    
    private void clearAsse() {
    	nomeAssegnaText.clear();
    	cognomeAssegnaText.clear();
    }
    
    private boolean  isSelected() {
    	if(tableTerritori.getSelectionModel().getSelectedItem()==null) {
			error("seleziona prima un territorio!");
			return false;
    	}
    	
    	return true;
    }
    private void  showAssegn() {
    boxAggiungiTerritiorio.setVisible(false);
	assegnaBox.setVisible(true);
	tableTerritori.setVisible(false);
   
    }
    
    private void error(String res) {
    	SceneHandler.getInstance().showInfo(res);
    	SoundHandler.getInstance().playErrorSound();
    }
    
}
