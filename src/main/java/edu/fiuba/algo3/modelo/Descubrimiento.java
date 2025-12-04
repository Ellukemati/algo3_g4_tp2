package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Descubrimiento extends CartaDesarolloGeneral {
    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores, ParametrosCarta parametrosCarta) {

        List<Recurso> recursos = new ArrayList<>(Arrays.asList(parametrosCarta.getRecurso1(),
                parametrosCarta.getRecurso2()));
        for (int i = 0; i < recursos.size(); i++) {
            jugador.agregarRecurso(recursos.get(i), 1);
        }
    }

    @Override
    public void mostrar(CartaDesarolloRenderizador renderizador) {
        renderizador.descubrimientoRenderizar(this);
    }
}
