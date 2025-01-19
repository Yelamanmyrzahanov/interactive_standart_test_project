package com.example.interactivestandarttest.domain

import com.example.interactivestandarttest.domain.repository.FileRepository
import com.example.interactivestandarttest.domain.usecase.SaveChartToFileUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SaveChartToFileUseCaseTest {

    @Mock
    private lateinit var fileRepository: FileRepository

    private lateinit var saveChartToFileUseCase: SaveChartToFileUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        saveChartToFileUseCase = SaveChartToFileUseCase(fileRepository)
    }

    @Test
    fun `execute should call FileRepository saveChart and return success`() {
        val data = byteArrayOf(1, 2, 3)
        val fileName = "test_chart.png"
        `when`(fileRepository.saveChartToFile(data, fileName)).thenReturn(Single.just(true))

        val result = saveChartToFileUseCase.execute(data, fileName).test()

        verify(fileRepository).saveChartToFile(data, fileName)
        result.assertValue(true)
    }

    @Test
    fun `execute should return error when FileRepository fails`() {
        val data = byteArrayOf(1, 2, 3)
        val fileName = "test_chart.png"
        val error = RuntimeException("Failed to save chart")
        `when`(fileRepository.saveChartToFile(data, fileName)).thenReturn(Single.error(error))

        val result = saveChartToFileUseCase.execute(data, fileName).test()

        verify(fileRepository).saveChartToFile(data, fileName)
        result.assertError(error)
    }
}