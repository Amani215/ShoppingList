package com.example.shoppinglist.retrofitConversion

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoneyAPI {
    @GET("/latest")
    fun getRates(@Query("from") base: String): Call<MoneyResult>
}