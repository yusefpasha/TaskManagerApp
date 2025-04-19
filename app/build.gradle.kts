plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.androidx.hilt)
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

        buildConfigField("String", "BASE_URL", "\"https://run.mocky.io/v3/4ec15201-b157-49bd-8bef-a34f55cc8de4/\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    implementation(libs.bundles.datastore.preferences)

    implementation(libs.work.runtime.ktx)

    requestedImplementation(libs.bundles.hilt)
    requestedKsp(libs.hilt.compiler)
    requestedKsp(libs.hilt.androidx.compiler)


    requestedImplementation(libs.bundles.retrofit2)
    preferImplementation(libs.bundles.ktor.client)

}

fun DependencyHandlerScope.preferKsp(dependencyNotation: Any) {
    add(
        "kspPrefer",
        dependencyNotation = dependencyNotation
    )
}
fun DependencyHandlerScope.preferImplementation(dependencyNotation: Any) {
    add(
        "preferImplementation",
        dependencyNotation = dependencyNotation
    )
}

fun DependencyHandlerScope.requestedKsp(dependencyNotation: Any) {
    add(
        "kspRequested",
        dependencyNotation = dependencyNotation
    )
}
fun DependencyHandlerScope.requestedImplementation(dependencyNotation: Any) {
    add(
        "requestedImplementation",
        dependencyNotation = dependencyNotation
    )
}
