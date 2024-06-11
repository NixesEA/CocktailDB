plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.domain.search"
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
    api(project(":domain:model"))
    implementation(project(":domain:sharedCache"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}