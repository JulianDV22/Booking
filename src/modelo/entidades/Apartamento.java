package modelo.entidades;

import util.UtilNumero;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class Apartamento extends Alojamiento {
    private boolean amueblado;

    // Constructor
    private Apartamento(String nombre, double precioPorNoche, boolean amueblado) {
        super(nombre, precioPorNoche);
        setTipo("Apartamento");
        setAmueblado(amueblado);
    }

    private Apartamento(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE);
        setTipo("Apartamento");
        setAmueblado(UtilNumero.ZERO_BOOLEAN);
    }

    //Builders
    public static Apartamento build(String nombre, double precioPorNoche, boolean amueblado){
        return new Apartamento(nombre, precioPorNoche, amueblado);
    }

    public static Apartamento build(){
        return new Apartamento(0);
    }

    @Override
    public String obtenerDescripcion() {
        return "Apartamento: " + getNombre() + ", Amueblado: " + (amueblado ? "Sí" : "No");
    }

    // Getters y Setters
    public boolean isAmueblado() {
        return amueblado;
    }

    public Apartamento setAmueblado(boolean amueblado) {
        this.amueblado = amueblado;
        return this;
    }

    // Método toRow: Convierte el Apartamento a una lista de cadenas
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre());
        row.add(String.valueOf(getPrecioPorNoche()));
        row.add(String.valueOf(amueblado));
        return row;
    }

    // Método fromRow: Crea un Apartamento desde una lista de cadenas
    public static Apartamento fromRow(List<String> row) {
        String nombre = row.get(0);
        double precioPorNoche = Double.parseDouble(row.get(1));
        boolean amueblado = Boolean.parseBoolean(row.get(2));
        return Apartamento.build(nombre, precioPorNoche, amueblado);
    }
}
