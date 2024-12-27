package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Hotel extends Alojamiento{
    private int calificacion;
    private List<String> actividades;

    // Constructor
    private Hotel(String nombre, double precioPorNoche, String ciudad, int calificacion, List<String> actividades) {
        super(nombre, precioPorNoche, ciudad);
       setTipo("Hotel");
       setCalificacion(calificacion);
       setActividades(actividades);
    }

    private Hotel(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE, UtilTexto.EMPTY);
        setTipo("Hotel");
        setCalificacion(UtilNumero.ZERO);
        setActividades(UtilObjeto.LISTA_STRING);
    }

    //Builders
    public static Hotel build(String nombre, double precioPorNoche, String ciudad, int calificacion, List<String> actividades){
        return new Hotel(nombre, precioPorNoche, ciudad, calificacion, actividades);
    }

    public static Hotel build(){
        return new Hotel(0);
    }

    @Override
    public String obtenerDescripcion() {
        return "Hotel: " + getNombre() + ", Calificación: " + calificacion;
    }

    // Getters y Setters
    public int getCalificacion() {
        return calificacion;
    }

    private Hotel setCalificacion(int calificacion) {
        this.calificacion = calificacion;
        return this;
    }

    public List<String> getActividades() {
        return actividades;
    }

    private Hotel setActividades(List<String> actividades) {
        this.actividades = UtilObjeto.getUtilObjeto().getDefault(actividades, new ArrayList<>());
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre()); // Nombre del hotel
        row.add(String.valueOf(getPrecioPorNoche())); // Precio por noche
        row.add(getTipo()); // Tipo (siempre "Hotel")
        row.add(getCiudad()); // Ciudad
        row.add(String.valueOf(calificacion)); // Calificación del hotel
        row.add(String.join(", ", actividades)); // Actividades separadas por comas
        return row;
    }

    // Método auxiliar para convertir fila a objeto Hotel
    public static Hotel fromRow(List<String> row) {
        if (row.size() < 6) {
            throw new IllegalArgumentException("La fila no tiene suficientes datos: " + row);
        }

        String nombre = row.get(0); // Nombre del hotel
        double precioPorNoche = Double.parseDouble(row.get(1)); // Precio por noche
        String ciudad = row.get(3); // Ciudad
        int calificacion = Integer.parseInt(row.get(4)); // Calificación del hotel
        List<String> actividades = List.of(row.get(5).split(", ")); // Actividades separadas por comas

        return Hotel.build(nombre, precioPorNoche, ciudad, calificacion, actividades);
    }



    @Override
    public String toString() {
        return "Hotel{" +
                "nombre='" + getNombre() + '\'' +
                ", precioPorNoche=" + getPrecioPorNoche() +
                ", tipo='" + getTipo() + '\'' +
                ", ciudad='" + getCiudad() + '\'' +
                ", calificacion=" + calificacion +
                " habitaciones, actividades=" + String.join(", ", actividades) +
                '}';
    }

}
