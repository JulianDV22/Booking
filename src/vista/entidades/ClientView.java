package vista.entidades;

import controlador.ClientController;
import modelo.entidades.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClientView {

    public static String startBooking() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Iniciar Reserva ===");
        System.out.print("Ingrese su correo electrónico: ");
        String email = scanner.nextLine();

        // Buscar cliente por email
        Client client = ClientController.findClientByEmail(email);

        if (client != null) {
            // Cliente encontrado
            System.out.println("¡Bienvenido, " + client.getName() + "!");
        } else {
            // Cliente no encontrado, registrar nuevo cliente
            System.out.println("El email ingresado no está registrado. Complete el formulario para registrarse.");

            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese su apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese su nacionalidad: ");
            String nacionalidad = scanner.nextLine();
            System.out.print("Ingrese su número de teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
            String fechaNacimientoStr = scanner.nextLine();
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Registrar nuevo cliente
            String resultado = ClientController.registerNewClient(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);
            System.out.println(resultado);
            System.out.println("¡Bienvenido, " + nombre + "!");
        }
        return email;
    }
}
