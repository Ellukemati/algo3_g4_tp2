package edu.fiuba.algo3.modelo;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private final Map<Recurso, Integer> recursos;

    public Inventario() {
        this.recursos = new HashMap<>();
        for (Recurso recurso : Recurso.values()) {
            this.recursos.put(recurso, 0);
        }
    }

    public void agregar(Recurso recurso, int cantidad) {
        this.recursos.compute(recurso, (k, cantidadActual) -> cantidadActual + cantidad);
    }

    public void agregar(Map<Recurso, Integer> recursosNuevos) {
        for (Map.Entry<Recurso, Integer> entry : recursosNuevos.entrySet()) {
            this.agregar(entry.getKey(), entry.getValue());
        }
    }

    public void quitar(Recurso recurso, int cantidad) {
        this.recursos.compute(recurso, (k, cantidadActual) -> Math.max(0, cantidadActual - cantidad));
    }

    public void quitar(Map<Recurso, Integer> recursosAQuitar) {
        for (Map.Entry<Recurso, Integer> entry : recursosAQuitar.entrySet()) {
            this.quitar(entry.getKey(), entry.getValue());
        }
    }

    public int cantidadTotalDeRecursos() {
        int total = 0;
        for (int cantidad : this.recursos.values()) {
            total += cantidad;
        }
        return total;
    }

    public int cantidadDe(Recurso recurso) {
        return this.recursos.get(recurso);
    }

    public boolean poseeSuficientes(Map<Recurso, Integer> recursosAChequear) {
        for (Map.Entry<Recurso, Integer> entry : recursosAChequear.entrySet()) {
            Recurso recurso = entry.getKey();
            int cantidadRequerida = entry.getValue();

            if (this.recursos.get(recurso) < cantidadRequerida) {
                return false;
            }
        }
        return true;
    }

    public Map<Recurso, Integer> obtenerCopia() {
        return new HashMap<>(this.recursos);
    }
}
