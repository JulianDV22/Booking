package test;

import modelo.datos.SunnyDayData;
import modelo.entidades.SunnyDay;

import java.io.IOException;
import java.util.List;

public class DiaDeSolDatosTest {

    public static void main(String[] args) {
        try {
            // Crear días de sol
            SunnyDay dia1 = SunnyDay.build("Día Relax", 120.0, "Cartagena", List.of("Piscina", "Spa", "Yoga"), true, true);
            SunnyDay dia2 = SunnyDay.build("Aventura Extrema", 150.0, "Medellín", List.of("Senderismo", "Rappel", "Kayak"), false, true);
            SunnyDay dia3 = SunnyDay.build("Día Familiar", 100.0, "Bogotá", List.of("Juegos Infantiles", "Piscina"), true, false);

            SunnyDayData.createSunnyDay(dia1);
            SunnyDayData.createSunnyDay(dia2);
            SunnyDayData.createSunnyDay(dia3);
            System.out.println("Días de sol creados exitosamente.");

            // Leer todos los días de sol
            System.out.println("\n=== Lista de Todos los Días de Sol ===");
            List<SunnyDay> diasDeSol = SunnyDayData.findSunnyDays();
            diasDeSol.forEach(System.out::println);

            // Buscar días de sol por ciudad
            System.out.println("\n=== Buscar Días de Sol en Medellín ===");
            List<SunnyDay> diasEnMedellin = SunnyDayData.findByCity("Medellín");
            diasEnMedellin.forEach(System.out::println);

            // Actualizar un día de sol
            System.out.println("\n=== Actualizar Día de Sol ===");
            SunnyDay diaActualizado = SunnyDay.build("Aventura Extrema", 180.0, "Medellín", List.of("Senderismo", "Rappel", "Kayak", "Canopy"), false, true);
            SunnyDayData.updateSunnyDay(2, diaActualizado);
            System.out.println("Día de sol actualizado.");

            // Leer todos los días de sol después de la actualización
            System.out.println("\n=== Lista de Días de Sol Después de la Actualización ===");
            diasDeSol = SunnyDayData.findSunnyDays();
            diasDeSol.forEach(System.out::println);

            // Eliminar un día de sol
            System.out.println("\n=== Eliminar Día de Sol ===");
            SunnyDayData.deleteSunnyDay(1);
            System.out.println("Día de sol eliminado.");

            // Leer todos los días de sol después de la eliminación
            System.out.println("\n=== Lista de Días de Sol Después de la Eliminación ===");
            diasDeSol = SunnyDayData.findSunnyDays();
            diasDeSol.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
