package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT CCode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("Ccode"))) {
					Country c = new Country(rs.getInt("CCode"), rs.getString("StateNme"), rs.getString("StateAbb"));
					idMap.put(c.getCod(), c);
				}
			}
			
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> idMap) {
		List<Border> result = new ArrayList<Border>();
		
		String sql = "SELECT state1no, state2no, year " + 
				     "FROM contiguity " + 
				     "WHERE year<=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				
				if(c1!=null && c2!=null) {
					result.add(new Border(c1, c2, rs.getInt("year")));
				} else {
					System.out.println("Errore nel caricamento.");
				}
						
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	public List<Country> getVicini(int anno, Country c, Map<Integer, Country> idMap){
		List<Country> vicini = new ArrayList<Country>();
		
		String sql = "SELECT state2no " + 
				"FROM contiguity " + 
				"WHERE YEAR<=? AND state1no=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, c.getCod());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country vic = idMap.get(rs.getInt("state2no"));
				vicini.add(vic);
			}
			
			conn.close();
			return vicini;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}
