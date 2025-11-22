package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import edu.fiuba.algo3.modelo.*;

public class TableroTest {
    @Test
    public void test01LosVerticesSeCreanCorrectamente() {
        // ARRANGE & ACT
        Tablero tablero = new Tablero();

        // ASSERT
        Vertice vertice = tablero.obtenerVertice(0);
        assertNotNull(vertice);
    }

    @Test
    public void test02ReglaDistanciaImpideColocarPobladosAdyacentes() {
        // ARRANGE
        Tablero tablero = new Tablero();

        // ACT -
        boolean primerPoblado = tablero.construirPoblado(0);
        boolean segundoPobladoAdyacente = tablero.construirPoblado(30);
        boolean tercerPobladoAdyacente = tablero.construirPoblado(29);
        boolean cuartoPobladoAdyacente = tablero.construirPoblado(1);
        boolean PobladoNoAdyacente = tablero.construirPoblado(2);

        // ASSERT
        assertTrue(primerPoblado);
        assertFalse(segundoPobladoAdyacente);
        assertFalse(tercerPobladoAdyacente);
        assertFalse(cuartoPobladoAdyacente);
        assertTrue(PobladoNoAdyacente);
    }

    void test03LosHexagonosSeCreanCorrectamente() {
        Tablero tablero = new Tablero();

        tablero.obtenerVertice(15);

        assert(true);
    }

    @Test
    void test04LasAristasSeCreanCorrectamente() {
        Tablero tablero = new Tablero();

        tablero.obtenerArista(15);

        assert(true);
    }



    @Test
    void testVisualizarConexiones() throws NoSuchFieldException, IllegalAccessException {
        Tablero tablero = new Tablero();

        // 1. Acceder a la lista de vértices del tablero (privada)
        java.lang.reflect.Field campoVertices = Tablero.class.getDeclaredField("vertices");
        campoVertices.setAccessible(true);
        List<Vertice> vertices = (List<Vertice>) campoVertices.get(tablero);

        // 2. Preparar el acceso a la lista de adyacentes de cada vértice (privada)
        java.lang.reflect.Field campoAdyacentes = Vertice.class.getDeclaredField("verticesAdyacentes");
        campoAdyacentes.setAccessible(true);

        System.out.println("=== REPORTE DE CONEXIONES DEL TABLERO ===");

        // 3. Recorrer y printear
        for (Vertice v : vertices) {
            List<Vertice> adyacentes = (List<Vertice>) campoAdyacentes.get(v);

            // Formato de salida: "Vertice 0 se conecta con: [Vertice 1, Vertice 5, Vertice 16]"
            System.out.print(v.toString() + " (Grado " + adyacentes.size() + ") -> Conecta con: ");

            // Imprimir vecinos
            System.out.print("[ ");
            for (Vertice vecino : adyacentes) {
                System.out.print(vecino.toString() + ", ");
            }
            System.out.println("]");
        }

        System.out.println("=========================================");
    }
}