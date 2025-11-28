package labs.op;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import labs.op.fileManager.Escritor;
import labs.op.fileManager.Lector;
import labs.op.controllers.Conversor;

public class Main {
    public static void main(String[] args) throws IOException {
        Path ruta = Paths.get("C:/Users/loque/Documents/Practicas/Compilador_FY/src/labs/op/sources/factorial.mio");
        Conversor conversor = new Conversor();
        Path output = Paths.get("C:/Users/loque/Documents/Practicas/Compilador_FY/src/labs/op/sources/factorial.lex");
        List<String> codigoBase = Lector.leerCodigo(ruta);
        List<String> codigoLex = conversor.procesar(codigoBase);
        for (String code: codigoLex){
            System.out.println(code);
        }
        Escritor.escribirLex(output, codigoLex);
    }
}
