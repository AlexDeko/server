package com.post.auth

import com.post.service.JWTTokenService
import com.post.service.UserService
import io.ktor.auth.jwt.JWTAuthenticationProvider

class JwtAuth(
    private val jwtService: JWTTokenService,
    private val userService: UserService
) {

    companion object {

        const val NAME = "basic"
    }

    fun setup(configuration: JWTAuthenticationProvider.Configuration) {
        with(configuration) {
            verifier(jwtService.verifier)

            validate {
                val id = it.payload.getClaim("id").asLong()
                userService.getModelById(id)
            }
        }
    }
}