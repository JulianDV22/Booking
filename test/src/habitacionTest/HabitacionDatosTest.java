package habitacionTest;

import modelo.datos.HabitacionDatos;
import modelo.entidades.Habitacion;

import java.io.IOException;
import java.util.List;

public class HabitacionDatosTest {
    public static void main(String[] args) {
        try {
            // Crear habitaciones de ejemplo
            Habitacion simple = Habitacion.build("Simple", List.of("Aire acondicionado", "Televisión"), 50.0, "Hotel Central", true);
            Habitacion pareja = Habitacion.build("Pareja", List.of("Cama doble", "Vista al mar"), 100.0, "Hotel Central", true);
            Habitacion familiar1 = Habitacion.build("Familiar-1", List.of("Dos camas dobles", "Cafetera"), 150.0, "Hotel Central", true);
            Habitacion familiar2 = Habitacion.build("Familiar-2", List.of("Cama king-size", "Sala de estar"), 200.0, "Hotel Central", true);
            Habitacion suite = Habitacion.build("Suite", List.of("Jacuzzi", "Balcón privado"), 300.0, "Hotel Central", true);

            // Crear habitaciones en el archivo Excel
            HabitacionDatos.crearHabitacion(simple);
            HabitacionDatos.crearHabitacion(pareja);
            HabitacionDatos.crearHabitacion(familiar1);
            HabitacionDatos.crearHabitacion(familiar2);
            HabitacionDatos.crearHabitacion(suite);

            // Leer habitaciones
            List<Habitacion> habitaciones = HabitacionDatos.leerHabitaciones();
            System.out.println("Habitaciones registradas:");
            habitaciones.forEach(System.out::println);

            // Buscar habitaciones por hotel
            List<Habitacion> habitacionesHotel = HabitacionDatos.buscarPorHotel("Hotel Central");
            System.out.println("\nHabitaciones del Hotel Central:");
            habitacionesHotel.forEach(System.out::println);

            // Buscar habitaciones disponibles de tipo "Pareja"
            List<Habitacion> habitacionesPareja = HabitacionDatos.buscarDisponiblesPorTipo("Pareja");
            System.out.println("\nHabitaciones disponibles de tipo 'Pareja':");
            habitacionesPareja.forEach(System.out::println);

            // Actualizar una habitación
            Habitacion nuevaHabitacion = Habitacion.build("Pareja", List.of("Cama doble", "Vista a la ciudad"), 120.0, "Hotel Central", true);
            HabitacionDatos.actualizarHabitacion("Pareja", "Hotel Central", nuevaHabitacion);
            System.out.println("\nHabitación actualizada: ");
            HabitacionDatos.leerHabitaciones().forEach(System.out::println);

            // Eliminar una habitación
            HabitacionDatos.eliminarHabitacion("Simple", "Hotel Central");
            System.out.println("\nHabitaciones después de eliminar la Suite:");
            HabitacionDatos.leerHabitaciones().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error en las operaciones de HabitacionDatos: " + e.getMessage());
        }
    }
}
