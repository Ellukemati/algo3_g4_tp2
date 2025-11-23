package edu.fiuba.algo3.modelo;

import java.util.*;

public class Banca {
    /*private Map<Recurso, Integer> recursos = new HashMap<Recurso, Integer>(){{
        put(Recurso.MADERA, 19);
        put(Recurso.LADRILLO, 19);
        put(Recurso.LANA, 19);
        put(Recurso.GRANO, 19);
        put(Recurso.MINERAL, 19);
    }};*/
    private Inventario inventario = new Inventario();
    private List<CartaDesarollo> cartaDeDesarollosDisponible = new ArrayList<>();

    private void inicializacion() {
        Map<Carta , Integer> cartaDesarollo = new HashMap<>();
        cartaDesarollo.put(Carta.CABALLERO, 14);
        cartaDesarollo.put(Carta.PUNTODEVICTORIA, 5);
        cartaDesarollo.put(Carta.CONSTRUCCIONDECARRETERA, 2);
        cartaDesarollo.put(Carta.DESCUBRIMIENTO, 2);
        cartaDesarollo.put(Carta.MONOPOLIO, 2);

        for (Map.Entry<Carta, Integer> entrada : cartaDesarollo.entrySet()) {
            Integer valor = entrada.getValue();
            for (int i = 1; i < valor; i++) {
                cartaDeDesarollosDisponible.add(CartaDesarolloFactory.crearCartaDesarollo(entrada.getKey()));
            }
        }
        Collections.shuffle(cartaDeDesarollosDisponible);
        for (Recurso recurso : Recurso.values()) {
            inventario.agregar(recurso, 19);
        }

    }
    public Banca() {
        inicializacion();
    }

    public CartaDesarollo comprarCartaDeDesarollo(Inventario pago) {
        /*if (pago.get(Recurso.LANA) < 1 || pago.get(Recurso.GRANO) < 1 || pago.get(Recurso.MINERAL) < 1) {
            throw new IllegalArgumentException("recursos insuficientes");
        }*/
        Map<Recurso, Integer> coste = new HashMap<>();
        coste.put(Recurso.LANA, 1);
        coste.put(Recurso.GRANO, 1);
        coste.put(Recurso.MINERAL, 1);
        if (!pago.poseeSuficientes(coste)) {
            throw new IllegalArgumentException("recursos insuficientes");
        }

        for (Map.Entry<Recurso, Integer> recurso : coste.entrySet()) {
            pago.quitar(recurso.getKey(), recurso.getValue());
            inventario.agregar(recurso.getKey(), recurso.getValue());
        }
        CartaDesarollo carta = cartaDeDesarollosDisponible.get(0);
        cartaDeDesarollosDisponible.remove(0);
        return carta;
    }

}
