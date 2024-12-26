package controlador;

import modelo.datos.DiaDeSolDatos;
import modelo.entidades.DiaDeSol;

import java.io.IOException;
import java.util.List;

public class DiaDeSolControlador {

    // Crear un día de sol
    public static String crearDiaDeSol(String nombre, double precioPorNoche, List<String> actividades) {
        try {
            DiaDeSol diaDeSol = DiaDeSol.build(nombre, precioPorNoche, actividades);
            DiaDeSolDatos.crearDiaDeSol(diaDeSol);
            return "Día de sol creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el día de sol: " + e.getMessage();
        }
    }

    // Listar todos los días de sol
    public static List<DiaDeSol> listarDiasDeSol() {
        try {
            return DiaDeSolDatos.leerDiasDeSol();
        } catch (IOException e) {
            System.out.println("Error al leer los días de sol: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar un día de sol
    public static String actualizarDiaDeSol(int indice, DiaDeSol diaDeSol) {
        try {
            DiaDeSolDatos.actualizarDiaDeSol(indice, diaDeSol);
            return "Día de sol actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el día de sol: " + e.getMessage();
        }
    }

    // Eliminar un día de sol
    public static String eliminarDiaDeSol(int indice) {
        try {
            DiaDeSolDatos.eliminarDiaDeSol(indice);
            return "Día de sol eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el día de sol: " + e.getMessage();
        }
    }
}
