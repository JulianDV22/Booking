package modelo.datos;

import modelo.entidades.Room;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomData {

    private static final String FILE_PATH = "habitaciones.xlsx";
    private static final String SHEET_NAME = "Habitaciones";
    private static final List<String> HEADERS = List.of("Tipo", "Características", "Precio", "Hotel", "Disponible");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear una habitación
    public static void createRoom(Room room) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        utilExcel.writeRow(sheet, lastRow, room.toRow());
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las habitaciones
    public static List<Room> findRooms() throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                rooms.add(Room.fromRow(rowData));
            }
        }

        return rooms;
    }

    // Actualizar una habitación
    public static void updateRoom(String type, String hotel, Room newRoom) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        if (extracted(type, hotel, newRoom, sheet, workbook)) return;

        throw new IOException("Habitación no encontrada para actualizar.");
    }

    private static boolean extracted(String type, String hotel, Room newRoom, Sheet sheet, Workbook workbook) throws IOException {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                Room room = Room.fromRow(rowData);
                if (extracted(type, hotel, newRoom, sheet, workbook, room, i)) return true;
            }
        }
        return false;
    }

    private static boolean extracted(String type, String hotel, Room newRoom, Sheet sheet, Workbook workbook, Room room, int i) throws IOException {
        if (room.getType().equalsIgnoreCase(type) && room.getHotel().equalsIgnoreCase(hotel)) {
            utilExcel.writeRow(sheet, i, newRoom.toRow());
            utilExcel.saveWorkbook(workbook, FILE_PATH);
            return true;
        }
        return false;
    }

    // Eliminar una habitación
    public static void deleteRoom(String type, String hotel) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        if (extracted(type, hotel, sheet, workbook)) return;

        throw new IOException("Habitación no encontrada para eliminar.");
    }

    private static boolean extracted(String type, String hotel, Sheet sheet, Workbook workbook) throws IOException {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                Room room = Room.fromRow(rowData);
                if (extracted(type, hotel, sheet, workbook, room, row)) return true;
            }
        }
        return false;
    }

    private static boolean extracted(String type, String hotel, Sheet sheet, Workbook workbook, Room room, Row row) throws IOException {
        if (room.getType().equalsIgnoreCase(type) && room.getHotel().equalsIgnoreCase(hotel)) {
            sheet.removeRow(row);
            utilExcel.saveWorkbook(workbook, FILE_PATH);
            return true;
        }
        return false;
    }

    // Buscar habitaciones por hotel
    public static List<Room> findByHotel(String hotel) throws IOException {
        List<Room> rooms = findRooms();
        List<Room> result = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getHotel().equalsIgnoreCase(hotel)) {
                result.add(room);
            }
        }

        return result;
    }

    // Buscar habitaciones disponibles por type
    public static List<Room> findAvailableByType(String hotelName, String roomType) throws IOException {
        return findRooms().stream()
                .filter(room -> room.getHotel().equalsIgnoreCase(hotelName))
                .filter(room -> room.getType().equalsIgnoreCase(roomType))
                .filter(Room::isAvailable)
                .toList();
    }

    // Filtrar habitaciones por capacidad, disponibilidad y hotel
    public static List<Room> filtrarHabitaciones(List<Room> rooms, int capacityRequester, String hotel) {
        return rooms.stream()
                .filter(room -> room.getHotel().equalsIgnoreCase(hotel) && room.isAvailable() && getCapacidad(room.getType()) >= capacityRequester)
                .toList();
    }

    // Método auxiliar para obtener la capacidad según el tipo de habitación
    private static int getCapacidad(String type) {
        return switch (type.toLowerCase()) {
            case "simple" -> 1;
            case "pareja" -> 2;
            case "familiar-1" -> 3; // 2 adultos y 1 niño
            case "familiar-2" -> 4; // 2 adultos y 2 niños
            case "suite" -> 5; // Ejemplo para suites de lujo
            default -> 0;
        };
    }
}
