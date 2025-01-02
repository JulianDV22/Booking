package modelo.entidades;

import util.UtilNumber;
import util.UtilText;

import java.util.ArrayList;
import java.util.List;

public class Farm extends Accommodation {
    private Double area;
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private Farm(String name, double pricePerNight, String city
            , double area) {
        super(name, pricePerNight, city);
        setType("Finca");
        setArea(area);
    }

    private Farm() {
        super(utilText.EMPTY, utilNumber.ZERO_DOUBLE, utilText.EMPTY);
        setType("Finca");
        setArea(utilNumber.ZERO_DOUBLE);
    }

    //Builders
    public static Farm build(String name, double pricePerNight, String city, double area){
        return new Farm(name, pricePerNight, city, area);
    }

    public static Farm build(){
        return new Farm();
    }

    // Getters y Setters
    public double getArea() {
        return area;
    }

    private Farm setArea(double area) {
        this.area = area;
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getName()); // Nombre de la finca
        row.add(String.valueOf(getPricePerNight())); // Precio por noche
        row.add(getType()); // Tipo (siempre "Finca")
        row.add(getCity()); // Ciudad
        row.add(String.valueOf(area)); // Tamaño de la finca en hectáreas
        return row;
    }

    public static Farm fromRow(List<String> row) {
        String nombre = row.get(0); // Nombre de la finca
        double precioPorNoche = Double.parseDouble(row.get(1)); // Precio por noche
        String ciudad = row.get(3); // Ciudad
        double area = Double.parseDouble(row.get(4)); // Tamaño de la finca en hectáreas

        return new Farm(nombre, precioPorNoche, ciudad, area);
    }


    @Override
    public String toString() {
        return "Finca{" +
                "nombre='" + getName() + '\'' +
                ", precioPorNoche=" + getPricePerNight() +
                ", tipo='" + getType() + '\'' +
                ", ciudad='" + getCity() + '\'' +
                ", tamaño=" + area + " hectáreas" +
                '}';
    }

}
