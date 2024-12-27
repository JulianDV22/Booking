package controlador;

import modelo.datos.*;
import modelo.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservaControlador {

    // Crear una reserva para Día de Sol
    public static String crearReservaDiaDeSol(String emailCliente, String nombreDiaDeSol, LocalDate fecha, int cantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar Día de Sol por nombre
            DiaDeSol diaDeSol = DiaDeSolDatos.buscarPorNombre(nombreDiaDeSol);
            if (diaDeSol == null) {
                return "Día de Sol no encontrado con el nombre: " + nombreDiaDeSol;
            }
            // Calcular el precio total con reglas de negocio
            double precioBase = diaDeSol.getPrecioPorNoche() * cantidadPersonas;
            double precioTotal = calcularPrecioTotal(precioBase, fecha, fecha);

            // Crear la reserva
            Reserva reserva = Reserva.build(cliente, diaDeSol, fecha, fecha, precioTotal);
            ReservaDatos.crearReserva(reserva);

            // Imprimir factura
            imprimirFactura(reserva, precioBase);

            return "Reserva creada exitosamente para el Día de Sol: " + nombreDiaDeSol;
        } catch (IOException e) {
            return "Error al crear la reserva: " + e.getMessage();
        }
    }

    // Crear una reserva para Finca
    public static String crearReservaFinca(String emailCliente, String nombreFinca, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar Finca por nombre
            Finca finca = FincaDatos.buscarPorNombre(nombreFinca);
            if (finca == null) {
                return "Finca no encontrada con el nombre: " + nombreFinca;
            }

            // Calcular el precio total con reglas de negocio
            long diasEstadia = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
            double precioBase = finca.getPrecioPorNoche() * cantidadPersonas * diasEstadia;
            double precioTotal = calcularPrecioTotal(precioBase, fechaInicio, fechaFin);

            // Crear la reserva
            Reserva reserva = Reserva.build(cliente, finca, fechaInicio, fechaFin, precioTotal);
            ReservaDatos.crearReserva(reserva);

            // Imprimir factura
            imprimirFactura(reserva, precioBase);

            return "Reserva creada exitosamente para la Finca: " + nombreFinca;
        } catch (IOException e) {
            return "Error al crear la reserva: " + e.getMessage();
        }
    }

    // Crear reserva para un apartamento
    public static String crearReservaApartamento(String emailCliente, String nombreApartamento, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar el apartamento por su nombre
            Apartamento apartamento = ApartamentoDatos.buscarPorNombre(nombreApartamento);
            if (apartamento == null) {
                return "El apartamento especificado no existe.";
            }

            // Calcular el precio total con reglas de negocio
            long diasEstadia = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
            double precioBase = apartamento.getPrecioPorNoche() * cantidadPersonas * diasEstadia;
            double precioTotal = calcularPrecioTotal(precioBase, fechaInicio, fechaFin);

            // Crear la reserva
            Reserva reserva = Reserva.build(cliente, apartamento, fechaInicio, fechaFin, precioTotal);
            ReservaDatos.crearReserva(reserva);

            // Imprimir factura
            imprimirFactura(reserva, precioBase);

            return "Reserva creada exitosamente para la Finca: " + nombreApartamento;
        } catch (IOException e) {
            return "Error al crear la reserva: " + e.getMessage();
        }
    }

    // Leer todas las reservas
    public static List<Reserva> listarReservas() {
        try {
            return ReservaDatos.leerReservas();
        } catch (IOException e) {
            System.out.println("Error al leer las reservas: " + e.getMessage());
            return List.of();
        }
    }

    // Crear reserva para un hotel
    public static String crearReservaHotel(String emailCliente, Hotel hotel, Habitacion habitacion, int cantidadPersonas, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);

            if (cliente == null) {
                return "El cliente no está registrado.";
            }

            // Calcular precio total
            long diasEstadia = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
            double precioBase = hotel.getPrecioPorNoche() * cantidadPersonas * diasEstadia;
            double precioTotal = calcularPrecioTotal(precioBase, fechaInicio, fechaFin);

            // Crear y guardar la reserva
            Reserva reserva = Reserva.build(cliente, hotel, fechaInicio, fechaFin, precioTotal);
            ReservaDatos.crearReserva(reserva);

            // Imprimir factura
            imprimirFactura(reserva, precioBase);

            return "Reserva creada exitosamente para el hotel " + hotel.getNombre() + " con un total de $" + precioTotal;
        } catch (IOException e) {
            return "Error al crear la reserva: " + e.getMessage();
        }
    }

    // Actualizar una reserva para Día de Sol
    public static String actualizarReservaDiaDeSol(int indice, String emailCliente, String nombreDiaDeSol, LocalDate fecha, int cantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar Día de Sol por nombre
            DiaDeSol diaDeSol = DiaDeSolDatos.buscarPorNombre(nombreDiaDeSol);
            if (diaDeSol == null) {
                return "Día de Sol no encontrado con el nombre: " + nombreDiaDeSol;
            }

            // Calcular el precio total con reglas de negocio
            double precioBase = diaDeSol.getPrecioPorNoche() * cantidadPersonas;
            double precioTotal = calcularPrecioTotal(precioBase, fecha, fecha);

            // Crear la reserva actualizada
            Reserva reservaActualizada = Reserva.build(cliente, diaDeSol, fecha, fecha, precioTotal);
            ReservaDatos.actualizarReserva(reservaActualizada, reservaActualizada);

            // Imprimir factura
            imprimirFactura(reservaActualizada, precioBase);

            return "Reserva actualizada exitosamente para el Día de Sol: " + nombreDiaDeSol;
        } catch (IOException e) {
            return "Error al actualizar la reserva: " + e.getMessage();
        }
    }

    // Actualizar una reserva para Finca
    public static String actualizarReservaFinca(int indice, String emailCliente, String nombreFinca, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar Finca por nombre
            Finca finca = FincaDatos.buscarPorNombre(nombreFinca);
            if (finca == null) {
                return "Finca no encontrada con el nombre: " + nombreFinca;
            }

            // Calcular el precio total con reglas de negocio
            long diasEstadia = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
            double precioBase = finca.getPrecioPorNoche() * cantidadPersonas * diasEstadia;
            double precioTotal = calcularPrecioTotal(precioBase, fechaInicio, fechaFin);

            // Crear la reserva actualizada
            Reserva reservaActualizada = Reserva.build(cliente, finca, fechaInicio, fechaFin, precioTotal);
            ReservaDatos.actualizarReserva(reservaActualizada, reservaActualizada);

            // Imprimir factura
            imprimirFactura(reservaActualizada, precioBase);

            return "Reserva actualizada exitosamente para la Finca: " + nombreFinca;
        } catch (IOException e) {
            return "Error al actualizar la reserva: " + e.getMessage();
        }
    }

    // Actualizar una reserva de apartamento
    public static String actualizarReservaApartamento(int indice, String emailCliente, String nombreApartamento, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin, int nuevaCantidadPersonas) {
        try {
            // Buscar cliente por email
            Cliente cliente = ClienteDatos.buscarPorEmail(emailCliente);
            if (cliente == null) {
                return "Cliente no encontrado con el correo: " + emailCliente;
            }

            // Buscar Finca por nombre
            Apartamento apartamento = ApartamentoDatos.buscarPorNombre(nombreApartamento);
            if (apartamento == null) {
                return "Apartamento no encontrada con el nombre: " + nombreApartamento;
            }

            // Calcular el precio total con reglas de negocio
            long diasEstadia = nuevaFechaFin.toEpochDay() - nuevaFechaInicio.toEpochDay();
            double precioBase = apartamento.getPrecioPorNoche() * nuevaCantidadPersonas * diasEstadia;
            double precioTotal = calcularPrecioTotal(precioBase, nuevaFechaInicio, nuevaFechaFin);

            // Crear la reserva actualizada
            Reserva reservaActualizada = Reserva.build(cliente, apartamento, nuevaFechaInicio, nuevaFechaFin, precioTotal);
            ReservaDatos.actualizarReserva(reservaActualizada, reservaActualizada);

            // Imprimir factura
            imprimirFactura(reservaActualizada, precioBase);

            return "Reserva actualizada exitosamente para la Finca: " + nombreApartamento;

        } catch (IOException e) {
            return "Error al actualizar la reserva: " + e.getMessage();
        }
    }

    public static String actualizarReserva(String email, LocalDate fechaNacimiento, Reserva reservaExistente, boolean cambiarAlojamiento, String nuevoAlojamientoNombre, String nuevoTipoHabitacion) {
        try {
            // Paso 1: Validar la autenticación del cliente
            Cliente cliente = ClienteDatos.buscarPorEmail(email);
            if (cliente == null || !cliente.getFechaNacimiento().equals(fechaNacimiento)) {
                return "Error: Autenticación fallida. Verifica tu correo y fecha de nacimiento.";
            }

            if (cambiarAlojamiento) {
                // Paso 2: Eliminar la reserva existente
                ReservaDatos.eliminarReserva(reservaExistente);

                // Retornar mensaje indicando que se debe crear una nueva reserva
                return "La reserva existente ha sido eliminada. Por favor, cree una nueva reserva en el alojamiento deseado.";
            } else {
                // Paso 3: Filtrar habitaciones disponibles en el mismo alojamiento
                List<Habitacion> habitacionesDisponibles = HabitacionControlador.filtrarHabitaciones(reservaExistente.getAlojamiento().getNombre(), 1); // Ajustar capacidad según sea necesario
                Habitacion nuevaHabitacion = habitacionesDisponibles.stream()
                        .filter(h -> h.getTipo().equalsIgnoreCase(nuevoTipoHabitacion))
                        .findFirst()
                        .orElse(null);

                if (nuevaHabitacion == null) {
                    return "Error: No se encontró una habitación del tipo especificado en el mismo alojamiento.";
                }

                // Paso 4: Calcular el nuevo precio total
                double nuevoPrecioTotal = calcularPrecioTotal(
                        nuevaHabitacion.getPrecio(),
                        reservaExistente.getFechaInicio(),
                        reservaExistente.getFechaFin()
                );

                // Paso 5: Crear una nueva instancia de reserva actualizada
                Reserva nuevaReserva = Reserva.build(
                        cliente,
                        reservaExistente.getAlojamiento(),
                        reservaExistente.getFechaInicio(),
                        reservaExistente.getFechaFin(),
                        nuevoPrecioTotal
                );

                // Actualizar la reserva existente
                ReservaDatos.actualizarReserva(reservaExistente, nuevaReserva);

                return "Reserva actualizada exitosamente. Se cambió la habitación en el mismo alojamiento.";
            }
        } catch (IOException e) {
            return "Error al actualizar la reserva: " + e.getMessage();
        }
    }




    // Eliminar una reserva
    public static String eliminarReserva(Reserva reserva) {
        try {
            ReservaDatos.eliminarReserva(reserva);
            return "Reserva eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la reserva: " + e.getMessage();
        }
    }

    // Calcular el precio total de la reserva con reglas de negocio
    private static double calcularPrecioTotal(double precioBase, LocalDate fechaInicio, LocalDate fechaFin) {
        int diaInicio = fechaInicio.getDayOfMonth();
        int diaFin = fechaFin.getDayOfMonth();
        LocalDate finDeMes = fechaInicio.with(TemporalAdjusters.lastDayOfMonth());

        // Aplicar ajustes con base en las fechas
        if ((diaInicio >= 5 && diaFin <= 10)) {
            // Si algún día cae en el rango de descuento del 8%
            return precioBase - (precioBase * 0.08);
        } else if ((diaInicio >= 10 && diaFin <= 15)) {
            // Si algún día cae en el rango de aumento del 10%
            return precioBase + (precioBase * 0.10);
        } else if ((diaInicio >= finDeMes.getDayOfMonth() - 4) || (diaFin >= finDeMes.getDayOfMonth() - 4)) {
            // Si algún día cae en los últimos 5 días del mes
            return precioBase + (precioBase * 0.15);
        }

        // Sin ajustes
        return precioBase;
    }

    // Imprimir factura de la reserva
    private static void imprimirFactura(Reserva reserva, double precioBase) {
        System.out.println("\n=== Factura de Reserva ===");
        System.out.println("Cliente: " + reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido());
        System.out.println("Alojamiento: " + reserva.getAlojamiento().getNombre());
        System.out.println("Fecha Inicio: " + reserva.getFechaInicio());
        System.out.println("Fecha Fin: " + reserva.getFechaFin());
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Precio Total (con ajustes): $" + reserva.getPrecioTotal());
        System.out.println("========================\n");
    }

    public static boolean autenticarCliente(String email, LocalDate fechaNacimiento) {
        try {
            Cliente cliente = ClienteDatos.buscarPorEmail(email);
            if (cliente != null && cliente.getFechaNacimiento().equals(fechaNacimiento)) {
                return true;
            }
            return false;
        } catch (IOException e) {
            System.out.println("Error al autenticar cliente: " + e.getMessage());
            return false;
        }
    }

    public static List<Reserva> obtenerReservasPorCliente(String email, LocalDate fechaNacimiento) {
        try {
            // Obtener todas las reservas para el correo proporcionado
            List<Reserva> reservasCliente = ReservaDatos.consultarPorCliente(email);

            // Filtrar reservas según la fecha de nacimiento
            return reservasCliente.stream()
                    .filter(reserva -> reserva.getCliente().getFechaNacimiento().equals(fechaNacimiento))
                    .toList();
        } catch (IOException e) {
            System.out.println("Error al consultar las reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public static List<Habitacion> obtenerHabitacionesDisponibles(String nombreHotel, int capacidadRequerida) {
        try {
            List<Habitacion> todasHabitaciones = HabitacionDatos.buscarPorHotel(nombreHotel);
            return todasHabitaciones.stream()
                    .filter(habitacion -> habitacion.getDisponible() && capacidadHabitacion(habitacion.getTipo()) >= capacidadRequerida)
                    .toList();
        } catch (IOException e) {
            System.out.println("Error al obtener habitaciones disponibles: " + e.getMessage());
            return List.of();
        }
    }

    // Método auxiliar para determinar la capacidad según el tipo de habitación
    private static int capacidadHabitacion(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "simple" -> 1;
            case "pareja" -> 2;
            case "familiar-1" -> 3; // 2 adultos + 1 niño
            case "familiar-2" -> 4; // 2 adultos + 2 niños
            default -> 0;
        };
    }

}
