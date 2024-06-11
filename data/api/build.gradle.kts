plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data.api"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.converter.gson)

    implementation(project(":domain:details"))
    implementation(project(":domain:random"))
    implementation(project(":domain:search"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}