package controlador;

import controlador.command.Command;
import modelo.datos.*;
import modelo.entidades.*;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.logging.Logger;

public class BookingController {

    // Ejecución de comandos
    public <Booking> Booking executeCommand(Command<Booking> command) {
        return command.execute();
    }

    // Validación de cliente
    public Client validateClient(String emailClient) throws IOException {
        Client client = ClientData.findByEmail(emailClient);
        if (client == null) {
            throw new IllegalArgumentException("Cliente no encontrado con el correo: " + emailClient);
        }
        return client;
    }

    // Validación de alojamiento
    public Accommodation validateAccommodation(String accommodationName, AccommodationType accommodationType) throws IOException {
        Accommodation accommodation = switch (accommodationType) {
            case DIA_DE_SOL -> SunnyDayData.findByName(accommodationName);
            case FINCA -> FarmData.findByName(accommodationName);
            case APARTAMENTO -> ApartmentData.findPerName(accommodationName);
            case HOTEL -> HotelData.findByName(accommodationName);
        };

        if (accommodation == null) {
            throw new IllegalArgumentException(accommodationType + " no encontrado con el nombre: " + accommodationName);
        }
        return accommodation;
    }

    // Cálculo del precio total de la reserva
    public double calculateBookingPrice(Accommodation accommodation, int numberOfPeople, LocalDate startDate, LocalDate finishDate) {
        long stayingDays = finishDate.toEpochDay() - startDate.toEpochDay();
        double basePrice = accommodation.getPricePerNight() * numberOfPeople * stayingDays;
        return calculateTotalPrice(basePrice, startDate, finishDate);
    }

    // Lógica para calcular el precio con ajustes
    private double calculateTotalPrice(double basePrice, LocalDate startDate, LocalDate finishDate) {
        int startDay = startDate.getDayOfMonth();
        int finishDay = finishDate.getDayOfMonth();
        LocalDate endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth());

        if (isDiscountRange(startDay, finishDay)) {
            return applyDiscount(basePrice, 0.08);
        } else if (isIncreaseRange(startDay, finishDay)) {
            return applyIncrease(basePrice, 0.10);
        } else if (isEndOfMonthRange(startDay, finishDay, endOfMonth)) {
            return applyIncrease(basePrice, 0.15);
        }

        return basePrice;
    }

    private boolean isDiscountRange(int startDay, int finishDay) {
        return startDay >= 5 && finishDay <= 10;
    }

    private boolean isIncreaseRange(int startDay, int finishDay) {
        return startDay >= 10 && finishDay <= 15;
    }

    private boolean isEndOfMonthRange(int startDay, int finishDay, LocalDate endOfMonth) {
        int lastDay = endOfMonth.getDayOfMonth();
        return startDay >= lastDay - 4 || finishDay >= lastDay - 4;
    }

    private double applyDiscount(double basePrice, double percentage) {
        return basePrice - (basePrice * percentage);
    }

    private double applyIncrease(double basePrice, double percentage) {
        return basePrice + (basePrice * percentage);
    }

    // Generar factura para la reserva


    // Enumeración para tipos de alojamiento
    public enum AccommodationType {
        DIA_DE_SOL, FINCA, APARTAMENTO, HOTEL
    }
}
