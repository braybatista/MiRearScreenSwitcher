import 'dart:ui';
import 'package:flutter/material.dart';

class LanguageUtils {
  static const String CHINESE = 'zh';
  static const String ENGLISH = 'en';
  static const String SPANISH = 'es';
  
  static String _currentLanguage = CHINESE; // Default language
  
  static String get currentLanguage => _currentLanguage;
  
  static void initLanguage(BuildContext context) {
    final deviceLocale = window.locale.languageCode.toLowerCase();
    
    switch (deviceLocale) {
      case ENGLISH:
        _currentLanguage = ENGLISH;
        break;
      case SPANISH:
        _currentLanguage = SPANISH;
        break;
      default:
        _currentLanguage = CHINESE; // Default to Chinese
    }
  }
  
  static bool isEnglish() => _currentLanguage == ENGLISH;
  static bool isSpanish() => _currentLanguage == SPANISH;
  static bool isChinese() => _currentLanguage == CHINESE;
  
  static void setLanguage(String languageCode) {
    if ([CHINESE, ENGLISH, SPANISH].contains(languageCode)) {
      _currentLanguage = languageCode;
    }
  }
}