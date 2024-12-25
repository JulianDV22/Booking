package modelo.entidades;

public abstract class Alojamiento {
    private String nombre;
    private double precioPorNoche;

    // Constructor
    public Alojamiento(String nombre, double precioPorNoche) {
        this.nombre = nombre;
        this.precioPorNoche = precioPorNoche;
    }

    // Métodos abstractos (Abstracción)
    public abstract String obtenerDescripcion();

    // Getters y Setters (Encapsulamiento)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
}
