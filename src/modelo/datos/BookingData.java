package modelo.datos;

import modelo.entidades.Accommodation;
import modelo.entidades.Client;
import modelo.entidades.Booking;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingData {

    private static final String FILE_PATH = "reservas.xlsx";
    private static final String SHEET_NAME = "Reservas";
    private static final List<String> HEADERS = List.of("Cliente", "Alojamiento", "FechaInicio", "FechaFin", "PrecioTotal");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear una reserva
    public static void createBooking(Booking booking) throws IOException {
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1;
        utilExcel.writeRow(sheet, lastRow, booking.toRow());
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todas las reservas
    public static List<Booking> findBookings() throws IOException {
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Booking> bookings = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = utilExcel.readRow(sheet.getRow(i));
            String email = row.get(0); // Obtener el correo del cliente
            Client client = ClientData.findByEmail(email); // Recuperar el cliente por correo
            Accommodation accommodation = constructAccommodation(row.get(1)); // Construir el alojamiento
            bookings.add(Booking.fromRow(row, client, accommodation));
        }
        return bookings;
    }

    // Consultar reservas por cliente (correo)
    public static List<Booking> findByClient(String email) throws IOException {
        List<Booking> bookings = findBookings();
        List<Booking> clientBookings = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getClient().getEmail().equalsIgnoreCase(email)) {
                System.out.println("reserva.getCliente().getEmail() = " + booking.getClient().getEmail());
                clientBookings.add(booking);
            }
        }

        if (clientBookings.isEmpty()) {
            System.out.println("No se encontraron reservas para el cliente con email: " + email);
        }

        return clientBookings;
    }

    // Actualizar una reserva
    public static void updateBooking(Booking oldBooking, Booking newBooking) throws IOException {
        // Asegurar la existencia del archivo Excel
        utilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);

        // Cargar el archivo Excel
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        // Buscar el índice de la reserva existente
        int rowIndex = findBookingIndex(oldBooking);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("La reserva existente no se encontró en el archivo.");
        }

        // Actualizar la fila con los datos de la nueva reserva
        utilExcel.writeRow(sheet, rowIndex, newBooking.toRow());

        // Guardar los cambios en el archivo
        utilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Eliminar una reserva
    public static void deleteBooking(Booking booking) throws IOException {
        int index = findBookingIndex(booking);
        if (index == -1) {
            System.out.println("Reserva no encontrada para eliminar.");
            return;
        }

        // Aquí eliminas la reserva por su índice
        Workbook workbook = utilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(index + 1)); // Ajusta el índice para reflejar la fila en Excel
        utilExcel.saveWorkbook(workbook, FILE_PATH);
        System.out.println("Reserva eliminada con éxito.");
    }

    public static int findBookingIndex(Booking booking) throws IOException {
        return findBookingIndexInList(booking, findBookings());
    }

    private static int findBookingIndexInList(Booking booking, List<Booking> bookings) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookingCoincide(bookings.get(i), booking)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean bookingCoincide(Booking bookingReference, Booking booking) {
        return bookingReference.getClient().getEmail().equalsIgnoreCase(booking.getClient().getEmail()) &&
                bookingReference.getAccommodation().getName().equalsIgnoreCase(booking.getAccommodation().getName()) &&
                bookingReference.getStartDay().equals(booking.getStartDay()) &&
                bookingReference.getFinishDay().equals(booking.getFinishDay());
    }

    private static Accommodation constructAccommodation(String accommodationName) throws IOException {
        Accommodation accommodation = findInSources(accommodationName);
        if (accommodation != null) {
            return accommodation;
        }
        throw new IllegalArgumentException("Alojamiento no encontrado: " + accommodationName);
    }

    private static Accommodation findInSources(String accommodationName) throws IOException {
        for (AccommodationFinder source : getDataSources()) {
            Accommodation accommodation = findInSource(source, accommodationName);
            if (accommodation != null) {
                return accommodation;
            }
        }
        return null;
    }

    private static List<AccommodationFinder> getDataSources() {
        return List.of(
                HotelData::findByName,
                ApartmentData::findPerName,
                FarmData::findByName,
                SunnyDayData::findByName
        );
    }

    private static Accommodation findInSource(AccommodationFinder source, String accommodationName) {
        try {
            return source.find(accommodationName);
        } catch (IOException e) {
            throw new RuntimeException("Error al buscar en la fuente: " + source, e);
        }
    }

    @FunctionalInterface
    private interface AccommodationFinder {
        Accommodation find(String name) throws IOException;
    }



}
