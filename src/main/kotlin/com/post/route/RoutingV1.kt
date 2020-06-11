package com.post.route

import com.post.auth.BasicAuth
import com.post.auth.JwtAuth
import com.post.dto.AuthenticationRequestDto
import com.post.dto.PostRequestDto
import com.post.dto.reaction.ReactionResponseDto
import com.post.dto.token.firebase.TokenFirebaseResponse
import com.post.dto.user.UserRegisterRequestDto
import com.post.dto.user.UserResponseDto
import com.post.model.ReactionType
import com.post.model.toDto
import com.post.service.*
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.*


class RoutingV1(
    private val staticPath: String,
    private val postService: PostService,
    private val fileService: FileService,
    private val userService: UserService,
    private val firebaseService: FCMService,
    private val reactionService: ReactionService
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
                            val countPage =
                                call.parameters["count"]?.toIntOrNull() ?: throw ParameterConversionException(
                                    "id",
                                    "Int"
                                )
                            val response = postService.getPage(id, countPage)
                            call.respond(response)
                        }

                        get("/last/{count}") {
                            val countPage =
                                call.parameters["count"]?.toIntOrNull() ?: throw ParameterConversionException(
                                    "id",
                                    "Int"
                                )
                            val response = postService.getLastPage(countPage)
                            for (post in response) {
                                val reaction = reactionService
                                    .getByIdPostAndIdUser(idPost = post.id, idUser = me!!.id)
                                reaction?.let {
                                    post.isApprove = it.reactionType == ReactionType.APPROVE
                                    post.isNotApprove = it.reactionType == ReactionType.NOT_APPROVE
                                }
                            }
                            call.respond(response)
                        }

                        get("/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val reaction = reactionService
                                .getByIdPostAndIdUser(idPost = id, idUser = me!!.id)
                            val response = postService.getById(id)
                            reaction?.let {
                                call.respond(
                                    response.copy(
                                        isApprove = it.reactionType == ReactionType.APPROVE,
                                        isNotApprove = it.reactionType == ReactionType.NOT_APPROVE
                                    )
                                )
                            }
                                ?: call.respond(response)
                        }

                        post("/save") {
                            val input = call.receive<PostRequestDto>()
                            val response = postService.save(input, me!!.id)
                            val user = userService.getById(response.ownerId)
                            if (user.firebaseId!!.isNotEmpty()) firebaseService.send(
                                response.id,
                                CREATE_POST_MESSAGE,
                                user.id
                            )
                            call.respond(response)
                        }

                        post("/update") {
                            val input = call.receive<PostRequestDto>()
                            val response = postService.update(input, me!!.id)
                            call.respond(response)
                        }

                        post("/{id}/approves") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.approveById(id)
                            val user = userService.getById(response.ownerId)
                            if (!user.firebaseId.isNullOrEmpty()) firebaseService.send(
                                id,
                                LIKE_MESSAGE,
                                user.id
                            )
                            reactionService.saveOrUpdateReaction(
                                ReactionResponseDto(postId = id, userId = me!!.id, reactionType = ReactionType.APPROVE)
                            )
                            userService.update(user.copy(approve = user.approve.inc()))
                            call.respond(response)
                        }

                        post("/{id}/not_approves") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val user = me!!.toDto()
                            userService.update(user.copy(notApprove = user.notApprove.inc()))
                            reactionService.saveOrUpdateReaction(
                                ReactionResponseDto(
                                    postId = id, userId = user.id,
                                    reactionType = ReactionType.NOT_APPROVE
                                )
                            )
                            val response = postService.notApproveById(id)
                            call.respond(response)
                        }

                        post("/{id}/unselected_approves") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val post = postService.getById(id)
                            val user = me!!.toDto()
                            if (post.isApprove) userService.update(user.copy(approve = user.approve.dec()))
                            if (post.isNotApprove) userService.update(user.copy(notApprove = user.notApprove.dec()))
                            reactionService.removeById(idUser = user.id, idPost = id)
                            val response = postService.unselectedApproves(id)
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

                    route("/user") {
                        post("/update") {
                            val user = call.receive<UserResponseDto>()
                            userService.update(user)
                        }


                    }

                    route("/firebase") {
                        post("/save") {
                            val token = call.receive<TokenFirebaseResponse>()
                            firebaseService.save(token)
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