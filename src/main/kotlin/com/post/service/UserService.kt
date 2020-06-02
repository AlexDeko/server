package com.post.service

import io.ktor.features.NotFoundException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.security.crypto.password.PasswordEncoder
import com.post.dto.AuthenticationRequestDto
import com.post.dto.AuthenticationResponseDto
import com.post.dto.PasswordChangeRequestDto
import com.post.dto.user.UserResponseDto
import com.post.exception.InvalidPasswordException
import com.post.exception.PasswordChangeException
import com.post.exception.UserExistsException
import com.post.model.UserModel
import com.post.model.toDto
import com.post.repository.UserRepository

class UserService(
    private val repo: UserRepository,
    private val tokenService: JWTTokenService,
    private val passwordEncoder: PasswordEncoder
) {
    private val mutex = Mutex()

    suspend fun getModelById(id: Long): UserModel? {
        return repo.getById(id)
    }

    suspend fun getByUserName(username: String): UserModel? {
        return repo.getByUsername(username)
    }

    suspend fun getById(id: Long): UserResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return model.toDto()
    }

    suspend fun changePassword(id: Long, input: PasswordChangeRequestDto): UserModel =
        mutex.withLock {
            val model = repo.getById(id) ?: throw NotFoundException()
            if (!passwordEncoder.matches(input.old, model.password)) {
                throw PasswordChangeException("Wrong password!")
            }
            val copy = model.copy(password = passwordEncoder.encode(input.new))
            repo.save(copy)

            requireNotNull(repo.getById(id))
        }

    suspend fun authenticate(input: AuthenticationRequestDto): AuthenticationResponseDto {
        val model = repo.getByUsername(input.username) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.password, model.password)) {
            throw InvalidPasswordException("Wrong password!")
        }

        val token = tokenService.generate(model.id)
        return AuthenticationResponseDto(token)
    }

    suspend fun register(username: String, password: String): AuthenticationResponseDto =
        mutex.withLock {
            if (repo.getByUsername(username) != null) {
                throw UserExistsException("User exists!")
            }

            repo.save(
                UserModel(
                    username = username,
                    password = passwordEncoder.encode(password)
                )
            )

            val model = requireNotNull(repo.getByUsername(username))

            val token = tokenService.generate(model.id)
            AuthenticationResponseDto(token)
        }

    suspend fun getTokenFirebaseById(idPost: Long): String? {
        val user = getById(idPost)


        return null
    }
}