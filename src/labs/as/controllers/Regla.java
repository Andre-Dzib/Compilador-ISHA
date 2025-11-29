package labs.as.controllers;

import java.util.List;

public class Regla {
    final String[] expresiones;
    final String resultado;
    condicional condicion = ((previo, Siguiente) -> true);
    final boolean condicionado;

    Regla(String resultado, String[] expresiones) {
        this.expresiones = expresiones;
        this.resultado = resultado;
        condicionado = false;
    }


    Regla(String resultado, String[] expresiones, condicional condicion) {
        this.expresiones = expresiones;
        this.resultado = resultado;
        this.condicion = condicion;
        condicionado = true;
    }


    boolean sePuedeReducir(List<String> stack, String siguienteToken) {
        if( ( stack.size() < expresiones.length && ! condicionado ) || ( stack.size() < expresiones.length + 1 && condicionado ) ) return false;

        boolean resultado = true;
        int j = expresiones.length - 1;
        for(int i = stack.size() - 1; stack.size() - expresiones.length <= i; i--) {
            if( ! stack.get(i).contentEquals(expresiones[j]) ) {
                resultado = false;
                break;
            }
            j--;
        }

        if(condicionado) {
            String tope = stack.get(stack.size() - expresiones.length - 1);
            resultado = resultado && condicion.comprobar(tope, siguienteToken);
        }

        return resultado;
    }

    void reducir(List<String> stack) {
        for(int i = 0; i < expresiones.length; i++) {
            stack.remove(stack.size() - 1);
        }

        stack.add(resultado);
    }
}
