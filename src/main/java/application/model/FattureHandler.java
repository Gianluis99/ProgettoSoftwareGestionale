package application.model;

import java.util.ArrayList;
import java.util.regex.Pattern;

import application.SceneHandler;
import application.modelObject.Prodotto;
import application.net.client.Client;
import application.net.stuff.Protocol;

public class FattureHandler {
	private ArrayList<Prodotto> prodottiEnd;
	private int totaleIva;
	private int totalePrezzo;
	
	public FattureHandler() {
		prodottiEnd=new ArrayList<Prodotto>();
		
	}
	
	public int getTotaleIva() {
		return totaleIva;
	}
	public void setTotaleIva(int totaleIva) {
		this.totaleIva = totaleIva;
	}
	
	public int getTotalePrezzo() {
		return totalePrezzo;
	}
	
	public void setTotalePrezzo(int totalePrezzo) {
		this.totalePrezzo = totalePrezzo;
	}
	
	public ArrayList<Prodotto> getProdottiEnd() {
		return prodottiEnd;
	}
	
	public void clearProdottiEnd() {
		this.prodottiEnd.clear();
	}
	
	public Prodotto addProdotto(Prodotto prodotto,int q,int iva) {
		for(var e:prodottiEnd) {
    		if(e.getId()==prodotto.getId()) {
    			SceneHandler.getInstance().errorInfoSound("Prodotto già inserito!");
    			return null;
    		}
    	}
		
		prodotto.setQuantita(q);
		prodotto.setIva(iva);
		
		prodottiEnd.add(prodotto);
		
		
		totaleIva+=prodotto.getIva()*prodotto.getQuantita();
    	totalePrezzo+=(prodotto.getPrezzo()*prodotto.getQuantita());
		return prodotto;
	}
	
	public boolean updateProdotti() {

    	for(var e:prodottiEnd) {
    		if(e.getQuantita()==e.getNumDisponibili())
    			if(!SceneHandler.getInstance().showAlert("il prodotto con id= "+e.getId()+ " e nome= "+e.getNome()+"  sarà terminato ed eliminato! Sei sicuro di voler continuare?"))
    				return false;
    		String s=Client.getInstance().updateProdotto(e);
    		if(!s.equals(Protocol.SUCCESS)) {
    			SceneHandler.getInstance().errorInfoSound(s);
    			return false;
    		}
    	}
    	return true;
	}
	
	public boolean isNumber(String num) {
		return Pattern.matches("[0-9]+", num) ;
			
	}
	
	
}
