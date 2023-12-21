plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.yotfr.sevenwindstestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.yotfr.sevenwindstestapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val roomVersion = "2.6.1"
    val viewModelKTXVersion = "2.6.2"
    val coroutinesVersion = "1.7.3"
    val navVersion = "2.7.6"
    val daggerVersion = "2.48"
    val coreKTXVersion = "1.12.0"
    val lifecycleRuntimeKTXVersion = "2.6.2"
    val activityComposeVersion = "1.8.2"
    val composeBomVersion = "2023.03.00"
    val jUnitVersion = "4.13.2"
    val androidJUnitVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val hiltNavVersion = "1.1.0"
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.12.0"
    val dataStoreVersion = "1.0.0"
    val fusedLocationProviderVersion = "21.0.1"
    val yandexMapKitVersion = "4.4.0-lite"
    val coilVersion = "2.5.0"

    // Coil
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // Yandex map kit
    implementation("com.yandex.android:maps.mobile:$yandexMapKitVersion")

    // Fused location provider
    implementation("com.google.android.gms:play-services-location:$fusedLocationProviderVersion")

    // KTX
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelKTXVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.core:core-ktx:$coreKTXVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKTXVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavVersion")

    // Activity
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    // Compose
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Test
    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidJUnitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}