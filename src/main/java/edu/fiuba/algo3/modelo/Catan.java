package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catan {
    private final Tablero tablero;
    private final List<Jugador>  jugadores;
    private final Dado dado;
    private final GranCaballeria granCaballeria;
    private final GranRutaComercial granRutaComercial;
    private final AdministradorDeTurnos administrador;

    public Catan(List<Jugador> jugadores) {
        this.tablero = new Tablero();
        this.jugadores = jugadores;
        this.dado = new Dado();
        this.granCaballeria = new GranCaballeria();
        this.granRutaComercial = new GranRutaComercial();
        this.administrador = new AdministradorDeTurnos(jugadores);
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void jugarTurno(Jugador jugadorActual) {
        int numeroDado = dado.tirar();
        if (numeroDado == 7) {
            //luego cambiar posicion ladron por el input que decida el jugador
            int posicionLadron = 10;
            Hexagono hexagonoRobar = jugadorActual.moverLadron(tablero, posicionLadron);
            for (Jugador jugador : jugadores) {
                if (jugador != jugadorActual) {
                    // considerar crear un recurso nulo asi no hacemos verificaciones
                    Recurso recursoRobado = jugador.serRobadoPorLadron(hexagonoRobar);
                    if (recursoRobado != null) {
                        jugadorActual.agregarRecurso(recursoRobado, 1);
                    }
                }
            }
        } else {
            for (Jugador jugador : jugadores) {
                jugador.recibirLanzamientoDeDados(numeroDado);
            }
        }
    }
    
    public void jugadorColocarCarretera(Jugador jugador, Arista carretera) throws ConstruccionInvalidaException {
    	jugador.agregarCarretera(carretera);
        granRutaComercial.actualizar(jugador);
    }
    
    public void jugadorUsarCartaDeDesarrollo(Jugador jugador, CartaDesarollo carta) {
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores);
        granCaballeria.actualizar(jugador);
    }
    
    public Boolean verificarSiGanó(Jugador jugador) {
        int puntosVisibles = jugador.obtenerPuntage();
        int puntosDeVictoria = jugador.obtenerPuntosVictoriaOcultos();
        
        if (puntosVisibles + puntosDeVictoria >= 10) {
            return true;
       
        }else {
        	return false;
        }
    }
    
    public Jugador obtenerJugadorActual() {
		return administrador.obtenerJugadorActual();
	}
    
    public void pasarTurno() {
		administrador.siguienteTurno();
	}
}
