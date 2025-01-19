package com.example.interactivestandarttest.domain.usecase

import com.example.interactivestandarttest.domain.model.Point
import com.example.interactivestandarttest.domain.repository.PointRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val pointRepository: PointRepository
) {
    fun execute(count: Int): Single<List<Point>> {
        return pointRepository.getPoints(count)
    }
}