package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Jugador implements Observable {

    private final Inventario inventario;
    private final List<CartaDesarollo> cartasNuevas;
    private final List<CartaDesarollo> cartasUsables;
    private final Map<Recurso, Integer> tasasDeIntercambioConBanca;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;


    private List<Observador> observadores = new ArrayList<>();

    public Jugador() {
        this.inventario = new Inventario();
        this.cartasNuevas = new ArrayList<>();
        this.cartasUsables = new ArrayList<>();
        this.tasasDeIntercambioConBanca = new HashMap<>();
        for (Recurso r : Recurso.values()) {
            this.tasasDeIntercambioConBanca.put(r, 4);
        }
        this.construcciones = new ArrayList<>();
        this.carreteras = new ArrayList<>();
    }

    // --- IMPLEMENTACIÓN OBSERVER ---

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

    // --- GETTERS ---

    public int cantidadDe(Recurso recurso) {
        return this.inventario.cantidadDe(recurso);
    }

    public int obtenerTasaDe(Recurso recurso) {
        return this.tasasDeIntercambioConBanca.get(recurso);
    }

    // --- GESTIÓN INTERNA (Con Notificaciones) ---

    public void agregarRecurso(Recurso recurso, int cantidadAAgregar) {
        this.inventario.agregar(recurso, cantidadAAgregar);
        notificarObservadores(); // <--- AVISAR CAMBIO
    }

    public int cantidadTotalDeRecursos() {
        return this.inventario.cantidadTotalDeRecursos();
    }

    public boolean poseeRecursos(Map<Recurso, Integer> recursosAChequear) {
        return this.inventario.poseeSuficientes(recursosAChequear);
    }

    public void activarDescuentoGenerico() {
        this.tasasDeIntercambioConBanca.replaceAll((k, v) -> Math.min(v, 3));
    }

    public void activarDescuentoPara(Recurso recurso) {
        this.tasasDeIntercambioConBanca.put(recurso, 2);
    }

    public int quitarCantidadTotalDeRecursos(Recurso recurso) {
        int cantidadRecurso = this.inventario.cantidadDe(recurso);
        this.inventario.quitar(recurso, cantidadRecurso);
        notificarObservadores(); // <--- AVISAR CAMBIO (Al ser robado por Monopolio)
        return cantidadRecurso;
    }

    // --- LÓGICA DE JUEGO ---

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            this.inventario.descartarMitad();
            notificarObservadores(); // <--- AVISAR CAMBIO (Descarte por 7)
            // AÚN NO IMPLEMENTA moverLadron(). Iría acá?
        }

        for (Construccion c : construcciones) {
            List<Recurso> recursosCosechados = c.cosechar(numeroDado);
            for (Recurso recursoActual : recursosCosechados) {
                this.agregarRecurso(recursoActual, 1); // Este método ya notifica
            }
        }
    }

    public void intercambiar(Jugador otroJugador, Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (this.poseeRecursos(oferta) && otroJugador.poseeRecursos(solicitud)) {
            this.inventario.realizarTransferencia(oferta, solicitud);
            notificarObservadores();

            otroJugador.confirmarIntercambio(oferta, solicitud);
        }
    }

    protected void confirmarIntercambio(Map<Recurso, Integer> recursosRecibidos, Map<Recurso, Integer> recursosDados) {
        this.inventario.realizarTransferencia(recursosDados, recursosRecibidos);
        notificarObservadores();
    }

    public void comerciarConBanca(Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (esIntercambioConBancaValido(oferta, solicitud)) {
            this.inventario.canjear(oferta, solicitud);
            notificarObservadores();
        }
    }

    private boolean esIntercambioConBancaValido(Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        int capacidadDeCompra = 0;

        for (Map.Entry<Recurso, Integer> entry : oferta.entrySet()) {
            Recurso recurso = entry.getKey();
            int cantidadOfrecida = entry.getValue();

            int tasa = this.tasasDeIntercambioConBanca.get(recurso);
            if (cantidadOfrecida % tasa != 0) {
                return false;
            }

            capacidadDeCompra += (cantidadOfrecida / tasa);
        }

        int cantidadSolicitada = solicitud.values().stream().mapToInt(Integer::intValue).sum();

        return capacidadDeCompra == cantidadSolicitada;
    }

    public void comprarCartaDeDesarollo(Banca banca) {
        try {
            // Comprar modifica el inventario (pago), así que notificamos
            cartasNuevas.add(banca.comprarCartaDeDesarollo(inventario));
            notificarObservadores();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void usarCartaDeDesarollo(CartaDesarollo carta, Tablero tablero, List<Jugador> jugadores) {
        CartaDesarollo cartaDeDesarollo = cartasUsables.stream()
                .filter((cd) -> cd.equals(carta))
                .findFirst()
                .orElse(new NullCartaDesarollo());
        cartaDeDesarollo.usar(this, tablero, jugadores);
        // Nota: Algunas cartas modifican recursos (Monopolio, Descubrimiento)
        // y llamarán a métodos que ya notifican.
    }

    public Hexagono moverLadron(Tablero tablero, int posicion) {
        return tablero.moverLadron(posicion);
    }

    public Recurso serRobadoPorLadron(Hexagono hexagonoRobar) {
        boolean soyVictima = false;

        for (Construccion c : construcciones) {
            Vertice vert = c.obtenerVertice();
            if (vert.obtenerHexagonosAdyacentes().contains(hexagonoRobar)) {
                soyVictima = true;
                break;
            }
        }

        if (soyVictima) {
            Recurso robado = this.inventario.extraerRecursoAlAzar();
            if (robado != null) {
                notificarObservadores(); // <--- AVISAR CAMBIO (Me robaron)
                return robado;
            }
        }
        return null;
    }

    // --- LÓGICA DE CONSTRUCCIÓN ---

    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Vertice vertice = tablero.obtenerVertice(idVertice);
        Construccion nuevaConstruccion = new Poblado(vertice);

        if (tablero.construirPoblado(idVertice)) {
            // ACÁ FALTA DESCONTAR RECURSOS (Próximamente)
            // Cuando lo agregues: this.inventario.quitar(...); notificarObservadores();

            construcciones.add(nuevaConstruccion);
            vertice.aplicarEfectos(this);

            if (this.construcciones.size() == 2) {
                // Recursos iniciales (Setup)
                List<Recurso> recursosVertice = nuevaConstruccion.cosechar(-1);
                for (Recurso recursoActual : recursosVertice) {
                    this.agregarRecurso(recursoActual, 1); // Ya notifica
                }
            }
            return true;
        }
        return false;
    }

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
            // ACÁ FALTA DESCONTAR RECURSOS (Próximamente)

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
            aristaAgregar.ocupar();
            return true;

        } else {
            List<Arista> aristasAdyacentes = aristaAgregar.verAdyacentes();
            for (Arista aristaActual : carreteras) {
                if (aristasAdyacentes.contains(aristaActual)) {
                    carreteras.add(aristaAgregar);
                    aristaAgregar.ocupar();
                    return true;
                }
            }
            return false;
        }
    }

    public void finalizarTurno() {
        cartasUsables.addAll(cartasNuevas);
        cartasNuevas.clear();
    }
}