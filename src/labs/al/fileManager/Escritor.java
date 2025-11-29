package labs.al.fileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Escritor {
    public static void escribirLex(Path rutaSalida, List<String> lex) throws IOException {
        Files.write(rutaSalida, lex);
    }
}
