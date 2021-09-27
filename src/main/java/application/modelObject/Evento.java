package application.modelObject;

import java.io.Serializable;

public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6062968202464768409L;
	
	
	private String titolo;
	private String luogo;
	private String descrizione;
	private String data;
	
	public Evento(String titolo, String luogo, String descrizione, String data) {
		super();
		this.titolo = titolo;
		this.luogo = luogo;
		this.descrizione = descrizione;
		this.data = data;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Titolo=" + titolo + ", luogo=" + luogo + ", descrizione=" + descrizione + ", data=" + data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((luogo == null) ? 0 : luogo.hashCode());
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
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
		Evento other = (Evento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (luogo == null) {
			if (other.luogo != null)
				return false;
		} else if (!luogo.equals(other.luogo))
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		return true;
	}
	
	
	
}
