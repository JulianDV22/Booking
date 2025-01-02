package clienteTest;

import modelo.datos.ClientData;
import modelo.entidades.Client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClienteDatosTest {

    public static void main(String[] args) {
        try {
            // Crear cliente
            Client client1 = Client.build("Juan", "Pérez", "juan.perez@example.com", "Colombia", "123456789", LocalDate.of(1990, 5, 20));
            ClientData.createClient(client1);
            System.out.println("Cliente creado.");

            // Leer clientes antes de eliminar
            System.out.println("\n=== Clientes antes de eliminar ===");
            List<Client> clientesAntes = ClientData.findClients();
            for (Client c : clientesAntes) {
                System.out.println(c);
            }

            // Test: Buscar cliente por email
            System.out.println("\n=== Buscar Cliente por Email ===");
            String emailBusqueda = "juan.perez@example.com";
            Client clientBuscado = ClientData.findByEmail(emailBusqueda);
            if (clientBuscado != null) {
                System.out.println("Cliente encontrado: " + clientBuscado);
            } else {
                System.out.println("Cliente no encontrado.");
            }

            // Actualizar cliente por email
            Client clientActualizado = Client.build("Juan", "Pérez", "juan.perez@example.com", "Colombia", "987654321", LocalDate.of(1990, 5, 20));
            ClientData.updateClientByEmail("juan.perez@example.com", clientActualizado);
            System.out.println("Cliente actualizado.");

            System.out.println("\n=== Clientes antes de eliminar ===");
            List<Client> clientesDespues = ClientData.findClients();
            for (Client c : clientesDespues) {
                System.out.println(c);
            }

            // Eliminar cliente por email
            System.out.println("\n=== Eliminar Cliente por Email ===");
            ClientData.deleteClientByEmail("juan.perez@example.com");
            System.out.println("Cliente eliminado.");

            System.out.println("\n=== Clientes después de eliminar ===");
            List<Client> clientesEliminados = ClientData.findClients();
            for (Client c : clientesEliminados) {
                System.out.println(c);
            }

        } catch (IOException e) {
            System.err.println("Error durante la prueba: " + e.getMessage());
        }
    }
}
