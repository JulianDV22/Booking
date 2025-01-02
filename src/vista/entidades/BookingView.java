package vista.entidades;

import controlador.RoomController;
import controlador.HotelController;
import controlador.BookingController;
import modelo.datos.ApartmentData;
import modelo.datos.FarmData;
import modelo.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static vista.Main.showAccommodationSubMenu;

public class BookingView {

    private static String requestCity() {
        return requestCity("Ingrese la ciudad donde desea reservar: ");
    }

    private static LocalDate[] requestBookingDates() {
        LocalDate startDate = requestDate("Ingrese la fecha de inicio de la reserva (YYYY-MM-DD): ");
        if (startDate == null) return null;

        LocalDate finishDate = requestDate("Ingrese la fecha de fin de la reserva (YYYY-MM-DD): ");
        if (finishDate == null) return null;

        return new LocalDate[]{startDate, finishDate};
    }

    private static LocalDate requestDate(String mensaje) {
        return getValidatedDate(mensaje);
    }

    private static int requestNumberOfPeople() {
        return requestInteger("Ingrese la cantidad de personas: ");
    }

    private static LocalDate getValidatedDate(String message) {
        LocalDate date = getDate(message);
        if (date == null) {
            showMessage("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
        }
        return date;
    }

    private static void handleBookingErrors(IOException e) {
        showMessage("Error al procesar la reserva: " + e.getMessage());
    }

    private static String requestCity(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    private static LocalDate getDate(String message) {
        System.out.print(message);
        try {
            return LocalDate.parse(new Scanner(System.in).nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
            return null;
        }
    }

    private static int requestInteger(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextInt();
    }

    private static <T> T selectAccommodation(List<T> accommodations, String message) {
        showAccommodations(accommodations);
        int selection = requestSelection(message, accommodations.size());

        if (isValidateSelection(selection, accommodations.size())) {
            System.out.println("Selección inválida.");
            return null;
        }
        return accommodations.get(selection);
    }

    private static <T> void showAccommodations(List<T> accommodations) {
        for (int i = 0; i < accommodations.size(); i++) {
            System.out.println((i + 1) + ". " + accommodations.get(i));
        }
    }

    private static int requestSelection(String message, int size) {
        System.out.print(message);
        return new Scanner(System.in).nextInt() - 1;
    }

    private static boolean isValidateSelection(int selection, int size) {
        return selection < 0 || selection >= size;
    }

    private static void showMessage(String message) {
        System.out.println(message);
    }


    /*
        * Método que muestra el menú de reservas de fincas
     */
    public static void bookingFarmMenu(String clientEmail) {
        try {
            String city = requestCity();
            List<Farm> farmsInCity = getFarmByCity(city);

            if (verifyAvailableFarms(farmsInCity, city)) return;

            processSelectionAndBooking(clientEmail, farmsInCity);
        } catch (IOException e) {
            handleBookingErrors(e);
        }
    }

    private static void processSelectionAndBooking(String clientEmail, List<Farm> farmsInCity) {
        Farm selectedFarm = requestFarm(farmsInCity);
        if (selectedFarm == null) return;

        LocalDate[] dates = requestBookingDates();
        if (dates == null) return;

        int numberOfPeople = requestNumberOfPeople();

        processBooking(clientEmail, selectedFarm, dates[0], dates[1], numberOfPeople);
    }

    private static boolean verifyAvailableFarms(List<Farm> farmsInCity, String city) {
        if (farmsInCity.isEmpty()) {
            showMessage("No se encontraron fincas disponibles en la city: " + city);
            return true;
        }
        return false;
    }

    private static Farm requestFarm(List<Farm> farmsInCity) {
        return selectAccommodation(farmsInCity, "Seleccione una finca (número): ");
    }

    private static void processBooking(String clientEmail, Farm selectedFarm, LocalDate startDate, LocalDate finishDate, int numberOfPeople) {
        String result = processFarmBooking(clientEmail, selectedFarm, startDate, finishDate, numberOfPeople);
        showMessage(result);
    }

    private static List<Farm> getFarmByCity(String city) throws IOException {
        return FarmData.findFarmsByCity(city);
    }

    private static String processFarmBooking(String clientEmail, Farm selectedFarm, LocalDate startDate, LocalDate finishDate, int numberOfPeople) {
        return BookingController.createBooking(clientEmail, selectedFarm.getName(), startDate, finishDate, numberOfPeople, BookingController.AccommodationType.valueOf("Finca"));
    }


    /*
        * Método que muestra el menú de reservas de apartamento
        */
    public static void bookingApartmentMenu(String clientEmail) {
        try {
            String city = requestCity("Ingrese la ciudad donde desea reservar el apartamento: ");
            List<Apartment> apartmentsInCity = getApartmentsByCity(city);

            if (checkAvailableApartments(apartmentsInCity, city)) return;

            processSelectionAnBookingApartment(clientEmail, apartmentsInCity);
        } catch (IOException e) {
            handleBookingErrors(e);
        }
    }

    private static List<Apartment> getApartmentsByCity(String city) throws IOException {
        return ApartmentData.findApartmentPerCity(city);
    }

    private static boolean checkAvailableApartments(List<Apartment> apartmentsInCity, String city) {
        if (apartmentsInCity.isEmpty()) {
            showMessage("No se encontraron apartamentos disponibles en la ciudad: " + city);
            return true;
        }
        return false;
    }

    private static void processSelectionAnBookingApartment(String clientEmail, List<Apartment> apartmentsInCity) {
        Apartment selectedApartment = selectApartment(apartmentsInCity);
        if (selectedApartment == null) return;

        int numberOfPeople = requestNumberOfPeople();

        LocalDate[] dates = requestBookingDates();
        if (dates == null) return;

        processBookingApartment(clientEmail, selectedApartment, dates[0], dates[1], numberOfPeople);
    }

    private static Apartment selectApartment(List<Apartment> apartmentsInCity) {
        return selectAccommodation(apartmentsInCity, "Seleccione un apartamento (número): ");
    }

    private static void processBookingApartment(String clientEmail, Apartment selectedApartment, LocalDate startDate, LocalDate finishDate, int numberOfPeople) {
        String result = BookingController.createBooking(clientEmail, selectedApartment.getName(), startDate, finishDate, numberOfPeople, BookingController.AccommodationType.valueOf("Apartamento"));
        showMessage(result);
    }


    /*
        * Método que muestra el menú de reservas de hoteles
     */
    public static void bookingHotelMenu(String clientEmail) {
        try {
            String city = requestCity("Ingrese la ciudad donde desea reservar un hotel: ");
            List<Hotel> hotelsInCity = getHotelsByCity(city);

            if (checkAvailableHotels(hotelsInCity, city)) return;

            processSelectionAndBookingHotel(clientEmail, hotelsInCity);
        } catch (Exception e) {
            handleBookingErrors(e);
        }
    }

    private static List<Hotel> getHotelsByCity(String city) throws IOException {
        return HotelController.findHotelByCity(city);
    }

    private static boolean checkAvailableHotels(List<Hotel> hotelsInCity, String city) {
        if (hotelsInCity.isEmpty()) {
            showMessage("No se encontraron hoteles disponibles en la ciudad: " + city);
            return true;
        }
        return false;
    }

    private static void processSelectionAndBookingHotel(String clientEmail, List<Hotel> hotelsInCity) {
        Hotel selectedHotel = selectHotel(hotelsInCity);
        if (selectedHotel == null) return;

        int numberOfPeople = getNumberOfPeople();

        List<Room> availableRooms = getAvailableRooms(selectedHotel, numberOfPeople);
        if (availableRooms == null) return;

        processBookingHotel(clientEmail, selectedHotel, availableRooms, numberOfPeople);
    }

    private static Hotel selectHotel(List<Hotel> hotelsInCity) {
        return selectAccommodation(hotelsInCity, "Seleccione un hotel (número): ");
    }

    private static int getNumberOfPeople() {
        int numberOfAdults = requestInteger("Ingrese la cantidad de adultos: ");
        int numberOfKids = requestInteger("Ingrese la cantidad de niños: ");
        return numberOfAdults + numberOfKids;
    }

    private static List<Room> getAvailableRooms(Hotel selectedHotel, int numberOfPeople) {
        List<Room> availableRooms = RoomController.filtrateRoom(selectedHotel.getName(), numberOfPeople);
        if (availableRooms.isEmpty()) {
            showMessage("No hay habitaciones disponibles que cumplan con los requisitos.");
            return null;
        }
        return availableRooms;
    }

    private static void processBookingHotel(String clientEmail, Hotel selectedHotel, List<Room> availableRooms, int numberOfPeople) {
        Room selectedRoom = selectRoom(availableRooms);
        if (selectedRoom == null) return;

        LocalDate[] dates = requestBookingDates();
        if (dates == null) return;

        String result = BookingController.createBooking(clientEmail, selectedHotel.getName(), dates[0], dates[1], numberOfPeople, BookingController.AccommodationType.valueOf("Hotel"));
        showMessage(result);
    }

    private static void handleBookingErrors(Exception e) {
        showMessage("Error al realizar la reserva: " + e.getMessage());
    }

    public static void updateBookingMenu() {
        try {
            String email = requestEmail();
            LocalDate birthday = requestBirthDay();
            if (birthday == null) return;

            List<Booking> bookings = getClientBookings(email);
            if (checkAvailableBooking(bookings)) return;

            manageSelectionAndUpdate(email, birthday, bookings);
        } catch (Exception e) {
            handleBookingErrors(e);
        }
    }

    private static void manageSelectionAndUpdate(String email, LocalDate birthday, List<Booking> bookings) {
        Booking selectedBooking = selectBooking(bookings);
        if (selectedBooking == null) return;

        processBookingUpdate(email, birthday, selectedBooking);
    }

    private static String requestEmail() {
        System.out.print("Ingrese su correo electrónico para autenticación: ");
        return new Scanner(System.in).nextLine();
    }

    private static LocalDate requestBirthDay() {
        System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
        try {
            return LocalDate.parse(new Scanner(System.in).nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
            return null;
        }
    }

    private static List<Booking> getClientBookings(String email) {
        // Método para obtener reservas basado en el correo electrónico.
        return BookingController.obtenerReservasPorCliente(email);
    }

    private static boolean checkAvailableBooking(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("No se encontraron reservas asociadas a este cliente.");
            return true;
        }
        return false;
    }

    private static Booking selectBooking(List<Booking> bookings) {
        showBookings(bookings);
        int bookingIndex = getSelectionIndex(bookings.size());

        if (isValidateSelection(bookingIndex, bookings.size())) {
            noReachableMessage();
            return null;
        }
        return bookings.get(bookingIndex);
    }

    private static void showBookings(List<Booking> bookings) {
        System.out.println("Reservas encontradas:");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println((i + 1) + ". " + bookings.get(i));
        }
    }

    private static int getSelectionIndex(int size) {
        System.out.print("Seleccione la reserva que desea actualizar (número): ");
        return new Scanner(System.in).nextInt() - 1;
    }

    private static void noReachableMessage() {
        System.out.println("Selección inválida.");
    }

    private static void processBookingUpdate(String email, LocalDate birthday, Booking selectedBooking) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Cambiar de habitación");
        System.out.println("2. Cambiar de alojamiento");
        System.out.print("Seleccione una opción: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        switch (option) {
            case 1 -> updateRoom(email, birthday, selectedBooking);
            case 2 -> changeAccommodation(email, selectedBooking);
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void updateRoom(String email, LocalDate birthday, Booking selectedBooking) {
        String roomType = "1"; // Modificar tipo según necesidad
        List<Room> availableRooms = getAvailableRoomsForAccommodation(selectedBooking, roomType);

        if (checkAvailabilityForRooms(availableRooms)) return;

        processRoomChange(email, birthday, selectedBooking, availableRooms);
    }

    private static List<Room> getAvailableRoomsForAccommodation(Booking selectedBooking, String roomType) {
        return RoomController.obtenerHabitacionesDisponibles(
                selectedBooking.getAccommodation().getName(),
                roomType
        );
    }

    private static boolean checkAvailabilityForRooms(List<Room> availableRooms) {
        if (availableRooms.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para este alojamiento.");
            return true;
        }
        return false;
    }

    private static void processRoomChange(String email, LocalDate birthday, Booking selectedBooking, List<Room> availableRooms) {
        Room newRoom = selectRoom(availableRooms);
        if (newRoom == null) return;

        String result = BookingController.updateBooking(
                email,
                birthday,
                selectedBooking,
                false,
                newRoom.getType()
        );
        System.out.println(result);
    }

    private static void changeAccommodation(String email, Booking selectedBooking) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del nuevo alojamiento: ");
        String newAccommodationName = scanner.nextLine();

        String deleteResult = BookingController.deleteBooking(selectedBooking);
        System.out.println(deleteResult);

        System.out.println("Ahora puede proceder a crear una nueva reserva para el alojamiento: " + newAccommodationName);
        showAccommodationSubMenu();
    }

    private static Room selectRoom(List<Room> availableRooms) {
        showAvailableRooms(availableRooms);
        int habitacionIndex = getSelectionIndex(availableRooms.size());

        if (isValidateSelection(habitacionIndex, availableRooms.size())) {
            noReachableMessage();
            return null;
        }
        return availableRooms.get(habitacionIndex);
    }

    private static void showAvailableRooms(List<Room> habitacionesDisponibles) {
        System.out.println("Habitaciones disponibles:");
        for (int i = 0; i < habitacionesDisponibles.size(); i++) {
            System.out.println((i + 1) + ". Tipo: " + habitacionesDisponibles.get(i).getType() + ", Precio: " + habitacionesDisponibles.get(i).getPrice());
        }
    }

}
