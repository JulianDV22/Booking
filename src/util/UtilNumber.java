package util;

public class UtilNumber {
    public static final int ZERO = 0;
    public static final double ZERO_DOUBLE = 0.0;
    public static final boolean ZERO_BOOLEAN = false;
    private static UtilNumber instance;

    private UtilNumber() {
        super();
    }

    public static UtilNumber getInstance() {
        if (instance == null) {
            instance = new UtilNumber();
        }
        return instance;
    }
}
