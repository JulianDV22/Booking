package modelo.entidades;

public class Reserva {
    private Cliente cliente;
    private Alojamiento alojamiento; // Polimorfismo
    private Habitacion habitacion;
    private int cantidadHabitaciones;
    private String fechaInicio;
    private String fechaFin;
    private double precioTotal;
    private double ajustePorFechas;

    // Constructor
    public Reserva(Cliente cliente, Alojamiento alojamiento, Habitacion habitacion, int cantidadHabitaciones, String fechaInicio, String fechaFin, double precioTotal, double ajustePorFechas) {
        this.cliente = cliente;
        this.alojamiento = alojamiento;
        this.habitacion = habitacion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.ajustePorFechas = ajustePorFechas;
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getAjustePorFechas() {
        return ajustePorFechas;
    }

    public void setAjustePorFechas(double ajustePorFechas) {
        this.ajustePorFechas = ajustePorFechas;
    }
}
