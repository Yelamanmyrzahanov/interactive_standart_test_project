package com.example.interactivestandarttest.domain

import com.example.interactivestandarttest.domain.model.Point
import com.example.interactivestandarttest.domain.repository.PointRepository
import com.example.interactivestandarttest.domain.usecase.GetPointsUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetPointsUseCaseTest {

    @Mock
    private lateinit var pointRepository: PointRepository

    private lateinit var getPointsUseCase: GetPointsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPointsUseCase = GetPointsUseCase(pointRepository)
    }

    @Test
    fun `execute should return list of points`() {
        val mockPoints = listOf(
            Point(1.0F, 2.0F),
            Point(2.0F, 3.0F)
        )
        `when`(pointRepository.getPoints(2)).thenReturn(Single.just(mockPoints))

        val result = getPointsUseCase.execute(2).test()

        verify(pointRepository).getPoints(2)
        result.assertValue(mockPoints)
    }

    @Test
    fun `execute should return error when repository fails`() {
        val error = RuntimeException("Repository error")
        `when`(pointRepository.getPoints(2)).thenReturn(Single.error(error))

        val result = getPointsUseCase.execute(2).test()

        verify(pointRepository).getPoints(2)
        result.assertError(error)
    }
}
