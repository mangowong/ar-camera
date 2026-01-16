package com.example.arcamera

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.arcamera.ar.ArFragment
import com.example.arcamera.utils.PermissionHelper

class MainActivity : AppCompatActivity() {

    private lateinit var permissionHelper: PermissionHelper

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            checkArCoreSupport()
        } else {
            Toast.makeText(
                this,
                getString(R.string.camera_permission_required),
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionHelper = PermissionHelper(this)

        // Request camera permission
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun checkArCoreSupport() {
        if (ArFragment.isARCoreSupported(this)) {
            initializeARFragment()
        } else {
            Toast.makeText(
                this,
                getString(R.string.ar_not_supported),
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    private fun initializeARFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, ArFragment())
        }
    }
}
