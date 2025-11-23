package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Jugador {

    private final Inventario inventario;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;
    private List<CartaDesarollo> cartas = new ArrayList<>();

    public Jugador() {
        this.inventario = new Inventario();
        this.carreteras = new ArrayList<>();
        this.construcciones = new ArrayList<>();
    }

    public void agregarRecurso(Recurso recurso, int cantidadAAgregar) {
        this.inventario.agregar(recurso, cantidadAAgregar);
    }

    public void agregarRecursos(Map<Recurso, Integer> recursosNuevos) {
        for (Map.Entry<Recurso, Integer> entry : recursosNuevos.entrySet()) {
            this.inventario.agregar(entry.getKey(), entry.getValue());
        }
    }

    private void quitarRecurso(Recurso recurso, int cantidadAQuitar) {
        this.inventario.quitar(recurso, cantidadAQuitar);
    }

    public void quitarRecursos(Map<Recurso, Integer> recursosAQuitar) {
        for (Map.Entry<Recurso, Integer> entry : recursosAQuitar.entrySet()) {
            this.inventario.quitar(entry.getKey(), entry.getValue());
        }
    }

    public int cantidadTotalDeRecursos() {
        return this.inventario.cantidadTotalDeRecursos();
    }

    public boolean poseeRecursos(Map<Recurso, Integer> recursosAChequear) {
        return this.inventario.poseeSuficientes(recursosAChequear);
    }

    // LÓGICA DE JUEGO

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            int cantidadRecursos = this.cantidadTotalDeRecursos();
            if (cantidadRecursos > 7) {
                this.descartarRecursosPorTirada7(cantidadRecursos);
            }
            // ACÁ PODRÍA IR LA LÓGICA DE MOVER AL LADRÓN
        }
        // ACÁ SE PODRÍA AGREGAR LA LÓGICA DE DELEGARLE A SUS PUEBLOS QUE RECOLECTEN CON EL NÚMERO DEL DADO
    }

    private void descartarRecursosPorTirada7(int cantidadRecursosTotales) {
        int cantidadADescartar = cantidadRecursosTotales / 2;
        Map<Recurso, Integer> recursosParaDescartar = new HashMap<>();
        int acumuladorDescartados = 0;
        Map<Recurso, Integer> copiaRecursos = this.inventario.obtenerCopia();

        // PRIMERA IMPLEMENTACIÓN: Descarta los primeros X recursos que encuentra
        for (Recurso recurso : copiaRecursos.keySet()) {
            int cantidadActual = copiaRecursos.get(recurso);
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
        if (this.poseeRecursos(oferta) && otroJugador.poseeRecursos(solicitud)) {
            this.quitarRecursos(oferta);
            this.agregarRecursos(solicitud);

            otroJugador.confirmarIntercambio(oferta, solicitud);
        }
    }

    protected void confirmarIntercambio(Map<Recurso, Integer> recursosRecibidos, Map<Recurso, Integer> recursosDados) {
        this.quitarRecursos(recursosDados);
        this.agregarRecursos(recursosRecibidos);
    }

    // LÓGICA DE CONSTRUCCIÓN
    //Strategy aplicar aca
    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Construccion nuevaConstruccion = new Poblado(tablero.obtenerVertice(idVertice));
        if (tablero.construirPoblado(idVertice)) {
            construcciones.add(nuevaConstruccion);
            if (this.construcciones.size() == 2) {
                List<Recurso> recursosVertice = nuevaConstruccion.cosechar();

                for (Recurso recursoActual : recursosVertice) {
                    this.agregarRecurso(recursoActual, 1);
                }

            }
            return true;
        }
        return false;
    }

    //Str
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

    public void comprarCartaDeDesarollo(Banca banca){
        try {
            cartas.add(banca.comprarCartaDeDesarollo(inventario));
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

}
