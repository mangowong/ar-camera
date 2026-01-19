package com.example.arcamera.ar

import android.content.Context
import android.net.Uri
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ArManager(private val arFragment: ArFragment) {

    private var characterNode: TransformableNode? = null
    private val stickerNodes = mutableListOf<Node>()

    companion object {
        // Local character model (CesiumMan from Khronos glTF samples)
        private const val CHARACTER_MODEL_URL = "models/cesium_man.glb"

        // Sticker models
        private const val STICKER_ANIMATED_BOX = "models/animated_box.glb"
    }

    /**
     * Place the character at the hit result location
     */
    fun placeCharacter(hitResult: HitResult) {
        // Create anchor
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        // Load and add character model
        loadCharacterModel { renderable ->
            renderable?.let {
                // Create transformable node
                val transformableNode = TransformableNode(arFragment.transformationSystem)
                transformableNode.setParent(anchorNode)
                transformableNode.renderable = renderable
                transformableNode.scaleController.setMinScale(0.1f)
                transformableNode.scaleController.setMaxScale(3.0f)

                // Remove existing character if any
                characterNode?.let { node ->
                    node.setParent(null)
                }

                characterNode = transformableNode

                // Position model slightly above the plane
                transformableNode.localPosition = Vector3(0f, 0.5f, 0f)
            }
        }
    }

    /**
     * Load the 3D character model
     */
    private fun loadCharacterModel(onComplete: (ModelRenderable?) -> Unit) {
        ModelRenderable.builder()
            .setSource(arFragment.requireContext(), Uri.parse(CHARACTER_MODEL_URL))
            .build()
            .thenAccept { renderable ->
                onComplete(renderable)
            }
            .exceptionally { throwable ->
                throwable.printStackTrace()
                onComplete(null)
                null
            }
    }

    /**
     * Add a sticker to the AR scene
     */
    fun addSticker(stickerModelUri: Uri) {
        val stickerNode = Node()

        ModelRenderable.builder()
            .setSource(arFragment.requireContext(), stickerModelUri)
            .build()
            .thenAccept { renderable ->
                stickerNode.renderable = renderable
                stickerNode.setParent(arFragment.arSceneView.scene)
                stickerNodes.add(stickerNode)
            }
            .exceptionally { throwable ->
                throwable.printStackTrace()
                null
            }
    }

    /**
     * Clear all stickers from the scene
     */
    fun clearStickers() {
        stickerNodes.forEach { node ->
            node.setParent(null)
        }
        stickerNodes.clear()
    }

    /**
     * Remove the character from the scene
     */
    fun removeCharacter() {
        characterNode?.let { node ->
            node.setParent(null)
            characterNode = null
        }
    }

    /**
     * Get the transformation system for gesture controls
     */
    fun getTransformationSystem(): TransformationSystem {
        return arFragment.transformationSystem
    }
}
