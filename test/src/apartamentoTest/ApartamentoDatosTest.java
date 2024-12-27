package apartamentoTest;

import modelo.datos.ApartamentoDatos;
import modelo.entidades.Apartamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDatosTest {

    public static void main(String[] args) {
        try {
            System.out.println("=== Test ApartamentoDatos ===");

            // Crear Apartamentos
            System.out.println("\n--- Crear Apartamentos ---");
            Apartamento apartamento1 = Apartamento.build("Apartamento A", 150.0, "Bogotá", true, List.of("WiFi", "Cocina", "Vista a la ciudad"));
            Apartamento apartamento2 = Apartamento.build("Apartamento B", 200.0, "Bogotá", false, List.of("Balcón", "Aire acondicionado"));
            Apartamento apartamento3 = Apartamento.build("Apartamento C", 120.0, "Medellín", true, List.of("Piscina", "Gimnasio"));

            ApartamentoDatos.crearApartamento(apartamento1);
            ApartamentoDatos.crearApartamento(apartamento2);
            ApartamentoDatos.crearApartamento(apartamento3);
            System.out.println("Apartamentos creados.");

            // Leer todos los Apartamentos
            System.out.println("\n--- Leer Todos los Apartamentos ---");
            List<Apartamento> apartamentos = ApartamentoDatos.leerApartamentos();
            for (Apartamento apartamento : apartamentos) {
                System.out.println(apartamento);
            }

            // Buscar Apartamentos por Ciudad
            System.out.println("\n--- Buscar Apartamentos por Ciudad (Bogotá) ---");
            List<Apartamento> apartamentosBogota = ApartamentoDatos.buscarPorCiudad("Bogotá");
            for (Apartamento apartamento : apartamentosBogota) {
                System.out.println(apartamento);
            }

            // Buscar Apartamento por Nombre
            System.out.println("\n--- Buscar Apartamento por Nombre (Apartamento B) ---");
            Apartamento apartamentoB = ApartamentoDatos.buscarPorNombre("Apartamento B");
            if (apartamentoB != null) {
                System.out.println("Encontrado: " + apartamentoB);
            } else {
                System.out.println("No se encontró el Apartamento B.");
            }

            // Actualizar Apartamento
            System.out.println("\n--- Actualizar Apartamento (Apartamento B) ---");
            Apartamento apartamentoActualizado = Apartamento.build("Apartamento B", 250.0, "Bogotá", true, List.of("Balcón", "Jacuzzi", "Aire acondicionado"));
            ApartamentoDatos.actualizarApartamento("Apartamento B", "Bogotá", apartamentoActualizado);
            System.out.println("Apartamento B actualizado.");
            List<Apartamento> apartamentosActualizados = ApartamentoDatos.leerApartamentos();
            for (Apartamento apartamento : apartamentosActualizados) {
                System.out.println(apartamento);
            }

            // Eliminar Apartamento
            System.out.println("\n--- Eliminar Apartamento (Apartamento C) ---");
            ApartamentoDatos.eliminarApartamento("Apartamento C", "Medellín");
            System.out.println("Apartamento C eliminado.");
            List<Apartamento> apartamentosDespuesEliminacion = ApartamentoDatos.leerApartamentos();
            for (Apartamento apartamento : apartamentosDespuesEliminacion) {
                System.out.println(apartamento);
            }

        } catch (IOException e) {
            System.out.println("Error en el test: " + e.getMessage());
        }
    }
}

