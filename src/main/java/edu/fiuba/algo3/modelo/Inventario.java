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

    public void canjear(Recurso recursoAEntregar, int costo, Recurso recursoARecibir, int ganancia) {
        if (this.cantidadDe(recursoAEntregar) >= costo) {
            this.quitar(recursoAEntregar, costo);
            this.agregar(recursoARecibir, ganancia);
        }
    }

    public void realizarTransferencia(Map<Recurso, Integer> salida, Map<Recurso, Integer> entrada) {
        this.quitar(salida);
        this.agregar(entrada);
    }

    public void descartarMitad() {
        int total = this.cantidadTotalDeRecursos();
        if (total <= 7) return;

        int cantidadADescartar = total / 2;
        int acumuladorDescartados = 0;

        // PRIMERA IMPLEMENTACIÓN: Descarta los primeros X recursos que encuentra
        for (Recurso recurso : Recurso.values()) {
            int cantidadActual = this.recursos.get(recurso);

            if (cantidadActual > 0) {
                int cantidadAQuitarDeEsteRecurso = Math.min(cantidadActual, cantidadADescartar - acumuladorDescartados);
                this.quitar(recurso, cantidadAQuitarDeEsteRecurso);
                acumuladorDescartados += cantidadAQuitarDeEsteRecurso;
            }

            if (acumuladorDescartados == cantidadADescartar) break;
        }
    }

    public Recurso extraerRecursoAlAzar() {
        int totalCartas = this.cantidadTotalDeRecursos();
        if (totalCartas == 0) return null;

        int indiceRandom = new java.util.Random().nextInt(totalCartas);
        int acumulado = 0;

        for (Recurso recurso : Recurso.values()) {
            int cantidad = this.recursos.get(recurso);

            if (cantidad > 0) {
                acumulado += cantidad;
                if (indiceRandom < acumulado) {
                    this.quitar(recurso, 1);
                    return recurso;
                }
            }
        }
        return null;
    }
}
