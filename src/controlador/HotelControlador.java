package controlador;

import modelo.datos.HotelDatos;
import modelo.entidades.Habitacion;
import modelo.entidades.Hotel;

import java.io.IOException;
import java.util.List;

public class HotelControlador {

    // Crear un hotel
    public static String crearHotel(String nombre, double precioPorNoche, int calificacion, List<Habitacion> habitaciones, List<String> actividades) {
        try {
            Hotel hotel = Hotel.build(nombre, precioPorNoche, calificacion, habitaciones, actividades);
            HotelDatos.crearHotel(hotel);
            return "Hotel creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el hotel: " + e.getMessage();
        }
    }

    // Listar todos los hoteles
    public static List<Hotel> listarHoteles() {
        try {
            return HotelDatos.leerHoteles();
        } catch (IOException e) {
            System.out.println("Error al leer los hoteles: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un hotel
    public static String actualizarHotel(int indice, Hotel hotel) {
        try {
            HotelDatos.actualizarHotel(indice, hotel);
            return "Hotel actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el hotel: " + e.getMessage();
        }
    }

    // Eliminar un hotel
    public static String eliminarHotel(int indice) {
        try {
            HotelDatos.eliminarHotel(indice);
            return "Hotel eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el hotel: " + e.getMessage();
        }
    }
}
