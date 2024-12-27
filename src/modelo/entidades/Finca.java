package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Finca extends Alojamiento {
    private double area;

    // Constructor
    private Finca(String nombre, double precioPorNoche, String ciudad, double area) {
        super(nombre, precioPorNoche, ciudad);
        setTipo("Finca");
        setArea(area);
    }

    private Finca(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE, UtilTexto.EMPTY);
        setTipo("Finca");
        setArea(UtilNumero.ZERO_DOUBLE);
    }

    //Builders
    public static Finca build(String nombre, double precioPorNoche, String ciudad, double area){
        return new Finca(nombre, precioPorNoche, ciudad, area);
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

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre()); // Nombre de la finca
        row.add(String.valueOf(getPrecioPorNoche())); // Precio por noche
        row.add(getTipo()); // Tipo (siempre "Finca")
        row.add(getCiudad()); // Ciudad
        row.add(String.valueOf(area)); // Tamaño de la finca en hectáreas
        return row;
    }

    public static Finca fromRow(List<String> row) {
        String nombre = row.get(0); // Nombre de la finca
        double precioPorNoche = Double.parseDouble(row.get(1)); // Precio por noche
        String ciudad = row.get(3); // Ciudad
        double area = Double.parseDouble(row.get(4)); // Tamaño de la finca en hectáreas

        return new Finca(nombre, precioPorNoche, ciudad, area);
    }


    @Override
    public String toString() {
        return "Finca{" +
                "nombre='" + getNombre() + '\'' +
                ", precioPorNoche=" + getPrecioPorNoche() +
                ", tipo='" + getTipo() + '\'' +
                ", ciudad='" + getCiudad() + '\'' +
                ", tamaño=" + area + " hectáreas" +
                '}';
    }

}
