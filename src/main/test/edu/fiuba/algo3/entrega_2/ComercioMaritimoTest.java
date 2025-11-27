package edu.fiuba.algo3.entrega_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.fiuba.algo3.modelo.*;

public class ComercioMaritimoTest {

    @Test
    public void test01JugadorSinPuertosComerciaConBancaAl4Por1() {
        // ARRANGE
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 4);

        // ACT
        jugador.comerciarConBanca(Recurso.MADERA, Recurso.LADRILLO);

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos());
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
        jugador.comerciarConBanca(Recurso.MADERA, Recurso.LADRILLO);

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos());
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
        jugador.comerciarConBanca(Recurso.MADERA, Recurso.LANA);

        // ASSERT
        assertEquals(1, jugador.cantidadTotalDeRecursos());
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
        jugador.comerciarConBanca(Recurso.LANA, Recurso.MINERAL);

        // ASSERT
        // Como su puerto específico es de Madera, tiene coste genérico de 4 para intercambiar Lana y no hay intercambio
        assertEquals(3, jugador.cantidadTotalDeRecursos());
    }
}
