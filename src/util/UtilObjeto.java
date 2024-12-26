package util;

import modelo.entidades.Habitacion;

import java.util.ArrayList;
import java.util.List;

public class UtilObjeto {
    public static final List<String> LISTA_STRING = new ArrayList<>();
    public static final List<Habitacion> LISTA_OBJETO = new ArrayList<>();

    private static final UtilObjeto instance = new UtilObjeto();


    private UtilObjeto() {
        super();
    }

    public static final UtilObjeto getUtilObjeto() {
        return instance;
    }

    public final <T> boolean isNUll(T object) {
        return object == null;
    }

    public final <T> T getDefault(final T object, final T defaultObject) {
        return isNUll(object) ? defaultObject : object;
    }
}
