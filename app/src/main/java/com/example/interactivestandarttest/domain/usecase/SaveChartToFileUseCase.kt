package com.example.interactivestandarttest.domain.usecase

import com.example.interactivestandarttest.domain.repository.FileRepository
import io.reactivex.rxjava3.core.Single

class SaveChartToFileUseCase(private val repository: FileRepository) {
    fun execute(data: ByteArray, fileName: String): Single<Boolean> {
        return repository.saveChartToFile(data, fileName)
    }
}