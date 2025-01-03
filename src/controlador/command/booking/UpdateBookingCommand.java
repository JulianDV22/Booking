package controlador.command.booking;

import controlador.command.Command;
import modelo.datos.BookingData;
import modelo.entidades.Booking;

import java.io.IOException;

public class UpdateBookingCommand implements Command<String> {
    private final Booking oldBooking;
    private final Booking newBooking;

    public UpdateBookingCommand(Booking oldBooking, Booking newBooking) {
        this.oldBooking = oldBooking;
        this.newBooking = newBooking;
    }

    @Override
    public String execute() {
        try {
            BookingData.updateBooking(oldBooking, newBooking);
            return "Reserva actualizada exitosamente.";
        } catch (IOException e) {
            return "Error al actualizar la reserva: " + e.getMessage();
        }
    }
}
