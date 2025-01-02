package controlador;

import modelo.datos.HotelData;
import modelo.entidades.Hotel;

import java.io.IOException;
import java.util.List;

public class HotelController {

    // Crear un hotel
    public static String createHotel(String name, double pricePerNight, String city, int rating, List<String> activities) {
        try {
            Hotel hotel = Hotel.build(name, pricePerNight, city, rating, activities);
            HotelData.createHotel(hotel);
            return "Hotel creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el hotel: " + e.getMessage();
        }
    }

    // Listar todos los hoteles
    public static List<Hotel> findHotels() {
        try {
            return HotelData.findHotels();
        } catch (IOException e) {
            System.out.println("Error al listar los hoteles: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un hotel
    public static String updateHotel(String name, String city, String newName, double newPricePerNight, int newRating, List<String> newActivities) {
        try {
            Hotel newHotel = Hotel.build(newName, newPricePerNight, city, newRating, newActivities);
            HotelData.updateHotel(name, city, newHotel);
            return "Hotel actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el hotel: " + e.getMessage();
        }
    }

    // Eliminar un hotel
    public static String deleteHotel(String name, String city) {
        try {
            HotelData.deleteHotel(name, city);
            return "Hotel eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el hotel: " + e.getMessage();
        }
    }

    // Buscar hoteles por ciudad
    public static List<Hotel> findHotelByCity(String city) {
        try {
            return HotelData.findByCity(city);
        } catch (IOException e) {
            System.out.println("Error al buscar hoteles por ciudad: " + e.getMessage());
            return List.of();
        }
    }

    // Buscar hotel por nombre
    public static Hotel findHotelByName(String name) {
        try {
            return HotelData.findByName(name);
        } catch (IOException e) {
            System.out.println("Error al buscar el hotel por nombre: " + e.getMessage());
            return null;
        }
    }
}

