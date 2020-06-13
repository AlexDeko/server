package com.post.exception

import io.ktor.application.call
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

object ErrorHandler {

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(configuration: StatusPages.Configuration) {
        with(configuration) {
            respondAndThrow<NotImplementedError>(HttpStatusCode.NotImplemented)
            respondAndThrow<ParameterConversionException>(HttpStatusCode.BadRequest)
            respondAndThrow<UserExistsException>(HttpStatusCode.BadRequest)
            respondAndThrow<NotFoundException>(HttpStatusCode.NotFound)
            respondAndThrow<ForbiddenException>(HttpStatusCode.Forbidden)
            respondAndThrow<UnauthorizedException>(HttpStatusCode.Unauthorized)
        }
    }

    private inline fun <reified T : Throwable> StatusPages.Configuration.respondAndThrow(
        code: HttpStatusCode
    ) {
        exception<T> { e ->
            call.respond(code)
            throw e
        }
    }
}