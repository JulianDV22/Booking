package modelo.datos;

import modelo.entidades.Hotel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelData {

    private static final String FILE_PATH = "hoteles.xlsx";
    private static final String SHEET_NAME = "Hoteles";
    private static final List<String> HEADERS = List.of("Nombre", "Precio por Noche", "Tipo", "Ciudad", "Calificación", "Actividades");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear un hotel
    public static void createHotel(Hotel hotel) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        utilExcel.writeRow(sheet, lastRow, hotel.toRow());
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los hoteles
    public static List<Hotel> findHotels() throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Hotel> hotels = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) { // Validar si la fila no es nula
                List<String> rowData = utilExcel.readRow(row);
                hotels.add(Hotel.fromRow(rowData));
            }
        }

        return hotels;
    }

    // Actualizar un hotel
    public static void updateHotel(String name, String city, Hotel newHotel) throws IOException {
        List<Hotel> hotels = findHotels();
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            if (hotel.getName().equalsIgnoreCase(name) && hotel.getCity().equalsIgnoreCase(city)) {
                Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
                Sheet sheet = workbook.getSheet(SHEET_NAME);
                utilExcel.writeRow(sheet, i + 1, newHotel.toRow());
                utilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("No se encontró el hotel con el nombre y ciudad especificados.");
    }

    // Eliminar un hotel
    public static void deleteHotel(String name, String city) throws IOException {
        List<Hotel> hotels = findHotels();
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            if (hotel.getName().equalsIgnoreCase(name) && hotel.getCity().equalsIgnoreCase(city)) {
                sheet.removeRow(sheet.getRow(i + 1));
                utilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("No se encontró el hotel con el nombre y ciudad especificados.");
    }

    // Buscar hoteles por ciudad
    public static List<Hotel> findByCity(String city) throws IOException {
        List<Hotel> hotels = findHotels();
        List<Hotel> result = new ArrayList<>();

        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(city)) {
                result.add(hotel);
            }
        }
        return result;
    }

    // Buscar hotel por nombre
    public static Hotel findByName(String name) throws IOException {
        List<Hotel> hotels = findHotels();

        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(name)) {
                return hotel;
            }
        }
        return null; // No encontrado
    }
}
