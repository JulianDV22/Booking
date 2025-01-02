package modelo.datos;

import modelo.entidades.SunnyDay;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SunnyDayData {

    private static final String FILE_PATH = "dias_de_sol.xlsx";
    private static final String SHEET_NAME = "DiasDeSol";
    private static final List<String> HEADERS = List.of("Nombre", "PrecioPorNoche", "Tipo", "Ciudad", "Actividades", "IncluyeAlmuerzo", "IncluyeRefrigerio");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear un día de sol
    public static void createSunnyDay(SunnyDay sunnyDay) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        utilExcel.writeRow(sheet, lastRow, sunnyDay.toRow());

        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los días de sol
    public static List<SunnyDay> findSunnyDays() throws IOException {
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<SunnyDay> sunnyDays = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Salta la fila de encabezado
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) { // Verifica que la fila no sea null
                List<String> row = utilExcel.readRow(currentRow);
                sunnyDays.add(SunnyDay.fromRow(row)); // Convierte cada fila en un objeto `DiaDeSol`
            }
        }

        return sunnyDays;
    }


    // Actualizar un día de sol
    public static void updateSunnyDay(int rowIndex, SunnyDay sunnyDay) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        utilExcel.writeRow(sheet, rowIndex, sunnyDay.toRow());
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un día de sol
    public static void deleteSunnyDay(int rowIndex) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        sheet.removeRow(sheet.getRow(rowIndex));
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Buscar días de sol por city
    public static List<SunnyDay> findByCity(String city) throws IOException {
        // Cargar todos los Días de Sol desde el archivo Excel
        List<SunnyDay> sunnyDays = findSunnyDays();
        List<SunnyDay> result = new ArrayList<>();

        for (SunnyDay day : sunnyDays) {
            // Verificar que la city coincida
            if (day.getCity().equalsIgnoreCase(city)) {
                // Los objetos `DiaDeSol` ya incluyen actividades, refrigerio y almuerzo desde `fromRow`
                result.add(day);
            }
        }
        return result;
    }


    // Buscar Día de Sol por nombre
    public static SunnyDay findByName(String sunnyDayName) throws IOException {
        List<SunnyDay> sunnyDays = findSunnyDays();
        for (SunnyDay sunnyDay : sunnyDays) {
            if (sunnyDay.getName().equalsIgnoreCase(sunnyDayName)) {
                return sunnyDay;
            }
        }
        return null; // No encontrado
    }
}
