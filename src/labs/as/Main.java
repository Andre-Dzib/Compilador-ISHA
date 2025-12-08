package labs.as;

import labs.al.fileManager.Lector;
import labs.as.controllers.Conversor;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("se requiere especificar un archivo a evaluar");
            return;
        }
        Path entrada;
        boolean resultado = false;
        try {
            entrada = Paths.get(args[0]);

            List<String> lineas = Lector.leerCodigo(entrada);
            Conversor conversor = new Conversor();
            resultado = conversor.procesar(lineas);
        }
        catch (IOException e) {
            System.out.println("Error al leer el archivo");
            return;
        }
        catch (InvalidPathException e){
            System.out.println("La ruta del archivo proporcionado no fue encontrada");
            return;
        }

        if( resultado ) {
            System.out.println("Compilación exitosa");
        }
        else {
            System.out.println("Compilación fallida");
        }
    }
}
