package modelo.datos;

import modelo.entidades.Alojamiento;
import modelo.entidades.Cliente;
import modelo.entidades.Reserva;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDatos {

    private static final String FILE_PATH = "reservas.xlsx";
    private static final String SHEET_NAME = "Reservas";
    private static final List<String> HEADERS = List.of("Cliente", "Alojamiento", "FechaInicio", "FechaFin", "PrecioTotal");

    // Crear una reserva
    public static void crearReserva(Reserva reserva) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        UtilExcel.writeRow(sheet, lastRow, reserva.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las reservas
    public static List<Reserva> leerReservas() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Reserva> reservas = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            String email = row.get(0); // Obtener el correo del cliente
            Cliente cliente = ClienteDatos.buscarPorEmail(email); // Recuperar el cliente por correo
            Alojamiento alojamiento = construirAlojamiento(row.get(1)); // Construir el alojamiento
            reservas.add(Reserva.fromRow(row, cliente, alojamiento));
        }
        return reservas;
    }

    // Consultar reservas por cliente (correo)
    public static List<Reserva> consultarPorCliente(String email) throws IOException {
        List<Reserva> todasReservas = leerReservas();
        List<Reserva> reservasCliente = new ArrayList<>();

        for (Reserva reserva : todasReservas) {
            if (reserva.getCliente().getEmail().equalsIgnoreCase(email)) {
                System.out.println("reserva.getCliente().getEmail() = " + reserva.getCliente().getEmail());
                reservasCliente.add(reserva);
            }
        }

        if (reservasCliente.isEmpty()) {
            System.out.println("No se encontraron reservas para el cliente con email: " + email);
        }

        return reservasCliente;
    }

    // Actualizar una reserva
    public static void actualizarReserva(Reserva reservaExistente, Reserva nuevaReserva) throws IOException {
        // Asegurar la existencia del archivo Excel
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);

        // Cargar el archivo Excel
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        // Buscar el índice de la reserva existente
        int rowIndex = buscarIndiceReserva(reservaExistente);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("La reserva existente no se encontró en el archivo.");
        }

        // Actualizar la fila con los datos de la nueva reserva
        UtilExcel.writeRow(sheet, rowIndex, nuevaReserva.toRow());

        // Guardar los cambios en el archivo
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una reserva
    public static void eliminarReserva(Reserva reserva) throws IOException {
        int indice = buscarIndiceReserva(reserva);
        if (indice == -1) {
            System.out.println("Reserva no encontrada para eliminar.");
            return;
        }

        // Aquí eliminas la reserva por su índice
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(indice + 1)); // Ajusta el índice para reflejar la fila en Excel
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
        System.out.println("Reserva eliminada con éxito.");
    }

    public static int buscarIndiceReserva(Reserva reserva) throws IOException {
        List<Reserva> todasReservas = leerReservas(); // Método que obtiene todas las reservas
        for (int i = 0; i < todasReservas.size(); i++) {
            Reserva r = todasReservas.get(i);
            // Verifica si la reserva coincide con el cliente, alojamiento y fechas
            if (r.getCliente().getEmail().equalsIgnoreCase(reserva.getCliente().getEmail()) &&
                    r.getAlojamiento().getNombre().equalsIgnoreCase(reserva.getAlojamiento().getNombre()) &&
                    r.getFechaInicio().equals(reserva.getFechaInicio()) &&
                    r.getFechaFin().equals(reserva.getFechaFin())) {
                return i; // Devuelve el índice de la reserva encontrada
            }
        }
        return -1; // Retorna -1 si no encuentra la reserva
    }

    private static Alojamiento construirAlojamiento(String alojamientoNombre) throws IOException {
        Alojamiento alojamiento = null;

        // Buscar en Hoteles
        alojamiento = HotelDatos.buscarPorNombre(alojamientoNombre);
        if (alojamiento != null) {
            return alojamiento;
        }

        // Buscar en Apartamentos
        alojamiento = ApartamentoDatos.buscarPorNombre(alojamientoNombre);
        if (alojamiento != null) {
            return alojamiento;
        }

        // Buscar en Fincas
        alojamiento = FincaDatos.buscarPorNombre(alojamientoNombre);
        if (alojamiento != null) {
            return alojamiento;
        }

        // Buscar en Días de Sol
        alojamiento = DiaDeSolDatos.buscarPorNombre(alojamientoNombre);
        if (alojamiento != null) {
            return alojamiento;
        }

        // Si no se encuentra el alojamiento
        throw new IllegalArgumentException("Alojamiento no encontrado: " + alojamientoNombre);
    }



}
