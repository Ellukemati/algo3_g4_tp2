package edu.fiuba.algo3.modelo;

import java.util.*;

public class Banca {
    private Map<Recurso, Integer> recursos = new HashMap<Recurso, Integer>(){{
        put(Recurso.MADERA, 19);
        put(Recurso.LADRILLO, 19);
        put(Recurso.LANA, 19);
        put(Recurso.GRANO, 19);
        put(Recurso.MINERAL, 19);
    }};
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
    }
    public Banca() {
        inicializacion();
    }

    public CartaDesarollo comprarCartaDeDesarollo(Map<Recurso, Integer> pago) {
        List<Recurso> recurosNecezarios = new ArrayList<>(Arrays.asList(Recurso.LANA, Recurso.GRANO, Recurso.MINERAL));
        if (pago.get(Recurso.LANA) < 1 || pago.get(Recurso.GRANO) < 1 || pago.get(Recurso.MINERAL) < 1) {
            throw new IllegalArgumentException("recursos insuficientes");
        }
        for (int i = 0; i < 3; i++) {
            Recurso recurso = recurosNecezarios.get(i);
            pago.put(recurso, pago.get(recurso) - 1);
            recursos.put(recurso, recursos.get(recurso) + 1);
        }
        CartaDesarollo carta = cartaDeDesarollosDisponible.get(0);
        cartaDeDesarollosDisponible.remove(0);
        return carta;
    }

}
