package it.polito.tdp.flight.model;

public class Passeggero {
	
	private String id;
	private int numeroViaggi;
	public Passeggero(String id, int numeroViaggi) {
		super();
		this.id = id;
		this.numeroViaggi = numeroViaggi;
	}
	public String getId() {
		return id;
	}
	public int getNumeroViaggi() {
		return numeroViaggi;
	}
	
	public void incrementaViaggi(){
		this.numeroViaggi++;
	}
	@Override
	public String toString() {
		return String.format("Passeggero [%s,%s]", id, numeroViaggi);
	}

}
