package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Poblado;
import edu.fiuba.algo3.modelo.PosicionInvalidaException;
import edu.fiuba.algo3.modelo.Tablero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntregaTest {

    @Test
    void noSePuedenColocarDosPobladosAdyacentesPorLaReglaDeLaDistancia() {
        //Arrange
        Poblado poblado1 = new Poblado("rojo");
        Poblado poblado2 = new Poblado("azul");
        Tablero tablero = new Tablero("mapa.csv");

        poblado1.colocarEnTablero(tablero, 2, 8);

        assertThrows(PosicionInvalidaException.class, () -> poblado2.colocarEnTablero(tablero, 4, 6));



    }
}
