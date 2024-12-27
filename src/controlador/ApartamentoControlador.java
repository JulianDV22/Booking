package controlador;

import modelo.datos.ApartamentoDatos;
import modelo.entidades.Apartamento;

import java.io.IOException;
import java.util.List;

public class ApartamentoControlador {

    // Crear un apartamento
    public static String crearApartamento(String nombre, double precioPorNoche, String ciudad, boolean amueblado, List<String> caracteristicas) {
        try {
            Apartamento nuevoApartamento = Apartamento.build(nombre, precioPorNoche, ciudad, amueblado, caracteristicas);
            ApartamentoDatos.crearApartamento(nuevoApartamento);
            return "Apartamento creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el apartamento: " + e.getMessage();
        }
    }

    // Listar todos los apartamentos
    public static List<Apartamento> listarApartamentos() {
        try {
            return ApartamentoDatos.leerApartamentos();
        } catch (IOException e) {
            System.out.println("Error al leer los apartamentos: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un apartamento
    public static String actualizarApartamento(String nombre, String ciudad, String nuevoNombre, double nuevoPrecioPorNoche, boolean nuevoAmueblado, List<String> nuevasCaracteristicas) {
        try {
            Apartamento nuevoApartamento = Apartamento.build(nuevoNombre, nuevoPrecioPorNoche, ciudad, nuevoAmueblado, nuevasCaracteristicas);
            ApartamentoDatos.actualizarApartamento(nombre, ciudad, nuevoApartamento);
            return "Apartamento actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el apartamento: " + e.getMessage();
        }
    }

    // Eliminar un apartamento
    public static String eliminarApartamento(String nombre, String ciudad) {
        try {
            ApartamentoDatos.eliminarApartamento(nombre, ciudad);
            return "Apartamento eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el apartamento: " + e.getMessage();
        }
    }

    // Buscar apartamentos por ciudad
    public static List<Apartamento> buscarApartamentosPorCiudad(String ciudad) {
        try {
            return ApartamentoDatos.buscarPorCiudad(ciudad);
        } catch (IOException e) {
            System.out.println("Error al buscar apartamentos por ciudad: " + e.getMessage());
            return List.of();
        }
    }

    // Buscar apartamento por nombre
    public static Apartamento buscarApartamentoPorNombre(String nombre) {
        try {
            return ApartamentoDatos.buscarPorNombre(nombre);
        } catch (IOException e) {
            System.out.println("Error al buscar el apartamento por nombre: " + e.getMessage());
            return null;
        }
    }
}
