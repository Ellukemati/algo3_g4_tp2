package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jugador implements Observable, Bonificacion {

    private final String nombre;
    private final Inventario inventario;

    private final List<CartaDesarollo> cartasNuevas = new ArrayList<>();
    private final List<CartaDesarollo> cartasUsables = new ArrayList<>();
    private final List<CartaDesarollo> cartasUsadas = new ArrayList<>();

    private final Map<Recurso, Integer> tasasDeIntercambioConBanca = new HashMap<>();

    private final List<Construccion> construcciones = new ArrayList<>();
    private final List<Arista> carreteras = new ArrayList<>();
    private final List<Camino> caminos = new ArrayList<>();

    private boolean tieneGranRuta = false;
    private boolean tieneGranCaballeria = false;

    private final List<Observador> observadores = new ArrayList<>();

    // --- CONSTRUCTORES ---

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.inventario = new Inventario();
        for (Recurso r : Recurso.values()) {
            tasasDeIntercambioConBanca.put(r, 4);
        }
    }

    public Jugador() {
        this("Jugador");
    }

    // --- OBSERVER ---

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void notificarObservadores() {
        observadores.forEach(Observador::actualizar);
    }

    // --- PUNTAJE ---

    public int obtenerPuntajeTotal() {
        return calcularPuntosVisibles() + obtenerPuntosVictoriaOcultos();
    }

    public int obtenerPuntaje() {
        return calcularPuntosVisibles();
    }

    private int calcularPuntosVisibles() {
        int puntos = construcciones.stream()
                .mapToInt(Construccion::obtenerPuntosDeVictoria)
                .sum();

        if (tieneGranRuta) puntos += 2;
        if (tieneGranCaballeria) puntos += 2;

        return puntos;
    }

    public int obtenerPuntosVictoriaOcultos() {
        List<CartaDesarollo> todas = new ArrayList<>(cartasNuevas);
        todas.addAll(cartasUsables);
        return (int) todas.stream().filter(c -> c instanceof PuntoDeVictoria).count();
    }

    // --- BONIFICACIONES ---
    public List<CartaDesarollo> obtenerCartasDeDesarollo() {
        List<CartaDesarollo> cartasTotales = new ArrayList<>(cartasUsables);
        cartasTotales.addAll(cartasNuevas);
        return cartasTotales;
    }

    // --- GESTIÓN INTERNA ---

    @Override
    public void asignarGranRuta(boolean tiene) {
        this.tieneGranRuta = tiene;
        notificarObservadores();
    }

    @Override
    public void asignarGranCaballeria(boolean tiene) {
        this.tieneGranCaballeria = tiene;
        notificarObservadores();
    }

    // --- RECURSOS ---

    public int cantidadDe(Recurso recurso) {
        return inventario.cantidadDe(recurso);
    }

    public int cantidadTotalDeRecursos() {
        return inventario.cantidadTotalDeRecursos();
    }

    public int obtenerTasaDe(Recurso recurso) {
        return tasasDeIntercambioConBanca.get(recurso);
    }

    public void agregarRecurso(Recurso recurso, int cantidad) {
        inventario.agregar(recurso, cantidad);
        notificarObservadores();
    }

    public boolean poseeRecursos(Map<Recurso, Integer> recursos) {
        return inventario.poseeSuficientes(recursos);
    }

    public int quitarCantidadTotalDeRecursos(Recurso recurso) {
        int cantidad = inventario.cantidadDe(recurso);
        inventario.quitar(recurso, cantidad);
        notificarObservadores();
        return cantidad;
    }

    // --- COMERCIO ---

    public void activarDescuentoGenerico() {
        tasasDeIntercambioConBanca.replaceAll((k, v) -> Math.min(v, 3));
    }

    public void activarDescuentoPara(Recurso recurso) {
        tasasDeIntercambioConBanca.put(recurso, 2);
    }

    public void intercambiar(Jugador otro, Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (poseeRecursos(oferta) && otro.poseeRecursos(solicitud)) {
            inventario.realizarTransferencia(oferta, solicitud);
            otro.confirmarIntercambio(oferta, solicitud);
            notificarObservadores();
        }
    }

    protected void confirmarIntercambio(Map<Recurso, Integer> recibidos, Map<Recurso, Integer> dados) {
        inventario.realizarTransferencia(dados, recibidos);
        notificarObservadores();
    }

    public void comerciarConBanca(Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        if (esIntercambioConBancaValido(oferta, solicitud)) {
            inventario.canjear(oferta, solicitud);
            notificarObservadores();
        }
    }

    private boolean esIntercambioConBancaValido(Map<Recurso, Integer> oferta, Map<Recurso, Integer> solicitud) {
        int capacidad = oferta.entrySet().stream()
                .mapToInt(e -> (e.getValue() / tasasDeIntercambioConBanca.get(e.getKey())))
                .filter(x -> x > 0)
                .sum();

        int solicitado = solicitud.values().stream().mapToInt(Integer::intValue).sum();

        return capacidad == solicitado;
    }

    // --- DADOS Y LADRÓN ---

    public void recibirLanzamientoDeDados(int numero) {
        if (numero == 7) {
            inventario.descartarMitad();
            notificarObservadores();
        }

        for (Construccion c : construcciones) {
            c.cosechar(numero).forEach(r -> agregarRecurso(r, 1));
        }
    }

    public Hexagono moverLadron(Tablero tablero, int posicion) {
        return tablero.moverLadron(posicion);
    }

    public Recurso robarA(Jugador victima) {
        Recurso robado = victima.entregarRecursoAlAzar();
        if (robado != null) agregarRecurso(robado, 1);
        return robado;
    }

    public Recurso perderRecursoAlAzar() {
        return entregarRecursoAlAzar();
    }

    public Recurso entregarRecursoAlAzar() {
        Recurso r = inventario.extraerRecursoAlAzar();
        if (r != null) notificarObservadores();
        return r;
    }

    public Recurso serRobadoPorLadron(Hexagono hexagono) {
        boolean soyVictima = construcciones.stream()
                .anyMatch(c -> c.obtenerVertice().obtenerHexagonosAdyacentes().contains(hexagono));

        return soyVictima ? entregarRecursoAlAzar() : null;
    }

    // --- CARTAS DE DESARROLLO ---

    public void comprarCartaDeDesarrollo(Banca banca) {
        try {
            cartasNuevas.add(banca.comprarCartaDeDesarollo(inventario));
            notificarObservadores();
        } catch (Exception ignored) {}
    }

    public void usarCartaDeDesarrollo(CartaDesarollo carta, Tablero tablero, List<Jugador> jugadores,
                                      ParametrosCarta parametrosCarta) {
        CartaDesarollo cartaDeDesarollo = cartasUsables.stream()
                .filter((cd) -> cd.equals(carta))
                .findFirst()
                .orElse(new NullCartaDesarollo());
        cartaDeDesarollo.usar(this, tablero, jugadores, parametrosCarta);
        cartasUsables.remove(cartaDeDesarollo);
        cartasUsadas.add(cartaDeDesarollo);
    }

    public void finalizarTurno() {
        cartasUsables.addAll(cartasNuevas);
        cartasNuevas.clear();
    }

    // --- CONSTRUCCIONES ---

    public boolean construirPoblado(Tablero tablero, int idVertice) {
        Map<Recurso, Integer> costo = Map.of(
                Recurso.MADERA, 1,
                Recurso.LADRILLO, 1,
                Recurso.LANA, 1,
                Recurso.GRANO, 1
        );

        boolean esFaseInicial = construcciones.size() < 2;

        if (!esFaseInicial && !poseeRecursos(costo)) return false;
        if (!tablero.construirPoblado(idVertice)) return false;

        Vertice vertice = tablero.obtenerVertice(idVertice);
        Construccion poblado = new Poblado(vertice);

        if (!esFaseInicial) {
            inventario.quitar(costo);
        }

        construcciones.add(poblado);
        vertice.aplicarEfectos(this);

        if (construcciones.size() == 2) {
            poblado.cosechar(-1).forEach(r -> agregarRecurso(r, 1));
        }
        vertice.establecerDueño(this);
        notificarObservadores();
        return true;
    }

    public boolean construirCiudad(Tablero tablero, int idVertice) {
        Map<Recurso, Integer> costo = Map.of(
                Recurso.GRANO, 2,
                Recurso.MINERAL, 3
        );

        if (!poseeRecursos(costo)) return false;

        Vertice vertice = tablero.obtenerVertice(idVertice);
        Construccion poblado = construcciones.stream()
                .filter(c -> c instanceof Poblado && c.obtenerVertice() == vertice)
                .findFirst()
                .orElse(null);

        if (poblado == null) return false;

        inventario.quitar(costo);
        construcciones.remove(poblado);
        construcciones.add(new Ciudad(vertice));

        notificarObservadores();
        return true;
    }

    public boolean construirCarretera(Tablero tablero, int idArista) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);
        if ( (!poseeRecursos(costo) && (this.construcciones.size() > 2 ) )){
            return false;
        }

        Arista aristaAgregar = tablero.obtenerArista(idArista);
        if (aristaAgregar.verificarOcupado()) return false;

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
            if (caminos.isEmpty()) {
                this.inventario.quitar(costo);
                notificarObservadores();
            }

            aristaAgregar.establecerDueño(this);
            aristaAgregar.ocupar();
            carreteras.add(aristaAgregar);


            List<Camino> tocadas = new ArrayList<>();
            for (Camino c : caminos) {
                if (c.conectaCon(aristaAgregar)) {
                    tocadas.add(c);
                }
            }

            Camino fusion = new Camino();
            for (Camino c : tocadas) {
                fusion.agregarCarreteras(c.getCarreteras());
            }
            fusion.agregarCarretera(aristaAgregar);

            caminos.removeAll(tocadas);
            fusion.recalcularDiametro();
            caminos.add(fusion);

            return true;
        }

        return false;
    }


    // --- MÉTRICAS ---
    public int obtenerRutaMasLarga() {
        return caminos.stream()
                .mapToInt(Camino::getDiametro)
                .max()
                .orElse(0);
    }

    public int obtenerCaballerosUsados() {
        return (int) cartasUsadas.stream().filter(c -> c instanceof Caballero).count();
    }

    public String obtenerNombre() {
        return nombre;
    }
}
