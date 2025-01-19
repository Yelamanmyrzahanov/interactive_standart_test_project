package com.example.interactivestandarttest.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

class FileSaverImpl(private val context: Context) : FileSaver {

    override fun saveFile(data: ByteArray, fileName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveToMediaStore(data, fileName)
            } else {
                saveToLegacyStorage(data, fileName)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun saveToMediaStore(data: ByteArray, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Charts")
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(data)
            } ?: throw Exception("Failed to open output stream.")
        } ?: throw Exception("Failed to create MediaStore entry.")
    }

    private fun saveToLegacyStorage(data: ByteArray, fileName: String) {
        val filePath =
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$fileName.png"
        val file = File(filePath)

        FileOutputStream(file).use { outputStream ->
            outputStream.write(data)
        }
    }
}