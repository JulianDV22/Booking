package fincaTest;

import modelo.datos.FarmData;
import modelo.entidades.Farm;

import java.io.IOException;
import java.util.List;

public class FincaDatosTest {

    public static void main(String[] args) {
        try {
            // Crear una finca
            Farm farm1 = Farm.build("Finca Bella Vista", 250.0, "Medellín", 10.5);
            Farm farm2 = Farm.build("Finca El Paraíso", 300.0, "Bogotá", 15.0);
            Farm farm3 = Farm.build("Finca Los Pinos", 200.0, "Medellín", 8.0);

            FarmData.createFarm(farm1);
            FarmData.createFarm(farm2);
            FarmData.createFarm(farm3);

            System.out.println("Fincas creadas exitosamente.");

            // Leer todas las fincas
            List<Farm> farms = FarmData.findFarms();
            System.out.println("\nFincas disponibles:");
            for (Farm farm : farms) {
                System.out.println(farm);
            }

            // Buscar finca por ciudad
            String ciudadBusqueda = "Medellín";
            List<Farm> fincasEnCiudad = FarmData.findFarmsByCity(ciudadBusqueda);
            System.out.println("\nFincas en la ciudad de " + ciudadBusqueda + ":");
            for (Farm farm : fincasEnCiudad) {
                System.out.println(farm);
            }

            // Buscar finca por nombre
            String nombreBusqueda = "Finca Bella Vista";
            Farm farmEncontrada = FarmData.findByName(nombreBusqueda);
            if (farmEncontrada != null) {
                System.out.println("\nFinca encontrada por nombre: " + farmEncontrada);
            } else {
                System.out.println("\nFinca no encontrada con el nombre: " + nombreBusqueda);
            }

            // Actualizar finca
            Farm farmActualizada = Farm.build("Finca Bella Vista", 275.0, "Medellín", 12.0);
            int indiceActualizar = 1; // Índice en Excel (finca1 es la primera creada)
            FarmData.updateFarm(indiceActualizar, farmActualizada);
            System.out.println("\nFinca actualizada exitosamente.");

            // Leer todas las fincas después de la actualización
            farms = FarmData.findFarms();
            System.out.println("\nFincas después de la actualización:");
            for (Farm farm : farms) {
                System.out.println(farm);
            }

            // Eliminar finca
            int indiceEliminar = 2; // Índice en Excel (finca2 es la segunda creada)
            FarmData.deleteFarm(indiceEliminar);
            System.out.println("\nFinca eliminada exitosamente.");

            // Leer todas las fincas después de la eliminación
            farms = FarmData.findFarms();
            System.out.println("\nFincas después de la eliminación:");
            for (Farm farm : farms) {
                System.out.println(farm);
            }

        } catch (IOException e) {
            System.out.println("Error durante el test de FincaDatos: " + e.getMessage());
        }
    }
}
