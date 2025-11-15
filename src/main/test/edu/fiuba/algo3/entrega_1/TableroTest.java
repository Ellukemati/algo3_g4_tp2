package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.*;

public class TableroTest {
    @Test
    public void test01TableroSeInicializaCon19Hexagonos() {
        // ARRANGE & ACT
        Tablero tablero = new Tablero();

        // ASSERT
        Vertice vertice = tablero.obtenerVertice(0,6);
        assertNotNull(vertice);
    }

    @Test
    public void test02ReglaDistanciaImpideColocarPobladosAdyacentes() {
        // ARRANGE
        Tablero tablero = new Tablero();

        // ACT -
        boolean primerPoblado = tablero.construirPoblado(2, 4);
        boolean segundoPobladoAdyacente = tablero.construirPoblado(0, 6);

        // ASSERT
        assertTrue(primerPoblado);
        assertFalse(segundoPobladoAdyacente);
    }


}
