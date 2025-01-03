package controlador.command.booking;

import controlador.command.Command;
import modelo.datos.BookingData;
import modelo.entidades.Booking;

import java.io.IOException;

public class DeleteBookingCommand implements Command<String> {
    private final Booking booking;

    public DeleteBookingCommand(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String execute() {
        try {
            BookingData.deleteBooking(booking);
            return "Reserva eliminada exitosamente.";
        } catch (IOException e) {
            return "Error al eliminar la reserva: " + e.getMessage();
        }
    }
}
