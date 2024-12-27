package ClienteTest;

import modelo.datos.ClienteDatos;
import modelo.entidades.Cliente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClienteDatosTest {

    public static void main(String[] args) {
        try {
            // Crear cliente
            Cliente cliente1 = Cliente.build("Juan", "Pérez", "juan.perez@example.com", "Colombia", "123456789", LocalDate.of(1990, 5, 20));
            ClienteDatos.crearCliente(cliente1);
            System.out.println("Cliente creado.");

            // Leer clientes antes de eliminar
            System.out.println("\n=== Clientes antes de eliminar ===");
            List<Cliente> clientesAntes = ClienteDatos.leerClientes();
            for (Cliente c : clientesAntes) {
                System.out.println(c);
            }

            // Test: Buscar cliente por email
            System.out.println("\n=== Buscar Cliente por Email ===");
            String emailBusqueda = "juan.perez@example.com";
            Cliente clienteBuscado = ClienteDatos.buscarPorEmail(emailBusqueda);
            if (clienteBuscado != null) {
                System.out.println("Cliente encontrado: " + clienteBuscado);
            } else {
                System.out.println("Cliente no encontrado.");
            }

            // Actualizar cliente por email
            Cliente clienteActualizado = Cliente.build("Juan", "Pérez", "juan.perez@example.com", "Colombia", "987654321", LocalDate.of(1990, 5, 20));
            ClienteDatos.actualizarClientePorEmail("juan.perez@example.com", clienteActualizado);
            System.out.println("Cliente actualizado.");

            System.out.println("\n=== Clientes antes de eliminar ===");
            List<Cliente> clientesDespues = ClienteDatos.leerClientes();
            for (Cliente c : clientesDespues) {
                System.out.println(c);
            }

            // Eliminar cliente por email
            System.out.println("\n=== Eliminar Cliente por Email ===");
            ClienteDatos.eliminarClientePorEmail("juan.perez@example.com");
            System.out.println("Cliente eliminado.");

            System.out.println("\n=== Clientes después de eliminar ===");
            List<Cliente> clientesEliminados = ClienteDatos.leerClientes();
            for (Cliente c : clientesEliminados) {
                System.out.println(c);
            }

        } catch (IOException e) {
            System.err.println("Error durante la prueba: " + e.getMessage());
        }
    }
}
