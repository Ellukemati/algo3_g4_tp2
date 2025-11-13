package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Ciudad;
import edu.fiuba.algo3.modelo.Poblado;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CiudadTest {

    @Test
    void TestesUnPobladoMejorable() {
        Poblado poblado1 = new Poblado("rojo");
        Ciudad ciudad1 = new Ciudad("rojo");

        assertTrue(ciudad1.esMejorable(poblado1));
    }

}