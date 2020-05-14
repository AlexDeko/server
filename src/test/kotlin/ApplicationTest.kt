import com.jayway.jsonpath.JsonPath
import com.post.module
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.http.*
import io.ktor.http.content.PartData
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.io.streams.asInput
import org.junit.Test
import org.testcontainers.containers.PostgreSQLContainer
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    class AppPostgreSQLContainer : PostgreSQLContainer<AppPostgreSQLContainer>("postgres:latest")

    private val jsonContentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)
    private val multipartBoundary = "***blob***"
    private val multipartContentType =
        ContentType.MultiPart.FormData.withParameter("boundary", multipartBoundary).toString()
    private val uploadPath = Files.createTempDirectory("test").toString()
    private val postgresContainer = AppPostgreSQLContainer().apply {
        start()
    }

    private val configure: Application.() -> Unit = {
        (environment.config as MapApplicationConfig).apply {
            put("com.post.jwt.secret", "secret")
            put("db.jdbcUrl",
                "postgres://${postgresContainer.username}:${postgresContainer.password}@${postgresContainer.containerIpAddress}:${postgresContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgresContainer.databaseName}")
            put("server.upload.dir", uploadPath)

        }
        module()
    }

//    private val configure: Application.() -> Unit = {
//        (environment.config as MapApplicationConfig).apply {
//            put("com.post.jwt.secret", "secret")
//        }
//        module()
//    }

    @Test
    fun testUnauthorized() {
        withTestApplication(configure) {
            with(handleRequest(HttpMethod.Get, "/api/v1/me")) {
                response
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
    }

    @Test
    fun testUpload() {
        withTestApplication(configure) {
            with(handleRequest(HttpMethod.Post, "/api/v1/media") {
                addHeader(HttpHeaders.ContentType, multipartContentType)
                setBody(
                    multipartBoundary,
                    listOf(
                        PartData.FileItem({
                            Files.newInputStream(Paths.get("./src/test/resources/test.png")).asInput()
                        }, {}, headersOf(
                            HttpHeaders.ContentDisposition to listOf(
                                ContentDisposition.File.withParameter(
                                    ContentDisposition.Parameters.Name,
                                    "file"
                                ).toString(),
                                ContentDisposition.File.withParameter(
                                    ContentDisposition.Parameters.FileName,
                                    "photo.png"
                                ).toString()
                            ),
                            HttpHeaders.ContentType to listOf(ContentType.Image.PNG.toString())
                        )
                        )
                    )
                )
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.contains("\"id\""))
            }
        }
    }

    @Test
    fun testRegister() {
        withTestApplication(configure) {
            runBlocking {
                var token: String?
                with(handleRequest(HttpMethod.Post, "/api/v1/registration") {
                    addHeader(HttpHeaders.ContentType, jsonContentType.toString())
                    setBody(
                        """
                        {
                            "username": "vasya",
                            "password": "password"
                        }
                    """.trimIndent()
                    )
                }) {
                    assertEquals(HttpStatusCode.OK, response.status())
                    token = JsonPath.read<String>(response.content!!, "$.token")
                }
                with(handleRequest(HttpMethod.Get, "/api/v1/me") {
                    addHeader(HttpHeaders.Authorization, "Bearer $token")
                }) {
                    assertEquals(HttpStatusCode.OK, response.status())
                    val username = JsonPath.read<String>(response.content!!, "$.username")
                    assertEquals("vasya", username)
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    fun testAuth() {
        withTestApplication(configure) {
            runBlocking {
                handleRequest(HttpMethod.Post, "/api/v1/registration") {
                    addHeader(HttpHeaders.ContentType, jsonContentType.toString())
                    setBody(
                        """
                        {
                            "username": "vasya",
                            "password": "password"
                        }
                    """.trimIndent()
                    )
                }

                var token: String?
                with(handleRequest(HttpMethod.Post, "/api/v1/authentication") {
                    addHeader(HttpHeaders.ContentType, jsonContentType.toString())
                    setBody(
                        """
                        {
                            "username": "vasya",
                            "password": "password"
                        }
                    """.trimIndent()
                    )
                }) {
                    assertEquals(HttpStatusCode.OK, response.status())
                    token = JsonPath.read<String>(response.content!!, "$.token")
                }
                with(handleRequest(HttpMethod.Get, "/api/v1/me") {
                    addHeader(HttpHeaders.Authorization, "Bearer $token")
                }) {
                    assertEquals(HttpStatusCode.OK, response.status())
                    val username = JsonPath.read<String>(response.content!!, "$.username")
                    assertEquals("vasya", username)
                }
            }
        }
    }
}