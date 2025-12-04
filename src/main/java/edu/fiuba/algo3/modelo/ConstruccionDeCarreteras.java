package edu.fiuba.algo3.modelo;

import java.util.List;

public class ConstruccionDeCarreteras extends CartaDesarolloGeneral {

    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores) {
        int idArista1 = 0;
        int idArista2 = 1;

        jugador.construirCarretera(tablero, idArista1, true);
        jugador.construirCarretera(tablero, idArista2, true);
    }
}