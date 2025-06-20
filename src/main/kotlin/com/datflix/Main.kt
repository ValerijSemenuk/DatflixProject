package com.datflix
import com.datflix.config.configureRouting
import com.datflix.config.configureSerialization
import com.datflix.firebase.configureAuth
import com.datflix.firebase.configureFirebase
import com.datflix.repositories.ActorRepository
import com.datflix.repositories.CastRepository
import com.datflix.repositories.GenreRepository
import com.datflix.repositories.MovieRepository
import com.datflix.repositories.ReviewRepository
import com.datflix.repositories.UserRepository
import com.datflix.repositories.WatchlistItemRepository
import com.datflix.services.ActorService
import com.datflix.services.CastService
import com.datflix.services.GenreService
import com.datflix.services.MovieService
import com.datflix.services.ReviewService
import com.datflix.services.UserService
import com.datflix.services.WatchlistItemService
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8000,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureFirebase()
    configureAuth()
    configureSerialization()

    // Ініціалізація сервісів з репозиторіями
    val actorService = ActorService(ActorRepository())
    val castService = CastService(CastRepository())
    val genreService = GenreService(GenreRepository())
    val movieService = MovieService(MovieRepository())
    val reviewService = ReviewService(ReviewRepository())
    val userService = UserService(UserRepository())
    val watchlistItemService = WatchlistItemService(WatchlistItemRepository())

    // Конфігурація маршрутів
    configureRouting(
        actorService = actorService,
        castService = castService,
        genreService = genreService,
        movieService = movieService,
        reviewService = reviewService,
        userService = userService,
        watchlistItemService = watchlistItemService
    )
}
