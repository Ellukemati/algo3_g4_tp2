package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InicializadorTablero {
    private final List<Hexagono> hexagonos;
    private final List<Vertice> vertices;
    private final List<Arista> aristas;

    public InicializadorTablero(List<Hexagono> hexagonos, List<Vertice> vertices, List<Arista> aristas) {
        this.hexagonos = hexagonos;
        this.vertices = vertices;
        this.aristas = aristas;
    }

    public int inicializar() {
        List<Integer> numeros = crearNumerosAleatorios();
        List<Recurso> recursos = crearRecursos();

        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Hexagono(recursos.get(i), numeros.get(i)));
        }
        hexagonos.add(new Hexagono(null, 0)); // Desierto
        Collections.shuffle(hexagonos);

        Random random = new Random();
        int ladronIndex = random.nextInt(19);
        hexagonos.get(ladronIndex).ladronOcupar();

        crearVertices();
        crearAristas();

        conectarVertices();
        conectarVerticesConHexagonos();
        conectarAristas();
        conectarAristasConVertices();
        asignarPuertos();

        return ladronIndex;
    }

    // --- MÉTODOS DE CREACIÓN BÁSICA ---

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

    private List<Integer> crearNumerosAleatorios() {
        List<Integer> numeros = new ArrayList<>();
        Random random = new Random();
        int[] numerosPermitidos = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12};
        for (int num : numerosPermitidos) numeros.add(num);
        while (numeros.size() < 18) {
            numeros.add(numerosPermitidos[random.nextInt(numerosPermitidos.length)]);
        }
        Collections.shuffle(numeros);
        return numeros;
    }

    private List<Recurso> crearRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            recursos.add(Recurso.MADERA); recursos.add(Recurso.LADRILLO);
            recursos.add(Recurso.LANA); recursos.add(Recurso.GRANO);
            recursos.add(Recurso.MINERAL);
        }
        recursos.add(Recurso.MADERA); recursos.add(Recurso.GRANO); recursos.add(Recurso.LANA);
        return recursos;
    }

    // --- LÓGICA DE CONEXIÓN DE VÉRTICES ---

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

    private void conectarAnilloVerticesConAnterior(int inicio, int fin, int diferenciaInicial, int contadorReset) {
        conectarAnilloVertices(inicio, fin);
        int diferencia = diferenciaInicial;
        int contador = 0;
        for (int i = inicio; i < fin; i++) {
            if (contador == contadorReset) {
                diferencia -= 2;
                contador = 0;
            } else {
                conectarVerticesBidireccional(i, i - diferencia);
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

    // --- LÓGICA DE CONEXIÓN DE HEXÁGONOS ---

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
        int hexagonoActual = 12, hexagonoPrimerAnillo = 11, contador = 2;
        for (int i = 30; i < 48; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoPrimerAnillo));
            if (contador == 1 && hexagonoPrimerAnillo % 2 != 0) {
                hexagonoActual++;
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            } else {
                hexagonoPrimerAnillo++;
                if (hexagonoPrimerAnillo == 12) hexagonoPrimerAnillo = 0;
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoPrimerAnillo));
                contador = 0;
            }
            contador++;
        }
    }

    private void conectarTercerAnilloHexagonos() {
        int hexagonoSegundoAnillo = 12, hexagonoActual = 18;
        for (int i = 48; i < 54; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
            hexagonoSegundoAnillo++;
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
        }
    }

    // --- LÓGICA DE CONEXIÓN DE ARISTAS ---

    private void conectarAristas() {
        conectarAristasAnillo(0, 29);
        conectarAristasAnillo(42, 59);
        conectarAristasAnillo(66, 71);
        conectarAristasAnillosEntreSi(0, 29, 42, 59, 30, 41, new int[]{3, 2}, new int[]{1, 2});
        conectarAristasAnillosEntreSi(42, 59, 66, 71, 60, 65, new int[]{3, 1}, new int[]{3, 1});
    }

    private void conectarAristasAnillo(int inicio, int fin) {
        int cantidad = fin - inicio + 1;
        for (int i = inicio; i <= fin; i++) {
            int siguiente = inicio + ((i - inicio + 1) % cantidad);
            int anterior = inicio + ((i - inicio - 1 + cantidad) % cantidad);
            conectarAristasBidireccional(i, anterior);
            conectarAristasBidireccional(i, siguiente);
        }
    }

    private void conectarAristasAnillosEntreSi(int inicioAnilloA, int finAnilloA, int inicioAnilloB, int finAnilloB,
                                               int inicioConexiones, int finConexiones, int[] incrementosA, int[] incrementosB) {
        int indiceAnilloA = inicioAnilloA;
        int indiceAnilloB = inicioAnilloB;
        int cantidadConexiones = finConexiones - inicioConexiones + 1;

        for (int i = 0; i < cantidadConexiones; i++) {
            int aristaConexion = inicioConexiones + i;
            conectarAristasBidireccional(aristaConexion, indiceAnilloA);
            int anteriorA = (indiceAnilloA - 1 < inicioAnilloA) ? finAnilloA : indiceAnilloA - 1;
            conectarAristasBidireccional(aristaConexion, anteriorA);
            conectarAristasBidireccional(aristaConexion, indiceAnilloB);
            int anteriorB = (indiceAnilloB - 1 < inicioAnilloB) ? finAnilloB : indiceAnilloB - 1;
            conectarAristasBidireccional(aristaConexion, anteriorB);

            int incrementoIndex = i % 2;
            indiceAnilloA += incrementosA[incrementoIndex];
            indiceAnilloB += incrementosB[incrementoIndex];
            if (indiceAnilloA > finAnilloA) indiceAnilloA = inicioAnilloA + (indiceAnilloA - finAnilloA - 1);
            if (indiceAnilloB > finAnilloB) indiceAnilloB = inicioAnilloB + (indiceAnilloB - finAnilloB - 1);
        }
    }

    private void conectarAristasBidireccional(int id1, int id2) {
        aristas.get(id1).agregarAristaAdyacente(aristas.get(id2));
        aristas.get(id2).agregarAristaAdyacente(aristas.get(id1));
    }


    private void conectarAristasConVertices() {
        conectarAristasCirculares(0, 29, 0);
        conectarRayosExternosLogico();         // Tu lógica corregida
        conectarAristasCirculares(30, 47, 42);
        conectarRayosInternosManual();         // Tu lógica corregida
        conectarAristasCirculares(48, 53, 66);
    }

    private void conectarRayosExternosLogico() {
        int idArista = 30;
        int vExterno = 0;
        int vMedio = 30;
        for (int i = 0; i < 12; i++) {
            linkearAristaYVertices(aristas.get(idArista), vertices.get(vExterno), vertices.get(vMedio));
            idArista++;
            if (i % 2 == 0) { // Paso PAR
                vExterno += 3; vMedio += 1;
            } else { // Paso IMPAR
                vExterno += 2; vMedio += 2;
            }
        }
    }

    private void conectarRayosInternosManual() {
        int idArista = 60;
        int vCentral = 48;
        int vMedio = 32;
        for (int i = 0; i < 6; i++) {
            linkearAristaYVertices(aristas.get(idArista), vertices.get(vCentral), vertices.get(vMedio));
            idArista++; vCentral++; vMedio += 3;
        }
    }

    private void conectarAristasCirculares(int inicioVertice, int finVertice, int inicioArista) {
        int cantidad = finVertice - inicioVertice + 1;
        int idArista = inicioArista;
        for (int i = inicioVertice; i <= finVertice; i++) {
            int siguiente = inicioVertice + ((i - inicioVertice + 1) % cantidad);
            linkearAristaYVertices(aristas.get(idArista), vertices.get(i), vertices.get(siguiente));
            idArista++;
        }
    }

    private void linkearAristaYVertices(Arista arista, Vertice v1, Vertice v2) {
        arista.agregarVertice(v1); arista.agregarVertice(v2);
        v1.agregarArista(arista); v2.agregarArista(arista);
    }

    // --- PUERTOS ---

    private void asignarPuertos() {
        List<Puerto> puertos = crearPuertosAleatorios();
        int[][] ubicaciones = {{1, 2}, {4, 5}, {8, 9}, {11, 12}, {14, 15}, {18, 19}, {21, 22}, {24, 25}, {28, 29}};
        for (int i = 0; i < ubicaciones.length; i++) {
            Puerto puerto = puertos.get(i);
            vertices.get(ubicaciones[i][0]).asignarPuerto(puerto);
            vertices.get(ubicaciones[i][1]).asignarPuerto(puerto);
        }
    }

    private List<Puerto> crearPuertosAleatorios() {
        List<Puerto> puertos = new ArrayList<>();
        for (Recurso r : Recurso.values()) puertos.add(new PuertoEspecifico(r));
        for (int i = 0; i < 4; i++) puertos.add(new PuertoGenerico());
        Collections.shuffle(puertos);
        return puertos;
    }
}