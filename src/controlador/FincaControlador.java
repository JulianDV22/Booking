package controlador;

import modelo.datos.FincaDatos;
import modelo.entidades.Finca;

import java.io.IOException;
import java.util.List;

public class FincaControlador {

    // Crear una finca
    public static String crearFinca(String nombre, double precioPorNoche, String ciudad, double area) {
        try {
            Finca finca = Finca.build(nombre, precioPorNoche, ciudad, area);
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

    // Buscar fincas por ciudad
    public static List<Finca> buscarFincasPorCiudad(String ciudad) {
        try {
            return FincaDatos.buscarPorCiudad(ciudad);
        } catch (IOException e) {
            System.out.println("Error al buscar las fincas por ciudad: " + e.getMessage());
            return List.of();
        }
    }

    // Buscar finca por nombre
    public static Finca buscarFincaPorNombre(String nombre) {
        try {
            return FincaDatos.buscarPorNombre(nombre);
        } catch (IOException e) {
            System.out.println("Error al buscar la finca por nombre: " + e.getMessage());
            return null;
        }
    }

    // Actualizar una finca
    public static String actualizarFinca(String nombre, double precioPorNoche, String ciudad, double area) {
        try {
            Finca finca = Finca.build(nombre, precioPorNoche, ciudad, area);
            int indice = buscarIndiceFinca(finca);

            if (indice == -1) {
                return "Finca no encontrada para actualizar.";
            }

            FincaDatos.actualizarFinca(indice, finca);
            return "Finca actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la finca: " + e.getMessage();
        }
    }

    // Eliminar una finca
    public static String eliminarFinca(String nombre, String ciudad) {
        try {
            Finca finca = Finca.build(nombre, 0.0, ciudad, 0.0);
            int indice = buscarIndiceFinca(finca);

            if (indice == -1) {
                return "Finca no encontrada para eliminar.";
            }

            FincaDatos.eliminarFinca(indice);
            return "Finca eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la finca: " + e.getMessage();
        }
    }

    // Buscar índice de la finca
    private static int buscarIndiceFinca(Finca finca) throws IOException {
        List<Finca> fincas = FincaDatos.leerFincas();
        for (int i = 0; i < fincas.size(); i++) {
            if (fincas.get(i).getNombre().equalsIgnoreCase(finca.getNombre()) &&
                    fincas.get(i).getCiudad().equalsIgnoreCase(finca.getCiudad())) {
                return i + 1; // Ajustar índice para reflejar la fila en Excel
            }
        }
        return -1; // No encontrado
    }
}