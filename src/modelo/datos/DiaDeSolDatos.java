package modelo.datos;

import modelo.entidades.DiaDeSol;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiaDeSolDatos {

    private static final String FILE_PATH = "dias_de_sol.xlsx";
    private static final String SHEET_NAME = "Dias de Sol";

    // Crear un nuevo día de sol
    public static void crearDiaDeSol(DiaDeSol diaDeSol) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Nombre", "Precio por Noche", "Actividades"));
        }
        UtilExcel.writeRow(sheet, lastRow, diaDeSol.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los días de sol
    public static List<DiaDeSol> leerDiasDeSol() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<DiaDeSol> diasDeSol = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            diasDeSol.add(DiaDeSol.fromRow(row));
        }
        return diasDeSol;
    }

    // Actualizar un día de sol
    public static void actualizarDiaDeSol(int rowIndex, DiaDeSol diaDeSol) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, diaDeSol.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un día de sol
    public static void eliminarDiaDeSol(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
