package modelo.datos;

import modelo.entidades.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDatos {

    private static final String FILE_PATH = "reservas.xlsx";
    private static final String SHEET_NAME = "Reservas";

    // Crear una nueva reserva
    public static void crearReserva(Reserva reserva) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum() + 1;
        if (lastRow == 0) {
            UtilExcel.writeRow(sheet, 0, List.of("Cliente", "Alojamiento", "Habitación", "Cantidad", "Fecha Inicio", "Fecha Fin", "Precio Total", "Ajuste"));
        }
        UtilExcel.writeRow(sheet, lastRow, reserva.toRow());

        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las reservas existentes
    public static List<Reserva> leerReservas() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        List<Reserva> reservas = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));

            // Construir objetos necesarios para la reserva
            Cliente cliente = Cliente.build(); // Cliente inicializado
            Alojamiento alojamiento = construirAlojamiento(row); // Construir el alojamiento adecuado
            Habitacion habitacion = Habitacion.build(); // Habitacion inicializada

            reservas.add(Reserva.fromRow(row, cliente, alojamiento, habitacion));
        }
        return reservas;
    }

    // Actualizar una reserva
    public static void actualizarReserva(int rowIndex, Reserva reserva) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        UtilExcel.writeRow(sheet, rowIndex, reserva.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una reserva
    public static void eliminarReserva(int rowIndex) throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(rowIndex));
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Método auxiliar para construir un objeto Alojamiento según los datos de la fila
    private static Alojamiento construirAlojamiento(List<String> row) {
        String tipo = row.get(1); // Tipo de alojamiento (Hotel, Apartamento, Finca, Día de Sol)

        switch (tipo.toLowerCase()) {
            case "hotel":
                return Hotel.build(
                        row.get(2),                             // Nombre
                        Double.parseDouble(row.get(3)),         // Precio por noche
                        Integer.parseInt(row.get(4)),           // Calificación
                        Hotel.stringToHabitaciones(row.get(5)), // Habitaciones
                        List.of(row.get(6).split(", "))         // Actividades
                );
            case "apartamento":
                return Apartamento.build(row.get(2), Double.parseDouble(row.get(3)), Boolean.parseBoolean(row.get(4)));
            case "finca":
                return Finca.build(row.get(2), Double.parseDouble(row.get(3)), Integer.parseInt(row.get(4)));
            case "día de sol":
                return DiaDeSol.build(row.get(2), Double.parseDouble(row.get(3)), null);
            default:
                throw new IllegalArgumentException("Tipo de alojamiento no reconocido: " + tipo);
        }
    }
}