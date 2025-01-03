package vista.entidades;

import controlador.BookingController;
import controlador.command.booking.CreateBookingCommand;
import controlador.command.booking.FindBookingsByClientCommand;
import modelo.datos.ApartmentData;
import modelo.datos.FarmData;
import modelo.datos.HotelData;
import modelo.datos.SunnyDayData;
import modelo.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BookingView {

    private static final BookingController bookingController = new BookingController();

    // Entry Point for Booking Menu
    public void showBookingMenu() {
        while (true) {
            System.out.println("\n=== Menú de Reservas ===");
            System.out.println("1. Reservar Finca");
            System.out.println("2. Reservar Apartamento");
            System.out.println("3. Reservar Hotel");
            System.out.println("4. Actualizar Reserva");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int option = new Scanner(System.in).nextInt();
            switch (option) {
                case 1 -> bookingFarmMenu();
                case 2 -> bookingApartmentMenu();
                case 3 -> bookingHotelMenu();
                case 4 -> updateBookingMenu();
                case 5 -> {
                    System.out.println("Saliendo del menú de reservas...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // Menu for Booking a Farm
    public static void bookingFarmMenu() {
        String clientEmail = requestEmail();
        String city = requestCity("Ingrese la ciudad donde desea reservar: ");
        executeFindCommandAndBook(clientEmail, city, BookingController.AccommodationType.FINCA);
    }

    // Menu for Booking an Apartment
    public static void bookingApartmentMenu() {
        String clientEmail = requestEmail();
        String city = requestCity("Ingrese la ciudad donde desea reservar el apartamento: ");
        executeFindCommandAndBook(clientEmail, city, BookingController.AccommodationType.APARTAMENTO);
    }

    // Menu for Booking a Hotel
    public static void bookingHotelMenu() {
        String clientEmail = requestEmail();
        String city = requestCity("Ingrese la ciudad donde desea reservar un hotel: ");
        executeFindCommandAndBook(clientEmail, city, BookingController.AccommodationType.HOTEL);
    }

    // Handles finding accommodations and processing bookings
    private static void executeFindCommandAndBook(String clientEmail, String city, BookingController.AccommodationType type) {
        try {
            List<? extends Accommodation> accommodations = findAndValidateAccommodations(city, type);
            Accommodation selectedAccommodation = findAndValidateSelection(accommodations);
            LocalDate[] dates = findAndValidateDates();
            double totalPrice = handleBooking(clientEmail, selectedAccommodation, dates);

            printBookingResult(totalPrice);
        } catch (IOException e) {
            handleError(e);
        }
    }

    private static List<? extends Accommodation> findAndValidateAccommodations(String city, BookingController.AccommodationType type) throws IOException {
        List<? extends Accommodation> accommodations = findAccommodationsByCity(city, type);
        if (accommodations.isEmpty()) {
            System.out.println("No se encontraron alojamientos disponibles en la ciudad: " + city);
            return null;
        }
        return accommodations;
    }

    private static Accommodation findAndValidateSelection(List<? extends Accommodation> accommodations) {
        if (accommodations == null) return null;
        return selectAccommodation(accommodations, "Seleccione un alojamiento (número): ");
    }

    private static LocalDate[] findAndValidateDates() {
        return requestBookingDates();
    }

    private static double handleBooking(String clientEmail, Accommodation selectedAccommodation, LocalDate[] dates) throws IOException {
        if (selectedAccommodation == null || dates == null) return 0.0;

        Client client = bookingController.validateClient(clientEmail);
        int numberOfPeople = requestNumberOfPeople();
        return bookingController.calculateBookingPrice(selectedAccommodation, numberOfPeople, dates[0], dates[1]);
    }

    private List<? extends Accommodation> findAccommodationsAndValidate(String city, BookingController.AccommodationType type) throws IOException {
        List<? extends Accommodation> accommodations = findAccommodationsByCity(city, type);
        if (accommodations.isEmpty()) {
            System.out.println("No se encontraron alojamientos disponibles en la ciudad: " + city);
            return null;
        }
        return accommodations;
    }

    private Accommodation getSelectedAccommodation(List<? extends Accommodation> accommodations) {
        return selectAccommodation(accommodations, "Seleccione un alojamiento (número): ");
    }

    private LocalDate[] getValidatedBookingDates() {
        return requestBookingDates();
    }

    private double processBooking(String clientEmail, Accommodation accommodation, LocalDate[] dates) throws IOException {
        Client client = getClient(clientEmail);
        int numberOfPeople = getNumberOfPeople();
        return calculateTotalPrice(accommodation, numberOfPeople, dates);
    }

    private static void printBookingResult(double totalPrice) {
        System.out.println("Reserva creada exitosamente. Precio total: " + totalPrice);

    }

    private List<? extends Accommodation> findAccommodations(String city, BookingController.AccommodationType type) throws IOException {
        List<? extends Accommodation> accommodations = findAccommodationsByCity(city, type);
        if (accommodations.isEmpty()) {
            System.out.println("No se encontraron alojamientos disponibles en la ciudad: " + city);
            return null;
        }
        return accommodations;
    }

    private Accommodation selectAccommodation(List<? extends Accommodation> accommodations) {
        return selectAccommodation(accommodations, "Seleccione un alojamiento (número): ");
    }

    private LocalDate[] getBookingDates() {
        return requestBookingDates();
    }

    private Client getClient(String clientEmail) throws IOException {
        return bookingController.validateClient(clientEmail);
    }

    private int getNumberOfPeople() {
        return requestNumberOfPeople();
    }

    private double calculateTotalPrice(Accommodation accommodation, int numberOfPeople, LocalDate[] dates) {
        return bookingController.calculateBookingPrice(accommodation, numberOfPeople, dates[0], dates[1]);
    }

    private void createBooking(Client client, Accommodation accommodation, LocalDate[] dates, double totalPrice) {
        String result = bookingController.executeCommand(new CreateBookingCommand(
                client,
                accommodation,
                dates[0],
                dates[1],
                totalPrice
        ));
        System.out.println(result);
    }

    private static void handleError(IOException e) {
        System.out.println("Error al procesar la reserva: " + e.getMessage());
    }

    private static boolean extracted(boolean accommodations, String city) {
        if (accommodations) {
            System.out.println(city);
            return true;
        }
        return false;
    }

    // Updates an existing booking
    public static void updateBookingMenu() {
        String email = requestEmail();
        LocalDate birthday = validateBirthday();
        if (birthday == null) return;

        List<Booking> bookings = fetchBookings(email);
        if (bookings == null) return;

        processUpdate(email, birthday, bookings);
    }

    private static LocalDate validateBirthday() {
        LocalDate birthday = requestDate("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
        if (birthday == null) {
            System.out.println("Fecha de nacimiento inválida.");
        }
        return birthday;
    }

    private static List<Booking> fetchBookings(String email) {
        List<Booking> bookings = bookingController.executeCommand(new FindBookingsByClientCommand(email));
        if (bookings.isEmpty()) {
            System.out.println("No se encontraron reservas asociadas a este cliente.");
            return null;
        }
        return bookings;
    }

    private static void processUpdate(String email, LocalDate birthday, List<Booking> bookings) {
        Booking selectedBooking = selectBooking(bookings, "Seleccione una reserva para actualizar (número): ");
        if (selectedBooking == null) {
            System.out.println("No se seleccionó ninguna reserva.");
            return;
        }
        processBookingUpdate(email, birthday, selectedBooking);
    }

    private static void processBookingUpdate(String email, LocalDate birthday, Booking selectedBooking) {
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Cambiar de habitación");
        System.out.println("2. Cambiar de alojamiento");
        System.out.print("Seleccione una opción: ");

        int option = new Scanner(System.in).nextInt();
        switch (option) {
            case 1 -> updateRoom(email, birthday, selectedBooking);
            case 2 -> changeAccommodation(email, selectedBooking);
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void updateRoom(String email, LocalDate birthday, Booking selectedBooking) {
        // Implement room update logic
    }

    private static void changeAccommodation(String email, Booking selectedBooking) {
        // Implement accommodation change logic
    }

    // Helper Methods
    private static String requestEmail() {
        System.out.print("Ingrese su correo electrónico: ");
        return new Scanner(System.in).nextLine();
    }

    private static String requestCity(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    private static LocalDate requestDate(String message) {
        System.out.print(message);
        try {
            return LocalDate.parse(new Scanner(System.in).nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
            return null;
        }
    }

    private static LocalDate[] requestBookingDates() {
        LocalDate startDate = requestDate("Ingrese la fecha de inicio (YYYY-MM-DD): ");
        if (startDate == null) return null;

        LocalDate finishDate = requestDate("Ingrese la fecha de fin (YYYY-MM-DD): ");
        if (finishDate == null) return null;

        return new LocalDate[]{startDate, finishDate};
    }

    private static int requestNumberOfPeople() {
        System.out.print("Ingrese la cantidad de personas: ");
        return new Scanner(System.in).nextInt();
    }

    private static <T> T selectAccommodation(List<T> accommodations, String message) {
        displayAccommodations(accommodations);
        int selection = getUserSelection(message, accommodations.size());
        return getValidatedSelection(accommodations, selection);
    }

    private static <T> void displayAccommodations(List<T> accommodations) {
        for (int i = 0; i < accommodations.size(); i++) {
            System.out.println((i + 1) + ". " + accommodations.get(i));
        }
    }

    private static <T> T getValidatedSelection(List<T> accommodations, int selection) {
        if (selection < 0 || selection >= accommodations.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return accommodations.get(selection);
    }

    private static Booking selectBooking(List<Booking> bookings, String message) {
        displayBookings(bookings);
        int selection = getUserSelection(message, bookings.size());
        return getValidatedBooking(bookings, selection);
    }

    private static void displayBookings(List<Booking> bookings) {
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println((i + 1) + ". " + bookings.get(i));
        }
    }

    private static int getUserSelection(String message, int size) {
        System.out.print(message);
        return new Scanner(System.in).nextInt() - 1;
    }

    private static Booking getValidatedBooking(List<Booking> bookings, int selection) {
        if (selection < 0 || selection >= bookings.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return bookings.get(selection);
    }

    private static List<? extends Accommodation> findAccommodationsByCity(String city, BookingController.AccommodationType type) throws IOException {
        return switch (type) {
            case DIA_DE_SOL -> SunnyDayData.findByCity(city);
            case FINCA -> FarmData.findFarmsByCity(city);
            case APARTAMENTO -> ApartmentData.findApartmentPerCity(city);
            case HOTEL -> HotelData.findByCity(city);
        };
    }
}
