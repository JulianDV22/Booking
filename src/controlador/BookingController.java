package controlador;

import modelo.datos.*;
import modelo.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.logging.Logger;

public class BookingController {

    public static String createBooking(String emailClient, String accommodationName, LocalDate startDate, LocalDate finishDate, int numberOfPeople, AccommodationType accommodationType) {
        try {
            Client client = validateClient(emailClient);
            Accommodation accommodation = validateAccommodation(accommodationName, accommodationType);
            double totalPrice = calculateBookingPrice(accommodation, numberOfPeople, startDate, finishDate);
            return processBooking(client, accommodation, startDate, finishDate, totalPrice, accommodationType, accommodationName);
        } catch (IOException e) {
            return bookingErrorMessage(e);
        }
    }

    private static Client validateClient(String emailClient) throws IOException {
        Client client = getClientOrMessage(emailClient);
        if (client == null) {
            throw new IllegalArgumentException(clientNotFoundMessage(emailClient));
        }
        return client;
    }

    private static Accommodation validateAccommodation(String accommodationName, AccommodationType accommodationType) throws IOException {
        Accommodation accommodation = getAccommodationOrMessage(accommodationName, accommodationType);
        if (accommodation == null) {
            throw new IllegalArgumentException(accommodationNotFoundMessage(accommodationType, accommodationName));
        }
        return accommodation;
    }

    private static String processBooking(Client client, Accommodation accommodation, LocalDate startDate, LocalDate finishDate, double totalPrice, AccommodationType accommodationType, String accommodationName) throws IOException {
        return createAndRegisterBooking(client, accommodation, startDate, finishDate, totalPrice, accommodationType, accommodationName);
    }

    private static Client getClientOrMessage(String emailClient) throws IOException {
        return authenticateClient(emailClient);
    }

    private static Accommodation getAccommodationOrMessage(String accommodationName, AccommodationType accommodationType) throws IOException {
        return getAccommodation(accommodationName, accommodationType);
    }

    private static String clientNotFoundMessage(String emailClient) {
        return "Cliente no encontrado con el correo: " + emailClient;
    }

    private static String accommodationNotFoundMessage(AccommodationType accommodationType, String accommodationName) {
        return accommodationType + " no encontrado con el nombre: " + accommodationName;
    }

    private static String bookingErrorMessage(IOException e) {
        return "Error al crear la reserva: " + e.getMessage();
    }

    private static Client authenticateClient(String emailClient) throws IOException {
        return ClientData.findByEmail(emailClient);
    }

    private static Accommodation getAccommodation(String name, AccommodationType accommodationType) throws IOException {
        return findAccommodationByType(name, accommodationType);
    }

    private static double calculateBookingPrice(Accommodation accommodation, int numberOfPeople, LocalDate startDate, LocalDate finishDate) {
        long stayingDays = calculateStayingDays(startDate, finishDate);
        double basePrice = accommodation.getPricePerNight() * numberOfPeople * stayingDays;
        return calculateTotalPrice(basePrice, startDate, finishDate);
    }

    private static String createAndRegisterBooking(Client client, Accommodation accommodation, LocalDate startDate, LocalDate finishDate, double totalPrice, AccommodationType accommodationType, String accommodationName) throws IOException {
        Booking booking = Booking.build(client, accommodation, startDate, finishDate, totalPrice);
        BookingData.createBooking(booking);
        printTicket(booking, totalPrice / calculateStayingDays(startDate, finishDate));
        return "Reserva creada exitosamente para " + accommodationType + ": " + accommodationName;
    }

    private static Accommodation findAccommodationByType(String name, AccommodationType type) throws IOException {
        return switch (type) {
            case DIA_DE_SOL -> SunnyDayData.findByName(name);
            case FINCA -> FarmData.findByName(name);
            case APARTAMENTO -> ApartmentData.findPerName(name);
            case HOTEL -> HotelData.findByName(name);
        };
    }

    private static long calculateStayingDays(LocalDate startDate, LocalDate finishDate) {
        return finishDate.toEpochDay() - startDate.toEpochDay();
    }

    public static String updateBooking(String email, LocalDate birthday, Booking oldBooking, boolean changeAccommodation, String newRoomType) {
        try {
            Client client = validateAndAuthenticateClient(email, birthday);
            return changeAccommodation ? handleAccommodationChange(oldBooking) : updateRoomDetails(client, oldBooking, newRoomType);
        } catch (IOException e) {
            return bookingUpdateErrorMessage(e);
        }
    }

    private static Client validateAndAuthenticateClient(String email, LocalDate birthday) throws IOException {
        Client client = authenticateClient(email, birthday);
        if (client == null) {
            throw new IllegalArgumentException("Error: Autenticación fallida. Verifica tu correo y fecha de nacimiento.");
        }
        return client;
    }

