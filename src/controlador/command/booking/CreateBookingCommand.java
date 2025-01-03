package controlador.command.booking;

import controlador.command.Command;
import modelo.datos.BookingData;
import modelo.entidades.Booking;
import modelo.entidades.Client;
import modelo.entidades.Accommodation;

import java.io.IOException;
import java.time.LocalDate;

public class CreateBookingCommand implements Command<String> {
    private final Client client;
    private final Accommodation accommodation;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final double totalPrice;

    public CreateBookingCommand(Client client, Accommodation accommodation, LocalDate startDate, LocalDate finishDate, double totalPrice) {
        this.client = client;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.totalPrice = totalPrice;
    }

    @Override
    public String execute() {
        try {
            Booking booking = Booking.build(client, accommodation, startDate, finishDate, totalPrice);
            BookingData.createBooking(booking);
            return "Reserva creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la reserva: " + e.getMessage();
        }
    }

}
