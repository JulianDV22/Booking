package modelo.datos;

import modelo.entidades.Client;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private static final String FILE_PATH = "clientes.xlsx";
    private static final String SHEET_NAME = "Clientes";
    private static final List<String> HEADERS = List.of("Nombre", "Apellido", "Email", "Nacionalidad", "Teléfono", "Fecha de Nacimiento");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear un cliente
    public static void createClient(Client client) throws IOException {
        executeWithSheet(sheet -> {
            int lastRow = sheet.getLastRowNum() + 1;
            utilExcel.writeRow(sheet, lastRow, client.toRow());
            return null;
        });
    }

    // Leer todos los clientes
    public static List<Client> findClients() throws IOException {
        return executeWithSheet(sheet -> getClientsFromSheet(sheet));
    }

    private static List<Client> getClientsFromSheet(Sheet sheet) {
        List<Client> clients = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = utilExcel.readRow(row);
                clients.add(Client.fromRow(rowData));
            }
        }
        return clients;
    }

    // Actualizar cliente por email
    public static void updateClientByEmail(String email, Client clientUpdate) throws IOException {
        modifyClientByEmail(email, (sheet, rowIndex) -> {
            utilExcel.writeRow(sheet, rowIndex, clientUpdate.toRow());
        });
    }

    // Eliminar cliente por email
    public static void deleteClientByEmail(String email) throws IOException {
        modifyClientByEmail(email, (sheet, rowIndex) -> {
            sheet.removeRow(sheet.getRow(rowIndex));
        });
    }

    // Buscar cliente por email
    public static Client findByEmail(String email) throws IOException {
        return findClients().stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Método auxiliar para modificar cliente por email
    private static void modifyClientByEmail(String email, ClientModification modification) throws IOException {
        executeWithSheet(sheet -> findAndModifyRow(sheet, email, modification));
    }

    private static Void findAndModifyRow(Sheet sheet, String email, ClientModification modification) throws IOException {
        int rowIndex = findRowByEmail(sheet, email);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("No se encontró un cliente con el email: " + email);
        }
        modification.modification(sheet, rowIndex);
        return null;
    }

    private static int findRowByEmail(Sheet sheet, String email) throws IOException {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && emailCoincide(row, email)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean emailCoincide(Row row, String email) throws IOException {
        List<String> rowData = utilExcel.readRow(row);
        return rowData.get(2).equalsIgnoreCase(email); // Email en la columna 2
    }

    // Método genérico para manejar Sheets
    private static <T> T executeWithSheet(SheetExecutor<T> executor) throws IOException {
        return executeWithWorkbook(workbook -> {
            Sheet sheet = utilExcel.ensureSheetExists(workbook, SHEET_NAME, HEADERS);
            return executor.execute(sheet);
        });
    }

    // Método genérico para manejar Workbooks
    private static <T> T executeWithWorkbook(WorkbookExecutor<T> executor) throws IOException {
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        try {
            return executor.execute(workbook);
        } finally {
            utilExcel.saveWorkbook(workbook, FILE_PATH);
        }
    }

    @FunctionalInterface
    private interface ClientModification {
        void modification(Sheet sheet, int rowIndex) throws IOException;
    }

    @FunctionalInterface
    private interface SheetExecutor<T> {
        T execute(Sheet sheet) throws IOException;
    }

    @FunctionalInterface
    private interface WorkbookExecutor<T> {
        T execute(Workbook workbook) throws IOException;
    }
}
