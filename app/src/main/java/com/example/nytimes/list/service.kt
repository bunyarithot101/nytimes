package com.example.nytimes.list

import com.example.nytimes.HTTPLogger
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

private const val BASE_URL = " https://api.nytimes.com/svc/topstories/v2/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(HTTPLogger.getLogger())
    .build()


interface service {

    @GET("books.json")
    fun getBooks(
        @Query("api-key") key: String?
    ): Call<list>

}


object nyTimesApi {
    val retrofitService : service by lazy { retrofit.create(service::class.java) }
}