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

    // Gestión de Jugadores

    public void agregarJugador(Jugador jugador) {
        if (!juegoIniciado && jugadores.size() < 4) {
            jugadores.add(jugador);
        }
    }

    public void iniciarJuego() {
        if (jugadores.size() >= 3) {
            this.juegoIniciado = true;
            notificarObservadores();
        }
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }

    public Jugador obtenerJugadorActual() {
        if (jugadores.isEmpty()) return null;
        return jugadores.get(indiceJugadorActual);
    }

    // Lógica de Turnos

    public void siguienteTurno() {
        contadorTurnos++;
        obtenerJugadorActual().finalizarTurno();

        // Avanza al siguiente (circular)
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

    public int lanzarDado() {
        int resultado = dado.tirar();

        if (resultado != 7) {
            // Si NO es 7, repartimos recursos automáticamente
            repartirRecursos(resultado);
        } else {
            // Si ES 7, gestionamos el descarte por exceso de cartas
            gestionarDescartePorSiete();
            // NOTA: El movimiento del ladrón NO se hace acá,
            // la Vista debe detectar el 7 y pedirle al usuario que elija hexágono.
        }

        return resultado;
    }

    private void repartirRecursos(int numeroDado) {
        for (Jugador j : jugadores) {
            j.recibirLanzamientoDeDados(numeroDado);
        }
        notificarObservadores();
    }

    private void gestionarDescartePorSiete() {
        for (Jugador j : jugadores) {
            // Cada jugador verifica si tiene > 7 cartas y descarta la mitad
            // (Esto ya lo implementaste en Jugador.recibirLanzamientoDeDados(7))
            j.recibirLanzamientoDeDados(7);
        }
    }

    // --- Acciones del Jugador ---

    // Método separado para cuando la UI mande la coordenada del ladrón
    public void moverLadron(int posicionHexagono, Jugador victima) {
        Jugador jugadorActual = obtenerJugadorActual();
        Hexagono hexagono = jugadorActual.moverLadron(tablero, posicionHexagono);

        // Solo robamos si se eligió una víctima (puede no haber nadie)
        if (victima != null && victima != jugadorActual) {
            Recurso robado = jugadorActual.robarA(victima); // Usar tu método nuevo
            // O la lógica de intercambio forzado que tenías
        }
        notificarObservadores();
    }

    public void jugadorColocarCarretera(Jugador jugador, int idArista) {
        try {
            jugador.construirCarretera(tablero, idArista);
            granRutaComercial.actualizar(jugador);
            notificarObservadores();
        } catch (Exception e) {
            // Manejar error
        }
    }

    public void jugadorUsarCartaDeDesarrollo(Jugador jugador, CartaDesarollo carta, ParametrosCarta parametrosCarta) {
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores, parametrosCarta);
        granCaballeria.actualizar(jugador);
        notificarObservadores();
    }

    public boolean verificarSiGano(Jugador jugador) {
        // Ahora estos métodos existen en Jugador gracias a la corrección
        int puntosVisibles = jugador.obtenerPuntaje();
        int puntosOcultos = jugador.obtenerPuntosVictoriaOcultos();

        return (puntosVisibles + puntosOcultos) >= 10;
    }

    // --- Getters y Observable ---

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