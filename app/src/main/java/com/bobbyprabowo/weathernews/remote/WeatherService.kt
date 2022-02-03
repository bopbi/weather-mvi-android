package com.bobbyprabowo.weathernews.remote

import com.bobbyprabowo.weathernews.model.dto.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("location/{city_id}/2020/2/1/")
    fun listRepos(@Path("city_id") cityId: String): Single<WeatherResponse>
}
