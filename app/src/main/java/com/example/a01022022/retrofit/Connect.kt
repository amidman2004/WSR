package com.example.a01022022.retrofit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class Connect {
    companion object{
        val BASE_URL = "http://10.0.2.2:3000"
        val connect = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitRequests::class.java)
    }
}

interface RetrofitRequests{

    @GET("/valute")
    suspend fun getValute():Response<ArrayList<valute>>

    @GET("/bankomats")
    suspend fun getBankomats():Response<ArrayList<bankomat>>

    @POST("/login")
    suspend fun Auth(
        @Body user:user
    ):Response<Void>
}