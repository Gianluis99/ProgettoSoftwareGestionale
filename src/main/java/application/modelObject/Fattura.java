package application.modelObject;

import java.util.ArrayList;

public class Fattura {

	private String numeroRicevuta;
	private String numeroOrdine;
	private String dataOrdine;
	private Cliente cliente;
	private ArrayList<Prodotto> prodotti;
	private String dataScadenzaPagamento;
	private String imponibile;
	private String sconto;
	private int totaleIva;
	private int totalePrezzo;
	
	public Fattura(String numeroRicevuta, String numeroOrdine, String dataOrdine, Cliente cliente,
			ArrayList<Prodotto> prodotti,String dataScadenzaPagamento, String imponibile, 
			String sconto, int totaleIva, int totalePrezzo) {
		super();
		this.numeroRicevuta = numeroRicevuta;
		this.numeroOrdine = numeroOrdine;
		this.dataOrdine = dataOrdine;
		this.cliente = cliente;
		this.prodotti = prodotti;
		this.dataScadenzaPagamento = dataScadenzaPagamento;
		this.imponibile = imponibile;
		this.sconto = sconto;
		this.totaleIva = totaleIva;
		this.totalePrezzo = totalePrezzo;
	}

	public String getNumeroRicevuta() {
		return numeroRicevuta;
	}

	public void setNumeroRicevuta(String numeroRicevuta) {
		this.numeroRicevuta = numeroRicevuta;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	public String getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(String dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}



	public String getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}

	public void setDataScadenzaPagamento(String dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}

	public String getImponibile() {
		return imponibile;
	}

	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}

	public String getSconto() {
		return sconto;
	}

	public void setSconto(String sconto) {
		this.sconto = sconto;
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

	@Override
	public String toString() {
		return "Fattura [numeroRicevuta=" + numeroRicevuta + ", numeroOrdine=" + numeroOrdine + ", dataOrdine="
				+ dataOrdine + ", cliente=" + cliente + ", prodotti=" + prodotti+ ", dataScadenzaPagamento="
				+ dataScadenzaPagamento + ", imponibile=" + imponibile + ", sconto=" + sconto + ", totaleIva="
				+ totaleIva + ", totalePrezzo=" + totalePrezzo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataOrdine == null) ? 0 : dataOrdine.hashCode());
		result = prime * result + ((dataScadenzaPagamento == null) ? 0 : dataScadenzaPagamento.hashCode());
		result = prime * result + ((imponibile == null) ? 0 : imponibile.hashCode());
		result = prime * result + ((numeroOrdine == null) ? 0 : numeroOrdine.hashCode());
		result = prime * result + ((numeroRicevuta == null) ? 0 : numeroRicevuta.hashCode());
		result = prime * result + ((prodotti == null) ? 0 : prodotti.hashCode());
		result = prime * result + ((sconto == null) ? 0 : sconto.hashCode());
		result = prime * result + totaleIva;
		result = prime * result + totalePrezzo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fattura other = (Fattura) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataOrdine == null) {
			if (other.dataOrdine != null)
				return false;
		} else if (!dataOrdine.equals(other.dataOrdine))
			return false;
		if (dataScadenzaPagamento == null) {
			if (other.dataScadenzaPagamento != null)
				return false;
		} else if (!dataScadenzaPagamento.equals(other.dataScadenzaPagamento))
			return false;
		if (imponibile == null) {
			if (other.imponibile != null)
				return false;
		} else if (!imponibile.equals(other.imponibile))
			return false;
		
		if (numeroOrdine == null) {
			if (other.numeroOrdine != null)
				return false;
		} else if (!numeroOrdine.equals(other.numeroOrdine))
			return false;
		if (numeroRicevuta == null) {
			if (other.numeroRicevuta != null)
				return false;
		} else if (!numeroRicevuta.equals(other.numeroRicevuta))
			return false;
		if (prodotti == null) {
			if (other.prodotti != null)
				return false;
		} else if (!prodotti.equals(other.prodotti))
			return false;
	
		if (sconto == null) {
			if (other.sconto != null)
				return false;
		} else if (!sconto.equals(other.sconto))
			return false;
		if (totaleIva != other.totaleIva)
			return false;
		if (totalePrezzo != other.totalePrezzo)
			return false;
		return true;
	}
	
	
	
	

}
