plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.jelajahiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jelajahiapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.5")
    implementation("androidx.datastore:datastore-core:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.material:material-icons-extended:1.5.4")

    //testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //room
    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")

    //coroutine support
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1") //liveData
    implementation ("androidx.room:room-ktx:2.5.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation ("androidx.navigation:navigation-compose:2.7.5")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.6.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.25.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.25.0")
    // Splash API
    implementation ("androidx.core:core-splashscreen:1.0.0-beta01")

    // Room
    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.38.1")
    kapt ("com.google.dagger:hilt-android-compiler:2.38.1")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.33.2-alpha")

}