package com.example.arcamera.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class FileUtils {

    companion object {
        /**
         * Get app-specific external storage directory
         */
        fun getAppExternalDir(context: Context): File {
            return context.getExternalFilesDir(null) ?: context.filesDir
        }

        /**
         * Get models directory
         */
        fun getModelsDir(context: Context): File {
            val modelsDir = File(getAppExternalDir(context), "models")
            if (!modelsDir.exists()) {
                modelsDir.mkdirs()
            }
            return modelsDir
        }

        /**
         * Get photos directory
         */
        fun getPhotosDir(context: Context): File {
            val photosDir = File(getAppExternalDir(context), "photos")
            if (!photosDir.exists()) {
                photosDir.mkdirs()
            }
            return photosDir
        }

        /**
         * Check if file exists
         */
        fun fileExists(context: Context, filename: String): Boolean {
            val file = File(getModelsDir(context), filename)
            return file.exists()
        }

        /**
         * Save URI to file
         */
        fun saveUriToFile(context: Context, uri: Uri, filename: String): Boolean {
            return try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputFile = File(getModelsDir(context), filename)
                val outputStream = FileOutputStream(outputFile)

                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        /**
         * Get file size in MB
         */
        fun getFileSize(file: File): Double {
            return file.length() / (1024.0 * 1024.0)
        }

        /**
         * Delete file
         */
        fun deleteFile(file: File): Boolean {
            return file.delete()
        }

        /**
         * Clear cache directory
         */
        fun clearCache(context: Context): Boolean {
            return try {
                val cacheDir = context.cacheDir
                cacheDir.deleteRecursively()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
