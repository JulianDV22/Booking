package modelo.datos;

import modelo.entidades.Cliente;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDatos {

    private static final String FILE_PATH = "clientes.xlsx";
    private static final String SHEET_NAME = "Clientes";

    // Crear un nuevo cliente
    public static void crearCliente(Cliente cliente) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Nombre", "Apellido", "Email", "Nacionalidad", "Teléfono", "Fecha de Nacimiento"));
        }
        UtilExcel.writeRow(sheet, lastRow, cliente.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los clientes
    public static List<Cliente> leerClientes() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            clientes.add(Cliente.fromRow(row));
        }
        return clientes;
    }

    // Actualizar un cliente
    public static void actualizarCliente(int rowIndex, Cliente cliente) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, cliente.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar un cliente
    public static void eliminarCliente(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }
}
