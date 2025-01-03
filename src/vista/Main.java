package vista;

import vista.entidades.ClientView;
import vista.entidades.BookingView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Sistema de Reservas - Booking ===");
            System.out.println("1. Iniciar Reserva");
            System.out.println("2. Actualizar Reserva");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> {
                    showAccommodationSubMenu();
                }
                case 2 -> BookingView.updateBookingMenu();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (opcion != 0);
    }

    public static void showAccommodationSubMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcionAlojamiento;

        do {
            System.out.println("\n=== Elija un Tipo de Alojamiento ===");
            System.out.println("1. Hotel");
            System.out.println("2. Apartamento");
            System.out.println("3. Finca");
            System.out.println("4. Día de Sol");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcionAlojamiento = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcionAlojamiento) {
                case 1 -> BookingView.bookingHotelMenu();
                case 2 -> BookingView.bookingApartmentMenu();
                case 3 -> BookingView.bookingFarmMenu();
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (opcionAlojamiento != 0);
    }
}
