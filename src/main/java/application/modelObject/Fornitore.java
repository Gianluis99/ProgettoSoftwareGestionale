package application.modelObject;

import java.io.Serializable;

public class Fornitore  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5111709685365425776L;
	private String cap;
	private String telefono;
	private String partitaIva;
	private String nome;
	private String cognome;
	private String via;
	private String citta;
	private String ragioneSociale;
	
	public Fornitore( String cap, String telefono, String partitaIva, String nome, String cognome, String via,
			String citta, String ragioneSociale) {
	
		this.cap = cap;
		this.telefono = telefono;
		this.partitaIva = partitaIva;
		this.nome = nome;
		this.cognome = cognome;
		this.via = via;
		this.citta = citta;
		this.ragioneSociale=ragioneSociale;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}
	
	


	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	@Override
	public String toString() {
		return "idFornitore="  + ", cap=" + cap + ", telefono=" + telefono + ", partitaIva="
				+ partitaIva + ", nome=" + nome + ", cognome=" + cognome + ", via=" + via + ", città=" + citta ;
	}



}
