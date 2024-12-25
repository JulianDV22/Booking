package util;

public class UtilObjeto {
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
