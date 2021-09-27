 package application.modelObject;

import java.io.Serializable;

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102956960755981132L;
	
	
	private String nome; 
	private String cognome;
	private String citta;
	private String telefono;
	private String iva;
	
	public Cliente( String nome, String cognome, String citta, String telefono) {
	
		this.nome = nome;
		this.cognome = cognome;
		this.citta = citta;
		this.telefono = telefono;
	}

	
	public String getIva() {
		return iva;
	}
	
	public void setIva(String iva) {
		this.iva = iva;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return " nome=" + nome + ", cognome=" + cognome + ", citta=" + citta
				+ ", telefono=" + telefono ;
	}



	
	
}
