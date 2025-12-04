package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private final List<Hexagono> hexagonos;
    private final List<Vertice> vertices;
    private final List<Arista> aristas;
    private int ladron;

    public Tablero() {
        this.hexagonos = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();

        // Delegamos la inicialización a la nueva clase
        InicializadorTablero inicializador = new InicializadorTablero(hexagonos, vertices, aristas);
        this.ladron = inicializador.inicializar();
    }


    // --- MÉTODOS PÚBLICOS DEL JUEGO ---
    public List<Hexagono> obtenerHexagonos() {
        return this.hexagonos;
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
        this.ladron = nuevaPosicion; // Actualizamos la posición del ladrón
        return hexagonos.get(ladron);
    }
}