package com.example.arcamera.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: Activity) {

    companion object {
        const val CAMERA_PERMISSION_CODE = 100
        const val STORAGE_PERMISSION_CODE = 101
    }

    /**
     * Check if camera permission is granted
     */
    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check if storage permission is granted
     */
    fun hasStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Request camera permission
     */
    fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    /**
     * Request storage permission
     */
    fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    /**
     * Check if should show permission rationale
     */
    fun shouldShowCameraPermissionRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            android.Manifest.permission.CAMERA
        )
    }

    /**
     * Check if device has camera hardware
     */
    fun hasCameraHardware(): Boolean {
        return activity.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    /**
     * Check if device has ARCore support
     */
    fun isARCoreSupported(): Boolean {
        return try {
            val availability = com.google.ar.core.ArCoreApk.getInstance().checkAvailability(activity)
            availability.isSupported
        } catch (e: Exception) {
            false
        }
    }
}
