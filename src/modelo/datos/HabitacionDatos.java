package modelo.datos;

import modelo.entidades.Habitacion;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDatos {

    private static final String FILE_PATH = "habitaciones.xlsx";
    private static final String SHEET_NAME = "Habitaciones";

    // Crear una nueva habitación
    public static void crearHabitacion(Habitacion habitacion) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Tipo", "Características", "Precio"));
        }
        UtilExcel.writeRow(sheet, lastRow, habitacion.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las habitaciones
    public static List<Habitacion> leerHabitaciones() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Habitacion> habitaciones = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            habitaciones.add(Habitacion.fromRow(row));
        }
        return habitaciones;
    }

    // Actualizar una habitación
    public static void actualizarHabitacion(int rowIndex, Habitacion habitacion) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, habitacion.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una habitación
    public static void eliminarHabitacion(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
