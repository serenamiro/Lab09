package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	private SimpleGraph<Country, DefaultEdge> grafo;
	
	private Map<Country, Country> visita = new HashMap<>();

	public Model() {
		idMap = new HashMap<Integer, Country>();
		dao = new BordersDAO();
		this.dao.loadAllCountries(idMap);
	}
	
	public String creaGrafo(int y) {
		this.grafo = new SimpleGraph(DefaultEdge.class);
		
		// aggiungo i vertici
		for(Country c : idMap.values()) {
			this.grafo.addVertex(c);
		}
		
		// aggiungo gli archi 
		for(Border b : dao.getCountryPairs(y, idMap)) {
			if(this.grafo.containsVertex(b.getC1()) && this.grafo.containsVertex(b.getC2())) {
				DefaultEdge e = this.grafo.getEdge(b.getC1(), b.getC2());
				if(e==null) {
					Graphs.addEdgeWithVertices(this.grafo, b.getC1(), b.getC2());
				}
			}
		}
		
		String s = "";
		for(Country c : this.grafo.vertexSet()) {
			int num = Graphs.neighborListOf(this.grafo, c).size();
			s += c.getNome()+" "+num+" \n";
		}
		return s;
	}
	
	public int verNumber(){
		return this.grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return this.grafo.edgeSet().size();
	}
	
	// SELEZIONO I VICINI TRAMITE UNA QUERY SQL -> funge ma trovo solo i vicini di un nodo
	// non tutti i nodi raggiungibili (vicini dei vicini)
	public List<Country> getVicini(int y, Country c, Map<Integer, Country> idMap){
		return dao.getVicini(y, c, idMap);
	}
	
	// SELEZIONO I VICINI DI UN NODO COME PROPRIETA' DEL GRAFO -> funge ma trovo solo i vicini di un nodo
	// non tutti i nodi raggiungibili (vicini dei vicini)
	public List<Country> getVicini2(Country c){
		List<Country> vicini = Graphs.neighborListOf(this.grafo, c);
		return vicini;
	}

	public Map<Integer, Country> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Integer, Country> idMap) {
		this.idMap = idMap;
	}
	
	public Collection<Country> getCountries(){
		return this.grafo.vertexSet();
	}
	
	// MODO CORRETTO PER TROVARE I VICINI DEI VICINI - VISITA DEL GRAFO IN AMPIEZZA
	public List<Country> getNodiRaggiungibili(Country c){
		List<Country> raggiungibili = new ArrayList<Country>();
		
		BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, c);
		// aggiungo la radice dell'albero di visita
		visita.put(c, null);
		
		while(it.hasNext()) {
			raggiungibili.add(it.next());
		}
		return raggiungibili;
	}
	
	public List<Country> getNodiRaggiungibiliIterativamente(Country c){
		List<Country> daVisitare = new ArrayList<Country>();
		List<Country> visitati = new ArrayList<Country>();
		List<Country> temp;
		List<Country> daRitornare = new ArrayList<Country>();
		
		daVisitare.add(c);
		
		int i = 0;
		while(!daVisitare.isEmpty()) {
			// 1. ESTRARRE UN NODO
			Country cc = daVisitare.get(i);
			
			temp = new ArrayList<Country>(Graphs.neighborListOf(this.grafo, cc));
			
			// 2. SE I VICINI NON APPARTENGONO A VISITATI, ALLORA AGGIUNGERLI A DAVISITARE
			for(Country tempC : temp) {
				if(!visitati.contains(tempC)) {
					// credo che l'eccezione venga scatenata per questa operazione
					daVisitare.add(tempC);
					i++;
				}
			}
			visitati.add(cc);
			daVisitare.remove(cc);
			i--;
		}
		
		for(Country co : visitati) {
			if(!daRitornare.contains(co)) {
				daRitornare.add(co);
			}
		}
		return daRitornare;
	}

}
