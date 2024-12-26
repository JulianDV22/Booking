package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    private String tipo;
    private List<String> caracteristicas;
    private double precio;

    // Constructor
    private Habitacion(String tipo, List<String> caracteristicas, double precio) {
        setTipo(tipo);
        setCaracteristicas(caracteristicas);
        setPrecio(precio);
    }

    private Habitacion(final int numero) {
        setTipo(UtilTexto.EMPTY);
        setCaracteristicas(UtilObjeto.LISTA_STRING);
        setPrecio(UtilNumero.ZERO_DOUBLE);
    }

    //Builder
    public static Habitacion build(String tipo, List<String> caracteristicas, double precio){
        return new Habitacion(tipo, caracteristicas, precio);
    }

    public static Habitacion build(){
        return new Habitacion(0);
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public Habitacion setTipo(String tipo) {
        this.tipo = UtilTexto.applyTrim(tipo);
        return this;
    }

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public Habitacion setCaracteristicas(List<String> caracteristicas) {
        this.caracteristicas = UtilObjeto.getUtilObjeto().getDefault(caracteristicas, new ArrayList<>());
        return this;
    }

    public double getPrecio() {
        return precio;
    }

    public Habitacion setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    // Convierte un objeto Habitacion a una fila de Excel (toRow)
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(tipo);
        row.add(String.join(", ", caracteristicas)); // Convertir características a una cadena separada por comas
        row.add(String.valueOf(precio));
        return row;
    }

    // Crea un objeto Habitacion desde una fila de Excel (fromRow)
    public static Habitacion fromRow(List<String> row) {
        String tipo = row.get(0);
        List<String> caracteristicas = List.of(row.get(1).split(", ")); // Convertir cadena a lista
        double precio = Double.parseDouble(row.get(2));
        return new Habitacion(tipo, caracteristicas, precio);
    }
}
