package com.example.interactivestandarttest.domain.repository

import io.reactivex.rxjava3.core.Single

interface FileRepository {
    fun saveChartToFile(data: ByteArray, fileName: String): Single<Boolean>
}