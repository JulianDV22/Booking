package vista;

import vista.entidades.ClienteVista;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Sistema de Reservas - Booking ===");
            System.out.println("1. Iniciar Reserva");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> {
                    ClienteVista.iniciarReserva();
                    mostrarSubmenuAlojamientos();
                }
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void mostrarSubmenuAlojamientos() {
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
                case 1 -> System.out.println("Ha seleccionado Hotel. (Funcionalidad por implementar)");
                case 2 -> System.out.println("Ha seleccionado Apartamento. (Funcionalidad por implementar)");
                case 3 -> System.out.println("Ha seleccionado Finca. (Funcionalidad por implementar)");
                case 4 -> System.out.println("Ha seleccionado Día de Sol. (Funcionalidad por implementar)");
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (opcionAlojamiento != 0);
    }
}
