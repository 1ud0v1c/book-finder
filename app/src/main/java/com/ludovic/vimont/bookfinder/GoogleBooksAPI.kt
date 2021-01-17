package com.ludovic.vimont.bookfinder

import com.ludovic.vimont.bookfinder.model.GoogleBooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksAPI {
    companion object {
        val baseURL = "https://www.googleapis.com/books/v1/"
    }

    @GET("volumes")
    suspend fun get(@Query("q", encoded = true) isbn: String,
            @Query("key") apiKey: String): Response<GoogleBooksResponse>
}