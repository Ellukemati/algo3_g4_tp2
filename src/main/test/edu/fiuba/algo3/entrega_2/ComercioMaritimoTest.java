package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.Vertice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.fiuba.algo3.modelo.*;
import java.util.Map;

public class ComercioMaritimoTest {

    @Test
    public void test01JugadorSinPuertosComerciaConBancaAl4Por1() {
        // ARRANGE
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 4);

        // ACT
        jugador.comerciarConBanca(Map.of(Recurso.MADERA, 4), Map.of(Recurso.LADRILLO, 1));

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos()); // Gastó 4, recibió 1
    }

    @Test
    public void test02JugadorConPuertoGenericoComerciaAl3Por1() {
        // ARRANGE
        Jugador jugador = new Jugador();
        Tablero tableroMock = mock(Tablero.class);
        Vertice verticeConPuerto = new Vertice(1);
        verticeConPuerto.asignarPuerto(new PuertoGenerico());

        when(tableroMock.obtenerVertice(anyInt())).thenReturn(verticeConPuerto);
        when(tableroMock.construirPoblado(anyInt())).thenReturn(true);

        jugador.construirPoblado(tableroMock, 1);
        jugador.agregarRecurso(Recurso.MADERA, 3);

        // ACT
        jugador.comerciarConBanca(Map.of(Recurso.MADERA, 3), Map.of(Recurso.LADRILLO, 1));

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos()); // Gastó 3, recibió 1
    }

    @Test
    public void test03JugadorConPuertoEspecificoComerciaEseRecursoAl2Por1() {
        // ARRANGE
        Jugador jugador = new Jugador();
        Tablero tableroMock = mock(Tablero.class);
        Vertice verticeConPuerto = new Vertice(10);
        verticeConPuerto.asignarPuerto(new PuertoEspecifico(Recurso.MADERA));

        when(tableroMock.obtenerVertice(anyInt())).thenReturn(verticeConPuerto);
        when(tableroMock.construirPoblado(anyInt())).thenReturn(true);

        jugador.construirPoblado(tableroMock, 10);
        jugador.agregarRecurso(Recurso.MADERA, 2);

        // ACT
        jugador.comerciarConBanca(Map.of(Recurso.MADERA, 2), Map.of(Recurso.LANA, 1));

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos()); // Gastó 2, recibió 1
    }

    @Test
    public void test04JugadorConPuertoEspecificoPagaEstandarPorOtrosRecursos() {
        // ARRANGE
        Jugador jugador = new Jugador();
        Tablero tableroMock = mock(Tablero.class);
        Vertice verticeConPuerto = new Vertice(5);
        verticeConPuerto.asignarPuerto(new PuertoEspecifico(Recurso.MADERA));

        when(tableroMock.obtenerVertice(anyInt())).thenReturn(verticeConPuerto);
        when(tableroMock.construirPoblado(anyInt())).thenReturn(true);

        jugador.construirPoblado(tableroMock, 5);
        jugador.agregarRecurso(Recurso.LANA, 3);

        // ACT
        jugador.comerciarConBanca(Map.of(Recurso.LANA, 3), Map.of(Recurso.MINERAL, 1));

        // ASSERT
        // Como su puerto específico es de Madera tiene coste genérico de 4 para intercambiar Lana, entonces no hay intercambio
        assertEquals(3, jugador.cantidadTotalDeRecursos());
    }
}