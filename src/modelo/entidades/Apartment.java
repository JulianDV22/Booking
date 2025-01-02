package modelo.entidades;

import util.UtilNumber;
import util.UtilObject;
import util.UtilText;

import java.util.ArrayList;
import java.util.List;

public class Apartment extends Accommodation {
    private Boolean furnished;
    private List<String> characteristics;
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private Apartment(String name, double pricePerNight, String city, boolean furnished, List<String> characteristics) {
        super(name, pricePerNight, city);
        setType("Apartamento");
        setFurnished(furnished);
        setCharacteristics(characteristics);
    }

    private Apartment() {
        super(utilText.EMPTY, utilNumber.ZERO_DOUBLE, utilText.EMPTY);
        setType("Apartamento");
        setFurnished(utilNumber.ZERO_BOOLEAN);
        setCharacteristics(new ArrayList<>());
    }

    // Builders
    public static Apartment build(String name, double pricePerNight, String city, boolean furnished, List<String> characteristics) {
        return new Apartment(name, pricePerNight, city, furnished, characteristics);
    }

    public static Apartment build() {
        return new Apartment();
    }

    // Getters y Setters
    public boolean isFurnished() {
        return furnished;
    }

    public Apartment setFurnished(boolean furnished) {
        this.furnished = furnished;
        return this;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public Apartment setCharacteristics(List<String> characteristics) {
        this.characteristics = utilObject.getUtilObjeto().getDefault(characteristics, new ArrayList<>());
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getName()); // Nombre del apartamento
        row.add(String.valueOf(getPricePerNight())); // Precio por noche
        row.add(getType()); // Tipo (siempre "Apartamento")
        row.add(getCity()); // Ciudad
        row.add(isFurnished() ? "Sí" : "No"); // Indica si está amueblado
        row.add(String.join(", ", getCharacteristics())); // Características separadas por comas
        return row;
    }

    public static Apartment fromRow(List<String> row) {
        String name = row.get(0); // Nombre del apartamento
        double pricePerNight = Double.parseDouble(row.get(1)); // Precio por noche
        String city = row.get(3); // Ciudad
        boolean furnished = row.get(4).equalsIgnoreCase("Sí"); // Convertir a booleano
        List<String> characteristics = List.of(row.get(5).split(", ")); // Convertir cadena a lista

        return new Apartment(name, pricePerNight, city, furnished, characteristics);
    }

    @Override
    public String toString() {
        return "Apartamento{" +
                "nombre='" + getName() + '\'' +
                ", precioPorNoche=" + getPricePerNight() +
                ", tipo='" + getType() + '\'' +
                ", ciudad='" + getCity() + '\'' +
                ", amueblado=" + (furnished ? "Sí" : "No") +
                ", caracteristicas=" + String.join(", ", characteristics) +
                '}';
    }
}
