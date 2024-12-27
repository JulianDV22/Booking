package controlador;

import modelo.datos.HotelDatos;
import modelo.entidades.Hotel;

import java.io.IOException;
import java.util.List;

public class HotelControlador {

    // Crear un hotel
    public static String crearHotel(String nombre, double precioPorNoche, String ciudad, int calificacion, List<String> actividades) {
        try {
            Hotel hotel = Hotel.build(nombre, precioPorNoche, ciudad, calificacion, actividades);
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
            System.out.println("Error al listar los hoteles: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un hotel
    public static String actualizarHotel(String nombre, String ciudad, String nuevoNombre, double nuevoPrecioPorNoche, int nuevaCalificacion, List<String> nuevasActividades) {
        try {
            Hotel nuevoHotel = Hotel.build(nuevoNombre, nuevoPrecioPorNoche, ciudad, nuevaCalificacion, nuevasActividades);
            HotelDatos.actualizarHotel(nombre, ciudad, nuevoHotel);
            return "Hotel actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el hotel: " + e.getMessage();
        }
    }

    // Eliminar un hotel
    public static String eliminarHotel(String nombre, String ciudad) {
        try {
            HotelDatos.eliminarHotel(nombre, ciudad);
            return "Hotel eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el hotel: " + e.getMessage();
        }
    }

    // Buscar hoteles por ciudad
    public static List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        try {
            return HotelDatos.buscarPorCiudad(ciudad);
        } catch (IOException e) {
            System.out.println("Error al buscar hoteles por ciudad: " + e.getMessage());
            return List.of();
        }
    }

    // Buscar hotel por nombre
    public static Hotel buscarHotelPorNombre(String nombre) {
        try {
            return HotelDatos.buscarPorNombre(nombre);
        } catch (IOException e) {
            System.out.println("Error al buscar el hotel por nombre: " + e.getMessage());
            return null;
        }
    }
}