    private static String updateRoomDetails(Client client, Booking oldBooking, String newRoomType) throws IOException {
        return updateRoom(client, oldBooking, newRoomType);
    }

    private static String bookingUpdateErrorMessage(IOException e) {
        return "Error al actualizar la reserva: " + e.getMessage();
    }

    private static Client authenticateClient(String email, LocalDate fechaNacimiento) throws IOException {
        Client client = ClientData.findByEmail(email);
        return (client != null && client.getBirthday().equals(fechaNacimiento)) ? client : null;
    }

    private static String handleAccommodationChange(Booking oldBooking) throws IOException {
        BookingData.deleteBooking(oldBooking);
        return "La reserva existente ha sido eliminada. Por favor, cree una nueva reserva en el alojamiento deseado.";
    }

    private static String updateRoom(Client client, Booking olgBooking, String newRoomType) throws IOException {
        Room newRoom = findNewRoom(olgBooking.getAccommodation().getName(), newRoomType);
        if (newRoom == null) {
            return "Error: No se encontró una habitación del tipo especificado en el mismo alojamiento.";
        }

        double newTotalPrice = calculateTotalPrice(
                newRoom.getPrice(),
                olgBooking.getStartDay(),
                olgBooking.getFinishDay()
        );

        Booking newBooking = Booking.build(
                client,
                olgBooking.getAccommodation(),
                olgBooking.getStartDay(),
                olgBooking.getFinishDay(),
                newTotalPrice
        );

        BookingData.updateBooking(olgBooking, newBooking);

        return "Reserva actualizada exitosamente. Se cambió la habitación en el mismo alojamiento.";
    }

    public static String deleteBooking(Booking booking) {
        try {
            BookingData.deleteBooking(booking);
            return "Reserva eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la reserva: " + e.getMessage();
        }
    }

    private static double calculateTotalPrice(double basePrice, LocalDate startDate, LocalDate finishDate) {
        int startDay = startDate.getDayOfMonth();
        int finishDay = finishDate.getDayOfMonth();
        LocalDate endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth());

        if (isDiscountRange(startDay, finishDay)) {
            return applyDiscount(basePrice, 0.08);
        } else if (isIncreaseRange(startDay, finishDay)) {
            return applyIncrease(basePrice, 0.10);
        } else if (isEndOfMonthRange(startDay, finishDay, endOfMonth)) {
            return applyIncrease(basePrice, 0.15);
        }

        return basePrice;
    }

    private static boolean isDiscountRange(int startDay, int finishDay) {
        return startDay >= 5 && finishDay <= 10;
    }

    private static boolean isIncreaseRange(int startDay, int finishDay) {
        return startDay >= 10 && finishDay <= 15;
    }

    private static boolean isEndOfMonthRange(int startDay, int finishDay, LocalDate endOfMonth) {
        int lastDay = endOfMonth.getDayOfMonth();
        return startDay >= lastDay - 4 || finishDay >= lastDay - 4;
    }

    private static double applyDiscount(double basePrice, double percentage) {
        return basePrice - (basePrice * percentage);
    }

    private static double applyIncrease(double basePrice, double percentage) {
        return basePrice + (basePrice * percentage);
    }


    private static void printTicket(Booking booking, double basePrice) {
        Logger logger = Logger.getLogger(BookingController.class.getName());
        logger.info("\n=== Factura de Reserva ===");
        logger.info("Cliente: " + booking.getClient().getName() + " " + booking.getClient().getLastName());
        logger.info("Alojamiento: " + booking.getAccommodation().getName());
        logger.info("Fecha Inicio: " + booking.getStartDay());
        logger.info("Fecha Fin: " + booking.getFinishDay());
        logger.info(("Precio Base: $" + basePrice));
        logger.info("Precio Total (con ajustes): $" + booking.getTotalPrice());
        logger.info("========================\n");
    }

    private static Room findNewRoom(String accommodationName, String roomType) throws IOException {
        List<Room> availableRooms = RoomData.findByHotel(accommodationName);
        return availableRooms.stream()
                .filter(h -> h.isAvailable() && h.getType().equalsIgnoreCase(roomType))
                .findFirst()
                .orElse(null);
    }

    public enum AccommodationType {
        DIA_DE_SOL, FINCA, APARTAMENTO, HOTEL
    }

    public static List<Booking> obtenerReservasPorCliente (String email) {
        try {
            return BookingData.findByClient(email);
        } catch (IOException e) {
            Logger.getLogger(BookingController.class.getName()).severe("Error al obtener las reservas del cliente: " + e.getMessage());
            return List.of();
        }
    }
}
