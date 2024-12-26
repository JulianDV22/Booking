package controlador;

import modelo.datos.ApartamentoDatos;
import modelo.entidades.Apartamento;

import java.io.IOException;
import java.util.List;

public class ApartamentoControlador {

    // Crear un apartamento
    public static String crearApartamento(String nombre, double precioPorNoche, boolean amueblado) {
        try {
            Apartamento apartamento = Apartamento.build(nombre, precioPorNoche, amueblado);
            ApartamentoDatos.crearApartamento(apartamento);
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
    public static String actualizarApartamento(int indice, Apartamento apartamento) {
        try {
            ApartamentoDatos.actualizarApartamento(indice, apartamento);
            return "Apartamento actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el apartamento: " + e.getMessage();
        }
    }

    // Eliminar un apartamento
    public static String eliminarApartamento(int indice) {
        try {
            ApartamentoDatos.eliminarApartamento(indice);
            return "Apartamento eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el apartamento: " + e.getMessage();
        }
    }
}
