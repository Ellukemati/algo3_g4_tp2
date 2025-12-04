package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jugador implements Observable, Bonificacion {

    private final String nombre;
    private final Inventario inventario;
    private final List<CartaDesarollo> cartasNuevas;
    private final List<CartaDesarollo> cartasUsables;
    private final List<CartaDesarollo> cartasUsadas;
    private final Map<Recurso, Integer> tasasDeIntercambioConBanca;
    private final List<Construccion> construcciones;
    private final List<Arista> carreteras;
    private final List<Camino> caminos;
    private int puntosDeVictoria; 
    private int puntosDeVictoriaOcultos; 

    private List<Observador> observadores = new ArrayList<>();

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.inventario = new Inventario();
        this.cartasNuevas = new ArrayList<>();
        this.cartasUsables = new ArrayList<>();
        this.cartasUsadas = new ArrayList<>();
        this.tasasDeIntercambioConBanca = new HashMap<>();
        for (Recurso r : Recurso.values()) {
            this.tasasDeIntercambioConBanca.put(r, 4);
        }
        this.construcciones = new ArrayList<>();
        this.caminos = new ArrayList<>();
        this.carreteras = new ArrayList<>();
        this.puntosDeVictoria = 0;  
        this.puntosDeVictoriaOcultos = 0; 
    }

    public Jugador() {
        this("");
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

    public List<CartaDesarollo> obtenerCartasDeDesarollo() {
        List<CartaDesarollo> cartasTotales = new ArrayList<>(cartasUsables);
        cartasTotales.addAll(cartasNuevas);
        return cartasTotales;
    }

    // --- GESTIÓN INTERNA ---

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

    // --- LÓGICA DE JUEGO ---

    public void recibirLanzamientoDeDados(int numeroDado) {
        if (numeroDado == 7) {
            this.inventario.descartarMitad();
            notificarObservadores();
            // La lógica de mover ladrón suele ser iniciada por el controlador/turno.
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

    // --- LÓGICA DE CONSTRUCCIÓN ---

    public boolean construirPoblado(Tablero tablero, int idVertice) {
        // Definir costo
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);
        costo.put(Recurso.LANA, 1);
        costo.put(Recurso.GRANO, 1);

        // Lógica de fase inicial (los primeros 2 son gratis)
        boolean esFaseInicial = (this.construcciones.size() < 2);

        // 1. Verificación de Recursos (solo si no es fase inicial)
        if (!esFaseInicial && !poseeRecursos(costo)) {
            return false;
        }

        // 2. Intentar construir en el tablero (validaciones de reglas)
        if (tablero.construirPoblado(idVertice)) {
            Vertice vertice = tablero.obtenerVertice(idVertice);
            Construccion nuevaConstruccion = new Poblado(vertice);

            // 3. Pagar (si corresponde)
            if (!esFaseInicial) {
                this.inventario.quitar(costo);
                notificarObservadores();
            }

            // 4. Actualizar estado del jugador
            construcciones.add(nuevaConstruccion);
            vertice.aplicarEfectos(this); // Activa puertos si los hay

            // Regla especial: Recibir recursos del segundo poblado inicial
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

        // Verificar que sea un Poblado antes de convertirlo
        if (construccionAActualizar instanceof Poblado) {
            this.inventario.quitar(costo);
            notificarObservadores();

            construcciones.remove(construccionAActualizar);
            construcciones.add(new Ciudad(verticeBuscado));
            sumarPuntos(1);
            return true;
        }

        return false;
    }

    public boolean construirCarretera(Tablero tablero, int idArista) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);

        boolean esGratis = carreteras.size() <= 1;

        if (!esGratis && !poseeRecursos(costo)) {
            return false;
        }

        Arista aristaAgregar = tablero.obtenerArista(idArista);
        boolean conectaConRutaPropia = false;

        if (carreteras.size() <= 1) {
            conectaConRutaPropia = true;
        } else {
            List<Arista> aristasAdyacentes = aristaAgregar.verAdyacentes();
            for (Arista aristaActual : carreteras) {
                if (aristasAdyacentes.contains(aristaActual)) {
                    conectaConRutaPropia = true;
                    break;
                }
            }
        }

        if (conectaConRutaPropia && !aristaAgregar.verificarOcupado()) {
            if (!esGratis) {
                this.inventario.quitar(costo);
                notificarObservadores();
            }
            aristaAgregar.ocupar();
            carreteras.add(aristaAgregar);
            sumarPuntos(1);

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


    public int obtenerRutaMasLarga() {
        int max = 0;
        for (Camino c : caminos) {
            max = Math.max(max, c.getDiametro());
        }
        return max;
    }
    
    public int obtenerCaballerosUsados() {
    	int contador = 0;
        for (CartaDesarollo c : cartasUsadas) {
            if (c instanceof Caballero) {
                contador++;
            }
        }
        return contador;
    }
    
    public int obtenerPuntosVictoriaOcultos() {
        return puntosDeVictoriaOcultos;
    }
    
    public void sumarPuntos(int puntos) {
        puntosDeVictoria += puntos;
    }

    public void restarPuntos(int puntos) {
        puntosDeVictoria -= puntos;
    }
    
    public int obtenerPuntage() {
        return puntosDeVictoria;
    }

    public void finalizarTurno() {
    	for (CartaDesarollo c : cartasNuevas) {
            if (c instanceof PuntoDeVictoria) {
                puntosDeVictoriaOcultos++;
            }
        }
        cartasUsables.addAll(cartasNuevas);
        cartasNuevas.clear();
    }

    public String obtenerNombre() {
        return nombre;
    }
}