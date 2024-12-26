package controlador;

import modelo.datos.FincaDatos;
import modelo.entidades.Finca;

import java.io.IOException;
import java.util.List;

public class FincaControlador {

    // Crear una finca
    public static String crearFinca(String nombre, double precioPorNoche, double tamaño) {
        try {
            Finca finca = Finca.build(nombre, precioPorNoche, tamaño);
            FincaDatos.crearFinca(finca);
            return "Finca creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la finca: " + e.getMessage();
        }
    }

    // Listar todas las fincas
    public static List<Finca> listarFincas() {
        try {
            return FincaDatos.leerFincas();
        } catch (IOException e) {
            System.out.println("Error al leer las fincas: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar una finca
    public static String actualizarFinca(int indice, Finca finca) {
        try {
            FincaDatos.actualizarFinca(indice, finca);
            return "Finca actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la finca: " + e.getMessage();
        }
    }

    // Eliminar una finca
    public static String eliminarFinca(int indice) {
        try {
            FincaDatos.eliminarFinca(indice);
            return "Finca eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la finca: " + e.getMessage();
        }
    }
}
