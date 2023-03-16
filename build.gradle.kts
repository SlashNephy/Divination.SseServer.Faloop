plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

object Versions {
    const val Ktor = "1.6.2"
    const val KotlinxSerializationJson = "1.2.2"
    const val SocketIo = "2.0.1"

    const val KotlinLogging = "2.0.10"
    const val Logback = "1.2.3"
}

object Libraries {
    const val KtorClientCIO = "io.ktor:ktor-client-cio:${Versions.Ktor}"
    const val KtorClientSerialization = "io.ktor:ktor-client-serialization:${Versions.Ktor}"
    const val KtorClientLogging = "io.ktor:ktor-client-logging:${Versions.Ktor}"
    const val KotlinLogging = "io.github.microutils:kotlin-logging:${Versions.KotlinLogging}"
    const val KotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinxSerializationJson}"
    const val SocketIOClient = "io.socket:socket.io-client:${Versions.SocketIo}"

    const val LogbackCore = "ch.qos.logback:logback-core:${Versions.Logback}"
    const val LogbackClassic = "ch.qos.logback:logback-classic:${Versions.Logback}"

    val ExperimentalAnnotations = setOf(
        "kotlin.ExperimentalStdlibApi",
        "kotlin.time.ExperimentalTime",
        "kotlinx.coroutines.DelicateCoroutinesApi"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Libraries.KtorClientCIO)
    implementation(Libraries.KtorClientSerialization)
    implementation(Libraries.KtorClientLogging)
    implementation(Libraries.KotlinxSerializationJson)
    implementation(Libraries.SocketIOClient)

    implementation(Libraries.KotlinLogging)
    implementation(Libraries.LogbackCore)
    implementation(Libraries.LogbackClassic)
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                apiVersion = "1.5"
                languageVersion = "1.5"
                allWarningsAsErrors = true
                verbose = true
            }
        }
    }

    sourceSets.all {
        languageSettings.progressiveMode = true

        Libraries.ExperimentalAnnotations.forEach {
            languageSettings.useExperimentalAnnotation(it)
        }
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes("Main-Class" to "dev.horoscope.divination.MainKt")
    }
}
