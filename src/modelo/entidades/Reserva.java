package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private Cliente cliente;
    private Alojamiento alojamiento; // Polimorfismo
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    // Constructor
    private Reserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal) {
        setCliente(cliente);
        setAlojamiento(alojamiento);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setPrecioTotal(precioTotal);
    }

    private Reserva(final int numero) {
        setCliente(Cliente.build());
        setAlojamiento(Alojamiento.build());
        setFechaInicio(LocalDate.now());
        setFechaFin(LocalDate.now());
        setPrecioTotal(UtilNumero.ZERO_DOUBLE);
    }

    //Builder
    public static Reserva build(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal){
        return new Reserva(cliente, alojamiento, fechaInicio, fechaFin, precioTotal);
    }

    public static Reserva build(){
        return new Reserva(0);
    }


    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    private Reserva setCliente(Cliente cliente) {
        this.cliente = UtilObjeto.getUtilObjeto().getDefault(cliente, Cliente.build());
        return this;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public Reserva setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = UtilObjeto.getUtilObjeto().getDefault(alojamiento, Alojamiento.build());
        return this;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public Reserva setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = UtilObjeto.getUtilObjeto().getDefault(fechaInicio, LocalDate.now());
        return this;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Reserva setFechaFin(LocalDate fechaFin) {
        this.fechaFin = UtilObjeto.getUtilObjeto().getDefault(fechaFin, LocalDate.now());
        return this;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    private Reserva setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    /// Convierte un objeto Reserva a una fila de Excel (toRow)
    public List<String> toRow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> row = new ArrayList<>();
        row.add(cliente.getEmail()); // Nombre del cliente
        row.add(alojamiento.getNombre()); // Nombre del alojamiento
        row.add(fechaInicio.format(formatter));
        row.add(fechaFin.format(formatter));
        row.add(String.valueOf(precioTotal));
        return row;
    }

    // Crea un objeto Reserva desde una fila de Excel (fromRow)
    public static Reserva fromRow(List<String> row, Cliente cliente, Alojamiento alojamiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(row.get(2), formatter);
        LocalDate fechaFin = LocalDate.parse(row.get(3), formatter);
        double precioTotal = Double.parseDouble(row.get(4));
        return Reserva.build(cliente, alojamiento, fechaInicio, fechaFin, precioTotal);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
                ", alojamiento=" + alojamiento.getNombre() +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precioTotal=" + precioTotal +
                '}';
    }

}
