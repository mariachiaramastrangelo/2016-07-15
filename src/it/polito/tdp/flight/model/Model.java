package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.connectivity.GabowStrongConnectivityInspector;
import org.jgrapht.generate.GnmRandomBipartiteGraphGenerator;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	FlightDAO dao= new FlightDAO();
	Map<Integer, Airport> aIdMap;
	DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao= new FlightDAO();
		aIdMap= new HashMap<Integer, Airport>();
		dao.getAllAirports(aIdMap);
	}
	
	public void creaGrafo(double distanzaMax) {
		grafo= new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, aIdMap.values());
		for(Rotte r: dao.getRotte()) {
			if(r.getDistanza()< distanzaMax) {
				double tempo= r.getDistanza()/800;
				Graphs.addEdgeWithVertices(grafo, aIdMap.get(r.getaId1()), aIdMap.get(r.getaId2()), tempo);
			}
		}
		
		
	}
	
	public List<Set<Airport>> connetivita(){
		GabowStrongConnectivityInspector connectivity= new GabowStrongConnectivityInspector(grafo);
		return connectivity.stronglyConnectedSets();
	}
	
	public Airport daFiumicino() {
		ConnectivityInspector<Airport, DefaultWeightedEdge> conn= new ConnectivityInspector<>(grafo);
		Set<Airport> connessiAFiumicino= conn.connectedSetOf(aIdMap.get(1555));
		double distanza=0;
		Airport airport=null;
		Graphs.successorListOf(grafo, aIdMap.get(1555));
		for(Rotte r: dao.getRotte()) {
			for(Airport a: Graphs.successorListOf(grafo, aIdMap.get(1555))) {
				if(r.getaId1()==1555 && r.getaId2()==a.getAirportId()) {
					if(r.getDistanza()>distanza) {
						distanza= r.getDistanza();
						airport= a;
					}
				}
			}
		}
			
		
		return airport;
	}
}
