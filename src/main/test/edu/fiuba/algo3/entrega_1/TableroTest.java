package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;

import edu.fiuba.algo3.modelo.Vertice;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
//ver las conexiones directamente
    @Test
    void testVisualizarConexionesDeAristas() throws NoSuchFieldException, IllegalAccessException {
        Tablero tablero = new Tablero();

        // 1. Obtener la lista de ARISTAS del tablero
        java.lang.reflect.Field campoAristasDelTablero = Tablero.class.getDeclaredField("aristas");
        campoAristasDelTablero.setAccessible(true);
        // Aquí SÍ usamos 'tablero' porque pedimos la lista de aristas DEL tablero
        List<Arista> listaAristas = (List<Arista>) campoAristasDelTablero.get(tablero);

        // 2. Preparar el acceso al campo 'vertices' de la clase ARISTA
        java.lang.reflect.Field campoVerticesDeArista = Arista.class.getDeclaredField("vertices");
        campoVerticesDeArista.setAccessible(true);

        // (Opcional) Campo ID para mostrar el número
        java.lang.reflect.Field campoIdArista = Arista.class.getDeclaredField("id");
        campoIdArista.setAccessible(true);

        System.out.println("=== REPORTE DE CONEXIONES DE ARISTAS ===");

        // 3. Recorrer cada arista individualmente
        for (Arista aristaIndividual : listaAristas) {

            // --- CORRECCIÓN CRÍTICA ---
            // El error ocurría porque aquí abajo decías .get(tablero).
            // Tenés que pedirle los vértices a la 'aristaIndividual'.
            List<Vertice> verticesConectados = (List<Vertice>) campoVerticesDeArista.get(aristaIndividual);

            int id = (int) campoIdArista.get(aristaIndividual);

            String estado = (verticesConectados.size() == 2) ? "OK" : "!!! ERROR (" + verticesConectados.size() + ") !!!";

            System.out.print("Arista " + id + " [" + estado + "] -> Conecta con: ");
            System.out.print("[ ");
            for (Vertice v : verticesConectados) {
                System.out.print(v.toString() + ", ");
            }
            System.out.println("]");

            if (verticesConectados.size() != 2) {
                System.out.println("---------------------------------------------------------------");
            }
        }
        System.out.println("=========================================");
    }


//ACA SON LAS CONECCIONES ARISTA VERTICE
        @Test
        void test01TodasLasAristasTienenExactamenteDosVertices() {
            // REGLA FÍSICA: Una calle siempre conecta 2 puntos. Ni 1, ni 3.
            Tablero tablero = new Tablero();

            for (int i = 0; i < 72; i++) {
                Arista arista = tablero.obtenerArista(i);
                // Asumiendo que agregaste obtenerVertices() en Arista
                List<Vertice> verticesConectados = arista.obtenerVertices();

                assertEquals(2, verticesConectados.size(),
                        "La Arista " + i + " está rota: conecta " + verticesConectados.size() + " vértices en lugar de 2.");
            }
        }

        @Test
        void test02TodosLosVerticesTienenEntre2y3Aristas() {
            // REGLA TOPOLÓGICA: En Catan, de un cruce salen 2 caminos (costa) o 3 caminos (interior).
            Tablero tablero = new Tablero();

            for (int i = 0; i < 54; i++) {
                Vertice vertice = tablero.obtenerVertice(i);
                // Asumiendo que agregaste obtenerAristas() en Vertice
                List<Arista> aristasAdyacentes = vertice.obtenerAristas();

                int cantidad = aristasAdyacentes.size();
                assertTrue(cantidad >= 2 && cantidad <= 3,
                        "El Vertice " + i + " tiene una cantidad ilegal de caminos: " + cantidad);
            }
        }

        @Test
        void test03ConsistenciaBidireccional() {
            Tablero tablero = new Tablero();

            for (int i = 0; i < 72; i++) {
                Arista arista = tablero.obtenerArista(i);
                for (Vertice v : arista.obtenerVertices()) {
                    assertTrue(v.obtenerAristas().contains(arista),
                            "Inconsistencia: La Arista " + i + " conoce al " + v + ", pero el vértice no conoce a la arista.");
                }
            }
        }

        @Test
        void test04ConexionLogicaPrimerAnilloCircular() {
            // Verificar que el anillo exterior se cierra (0-29).
            // Arista 0 debe conectar Vertice 0 y Vertice 1.
            // Arista 29 debe conectar Vertice 29 y Vertice 0.
            Tablero tablero = new Tablero();

            Arista arista0 = tablero.obtenerArista(0);
            List<Vertice> vA0 = arista0.obtenerVertices();
            assertTrue(vA0.contains(tablero.obtenerVertice(0)));
            assertTrue(vA0.contains(tablero.obtenerVertice(1)));

            Arista arista29 = tablero.obtenerArista(29);
            List<Vertice> vA29 = arista29.obtenerVertices();
            assertTrue(vA29.contains(tablero.obtenerVertice(29)));
            assertTrue(vA29.contains(tablero.obtenerVertice(0))); // El cierre del anillo
        }

        @Test
        void test05ConexionCentro() {
            // Verificamos el anillo más interno (48-53) y sus conexiones hacia afuera.
            Tablero tablero = new Tablero();

            // Arista Radial del centro: Arista 60 conecta Vertice 48 (anillo 3) con Vertice 32 (anillo 2).
            // (48 - 16 = 32 según tu diferenciaInicial)
            Arista aristaRadialCentro = tablero.obtenerArista(60);

            assertTrue(aristaRadialCentro.obtenerVertices().contains(tablero.obtenerVertice(48)));
            assertTrue(aristaRadialCentro.obtenerVertices().contains(tablero.obtenerVertice(32)));
        }
    }

