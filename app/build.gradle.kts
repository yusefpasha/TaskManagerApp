plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.androidx.room)
}

kotlin {
    jvmToolchain(17)
}

android {

    val dimensionDeployment = "deployment"

    namespace = "ir.yusefpasha.taskmanagerapp"
    compileSdk = 36
    flavorDimensions += dimensionDeployment

    defaultConfig {
        applicationId = "ir.yusefpasha.taskmanagerapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    productFlavors {
        create("prefer") {
            dimension = dimensionDeployment
            versionNameSuffix = ".prefer"
        }
        create("requested") {
            isDefault = true
            dimension = dimensionDeployment
            versionNameSuffix = ".requested"
        }
    }

    room {
        generateKotlin = true
        schemaDirectory("$projectDir/schemas")
    }

}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    implementation(libs.bundles.datastore.preferences)

    implementation(libs.work.runtime.ktx)

}
