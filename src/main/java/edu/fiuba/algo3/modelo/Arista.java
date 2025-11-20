package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Arista {
    private int id;
    private final List<Arista> aristasAdyacentes;
    private boolean ocupado;

    public Arista(int id) {
        this.id = id;
        this.aristasAdyacentes = new ArrayList<>();
        this.ocupado = false;
    }

    public void ocupar() { ocupado = true; }

    public boolean verificarOcupado() { return ocupado; }

    public List<Arista> verAdyacentes() { return aristasAdyacentes; }

    public void agregarAristaAdyacente(Arista arista) {
        aristasAdyacentes.add(arista);
    }
}
