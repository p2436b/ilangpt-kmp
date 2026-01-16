import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

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
      linkerOpts.add("-lsqlite3")
    }
  }

  sourceSets {
    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.ktor.client.okhttp)
      implementation(libs.androidx.room.sqlite.wrapper)
    }
    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      implementation(libs.kotlinx.datetime)
      implementation(compose.materialIconsExtended)
      implementation(libs.navigation.compose)
      implementation(libs.kotlinx.serialization.core)
      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)
      implementation(libs.coil.compose)
      implementation(libs.coil.network.ktor3)
      implementation(libs.coil.compose.core)
      api(libs.koin.core)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.koin.compose.viewmodel.navigation)
      implementation(libs.koin.core.viewmodel)

      implementation(libs.kmpauth.google)
      implementation(libs.kmpauth.uihelper)

      implementation(libs.androidx.datastore)
      implementation(libs.androidx.datastore.preferences)

      api(libs.koin.annotations)

      implementation(libs.androidx.room.runtime)
      implementation(libs.androidx.sqlite.bundled)
    }
  }

//  sourceSets.named("commonMain").configure {
//    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
//  }
}

ksp {
  arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
  arg("KOIN_CONFIG_CHECK", "true")
}

//project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
//  if (name != "kspCommonMainKotlinMetadata") {
//    dependsOn("kspCommonMainKotlinMetadata")
//  }
//}

dependencies {
  add("kspCommonMainMetadata", libs.androidx.room.compiler)
  add("kspAndroid", libs.koin.ksp.compiler)

  //add("kspAndroid", libs.androidx.room.compiler)
  //add("kspIosSimulatorArm64", libs.androidx.room.compiler)
  //add("kspIosArm64", libs.androidx.room.compiler)
}


android {
  namespace = "tr.com.ilangpt"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  signingConfigs {
    create("release") {
      storeFile = file("composeApp/src/androidMain/keystore/release.keystore")
      storePassword = "postgpt"
      keyAlias = "mulkiyet"
      keyPassword = "postgpt"
    }
  }

  defaultConfig {
    applicationId = "tr.com.ilangpt"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
}

room {
  schemaDirectory("$projectDir/schemas")
}

afterEvaluate {
  tasks.matching { it.name.startsWith("ksp") && it.name.contains("KotlinAndroid") }
    .configureEach {
      dependsOn(tasks.matching { it.name == "kspCommonMainKotlinMetadata" })
    }
}

