package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class DiaDeSol extends Alojamiento{
    private List<String> actividades;
    private boolean incluyeAlmuerzo;
    private boolean incluyeRefrigerio;

    // Constructor
    private DiaDeSol(String nombre, double precioPorEstadia, String ciudad, List<String> actividades, boolean incluyeAlmuerzo, boolean incluyeRefrigerio) {
        super(nombre, precioPorEstadia, ciudad);
        setTipo("Día de Sol");
        setActividades(actividades);
        setIncluyeAlmuerzo(incluyeAlmuerzo);
        setIncluyeRefrigerio(incluyeRefrigerio);
    }

    private DiaDeSol(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE, UtilTexto.EMPTY);
        setTipo("Día de Sol");
        setActividades(UtilObjeto.LISTA_STRING);
        setIncluyeAlmuerzo(UtilNumero.ZERO_BOOLEAN);
        setIncluyeRefrigerio(UtilNumero.ZERO_BOOLEAN);
    }

    //Builders
    public static DiaDeSol build(String nombre, double precioPorEstadia, String ciudad, List<String> actividades, boolean incluyeAlmuerzo, boolean incluyeRefrigerio){
        return new DiaDeSol(nombre, precioPorEstadia, ciudad, actividades, incluyeAlmuerzo, incluyeRefrigerio);
    }

    public static DiaDeSol build(){
        return new DiaDeSol(0);
    }

    @Override
    public String obtenerDescripcion() {
        return "Día de Sol: " + getNombre() + ", Actividades: " + String.join(", ", actividades);
    }

    // Getters y Setters
    public List<String> getActividades() {
        return actividades;
    }

    public DiaDeSol setActividades(List<String> actividades) {
        this.actividades = UtilObjeto.getUtilObjeto().getDefault(actividades, new ArrayList<>());
        return this;
    }

    public boolean isIncluyeAlmuerzo() {
        return incluyeAlmuerzo;
    }

    public DiaDeSol setIncluyeAlmuerzo(boolean incluyeAlmuerzo) {
        this.incluyeAlmuerzo = incluyeAlmuerzo;
        return this;
    }

    public boolean isIncluyeRefrigerio() {
        return incluyeRefrigerio;
    }

    public DiaDeSol setIncluyeRefrigerio(boolean incluyeRefrigerio) {
        this.incluyeRefrigerio = incluyeRefrigerio;
        return this;
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(getNombre()); // Nombre del alojamiento
        row.add(String.valueOf(getPrecioPorNoche())); // Precio por noche
        row.add(getTipo()); // Tipo (siempre "Día de Sol")
        row.add(getCiudad()); // Ciudad
        row.add(String.join(", ", actividades)); // Actividades separadas por comas
        row.add(incluyeAlmuerzo ? "Sí" : "No"); // Si incluye almuerzo
        row.add(incluyeRefrigerio ? "Sí" : "No"); // Si incluye refrigerio
        return row;
    }

    public static DiaDeSol fromRow(List<String> row) {
        String nombre = row.get(0); // Nombre del alojamiento
        double precioPorNoche = Double.parseDouble(row.get(1)); // Precio por noche
        String ciudad = row.get(3); // Ciudad
        List<String> actividades = List.of(row.get(4).split(", ")); // Actividades separadas por comas
        boolean incluyeAlmuerzo = row.get(5).equalsIgnoreCase("Sí"); // Convertir a booleano
        boolean incluyeRefrigerio = row.get(6).equalsIgnoreCase("Sí"); // Convertir a booleano

        return DiaDeSol.build(nombre, precioPorNoche, ciudad, actividades, incluyeAlmuerzo, incluyeRefrigerio);
    }


    @Override
    public String toString() {
        return "DiaDeSol{" +
                "nombre='" + getNombre() + '\'' +
                ", precioPorNoche=" + getPrecioPorNoche() +
                ", tipo='" + getTipo() + '\'' +
                ", ciudad='" + getCiudad() + '\'' +
                ", actividades=" + String.join(", ", actividades) +
                '}';
    }

}
