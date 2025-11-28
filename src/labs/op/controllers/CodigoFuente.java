package labs.op.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodigoFuente {
    private static final Pattern IDENTIFICADORES = Pattern.compile("^[A-Za-z][A-Za-z0-9]{0,15}$");
    private static final Pattern HEXADECIMAL = Pattern.compile("^0x[0-9A-Fa-f]+$");
    private static final Pattern TEXTO = Pattern.compile("^\".*\"$");
    // Palabras reservadas
    private static final Set<String> PALABRAS_RESERVADAS = Set.of("PROGRAMA", "FINPROG", "SI", "ENTONCES", "SINO", "FINSI", "MIENTRAS", "HACER", "FINM", "IMPRIME", "LEE" );
    private static final Set<String> OPERACIONES_REL = Set.of(">", "<", "==", ">=", "<=", "<>");
    private static final Set<String> OPERACIONES_ARITMETICAS = Set.of("+","-","*","/");
    // Para guardar datos dsp
    private List<ErrorCompilacion> errores = new ArrayList<>();
    private HashMap<String, Integer> salida = new HashMap<>();
    private TablaSimbolo tabla = new TablaSimbolo();
    private int linea = 1;

    public void conversor(List<String> codeBase){

        for(String lnActual: codeBase){
            if (lnActual == null || lnActual.isEmpty()){continue;} // Que este vacio o no tenga nada
            List<String> tokens = tokenizar(lnActual);
            for (String token: tokens){
                if (PALABRAS_RESERVADAS.contains(token)){nuevoLex(token);}
                if (OPERACIONES_REL.contains(token) || OPERACIONES_ARITMETICAS.contains(token)) nuevoLex(token);
            }
        }
    }
    private void nuevoLex(String token){
        salida.put(token, linea);
        linea++;
    }
    public List<String> tokenizar(String linea){
        List<String> tokens = new ArrayList<>();
        boolean enComillas = false;
        StringBuilder cur = new StringBuilder();
        for(int i = 0; i < linea.length(); i++){
            char c = linea.charAt(i);
            if (c == '"'){ // Ciclo para comillas
                cur.append(c);
                enComillas = !enComillas;
                if (!enComillas){
                    tokens.add(cur.toString());
                    cur.setLength(0);
                }
            }
            if (enComillas) { cur.append(c); }
            else {
                if (Character.isWhitespace(c) || cur.length() > 0) {
                    tokens.add(cur.toString());
                    cur.setLength(0);
                }
                else {
                    if (c == '<')
                    if (OPERACIONES_ARITMETICAS.contains(c)){
                        tokens.add(cur.toString());
                        cur.setLength(0);
                    }
                }
            }
        }
        return tokens;
    }
    public void validarHex(){
        // Valida que sea un hexadecimal
    }
    public void validarTextos(){
        // Tomar en cuenta que espacios en blanco pueden estar aún dentro de texto
        // Ejemplo: "Hola causa =>="
    }
    // Validar otros

}
