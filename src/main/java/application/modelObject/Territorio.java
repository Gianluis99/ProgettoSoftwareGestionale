package application.modelObject;

import java.io.Serializable;

public class Territorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972564470256993041L;
	
	
	private String nome;
	private int numeroTerritorio;
	private String nota;
	private String assegnato;

	
	
	public Territorio(String nome, int numeroTerritorio) {
		super();
		this.nome = nome;
		this.numeroTerritorio = numeroTerritorio;
	
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getNumeroTerritorio() {
		return numeroTerritorio;
	}
	public void setNumeroTerritorio(int numeroTerritorio) {
		this.numeroTerritorio = numeroTerritorio;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public void setAssegnato(String assegnato) {
		this.assegnato = assegnato;
	}
	
	public String getAssegnato() {
		return assegnato;
	}
	

	@Override
	public String toString() {
		return "Territorio [nome=" + nome + ", numeroTerritorio=" + numeroTerritorio + ", nota=" + nota +" ,Assegnato? = "+assegnato+"]";
	}
	
	
	
}
