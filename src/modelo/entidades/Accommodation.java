package modelo.entidades;

import util.UtilNumber;
import util.UtilText;

import java.util.List;

public abstract class Accommodation {
    private String name;
    private Double pricePerNight;
    private String type;
    private String city;
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    Accommodation(String name, double pricePerNight, String city) {
        setName(name);
        setPricePerNight(pricePerNight);
        setCity(city);
    }

    // Constructor
    private Accommodation() {
        setName(utilText.EMPTY);
        setPricePerNight(utilNumber.ZERO_DOUBLE);
        setCity(utilText.EMPTY);
    }

    public static Accommodation build(String name, double pricePerNight, String city){
        return new Accommodation(name, pricePerNight, city) {
            @Override
            public List<String> toRow() {
                return List.of();
            }
        };
    }

    public static Accommodation build(){
        return new Accommodation() {
            @Override
            public List<String> toRow() {
                return List.of();
            }
        };
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public Accommodation setName(String name) {
        this.name = utilText.applyTrim(name);
        return this;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public Accommodation setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
        return this;
    }

    public String getType() {
        return type;
    }

    public Accommodation setType(String type) {
        this.type = utilText.applyTrim(type);
        return this;
    }

    public String getCity() {
        return city;
    }

    public Accommodation setCity(String city) {
        this.city = utilText.applyTrim(city);
        return this;
    }

    public abstract List<String> toRow();

    @Override
    public String toString() {
        return "Alojamiento{" +
                "nombre='" + name + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

}
