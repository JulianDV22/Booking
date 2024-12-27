package reservaTest;

import modelo.datos.ReservaDatos;
import modelo.entidades.Alojamiento;
import modelo.entidades.Cliente;
import modelo.entidades.Reserva;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDatosTest {

    public static void main(String[] args) {
        try {
            // Crear listas de ejemplo para clientes y alojamientos
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(Cliente.build("Juan", "Pérez", "juan.perez@example.com", "Colombia", "1234567890", LocalDate.of(1985, 5, 20)));
            clientes.add(Cliente.build("María", "Gómez", "maria.gomez@example.com", "México", "0987654321", LocalDate.of(1990, 8, 15)));

            List<Alojamiento> alojamientos = new ArrayList<>();
            alojamientos.add(Alojamiento.build("Hotel Las Palmas", 120.0, "Cartagena"));
            alojamientos.add(Alojamiento.build("Casa de Campo", 80.0, "Medellín"));

            // Crear reservas
            Reserva reserva1 = Reserva.build(clientes.get(0), alojamientos.get(0), LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15), 600.0);
            Reserva reserva2 = Reserva.build(clientes.get(1), alojamientos.get(1), LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 10), 400.0);

            ReservaDatos.crearReserva(reserva1);
            ReservaDatos.crearReserva(reserva2);
            System.out.println("Reservas creadas exitosamente.");

            // Leer todas las reservas
            System.out.println("\n=== Lista de Reservas ===");
            List<Reserva> reservas = ReservaDatos.leerReservas();
            reservas.forEach(System.out::println);

            // Consultar reservas por cliente (correo)
            String email = "juan.perez@example.com";
            System.out.println("\n=== Reservas del Cliente: " + email + " ===");
            List<Reserva> reservasCliente = ReservaDatos.consultarPorCliente(email);
            reservasCliente.forEach(System.out::println);

            // Actualizar una reserva
            System.out.println("\n=== Actualizar Reserva ===");
            Reserva reservaActualizada = Reserva.build(clientes.get(0), alojamientos.get(1), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 5), 320.0);
            ReservaDatos.actualizarReserva(1, reservaActualizada);
            System.out.println("Reserva actualizada exitosamente.");

            // Leer reservas después de la actualización
            System.out.println("\n=== Lista de Reservas Después de Actualización ===");
            reservas = ReservaDatos.leerReservas();
            reservas.forEach(System.out::println);

            // Eliminar una reserva
            System.out.println("\n=== Eliminar Reserva ===");
            ReservaDatos.eliminarReserva(1);
            System.out.println("Reserva eliminada exitosamente.");

            // Leer reservas después de la eliminación
            System.out.println("\n=== Lista de Reservas Después de Eliminación ===");
            reservas = ReservaDatos.leerReservas();
            reservas.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
