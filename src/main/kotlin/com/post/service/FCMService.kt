package com.post.service


import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.post.dto.token.firebase.TokenFirebaseResponse
import com.post.dto.token.firebase.toModel
import com.post.repository.TokenFirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Hex
import org.springframework.security.crypto.encrypt.Encryptors
import java.io.ByteArrayInputStream
import java.nio.file.Files
import java.nio.file.Paths

const val LIKE_MESSAGE = "New Like"
const val CREATE_POST_MESSAGE = "New Post"

class FCMService(
    private val dbUrl: String, private val password: String, private val salt: String, private val path: String,
    private val repo: TokenFirebaseRepository
) {
    private var targetIdToken: Long = 0

    init {
        val decryptor = Encryptors.stronger(password, Hex.encodeHexString(salt.toByteArray(Charsets.UTF_8)))
        val decrypted = decryptor.decrypt(Files.readAllBytes(Paths.get(path)))

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(decrypted)))
            .setDatabaseUrl(dbUrl)
            .build()

        FirebaseApp.initializeApp(options)
    }

    suspend fun send(recipientId: Long, title: String, userId: Long) = withContext(Dispatchers.IO) {
        try {
            val tokens = repo.getAllByIdUser(userId)
            for (e in tokens) {
                targetIdToken = e.id
                val message = Message.builder()
                    .putData("recipientId", recipientId.toString())
                    .putData("title", title)
                    .setToken(e.token)
                    .build()

                FirebaseMessaging.getInstance().send(message)
            }


        } catch (e: FirebaseMessagingException) {
            when (e.errorCode) {
                "registration-token-not-registered" -> {
                    repo.removedById(targetIdToken)
                }
                else -> Unit
            }
        }
    }

    suspend fun save(item: TokenFirebaseResponse) {
        val check = repo.getByIdUser(item.id)
        if (check != null) {
            repo.update(item.toModel())
        } else repo.save(item.toModel())
    }
}