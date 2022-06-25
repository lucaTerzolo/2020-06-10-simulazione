package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import it.polito.tdp.imdb.model.Evento.EventType;


public class Simulatore {
	//dati ingresso
	private int n; //numero giorni
	private String genere;
	
	
	//dati uscita
	private List<Actor> intervistati;
	private int nPause;
	
	//situazione mondo
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private List<Actor> attori;
	
	//coda eventi
	private Queue<Evento> coda;


	public Simulatore() {
	this.nPause=0;
	}
	
	public void init(int n, String s, Graph<Actor,DefaultWeightedEdge> grafo) {
		this.n=n;
		this.genere=s;
		this.grafo=grafo;
		this.intervistati=new ArrayList();
		coda=new PriorityQueue<>();
		attori=new ArrayList<>(grafo.vertexSet());
		
		Actor a=this.getAttoreCasuale(attori);
		this.intervistati.add(a);
		this.attori.remove(a);
		if(a.gender.equals("M")) {
			coda.add(new Evento(EventType.INTERVISTA_M,1,a));
		}else {
			coda.add(new Evento(EventType.INTERVISTA_F,1,a));
		}
	}
	
	public List<Actor> run() {
		for(int i=2;i<=n;i++) {
			double caso=Math.random();
			
			if(i>=3 && this.intervistati.get(intervistati.size()-1).getGender().equals(intervistati.get(intervistati.size()-2).getGender())) {
				if(caso<0.9) {
					coda.add(new Evento(EventType.PAUSA,i,null));
					this.nPause++;
					continue;
				}
			}
			
			if(caso<0.6) {
				//Scelta casuale
				Actor a=this.getAttoreCasuale(attori);
				if(a.gender.equals("M")) {
					coda.add(new Evento(EventType.INTERVISTA_M,i,a));
				}else {
					coda.add(new Evento(EventType.INTERVISTA_F,i,a));
				}
				this.intervistati.add(a);
				this.attori.remove(a);
				
			}else {
				//Scelta ponderata
				if(this.getAttoreVicino(attori)!=null) {
					Actor a=this.getAttoreVicino(attori);
					if(a.gender.equals("M")) {
						coda.add(new Evento(EventType.INTERVISTA_M,i,a));
					}else {
						coda.add(new Evento(EventType.INTERVISTA_F,i,a));
					}
					this.intervistati.add(a);
					this.attori.remove(a);
				}else {
					//Scelta casuale
					Actor a=this.getAttoreCasuale(attori);
					if(a.gender.equals("M")) {
						coda.add(new Evento(EventType.INTERVISTA_M,i,a));
					}else {
						coda.add(new Evento(EventType.INTERVISTA_F,i,a));
					}
					this.intervistati.add(a);
					this.attori.remove(a);
				}
			}
		}
		return this.intervistati;
		
	}
	
	private Actor getAttoreCasuale(List<Actor> attori) {
		int caso=(int)(Math.random()*attori.size()+1);
		return attori.get(caso);
	}
	
	private Actor getAttoreVicino(List<Actor> attori) {
		Actor precedente=this.intervistati.get(intervistati.size()-1);
		int gradoMax=0;
		Actor res=null;
		for(Actor a:Graphs.neighborListOf(this.grafo, precedente)) {
			if(gradoMax>this.grafo.degreeOf(a) && !intervistati.contains(a)) {
				gradoMax=this.grafo.degreeOf(a);
				res=a;
			}
		}
		return res;
	}

	public List<Actor> getIntervistati() {
		return intervistati;
	}

	public int getnPause() {
		return nPause;
	}
	
	
}