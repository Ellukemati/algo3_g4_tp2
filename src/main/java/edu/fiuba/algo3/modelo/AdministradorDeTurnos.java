package edu.fiuba.algo3.modelo;

import java.util.List;

public class AdministradorDeTurnos {
	private List<Jugador> jugadores;
	private int indiceDeJugador;
	
	public AdministradorDeTurnos(List<Jugador> grupoJugadores) {
		this.jugadores = grupoJugadores;
		this.indiceDeJugador = 0;
	}
	
	public Jugador obtenerJugadorActual() {
		return jugadores.get(indiceDeJugador);
	}
	
	public void siguienteTurno() {
		indiceDeJugador = (indiceDeJugador + 1) % jugadores.size();
	}

}
