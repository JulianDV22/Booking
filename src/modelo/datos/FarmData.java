package modelo.datos;

import modelo.entidades.Farm;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmData {

    private static final String FILE_PATH = "fincas.xlsx";
    private static final String SHEET_NAME = "Fincas";
    private static final List<String> HEADERS = List.of("Nombre", "Precio", "Tipo", "Ciudad", "Tamaño");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear una finca
    public static void createFarm(Farm farm) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        utilExcel.writeRow(sheet, lastRow, farm.toRow());
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las fincas
    public static List<Farm> findFarms() throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Farm> farms = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                farms.add(Farm.fromRow(rowData));
            }
        }
        return farms;
    }

    // Buscar finca por city
    public static List<Farm> findFarmsByCity(String city) throws IOException {
        List<Farm> farms = findFarms();
        List<Farm> farmsInCity = new ArrayList<>();

        for (Farm farm : farms) {
            if (farm.getCity().equalsIgnoreCase(city)) {
                farmsInCity.add(farm);
            }
        }
        return farmsInCity;
    }

    // Buscar finca por nombre
    public static Farm findByName(String farmName) throws IOException {
        List<Farm> farms = findFarms();
        for (Farm farm : farms) {
            if (farm.getName().equalsIgnoreCase(farmName)) {
                return farm;
            }
        }
        return null; // No encontrado
    }

    // Actualizar una finca
    public static void updateFarm(Farm oldFarm, Farm newFarm) throws IOException {
        // Asegurar la existencia del archivo Excel
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);

        // Cargar el archivo Excel
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        // Buscar el índice de la reserva existente
        int rowIndex = findFarmIndexByName(oldFarm.getName());
        if (rowIndex == -1) {
            throw new IllegalArgumentException("La reserva existente no se encontró en el archivo.");
        }

        // Actualizar la fila con los datos de la nueva reserva
        utilExcel.writeRow(sheet, rowIndex, newFarm.toRow());

        // Guardar los cambios en el archivo
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una finca
    public static void deleteFarm(String farmName) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int rowIndex = findFarmIndexByName(farmName);

        sheet.removeRow(sheet.getRow(rowIndex));
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    public static int findFarmIndexByName(String farmName) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Empieza desde 1 para omitir los encabezados
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                if (rowData.get(0).equalsIgnoreCase(farmName)) { // Compara el nombre (primera columna)
                    return i;
                }
            }
        }
        return -1; // Retorna -1 si no se encuentra
    }
}
