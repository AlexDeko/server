package com.post.route

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.*
import com.post.auth.BasicAuth
import com.post.auth.JwtAuth
import com.post.dto.AuthenticationRequestDto
import com.post.dto.PostRequestDto
import com.post.dto.user.UserRegisterRequestDto
import com.post.model.toDto
import com.post.route.me
import com.post.service.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein


class RoutingV1(
    private val staticPath: String,
    private val postService: PostService,
    private val fileService: FileService,
    private val userService: UserService,
    private val firebaseService: FCMService
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            route("/api/v1/") {
                static("/static") {
                    files(staticPath)
                }

                route("/") {
                    post("/registration") {
                        val input = call.receive<UserRegisterRequestDto>()
                        val response = userService.register(input.username, input.password)
                        call.respond(response)
                    }

                    post("/authentication") {
                        val input = call.receive<AuthenticationRequestDto>()
                        val response = userService.authenticate(input)
                        call.respond(response)
                    }
                }

                authenticate(BasicAuth.NAME, JwtAuth.NAME) {
                    route("/me") {
                        get {
                            call.respond(requireNotNull(me).toDto())
                        }
                    }
                    route("/posts") {
                        get {
                            val response = postService.getAll()
                            call.respond(response)
                        }
                        get("/{id}/{count}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val countPage = call.parameters["count"]?.toIntOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Int"
                            )
                            val response = postService.getPage(id, countPage)
                            call.respond(response)
                        }

                        get("/last/{count}") {
                            val countPage = call.parameters["count"]?.toIntOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Int"
                            )
                            val response = postService.getLastPage(countPage)
                            call.respond(response)
                        }

                        get("/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.getById(id)
                            call.respond(response)
                        }

                        post("/save") {
                            val input = call.receive<PostRequestDto>()
                            val response = postService.save(input, me!!.id)
                            firebaseService.send(response.id,"", CREATE_POST_MESSAGE)
                            call.respond(response)
                        }

                        post("/update") {
                            val input = call.receive<PostRequestDto>()
                            val response = postService.update(input, me!!.id)
                            call.respond(response)
                        }

                        post("/{id}/likes") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.likedById(id)
                            firebaseService.send(id, "", LIKE_MESSAGE)
                            call.respond(response)
                        }

                        post("/{id}/dislikes") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.dislikeById(id)
                            call.respond(response)
                        }

                        post("/{id}/reposts") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.repostById(id, me!!.id)
                            call.respond(response)
                        }

                        delete("/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            postService.removeById(id, me!!.id)
                            call.respond(HttpStatusCode.NoContent)
                        }
                    }
                    route("/media") {
                        post {
                            val multipart = call.receiveMultipart()
                            val response = fileService.save(multipart)
                            call.respond(response)
                        }
                    }
                }
            }
        }
    }
}