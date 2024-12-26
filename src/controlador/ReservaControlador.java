package controlador;

import modelo.entidades.*;
import modelo.datos.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReservaControlador {

    // Método para realizar una reserva
    public static String realizarReserva(
            String nombre,
            String apellido,
            String email,
            String nacionalidad,
            String telefono,
            LocalDate fechaNacimiento,
            LocalDate inicio,
            LocalDate fin,
            String nombreHotel,
            String tipoHabitacion,
            int cantidadHabitaciones) throws IOException {

        // Crear cliente
        Cliente cliente = Cliente.build(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);

        // Buscar el hotel
        List<Hotel> hoteles = HotelDatos.leerHoteles();
        Hotel hotelSeleccionado = hoteles.stream()
                .filter(hotel -> hotel.getNombre().equalsIgnoreCase(nombreHotel))
                .findFirst()
                .orElse(null);

        if (hotelSeleccionado == null) {
            return "No se encontró el hotel especificado.";
        }

        // Validar disponibilidad de habitaciones
        Habitacion habitacionDisponible = hotelSeleccionado.getHabitaciones().stream()
                .filter(habitacion -> habitacion.getTipo().equalsIgnoreCase(tipoHabitacion))
                .findFirst()
                .orElse(null);

        if (habitacionDisponible == null) {
            return "No hay habitaciones disponibles del tipo especificado.";
        }

        // Calcular precio total y ajuste por fechas
        double precioBase = habitacionDisponible.getPrecio() * cantidadHabitaciones * calcularDiasEstadia(inicio, fin);
        double ajuste = calcularAjuste(inicio, fin, precioBase);
        double precioTotal = precioBase + ajuste;

        // Crear la reserva
        Reserva reserva = Reserva.build(cliente, hotelSeleccionado, habitacionDisponible, cantidadHabitaciones, inicio, fin, precioTotal, ajuste);

        // Guardar la reserva
        ReservaDatos.crearReserva(reserva);

        // Actualizar habitaciones disponibles
        hotelSeleccionado.getHabitaciones().remove(habitacionDisponible);
        HotelDatos.actualizarHotel(hoteles.indexOf(hotelSeleccionado), hotelSeleccionado);

        return "Se ha realizado la reserva con éxito.";
    }

    // Método para actualizar una reserva
    public static String actualizarReserva(String email, LocalDate fechaNacimiento, int indiceReserva) throws IOException {
        List<Reserva> reservas = ReservaDatos.leerReservas();
        Reserva reservaSeleccionada = reservas.stream()
                .filter(reserva -> reserva.getCliente().getEmail().equalsIgnoreCase(email)
                        && reserva.getCliente().getFechaNacimiento().equals(fechaNacimiento))
                .findFirst()
                .orElse(null);

        if (reservaSeleccionada == null) {
            return "No se encontró una reserva asociada con este correo y fecha de nacimiento.";
        }

        // Actualizar la reserva
        reservas.set(indiceReserva, reservaSeleccionada);
        ReservaDatos.actualizarReserva(indiceReserva, reservaSeleccionada);

        return "Se ha actualizado la reserva con éxito.";
    }

    // Método para cambiar una habitación
    public static String cambiarHabitacion(Reserva reserva, String tipoHabitacionNueva) throws IOException {
        Hotel hotel = (Hotel) reserva.getAlojamiento();
        Habitacion nuevaHabitacion = hotel.getHabitaciones().stream()
                .filter(habitacion -> habitacion.getTipo().equalsIgnoreCase(tipoHabitacionNueva))
                .findFirst()
                .orElse(null);

        if (nuevaHabitacion == null) {
            return "No hay habitaciones disponibles del tipo especificado.";
        }

        // Cambiar la habitación
        reserva.setHabitacion(nuevaHabitacion);
        ReservaDatos.actualizarReserva(ReservaDatos.leerReservas().indexOf(reserva), reserva);

        return "Se ha cambiado la habitación con éxito.";
    }

    // Método para cambiar de alojamiento
    public static String cambiarAlojamiento(int indiceReserva) throws IOException {
        ReservaDatos.eliminarReserva(indiceReserva);
        return "La reserva ha sido eliminada. Proceda a crear una nueva reserva.";
    }

    // Métodos auxiliares
    private static long calcularDiasEstadia(LocalDate inicio, LocalDate fin) {
        return java.time.temporal.ChronoUnit.DAYS.between(inicio, fin);
    }

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
}
