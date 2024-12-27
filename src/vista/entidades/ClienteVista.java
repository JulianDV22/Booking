package vista.entidades;

import controlador.ClienteControlador;
import modelo.entidades.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClienteVista {

    public static void iniciarReserva() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Iniciar Reserva ===");
        System.out.print("Ingrese su correo electrónico: ");
        String email = scanner.nextLine();

        // Buscar cliente por email
        Cliente cliente = ClienteControlador.buscarClientePorEmail(email);

        if (cliente != null) {
            // Cliente encontrado
            System.out.println("¡Bienvenido, " + cliente.getNombre() + "!");
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
            String resultado = ClienteControlador.registrarNuevoCliente(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);
            System.out.println(resultado);
            System.out.println("¡Bienvenido, " + nombre + "!");
        }
    }
}
