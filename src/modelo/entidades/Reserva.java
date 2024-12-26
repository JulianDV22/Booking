package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;

import java.time.LocalDate;

public class Reserva {
    private Cliente cliente;
    private Alojamiento alojamiento; // Polimorfismo
    private Habitacion habitacion;
    private int cantidadHabitaciones;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;
    private double ajustePorFechas;

    // Constructor
    private Reserva(Cliente cliente, Alojamiento alojamiento, Habitacion habitacion, int cantidadHabitaciones, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal, double ajustePorFechas) {
        setCliente(cliente);
        setAlojamiento(alojamiento);
        setHabitacion(habitacion);
        setCantidadHabitaciones(cantidadHabitaciones);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setPrecioTotal(precioTotal);
        setAjustePorFechas(ajustePorFechas);
    }

    private Reserva(final int numero) {
        setCliente(Cliente.build());
        setAlojamiento(Alojamiento.build());
        setCantidadHabitaciones(UtilNumero.ZERO);
        setFechaInicio(LocalDate.now());
        setFechaFin(LocalDate.now());
        setPrecioTotal(UtilNumero.ZERO_DOUBLE);
        setAjustePorFechas(UtilNumero.ZERO_DOUBLE);
    }

    //Builder
    public static Reserva build(Cliente cliente, Alojamiento alojamiento, Habitacion habitacion, int cantidadHabitaciones, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal, double ajustePorFechas){
        return new Reserva(cliente, alojamiento, habitacion, cantidadHabitaciones, fechaInicio, fechaFin, precioTotal, ajustePorFechas);
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

    private Reserva setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = UtilObjeto.getUtilObjeto().getDefault(alojamiento, Alojamiento.build());
        return this;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    private Reserva setHabitacion(Habitacion habitacion) {
        this.habitacion = UtilObjeto.getUtilObjeto().getDefault(habitacion, Habitacion.build());
        return this;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    private Reserva setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
        return this;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private Reserva setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = UtilObjeto.getUtilObjeto().getDefault(fechaInicio, LocalDate.now());
        return this;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private Reserva setFechaFin(LocalDate fechaFin) {
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

    public double getAjustePorFechas() {
        return ajustePorFechas;
    }

    private Reserva setAjustePorFechas(double ajustePorFechas) {
        this.ajustePorFechas = ajustePorFechas;
        return this;
    }
}
