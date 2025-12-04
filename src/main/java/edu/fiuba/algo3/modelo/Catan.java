package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catan implements Observable {
    private final Tablero tablero;
    private final List<Jugador> jugadores;
    private final Dado dado;

    private final GranCaballeria granCaballeria;
    private final GranRutaComercial granRutaComercial;

    private final List<Observador> observadores;
    private final Banca banca;

    private int indiceJugadorActual;
    private int contadorTurnos;
    private boolean juegoIniciado;
    private boolean faseInicial;

    public Catan() {
        this.contadorTurnos = 0;
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.dado = new Dado();

        this.granCaballeria = new GranCaballeria();
        this.granRutaComercial = new GranRutaComercial();

        this.observadores = new ArrayList<>();
        this.indiceJugadorActual = 0;
        this.juegoIniciado = false;
        this.banca = new Banca();
        this.faseInicial = true;
    }


    public void agregarJugador(Jugador jugador) {
        if (!juegoIniciado) {
            jugadores.add(jugador);
        }
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }

    public Jugador obtenerJugadorActual() {
        if (jugadores.isEmpty()) return null;
        return jugadores.get(indiceJugadorActual);
    }


    public void siguienteTurno() {
        contadorTurnos++;

        if (contadorTurnos >= jugadores.size() * 2) {
            faseInicial = false;
        }

        obtenerJugadorActual().finalizarTurno();

        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();

        notificarObservadores();
    }


    public void jugadorActualComprarCartaDeDesarrollo() {
        Jugador jugadorActual = obtenerJugadorActual();
        jugadorActual.comprarCartaDeDesarollo(banca);
    }

    public int obtenerTurno() {
        return contadorTurnos;

    }

    public boolean esFaseInicial() {
        return faseInicial;
    }

    public int lanzarDado() {
        // En fase inicial no se tiran dados
        if (faseInicial) {
            return 0;
        }

        int resultado = dado.tirar();
        jugarTurno(resultado);
        return resultado;
    }

    private void jugarTurno(int numeroDado) {
        Jugador jugadorActual = obtenerJugadorActual();

        if (numeroDado == 7) {
            int posicionLadron = 10; // TODO: Esto debería venir de la vista/input
            Hexagono hexagonoRobar = jugadorActual.moverLadron(tablero, posicionLadron);

            for (Jugador j : jugadores) {
                if (j != jugadorActual) {
                    Recurso recursoRobado = j.serRobadoPorLadron(hexagonoRobar);
                    if (recursoRobado != null) {
                        jugadorActual.agregarRecurso(recursoRobado, 1);
                    }
                }
            }
        } else {
            for (Jugador j : jugadores) {
                j.recibirLanzamientoDeDados(numeroDado);
            }
        }
        notificarObservadores();
    }


    public void jugadorColocarCarretera(Jugador jugador, int idArista) {
        boolean construyo = jugador.construirCarretera(tablero, idArista, this.faseInicial);

        if (construyo) {
            granRutaComercial.actualizar(jugador);
        }
    }

    public void jugadorUsarCartaDeDesarrollo(Jugador jugador, CartaDesarollo carta, ParametrosCarta parametrosCarta) {
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores, parametrosCarta);
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

        return (puntosVisibles + puntosDeVictoria) >= 10;
    }

    public Tablero obtenerTablero() {
        return this.tablero;
    }


    @Override
    public void agregarObservador(Observador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }
}