package modelo.datos;

import modelo.entidades.Apartamento;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDatos {

    private static final String FILE_PATH = "apartamentos.xlsx";
    private static final String SHEET_NAME = "Apartamentos";

    // Crear un nuevo apartamento
    public static void crearApartamento(Apartamento apartamento) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Nombre", "Precio por Noche", "Amueblado"));
        }
        UtilExcel.writeRow(sheet, lastRow, apartamento.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los apartamentos
    public static List<Apartamento> leerApartamentos() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Apartamento> apartamentos = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            apartamentos.add(Apartamento.fromRow(row));
        }
        return apartamentos;
    }

    // Actualizar un apartamento
    public static void actualizarApartamento(int rowIndex, Apartamento apartamento) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, apartamento.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un apartamento
    public static void eliminarApartamento(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
