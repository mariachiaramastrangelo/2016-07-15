package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Rotte;
import it.polito.tdp.flight.model.Route;
import javafx.scene.paint.RadialGradient;

public class FlightDAO {

	public List<Airline> getAllAirlines() {
		String sql = "SELECT * FROM airline";
		List<Airline> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Airline(res.getInt("Airline_ID"), res.getString("Name"), res.getString("Alias"),
						res.getString("IATA"), res.getString("ICAO"), res.getString("Callsign"),
						res.getString("Country"), res.getString("Active")));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Route> getAllRoutes() {
		String sql = "SELECT * FROM route";
		List<Route> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Route(res.getString("Airline"), res.getInt("Airline_ID"), res.getString("Source_airport"),
						res.getInt("Source_airport_ID"), res.getString("Destination_airport"),
						res.getInt("Destination_airport_ID"), res.getString("Codeshare"), res.getInt("Stops"),
						res.getString("Equipment")));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void getAllAirports(Map<Integer, Airport> aIdMap) {
		String sql = "SELECT * FROM airport";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				
				if(!aIdMap.containsKey(res.getInt("Airport_ID"))) {
					Airport a= new Airport(res.getInt("Airport_ID"), res.getString("name"), res.getString("city"),
							res.getString("country"), res.getString("IATA_FAA"), res.getString("ICAO"),
							res.getDouble("Latitude"), res.getDouble("Longitude"), res.getFloat("timezone"),
							res.getString("dst"), res.getString("tz"));
					aIdMap.put(res.getInt("Airport_ID"), a);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public List<Rotte> getRotte(){
		String sql="SELECT a1.`Airport_ID` as id1,  a1.`Latitude` as lat1, a1.`Longitude` as lon1, a2.`Airport_ID` as id2, a2.`Latitude` as lat2, a2.`Longitude` as lon2 " + 
				"FROM route r, airport a1, airport a2 " + 
				"WHERE r.`Source_airport_ID`=a1.`Airport_ID` && r.`Destination_airport_ID`=a2.`Airport_ID`  " + 
				"GROUP BY a1.`Airport_ID`, a2.`Airport_ID` ";
		List<Rotte> result= new ArrayList<>();
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			while (rs.next()) {
				double distanza= calcolaDistanza(rs.getDouble("lat1"), rs.getDouble("lon1"), rs.getDouble("lat2"), rs.getDouble("lon2"));
				Rotte rotta= new Rotte(rs.getInt("id1"), rs.getInt("id2"), distanza);
				result.add(rotta);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
		LatLng a1= new LatLng(lat1, lon1);
		LatLng a2= new LatLng(lat2, lon2);
		return LatLngTool.distance(a1, a2, LengthUnit.KILOMETER);
	}

}
