package controlador;

import modelo.datos.FarmData;
import modelo.entidades.Farm;

import java.io.IOException;
import java.util.List;

public class FarmController {
    public static String createFarm(String name, Double pricePerNight, String city, Double area) {
        try {
            Farm farm = Farm.build(name, pricePerNight, city, area);
            FarmData.createFarm(farm);
            return "Finca creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la finca: " + e.getMessage();
        }
    }

    public static List<Farm> findFarms() {
        try {
            return FarmData.findFarms();
        } catch (IOException e) {
            System.out.println("Error al listar las fincas: " + e.getMessage());
            return List.of();
        }
    }

    public static String updateFarm(String name, String city, String newName, double newPricePerNight, double newArea, Farm oldFarm) {
        try {
            Farm newFarm = Farm.build(newName, newPricePerNight, city, newArea);
            FarmData.updateFarm(oldFarm, newFarm);
            return "Finca actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la finca: " + e.getMessage();
        }
    }

    public static String deleteFarm(String name) {
        try {
            FarmData.deleteFarm(name);
            return "Finca eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar el hotel: " + e.getMessage();
        }
    }

    public static List<Farm> findFarmByCity(String city) {
        try {
            return FarmData.findFarmsByCity(city);
        } catch (IOException e) {
            System.out.println("Error al buscar hoteles por ciudad: " + e.getMessage());
            return List.of();
        }
    }

    public static Farm findFarmByName(String name) {
        try {
            return FarmData.findByName(name);
        } catch (IOException e) {
            System.out.println("Error al buscar el hotel por nombre: " + e.getMessage());
            return null;
        }
    }
}
