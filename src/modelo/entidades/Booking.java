package modelo.entidades;

import util.UtilNumber;
import util.UtilObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private Client client;
    private Accommodation accommodation; // Polimorfismo
    private LocalDate startDay;
    private LocalDate finishDay;
    private Double totalPrice;
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private Booking(Client client, Accommodation accommodation, LocalDate startDay, LocalDate finishDay, double totalPrice) {
        setClient(client);
        setAccommodation(accommodation);
        setStartDay(startDay);
        setFinishDay(finishDay);
        setTotalPrice(totalPrice);
    }

    private Booking() {
        setClient(Client.build());
        setAccommodation(Accommodation.build());
        setStartDay(LocalDate.now());
        setFinishDay(LocalDate.now());
        setTotalPrice(utilNumber.ZERO_DOUBLE);
    }

    //Builder
    public static Booking build(Client client, Accommodation accommodation, LocalDate startDate, LocalDate finishDate, double totalPrice){
        return new Booking(client, accommodation, startDate, finishDate, totalPrice);
    }

    public static Booking build(){
        return new Booking();
    }


    // Getters y Setters
    public Client getClient() {
        return client;
    }

    private Booking setClient(Client client) {
        this.client = utilObject.getUtilObjeto().getDefault(client, Client.build());
        return this;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Booking setAccommodation(Accommodation accommodation) {
        this.accommodation = utilObject.getUtilObjeto().getDefault(accommodation, Accommodation.build());
        return this;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public Booking setStartDay(LocalDate startDay) {
        this.startDay = utilObject.getUtilObjeto().getDefault(startDay, LocalDate.now());
        return this;
    }

    public LocalDate getFinishDay() {
        return finishDay;
    }

    public Booking setFinishDay(LocalDate finishDay) {
        this.finishDay = utilObject.getUtilObjeto().getDefault(finishDay, LocalDate.now());
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private Booking setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    /// Convierte un objeto Reserva a una fila de Excel (toRow)
    public List<String> toRow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> row = new ArrayList<>();
        row.add(client.getEmail()); // Nombre del cliente
        row.add(accommodation.getName()); // Nombre del alojamiento
        row.add(startDay.format(formatter));
        row.add(finishDay.format(formatter));
        row.add(String.valueOf(totalPrice));
        return row;
    }

    // Crea un objeto Reserva desde una fila de Excel (fromRow)
    public static Booking fromRow(List<String> row, Client client, Accommodation accommodation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(row.get(2), formatter);
        LocalDate finishDate = LocalDate.parse(row.get(3), formatter);
        double totalPrice = Double.parseDouble(row.get(4));
        return Booking.build(client, accommodation, startDate, finishDate, totalPrice);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "cliente=" + client.getName() + " " + client.getLastName() +
                ", alojamiento=" + accommodation.getName() +
                ", fechaInicio=" + startDay +
                ", fechaFin=" + finishDay +
                ", precioTotal=" + totalPrice +
                '}';
    }

}
