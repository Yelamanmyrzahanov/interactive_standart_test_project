package com.example.interactivestandarttest.presentation.utils

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtils {

    /**
     * Checks if the required WRITE_EXTERNAL_STORAGE permission is granted.
     * Returns true if permission is not needed or already granted.
     */
    fun isStoragePermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // No need for WRITE_EXTERNAL_STORAGE on Android 10+ (API 29+)
            true
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Requests the WRITE_EXTERNAL_STORAGE permission for devices below Android 10 (API 29).
     * @param context The current context.
     * @param requestPermissionLauncher The ActivityResultLauncher to handle the request.
     */
    fun requestStoragePermission(
        context: Context,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        onShowRationale: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // No need to request WRITE_EXTERNAL_STORAGE on Android 10+ (API 29+)
            return
        }

        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
            }

            shouldShowRequestPermissionRationale(context) -> {
                // Show rationale before requesting permission
                onShowRationale()
            }

            else -> {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    /**
     * Checks if we should show a rationale for requesting the WRITE_EXTERNAL_STORAGE permission.
     */
    private fun shouldShowRequestPermissionRationale(context: Context): Boolean {
        return if (context is androidx.fragment.app.FragmentActivity) {
            context.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            false
        }
    }
}