package com.example.arcamera.camera

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.PixelCopy
import androidx.fragment.app.Fragment
import com.google.ar.sceneform.SceneView
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.OutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PhotoCaptureManager(
    private val fragment: Fragment,
    private val sceneView: SceneView
) {

    /**
     * Capture the current AR scene as a photo
     */
    suspend fun capturePhoto(): Boolean = suspendCancellableCoroutine { continuation ->
        val width = sceneView.width
        val height = sceneView.height

        if (width == 0 || height == 0) {
            continuation.resume(false)
            return@suspendCancellableCoroutine
        }

        // Create bitmap to store the captured image
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Request pixel copy from the scene view
        PixelCopy.request(sceneView, bitmap, { result ->
            if (result == PixelCopy.SUCCESS) {
                // Save the bitmap to gallery
                saveBitmapToGallery(bitmap) { success ->
                    continuation.resume(success)
                }
            } else {
                continuation.resume(false)
            }
        }, fragment.requireContext().mainHandler)
    }

    /**
     * Capture photo with callback (non-suspend version)
     */
    fun capturePhoto(onComplete: (Boolean) -> Unit) {
        val width = sceneView.width
        val height = sceneView.height

        if (width == 0 || height == 0) {
            onComplete(false)
            return
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        PixelCopy.request(sceneView, bitmap, { result ->
            if (result == PixelCopy.SUCCESS) {
                saveBitmapToGallery(bitmap, onComplete)
            } else {
                onComplete(false)
            }
        }, fragment.requireContext().mainHandler)
    }

    /**
     * Save bitmap to device gallery
     */
    private fun saveBitmapToGallery(bitmap: Bitmap, onComplete: (Boolean) -> Unit) {
        val context = fragment.requireContext()

        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "AR_Photo_${System.currentTimeMillis()}")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(collection, contentValues)

            uri?.let {
                val outputStream: OutputStream = resolver.openOutputStream(it) ?: run {
                    onComplete(false)
                    return
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(it, contentValues, null, null)
                }

                onComplete(true)
            } ?: onComplete(false)
        } catch (e: Exception) {
            e.printStackTrace()
            onComplete(false)
        }
    }
}
