package controlador;

import modelo.datos.HabitacionDatos;
import modelo.entidades.Habitacion;

import java.io.IOException;
import java.util.List;

public class HabitacionControlador {

    // Crear una habitación
    public static String crearHabitacion(String tipo, List<String> caracteristicas, double precio, String hotel, boolean disponible) {
        try {
            Habitacion habitacion = Habitacion.build(tipo, caracteristicas, precio, hotel, disponible);
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
    public static String actualizarHabitacion(Habitacion habitacion) {
        try {
            HabitacionDatos.actualizarHabitacion(habitacion.getTipo(), habitacion.getHotel(), habitacion);
            return "Habitación actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la habitación: " + e.getMessage();
        }
    }

    // Eliminar una habitación
    public static String eliminarHabitacion(String tipo, String hotel) {
        try {
            HabitacionDatos.eliminarHabitacion(tipo, hotel);
            return "Habitación eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la habitación: " + e.getMessage();
        }
    }

    // Filtrar habitaciones por hotel y disponibilidad
    public static List<Habitacion> filtrarHabitaciones(String hotel, int capacidadRequerida) {
        try {
            List<Habitacion> habitaciones = HabitacionDatos.leerHabitaciones();
            return habitaciones.stream()
                    .filter(h -> h.getHotel().equalsIgnoreCase(hotel)
                            && h.getDisponible()
                            && getCapacidad(h.getTipo()) >= capacidadRequerida)
                    .toList();
        } catch (IOException e) {
            System.out.println("Error al filtrar habitaciones: " + e.getMessage());
            return List.of();
        }
    }

    // Método auxiliar para obtener la capacidad según el tipo de habitación
    private static int getCapacidad(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "simple" -> 1;
            case "pareja" -> 2;
            case "familiar-1" -> 3; // 2 adultos y 1 niño
            case "familiar-2" -> 4; // 2 adultos y 2 niños
            case "suite" -> 5; // Suite de lujo
            default -> 0;
        };
    }
}
