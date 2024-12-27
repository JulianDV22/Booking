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
    private String hotel;
    private boolean disponible;

    // Constructor
    private Habitacion(String tipo, List<String> caracteristicas, double precio, String hotel, boolean disponible) {
        setTipo(tipo);
        setCaracteristicas(caracteristicas);
        setPrecio(precio);
        setHotel(hotel);
        setDisponible(disponible);
    }

    private Habitacion(final int numero) {
        setTipo(UtilTexto.EMPTY);
        setCaracteristicas(UtilObjeto.LISTA_STRING);
        setPrecio(UtilNumero.ZERO_DOUBLE);
    }

    //Builder
    public static Habitacion build(String tipo, List<String> caracteristicas, double precio, String hotel, boolean disponible){
        return new Habitacion(tipo, caracteristicas, precio, hotel, disponible);
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

    public String getHotel() {
        return hotel;
    }

    public Habitacion setHotel(String hotel) {
        this.hotel = UtilTexto.applyTrim(hotel);
        return this;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public Habitacion setDisponible(boolean disponible) {
        this.disponible = disponible;
        return this;
    }

    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(tipo); // Tipo de habitación
        row.add(String.join(", ", caracteristicas)); // Características separadas por comas
        row.add(String.valueOf(precio)); // Precio en formato de cadena
        row.add(hotel); // Nombre del hotel al que pertenece
        row.add(disponible ? "Disponible" : "Ocupada"); // Estado de disponibilidad
        return row;
    }

    public static Habitacion fromRow(List<String> row) {
        String tipo = row.get(0); // Tipo de habitación
        List<String> caracteristicas = List.of(row.get(1).split(", ")); // Características separadas por comas
        double precio = Double.parseDouble(row.get(2)); // Convertir el precio a un valor numérico
        String hotel = row.get(3); // Nombre del hotel
        boolean disponible = row.get(4).equalsIgnoreCase("Disponible"); // Convertir a booleano según el texto
        return new Habitacion(tipo, caracteristicas, precio, hotel, disponible);
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "tipo='" + tipo + '\'' +
                ", caracteristicas=" + String.join(", ", caracteristicas) +
                ", precio=" + precio +
                '}';
    }

}
