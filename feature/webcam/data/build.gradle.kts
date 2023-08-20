plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.feature.webcam.data"
    compileSdk = CoreVersion.compileSdk

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(App.webcamDomainLayer))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation(project(mapOf("path" to ":app")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(Retrofit.retrofit)
    implementation(Retrofit.gsonConverter)
    implementation(Retrofit.moshiConverter)
    implementation(OkHttp.okhttp)
    implementation(OkHttp.loggingInterceptor)
    implementation(DaggerHilt.hilt)
    androidTestImplementation(AndroidTestImplementation.daggerHilt)
    kaptAndroidTest(KaptAndroidTest.daggerHilt)
    testImplementation(TestImplementation.daggerHilt)
    kaptTest(KaptTest.daggerHilt)
    androidTestImplementation(AndroidTestImplementation.daggerHiltTesting)
    kaptAndroidTest(KaptAndroidTest.hiltAndroidCompiler)
}