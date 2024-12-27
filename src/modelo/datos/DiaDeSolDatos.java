package modelo.datos;

import modelo.entidades.DiaDeSol;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiaDeSolDatos {

    private static final String FILE_PATH = "dias_de_sol.xlsx";
    private static final String SHEET_NAME = "DiasDeSol";
    private static final List<String> HEADERS = List.of("Nombre", "PrecioPorNoche", "Tipo", "Ciudad", "Actividades", "IncluyeAlmuerzo", "IncluyeRefrigerio");

    // Crear un día de sol
    public static void crearDiaDeSol(DiaDeSol diaDeSol) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, diaDeSol.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los días de sol
    public static List<DiaDeSol> leerDiasDeSol() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<DiaDeSol> diasDeSol = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Salta la fila de encabezado
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) { // Verifica que la fila no sea null
                List<String> row = UtilExcel.readRow(currentRow);
                diasDeSol.add(DiaDeSol.fromRow(row)); // Convierte cada fila en un objeto `DiaDeSol`
            }
        }

        return diasDeSol;
    }


    // Actualizar un día de sol
    public static void actualizarDiaDeSol(int rowIndex, DiaDeSol diaDeSol) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        UtilExcel.writeRow(sheet, rowIndex, diaDeSol.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un día de sol
    public static void eliminarDiaDeSol(int rowIndex) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Buscar días de sol por ciudad
    public static List<DiaDeSol> buscarPorCiudad(String ciudad) throws IOException {
        // Cargar todos los Días de Sol desde el archivo Excel
        List<DiaDeSol> diasDeSol = leerDiasDeSol();
        List<DiaDeSol> resultado = new ArrayList<>();

        for (DiaDeSol dia : diasDeSol) {
            // Verificar que la ciudad coincida
            if (dia.getCiudad().equalsIgnoreCase(ciudad)) {
                // Los objetos `DiaDeSol` ya incluyen actividades, refrigerio y almuerzo desde `fromRow`
                resultado.add(dia);
            }
        }
        return resultado;
    }


    // Buscar Día de Sol por nombre
    public static DiaDeSol buscarPorNombre(String nombreDiaDeSol) throws IOException {
        List<DiaDeSol> diasDeSol = leerDiasDeSol();
        for (DiaDeSol diaDeSol : diasDeSol) {
            if (diaDeSol.getNombre().equalsIgnoreCase(nombreDiaDeSol)) {
                return diaDeSol;
            }
        }
        return null; // No encontrado
    }
}
