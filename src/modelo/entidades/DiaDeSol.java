package modelo.entidades;

import util.UtilNumero;
import util.UtilObjeto;
import util.UtilTexto;

import java.util.ArrayList;
import java.util.List;

public class DiaDeSol extends Alojamiento{
    private List<String> actividades;

    // Constructor
    private DiaDeSol(String nombre, double precioPorNoche, List<String> actividades) {
        super(nombre, precioPorNoche);
        setTipo("Día de Sol");
        setActividades(actividades);
    }

    private DiaDeSol(final int numero) {
        super(UtilTexto.EMPTY, UtilNumero.ZERO_DOUBLE);
        setTipo("Día de Sol");
        setActividades(UtilObjeto.LISTA_STRING);
    }

    //Builders
    public static DiaDeSol build(String nombre, double precioPorNoche, List<String> actividades){
        return new DiaDeSol(nombre, precioPorNoche, actividades);
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

}
