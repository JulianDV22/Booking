package util;

import static util.UtilObject.getUtilObjeto;

public class UtilText {
    public static final String EMPTY = "";
    public static final String UNDERLINE = "_";
    public static final String EMAIL_RE = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static UtilText instance;

    private UtilText() {
        super();
    }

    public static UtilText getInstance() {
        if (instance == null) {
            instance = new UtilText();
        }
        return instance;
    }

    public static final boolean isNull(final String string) {
        return getUtilObjeto().isNull(string);
    }

    public static final boolean isNullOrEmpty(final String string) {
        return isNull(string) || EMPTY.equals(applyTrim(string));
    }

    public static final String applyTrim(final String string) {
        return getDefault(string).trim();
    }

    public static final String getDefault(final String string, final String defaultValue) {
        return getUtilObjeto().getDefault(string, defaultValue);
    }

    public static final String getDefault(final String string) {
        return getDefault(string, EMPTY);
    }

    public static final String concatenate(final String... strings) {
        final var sb = new StringBuilder(EMPTY);

        if (!UtilObject.getUtilObjeto().isNull(strings)) {
            for (final var string : strings) {
                sb.append(applyTrim(string));
            }
        }

        return sb.toString();
    }

    public final static boolean matchPattern(final String text, final String pattern) {
        return getDefault(text).matches(getDefault(pattern));
    }

    public static final boolean emailStringIsValid(final String emailValue) {
        return (UtilText.matchPattern(emailValue, EMAIL_RE));
    }

    public static boolean isPasswordValid(final String password) {
        return UtilText.matchPattern(password, PASSWORD_PATTERN);
    }
}
