package application.view;

import java.io.IOException;
import java.util.ArrayList;

import application.SceneHandler;
import application.controller.prodotti.ProdottoItemController;
import application.modelObject.Prodotto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class WarehouseView {

	
	public void addProdotti(ArrayList<Prodotto> prodotti,GridPane gridPane) {
		if(prodotti==null) {
			   SceneHandler.getInstance().showInfo("Non è stato possibile caricare i prodotti! Riprova più tardi o aggiungi dei prodotti!");
			   return;
		   }
		    int col=0;
			int row=0;
			for(var e:prodotti) {
				try {
		    	FXMLLoader  loader=new FXMLLoader(getClass().getResource("/application/fxml/prodotto.fxml"));
		    	AnchorPane anchorPane=loader.load();
		    	
		    	ProdottoItemController pcont=loader.getController();
		    	pcont.setAll(e);
		    	
		    	
		    	if(col==3) {
		    		col=0;
		    		row++;
		    	}
		    	gridPane.add(anchorPane,col++,row);
		    	
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    	}
	}
	
}
