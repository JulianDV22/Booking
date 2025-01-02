package apartamentoTest;

import modelo.datos.ApartmentData;
import modelo.entidades.Apartment;

import java.io.IOException;
import java.util.List;

public class ApartamentoDatosTest {

    public static void main(String[] args) {
        try {
            System.out.println("=== Test ApartamentoDatos ===");

            // Crear Apartamentos
            System.out.println("\n--- Crear Apartamentos ---");
            Apartment apartment1 = Apartment.build("Apartamento A", 150.0, "Bogotá", true, List.of("WiFi", "Cocina", "Vista a la ciudad"));
            Apartment apartment2 = Apartment.build("Apartamento B", 200.0, "Bogotá", false, List.of("Balcón", "Aire acondicionado"));
            Apartment apartment3 = Apartment.build("Apartamento C", 120.0, "Medellín", true, List.of("Piscina", "Gimnasio"));

            ApartmentData.createApartment(apartment1);
            ApartmentData.createApartment(apartment2);
            ApartmentData.createApartment(apartment3);
            System.out.println("Apartamentos creados.");

            // Leer todos los Apartamentos
            System.out.println("\n--- Leer Todos los Apartamentos ---");
            List<Apartment> apartments = ApartmentData.findApartments();
            for (Apartment apartment : apartments) {
                System.out.println(apartment);
            }

            // Buscar Apartamentos por Ciudad
            System.out.println("\n--- Buscar Apartamentos por Ciudad (Bogotá) ---");
            List<Apartment> apartamentosBogota = ApartmentData.findApartmentPerCity("Bogotá");
            for (Apartment apartment : apartamentosBogota) {
                System.out.println(apartment);
            }

            // Buscar Apartamento por Nombre
            System.out.println("\n--- Buscar Apartamento por Nombre (Apartamento B) ---");
            Apartment apartmentB = ApartmentData.findPerName("Apartamento B");
            if (apartmentB != null) {
                System.out.println("Encontrado: " + apartmentB);
            } else {
                System.out.println("No se encontró el Apartamento B.");
            }

            // Actualizar Apartamento
            System.out.println("\n--- Actualizar Apartamento (Apartamento B) ---");
            Apartment apartmentActualizado = Apartment.build("Apartamento B", 250.0, "Bogotá", true, List.of("Balcón", "Jacuzzi", "Aire acondicionado"));
            ApartmentData.updateApartment("Apartamento B", "Bogotá", apartmentActualizado);
            System.out.println("Apartamento B actualizado.");
            List<Apartment> apartamentosActualizados = ApartmentData.findApartments();
            for (Apartment apartment : apartamentosActualizados) {
                System.out.println(apartment);
            }

            // Eliminar Apartamento
            System.out.println("\n--- Eliminar Apartamento (Apartamento C) ---");
            ApartmentData.deleteApartment("Apartamento C", "Medellín");
            System.out.println("Apartamento C eliminado.");
            List<Apartment> apartamentosDespuesEliminacion = ApartmentData.findApartments();
            for (Apartment apartment : apartamentosDespuesEliminacion) {
                System.out.println(apartment);
            }

        } catch (IOException e) {
            System.out.println("Error en el test: " + e.getMessage());
        }
    }
}

