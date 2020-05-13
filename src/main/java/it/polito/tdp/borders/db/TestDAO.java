package it.polito.tdp.borders.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		Map<Integer, Country> idMap = new HashMap<Integer, Country>();

		//System.out.println("Lista di tutte le nazioni:");
		// List<Country> countries = dao.loadAllCountries();
		dao.loadAllCountries(idMap);
		List<Border> lista = dao.getCountryPairs(1900, idMap);
		System.out.println(lista.toString());
	}
}
