package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

public abstract class Alojamiento {
    private String nombre;
    private double precioPorNoche;

    // Constructor
    Alojamiento(String nombre, double precioPorNoche) {
        setNombre(nombre);
        setPrecioPorNoche(precioPorNoche);
    }

    // Constructor
    private Alojamiento(final int numero) {
        setNombre(UtilTexto.EMPTY);
        setPrecioPorNoche(UtilNumero.ZERO_DOUBLE);
    }

    public static Alojamiento build(String nombre, double precioPorNoche){
        return new Alojamiento(nombre, precioPorNoche) {
            @Override
            public String obtenerDescripcion() {
                return "";
            }
        };
    }

    public static Alojamiento build(){
        return new Alojamiento(0) {
                    @Override
                    public String obtenerDescripcion() {
                        return "";
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
}
