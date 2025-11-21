import java.util.Properties
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.android.build.api.variant.FilterConfiguration.FilterType

plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.tgwgroup.MiRearScreenSwitcher"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = "27.0.12077973"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.tgwgroup.MiRearScreenSwitcher"
        minSdk = 24  // Shizuku需要最低24
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }
    
    // Shizuku需要
    buildFeatures {
        aidl = true
    }
    
    lint {
        disable.add("Instantiatable")
    }

    signingConfigs {
        // 仅当 key.properties 存在时才创建 release 签名配置，避免空值强制转换报错
        if (keystorePropertiesFile.exists()) {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = keystoreProperties["storeFile"]?.let { file(it) }
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        release {
            signingConfig = if (keystorePropertiesFile.exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

// Personalizar nombre del APK - debe estar FUERA del bloque android{}
android.applicationVariants.all {
    outputs.all {
        val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        val versionName = android.defaultConfig.versionName
        val buildType = buildType.name
        val abi = output.filters.find { it.filterType == FilterType.ABI.name }?.identifier ?: "universal"
        
        // Obtener fecha y hora actual en formato MM-dd-yyyy_HH-mm-ss
        val dateFormat = SimpleDateFormat("MM-dd-yyyy_HH-mm-ss", Locale.getDefault())
        val buildTime = dateFormat.format(Date())
        
        output.outputFileName = "MRSS-v${versionName}-${buildType}-${abi}-${buildTime}.apk"
    }
}

flutter {
    source = "../.."
}

dependencies {
    // Shizuku API
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")
    // Dependencia principal para funciones multimedia y MediaSessionCompat
    implementation("androidx.media:media:1.7.1")
    // Usa la versión más reciente si está disponible
    // Dependencia para NotificationCompat y otras utilidades de soporte
    implementation("androidx.core:core:1.17.0")
    // Usa la versión más reciente si está disponible
    // Dependencia para MediaStyle en las notificaciones
    implementation("androidx.media:media:1.7.1")
    // Nota: La línea de arriba ya cubre MediaStyle también.
}
