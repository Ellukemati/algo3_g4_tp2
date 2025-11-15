package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Jugador {
    private Map<Recurso, Integer> recursos;
    private List<Construccion> construcciones;

    public Jugador() {
        this.construcciones = new ArrayList<>();
        this.recursos = new HashMap<>();
        for (Recurso recurso : Recurso.values()) {
            this.recursos.put(recurso, 0);
        }
    }

    public boolean construirPoblado(Tablero tablero, int fila, int columna) {
        Construccion nuevaConstruccion = new Poblado(tablero.obtenerVertice(fila, columna));
        if (tablero.construirPoblado(fila, columna)) {
            construcciones.add(nuevaConstruccion);
            if (this.construcciones.size() == 2) {
                List<Recurso> recursosVertice = nuevaConstruccion.cosechar();
                for (int i = 0; i < recursosVertice.size(); i++) {
                    Recurso recursoActual = recursosVertice.get(i);
                    int cantidadActualizada = this.recursos.get(recursoActual) + 1;
                    recursos.put((recursoActual), cantidadActualizada);
                }
            }
            return true;
        }
        return false;
    }

    public boolean construirCiudad() {
        return false;
    }

    public void agregarRecurso(Recurso recurso) {
        int cantidadActual = this.recursos.getOrDefault(recurso, 0);
        this.recursos.put(recurso, cantidadActual + 1);
    }

    public int cantidadTotalDeRecursos() {
        int total = 0;
        for (Integer cantidad : this.recursos.values()) {
            total += cantidad;
        }
        return total;
    }

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            this.descartarRecursos();
        }
        // ACÁ DESPUÉS SE PUEDE AGREGAR LA LÓGICA DE DELEGARLE A SUS PUEBLOS QUE RECOLECTEN CON EL NÚMERO DEL DADO
    }

    private void descartarRecursos() {
        int recursosTotales = this.cantidadTotalDeRecursos();
        if (recursosTotales > 7) {
            int cantidadADescartar = recursosTotales / 2;

            // PRIMERA IMPLEMENTACIÓN: Descarta los primeros recursos que encuentra
            int descartados = 0;
            for (Recurso recurso : new HashMap<>(this.recursos).keySet()) {
                int cantidadQueTiene = this.recursos.get(recurso);
                int puedeDescartar = Math.min(cantidadQueTiene, cantidadADescartar - descartados);

                this.recursos.put(recurso, cantidadQueTiene - puedeDescartar);
                descartados += puedeDescartar;

                if (descartados == cantidadADescartar) {
                    break;
                }
            }
        }
    }
}
