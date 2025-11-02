
package com.tgwgroup.MiRearScreenSwitcher.misc;

import java.util.Locale;

public class Utils {

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
