package diaDeSolTest;

import controlador.SunnyDayController;
import modelo.entidades.SunnyDay;

import java.util.List;

public class DiaDeSolControladorTest {

    public static void main(String[] args) {
        // Crear un nuevo día de sol
        String resultadoCrear = SunnyDayController.createSunnyDay("Día Familiar", 100.0, "Bogotá", List.of("Piscina", "Juegos Infantiles"), true, false);
        System.out.println(resultadoCrear);

        // Listar todos los días de sol
        System.out.println("\n=== Lista de Días de Sol ===");
        List<SunnyDay> diasDeSol = SunnyDayController.findSunnyDays();
        diasDeSol.forEach(System.out::println);

        // Buscar días de sol por ciudad
        System.out.println("\n=== Buscar Días de Sol en Bogotá ===");
        List<SunnyDay> diasEnBogota = SunnyDayController.findSunnyDayByCity("Bogotá");
        diasEnBogota.forEach(System.out::println);

        // Actualizar un día de sol
        String resultadoActualizar = SunnyDayController.updateSunnyDay(1, "Día Familiar Actualizado", 120.0, "Bogotá", List.of("Piscina", "Yoga"), true, true);
        System.out.println(resultadoActualizar);

        // Eliminar un día de sol
        String resultadoEliminar = SunnyDayController.deleteSunnyDay(1);
        System.out.println(resultadoEliminar);
    }
}
