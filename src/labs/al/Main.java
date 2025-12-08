package labs.al;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import labs.al.controllers.TablaSimbolo;
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
        Path ruta, rutaLex, rutaSim, rutaError;
        List<String> codigoBase;
        List<String> codigoLex;
        try {
            // ruta actual src.labs.al.main
            // ruta del documento src.sources.tests donde esta factorial.mio
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
        rutaLex = ruta.getParent().resolve(ruta.getFileName().toString().replaceFirst("\\.[^.]+$", "") + ".lex");
        rutaSim = ruta.getParent().resolve(ruta.getFileName().toString().replaceFirst("\\.[^.]+$", "") + ".sim");
        rutaError = ruta.getParent().resolve(ruta.getFileName().toString().replaceFirst("\\.[^.]+$", "") + ".txt");
        Escritor.escribirLex(rutaLex, codigoLex);
        Escritor.escribirSim(rutaError, conversor.getListErrores());
        Escritor.escribirSim(rutaSim, conversor.getTabla().lineal());
        Escritor.escribirTablaVar(conversor.getTabla().getVal());
        Escritor.escribirTablaDatos(conversor.getTabla().getIds(), "ID");
        Escritor.escribirTablaDatos(conversor.getTabla().getTxt(), "TXT");
    }
}
