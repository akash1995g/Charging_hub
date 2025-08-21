import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.spotless)
}

android {
    namespace = "com.akashg.androidapp.charginghub"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.akashg.androidapp.charginghub"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.akashg.androidapp.charginghub.HiltTestRunner"
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
        buildConfig = true
        resValues = true
    }

    defaultConfig {
        resValue("string", "maps_api_key", getLocalProperty("key").toString())
    }

    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "META-INF/LICENSE.md",
                    "META-INF/LICENSE-notice.md"
                )
            )
        }
    }
}

fun getLocalProperty(key: String, file: String = "config.properties"): String {
    val localFile = rootProject.file(file)
    return if (localFile.exists()) {
        val props = Properties().apply { load(localFile.reader()) }
        props.getProperty(key) ?: ""
    } else {
        System.getenv(key.uppercase()) ?: ""
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.rules)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // map
    implementation(libs.maps.compose)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt Navigation for Compose
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.gson)
    // permissions
    implementation(libs.accompanist.permissions)
    // location services
    implementation(libs.play.services.location)

    //mockk
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    // run test
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)

    // Hilt testing
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // Compose UI testing
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    // UI test
    androidTestAnnotationProcessor(libs.hilt.android.compiler)

    implementation(libs.core.ktx)

}
spotless {
    java {
        target("**/*.java")
        googleJavaFormat().aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlin {
        target("**/*.kt")
        trimTrailingWhitespace()
        ktlint()
        indentWithSpaces()
        endWithNewline()
    }
}




