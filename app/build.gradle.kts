plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.apollographql.apollo").version("2.5.6")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.example.rocketreserver"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.recyclerview:recyclerview:1.2.0")
    implementation("io.coil-kt:coil:1.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.paging:paging-runtime-ktx:2.1.2")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")
    implementation("com.apollographql.apollo:apollo-runtime:2.5.6")
    implementation("com.apollographql.apollo:apollo-coroutines-support:2.5.6")
    implementation("io.insert-koin:koin-android:3.0.1")
    implementation("io.insert-koin:koin-android-ext:3.0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.insert-koin:koin-test-junit4:3.0.1")
    testImplementation("org.mockito:mockito-inline:3.4.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.1.0")
}

apollo {
    generateKotlinModels.set(true)
}
