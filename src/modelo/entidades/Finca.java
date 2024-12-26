package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

public class Finca extends Alojamiento {
    private int area;

    // Constructor
    private Finca(String nombre, double precioPorNoche, int area) {
        super(nombre, precioPorNoche);
        setTipo("Finca");
        setArea(area);
    }

    private Finca(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE);
        setTipo("Finca");
        setArea(UtilNumero.ZERO);
    }

    //Builders
    public static Finca build(String nombre, double precioPorNoche, int area){
        return new Finca(nombre, precioPorNoche, area);
    }

    public static Finca build(){
        return new Finca(0);
    }

    @Override
    public String obtenerDescripcion() {
        return "Finca: " + getNombre() + ", Tamaño: " + area + " hectáreas";
    }

    // Getters y Setters
    public int getArea() {
        return area;
    }

    private Finca setArea(int area) {
        this.area = area;
        return this;
    }
}
