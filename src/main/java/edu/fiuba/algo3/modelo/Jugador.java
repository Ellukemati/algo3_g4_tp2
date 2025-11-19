package edu.fiuba.algo3.modelo;

import java.util.Map;
import java.util.HashMap;

public class Jugador {
    private Map<Recurso, Integer> recursos = new HashMap<>();

    public void agregarRecurso(Recurso recurso, int cantidadAAgregar) {
        int cantidadActual = this.recursos.getOrDefault(recurso, 0);
        this.recursos.put(recurso, cantidadActual + cantidadAAgregar);
    }

    private void agregarRecursos(Map<Recurso, Integer> recursosAAgregar) {
        for (Map.Entry<Recurso, Integer> entry : recursosAAgregar.entrySet()) {
            this.agregarRecurso(entry.getKey(), entry.getValue());
        }
    }

    public void quitarRecurso(Recurso recurso, int cantidadAQuitar) {
        int cantidadActual = this.recursos.getOrDefault(recurso, 0);
        int nuevaCantidad = Math.max(0, cantidadActual - cantidadAQuitar);
        this.recursos.put(recurso, nuevaCantidad);
    }

    private void quitarRecursos(Map<Recurso, Integer> recursosAQuitar) {
        for (Map.Entry<Recurso, Integer> entry : recursosAQuitar.entrySet()) {
            this.quitarRecurso(entry.getKey(), entry.getValue());
        }
    }

    public int cantidadTotalDeRecursos() {
        int total = 0;
        for (Integer cantidad : this.recursos.values()) {
            total += cantidad;
        }
        return total;
    }

    public boolean tieneRecursos(Map<Recurso, Integer> recursosAChequear) {
        for (Map.Entry<Recurso, Integer> entry : recursosAChequear.entrySet()) {
            Recurso recurso = entry.getKey();
            int cantidadRequerida = entry.getValue();

            if (this.recursos.getOrDefault(recurso, 0) < cantidadRequerida) {
                return false;
            }
        }
        return true;
    }

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            int cantidadRecursos = this.cantidadTotalDeRecursos();
            if (cantidadRecursos > 7)
                this.descartarRecursosPorTirada7(cantidadRecursos);
            // ACÁ PODRÍA IR LA LÓGICA DE MOVER AL LADRÓN
        }
        // ACÁ SE PODRÍA AGREGAR LA LÓGICA DE DELEGARLE A SUS PUEBLOS QUE RECOLECTEN CON EL NÚMERO DEL DADO
    }

    private void descartarRecursosPorTirada7(int cantidadRecursosTotales) {
        int cantidadADescartar = cantidadRecursosTotales / 2;

        Map<Recurso, Integer> recursosParaDescartar = new HashMap<>();
        int acumuladorDescartados = 0;

        // PRIMERA IMPLEMENTACIÓN: Descarta los primeros X recursos que encuentra
        for (Recurso recurso : this.recursos.keySet()) {
            int cantidadActual = this.recursos.get(recurso);

            int cantidadAQuitarDeEsteRecurso = Math.min(cantidadActual, cantidadADescartar - acumuladorDescartados);

            if (cantidadAQuitarDeEsteRecurso > 0) {
                recursosParaDescartar.put(recurso, cantidadAQuitarDeEsteRecurso);
                acumuladorDescartados += cantidadAQuitarDeEsteRecurso;
            }

            if (acumuladorDescartados == cantidadADescartar) {
                break;
            }
        }

        this.quitarRecursos(recursosParaDescartar);
    }

    public void intercambiar(Jugador otroJugador, Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (this.tieneRecursos(oferta) && otroJugador.tieneRecursos(solicitud)) {
            this.quitarRecursos(oferta);
            this.agregarRecursos(solicitud);

            otroJugador.quitarRecursos(solicitud);
            otroJugador.agregarRecursos(oferta);
        }
    }
}
