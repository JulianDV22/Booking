package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Apartamento extends Alojamiento {
    private boolean amueblado;
    private List<String> caracteristicas;

    // Constructor
    private Apartamento(String nombre, double precioPorNoche, String ciudad, boolean amueblado, List<String> caracteristicas) {
        super(nombre, precioPorNoche, ciudad);
        setTipo("Apartamento");
        setAmueblado(amueblado);
        setCaracteristicas(caracteristicas);
    }

    private Apartamento(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE, UtilTexto.EMPTY);
        setTipo("Apartamento");
        setAmueblado(UtilNumero.ZERO_BOOLEAN);
        setCaracteristicas(new ArrayList<>());
    }

    // Builders
    public static Apartamento build(String nombre, double precioPorNoche, String ciudad, boolean amueblado, List<String> caracteristicas) {
        return new Apartamento(nombre, precioPorNoche, ciudad, amueblado, caracteristicas);
    }

    public static Apartamento build() {
        return new Apartamento(0);
    }

    @Override
    public String obtenerDescripcion() {
        return "Apartamento: " + getNombre() + ", Amueblado: " + (amueblado ? "Sí" : "No") + ", Características: " + String.join(", ", caracteristicas);
    }

    // Getters y Setters
    public boolean isAmueblado() {
        return amueblado;
    }

    public Apartamento setAmueblado(boolean amueblado) {
        this.amueblado = amueblado;
        return this;
    }

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public Apartamento setCaracteristicas(List<String> caracteristicas) {
        this.caracteristicas = UtilObjeto.getUtilObjeto().getDefault(caracteristicas, new ArrayList<>());
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre()); // Nombre del apartamento
        row.add(String.valueOf(getPrecioPorNoche())); // Precio por noche
        row.add(getTipo()); // Tipo (siempre "Apartamento")
        row.add(getCiudad()); // Ciudad
        row.add(amueblado ? "Sí" : "No"); // Indica si está amueblado
        row.add(String.join(", ", caracteristicas)); // Características separadas por comas
        return row;
    }

    public static Apartamento fromRow(List<String> row) {
        String nombre = row.get(0); // Nombre del apartamento
        double precioPorNoche = Double.parseDouble(row.get(1)); // Precio por noche
        String ciudad = row.get(3); // Ciudad
        boolean amueblado = row.get(4).equalsIgnoreCase("Sí"); // Convertir a booleano
        List<String> caracteristicas = List.of(row.get(5).split(", ")); // Convertir cadena a lista

        return new Apartamento(nombre, precioPorNoche, ciudad, amueblado, caracteristicas);
    }

    @Override
    public String toString() {
        return "Apartamento{" +
                "nombre='" + getNombre() + '\'' +
                ", precioPorNoche=" + getPrecioPorNoche() +
                ", tipo='" + getTipo() + '\'' +
                ", ciudad='" + getCiudad() + '\'' +
                ", amueblado=" + (amueblado ? "Sí" : "No") +
                ", caracteristicas=" + String.join(", ", caracteristicas) +
                '}';
    }
}
