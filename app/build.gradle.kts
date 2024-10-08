plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    //auth

    //google services
    id("com.google.gms.google-services")
    //Ksp
    id("com.google.devtools.ksp")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.newsapp"
    compileSdk = 34
    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.5.0")
    // Navigation Components
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.5")
    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    //swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //Room db
    implementation("androidx.room:room-runtime:2.6.1")
    //Firebase Bom
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    //Firestore
    implementation("com.google.firebase:firebase-firestore")
    //Ksp
    ksp("androidx.room:room-compiler:2.6.1")
//  Navigation Fragment library
    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0")
    // Navigation UI library
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0")

    implementation("com.google.firebase:firebase-bom:33.2.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.airbnb.android:lottie:3.4.1")
}