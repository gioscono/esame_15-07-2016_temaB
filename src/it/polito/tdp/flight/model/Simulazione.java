package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Simulazione {

	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private PriorityQueue<Evento> queue;
	private Map<Airport, Integer> mappaPasseggeri;
	private Map<Integer, Airport> mappaAeroporti;
	int[] ore = {7,9,11,13,15,17,19,21,23,31,33,35,37,39,41,43,45,47};
	
	
	public Simulazione(SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo, Map<Integer, Airport> mappaAeroporti){
		this.grafo = grafo;
		this.mappaPasseggeri = new HashMap<>();
		this.mappaAeroporti = mappaAeroporti;
		queue = new PriorityQueue<>();
	}


	public void inserisciPasseggeri(int k) {
		//mappa che tiene conto del presente
		for(Airport a: grafo.vertexSet()){
			mappaPasseggeri.put(a, 0);
		}
		Airport parigi = mappaAeroporti.get(1382);
		Airport newYork = mappaAeroporti.get(3697);
		//creo i passeggeri
		for(int i =0; i<k; i++){
			String id = "Pass_"+i;
			Passeggero p = new Passeggero(id, 0);
			if(i<k/2){
				List<Airport> vicini = Graphs.successorListOf(grafo, parigi);
				Random r = new Random();
				int scelta = r.nextInt(vicini.size());
				Airport destinazione = vicini.get(scelta);
				Evento e = new Evento(6, p, parigi, destinazione);
				p.incrementaViaggi();
				//this.incrementaPasseggeri(destinazione);
				this.decrementaPasseggeri(parigi);
				queue.add(e);
			}else{
				List<Airport> vicini = Graphs.successorListOf(grafo, newYork);
				System.out.println(vicini.size());
				Random r = new Random();
				int scelta = r.nextInt(vicini.size());
				Airport destinazione = vicini.get(scelta);
				Evento e = new Evento(6, p, newYork, destinazione);
				p.incrementaViaggi();
				//this.incrementaPasseggeri(destinazione);
				this.decrementaPasseggeri(newYork);
				queue.add(e);
			}
		}
		System.out.println(queue);
	
		
		
	}


	private void decrementaPasseggeri(Airport partenza) {
		int punteggio = mappaPasseggeri.get(partenza);
		punteggio--;
		mappaPasseggeri.put(partenza, punteggio);
		
	}


	private void incrementaPasseggeri(Airport destinazione) {
		int punteggio = mappaPasseggeri.get(destinazione);
		punteggio++;
		mappaPasseggeri.put(destinazione, punteggio);
		
	}


	public List<AeroportoConPasseggeri> run() {

		while(!queue.isEmpty()){
			Evento e = queue.poll();
			System.out.println(e);
			this.incrementaPasseggeri(e.getDestinazione());
			this.decrementaPasseggeri(e.getPartenza());
			
			if(e.getPasseggero().getNumeroViaggi()<5){
				
				List<Airport> vicini = Graphs.successorListOf(grafo, e.getDestinazione());
				Random r = new Random();
				int scelta = r.nextInt(vicini.size());
				
				Airport destinazione = vicini.get(scelta);
				Evento e2 = new Evento(this.calcolaOraPartenza(e), e.getPasseggero(), e.getDestinazione(), destinazione);
				e.getPasseggero().incrementaViaggi();
				queue.add(e2);
			}
		}
		List<AeroportoConPasseggeri> lista = new ArrayList<>();
		for(Airport a : mappaPasseggeri.keySet()){
			if(mappaPasseggeri.get(a)>0){
				lista.add(new AeroportoConPasseggeri(a,mappaPasseggeri.get(a) ));
			}
		}
		
		return lista;
	}


	private int calcolaOraPartenza(Evento e) {
		int arrivo = (int) (LatLngTool.distance(e.getPartenza().getCoords(), e.getDestinazione().getCoords(), LengthUnit.KILOMETER)/900);
		arrivo += e.getTyme();
		
		for(int i =0; i<ore.length; i++){
			if(arrivo<ore[i]){
				return ore[i];
			}
		}
		return 0;
	}
}
