package ClienteTest;

import controlador.ClienteControlador;
import modelo.entidades.Cliente;

import java.time.LocalDate;

public class ClienteControladorTest {

    public static void main(String[] args) {
        // Test: Buscar cliente existente
        String emailExistente = "juan.perez@example.com";
        Cliente clienteExistente = ClienteControlador.buscarClientePorEmail(emailExistente);
        if (clienteExistente != null) {
            System.out.println("Cliente encontrado: " + clienteExistente);
        } else {
            System.out.println("Cliente no encontrado.");
        }

        // Test: Registrar nuevo cliente
        String resultadoRegistro = ClienteControlador.registrarNuevoCliente(
                "Ana", "Gómez", "ana.gomez@example.com", "España", "987654321", LocalDate.of(1985, 3, 15));
        System.out.println(resultadoRegistro);

        // Test: Buscar cliente recién registrado
        String emailNuevo = "ana.gomez@example.com";
        Cliente clienteNuevo = ClienteControlador.buscarClientePorEmail(emailNuevo);
        if (clienteNuevo != null) {
            System.out.println("Cliente registrado encontrado: " + clienteNuevo);
        } else {
            System.out.println("No se encontró el cliente recién registrado.");
        }
    }
}
