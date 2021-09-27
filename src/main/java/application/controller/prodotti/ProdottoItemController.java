package application.controller.prodotti;

import java.io.ByteArrayInputStream;

import application.modelObject.Prodotto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProdottoItemController {

	private Prodotto prodotto;
	
    @FXML
    private ImageView imageV;

    @FXML
    private Label categoriaLabel;

    @FXML
    private Label nomeFornitoreLabel;

    @FXML
    private Label prezzoLabel;

    @FXML
    private Label dataLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label numDispLabel;

    @FXML
    private Label cognomeFornitoreLabel;

    @FXML
    private Label codiceLabel;
    
    
    
	public void setAll(Prodotto prodotto) {
    	this.setProdotto(prodotto);
    	categoriaLabel.setText(prodotto.getCategoria());
    	nomeLabel.setText(prodotto.getNome());
    	prezzoLabel.setText(Integer.toString(prodotto.getPrezzo()));
    	cognomeFornitoreLabel.setText(prodotto.getCognomeFornitore());
    	dataLabel.setText(prodotto.getData());
    	codiceLabel.setText(prodotto.getId());
    	numDispLabel.setText(Integer.toString(prodotto.getNumDisponibili()));
    	
    	byte []b=prodotto.getImgProdotto();
    	if(b!=null) {
    	Image img=new Image(new ByteArrayInputStream(b),120,90,false,true);
    		imageV.setImage(img);
    	}
    	
    }



	public Prodotto getProdotto() {
		return prodotto;
	}



	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
}
