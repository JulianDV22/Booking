package controlador;

import modelo.datos.SunnyDayData;
import modelo.entidades.SunnyDay;

import java.io.IOException;
import java.util.List;

public class SunnyDayController {

    /**
     * Crear un nuevo día de sol.
     */
    public static String createSunnyDay(String name, double pricePerDay, String city, List<String> activities, boolean includesLunch, boolean includesSnack) {
        try {
            SunnyDay sunnyDay = SunnyDay.build(name, pricePerDay, city, activities, includesLunch, includesSnack);
            SunnyDayData.createSunnyDay(sunnyDay);
            return "Día de Sol creado exitosamente.";
        } catch (IOException e) {
            return "Error al crear el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Leer todos los días de sol.
     */
    public static List<SunnyDay> findSunnyDays() {
        try {
            return SunnyDayData.findSunnyDays();
        } catch (IOException e) {
            System.err.println("Error al leer los Días de Sol: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Actualizar un día de sol.
     */
    public static String updateSunnyDay(int index, String name, double pricePerDay, String city, List<String> activities, boolean includesLunch, boolean includesSnack) {
        try {
            SunnyDay sunnyDay = SunnyDay.build(name, pricePerDay, city, activities, includesLunch, includesSnack);
            SunnyDayData.updateSunnyDay(index, sunnyDay);
            return "Día de Sol actualizado exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Eliminar un día de sol.
     */
    public static String deleteSunnyDay(int index) {
        try {
            SunnyDayData.deleteSunnyDay(index);
            return "Día de Sol eliminado exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el Día de Sol: " + e.getMessage();
        }
    }

    /**
     * Buscar días de sol por ciudad.
     */
    public static List<SunnyDay> findSunnyDayByCity(String city) {
        try {
            return SunnyDayData.findByCity(city);
        } catch (IOException e) {
            System.err.println("Error al buscar los Días de Sol por ciudad: " + e.getMessage());
            return List.of();
        }
    }
}
