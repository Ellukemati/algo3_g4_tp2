package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Jugador {

    private final Inventario inventario;
    private final List<CartaDesarollo> cartasNuevas;
    private final List<CartaDesarollo> cartasUsables;
    private final Map<Recurso, Integer> tasasDeIntercambioConBanca;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;

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

    // GESTIÓN INTERNA

    public void agregarRecurso(Recurso recurso, int cantidadAAgregar) {
        this.inventario.agregar(recurso, cantidadAAgregar);
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
        return cantidadRecurso;
    }

    // LÓGICA DE JUEGO

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            this.inventario.descartarMitad();

            // AÚN NO IMPLEMENTA moverLadron(). Iría acá?
        }

        for (Construccion c : construcciones) {
            List<Recurso> recursosCosechados = c.cosechar(numeroDado);
            for (Recurso recursoActual : recursosCosechados) {
                this.agregarRecurso(recursoActual, 1);
            }
        }
    }

    public void intercambiar(Jugador otroJugador, Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (this.poseeRecursos(oferta) && otroJugador.poseeRecursos(solicitud)) {
            this.inventario.realizarTransferencia(oferta, solicitud);

            otroJugador.confirmarIntercambio(oferta, solicitud);
        }
    }

    protected void confirmarIntercambio(Map<Recurso, Integer> recursosRecibidos, Map<Recurso, Integer> recursosDados) {
        this.inventario.realizarTransferencia(recursosDados, recursosRecibidos);
    }

    public void comerciarConBanca(Recurso recursoAEntregar, Recurso recursoAPedir) {
        int costo = this.tasasDeIntercambioConBanca.get(recursoAEntregar);
        this.inventario.canjear(recursoAEntregar, costo, recursoAPedir, 1);
    }

    public void comprarCartaDeDesarollo(Banca banca) {
        try {
            cartasNuevas.add(banca.comprarCartaDeDesarollo(inventario));
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
    }

    // a ser usada cuando toca 7 en la tirada y por la carta de desarrollo si el jugador la posee y usa
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
            return this.inventario.extraerRecursoAlAzar();
        }
        return null;
    }

    // LÓGICA DE CONSTRUCCIÓN

    //Strategy aplicar aca
    //considerar para caso de size > 3, es decir para el resto del juego
    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Vertice vertice = tablero.obtenerVertice(idVertice);
        Construccion nuevaConstruccion = new Poblado(vertice);

        if (tablero.construirPoblado(idVertice)) {
            construcciones.add(nuevaConstruccion);
            vertice.aplicarEfectosSiCorresponde(this);

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

    public void finalizarTurno() {
        cartasUsables.addAll(cartasNuevas);
        cartasNuevas.clear();
    }
}
