package com.example.machinetest1.ui.theme

import WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>


    /*
   @GET("data/2.5/weather")
    suspend fun current(@Body currentRequest: CurrentRequest ): Response<CurrentSuccessResponse>*/
    @GET("forecast")
      fun forecast(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<ForcastSuccessResponse>
}