package controlador;

import modelo.datos.DiaDeSolDatos;
import modelo.entidades.DiaDeSol;

import java.io.IOException;
import java.util.List;

public class DiaDeSolControlador {

    /**
     * Crear un nuevo día de sol.
     */
    public static String crearDiaDeSol(String nombre, double precioPorEstadia, String ciudad, List<String> actividades, boolean incluyeAlmuerzo, boolean incluyeRefrigerio) {
        try {
            DiaDeSol diaDeSol = DiaDeSol.build(nombre, precioPorEstadia, ciudad, actividades, incluyeAlmuerzo, incluyeRefrigerio);
            DiaDeSolDatos.crearDiaDeSol(diaDeSol);
            return "Día de Sol creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Leer todos los días de sol.
     */
    public static List<DiaDeSol> listarDiasDeSol() {
        try {
            return DiaDeSolDatos.leerDiasDeSol();
        } catch (IOException e) {
            System.err.println("Error al leer los Días de Sol: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Actualizar un día de sol.
     */
    public static String actualizarDiaDeSol(int indice, String nombre, double precioPorEstadia, String ciudad, List<String> actividades, boolean incluyeAlmuerzo, boolean incluyeRefrigerio) {
        try {
            DiaDeSol diaDeSol = DiaDeSol.build(nombre, precioPorEstadia, ciudad, actividades, incluyeAlmuerzo, incluyeRefrigerio);
            DiaDeSolDatos.actualizarDiaDeSol(indice, diaDeSol);
            return "Día de Sol actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Eliminar un día de sol.
     */
    public static String eliminarDiaDeSol(int indice) {
        try {
            DiaDeSolDatos.eliminarDiaDeSol(indice);
            return "Día de Sol eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Buscar días de sol por ciudad.
     */
    public static List<DiaDeSol> buscarDiasDeSolPorCiudad(String ciudad) {
        try {
            return DiaDeSolDatos.buscarPorCiudad(ciudad);
        } catch (IOException e) {
            System.err.println("Error al buscar los Días de Sol por ciudad: " + e.getMessage());
            return List.of();
        }
    }
}
