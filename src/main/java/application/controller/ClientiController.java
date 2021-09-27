package application.controller;


import java.util.ArrayList;


import application.SceneHandler;
import application.model.GeneratePdf;
import application.modelObject.Cliente;
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

public class ClientiController {

		private ObservableList<Cliente> clOb;
		private ArrayList<Cliente> cl;
		private Cliente modifi=null;
		private boolean modifica=false;
		
		@FXML
    	private TableView<Cliente> clienteTable;

	  	@FXML
	    private TableColumn<Cliente, String> cittaCliente;
	  
	    @FXML
	    private TableColumn<Cliente, String> cognomeCliente;

	    @FXML
	    private TableColumn<Cliente, String> telefonoCliente;

	    @FXML
	    private TableColumn<Cliente, String> nomeCliente;
	    
	    @FXML
	    private Button modificaClienteButton;

	    @FXML
	    private Button eliminaClienteButton;

	    @FXML
	    private Button scaricaPdfClientiButton;

	    @FXML
	    private Button aggiungiClienteButton;
	    
	    @FXML
	    private Button ricercaClienteButton;
	    

	    @FXML
	    private TextField ricercaClienteText;

	    
	    //boxxAdd
	    
	    @FXML
	    private TextField insertCittaCliente;

	    @FXML
	    private TextField insertTelefonoCliente;

	    @FXML
	    private TextField insertCognomeCliente;

	    @FXML
	    private TextField insertNomeCliente;
	    
	    @FXML
	    private AnchorPane AnchorPaneBox;
	    
	    @FXML
	    private Button salvaNewClienteButton;

	    @FXML
	    private Button annullaClienteButton;

	    
	    
	    @FXML
	    public  void initialize() {
	    	AnchorPaneBox.setVisible(false);
	    	
	    	cittaCliente.prefWidthProperty().bind(clienteTable.widthProperty().divide(4));
	    	nomeCliente.prefWidthProperty().bind(clienteTable.widthProperty().divide(4));
	    	cognomeCliente.prefWidthProperty().bind(clienteTable.widthProperty().divide(4));
	    	telefonoCliente.prefWidthProperty().bind(clienteTable.widthProperty().divide(4));
	    	
	    	
	    	cl=Client.getInstance().getClienti();
	    	updateAll();
	    	
	    }
	    
