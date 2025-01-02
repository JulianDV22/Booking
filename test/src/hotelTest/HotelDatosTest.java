package hotelTest;

import modelo.datos.HotelData;
import modelo.entidades.Hotel;

import java.io.IOException;
import java.util.List;

public class HotelDatosTest {

    public static void main(String[] args) {
        try {
            System.out.println("=== Test HotelDatos ===");

            // Crear Hoteles
            System.out.println("\n--- Crear Hoteles ---");
            Hotel hotel1 = Hotel.build("Hotel Sunrise", 300.0, "Cartagena", 5, List.of("Piscina", "Spa", "Playa privada"));
            Hotel hotel2 = Hotel.build("Hotel Andes", 250.0, "Bogota", 4, List.of("Gimnasio", "Salón de eventos", "Restaurante gourmet"));
            Hotel hotel3 = Hotel.build("Hotel Pacific", 200.0, "Medellin", 3, List.of("Terraza", "Bar", "WiFi gratis"));

            HotelData.createHotel(hotel1);
            HotelData.createHotel(hotel2);
            HotelData.createHotel(hotel3);
            System.out.println("Hoteles creados exitosamente.");

            // Leer todos los Hoteles
            System.out.println("\n--- Leer Todos los Hoteles ---");
            List<Hotel> hoteles = HotelData.findHotels();
            for (Hotel hotel : hoteles) {
                System.out.println(hotel);
            }

            // Buscar Hoteles por Ciudad
            System.out.println("\n--- Buscar Hoteles por Ciudad (Bogota) ---");
            List<Hotel> hotelesBogota = HotelData.findByCity("Bogota");
            for (Hotel hotel : hotelesBogota) {
                System.out.println(hotel);
            }

            // Buscar Hotel por Nombre
            System.out.println("\n--- Buscar Hotel por Nombre (Hotel Andes) ---");
            Hotel hotelAndes = HotelData.findByName("Hotel Andes");
            if (hotelAndes != null) {
                System.out.println("Encontrado: " + hotelAndes);
            } else {
                System.out.println("No se encontró el Hotel Andes.");
            }

            // Actualizar Hotel
            System.out.println("\n--- Actualizar Hotel (Hotel Andes) ---");
            Hotel hotelActualizado = Hotel.build("Hotel Andes", 275.0, "Bogota", 5, List.of("Piscina climatizada", "Gimnasio", "Restaurante gourmet"));
            HotelData.updateHotel("Hotel Andes", "Bogota", hotelActualizado);
            System.out.println("Hotel Andes actualizado.");
            List<Hotel> hotelesActualizados = HotelData.findHotels();
            for (Hotel hotel : hotelesActualizados) {
                System.out.println(hotel);
            }

            // Eliminar Hotel
            System.out.println("\n--- Eliminar Hotel (Hotel Pacific) ---");
            HotelData.deleteHotel("Hotel Pacific", "Medellin");
            System.out.println("Hotel Pacific eliminado.");
            List<Hotel> hotelesDespuesEliminacion = HotelData.findHotels();
            for (Hotel hotel : hotelesDespuesEliminacion) {
                System.out.println(hotel);
            }

        } catch (IOException e) {
            System.out.println("Error en el test: " + e.getMessage());
        }
    }
}
