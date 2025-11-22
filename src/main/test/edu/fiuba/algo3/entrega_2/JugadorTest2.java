package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Tablero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.Map;

public class JugadorTest2 {

    @Test
    public void test01JugadorIntercambiaRecursosConOtroJugadorExitosamente() {
        // ARRANGE
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        jugador1.agregarRecurso(Recurso.MADERA, 1);
        jugador2.agregarRecurso(Recurso.LADRILLO, 1);

        Map<Recurso, Integer> oferta = Map.of(Recurso.MADERA, 1);
        Map<Recurso, Integer> solicitud = Map.of(Recurso.LADRILLO, 1);

        // ACT
        jugador1.intercambiar(jugador2, oferta, solicitud);

        // ASSERT
        assertEquals(1, jugador1.cantidadTotalDeRecursos());
        assertEquals(1, jugador2.cantidadTotalDeRecursos());

        // Invirtieron su inventario oferta/solicitud = Intercambio exitoso
        assertTrue(jugador1.poseeRecursos(solicitud));
        assertTrue(jugador2.poseeRecursos(oferta));
    }

    @Test
    public void test02IntercambioFallaSiJugadorNoTieneRecursosSuficientes() {
        // ARRANGE
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        jugador2.agregarRecurso(Recurso.LADRILLO, 1);

        Map<Recurso, Integer> oferta = Map.of(Recurso.MADERA, 1);
        Map<Recurso, Integer> solicitud = Map.of(Recurso.LADRILLO, 1);

        // ACT
        jugador1.intercambiar(jugador2, oferta, solicitud);

        // ASSERT
        assertEquals(0, jugador1.cantidadTotalDeRecursos());
        assertEquals(1, jugador2.cantidadTotalDeRecursos());

        // jugador1 no posee los recursos que jugador2 solicita = Intercambio fallido
        assertFalse(jugador1.poseeRecursos(solicitud));
    }

    @Test
    public void test03CreacionCorrectaCarreteras() {
        //ARRANGE
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        //ACT && ASSERT
        assertTrue(jugador1.construirCarretera(tablero, 0));
    }

    @Test
    public void test04NoSePermiteLaCreacionCarreterasNoAdyacentes() {
        //ARRANGE
        Jugador jugador = new Jugador();
        Tablero tablero = new Tablero();

        //ACT
        jugador.construirCarretera(tablero, 0);

        //ASSERT
        assertFalse(jugador.construirCarretera(tablero, 70));
    }

    @Test
    public void test05CreacionCarreterasSobreOtras() {
        //ARRANGE
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        //ACT
        jugador1.construirCarretera(tablero, 0);

        // ASSERT
        assertFalse(jugador1.construirCarretera(tablero, 0));
    }

        @Test
        public void test06CreacionCarreterasAdyacentes() {
            //ARRANGE
            Jugador jugador1 = new Jugador();
            Tablero tablero = new Tablero();

            //ACT
            jugador1.construirCarretera(tablero, 0);

            // ASSERT
            assertTrue(jugador1.construirCarretera(tablero, 1));
        }
    }