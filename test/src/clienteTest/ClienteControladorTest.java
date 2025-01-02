package clienteTest;

import controlador.ClientController;
import modelo.entidades.Client;

import java.time.LocalDate;

public class ClienteControladorTest {

    public static void main(String[] args) {
        // Test: Buscar cliente existente
        String emailExistente = "juan.perez@example.com";
        Client clientExistente = ClientController.findClientByEmail(emailExistente);
        if (clientExistente != null) {
            System.out.println("Cliente encontrado: " + clientExistente);
        } else {
            System.out.println("Cliente no encontrado.");
        }

        // Test: Registrar nuevo cliente
        String resultadoRegistro = ClientController.registerNewClient(
                "Ana", "Gómez", "ana.gomez@example.com", "España", "987654321", LocalDate.of(1985, 3, 15));
        System.out.println(resultadoRegistro);

        // Test: Buscar cliente recién registrado
        String emailNuevo = "ana.gomez@example.com";
        Client clientNuevo = ClientController.findClientByEmail(emailNuevo);
        if (clientNuevo != null) {
            System.out.println("Cliente registrado encontrado: " + clientNuevo);
        } else {
            System.out.println("No se encontró el cliente recién registrado.");
        }
    }
}
