package labs.op.controllers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversor {

    private static final Pattern IDENTIFICADORES = Pattern.compile("^[A-Za-z][A-Za-z0-9]{0,15}$");
    private static final Pattern HEXADECIMAL = Pattern.compile("^0x[0-9A-Fa-f]+$");
    private static final Pattern TEXTO = Pattern.compile("^\".*\"$");

    private static final Set<String> PALABRAS_RESERVADAS = Set.of(
            "PROGRAMA", "FINPROG", "SI","ENTONCES","SINO","FINSI",
            "MIENTRAS","HACER","FINM","IMPRIME","LEE"
    );

    private static final Set<String> OPERACIONES_ARITMETICAS = Set.of("+","-","*","/");
    private static final Set<String> OPERACIONES_REL = Set.of(">", "<", "==", ">=", "<=", "<>");

    // Tabla de símbolos y lista de errores
    private TablaSimbolo simbolos = new TablaSimbolo();
    private List<ErrorCompilacion> errores = new ArrayList<>();

    // Salida del .lex
    private List<String> lex = new ArrayList<>();

    private int numLinea = 1;


    // ================================================
    // =                PROCESAR CÓDIGO               =
    // ================================================
    public List<String> procesar(List<String> lineas) {

        for (String linea : lineas) {

            if (linea == null || linea.isBlank()) {
                numLinea++;
                continue;
            }

            List<String> tokens = tokenizar(linea);

            if (tokens.isEmpty()) {
                numLinea++;
                continue;
            }

            procesarLinea(tokens);
            numLinea++;
        }

        return lex;
    }


    // Procesar linea
    private void procesarLinea(List<String> tokens) {

        String primero = tokens.get(0);

        // PROGRAMA nombre
        if (primero.equals("PROGRAMA")) {
            lex.add("PROGRAMA");

            if (tokens.size() < 2) {
                errores.add(new ErrorCompilacion("E001", "Falta nombre del programa", numLinea));
                return;
            }

            String nombre = tokens.get(1);

            if (esIdentificador(nombre)) {
                simbolos.registrarID(nombre);
                lex.add("[id]");
            } else {
                errores.add(new ErrorCompilacion(nombre, "Nombre de programa inválido", numLinea));
            }

            return;
        }

        // FINPROG
        if (primero.equals("FINPROG")) {
            lex.add("FINPROG");
            return;
        }

        // IMPRIME
        if (primero.equals("IMPRIME")) {
            if (tokens.size() < 2) {
                errores.add(new ErrorCompilacion("E002", "Falta argumento para IMPRIME", numLinea));
                return;
            }

            String arg = tokens.get(1);

            lex.add("IMPRIME");

            if (esTextoLiteral(arg)) {
                simbolos.registrarTXT(arg);
                lex.add("[txt]");
            } else if (esIdentificador(arg)) {
                simbolos.registrarID(arg); // Verificar
                lex.add("[id]");
            } else {
                errores.add(new ErrorCompilacion(arg, "Literal o identificador inválido en IMPRIME", numLinea));
            }

            return;
        }

        // LEE id
        if (primero.equals("LEE")) {
            lex.add("LEE");

            if (tokens.size() != 2) {
                errores.add(new ErrorCompilacion("E003", "LEE requiere un identificador", numLinea));
                return;
            }

            String id = tokens.get(1);
            if (esIdentificador(id)) {
                simbolos.registrarID(id);
                lex.add("[id]");
            } else {
                errores.add(new ErrorCompilacion(id, "Identificador inválido en LEE", numLinea));
            }
            return;
        }

        // SI id < expr
        if (primero.equals("SI")) {
            if (tokens.size() < 5) {
                errores.add(new ErrorCompilacion("E004", "Estructura SI incompleta", numLinea));
                return;
            }

            lex.add("SI");

            // ID
            String id = tokens.get(1);
            if (esIdentificador(id)) {
                simbolos.registrarID(id);
                lex.add("[id]");
            }

            // operador relacional
            String opRel = tokens.get(2);
            if (OPERACIONES_REL.contains(opRel)) {
                lex.add("[op_rel]");
            } else {
                errores.add(new ErrorCompilacion(opRel, "Operador relacional inválido", numLinea));
            }

            // valor derecho (hex o id)
            String fac = tokens.get(3);
            procesarFactor(fac);

            // ENTONCES
            if (!tokens.contains("ENTONCES")) {
                errores.add(new ErrorCompilacion("E005", "Falta ENTONCES", numLinea));
            } else {
                lex.add("ENTONCES");
            }

            // Puede venir IMPRIME o nada
            if (tokens.contains("IMPRIME")) {
                lex.add("IMPRIME");
                int idx = tokens.indexOf("IMPRIME");
                if (idx + 1 < tokens.size()) {
                    String arg = tokens.get(idx + 1);
                    if (esTextoLiteral(arg)) {
                        simbolos.registrarTXT(arg);
                        lex.add("[txt]");
                    } else if (esIdentificador(arg)) {
                        simbolos.registrarID(arg);
                        lex.add("[id]");
                    }
                }
            }

            // FINSI
            if (tokens.contains("FINSI")) lex.add("FINSI");

            return;
        }

        // MIENTRAS id < expr Hacer
        if (primero.equals("MIENTRAS")) {
            if (tokens.size() < 5) {
                errores.add(new ErrorCompilacion("E006", "Estructura MIENTRAS incompleta", numLinea));
                return;
            }

            lex.add("MIENTRAS");

            String id = tokens.get(1);
            if (esIdentificador(id)) {
                simbolos.registrarID(id);
                lex.add("[id]");
            }

            String opRel = tokens.get(2);
            if (OPERACIONES_REL.contains(opRel)) {
                lex.add("[op_rel]");
            }

            procesarFactor(tokens.get(3));

            if (tokens.contains("HACER")) {
                lex.add("HACER");
            }

            return;
        }

        // FINM
        if (primero.equals("FINM")) {
            lex.add("FINM");
            return;
        }

        // Asignación: id = expr
        if (tokens.size() >= 3 && tokens.get(1).equals("=")) {
            String id = tokens.get(0);

            if (esIdentificador(id)) {
                simbolos.registrarID(id);
                lex.add("[id]");
            } else {
                errores.add(new ErrorCompilacion(id, "Identificador inválido en asignación", numLinea));
            }

            lex.add("=");

            procesarFactor(tokens.get(2));
            return;
        }

        // Nada coincide → error
        errores.add(new ErrorCompilacion(primero, "Token no reconocido", numLinea));
    }


    // ================================================
    // =                PROCESAR FACTOR               =
    // ================================================
    private void procesarFactor(String token) {

        if (esIdentificador(token)) {
            simbolos.registrarID(token);
            lex.add("[id]");
        }
        else if (esHex(token)) {
            simbolos.registrarVAL(token, token); // Error, solucionarlo más tarde
            lex.add("[val]");
        }
        else {
            errores.add(new ErrorCompilacion(token, "Factor inválido", numLinea));
        }
    }


    // ================================================
    // =                 TOKENIZADOR                  =
    // ================================================
    public List<String> tokenizar(String linea) {
        List<String> tokens = new ArrayList<>();
        boolean enComillas = false;
        StringBuilder cur = new StringBuilder();

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                enComillas = !enComillas;
                cur.append(c);
                continue;
            }

            if (enComillas) {
                cur.append(c);
                continue;
            }

            // Separadores
            if (Character.isWhitespace(c) || c == '=' || c == '<' || c == '>' || c == '+'
                    || c == '-' || c == '*' || c == '/') {

                if (cur.length() > 0) {
                    tokens.add(cur.toString());
                    cur.setLength(0);
                }

                if (c != ' ') tokens.add(String.valueOf(c));
            } else {
                cur.append(c);
            }
        }

        if (cur.length() > 0) tokens.add(cur.toString());

        return tokens;
    }


    // ================================================
    // =                VALIDADORES                   =
    // ================================================
    private boolean esIdentificador(String t) {
        Matcher m = IDENTIFICADORES.matcher(t);
        return m.matches() && !PALABRAS_RESERVADAS.contains(t);
    }

    private boolean esHex(String t) {
        return HEXADECIMAL.matcher(t).matches();
    }

    private boolean esTextoLiteral(String t) {
        return TEXTO.matcher(t).matches();
    }


    // ================================================
    // =       ACCESOS A TABLA Y ERRORES              =
    // ================================================
    public TablaSimbolo getTabla() { return simbolos; }

    public List<ErrorCompilacion> getErrores() { return errores; }

    public List<String> getLex() { return lex; }
}
