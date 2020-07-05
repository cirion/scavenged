package com.seberin

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.features.logging.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(FreeMarkerContent("404.ftl", null))
        }
    }

    install(DefaultHeaders) {
        header("X-Robots-Tag", "noindex, nofollow, noarchive, nosnippet, noimageindex")
        header("X-Engine", "Ktor") // will send this header with each response
    }

    routing {
        route("/afterparty") {
            get {
                call.respond(FreeMarkerContent("index.ftl", null))
            }
            post {
                val post = call.receiveParameters()
                val errors = mutableMapOf<String, String>()
                if (post["turtle"]?.toLowerCase() != "donatello")
                    errors["turtle"] = "No way, dude!"
                if (post["country"]?.toLowerCase() != "new zealand")
                    errors["country"] = "There's someplace better!"
                if (post["box"]?.toIntOrNull() != 6)
                    errors["box"] = "Check again!"
                if (errors.isEmpty()) {
                    call.respondRedirect("/funkytown", permanent = false)
                } else {
                    call.respond(FreeMarkerContent("index.ftl", errors))
                }
            }
        }

        route("/funkytown") {
            get {
                call.respond(FreeMarkerContent("inside.ftl", null))
            }
        }

        route("/") {
            get {
                call.respond(FreeMarkerContent("afterparty.ftl", null))
            }
        }

        static {
            staticBasePackage = "/static"

            resource("favicon.ico")
            resource("robots.txt")

            route("static") {
                files("resources/static")
            }
        }

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
