package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilExcel {

    // Cargar un archivo Excel
    public static Workbook loadWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + filePath);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            return new XSSFWorkbook(fis);
        }
    }

    // Guardar un archivo Excel
    public static void saveWorkbook(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }

    // Leer una fila de Excel como una lista de cadenas
    public static List<String> readRow(Row row) {
        List<String> rowData = new ArrayList<>();
        for (Cell cell : row) {
            switch (cell.getCellType()) {
                case STRING -> rowData.add(cell.getStringCellValue());
                case NUMERIC -> rowData.add(String.valueOf(cell.getNumericCellValue()));
                case BOOLEAN -> rowData.add(String.valueOf(cell.getBooleanCellValue()));
                case FORMULA -> rowData.add(cell.getCellFormula());
                default -> rowData.add("");
            }
        }
        return rowData;
    }

    // Escribir una fila en Excel desde una lista de cadenas
    public static void writeRow(Sheet sheet, int rowIndex, List<String> rowData) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        for (int i = 0; i < rowData.size(); i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }
            cell.setCellValue(rowData.get(i));
        }
    }

    // Asegurar la existencia del archivo Excel y la hoja, con encabezados
    public static void ensureFileExists(String filePath, String sheetName, List<String> headers) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Escribir encabezados si se proporcionan
            if (headers != null && !headers.isEmpty()) {
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers.get(i));
                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            workbook.close();
        }
    }
}