package modelo.datos;

import modelo.entidades.Hotel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelDatos {

    private static final String FILE_PATH = "hoteles.xlsx";
    private static final String SHEET_NAME = "Hoteles";
    private static final List<String> HEADERS = List.of("Nombre", "Precio por Noche", "Tipo", "Ciudad", "Calificación", "Actividades");

    // Crear un hotel
    public static void crearHotel(Hotel hotel) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, hotel.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los hoteles
    public static List<Hotel> leerHoteles() throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Hotel> hoteles = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) { // Validar si la fila no es nula
                List<String> rowData = UtilExcel.readRow(row);
                hoteles.add(Hotel.fromRow(rowData));
            }
        }

        return hoteles;
    }

    // Actualizar un hotel
    public static void actualizarHotel(String nombre, String ciudad, Hotel nuevoHotel) throws IOException {
        List<Hotel> hoteles = leerHoteles();
        for (int i = 0; i < hoteles.size(); i++) {
            Hotel hotel = hoteles.get(i);
            if (hotel.getNombre().equalsIgnoreCase(nombre) && hotel.getCiudad().equalsIgnoreCase(ciudad)) {
                Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
                Sheet sheet = workbook.getSheet(SHEET_NAME);
                UtilExcel.writeRow(sheet, i + 1, nuevoHotel.toRow());
                UtilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("No se encontró el hotel con el nombre y ciudad especificados.");
    }

    // Eliminar un hotel
    public static void eliminarHotel(String nombre, String ciudad) throws IOException {
        List<Hotel> hoteles = leerHoteles();
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 0; i < hoteles.size(); i++) {
            Hotel hotel = hoteles.get(i);
            if (hotel.getNombre().equalsIgnoreCase(nombre) && hotel.getCiudad().equalsIgnoreCase(ciudad)) {
                sheet.removeRow(sheet.getRow(i + 1));
                UtilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("No se encontró el hotel con el nombre y ciudad especificados.");
    }

    // Buscar hoteles por ciudad
    public static List<Hotel> buscarPorCiudad(String ciudad) throws IOException {
        List<Hotel> hoteles = leerHoteles();
        List<Hotel> resultado = new ArrayList<>();

        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().equalsIgnoreCase(ciudad)) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }

    // Buscar hotel por nombre
    public static Hotel buscarPorNombre(String nombre) throws IOException {
        List<Hotel> hoteles = leerHoteles();

        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombre)) {
                return hotel;
            }
        }
        return null; // No encontrado
    }
}
