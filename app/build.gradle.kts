plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.prm392_proj"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.prm392_proj"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.amazonaws:aws-android-sdk-s3:2.22.5")
    implementation ("com.amazonaws:aws-android-sdk-core:2.22.5")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.room.common)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.room.runtime)
    implementation(libs.picasso)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)
    annotationProcessor(libs.room.compiler)

}