package controlador.command.booking;

import controlador.command.Command;
import modelo.datos.BookingData;
import modelo.entidades.Booking;

import java.io.IOException;
import java.util.List;

public class FindBookingsByClientCommand implements Command<List<Booking>> {
    private final String email;

    public FindBookingsByClientCommand(String email) {
        this.email = email;
    }

    @Override
    public List<Booking> execute() {
        try {
            return BookingData.findByClient(email);
        } catch (IOException e) {
            throw new RuntimeException("Error al buscar reservas: " + e.getMessage(), e);
        }
    }
}
