package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Camino {
    private final List<Arista> carreteras = new ArrayList<>();
    private int diametro = 0;

    public void agregarCarretera(Arista a) {
        carreteras.add(a);
    }

    public void agregarCarreteras(List<Arista> lista) {
        carreteras.addAll(lista);
    }

    public List<Arista> getCarreteras() {
        return carreteras;
    }

    public boolean conectaCon(Arista nueva) {
        for (Arista a : carreteras) {
            if (a.verAdyacentes().contains(nueva)) {
                return true;
            }
        }
        return false;
    }

    public int getDiametro() {
        return diametro;
    }

    public void recalcularDiametro() {
        if (carreteras.isEmpty()) {
            diametro = 0;
            return;
        }

        Arista inicio = carreteras.get(0);

        Resultado extremo = dfsLejano(inicio, new HashSet<>());

        Resultado finalR = dfsLejano(extremo.nodo, new HashSet<>());

        this.diametro = finalR.distancia + 1;
    }

    private Resultado dfsLejano(Arista actual, Set<Arista> visitados) {
        visitados.add(actual);
        Resultado mejor = new Resultado(actual, 0);

        for (Arista ady : actual.verAdyacentes()) {
            if (!visitados.contains(ady) && carreteras.contains(ady)) {
                Resultado r = dfsLejano(ady, visitados);

                if (r.distancia + 1 > mejor.distancia) {
                    mejor = new Resultado(r.nodo, r.distancia + 1);
                }
            }
        }

        return mejor;
    }

    private static class Resultado {
        Arista nodo;
        int distancia;

        Resultado(Arista n, int d) {
            nodo = n;
            distancia = d;
        }
    }
}
