package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

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
        Arista start = carreteras.iterator().next();
        Arista extremo = dfsLejano(start, new ArrayList<>()).nodo;
        diametro = dfsLejano(extremo, new ArrayList<>()).distancia + 1;
    }

    private Resultado dfsLejano(Arista actual, List<Arista> visit) {
        visit.add(actual);
        Resultado mejor = new Resultado(actual, 0);
        for (Arista ady : actual.verAdyacentes()) {
            if (!visit.contains(ady) && carreteras.contains(ady)) {
                List<Arista> copia = new ArrayList<>(visit);
                Resultado r = dfsLejano(ady, copia);
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