	    //inserisce i dati nella tabella
	    private void updateAll() {
			clOb = FXCollections.observableArrayList(cl);

    		clienteTable.setItems(clOb);
    	
	    	if(cl!=null) {
	        	nomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
	        	cognomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cognome"));
	        	cittaCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("citta"));
	        	telefonoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
			
		}
	    	else {
	    		SceneHandler.getInstance().errorInfoSound("Non è stato possibile caricare i clienti!");;
	    	}
		}


	    
	    
	    
	    //1
	    
	    @FXML
	    void aggiungiCliente(ActionEvent event) {
	    modifica=false;
	    clearAll();
	    showBoxAdd(true);
	    
	    }
	    
	    
	     
	  
	    //2
		@FXML
	    void modificaCliente(ActionEvent event) {
		    modifica=true;

			if(clienteTable.getSelectionModel().getSelectedItem()== null) {
				SceneHandler.getInstance().errorInfoSound("Seleziona prima il cliente da modificare!");
				return;
			}
			
	    		//prendo l'utente selezionato
	    	ObservableList<Cliente> sel;
	    	sel=clienteTable.getSelectionModel().getSelectedItems();
	    	
	    	Cliente c=sel.get(0); 
			modifi=c;
			load(modifi.getNome(), modifi.getCognome(), modifi.getCitta(), modifi.getTelefono());
			showBoxAdd(true);
			
			
	    	
	    }
		
		//3
		   @FXML
		    void salvaCliente(ActionEvent event) {
			   
			   
			   if(!modifica) {
				   String res=Client.getInstance().addCliente(insertNomeCliente.getText(), insertCognomeCliente.getText(), 
			    			insertCittaCliente.getText(), insertTelefonoCliente.getText());
			    	if(res.equals(Protocol.CLIENTE_SUCCESS)) {
			    		try {
			        		SceneHandler.getInstance().successInfoSound(res);
			        		clearAll();
			    	    	cl=Client.getInstance().getClienti();
			        		updateAll();
			        		showBoxAdd(false);
			        		
						} catch (Exception e) {
							clearAll();
			        		SceneHandler.getInstance().errorInfoSound(res);
							e.printStackTrace();
						}
			    	}
			    	else
		        		SceneHandler.getInstance().errorInfoSound(res);

			    		
			   }
			   else {
				   Cliente cnew=new Cliente(insertNomeCliente.getText(), insertCognomeCliente.getText(), 
		        			insertCittaCliente.getText(), insertTelefonoCliente.getText());
			   
					String res=Client.getInstance().editCliente(modifi,cnew);
		        	if(res.equals(Protocol.CLIENTE_EDIT_SUCCESS)) {
		        		try {
		            		SceneHandler.getInstance().successInfoSound(res);
		        	    	cl=Client.getInstance().getClienti();
		            		updateAll();
		     			   showBoxAdd(false);
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
		    void annullaClienteEnd(ActionEvent event) {
			   showBoxAdd(false);
		    }
		
		
		
		   
		
		

	    @FXML
	    void eliminaCliente(ActionEvent event) {
	    	if(clienteTable.getSelectionModel().getSelectedItem()!= null) {
		    	ObservableList<Cliente> sel;
		    	sel=clienteTable.getSelectionModel().getSelectedItems();
		    	Cliente c=sel.get(0);
		    	if(SceneHandler.getInstance().showAlert("Sei sicuro di voler eliminare questo Cliente?"+c)) {
		    		//richesta al client
		    		String res=Client.getInstance().deleteCliente(c);
		    		SceneHandler.getInstance().showInfo(res);
		    	    cl=Client.getInstance().getClienti();
		    		updateAll();
		    	}
		    	
		    	}
		    	else {
		    		SceneHandler.getInstance().errorInfoSound("seleziona prima un cliente!");
		    	}
		    }
	    
	    @FXML
	    void scaricaPdfClienti(ActionEvent event) {
	     
			ArrayList<Cliente> cli=Client.getInstance().getClienti();
			int result=GeneratePdf.getInstance().generatePdfClienti(cli);
			if(result==1) {
	    		SceneHandler.getInstance().successInfoSound("Pdf scaricato correttamente nella cartella selezionata!");
			}
			else if(result==2){
	    		SceneHandler.getInstance().errorInfoSound("Non è stato possibile scaricare il Pdf nella cartella selezionata!");

			}
	    }
	    
	    
	    
	    @FXML
	    void ricercaCliente(ActionEvent event) {
	    	String ricerca=ricercaClienteText.getText();

	    	String[] divide = ricerca.split("\\s+");
	    	if(divide[0].equals("") || divide.length!=2) {
	    		SceneHandler.getInstance().errorInfoSound("Inserisci Nome e Cognome!");
	    		return;
	    	}
	    	cl=Client.getInstance().searchClienti(divide[0],divide[1]);
	    	if(!cl.isEmpty()) {
	    		updateAll();
		    	ricercaClienteText.clear();
	
	    	}
	    	else
	    		SceneHandler.getInstance().showInfo("Nessun risultato trovato!");
	    	
	    }

	  
	   
	    
	   private void load( String nome, String cognome, String citta, String telefono ) {
	   	   insertNomeCliente.setText(nome);
	   	   insertCittaCliente.setText(citta);
	   	   insertCognomeCliente.setText(cognome);
	   	   insertTelefonoCliente.setText(telefono);
	   	   
	      }
	    
	    private void showBoxAdd(boolean  a) {
	    	AnchorPaneBox.setVisible(a);
	    	clienteTable.setVisible(!a);
	    }
	    

	   	private void clearAll() {
	    	insertCittaCliente.clear();
	        insertTelefonoCliente.clear();
	        insertCognomeCliente.clear();
	        insertNomeCliente.clear();
	    }
}
