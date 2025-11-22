package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Jugador {
    private final Map<Recurso, Integer> recursos;
    private final List<Construccion> construcciones;
    // considerar crear el objeto carretera
    private final List<Arista> carreteras;

    public Jugador() {
        this.construcciones = new ArrayList<>();
        this.carreteras = new ArrayList<>();
        this.recursos = new HashMap<>();
        for (Recurso recurso : Recurso.values()) {
            this.recursos.put(recurso, 0);
        }
    }

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

    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Construccion nuevaConstruccion = new Poblado(tablero.obtenerVertice(idVertice));
        if (tablero.construirPoblado(idVertice)) {
            construcciones.add(nuevaConstruccion);
            if (this.construcciones.size() == 2) {
                List<Recurso> recursosVertice = nuevaConstruccion.cosechar();
                for (Recurso recursoActual : recursosVertice) {
                    int cantidadActualizada = this.recursos.get(recursoActual) + 1;
                    recursos.put((recursoActual), cantidadActualizada);
                }
            }
            return true;
        }
        return false;
    }
//Strategy aplicar aca
    public boolean construirCiudad(Tablero tablero, int idVertice) {
        Vertice verticeBuscado = tablero.obtenerVertice(idVertice);

        Construccion construccionAActualizar = null;

        for (Construccion c : construcciones) {
            if (c.obtenerVertice() == verticeBuscado) {
                construccionAActualizar = c;
                break;
            }
        }
        if (construccionAActualizar instanceof Poblado) {
            construcciones.remove(construccionAActualizar);
            construcciones.add(new Ciudad(verticeBuscado));
            return true;
        }
        return false;
    }


//se puede aplicar command a construir


    public boolean construirCarretera(Tablero tablero, int idArista) {
        Arista aristaAgregar = tablero.obtenerArista(idArista);
        if (carreteras.isEmpty()) {
            carreteras.add(aristaAgregar);
        } else {
            List<Arista> aristasAdyacentes = aristaAgregar.verAdyacentes();
            for (Arista aristaActual : carreteras) {
                if (aristasAdyacentes.contains(aristaActual)) {
                    carreteras.add(aristaAgregar);
                    return true;
                }
            }
            return false;
        }
        return true;
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
