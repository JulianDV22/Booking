package modelo.entidades;

import util.UtilNumber;
import util.UtilObject;
import util.UtilText;

import java.util.ArrayList;
import java.util.List;

public class Hotel extends Accommodation {
    private Integer calificacion;
    private List<String> actividades;
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private Hotel(String nombre, double precioPorNoche, String ciudad, int calificacion, List<String> actividades) {
        super(nombre, precioPorNoche, ciudad);
       setType("Hotel");
       setCalificacion(calificacion);
       setActividades(actividades);
    }

    private Hotel(final int numero) {
        super(utilText.EMPTY, utilNumber.ZERO_DOUBLE, utilText.EMPTY);
        setType("Hotel");
        setCalificacion(utilNumber.ZERO);
        setActividades(utilObject.LISTA_STRING);
    }

    //Builders
    public static Hotel build(String nombre, double precioPorNoche, String ciudad, int calificacion, List<String> actividades){
        return new Hotel(nombre, precioPorNoche, ciudad, calificacion, actividades);
    }

    public static Hotel build(){
        return new Hotel(0);
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
        this.actividades = utilObject.getUtilObjeto().getDefault(actividades, new ArrayList<>());
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getName()); // Nombre del hotel
        row.add(String.valueOf(getPricePerNight())); // Precio por noche
        row.add(getType()); // Tipo (siempre "Hotel")
        row.add(getCity()); // Ciudad
        row.add(String.valueOf(calificacion)); // Calificación del hotel
        row.add(String.join(", ", actividades)); // Actividades separadas por comas
        return row;
    }

    // Método auxiliar para convertir fila a objeto Hotel
    public static Hotel fromRow(List<String> row) {
        if (row.size() > 6) {
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
                "nombre='" + getName() + '\'' +
                ", precioPorNoche=" + getPricePerNight() +
                ", tipo='" + getType() + '\'' +
                ", ciudad='" + getCity() + '\'' +
                ", calificacion=" + calificacion +
                " habitaciones, actividades=" + String.join(", ", actividades) +
                '}';
    }

}
