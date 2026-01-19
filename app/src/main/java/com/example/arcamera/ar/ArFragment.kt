package com.example.arcamera.ar

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.arcamera.R
import com.example.arcamera.camera.PhotoCaptureManager
import com.example.arcamera.filters.FilterManager
import com.example.arcamera.stickers.StickerManager
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.android.material.button.MaterialButton
import com.google.ar.sceneform.ux.ArFragment

class ArFragment : ArFragment() {

    private lateinit var arManager: ArManager
    private lateinit var photoCaptureManager: PhotoCaptureManager
    private lateinit var filterManager: FilterManager
    private lateinit var stickerManager: StickerManager

    private var currentFilter = FilterManager.FilterType.NONE

    // UI elements
    private lateinit var btnCapture: MaterialButton
    private lateinit var btnFilters: MaterialButton
    private lateinit var btnStickers: MaterialButton
    private lateinit var btnHelp: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ensure arSceneView is available before proceeding
        if (arSceneView == null) {
            Toast.makeText(requireContext(), "AR Scene View initialization failed", Toast.LENGTH_LONG).show()
            return
        }

        setupUI()
        setupManagers()
        setupPlaneDiscoveryController()
    }

    private fun setupManagers() {
        arManager = ArManager(this)
        photoCaptureManager = PhotoCaptureManager(this, arSceneView)
        filterManager = FilterManager(requireContext(), arSceneView)
        stickerManager = StickerManager(requireContext())
    }

    private fun setupUI() {
        // Get the root view from the parent ArFragment
        val rootView = arSceneView as? ViewGroup ?: return

        // Create bottom controls container
        val bottomControls = LinearLayout(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 32.toPx()
                gravity = android.view.Gravity.BOTTOM
            }
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER
        }

        // Create Filter button
        btnFilters = MaterialButton(requireContext(), null, com.google.android.material.R.style.Widget_Material3_Button_Icon).apply {
            layoutParams = LinearLayout.LayoutParams(
                64.toPx(),
                64.toPx()
            ).apply {
                rightMargin = 24.toPx()
            }
            text = "滤镜"
            setTextColor(resources.getColor(android.R.color.white, null))
            icon = requireContext().getDrawable(android.R.drawable.ic_menu_gallery)
            iconTint = requireContext().getColorStateList(android.R.color.white)
            iconSize = 32.toPx()
            background = requireContext().getDrawable(R.drawable.circle_button_background)
            contentDescription = "Filters"
        }

        // Create Capture button
        btnCapture = MaterialButton(requireContext(), null, com.google.android.material.R.style.Widget_Material3_Button_Icon).apply {
            layoutParams = LinearLayout.LayoutParams(
                80.toPx(),
                80.toPx()
            )
            icon = requireContext().getDrawable(android.R.drawable.ic_menu_camera)
            iconTint = requireContext().getColorStateList(android.R.color.white)
            iconSize = 40.toPx()
            background = requireContext().getDrawable(R.drawable.capture_button_background)
            contentDescription = "Capture Photo"
            elevation = 8f
        }

        // Create Stickers button
        btnStickers = MaterialButton(requireContext(), null, com.google.android.material.R.style.Widget_Material3_Button_Icon).apply {
            layoutParams = LinearLayout.LayoutParams(
                64.toPx(),
                64.toPx()
            ).apply {
                leftMargin = 24.toPx()
            }
            text = "贴纸"
            setTextColor(resources.getColor(android.R.color.white, null))
            icon = requireContext().getDrawable(android.R.drawable.ic_menu_add)
            iconTint = requireContext().getColorStateList(android.R.color.white)
            iconSize = 32.toPx()
            background = requireContext().getDrawable(R.drawable.circle_button_background)
            contentDescription = "Stickers"
        }

        // Add buttons to bottom controls
        bottomControls.addView(btnFilters)
        bottomControls.addView(btnCapture)
        bottomControls.addView(btnStickers)

        // Create Help button (top right)
        btnHelp = MaterialButton(requireContext(), null, com.google.android.material.R.style.Widget_Material3_Button_Icon).apply {
            layoutParams = FrameLayout.LayoutParams(
                48.toPx(),
                48.toPx()
            ).apply {
                topMargin = 16.toPx()
                rightMargin = 16.toPx()
                gravity = android.view.Gravity.TOP or android.view.Gravity.END
            }
            icon = requireContext().getDrawable(android.R.drawable.ic_menu_help)
            iconTint = requireContext().getColorStateList(android.R.color.white)
            background = requireContext().getDrawable(android.R.drawable.toast_frame)
            contentDescription = "Help"
        }

        // Add views to root
        rootView.addView(bottomControls)
        rootView.addView(btnHelp)

        // Set click listeners
        btnCapture.setOnClickListener {
            capturePhoto()
        }

        btnFilters.setOnClickListener {
            showFilterSelector()
        }

        btnStickers.setOnClickListener {
            showStickerSelector()
        }

        btnHelp.setOnClickListener {
            showHelp()
        }
    }

    private fun Int.toPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private fun setupPlaneDiscoveryController() {
        // Customize plane discovery
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)

        // Show plane detection hint
        Toast.makeText(
            requireContext(),
            getString(R.string.plane_detected),
            Toast.LENGTH_LONG
        ).show()

        // Set tap listener to place objects
        setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            arManager.placeCharacter(hitResult)
        }
    }

    private fun capturePhoto() {
        photoCaptureManager.capturePhoto { success ->
            if (success) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.photo_saved),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.photo_save_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showFilterSelector() {
        filterManager.showFilterSelector(childFragmentManager) { filterType ->
            currentFilter = filterType
        }
    }

    private fun showStickerSelector() {
        stickerManager.showStickerSelector(childFragmentManager) { stickerUri ->
            // Add sticker to AR scene
            arManager.addSticker(stickerUri)
        }
    }

    private fun showHelp() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.help_title))
            .setMessage(getString(R.string.help_text))
            .setPositiveButton("确定", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // No need to clean up binding since we're not using it anymore
    }

    companion object {
        fun isARCoreSupported(context: android.content.Context): Boolean {
            return try {
                val availability = com.google.ar.core.ArCoreApk.getInstance().checkAvailability(context)
                availability.isSupported
            } catch (e: Exception) {
                false
            }
        }
    }
}
