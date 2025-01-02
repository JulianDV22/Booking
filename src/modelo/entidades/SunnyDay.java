package modelo.entidades;

import util.UtilNumber;
import util.UtilObject;
import util.UtilText;

import java.util.ArrayList;
import java.util.List;

public class SunnyDay extends Accommodation {
    private List<String> activities;
    private Boolean includesLunch;
    private Boolean includesSnack;
    private static final UtilText utilText = UtilText.getInstance();
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilNumber utilNumber = UtilNumber.getInstance();

    // Constructor
    private SunnyDay(String name, double pricePerDay, String city, List<String> activities, boolean includesLunch, boolean includesSnack) {
        super(name, pricePerDay, city);
        setType("Día de Sol");
        setActivities(activities);
        setIncludesLunch(includesLunch);
        setIncludesSnack(includesSnack);
    }

    private SunnyDay() {
        super(utilText.EMPTY, utilNumber.ZERO_DOUBLE, utilText.EMPTY);
        setType("Día de Sol");
        setActivities(utilObject.LISTA_STRING);
        setIncludesLunch(utilNumber.ZERO_BOOLEAN);
        setIncludesSnack(utilNumber.ZERO_BOOLEAN);
    }

    //Builders
    public static SunnyDay build(String name, double pricePerDay, String city, List<String> activities, boolean includesLunch, boolean includesSnack){
        return new SunnyDay(name, pricePerDay, city, activities, includesLunch, includesSnack);
    }

    public static SunnyDay build(){
        return new SunnyDay();
    }

    // Getters y Setters
    public List<String> getActivities() {
        return activities;
    }

    public SunnyDay setActivities(List<String> activities) {
        this.activities = utilObject.getUtilObjeto().getDefault(activities, new ArrayList<>());
        return this;
    }

    public boolean isIncludesLunch() {
        return includesLunch;
    }

    public SunnyDay setIncludesLunch(boolean includesLunch) {
        this.includesLunch = includesLunch;
        return this;
    }

    public boolean isIncludesSnack() {
        return includesSnack;
    }

    public SunnyDay setIncludesSnack(boolean includesSnack) {
        this.includesSnack = includesSnack;
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getName()); // Nombre del alojamiento
        row.add(String.valueOf(getPricePerNight())); // Precio por noche
        row.add(getType()); // Tipo (siempre "Día de Sol")
        row.add(getCity()); // Ciudad
        row.add(String.join(", ", getActivities())); // Actividades separadas por comas
        row.add(isIncludesLunch() ? "Sí" : "No"); // Si incluye almuerzo
        row.add(isIncludesSnack() ? "Sí" : "No"); // Si incluye refrigerio
        return row;
    }

    public static SunnyDay fromRow(List<String> row) {
        String name = row.get(0); // Nombre del alojamiento
        double pricePerNight = Double.parseDouble(row.get(1)); // Precio por noche
        String city = row.get(3); // Ciudad
        List<String> activities = List.of(row.get(4).split(", ")); // Actividades separadas por comas
        boolean includesLunch = row.get(5).equalsIgnoreCase("Sí"); // Convertir a booleano
        boolean includesSnack = row.get(6).equalsIgnoreCase("Sí"); // Convertir a booleano

        return SunnyDay.build(name, pricePerNight, city, activities, includesLunch, includesSnack);
    }


    @Override
    public String toString() {
        return "DiaDeSol{" +
                "nombre='" + getName() + '\'' +
                ", precioPorNoche=" + getPricePerNight() +
                ", tipo='" + getType() + '\'' +
                ", ciudad='" + getCity() + '\'' +
                ", actividades=" + String.join(", ", activities) +
                '}';
    }

}
