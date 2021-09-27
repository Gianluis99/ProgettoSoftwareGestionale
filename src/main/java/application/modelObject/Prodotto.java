package application.modelObject;

import java.io.Serializable;


public class Prodotto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5690093898978360333L;
	
	
	private String id;
	private String nome;
	private String categoria;
	private  int prezzo;
	private String data ;
	private int  numDisponibili;
	private String nomeFornitore;
	private String cognomeFornitore;
	private byte []  imgProdotto;
	
	private int iva;
	private int quantita;
	
	
	
	public Prodotto(String id, String nome, String categoria, int prezzo, String data, int numDisponibili,
			String nomeFornitore, String cognomeFornitore,  byte [] imgProdotto) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.data = data;
		this.numDisponibili = numDisponibili;
		this.nomeFornitore = nomeFornitore;
		this.cognomeFornitore = cognomeFornitore;
		this.imgProdotto = imgProdotto;
	}

	
	public int getQuantita() {
		return quantita;
	}
	
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	
	
	
	public int getIva() {
		return iva;
	}
	
	public void setIva(int iva) {
		this.iva = iva;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getNumDisponibili() {
		return numDisponibili;
	}

	public void setNumDisponibili(int numDisponibili) {
		this.numDisponibili = numDisponibili;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public  byte [] getImgProdotto() {
		return imgProdotto;
	}

	public void setImgProdotto( byte [] imgProdotto) {
		this.imgProdotto = imgProdotto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "id=" + id + ", nome=" + nome + ", categoria=" + categoria + ", prezzo=" + prezzo + ", data="
				+ data + ", numDisponibili=" + numDisponibili + ", nomeFornitore=" + nomeFornitore
				+ ", cognomeFornitore=" + cognomeFornitore ;
	}

}
