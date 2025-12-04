package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Arista implements Observable {
    private int id;
    private final List<Arista> aristasAdyacentes;
    private List<Vertice> vertices;
    private boolean ocupado;
    private Jugador duennio;
    private List<Observador> observadores = new ArrayList<>();

    public Arista(int id) {
        this.id = id;
        this.aristasAdyacentes = new ArrayList<>();
        this.ocupado = false;
        this.vertices = new ArrayList<>();
        this.duennio = null;
    }
    public void establecerDueño(Jugador jugador) {
        this.duennio = jugador;
        this.ocupado = true; // Si tiene dueño, está ocupada
        notificarObservadores();
    }
    public Jugador obtenerDueño() {
        return this.duennio;
    }
    @Override
    public void agregarObservador(Observador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }
    public void ocupar() {
        ocupado = true;
        notificarObservadores();
    }
    public int getId() {
        return this.id;
    }

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
