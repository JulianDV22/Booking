package modelo.datos;

import modelo.entidades.Finca;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FincaDatos {

    private static final String FILE_PATH = "fincas.xlsx";
    private static final String SHEET_NAME = "Fincas";
    private static final List<String> HEADERS = List.of("Nombre", "Precio", "Tipo", "Ciudad", "Tamaño");

    // Crear una finca
    public static void crearFinca(Finca finca) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, finca.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las fincas
    public static List<Finca> leerFincas() throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Finca> fincas = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                fincas.add(Finca.fromRow(rowData));
            }
        }
        return fincas;
    }

    // Buscar finca por ciudad
    public static List<Finca> buscarPorCiudad(String ciudad) throws IOException {
        List<Finca> fincas = leerFincas();
        List<Finca> fincasEnCiudad = new ArrayList<>();

        for (Finca finca : fincas) {
            if (finca.getCiudad().equalsIgnoreCase(ciudad)) {
                fincasEnCiudad.add(finca);
            }
        }
        return fincasEnCiudad;
    }

    // Buscar finca por nombre
    public static Finca buscarPorNombre(String nombreFinca) throws IOException {
        List<Finca> fincas = leerFincas();
        for (Finca finca : fincas) {
            if (finca.getNombre().equalsIgnoreCase(nombreFinca)) {
                return finca;
            }
        }
        return null; // No encontrado
    }

    // Actualizar una finca
    public static void actualizarFinca(int rowIndex, Finca finca) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        UtilExcel.writeRow(sheet, rowIndex, finca.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una finca
    public static void eliminarFinca(int rowIndex) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
