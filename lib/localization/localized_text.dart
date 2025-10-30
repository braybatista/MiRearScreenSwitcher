import 'package:flutter/material.dart';
import 'app_translations.dart';

class LocalizedText {
  static String? _currentLanguage; // app-level override (set by app)

  static void setLanguage(String? languageCode) {
    _currentLanguage = languageCode;
  }

  static String get(String key, [List<dynamic>? args]) {
    String languageCode = _currentLanguage ?? 'en'; // default to English if no language set

    String text = AppTranslations.get(key, languageCode);
    if (args != null) {
      text = AppTranslations.format(text, args);
    }
    return text;
  }
}

// Global navigator key for accessing context
final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();