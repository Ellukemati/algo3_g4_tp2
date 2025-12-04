package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.List;

public class ConstruccionDeCarreteras extends CartaDesarolloGeneral {

    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores) {
        // debe recibie los valores del id por imput
        int idArista1 = 0;
        int idArista2 = 1;
        jugador.construirCarretera(tablero, idArista1);
        jugador.construirCarretera(tablero, idArista2);
    }

    @Override
    public void mostrar(CartaDesarolloRenderizador renderizador) {
        renderizador.contruccionDeCarreterasRenderizar(this);
    }
}
