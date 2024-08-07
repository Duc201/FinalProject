plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.urban_area_manager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.urban_area_manager"
        minSdk = 26
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // dùng để splash
    implementation("com.airbnb.android:lottie:3.4.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\Duc_Do\\Desktop\\zalo_lib",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    implementation("com.google.firebase:firebase-messaging:24.0.0")
    implementation("com.google.firebase:firebase-functions:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // tablayout
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    // RX JAVA
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")


    //Reactive network
    implementation ("com.github.pwittchen:reactivenetwork-rx2:3.0.8")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
//    implementation ("com.github.Kwasow:BottomNavigationCircles-Android:1.2")
    implementation ("com.makeramen:roundedimageview:2.3.0")



    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    // google api
    implementation("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")

    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //indicator
    implementation ("me.relex:circleindicator:2.1.6")

    //toolbar
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")

    //country
    implementation("com.hbb20:ccp:2.6.0")

    // draw 2D topology
    //noinspection GradleDependency
//    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //Zalo
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    // Camera

    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    implementation("androidx.media3:media3-exoplayer-rtsp:1.3.1")

    // table
    implementation("com.github.levitnudi:LegacyTableView:1.4")

    //chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")




}