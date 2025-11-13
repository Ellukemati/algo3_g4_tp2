package edu.fiuba.algo3.modelo;

import java.util.*;

public class Mapa {
    private Object[][] matriz;
    private int filas;
    private int columnas;

    private static final String TIPO_HEXAGONO = "HEXAGONO";
    private static final String TIPO_VERTICE = "VERTICE";
    private static final String TIPO_ARISTA = "ARISTA";
    private static final String TIPO_VACIO = "VACIO";

    public Mapa(List<Hexagono> hexagonos) {
        this.filas = 21;
        this.columnas = 23;
        this.matriz = new Object[filas][columnas];
        inicializarMapa(hexagonos);
    }

    private void inicializarMapa(List<Hexagono> hexagonos) {
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                matriz[i][j] = TIPO_VACIO;
            }
        }
        posicionarComponentes(hexagonos);
    }

    private void posicionarComponentes(List<Hexagono> hexagonos) {
        int[][] coordenadasHexagonos = {
                {3, 6}, {3, 10}, {3, 14},
                {7, 4}, {7, 8}, {7, 12}, {7, 16},
                {11, 2}, {11, 6}, {11, 10}, {11, 14}, {11, 18},
                {14, 4}, {14, 8}, {14, 12}, {14, 16},
                {18, 6}, {18, 10}, {18, 14}
        };

        for (int i = 0; i < 19; i++) {
            int fila = coordenadasHexagonos[i][0];
            int columna = coordenadasHexagonos[i][1];

            matriz[fila][columna] = hexagonos.get(i);
            crearVerticesDesdeHexagono(fila, columna);
            crearAristasDesdeHexagono(fila, columna);
        }
    }

    private boolean esPosicionValida(int fila, int col) {
        return fila >= 0 && fila <= filas && col >= 0 && col <= columnas;
    }

    private boolean esVacio(int fila, int col) {
        return TIPO_VACIO.equals(matriz[fila][col]);
    }


    private void crearVerticesDesdeHexagono(int filaInicial, int columnaInicial) {
        int[][] posicionDesdeHexagono = {
                {-3, 0},
                {-1, -2}, {-1, 2},
                {1, -2}, {1, 2},
                {3, 0}
        };

        for (int i = 0; i < 6; i++) {
            int fil = filaInicial + posicionDesdeHexagono[i][0];
            int col = columnaInicial + posicionDesdeHexagono[i][1];

            if (esPosicionValida(fil, col) && esVacio(fil, col)) {
                matriz[fil][col] = new Vertice(fil, col);
            }
        }
    }

    private void crearAristasDesdeHexagono(int filaInicial, int columnaInicial) {
        int[][] posicionDesdeHexagono = {
                {-2, -1}, {-2, 1},
                {0, -2}, {0, 2},
                {2, -1}, {2, 1}
        };

        for (int i = 0; i < 6; i++) {
            int fil = filaInicial + posicionDesdeHexagono[i][0];
            int col = columnaInicial + posicionDesdeHexagono[i][1];

            if (esPosicionValida(fil, col) && esVacio(fil, col)) {
                matriz[fil][col] = new Arista(fil, col);
            }
        }
    }
}
