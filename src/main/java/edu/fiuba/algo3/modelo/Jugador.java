package edu.fiuba.algo3.modelo;

import java.util.*;

public class Jugador {

    private final Inventario inventario;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;
    private final List<CartaDesarollo> cartas = new ArrayList<>();

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
        for (Construccion c : construcciones) {
            List<Recurso> recursosCosechados =  c.cosechar(numeroDado);
            for (Recurso recursoActual : recursosCosechados) {
                this.agregarRecurso(recursoActual, 1);
            }
        }
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

    //considerar para caso de size > 3, es decir para el resto del juego
    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Construccion nuevaConstruccion = new Poblado(tablero.obtenerVertice(idVertice));
        if (tablero.construirPoblado(idVertice)) {
            construcciones.add(nuevaConstruccion);
            if (this.construcciones.size() == 2) {
                // pasarle -1 significa que siempre recoge mientras no haya ladron
                List<Recurso> recursosVertice = nuevaConstruccion.cosechar(-1);
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

    public Recurso serRobadoPorLadron(Hexagono hexagonoRobar) {
        for (Construccion c : construcciones) {
            Vertice vert = c.obtenerVertice();
            if (vert.obtenerHexagonosAdyacentes().contains(hexagonoRobar)) {
                Recurso[] recursos = {Recurso.MADERA, Recurso.LADRILLO,
                        Recurso.MINERAL, Recurso.LANA, Recurso.GRANO};
                List<Recurso> recursosRobables = new ArrayList<>();
                for (Recurso recurso : recursos) {
                    if (inventario.cantidadDe(recurso) > 0) {
                        recursosRobables.add(recurso);
                    }
                }
                Random random = new Random();
                Recurso recursoRobado = recursosRobables.get(random.nextInt(recursosRobables.size()));
                inventario.quitar(recursoRobado, 1);
                return recursoRobado;
            }
        }
        return null;
    }

    // a ser usada cuando toca 7 y por la carta de desarrollo si el jugador la posee
    public Hexagono moverLadron(Tablero tablero, int posicion) {
        return tablero.moverLadron(posicion);
    }
}
