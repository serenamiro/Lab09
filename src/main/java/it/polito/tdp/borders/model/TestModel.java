package it.polito.tdp.borders.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		
		System.out.println("Creo il grafo relativo al 1900");
		model.creaGrafo(1900);
		
		System.out.println("Vertici: "+model.verNumber());
		System.out.println("Archi: "+model.edgeNumber());
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
		Country c = model.getIdMap().get(390);
		List<Country> vicini = model.getNodiRaggiungibiliIterativamente(c);
		System.out.println(vicini.toString());
		
	}

}
