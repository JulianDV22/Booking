package controlador;

import modelo.datos.RoomData;
import modelo.entidades.Room;

import java.io.IOException;
import java.util.List;

public class RoomController {

    // Crear una habitación
    public static String createRoom(String type, List<String> characteristics, double price, String hotel, boolean available) {
        try {
            Room room = Room.build(type, characteristics, price, hotel, available);
            RoomData.createRoom(room);
            return "Habitación creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la habitación: " + e.getMessage();
        }
    }

    // Listar todas las habitaciones
    public static List<Room> findRooms() {
        try {
            return RoomData.findRooms();
        } catch (IOException e) {
            System.out.println("Error al leer las habitaciones: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar una habitación
    public static String updateRoom(Room room) {
        try {
            RoomData.updateRoom(room.getType(), room.getHotel(), room);
            return "Habitación actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la habitación: " + e.getMessage();
        }
    }

    // Eliminar una habitación
    public static String deleteRoom(String type, String hotel) {
        try {
            RoomData.deleteRoom(type, hotel);
            return "Habitación eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la habitación: " + e.getMessage();
        }
    }

    // Filtrar habitaciones por hotel y disponibilidad
    public static List<Room> filtrateRoom(String hotel, int capacityRequester) {
        try {
            return filterRoomsByCriteria(RoomData.findRooms(), hotel, capacityRequester);
        } catch (IOException e) {
            System.out.println("Error al filtrar habitaciones: " + e.getMessage());
            return List.of();
        }
    }

    private static List<Room> filterRoomsByCriteria(List<Room> rooms, String hotel, int capacityRequester) {
        return rooms.stream()
                .filter(room -> isRoomMatchingCriteria(room, hotel, capacityRequester))
                .toList();
    }

    private static boolean isRoomMatchingCriteria(Room room, String hotel, int capacityRequester) {
        return room.getHotel().equalsIgnoreCase(hotel)
                && room.isAvailable()
                && getCapacity(room.getType()) >= capacityRequester;
    }

    // Método auxiliar para obtener la capacidad según el tipo de habitación
    public static int getCapacity(String type) {
        return switch (type.toLowerCase()) {
            case "simple" -> 1;
            case "pareja" -> 2;
            case "familiar-1" -> 3; // 2 adultos y 1 niño
            case "familiar-2" -> 4; // 2 adultos y 2 niños
            case "suite" -> 5; // Suite de lujo
            default -> 0;
        };
    }

    public static List<Room> obtenerHabitacionesDisponibles(String hotelName, String roomType) {
        try {
            return RoomData.findAvailableByType(hotelName, roomType);
        } catch (IOException e) {
            System.out.println("Error al leer las habitaciones: " + e.getMessage());
            return List.of();
        }
    }
}
