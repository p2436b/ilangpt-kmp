import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.kotlinSerialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.androidx.room)
}

kotlin {

  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
    }
  }

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.materialIconsExtended)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)

      implementation(libs.kotlinx.datetime)
      implementation(libs.kotlinx.serialization.core)

      implementation(libs.navigation.compose)

      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)

      implementation(libs.coil.compose)
      implementation(libs.coil.compose.core)
      implementation(libs.coil.network.ktor3)

      api(libs.koin.core)
      api(libs.koin.annotations)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.koin.compose.viewmodel.navigation)
      implementation(libs.koin.core.viewmodel)

      implementation(libs.kmpauth.google)
      implementation(libs.kmpauth.uihelper)

      implementation(libs.androidx.datastore)
      implementation(libs.androidx.datastore.preferences)

      // Room Multiplatform (2.7+)
      implementation(libs.androidx.room.runtime)
      implementation(libs.androidx.sqlite.bundled)
    }

    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.ktor.client.okhttp)
    }

    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }
  }
  sourceSets.named("commonMain").configure {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
  }
}

ksp {
  arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
  arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
  // Koin KSP (multiplatform metadata)
  add("kspCommonMainMetadata", libs.koin.ksp.compiler)

  // Room KSP (multiplatform-supported versions)
  add("kspAndroid", libs.androidx.room.compiler)
  add("kspIosArm64", libs.androidx.room.compiler)
  add("kspIosSimulatorArm64", libs.androidx.room.compiler)

  debugImplementation(compose.uiTooling)
}

android {
  namespace = "tr.com.ilangpt"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "tr.com.ilangpt"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }

  signingConfigs {
    create("release") {
      storeFile = file("composeApp/src/androidMain/keystore/release.keystore")
      storePassword = "postgpt"
      keyAlias = "mulkiyet"
      keyPassword = "postgpt"
    }
  }

  buildTypes {
    getByName("release") {
      signingConfig = signingConfigs.getByName("release")
      isMinifyEnabled = false
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

room {
  schemaDirectory("$projectDir/schemas")
}

/**
 * REQUIRED for Gradle 8 + KSP + KMP
 * Explicitly wire Android KSP tasks to common metadata KSP
 */
afterEvaluate {
  tasks.matching { it.name.startsWith("ksp") && it.name.contains("Android") }
    .configureEach {
      dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
    }
}
