package modelo.entidades;

import util.UtilNumber;
import util.UtilObject;
import util.UtilText;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String type;
    private List<String> features;
    private Double price;
    private String hotel;
    private Boolean available;
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private Room(String type, List<String> features, double price, String hotel, boolean available) {
        setType(type);
        setFeatures(features);
        setPrice(price);
        setHotel(hotel);
        setAvailable(available);
    }

    private Room() {
        setType(utilText.EMPTY);
        setFeatures(utilObject.LISTA_STRING);
        setPrice(utilNumber.ZERO_DOUBLE);
        setHotel(utilText.EMPTY);
        setAvailable(true);
    }

    // Builder
    public static Room build(String type, List<String> features, double price, String hotel, boolean available) {
        return new Room(type, features, price, hotel, available);
    }

    public static Room build() {
        return new Room();
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public Room setType(String type) {
        this.type = utilText.applyTrim(type);
        return this;
    }

    public List<String> getFeatures() {
        return features;
    }

    public Room setFeatures(List<String> features) {
        this.features = utilObject.getUtilObjeto().getDefault(features, new ArrayList<>());
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Room setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getHotel() {
        return hotel;
    }

    public Room setHotel(String hotel) {
        this.hotel = utilText.applyTrim(hotel);
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public Room setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    // Convert to row
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(type);
        row.add(String.join(", ", features));
        row.add(String.valueOf(price));
        row.add(hotel);
        row.add(available ? "Available" : "Occupied");
        return row;
    }

    // Convert from row
    public static Room fromRow(List<String> row) {
        String type = row.get(0);
        List<String> features = List.of(row.get(1).split(", "));
        double price = Double.parseDouble(row.get(2));
        String hotel = row.get(3);
        boolean available = row.get(4).equalsIgnoreCase("Available");
        return new Room(type, features, price, hotel, available);
    }

    @Override
    public String toString() {
        return "Room{" +
                "type='" + type + '\'' +
                ", features=" + String.join(", ", features) +
                ", price=" + price +
                ", hotel='" + hotel + '\'' +
                ", available=" + available +
                '}';
    }
}