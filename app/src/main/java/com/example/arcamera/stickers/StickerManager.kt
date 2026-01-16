package com.example.arcamera.stickers

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.fragment.app.FragmentManager

class StickerManager(private val context: Context) {

    data class Sticker(
        val id: String,
        val name: String,
        val modelUri: Uri,
        val iconResId: Int? = null
    )

    private val availableStickers = listOf<Sticker>(
        // Using available demo models
        Sticker(
            "animated_box",
            "动画方块",
            Uri.parse("models/animated_box.glb")
        ),
        Sticker(
            "cesium_mini",
            "迷你角色",
            Uri.parse("models/cesium_man.glb")
        )
    )

    /**
     * Show sticker selection dialog
     */
    fun showStickerSelector(
        fragmentManager: FragmentManager,
        onStickerSelected: (Uri) -> Unit
    ) {
        val stickerNames = availableStickers.map { it.name }.toTypedArray()

        AlertDialog.Builder(context)
            .setTitle("选择贴纸")
            .setItems(stickerNames) { dialog, which ->
                onStickerSelected(availableStickers[which].modelUri)
                dialog.dismiss()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    /**
     * Get all available stickers
     */
    fun getAvailableStickers(): List<Sticker> = availableStickers

    /**
     * Get sticker by ID
     */
    fun getStickerById(id: String): Sticker? {
        return availableStickers.find { it.id == id }
    }
}
