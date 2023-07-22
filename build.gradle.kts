plugins {
    val kotlinVersion = "1.8.21"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `maven-publish`
}

group = "dev.covercash"
version = "0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.akuleshov7:ktoml-core:0.5.0")
                implementation("com.akuleshov7:ktoml-file:0.5.0")
                implementation("com.squareup.okio:okio:3.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.squareup.okio:okio-fakefilesystem:3.4.0")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("com.squareup.okio:okio-fakefilesystem:3.4.0")
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}
