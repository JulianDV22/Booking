package fincaTest;

import modelo.datos.FincaDatos;
import modelo.entidades.Finca;

import java.io.IOException;
import java.util.List;

public class FincaDatosTest {

    public static void main(String[] args) {
        try {
            // Crear una finca
            Finca finca1 = Finca.build("Finca Bella Vista", 250.0, "Medellín", 10.5);
            Finca finca2 = Finca.build("Finca El Paraíso", 300.0, "Bogotá", 15.0);
            Finca finca3 = Finca.build("Finca Los Pinos", 200.0, "Medellín", 8.0);

            FincaDatos.crearFinca(finca1);
            FincaDatos.crearFinca(finca2);
            FincaDatos.crearFinca(finca3);

            System.out.println("Fincas creadas exitosamente.");

            // Leer todas las fincas
            List<Finca> fincas = FincaDatos.leerFincas();
            System.out.println("\nFincas disponibles:");
            for (Finca finca : fincas) {
                System.out.println(finca);
            }

            // Buscar finca por ciudad
            String ciudadBusqueda = "Medellín";
            List<Finca> fincasEnCiudad = FincaDatos.buscarPorCiudad(ciudadBusqueda);
            System.out.println("\nFincas en la ciudad de " + ciudadBusqueda + ":");
            for (Finca finca : fincasEnCiudad) {
                System.out.println(finca);
            }

            // Buscar finca por nombre
            String nombreBusqueda = "Finca Bella Vista";
            Finca fincaEncontrada = FincaDatos.buscarPorNombre(nombreBusqueda);
            if (fincaEncontrada != null) {
                System.out.println("\nFinca encontrada por nombre: " + fincaEncontrada);
            } else {
                System.out.println("\nFinca no encontrada con el nombre: " + nombreBusqueda);
            }

            // Actualizar finca
            Finca fincaActualizada = Finca.build("Finca Bella Vista", 275.0, "Medellín", 12.0);
            int indiceActualizar = 1; // Índice en Excel (finca1 es la primera creada)
            FincaDatos.actualizarFinca(indiceActualizar, fincaActualizada);
            System.out.println("\nFinca actualizada exitosamente.");

            // Leer todas las fincas después de la actualización
            fincas = FincaDatos.leerFincas();
            System.out.println("\nFincas después de la actualización:");
            for (Finca finca : fincas) {
                System.out.println(finca);
            }

            // Eliminar finca
            int indiceEliminar = 2; // Índice en Excel (finca2 es la segunda creada)
            FincaDatos.eliminarFinca(indiceEliminar);
            System.out.println("\nFinca eliminada exitosamente.");

            // Leer todas las fincas después de la eliminación
            fincas = FincaDatos.leerFincas();
            System.out.println("\nFincas después de la eliminación:");
            for (Finca finca : fincas) {
                System.out.println(finca);
            }

        } catch (IOException e) {
            System.out.println("Error durante el test de FincaDatos: " + e.getMessage());
        }
    }
}
