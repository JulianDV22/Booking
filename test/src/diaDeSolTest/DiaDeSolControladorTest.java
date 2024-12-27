package diaDeSolTest;

import controlador.DiaDeSolControlador;
import modelo.entidades.DiaDeSol;

import java.util.List;

public class DiaDeSolControladorTest {

    public static void main(String[] args) {
        // Crear un nuevo día de sol
        String resultadoCrear = DiaDeSolControlador.crearDiaDeSol("Día Familiar", 100.0, "Bogotá", List.of("Piscina", "Juegos Infantiles"), true, false);
        System.out.println(resultadoCrear);

        // Listar todos los días de sol
        System.out.println("\n=== Lista de Días de Sol ===");
        List<DiaDeSol> diasDeSol = DiaDeSolControlador.listarDiasDeSol();
        diasDeSol.forEach(System.out::println);

        // Buscar días de sol por ciudad
        System.out.println("\n=== Buscar Días de Sol en Bogotá ===");
        List<DiaDeSol> diasEnBogota = DiaDeSolControlador.buscarDiasDeSolPorCiudad("Bogotá");
        diasEnBogota.forEach(System.out::println);

        // Actualizar un día de sol
        String resultadoActualizar = DiaDeSolControlador.actualizarDiaDeSol(1, "Día Familiar Actualizado", 120.0, "Bogotá", List.of("Piscina", "Yoga"), true, true);
        System.out.println(resultadoActualizar);

        // Eliminar un día de sol
        String resultadoEliminar = DiaDeSolControlador.eliminarDiaDeSol(1);
        System.out.println(resultadoEliminar);
    }
}
