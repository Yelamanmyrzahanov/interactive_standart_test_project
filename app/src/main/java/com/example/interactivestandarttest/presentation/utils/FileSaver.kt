package com.example.interactivestandarttest.presentation.utils

interface FileSaver {
    fun saveFile(data: ByteArray, fileName: String): Boolean
}