package com.example.interactivestandarttest.data.repository

import com.example.interactivestandarttest.data.network.ApiService
import com.example.interactivestandarttest.domain.repository.PointRepository
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PointRepository {
    override fun getPoints(count: Int) = apiService.getPoints(count).map { response ->
        response.points.map { pointDto -> pointDto.toDomainModel() }
    }
}