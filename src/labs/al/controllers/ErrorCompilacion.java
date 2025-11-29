package labs.al.controllers;

public class ErrorCompilacion {
    private String codigo; // Por ejemplo, valor hexadecima 0xg8
    private String mensaje; // Numero invalido
    private int linea; // Posición de linea

    public ErrorCompilacion(String codigo, String mensaje, int linea) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.linea = linea;
    }

    public String getCodigo() {return this.codigo;}
    public String getMensaje() {return this.mensaje;}
    public int getLinea() {return this.linea;}

    @Override
    public String toString(){
        return String.format("[%s] Línea %d: %s", getMensaje(), getLinea(), getCodigo());
    }
}
