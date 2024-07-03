package com.strayfox.testrockruit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("about/")
    suspend fun getAboutPage(): Response<String>
}

// Retrofit instance
val retrofit = Retrofit.Builder()
    .baseUrl("https://www.compass.com/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()
