package controlador;

import modelo.datos.HabitacionDatos;
import modelo.entidades.Habitacion;

import java.io.IOException;
import java.util.List;

public class HabitacionControlador {

    // Crear una habitación
    public static String crearHabitacion(String tipo, List<String> caracteristicas, double precio) {
        try {
            Habitacion habitacion = Habitacion.build(tipo, caracteristicas, precio);
            HabitacionDatos.crearHabitacion(habitacion);
            return "Habitación creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la habitación: " + e.getMessage();
        }
    }

    // Listar todas las habitaciones
    public static List<Habitacion> listarHabitaciones() {
        try {
            return HabitacionDatos.leerHabitaciones();
        } catch (IOException e) {
            System.out.println("Error al leer las habitaciones: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar una habitación
    public static String actualizarHabitacion(int indice, Habitacion habitacion) {
        try {
            HabitacionDatos.actualizarHabitacion(indice, habitacion);
            return "Habitación actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la habitación: " + e.getMessage();
        }
    }

    // Eliminar una habitación
    public static String eliminarHabitacion(int indice) {
        try {
            HabitacionDatos.eliminarHabitacion(indice);
            return "Habitación eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la habitación: " + e.getMessage();
        }
    }
}
