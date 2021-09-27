package application.controller;

import java.util.ArrayList;

import application.SceneHandler;
import application.model.GeneratePdf;
import application.modelObject.Fornitore;
import application.net.client.Client;
import application.net.stuff.Protocol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class FornitoreController {

	
		private ObservableList<Fornitore> foOb;
		private ArrayList<Fornitore> fo;
		private boolean modifica=false;
		private Fornitore modificaFornitore=null;
		


		@FXML
	    private TableView<Fornitore> fornitoreTable;

	    @FXML
	    private TableColumn<Fornitore, String> telefonoFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> cittaFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> nomeFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> ragioneSocialeFornitore;
	    
	    @FXML
	    private TableColumn<Fornitore, String> capFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> viaFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> ivaFornitore;

	    @FXML
	    private TableColumn<Fornitore, String> cognomeFornitore;
	    
	    
	    

	    @FXML
	    private TextField ricercaFornitoreText;


	    @FXML
	    private Button scaricaPdfFornitoreButton;

	    
	    @FXML
	    private Button ricercaFornitoreButton;

	  
	    @FXML
	    private Button aggiungiFornitoreButton;

	    @FXML
	    private Button modificaFornitoreButton;


	    @FXML
	    private Button eliminaFornitoreButton;

	    
	    
	    //boxAddFornitore
	    @FXML
	    private Button salvaFornitoreButton;
	    
	    @FXML
	    private Button annullaButtonEnd;


	    @FXML
	    private TextField inserisciViaFornitore;

	    @FXML
	    private TextField inserisciPartitaIvaFornitore;

	    @FXML
	    private TextField inserisciNomeFornitore;

	    @FXML
	    private TextField inserisciCognomeFornitore;
	    
	    @FXML
	    private TextField inserisciCittaFornitore;

	    @FXML
	    private TextField inserisciTelefono;

	    @FXML
	    private TextField inserisciRagionesocialeFornitore;

	    @FXML
	    private TextField inserisciCapFornitore;
	    

	    @FXML
	    private AnchorPane boxAddFornitore;

	   

	    
	    
	    @FXML
	    public  void initialize() {
	    	showBoxAdd(false);
	    	nomeFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	cognomeFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	telefonoFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	ivaFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	viaFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	ragioneSocialeFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	cittaFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	capFornitore.prefWidthProperty().bind(fornitoreTable.widthProperty().divide(8));
	    	fo=Client.getInstance().getFornitori();
	    	updateAll();
	    	
	    }
	    
	    
	    private void updateAll() {
			foOb = FXCollections.observableArrayList(fo);

			fornitoreTable.setItems(foOb);
		
	    	if(fo!=null) {
	        	nomeFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("nome"));
	        	cognomeFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("cognome"));
	        	cittaFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("citta"));
	        	telefonoFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("telefono"));
	        	capFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("cap"));
	        	ivaFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("partitaIva"));
	        	ragioneSocialeFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("ragioneSociale"));
	        	viaFornitore.setCellValueFactory(new PropertyValueFactory<Fornitore, String>("via"));

		}
	    	else
	    		SceneHandler.getInstance().errorInfoSound("Non è stato possibile caricare i fornitori!");
		
		}
	    
	    
	    //1
	    @FXML
	    void aggiungiFornitore(ActionEvent event) {
	    	clearAll();
	    	showBoxAdd(true);
	    	modifica=false;
	    }

	    
	    //2
	    @FXML
	    void modificaFornitore(ActionEvent event) {
	    	  modifica=true;

				if(fornitoreTable.getSelectionModel().getSelectedItem()== null) {
					SceneHandler.getInstance().errorInfoSound("Seleziona prima il fornitore da modificare!");
					return;
				}
				
				ObservableList<Fornitore> sel;
		    	sel=fornitoreTable.getSelectionModel().getSelectedItems();
		    	
		    	Fornitore fornitore=sel.get(0); 
				modificaFornitore=fornitore;

				loadFornitoreEdit(fornitore.getNome(),fornitore.getCognome() , fornitore.getCap(), fornitore.getPartitaIva(),
						fornitore.getRagioneSociale(),fornitore.getCitta(), fornitore.getTelefono(), fornitore.getVia());
				showBoxAdd(true);
				
	    }

	    
	    //3
	    @FXML
	    void salvaFornitore(ActionEvent event) {
	    	
	    	if(!modifica) {
	    	String res=Client.getInstance().addFornitore(inserisciCapFornitore.getText(), inserisciTelefono.getText(), 
	    	inserisciPartitaIvaFornitore.getText(), inserisciNomeFornitore.getText(),inserisciCognomeFornitore.getText(), 
	    	inserisciViaFornitore.getText(), inserisciCittaFornitore.getText(), inserisciRagionesocialeFornitore.getText());
	    	
	    	if(res.equals(Protocol.FORNITORE_SUCCESS)) {
	    		try {
	    			clearAll();
					SceneHandler.getInstance().successInfoSound(res);
	        		showBoxAdd(false);
	    	    	fo=Client.getInstance().getFornitori();
	        		updateAll();

				} catch (Exception e) {
					clearAll();
					SceneHandler.getInstance().errorInfoSound("Errore del Server");
					e.printStackTrace();
				}
	    	}
	    	else 
				SceneHandler.getInstance().errorInfoSound(res);
	    		
	    	}
	    	else {
	    		Fornitore newForn=new Fornitore(inserisciCapFornitore.getText(), inserisciTelefono.getText(), 
	    		    	inserisciPartitaIvaFornitore.getText(), inserisciNomeFornitore.getText(),inserisciCognomeFornitore.getText(), 
	    		    	inserisciViaFornitore.getText(), inserisciCittaFornitore.getText(), inserisciRagionesocialeFornitore.getText());
	    		
	    		String res=Client.getInstance().editFornitore(modificaFornitore,newForn);
	        	if(res.equals(Protocol.FORNITORE_EDIT_SUCCESS)) {
	        		try {
	            		SceneHandler.getInstance().successInfoSound(res);
	     			   showBoxAdd(false);
	     		    	fo=Client.getInstance().getFornitori();
	            		updateAll();	
	    			} catch (Exception e) {
	    				clearAll();
	    				e.printStackTrace();
	    			}
	        	}
	        	else 
    				SceneHandler.getInstance().errorInfoSound(res);
	        	
		   }
		   
	    	
	    	}
	    

	  
	    @FXML
	    void annullaFornitoreEnd(ActionEvent event) {
	    	showBoxAdd(false);
	    }
	    
	    
	    
	    
	    @FXML
	    void eliminaFornitore(ActionEvent event) {
	    	if(fornitoreTable.getSelectionModel().getSelectedItem()!= null) {
		    	ObservableList<Fornitore> selezionato;
		    	selezionato=fornitoreTable.getSelectionModel().getSelectedItems();
		    	Fornitore f=selezionato.get(0);
		    	if(SceneHandler.getInstance().showAlert("Sei sicuro di voler eliminare questo Fornitore?"+f)) {

		    		String res=Client.getInstance().deleteFornitore(f);
		    		SceneHandler.getInstance().successInfoSound(res);
		    		fo=Client.getInstance().getFornitori();
		    		updateAll();
		    		
		    	}
		    	
		    	}
		    	else {
		    		SceneHandler.getInstance().errorInfoSound("seleziona prima un fornitore!");
		    	}
	    }

	    @FXML
	    void scaricaPdfFornitore(ActionEvent event) {
	    	ArrayList<Fornitore> forni=Client.getInstance().getFornitori();
			
	    	int res=GeneratePdf.getInstance().generatePdfFornitori(forni);
	    	
			if(res==1) {
				
	    		SceneHandler.getInstance().successInfoSound("Pdf scaricato correttamente nella cartella selezionata!");
			
			}
			else if(res==2)
			{
				
	    		SceneHandler.getInstance().errorInfoSound("Non è stato possibile scaricare il pdf!Riprova più tardi");
			
			}
	    }

	    @FXML
	    void ricercaFornitore(ActionEvent event) {
	    	String ricerca=ricercaFornitoreText.getText();

	    	String[] div = ricerca.split("\\s+");
	    	if(div[0].equals("") || div.length!=2) {
	    		SceneHandler.getInstance().errorInfoSound("Inserisci Nome e Cognome!");
	    		return;
	    	}
	    	fo=Client.getInstance().searchFornitori(div[0],div[1]);
	    	if(!fo.isEmpty())
	    		updateAll();
	    	else
	    		SceneHandler.getInstance().showInfo("Nessun risultato trovato!");
	    	ricercaFornitoreText.clear();
	    	
	    }
	    
	    
	    private void showBoxAdd(boolean  value) {
	    	boxAddFornitore.setVisible(value);
	    	fornitoreTable.setVisible(!value);
	    }
	    
	    
	    private void clearAll() {
	    	inserisciCittaFornitore.clear();
	    	inserisciCapFornitore.clear();
	    	inserisciCognomeFornitore.clear();
	    	inserisciNomeFornitore.clear();
	    	inserisciPartitaIvaFornitore.clear();
	    	inserisciRagionesocialeFornitore.clear();
	    	inserisciTelefono.clear();
	    	inserisciViaFornitore.clear();
	    }

	    private void loadFornitoreEdit(String nome,String cognome ,String cap,String iva,String ragioneSociale,String citta,
	    		String telefono,String via) {
	    	
	    	inserisciCittaFornitore.setText(citta);
	    	inserisciCapFornitore.setText(cap);
	    	inserisciCognomeFornitore.setText(cognome);
	    	inserisciNomeFornitore.setText(nome);
	    	inserisciPartitaIvaFornitore.setText(iva);
	    	inserisciRagionesocialeFornitore.setText(ragioneSociale);
	    	inserisciTelefono.setText(telefono);
	    	inserisciViaFornitore.setText(via);
	    }
	}
