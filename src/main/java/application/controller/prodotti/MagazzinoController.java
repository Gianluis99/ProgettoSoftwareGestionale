package application.controller.prodotti;


import application.net.client.Client;
import application.view.WarehouseView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class MagazzinoController {

	private WarehouseView wv;
	
	@FXML
	private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;
	    
    @FXML
    private Button gestisciButton;

   
    
	@FXML  
	void initialize(){
		wv=new WarehouseView();
		gridPane.prefHeightProperty().bind(scrollPane.heightProperty());
		gridPane.prefWidthProperty().bind(scrollPane.widthProperty());

		wv.addProdotti(Client.getInstance().getProdotti(), gridPane);
		
	    }

	 @FXML
	    void goToGestisci(ActionEvent event) {

	    }
	
}
