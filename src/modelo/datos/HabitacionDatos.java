package modelo.datos;

import modelo.entidades.Habitacion;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDatos {

    private static final String FILE_PATH = "habitaciones.xlsx";
    private static final String SHEET_NAME = "Habitaciones";
    private static final List<String> HEADERS = List.of("Tipo", "Características", "Precio", "Hotel", "Disponible");

    // Crear una habitación
    public static void crearHabitacion(Habitacion habitacion) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, habitacion.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las habitaciones
    public static List<Habitacion> leerHabitaciones() throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Habitacion> habitaciones = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                habitaciones.add(Habitacion.fromRow(rowData));
            }
        }

        return habitaciones;
    }

    // Actualizar una habitación
    public static void actualizarHabitacion(String tipo, String hotel, Habitacion nuevaHabitacion) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                Habitacion habitacion = Habitacion.fromRow(rowData);
                if (habitacion.getTipo().equalsIgnoreCase(tipo) && habitacion.getHotel().equalsIgnoreCase(hotel)) {
                    UtilExcel.writeRow(sheet, i, nuevaHabitacion.toRow());
                    UtilExcel.saveWorkbook(workbook, FILE_PATH);
                    return;
                }
            }
        }

        throw new IOException("Habitación no encontrada para actualizar.");
    }

    // Eliminar una habitación
    public static void eliminarHabitacion(String tipo, String hotel) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                Habitacion habitacion = Habitacion.fromRow(rowData);
                if (habitacion.getTipo().equalsIgnoreCase(tipo) && habitacion.getHotel().equalsIgnoreCase(hotel)) {
                    sheet.removeRow(row);
                    UtilExcel.saveWorkbook(workbook, FILE_PATH);
                    return;
                }
            }
        }

        throw new IOException("Habitación no encontrada para eliminar.");
    }

    // Buscar habitaciones por hotel
    public static List<Habitacion> buscarPorHotel(String hotel) throws IOException {
        List<Habitacion> habitaciones = leerHabitaciones();
        List<Habitacion> resultado = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getHotel().equalsIgnoreCase(hotel)) {
                resultado.add(habitacion);
            }
        }

        return resultado;
    }

    // Buscar habitaciones disponibles por tipo
    public static List<Habitacion> buscarDisponiblesPorTipo(String tipo) throws IOException {
        List<Habitacion> habitaciones = leerHabitaciones();
        List<Habitacion> resultado = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equalsIgnoreCase(tipo) && habitacion.getDisponible()) {
                resultado.add(habitacion);
            }
        }

        return resultado;
    }

    // Filtrar habitaciones por capacidad, disponibilidad y hotel
    public static List<Habitacion> filtrarHabitaciones(List<Habitacion> habitaciones, int capacidadRequerida, String hotel) {
        return habitaciones.stream()
                .filter(h -> h.getHotel().equalsIgnoreCase(hotel) && h.getDisponible() && getCapacidad(h.getTipo()) >= capacidadRequerida)
                .toList();
    }

    // Método auxiliar para obtener la capacidad según el tipo de habitación
    private static int getCapacidad(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "simple" -> 1;
            case "pareja" -> 2;
            case "familiar-1" -> 3; // 2 adultos y 1 niño
            case "familiar-2" -> 4; // 2 adultos y 2 niños
            case "suite" -> 5; // Ejemplo para suites de lujo
            default -> 0;
        };
    }
}
