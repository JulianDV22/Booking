package controlador;

import modelo.entidades.*;
import modelo.datos.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlojamientoControlador {
    // Método para buscar alojamientos según los parámetros
    public static List<String> buscarAlojamientos(String nombre, String tipoAlojamiento, LocalDate inicio, LocalDate fin, int adultos, int niños, int cantidadHabitaciones) throws IOException {
        List<String> resultados = new ArrayList<>();
        long diasEstadia = calcularDiasEstadia(inicio, fin);

        switch (tipoAlojamiento.toLowerCase()) {
            case "hotel":
                resultados.addAll(buscarHoteles(nombre, inicio, fin, cantidadHabitaciones, diasEstadia));
                break;
            case "apartamento":
                resultados.addAll(buscarApartamentos(nombre, inicio, fin, cantidadHabitaciones, diasEstadia));
                break;
            case "finca":
                resultados.addAll(buscarFincas(nombre, inicio, fin, cantidadHabitaciones, diasEstadia));
                break;
            case "día de sol":
                resultados.addAll(buscarDiasDeSol(nombre));
                break;
            default:
                resultados.add("Tipo de alojamiento no reconocido.");
        }

        return resultados;
    }

    // Buscar hoteles según los parámetros
    private static List<String> buscarHoteles(String nombre, LocalDate inicio, LocalDate fin, int cantidadHabitaciones, long diasEstadia) throws IOException {
        List<Hotel> hoteles = HotelDatos.leerHoteles();
        List<String> resultados = new ArrayList<>();

        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombre)) {
                double precioBase = calcularPrecioBase(hotel, cantidadHabitaciones, diasEstadia);
                double ajuste = calcularAjuste(inicio, fin, precioBase);

                resultados.add(String.format(
                        "Nombre: %s, Calificación: %d, Precio por noche: %.2f, Precio total: %.2f, Ajuste: %.2f",
                        hotel.getNombre(),
                        hotel.getCalificacion(),
                        hotel.getPrecioPorNoche(),
                        precioBase + ajuste,
                        ajuste
                ));
            }
        }

        return resultados;
    }

    // Buscar apartamentos según los parámetros
    private static List<String> buscarApartamentos(String nombre, LocalDate inicio, LocalDate fin, int cantidadHabitaciones, long diasEstadia) throws IOException {
        List<Apartamento> apartamentos = ApartamentoDatos.leerApartamentos();
        List<String> resultados = new ArrayList<>();

        for (Apartamento apartamento : apartamentos) {
            if (apartamento.getNombre().equalsIgnoreCase(nombre)) {
                double precioBase = calcularPrecioBase(apartamento, cantidadHabitaciones, diasEstadia);
                double ajuste = calcularAjuste(inicio, fin, precioBase);

                resultados.add(String.format(
                        "Nombre: %s, Precio por noche: %.2f, Precio total: %.2f, Ajuste: %.2f, Amueblado: %s",
                        apartamento.getNombre(),
                        apartamento.getPrecioPorNoche(),
                        precioBase + ajuste,
                        ajuste,
                        apartamento.isAmueblado() ? "Sí" : "No"
                ));
            }
        }

        return resultados;
    }

    // Buscar fincas según los parámetros
    private static List<String> buscarFincas(String nombre, LocalDate inicio, LocalDate fin, int cantidadHabitaciones, long diasEstadia) throws IOException {
        List<Finca> fincas = FincaDatos.leerFincas();
        List<String> resultados = new ArrayList<>();

        for (Finca finca : fincas) {
            if (finca.getNombre().equalsIgnoreCase(nombre)) {
                double precioBase = calcularPrecioBase(finca, cantidadHabitaciones, diasEstadia);
                double ajuste = calcularAjuste(inicio, fin, precioBase);

                resultados.add(String.format(
                        "Nombre: %s, Precio por noche: %.2f, Precio total: %.2f, Ajuste: %.2f, Tamaño: %.2f hectáreas",
                        finca.getNombre(),
                        finca.getPrecioPorNoche(),
                        precioBase + ajuste,
                        ajuste,
                        finca.getArea()
                ));
            }
        }

        return resultados;
    }

    // Buscar días de sol
    private static List<String> buscarDiasDeSol(String nombre) throws IOException {
        List<DiaDeSol> diasDeSol = DiaDeSolDatos.leerDiasDeSol();
        List<String> resultados = new ArrayList<>();

        for (DiaDeSol dia : diasDeSol) {
            if (dia.getNombre().equalsIgnoreCase(nombre)) {
                resultados.add(String.format(
                        "Nombre: %s, Precio por día: %.2f, Actividades: %s",
                        dia.getNombre(),
                        dia.getPrecioPorNoche(),
                        String.join(", ", dia.getActividades())
                ));
            }
        }

        return resultados;
    }

    // Calcular el precio base
    private static double calcularPrecioBase(Alojamiento alojamiento, int cantidadHabitaciones, long diasEstadia) {
        return alojamiento.getPrecioPorNoche() * cantidadHabitaciones * diasEstadia;
    }

    // Calcular ajuste por fechas
    private static double calcularAjuste(LocalDate inicio, LocalDate fin, double precioBase) {
        int inicioDia = inicio.getDayOfMonth();
        int finDia = fin.getDayOfMonth();

        if (inicioDia >= 5 && finDia <= 10) {
            return -precioBase * 0.08; // Descuento del 8%
        } else if (inicioDia >= 10 && finDia <= 15) {
            return precioBase * 0.10; // Aumento del 10%
        } else if (finDia >= 26 && finDia <= 31) {
            return precioBase * 0.15; // Aumento del 15%
        }

        return 0;
    }

    // Calcular los días de estadía
    private static long calcularDiasEstadia(LocalDate inicio, LocalDate fin) {
        return java.time.temporal.ChronoUnit.DAYS.between(inicio, fin);
    }

    // Método para confirmar disponibilidad de habitaciones
    public static List<String> confirmarHabitacionesDisponibles(
            String nombreHotel,
            LocalDate inicio,
            LocalDate fin,
            int cantidadAdultos,
            int cantidadNiños,
            int cantidadHabitaciones) throws IOException {

        List<Hotel> hoteles = HotelDatos.leerHoteles();
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();

        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                // Buscar habitaciones del hotel
                for (Habitacion habitacion : hotel.getHabitaciones()) {
                    if (esHabitacionDisponible(habitacion, inicio, fin, cantidadAdultos, cantidadNiños)) {
                        habitacionesDisponibles.add(habitacion);
                    }
                }
                break;
            }
        }

        // Formatear la información de las habitaciones disponibles
        return formatearHabitacionesDisponibles(habitacionesDisponibles, cantidadHabitaciones);
    }

    // Verificar si una habitación está disponible para las fechas dadas
    private static boolean esHabitacionDisponible(Habitacion habitacion, LocalDate inicio, LocalDate fin, int cantidadAdultos, int cantidadNiños) {
        int[] capacidad = obtenerCapacidadPorTipo(habitacion.getTipo());
        int capacidadAdultos = capacidad[0];
        int capacidadNiños = capacidad[1];

        // Verificar si la capacidad cumple con los requisitos
        return (cantidadAdultos <= capacidadAdultos && cantidadNiños <= capacidadNiños);
    }

    // Obtener capacidad por tipo de habitación
    private static int[] obtenerCapacidadPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "simple" -> new int[]{1, 0};
            case "pareja" -> new int[]{2, 0};
            case "familiar-1" -> new int[]{2, 1};
            case "familiar-2" -> new int[]{2, 2};
            default -> new int[]{0, 0}; // Tipo desconocido
        };
    }

    // Formatear información sobre las habitaciones disponibles
    private static List<String> formatearHabitacionesDisponibles(List<Habitacion> habitacionesDisponibles, int cantidadHabitaciones) {
        List<String> resultados = new ArrayList<>();

        for (Habitacion habitacion : habitacionesDisponibles) {
            String info = String.format(
                    "Tipo: %s, Precio por noche: %.2f, Características: %s",
                    habitacion.getTipo(),
                    habitacion.getPrecio(),
                    String.join(", ", habitacion.getCaracteristicas())
            );

            resultados.add(info);

            // Detener si alcanzamos la cantidad requerida
            if (resultados.size() >= cantidadHabitaciones) {
                break;
            }
        }

        return resultados.isEmpty() ? List.of("No hay habitaciones disponibles para los parámetros proporcionados.") : resultados;
    }
}
