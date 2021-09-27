package application.controller;
import application.model.ModelUtility;
import application.net.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PaneHomeController {

    @FXML
    private Label prodottiLabel;

    @FXML
    private Label fornitoriLabel;

    @FXML
    private Label clientiLabel;
     

    @FXML
    private Label dataLabel;
    
  

    
    @FXML
    void initialize() {
    	//otteniamo il numero di elementi
    	int numProdotti=Client.getInstance().getNumProdotti();
    	String n=Integer.toString(numProdotti);
    	if(!n.equals("-1"))
    		prodottiLabel.setText(n);
    	
    	int numFornitori=Client.getInstance().getNumFornitori();
    	 n=Integer.toString(numFornitori);
    	 if(!n.equals("-1"))
    		 fornitoriLabel.setText(n);
    	
    	int numClienti=Client.getInstance().getNumClienti();
    	 n=Integer.toString(numClienti);
    	 if(!n.equals("-1"))
    		 clientiLabel.setText(n);
    	 
    	 	ModelUtility.getInstance().initTimeAndDate(dataLabel);
    }
    

	
    


}