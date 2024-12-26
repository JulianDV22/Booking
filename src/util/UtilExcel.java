package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilExcel {

    public static Workbook loadWorkbook(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        return new XSSFWorkbook(fis);
    }

    public static void saveWorkbook(Workbook workbook, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    public static List<String> readRow(Row row) {
        List<String> values = new ArrayList<>();
        for (Cell cell : row) {
            values.add(cell.toString());
        }
        return values;
    }

    public static void writeRow(Sheet sheet, int rowIndex, List<String> values) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(values.get(i));
        }
    }
}