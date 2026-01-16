package com.example.arcamera.filters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import com.google.ar.sceneform.SceneView

class FilterManager(private val context: Context, private val sceneView: SceneView) {

    enum class FilterType(val displayName: String) {
        NONE("原图"),
        WARM("暖色"),
        COOL("冷色"),
        BLACK_WHITE("黑白"),
        VINTAGE("复古")
    }

    private var currentFilter = FilterType.NONE

    /**
     * Show filter selection dialog
     */
    fun showFilterSelector(
        fragmentManager: FragmentManager,
        onFilterSelected: (FilterType) -> Unit
    ) {
        val filters = FilterType.values()
        val filterNames = filters.map { it.displayName }.toTypedArray()
        val currentIndex = filters.indexOf(currentFilter)

        AlertDialog.Builder(context)
            .setTitle("选择滤镜")
            .setSingleChoiceItems(filterNames, currentIndex) { dialog, which ->
                applyFilter(filters[which])
                currentFilter = filters[which]
                onFilterSelected(filters[which])
                dialog.dismiss()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    /**
     * Apply filter to the scene view
     */
    fun applyFilter(filterType: FilterType) {
        val colorFilter = when (filterType) {
            FilterType.NONE -> null
            FilterType.WARM -> createWarmFilter()
            FilterType.COOL -> createCoolFilter()
            FilterType.BLACK_WHITE -> createBlackWhiteFilter()
            FilterType.VINTAGE -> createVintageFilter()
        }

        sceneView.renderableReleaseInstaInstanceDispose = true

        // Apply color filter to scene view
        // Note: Sceneform doesn't directly support color filters on the entire view
        // You may need to apply filters to individual renderables or use post-processing

        // For MVP, we'll store the filter and apply it to photo capture
        currentFilter = filterType
    }

    /**
     * Create warm color filter
     */
    private fun createWarmFilter(): ColorMatrixColorFilter {
        val colorMatrix = ColorMatrix()
        colorMatrix.setScale(1.1f, 1.0f, 0.9f, 1.0f)
        return ColorMatrixColorFilter(colorMatrix)
    }

    /**
     * Create cool color filter
     */
    private fun createCoolFilter(): ColorMatrixColorFilter {
        val colorMatrix = ColorMatrix()
        colorMatrix.setScale(0.9f, 1.0f, 1.1f, 1.0f)
        return ColorMatrixColorFilter(colorMatrix)
    }

    /**
     * Create black and white filter
     */
    private fun createBlackWhiteFilter(): ColorMatrixColorFilter {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        return ColorMatrixColorFilter(colorMatrix)
    }

    /**
     * Create vintage filter
     */
    private fun createVintageFilter(): ColorMatrixColorFilter {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0.7f)
        val sepia = floatArrayOf(
            1.5f, 0f, 0f, 0f, -50f,
            0f, 1.4f, 0f, 0f, -30f,
            0f, 0f, 1.2f, 0f, -20f,
            0f, 0f, 0f, 1f, 0f
        )
        colorMatrix.postConcat(ColorMatrix(sepia))
        return ColorMatrixColorFilter(colorMatrix)
    }

    /**
     * Get current filter
     */
    fun getCurrentFilter(): FilterType = currentFilter

    /**
     * Apply filter to a bitmap (for photo processing)
     */
    fun applyFilterToBitmap(bitmap: android.graphics.Bitmap, filterType: FilterType): android.graphics.Bitmap {
        val paint = android.graphics.Paint()
        paint.colorFilter = when (filterType) {
            FilterType.NONE -> return bitmap
            FilterType.WARM -> createWarmFilter()
            FilterType.COOL -> createCoolFilter()
            FilterType.BLACK_WHITE -> createBlackWhiteFilter()
            FilterType.VINTAGE -> createVintageFilter()
        }

        val filteredBitmap = android.graphics.Bitmap.createBitmap(
            bitmap.width, bitmap.height, bitmap.config
        )
        val canvas = android.graphics.Canvas(filteredBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return filteredBitmap
    }
}
