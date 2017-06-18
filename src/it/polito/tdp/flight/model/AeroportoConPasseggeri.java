package it.polito.tdp.flight.model;

public class AeroportoConPasseggeri implements Comparable<AeroportoConPasseggeri>{
	
	private Airport a;
	private int pers;
	public AeroportoConPasseggeri(Airport a, int pers) {
		super();
		this.a = a;
		this.pers = pers;
	}
	public Airport getA() {
		return a;
	}
	public void setA(Airport a) {
		this.a = a;
	}
	public int getPers() {
		return pers;
	}
	public void setPers(int pers) {
		this.pers = pers;
	}
	
	@Override
	public int compareTo(AeroportoConPasseggeri o) {
		
		return this.pers-o.pers;
	}
	

}
