package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {
    private final List<Hexagono> hexagonos;
    private final List<Vertice> vertices;
    private final List<Arista> aristas;
    private int ladron;

    public Tablero() {
        this.hexagonos = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();
        inicializarTablero();
    }

    private void inicializarTablero() {
        List<Integer> numeros = crearNumerosAleatorios();
        List<Recurso> recursos = crearRecursos();

        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Hexagono(recursos.get(i), numeros.get(i)));
        }
        hexagonos.add(new Hexagono(null, 0));
        Collections.shuffle(hexagonos);
        Random random = new Random();
        this.ladron = random.nextInt(19);
        hexagonos.get(ladron).ladronOcupar();

        crearVertices();
        crearAristas();

        conectarVertices();
        conectarVerticesConHexagonos();
        conectarAristas();
    }

    private void crearVertices() {
        for (int i = 0; i < 54; i++) {
            vertices.add(new Vertice(i));
        }
    }

    private void crearAristas() {
        for (int i = 0; i < 72; i++) {
            aristas.add(new Arista(i));
        }
    }

    private void conectarVertices() {
        final int VERTICES_PRIMER_ANILLO = 30;
        final int VERTICES_SEGUNDO_ANILLO = 48;
        final int VERTICES_TERCER_ANILLO = 54;

        conectarAnilloVertices(0, VERTICES_PRIMER_ANILLO);
        conectarAnilloVerticesConAnterior(30, VERTICES_SEGUNDO_ANILLO, 30, 3);
        conectarAnilloVerticesConAnterior(48, VERTICES_TERCER_ANILLO, 16, 0);
    }

    private void conectarAnilloVertices(int inicio, int fin) {
        int cantidad = fin - inicio;
        for (int i = inicio; i < fin; i++) {
            int anterior = inicio + ((i - inicio - 1 + cantidad) % cantidad);
            int siguiente = inicio + ((i - inicio + 1) % cantidad);

            conectarVerticesBidireccional(i, anterior);
            conectarVerticesBidireccional(i, siguiente);
        }
    }

    private void conectarAnilloVerticesConAnterior(int inicio, int fin, int diferenciaInicial,
                                                   int contadorReset) {
        conectarAnilloVertices(inicio, fin);

        int diferencia = diferenciaInicial;
        int contador = 0;

        for (int i = inicio; i < fin; i++) {
            if (contador == contadorReset) {
                diferencia -= 2;
                contador = 0;
            } else {
                int verticeAnterior = i - diferencia;
                conectarVerticesBidireccional(i, verticeAnterior);
                contador++;
            }
        }
    }

    private void conectarVerticesBidireccional(int id1, int id2) {
        if (!(vertices.get(id1).obtenerAdyacentes()).contains(vertices.get(id2))) {
            vertices.get(id1).agregarVerticeAdyacente(vertices.get(id2));
        }
        if (!(vertices.get(id2).obtenerAdyacentes()).contains(vertices.get(id1))) {
            vertices.get(id2).agregarVerticeAdyacente(vertices.get(id1));
        }
    }

    private void conectarVerticesConHexagonos() {
        conectarPrimerAnilloHexagonos();
        conectarSegundoAnilloHexagonos();
        conectarTercerAnilloHexagonos();
    }

    private void conectarPrimerAnilloHexagonos() {
        int hexagonoActual = 0, contador = 0;
        vertices.get(0).agregarHexagono(hexagonos.get(11));

        for (int i = 0; i < 30; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            boolean cambiarHexagono = (hexagonoActual % 2 == 0 && contador == 3) ||
                    (hexagonoActual % 2 != 0 && contador == 2);

            if (cambiarHexagono) {
                hexagonoActual++;
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
                contador = 0;
            }
            contador++;
        }
    }

    private void conectarSegundoAnilloHexagonos() {
        int hexagonoActual = 12;
        int hexagonoPrimerAnillo = 11;
        int contador = 2;

        for (int i = 30; i < 48; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoPrimerAnillo));

            if (contador == 1 && hexagonoPrimerAnillo % 2 != 0) {
                hexagonoActual++;
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            } else {
                hexagonoPrimerAnillo++;
                if (hexagonoPrimerAnillo == 12) {
                    hexagonoPrimerAnillo = 0;
                }
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoPrimerAnillo));
                contador = 0;
            }
            contador++;
        }
    }

    private void conectarTercerAnilloHexagonos() {
        int hexagonoSegundoAnillo = 12;
        int hexagonoActual = 18;

        for (int i = 48; i < 54; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
            hexagonoSegundoAnillo++;
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
        }
    }

    private void conectarAristas() {
        final int PRIMER_ANILLO_INICIO = 0, PRIMER_ANILLO_FIN = 29;
        final int CONEXION_1_2_INICIO = 30, CONEXION_1_2_FIN = 41;
        final int SEGUNDO_ANILLO_INICIO = 42, SEGUNDO_ANILLO_FIN = 59;
        final int CONEXION_2_3_INICIO = 60, CONEXION_2_3_FIN = 65;
        final int TERCER_ANILLO_INICIO = 66, TERCER_ANILLO_FIN = 71;

        conectarAristasAnillo(PRIMER_ANILLO_INICIO, PRIMER_ANILLO_FIN);
        conectarAristasAnillo(SEGUNDO_ANILLO_INICIO, SEGUNDO_ANILLO_FIN);
        conectarAristasAnillo(TERCER_ANILLO_INICIO, TERCER_ANILLO_FIN);

        conectarAristasAnillosEntreSi(PRIMER_ANILLO_INICIO, PRIMER_ANILLO_FIN,
                SEGUNDO_ANILLO_INICIO, SEGUNDO_ANILLO_FIN,
                CONEXION_1_2_INICIO, CONEXION_1_2_FIN,
                new int[]{3, 2}, new int[]{1, 2});

        conectarAristasAnillosEntreSi(SEGUNDO_ANILLO_INICIO, SEGUNDO_ANILLO_FIN,
                TERCER_ANILLO_INICIO, TERCER_ANILLO_FIN,
                CONEXION_2_3_INICIO, CONEXION_2_3_FIN,
                new int[]{3, 1}, new int[]{3, 1});
    }

    private void conectarAristasAnillo(int inicio, int fin) {
        int cantidad = fin - inicio + 1;
        for (int i = inicio; i <= fin; i++) {
            int anterior = inicio + ((i - inicio - 1 + cantidad) % cantidad);
            int siguiente = inicio + ((i - inicio + 1) % cantidad);

            conectarAristasBidireccional(i, anterior);
            conectarAristasBidireccional(i, siguiente);
        }
    }

    private void conectarAristasAnillosEntreSi(int inicioAnilloA, int finAnilloA,
                                        int inicioAnilloB, int finAnilloB,
                                        int inicioConexiones, int finConexiones,
                                        int[] incrementosA, int[] incrementosB) {

        int indiceAnilloA = inicioAnilloA;
        int indiceAnilloB = inicioAnilloB;
        int cantidadConexiones = finConexiones - inicioConexiones + 1;

        for (int i = 0; i < cantidadConexiones; i++) {
            int aristaConexion = inicioConexiones + i;

            conectarAristasBidireccional(aristaConexion, indiceAnilloA);
            // si es menor al inicio, significa que tenemos que conectar con el ultimo
            int anteriorA = (indiceAnilloA - 1 < inicioAnilloA) ? finAnilloA : indiceAnilloA - 1;
            conectarAristasBidireccional(aristaConexion, anteriorA);

            conectarAristasBidireccional(aristaConexion, indiceAnilloB);
            int anteriorB = (indiceAnilloB - 1 < inicioAnilloB) ? finAnilloB : indiceAnilloB - 1;
            conectarAristasBidireccional(aristaConexion, anteriorB);

            int incrementoIndex = i % 2;
            indiceAnilloA += incrementosA[incrementoIndex];
            indiceAnilloB += incrementosB[incrementoIndex];

            // si nos pasamos, significa que tenemos que sumarle lo que nos pasamos al inicio
            if (indiceAnilloA > finAnilloA) indiceAnilloA = inicioAnilloA + (indiceAnilloA - finAnilloA - 1);
            if (indiceAnilloB > finAnilloB) indiceAnilloB = inicioAnilloB + (indiceAnilloB - finAnilloB - 1);
        }
    }

    private void conectarAristasBidireccional(int id1, int id2) {
        aristas.get(id1).agregarAristaAdyacente(aristas.get(id2));
        aristas.get(id2).agregarAristaAdyacente(aristas.get(id1));
    }

    private List<Integer> crearNumerosAleatorios() {
        List<Integer> numeros = new ArrayList<>();
        Random random = new Random();
        int[] numerosPermitidos = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12};

        for (int num : numerosPermitidos) {
            numeros.add(num);
        }

        while (numeros.size() < 18) {
            int i = random.nextInt(numerosPermitidos.length);
            numeros.add(numerosPermitidos[i]);
        }

        Collections.shuffle(numeros);
        return numeros;
    }

    private List<Recurso> crearRecursos() {
        List<Recurso> recursos = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            recursos.add(Recurso.MADERA);
            recursos.add(Recurso.LADRILLO);
            recursos.add(Recurso.LANA);
            recursos.add(Recurso.GRANO);
            recursos.add(Recurso.MINERAL);
        }
        recursos.add(Recurso.MADERA);
        recursos.add(Recurso.GRANO);
        recursos.add(Recurso.LANA);

        return recursos;
    }

    public Vertice obtenerVertice(int idVertice) {
        return vertices.get(idVertice);
    }

    public Arista obtenerArista(int idAristas) {
        return aristas.get(idAristas);
    }

    public boolean construirPoblado(int idVertice) {
        return vertices.get(idVertice).construirPoblado();
    }

    public Hexagono moverLadron(int nuevaPosicion) {
        hexagonos.get(ladron).ladronDesocupar();
        hexagonos.get(nuevaPosicion).ladronOcupar();
        return hexagonos.get(ladron);
    }
}

