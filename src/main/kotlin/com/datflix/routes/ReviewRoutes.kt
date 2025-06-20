package com.datflix.routes

import com.datflix.models.Review
import com.datflix.services.ReviewService
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

fun Route.reviewRoutes(reviewService: ReviewService) {
    route("/reviews") {
        get {
            call.respond(reviewService.getAllReviews())
        }
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val review = reviewService.getReviewById(id)
            if (review == null) {
                call.respond(HttpStatusCode.NotFound, "Review not found")
            } else {
                call.respond(review)
            }
        }
        post {
            val review = call.receive<Review>()
            try {
                val created = reviewService.createReview(review)
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
            val review = call.receive<Review>()
            if (review.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = reviewService.updateReview(review)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Review updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Review not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = reviewService.deleteReview(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Review deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Review not found")
            }
        }
    }
}
