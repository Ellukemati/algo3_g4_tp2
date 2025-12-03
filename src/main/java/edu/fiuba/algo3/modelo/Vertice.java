package edu.fiuba.algo3.modelo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Vertice implements Observable {
    private final int id;
    private final List<Hexagono> hexagonosAdyacentes;
    private final List<Vertice> verticesAdyacentes;
    private List<Arista> aristas;
    private Puerto puerto;
    private List<Observador> observadores = new ArrayList<>(); // Lista de interesados

    private boolean ocupado;

    public Vertice(int id) {
        this.id = id;
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.aristas = new ArrayList<>();
        this.puerto = new SinPuerto();

        this.ocupado = false;

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
    public void ocuparVertice() {
        this.ocupado = true;
        notificarObservadores(); // <--- ¡AVISO IMPORTANTE!
    }

    public void asignarPuerto(Puerto puerto) {
        this.puerto = puerto;
    }

    public void agregarHexagono(Hexagono hexagono) {
        hexagonosAdyacentes.add(hexagono);
    }

    public void agregarArista(Arista arista) {
        if (!this.aristas.contains(arista)) {
            this.aristas.add(arista);
        }
    }

    // getter para los tests (devuelve la lista)
    public List<Arista> obtenerAristas() {
        return this.aristas;
    }

    public void agregarVerticeAdyacente(Vertice vertice) {
        verticesAdyacentes.add(vertice);
    }

    public boolean construirPoblado() {
        if (!verificarOcupado()) {
            ocuparVertice();
            for (Vertice vertice : verticesAdyacentes) {
                vertice.ocuparVertice();
            }
            return true;
        }
        return false;
    }



    public boolean verificarOcupado() {
        return this.ocupado;
    }

    public void aplicarEfectos(Jugador jugador) {
        this.puerto.aplicarBeneficio(jugador);
    }

    public List<Recurso> cosecharRecursos(int numeroDado) {
        List<Recurso> recursos = new ArrayList<>();
        for (Hexagono hexagonosAdyacente : this.hexagonosAdyacentes) {
            if (hexagonosAdyacente.verificarNumero(numeroDado) != null) {
                recursos.add(hexagonosAdyacente.obtenerRecurso());
            }
        }
        return recursos;
    }

    public List<Hexagono> obtenerHexagonosAdyacentes() {
        return this.hexagonosAdyacentes;
    }

    public List<Vertice> obtenerAdyacentes() {
        return this.verticesAdyacentes;
    }

    public Puerto obtenerPuerto() {
        return this.puerto;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Vertice " + this.id;
    }
}
