package edu.fiuba.algo3.modelo;

import java.util.*;

public class Tablero {
    private List<Hexagono> hexagonos;
    private Mapa mapa;
    private Colocable[][] tablero;


    public Tablero(String ruta) {
        this.hexagonos = new ArrayList<>();
        this.mapa = new Mapa(ruta);
        int[] tamanio = mapa.calcularTamañoMapa();
        this.tablero = new Colocable[tamanio[0]][tamanio[1]];
        inicializarTablero();
    }


    private void inicializarTablero() {

        List<Integer> numeros = crearNumerosAleatorios();
        List<Recurso> recursos = crearRecursos();

        Queue<int[]> pocicionExagonos = this.mapa.calcularPosicionesExagonos();

        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Hexagono(recursos.get(i), numeros.get(i)));
            int[] pocicion = pocicionExagonos.poll();
            tablero[pocicion[0]][pocicion[1]] = new Hexagono(recursos.get(i), numeros.get(i));
        }

        hexagonos.add(new Hexagono(null, 0));
        int[] pocicion = pocicionExagonos.poll();
        tablero[pocicion[0]][pocicion[1]] = new Hexagono(null, 0);

        Collections.shuffle(hexagonos);
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

    /*public void colocarConstruible(Contruible contruible, int x, int y, String identificador) {


    }*/

    public ArrayList<Colocable> obtenerAdyacentes(int x, int y, String identificador) {
        ArrayList<int[]> pocicionesAdyacentes = mapa.obtenerPosicionesAdyacentes(x, y, identificador);
        ArrayList<Colocable> colocables = new ArrayList<>();
        for (int[] pocicion: pocicionesAdyacentes) {
            if (tablero[pocicion[0]][pocicion[1]] != null) {
                colocables.add(tablero[pocicion[0]][pocicion[1]]);
            }

        }
        return colocables;
    }


    public void colocarColocable(Colocable colocable, int x, int y, String identificador) {
        if (mapa.esPocicionValida(x, y, identificador)) {
            tablero[x][y] = colocable;
        }
    }

    public void mejorarPoblado(Ciudad ciudad, int x, int y) {
       if ( ciudad.esMejorable(tablero[x][y])) {
           tablero[x][y] = ciudad;
       }

    }
}

