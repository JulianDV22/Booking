package util;

import modelo.entidades.Room;

import java.util.ArrayList;
import java.util.List;

public class UtilObject {
    public static final List<String> LISTA_STRING = new ArrayList<>();
    public static final List<Room> LISTA_OBJETO = new ArrayList<>();
    private static UtilObject instance;

    public static UtilObject getInstance() {
        if (instance == null) {
            instance = new UtilObject();
        }
        return instance;
    }

    private UtilObject() {
        super();
    }

    public static final UtilObject getUtilObjeto() {
        return instance;
    }

    public final <T> boolean isNUll(T object) {
        return object == null;
    }

    public final <T> T getDefault(final T object, final T defaultObject) {
        return isNUll(object) ? defaultObject : object;
    }
}
