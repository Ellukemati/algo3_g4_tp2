package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.ControladorArista;
import edu.fiuba.algo3.controllers.ControladorVertice;
import edu.fiuba.algo3.modelo.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import edu.fiuba.algo3.modelo.Catan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistaTablero extends Pane {
    private final Tablero tablero;
    private final Catan juego;
    private final double RADIO = 40;
    private final double CENTRO_X = 400;
    private final double CENTRO_Y = 350;
    private final Map<Integer, VistaVertice> vistasVertices = new HashMap<>();
    private final double DX = RADIO * Math.sqrt(3) / 2;
    private final double DY = RADIO / 2;private final Group panelAcciones = new Group();
    private final Map<Integer, Point2D> mapaVertices = new HashMap<>();

    public VistaTablero(Catan juego) {
        this.juego = juego;
        this.tablero = juego.obtenerTablero();

        inicializarCoordenadas();
        dibujarHexagonos();
        dibujarAristas();
        dibujarVertices();
        this.getChildren().add(panelAcciones);
        this.setOnMouseClicked(e -> limpiarAcciones());
    }
    public void mostrarBotonAccion(Button boton, double x, double y) {
        limpiarAcciones();

        boton.setLayoutX(x);
        boton.setLayoutY(y);
        boton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-cursor: hand; -fx-font-size: 10px;");

        panelAcciones.getChildren().add(boton);
    }
    public void limpiarAcciones() {
        panelAcciones.getChildren().clear();
    }
    private void inicializarCoordenadas() {
        // === ANILLO EXTERIOR (0-29) ===
        mapa(0,  -3 * DX, -5 * DY);
        mapa(1,  -3 * DX, -7 * DY);
        mapa(2,  -2 * DX, -8 * DY);
        mapa(3,  -1 * DX, -7 * DY);
        mapa(4,   0 * DX, -8 * DY);
        mapa(5,   1 * DX, -7 * DY);
        mapa(6,   2 * DX, -8 * DY);
        mapa(7,   3 * DX, -7 * DY);
        mapa(8,   3 * DX, -5 * DY);
        mapa(9,   4 * DX, -4 * DY);
        mapa(10,  4 * DX, -2 * DY);
        mapa(11,  5 * DX, -1 * DY);
        mapa(12,  5 * DX,  1 * DY);
        mapa(13,  4 * DX,  2 * DY);
        mapa(14,  4 * DX,  4 * DY);
        mapa(15,  3 * DX,  5 * DY);
        mapa(16,  3 * DX,  7 * DY);
        mapa(17,  2 * DX,  8 * DY);
        mapa(18,  1 * DX,  7 * DY);
        mapa(19,  0 * DX,  8 * DY);
        mapa(20, -1 * DX,  7 * DY);
        mapa(21, -2 * DX,  8 * DY);
        mapa(22, -3 * DX,  7 * DY);
        mapa(23, -3 * DX,  5 * DY);
        mapa(24, -4 * DX,  4 * DY);
        mapa(25, -4 * DX,  2 * DY);
        mapa(26, -5 * DX,  1 * DY);
        mapa(27, -5 * DX, -1 * DY);
        mapa(28, -4 * DX, -2 * DY);
        mapa(29, -4 * DX, -4 * DY);

        // === ANILLO MEDIO (30-47) ===
        mapa(30, -2 * DX, -4 * DY);
        mapa(31, -1 * DX, -5 * DY);
        mapa(32,  0 * DX, -4 * DY);
        mapa(33,  1 * DX, -5 * DY);
        mapa(34,  2 * DX, -4 * DY);
        mapa(35,  2 * DX, -2 * DY);
        mapa(36,  3 * DX, -1 * DY);
        mapa(37,  3 * DX,  1 * DY);
        mapa(38,  2 * DX,  2 * DY);
        mapa(39,  2 * DX,  4 * DY);
        mapa(40,  1 * DX,  5 * DY);
        mapa(41,  0 * DX,  4 * DY);
        mapa(42, -1 * DX,  5 * DY);
        mapa(43, -2 * DX,  4 * DY);
        mapa(44, -2 * DX,  2 * DY);
        mapa(45, -3 * DX,  1 * DY);
        mapa(46, -3 * DX, -1 * DY);
        mapa(47, -2 * DX, -2 * DY);

        // === ANILLO INTERIOR (48-53) ===
        mapa(48,  0 * DX, -2 * DY);
        mapa(49,  1 * DX, -1 * DY);
        mapa(50,  1 * DX,  1 * DY);
        mapa(51,  0 * DX,  2 * DY);
        mapa(52, -1 * DX,  1 * DY);
        mapa(53, -1 * DX, -1 * DY);
    }

    private void mapa(int id, double xOffset, double yOffset) {
        mapaVertices.put(id, new Point2D(CENTRO_X + xOffset, CENTRO_Y + yOffset));
    }

    private void dibujarHexagonos() {
        List<Hexagono> hexagonos = tablero.obtenerHexagonos();
        Group grupo = new Group();
        int[][] axiales = {
                {0,0}, {1,-1},{1,0},{0,1},{-1,1},{-1,0},{0,-1},
                {2,-2},{2,-1},{2,0},{1,1},{0,2},{-1,2},{-2,2},{-2,1},{-2,0},{-1,-1},{0,-2},{1,-2}
        };
        double ancho = Math.sqrt(3) * RADIO;
        double alto = 2 * RADIO;

        for (int i = 0; i < hexagonos.size() && i < axiales.length; i++) {
            int q = axiales[i][0];
            int r = axiales[i][1];
            double x = CENTRO_X + RADIO * (Math.sqrt(3) * (q + r/2.0));
            double y = CENTRO_Y + RADIO * (1.5 * r);

            VistaHexagono hex = new VistaHexagono(hexagonos.get(i), RADIO);
            hex.setLayoutX(x - ancho/2);
            hex.setLayoutY(y - alto/2);
            grupo.getChildren().add(hex);
        }
        this.getChildren().add(grupo);
    }

    private void dibujarVertices() {
        for (Map.Entry<Integer, Point2D> entry : mapaVertices.entrySet()) {
            Vertice v = tablero.obtenerVertice(entry.getKey());
            Point2D p = entry.getValue();

            VistaVertice vistaV = new VistaVertice(v, p.getX(), p.getY());

            // Pasamos 'juego' y 'this' (la vista) al controlador
            vistaV.setOnMouseClicked(new ControladorVertice(tablero, v, this, juego));
            this.getChildren().add(vistaV);
            vistasVertices.put(entry.getKey(), vistaV);
        }
    }



    private void dibujarAristas() {
        for (int i = 0; i < 72; i++) {
            try {
                Arista arista = tablero.obtenerArista(i);
                List<Vertice> pares = arista.obtenerVertices();

                if (pares.size() == 2) {
                    Point2D p1 = mapaVertices.get(pares.get(0).getId());
                    Point2D p2 = mapaVertices.get(pares.get(1).getId());

                    if (p1 != null && p2 != null) {
                        VistaArista vistaArista = new VistaArista(arista, p1.getX(), p1.getY(), p2.getX(), p2.getY());

                        vistaArista.setOnMouseClicked(e -> {vistaArista.setOnMouseClicked(new ControladorArista(tablero, arista, this, juego));                        });

                        this.getChildren().add(vistaArista);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}