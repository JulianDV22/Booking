package test;

import modelo.datos.DiaDeSolDatos;
import modelo.entidades.DiaDeSol;

import java.io.IOException;
import java.util.List;

public class DiaDeSolDatosTest {

    public static void main(String[] args) {
        try {
            // Crear días de sol
            DiaDeSol dia1 = DiaDeSol.build("Día Relax", 120.0, "Cartagena", List.of("Piscina", "Spa", "Yoga"), true, true);
            DiaDeSol dia2 = DiaDeSol.build("Aventura Extrema", 150.0, "Medellín", List.of("Senderismo", "Rappel", "Kayak"), false, true);
            DiaDeSol dia3 = DiaDeSol.build("Día Familiar", 100.0, "Bogotá", List.of("Juegos Infantiles", "Piscina"), true, false);

            DiaDeSolDatos.crearDiaDeSol(dia1);
            DiaDeSolDatos.crearDiaDeSol(dia2);
            DiaDeSolDatos.crearDiaDeSol(dia3);
            System.out.println("Días de sol creados exitosamente.");

            // Leer todos los días de sol
            System.out.println("\n=== Lista de Todos los Días de Sol ===");
            List<DiaDeSol> diasDeSol = DiaDeSolDatos.leerDiasDeSol();
            diasDeSol.forEach(System.out::println);

            // Buscar días de sol por ciudad
            System.out.println("\n=== Buscar Días de Sol en Medellín ===");
            List<DiaDeSol> diasEnMedellin = DiaDeSolDatos.buscarPorCiudad("Medellín");
            diasEnMedellin.forEach(System.out::println);

            // Actualizar un día de sol
            System.out.println("\n=== Actualizar Día de Sol ===");
            DiaDeSol diaActualizado = DiaDeSol.build("Aventura Extrema", 180.0, "Medellín", List.of("Senderismo", "Rappel", "Kayak", "Canopy"), false, true);
            DiaDeSolDatos.actualizarDiaDeSol(2, diaActualizado);
            System.out.println("Día de sol actualizado.");

            // Leer todos los días de sol después de la actualización
            System.out.println("\n=== Lista de Días de Sol Después de la Actualización ===");
            diasDeSol = DiaDeSolDatos.leerDiasDeSol();
            diasDeSol.forEach(System.out::println);

            // Eliminar un día de sol
            System.out.println("\n=== Eliminar Día de Sol ===");
            DiaDeSolDatos.eliminarDiaDeSol(1);
            System.out.println("Día de sol eliminado.");

            // Leer todos los días de sol después de la eliminación
            System.out.println("\n=== Lista de Días de Sol Después de la Eliminación ===");
            diasDeSol = DiaDeSolDatos.leerDiasDeSol();
            diasDeSol.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
