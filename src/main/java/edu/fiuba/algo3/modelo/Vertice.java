package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int fila;
    private int columna;
    private List<Hexagono> hexagonos;
    private boolean ocupado;

    public Vertice(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.hexagonos = new ArrayList<>();
        this.ocupado = false;
    }

    public List<Recurso> cosecharRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        for (int i = 0; i < this.hexagonos.size(); i++) {
            if (hexagonos.get(i).obtenerRecurso() != null) {
                recursos.add(this.hexagonos.get(i).obtenerRecurso());
            }
        }
        return recursos;
    }

    public void agregarHexagono(Hexagono hexagono) {
        hexagonos.add(hexagono);
    }

    public void ocuparVertice() {
        this.ocupado = true;
    }
    public boolean verificarOcupado() {
        return this.ocupado;
    }
}
