package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jugador implements Observable {
    private final String nombre;
    private final Inventario inventario;
    private final List<CartaDesarollo> cartasNuevas;
    private final List<CartaDesarollo> cartasUsables;
    private final Map<Recurso, Integer> tasasDeIntercambioConBanca;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;

    private List<Observador> observadores = new ArrayList<>();

    public Jugador(String nombre) {
        this.nombre = nombre;
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

    public Jugador() {
        this("");
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


    public int cantidadDe(Recurso recurso) {
        return this.inventario.cantidadDe(recurso);
    }

    public int obtenerTasaDe(Recurso recurso) {
        return this.tasasDeIntercambioConBanca.get(recurso);
    }


    public void agregarRecurso(Recurso recurso, int cantidadAAgregar) {
        this.inventario.agregar(recurso, cantidadAAgregar);
        notificarObservadores();
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
        notificarObservadores();
        return cantidadRecurso;
    }


    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            this.inventario.descartarMitad();
            notificarObservadores();
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
                notificarObservadores();
                return robado;
            }
        }
        return null;
    }


    public boolean construirPoblado(Tablero tablero, int idVertice) {
        // Definir costo
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);
        costo.put(Recurso.LANA, 1);
        costo.put(Recurso.GRANO, 1);

        boolean esFaseInicial = (this.construcciones.size() < 2);

        if (!esFaseInicial && !poseeRecursos(costo)) {
            return false;
        }

        if (tablero.construirPoblado(idVertice)) {
            Vertice vertice = tablero.obtenerVertice(idVertice);
            vertice.establecerDueño(this);
            Construccion nuevaConstruccion = new Poblado(vertice);

            if (!esFaseInicial) {
                this.inventario.quitar(costo);
                notificarObservadores();
            }

            construcciones.add(nuevaConstruccion);
            vertice.aplicarEfectos(this);

            if (construcciones.size() == 2) {
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
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.GRANO, 2);
        costo.put(Recurso.MINERAL, 3);

        if (!poseeRecursos(costo)) {
            return false;
        }

        Vertice verticeBuscado = tablero.obtenerVertice(idVertice);
        Construccion construccionAActualizar = null;

        for (Construccion c : construcciones) {
            if (c.obtenerVertice() == verticeBuscado) {
                construccionAActualizar = c;
                break;
            }
        }

        if (construccionAActualizar instanceof Poblado) {
            this.inventario.quitar(costo);
            notificarObservadores();

            construcciones.remove(construccionAActualizar);
            construcciones.add(new Ciudad(verticeBuscado));
            return true;
        }

        return false;
    }

    public boolean construirCarretera(Tablero tablero, int idArista) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);

        boolean esGratis = carreteras.isEmpty() && construcciones.size() < 2;

        if (!esGratis && !poseeRecursos(costo)) {
            return false;
        }

        Arista aristaAgregar = tablero.obtenerArista(idArista);
        if (aristaAgregar.verificarOcupado()) return false; // Ya está ocupada

        boolean conectaConLoSuyo = false;


        List<Arista> aristasVecinas = aristaAgregar.verAdyacentes();
        for (Arista vecina : aristasVecinas) {

            if (vecina.obtenerDueño() == this) {
                conectaConLoSuyo = true;
                break;
            }
        }

        if (!conectaConLoSuyo) {
            List<Vertice> verticesDeArista = aristaAgregar.obtenerVertices();
            for (Vertice v : verticesDeArista) {
                if (v.obtenerDueño() == this) {
                    conectaConLoSuyo = true;
                    break;
                }
            }
        }

        if (carreteras.isEmpty()) {
            if (construcciones.isEmpty()) conectaConLoSuyo = true;
        }

        if (conectaConLoSuyo) {
            if (!esGratis) {
                this.inventario.quitar(costo);
                notificarObservadores();
            }

            aristaAgregar.establecerDueño(this); //


            carreteras.add(aristaAgregar);
            return true;
        }
        return false;
    }

    public void finalizarTurno() {
        cartasUsables.addAll(cartasNuevas);
        cartasNuevas.clear();
    }

    public String obtenerNombre() {
        return nombre;
    }
}