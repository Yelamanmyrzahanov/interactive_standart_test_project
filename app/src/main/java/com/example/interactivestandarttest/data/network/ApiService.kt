package com.example.interactivestandarttest.data.network

import android.graphics.Point
import com.example.interactivestandarttest.data.model.PointResponseDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/test/points")
    fun getPoints(@Query("count") count: Int): Single<PointResponseDTO>
}