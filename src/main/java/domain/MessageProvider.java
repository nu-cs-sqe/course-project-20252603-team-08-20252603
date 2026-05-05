package domain;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageProvider {
    private static final String BUNDLE_NAME = "messages";

    public static String getMessage(String key, Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key; // Fallback
        }
    }
}