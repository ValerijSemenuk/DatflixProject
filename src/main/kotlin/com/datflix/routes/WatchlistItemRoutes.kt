package com.datflix.routes

import com.datflix.models.WatchlistItem
import com.datflix.services.WatchlistItemService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.watchlistRoutes(watchlistService: WatchlistItemService) {
    route("/watchlist") {
        get {
            call.respond(watchlistService.getAllWatchlistItems())
        }
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val item = watchlistService.getWatchlistItemById(id)
            if (item == null) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                call.respond(item)
            }
        }
        post {
            val item = call.receive<WatchlistItem>()
            try {
                val created = watchlistService.createWatchlistItem(item)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid data")
            }
        }
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@put
            }
            val item = call.receive<WatchlistItem>()
            if (item.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = watchlistService.updateWatchlistItem(item)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Item updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = watchlistService.deleteWatchlistItem(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Item deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            }
        }
    }
}
