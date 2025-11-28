package labs.op.fileManager;

import labs.op.controllers.ErrorCompilacion;
import labs.op.controllers.TablaSimbolo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Escritor {
    public static void escribirLex(Path rutaSalida, List<String> lex) throws IOException {
        Files.write(rutaSalida, lex);
    }
}
