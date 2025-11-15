package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.Dado;

public class DadoTest {

    @Test
    public void test01DadoGeneraNumeroEntre2Y12() {
        // ARRANGE
        Dado dado = new Dado();

        // ACT
        int resultado = dado.tirar();

        // ASSERT
        assertTrue(resultado >= 2 && resultado <= 12);
    }

    @Test
    public void test02DadoGeneraNumerosDentroDelRangoEnMultiplesTiradas() {
        // ARRANGE
        Dado dado = new Dado();

        // ACT & ASSERT
        for (int i = 0; i < 100; i++) {
            int resultado = dado.tirar();
            assertTrue(resultado >= 2 && resultado <= 12);
        }
    }

}
