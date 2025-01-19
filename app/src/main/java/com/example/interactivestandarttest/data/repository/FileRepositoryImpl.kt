package com.example.interactivestandarttest.data.repository

import com.example.interactivestandarttest.domain.repository.FileRepository
import com.example.interactivestandarttest.presentation.utils.FileSaver
import io.reactivex.rxjava3.core.Single

class FileRepositoryImpl(private val fileSaver: FileSaver) : FileRepository {
    override fun saveChartToFile(data: ByteArray, fileName: String): Single<Boolean> {
        return Single.create { emitter ->
            try {
                val success = fileSaver.saveFile(data, fileName)
                emitter.onSuccess(success)
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }
        }
    }
}