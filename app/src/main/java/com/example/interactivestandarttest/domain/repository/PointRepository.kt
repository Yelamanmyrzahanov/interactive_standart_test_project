package com.example.interactivestandarttest.domain.repository

import com.example.interactivestandarttest.domain.model.Point
import io.reactivex.rxjava3.core.Single

interface PointRepository {
    fun getPoints(count: Int): Single<List<Point>>
}