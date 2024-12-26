package controlador;

import modelo.datos.ClienteDatos;
import modelo.entidades.Cliente;

import java.io.IOException;
import java.util.List;

public class ClienteControlador {

    // Crear un cliente
    public static String crearCliente(String nombre, String apellido, String email, String nacionalidad, String telefono, java.time.LocalDate fechaNacimiento) {
        try {
            Cliente cliente = Cliente.build(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);
            ClienteDatos.crearCliente(cliente);
            return "Cliente creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el cliente: " + e.getMessage();
        }
    }

    // Listar todos los clientes
    public static List<Cliente> listarClientes() {
        try {
            return ClienteDatos.leerClientes();
        } catch (IOException e) {
            System.out.println("Error al leer los clientes: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un cliente
    public static String actualizarCliente(int indice, Cliente cliente) {
        try {
            ClienteDatos.actualizarCliente(indice, cliente);
            return "Cliente actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el cliente: " + e.getMessage();
        }
    }

    // Eliminar un cliente
    public static String eliminarCliente(int indice) {
        try {
            ClienteDatos.eliminarCliente(indice);
            return "Cliente eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el cliente: " + e.getMessage();
        }
    }
}
