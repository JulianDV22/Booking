package modelo.datos;

import modelo.entidades.Hotel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelDatos {

    private static final String FILE_PATH = "hoteles.xlsx";
    private static final String SHEET_NAME = "Hoteles";

    // Crear un nuevo hotel
    public static void crearHotel(Hotel hotel) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Nombre", "Precio por Noche", "Calificación", "Habitaciones", "Actividades"));
        }
        UtilExcel.writeRow(sheet, lastRow, hotel.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los hoteles
    public static List<Hotel> leerHoteles() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Hotel> hoteles = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            hoteles.add(Hotel.fromRow(row));
        }
        return hoteles;
    }

    // Actualizar un hotel
    public static void actualizarHotel(int rowIndex, Hotel hotel) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, hotel.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un hotel
    public static void eliminarHotel(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
