package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private Map<Integer,Actor> attori;
	private Simulatore sim;
	
	public Model() {
		dao=new ImdbDAO();
		attori=new HashMap<>();
		dao.listAllActors(attori);
		sim=new Simulatore();
	}
	
	public String creaGrafo(String genre){
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo,dao.getAllActorByGenre(genre));
		for(Adiacenza a:dao.getAllAdiecenze(genre, attori))
			Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		return "Grafo creato:\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "
			+this.grafo.edgeSet().size();
	}
	
	public List<Actor> getAllVertici(){
		List<Actor> a=new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(a);
		return a;
	}
	
	public List<Actor> getSimile(Actor a){
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
		List<Actor> res=new ArrayList(ci.connectedSetOf(a));
		Collections.sort(res);
		return res;
	}
	
	public List<Actor> simula(int n,String genre,List<Actor> intervistati) {
		sim.init(n, genre, this.grafo);
		intervistati=sim.run();
		
		// "Simulazione durata "+n+"giorni di cui "+sim.getnPause()+" di pausa\n";
		return sim.run();
	}
	
	public List<String> getAllGenre(){
		return dao.getAllGenre();
	}

}
