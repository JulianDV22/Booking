package controlador;

import modelo.datos.ClienteDatos;
import modelo.entidades.Cliente;

import java.io.IOException;
import java.time.LocalDate;

public class ClienteControlador {

    /**
     * Verifica si un cliente existe según su email.
     * Si existe, devuelve el objeto Cliente.
     * Si no existe, devuelve null.
     */
    public static Cliente buscarClientePorEmail(String email) {
        try {
            return ClienteDatos.buscarPorEmail(email);
        } catch (IOException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * Recibe los datos completos del cliente como parámetros.
     */
    public static String registrarNuevoCliente(String nombre, String apellido, String email, String nacionalidad, String telefono, LocalDate fechaNacimiento) {
        try {
            Cliente nuevoCliente = Cliente.build(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);
            ClienteDatos.crearCliente(nuevoCliente);
            return "Cliente registrado exitosamente.";
        } catch (IOException e) {
            return "Error al registrar el cliente: " + e.getMessage();
        }
    }
}
