package modelo.entidades;

import java.util.List;

public class Hotel extends Alojamiento{
    private int calificacion; // Del 1 al 5
    private List<Habitacion> habitaciones;
    private List<String> actividades;

    // Constructor
    public Hotel(String nombre, double precioPorNoche, int calificacion, List<Habitacion> habitaciones, List<String> actividades) {
        super(nombre, precioPorNoche);
        this.calificacion = calificacion;
        this.habitaciones = habitaciones;
        this.actividades = actividades;
    }

    @Override
    public String obtenerDescripcion() {
        return "Hotel: " + getNombre() + ", Calificación: " + calificacion;
    }

    // Getters y Setters
    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<String> getActividades() {
        return actividades;
    }

    public void setActividades(List<String> actividades) {
        this.actividades = actividades;
    }
}
