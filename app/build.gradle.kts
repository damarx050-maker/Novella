import java.io.File
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    // Apply Google Services plugin at the app module so Firebase SDKs read google-services.json
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("jacoco")
}

android {
    namespace = "com.novella.app"
    compileSdk = (rootProject.ext["versions"] as Map<*, *>)["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.novella.app"
        minSdk = (rootProject.ext["versions"] as Map<*, *>)["minSdk"] as Int
        targetSdk = (rootProject.ext["versions"] as Map<*, *>)["targetSdk"] as Int
        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = (rootProject.ext["versions"] as Map<*, *>)["composeCompiler"] as String
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlin {
        // Upgrade Kotlin compilation to use JDK 21 toolchain
        jvmToolchain(21)
    }

    // Configure Java source/target compatibility for Java 21
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    // Ensure Kotlin JVM target bytecode level matches Java 21
    // (Gradle Kotlin DSL for Android allows setting via tasks if needed)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "21"
    }
}

// Jacoco coverage report for unit tests (debug)
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "**/*Hilt*.*",
        "**/hilt_*/**"
    )

    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val javaDebugTree = fileTree("${buildDir}/intermediates/javac/debug/classes") {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(kotlinDebugTree, javaDebugTree))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    executionData.setFrom(fileTree(buildDir) {
        include(
            "**/jacoco/testDebugUnitTest.exec",
            "**/outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
            "**/*.ec"
        )
    })
}

dependencies {
    val versions = rootProject.ext["versions"] as Map<*, *>
    val room = versions["room"] as String
    val firebaseBom = versions["firebaseBom"] as String
    val coil = versions["coil"] as String
    val lifecycle = versions["lifecycle"] as String
    val billing = versions["billing"] as String
    val navigation = versions["navigation"] as String

    // Compose BOM to align Compose artifacts
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
    implementation("androidx.navigation:navigation-compose:$navigation")

    implementation("io.coil-kt:coil-compose:$coil")

    // Firebase via BoM
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBom"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    // Optional analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Room
    implementation("androidx.room:room-ktx:$room")
    ksp("androidx.room:room-compiler:$room")

    // Billing
    implementation("com.android.billingclient:billing-ktx:$billing")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}

// google-services plugin is applied above in the plugins block
