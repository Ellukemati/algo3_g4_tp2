package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {
    private List<Hexagono> hexagonos;
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public Tablero() {
        this.hexagonos = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();
    }

    private void inicializarTablero() {
        List<Integer> numeros = crearNumerosAleatorios();
        List<Recurso> recursos = crearRecursos();

        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Hexagono(recursos.get(i), numeros.get(i)));
        }
        hexagonos.add(new Hexagono(null, 0));
        Collections.shuffle(hexagonos);
        crearVertices();
    }

    private void crearVertices() {
        for (int i = 0; i < 54; i++){
            vertices.add(new Vertice(i));
        }
    }
//
    private void conectarVertices() {
        int verticesPrimerAnillo = 30;

        for (int i = 0; i < verticesPrimerAnillo; i++) {
            int anterior = (i - 1 + verticesPrimerAnillo) % verticesPrimerAnillo;
            int siguiente = (i + 1) % verticesPrimerAnillo;

            vertices.get(i).agregarVerticeAdyacente(vertices.get(anterior));
            vertices.get(i).agregarVerticeAdyacente(vertices.get(siguiente));
        }

        int diferenciaConPrimerAnillo = 30;
        int contador = 0;
        int verticesSegundoAnillo = 48;
        for (int i = 30; i < verticesSegundoAnillo; i++) {
            int anterior = (i - 1 + verticesPrimerAnillo) % verticesPrimerAnillo;
            int siguiente = (i + 1) % verticesPrimerAnillo;

            vertices.get(i).agregarVerticeAdyacente(vertices.get(anterior));
            vertices.get(i).agregarVerticeAdyacente(vertices.get(siguiente));

            if (contador == 2) {
                diferenciaConPrimerAnillo = diferenciaConPrimerAnillo - 2;
                contador = 0;
            } else {
                vertices.get(i - diferenciaConPrimerAnillo).agregarVerticeAdyacente(vertices.get(i));
                vertices.get(i).agregarVerticeAdyacente(vertices.get(i - diferenciaConPrimerAnillo));
                contador++;
            }
        }

        int diferenciaConSegundoAnillo = 16;
        int verticesTercerAnillo = 54;
        for (int i = 48; i < verticesTercerAnillo; i++) {
            int anterior = (i - 1 + verticesPrimerAnillo) % verticesPrimerAnillo;
            int siguiente = (i + 1) % verticesPrimerAnillo;

            vertices.get(i).agregarVerticeAdyacente(vertices.get(anterior));
            vertices.get(i).agregarVerticeAdyacente(vertices.get(siguiente));

            vertices.get(i - diferenciaConSegundoAnillo).agregarVerticeAdyacente(vertices.get(i));
            vertices.get(i).agregarVerticeAdyacente(vertices.get(i - diferenciaConSegundoAnillo));

            diferenciaConPrimerAnillo = diferenciaConPrimerAnillo - 2;
        }
    }

    // verificar que funcione luego
    private void conectarVerticesConHexagonos() {
        int verticesPrimerAnillo = 30;
        int hexagonoActual = 0;
        int contador = 0;
        vertices.get(0).agregarHexagono(hexagonos.get(11));

        for (int i = 0; i < verticesPrimerAnillo; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));

            if ((hexagonoActual % 2 == 0 && contador == 3) || (hexagonoActual % 2 != 0 && contador == 2)) {
                hexagonoActual++;
                vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
                contador = 0;
            }
            contador++;
        }

        // esta parte es la mas complicada, priorizar revision si algun test relacionado falla
        hexagonoActual++;
        int hexagonoPrimerAnillo = 11;
        contador = 2;
        int verticesSegundoAnillo = 48;
        for (int i = 30; i < verticesSegundoAnillo; i++) {
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

        int hexagonoSegundoAnillo = 12;
        int verticesTercerAnillo = 54;
        hexagonoActual = 18;

        for (int i = 48; i < verticesTercerAnillo; i++) {
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoActual));
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
            hexagonoSegundoAnillo++;
            vertices.get(i).agregarHexagono(hexagonos.get(hexagonoSegundoAnillo));
        }
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

}

