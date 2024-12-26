package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Hotel extends Alojamiento{
    private int calificacion;
    private List<Habitacion> habitaciones;
    private List<String> actividades;

    // Constructor
    private Hotel(String nombre, double precioPorNoche, int calificacion, List<Habitacion> habitaciones, List<String> actividades) {
        super(nombre, precioPorNoche);
       setTipo("Hotel");
       setCalificacion(calificacion);
       setHabitaciones(habitaciones);
       setActividades(actividades);
    }

    private Hotel(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE);
        setCalificacion(UtilNumero.ZERO);
        setHabitaciones(UtilObjeto.LISTA_OBJETO);
        setActividades(UtilObjeto.LISTA_STRING);
    }

    //Builders
    public static Hotel build(String nombre, double precioPorNoche, int calificacion, List<Habitacion> habitaciones, List<String> actividades){
        return new Hotel(nombre, precioPorNoche, calificacion, habitaciones, actividades);
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

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    private Hotel setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = UtilObjeto.getUtilObjeto().getDefault(habitaciones, new ArrayList<>());
        return this;
    }

    public List<String> getActividades() {
        return actividades;
    }

    private Hotel setActividades(List<String> actividades) {
        this.actividades = UtilObjeto.getUtilObjeto().getDefault(actividades, new ArrayList<>());
        return this;
    }

    // Convierte un objeto Hotel a una fila de Excel (toRow)
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre());
        row.add(String.valueOf(getPrecioPorNoche()));
        row.add(String.valueOf(calificacion));
        row.add(habitacionesToString(habitaciones));
        row.add(String.join(", ", actividades)); // Convertir actividades a una cadena separada por comas
        return row;
    }

    // Crea un objeto Hotel desde una fila de Excel (fromRow)
    public static Hotel fromRow(List<String> row) {
        String nombre = row.get(0);
        double precioPorNoche = Double.parseDouble(row.get(1));
        int calificacion = Integer.parseInt(row.get(2));
        List<Habitacion> habitaciones = stringToHabitaciones(row.get(3));
        List<String> actividades = List.of(row.get(4).split(", ")); // Convertir cadena a lista
        return new Hotel(nombre, precioPorNoche, calificacion, habitaciones, actividades);
    }

    // Métodos auxiliares para convertir habitaciones
    public static String habitacionesToString(List<Habitacion> habitaciones) {
        List<String> habitacionStrings = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            habitacionStrings.add(habitacion.getTipo()); // Agregar solo el tipo de habitación
        }
        return String.join("; ", habitacionStrings); // Separar por punto y coma
    }

    public static List<Habitacion> stringToHabitaciones(String habitacionesString) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String[] habitacionTipos = habitacionesString.split("; ");
        for (String tipo : habitacionTipos) {
            habitaciones.add(Habitacion.build(tipo, new ArrayList<>(), 0.0)); // Crear objetos Habitacion básicos
        }
        return habitaciones;
    }
}
