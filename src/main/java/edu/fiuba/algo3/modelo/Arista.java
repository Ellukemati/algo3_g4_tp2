package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Arista {
    private int id;
    private final List<Arista> aristasAdyacentes;
    private List<Vertice> vertices;
    private boolean ocupado;

    public Arista(int id) {
        this.id = id;
        this.aristasAdyacentes = new ArrayList<>();
        this.ocupado = false;
        this.vertices = new ArrayList<>();
    }


    public void ocupar() { ocupado = true; }

    public void agregarVertice(Vertice vertice) {
        if (!this.vertices.contains(vertice)) {
            this.vertices.add(vertice);
        }
    }

    // NUEVO: Getter para los tests (devuelve la lista)
    public List<Vertice> obtenerVertices() {
        return this.vertices;
    }
    public boolean verificarOcupado() { return ocupado; }

    public List<Arista> verAdyacentes() { return aristasAdyacentes; }
    public void agregarAristaAdyacente(Arista arista) {
        aristasAdyacentes.add(arista);
    }


	public int obtenerId() {
		return id;
	}
}
