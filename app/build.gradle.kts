plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.apollo.graphql)
}

android {
    namespace = "no.brauterfallet.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "no.brauterfallet.myapplication"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

apollo {
    service("service") {
        packageName.set("no.brauterfallet")
        introspection {
            endpointUrl.set("https://api.entur.io/journey-planner/v3/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }

        mapScalar(
            "DateTime",
            "kotlinx.datetime.Instant",
            "com.apollographql.adapter.datetime.KotlinxInstantAdapter"
        )
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
    implementation(libs.bundles.ktor.client)
    implementation(libs.koin)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.bundles.apollo.graphql)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.navigation)
    implementation(libs.play.services.location)
    implementation(libs.accompanist)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.koin.test)
}