package it.polito.tdp.flight.model;

public class Evento implements Comparable<Evento>{
	
	private int tyme;
	private Passeggero passeggero;
	private Airport partenza;
	private Airport destinazione;
	public Evento(int tyme, Passeggero passeggero, Airport partenza, Airport destinazione) {
		super();
		this.tyme = tyme;
		this.passeggero = passeggero;
		this.partenza = partenza;
		this.destinazione = destinazione;
	}
	public int getTyme() {
		return tyme;
	}
	public void setTyme(int tyme) {
		this.tyme = tyme;
	}
	public Passeggero getPasseggero() {
		return passeggero;
	}
	public void setPasseggero(Passeggero passeggero) {
		this.passeggero = passeggero;
	}
	public Airport getPartenza() {
		return partenza;
	}
	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}
	public Airport getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(Airport destinazione) {
		this.destinazione = destinazione;
	}
	@Override
	public String toString() {
		return this.tyme+" "+this.passeggero+" "+this.partenza+" "+this.destinazione;
	}
	@Override
	public int compareTo(Evento altro) {
		// TODO Auto-generated method stub
		return this.tyme-altro.tyme;
	}
	
	

}
