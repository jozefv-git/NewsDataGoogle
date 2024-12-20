import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}
android {
    namespace = "com.jozefv.newsdata"
    compileSdk = 34

    val apiKey = gradleLocalProperties(rootDir,rootProject.providers).getProperty("API_KEY")
    val googleCloudServerId = gradleLocalProperties(rootDir,rootProject.providers).getProperty("GOOGLE_CLOUD_WEB_SERVER_CLIENT_ID")

    defaultConfig {
        // Because of gradle, we need to define api in additional quotes
        buildConfigField("String","API_KEY","\"$apiKey\"")
        buildConfigField("String","GOOGLE_CLOUD_WEB_SERVER_CLIENT_ID","\"$googleCloudServerId\"")
        applicationId = "com.jozefv.newsdata"
        minSdk = 28
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.navigation)

    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(libs.kotlin.serialization.json)

    implementation(libs.coil.compose)

    implementation(libs.bundles.ktor)

    implementation(libs.encrypted.shared.prefs)

    implementation(libs.splash.screen)

    implementation(libs.bundles.adaptive.layouts)

    implementation(libs.bundles.google.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}