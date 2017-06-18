package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private FlightDAO dao;
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> aeroporti;
	private Map<Integer, Airport> mappaAeroporti;
	private Airport piuVicino = null;
	
	
	public Model(){
		dao = new FlightDAO();
		mappaAeroporti = new HashMap<>();
	}
	
	public List<Airport> getTuttiAeroporti(){
		if(aeroporti == null){
			this.aeroporti = dao.getAllAirports();
			for(Airport a : aeroporti){
				mappaAeroporti.put(a.getAirportId(), a);
			}
			
		}
		return aeroporti;
	}
	
	
	public boolean creaGrafo(int distanza){
		
		grafo = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Route> rotte = dao.getAllRoutes();
		this.getTuttiAeroporti();
		Graphs.addAllVertices(grafo, this.aeroporti);
		//System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		for(Route r : rotte){
			if(mappaAeroporti.containsKey(r.getSourceAirportId()) && mappaAeroporti.containsKey(r.getDestinationAirportId())){
				Airport da = mappaAeroporti.get(r.getSourceAirportId());
				Airport a = mappaAeroporti.get(r.getDestinationAirportId());
				double spazio = LatLngTool.distance(da.getCoords(), a.getCoords(), LengthUnit.KILOMETER );
				if(spazio >distanza){
					Graphs.addEdgeWithVertices(grafo, da, a, spazio/900);
				}
			}
		}
		//System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		List<Airport> daRimuovere = new ArrayList<>();
		for(Airport a : grafo.vertexSet()){
			if(grafo.outgoingEdgesOf(a).size()==0 && grafo.incomingEdgesOf(a).size()==0){
				daRimuovere.add(a);
			}
		}
		grafo.removeAllVertices(daRimuovere);
		//System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		ConnectivityInspector<Airport,DefaultWeightedEdge> inspector = new ConnectivityInspector<Airport,DefaultWeightedEdge>(grafo);
		boolean connesso = inspector.isGraphConnected();
		Airport a = mappaAeroporti.get(3484);
		//System.out.println(a);
		Set<Airport> raggiungibili = inspector.connectedSetOf(a);
		Set<Airport> tutti = new HashSet<>(this.aeroporti);
		tutti.removeAll(raggiungibili);
		//System.out.println(tutti.size());
		
		double dist = Double.MAX_VALUE;
		for(Airport aer : tutti){
			double distan = LatLngTool.distance(a.getCoords(), aer.getCoords(), LengthUnit.KILOMETER);
			if(distan < dist){
				dist=distan;
				piuVicino = aer;
			}
		}
		//System.out.println("Meta: "+min);
		return connesso;
		
		
	}
	public Airport getPiuVicino(){
		return piuVicino;
	}
	
	
	public List<AeroportoConPasseggeri> avviaSimulazione(int k){
		
		Simulazione sim = new Simulazione(grafo, mappaAeroporti);
		sim.inserisciPasseggeri(k);
		List<AeroportoConPasseggeri> ris = sim.run();
		return ris;
		
		
		
		
	}

}
