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
}
