package labs.al.fileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Escritor {
    public static void escribirLex(Path rutaSalida, List<String> lex) throws IOException {
        Files.write(rutaSalida, lex);
    }
    public static void escribirTablaVar(Map<String, String> vals){
        for (Map.Entry<String, String> code: vals.entrySet()){
            System.out.println(code.getKey() + ",  " + code.getValue());
        }
    }
    public static void escribirTablaDatos(Map<String, Integer> datos, String tipo){
        for (Map.Entry<String, Integer> code: datos.entrySet()){
            System.out.println(code.getKey() + ",  " + tipo + code.getValue());
        }
    }
    public static void escribirSim(Path rutaSalida, List<String> sim) throws IOException {
        Files.write(rutaSalida, sim);
    }
}
