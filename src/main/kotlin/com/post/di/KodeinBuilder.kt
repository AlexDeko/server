package com.post.di

import com.post.auth.BasicAuth
import com.post.auth.JwtAuth
import com.post.db.data.DatabaseFactory
import com.post.exception.ConfigurationException
import com.post.repository.*
import com.post.route.RoutingV1
import com.post.service.*
import io.ktor.application.ApplicationEnvironment
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.net.URI

class KodeinBuilder(private val environment: ApplicationEnvironment) {

    companion object {
        private const val UPLOAD_DIR = "upload-dir"
        private const val FCM_DB_URL = "fcm-db-url"
        private const val FCM_PASSWORD = "fcm-password"
        private const val FCM_SALT = "fcm-salt"
        private const val FCM_PATH = "fcm-path"
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(builder: Kodein.MainBuilder) {
        with(builder) {
            bind<DatabaseFactory>() with eagerSingleton {
                val dbUri = URI(environment.config.property("db.jdbcUrl").getString())
                val username: String = dbUri.userInfo.split(":")[0]
                val password: String = dbUri.userInfo.split(":")[1]
                val dbUrl = ("jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}")

                DatabaseFactory(
                    dbUrl = dbUrl,
                    dbPassword = password,
                    dbUser = username
                ).apply {
                    init()
                }
            }

            constant(tag = UPLOAD_DIR) with (environment.config.propertyOrNull("server.upload.dir")?.getString()
                ?: throw ConfigurationException("Upload dir is not specified"))
            bind<PasswordEncoder>() with eagerSingleton { BCryptPasswordEncoder() }
            bind<JWTTokenService>() with eagerSingleton { JWTTokenService() }
            bind<PostRepository>() with eagerSingleton { PostRepositoryImpl() }
            bind<PostService>() with eagerSingleton { PostService(instance()) }
            bind<FileService>() with eagerSingleton { FileService(instance(tag = UPLOAD_DIR)) }
            bind<UserRepository>() with eagerSingleton { UserRepositoryImpl() }
            bind<UserService>() with eagerSingleton { UserService(instance(), instance(), instance()) }
            bind<ReactionRepository>() with eagerSingleton { ReactionRepositoryImpl() }
            bind<ReactionService>() with singleton { ReactionService(instance()) }
            constant(tag = FCM_DB_URL) with (environment.config.propertyOrNull("server.fcm.db-url")?.getString()
                ?: throw ConfigurationException("FCM DB Url is not specified"))
            constant(tag = FCM_PASSWORD) with (environment.config.propertyOrNull("server.fcm.password")?.getString()
                ?: throw ConfigurationException("FCM Password is not specified"))
            constant(tag = FCM_SALT) with (environment.config.propertyOrNull("server.fcm.salt")?.getString()
                ?: throw ConfigurationException("FCM Salt is not specified"))
            constant(tag = FCM_PATH) with (environment.config.propertyOrNull("server.fcm.path")?.getString()
                ?: throw ConfigurationException("FCM JSON Path is not specified"))
            bind<FCMService>() with eagerSingleton {
                FCMService(
                    instance(tag = FCM_DB_URL),
                    instance(tag = FCM_PASSWORD),
                    instance(tag = FCM_SALT),
                    instance(tag = FCM_PATH),
                    instance()
                )
            }
            bind<TokenFirebaseRepository>() with eagerSingleton { TokenFirebaseRepositoryImpl() }
            bind<ReactionRepository>() with eagerSingleton { ReactionRepositoryImpl() }

            bind<RoutingV1>() with eagerSingleton {
                RoutingV1(
                    instance(tag = UPLOAD_DIR),
                    instance(),
                    instance(),
                    instance(),
                    instance(),
                    instance()
                )
            }
            bind<BasicAuth>() with eagerSingleton { BasicAuth(instance(), instance()) }
            bind<JwtAuth>() with eagerSingleton { JwtAuth(instance(), instance()) }
        }
    }
}