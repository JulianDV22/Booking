package modelo.datos;

import modelo.entidades.Cliente;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDatos {

    private static final String FILE_PATH = "clientes.xlsx";
    private static final String SHEET_NAME = "Clientes";
    private static final List<String> HEADERS = List.of("Nombre", "Apellido", "Email", "Nacionalidad", "Teléfono", "Fecha de Nacimiento");

    // Crear un cliente
    public static void crearCliente(Cliente cliente) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, cliente.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los clientes
    public static List<Cliente> leerClientes() throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                clientes.add(Cliente.fromRow(rowData));
            }
        }
        return clientes;
    }

    // Actualizar cliente por email
    public static void actualizarClientePorEmail(String email, Cliente clienteActualizado) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                if (rowData.get(2).equalsIgnoreCase(email)) { // Email en la columna 2
                    UtilExcel.writeRow(sheet, i, clienteActualizado.toRow());
                    UtilExcel.saveWorkbook(workbook, FILE_PATH);
                    return;
                }
            }
        }

        throw new IllegalArgumentException("No se encontró un cliente con el email: " + email);
    }

    // Eliminar cliente por email
    public static void eliminarClientePorEmail(String email) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = UtilExcel.readRow(row);
                if (rowData.get(2).equalsIgnoreCase(email)) { // Email en la columna 2
                    sheet.removeRow(row); // Eliminar fila
                    UtilExcel.saveWorkbook(workbook, FILE_PATH);
                    return; // Salir después de eliminar
                }
            }
        }

        throw new IllegalArgumentException("No se encontró un cliente con el email: " + email);
    }

    // Buscar cliente por email
    public static Cliente buscarPorEmail(String email) throws IOException {
        List<Cliente> clientes = leerClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null; // Devuelve null si no se encuentra el cliente
    }
}
