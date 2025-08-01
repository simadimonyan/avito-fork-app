import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.baselineprofile)
    id("com.google.devtools.ksp")
}

val properties = Properties().apply { load(File(rootProject.rootDir, "gradle.properties").inputStream()) }

val mapKitKey = properties["mapKitKey"] as String

android {

    namespace = "samaryanin.avitofork"
    compileSdk = 35

    defaultConfig {
        applicationId = "samaryanin.avitofork"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        debug {
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "MAPS_KEY", "\"$mapKitKey\"")
        }

        release {
            isMinifyEnabled = true //R8 compiler
            isShrinkResources = true //Shrinking
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "MAPS_KEY", "\"$mapKitKey\"")
        }

        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "MAPS_KEY", "\"$mapKitKey\"")
        }

    }

    composeCompiler {
        enableStrongSkippingMode = true
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
        metricsDestination = layout.buildDirectory.dir("compose_compiler")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0"

}

dependencies {

    // Domain Lib
    implementation(files("libs/domain-jvm-1.3.0.jar"))

    // Yandex
    implementation (libs.maps.mobile)

    // Decompose
    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.benchmark.macro)

    // Hilt
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // Coil
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Utils: Gson / Log
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.accompanist.placeholder.material)

    // Compose
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.security.crypto)
    implementation (libs.androidx.datastore.preferences)

    // Baseline Profiler
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.camera.core)
    "baselineProfile"(project(":baselineprofile"))
    implementation(libs.androidx.hilt.navigation.compose.v120)

    // Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}