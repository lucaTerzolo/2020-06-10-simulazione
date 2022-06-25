package it.polito.tdp.imdb.model;

public class Evento implements Comparable<Evento> {

	enum EventType{
		INTERVISTA_M,
		INTERVISTA_F,
		PAUSA
	}
	
	private EventType tipo;
	private int tempo;//in giorni
	private Actor intervistato;
	
	public EventType getTipo() {
		return tipo;
	}
	public int getTempo() {
		return tempo;
	}
	public Actor getIntervistato() {
		return intervistato;
	}
		
	public Evento(EventType tipo, int tempo, Actor intervistato) {
		super();
		this.tipo = tipo;
		this.tempo = tempo;
		this.intervistato = intervistato;
	}
	
	@Override
	public int compareTo(Evento o) {
		return this.tempo-o.getTempo();
	}
	
	
}
