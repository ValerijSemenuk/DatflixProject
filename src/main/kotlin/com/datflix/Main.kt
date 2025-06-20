package com.datflix

import io.github.papsign.ktor.openapigen.OpenAPIGen
import io.github.papsign.ktor.openapigen.route.normal.any

import com.datflix.config.configureRouting
import com.datflix.config.configureSerialization
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.InputStream

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureFirebase()          // Ініціалізуємо Firebase Admin SDK
    configureRouting()           // Роути
    configureSerialization()     // JSON
}

fun Application.configureFirebase() {
    val serviceAccountStream: InputStream = this::class.java.classLoader
        .getResourceAsStream("firebase/serviceAccountKey.json")
        ?: throw IllegalStateException("Firebase service account key not found!")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    FirebaseApp.initializeApp(options)
}
