plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:2.3.7")
    implementation("io.ktor:ktor-server-netty:2.3.7")
    implementation("io.ktor:ktor-serialization-gson:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-server-auth:2.3.7")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.7")

    // Firebase Admin SDK
    implementation("com.google.firebase:firebase-admin:9.2.0") // Використовуй одну версію

    // Серіалізація
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Логування
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // БД (Exposed, якщо плануєш використовувати)
    implementation("org.jetbrains.exposed:exposed-core:0.44.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.0")

    // Тестування (опціонально)
    testImplementation("io.ktor:ktor-server-tests:2.3.7")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

configurations.all {
    resolutionStrategy {
        // Примусова версія Guava для уникнення конфліктів
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