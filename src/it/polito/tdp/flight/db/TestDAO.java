package it.polito.tdp.flight.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.flight.model.Airport;

public class TestDAO {
	public static void main(String[] args) {
	FlightDAO dao= new FlightDAO();
	Map<Integer, Airport> mappa = new HashMap<Integer, Airport>();
	dao.getAllAirports(mappa);
	for(Integer i: mappa.keySet()) {
		System.out.println(mappa.get(i)+"\n");
	}
	
	}
	
}
