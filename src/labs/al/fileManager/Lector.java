package labs.al.fileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Lector {

    public static List<String> leerCodigo(Path ruta) throws IOException {

        List<String> lineasArchivo = Files.readAllLines(ruta);
        List<String> codigoLimpio = new ArrayList<>();

        for (String lineaActual : lineasArchivo) {

            if (lineaActual == null || lineaActual.isBlank()) {
                codigoLimpio.add("");
                continue;
            }

            // Quitar comentarios tipo #
            if (lineaActual.trim().startsWith("#")) continue;

            int posComentario = lineaActual.indexOf('#');
            if (posComentario >= 0) {
                lineaActual = lineaActual.substring(0, posComentario);
            }

            codigoLimpio.add(lineaActual.trim());
        }

        return codigoLimpio;
    }
}
