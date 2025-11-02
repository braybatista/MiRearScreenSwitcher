import 'package:flutter/material.dart';

// Primary gradient palette (used as the app background gradient and in many UI accents)
// Defined via `colorFromHex` to allow '#fff' / '#ffffff' style strings.
final Color kCoral = colorFromHex('#FF9D88'); // 珊瑚橙 - primary seed color
final Color kPink = colorFromHex('#FFB5C5');  // 粉红
final Color kPurple = colorFromHex('#E0B5DC'); // 紫色
final Color kBlue = colorFromHex('#A8C5E5');   // 蓝色

// Common gradient lists (non-const because above colors are runtime-initialized)
final List<Color> kAppGradient = [kCoral, kPink, kPurple, kBlue];

// Grayscale palette used when the system/app is in dark mode.
final Color kGray900 = colorFromHex('#0D0D0D');  //#0D0D0D
final Color kGray800 = colorFromHex('#1A1A1A');  //#2c2c2cff
final Color kGray700 = colorFromHex('#2B2B2B');  //#757575ff
final Color kGray600 = colorFromHex('#383838');  //#b6b6b6ff

final List<Color> kAppGradientDark = [kGray900, kGray800, kGray700, kGray600];


// Neutral & utility colors
const Color kTransparent = Colors.transparent;
const Color kWhite = Colors.white;
final Color kWhite70 = colorFromHex('#B3FFFFFF'); // Colors.white70 equivalent (approx)

// Overlay / glass layers (backdrop containers use white with lower opacity)
// In `main.dart` there are many places using `Colors.white.withOpacity(0.5)` etc.
final Color kWhite50 = colorFromHex('#80FFFFFF'); // 50% white
final Color kWhite25 = colorFromHex('#40FFFFFF'); // 25% white
final Color kWhite30 = colorFromHex('#4DFFFFFF'); // 30% white (used for splash/overlay)
final Color kWhite20 = colorFromHex('#33FFFFFF'); // 20% white (highlights)

// Ink splash/highlight presets (used by InkWell/Buttons)
final Color kInkSplash = colorFromHex('#4DFFFFFF'); // ~30% white
final Color kInkHighlight = colorFromHex('#33FFFFFF'); // ~20% white

// Shadow / scrim colors (not present as explicit constants in main.dart, but useful)
// Example from Java side used: #40000000 -> 25% black shadow
final Color kBlack25 = colorFromHex('#40000000');

/// Returns the resolved coral color for the current context.
Color coralFor(BuildContext context) => isSystemDarkMode(context) ? kGray900 : kCoral;

/// Returns the resolved pink color for the current context.
Color pinkFor(BuildContext context) => isSystemDarkMode(context) ? kGray800 : kPink;

/// Returns the resolved purple color for the current context.
Color purpleFor(BuildContext context) => isSystemDarkMode(context) ? kGray700 : kPurple;

/// Returns the resolved blue color for the current context.
Color blueFor(BuildContext context) => isSystemDarkMode(context) ? kGray600 : kBlue;

/// Returns the app gradient adjusted to dark/light mode for the given context.
List<Color> appGradientFor(BuildContext context) => isSystemDarkMode(context) ? kAppGradientDark : kAppGradient;

/// Convenience: Gradient that adapts to the current system brightness.
Gradient mainGradientFor(BuildContext context) => LinearGradient(
      begin: Alignment.topLeft,
      end: Alignment.bottomRight,
      colors: appGradientFor(context),
    );

// --------------------------------------------------
// UTILITIES
// --------------------------------------------------
bool isSystemDarkModeNoArgs() {
  final brightness = WidgetsBinding.instance.platformDispatcher.platformBrightness;
  return brightness == Brightness.dark;
}

bool isSystemDarkMode(BuildContext context) {
  // Use MediaQuery helper to get platform brightness
  final Brightness brightness = MediaQuery.platformBrightnessOf(context);
  return brightness == Brightness.dark;
}

// Convenience Gradient objects you can import directly
final Gradient kMainGradient = LinearGradient(
  begin: Alignment.topLeft,
  end: Alignment.bottomRight,
  colors: isSystemDarkModeNoArgs() ? kAppGradientDark : kAppGradient,
);

final Gradient kSelectedButtonGradient = LinearGradient(
  begin: Alignment.topLeft,
  end: Alignment.bottomRight,
  colors: isSystemDarkModeNoArgs() ? kAppGradientDark : kAppGradient,
);

// --------------------------------------------------
// Hex string helpers
// --------------------------------------------------
Color colorFromHex(String hex) {
  final String cleaned = hex.replaceAll('#', '').trim();
  String normalized;

  if (cleaned.length == 3) {
    // Expand shorthand (e.g. "abc" -> "aabbcc")
    normalized = cleaned.split('').map((c) => c + c).join();
  } else if (cleaned.length == 6) {
    normalized = cleaned;
  } else if (cleaned.length == 8) {
    // Already has alpha
    normalized = cleaned;
  } else {
    throw ArgumentError('Invalid hex color format: "$hex". Use #RGB, #RRGGBB or #AARRGGBB.');
  }

  // If only RRGGBB was provided, prepend alpha FF
  if (normalized.length == 6) normalized = 'FF' + normalized;

  // Parse as ARGB hex int
  final int val = int.parse(normalized, radix: 16);
  return Color(val);
}

/// Extension for ergonomic conversion: `'#fff'.toColor()`
extension HexColorExtension on String {
  Color toColor() => colorFromHex(this);
}
