plugins {
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
}

// Centralized versions used across modules
ext.set("versions", mapOf(
    "compileSdk" to 35,
    "minSdk" to 24,
    "targetSdk" to 35,
    "composeCompiler" to "1.5.14",
    "room" to "2.6.1",
    "firebaseBom" to "33.6.0",
    "coil" to "2.6.0",
    "lifecycle" to "2.8.6",
    "billing" to "7.1.1",
    "navigation" to "2.8.3"
))
