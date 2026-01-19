package com.example.arcamera.ar

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.arcamera.R
import com.example.arcamera.camera.PhotoCaptureManager
import com.example.arcamera.databinding.FragmentArBinding
import com.example.arcamera.filters.FilterManager
import com.example.arcamera.stickers.StickerManager
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.ux.ArFragment

class ArFragment : ArFragment() {

    private var _binding: FragmentArBinding? = null
    private val binding get() = _binding!!

    private lateinit var arManager: ArManager
    private lateinit var photoCaptureManager: PhotoCaptureManager
    private lateinit var filterManager: FilterManager
    private lateinit var stickerManager: StickerManager

    private var currentFilter = FilterManager.FilterType.NONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupManagers()
        setupUI()
        setupPlaneDiscoveryController()
    }

    private fun setupManagers() {
        arManager = ArManager(this)
        photoCaptureManager = PhotoCaptureManager(this, arSceneView)
        filterManager = FilterManager(requireContext(), arSceneView)
        stickerManager = StickerManager(requireContext())
    }

    private fun setupUI() {
        // Photo capture button
        binding.btnCapture.setOnClickListener {
            capturePhoto()
        }

        // Filter button
        binding.btnFilters.setOnClickListener {
            showFilterSelector()
        }

        // Stickers button
        binding.btnStickers.setOnClickListener {
            showStickerSelector()
        }

        // Help button
        binding.btnHelp.setOnClickListener {
            showHelp()
        }
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
        _binding = null
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
