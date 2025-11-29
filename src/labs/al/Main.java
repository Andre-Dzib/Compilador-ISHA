package labs.al;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import labs.al.fileManager.Escritor;
import labs.al.fileManager.Lector;
import labs.al.controllers.Conversor;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Se requiere un argumento para el archivo a leer");
            return;
        }

        Conversor conversor = new Conversor();
        Path ruta;
        List<String> codigoBase;
        List<String> codigoLex;
        try {
            ruta = Paths.get(args[0]);
            codigoBase = Lector.leerCodigo(ruta);
            codigoLex = conversor.procesar(codigoBase);

            for (String code: codigoLex){
                System.out.println(code);
            }
        }
        catch (InvalidPathException | NoSuchFileException e){
            System.out.println("La ruta del archivo proporcionado no fue encontrada");
            return;
        }

        Path output = ruta.getParent().resolve(ruta.getFileName().toString().split("\\.")[0] + ".lex");
        Escritor.escribirLex(output, codigoLex);
    }
}
