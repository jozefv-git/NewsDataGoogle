package com.jozefv.newsdata.news.data

import com.jozefv.newsdata.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory {
    fun build(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL // Log everything of our request
            }
            install(ContentNegotiation) {
                // Library for parsing data
                json(
                    json = Json {
                        // name of the specific json field
                        // if the Json response includes field for which we don't have parse, ignore it
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                // Request expect Json input and Json output
                contentType(ContentType.Application.Json)
                url("https://newsdata.io/api/1/")
                header("X-ACCESS-KEY", BuildConfig.API_KEY)
            }
        }
    }
}