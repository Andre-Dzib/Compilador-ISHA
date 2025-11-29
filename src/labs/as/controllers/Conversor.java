package labs.as.controllers;

import java.util.ArrayList;
import java.util.List;

public class Conversor {
    int line = 0;
    ArrayList<String> pila = new ArrayList<>(20);

    private void desplazar(String linea) {
        pila.add(linea);
    }

    private boolean comprobarReducir(Regla[] reglas, String siguienteToken) {
        boolean sePuede = false;

        for( Regla regla : reglas ) {
            sePuede = regla.sePuedeReducir(pila, siguienteToken);
            if( sePuede ) {
                regla.reducir(pila);
                break;
            }
        }
        return sePuede;
    }

    public boolean procesar(List<String> lineas) {
        int i = 0;
        boolean seRedujo = false;
        desplazar(lineas.get(i));
        i++;

        while(i < lineas.size()) {
            String siguienteLinea = lineas.get(i);

            switch(pila.get(pila.size() - 1)) {

                case Gramatica.FINPROG -> {
                }
                case Gramatica.SENTS -> {
                    seRedujo = Gramatica.reglaSents.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaSents.reducir(pila);
                    }
                }
                case Gramatica.SENT -> {
                    seRedujo = Gramatica.reglaSent.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaSent.reducir(pila);
                    }
                }
                case Gramatica.EXPR -> {
                    seRedujo = comprobarReducir(Gramatica.reglasExpr, siguienteLinea);
                }
                case Gramatica.FINSI -> {
                    seRedujo = comprobarReducir(Gramatica.reglasFinsi, siguienteLinea);
                }
                case Gramatica.FINM -> {
                    seRedujo = Gramatica.reglaMientras.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaMientras.reducir(pila);
                    }
                }
                case Gramatica.TXT -> {
                    seRedujo = Gramatica.reglaTXT.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaTXT.reducir(pila);
                    }
                }
                case Gramatica.ID -> {
                    seRedujo = comprobarReducir(Gramatica.reglasID, siguienteLinea);
                }
                case Gramatica.TERM -> {
                    seRedujo = comprobarReducir(Gramatica.reglasTerm, siguienteLinea);
                }
                case Gramatica.FAC -> {
                    seRedujo = comprobarReducir(Gramatica.reglasFac, siguienteLinea);
                }
                case Gramatica.VAL -> {
                    seRedujo = Gramatica.reglaVal.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaVal.reducir(pila);
                    }
                }
                case ")" -> {
                    seRedujo = Gramatica.reglaParentesis.sePuedeReducir(pila, siguienteLinea);
                    if(seRedujo) {
                        Gramatica.reglaParentesis.reducir(pila);
                    }
                }
                default -> {
                    seRedujo = false;
                }
            }

            if( ! seRedujo ) {
                desplazar(siguienteLinea);
                i++;
            }
            System.out.println(pila);
        }


        System.out.println(pila);
        if( ! Gramatica.reglaProg.sePuedeReducir(pila, pila.get(pila.size() - 1)) ) {
            return false;
        }

        Gramatica.reglaProg.reducir(pila);
        return true;
    }
}
