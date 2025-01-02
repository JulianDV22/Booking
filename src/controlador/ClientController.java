package controlador;

import modelo.datos.ClientData;
import modelo.entidades.Client;

import java.io.IOException;
import java.time.LocalDate;

public class ClientController {

    /**
     * Verifica si un cliente existe según su email.
     * Si existe, devuelve el objeto Cliente.
     * Si no existe, devuelve null.
     */
    public static Client findClientByEmail(String email) {
        try {
            return ClientData.findByEmail(email);
        } catch (IOException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * Recibe los datos completos del cliente como parámetros.
     */
    public static String registerNewClient(String name, String lastName, String email, String nationality, String phoneNumber, LocalDate birthday) {
        try {
            Client newClient = Client.build(name, lastName, email, nationality, phoneNumber, birthday);
            ClientData.createClient(newClient);
            return "Cliente registrado exitosamente.";
        } catch (IOException e) {
            return "Error al registrar el cliente: " + e.getMessage();
        }
    }
}
