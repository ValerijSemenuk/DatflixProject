plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:2.3.7")
    implementation("io.ktor:ktor-server-netty:2.3.7")
    implementation("io.ktor:ktor-serialization-gson:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.7")

    implementation("com.github.papsign:Ktor-OpenAPI-Generator:-SNAPSHOT")


    // Серіалізація
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Firebase Admin SDK з виключенням listenablefuture
    implementation("com.google.firebase:firebase-admin:9.1.1") {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    implementation("com.google.cloud:google-cloud-firestore:3.13.6") {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }

    // Логування
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // БД
    implementation("org.jetbrains.exposed:exposed-core:0.44.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.0")
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:31.1-jre")
    }
}

application {
    mainClass.set("com.datflix.MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
