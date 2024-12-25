package modelo.entidades;

import java.util.List;

public class Habitacion {
    private String tipo;
    private List<String> caracteristicas;
    private double precio;

    // Constructor
    public Habitacion(String tipo, List<String> caracteristicas, double precio) {
        this.tipo = tipo;
        this.caracteristicas = caracteristicas;
        this.precio = precio;
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
