package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

import java.util.List;

public abstract class Alojamiento {
    private String nombre;
    private double precioPorNoche;
    private String tipo;
    private String ciudad;

    // Constructor
    Alojamiento(String nombre, double precioPorNoche, String ciudad) {
        setNombre(nombre);
        setPrecioPorNoche(precioPorNoche);
        setCiudad(ciudad);
    }

    // Constructor
    private Alojamiento(final int numero) {
        setNombre(UtilTexto.EMPTY);
        setPrecioPorNoche(UtilNumero.ZERO_DOUBLE);
        setCiudad(UtilTexto.EMPTY);
    }

    public static Alojamiento build(String nombre, double precioPorNoche, String ciudad){
        return new Alojamiento(nombre, precioPorNoche, ciudad) {
            @Override
            public String obtenerDescripcion() {
                return "";
            }

            @Override
            public List<String> toRow() {
                return List.of();
            }
        };
    }

    public static Alojamiento build(){
        return new Alojamiento(0) {
                    @Override
                    public String obtenerDescripcion() {
                        return "";
                    }

            @Override
            public List<String> toRow() {
                return List.of();
            }
        };
    }

    // Métodos abstractos
    public abstract String obtenerDescripcion();

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Alojamiento setNombre(String nombre) {
        this.nombre = UtilTexto.applyTrim(nombre);
        return this;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public Alojamiento setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public Alojamiento setTipo(String tipo) {
        this.tipo = UtilTexto.applyTrim(tipo);
        return this;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Alojamiento setCiudad(String ciudad) {
        this.ciudad = UtilTexto.applyTrim(ciudad);
        return this;
    }

    public abstract List<String> toRow();

    @Override
    public String toString() {
        return "Alojamiento{" +
                "nombre='" + nombre + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", tipo='" + tipo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }

}
