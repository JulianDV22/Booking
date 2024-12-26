package modelo.datos;

import modelo.entidades.Finca;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FincaDatos {

    private static final String FILE_PATH = "fincas.xlsx";
    private static final String SHEET_NAME = "Fincas";

    // Crear una nueva finca
    public static void crearFinca(Finca finca) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Nombre", "Precio por Noche", "Tamaño"));
        }
        UtilExcel.writeRow(sheet, lastRow, finca.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las fincas
    public static List<Finca> leerFincas() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Finca> fincas = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            fincas.add(Finca.fromRow(row));
        }
        return fincas;
    }

    // Actualizar una finca
    public static void actualizarFinca(int rowIndex, Finca finca) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, finca.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una finca
    public static void eliminarFinca(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
