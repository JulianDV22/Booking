package util;

import modelo.entidades.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilObject {

    // Listas constantes inicializadas con valores inmutables
    public static final List<String> LISTA_STRING = Collections.unmodifiableList(new ArrayList<>());
    public static final List<Room> LISTA_OBJETO = Collections.unmodifiableList(new ArrayList<>());

    // Instancia Singleton
    private static UtilObject instance;

    // Constructor privado para el patrón Singleton
    private UtilObject() {
        // Inicialización, si es necesario
    }

    // Método Singleton para obtener la instancia
    public static UtilObject getInstance() {
        if (instance == null) {
            instance = new UtilObject();
        }
        return instance;
    }

    // Método de compatibilidad con el nombre anterior (opcional)
    public static UtilObject getUtilObjeto() {
        return getInstance();
    }

    // Verifica si un objeto es nulo
    public <T> boolean isNull(T object) {
        return object == null;
    }

    // Devuelve un valor predeterminado si el objeto es nulo
    public <T> T getDefault(T object, T defaultObject) {
        return isNull(object) ? defaultObject : object;
    }
}
