package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {
    private List<Hexagono> hexagonos;
    private Mapa mapa;

    public Tablero() {
        this.hexagonos = new ArrayList<>();
        this.mapa = inicializarTablero();
    }

    private Mapa inicializarTablero() {

        List<Integer> numeros = crearNumerosAleatorios();
        List<Recurso> recursos = crearRecursos();

        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Hexagono(recursos.get(i), numeros.get(i)));
        }
        hexagonos.add(new Hexagono(null, 0));

        Collections.shuffle(hexagonos);

        return new Mapa(hexagonos);
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

    public Vertice obtenerVertice(int fila, int columna) {
        return this.mapa.obtenerVertice(fila, columna);
    }

    public boolean construirPoblado(int fila, int columna) {
        return mapa.colocarPoblado(fila, columna);
    }
}

