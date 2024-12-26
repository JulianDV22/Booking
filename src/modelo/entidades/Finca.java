package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Finca extends Alojamiento {
    private double area;

    // Constructor
    private Finca(String nombre, double precioPorNoche, double area) {
        super(nombre, precioPorNoche);
        setTipo("Finca");
        setArea(area);
    }

    private Finca(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE);
        setTipo("Finca");
        setArea(UtilNumero.ZERO_DOUBLE);
    }

    //Builders
    public static Finca build(String nombre, double precioPorNoche, double area){
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
    public double getArea() {
        return area;
    }

    private Finca setArea(double area) {
        this.area = area;
        return this;
    }

    // Método toRow: Convierte la Finca a una lista de cadenas
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre());
        row.add(String.valueOf(getPrecioPorNoche()));
        row.add(String.valueOf(area));
        return row;
    }

    // Método fromRow: Crea una Finca desde una lista de cadenas
    public static Finca fromRow(List<String> row) {
        String nombre = row.get(0);
        double precioPorNoche = Double.parseDouble(row.get(1));
        double area = Double.parseDouble(row.get(2));
        return new Finca(nombre, precioPorNoche, area);
    }
}
