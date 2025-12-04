package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.List;

public class PuntoDeVictoria extends CartaDesarolloGeneral {
    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores, ParametrosCarta parametrosCarta) {

    }

    @Override
    public void mostrar(CartaDesarolloRenderizador renderizador) {
        renderizador.puntoDeVictoriaRenderizar(this);
    }

}
