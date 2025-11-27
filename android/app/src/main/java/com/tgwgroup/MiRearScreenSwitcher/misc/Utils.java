
package com.tgwgroup.MiRearScreenSwitcher.misc;

import java.util.Locale;

/**
 * Utility class for common helper methods used throughout the application.
 * This class provides various utility functions that can be used across different components.
 */
public class Utils {

    /**
     * Gets the system default language code.
     * @return The language code (e.g., "en", "es", "zh")
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getLocalizedString(String en, String es, String zh) {
        switch (getSystemLanguage()) {
            case "es":
                return es;
            case "zh":
                return zh;
            default:
                return en;
        }
    }
}
